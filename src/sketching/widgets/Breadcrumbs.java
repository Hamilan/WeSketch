package sketching.widgets;


/**
 * This class represents the Breadcrumbs widget to use in the Sketching system.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class Breadcrumbs extends ComponentWidget {
	/**
	 * Based on Balsamiq way of storing. Every comma will indicate a sub-level.
	 * Example:
	 *  Home, Products, Electronics, Cameras  
	 */
	//public String text; //already defined in super class Widget
	
	/** No-params constructor needed for sending trough Kryonet */
	public Breadcrumbs() {
		this.data.widgetType="Breadcrumbs";
	}
	
	public Breadcrumbs(String text) {
		this.data.widgetType="Breadcrumbs";
		this.data.text = text;
	}
	
	public Widget getClone() {
		return new Breadcrumbs(data.text);
	};
	
	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
		
	}

	@Override
	public void update(WidgetData widgetData) {
		// TODO Auto-generated method stub
		
	}

}