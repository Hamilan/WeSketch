package sketching;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import networking.WeSketchClient;
import sketching.widgets.Widget;

import com.jme.input.MouseInput;

public class SketchingPanel extends JPanel {
	int xDragIni,yDragIni;
	int w, h;
	private boolean selectionIsDrawn=false;
	private boolean drawRectangle=false;
	private SketchingFrame skFrame;

	private JPopupMenu popup;
	private JMenuItem miPasteFromClipboard;
	ArrayList<Widget> widgets = new ArrayList<Widget>();

	public SketchingPanel(SketchingFrame skFrame) {
		this.skFrame = skFrame;
		this.setFocusable(false);//TODO to avoid losing cursors when this panel gets the focus 

		setPopup();

		SketchPanelMouseListener skListener = new SketchPanelMouseListener();
		this.addMouseListener( skListener );
		this.addMouseMotionListener( skListener );
	}

	private void setPopup() {
		popup = new JPopupMenu();

		JMenuItem miDuplicate = new JMenuItem("Duplicar Widgets");
		miDuplicate.setToolTipText("Duplicar los widgets seleccionados");
		miDuplicate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SketchingFrame.copyCurrentWidget();
			}
		});
		popup.add(miDuplicate);

		JMenuItem miCopyToClipboard = new JMenuItem("Copiar Widgets");
		miCopyToClipboard.setToolTipText("Copiar para pegar después\n en este u otro Bosquejo");
		miCopyToClipboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				skFrame.copyToSession();
			}
		});
		popup.add(miCopyToClipboard);

		miPasteFromClipboard = new JMenuItem("Pegar Widgets");
		miPasteFromClipboard.setToolTipText("Pegar widgets copiados desde este u otro Bosquejo");
		miPasteFromClipboard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				skFrame.pasteFromSession();
			}
		});
		popup.add(miPasteFromClipboard);

		JMenuItem miDelete = new JMenuItem("Eliminar Widgets");
		miDelete.setToolTipText("Eliminar los widgets seleccionados");
		miDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				skFrame.deleteSelectedWidgets();
			}
		});
		popup.add(miDelete);

		popup.addSeparator();

		JMenuItem miToFront = new JMenuItem("Traer al frente");
		miToFront.setToolTipText("Traer Widgets hacia el frente");
		miToFront.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SketchingFrame.bringSelectedToFront();
			}
		});
		popup.add(miToFront);

		JMenuItem miToBottom = new JMenuItem("Enviar al fondo");
		miToBottom.setToolTipText("Enviar widgets hacia el fondo");
		miToBottom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SketchingFrame.sendSelectedToBottom();
			}
		});
		popup.add(miToBottom);
		this.add(popup);
	}

	public void paint(Graphics g){
		super.paint(g);
		if(drawRectangle){
			int xd=xDragIni,yd=yDragIni,wd=w,hd=h;
			if( w < 0 ){
				xd = xDragIni+w; wd = -w;
			}
			if( h<0 ){
				yd = yDragIni+h; hd = -h;
			}
			BasicGraphicsUtils.drawDashedRect(g,xd,yd,wd,hd);
		}
	}
	public void paintSelectionRectangle(int x, int y, int w, int h){
		xDragIni = x;
		yDragIni = y; 
		this.w = w;
		this.h = h;
		drawRectangle=true;
		repaint();
	}
	public void setDrawRectangle(boolean drawRectangle) {
		this.drawRectangle = drawRectangle;
	}

	class SketchPanelMouseListener extends MouseAdapter {
		/** isDragging will allow us to draw a Rectangle for Selection */
		boolean isDragging = false;
		int xIni,yIni;

		@Override
		public void mouseDragged(MouseEvent me) {
			popup.setVisible(false);
			//if left button is not pressed, then ignore
			if( skFrame.myClient!=null &&  MouseInput.get().isButtonDown(0)==false ){
				return;
			}
			if(!isDragging){
				isDragging = true;
				xIni = me.getX();
				yIni = me.getY(); 
				skFrame.releaseSelectedWidgets();
			}
			paintSelectionRectangle(xIni, yIni, (me.getX()-xIni), (me.getY()-yIni));
		}
		@Override
		public void mouseReleased(MouseEvent me) {
			if(isDragging){
				int x1= xIni, y1=yIni, x2=me.getX(), y2=me.getY();
				if( x1 > x2 ){
					x1 = x2;
					x2= xIni;
				}
				if( y1 > y2 ){
					y1 = y2;
					y2= yIni;
				}
				skFrame.selectWidgetsInside(x1,y1,x2,y2);
				isDragging=false;
			}
			setDrawRectangle(false);
			repaint();
			checkPopupMenu(me);
		}
		@Override
		public void mousePressed(MouseEvent me) {
			setDrawRectangle(false);
			skFrame.releaseSelectedWidgets();
			//skFrame.repaint();
			checkPopupMenu(me);
		}
		private void checkPopupMenu(MouseEvent me) {
			if(me.isPopupTrigger())
				showPopupMenu(me.getComponent(), me.getX(), me.getY());
		}
	}

	void showPopupMenu(Component component, int x, int y) {
		if(WeSketchClient.getWidgetsClipBoard().size()==0){
			miPasteFromClipboard.setVisible(false);
		}else{
			miPasteFromClipboard.setVisible(true);
		}
		popup.show(component, x, y);
	}

	@Override
	public void paintChildren(Graphics g) {
		super.paintComponents(g);
//		int i = 0;
//		for (Component c : this.getComponents()) {
//			if(c instanceof Widget){
//				((Widget)c).repaint();
//			}
//			i++;
//		}
	}
//	
//	@Override
//	public Component add(Component widget, int index) {
//		super.add(widget, index);
//		widgets.add(index,(Widget)widget);
//		return widget;
//	}
}
