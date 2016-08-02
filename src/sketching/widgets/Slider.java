package sketching.widgets;


/**
 * This class represents the Slider widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class Slider extends ComponentWidget {
	/**
	 * data.value : 0 to 100 integer value. Defines the position of the value selector/arrow.
	 */

	public Slider() {
		this.data.widgetType="Slider";
	}
	
	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
		// TODO Auto-generated method stub
		
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
