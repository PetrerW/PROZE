/*
 * 
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/*
 * @author: PetrerW
 * @version: 15.06.2017
 * 
 * A Server class. It holds:
 * level information
 * 3 maps (easy, medium, hard)
 * list of the best scores
 */

/**
 * The Class Server.
 */
public class Server {
	
	/** The port number. */
	private int portNumber;
	
	/** The level. */
	private Level level;
	
	/** The Map. */
	private ArrayList<String> Map;
	
	/** The Clients IP. */
	private ArrayList<String> ClientsIP;
	
	/** The Best ranking. */
	private String BestRanking;
	
	/** The Map directory. */
	private static String[] MapDirectory = {"src/Map1.txt", "src/Map2.txt", "src/Map3.txt"};
	
	/** The client request matcher. */
	ClientRequestMatcher clientRequestMatcher;
	
	/** The s1. */
	ServerSocket S1;
	
	/** The ss. */
	Socket SS;
	
	/**
	 * Instantiates a new server.
	 */
	Server(){
		portNumber = 27272;
		Map = new ArrayList<String>();
		ClientsIP = new ArrayList<String>();
		setUpSockets();
		//clientRequestMatcher = new ClientRequestMatcher();
	}
	
	/**
	 * Sets the up sockets.
	 */
	private void setUpSockets(){
		try{
			//if(S1 == null)
			//{
				//Create a server socket in port No. portNumber
				S1 = new ServerSocket(portNumber);
			//}

			//if(SS == null){
				//accept() Listens for the connection made by socket S1 and accepts it
				SS = S1.accept(); 
			//}

		}catch(Exception e){
			System.err.println("(Server.setUpSockets()) " + e.getMessage());
		}

	}
	
	/**
	 * Start.
	 */
	public void start(){
		String Message;
		
		try{
			
			//Create a scanner to read from an input stream of socket SS
			Scanner serverScanner = new Scanner(SS.getInputStream());
			
			do{
				
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

			}while(!Message.contains("Error"));
			
		}catch(Exception e){
			System.err.println(e.getMessage() + " (Server.start()) " );
		}
		
	}
	
	/**
	 * Gets the port number.
	 *
	 * @return the port number
	 */
	//Returns port number used by server
	public int getportNumber(){
		return this.portNumber;
	}
	
	/**
	 * Sets the port number.
	 *
	 * @param newNumber the new port number
	 */
	//set new port number used by the server
	public void setportNumber(int newNumber){
		this.portNumber = newNumber;
		setUpSockets();
	}
	
	/**
	 * Read BOARD.
	 *
	 * @param lvl the lvl
	 * @return the string
	 */
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
	
	/**
	 * Handle login.
	 *
	 * @param line the line
	 * @return the string
	 */
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
	
	/**
	 * Handle get level.
	 *
	 * @param line the line
	 * @return the string
	 */
	//Handle get_Level command
	private String handleGetLevel(String line){
		//template: get_Level lvl_number @ <Client's Index>
		//Example: "get_Level 1 @ 34
				
		//10 is length of "get_Level "
		String Lvl = line.substring(10, 11);
				
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
			return "Error: Wrong index!";
				
		//lvl is invalid
		if(lvl > 3 | lvl < 1)
			return "Error: lvl field";
		else{
			//Read a Map from file
			readFromFile(lvl);
			String BOARD = generateBoardString();
			System.out.println("Server.handleGetLevel sending BOARD: " + BOARD);
			//Read BOARD from file
			return new String("send_Level " + Lvl + " BOARD [" + BOARD  + "]");
		}
	}
	
	/**
	 * Generate board string.
	 *
	 * @return the string
	 */
	private String generateBoardString(){
		StringBuilder sb = new StringBuilder();
		for(String line: Map){
			sb.append(line);
			sb.append(" "); //Split two elements of the map
		}
		System.out.println("Server.generateBoard: " + sb.toString());
		return sb.toString();
	}
	
	/**
	 * Send best ranking.
	 *
	 * @param line the line
	 * @return the string
	 */
	private String sendBestRanking(String line){
		
		return null;
	}	
	
