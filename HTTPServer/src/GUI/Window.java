package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.Caret;


public class Window extends JFrame {
	public JPanel contenedor, abrir;
	public JButton on, off, restart, exit;
	public JTextField portNum;
	public JPasswordField pass;
	public JLabel port;
	public JTextArea book; 
	public JMenuBar barraMenu;
	public JMenu archivo;
	public JMenuItem salir, mostrar, guardarc;
	public JScrollPane b;
	private final String ACTUAL_VERSION = "1.0";
	
	public Window(){
		
		setTitle("[ DRKWB - Java Server ] " + "Current Version " + ACTUAL_VERSION);
		setBounds(100, 100, 500, 320);
		setResizable(false);
		setIconImage(new ImageIcon("src/guard.png").getImage());
		
		/*Login*/
		portNum=new JTextField();
		portNum.setBounds(75, 20, 50, 20);
		port=new JLabel("Puerto:");
		port.setBounds(30, 20, 50, 20);
		
		/*Botones*/
		on=new JButton("Start");
		on.setBounds(390, 60, 80, 30);
		off=new JButton("Stop");
		off.setBounds(390, 120, 80, 30);
		restart=new JButton("Restart");
		restart.setBounds(390, 180, 80, 30);
		off.setEnabled(false);
		restart.setEnabled(false);
		
		/*Área de texto*/
		book=new JTextArea(10,20);
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		book.setBorder(BorderFactory.createCompoundBorder(border, 
	            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		b=new JScrollPane(book);
		b.setBounds(30, 50, 330, 190);
		add(b);
		book.setBackground(Color.BLACK);
		book.setForeground(Color.WHITE);
		book.setLineWrap(true);
		book.setWrapStyleWord(true);
		book.setEditable(false);
			
		/*Menús*/
		barraMenu=new JMenuBar();
		archivo=new JMenu("Archivo");
		barraMenu.add(archivo);
		
		/*Items Menu*/
		salir=new JMenuItem("Salir");
		guardarc=new JMenuItem("Guardar Como...");
		mostrar=new JMenuItem("Mostrar Errores");
		
	    archivo.add(guardarc);	
	    archivo.add(new JSeparator());
		archivo.add(mostrar);	
		archivo.add(new JSeparator());
		archivo.add(salir); 	
			
		/*Contenedor*/
		contenedor=new JPanel();
		getContentPane().add(contenedor);
		setJMenuBar(barraMenu);
		
		//Add
		contenedor.setLayout(null);
		contenedor.add(on);
		contenedor.add(off);
		contenedor.add(restart);
		
		contenedor.add(port);
		contenedor.add(portNum);
		}
}

