package sketching.widgets;

/**
 * This class represents the InputDialog widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class InputDialog extends MessageDialog {
	/**
	 * data..text : initial text to display in the field of the dialog
	 */
	
	public InputDialog() {
		this.data.widgetType="InputDialog";
	}
}
