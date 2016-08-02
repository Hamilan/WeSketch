package sketching.widgets;

import java.awt.Graphics2D;


/**
 * This class represents the Image widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class Image extends ComponentWidget {

	/** No-params constructor needed for sending trough Kryonet */
	public Image() {
		this.data.widgetType="Image";
	}

	public Image(long id, long sketchId, int x, int y, int width, int height, int zOrder, String url) {
		this.data.widgetType="Image";
		this.data.text = "Imagen";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.image=url;
		setSize(width,height);
		setLocation(x, y);
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width-1, data.height-1);
		gContext.setColor(BORDERCOLOR);
		gContext.drawRect(0, 0, data.width, data.height);
		gContext.drawLine( 0, 0, data.width, data.height );
		gContext.drawLine(0, data.height, data.width, 0);
		if(data.image==null){
			int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
			int textH =	gContext.getFontMetrics().getHeight();
			gContext.drawChars(data.text.toCharArray(), 0, data.text.length(), this.getWidth()/2-textW/2, this.getHeight()/2+textH/3);
		}
	}

	@Override
	public Image getClone() {
		return new Image(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.image);
	}

	@Override
	public void update(WidgetData widgetData) {
		data.image = widgetData.image;
		updateCommonDataAndPaint(widgetData);
	}
}
