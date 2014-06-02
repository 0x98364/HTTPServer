package main;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import GUI.Window;


public class Server {
	private Window w = new Window();
	private int port=-1;
	public static int STANDART_PORT=8040;
	private int stackSize;
	private boolean stop;
	private ServerLog log;
	
	public void startup(){
		ServerSocket srv = null;
		log = new ServerLog("logs/");
		
		if(port==-1){
			port=STANDART_PORT;
		}
		
		/*---REGISTRAR PUERTO DE ESCUCHA---*/
		try {
			System.err.println("Registrando puerto " + port + ".....");
			srv = new ServerSocket(port,stackSize);
			System.err.println("Puerto registrado " + port);
			log.saveMsg("Server initialized on port " + port);
			
			/*---ADMINISTRAR CONEXIONES ENTRANTES--*/
			try {
				while(!stop){
					System.out.println("Esperando conexiones entrantes...");
					Socket s = srv.accept();
					System.out.println("Conexion entrande desde " + s.getInetAddress());
					
					/*---ATENDER CONEXION ENTRANTE EN OTRO HILO---*/
					
					ClienteThread st = new ClienteThread(s);
					st.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.saveMsg("Server ERROR on listening clients");
			}
			
		}catch(BindException e){
			System.err.println("Ese puerto ya esta en uso!");
			log.saveMsg("Server ERROR on bind " + port);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.saveMsg("Server initialization ERROR");
		}
	}
	public static void main(String[] args) {
		Server srv = new Server();
		srv.w.setVisible(true);
		srv.startup();
	}

}
