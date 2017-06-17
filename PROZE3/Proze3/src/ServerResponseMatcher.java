/*
 * 
 */
import java.util.ArrayList;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerResponseMatcher.
 *
 * @author PetrerW
 * @version 16.06.2017
 * 
 * A class with patterns to match server's response
 */

public class ServerResponseMatcher {
	
	/** The I ppattern. */
	public static String IPpattern = "(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)[.]){3}(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)";
	
	/** The Login pattern. */
	public static Pattern LoginPattern = Pattern.compile("loggedin @ [0-9]+");
	
	/** The Logout pattern. */
	public static Pattern LogoutPattern = Pattern.compile("loggedout");
	
	/** The patterns. */
	public static ArrayList<Pattern> patterns;;

	/**
	 * Instantiates a new server response matcher.
	 */
	ServerResponseMatcher(){
		//empty
	}
	
	static{
		patterns = new ArrayList<Pattern>();
		/**
		 * Get info about changed index in the list
		 * Example: 
		 * index_change @ 3
		 */
		patterns.add(Pattern.compile("index_change @ [0-9]+"));
	}
	
}