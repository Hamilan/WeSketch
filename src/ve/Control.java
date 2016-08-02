package ve;

import java.awt.Color;
import java.awt.dnd.DnDConstants;

import javax.swing.JInternalFrame;

import networking.WeSketchClient;
import shared.SessionParticipant;
import ve.environment.ColoredAvatar;
import ve.environment.Mesa;
import ve.environment.Sketch3D;
import clientgui.MainMenuFrame;

import com.jme.input.InputHandler;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.intersection.PickData;
import com.jme.intersection.PickResults;
import com.jme.intersection.TrianglePickResults;
import com.jme.math.Ray;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.system.DisplaySystem;
import com.jmex.awt.input.AWTKeyInput;
import com.jmex.awt.swingui.JMEDesktop;
import com.jmex.awt.swingui.dnd.JMEDragAndDrop;
import com.jmex.awt.swingui.dnd.JMEDragGestureEvent;
import com.jmex.awt.swingui.dnd.JMEDragGestureListener;
import com.jmex.awt.swingui.dnd.JMEDragSourceEvent;
import com.jmex.awt.swingui.dnd.JMEDragSourceListener;
import com.jmex.awt.swingui.dnd.JMEDropTargetEvent;
import com.jmex.awt.swingui.dnd.JMEDropTargetListener;
import com.jmex.awt.swingui.dnd.JMEMouseDragGestureRecognizer;

import conference.ConferenceController;

public class Control implements JMEDragGestureListener, JMEDragSourceListener, JMEDropTargetListener {
	private static final int LEFTBUTTON = 0;
	private static final int RIGHTBUTTON = 1;
	private static final int MIDDLEBUTTON = 2;

	private static WeSketchGame myJMEGame;
	private static int width, height;
	private Node desktopNode;
	final static JMEDesktop jmeDesktop = new JMEDesktop( "internalDesktop" );

	private JMEDragAndDrop supDragDrop;
	private JMEMouseDragGestureRecognizer mouseRecognizer;
	private JMEDragGestureListener dragListener;
	private WeSketchClient myClient;
	private ConferenceController conferenceController;
	private MainMenuFrame mainMenuFrame;

	//These flags were created to manage certain states in the game and allow or deny some behaviors
	private static boolean isManagingSketchesFlag = false;
	private static boolean isSketchingFlag = false;
	private static boolean isLogingInFlag = false;
	private static boolean isManagingRequirementsFlag = false;
	private static boolean isListingSketchesFlag = false;
	private static boolean isWatchingAnotherFlag = false;
	public static boolean pickAvailable=true;

	public static Control controlInstance=null;

	public Control(int width, int height, WeSketchClient myClient) {
		controlInstance = this;
		this.width = width;
		this.height = height;
		this.myClient = myClient;

		new JMEDragAndDrop(jmeDesktop);
		supDragDrop = jmeDesktop.getDragAndDropSupport();
		mouseRecognizer = new JMEMouseDragGestureRecognizer(supDragDrop, jmeDesktop.getJDesktop(), DnDConstants.ACTION_MOVE, this);
	}

	/**
	 * Prepares the JDesktop where JInternalFrames will be shown
	 * @param rootNode
	 * @param input
	 */
	public void createDesktop(Node rootNode, InputHandler input) {
		input.setEnabled(true);
		jmeDesktop.setup( width,height, false, input );
		jmeDesktop.setLightCombineMode( Spatial.LightCombineMode.Off );
		jmeDesktop.getJDesktop().setBackground( new Color( 0, 0, 0, 0.0f ) );
		jmeDesktop.unlock();
		desktopNode = new Node( "desktop node" );
		desktopNode.attachChild( jmeDesktop );
		rootNode.attachChild( desktopNode );
		rootNode.setCullHint( Spatial.CullHint.Never );
		fullScreen();
		//perspective();
		rootNode.updateModelBound();
		rootNode.updateRenderState();
		rootNode.updateWorldBound(); 
		//attach base internal frames
		createMainToolBarFrame();
	}
	private void createMainToolBarFrame() {
		mainMenuFrame=new MainMenuFrame(myClient);
		mainMenuFrame.setVisible(true);
	}
	public void showMainMenuFrame() {
		mainMenuFrame.setAtBottomOf(jmeDesktop.getJDesktop());
		jmeDesktop.getJDesktop().add( mainMenuFrame );
		conferenceController = mainMenuFrame.getConferenceController();
	}

