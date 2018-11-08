package nf.fr.k49.noob.core.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import nf.fr.k49.noob.core.digest.NoobRequestDigester;

public class NoobServer {

	private final int listenPort;
	private NoobRequestDigester requestDigester;

	public NoobServer(int listenPort, final NoobRequestDigester requestDigester) {
		this.listenPort = listenPort;
		this.requestDigester = requestDigester;
	}

	public NoobServer start() {
		new Thread() {
			@Override
			public void run() {
				super.run();

				while (true) {
					try (final ServerSocket serverSocket = new ServerSocket(listenPort)) {
						Socket clientSocket;
						while ((clientSocket = serverSocket.accept()) != null) {
							requestDigester.digest(clientSocket);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();

		return this;
	}
}
