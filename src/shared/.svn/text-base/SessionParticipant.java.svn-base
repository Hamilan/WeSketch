package shared;

/**
 * This class represents the Session Participant information
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class SessionParticipant {
	/** Unique login used for the user during the session */
	public String login;
	/** id of this participant connection (required for networking) */
	public int id;
	/** ip address of this participant, used for audio-conference */
	public String ip;
	/**
	 * Takes as value the index of this participant in the participants array.</br>
	 * This value relates to one the colors in CVEConstants</br>
	 * Unique color assigned to this participant during the session.
	 */
	public int chairIndexOrColor;
	/** password to verify user */
	public String password;
	/** Id of the registered user */
	public long userId;
	
	public SessionParticipant() {
	}

}