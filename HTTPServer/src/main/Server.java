package main;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	private int port=-1;
	public static int STANDART_PORT=8040;
	private int stackSize;
	private boolean stop;
	
	public void startup(){
		ServerSocket srv = null;
		
		if(port==-1){
			port=STANDART_PORT;
		}
		
		/*---REGISTRAR PUERTO DE ESCUCHA---*/
		try {
			System.err.println("Registrando puerto " + port + ".....");
			srv = new ServerSocket(port,stackSize);
			System.err.println("Puerto registrado " + port);
			
			/*---ADMINISTRAR CONEXIONES ENTRANTES--*/
			try {
				while(!stop){
					System.out.println("Esperando conexion entrante...");
					Socket s = srv.accept();
					System.out.println("Conexion entrande desde " + s.getInetAddress());
					
					/*---ATENDER CONEXION ENTRANTE EN OTRO HILO---*/
					ClienteThread st = new ClienteThread(s);
					st.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}catch(BindException e){
			System.err.println("Ese puerto ya esta en uso!");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Server srv = new Server();
		
		srv.startup();
		

	}

}
