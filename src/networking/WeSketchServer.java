package networking;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import shared.ChatMessage;
import shared.Cursor;
import shared.Notification;
import shared.Requirement;
import shared.Session;
import shared.SessionParticipant;
import shared.Telepointer;
import shared.User;
import sketching.Sketch;
import sketching.widgets.WidgetData;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

//------------------------------Do not modify enclosed lines-------------------
/**
 * Server of the CVE Distributed System.
 * Administers access to shared information of Sessions.
 * Defines the interaction protocols with Clients.
 * @author Hamilan
 */
public class WeSketchServer {
	Server server;
	ServerSharedData sharedData;
	//Only one session will be running, for now.
	Session session;

	public static JFrame frame = new JFrame("Servidor CVEGUIS");
	public static JTextArea out=new JTextArea("");

	public static void main(String[] args) {
		try {
			new WeSketchServer();
		} catch (IOException e) {
			//Esta línea debe reemplazarse por un mensaje de error más adecuado
			e.printStackTrace();
		}
	}

	/**
	 * Prepares data to be shared and starts receiving clients
	 * @throws IOException
	 */
	public WeSketchServer() throws IOException {
		//prepare the information to be shared
		prepareCommonData();

		server = new Server() {
			protected Connection newConnection () {
				return new WeSketchConnection();
			}
		};

		WeSketchNetwork.register(server);
		server.addListener( new WeSketchServerListener() );
		//start receiving clients
		server.bind(WeSketchNetwork.port);
		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println(
				"___________________________________________________\n" +
				"COLLABORATIVE VIRTUAL ENVIRONMENT for GUI SKETCHING\n"+
				"                     SERVER                        \n"+
				"    IP: "+ip+"     Port: "+WeSketchNetwork.port+"           \n"+
		"___________________________________________________\n" );

		JButton botonVerEstadísticas= new JButton("Ver estadísticas");
		botonVerEstadísticas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(""+session.getTimeInfo()+
						"\n#Msj chat: "+session.getSessionChatMessages().size()+
						"\n#cambios:  "+session.getFilteredChanges().size() );
			}
		});
		frame.add(botonVerEstadísticas);
		frame.setVisible(true);
		
		server.start();
	}
	/**
	 * Prepares information that should be available before receiving any participant
	 */
	private void prepareCommonData() {
		sharedData = new ServerSharedData();
		session = sharedData.getSession();
		session.resetTime();
		session.resetChanges();
		session.resetChatMessages();
		
		int t = Integer.parseInt(JOptionPane.showInputDialog("N° de tarea a usar (1, 2 ó 3):"));
		session.indexTarea=t-1;
	}
	/**
	 * This class represents the Connection for the Distributed System 
	 * @author Hamilan
	 */
	static class WeSketchConnection extends Connection {
		public String name;
	}
	//----------------------------------------------------------------------------------
	/**
	 * Objects of this class will listen to the Clients
	 * @author Hamilan
	 */
	public class WeSketchServerListener extends com.esotericsoftware.kryonet.Listener
	{
		/**
		 * Processes a received object sent by a client.
		 * Verifications are sort from the most frequent object to the least frequent
		 * This receiver doesn't verify Session objects because clients never send them
		 */
		public void received (Connection c, Object object) {
			WeSketchConnection connection = (WeSketchConnection)c;
			//For testing
			String obname = object.getClass().getSimpleName();
//			System.out.println("Server received object "+obname);
			//frame.setVisible(true);
			if(!obname.equals("KeepAlive")) out.append("\nRec: "+obname+" from "+connection.name);
			//----------------------------------------------------------------------------------
			if (object instanceof Cursor) {
				processCursor((Cursor)object, connection);
				return;
			}
			if (object instanceof Telepointer) {
				out.append("\nSend Telepointer ToAllExcept "+connection.name);
				server.sendToAllExceptTCP( connection.getID(), (Telepointer)object );
				return;
			}
			//A widget has been modified or created
			if (object instanceof WidgetData) {
				notifyUpdatewidget((WidgetData) object, connection.getID());
				return;
			}
			if (object instanceof ChatMessage) {
				processChatMessage((ChatMessage) object,connection);
				return;
			}
			//A new sketch has been created
			if (object instanceof Sketch) {
				sharedData.registerSketch((Sketch)object);
				out.append("\nSend Sketch ToAllExcept "+connection.name);
				server.sendToAllExceptTCP(connection.getID(), object);
			}
			//An action like: Erase a Widget, Participant, User, etc. is required.
			if (object instanceof Notification) {
				processNotification((Notification) object, connection.getID() );
				return;
			}
			//llegada de un nuevo cliente. Todo cliente envía este mensaje de primero.
			if (object instanceof SessionParticipant) {
				if( receiveParticipant((SessionParticipant)object, connection.getID() ) ){
					connection.name = ((SessionParticipant)object).login;
					
					//metrics
					session.continueTime();
				}
				return;
			}
			//Un cliente quiere crear un nuevo requerimiento (Subsistema administración de requerimientos)
			if (object instanceof Requirement) {
				registerRequirement( (Requirement)object, connection.getID()  );
				return;
			}
			//Llegan los datos de un usuario que se está registrando
			if ( object instanceof User ) {
				registerUser( (User)object, connection.getID()  );
				return;
			}
		}

		/**
		 * Sends the cursor only to the co-editors of the sketch where the Cursor is being used
		 * @param c
		 * @param connection
		 */
		private void processCursor(Cursor c, WeSketchConnection connection) {
			long skid=0;

			ArrayList<SessionParticipant> collaborators = 
				session.getParticipantsInSketch( c.sketchId );
			for (SessionParticipant p : collaborators) {
				if(p.id!=connection.getID()){
					out.append("\nSend Cursor to "+p.login);
					server.sendToTCP(p.id, c );
				}
			}
			return;
		}

		private void processChatMessage(ChatMessage chatMsg, WeSketchConnection connection) {
			//Metrics---------
			session.addChatMessage(chatMsg);
			//----------------
			
			String message = chatMsg.text;
			if (message == null) return;
			message = message.trim();
			if (message.length() == 0) return;

			//if message is not private
			if(chatMsg.privateToId==-1){
				//Prepend the connection's name and send to everyone.
				//chatMsg.text = connection.name + ": " + message;	
				chatMsg.text = message;
				//if message is for everyone in the session
				if(chatMsg.groupId==-1 ){
					out.append("\nSend ChatMsg ToAll");
					server.sendToAllExceptTCP( connection.getID(),chatMsg );
					return;
				}
				//if message is for everyone involved in a sketch
				else{
					ArrayList<SessionParticipant> collaborators = 
						session.getParticipantsInSketch( chatMsg.groupId );
					for (SessionParticipant participant : collaborators) {
						if(participant.id!=connection.getID()){
							out.append("\nSend ChatMsg to "+participant.login);
							server.sendToTCP(participant.id, chatMsg );
						}
					}
					return;
				}
			}
			//message is private
			else{
				//Prepend the connection's name and send to everyone.
				//chatMsg.text = connection.name + "(en Privado): " + message;
				chatMsg.text = message;
				out.append("\nSend ChatMsg To "+chatMsg.privateToId);
				server.sendToTCP( chatMsg.privateToId, chatMsg );
				return;
			}
		}

		/**
		 * Notifies to everyone about the widget update.
		 * The participant who sent the widget update is not informed.
		 * @param widgetData
		 * @param connectionID
		 */
		private void notifyUpdatewidget(WidgetData widgetData, int connectionID) {
			boolean done = false;
			for (Sketch sketch : sharedData.getSession().sketches) {
				if(sketch.id == widgetData.sketchId){
					int i = 0;
					for (WidgetData w : sketch.widgets) {
						if(w.id == widgetData.id){
							done = true;
							//replaces the old widget
							sketch.widgets.set(i, widgetData);
							break;
						}
						i++;
					}
					if(done==false){
						sketch.widgets.add(widgetData);
					}
					break;
				}
			}
			out.append("\nSend WidgetData ToAllExcept "+connectionID);
			server.sendToAllExceptTCP(connectionID, widgetData);
			//Metrics---------
			session.addChange(widgetData, connectionID);
			//----------------
		}

		/**
		 * Identifies the notification type and tries to do what is required
		 * Cases are evaluated from the most frequent to the least one.
		 * USERLOGIN_ALREADY_USED, LOGIN_ALREADY_USED and NO_ROOM notifications will never arrive to the server 
		 * @param notification
		 * @param connectionID
		 */
		private void processNotification(Notification notification, int connectionID) {
			out.append("\nSend Notf ToAllExcept "+connectionID);
			switch (notification.signal) {
			case Notification.DELETE_WIDGET:
				//remove it from the sketch and notify to everyone
				session.removeWidget(notification.parameters[0],notification.parameters[1]);
				server.sendToAllExceptTCP(connectionID, notification);
				//Metrics---------
				WidgetData wd = new WidgetData();
				wd.id = notification.parameters[1];
				session.addChange(wd, connectionID);
				//----------------
				break;
			case Notification.REMOVE_PARTICIPANT_FROM_SKETCH:
				session.removeParticipantFromSketch(notification.parameters[0],(int)notification.parameters[1]);
				server.sendToAllExceptTCP(connectionID, notification);
				break;
			case Notification.ADD_PARTICIPANT_TO_SKETCH:
				session.addParticipantToSketch(notification.parameters[0],(int)notification.parameters[1]);
				server.sendToAllExceptTCP(connectionID, notification);
				break;
			case Notification.REMOVE_PARTICIPANT_FROM_SESSION:
				//The only way to get this is if a client sends this signal,
				//like expelling the participant
				session.removeParticipant((int)notification.parameters[0]);
				server.sendToAllExceptTCP(connectionID, notification);
				break;
			case Notification.MOVE_TO_BOTTOM:
				updateMoveToBottom(notification.parameters[0],notification.parameters[1]);
				server.sendToAllExceptTCP(connectionID, notification);
				break;
			case Notification.MOVE_TO_FRONT:
				updateMoveToFront(notification.parameters[0],notification.parameters[1]);
				server.sendToAllExceptTCP(connectionID, notification);
				break;
//-----------------------------------------------------------------------------
// TODO: Next Notifications aren't used in the 1st release of the tool 
//-----------------------------------------------------------------------------

			case Notification.DELETE_SKETCH:
				session.removeSketch( notification.parameters[0] );
				server.sendToAllExceptTCP(connectionID, notification);
				break;
			case Notification.DELETE_REQUIREMENT_FROM_SERVER:
				sharedData.removeRequirement(notification.parameters[0]);
			case Notification.DELETE_REQUIREMENT_FROM_SESSION:
				session.removeRequirement(notification.parameters[0]);
				server.sendToAllExceptTCP(connectionID, notification);
				break;
			case Notification.DELETE_USER:
				String login = sharedData.getUserLogin( notification.parameters[0] );
				if(login==null) return;
				int participantId = session.getPositionOf(login);
				sharedData.removeUser( notification.parameters[0] );
				if(participantId==-1) return;
				Notification n = new Notification(Notification.REMOVE_PARTICIPANT_FROM_SESSION, participantId);
				server.sendToAllExceptTCP(connectionID, n );
				break;
			}
		}

		private void updateMoveToFront(long skId, long wId) {
			for (Sketch sk : sharedData.getSession().sketches) {
				if(sk.id == skId){
					sk.moveToFront(wId);
					return;
				}
			}
		}

		private void updateMoveToBottom(long skId, long wId) {
			for (Sketch sk : sharedData.getSession().sketches) {
				if(sk.id == skId){
					sk.moveToBottom(wId);
					return;
				}
			}
		}

		/**
		 * Tries to sit a participant in a chair and assign him/her a color
		 * @param participant
		 * @param connectionID
		 */
		private boolean receiveParticipant(SessionParticipant participant, int connectionID) {
			//verify login is not being used
			if( session.getPositionOf(participant.login) != -1 ){
				Notification note = new Notification(Notification.LOGIN_ALREADY_USED, null);
				out.append("\nSend Ntf To "+connectionID);
				server.sendToTCP(connectionID, note);
				return false;
			}
			//find available position
			int pos = session.sitParticipant( connectionID, participant );
			if( pos == -1 ){
				Notification note = new Notification(Notification.NO_ROOM, null);
				out.append("\nSend Cursor To "+connectionID);
				server.sendToTCP(connectionID, note);
				return false;
			}
			//all the information of the session is sent to the new participant
			out.append("\nSend Session To "+connectionID);
			server.sendToTCP(connectionID, session);
			//notify to the other participants
			participant.password="";
			out.append("\nSend Participant ToAllExcept "+connectionID);
			server.sendToAllExceptTCP( connectionID, participant );
			return true;
		}

		/**
		 * Registers a new requirement
		 * @param req
		 * @param connectionID
		 */
		private void registerRequirement(Requirement req, int connectionID) {
			sharedData.registerRequirement(req);
			out.append("\nSend Req ToAllExcept "+connectionID);
			server.sendToAllExceptTCP(connectionID, req);
		}

		/**
		 * Registers a new user
		 * @param connectionId 
		 * @param object
		 */
		private void registerUser(User u, int connectionID) {
			if(sharedData.existsUser(u.login) ){
				Notification note = new Notification(Notification.USERLOGIN_ALREADY_USED, null);
				out.append("\nSend Ntf To "+connectionID);
				server.sendToTCP(connectionID, note);
				return;
			}
			sharedData.registerUser(u);
		}
		/** A client left the server */
		public void disconnected (Connection c) {
			processDisconnection((WeSketchConnection)c);
		}
		/** Processes the exit of a participant */
		private void processDisconnection(WeSketchConnection c){
			if (c.name != null) {
				Notification notif = new Notification( Notification.REMOVE_PARTICIPANT_FROM_SESSION, c.getID() );
				session.removeParticipant( c.getID() );
				server.sendToAllExceptTCP( c.getID(), notif );
				out.append("\nSend Ntf ToAllExcept "+c.name);
				
				//metrics
				if(session.countParticipants()==0){
					session.pauseTime();
				}
			}
		}
	}
	
}
