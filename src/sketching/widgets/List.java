package sketching.widgets;

import java.awt.Graphics2D;
import java.util.StringTokenizer;

/**
 * This class represents the List widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class List extends ComponentWidget {

	/** No-params constructor needed for sending trough Kryonet */
	public List() {
		this.data.widgetType="List";
	}
	
	public List(long id, long sketchId, int x, int y, int width, int height, int zOrder, 
			String options) {
		this.data.widgetType="List";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.text= options;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.font = new WidgetFont();
		setSize(width,height);
		setLocation(x, y);
	}
	
	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		
		StringTokenizer tokenizer= new StringTokenizer(data.text,"\n");
		int textH =	gContext.getFontMetrics().getHeight();
		int count=0;
		int i=textH;
		while(tokenizer.hasMoreTokens())
		{
			String opcion= tokenizer.nextToken();
			if(count%2==0){
				gContext.setColor(this.HIGHLIGHT_COLOR);
				gContext.fillRect(2, i+textH/3, data.width-4, textH+textH/2);
				gContext.setColor(BORDERCOLOR);
			}
			gContext.drawChars(opcion.toCharArray(), 0, opcion.length(),3, i);
			i+=textH+LINE_SPACING;
			count++;
		}
		gContext.drawRect(1, 1, data.width-3, data.height-3);
		drawInternalScrollBar(gContext,data);
	}

	@Override
	public List getClone() {
		return new List(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text);
	}

	@Override
	public void update(WidgetData widgetData) {
		this.data.value = widgetData.value;
		this.data.font = widgetData.font;
		this.data.alignment= widgetData.alignment;
		updateCommonDataAndPaint(widgetData);
	}
}
