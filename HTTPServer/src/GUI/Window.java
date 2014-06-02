package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;






public class Window extends JFrame {
	JPanel contenedor;
	JButton on, off, restart, exit;
	JTextArea book; 
	JMenuBar barraMenu;
	JMenu colores,fuentes;
	JMenuItem rojo,verde,azul,negrita,cursiva,normal,salir;
	
	public Window(){
		
		setTitle("SERVER");
		setBounds(0, 0, 500, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
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
		
		
			
		/*Menús*/
			barraMenu=new JMenuBar();
			colores=new JMenu("Colores");
			barraMenu.add(colores);
			rojo=new JMenuItem("Rojo");
			colores.add(rojo);
			verde=new JMenuItem("Verde");
			colores.add(verde);
			azul=new JMenuItem("Azul");
			colores.add(azul);
			
			colores.add(new JSeparator());
			
			fuentes=new JMenu("Fuentes");
			colores.add(fuentes);
			
			negrita=new JMenuItem("Negrita");
			fuentes.add(negrita);
			cursiva=new JMenuItem("Cursiva");
			fuentes.add(cursiva);
			normal=new JMenuItem("Normal");
			fuentes.add(normal);
			
			
			colores.add(new JSeparator());
			salir=new JMenuItem("Salir");
			colores.add(salir);
			
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
			
			accion a=new accion();
			
			salir.addActionListener(a);
			exit.addActionListener(a);
			rojo.addActionListener(a);
			verde.addActionListener(a);
			azul.addActionListener(a);
			negrita.addActionListener(a);
			normal.addActionListener(a);		
			cursiva.addActionListener(a);
			
			
		}
		
		public class accion implements ActionListener{

			
			public void actionPerformed(ActionEvent arg0) {
				
				if(arg0.getSource()==salir || arg0.getSource()==exit){
					System.exit(0);
				}
				
				if(arg0.getSource()==rojo){
					contenedor.setBackground(Color.red);
					
				}
				if(arg0.getSource()==verde){
					contenedor.setBackground(Color.green);
					
				}
				if(arg0.getSource()==azul){
					contenedor.setBackground(Color.blue);
					
				}
				
				if(arg0.getSource()==cursiva){
					Font f=new Font("Arial",Font.ITALIC,12);
					
				}
				if(arg0.getSource()==negrita){
					Font f=new Font("Arial",Font.BOLD,12);
					
				}
				if(arg0.getSource()==normal){
					Font f=new Font("Arial",Font.PLAIN,12);
					
				}
			}
			
		}	
}

