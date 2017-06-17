import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * @author: PetrerW
 * @version: 15.06.2017
 * 
 * A Server class. It holds:
 * level information
 * 3 maps (easy, medium, hard)
 * list of the best scores
 */

public class Server {
	private int portNumber;
	private Level level;
	private ArrayList<String> Map;
	private ArrayList<String> ClientsIP;
	private String BestRanking;
	private static String[] MapDirectory = {"Map 1.txt", "Map 2.txt", "Map 3.txt"};
	ClientRequestMatcher clientRequestMatcher;
	
	Server(){
		portNumber = 27272;
		Map = new ArrayList<>();
		ClientsIP = new ArrayList<>();
		//clientRequestMatcher = new ClientRequestMatcher();
	}
	
	public void start(){
		
		try{
			//Create a server socket in port No. portNumber
			ServerSocket S1 = new ServerSocket(portNumber);
			
			//accept() Listens for the connection made by socket S1 and accepts it
			Socket SS = S1.accept(); 
			
			//Create a scanner to read from an input stream of socket SS
			Scanner serverScanner = new Scanner(SS.getInputStream());
			
			String Message;
			String line;
			
			//if((line = serverScanner.nextLine()) == "PROZE_PROTOCOL"){
			//Generate server's response
			Message = this.decide(serverScanner);
				
			//Create a PrintStream to write to Client
			PrintStream serverPS = new PrintStream(SS.getOutputStream());
			
			//Send message to the client
			serverPS.println(Message);
				
			//probe
			System.out.println(Message);
			//}
		}catch(Exception e){
			System.err.println(e.getMessage() + " (Server.start()) " );
		}
	}
	
	//Returns port number used by server
	public int getportNumber(){
		return this.portNumber;
	}
	
	//set new port number used by the server
	public void setportNumber(int newNumber){
		this.portNumber = newNumber;
	}
	
	//a function that reads BOARD from file and makes it ready to send
	private String readBOARD(int lvl){
		
		//path to the file with the map of appropriate level
		String path = "Map " + lvl + ".txt";
		
		StringBuilder sb = new StringBuilder();
		String line;
		try{
			FileInputStream fstream = new FileInputStream(path);
	        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
	        
	        while((line = br.readLine()) != null){
	        	//add new line to the BOARD
	        	sb.append(line + " ");
	        }
	        //delete the last space from the BOARD
			sb.deleteCharAt(sb.length()-1);
			
			//create BOARD
	        String BOARD = sb.toString();
	        
	        return BOARD;
		}
		catch(Exception e){
			System.err.println("Server.readBOARD:");
			System.err.println(e.getMessage());
			return "Error at Server.readBoard";
		}
	}
	
	//Handle login command
	private String handleLogin(String line){
		//template: login @ <Client's IP>
		//Example: "login @ 192.0.33.123
		
		//8 is length of "login @ "
		String IP = line.substring(8);
		
		//remove white signs
		IP.trim();
		
		if(ClientsIP.contains(IP))
			return "Error: Already logged in!";
		
		//IP is invalid
		if(!IpMatcher.isValidIP(IP))
			return "Error: Wrong IP address";
		else{
			ClientsIP.add(IP);
			return new String("loggedin @ " + (ClientsIP.size()-1));
		}
	}
	
	//Handle get_Level command
	private String handleGetLevel(String line){
		//template: get_Level lvl_number @ <Client's Index>
		//Example: "get_Level 1 @ 34
				
		//9 is length of "get_Level "
		String Lvl = line.substring(9, 10);
				
		//remove white signs if any
		Lvl.trim();
		
		//parse String to int
		int lvl = Integer.parseInt(Lvl);
		
		//14 is length of "get_Level 1 @ "
		//In the place of 1 can be 2 or 3
		String Index = line.substring(14);
		
		//remove white signs if any
		Index.trim();
		
		//parse String to int
		int index = Integer.parseInt(Index);
				
		if(index < ClientsIP.size())
			return "Error: Already logged in!";
				
		//lvl is invalid
		if(lvl > 3 | lvl < 1)
			return "Error: lvl field";
		else{
			//Read BOARD from file
			return new String("send_Level " + Lvl + "["/* + BOARD */ + "]");
		}
	}
	
	private String sendBestRanking(String line){
		
		return null;
	}	
	
	private String handleSetPlayerResult(String line){
		return null;
	}
	
	private String handleLogout(String line){
		//Template: "logout @ <Client's Index>"
		//Example: "logout @ 34"
		try{
			
		//9 is length of "logout @ "
		String Index = line.substring(9);
	
		int index = Integer.parseInt(Index);
		
		//remove appropriate IP address from the list
		this.ClientsIP.remove(index);
		
		//tell all the clients from the list above index that their index changed
		this.announceIndexChange(index);
		
		//return response to the server
		return "loggedout";
		}catch(Exception e){
			System.out.println("Server.handleLogout: " + e.getMessage());
		}
		return "Server error: handleLogout";
	}
	
	
	//Function that decides what to do on the basis of a message from a client
	public String decide(Scanner scanner){
		
		String line; 
		Pattern pattern;
		Matcher matcher;
		try{
			for(int i = 0; i<ClientRequestMatcher.patterns.size(); i++){
				if(ClientRequestMatcher.patterns == null){
					throw new Exception("ClientRequestMatcher.patterns.get(i) = null");
				}
				pattern = ClientRequestMatcher.patterns.get(i);
				System.out.println(pattern.toString());
				line = scanner.nextLine();
				System.out.println(line);
				matcher = pattern.matcher(line);
				
				//handle command depending on pattern match
				if(matcher.matches()){
					switch(i){
					case 0:
						//handle LOGIN
						String Message = this.handleLogin(line);
						System.out.println("Server.decide, case " + i + ": " + Message);
						return Message;
					case 1:
						//handle get_Level
						
					case 2:
						//handle get_best_ranking
					case 3:
						//handle set_player_result
					case 4:
						//handle logout
						String Message4 = this.handleLogout(line);
						System.out.println("Server.decide, case " + i + ": " + Message4);
						return Message4;
					default:
						//send error notification (wrong input data)
							
					}//switch
				}//if
				else 
					return "Error: Server.decide, code did not go into \"if\" statement";
			}//for
		}catch(Exception e){
			System.err.println("Server.decide " + e.getMessage());
		}
		return "Error: Server.decide";
	}//decide
	
	//announce that a client has left server and indexes in the list changed
	public void announceIndexChange(int index){

		String ip;
		if(ClientsIP.size() != 0){
		for(int i = index; i<ClientsIP.size(); i++){
			//Get IP address
			ip = ClientsIP.get(i);
			try{		
				//Create a socket to connect with client
				Socket socket = new Socket(ip, this.portNumber);
				
				//Get socket's output stream
				PrintStream ps = new PrintStream(socket.getOutputStream());
				
				//write line to the client
				ps.println("index_change @ " + i);
				
				//close the socket
				socket.close();
			}catch(Exception e){
				System.out.println("Failure to send message to client with IP: " + ip );
			}

		}//for
		}//if
	}//announceIndexChange
}//class
