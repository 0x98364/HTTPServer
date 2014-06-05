package main;

import java.io.IOException;
import java.net.Socket;

public class ListenerThread extends Thread {

	private boolean stop=false;
	
	@Override
	public void run() {
		try {
			while(!stop){
				System.out.println("Esperando conexiones entrantes...");
				Socket s = Server.srv.accept();
				System.out.println("Conexion entrande desde " + s.getInetAddress());
				
				/*---ATENDER CONEXION ENTRANTE EN OTRO HILO---*/
				
				ClienteThread st = new ClienteThread(s);
				st.start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bucleListener(Server srv){
		
	}
	
	
}
