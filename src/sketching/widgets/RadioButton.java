package sketching.widgets;

import java.awt.Graphics2D;


/**
 * This class represents the RadioButton widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class RadioButton extends ComponentWidget {
	
	/** No-params constructor needed for sending trough Kryonet */
	public RadioButton() {
		this.data.widgetType="RadioButton";
	}
	
	public RadioButton(long id, long sketchId, int x, int y, int width, int height, int zOrder, String title, boolean selected) {
		this.data.widgetType="RadioButton";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.x = x;
		this.data.y = y;
		this.data.width = width;
		this.data.height = height;
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
		gContext.fillOval(3,(data.height/2)-5,10,10);
		if(data.selected) {
//			//button foreground
			gContext.setColor(java.awt.Color.BLACK);
			gContext.fillOval(5,(data.height/2)-3, 6, 6);
		}
//		//Button Border
		gContext.setColor(java.awt.Color.BLACK);
		gContext.drawOval(3,(data.height/2)-5,10,10);
		//Button Text
		int textH =	gContext.getFontMetrics().getHeight();
		gContext.drawChars(data.text.toCharArray(), 0, data.text.length(),18, this.getHeight()/2+textH/3);
	}

	@Override
	public RadioButton getClone() {
		return new RadioButton(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text, data.selected);
	}

	@Override
	public void update(WidgetData widgetData) {
		this.data.selected = widgetData.selected;
		updateCommonDataAndPaint(widgetData);
	}
}
