package nf.fr.k49.noob.core.message;

public enum HttpVersion {
	HTTP_1_0("HTTP/1.0"), HTTP_1_1("HTTP/1.1"), HTTP_2("HTTP/2"), UNKNOWN("");

	private final String msgStr;

	HttpVersion(final String messageStr) {
		this.msgStr = messageStr;
	}

	public String toString() {
		return this.msgStr;
	}

	public static HttpVersion of(final String messageStr) {
		switch (messageStr) {
		case "HTTP/1.0":
			return HttpVersion.HTTP_1_0;
		case "HTTP/1.1":
			return HttpVersion.HTTP_1_1;
		case "HTTP/2":
			return HttpVersion.HTTP_2;
		default:
			return UNKNOWN;
		}
	}
}
