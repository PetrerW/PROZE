import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
/**
 * 
 * @author PetrerW
 * @version 16.06.2017
 */
public class Client {
	private int portNumber;
	private String ServerIP;
	private int index;
	private Socket socket;
	ServerResponseMatcher serverResponseMatcher;
	
	//default constructor
	Client(){
		portNumber = 27272;
		ServerIP = "192.168.0.101";
		setUpSocket();
	}
	
	//Constructor that sets serverIP parameter
	Client(String ServerIP){
		portNumber = 27272;
		this.ServerIP = ServerIP;
		setUpSocket();
	}
	
	//Create a new Socket
	private void setUpSocket(){
		try{
			//if the socket is not connected, create one
			if(socket == null)
				socket = new Socket(ServerIP, portNumber);
		}catch(Exception e){
			System.err.println("(Client())- unable to create the socket. " + e.getMessage());
		}
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public void setIndex(int index){
		this.index = index;
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
	
	//TODO: function decide() for index_change command
	
	public String sendMessage(String Message) {
		//Client client = new Client();
		try{

			//12345 is a port number
			//this fragment throws an exception

			//Socket socket = new Socket(client.ServerIP, client.portNumber);
			
			//Scanner reading from input stream of a socket
			Scanner scanner1 = new Scanner(this.socket.getInputStream());
			
			//A stream bound with socket's output stream
			PrintStream ps = new PrintStream(socket.getOutputStream());
			
			//Send a message to the server
			//ps.println(Head);
			ps.println(Message);
			
			//response of the server
			String response = scanner1.nextLine();
			
			//Print the response of the server
			System.out.println(response);
			
			//close socket
			//socket.close();
			
			//close scanner - it closes the socket too (closing one of socket's input streams closes the socket
			//scanner1.close();
		
			return response;
			
		}catch(Exception e){
			System.out.println("Client exception: " + e.getMessage());
			//Case of error return null
			return null;
		}
	}
	
	//handle login command
	public void handleLogin(String Message){
		//Template: "loggedin @ 34"
		//34 is index number in server's clients list
		
		try{
			
		//Check if Message is correct
		Pattern pattern = ServerResponseMatcher.LoginPattern;
		Matcher matcher = pattern.matcher(Message);
		
		System.out.println("Client has got response: \"" + Message + "\"");
		
		if(matcher.matches()){
			//11 is length of "loggedin @ "
			String Index = Message.substring(11);
			
			//remove white signs if any
			Index.trim();
			
			//parse the Index to int
			this.index = Integer.parseInt(Index);
			
			JOptionPane.showMessageDialog(null,  "Succesfully logged in!", "Success", JOptionPane.INFORMATION_MESSAGE);
			//System.out.println(Index);
		}
		else{
			JOptionPane.showMessageDialog(null, "Error: Wrong Server response", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}catch(Exception e){
		JOptionPane.showMessageDialog(null, "Error: Wrong Server response. Probably you're already logged in.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//handle loggedout response
	public void handleLogout(String Message){
		//Template: "loggedout"
		
	System.out.println("Client has got response: \"" + Message + "\"");
		try{
			//Chack if Message fits to server's response pattern
			Pattern pattern = ServerResponseMatcher.LogoutPattern;
			Matcher matcher = pattern.matcher(Message);
			
			if(matcher.matches()){
				//handle command
				this.index = -1;
			}
			else
				throw new Exception("Client.handleLogout: Wrong loggedout message");
		}catch(Exception e){
			System.out.println("Client.handleLogout: " + e.getMessage());
		}

	}
}
