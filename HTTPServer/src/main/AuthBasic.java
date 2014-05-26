package main;

import java.io.File;
import java.util.ArrayList;

/*---------------------------------------------------------
 * CLASE ENCARGADA DE PROTEGER DIRECTORIOS
 * --------------------------------------------------------
 */

public class AuthBasic {
	private String user,pass;
	private String authMessage = "Autorizacion requerida";

	public String getAuthMessage() {
		return authMessage;
	}


	public void setAuthMessage(String authMessage) {
		this.authMessage = authMessage;
	}



	public boolean isProtected(File carp) {
		String [] dir = carp.list();
		
		try{ //Creado para resolver excepcion al tratar con un fichero y no con una carpeta
			for(int i = 0;i<dir.length;i++){ //Buscamos si existe el archivo de configuracion
				if(dir[i].equals("conf.drkwb")){
					return true;
				}
			}
		}catch(NullPointerException e){
			//No estamos tratando un directorio
		}
		return false;
	}


	public boolean comprobarCredenciales(ArrayList<String> cabecera) {
		int linea = 0;
		for(int i = 0;i<cabecera.size();i++){
			if(cabecera.get(i).startsWith("Authorization: Basic")){
				linea = i;
			}
		}
		System.err.println(cabecera.get(linea));
		
		String[] line = cabecera.get(linea).split(" ");
		
		String base64 = line[2];
		
		System.err.println(base64);

		return false;
	}
	
	

}
