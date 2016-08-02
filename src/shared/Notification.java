package shared;

/**
 * This class represents a Notification sent from Server to Client as a request
 * of an action to be performed.<br/>
 * Every signal requires a group of parameters:<br/><pre>
 * -                         NO_ROOM  {}
 * -                   DELETE_WIDGET  {sketchId, widgetId}
 * -  REMOVE_PARTICIPANT_FROM_SKETCH  {sketchId, participantId}
 * - REMOVE_PARTICIPANT_FROM_SESSION  {participantIndex}
 * -                   DELETE_SKETCH  {sketchId} 
 * -                     DELETE_USER  {userId} 
 * -              LOGIN_ALREADY_USED  {} 
 * - DELETE_REQUIREMENT_FROM_SESSION  {requirementId} 
 * -  DELETE_REQUIREMENT_FROM_SERVER  {requirementId} 
 * -          USERLOGIN_ALREADY_USED  {}
 * -       ADD_PARTICIPANT_TO_SKETCH  {sketchId, participantId} *
 * -                  WRONG_PASSWORD  {} 
 * -              REMOVE_TELEPOINTER  {participantIndex} 
 * </pre> 
 * @author Hamilan
 */
public class Notification {
	transient public final static char NO_ROOM = 0; 
	transient public final static char DELETE_WIDGET = 1; 
	transient public final static char REMOVE_PARTICIPANT_FROM_SKETCH = 2;
	transient public final static char REMOVE_PARTICIPANT_FROM_SESSION = 3;
	transient public final static char DELETE_SKETCH = 5; 
	transient public final static char DELETE_USER = 6;
	transient public final static char LOGIN_ALREADY_USED = 7; 
	transient public final static char DELETE_REQUIREMENT_FROM_SESSION = 8; 
	transient public final static char DELETE_REQUIREMENT_FROM_SERVER = 9;
	transient public static final char USERLOGIN_ALREADY_USED = 10; 
	transient public static final char ADD_PARTICIPANT_TO_SKETCH = 11; 
	transient public static final char WRONG_PASSWORD = 12;
	transient public static final char REMOVE_TELEPOINTER = 13;
	transient public static final char MOVE_TO_FRONT = 14;
	transient public static final char MOVE_TO_BOTTOM = 15; 

	/**
	 * Represents a code to send
	 */
	public char signal;
	/**
	 * Array with integer information to accomplish the asked task
	 */
	public long parameters[];

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public Notification(char signal, long[] parameters) {
		this.signal = signal;
		this.parameters = parameters;
	}

	public Notification(char signal, long id) {
		this.signal = signal;
		this.parameters = new long[]{id};
	}
}
