package fr.thisismac.mineweb.web; 

import org.eclipse.jetty.server.Server;
import fr.thisismac.mineweb.Core;
import lombok.Getter;

public class WebThread extends Thread {
	
	@Getter private Server webServer;
	
	public WebThread () {
		this.webServer = new Server(Core.get().getDB().getPort());
	}
	
	@Override
	public void run() {
		try {
			webServer.setHandler(new WebHandler());
			webServer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stopThread() {
		try {
			webServer.stop();
			interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
