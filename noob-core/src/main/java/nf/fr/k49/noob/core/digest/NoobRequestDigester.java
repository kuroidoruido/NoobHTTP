package nf.fr.k49.noob.core.digest;

import java.net.Socket;

public interface NoobRequestDigester {

	void digest(final Socket clientSocket);
}
