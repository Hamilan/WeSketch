package ve;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import networking.WeSketchClient;
import shared.WeSketchConstants;
import shared.SessionParticipant;
import shared.Telepointer;
import sketching.Sketch;
import ve.environment.ColoredAvatar;
import ve.environment.DesignRoom;
import ve.environment.Mesa;
import ve.environment.Silla;
import ve.environment.Sketch3D;
import ve.environment.TextLabel2D;

import com.jme.app.BaseGame;
import com.jme.bounding.BoundingBox;
import com.jme.input.FirstPersonHandler;
import com.jme.input.InputHandler;
import com.jme.input.KeyBindingManager;
import com.jme.input.KeyInput;
import com.jme.input.MouseInput;
import com.jme.light.PointLight;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.renderer.ColorRGBA;
import com.jme.scene.BillboardNode;
import com.jme.scene.Line;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.Box;
import com.jme.scene.state.LightState;
import com.jme.scene.state.ZBufferState;
import com.jme.system.DisplaySystem;
import com.jme.util.Timer;

public class WeSketchGame extends BaseGame{
	public static final String AVATARPREFIX = "3DAvatar ";

	private static WeSketchClient myClient;

	private static ArrayList<Sketch3D> sketches3D = new ArrayList<Sketch3D>();
	private DesignRoom miCuarto=null;
	private static Camera cam;
	private Timer timer;
	private boolean flag = false;
	private int width;
	private int height;
	private int depht;
	private int freq;
	private boolean fullscreen;
	public static boolean changedTitle;
	public static String title=WeSketchConstants.APPLICATION_NAME+" - Inicio de Sesión";

	public static Vector<Silla> sillas=null;
	public static ColoredAvatar avatars[]=new ColoredAvatar[WeSketchConstants.MAX_SESSION_PARTICIPANTS];
	public static Node rootNode;
	public static Node mesa=null;
	public static InputHandler input;
	public static Control control;
	public static Telepointer telepointer; 
	public static DisplaySystem ds;

	private static int called=0;

	public WeSketchGame( WeSketchClient cliente ) {
		super();
		myClient = cliente;
		//TODO mostrar la configuracion de JME para iniciar esta línea se removerá
		//en la versión final ya que la idea es crear la ventana del tamaño máximo permitido en el display actual
		//setConfigShowMode(ConfigShowMode.AlwaysShow);
		//iniciar la aplicacion cargando el metodo simpleInitGame
		start();
	}
	/**
	 * metodo que inicializa el sistema antes de iniciar el juego
	 */
	protected void initSystem() {
		//set the display
		{	
			//TODO capturar el tamaño máximo de la pantalla y configurarlo aquí
			//TODO durante la etapa de diseño de la ventana se usará 1024x768 pero 
			//la idea es crear la ventana del tamaño máximo permitido en la pantalla actual
			//			width = this.settings.getWidth();
			//			height = this.settings.getHeight();
			width = 1200;
			height = 720;

			depht = this.settings.getDepth();
			freq = this.settings.getFrequency();
			fullscreen = this.settings.isFullscreen();

			control=new Control(width,height,myClient);

			display = DisplaySystem.getDisplaySystem(this.getNewSettings().getRenderer());
			display.createWindow(width, height, depht, freq, fullscreen);
			display.setTitle(title);


			//Antialias 4X, reducir a 2 en caso de bajo rendimiento. Hamilton.
			display.setMinSamples(4);
			ds = display;

			cam = display.getRenderer().createCamera(width, height);
			cam.setFrustumPerspective(45.0f, (float) display.getWidth()/(float) display.getHeight(),1f, 1000);

			// Assign the camera to this renderer
			display.getRenderer().setCamera(cam);
			// fondo blanco de la aplicacion final
			display.getRenderer().setBackgroundColor(ColorRGBA.white);
		}
		//-----------------------------------------------------------------------
		//set input (mouse and keyboard)
		{
			// creacion del manejador en primera persona asignandole la camara de la escena
			// y activando la manipulacion mediante el mouse pero no mediante el teclado
			input = new FirstPersonHandler(cam, 0, 1); //input = new InputHandler();
			// requerir que se presione el click izquierdo para poder manipular la vista en
			// primera persona con ello se crea un mayor control del avatar
			((FirstPersonHandler) input).setButtonPressRequired(true);
			((FirstPersonHandler)input).getMouseLookHandler().setEnabled(true);
			((FirstPersonHandler)input).getKeyboardLookHandler().setEnabled(false);
			((FirstPersonHandler)input).getMouseLookHandler().requireButtonPress(true);
			((FirstPersonHandler)input).getMouseLookHandler().getMouseLook().setMouseButtonForRequired(1);

			timer = Timer.getTimer();
			setHotKeysFor3D();
		}
	}

