package sketching.widgets;

import java.awt.Color;

import com.jme.scene.Text;


/**
 * This class represents the Panel container widget to use in the Sketching system. 
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class Panel extends ContainerWidget {
	
	public Panel() {}
	/**
	 * data.title : Is the panel border title. If empty or null the border will be continuous. 
	 * data.color : 
	 */
	public Panel(long id, long sketchId, int x, int y, int width, int height, int zOrder, String title) {
		this.data.widgetType="Panel";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.text=title;
		setSize(width, height);
		setLocation(x, y);
		
	}
	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		gContext.setColor(BORDERCOLOR);
		gContext.drawRect(0, 2,  data.width-1,data.height-3);
		//dibujar el nombre del panel en el borde
		int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
		int textH =	gContext.getFontMetrics().getMaxAscent();
		gContext.setColor(BACKGROUNDCOLOR);
		gContext.fillRect(5, 0, textW, textH);
		gContext.setColor(java.awt.Color.black);
		gContext.drawChars(this.data.text.toCharArray(), 0, this.data.text.length(), 5, textH/2+3);
	}

	@Override
	public Widget getClone() {
		return new Panel(data.groupId, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.text);
	}

	@Override
	public void update(WidgetData widgetData) {
		updateCommonDataAndPaint(widgetData);
	}
}
