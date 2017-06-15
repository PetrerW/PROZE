import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
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
	private Level level;
	private ArrayList<String> Map;
	private ArrayList<String> ClientsIP;
	private String BestRanking;
	private static String[] MapDirectory = {"Map 1.txt", "Map 2.txt", "Map 3.txt"};
	
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
		return null;
	}
	
	
	//Function that decides what to do on the basis of a message from a client
	private String decide(Scanner scanner){
		
		String line; 
		Pattern pattern;
		Matcher matcher;
		
		for(int i = 0; i<ClientRequestMatcher.patterns.size(); i++){
			pattern = ClientRequestMatcher.patterns.get(i);
			line = scanner.nextLine();
			matcher = pattern.matcher(line);
			
			//handle command depending on pattern match
			if(matcher.matches()){
				switch(i){
				case 1:
					//handle LOGIN
					return handleLogin(scanner.nextLine());
				case 2:
					//handle get_Level
					
				case 3:
					//handle get_best_ranking
				case 4:
					//handle set_player_result
				case 5:
					//handle logout
				default:
					//send error notification (wrong input data)
						
				}
			}
		}
		return null;
	}
	
	public static void main(String args[]){
		try{
			//Create a server socket in port No 27272
			ServerSocket S1 = new ServerSocket(27272);
			
			//accept() Listens for the connection made by socket S1 and accepts it
			Socket SS = S1.accept(); 
			
			//Create a scanner to read from an input stream of socket SS
			Scanner serverScanner = new Scanner(SS.getInputStream());
			
			String Message;
			
			if(serverScanner.nextLine() == "PROZE_PROTOCOL"){
				Message = decide(serverScanner);
			}
		}catch(Exception e){
			System.err.println(e.getMessage() + " (Server) " );
		}
	}
}
