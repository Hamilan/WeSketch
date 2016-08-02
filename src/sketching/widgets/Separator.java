package sketching.widgets;


/**
 * This class represents the Separator widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class Separator extends ComponentWidget {
	/**
	 * selected : if true will be displayed as horizontal separator, if false will be vertical.
	 */
	public Separator() {
		this.data.widgetType="Separator";
	}
	
	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
	}

	@Override
	public Widget getClone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(WidgetData widgetData) {
		// TODO Auto-generated method stub
		
	}
}
