package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

import GUI.Window;

public class Server {
	private Window w = new Window();
	private int port=-1;
	public static int STANDART_PORT=8040;
	private int stackSize;
	private boolean stop;
	public static ServerSocket srv = null;
	ListenerThread lt;
	
	public Server(){
	eventos e=new eventos();
	w.salir.addActionListener(e);
	
	/*Eventos*/
	
	w.on.addActionListener(e);
	w.off.addActionListener(e);
	w.restart.addActionListener(e);
	}
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public void startup(){
		
		
		if(port==-1){
			port=STANDART_PORT;
		}
		
		/*---REGISTRAR PUERTO DE ESCUCHA---*/
		try {
			System.err.println("Registrando puerto " + port + ".....");
			srv = new ServerSocket(port,stackSize);
			System.err.println("Puerto registrado " + port);
			
			/*---INICIMAOS EL HILO ESCUCHADOR DE CONEXIONES--*/
			lt = new ListenerThread();
			lt.start();
			
		}catch(BindException e){
			System.err.println("Ese puerto ya esta en uso!");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
			
	public class eventos implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==w.salir || e.getSource()==w.exit){
				System.exit(0);
			}	if(e.getSource()==w.on){
				startup();
			}
			else if(e.getSource()==w.off){
				lt.stop();
			}
			else if(e.getSource()==w.restart){
				startup();
			}
			else if(e.getSource()==w.exit){
				int valor=JOptionPane.showConfirmDialog(w.contenedor, "Are you sure to want to quit?"
						, "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
				if (valor==JOptionPane.YES_OPTION) {System.exit(1);}
				if (valor==JOptionPane.NO_OPTION) {System.exit(0);}
			}
		}
		
	}
		
	public static void main(String[] args) {
		
		Server srv = new Server();
		srv.w.setVisible(true);
		
		}

	}
