//package shared;
//
//import java.util.Vector;
//
//import com.jme.curve.BezierCurve;
//import com.jme.math.Ray;
//import com.jme.math.Vector3f;
//import com.jme.renderer.ColorRGBA;
//import com.jme.scene.shape.Box;
//import com.jme.scene.shape.Capsule;
//import com.jme.util.geom.BufferUtils;
//
///**
// * This class represents the Telepointer shared among participants. 
// * @author Hamilan
// */
//public class Telepointer{
//	/**
//	 * Colores preestablecidos segun el avatar asignado en la posicion correspondiente
//	 */
//	static ColorRGBA[] colors = {ColorRGBA.blue,ColorRGBA.green,ColorRGBA.magenta,ColorRGBA.orange,ColorRGBA.pink,ColorRGBA.white};
//
//	Vector3f origin;
//	Vector3f direction;
//	static Vector3f[] points = new Vector3f[2];
//	static BezierCurve curve;
//	static BezierCurve curves[]=new BezierCurve[6];
//	public static Ray rayo;
//
//	/**
//	 * color of the telepointer, takes a value from the CVEConstants: WHITE, BLUE, GREEN, RED, YELLOW, MAGENTA, BLACK
//	 * It takes a value according to the index of the user among the participants  
//	 */
//	static int color;
//		
//
//	public Telepointer() {}
//	
//	/**
//	 * create a new array of bezier curves for the telepointer implementation
//	 * @param ray
//	 * @param color
//	 */
//	public Telepointer(Ray ray, int color) {
//		rayo=ray;
//		origin = ray.origin;
//		direction = ray.direction;
//		
//		points[0] = origin;
//		points[1] = direction;
//		points[1].z=5;
//		
//		for(int i=1;i<7;i++)
//		{
//			points[1].x=(float) (points[1].x+((i)*0.05));
//			curve=new BezierCurve("telepointer:"+i, points);
//			curve.setColorBuffer(BufferUtils.createFloatBuffer(colors[color]));
//			curves[i-1]=curve;
//		}
//	}
//	
//	public Vector3f getOrigin() {
//		return origin;
//	}
//
//	public void setOrigin(Vector3f origin) {
//		this.origin = origin;
//	}
//
//	public Vector3f getDirection() {
//		return direction;
//	}
//
//	public void setDirection(Vector3f direction) {
//		this.direction = direction;
//	}
//	
//	/**
//	 * update the telepointer position
//	 * @param telepointer
//	 * @param ray
//	 */
//	public static void updateTelepointer(Telepointer telepointer,Ray ray)
//	{
//		rayo=ray;
//		telepointer.setOrigin(ray.origin);
//		telepointer.setDirection(ray.direction);
//		points[0]=telepointer.getOrigin();
//		points[1]=telepointer.getDirection();
//		points[1].z=5;
//		
//		for(int i=1;i<7;i++)
//		{
//			points[1].x=(float) (points[1].x+((i)*0.05));
//			curve=new BezierCurve("telepointer:"+i, points);
//			curve.setColorBuffer(BufferUtils.createFloatBuffer(colors[color]));
//			curves[i-1]=curve;
//		}
//	}
//
//	public Ray getRay() {
//		return rayo;
//	}
//
//	public static void setRay(Ray ray) {
//		rayo = ray;
//	}
//
//	public static BezierCurve[] getCurve() {
//		return curves;
//	}
//
//		
//}

package shared;

import com.jme.math.LineSegment;
import com.jme.math.Ray;
import com.jme.math.Vector3f;
import com.jme.scene.Line;
import com.jme.scene.Spatial;

/**
 * This class represents the Telepointer shared among participants. 
 * @author Hamilan
 */
