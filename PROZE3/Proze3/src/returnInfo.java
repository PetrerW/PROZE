/**
 * @author: PetrerW
 * @version: 14.06.2017
 * 
 * A class to return IpMatcher match success (boolean) and a Message (String) to throw exceptions and display error windows
 */
public class returnInfo {
		private boolean matched;
		private String Message;
		
		returnInfo(boolean matched_, String Message_){
			matched = matched_;
			Message = Message_;
		}
		
		public boolean isMatched(){
			return this.matched;
		}
		
		public String getMessage(){
			return this.Message;
		}
}
