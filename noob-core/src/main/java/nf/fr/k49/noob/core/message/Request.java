package nf.fr.k49.noob.core.message;

import java.util.Objects;

public class Request<T> extends Exchange<T> {

	private Method method;
	private String resourcePath;
	
	protected Request(T body) {
		super(body);
	}

	public static <T> Request<T> build(T body) {
		return new Request<T>(body);
	}

	public static <T,U> Request<T> build(T body, Request<U> from) {
		final Request<T> request = new Request<T>(body);
		request.headers = from.headers;
		request.httpVersion = from.httpVersion;
		request.method = from.method;
		request.resourcePath = from.resourcePath;
		return request;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(method, resourcePath);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		return method == other.method && Objects.equals(resourcePath, other.resourcePath);
	}

	@Override
	public String toString() {
		return "Request [method=" + method + ", resourcePath=" + resourcePath + ", httpVersion=" + httpVersion
				+ ", body=" + body + ", headers=" + headers + "]";
	}
}
