import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
/**
 * 
 * @author PetrerW
 * @version 16.06.2017
 */
public class Client {
	private int portNumber;
	private String ServerIP;
	
	Client(){
		portNumber = 27272;
		ServerIP = "192.168.0.101";
	}
	
	//get current Server's IP address
	public String getServerIP(){
		return ServerIP;
	}
	
	//Set a new Server's IP
	public void setServerIP(String newIP){
		this.ServerIP = newIP;
	}
	
	//get current port number of the client
	public int getportNumber(){
		return portNumber;
	}
	
	//Set a new port number for the client
	public void setportNumber(int newNumber){
		this. portNumber = newNumber;
	}
	
	public static void main(String args[]) throws Exception{
		Client client = new Client();
		try{
			//Create a new Socket
			//12345 is a port number
			Socket socket = new Socket(client.ServerIP, client.portNumber);
			
			//Scanner reading from input stream of a socket
			Scanner scanner1 = new Scanner(socket.getInputStream());
			
			//A stream bound with socket's output stream
			PrintStream ps = new PrintStream(socket.getOutputStream());
			
			//temporary! Probe
			//String Head = "PROZE_PROTOCOL";
			String Message = "login @ 192.168.0.101";
			
			//Send a message to the server
			//ps.println(Head);
			ps.println(Message);
			
			//Server will send us a response
			//Print the response of the server
			System.out.println(scanner1.nextLine());
			
			//close socket
			socket.close();
			
		}catch(Exception e){
			System.out.println("Client exception: " + e.getMessage());
		}
	}
}
