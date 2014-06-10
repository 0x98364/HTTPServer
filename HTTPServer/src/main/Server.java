package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import GUI.Window;

public class Server {
	public Window w = new Window();
	private int port;
	private int stackSize;
	private boolean stop;
	public static ServerSocket srv = null;
	private String logsDirectory = "logs/";
	ListenerThread lt;
	
	public Server(){
		/*Eventos*/
	eventos e=new eventos();
	mostrar m=new mostrar();
	
	w.salir.addActionListener(e);
	w.on.addActionListener(e);
	w.off.addActionListener(e);
	w.restart.addActionListener(e);
	w.portNum.addActionListener(e);
	w.salir.addActionListener(e);
		/*Mostrar elementos del menu*/
	w.archivo.addActionListener(m);
	w.guardarc.addActionListener(m);
	w.mostrar.addActionListener(m);
 //Método para escuchar los botones de la interfaz predeterminados
	w.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e) {
			int n=JOptionPane.showConfirmDialog(w.contenedor,
					"Are you sure to want to shotdown the Server?", 
					"Close Confirmation", JOptionPane.YES_NO_OPTION);
			if(n==JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}});
	
	}
	public boolean isStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public void startup(){		
		/*---REGISTRAR PUERTO DE ESCUCHA---*/
		try {
			w.book.setText(w.book.getText() + "Registrando puerto " + port + ".....\n");
			srv = new ServerSocket(port,stackSize);
			w.book.setText(w.book.getText()+"Puerto registrado " + port + "\n");
			
			/*---INICIMAOS EL HILO ESCUCHADOR DE CONEXIONES--*/
			lt = new ListenerThread(w);
			lt.start();
			
		}catch(BindException e){
			w.book.setText("Ese puerto ya esta en uso!\n");
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public class mostrar implements ActionListener{
		public void actionPerformed(ActionEvent m) {
			if(m.getSource()==w.guardarc){
				JFileChooser guardarArchivo = new JFileChooser();
				guardarArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int seleccion = guardarArchivo.showSaveDialog(null);
				if(seleccion == JFileChooser.APPROVE_OPTION){
				File f = guardarArchivo.getSelectedFile();
				String path = f.getAbsolutePath();
				if(f.exists()){
					if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(w.contenedor,"El fichero existe,deseas reemplazarlo?","Sobreescribir Archivo",JOptionPane.YES_NO_OPTION)){
						salvarArchivo(path);
					}
				}else{
					try {
						f.createNewFile();
						salvarArchivo(path);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
			if(m.getSource()==w.mostrar){
				try {
		            Runtime obj = Runtime.getRuntime();
		            obj.exec("notepad "+logsDirectory+"events.log");
		        } catch (IOException ex) {
		             System.out.println("IOException "+ex.getMessage());
		        }
			}
	}
		public void salvarArchivo(String path){
			FileWriter fw;
			BufferedWriter bw = null ;
			String contenido="";
			try {
				fw = new FileWriter(path);
				bw = new BufferedWriter(fw);
				
				BufferedReader reader = new BufferedReader(
						  new StringReader(w.book.getText()));
				
				String linea;
				while((linea=reader.readLine())!=null){
					contenido += linea + "\n";
				}
				
				bw.write(contenido);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public class eventos implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource()==w.salir){
				int n=JOptionPane.showConfirmDialog(w.contenedor, "Are you sure to close the Server?", "Close Confirmation", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_NO_OPTION){System.exit(0);}
				
				
			}if(e.getSource()==w.on){
				if(w.portNum.getText().isEmpty()){
					JOptionPane.showMessageDialog(w.contenedor, "Error!. The port is empty", "Wrong Port", JOptionPane.INFORMATION_MESSAGE);
				}else{
				w.portNum.getText();
				try{
					int p=Integer.parseInt(w.portNum.getText());
					port=p;
					startup();
					w.on.setEnabled(false);
					w.off.setEnabled(true);
					w.restart.setEnabled(true);
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(w.contenedor, "¡Error!. The port is not a number!", "Wrong Port", JOptionPane.INFORMATION_MESSAGE);
				}
				}				
			}else if(e.getSource()==w.off){
				int n=JOptionPane.showConfirmDialog(w.contenedor, "Are you sure to stop the Server?", "Close Confirmation", JOptionPane.YES_NO_OPTION);
				if(n==JOptionPane.YES_NO_OPTION){
					lt.stop();
					w.on.setEnabled(true);
					w.book.setText(w.book.getText()+"Se ha parado el servidor\n");
					try {
						srv.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					w.off.setEnabled(false);
					w.restart.setEnabled(false);
				}
				
			}else if(e.getSource()==w.restart){
				if(w.portNum.getText().isEmpty()){
					JOptionPane.showMessageDialog(w.contenedor, "¡Error!. The port is empty", "Wrong Port", JOptionPane.INFORMATION_MESSAGE);
				}else{
					w.portNum.getText();
					try{
						srv.close();
						lt.stop();
						int p=Integer.parseInt(w.portNum.getText());
						port=p;
						startup();
					}catch(NumberFormatException | IOException ex){
						JOptionPane.showMessageDialog(w.contenedor, "¡Error!. The port is not a number", "Wrong Port", JOptionPane.INFORMATION_MESSAGE);
					}
					//w.restart.setEnabled(false);
					w.off.setEnabled(true);
					w.book.setText(w.book.getText()+"Se ha reiniciado el servidor\n");
				}				
			}else if(e.getSource()==w.exit){
				int valor=JOptionPane.showConfirmDialog(w.contenedor, "Are you sure to want to quit?"
						, "Exit Confirmation", JOptionPane.YES_NO_CANCEL_OPTION);
				if (valor==JOptionPane.YES_OPTION) {System.exit(0);}
				if (valor==JOptionPane.NO_OPTION) {System.exit(0);}
			}
		}		
	}		
	public static void main(String[] args) {
		
		Server srv = new Server();
		srv.w.setVisible(true);
		
		}

	}
