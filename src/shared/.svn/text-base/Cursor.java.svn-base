package shared;

/**
 * This class represents the Cursor shared among participants. 
 * @author Hamilan
 */
public class Cursor{
//	/** participant's connection id */
//	public int userId;

	/** Type of cursor, takes a value from CVEConstants ARROW, HAND, EXPAND_HORIZONTAL, EXPAND_VERTICAL, MOVE */
	public int type;
	/**
	 * Takes a value from the CVEConstants: WHITE, BLUE, GREEN, RED, YELLOW, MAGENTA, BLACK
	 * It's value is according to the index of the user among the participants  
	 */
	public int color;
	/** Position of the cursor inside the Sketching Frame. */
	public int x, y;
	/** Id of the sketch where this Cursor is being used */
	public long sketchId;
	
	public Cursor() {
		x = y = 1;
//		userId = -1;
		color = -1;
		type = WeSketchConstants.ARROW_CURSOR;
		sketchId = -1;
	}

	public Cursor(
			//int userId, 
			int type, int color, int x, int y, long sketchId) {
//		this.userId = userId;
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
		this.sketchId = sketchId;
	}
	public void setTypeAndPosition(int type, int x, int y){
		this.type = type;
		this.x = x;
		this.y = y;
	}
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}