	/**
	 * This method sets the hotkeys to use in the 3D environment. 
	 */
	private void setHotKeysFor3D() {
		Toolkit.getDefaultToolkit().getSystemEventQueue().push(
				new EventQueue(){
					protected void dispatchEvent(AWTEvent event) {
						if (event instanceof KeyEvent) {
							KeyEvent keyEvent = (KeyEvent) event;
							if (keyEvent.getID() == KeyEvent.KEY_TYPED){
								System.out.println(keyEvent.getKeyChar());
							}
							System.out.println(keyEvent.getKeyChar());
						}
						super.dispatchEvent(event);
					}
				});

		//Some standard functionalities of jme games
		//TODO Salir debería ser con Alt+F4
		KeyBindingManager.getKeyBindingManager().set("ApplicationExit", KeyInput.KEY_ESCAPE);
		KeyBindingManager.getKeyBindingManager().set( "screen_shot", KeyInput.KEY_F12 );

		//Functionalities of the CVE for Sketching
		KeyBindingManager.getKeyBindingManager().set("CVEHelp", KeyInput.KEY_F1);
		KeyBindingManager.getKeyBindingManager().set("CVESoftwareInfo", KeyInput.KEY_F2);
		KeyBindingManager.getKeyBindingManager().set("CVESearch", KeyInput.KEY_F3);
		KeyBindingManager.getKeyBindingManager().set("CVESessionInfo", KeyInput.KEY_F4);
		KeyBindingManager.getKeyBindingManager().set("CVESketchesPreview", KeyInput.KEY_F5);
		KeyBindingManager.getKeyBindingManager().set("CVENewSketch", KeyInput.KEY_F6);
		KeyBindingManager.getKeyBindingManager().set("CVEManageReq", KeyInput.KEY_F7);
		KeyBindingManager.getKeyBindingManager().set("CVEManageUser", KeyInput.KEY_F8);

		//Ejemplo de definición de Acción con 2 teclas a la vez
		KeyBindingManager.getKeyBindingManager().set("TestHamilan", new int[]{KeyInput.KEY_LCONTROL,KeyInput.KEY_H});
		//KeyBindingManager.getKeyBindingManager().set("SetControlKey", KeyInput.KEY_LCONTROL);
		//Una tecla para centrar la cámara al frente de mi visión
		//Una tecla para ver el escenario desde arriba
	}

	/**
	 * este metodo es el metodo que inicia todo el juego, es el segundo cargado despues del metodo main
	 * este metodo corre por defecto una vez inicializada una aplicacion que extiende de 
	 * la clase SimpleGame
	 */	
	protected void initGame() {
		// inicializacion del nodo raiz de la escena
		rootNode = new Node("root");

		ZBufferState buf = display.getRenderer().createZBufferState();
		buf.setEnabled( true );
		buf.setFunction( ZBufferState.TestFunction.LessThanOrEqualTo );
		rootNode.setRenderState( buf );

		// configura iluminación, escenario (paredes, techo, piso) y mesa
		setStaticSceneObjects();

		// inicializacion de las sillas
		sillas=new Vector<Silla>();
		loadChairs(sillas,"chair");

		// hacer el cursor visible
		MouseInput.get().setCursorVisible(true);

		// reset camera top view
		ve.library.CameraManager.resetCamera(cam);

		// actualizar el nodo raiz
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0.0f, true);

		control.createDesktop(rootNode, input);

		//setCursorImage(1,0);

