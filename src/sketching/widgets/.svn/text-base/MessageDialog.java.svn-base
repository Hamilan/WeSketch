package sketching.widgets;

import javax.swing.JOptionPane;


/**
 * This class generalizes Simple Dialogs which are not containers of widgets but do display text and an icon.
 * Objects of this general class will be simple MessageDialogs.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class MessageDialog extends ComponentWidget {
	transient public final static int SIMPLE=0; 
	transient public final static int INFORMATION=1; 
	transient public final static int WARNING=2; 
	transient public final static int QUESTION=3; 
	transient public final static int ERROR=4; 
	
	/**
	 * data.title : message to show in the titlebar of the dialog
	 * data.text : message to display in the body of the dialog
	 * data.iconType : will take a value among the constants SIMPLE, INFORMATION, WARNING, QUESTION and ERROR
	 */
	
	public MessageDialog() {
		this.data.widgetType="MessageDialog";
	}
	
	public MessageDialog(long id, long sketchId, int x, int y, int width, int height, int zOrder, String title,String text,byte iconType) {
		this.data.widgetType="Frame";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		this.data.title = title;
		this.data.iconType=iconType;
		setSize(width, height);
		setLocation(x, y);
		this.data.text=text;
	}
	@Override
	public void drawWidgetInside(java.awt.Graphics2D gContext, WidgetData data) {
		gContext.setBackground(BACKGROUNDCOLOR);
		gContext.clearRect(0, 0, data.width, data.height);
		gContext.setColor(BORDERCOLOR);
		gContext.drawRect(0, 0, data.width-1,data.height-1);
		gContext.drawLine(0, 15, data.width, 15);//title division		
		int textWT =	gContext.getFontMetrics().charsWidth(data.title.toCharArray(), 0, data.title.length());
		int textHT =	gContext.getFontMetrics().getHeight();
		gContext.drawChars( data.title.toCharArray(), 0, data.title.length(), 2, textHT-3);
		
		//Panel background
		gContext.setBackground(java.awt.Color.WHITE);
		gContext.clearRect(data.width/2-25, data.height-30, 50, 20);
		//Button shadow and button background
		gContext.setColor(java.awt.Color.DARK_GRAY);
		gContext.fillRoundRect((data.width/2-25)+2, (data.height-30)+2, 48, 18, 2, 2);
		//Button foreground
		gContext.setColor(java.awt.Color.LIGHT_GRAY);
		gContext.fillRoundRect((data.width/2-25)+1,(data.height-30)+1, 48, 18, 2, 2);
		//Button Border
		gContext.setColor(java.awt.Color.DARK_GRAY);
		gContext.drawRoundRect((data.width/2-25)+1,(data.height-30)+1, 47,17, 2, 2);
		//Button Text
		String textButton="Aceptar";
		int textWB =	gContext.getFontMetrics().charsWidth(textButton.toCharArray(), 0, textButton.length());
		int textHB =	gContext.getFontMetrics().getHeight();
		gContext.drawChars(textButton.toCharArray(), 0, textButton.length(), (data.width/2)-textWB/2, (data.height-20)+textHB/3);	
		
		int textW =	gContext.getFontMetrics().charsWidth(data.text.toCharArray(), 0, data.text.length());
		int textH =	gContext.getFontMetrics().getHeight();
		gContext.drawChars( data.text.toCharArray(), 0, data.text.length(), 80, data.height/2);
		
		gContext.drawOval(10, data.height/4, 40,40);
		gContext.drawLine(24, data.height/4+8, 36, data.height/4+8);
		gContext.drawLine(24, data.height/4+8, 30, data.height/4+25);
		gContext.drawLine(36, data.height/4+8, 30, data.height/4+25);
		gContext.fillOval(26, data.height/4+28, 8, 8);
		
	}
	@Override
	public Widget getClone() {
		// TODO Auto-generated method stub
		return new MessageDialog(data.groupId, data.sketchId, data.x, data.y, data.width, data.height, data.zOrder, data.title,data.text,data.iconType);
	}
	@Override
	public void update(WidgetData widgetData) {
		this.data.title=widgetData.title;
		this.data.iconType=widgetData.iconType;
		updateCommonDataAndPaint(widgetData);
		
	}
}
