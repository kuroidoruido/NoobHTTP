package nf.fr.k49.noob.core.message;

public class Response<T> extends Exchange<T> {

	private int statusCode;
	private String statusPhrase;

	protected Response(T body) {
		super(body);
	}

	public static <T> Response<T> build(T body) {
		return new Response<T>(body);
	}
	
	public static Response<String> notFound(Request<?> req) {
		final Response<String> res = Response.build("");
		res.setStatusCode(404);
		res.setStatusPhrase("Not Found");
		res.setHttpVersion(req.getHttpVersion());
		return res;
	}
	
	public static <T> Response<T> ok(Request<?> req, T body) {
		final Response<T> res = Response.build(body);
		res.setStatusCode(200);
		res.setStatusPhrase("OK");
		res.setHttpVersion(req.getHttpVersion());
		return res;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusPhrase() {
		return statusPhrase;
	}

	public void setStatusPhrase(String statusPhrase) {
		this.statusPhrase = statusPhrase;
	}

	@Override
	public String toString() {
		return "Response [statusCode=" + statusCode + ", statusPhrase=" + statusPhrase + ", httpVersion=" + httpVersion
				+ ", body=" + body + ", headers=" + headers + "]";
	}

}
