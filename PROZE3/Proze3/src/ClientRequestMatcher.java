import java.util.ArrayList;
import java.util.regex.Pattern;

/*
 * @author PetrerW
 * @version 15.06.2017
 * 
 * 
 * A class to match Client's request 
 */
public class ClientRequestMatcher {
	public static ArrayList<Pattern> patterns;

	public static String IPpattern = "(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)[.]){3}(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)";
	ClientRequestMatcher(){
		
		/*
		 * LOGIN
		 * Example:
		 * login @ 192.0.0.14
		 * 192.0.0.14 is an IP address of the client
		*/
		patterns.add(Pattern.compile("login @ " + IPpattern));
		
		/*
		 * Get Level from Server
		 * Example:
		 * get_Level 2 @ 95
		 */
		patterns.add(Pattern.compile("get_Level [1-3] @ [0-9]+"));
		
		/*
		 * Get best ranking from the server
		 * Example:
		 * get_best_ranking @ 34
		 * 34 is index of a client
		 */
		patterns.add(Pattern.compile("get_best_ranking @ [0-9]+"));
		
		/*
		 * Write the result of a player
		 * Example:
		 * set_player_result Player1 1024 @ 34
		 * Player1 - nickname
		 * 1024 - score
		 * 34 - client's index
		 */
		patterns.add(Pattern.compile("set_player_result [a-z]+ [0-9]+ @ [0-9]+"));
		
		/*
		 * logout
		 * Example:
		 * logout @ 34
		 * 34 - the client's index
		 */
		 patterns.add(Pattern.compile("logout @ [0-9]+"));
	}
}
