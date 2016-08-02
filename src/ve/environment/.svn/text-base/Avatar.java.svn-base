package ve.environment;

import shared.WeSketchConstants;
import ve.library.RotationModel;
import ve.library.XmlLoaderModels;

import com.jme.bounding.BoundingBox;
import com.jme.light.PointLight;
import com.jme.math.Vector3f;
import com.jme.scene.BillboardNode;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jme.system.DisplaySystem;

public class Avatar {
	int posicion=0;
	String nombre="";
	Node modelo=null;
	final int MAX_SILLAS=6;
	int [] correccion={255,212,200,194,191,189};

	/**
	 * este metodo se encarga de agregar un avatar al escenario
	 * @return Nodo avatar
	 * @throws Exception 
	 */
	public Avatar(int posicion,String nombre, DisplaySystem display) throws Exception
	{
		if(posicion>0&&posicion<MAX_SILLAS+1) {
			this.posicion=posicion;
			this.nombre=nombre;	    
			modelo=(Node)XmlLoaderModels.loadModel("simple.blend.xml");
			//escalar la imagen en la escena
			//modelo.setLocalScale(1f);
			modelo.setLocalScale(0.8f);

			//TODO arreglar esto para que no use la correción en la conversión de Grados a Radianes, tal vez en otro lado.
			Vector3f position=new Vector3f((float)((Mesa.TABLE_RADIUS-1)*Math.cos(posicion*60*Math.PI/correccion[posicion-1])), 6,(float)((Mesa.TABLE_RADIUS-1)*Math.sin(posicion*60*Math.PI/correccion[posicion-1])));
			// creacion punto de luz para el avatar
			PointLight bombillo = new PointLight();
			bombillo.setDiffuse(WeSketchConstants.RGBACOLOR[posicion-1]);
//			bombillo.setQuadratic(10);
			Vector3f lightPosition=new Vector3f(position.x,0,position.z);
			bombillo.setLocation(lightPosition);
			bombillo.setEnabled( true );

			// agregar el bombillo a la iluminacion y esta al nodo raiz
			LightState iluminacion = display.getRenderer().createLightState();
			iluminacion.setEnabled(true);
			iluminacion.attach(bombillo);
			modelo.setRenderState(iluminacion);
			
			//posicionar y rotar la silla para que queden correctamente en el escenario 
//			modelo.setLocalTranslation(0, 10, 0);
			modelo.setLocalTranslation(position);
			modelo.setLocalRotation(RotationModel.rotateXYZ(-90, 0, -45+(posicion-1)*-60));

			addLabel(modelo,nombre);
			
			//agregar dimensiones en el escenario
			modelo.setModelBound(new BoundingBox());
			modelo.updateModelBound();
//			modelo
		}
		else
		{
			Exception e=new Exception("No es posible añadir el avatar en la silla: "+posicion);
			e.printStackTrace();
		}
	}
	
	private void addLabel(Node modelToLabel, String text){
		TextLabel2D label = new TextLabel2D(text);
		label.setBackground(WeSketchConstants.RGBCOLOR[posicion-1]);
		label.setBlurStrength(0.5f);
		BillboardNode labelNode = label.getBillboard(0.9f,"AvtrLbl-"+text);
		//this tries to reduce coupling with the avatar model. not sure it works.
		labelNode.setLocalScale(1f/modelToLabel.getLocalScale().x);
		labelNode.setLocalTranslation(
				0.5f,//moves label closer/further to/from the table's center
				-0.5f,//moves label to the left/right of avatar's center
				6f);//moves label up/down
		labelNode.setLocalRotation( RotationModel.rotateX(90) );
		modelToLabel.attachChild( labelNode );
	}
	
	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Node getModelo() {
		return modelo;
	}

	public void setModelo(Node modelo) {
		this.modelo = modelo;
	}
	
	@Override
	public String toString() {
		return "POS: "+posicion+" Nombre: "+nombre;
	}

}
