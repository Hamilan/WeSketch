package sketching.widgets;

import java.awt.Graphics2D;


/**
 * This class represents the CheckBox widget to use in the Sketching system.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class CheckBox extends ComponentWidget {

	/** No-params constructor needed for sending trough Kryonet */
	public CheckBox() {
		this.data.widgetType="CheckBox";
	}

	public CheckBox(long id, long sketchId, int x, int y, int width, int height, int zOrder, String title, boolean selected) {
		this.data.widgetType="CheckBox";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;

		this.data.text = title;
		this.data.selected= selected;

		setSize(width, height);
		setLocation(x, y);
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		//panel background
		gContext.setBackground(java.awt.Color.WHITE);
		gContext.clearRect(0, 0, data.width, data.height);
		//button shadow and button background
		gContext.setColor(java.awt.Color.WHITE);
		gContext.fillRect(3,(data.height/2)-5,10,10);

		if(data.selected) {
			//button foreground
			gContext.setColor(java.awt.Color.BLACK);
			gContext.drawLine(5, (data.height/2)-1, 8, (data.height/2)+3);
			gContext.drawLine(5, (data.height/2), 8, (data.height/2)+4);
			gContext.drawLine(8, (data.height/2)+4, 12, (data.height/2)-4);
			gContext.drawLine(8, (data.height/2)+3, 12, (data.height/2)-5);
		}
		//Button Border
		gContext.setColor(java.awt.Color.BLACK);
		gContext.drawRect(3,(data.height/2)-5,10,10);
		//Button Text
		int textH =	gContext.getFontMetrics().getHeight();
		gContext.drawChars(data.text.toCharArray(), 0, data.text.length(),15, this.getHeight()/2+textH/3);
	}
	@Override
	public CheckBox getClone() {
		return new CheckBox(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text, data.selected);
	}

	@Override
	public void update(WidgetData widgetData) {

	}
}
