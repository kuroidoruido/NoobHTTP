package nf.fr.k49.noob.demo.app;

import java.util.ArrayList;
import java.util.List;

import nf.fr.k49.noob.core.message.Request;
import nf.fr.k49.noob.core.message.Response;
import nf.fr.k49.noob.demo.app.model1.Element;

public class ElementCtl {

	private List<Element> elements = new ArrayList<Element>();
	
	public Response<List<Element>> add(Request<Element> req) {
		elements.add(req.getBody());
		System.out.println(elements);
		return Response.ok(req, elements);
	}
}
