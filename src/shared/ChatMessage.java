package shared;

/**
 * This class represents a ChatMessage 
 * Objects of this class will be used for the chat system.
 * Allows messages sent in private to a user involved in a sketch or a session.
 * It also supports messages to a group of users involved in a sketch.
 * And of course it can be sent to just everyone.  
 * @author Hamilan
 */
public class ChatMessage {
	/**
	 * chat message
	 */
	public String text;
	/**
	 * Id of the recipients group.
	 * It is really the id of the sketch through which the message was sent. 
	 * If groupId is -1 and privateToId is -1 the message will be sent to 
	 * everyone in the session.
	 */
	public long groupId;
	/**
	 * Id of the user for a private messages. If -1 it won't be considered private.
	 */
	public int privateToId;
	public int senderIndex;
	
	public ChatMessage( ) {
	}
	/**
	 * This constructor creates a chat message for everyone in the session
	 * @param message
	 */
	public ChatMessage( String message ) {
		this.text = message;
		this.groupId = -1;
		this.privateToId = -1;
	}
	/**
	 * This constructor creates a chat message for everyone involved in the session groupID
	 * @param message
	 */
	public ChatMessage( String message, int groupId ) {
		this.text = message;
		this.groupId = groupId;
		this.privateToId = -1;
	}
	/**
	 * This constructor creates a private chat message for someone in the session
	 * @param message
	 */
	public ChatMessage( String message, int groupId, int privateToId ) {
		this.text = message;
		this.groupId = groupId;
		this.privateToId = privateToId;
	}
	/**
	 * This constructor creates a private chat message for someone in the session
	 * @param message
	 */
	public ChatMessage( String message, long id, int privateToId,int senderIndex ) {
		this.text = message;
		this.groupId = id;
		this.privateToId = privateToId;
		this.senderIndex = senderIndex;
	}
}
