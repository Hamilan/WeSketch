package networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * This class registers the Data types (Classes) to be sent from Client to Server and vice versa. 
 * Original Kryonet Network classes defined the types to use but for this project
 * an isolated package gives better organization and eases updating global references.
 * @author Hamilan
 */
public class WeSketchNetwork
{
	/**
	 * The default port this application uses to communicate (chosen randomly).  
	 */
	static public final int port = 53335;

	/**
	 * This registers for the endPoint the classes that are going to be sent through it. 
	 */
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		
		//Needed Classes from JDK
		kryo.register(java.util.ArrayList.class);
		kryo.register(int[].class);
		kryo.register(long[].class);
		kryo.register(float[].class);
		//Classes related to Sessions
		kryo.register(shared.Session.class);
		kryo.register(shared.User.class);
		kryo.register(shared.Requirement.class);
		kryo.register(shared.SessionParticipant.class);
		kryo.register(shared.SessionParticipant[].class);
		//Classes related to Chat 
		kryo.register(shared.ChatMessage.class);
		//Classes related to Awareness
		kryo.register(shared.Notification.class);
		kryo.register(shared.Cursor.class);
		kryo.register(shared.Telepointer.class);
		//Classes related to Sketching
		kryo.register(sketching.widgets.WidgetFont.class);
		kryo.register(sketching.widgets.WidgetData.class);
		kryo.register(sketching.widgets.Widget.class);
		kryo.register(sketching.Sketch.class);
	}
}