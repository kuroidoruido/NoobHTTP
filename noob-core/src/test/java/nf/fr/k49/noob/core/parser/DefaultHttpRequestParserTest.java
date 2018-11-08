package nf.fr.k49.noob.core.parser;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nf.fr.k49.noob.core.message.HttpVersion;
import nf.fr.k49.noob.core.message.Method;
import nf.fr.k49.noob.core.message.Request;

public class DefaultHttpRequestParserTest {

	private DefaultHttpRequestParser parser;

	@BeforeEach
	public void beforeEach() {
		this.parser = new DefaultHttpRequestParser();
	}

	@Test
	public void testParseFirstLine() {
		final Request<String> expected = Request.build("");
		expected.setMethod(Method.GET);
		expected.setResourcePath("/status");
		expected.setHttpVersion(HttpVersion.HTTP_1_1);

		final Request<String> actual = Request.build("");
		parser.parseFirstLine(actual, "GET /status HTTP/1.1");

		assertEquals(expected, actual);
	}

	@Test
	public void testParseHeaders() {
		final Request<String> expected = Request.build("");
		expected.getHeaders().put("Host", "github.com");
		expected.getHeaders().put("Cookie", "myCookies");

		final Request<String> actual = Request.build("");
		List<String> input = Arrays.asList("Host: github.com", "Cookie:myCookies ", "");
		parser.parseHeaders(actual, input.iterator());

		assertEquals(expected, actual);
	}

	@Test
	public void testParseBody() {
		final Request<String> expected = Request.build("");
		expected.setBody("{\n\"test\":\"noob\"\n}\n");

		final Request<String> actual = Request.build("");
		List<String> input = Arrays.asList("{", "\"test\":\"noob\"", "}", "");
		parser.parseBody(actual, input.iterator());

		assertEquals(expected, actual);
	}

	@Test
	public void testParseBodyEmpty() {
		final Request<String> expected = Request.build("");
		expected.setBody("");

		final Request<String> actual = Request.build("");
		List<String> input = Arrays.asList();
		parser.parseBody(actual, input.iterator());

		assertEquals(expected, actual);
	}
}
