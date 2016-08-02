package sketching.widgets;

import java.awt.Graphics2D;



/**
 * This class represents the Label widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class Label extends ComponentWidget {

	/** No-params constructor needed for sending trough Kryonet */
	public Label() {
		this.data.widgetType="Label";
	}

	public Label(long id, long sketchId, int x, int y, int width, int height, int zOrder, 
			String title,byte alignment) {
		this.data.widgetType="Label";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.text= title;
		this.data.enabled = true;
		this.data.groupId = -1;
		setSize(width,height);
		setLocation(x, y);

		this.data.font = new WidgetFont();
		this.data.alignment = alignment;
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		//BUtton Text
		gContext.setColor(BORDERCOLOR);

		// 30 pixel line, 10 pixel gap, 10 pixel line, 10 pixel gap
		float[] dashPattern = { 4, 3 };
		gContext.setStroke(new java.awt.BasicStroke( 1, java.awt.BasicStroke.CAP_BUTT,
				java.awt.BasicStroke.JOIN_MITER, 10, dashPattern, 0));
		gContext.drawRoundRect(1, 1, data.width-3, data.height-3,5,5);

		if(data.alignment==LEFT_ALIGNMENT)
		{
			int textH =	gContext.getFontMetrics().getHeight();
			gContext.drawChars(data.text.toCharArray(), 0, data.text.length(),3, this.getHeight()/2+textH/3);

		}else
		{
			if (data.alignment==CENTER_ALIGNMENT) 
			{
				int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
				int textH =	gContext.getFontMetrics().getHeight();
				gContext.drawChars(data.text.toCharArray(), 0, data.text.length(), this.getWidth()/2-textW/2, this.getHeight()/2+textH/3);
			}
			else {

				int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
				int textH =	gContext.getFontMetrics().getHeight();
				gContext.drawChars(data.text.toCharArray(), 0, data.text.length(), this.getWidth()-textW-2, this.getHeight()/2+textH/3);
			}
		}
	}

	@Override
	public Widget getClone() {
		return new Label(data.id, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text, data.alignment);
	}

	@Override
	public void update(WidgetData widgetData) {
		data.font = widgetData.font;
		data.alignment=widgetData.alignment;
		updateCommonDataAndPaint(widgetData);
	}
}