public class Telepointer{
	public transient static final String TELEPOINTER_PREFIX = "telepointer";
	private transient Vector3f origin;	//cannot send this object through the net
	private transient Vector3f direction; //cannot send this object through the net
	public static Vector3f globalVector;
	/**
	 * Stores information of the origin Vector3f.
	 * Used for networking, since Vector3f objects cannot be sent.
	 */
	float originf[] = new float[3];	//   so this one will be used
	/**
	 * Stores information of the direction Vector3f.
	 * Used for networking, since Vector3f objects cannot be sent.
	 */
	float directionf[] = new float[3]; //   so this one will be used

	/**
	 * color of the telepointer, takes a value from the CVEConstants: WHITE, BLUE, GREEN, RED, YELLOW, MAGENTA, BLACK
	 * It takes a value according to the index of the user among the participants
	 */
	public int color;
	//used only for painting, won't be transmitted to the others.
	transient private static Vector3f[] points = new Vector3f[2];
	transient private static Line line;
	transient private static LineSegment linea;


	public Telepointer() {}

	public Telepointer(Ray ray, int color) {
		this.color = color;    
		System.out.println("Telepointer creado con color "+color);
		origin = ray.origin;
		direction = ray.direction;
		

		points[0] = origin;
		points[1] = direction;

		linea=new LineSegment(points[0], direction, 100);
		
		line = new Line("telepointer"+color,points,null,null,null);
		line.setLineWidth(5);
		line.setLightCombineMode(Spatial.LightCombineMode.Off);
		
		line.setDefaultColor(WeSketchConstants.RGBACOLOR[color]);
		
		originf[0]=origin.getX();
		originf[1]=origin.getY();
		originf[2]=origin.getZ();
		directionf[0] = direction.getX();
		directionf[1] = direction.getY();
		directionf[2] = direction.getZ();
	}

	public static void updateTelepointer(Telepointer telepointer,Ray ray) {
		
		points[0]=ray.origin;
		points[1]=ray.direction;
		
		telepointer.setOrigin(points[0]);
		telepointer.setDirection(points[1]);


		line = new Line("telepointer"+telepointer.color,points,null,null,null);
		line.setLineWidth(5);
		line.setLightCombineMode(Spatial.LightCombineMode.Off);
		line.setDefaultColor(WeSketchConstants.RGBACOLOR[telepointer.color]);
	}

	public Vector3f getOrigin() {
		return origin;
	}

	public void setOrigin(Vector3f origin) {
		this.origin = origin;
		originf[0]=origin.getX();
		originf[1]=origin.getY();
		originf[2]=origin.getZ();
	}

	public Vector3f getDirection() {
		return direction;
	}

	public void setDirection(Vector3f direction) {
		this.direction = direction;
		directionf[0] = direction.getX();
		directionf[1] = direction.getY();
		directionf[2] = direction.getZ();
	}
	
	public static Vector3f getGlobalTelepointer()
	{
		
		linea.getPositiveEnd(globalVector);
		return globalVector;
	}

	public Line getLine() {
		if(line==null){
			points[0]=this.getOrigin();
			points[1]=this.getDirection();

			line = new Line(TELEPOINTER_PREFIX+this.color,points,null,null,null);
			line.setLineWidth(5);
			line.setLightCombineMode(Spatial.LightCombineMode.Off);
			line.setDefaultColor(WeSketchConstants.RGBACOLOR[this.color]);
		}
		return line;
	}

	public static void setLine(Line line) {
		Telepointer.line= line;
	}
	
	public static void setGlobalVector(Vector3f globalVector) {
		Telepointer.globalVector= globalVector;
	}

	public void loadVectors() {
		origin = new Vector3f(originf[0],originf[1],originf[2]);
		direction = new Vector3f(directionf[0],directionf[1],directionf[2]);
	}
	
	@Override
	public String toString() {
		StringBuffer date=new StringBuffer();
		date.append("origen: X:"+originf[0]+" Y:"+originf[1]+" Z:"+originf[2]);
		date.append(" direccion: X:"+directionf[0]+" Y:"+directionf[1]+" Z:"+directionf[2]);
		return date.toString();
	}
}