		myClient.showLoginFrame();
	}
	public static void setCursorImage(int color,int type) {
		URL file = WeSketchGame.class.getClassLoader().getResource("sketching/cursors/"+WeSketchConstants.COLORNAME[color]+"/"+WeSketchConstants.CURSORFILE[type]+".png");
		MouseInput.get().setHardwareCursor(file);
	}
	/**
	 * Este método configura el contenido de la escena que no cambiará durante la
	 * utilización de la aplicación, a saber: paredes, techo, suelo, iluminación y
	 * mesa redonda en el centro de la habitación
	 */
	@SuppressWarnings("static-access")
	private void setStaticSceneObjects()
	{
		// creacion punto de luz para la escena
		PointLight bombillo = new PointLight();
		bombillo.setDiffuse( new ColorRGBA( 0.75f, 0.75f, 0.75f, 0.75f ) );
		bombillo.setLocation( new Vector3f( 0,50,0) );
		bombillo.setEnabled( true );

		// agregar el bombillo a la iluminacion y esta al nodo raiz
		LightState iluminacion = display.getRenderer().createLightState();
		iluminacion.setEnabled(true);
		iluminacion.attach(bombillo);
		rootNode.setRenderState(iluminacion);

		miCuarto=new DesignRoom();
		miCuarto.setLocalTranslation(0,miCuarto.getTamaY(),0);
		rootNode.attachChild(miCuarto);	  
		rootNode.updateRenderState();

		// cargar mesa en la escena
		mesa=Mesa.cargarMesa();
		rootNode.attachChild(mesa);

		//object that simulates a pile of sheets
		createWhiteSheetsPile();
	}

	private void createWhiteSheetsPile() {
		Vector3f MIN_POINT = new Vector3f(-0.5f,7.3f,-0.7f),
		MAX_POINT = new Vector3f(0.5f,7.5f,0.7f);
		Box pile = new Box("Pila", MIN_POINT, MAX_POINT);
		pile.setModelBound(new BoundingBox());
		pile.updateModelBound();
		rootNode.attachChild(pile);

		TextLabel2D label = new TextLabel2D("Nuevo bosquejo");
		label.setBlurStrength(1f);
		BillboardNode quadLabel = label.getBillboard(0.25f,"LabelPila");
		quadLabel.setLocalTranslation(0,7.7f,0);
		rootNode.attachChild(quadLabel);
	}

	/**
	 * este metodo agrega las sillas vacias en la escena respectiva
	 */
	private void loadChairs(Vector<Silla> sillas2,String name) {
		try  {
			Silla silla=null;
			for(int i=0;i<WeSketchConstants.MAX_SESSION_PARTICIPANTS;i++){
				silla=new Silla(i,name);
				sillas.add(silla);
				rootNode.attachChild(silla.getChairModel());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void cleanup() {
	}

	@Override
	protected void reinit() {
	}

	@Override
	protected void render(float arg0) {
		display.getRenderer().clearBuffers();
		try{
			display.getRenderer().draw(rootNode);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void update(float notUsedArgument) {
		if(changedTitle){
			changedTitle=false;
			display.setTitle(title);
		}

		// actualizar el tiempo
		timer.update();
		// Update tpf to time per frame according to the Timer.
		float tpf = timer.getTimePerFrame();
		// Check for key/mouse updates.
		input.update(tpf);

		// checks if mouse is being used to pick virtual objects
		control.mousePick( this, myClient );
		checkKeyboard();

		//actualizar el nodo raiz respecto a los cambios
		rootNode.updateRenderState();
		rootNode.updateGeometricState(0.0f, true);
		//TODO animar sketch
	}

	private void checkKeyboard() {
		// finalizar la aplicacion con la tecla escape
		if ( KeyBindingManager.getKeyBindingManager().isValidCommand( "screen_shot", false ) ) {
			display.getRenderer().takeScreenShot( "SimpleGameScreenShot" );
		}
		/** programacion eventos de teclado */
		if (!flag) {
			if (KeyBindingManager.getKeyBindingManager().isValidCommand("TestHamilan",false))
			{System.out.println("TestHamilan");flag=true;}

			if (KeyBindingManager.getKeyBindingManager().isValidCommand("CVENewSketch",false)) {
				flag=true;
				control.loadInternalFrame(myClient.newSketch());
			}
			if (KeyBindingManager.getKeyBindingManager().isValidCommand("ApplicationExit",false)) {
				flag=true;
				//if( JOptionPane.showConfirmDialog(null, "¿En realidad desea abandonar la Sesión?") == JOptionPane.YES_OPTION){
				finished=true;
				System.exit(0);
				//}
			}
			if (KeyBindingManager.getKeyBindingManager().isValidCommand("newChatMessage",false)){
				//TODO cambiar por darle foco a la ventana de chat global
				String msg=JOptionPane.showInputDialog("Ingrese mensaje chat:","mensaje");
				myClient.sender.sendGlobalChat( msg );
			}
			//These lines just for testing
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("avaUno",false))
			//			{updateAvatar(1,"david");flag=true;}
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("avaDos",false))
			//			{updateAvatar(2,"Jesse");flag=true;}
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("avaTres",false))
			//			{updateAvatar(3,"Hamilan");flag=true;}
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("avaCuatro",false))
			//			{updateAvatar(4,"Stephanie");flag=true;}
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("avaCinco",false))
			//			{updateAvatar(5,"Helmuth");flag=true;}
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("avaSeis",false))
			//			{updateAvatar(6,"Marilyn");flag=true;}
			//			if (KeyBindingManager.getKeyBindingManager().isValidCommand("nuevoAvatar",false))
			//			{control.iniciarFrame(rootNode,this,input,cam);flag=true;}

			flag=false;
		}
	}

	//	//This method is just for testing, should be removed
	//		public static void updateAvatar(int position,String nombre) {
	//			// se valida la condicion de que el avatar no se encuentre ya sobre la silla
	//			System.out.println("Updating Avatar "+position+" ("+nombre+")");
	//			if(sillas.get(position-1).getAvatar()==null)
	//			{
	//				try {
	//					// se crea le asigna el avatar a la silla respectiva
	//					sillas.get(position-1).setAvatar(new Avatar(position,nombre));
	//					// se crea un Spatial para asignar al nodo raiz
	//					Spatial sil=sillas.get(position-1).getAvatar().getModelo();
	//					// se le da un nombre al Spatial para manipular posteriormente su eliminacion mas facil
	//					sil.setName(AVATARPREFIX+position);
	//					// se agrega el spatial al nodo raiz
	//					rootNode.attachChild(sil);
	//					System.out.println("Agregado avatar "+nombre+" en "+position);
	//				} catch (Exception e) {
	//					System.out.println("no se pudo agregar el avatar en la position: "+position);
	//					e.printStackTrace();
	//				}
	//			}
	//			else
	//			{
	//				// se desasigna el avatar de la silla
	//				sillas.get(position-1).setAvatar(null);
	//				// se elimina el avatar del nodo raiz mediante su nombre especifico
	//				rootNode.detachChildNamed(AVATARPREFIX+position);
	//			}
	//		}

	public static void addAvatar(int position, String login) {
		try {
			// se crea le asigna el avatar a la silla respectiva
			//sillas.get(position).setAvatar(new Avatar(position+1,login,DisplaySystem.getDisplaySystem()));
			// se crea un Spatial para asignar al nodo raiz
			//Spatial sil=sillas.get(position).getAvatar().getModelo();
			// se le da un nombre al Spatial para manipular posteriormente su eliminacion mas facil
			//sil.setName(AVATARPREFIX+position);
			// se agrega el spatial al nodo raiz
			//rootNode.attachChild(sil);

			ColoredAvatar av=new ColoredAvatar(login,position);
			avatars[position]=av;
			rootNode.attachChild(av.getModel());
			av.setStillPostureA();
		} catch (Exception e) {
			System.out.println("no se pudo agregar el avatar en la posicion: "+position);
			e.printStackTrace();
		}
	}

	public static void removeAvatar( int position ) {
		// se desasigna el avatar de la silla
		//sillas.get(position).setAvatar(null);
		// se elimina el avatar del nodo raiz mediante su nombre especifico
		System.out.println("removed avatar "+AVATARPREFIX+position);
		//rootNode.detachChildNamed(AVATARPREFIX+position);
		rootNode.detachChildNamed(ColoredAvatar.SIMPLE_AVATAR_PREFIX+position);
	}
	/**
	 * This method updates the view of the existing sketches.
	 * The idea is to follow the same principle of the chairs, but allowing lots of sketches
	 * @param sketches 
	 * @param sk
	 * @param pos
	 */
	public static void show3DSketches(ArrayList<Sketch> sketches) {
		//TODO remover primero cualquier Sketch que se esté mostrando en el escenario
		for (Sketch3D sk : sketches3D) {
			System.out.println("Removed 3DSketch from table");
			rootNode.detachChildNamed(sk.getModelo().getName());
		}
		sketches3D = new ArrayList<Sketch3D>();

		if(sketches.size()==0) return;
		try {
			int angle = 360/sketches.size();
			for(int i=0;i<sketches.size();i++){
				Sketch3D sketch=null;
				Sketch s = sketches.get(i);
				if(s.collaborators.size()==0){
					sketch=new Sketch3D(i,s,angle);
				}else{
					int ownerIndex = s.collaborators.get(0).chairIndexOrColor;
					sketch=new Sketch3D(ownerIndex, s );
					avatars[ownerIndex].setEditingPosture();
					for (int j = 1; j < s.collaborators.size(); j++) {
						avatars[ s.collaborators.get(j).chairIndexOrColor ].setEditingPostureWith( ownerIndex );
						System.out.println("("+called+")for: SetEditingPostureWith "+ownerIndex);
					}
				}
				// se almacenan los sketches en el conjunto de sillas
				sketches3D.add(sketch);
				// se agregan los sketches al nodo raiz
				rootNode.attachChild(sketch.getModelo());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public static void update3DSketches(ArrayList<Sketch> sketchesToUpdate){
		int angle;
		if(sketchesToUpdate.size()>0){
			angle = 360/sketchesToUpdate.size();
		}
		else{
			System.out.println("There are "+sketchesToUpdate.size()+" sketches to update");
			angle = 0;
		}
		int position = 0;
		for (int i = sketches3D.size()-1; i >= 0; i--) {
			Sketch3D sk3D =  sketches3D.get(i);
			//checks if the sketch still exists
			boolean stillExists = false;
			Sketch skData=null;
			String name = sk3D.getModelo().getName();
			System.out.println("Checking existence of "+name+ "with:" );
			for (Sketch sk : sketchesToUpdate) {
				System.out.println("'"+Sketch3D.SKETCHPREFIX+sk.id+"'");
				if(name.equals( Sketch3D.SKETCHPREFIX+sk.id )){
					skData = sk;
					stillExists=true;
					break;
				}
			}
			sketchesToUpdate.remove(skData);
			if( stillExists ){
				System.out.println("UPdates texture, and relocates sk3D");
				sk3D.setTexture( skData );
				locateSketch3D(sk3D,skData,position,angle);
			}else{
				System.out.println("Deleting SK3D cause no longer exists");
				rootNode.detachChildNamed(sk3D.getModelo().getName());
				sketches3D.remove(i);
			}
			position++;
		}
		//remaining sketches in the ArrayList are the new ones
		System.out.println("Creating "+sketchesToUpdate.size()+" new Sk3D");
		for (Sketch sketch : sketchesToUpdate) {
			Sketch3D sk3D =  new Sketch3D(sketch);
			locateSketch3D(sk3D,sketch,position,angle);
			rootNode.attachChild(sk3D.getModelo());
			sketches3D.add(sk3D);
			position++;
		}
		rootNode.updateRenderState();
	}

	private static void locateSketch3D(Sketch3D sk3D, Sketch skData,
			int position, int angle) {
		called++;
		System.out.println("called "+called);
		if(skData.collaborators.size()==0){
			sk3D.locateUnassigned(position, angle);
		}else{
			int ownerIndex = skData.collaborators.get(0).chairIndexOrColor;
			avatars[ownerIndex].setEditingPosture();
			System.out.println("("+called+") SetEditingPosture");
			sk3D.locateAssignedTo( ownerIndex );
			for (int j = 1; j < skData.collaborators.size(); j++) {
				avatars[ skData.collaborators.get(j).chairIndexOrColor ].setEditingPostureWith( ownerIndex );
				System.out.println("("+called+")for: SetEditingPostureWith "+ownerIndex);
			}
		}

	}
	public static void setViewToParticipant(int userPos) {
		ve.library.CameraManager.setupAvatarCamera(userPos,mesa,cam);
	}
	/**
	 * Shows that a participant has started sketching.
	 * @param s
	 * @param index
	 */
	public static void addSketchToParticipant(Sketch s, int index ) {
		Sketch3D sketch = new Sketch3D( index , s );
		sketches3D.add(sketch);
		rootNode.attachChild(sketch.getModelo());
		if(s.collaborators.size()==1){
			avatars[index].setEditingPosture();
		}else{
			avatars[index].setEditingPostureWith(s.collaborators.get(0).chairIndexOrColor);
			drawAvatarLookingAt(index);
		}
	}
	/**
	 * Method to test that an avatar is watching at another's work
	 * Draw a telepointer
	 * @param index
	 */
	private static void drawAvatarLookingAt(int index) {

	}
	/**
	 * FALTA EL MÉTODO PARA ACTUALIZAR EL AVATAR CUANDO SE QUITE DE UN SKETCH
	 * @param var
	 */


	//	public static void assignSketchToParticipant(Sketch s, int index ) {
	//TODO mostrar que un Participante ha empezado a trabajar en un Sketch
	//Sketch3D sketch = new Sketch3D( index , s );
	//sketches3D.add(sketch);
	//rootNode.attachChild(sketch.getModelo());
	//	}

	public static void setEnableInput(boolean var) {
		//modificar metodo para que detenga la position de la camara al presionar el mouse
	}
	/**
	 * This method is called only when a Telepointer has been received by the Client
	 */
	public static void updateTelepointers(Telepointer tp) {
		tp.loadVectors();
		System.out.println("TELEPOINTER RECIBIDO "+tp.toString());
		Vector3f[] points = new Vector3f[2];
		points[0]=tp.getOrigin();
		points[1]=tp.getDirection();


		points[1]=tp.getDirection();
		Line linea = new Line("linea"+tp.color,points,null,null,null);

		//		linea.setLocalTranslation(avatars[myClient.getColor()].getModel().getLocalTranslation());
		linea.setLocalRotation(avatars[myClient.getColor()].getModel().getLocalRotation());
		linea.setLineWidth(5);
		linea.setLightCombineMode(Spatial.LightCombineMode.Off);
		linea.setDefaultColor(WeSketchConstants.RGBACOLOR[tp.color]);

		try {
			//			rootNode.detachChildNamed("linea"+tp.color);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			rootNode.attachChild(linea);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		rootNode.detachChildNamed("telepointer"+tp.color); //in case it existed
		//		System.out.println("Paint telepointer "+tp.color);
		//		rootNode.attachChild(tp.getLine());

	}
	/**
	 * This method is called from Control when this user fired the Telepointer
	 * @param results
	 * @param avatarColor
	 * @throws Exception 
	 */
	public static void loadTelepointer(Ray results,int avatarColor) throws Exception {
		final String METHOD_NAME="CVEJMEGame - loadTelepointer: ";
		if(telepointer==null) {
			telepointer = new Telepointer(results,avatarColor);
			try {
				rootNode.detachChildNamed(Telepointer.TELEPOINTER_PREFIX+avatarColor);
			} catch (Exception e) {
				throw new Exception(METHOD_NAME+"NO SE ELIMINO EL TELEPOINTER EN EL INTENTO");
			}
			try {
				rootNode.attachChild(telepointer.getLine());
				System.out.println("TELEPOINTER ENVIADO "+telepointer.toString());
				myClient.sender.sendTelepointer(telepointer);
			} catch (Exception e) {
				throw new Exception(METHOD_NAME+"NO SE AGREGO EL TELEPOINTER EN EL INTENTO");
			}
		}
		else {
			rootNode.detachChildNamed(Telepointer.TELEPOINTER_PREFIX+telepointer.color);
			telepointer.updateTelepointer(telepointer,results);
			System.out.println("TELEPOINTER ENVIADO "+telepointer.toString());
			rootNode.attachChild(telepointer.getLine());
			myClient.sender.sendTelepointer(telepointer);
		}
	}
	public void removeTelepointer(int pIndex) {
		System.out.println("Remove telepointer "+pIndex);
		rootNode.detachChildNamed(Telepointer.TELEPOINTER_PREFIX+pIndex);
	}


	public void activateKeyboardLookHandler(boolean value){
		if(!value){
			System.out.println("*****************\nDESACTIVADO TECLADO\n*****************");
		}
		((FirstPersonHandler)input).getKeyboardLookHandler().setEnabled(value);
	}

	public static void addParticipant(SessionParticipant p) {
		addAvatar(p.chairIndexOrColor, p.login);
		control.udpateConferenceAdd(p);
	}
	public static void removeParticipant(int partPos) {
		removeAvatar(partPos);
		control.udpateConferenceRemove(partPos);
		rootNode.detachChildNamed(Telepointer.TELEPOINTER_PREFIX+partPos);
	}
	public static void setTitle(String newTitle) {
		changedTitle=true;
		title=newTitle;
	}
	public static void removeParticipantFromSketch(int parId) {
		avatars[parId].setStillPostureC();
	}
	public static void addParticipantToSketch(int participantId, long sketchId) {
		//TODO mostrar que el avatar empieza a ver al participante que es dueño del sketch o al sketch
		//orientar la cabeza hacia el sketch
		avatars[participantId].setEditingPosture();
	}
	/** removes every avatar around the table */
	public static void resetAvatars() {
		if(avatars!=null){
			for (int i = 0; i < WeSketchConstants.MAX_SESSION_PARTICIPANTS; i++) {
				rootNode.detachChildNamed(ColoredAvatar.SIMPLE_AVATAR_PREFIX+i);
			}
		}
	}
	public static void setAvatarIdle(int chairIndexOrColor) {
		avatars[chairIndexOrColor].setStillPostureC();
	}
	@Override
	protected void quit() {
		super.quit();
		System.exit(0);
	}
}
