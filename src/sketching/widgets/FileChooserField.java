package sketching.widgets;


/**
 * This class represents the FileChooserField widget to use in the Sketching system.
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class FileChooserField extends ComponentWidget {
	/**
	 * data.text : url to show in the widget
	 */

	public FileChooserField() {
		this.data.widgetType="FileChooserField";
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
