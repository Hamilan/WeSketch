package ve.environment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import shared.WeSketchConstants;
import ve.library.RotationModel;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.BillboardNode;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.shape.RoundedBox;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

/**
 * This class represents the Avatar to show at the table.
 * Gets the color specified by position. Gets the label specified by name 
 * @author Hamilan
 */
public class ColoredAvatar {
	public final static float ORIGIN_RADIUS=Mesa.TABLE_RADIUS*0.84f;
	public static final String SIMPLE_AVATAR_PREFIX = "SIMPLE_AVATAR ";
	String name;

	private Node avModel;
	private Node leftArm;
	private Node rightArm;
	private Node leftForearm;
	private Node rightForearm;
	private Node headNode;
	private int myColor;
	private int myPosition;

	public ColoredAvatar(String name, int position) {
		this.name = name;
		this.myColor = position;
		this.myPosition = position;
		setModel(name);
		locateAtChair(position);
		avModel.setLocalScale(0.7f);
	}
	public Spatial getModel() {
		return avModel;
	}
	/**
	 * translates and orients this avatar to the specified angle
	 * @param angle must be a value between 0-1
	 */
	public void locateAtChair(int pos) {
		this.myPosition = pos;
		this.myColor = pos;
		float angle = pos/WeSketchConstants.MAX_SESSION_PARTICIPANTSF;
		float angleRad=angle*360*FastMath.DEG_TO_RAD;
		avModel.setLocalTranslation(FastMath.cos(angleRad)*ORIGIN_RADIUS,
				9,
				FastMath.sin(angleRad)*ORIGIN_RADIUS);
		avModel.setLocalRotation(RotationModel.rotateY( -angle*360-90 ));
		System.out.println("Located coloured avatar "+pos);
	}
	private void setModel(String name) {
		avModel = new Node(SIMPLE_AVATAR_PREFIX+myPosition);
		//Head
		headNode = new Node();
		Vector3f extent = new Vector3f(1f, 1f, 0.2f);
		Vector3f borders = new Vector3f(0.2f, 0.2f, 0.05f);
		Vector3f slope = new Vector3f(0.1F, 0.1F, 0.1F);
		RoundedBox head = new RoundedBox("rounded box", extent, borders, slope);
		head.setLocalTranslation(0,1f,0);
		headNode.attachChild(head);
		avModel.attachChild(headNode);
		//Body
		Vector3f extent2 = new Vector3f(1.2f, 2f, 0.2f);
		Vector3f borders2 = new Vector3f(0.2f, 0.2f, 0.05f);
		Vector3f slope2 = new Vector3f(0.1F, 0.1F, 0.1F);
		RoundedBox body = new RoundedBox("rounded box2", extent2, borders2, slope2);
		body.setLocalTranslation(0,-2f,0);
		avModel.attachChild(body);

		//Arms
		Vector3f extent3 = new Vector3f(0.3f, 1f, 0.2f);
		Vector3f borders3 = new Vector3f(0.05f, 0.2f, 0.05f);
		Vector3f slope3 = new Vector3f(0.1F, 0.1F, 0.1F);

		leftArm= new Node();
		RoundedBox leftBicep = new RoundedBox("Arm1", extent3, borders3, slope3);
		leftBicep.setLocalTranslation(1.3f,-1,0);
		leftArm.attachChild(leftBicep);
		avModel.attachChild(leftArm);

		rightArm= new Node();
		RoundedBox rightBicep = new RoundedBox("Arm2", extent3, borders3, slope3);
		rightBicep.setLocalTranslation(-1.3f,-1,0);
		rightArm.attachChild(rightBicep);
		avModel.attachChild(rightArm);

		//ForeArms
		Vector3f extent4 = new Vector3f(0.2f, 1f, 0.25f);
		Vector3f borders4 = new Vector3f(0.05f, 0.2f, 0.05f);
		Vector3f slope4 = new Vector3f(0.1F, 0.1F, 0.1F);

		leftForearm = new Node(); 
		RoundedBox forearm1 = new RoundedBox("ForeArm1", extent4, borders4, slope4);
		forearm1.setLocalTranslation(0f,-1f,0f);
		leftForearm.attachChild(forearm1);
		leftForearm.setLocalTranslation(1.3f,-2,0);
		leftArm.attachChild(leftForearm);

		rightForearm = new Node();
		RoundedBox forearm2 = new RoundedBox("ForeArm2", extent4, borders4, slope4);
		forearm2.setLocalTranslation(0f,-1f,0f);
		rightForearm.attachChild(forearm2);
		rightForearm.setLocalTranslation(-1.3f,-2,0);
		rightArm.attachChild(rightForearm);

		setHeadTexture(head);
		setBodyTexture(body,leftBicep,rightBicep);

		avModel.attachChild(getLabel(avModel,name));
		avModel.setModelBound(new BoundingBox());
		avModel.updateModelBound();
	}
	private void setBodyTexture(RoundedBox body, RoundedBox leftBicep, RoundedBox rightBicep) {
		int w=64,h=64;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(WeSketchConstants.RGBCOLOR[myColor]);
		g.fillRect(0, 0, w, h);

		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setEnabled(true);
		Texture t1 = TextureManager.loadTexture(image,
				Texture.MinificationFilter.BilinearNearestMipMap,
				Texture.MagnificationFilter.Bilinear,false);
		ts.setTexture(t1);
		body.setRenderState(ts);
		body.updateRenderState();
		leftBicep.setRenderState(ts);
		leftBicep.updateRenderState();
		rightBicep.setRenderState(ts);
		rightBicep.updateRenderState();
	}

