package clientgui;

import javax.swing.JTextPane;

/**
 * This class allow to easily append text into a JTextPane 
 * @author Hamilan
 */
public class AppendingTextPane extends JTextPane {
	StringBuffer sb;

	public AppendingTextPane() {
		super();
		setContentType("text/html");
		sb = new StringBuffer();
		setEditable(false);
		setAutoscrolls(true);
	}
	public void append(String message) {
		sb.append(message);
		setText(sb.toString());
		//setCaretPosition(getText().length() - 1);
	}
	public void newLine() {
		append("<br/>");
	}
}
