package sketching.widgets;

import shared.WeSketchConstants;

/**
 * This class represents the Font properties for some component widgets in the Sketching system that show text.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class WidgetFont {
	/**
	 * Is the int representation of a RGB color, between 0 (Black) and 65535 (White).
	 */
	public int color;
	public int size;
	public boolean bold;
	public boolean italic;
	public boolean underlined;
	 
	/** No-params constructor needed for sending trough Kryonet */
	public WidgetFont() {
		color = 0; //black
		size = 12;
		bold = false;
		italic = false;
		underlined = false;

	}

	public WidgetFont(int color, int size, boolean bold, boolean italic,
			boolean underlined,int aligment) {
		this.color = color;
		this.size = size;
		this.bold = bold;
		this.italic = italic;
		this.underlined = underlined;
	
	}
}
