import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.swing.JOptionPane;

/*
 * @author PetrerW
 * @version 14.06.2017
 * 
 * A class that checks if the IP address matches the IPv4 pattern
 */
public class IpMatcher {

	IpMatcher(){
		//empty
	}
	
	//Check if String correctly describes IP address
	public static boolean isValidIP(String ip){
		
		Pattern pattern = Pattern.compile(ClientRequestMatcher.IPpattern);
		Matcher matcher = pattern.matcher(ip);
		
		return matcher.matches();
	}
	
	/*
	 * @param ip is a string that represents an IP address
	 * @return true if IP address is correct, false otherwise
	 */
	public static returnInfo validateIP(String ip){
		
			//String doesn't exist or is empty - return false
			if(ip == null || ip.isEmpty())
				return new returnInfo(false, "IP address is empty! ");
			
			//Remove white signs from string
			ip = ip.trim();
			
			//Wrong length of ip address
			//x.x.x.x -> 7
			//xxx.xxx.xxx.xxx -> 15
			if(ip.length() < 7 || ip.length() > 15)
				return new returnInfo(false, "Invalid length of the IP address! ");
		
		try{
			//An IP address pattern
			Pattern IpPattern = Pattern.compile("^(?:(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[0-1]?[0-9][0-9]?)$");
			//Pattern IpPattern = Pattern.compile("get_Level [1-3] @ [0-9]+");
			
			//Does IP address match the pattern?
			Matcher matcher = IpPattern.matcher(ip);
			
			//return {boolean, Message}
			if(matcher.matches()){
				return new returnInfo(matcher.matches(), "Success"); 
			}
			else{
				return new returnInfo(matcher.matches(), "Error reading IP address. ");
			}
				

			
		}catch(PatternSyntaxException ex){
			System.err.println(ex.getMessage());
			return new returnInfo(false, "Error reading the IP: address doesn't match the pattern");
		}
	}
}

