package main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.lang.*;

public class ClienteThread extends Thread {
	private Socket s;
	private String protocol;
	private ArrayList<String> cabeceraEntrante;
	private ArrayList<String> webPage;
	private String rootDirectory = "public_html/";
	private String errorDirectory = "errors/";
	private String[] methods = {"HEAD","GET","POST","PUT","DELETE","TRACE","OPTIONS","CONNECT"};
	
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
			while(!((msgCliente=reader.readLine()).equalsIgnoreCase(""))){
				cabeceraEntrante.add(msgCliente); //Metemos la cabecera recibida en una lista
			}
			
			for(int i = 0;i<cabeceraEntrante.size();i++){
				System.out.println(cabeceraEntrante.get(i));
			}

			//Comprobamos que la cabecera esta bien formada (CODE 400). Si lo está, respondemos la peticion
			if(verifyHeader(cabeceraEntrante)){
				
				FirstLine = cabeceraEntrante.get(0);
				String [] peticion=FirstLine.split(" "); //Recuperamos las peticiones de cliente
				
				switch (getMethod(peticion[0])) {
				case 1: //El metodo es GET
					String webRequired = peticion[1];
					File web; String path;
					
					if(webRequired.equalsIgnoreCase("/")){ //Si no se pone web especifica, se busca el index
						path = rootDirectory + "index.html";
						web = new File(path);
					}else{
						path = rootDirectory + webRequired;
						web = new File(path);
					}
					
					if(web.exists() && getMime(path).equalsIgnoreCase("text/html")){
						/*---GESTIONAMOS LA RESPUESTA DEL SERVIDOR SI LA WEB EXISTE---*/
						writer = new PrintWriter(s.getOutputStream(),true);
						
						String webPlano = readFileAsString(path); //Pasamos el file a String
						String headerResp = makeHeader(200,webPlano.length(),getMime(path)); //Construimos cabecera para web
						
						System.out.println(headerResp + webPlano);
						writer.println(headerResp + webPlano);
					}else if(web.exists() && (getMime(path).equalsIgnoreCase("image/png") || getMime(path).equalsIgnoreCase("image/jpeg") || getMime(path).equalsIgnoreCase("application/pdf"))){
						/*---GESTIONAMOS LA RESPUESTA DEL SERVIDOR SI LA IMAGEN EXISTE---*/
						DataOutputStream out = new DataOutputStream(s.getOutputStream());
						
						out.writeBytes(makeHeader(200, (int) web.length(), getMime(path)));
						out.flush();
						
						InputStream is =  new FileInputStream(path);
			            BufferedInputStream bis = new BufferedInputStream(is);
			 
			            int ch;
			            while ((ch = bis.read()) != -1){
			            	out.write(ch);
			            }
			            out.writeBytes("\n");
			            out.flush();
			            
			            out.close();
			            is.close();
			            bis.close();
			        }else if(!web.exists()){
			        	/*---GESTIONAMOS LA RESPUESTA DEL SERVIDOR SI LA WEB NO EXISTE---*/
			        	writer = new PrintWriter(s.getOutputStream(),true);
			        	
			        	String webPlano = readFileAsString(errorDirectory + "404.html");
						String headerResp = makeHeader(404,webPlano.length(),getMime(path));
		
						System.out.println(headerResp + webPlano);
						writer.println(headerResp + webPlano);
					}
					break;
				case 2: // El metodo es POST
					break;
				default:
					break;
				}
			}else{
				writer = new PrintWriter(s.getOutputStream(),true);
	        	
	        	String webPlano = readFileAsString(errorDirectory + "400.html");
				String headerResp = makeHeader(400,webPlano.length(),"text/html");

				System.out.println(headerResp + webPlano);
				writer.println(headerResp + webPlano);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(reader!=null){
					reader.close();
				}
				if(writer!=null){
					writer.close();
				}
				if(s!=null){
					s.close();
				}
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private boolean verifyHeader(ArrayList<String> cabecera) {
		String FirstLine = cabeceraEntrante.get(0);
		String [] peticion=FirstLine.split(" ");
		
		for(int i = 0;i<methods.length;i++){
			if(peticion[0].equalsIgnoreCase(methods[i])){
				return true;
			}
		}
		return false;
	}

	private String getMime(String path) {
		
		String extension = getExtension(path);
		
		if(extension.equalsIgnoreCase("html")){
			return "text/html";
		}else if(extension.equalsIgnoreCase("jpg")){
			return "image/jpeg";
		}else if(extension.equalsIgnoreCase("png")){
			return "image/png";
		}else if(extension.equalsIgnoreCase("pdf")){
			return "application/pdf";
		}else if(extension.equalsIgnoreCase("xml")){
			return "application/xml";
		}
		return null;
	}

	private String makeHeader(int status,int length,String mime) {
		String header;
		
		switch (status) {
		case 200:
				header = "HTTP/1.1 " + status + " OK\n";
				header += "Server:	HTTPJava SERVER DRKWB\n";
				header += "Content-Type: " + mime + "\n";
				header += "Content-Length: " + length + "\n";
				header += "\n";
				return header;
		case 404:
				header = "HTTP/1.1 " + status + " Not Found\n";
				header += "Server:	HTTPJava SERVER DRKWB\n";
				header += "Content-Type: " + mime + "\n";
				header += "Content-Length: " + length + "\n";
				header += "\n";
				return header;
		case 400:
				header = "HTTP/1.1 " + status + "  Bad Request\n";
				header += "Server:	HTTPJava SERVER DRKWB\n";
				header += "Content-Type: " + mime + "\n";
				header += "Content-Length: " + length + "\n";
				header += "\n";
				return header;
		default:
			
			break;
		}
		return null;
		
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
	
	 public String getExtension(String filename) {
         int index = filename.lastIndexOf('.');
         if (index == -1) {
               return "";
         } else {
               return filename.substring(index + 1);
         }
}
	
	
}