	/**
	 * shows the Desktop full screen
	 */
	public void fullScreen() {
		final DisplaySystem display = DisplaySystem.getDisplaySystem();

		desktopNode.getLocalRotation().set( 0, 0, 0, 1 );
		desktopNode.getLocalTranslation().set( display.getWidth() / 2, display.getHeight() / 2, 1f );
		desktopNode.getLocalScale().set( 1, 1, 1 );
		desktopNode.setRenderQueueMode( Renderer.QUEUE_ORTHO );
		desktopNode.setCullHint( Spatial.CullHint.Never );
	}
	/**
	 * shows the Desktop in a perspective view
	 */
	public void perspective() {
		desktopNode.getLocalRotation().fromAngleNormalAxis( -0.7f, new Vector3f( 1, 0, 0 ) );
		desktopNode.setLocalScale( 24f / jmeDesktop.getJDesktop().getWidth() );
		desktopNode.getLocalTranslation().set( 0, 0, 0 );
		desktopNode.setRenderQueueMode( Renderer.QUEUE_TRANSPARENT );
		desktopNode.setCullHint( Spatial.CullHint.Dynamic );
	}

	/**
	 * Checks the mouse if events have been launched
	 * @param juego
	 * @param myClient
	 */
	public static void mousePick(WeSketchGame juego,WeSketchClient myClient) {
		//If any of the flags used to control Frames is true, then can not Pick 
		if( canPick()==false ) return;

		//Get the mouse position
		Vector2f mousePosition = new Vector2f(MouseInput.get().getXAbsolute(), MouseInput.get().getYAbsolute());
		//Create a pick ray from the display
		Ray ray = DisplaySystem.getDisplaySystem().getPickRay(mousePosition, false, new Ray());
		//Find the results
		PickResults results = new TrianglePickResults();
		//PickResults results = new BoundingPickResults();
		//We normally want the distance to see which object is closest
		results.setCheckDistance(true);
		//Get the results from a node
		juego.rootNode.findPick(ray, results);

		//Wheel Button TODO: usability test if this is a good button to do it
//		if ( MouseInput.get().isButtonDown(MIDDLEBUTTON) ) {
//			for (int i = 0; i < results.getNumber(); i++) {
//				try {
//					PickData hit = results.getPickData(i);
//					hit.getTargetMesh().localToWorld(ray.getDirection(), Telepointer.globalVector);
//					//esta era la única línea antes
//					juego.loadTelepointer(ray,myClient.getColor());
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}	
//			return;
//		}
//		else
//		{
//			boolean tpExisted=juego.rootNode.detachChildNamed(Telepointer.TELEPOINTER_PREFIX+myClient.getColor())!=-1;
//			if(tpExisted){
//				myClient.sender.sendHideTelepointer(myClient.getColor());
//			}
//		}

