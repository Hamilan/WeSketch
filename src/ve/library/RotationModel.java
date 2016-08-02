package ve.library;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;


public class RotationModel{
	
	static public Quaternion rotateX(float angle)
	{
		return new Quaternion().fromAngleAxis(((FastMath.PI)*(angle/180)), new Vector3f(1,0,0));
	}
	
	static public Quaternion rotateY(float angle)
	{
		return new Quaternion().fromAngleAxis(((FastMath.PI)*(angle/180)), new Vector3f(0,1,0));
	}
	
	static public Quaternion rotateZ(float angle)
	{
		return new Quaternion().fromAngleAxis(((FastMath.PI)*(angle/180)), new Vector3f(0,0,1));
	}
	
	static public Quaternion rotateXYZ(float angleX,float angleY,float angleZ)
	{
		if(angleX==0&&angleY==0&&angleZ==0)
		{return new Quaternion();}
		else
		{
			if(angleX!=0&&angleY!=0&&angleZ!=0)
			{return RotationModel.rotateX(angleX).mult(RotationModel.rotateY(angleY)).mult(RotationModel.rotateZ(angleZ));}
			else if(angleX!=0&&angleY!=0&&angleZ==0)
			{return RotationModel.rotateX(angleX).mult(RotationModel.rotateY(angleY));}
			else if(angleX!=0&&angleY==0&&angleZ!=0)
			{return RotationModel.rotateX(angleX).mult(RotationModel.rotateZ(angleZ));}
			else if(angleX==0&&angleY!=0&&angleZ!=0)
			{return RotationModel.rotateY(angleY).mult(RotationModel.rotateZ(angleZ));}
			else if(angleX!=0)
			{return RotationModel.rotateX(angleX);}
			else if(angleY!=0)
			{return RotationModel.rotateY(angleY);}
			else {return RotationModel.rotateZ(angleZ);}	
		}
	}
}
