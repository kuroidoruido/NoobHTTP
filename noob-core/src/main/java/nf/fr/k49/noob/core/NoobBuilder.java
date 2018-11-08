package nf.fr.k49.noob.core;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import nf.fr.k49.noob.core.digest.NoobHttp11RequestDigester;
import nf.fr.k49.noob.core.mapper.NoobMapper;
import nf.fr.k49.noob.core.message.Request;
import nf.fr.k49.noob.core.message.Response;
import nf.fr.k49.noob.core.parser.DefaultHttpRequestParser;
import nf.fr.k49.noob.core.server.NoobServer;
import nf.fr.k49.noob.core.server.Endpoint;

public class NoobBuilder {

	private int listenPort;
	private NoobMapper mapper;

	private final Map<String, Endpoint> endpoints;

	protected NoobBuilder() {
		this.listenPort = 8080;
		this.endpoints = new HashMap<>();
	}

	public NoobBuilder listenPort(int listenPort) {
		this.listenPort = listenPort;
		return this;
	}

	public NoobBuilder mapper(final NoobMapper mapper) {
		this.mapper = mapper;
		return this;
	}

	/**
	 * Declares a new DELETE endpoint.
	 * 
	 * @param path a regex that match accepted url
	 * @param fn   the Function that will called when a matching request will be
	 *             received.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <I, O> NoobBuilder delete(final String path, final Function<Request<I>, Response<O>> fn,
			final Type inputType) {
		this.endpoints.put("DELETE " + path, new Endpoint((Function) fn, inputType));
		return this;
	}

	/**
	 * Declares a new GET endpoint.
	 * 
	 * @param path a regex that match accepted url
	 * @param fn   the Function that will called when a matching request will be
	 *             received.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <I, O> NoobBuilder get(final String path, final Function<Request<I>, Response<O>> fn, final Type inputType) {
		this.endpoints.put("GET " + path, new Endpoint((Function) fn, inputType));
		// TODO add HEAD request with a wrapper to remove body + "payload header fields"
		// see https://tools.ietf.org/html/rfc7231#section-4.3.2
		// and https://tools.ietf.org/html/rfc7231#section-3.3
		return this;
	}

	/**
	 * Declares a new PATCH endpoint.
	 * 
	 * @param path a regex that match accepted url
	 * @param fn   the Function that will called when a matching request will be
	 *             received.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <I, O> NoobBuilder patch(final String path, final Function<Request<I>, Response<O>> fn,
			final Type inputType) {
		this.endpoints.put("PATCH " + path, new Endpoint((Function) fn, inputType));
		return this;
	}

	/**
	 * Declares a new POST endpoint.
	 * 
	 * @param path a regex that match accepted url
	 * @param fn   the Function that will called when a matching request will be
	 *             received.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <I, O> NoobBuilder post(final String path, final Function<Request<I>, Response<O>> fn,
			final Type inputType) {
		this.endpoints.put("POST " + path, new Endpoint((Function) fn, inputType));
		return this;
	}

	/**
	 * Declares a new PUT endpoint.
	 * 
	 * @param path a regex that match accepted url
	 * @param fn   the Function that will called when a matching request will be
	 *             received.
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <I, O> NoobBuilder put(final String path, final Function<Request<I>, Response<O>> fn, final Type inputType) {
		this.endpoints.put("PUT " + path, new Endpoint((Function) fn, inputType));
		return this;
	}

	public NoobServer start() {
		return new NoobServer(this.listenPort,
				new NoobHttp11RequestDigester(this.endpoints, new DefaultHttpRequestParser(), this.mapper)).start();
	}

}
