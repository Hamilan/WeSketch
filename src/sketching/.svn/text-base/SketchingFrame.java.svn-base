package sketching;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import networking.WeSketchClient;
import shared.WeSketchConstants;
import shared.Cursor;
import shared.SessionParticipant;
import sketching.SketchingPanel.SketchPanelMouseListener;
import sketching.widgets.Button;
import sketching.widgets.CheckBox;
import sketching.widgets.ComboBox;
import sketching.widgets.Image;
import sketching.widgets.Label;
import sketching.widgets.List;
import sketching.widgets.RadioButton;
import sketching.widgets.TextArea;
import sketching.widgets.TextField;
import sketching.widgets.Widget;
import sketching.widgets.WidgetData;

@SuppressWarnings("serial")
public class SketchingFrame extends JInternalFrame implements ActionListener {

	private JTabbedPane widgetsTabs;
	private JPanel panelDialogos;
	private JScrollPane scrollPaneLayersTree;
	private JTree layersTree;

	private JTextField findBox;
	private JPanel coeditorsPanel;
	private JScrollPane panelEncontrados;
	private JPanel panelFindComponent;

	private JSeparator jSeparatorView1;
	private JMenuItem miHowTo;
	private JMenu menuHelp;
	private JMenuItem miZoom50;
	private JMenuItem miZoom75;
	private JMenuItem miZoom100;
	private JMenuItem miShowGrid;
	private JMenuItem miPreview;
	private JMenu menuView;
	private JMenuItem miExport;
	private JSeparator jSeparatorSketch1;
	private JMenuItem miUngroup;
	private JMenuItem miGroup;
	private JSeparator jSeparatorEdit3;
	private JMenuItem miSelectAll;

	private JPanel panelTree;
	private JPanel panelProperties;
	private JTabbedPane propertiesAndLayersPane;
	private SketchingPanel sketchPanel;
	private JPanel panelMenus;
	private JPanel panelContainers;
	private JPanel panelComponentes;

	private JSeparator jSeparatorEdit2;
	private JMenuItem miDuplicate;
	private JMenuItem miDelete;
	private JMenuItem miPaste;
	private JMenuItem miCut;
	private JMenuItem miCopy;
	private JSeparator jSeparatorEdit1;
	private JMenuItem miRedo;
	private JMenuItem miUndo;
	private JMenu menuEdit;
	private JMenuItem miClose;
	private JMenuItem miSave;
	private JMenu jMenu1;
	private JMenuBar skMenuBar;

	static JInternalFrame quickEditPanel;
	static JTextArea quickEditText;

	//	private JButton jButtonInvitar;
	Insets defaultMargin = new Insets(2,2,2,2);

	JLabel avatarsLabels[] = new JLabel[WeSketchConstants.MAX_SESSION_PARTICIPANTS];
	JLabel sharedCursors[] = new JLabel[WeSketchConstants.MAX_SESSION_PARTICIPANTS];
	Cursor participantsCursors[] = new Cursor[WeSketchConstants.MAX_SESSION_PARTICIPANTS];

	public Sketch mySketch;
	public static WeSketchClient myClient;
	public Cursor myCursor;
	private MouseSender myMouseSender;
	private ArrayList<Widget> visualWidgets = new ArrayList<Widget>();
	private boolean controlDown=false;
	public static Widget selectedWidget=null;
	public static ArrayList<Widget> selectedWidgets=new ArrayList<Widget>();
	//TODO: si se mostraran varias sketchingFrame, esto generaría problemas (static)

	/** Currently displayed options panel for the selected widget*/
	public static JPanel optionsPanelDisplayed=null;
	public static SketchingFrame skfInstance;
	/** holds every panel so that other's Mouse icons can be added to the top frame */
	private static JPanel framesContainer;
	/** The frame's content pane*/
	private static java.awt.Container contentPane;

