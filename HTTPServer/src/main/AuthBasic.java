package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import crypto.Base64;

/*---------------------------------------------------------
 * CLASE ENCARGADA DE PROTEGER DIRECTORIOS
 * --------------------------------------------------------
 */

public class AuthBasic {
	private String authMessage = "Mensaje de autorizacion";

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


	public boolean comprobarCredenciales(ArrayList<String> cabecera, File carp) {
		int linean = 0;
		for(int i = 0;i<cabecera.size();i++){
			if(cabecera.get(i).startsWith("Authorization: Basic")){
				linean = i;
			}
		}
		String[] line = cabecera.get(linean).split(" ");
		String base64 = line[2];
		String base64plain = new String(Base64.decode(base64)); //Desencriptamos la pass recibida
		String credenciales = null;
		
		System.out.println(carp.getParent());
		
		File config = new File(carp.getParent() + "/" + "conf.drkwb");
			
		try{
			FileReader fr = new FileReader(config);
			BufferedReader bf = new BufferedReader(fr);
			
			String linea;
			while((linea = bf.readLine())!=null){
				credenciales = linea;
			}
			bf.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(base64plain.equals(credenciales)){
			return true;
		}
		return false;
	}


}
