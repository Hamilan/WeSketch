package ve.environment;

import shared.WeSketchConstants;
import ve.library.RotationModel;
import ve.library.XmlLoaderModels;

import com.jme.bounding.BoundingBox;
import com.jme.math.FastMath;
import com.jme.scene.Node;
import com.jme.scene.Spatial;

public  class Silla {
	private static final String CHAIR_PREFIX = "3DChair ";
	public final static float ORIGIN_RADIUS=Mesa.TABLE_RADIUS*0.92f;
	int posicion=0;
	Avatar avatar=null;
	String name="";
	Node chairModel=null;

	/**
	 * este metodo se encarga de agregar una silla al escenario
	 * @return Nodo silla
	 * @throws Exception 
	 */
	public Silla(int posicion,String name) {
		this.posicion=posicion;
		this.name=name;
		float angle=(float)((posicion*360/WeSketchConstants.MAX_SESSION_PARTICIPANTS)*FastMath.DEG_TO_RAD);

		chairModel = new Node(CHAIR_PREFIX+posicion);
		chairModel.attachChild( getBaseChair() );
		chairModel.setLocalRotation(RotationModel.rotateY(-10+posicion*-60));
		chairModel.setLocalTranslation((float)(ORIGIN_RADIUS*Math.cos(angle)),
				0,
				(float)(ORIGIN_RADIUS*Math.sin(angle)));

		chairModel.setModelBound(new BoundingBox());
		chairModel.updateModelBound();
	}

	/**
	 * Loads and sets a chair that can be translated and rotated around the table
	 * @return
	 */
	private Spatial getBaseChair() {
		Node modelo=null;
		modelo=(Node)XmlLoaderModels.loadModel("silla_sencilla_xml.xml");
		//escalar el modelo en la escena
		modelo.setLocalScale(9f);
		//posicionar y rotar la silla para que queden correctamente en el escenario 
		modelo.setLocalRotation(RotationModel.rotateXYZ(-90, 0, 173));
		modelo.setLocalTranslation(0,0,2.5f);
		return modelo;
	}


	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public Avatar getAvatar() {
		return avatar;
	}

	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	public Node getChairModel() {
		return chairModel;
	}
}
