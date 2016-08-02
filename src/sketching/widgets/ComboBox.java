package sketching.widgets;

import java.awt.Graphics2D;
import java.util.StringTokenizer;

/**
 * This class represents the ComboBox widget to use in the Sketching system.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class ComboBox extends ComponentWidget {

	/** No-params constructor needed for sending trough Kryonet */
	public ComboBox() {
		this.data.widgetType="ComboBox";
	}
	
	public ComboBox(long id, long sketchId, int x, int y, int width, int zOrder, 
			String options, boolean expanded) {
		this.data.widgetType="ComboBox";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.selected=expanded;
		this.data.text = options;
		
		StringTokenizer tokenizer= new StringTokenizer(options,"\n");
		imageBuffer = new java.awt.image.BufferedImage(width,20,java.awt.image.BufferedImage.TYPE_INT_RGB);
		gContext= imageBuffer.createGraphics();
		int textH =	gContext.getFontMetrics().getHeight();
		if(expanded) {
			data.height=(textH+3)*tokenizer.countTokens();
		}
		else {
			data.height=textH+5;
		}
		setSize(width, data.height);
		setLocation(x, y);
	}
	@Override
	public ComboBox getClone() {
		return new ComboBox(data.id, data.sketchId, data.x, data.y, data.width, data.zOrder, data.text, data.selected);
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		gContext.setColor(BORDERCOLOR);

		StringTokenizer tokenizer= new StringTokenizer(data.text,"\n");
		int textH =	gContext.getFontMetrics().getHeight();
		int i=textH;
		int expandedX=3;

		while( tokenizer.hasMoreTokens() ) {
			String opcion= tokenizer.nextToken();
			gContext.drawChars(opcion.toCharArray(), 0, opcion.length(),expandedX, i);
			i+=textH+3;
			if (data.selected==false) {
				break;
			}else{
				expandedX=10;
			}
		}
		//Background of expand button
		gContext.setColor(BACKGROUNDCOLOR);
		gContext.fillRect(data.width-16, 0, data.width-1, textH+3);
		//Triangle
		int[] xPoints={data.width-14,data.width-9,data.width-3};
		int[] yPoints={textH-8,textH-1,textH-8};
		gContext.setColor(BORDERCOLOR);
		gContext.fillPolygon(xPoints, yPoints, 3);
		//line for first option inside box
		gContext.drawRect(0,0, data.width-1, textH+3);
		if (data.selected) {
			gContext.drawRect(expandedX-3, textH+3, data.width-expandedX+2, data.height-4-textH);
		}
		gContext.drawLine(0, textH+3, data.width, textH+3);
		gContext.setColor(BORDERCOLOR);
		gContext.drawLine(data.width-16, 0, data.width-16, textH+3);
	}
	
	/** should be overridden by Widgets that have a text attribute that can be quick-editable */
	public void quickUpdate(String text){
		this.data.text = text;
		int totalHeight = (gContext.getFontMetrics().getHeight() + LINE_SPACING);
		if (data.selected) {
			StringTokenizer tokenizer= new StringTokenizer(text,"\n");
			totalHeight *= tokenizer.countTokens();
		}
		setSize(data.width, totalHeight);
	}

	@Override
	public void update(WidgetData widgetData) {
		updateCommonDataAndPaint(widgetData);
	}

}
