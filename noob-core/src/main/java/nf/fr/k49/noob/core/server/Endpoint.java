package nf.fr.k49.noob.core.server;

import java.lang.reflect.Type;
import java.util.function.Function;

import nf.fr.k49.noob.core.message.Request;
import nf.fr.k49.noob.core.message.Response;

public class Endpoint {
	@SuppressWarnings("rawtypes")
	final private Function<Request, Response> function;
	final private Type inputType;
	
	@SuppressWarnings("rawtypes")
	public Endpoint(final Function<Request, Response> function, final Type inputType) {
		this.function = function;
		this.inputType = inputType;
	}

	@SuppressWarnings("rawtypes")
	public Function<Request, Response> getFunction() {
		return function;
	}

	public Type getInputType() {
		return inputType;
	}
}
