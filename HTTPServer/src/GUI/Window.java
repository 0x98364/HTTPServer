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
		
		/*Login*/
		user=new JTextField();
		user.setBounds(85, 10, 80, 20);
		us=new JLabel("Usuario:");
		us.setBounds(25, 10, 50, 20);
		pass=new JPasswordField("contraseña");
		pass.setBounds(250, 10, 80, 20);
		ps=new JLabel("Contraseña");
		ps.setBounds(175, 10, 80, 20);
		
		/*Botones*/
		on=new JButton("Start");
		on.setBounds(390, 20, 80, 30);
		off=new JButton("Stop");
		off.setBounds(390, 80, 80, 30);
		restart=new JButton("Restart");
		restart.setBounds(390, 140, 80, 30);
		exit=new JButton("Exit");
		exit.setBounds(390, 200, 80, 30);
		
		/*Área de texto*/
		book=new JTextArea(20,20);
		book.setBounds(30, 50, 330, 190);
		book.setBackground(Color.gray);
		book.setCaretColor(Color.white);
			
		/*Menús*/
			barraMenu=new JMenuBar();
			archivo=new JMenu("Archivo");
			barraMenu.add(archivo);
						
			archivo.add(new JSeparator());
			
			fuentes=new JMenu("Color de fuentes");
			archivo.add(fuentes);
			
			
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
				contenedor.add(exit);
				contenedor.add(book);
				contenedor.add(user);
				contenedor.add(pass);
				contenedor.add(us);
				contenedor.add(ps);
				
		
		}
}

