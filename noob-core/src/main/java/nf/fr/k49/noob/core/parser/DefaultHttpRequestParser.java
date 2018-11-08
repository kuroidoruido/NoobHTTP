package nf.fr.k49.noob.core.parser;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import nf.fr.k49.noob.core.message.HttpVersion;
import nf.fr.k49.noob.core.message.Method;
import nf.fr.k49.noob.core.message.Request;

public class DefaultHttpRequestParser implements HttpRequestParser {

	@Override
	public Request<String> parse(final List<String> strReq) {
		final Request<String> req = Request.build("");

		final Iterator<String> it = strReq.iterator();
		parseFirstLine(req, it.next());
		parseHeaders(req, it);
		parseBody(req, it);

		return req;
	}

	protected void parseFirstLine(final Request<String> req, String firstLine) {
		final String[] splitted = firstLine.split(" ");
		req.setMethod(Method.valueOf(splitted[0]));
		req.setHttpVersion(HttpVersion.of(splitted[splitted.length - 1]));
		req.setResourcePath(String.join(" ", Arrays.copyOfRange(splitted, 1, splitted.length - 1)));
	}

	protected void parseHeaders(final Request<String> req, final Iterator<String> it) {
		String line;
		String[] splitted;
		while (it.hasNext() && !"".equals(line = it.next())) {
			splitted = line.split(":");
			req.getHeaders().put(splitted[0].trim(), splitted[1].trim());
		}
	}

	protected void parseBody(Request<String> req, Iterator<String> it) {
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append("\n");
			}
		}
		req.setBody(sb.toString());
	}

}
