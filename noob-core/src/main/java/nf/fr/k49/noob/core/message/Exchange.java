package nf.fr.k49.noob.core.message;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Exchange<T> {

	protected HttpVersion httpVersion;
	protected T body;
	protected Map<String, String> headers;

	protected Exchange(T body) {
		this.body = body;
		this.headers = new HashMap<String, String>();
	}

	public HttpVersion getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(HttpVersion httpVersion) {
		this.httpVersion = httpVersion;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	public int hashCode() {
		return Objects.hash(body, headers, httpVersion);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exchange other = (Exchange) obj;
		return Objects.equals(body, other.body) && Objects.equals(headers, other.headers)
				&& httpVersion == other.httpVersion;
	}

	@Override
	public String toString() {
		return "Exchange [httpVersion=" + httpVersion + ", body=" + body + ", headers=" + headers + "]";
	}

}
