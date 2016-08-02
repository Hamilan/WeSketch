package sketching.widgets;

import java.awt.Graphics2D;


/**
 * This class represents the Frame to use in the Sketching System.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class Frame extends ContainerWidget {
	//El titulo del frame estara guardado en el atributo text de la clase Widget

	transient public final static byte NO_BUTTONS=0;
	transient public final static byte MAXIMIZE_MINIMIZE_CLOSE_STATEBAR=1;
	transient public final static byte MAXIMIZE_MINIMIZE_CLOSE=7;
	transient public final static byte MAXIMIZE_MINIMIZE_STATEBAR=3;
	transient public final static byte MAXIMIZE_MINIMIZE=9;
	transient public final static byte MAXIMIZE_CLOSE_STATEBAR=2;
	transient public final static byte MAXIMIZE_CLOSE=8;
	transient public final static byte MAXIMIZE_STATEBAR=4;
	transient public final static byte MAXIMIZE=11;
	transient public final static byte MINIMIZE_CLOSE_STATEBAR=15;
	transient public final static byte MINIMIZE_STATEBAR=5;
	transient public final static byte MINIMIZE_CLOSE=10;
	transient public final static byte MINIMIZE=12;
	transient public final static byte CLOSE_STATEBAR=6;
	transient public final static byte CLOSE=13;
	transient public final static byte STATEBAR=14;

	//public boolean hasCloseButton, hasMinimizeButton, hasMaximizeButton, hasStateBar;

	public Frame() {
		this.data.widgetType="Frame";
	}
	public Frame(long id, long sketchId, int x, int y, int width, int height, int zOrder, String title, byte type) {
		this.data.widgetType="Frame";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.title = title;
		this.data.text = title;
		setSize(width, height);
		setLocation(x, y);
		this.data.type=type;
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		gContext.setColor(BORDERCOLOR);
		gContext.drawRect(0, 0, data.width-1,data.height-1);
		gContext.drawLine(0, 15, data.width, 15);//title division		
		int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
		int textH =	gContext.getFontMetrics().getHeight();
		gContext.drawChars( data.text.toCharArray(), 0, data.text.length(), 2, textH-3);
		//gContext.drawLine(width-12, 10, width-4, 4);

		if( data.type == MAXIMIZE_MINIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_MINIMIZE_CLOSE ||
				data.type == MAXIMIZE_MINIMIZE_STATEBAR ||
				data.type == MAXIMIZE_MINIMIZE||
				data.type == MAXIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_CLOSE ||
				data.type == MAXIMIZE_STATEBAR ||
				data.type == MAXIMIZE )
		{
			drawMaximizeButton(gContext, data);
		}
		if( data.type == MAXIMIZE_MINIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_MINIMIZE_CLOSE ||
				data.type == MAXIMIZE_MINIMIZE_STATEBAR ||
				data.type == MINIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_MINIMIZE||
				data.type == MINIMIZE_CLOSE||
				data.type == MINIMIZE_STATEBAR||
				data.type == MINIMIZE )
		{
			drawMinimizeButton(gContext, data);
		}
		if( data.type == MAXIMIZE_MINIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_MINIMIZE_CLOSE ||
				data.type == MAXIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_CLOSE ||
				data.type == MINIMIZE_CLOSE_STATEBAR ||
				data.type == CLOSE_STATEBAR ||
				data.type == MINIMIZE_CLOSE||
				data.type == CLOSE )
		{
			drawCloseButton(gContext, data);
		}
		if( data.type == MAXIMIZE_MINIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_MINIMIZE_STATEBAR ||
				data.type == MAXIMIZE_CLOSE_STATEBAR ||
				data.type == MAXIMIZE_STATEBAR ||
				data.type == MINIMIZE_CLOSE_STATEBAR ||
				data.type == MINIMIZE_STATEBAR||
				data.type == CLOSE_STATEBAR ||
				data.type == STATEBAR )
		{
			drawStateBar(gContext, data);
		}
	}

	private void drawMinimizeButton(Graphics2D gContext, WidgetData data) {
		gContext.drawRect(data.width-37,2,10,10);
		gContext.drawLine(data.width-35, 9, data.width-30, 9);
	}
	private void drawStateBar(Graphics2D gContext, WidgetData data) {
		gContext.drawLine(0, data.height-15, data.width,data.height-15 );
	}
	private void drawMaximizeButton(Graphics2D gContext, WidgetData data) {
		gContext.drawRect(data.width-25, 2, 10, 10);
		gContext.drawRect(data.width-23, 5, 5, 5);		
	}
	private void drawCloseButton(Graphics2D gContext, WidgetData data) {
		gContext.drawRect(data.width-13,2,10,10);
		gContext.drawLine(data.width-12, 4, data.width-4, 10);
		gContext.drawLine(data.width-12, 10, data.width-4, 4);		
	}
	@Override
	public Frame getClone() {
		return new Frame(data.groupId, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.title,data.type);
	}
	@Override
	public void update(WidgetData widgetData) {
		this.data.type=widgetData.type;
		updateCommonDataAndPaint(widgetData);
	}
}
