import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * 
 * @author PetrerW
 * @version 16.06.2017
 * 
 * A class with patterns to match server's response
 */

public class ServerResponseMatcher {
	public static String IPpattern = "(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)[.]){3}(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)";
	public static Pattern LoginPattern = Pattern.compile("loggedin @ [0-9]+");
	public static Pattern LogoutPattern = Pattern.compile("loggedout");
	public static ArrayList<Pattern> patterns;;

	ServerResponseMatcher(){
		//empty
	}
	
	static{
		patterns = new ArrayList<>();
		/**
		 * Get info about changed index in the list
		 * Example: 
		 * index_change @ 3
		 */
		patterns.add(Pattern.compile("index_change @ [0-9]+"));
	}
	
}