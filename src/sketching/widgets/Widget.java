package sketching.widgets;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import networking.WeSketchClient;

import shared.WeSketchConstants;
import sketching.SketchingFrame;

/**
 * This class describes the common properties of all the widgets in the sketching package.
 * Every Widget is attached to it's sketch by the sketchId property
 * Objects of classes derived from this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public abstract class Widget extends javax.swing.JLabel implements MouseListener, MouseMotionListener, KeyListener{
	/** Constants to paint the widget */
	transient public static final java.awt.Color BACKGROUNDCOLOR= java.awt.Color.WHITE;
	transient public static final java.awt.Color SHADOWCOLOR= java.awt.Color.DARK_GRAY;
	transient public static final java.awt.Color FOREGROUNDCOLOR = new java.awt.Color(230,230,230);
	transient public static final java.awt.Color BORDERCOLOR= java.awt.Color.BLACK;
	transient public static final java.awt.Color HIGHLIGHT_COLOR = new java.awt.Color(240,240,240);
	transient public static byte LEFT_ALIGNMENT=0;
	transient public static byte CENTER_ALIGNMENT=1;
	transient public static byte RIGHT_ALIGNMENT=2;
	transient public static byte LINE_SPACING=4;

	/** Size of the border of the control to allow scaling*/
	transient public static int CONTROL_BORDER_SIZE=6;
	transient public static int MIN_SIZE=CONTROL_BORDER_SIZE*3;

	//Fields to manage the interaction with the widget
	/** if focused the widget should paint controls to scale */
	transient public boolean focused = false;
	transient private boolean dragStarted=false;
	transient private int draggingX, draggingY; //these two are for dragging
	transient private boolean scaleStarted=false;
	transient private boolean scalingNW=false,scalingN=false,scalingNE=false,
	scalingE=false,scalingW=false,
	scalingSW=false,scalingS=false,scalingSE=false;

	//Elements needed to paint the component on screen
	//transient 
	public java.awt.image.BufferedImage imageBuffer =null;
	public java.awt.Graphics2D gContext=null;

	//------------------------------DATA TO SEND THROUGH THE NET---------------

	public WidgetData data = new WidgetData();
	//------------------------------------------------------------------------

	public Widget(){
		this.setFocusable(true);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
	}
	public void setData(WidgetData data) {
		this.data = data;
	}
	public void clearListeners(){
		this.removeMouseListener(this);
		this.removeMouseMotionListener(this);
	}
	public void updateCommonDataAndPaint(WidgetData widgetData){
		this.data.enabled = widgetData.enabled;
		this.data.zOrder = widgetData.zOrder;
		this.data.groupId = widgetData.groupId;
		this.data.text = widgetData.text;
		this.setSize(widgetData.width, widgetData.height);
		this.setLocation(widgetData.x, widgetData.y);
		repaint();
	}
	/** Overriden by every subclass. Updates data and calls a repaint */
	public abstract void update(WidgetData widgetData);

	/** This method creates an exact copy of this widget */
	public abstract Widget getClone();

	@Override
	public void setSize(int w, int h){
		super.setSize(w, h);
		data.width= w;
		data.height=h;
		imageBuffer=null; //forces the next repaint
	}
	@Override
	public void setLocation(int x, int y){
		super.setLocation(x, y);
		this.data.x= x;
		this.data.y=y;
	}
	@Override
	protected void paintComponent(Graphics g) {
		if( imageBuffer==null ){
			imageBuffer = new java.awt.image.BufferedImage(this.getWidth(),this.getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
			gContext= imageBuffer.createGraphics();
			drawWidgetInside(gContext,data);
		}
		g.drawImage(imageBuffer, 0, 0, null);
		if(focused || data.remotelyFocused!=-1){
			drawEditingControls(g);
		}
	}
//	@Override
//	public void paint(Graphics g){
//		if( imageBuffer==null ){
//			imageBuffer = new java.awt.image.BufferedImage(this.getWidth(),this.getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
//			gContext= imageBuffer.createGraphics();
//			drawWidgetInside(gContext,data);
//		}
//		g.drawImage(imageBuffer, 0, 0, null);
//		if(focused || data.remotelyFocused!=-1){
//			drawEditingControls(g);
//		}
//	}
	/** Every child should override this method with the appropriate drawing */
	abstract public void drawWidgetInside(Graphics2D gContext, WidgetData data);
	/** This method draws 4 squres in every corner of the widget to show availability of control*/
	private void drawEditingControls(Graphics g) {
		int cw=CONTROL_BORDER_SIZE;
		g.setColor(java.awt.Color.WHITE);
		if(data.remotelyFocused!=-1 && data.remotelyFocused!=WeSketchClient.getColor()){
			g.setColor(WeSketchConstants.RGBCOLOR[data.remotelyFocused]);
		}
		g.fillRect(1, 1, cw, cw);	//top-left control
		g.fillRect(1, data.height-1-cw, cw, cw);	//bottom-left control
		g.fillRect(data.width-1-cw, 1, cw, cw);	//top-right control
		g.fillRect(data.width-1-cw, data.height-1-cw, cw, cw);	//bottom-right control
		g.fillRect(1, data.height/2-1-cw/2, cw, cw);	//left control
		g.fillRect(data.width-1-cw, data.height/2-1-cw/2, cw, cw);	//right control
		g.fillRect(data.width/2-1-cw/2, 1, cw, cw);	//top control
		g.fillRect(data.width/2-1-cw/2, data.height-1-cw, cw, cw);	//bottom control
		
		g.setColor(java.awt.Color.BLACK);
		g.drawRect(1, 1, cw, cw);	//top-left control
		g.drawRect(1, data.height-1-cw, cw, cw);	//bottom-left control
		g.drawRect(data.width-1-cw, 1, cw, cw);	//top-right control
		g.drawRect(data.width-1-cw, data.height-1-cw, cw, cw);	//bottom-right control
		g.drawRect(1, data.height/2-1-cw/2, cw, cw);	//left control
		g.drawRect(data.width-1-cw, data.height/2-1-cw/2, cw, cw);	//right control
		g.drawRect(data.width/2-1-cw/2, 1, cw, cw);	//top control
		g.drawRect(data.width/2-1-cw/2, data.height-1-cw, cw, cw);	//bottom control
	}
	/** draws an internal scrollbar for the widget*/
	public void drawInternalScrollBar(Graphics2D gContext, WidgetData data){
		int scrW=12;
		gContext.setColor(FOREGROUNDCOLOR);
		gContext.fillRect(data.width-scrW-1, 2, scrW-1, data.height-4);
		gContext.setColor(BORDERCOLOR);
		gContext.drawLine(data.width-scrW, scrW, data.width-2, scrW);
		gContext.drawLine(data.width-scrW, data.height-scrW, data.width-2, data.height-scrW);
		gContext.drawRect(data.width-scrW-1, 1, scrW-1, data.height-3);
		//Triangle
		int[] xPoints={data.width-scrW+1,data.width-scrW/2,data.width-3};
		int[] yPoints={ scrW-3 ,3, scrW-3 };
		int[] y2Points={ data.height-scrW+3 ,data.height-3, data.height-scrW+3 };
		gContext.setColor(BORDERCOLOR);
		gContext.fillPolygon(xPoints, yPoints, 3);
		gContext.fillPolygon(xPoints, y2Points, 3);
		//Center Button
		gContext.setColor(java.awt.Color.DARK_GRAY);
		gContext.fillRect(data.width-scrW, scrW+3, scrW-1, scrW );
		gContext.setColor(java.awt.Color.LIGHT_GRAY);
		gContext.fillRect(data.width-scrW-1, scrW+2, scrW-1, scrW );
		gContext.setColor(java.awt.Color.BLACK);
		gContext.drawRect(data.width-scrW-1, scrW+2, scrW-1, scrW );
	}
	private void focus() {
//		if(data.remotelyFocused!=-1 && data.remotelyFocused!=CVEGUIDClient.getColor()){
//			System.out.println("Widget is being used by another one");
//			return;
//		}
		SketchingFrame.setSelectedWidget(this);
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
		if( focused ){
			data.remotelyFocused=WeSketchClient.getColor();
		}else{
			if(data.remotelyFocused==WeSketchClient.getColor()){
				data.remotelyFocused=-1;
			}
		}
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.ARROW_CURSOR;
		repaint();//TODO not sure if it works
	}
	public boolean isInside(int x1, int y1, int x2, int y2) {
		//clipping: if any x is inside and any y is inside -> true
		if( (data.x>=x1&&data.x<=x2 || data.x+data.width>=x1&&data.x+data.width<=x2) &&
				(data.y>=y1&&data.y<=y2 || data.y+data.height>=y1&&data.y+data.height<=y2)){

			return true;
		}
		return false;
	}
	/** should be overridden by Widgets that have a text attribute that can be quick-editable */
	public void quickUpdate(String text){
		this.data.text = text;
		drawWidgetInside(gContext, data);
		repaint();
		SketchingFrame.sendWidgetToServer(data);
	}
	private void quickEdit() {
		SketchingFrame.displayQuickEditPanel(this);
	}
	//-----------------------------------------------------------------------------------
	//	MOUSELISTENERS
	//-----------------------------------------------------------------------------------
	//These two methods will be standard for every Widget
	@Override
	public void mouseDragged(MouseEvent e) {
		//if drag started on widget border and it was already focused
		if( scaleStarted || !dragStarted && focused && (e.getX()<=CONTROL_BORDER_SIZE || e.getX()>=data.width-1-CONTROL_BORDER_SIZE || 
				e.getY()<=CONTROL_BORDER_SIZE || e.getY()>=data.height-1-CONTROL_BORDER_SIZE) ){
			//do not allow scale for several selected widgets
			if(SketchingFrame.skfInstance.selectedWidgets.size()>1){
				return;
			}
			scale(e.getX(),e.getY());
		}
		else{
			if(!dragStarted){
				dragStarted=true;
				draggingX = e.getX();
				draggingY = e.getY();
				this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.MOVE_CURSOR;
			}
			setLocation(data.x+e.getX()-draggingX, data.y+e.getY()-draggingY);
			if(focused){
				SketchingFrame.skfInstance.setLocationForSelectedWidgets(e.getX()-draggingX,e.getY()-draggingY,this);
			}else{
				SketchingFrame.releaseSelectedWidgets();
			}
		}
		SketchingFrame.sendWidgetToServer(data);
	}
	private void scale(int cx, int cy) {
		if( !scaleStarted ){
			//scaling just started, determine where it started
			scaleStarted=true;
			//if it was in the north part of widget
			if(cy<=CONTROL_BORDER_SIZE){
				if( cx<=CONTROL_BORDER_SIZE ){
					scalingNW=true;
					this.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
					SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_HORIZONTAL_CURSOR;
				}else
					if(cx>=data.width-1-CONTROL_BORDER_SIZE){
						scalingNE=true;
						this.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
						SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_HORIZONTAL_CURSOR;
					}else{
						scalingN=true;
						this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
						SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_VERTICAL_CURSOR;
					}
			}
			else
				//if it was in the south part of widget
				if(cy>=data.height-1-CONTROL_BORDER_SIZE){
					if( cx<=CONTROL_BORDER_SIZE ){
						scalingSW=true;
						this.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
						SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_VERTICAL_CURSOR;
					}else
						if(cx>=data.width-1-CONTROL_BORDER_SIZE){
							scalingSE=true;
							this.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
							SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_VERTICAL_CURSOR;
						}else{
							scalingS=true;
							this.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
							SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_VERTICAL_CURSOR;
						}	
				}
			//it was in the east or in the west
				else{
					if( cx<=CONTROL_BORDER_SIZE ){
						scalingW=true;
						this.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
						SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_HORIZONTAL_CURSOR;
					}else{
						scalingE=true;
						this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
						SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_HORIZONTAL_CURSOR;
					}
				}
		}
		int newW=data.width, newH=data.height;
		if(scalingNE||scalingE||scalingSE){ newW=cx+2; }
		//else
		if(scalingSW||scalingS||scalingSE){ newH=cy+2; }
		//else
		if(scalingNW||scalingW||scalingSW){
			newW-=cx-2;
			setLocation(data.x+cx-2,data.y);
		}
		else
			if(scalingNW||scalingN||scalingNE){
				newH-=cy-2;
				setLocation(data.x,data.y+cy-2);
			}
		if(newW<MIN_SIZE){ newW = MIN_SIZE; }
		if(newH<MIN_SIZE){ newH = MIN_SIZE; }
		setSize(newW, newH);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		if(focused){
			if(e.getX()<=CONTROL_BORDER_SIZE || e.getX()>=data.width-1-CONTROL_BORDER_SIZE ){
				this.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
				SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_HORIZONTAL_CURSOR;
			}else
				if(	e.getY()<=CONTROL_BORDER_SIZE || e.getY()>=data.height-1-CONTROL_BORDER_SIZE ){
					this.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
					SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.EXPAND_VERTICAL_CURSOR;
				}else{
					this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.ARROW_CURSOR;
				}
		}
		SketchingFrame.sendCursorOverWidget(e.getX()+data.x, e.getY()+data.y,data.id);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		focus();
		//double left click
		if(e.getButton()==1 && e.getClickCount()==2){
			quickEdit();
			return;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		dragStarted=false;
		scaleStarted=false;
		scalingNW=scalingN=scalingNE=scalingW=scalingE=scalingSW=scalingS=scalingSE=false;
		if(this.focused==false){
			focus();
		}
		checkPopupMenu(e);
	}
	@Override
	public void keyReleased(KeyEvent e) {
		//only works in jme :(
		if( e.getKeyCode()== KeyEvent.VK_CONTROL ){
			SketchingFrame.skfInstance.setControlDown(false);
			return;
		}
		if(e.getKeyCode()==KeyEvent.VK_DELETE){
			SketchingFrame.deleteSelectedWidgets();
			return;
		}
		if(e.getKeyCode()==KeyEvent.VK_F2){
			quickEdit();
			return;
		}
		if ( e.isControlDown() ){
			if (e.getKeyCode() == KeyEvent.VK_C){
				SketchingFrame.copyCurrentWidget();
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e){}
	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		SketchingFrame.skfInstance.myCursor.type = WeSketchConstants.ARROW_CURSOR;
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if( SketchingFrame.skfInstance.isControlDown() ){
			SketchingFrame.addWidgetToSelection(this);
			return;
		}
		checkPopupMenu(e);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_CONTROL || e.isControlDown()){
			SketchingFrame.skfInstance.setControlDown(true);
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	public java.awt.image.BufferedImage getImageBuffer() {
		if(imageBuffer==null){
			repaint();
		}
		return imageBuffer;
	}
	private void checkPopupMenu(MouseEvent me) {
		if(me.isPopupTrigger())
			SketchingFrame.skfInstance.showPopupMenu(me.getComponent(),me.getX(),me.getY());
	}
}
