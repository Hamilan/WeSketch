package sketching.widgets;

import java.awt.Graphics2D;

/**
 * This class represents the TextField/InputField widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class TextField extends ComponentWidget {
	/** No-params constructor needed for sending trough Kryonet */
	public TextField() {
		this.data.widgetType=this.getClass().getSimpleName();
	}
	
	public TextField(long id, long sketchId, int x, int y, int width, int height, int zOrder, 
			String text) {
		this.data.widgetType=this.getClass().getSimpleName();
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.x = x;
		this.data.y = y;
		this.data.width = width;
		this.data.height = height;
		this.data.zOrder = zOrder;
		this.data.text=text;
		this.data.enabled = true;
		this.data.groupId = -1;
		
		setSize(width, height);
		setLocation(x, y);
	}
	
	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		//panel background
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		//button shadow and button background
		gContext.setColor(FOREGROUNDCOLOR);
		gContext.fillRoundRect(1, 1, data.width-2, data.height-2, 5, 5);
		//button foreground
		gContext.setColor(BACKGROUNDCOLOR);
		gContext.fillRoundRect(3, 3, data.width-7, data.height-7, 5, 5);
		//Button Border
		gContext.setColor(BORDERCOLOR);
		gContext.drawRect(3, 3, data.width-7, data.height-7);
		//BUtton Text
		//int textW =	gContext.getFontMetrics().charsWidth(text.toCharArray(), 0, text.length());
		int textH =	gContext.getFontMetrics().getHeight();
		//gContext.drawChars(text.toCharArray(), 0, text.length(), this.getWidth()/2-textW/2, this.getHeight()/2+textH/3);
		gContext.drawChars(data.text.toCharArray(), 0, data.text.length(), 4, this.getHeight()/2+textH/3);
	}

	@Override
	public TextField getClone() {
		return new TextField(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text);
	}

	@Override
	public void update(WidgetData widgetData) {
		updateCommonDataAndPaint(widgetData);		
	}

}
