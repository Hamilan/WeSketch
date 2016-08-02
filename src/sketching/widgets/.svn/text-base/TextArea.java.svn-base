package sketching.widgets;

import java.util.StringTokenizer;

/**
 * This class represents the TextArea widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class TextArea extends ComponentWidget {
	
	/** No-params constructor needed for sending trough Kryonet */
	public TextArea() {
		this.data.widgetType="TextArea";
	}
	
	public TextArea(long id, long sketchId, int x, int y, int width, int height, int zOrder, 
			String text) {
		this.data.widgetType="TextArea";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.text= text;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.text = text;
		this.data.font = new WidgetFont();
		this.data.alignment = Widget.LEFT_ALIGNMENT;
		setSize(width,height);
		setLocation(x, y);
	}

	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		//Button Text
		gContext.setColor(BORDERCOLOR);
		gContext.drawRect(1, 1, data.width-3, data.height-3);
		
		StringTokenizer tokenizer= new StringTokenizer(data.text,"\n");
		int textH =	gContext.getFontMetrics().getHeight();
		int i=textH;
		while(tokenizer.hasMoreTokens()) {
			String opcion= tokenizer.nextToken();
			gContext.drawChars(opcion.toCharArray(), 0, opcion.length(),3, i);
			i+=textH+LINE_SPACING;
		}
		drawInternalScrollBar(gContext,data);
	}

	@Override
	public TextArea getClone() {
		return new TextArea(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text);
	}

	@Override
	public void update(WidgetData widgetData) {
		this.data.font = widgetData.font;
		this.data.alignment= widgetData.alignment;
		updateCommonDataAndPaint(widgetData);
	}
}
