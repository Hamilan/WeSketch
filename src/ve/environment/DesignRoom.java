package ve.environment;

import java.util.concurrent.Callable;

import ve.library.TextureLoader;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.scene.Skybox;
import com.jme.util.GameTaskQueue;
import com.jme.util.GameTaskQueueManager;

@SuppressWarnings("serial")
public class DesignRoom extends Skybox
{
	public final static float TAMA_X=40;
	public final static float TAMA_Y=15;
	public final static float TAMA_z=40;
	
	public DesignRoom()
	{ 
		super("universo", TAMA_X, TAMA_Y, TAMA_z);
		Texture northWallTx = TextureLoader.loadTexture("walltexture.jpg");  
		Texture southWallTx = TextureLoader.loadTexture("walltexture.jpg");  
		Texture eastWallTx = TextureLoader.loadTexture("walltexture.jpg");  
		Texture westWallTx = TextureLoader.loadTexture("walltexture.jpg");  
		Texture ceilingTx = TextureLoader.loadTexture("ceiltexture.jpg");
		Texture floorTx = TextureLoader.loadTexture("floortiletexture.jpg");  
		floorTx.setWrap(Texture.WrapMode.Repeat);
		floorTx.setScale(new Vector3f(8,8,0));
		setTexture(Skybox.Face.North, northWallTx);  
		setTexture(Skybox.Face.West, westWallTx);  
		setTexture(Skybox.Face.South, southWallTx);  
		setTexture(Skybox.Face.East, eastWallTx);  
		setTexture(Skybox.Face.Up, ceilingTx);  
		setTexture(Skybox.Face.Down, floorTx);
		Callable<Object> preload = new Callable<Object>()
		{  
			public Object call() throws Exception
			{  
				preloadTextures();  
				return null;  
			}  
		};
		
		GameTaskQueueManager.getManager().getQueue(GameTaskQueue.RENDER).enqueue(preload);  
		updateRenderState();  
	}  

	public static float getTamaX() {
		return TAMA_X;
	}

	public static float getTamaY() {
		return TAMA_Y;
	}

	public static float getTamaZ() {
		return TAMA_z;
	}
}
