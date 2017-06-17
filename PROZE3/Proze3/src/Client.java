/*
 * 
 */
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
// TODO: Auto-generated Javadoc

/**
 * The Class Client.
 *
 * @author PetrerW
 * @version 16.06.2017
 * 
 *  Client that will download parameters from server.
 */
public class Client {
	
	/** The port number. */
	private int portNumber;
	
	/** The Server IP. */
	private String ServerIP;
	
	/** The index. */
	private int index;
	
	/** The socket. */
	private Socket socket;
	
	/** The server response matcher. */
	ServerResponseMatcher serverResponseMatcher;
	
	/**
	 * Instantiates a new client.
	 */
	//default constructor
	Client(){
		portNumber = 27272;
		ServerIP = null; //"192.168.0.101";
		setUpSocket();
	}
	
	/**
	 * Instantiates a new client.
	 *
	 * @param ServerIP the server IP
	 */
	//Constructor that sets serverIP parameter
	Client(String ServerIP){
		portNumber = 27272;
		this.ServerIP = ServerIP;
		try{
			this.socket = new Socket(ServerIP, portNumber);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

	}
	
	/**
	 * Sets the up socket.
	 */
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
	
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex(){
		return this.index;
	}
	
	/**
	 * Sets the index.
	 *
	 * @param index the new index
	 */
	public void setIndex(int index){
		this.index = index;
	}
	
	/**
	 * Gets the server IP.
	 *
	 * @return the server IP
	 */
	//get current Server's IP address
	public String getServerIP(){
		return ServerIP;
	}
	
	/**
	 * Sets the server IP.
	 *
	 * @param newIP the new server IP
	 */
	//Set a new Server's IP
	public void setServerIP(String newIP){
		this.ServerIP = newIP;
		//try to create new socket with new IP
		try{
			this.socket = new Socket(newIP, portNumber);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Gets the port number.
	 *
	 * @return the port number
	 */
	//get current port number of the client
	public int getportNumber(){
		return portNumber;
	}
	
	/**
	 * Sets the port number.
	 *
	 * @param newNumber the new port number
	 */
	//Set a new port number for the client
	public void setportNumber(int newNumber){
		this. portNumber = newNumber;
	}
	
	//TODO: function decide() for index_change command
	
	/**
	 * Send message.
	 *
	 * @param Message the message
	 * @return the string
	 */
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
	
	/**
	 * Handle login.
	 *
	 * @param Message the message
	 */
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
	
	/**
	 * Handle logout.
	 *
	 * @param Message the message
	 */
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
				JOptionPane.showMessageDialog(null,  "Succesfully logged out!", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			else
				throw new Exception("Client.handleLogout: Wrong loggedout message");
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Error: Wrong Server response", "Error", JOptionPane.ERROR_MESSAGE);
			System.out.println("Client.handleLogout: " + e.getMessage());
		}

	}

	/**
	 * Handle get level.
	 *
	 * @param response the response
	 */
	public void handleGetLevel(String response) {
		System.out.println("Client.handleGetLevel got response: " + response);
		this.parseMap(response);
	}
	
	/**
	 * Parses the map.
	 *
	 * @param response the response
	 * @return the array list
	 */
	public ArrayList<String> parseMap(String response){
		//create a new ArrayList
		ArrayList<String> Map = new ArrayList<String>();
		
		//length of "send_Level 1 BOARD [" is 19
		String FirstPart = "send_Level 1 BOARD [";
		
		String BOARD = response.substring(FirstPart.length(), response.length()-1);
		//System.out.println("Client.parseMap() clear BOARD: " + BOARD );
		
		//remove white spaces from board
		BOARD = BOARD.replaceAll("\\s", "");
		char color;
		String Color;
		
		for(int i = 0; i<BOARD.length(); i++){
			
			//get the i-th string
			color = BOARD.charAt(i);
			Color = String.valueOf(color);
			
			//Add color as string to the color Map
			if( Color != "" )
				Map.add(""+color);
		}
		return Map;
	}
}
