package nf.fr.k49.noob.core.parser;

import java.util.List;

import nf.fr.k49.noob.core.message.Request;

public interface HttpRequestParser {

	Request<String> parse(final List<String> strReq); 
}
