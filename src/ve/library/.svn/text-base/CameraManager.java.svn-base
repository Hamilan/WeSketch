package ve.library;

import shared.WeSketchConstants;
import ve.environment.Mesa;

import com.jme.input.ChaseCamera;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Camera;
import com.jme.scene.Node;

/**
 * esta clase nos permite administrar de manera comoda la camara en el escenario
 * @author David Cadena
 * @author Hamilton Hernández revision and improvements
 * @version 1
 */
public class CameraManager {
	
	//vector de angulos para posicionar mejor la camara sobre los avatares
//	private static final double[] angulos={47,53,55,56,57,58};

	/** distance from center ofthe table to camera location */
	public final static float ORIGIN_RADIUS=Mesa.TABLE_RADIUS*0.83f;	
	public final static float ORIGIN_TOP=9.5f;

	/**
	 * metodo que permite configurar una camara a partir de las coordenadas donde se desea este esta
	 * @param xPosition
	 * @param yPosition
	 * @param zPosition
	 * @return vector3f
	 */
	static public Vector3f setupUserCamera(float xPosition,float yPosition,float zPosition) 
	{
		return new Vector3f(xPosition,yPosition,zPosition);
	}
	
	/**
	 * metodo que permite configurar una camara a partir de las coordenadas donde se desea este esta
	 * y a su vez especificando la camara misma a modificar 
	 * @param xPosition
	 * @param yPosition
	 * @param zPosition
	 * @param camara
	 */
	static public void setupUserCamera(float xPosition,float yPosition,float zPosition,Camera camara) 
	{
		camara.setLocation(new Vector3f(xPosition,yPosition,zPosition));
	}
	
	/**
	 * metodo que permite configurar una camara a partir de un determinado nodo
	 * y a su vez especificando la camara misma a modificar
	 * @param nodo
	 * @param camara
	 */
	static public void setupAvatarCamera(int userPos,Node nodoObjetivo,Camera camara) 
	{
		if(nodoObjetivo!=null) {
			float angle = userPos/WeSketchConstants.MAX_SESSION_PARTICIPANTSF;
			float angleRad=angle*360*FastMath.DEG_TO_RAD;
			camara.setLocation(new Vector3f(FastMath.cos(angleRad)*ORIGIN_RADIUS,
					ORIGIN_TOP,
					FastMath.sin(angleRad)*ORIGIN_RADIUS));
			
			//posicion camara sobre el nodo
//			camara.setLocation(new Vector3f(
//					(float)((Mesa.TABLE_RADIUS-2)*Math.cos(sillaPosicion*angulos[sillaPosicion]*FastMath.DEG_TO_RAD)),
//					(float)10,
//					(float)((Mesa.TABLE_RADIUS-2)*Math.sin(sillaPosicion*angulos[sillaPosicion]*FastMath.DEG_TO_RAD))));
			//foco de la camara
			camara.lookAt(new Vector3f(nodoObjetivo.getLocalTranslation().x,nodoObjetivo.getLocalTranslation().y+7,nodoObjetivo.getLocalTranslation().z),Vector3f.ZERO);
		}
		else {		
			System.out.println("Nodo no inicializado: "+userPos);
			camara.setLocation(new Vector3f(0,0,0));
		}
	}
	
	/**
	 * 
	 * @param nodo
	 * @param camara
	 */
	@SuppressWarnings("null")
	static public void setupUserChaseCamera(Node nodo,Camera camara) 
	{
		//este metodo no se a probado aun
		if(nodo!=null)
		{
			ChaseCamera camaraPersecutora = new ChaseCamera(camara, nodo);
			camaraPersecutora.setTargetOffset(new Vector3f(nodo.getLocalTranslation().x,(nodo.getLocalTranslation().y*1.5f),nodo.getLocalTranslation().z));
        }
		else
		{		
			System.out.println("Nodo no inicializado: "+nodo.getLocalTranslation());
			camara.setLocation(new Vector3f(0,0,0));
		}
	}
	
	static public void resetCamera(Camera camara) 
	{
		setupUserCamera(0,40,0,camara);
		camara.lookAt(Vector3f.UNIT_XYZ,Vector3f.ZERO);
	}
	

}