	/**
	 * Handle set player result.
	 *
	 * @param line the line
	 * @return the string
	 */
	private String handleSetPlayerResult(String line){
		return null;
	}
	
	/**
	 * Handle logout.
	 *
	 * @param line the line
	 * @return the string
	 */
	private String handleLogout(String line){
		//Template: "logout @ <Client's Index>"
		//Example: "logout @ 34"
		try{
			
		//9 is length of "logout @ "
		String Index = line.substring(9);
		
		//delete white signs if any;
		line.trim();
	
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
	
	
	/**
	 * Decide.
	 *
	 * @param scanner the scanner
	 * @return the string
	 */
	//Function that decides what to do on the basis of a message from a client
	public String decide(Scanner scanner){
		
		String line; 
		Pattern pattern;
		Matcher matcher;
		try{
			//read line from client
			line = scanner.nextLine();
			
			//TODO: fix it doesn't work good at all
			//if(line == null){
				//return null;
				//this.wait();
			//}
			for(int i = 0; i<ClientRequestMatcher.patterns.size(); i++){
				//System.out.println("(Serve.decide() in the loop. shall end when i < " + ClientRequestMatcher.patterns.size() );
				if(ClientRequestMatcher.patterns == null){
					throw new Exception("ClientRequestMatcher.patterns = null");
				}
				pattern = ClientRequestMatcher.patterns.get(i);
				System.out.println("(Server.decide()) " + i + " " + pattern.toString());
				
				System.out.println("(Server.decide()) " + i + " " + line);
				matcher = pattern.matcher(line);
				
				//handle command depending on pattern match
				if(matcher.matches()){
					System.out.println("(Server.decide) In \"if\" statement with i = " + i);
					switch(i){
					case 0:
						//handle LOGIN
						String Message = this.handleLogin(line);
						System.out.println("Server.decide, case " + i + ": " + Message);
						return Message;
					case 1:
						//handle get_Level
						String Message1 = this.handleGetLevel(line);
						System.out.println("Server.decide, case " + i + ": " + Message1);
						return Message1;
					case 2:
						//handle get_best_ranking
						continue;
					case 3:
						//handle set_player_result
						continue;
					case 4:
						//handle logout
						String Message4 = this.handleLogout(line);
						System.out.println("Server.decide, case " + i + ": " + Message4);
						return Message4;
					default:
						//send error notification (wrong input data)
						return "Error: Wrong input data";
							
					}//switch
				}//if
				//else if we are at the end of the pattern list and nothing matched 
				else if (i >= ClientRequestMatcher.patterns.size())
					return "Error: Server.decide, code did not go into \"if\" statement";
				else{
					System.out.println("I should continue, i = " + i);
					continue;
				}
			}//for
			System.out.println("(Serve.decide() out of the loop. shall end when i < " + ClientRequestMatcher.patterns.size() );
		}catch(Exception e){
			System.err.println("Server.decide " + e.getMessage());
		}
		return "Error: Server.decide returned out of the for loop";
	}//decide
	
	/**
	 * Announce index change.
	 *
	 * @param index the index
	 */
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
	
	/**
	 * Read from file.
	 *
	 * @param lvl the lvl
	 * @return the array list
	 */
	//Read map from file to arrayList<String>
	public ArrayList<String> readFromFile(int lvl) {
		//Choose a correct map path
		String path = MapDirectory[lvl-1];
		
		//clear Map to append new data
		if (Map != null)
			Map.removeAll(Map);
		else
			Map = new ArrayList<String>();

		try {

			FileInputStream fstream = new FileInputStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			String strLine;
			int kindBubble;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				//Determining position on the colorList
				//kindBubble = Bubble.determineColorInt(Integer.parseInt(strLine));
				kindBubble = Integer.parseInt(strLine);
				//Adding new Bubble to the list with appropriate Color
				if (kindBubble > 0) {
					Map.add(strLine);
				} else {
					Map.add(null);
				}
			}
			br.close();

		} catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			System.err.println("GameInstance.readFromFile(String f)");
		}
		return Map;
	}
	
}//class
