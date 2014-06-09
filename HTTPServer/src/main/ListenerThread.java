package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import GUI.Window;

public class ListenerThread extends Thread {

	private boolean stop=false;
	private Window w;
	

	public ListenerThread(Window w) {
		// TODO Auto-generated constructor stub
		this.w=w;
	}

	@Override
	public void run() {
		try {
			while(!stop){
				w.book.setText(w.book.getText() + "Esperando conexiones entrantes...\n");
				Socket s = Server.srv.accept();
				w.book.setText(w.book.getText() + "Conexion entrande desde " + s.getInetAddress() + "\n");
				
				/*---ATENDER CONEXION ENTRANTE EN OTRO HILO---*/
				
				ClienteThread st = new ClienteThread(s,w);
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
