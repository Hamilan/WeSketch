package sketching.widgets;


/**
 * This class represents the ButtonGroup widget to use in the Sketching system.
 * It works as a container of RadioButtons or CheckBoxes.
 * Internally defines constants for the ButtonGroup type allowing further creation of other types. 
 * Objects of this class are sendable through the net with Kryonet.
 * @author Hamilan
 */
public class ButtonGroup extends ContainerWidget {
	transient public final static int RADIOGROUP = 0; 
	transient public final static int CHECKBOXGROUP= 1;

	//data.title : title for the buttongroup
	//data.text : multiline text to show a group of radios or checkBoxes
	//data.type : takes a value among the constants defined in this class.

	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
		this.data.widgetType="ButtonGroup";		
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
