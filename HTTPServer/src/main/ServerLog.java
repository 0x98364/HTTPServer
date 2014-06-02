package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


/*---------------------------------------------------------
 * CLASE ENCARGADA DE ESCRIBIR LOGS DEL ESTADO DEL SERVIDOR
 * --------------------------------------------------------
 */
public class ServerLog {
	private String path;

	public ServerLog(String logsDirectory) {
		path = logsDirectory;
	}
	
	public void saveMsg(String mensaje){
		File log = new File(path + "events.log");
		
		if(!log.exists()){
			try {
				log.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		FileWriter fw;
		BufferedWriter bf;
		
		try {
			fw = new FileWriter(log,true);
			bf = new BufferedWriter(fw);
			
			/*ESCRIBIMOS EL MENSAJE*/
			bf.write("[" + new Date() + "] " + mensaje + "\n");
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
