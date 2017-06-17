
// TODO: Auto-generated Javadoc
/**
 * The Class returnInfo.
 */
/*
 * @author: PetrerW
 * @version: 14.06.2017
 *
 * A class to return IpMatcher match success (boolean) and a Message (String) to throw exceptions and display error windows
 */
public class returnInfo {
    
    /** The matched. */
    private boolean matched;
    
    /** The Message. */
    private String Message;

    /**
     * Instantiates a new return info.
     *
     * @param matched_ the matched
     * @param Message_ the message
     */
    returnInfo(boolean matched_, String Message_){
        matched = matched_;
        Message = Message_;
    }

    /**
     * Checks if is matched.
     *
     * @return true, if is matched
     */
    public boolean isMatched(){
        return this.matched;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage(){
        return this.Message;
    }
}
