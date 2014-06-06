package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.text.Caret;


public class Window extends JFrame {
	public JPanel contenedor;
	public JButton on, off, restart, exit;
	public JTextField user;
	public JPasswordField pass;
	public JLabel us, ps;
	public JTextArea book; 
	public JMenuBar barraMenu;
	public JMenu archivo,fuentes;
	public JMenuItem blanco,azul,salir;

	public Window(){
		
		setTitle("SERVER");
		setBounds(0, 0, 500, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		/*Login*/
		user=new JTextField();
		user.setBounds(85, 10, 80, 20);
		us=new JLabel("Puerto:");
		us.setBounds(25, 10, 50, 20);
		
		/*Botones*/
		on=new JButton("Start");
		on.setBounds(390, 20, 80, 30);
		off=new JButton("Stop");
		off.setBounds(390, 80, 80, 30);
		restart=new JButton("Restart");
		restart.setBounds(390, 140, 80, 30);
		off.setEnabled(false);
		restart.setEnabled(false);
		
		/*Área de texto*/
		book=new JTextArea(20,20);
		book.setBounds(30, 50, 330, 190);
		book.setBackground(Color.BLACK);
		book.setForeground(Color.GREEN);
		book.setEditable(false);
			
		/*Menús*/
			barraMenu=new JMenuBar();
			archivo=new JMenu("Archivo");
			barraMenu.add(archivo);
						
			archivo.add(new JSeparator());

			archivo.add(new JSeparator());
			salir=new JMenuItem("Salir");
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
				contenedor.add(book);
				contenedor.add(user);
				contenedor.add(us);
				
		
		}
}

