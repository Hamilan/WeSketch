package networking;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import shared.WeSketchConstants;
import shared.ChatMessage;
import shared.Cursor;
import shared.Notification;
import shared.Session;
import shared.SessionParticipant;
import shared.Telepointer;
import sketching.NewSketchFrame;
import sketching.Sketch;
import sketching.SketchingFrame;
import sketching.widgets.Widget;
import sketching.widgets.WidgetData;
import ve.WeSketchGame;
import clientgui.ChatFrame;
import clientgui.LoginFrame;
import clientgui.RegisterUserFrame;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

public class WeSketchClient {
	WeSketchClient cliente;
	//required fields
	private String myHost="";
	private String myLogin="";
	private static Client kryoClient;
	private boolean loggedIn = false;
	public String myIp;

	//Listeners
	private LoginListener loginListener; //won't last long
	private SessionListener sessionListener; //will last long

	//current session's data
	public static Session currentSession;
	public static SessionParticipant myParticipantInfo;
	Sketch currentSketch;
	
	//frames
	public static WeSketchGame myJMEGame;
	LoginFrame myLoginFrame;
	RegisterUserFrame myRegisterFrame;
	private NewSketchFrame myNewSketchFrame;
	private SketchingFrame mySketchingFrame;
	private ChatFrame myChatFrame;
	
	//only sender
	public static Sender sender;
	private static ArrayList<Widget> widgetsClipBoard=new ArrayList<Widget>();
	
	public static int getColor(){
		return myParticipantInfo.chairIndexOrColor;
	}

	public static void main(String[] args) {
		new WeSketchClient();
	}

