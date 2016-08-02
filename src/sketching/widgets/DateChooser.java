package sketching.widgets;


/**
 * This class represents the DateChooser widget to use in the Sketching system.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class DateChooser extends ComponentWidget {
	/**
	 * will be saved as a "yyyy/mm/dd" single String
	 * data.text : date
	 */
	public DateChooser() {
		this.data.widgetType="DateChooser";
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
