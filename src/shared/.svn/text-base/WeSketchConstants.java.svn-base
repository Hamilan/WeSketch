package shared;

import java.awt.Color;

import com.jme.renderer.ColorRGBA;

/**
 * This class defines the Constants to use in many values in the application. 
 * @author Hamilan
 */
public abstract class WeSketchConstants {
	/**
	 * Used for color of cursors, avatars, telepointers.
	 * For the 1st release of the application only the first 6 (0-5) will be used. 
	 */
	transient public static final int BLACK=0, BLUE=1, GREEN=2, RED=3, PURPLE=4, YELLOW=5, MAGENTA=6, WHITE=7;
	/**
	 * Used for getting name of the color and get names for image files 
	 */
	transient public static final String[] COLORNAME = {"black","blue","green","red","purple","yellow","magenta","white"};
	/**
	 * Used for getting name of the color and get names for image files 
	 */
	transient public static final Color[] RGBCOLOR = {Color.BLACK,Color.BLUE,Color.GREEN,Color.RED,new Color(221,0,255),new Color(204,204,0),Color.MAGENTA,Color.WHITE};	
	transient public static final ColorRGBA[] RGBACOLOR = {new ColorRGBA(0,0,0,0.5f),ColorRGBA.blue,ColorRGBA.green,ColorRGBA.red,new ColorRGBA(0.9f,0,1,1),new ColorRGBA(0.8f,0.8f,0,1),ColorRGBA.magenta,ColorRGBA.white};
	/**
	 * Used for types of cursors
	 */
	transient public static final int ARROW_CURSOR=0, HAND_CURSOR=1, EXPAND_HORIZONTAL_CURSOR=2, EXPAND_VERTICAL_CURSOR=3, MOVE_CURSOR=4, WRITE_CURSOR=5;
	transient public static final String[] CURSORFILE={"arrow","hand","scaleHorizontal","scaleVertical","move","textEditing"};
	/**
	 * Max users allowed in a design session
	 */
	transient public static final int MAX_SESSION_PARTICIPANTS = 6;
	transient public static final float MAX_SESSION_PARTICIPANTSF = 6f;
	transient public static final String APPLICATION_NAME = "WeSketch";
	transient public static final int MAX_ZORDER = 130000;

	/** Generates a general purpose ID based on currentTime in millis and userId */
	public static long getNewId(){
		long newId=System.currentTimeMillis()*100;//+CVEGUIDClient.myParticipantInfo.userId;
		return newId;
	}

}