	/**
	 * Shows login frame and starts kryoClient (doesn't connect)
	 */
	public WeSketchClient()	{
		cliente = this;
		try {
			myIp = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		new Thread("JMEGame"){
			@Override
			public void run() {
				//since the JME loop doesn't end, it should run as another Thread
				myJMEGame = new WeSketchGame( cliente );
			}
		}.start();

		//prepares client for network use
		kryoClient = new Client();
		kryoClient.start();
		WeSketchNetwork.register(kryoClient);
	}

	/**
	 * TODO este método debería pasarse para otra clase relacionada con InterfazGráfica
	 * @param e
	 */
	public void showLoginFrame() {
		myLoginFrame = new LoginFrame( this );
		myLoginFrame.setVisible(true);
		myJMEGame.control.loadInternalFrame(myLoginFrame);
		myLoginFrame.focusOnLogin();
	}
	public void setChatFrame(ChatFrame chatFrame) {
		this.myChatFrame= chatFrame;
	}
	//----------------------------------------------------------------------
	/**
	 * Allows the login frame to start connection with the server
	 * @param hostAddress
	 * @param login
	 */
	public void login(String hostAddress, String login, String password) {
		if(loggedIn) return;
		//if no connection has been established nor login has been accepted
		if(kryoClient==null || kryoClient.isConnected()==false){
			connectFirstTime(hostAddress,login,password);//no password yet
		}
		//connection established before but login wasn't accepted, we'll try again
		else {
			sendSessionParticipantInfo( login, password);
		}
	}
	/**
	 * Establishes connection with server (for the first time) to introduce client's name
	 * @param password 
	 * @param login 
	 * @param hostAddress 
	 */
	private void connectFirstTime(final String hostAddress, final String login, final String password) {
		// We'll do the connect on a new thread so the LoginFrame can show a progress bar.
		// Connecting to localhost is usually so fast you won't see the progress bar.
		new Thread("Connect") {
			public void run () {
				try {
					//TODO: mostrar animación de intentando conexión en la ventana 
					kryoClient.connect(5000, hostAddress, WeSketchNetwork.port);
					//if this line is reached, connection was established
					myHost=hostAddress;
					myLoginFrame.setServerConnectionStatus(true);
				} catch (IOException ex) {
					myLoginFrame.setServerConnectionStatus(false);
					return;
				}
				//defines thread to receive first data from the Server
				loginListener = new LoginListener(); 
				kryoClient.addListener( loginListener );
				sender = new Sender();
				sendSessionParticipantInfo(login, password);
			}
		}.start();
	}
	/** save the address so the client loads it in the next launch */
	private void saveSessionLoginInfo(String myHost, String myLogin) {
		try {
			PrintWriter out= new PrintWriter("lastconnectiondata.cnf");
			out.println(myHost);
			out.println(myLogin);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Sends information to login
	 */
	private void sendSessionParticipantInfo(String login, String password) {
		myLogin = login;
		SessionParticipant participantInfo = new SessionParticipant();
		participantInfo.login = login;
		participantInfo.password = password;
		participantInfo.id = kryoClient.getID();
		try {
			participantInfo.ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		//participantInfo.ip = my
		kryoClient.sendTCP(participantInfo);
	}
	/**
	 * This class will listen to the first messages sent by the Server
	 * @author Hamilan
	 */
	public class LoginListener extends com.esotericsoftware.kryonet.Listener {
		@Override
		/**
		 * Initially Server sends only a Session object if accepts the client or
		 * a Notification if something went wrong
		 */
		public void received(Connection connection, Object object) {
			if(object==null) return;

			if (object instanceof Session) {
				//if received, then Client was accepted to the session
				prepareSession( (Session)object );
				return;
			}
			if (object instanceof Notification) {
				//if received something went wrong
				Notification notif = (Notification) object;
				if(notif.signal==Notification.LOGIN_ALREADY_USED){
					myLoginFrame.showNotification("El login ingresado ya se encuentra en uso, por favor intente uno diferente.","Login ya está en uso",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(notif.signal==Notification.WRONG_PASSWORD){
					myLoginFrame.showNotification("El login o contraseña son incorrectos, por favor intente de nuevo.","Login ya está en uso",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(notif.signal==Notification.NO_ROOM){
					myLoginFrame.showNotification("La sala a la que intenta ingresar se encuentra llena, por favor intente una diferente suministrando otro servidor.","Login ya está en uso",JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(notif.signal==Notification.USERLOGIN_ALREADY_USED){
					myLoginFrame.showNotification("Ya hay un usuario registrado cono ese Login\nIntenta uno diferente.","Login ya está en uso",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}

		}
	}
	/**
	 * Called when the user was accepted to the session and the whole information of it
	 * should be shown in the 3D Environment 
	 * @param session
	 */
	private void prepareSession(Session session) {
		myLoginFrame.setLoginConnectionStatus(true);
		myLoginFrame.dispose();
		loggedIn = true;
		currentSession = session;

		//updates listeners
		kryoClient.removeListener(loginListener);
		loginListener = null;
		sessionListener = new SessionListener();
		kryoClient.addListener( sessionListener );

		myJMEGame.resetAvatars();
		//con arreglo participants identificar Avatar presentes,
		//mostrarlos al rededor de la mesa y mostrar sus nombres
		for (int i = 0; i < currentSession.participants.length; i++) {
			SessionParticipant p =currentSession.participants[i]; 
			if(p!=null){
				myJMEGame.addAvatar(i,p.login );

				//this guarantees that myParticipantInfo.color is set.
				if(p.login.equals(myLogin)){
					myParticipantInfo = p;
				}
			}
		}
		myJMEGame.setTitle(WeSketchConstants.APPLICATION_NAME+" - "+myLogin+"@"+myIp);
		myJMEGame.show3DSketches( currentSession.sketches );
		myJMEGame.setViewToParticipant( myParticipantInfo.chairIndexOrColor );
		myJMEGame.control.setLogingInFlag(false);
		myJMEGame.control.pickAvailable=true;
		myJMEGame.control.controlInstance.showMainMenuFrame();
		saveSessionLoginInfo(myHost,myLogin);
	}

	public String getMyLogin() {
		return myLogin;
	}
	/**
	 * returns participants in the current session
	 * @return
	 */
	public SessionParticipant[] getSessionParticipants() {
		if(currentSession==null){
			return new SessionParticipant[]{};
		}
		return currentSession.participants;
	}

	//----------------------------------------------------------------------
	/**
	 * Objects of this class will listen to the Server.
	 * @author Hamilan
	 */
	public class SessionListener extends com.esotericsoftware.kryonet.Listener {
		/**
		 * Server sends these objects which are listened by this listener:
		 * -OK ChatMessage / several times
		 * -OK Notification /several times
		 * -OK SessionParticipant /every time a new one joins
		 * -OK Sketch / when a new one has been created
		 * -OK Cursor / several times CURSOR TYPE CHANGE STILL NEEDS TESTING
		 * -OK Telepointer / several times	NOT TESTED
		 * -OK WidgetData /several times, when a widget is created or updated
		 * -Requirement /when a new one has been created
		 */
		@Override
		public void received(Connection connection, Object object) {
			//for testing
//			System.out.println("Client received object "+object.getClass().getName());
			if(object==null || loggedIn==false) return;
			
			if (object instanceof Cursor) {
				if(mySketchingFrame!=null){
					mySketchingFrame.updateCursor((Cursor)object);//TODO esto está generando exception
				}
				return;
			}
			if (object instanceof WidgetData) {
				udpateWidget((WidgetData)object);
				return;
			}
			if (object instanceof Telepointer) {
				Telepointer telepointer=(Telepointer) object;
				myJMEGame.updateTelepointers(telepointer);
				return;
			}
			if (object instanceof ChatMessage) {
				updateChat((ChatMessage)object);
				return;
			}
			if (object instanceof Notification) {
				processNotification((Notification)object);
				return;
			}
			if (object instanceof Sketch) {
				updateAddSketch((Sketch)object);
				return;
			}
			if (object instanceof SessionParticipant) {
				//Server added someone to the session
				updateAddParticipant((SessionParticipant)object); 
				return;
			}
		}
		private void udpateWidget(WidgetData w) {
			if(currentSketch!=null && currentSketch.id == w.sketchId){
				mySketchingFrame.updateWidget(w);
			}else{
				currentSession.updateWidget(w);
			}
		}
		private void updateChat(ChatMessage ch) {
			//if message is global
			if(ch.groupId==-1){
				//if message is private
				if(ch.privateToId!=-1){
					//cambios en el color o algo similar
					ch.text="<i>(Privado)</i> "+ch.text;
				}
				myChatFrame.updateSessionChat(ch.senderIndex, currentSession.participants[ch.senderIndex].login, ch.text);
			}else{
				if(ch.privateToId!=-1){
					//cambios en el color o algo similar
					ch.text="<i>(Privado)</i> "+ch.text;
				}
				//este debería borrarse y dejarse solo en JME
				myChatFrame.updateSketchChat(ch.senderIndex, currentSession.participants[ch.senderIndex].login, ch.text);
			}
		}
		private void processNotification(Notification notification) {
			switch (notification.signal) {
			case Notification.REMOVE_TELEPOINTER:
				myJMEGame.removeTelepointer((int)notification.parameters[0]);
				break;
			case Notification.DELETE_WIDGET:
				removeWidgetFromSketch(notification.parameters[0],notification.parameters[1]);
				break;
			case Notification.REMOVE_PARTICIPANT_FROM_SKETCH:
				removeParticipantFromSketch(notification.parameters[0],(int)notification.parameters[1]);
				break;
			case Notification.ADD_PARTICIPANT_TO_SKETCH:
				addParticipantToSketch(notification.parameters[0],(int)notification.parameters[1]);
				break;
			case Notification.REMOVE_PARTICIPANT_FROM_SESSION:
				updateRemoveParticipant((int)notification.parameters[0]);
				break;
			case Notification.MOVE_TO_BOTTOM:
				updateMoveToBottom(notification.parameters[0],notification.parameters[1]);
				break;
			case Notification.MOVE_TO_FRONT:
				updateMoveToFront(notification.parameters[0],notification.parameters[1]);
				break;
			case Notification.DELETE_REQUIREMENT_FROM_SESSION:
				//mySession.removeRequirement(notification.parameters[0]);
				//TODO: update requirementsFrame
				//TODO: test
				break;
			case Notification.DELETE_SKETCH:
				//TODO: mySession.removeSketch( notification.parameters[0] );
				//TODO: update myJMEGame
				break;
			}
		}
		
		private void updateMoveToFront(long skId, long wId) {
			for (Sketch sk : currentSession.sketches) {
				if(sk.id == skId){
					sk.moveToFront(wId);
					if(currentSketch!=null && currentSketch.id == skId){
						mySketchingFrame.moveToFront(wId);
					}
					return;
				}
			}
		}
		private void updateMoveToBottom(long skId, long wId) {
			for (Sketch sk : currentSession.sketches) {
				if(sk.id == skId){
					sk.moveToFront(wId);
					if(currentSketch!=null && currentSketch.id == skId){
						mySketchingFrame.moveToBottom(wId);
					}
					return;
				}
				
			}
		}
		/** remove it from the sketch and notify to everyone
		 * @param skId
		 * @param wId
		 */
		private void removeWidgetFromSketch(long skId, long wId) {
			if(currentSketch!=null && currentSketch.id==skId){
				//this will erase the widget from everywhere
				mySketchingFrame.removeWidget(wId);
			}else{
				currentSession.removeWidget(skId, wId);
				//TODO: if real time views of others' sketches is available
				// then we need to call their update
				// calling the specific Sketch3D and set it's texture
			}
			
		}
		/**
		 * Updates the session and the environment adding a Sketch to the roundtable<br/>
		 * Since the sketch was just created, it has at least one collaborator in it, it's creator 
		 * @param s
		 */
		private void updateAddSketch(Sketch s) {
			currentSession.sketches.add(s);
			myJMEGame.addSketchToParticipant(s, s.collaborators.get(0).chairIndexOrColor);
		}
		/**
		 * Updates the session and the environment adding a Participant to the table<br/>
		 * Since the participant is new in the room has no Sketches assigned
		 * @param p
		 */
		private void updateAddParticipant(SessionParticipant p) {
			currentSession.participants[ p.chairIndexOrColor ] = p;
			myJMEGame.addParticipant( p );
		}
		/**
		 * Removes a participant from the local copy of the session.<br/>
		 * Removes the participant from the 3D Environment.
		 * Shows the sketches again.
		 * @param partId Id of the participant to remove.
		 */
		private void updateRemoveParticipant(int partId) {
			System.out.println("Participant widh id:"+partId+" left the session");
			//TODO: test
			if( mySketchingFrame!=null ){
				mySketchingFrame.removeParticipant(partId);
			}
			int pos = currentSession.removeParticipant(partId);
			if(pos==-1)
				return;
			myJMEGame.removeParticipant( pos );
			myJMEGame.show3DSketches(currentSession.sketches);
		}
		@Override
		public void disconnected(Connection connection) {
			signOut();
		}
	}
	//-----------------------------------------------------------------------
	// METHODS CALLED FROM OTHER CLASSES NOT LISTENERS
	//-----------------------------------------------------------------------

	public JInternalFrame newSketch() {
		myNewSketchFrame = new NewSketchFrame( this );
		myNewSketchFrame.setVisible(true);
		return myNewSketchFrame;
	}

	public JInternalFrame newSketchingFrame(Sketch s) {
		mySketchingFrame = new SketchingFrame( s, this );
		//mySketchingFrame.setSize(1030,600);
		mySketchingFrame.setVisible(true);
		return mySketchingFrame;
	}

	public void setSketchingFrame(String name, String description, String author, String requisite) {
		myNewSketchFrame.dispose();

		long id=WeSketchConstants.getNewId();
		Sketch s = new Sketch(id,name, description, author, requisite );
		s.collaborators.add(myParticipantInfo);
		kryoClient.sendTCP( s );

		//SessionListener wont receive the message so we have to add the sketch here
		currentSession.sketches.add(s);
		myJMEGame.addSketchToParticipant(s, myParticipantInfo.chairIndexOrColor );
		currentSketch = s;
		//cargar la ventana de sketching dentro del JMEDesktop
		myJMEGame.control.loadInternalFrame( newSketchingFrame(s) );
		myJMEGame.control.setNewSketchFlag(false);
		myJMEGame.control.setSketchingFlag(true);
		//myJMEGame.control.perspective();
	}
	public void addParticipantToSketch(long sketchId, int participantId){
		//if participant is me I already did this
		if(participantId==myParticipantInfo.id)
			return;
		Sketch s=null;
		for (Sketch sk : currentSession.sketches) {
			if(sk.id==sketchId){
				s = sk;
				break;
			}
		}
		SessionParticipant p=null;
		for (SessionParticipant pi : currentSession.participants) {
			if(pi!=null && pi.id==participantId){
				p = pi;
				break;
			}
		}
		s.collaborators.add(p);
		myJMEGame.show3DSketches(currentSession.sketches);
		myJMEGame.addParticipantToSketch(p.chairIndexOrColor,sketchId);
		
		if( mySketchingFrame!=null && currentSketch.id == sketchId ){
			mySketchingFrame.addParticipant(p);
		}
	}
	
	public void removeParticipantFromSketch(long skId, int parId) {
		//if the user is me, I already did this
		if(parId== myParticipantInfo.id)
			return;
		//if user was in the sketch i'm editing
		if(currentSketch!=null &&  skId==currentSketch.id){
			mySketchingFrame.removeParticipant(parId);
		}
		currentSession.removeParticipantFromSketch(skId, parId);
		
		int pos = currentSession.getPositionOf(parId);
		myJMEGame.removeParticipantFromSketch(pos);
		myJMEGame.show3DSketches(currentSession.sketches);
	}

	public class Sender{
		/*-------------------------------------------------------------------
		 *	METHODS TO SEND TO THE SERVER FROM EVERYWHERE IN THE CVE DURING A SESSION
		 *
		 *GlobalChat	OK
		 *LocalChat		OK
		 *Cursor		OK
		 *Telepointer	OK
		 *Widget		
		 *Sketch		OK Out from this class
		 *Notification	
		 *
		-------------------------------------------------------------------*/
		public void sendGlobalChat(String msg) {
			kryoClient.sendTCP( new ChatMessage(msg,-1,-1,myParticipantInfo.chairIndexOrColor) );
		}
		public void sendLocalChat(String text, int sketchId) {
			kryoClient.sendTCP( new ChatMessage(text,sketchId,-1,myParticipantInfo.chairIndexOrColor) );		
		}
		public void sendLocalChat(String text) {
			kryoClient.sendTCP( new ChatMessage(text,currentSketch.id,-1,myParticipantInfo.chairIndexOrColor) );		
		}
		public void sendCursor( shared.Cursor cursor) {
			kryoClient.sendTCP( cursor );		
		}
		public void sendTelepointer( shared.Telepointer tp){
			kryoClient.sendTCP( tp );
		}
		public void sendWidget(sketching.widgets.WidgetData w ){
			kryoClient.sendTCP( w );
		}
		public void sendHideTelepointer(int color) {
			Notification notif = new Notification( Notification.REMOVE_TELEPOINTER, color );
			kryoClient.sendTCP(notif);
		}
		public void sendRemoveWidget(long wId) {
			kryoClient.sendTCP( new Notification(Notification.DELETE_WIDGET,new long[]{currentSketch.id,wId}) );
		}
		
		public void addMeToSketch(long l) {
			Sketch s=null;
			for (Sketch sk : currentSession.sketches) {
				if(sk.id==l){
					s = sk;
					break;
				}
			}
			s.collaborators.add(myParticipantInfo);
			currentSketch=s;
			myJMEGame.addSketchToParticipant(s, myParticipantInfo.chairIndexOrColor );
			myJMEGame.control.loadInternalFrame(newSketchingFrame(s));
			myJMEGame.control.setSketchingFlag(true);
			myJMEGame.show3DSketches(currentSession.sketches);
			kryoClient.sendTCP( new Notification(Notification.ADD_PARTICIPANT_TO_SKETCH,new long[]{l,myParticipantInfo.id}) );
		}
		public void notifyToFront(WidgetData data) {
			kryoClient.sendTCP( new Notification(Notification.MOVE_TO_FRONT,new long[]{data.sketchId,data.id}) );
		}
		public void notifyToBottom(WidgetData data) {
			kryoClient.sendTCP( new Notification(Notification.MOVE_TO_BOTTOM,new long[]{data.sketchId,data.id}) );
		}
	}
	/**
	 * This method will only be called from SketchingFrame, so it's
	 * guaranteed that there is a currentSketch.
	 * @param x
	 * @param y
	 */
	public void removeMeFromSketch() {
		myJMEGame.control.setSketchingFlag(false);
		//TODO Verificar si también hay que removerlo de la audio conferencia
		currentSession.removeParticipantFromSketch(currentSketch.id, myParticipantInfo.id);
		kryoClient.sendTCP( new Notification(Notification.REMOVE_PARTICIPANT_FROM_SKETCH, new long[]{currentSketch.id,myParticipantInfo.id}) );
		myJMEGame.show3DSketches(currentSession.sketches);
		myJMEGame.setAvatarIdle(myParticipantInfo.chairIndexOrColor);
		currentSketch = null;
		mySketchingFrame=null;
	}
	private void signOut() {
		//TODO arreglo temporal
		System.out.println("Se perdió conexión con el servidor");
		if(currentSketch!=null){
			mySketchingFrame.dispose();
			mySketchingFrame=null;
		}
		loggedIn = false;
		currentSketch=null;
		currentSession=null;
		myParticipantInfo=null;
		myJMEGame.control.pickAvailable=false;
		showLoginFrame();
		//TODO: arreglar para que solo se cierre la sesión y muestre que se perdió la conexión. 
	}

	//TODO not tested yet
	public static void copyWidgetsToClipBoard(ArrayList<Widget> newSelectedWidgets) {
		widgetsClipBoard= newSelectedWidgets;
	}
	//TODO not tested yet
	public static ArrayList<Widget> getWidgetsClipBoard(){
		return widgetsClipBoard;
	}
}