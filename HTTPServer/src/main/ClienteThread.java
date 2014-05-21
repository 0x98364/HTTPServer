package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClienteThread extends Thread {
	private Socket s;
	private String protocol;
	private ArrayList<String> cabeceraEntrante;
	private ArrayList<String> webPage;
	private String rootDirectory = "public_html/";
	public ClienteThread(Socket s) {
		this.s=s;
	}

	@Override
	public void run() {
		/*---PREPARAR FLUJOS---*/
		BufferedReader reader = null;
		PrintWriter writer = null;
		cabeceraEntrante = new ArrayList<String>();
		
		String FirstLine;
		try {
			/*--GESTIONAR PETICION CLIENTE---*/
			System.out.println("Atendiendo cliente...");
			reader = new BufferedReader(
					new InputStreamReader(s.getInputStream()));
			
			String msgCliente;
			while(!(msgCliente=reader.readLine()).equalsIgnoreCase("")){
				cabeceraEntrante.add(msgCliente); //Metemos la cabecera recibida en una lista
			}
			
			for(int i = 0;i<cabeceraEntrante.size();i++){
				System.out.println(cabeceraEntrante.get(i));
			}
			
			FirstLine = cabeceraEntrante.get(0);
			String [] peticion=FirstLine.split(" "); //Recuperamos las peticiones de cliente
			
			switch (getMethod(peticion[0])) {
			case 1: //El metodo es GET
				String webRequired = peticion[1];
				File web = new File(rootDirectory + webRequired);
				if(web.exists()){
					/*---GESTIONAMOS LA RESPUESTA DEL SERVIDOR---*/
					writer = new PrintWriter(s.getOutputStream(),true);
					String web2 = readFileAsString(rootDirectory + webRequired);
					System.out.println(web2);
					writer.println(web2);
				}else{
					System.out.println("No existe!");
				}
				break;
			case 2: // El metodo es POST
				break;
			default:
				break;
			}
			 
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private int getMethod(String peticion ){
		if(peticion.equalsIgnoreCase("GET")){
			return 1;
		}else if(peticion.equalsIgnoreCase("POST")){
			return 2;
		}
		return 0;
	}
	
	private String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer();
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }
	
	
	
}