	private void setHeadTexture(RoundedBox head) {
		int w=128,h=128;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(WeSketchConstants.RGBCOLOR[myColor]);
		g.drawOval(38, 20, 25, 30);	//ojo
		g.fillOval(43, 30, 14, 20);	//ojo
		g.drawOval(68, 20, 25, 30);	//ojo
		g.fillOval(73, 30, 14, 20);	//ojo
		g.drawArc(20, 30, 90, 80, 145, 250);

		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		ts.setEnabled(true);
		Texture t1 = TextureManager.loadTexture(image,
				Texture.MinificationFilter.BilinearNearestMipMap,
				Texture.MagnificationFilter.Bilinear,true);
		//t1.setWrap(Texture.WrapMode.Clamp);//WM_WRAP_S_WRAP_T);
		ts.setTexture(t1);
		head.setRenderState(ts);
		head.updateRenderState();
	}
	/** working on my task */
	public void setEditingPosture() {
		leftArm.setLocalRotation(RotationModel.rotateX(-30));
		rightArm.setLocalRotation(RotationModel.rotateX(-30));
		leftForearm.setLocalRotation(RotationModel.rotateXYZ(0,45,-45));
		rightForearm.setLocalRotation(RotationModel.rotateXYZ(0,-45,45));
		headNode.setLocalRotation(RotationModel.rotateX(30));
	}
	/** looking at other in position position
	 * @param position avatar's position to look at
	 */
	public void setEditingPostureWith(int position) {
		float angle = position-myPosition;
		if(angle<0) angle+=WeSketchConstants.MAX_SESSION_PARTICIPANTSF;
		angle = 90-angle*180/WeSketchConstants.MAX_SESSION_PARTICIPANTSF;

		leftArm.setLocalRotation(RotationModel.rotateX(-30));
		rightArm.setLocalRotation(RotationModel.rotateX(-30));
		leftForearm.setLocalRotation(RotationModel.rotateXYZ(0,45,-45));
		rightForearm.setLocalRotation(RotationModel.rotateXYZ(0,-45,45));
		headNode.setLocalRotation(RotationModel.rotateY(angle));
	}
	/** hands behind the neck */
	public void setStillPostureC() {
		leftArm.setLocalRotation(RotationModel.rotateXYZ(165,0,30));
		rightArm.setLocalRotation(RotationModel.rotateXYZ(155,0,-30));
		leftForearm.setLocalRotation(RotationModel.rotateZ(-110));
		rightForearm.setLocalRotation(RotationModel.rotateZ(110));
		headNode.setLocalRotation(RotationModel.rotateXYZ(0,0,0));
	}
	/** hands behind the back */
	public void setStillPostureB() {
		leftArm.setLocalRotation(RotationModel.rotateX(25));
		rightArm.setLocalRotation(RotationModel.rotateX(15));
		leftForearm.setLocalRotation(RotationModel.rotateZ(-90));
		rightForearm.setLocalRotation(RotationModel.rotateZ(90));
		headNode.setLocalRotation(RotationModel.rotateXYZ(0,0,0));
	}
	/** crossed-arms */
	public void setStillPostureA() {
		leftArm.setLocalRotation(RotationModel.rotateX(-30));
		rightArm.setLocalRotation(RotationModel.rotateX(-20));
		leftForearm.setLocalRotation(RotationModel.rotateZ(-90));
		rightForearm.setLocalRotation(RotationModel.rotateZ(90));
		headNode.setLocalRotation(RotationModel.rotateXYZ(0,0,0));
	}
	private BillboardNode getLabel(Node modelToLabel, String text){
		float labelSize = 0.07f;
		TextLabel2D label = new TextLabel2D(text);
		//label.setBackground(CVEConstants.RGBCOLOR[myColor]);
		label.setBlurStrength(1f);
		BillboardNode quadLabel = label.getBillboard(labelSize,SIMPLE_AVATAR_PREFIX+"Label"+myPosition);
		quadLabel.setLocalScale(10f/modelToLabel.getLocalScale().x);
		quadLabel.setLocalTranslation(0f,2.7f,0f);
		return quadLabel;
	}
}
