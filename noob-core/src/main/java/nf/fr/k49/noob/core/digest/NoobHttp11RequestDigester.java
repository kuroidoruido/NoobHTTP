package nf.fr.k49.noob.core.digest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import nf.fr.k49.noob.core.mapper.NoobMapper;
import nf.fr.k49.noob.core.message.Request;
import nf.fr.k49.noob.core.message.Response;
import nf.fr.k49.noob.core.parser.HttpRequestParser;
import nf.fr.k49.noob.core.server.Endpoint;

public class NoobHttp11RequestDigester implements NoobRequestDigester {

	private static final int DEFAULT_THREAD_NUMBER = 5;

	private final Map<String, Endpoint> endpoints;
	private final HttpRequestParser httpRequestParser;
	private final NoobMapper mapper;
	private final ExecutorService executor;

	public NoobHttp11RequestDigester(final Map<String, Endpoint> endpoints, final HttpRequestParser httpRequestParser,
			final NoobMapper mapper) {
		this(endpoints, httpRequestParser, mapper, DEFAULT_THREAD_NUMBER);
	}

	public NoobHttp11RequestDigester(final Map<String, Endpoint> endpoints, final HttpRequestParser httpRequestParser,
			final NoobMapper mapper, int threadNumber) {
		this.endpoints = endpoints;
		this.httpRequestParser = httpRequestParser;
		this.mapper = mapper;
		this.executor = Executors.newFixedThreadPool(threadNumber);
	}

	@Override
	public void digest(final Socket clientSocket) {
		executor.execute(new Worker(endpoints, httpRequestParser, mapper, clientSocket));
	}

	private static class Worker implements Runnable {

		private final Map<String, Endpoint> endpoints;
		private final Socket clientSocket;
		private final HttpRequestParser httpRequestParser;
		private final NoobMapper mapper;

		public Worker(final Map<String, Endpoint> endpoints, final HttpRequestParser httpRequestParser,
				final NoobMapper mapper, final Socket clientSocket) {
			this.endpoints = endpoints;
			this.httpRequestParser = httpRequestParser;
			this.mapper = mapper;
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			try {
				final OutputStream outSock = clientSocket.getOutputStream();
				final List<String> rawReq = readRequest(clientSocket.getInputStream());
				final Request<String> parsedReq = httpRequestParser.parse(rawReq);
				final Endpoint endpoint = endpoints.get(parsedReq.getMethod() + " " + parsedReq.getResourcePath());
				if (endpoint == null) {
					writeResponse(outSock, Response.notFound(parsedReq));
				} else {
					@SuppressWarnings("rawtypes")
					final Response res = endpoint.getFunction()
							.apply(toTypedRequest(parsedReq, endpoint.getInputType()));
					writeResponse(outSock, res);
					// TODO do not close to support pipeline requests
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (clientSocket != null && !clientSocket.isClosed()) {
					try {
						clientSocket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		private List<String> readRequest(InputStream inputStream) throws IOException {
			final List<String> res = new ArrayList<>();
			final BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			// reads headers
			String line;
			while (!"".equals(line = in.readLine())) {
				res.add(line);
			}
			res.add(line);

			// reads body only if Content-Length header is present
			Optional<String> contentLengthLine = res.stream()//
					.filter(l -> l.startsWith("Content-Length")).findFirst();
			if (contentLengthLine.isPresent()) {
				int contentLength = Integer.parseInt(contentLengthLine.get().split(":")[1].trim());
				try {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < contentLength; i++) {
						int c = in.read();
						if (c != 10) { // 10 == '\n'
							char cc = (char) c;
							sb.append(cc);
						}
					}
					res.add(sb.toString());
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
			return res;
		}

		private void writeResponse(OutputStream outputStream, @SuppressWarnings("rawtypes") Response res)
				throws IOException {
			final StringBuffer sb = new StringBuffer();
			sb.append(res.getHttpVersion() + " " + res.getStatusCode() + " " + res.getStatusPhrase() + "\n");
			@SuppressWarnings("unchecked")
			final Set<Map.Entry<String, String>> headers = res.getHeaders().entrySet();
			for (Map.Entry<String, String> e : headers) {
				sb.append(e.getKey() + ": " + e.getValue() + "\n");
			}
			sb.append("\n");
			sb.append(this.mapper.toJson(res.getBody()));

			try (PrintWriter out = new PrintWriter(outputStream)) {
				out.println(sb.toString());
			}
		}

		@SuppressWarnings("rawtypes")
		private Request toTypedRequest(Request<String> strReq, Type inputType) {
			return Request.build(this.mapper.toObject(strReq.getBody(), inputType), strReq);
		}

	}

}