		boolean pickProcessed = false;
		//Loop the results
		for (int i = 0; i < results.getNumber() && pickProcessed==false; i++) {
			PickData hit = results.getPickData(i);
			String mouseTarget=hit.getTargetMesh().getName();

			if ( MouseInput.get().isButtonDown(LEFTBUTTON) ) {
				if( mouseTarget.equals("Pila") ) {
					isManagingSketchesFlag=true;
					juego.control.loadInternalFrame( myClient.newSketch() );
					pickProcessed=true;
					break;
				}
				if( mouseTarget.contains(Sketch3D.SKETCHPREFIX) ) {
					isSketchingFlag=true;
					//TODO arreglar la manera como se saca el ID, ya que es dependiente de la clase Sketch3D
					String sketchId = mouseTarget.substring(mouseTarget.lastIndexOf(' ')+1);
					String sketchId2 = mouseTarget.substring(Sketch3D.SKETCHPREFIX.length());
					System.out.println("Click on sketch "+sketchId+"("+sketchId2+")");
					myClient.sender.addMeToSketch(Long.parseLong(sketchId));
					pickProcessed=true;
					break;
				}
				if( mouseTarget.contains(ColoredAvatar.SIMPLE_AVATAR_PREFIX)){
					pickProcessed=true;
					break;
				}
			}
			//TODO Usability test to see what users expect to happen with Right click
			if ( MouseInput.get().isButtonDown(RIGHTBUTTON) ) {
				if( mouseTarget.contains(ColoredAvatar.SIMPLE_AVATAR_PREFIX) ) {
				}
				if( mouseTarget.contains(Sketch3D.SKETCHPREFIX) ) {
				}
				if( mouseTarget.contains(Mesa.TABLE_PREFIX) ) {
				}
			}
		}
	}

	/**
	 * If any of the flags used to control Frames is true, then can not Pick 
	 * @return
	 */
	private static boolean canPick() {
		return !( isLogingInFlag || isManagingSketchesFlag || isSketchingFlag ||
				isListingSketchesFlag || isManagingRequirementsFlag ||
				isWatchingAnotherFlag || pickAvailable==false);
	}
	/**
	 * Loads an Internalframe in the Desktop
	 * @param frame
	 */
	public static void loadInternalFrame(final JInternalFrame frame) { 
		jmeDesktop.getJDesktop().add(frame);
		frame.setLocation(width/2-frame.getWidth()/2, height/2-frame.getHeight()/2);
		frame.requestFocus();
		//TODO decidir qué eventos del teclado deben bloquearse para que funcionen
		//los eventos y formularios de los frames a mostrar
		//myJMEGame.setEnableInput(false);

		String frameType = frame.getClass().getSimpleName(); 
		if( frameType.equals("SketchingFrame") ){
			setSketchingFlag(true);
		}
		else
			if(frameType.equals("LoginFrame")){
				setLogingInFlag(true);
			}
			else
				if(frameType.equals("NewSketchFrame")){
					setNewSketchFlag(true);
				}
	}
	//--------------------------------------------------------------------------
	public static void setNewSketchFlag(boolean value) {
		isManagingSketchesFlag=value;
	}

	public static void setSketchingFlag(boolean sketching) {
		isSketchingFlag = sketching;
		MainMenuFrame.chatFrame.enableSketchChat(sketching);
	}

	public static void setLogingInFlag(boolean b) {
		isLogingInFlag = b;
	}
	//--------------------------------------------------------------------------
	@Override
	public void dragGestureRecognized(JMEDragGestureEvent dge) {
		//		System.out.print(""+dge.getAction());
		//		System.out.println("     X:"+dge.getPoint().x + "Y:"+dge.getPoint().y);
	}

	@Override
	public void dragDropEnd(JMEDragSourceEvent e) {}

	@Override
	public void dragEnter(JMEDragSourceEvent e) {}

	@Override
	public void dragExit(JMEDragSourceEvent e) {}

	@Override
	public void dragEnter(JMEDropTargetEvent e) {}

	@Override
	public void dragExit(JMEDropTargetEvent e) {}

	@Override
	public void dragOver(JMEDropTargetEvent e) {}

	@Override
	public void drop(JMEDropTargetEvent e) {}
	//--------------------------------------------------------------------------
	/** Makes Conference Controller to add the new participant 
	 * @param p
	 */
	public void udpateConferenceAdd(SessionParticipant p){
		conferenceController.addTarget(p);
	}
	/**
	 * Makes Conference Controller to remove the participant
	 * @param participantIndex
	 */
	public void udpateConferenceRemove(int participantIndex){
		conferenceController.removeTarget(participantIndex);
	}
}
