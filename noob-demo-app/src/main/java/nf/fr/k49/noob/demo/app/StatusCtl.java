package nf.fr.k49.noob.demo.app;

import nf.fr.k49.noob.core.message.Request;
import nf.fr.k49.noob.core.message.Response;
import nf.fr.k49.noob.demo.app.model1.Status;

public class StatusCtl {
	
	public Response<Status> getStatus(Request<Void> req) {
		final Response<Status> res = Response.ok(req, new Status());
		res.getHeaders().put("X-Test","myTestHeaderValue");
		return res;
	}
}