	/**
	 * Auto-generated main method to display this JFrame
	 */
	public static void main(String[] args) {
		javax.swing.JFrame x = new javax.swing.JFrame("Test");
		x.setSize(1020,520);

		Sketch defaultSketch = new Sketch(0,"Sketch de prueba","Prueba","Yo","R01");
		SketchingFrame inst = new SketchingFrame(defaultSketch,null );
		inst.setVisible(true);

		x.add(inst);
		x.setVisible(true);
		x.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public SketchingFrame(Sketch s, WeSketchClient client) {
		super("Editando Bosquejo: "+s.title);
		skfInstance=this;
		mySketch = s;
		myClient = client;
		if(myClient!=null){
			myCursor = new Cursor(WeSketchConstants.ARROW_CURSOR,myClient.myParticipantInfo.chairIndexOrColor,1,1,mySketch.id);
		}else{
			myCursor = new Cursor(WeSketchConstants.ARROW_CURSOR,1,1,1,mySketch.id);
		}
		initGUI();
		setListeners();
	}

	private void setListeners(){
		SketchingFrameListener skFListener= new SketchingFrameListener();
		this.addInternalFrameListener(skFListener);

		myMouseSender = new MouseSender();
		sketchPanel.addMouseListener(myMouseSender);
		sketchPanel.addMouseMotionListener(myMouseSender);
		widgetsTabs.addMouseListener(myMouseSender);
		widgetsTabs.addMouseMotionListener(myMouseSender);
		propertiesAndLayersPane.addMouseListener(myMouseSender);
		propertiesAndLayersPane.addMouseMotionListener(myMouseSender);
		coeditorsPanel.addMouseListener(myMouseSender);
		coeditorsPanel.addMouseMotionListener(myMouseSender);
		contentPane.addMouseListener(myMouseSender);//??
		contentPane.addMouseMotionListener(myMouseSender);//??

		panelComponentes.addMouseListener(myMouseSender);
		panelComponentes.addMouseMotionListener(myMouseSender);
		panelContainers.addMouseListener(myMouseSender);
		panelContainers.addMouseMotionListener(myMouseSender);
		//panelDialogos.addMouseListener(myMouseSender);
		//panelDialogos.addMouseMotionListener(myMouseSender);
		//panelMenus.addMouseListener(myMouseSender);
		//panelMenus.addMouseMotionListener(myMouseSender);
		//panelFindComponent.addMouseListener(myMouseSender);
		//panelFindComponent.addMouseMotionListener(myMouseSender);

	}

	private void initGUI() {
		try {
			this.setClosable(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setSize(1020,540);	//Necessary
			this.setPreferredSize(new Dimension(1020,540));
			//TODO next line doesn't work well with JME, affects screen coordinates
			//we have to find another way to include the menubar
			//this.setJMenuBar(getSKMenuBar());
			setContentPane();
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setContentPane() {
		contentPane=getContentPane();
		contentPane.setLayout(null);

		framesContainer = new JPanel(new BorderLayout(1,1));
		framesContainer.add( getPropertiesAndLayersPane(),BorderLayout.WEST );
		framesContainer.add( getSketchPanel(),BorderLayout.CENTER );
		framesContainer.add( getCoeditorsPanel(),BorderLayout.EAST );
		framesContainer.add( getWidgetsTabs(),BorderLayout.NORTH );
		framesContainer.setBounds(0, 0, 1010, 500);
		contentPane.add(framesContainer);
	}

	private JMenuBar getSKMenuBar() {
		{
			skMenuBar = new JMenuBar();
			{
				jMenu1 = new JMenu();
				skMenuBar.add(jMenu1);
				jMenu1.setText("Sketch");
				{
					miSave = new JMenuItem();
					jMenu1.add(miSave);
					miSave.setText("Save");
					miSave.setToolTipText("Save this sketch");
					miSave.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed S"));
					miSave.setActionCommand("save");
				}
				{
					miExport = new JMenuItem();
					jMenu1.add(miExport);
					miExport.setText("Export");
					miExport.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed E"));
				}
				{
					jSeparatorSketch1 = new JSeparator();
					jMenu1.add(jSeparatorSketch1);
				}
				{
					miClose = new JMenuItem();
					jMenu1.add(miClose);
					miClose.setText("Close");
					miClose.setToolTipText("Close this sketch");
					miClose.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed F4"));
				}
			}
			{
				menuEdit = new JMenu();
				skMenuBar.add(menuEdit);
				menuEdit.setText("Edit");
				{
					miUndo = new JMenuItem();
					menuEdit.add(miUndo);
					miUndo.setText("Undo");
					miUndo.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed Z"));
				}
				{
					miRedo = new JMenuItem();
					menuEdit.add(miRedo);
					miRedo.setText("Redo");
					miRedo.setAccelerator(KeyStroke.getKeyStroke("shift pressed Z"));
				}
				{
					jSeparatorEdit1 = new JSeparator();
					menuEdit.add(jSeparatorEdit1);
				}
				{
					miCopy = new JMenuItem();
					menuEdit.add(miCopy);
					miCopy.setText("Copy");
					miCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed C"));
				}
				{
					miCut = new JMenuItem();
					menuEdit.add(miCut);
					miCut.setText("Cut");
					miCut.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed X"));
				}
				{
					miPaste = new JMenuItem();
					menuEdit.add(miPaste);
					miPaste.setText("Paste");
					miPaste.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed V"));
				}
				{
					miDelete = new JMenuItem();
					menuEdit.add(miDelete);
					miDelete.setText("Delete");
				}
				{
					miDuplicate = new JMenuItem();
					menuEdit.add(miDuplicate);
					miDuplicate.setText("Duplicate");
					miDuplicate.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed D"));
				}
				{
					jSeparatorEdit2 = new JSeparator();
					menuEdit.add(jSeparatorEdit2);
				}
				{
					miSelectAll = new JMenuItem();
					menuEdit.add(miSelectAll);
					miSelectAll.setText("Select All");
					miSelectAll.setAccelerator(KeyStroke.getKeyStroke("alt pressed A"));
				}
				{
					jSeparatorEdit3 = new JSeparator();
					menuEdit.add(jSeparatorEdit3);
				}
				{
					miGroup = new JMenuItem();
					menuEdit.add(miGroup);
					miGroup.setText("Group");
					miGroup.setAccelerator(KeyStroke.getKeyStroke("alt pressed G"));
				}
				{
					miUngroup = new JMenuItem();
					menuEdit.add(miUngroup);
					miUngroup.setText("Ungroup");
					miUngroup.setAccelerator(KeyStroke.getKeyStroke("shift ctrl pressed G"));
				}
			}
			{
				menuView = new JMenu();
				skMenuBar.add(menuView);
				menuView.setText("View");
				{
					miPreview = new JMenuItem();
					menuView.add(miPreview);
					miPreview.setText("Preview");
					miPreview.setAccelerator(KeyStroke.getKeyStroke("F5"));
				}
				{
					miShowGrid = new JMenuItem();
					menuView.add(miShowGrid);
					miShowGrid.setText("Show Grid");
					miShowGrid.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed F5"));
				}
				{
					jSeparatorView1 = new JSeparator();
					menuView.add(jSeparatorView1);
				}
				{
					miZoom100 = new JMenuItem();
					menuView.add(miZoom100);
					miZoom100.setText("Zoom 100%");
					miZoom100.setAccelerator(KeyStroke.getKeyStroke("shift ctrl pressed 1"));
				}
				{
					miZoom75 = new JMenuItem();
					menuView.add(miZoom75);
					miZoom75.setText("Zoom 75%");
					miZoom75.setAccelerator(KeyStroke.getKeyStroke("shift ctrl pressed 2"));
				}
				{
					miZoom50 = new JMenuItem();
					menuView.add(miZoom50);
					miZoom50.setText("Zoom50%");
					miZoom50.setAccelerator(KeyStroke.getKeyStroke("shift ctrl pressed 3"));
				}

			}
			{
				menuHelp = new JMenu();
				skMenuBar.add(menuHelp);
				menuHelp.setText("Help");
				{
					miHowTo = new JMenuItem();
					menuHelp.add(miHowTo);
					miHowTo.setText("How to Edit");
					miHowTo.setAccelerator(KeyStroke.getKeyStroke("ctrl pressed F1"));
				}
			}
		}
		return skMenuBar;
	}

	private JTabbedPane getWidgetsTabs() {
		widgetsTabs = new JTabbedPane();
		widgetsTabs.setSize(new Dimension(640, 75));
		widgetsTabs.setPreferredSize(new Dimension(640, 75));

		widgetsTabs.addTab("Componentes", null, getPanelComponentes(), null);
		widgetsTabs.addTab("Contenedores", null, getPanelContainers(), null);
		//widgetsTabs.addTab("Menúes", null, getPanelMenus(), null);
		//widgetsTabs.addTab("Diálogos", null, getPanelDialogos(), null);
		//widgetsTabs.addTab("Diálogos", null, getPanelBuscar(),null);
		return widgetsTabs;
	}

	private Component getPanelBuscar() {
		panelFindComponent = new JPanel();
		panelFindComponent.setPreferredSize(new java.awt.Dimension(625, 45));
		{
			findBox = new JTextField();
			panelFindComponent.add(findBox);
			findBox.setPreferredSize(new java.awt.Dimension(92, 23));
		}
		{
			panelEncontrados = new JScrollPane();
			panelFindComponent.add(panelEncontrados);
			panelEncontrados.setPreferredSize(new java.awt.Dimension(520, 34));
		}
		return panelFindComponent;
	}

	private Component getPanelDialogos() {
		panelDialogos = new JPanel();
		return panelDialogos ;
	}

	private JPanel getPanelMenus() {
		panelMenus = new JPanel();
		return panelMenus;
	}

	private JPanel getPanelContainers() {
		panelContainers = new JPanel();

		sketching.widgets.Panel panel = new sketching.widgets.Panel(0,0,10,10,50,50,0,"Panel");
		panel.setPreferredSize(new Dimension(50, 40));
		panel.clearListeners();
		WidgetsToolBarListener wpanel = new WidgetsToolBarListener(panel);
		panel.addMouseMotionListener(wpanel);
		panel.addMouseListener(wpanel);
		panelContainers.add(panel);

		sketching.widgets.Frame frame = new sketching.widgets.Frame(0,0,10,10,50,50,0,"Frame",sketching.widgets.Frame.CLOSE);
		frame.setPreferredSize(new Dimension(50, 40));
		frame.clearListeners();
		WidgetsToolBarListener wframe = new WidgetsToolBarListener(frame);
		frame.addMouseMotionListener(wframe);
		frame.addMouseListener(wframe);
		panelContainers.add(frame);

		return panelContainers;
	}

	private JPanel getPanelComponentes() {
		panelComponentes = new JPanel(new FlowLayout());
		try{
			Label l = new Label(0, 0, 10, 10, 50, 25, 0, "Etiqueta", Widget.RIGHT_ALIGNMENT);
			l.setPreferredSize(new Dimension(50, 25));
			panelComponentes.add(l);
			l.clearListeners();
			WidgetsToolBarListener wl = new WidgetsToolBarListener(l);
			l.addMouseMotionListener(wl);
			l.addMouseListener(wl);

			TextField tf = new TextField(0,0,10,10,50,25,0,"CajaText");
			tf.setPreferredSize(new Dimension(60, 25));
			panelComponentes.add(tf);
			tf.clearListeners();
			WidgetsToolBarListener wtf = new WidgetsToolBarListener(tf);
			tf.addMouseMotionListener(wtf);
			tf.addMouseListener(wtf);

			Button b = new Button(0,0,10,10,50,25,0,"Botón",null);
			b.setPreferredSize(new Dimension(50, 25));
			panelComponentes.add(b);
			b.clearListeners();
			WidgetsToolBarListener wb = new WidgetsToolBarListener(b);
			b.addMouseMotionListener(wb);
			b.addMouseListener(wb);

			RadioButton r = new RadioButton(0, 0, 10, 10, 50, 25, 0, "Radio",true);
			r.setPreferredSize(new Dimension(50, 25));
			panelComponentes.add(r);
			r.clearListeners();
			WidgetsToolBarListener wr = new WidgetsToolBarListener(r);
			r.addMouseMotionListener(wr);
			r.addMouseListener(wr);

			CheckBox ckb= new CheckBox(0, 0, 10, 10, 80,25, 0, "CheckBox",true);
			ckb.setPreferredSize(new Dimension(70, 25));
			panelComponentes.add(ckb);
			ckb.clearListeners();
			WidgetsToolBarListener wckb= new WidgetsToolBarListener(ckb);
			ckb.addMouseMotionListener(wckb);
			ckb.addMouseListener(wckb);

			ComboBox cmb= new ComboBox(0, 0, 10, 10, 80, 0, "Combo\nBox",true);
			cmb.setPreferredSize(new Dimension(70, 40));
			panelComponentes.add(cmb);
			cmb.clearListeners();
			WidgetsToolBarListener wcmb= new WidgetsToolBarListener(cmb);
			cmb.addMouseMotionListener(wcmb);
			cmb.addMouseListener(wcmb);

			TextArea ta= new TextArea(0, 0, 10, 10, 70,40, 0, "Área\nde texto");
			ta.setPreferredSize(new Dimension(70, 40));
			panelComponentes.add(ta);
			ta.clearListeners();
			WidgetsToolBarListener wta= new WidgetsToolBarListener(ta);
			ta.addMouseMotionListener(wta);
			ta.addMouseListener(wta);

			List list = new List(0, 0, 10, 10, 50, 25, 0, "Lista1\nLista2");
			list.setPreferredSize(new Dimension(50, 40));
			panelComponentes.add(list);
			list.clearListeners();
			WidgetsToolBarListener wlist = new WidgetsToolBarListener(list);
			list.addMouseMotionListener(wlist);
			list.addMouseListener(wlist);

			Image img = new Image(0, 0, 10, 10, 50, 25, 0, null);
			img.setPreferredSize(new Dimension(50, 40));
			panelComponentes.add(img);
			img.clearListeners();
			WidgetsToolBarListener wimg= new WidgetsToolBarListener(img);
			img.addMouseMotionListener(wimg);
			img.addMouseListener(wimg);

		}catch(Exception e){
			System.out.println("Something wrong creating widgets toolbar "+e.getMessage());
		}
		//...
		return panelComponentes;
	}

	private JPanel getSketchPanel() {
		sketchPanel = new SketchingPanel(this);
		sketchPanel.setLayout(null);
		sketchPanel.setSize(new Dimension(640, 400));
		sketchPanel.setPreferredSize(new Dimension(640, 400));
		sketchPanel.setBackground(new java.awt.Color(255,255,255));

		//widgets received from the server
		for (WidgetData widgetData : mySketch.widgets) {
			Class widgetClass;
			try {
				widgetClass = Class.forName("sketching.widgets."+widgetData.widgetType);
				Widget widget = (Widget)widgetClass.newInstance();
				widget.data = widgetData;
				widget.data.widget = widget;
				widget.setLocation(widget.data.x, widget.data.y);
				widget.setSize(widget.data.width, widget.data.height);
				this.visualWidgets.add( widget );
				
				sketchPanel.add( widget );//***
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sketchPanel.repaint();
		return sketchPanel;
	}
	private JPanel getCoeditorsPanel() {
		{
			coeditorsPanel = new JPanel();
			coeditorsPanel.setSize(new Dimension(70, 400));
			coeditorsPanel.setPreferredSize(new Dimension(70, 400));
			coeditorsPanel.setBorder(BorderFactory.createTitledBorder("Editores"));
			{
				for(SessionParticipant p : mySketch.collaborators) {
					//doesn't include my avatar into the chatPanel 
					if(myClient.myParticipantInfo.id==p.id){
						continue;
					}
					addParticipant(p);
				}
			}
			//			{
			//				jButtonInvitar = new JButton();
			//				coeditorsPanel.add(jButtonInvitar);
			//				jButtonInvitar.setText("Invitar");
			//				jButtonInvitar.setSize(60, 32);
			//				jButtonInvitar.setMargin( defaultMargin );
			//				jButtonInvitar.setPreferredSize(new java.awt.Dimension(54, 50));
			//				jButtonInvitar.setToolTipText("Invitar un participante a este bosquejo");
			//				jButtonInvitar.setActionCommand("invitar");
			//			}
		}
		return coeditorsPanel;
	}

	private JTabbedPane getPropertiesAndLayersPane() {
		{
			propertiesAndLayersPane = new JTabbedPane();
			propertiesAndLayersPane.setSize(200, 400);
			propertiesAndLayersPane.setPreferredSize(new Dimension(200, 400));
//			{
//				panelProperties = new JPanel();
//				propertiesAndLayersPane.addTab("Propiedades", null, panelProperties, null);
//				panelProperties.setPreferredSize(new java.awt.Dimension(182, 364));
//			}
//			{
//				panelTree = new JPanel();
//				BorderLayout panelTreeLayout = new BorderLayout();
//				panelTree.setLayout(panelTreeLayout);
//				propertiesAndLayersPane.addTab("Capas", null, panelTree, null);
//				{
//					scrollPaneLayersTree = new JScrollPane();
//					panelTree.add(scrollPaneLayersTree, BorderLayout.CENTER);
//					scrollPaneLayersTree.setPreferredSize(new java.awt.Dimension(28, 43));
//					{
//						layersTree = new JTree();
//						layersTree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Sketch")));
//						scrollPaneLayersTree.setViewportView(layersTree);
//					}
//				}
//			}
		}
		return propertiesAndLayersPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//actions from Menus
	}

	/**
	 * This method is called only by the client when a notification of update Widget has arrived
	 * It will be called only if the widget belongs to the current Sketch 
	 * @param widgetData
	 */
	public void updateWidget(WidgetData widgetData) {
		if(widgetData.sketchId!=mySketch.id)
			return;
		for (WidgetData wdata : mySketch.widgets) {
			if(wdata.id==widgetData.id){
				wdata.update(widgetData);
				findVisualWidgetWithId(wdata.id).updateCommonDataAndPaint(wdata);
				return;
			}
		}
		//if Widget didn't exist
		try {
			//adds the widget to the local copy of the sketch/session
			mySketch.widgets.add(widgetData);
			Class widgetClass = Class.forName("sketching.widgets."+widgetData.widgetType);
			Widget widget = (Widget)widgetClass.newInstance();
			widget.data = widgetData;
			widget.data.widget = widget;
			widget.setLocation(widget.data.x, widget.data.y);
			widget.setSize(widget.data.width, widget.data.height);
			this.visualWidgets.add( widget );
			
			sketchPanel.add( widget,0 );//***
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if(quickEditPanel!=null && quickEditPanel.isVisible()){
			sketchPanel.setComponentZOrder(quickEditPanel, 0);
		}
		
	}

	public void updateCursor(Cursor c) {
		int xAdjust = -6, yAdjust = -5;
		//TODO modificar los cursores de los demás participantes
		c.x+=xAdjust;
		c.y+=yAdjust;
		sharedCursors[c.color].setLocation(c.x, c.y);
		participantsCursors[c.color].setLocation( c.x, c.y);
		if( participantsCursors[c.color].type!= c.type ){
			participantsCursors[c.color].type=c.type;
			sharedCursors[c.color].setIcon(new ImageIcon(getClass().getClassLoader().getResource("sketching/cursors/"+WeSketchConstants.COLORNAME[c.color]+"/"+WeSketchConstants.CURSORFILE[c.type]+".png")));
		}
	}
	/**
	 * Adds the representation of a participant to the SketchingFrame.
	 * -Adds an icon with the color and the name of the user
	 * -Adds a cursor with the color and the name of the user
	 * @param p
	 */
	public void addParticipant(SessionParticipant p ) {
		JLabel jLabelAvatar1 = new JLabel( p.login );
		jLabelAvatar1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/user"+WeSketchConstants.COLORNAME[p.chairIndexOrColor]+"16x16.png")));
		jLabelAvatar1.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabelAvatar1.setVerticalTextPosition(SwingConstants.BOTTOM);
		jLabelAvatar1.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelAvatar1.setIconTextGap(0);
		jLabelAvatar1.setSize(60, 50);
		jLabelAvatar1.setPreferredSize(new java.awt.Dimension(60, 50));
		coeditorsPanel.add(jLabelAvatar1,0);
		avatarsLabels[p.chairIndexOrColor]=jLabelAvatar1;
		coeditorsPanel.repaint();
		coeditorsPanel.validate();

		JLabel jCursor = new JLabel( p.login );
		jCursor.setIcon(new ImageIcon(getClass().getClassLoader().getResource("sketching/cursors/"+WeSketchConstants.COLORNAME[p.chairIndexOrColor]+"/arrow.png")));
		jCursor.setBackground(Color.darkGray); //TODO remove this line
		jCursor.setIconTextGap(0);
		jCursor.setSize(90, 32);
		jCursor.setMaximumSize(new Dimension(90,32));
		jCursor.setPreferredSize(new java.awt.Dimension(90, 32));
		jCursor.setLocation(this.getWidth()/2 - p.chairIndexOrColor*30, 
				this.getHeight()/2 - p.chairIndexOrColor*30);
		jCursor.setHorizontalTextPosition(SwingConstants.RIGHT);
		jCursor.setVerticalTextPosition(SwingConstants.CENTER);
		jCursor.setHorizontalAlignment(SwingConstants.CENTER);
		sharedCursors[p.chairIndexOrColor]=jCursor;
		participantsCursors[p.chairIndexOrColor]=new Cursor();
		contentPane.add(jCursor,0);	//debería ser a todo el frame
		contentPane.repaint();
		contentPane.validate();
	}
	/**
	 * Removes from the SketchingFrame every representation of the participant
	 * identified by id partId.
	 * If a participant with the id provided is not found, the method does nothing.
	 * @param partId
	 */
	public void removeParticipant(int partId) {
		for (SessionParticipant p : mySketch.collaborators) {
			if(p.id==partId){
				//remueve el avatar que se muestra en la ventana
				coeditorsPanel.remove(avatarsLabels[p.chairIndexOrColor]);
				avatarsLabels[p.chairIndexOrColor]=null;
				//oculta también el cursor de este participante
				contentPane.remove(sharedCursors[p.chairIndexOrColor]);
				//TODO test
				sharedCursors[p.chairIndexOrColor]=null;
				participantsCursors[p.chairIndexOrColor]=null;
				repaint();
				return;
			}
		}
	}


	public void cerrarSKFrame() {
		this.dispose();
	}

	private Widget findVisualWidgetWithId(long id){
		for (Widget w : visualWidgets) {
			if(w.data.id==id) return w;
		}
		return null;
	}

	public static void setSelectedWidget(Widget widget) {
		if(!skfInstance.isControlDown()){
			releaseSelectedWidgets();
		}
		SketchingFrame.selectedWidget=widget;
		selectedWidget.setFocused(true);
		selectedWidget.repaint();

		selectedWidgets.add(selectedWidget);

		sendWidgetToServer(selectedWidget.data);
		displayOptionsPanel();
	}

	public static void releaseSelectedWidget() {
		if(selectedWidget!=null){
			selectedWidget.setFocused(false);
			selectedWidget.repaint();
			sendWidgetToServer(selectedWidget.data);
			selectedWidget=null;

			if(optionsPanelDisplayed!=null){
				optionsPanelDisplayed.setVisible(false);
				optionsPanelDisplayed=null;
			}
			if(selectedWidgets.size()>0){
				selectedWidgets.remove(selectedWidget);
			}
		}
		hideQuickEditPanel();
	}

	/**
	 * Adds a visible widget to the SketchPanel. This is called only when a widget
	 * has been dropped from the WidgetsToolBar or copied from another existing one
	 * @param w
	 */
	private void addWidgetToSketch(Widget w, boolean sendToServer){
		this.visualWidgets.add(w);	//adds to a list of visible Widgets
		
		w.data.zOrder = mySketch.getNewZorder();
		sketchPanel.add( w,0 );//***
		
		mySketch.addWidget(w.data);
		//mySketch.widgets.add(w.data);	//adds the data to the local copy of Sketch
		if(sendToServer && myClient!=null){
			myClient.sender.sendWidget(w.data);
		}
		sketchPanel.repaint();
	}

	public static void displayOptionsPanel() {
		String wType = selectedWidget.data.widgetType;
		//TODO
		//selectedWidget.getOptionsPanel();
//		System.out.println("Display options for "+wType);
	}

	private static void createQuickEditPanel() {
		quickEditPanel=new JInternalFrame();
		quickEditPanel.setSize(300,100);
		quickEditPanel.setPreferredSize(new Dimension(300,100));
		
		javax.swing.plaf.InternalFrameUI ifu= quickEditPanel.getUI();
		((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);

		JScrollPane scp = new JScrollPane();
		quickEditText = new javax.swing.JTextArea(  );
		scp.setViewportView(quickEditText);
		scp.setSize(300, 70);
		quickEditPanel.add(scp,BorderLayout.CENTER);
		JButton updateButton = new JButton("Actualizar");
		updateButton.setSize(300, 30);
		quickEditPanel.add(updateButton,BorderLayout.SOUTH);
		updateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selectedWidget.quickUpdate( quickEditText.getText() );
				hideQuickEditPanel();
			}
		});
		skfInstance.sketchPanel.add(quickEditPanel,0);
	}

	/**
	 * Displays a small textarea where user can edit what the widget shows
	 */
	public static void displayQuickEditPanel(final Widget w) {
		releaseSelectedWidgets();
		setSelectedWidget(w);
		if(quickEditPanel==null){
			createQuickEditPanel();
		}else{
			skfInstance.sketchPanel.setComponentZOrder(quickEditPanel, 0);
		}
		int xq;
		int yq=w.data.y;
		if(w.data.x+10+quickEditPanel.getWidth()>skfInstance.sketchPanel.getWidth()){
			xq=w.data.x+w.data.width-quickEditPanel.getWidth();
		}else{
			xq=w.data.x;
		}
		if(w.data.y+25+quickEditPanel.getHeight()>skfInstance.sketchPanel.getHeight()){
			yq=w.data.y-quickEditPanel.getHeight();	
		}else{
			yq=w.data.y+25;
		}
		
		quickEditText.setText(w.data.text);
		quickEditPanel.setLocation( xq, yq );
		quickEditPanel.setVisible(true);
		quickEditText.requestFocus();

	}
	public static void hideQuickEditPanel() {
		if(quickEditPanel!=null){
			quickEditPanel.setVisible(false);
			quickEditText.setText("");
		}
	}
	public static void sendWidgetToServer(WidgetData w){
		if(myClient!=null){
			//requests the server to update
			myClient.sender.sendWidget(w);
			//updates local copy
		}
	}
	//invoked here, in SketchingFrame and in Widget
	public static void deleteSelectedWidgets() {
		if(selectedWidgets.size()>1){
			for (Widget w : selectedWidgets) {
				skfInstance.mySketch.removeWidget(w.data.id);
				skfInstance.visualWidgets.remove( w );
				skfInstance.sketchPanel.remove( w );
				myClient.sender.sendRemoveWidget(w.data.id);
			}
			selectedWidgets = new ArrayList<Widget>();
		}else{
			if(selectedWidget==null)
				return;
			skfInstance.mySketch.removeWidget(selectedWidget.data.id);
			skfInstance.visualWidgets.remove( selectedWidget );
			skfInstance.sketchPanel.remove( selectedWidget );
			myClient.sender.sendRemoveWidget(selectedWidget.data.id);
		}
		selectedWidget=null;
		if(optionsPanelDisplayed!=null){
			optionsPanelDisplayed.setVisible(false);
			optionsPanelDisplayed=null;
		}
		skfInstance.sketchPanel.repaint();
	}
	//invoked remotely
	public void removeWidget(long wId){
		Widget wToErase=null;
		wToErase=findVisualWidgetWithId(wId);
		if(wToErase==null)
			return;
		if(wToErase.equals(selectedWidget)){
			releaseSelectedWidget();
		}
		mySketch.removeWidget(wId);
		skfInstance.visualWidgets.remove( wToErase );
		skfInstance.sketchPanel.remove( wToErase );
		skfInstance.sketchPanel.repaint();
	}
	public static void setCVECursor(java.awt.Cursor cursor) {
		skfInstance.sketchPanel.setCursor(cursor);
		//TODO cambiar el cursor de JME
	}
	
	public static void copyCurrentWidget() {
		ArrayList<Widget> newSelectedWidgets = new ArrayList<Widget>();
		if(selectedWidgets.size()>1){
			for (Widget w : selectedWidgets) {
				Widget w2 = w.getClone();
				w2.data.setFirst(WeSketchConstants.getNewId(), skfInstance.mySketch.id, w2);
				int x = w.getX()+20;//selectedWidget.getWidth()/2;
				int y = w.getY()+20;//selectedWidget.getHeight()/2;
				w2.setLocation(x, y);
				w2.setFocused(true);
				newSelectedWidgets.add(w2);
				w.setFocused(false);
				skfInstance.addWidgetToSketch(w2,true);
			}
			selectedWidgets=newSelectedWidgets;
		}else{
			Widget w2 = selectedWidget.getClone();
			w2.data.setFirst(WeSketchConstants.getNewId(), skfInstance.mySketch.id, w2);
			int x = selectedWidget.getX()+20;//selectedWidget.getWidth()/2;
			int y = selectedWidget.getY()+20;//selectedWidget.getHeight()/2;
			w2.setLocation(x, y);
			skfInstance.addWidgetToSketch(w2,true);
			releaseSelectedWidget();
			setSelectedWidget(w2);
		}
	}
	/**
	 * Selects all the componentes inside rectangle formed by x1,y1 and x2,y2.
	 * x1,y1 is assumed to be less than x2,y2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void selectWidgetsInside(int x1, int y1, int x2, int y2) {
		releaseSelectedWidgets();
		for (Widget w : visualWidgets) {
			if( w.isInside(x1,y1,x2,y2) ){
				w.setFocused(true);
				selectedWidgets.add(w);
				sendWidgetToServer(w.data);
			}
		}
	}

	//------------------------------------------------------- LISTENERS


	class SketchingFrameListener implements InternalFrameListener {
		@Override
		public void internalFrameActivated(InternalFrameEvent e) { }
		@Override
		public void internalFrameClosed(InternalFrameEvent e) { }
		@Override
		public void internalFrameClosing(InternalFrameEvent e) {
			myClient.removeMeFromSketch();
			cerrarSKFrame();
		}
		@Override
		public void internalFrameDeactivated(InternalFrameEvent e) { }
		@Override
		public void internalFrameDeiconified(InternalFrameEvent e) { }
		@Override
		public void internalFrameIconified(InternalFrameEvent e) { }
		@Override
		public void internalFrameOpened(InternalFrameEvent e) { }
	}
	/**
	 * This class allows to drag & drop widgets from the widgets tool bar to the sketch 
	 * @author Hamilan
	 */
	class WidgetsToolBarListener extends MouseAdapter {//implements MouseListener, MouseMotionListener {
		Widget w=null;
		Widget w2=null;

		public WidgetsToolBarListener(Widget w) {
			this.w = w;
		}
		@Override
		public void mouseDragged(MouseEvent me) {
			if(w2==null){
				w2 = w.getClone();
				w2.data.setFirst(WeSketchConstants.getNewId(), mySketch.id, w2);
				w2.setLocation(w.getX()+me.getX()+widgetsTabs.getX(),w.getY()+me.getY()+widgetsTabs.getY());
				contentPane.add(w2,0);
			}
			w2.setLocation(w.getX()-w.getWidth()/2+me.getX()+widgetsTabs.getX(), w.getY()+me.getY()+w.getHeight()/2+widgetsTabs.getY());
			sendCursorOverWidget(me.getX()+w.getX(), me.getY()+w.getY(), 0);
		}
		@Override
		public void mouseReleased(MouseEvent me) {
			if(w2==null) return;
			int x = w.getX()-w.getWidth()/2+me.getX();
			int y = w.getY()+me.getY()+w.getHeight()/2;
			contentPane.remove(w2);	//remove the widget that was being dragged 
			//if widget was dropped inside sketchPanel
			if(x>=sketchPanel.getX() || y>=sketchPanel.getY()){
				w2.setLocation(x-sketchPanel.getX(), y-sketchPanel.getY());
				addWidgetToSketch(w2,true);
				setSelectedWidget(w2);
			}
			w2=null;
		}
		@Override
		public void mouseMoved(MouseEvent me) {
			sendCursorOverWidget(me.getX()+w.getX(), me.getY()+w.getY(), 0);
		}
	}
	/**
	 * Used only to send information trough the net
	 * @author Hamilan
	 */
	class MouseSender extends MouseAdapter{
		int xBase = 0;
		int yBase = 0;
		public MouseSender() {
		}
		public MouseSender(int x, int y){
			xBase = x;
			yBase = y;
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			Component c = (Component)e.getSource(); 
			int x = (c.getX()+e.getX()) + xBase;
			int y = (c.getY()+e.getY()) + yBase;
			myCursor.x=x;
			myCursor.y=y;
			if(myClient!=null){
				myClient.sender.sendCursor( myCursor );
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			Component c = (Component)e.getSource();
			int x = (c.getX()+e.getX()) + xBase;
			int y = (c.getY()+e.getY()) + yBase;
			myCursor.x=x;
			myCursor.y=y;
			if(myClient!=null){
				myClient.sender.sendCursor( myCursor );
			}
		}
	}
	/** This method will be called from inside Widgets. No more. */
	public static void sendCursorOverWidget(int x, int y, long id){
		if(id==0){
			x+=skfInstance.widgetsTabs.getX();
			int ajusteY = 23;
			y+=skfInstance.widgetsTabs.getY()+ajusteY;
		}else{
			x+=skfInstance.sketchPanel.getX();
			y+=skfInstance.sketchPanel.getY();
		}
		skfInstance.myCursor.x=x;
		skfInstance.myCursor.y=y;
		if(myClient!=null){
			myClient.sender.sendCursor( skfInstance.myCursor );
		}
	}
	public static void releaseSelectedWidgets() {
		for (int i = selectedWidgets.size()-1; i >= 0; i--) {
			selectedWidgets.get(i).setFocused(false);
			sendWidgetToServer(selectedWidgets.get(i).data);
			selectedWidgets.remove(i);
		}
		releaseSelectedWidget();
	}
	public static void addWidgetToSelection(Widget w) {
		if(selectedWidget!=null && !selectedWidgets.contains(selectedWidget)){
			selectedWidgets.add( selectedWidget );
		}
		w.setFocused(true);
		selectedWidgets.add(w);
	}

	public void setLocationForSelectedWidgets(int x, int y, Widget wReady) {
		for (int i = 0; i < selectedWidgets.size(); i++) {
			Widget w = selectedWidgets.get(i);
			if(w.equals(wReady)){
				continue;
			}
			w.setLocation(w.data.x+x, w.data.y+y);
			sendWidgetToServer(w.data);
		}
	}

	public void setControlDown(boolean b) {
		controlDown = b;
	}

	public boolean isControlDown() {
		return controlDown;
	}

	public void showPopupMenu(Component component, int x, int y) {
		sketchPanel.showPopupMenu(component,x,y);
	}

	public void copyToSession() {
		ArrayList<Widget> newSelectedWidgets = new ArrayList<Widget>();
		if(selectedWidgets.size()>1){
			for (Widget w : selectedWidgets) {
				Widget w2 = w.getClone();
				w2.data.setFirst(WeSketchConstants.getNewId(), skfInstance.mySketch.id, w2);
				int x = w.getX()+20;//selectedWidget.getWidth()/2;
				int y = w.getY()+20;//selectedWidget.getHeight()/2;
				w2.setLocation(x, y);
				w2.setFocused(true);
				newSelectedWidgets.add(w2);
			}
		}else{
			Widget w2 = selectedWidget.getClone();
			w2.data.setFirst(WeSketchConstants.getNewId(), skfInstance.mySketch.id, w2);
			int x = selectedWidget.getX()+20;//selectedWidget.getWidth()/2;
			int y = selectedWidget.getY()+20;//selectedWidget.getHeight()/2;
			w2.setLocation(x, y);
			newSelectedWidgets.add(w2);
		}
		myClient.copyWidgetsToClipBoard(newSelectedWidgets);
	}
	public void pasteFromSession() {
		ArrayList<Widget> newSelectedWidgets = new ArrayList<Widget>();
		ArrayList<Widget> wClipboard = myClient.getWidgetsClipBoard();
		if(wClipboard.size()>0){
			for (Widget w : wClipboard) {
				Widget w2 = w.getClone();
				w2.data.setFirst(WeSketchConstants.getNewId(), skfInstance.mySketch.id, w2);
				int x = w.getX()+20;//selectedWidget.getWidth()/2;
				int y = w.getY()+20;//selectedWidget.getHeight()/2;
				w2.setLocation(x, y);
				w2.setFocused(true);
				newSelectedWidgets.add(w2);
				w.setFocused(false);
				skfInstance.addWidgetToSketch(w2,true);
			}
			selectedWidgets=newSelectedWidgets;
		}
	}

	public static void bringSelectedToFront() {
		if(selectedWidgets.size()>0){
			for (Widget w : selectedWidgets) {
				skfInstance.sketchPanel.setComponentZOrder(w, 0);
				w.data.zOrder = skfInstance.mySketch.getNewZorder();
				skfInstance.myClient.sender.sendWidget(w.data);
				skfInstance.myClient.sender.notifyToFront(w.data);
			}
			skfInstance.sketchPanel.repaint();
		}
	}

	public static void sendSelectedToBottom() {
		if(selectedWidgets.size()>0){
			for (Widget w : selectedWidgets) {
				int count = skfInstance.sketchPanel.getComponentCount()-1;
				skfInstance.sketchPanel.setComponentZOrder(w, count );
				w.data.zOrder = skfInstance.mySketch.getMaxZorder();
				skfInstance.mySketch.moveToBottom( w.data );
				skfInstance.myClient.sender.sendWidget(w.data);
				skfInstance.myClient.sender.notifyToBottom(w.data);
			}
			skfInstance.sketchPanel.repaint();
		}
	}

	public void moveToFront(long wId) {
		Widget w=null;
		for (Widget wi : visualWidgets) {
			if(wi.data.id==wId){
				w = wi;
				break;
			}
		}
		if(w!=null){
			skfInstance.sketchPanel.setComponentZOrder(w, 0 );
		}
	}

	public void moveToBottom(long wId) {
		Widget w=null;
		for (Widget wi : visualWidgets) {
			if(wi.data.id==wId){
				w = wi;
				break;
			}
		}
		if(w!=null){
			int count = skfInstance.sketchPanel.getComponentCount()-1;
			skfInstance.sketchPanel.setComponentZOrder(w, count );
		}
	}
	
	
}
