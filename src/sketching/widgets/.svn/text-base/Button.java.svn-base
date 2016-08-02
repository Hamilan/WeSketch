package sketching.widgets;

import java.awt.Graphics2D;


/**
 * This class represents the Button widget to use in the Sketching system.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class Button extends ComponentWidget {

	/** No-params constructor needed for sending trough Kryonet */
	public Button() {
		this.data.widgetType="Button";
	}
	
	public Button(long id, long sketchId, int x, int y, int width, int height, int zOrder, String text, String image) {
		this.data.widgetType="Button";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.text = text;
		this.data.image = image;

		setSize(width, height);
		setLocation(x, y);
	}
	@Override
	public Button getClone() {
		return new Button(data.id,data.sketchId,data.x,data.y,data.width,data.height,data.zOrder,data.text,data.image);
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		//Background
		gContext.setBackground(java.awt.Color.WHITE);
		gContext.clearRect(0, 0, data.width, data.height);
		//Button shadow and button background
		gContext.setColor(java.awt.Color.DARK_GRAY);
		gContext.fillRoundRect(2, 2, data.width-2, data.height-2, 5, 5);
		//Button foreground
		gContext.setColor(java.awt.Color.LIGHT_GRAY);
		gContext.fillRoundRect(1, 1, data.width-2, data.height-2, 5, 5);
		//Button Border
		gContext.setColor(java.awt.Color.DARK_GRAY);
		gContext.drawRoundRect(1, 1, data.width-3, data.height-3, 5, 5);
		//Button Text
		int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
		int textH =	gContext.getFontMetrics().getHeight();
		gContext.drawChars(data.text.toCharArray(), 0, data.text.length(), data.width/2-textW/2, data.height/2+textH/3);	
	}
	
	@Override
	public void update(WidgetData widgetData) {
		data.image = widgetData.image;
		updateCommonDataAndPaint(widgetData);
	}
	
}
