package clientgui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import networking.WeSketchClient;

import shared.WeSketchConstants;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 * This frame contains the Session Chat Messages and the Sketch Chat Messages,
 * groups in Tabs.  
 * @author Hamilan
 */
public class ChatFrame extends javax.swing.JInternalFrame implements ActionListener, ChangeListener, FocusListener {
	private String basehtml="<style>" +
	"body{font-family:trebuchet;font-size:12pt;}"+
	".black{color:#000000; font:bold 11pt;}" +
	".blue{color:#0000FF; font:bold 11pt;}" +
	".green{color:#00AA00; font:bold 11pt;}" +
	".red{color:#FF0000; font:bold 11pt;}" +
	".purple{color:#DD00FF; font:bold 11pt;}" +
	".yellow{color:#BBBB00; font:bold 11pt;}" +
	"</style>";

	private JTabbedPane tabbedPane;

	private JPanel sessionChatPanel;
	private JScrollPane sessionScrollPane;
	private AppendingTextPane sessionChatArea;
	private JTextArea sessionBox;
	private JButton sessionSendButton;

	private JPanel sketchChatPanel;
	private JScrollPane sketchScrollPane;
	private AppendingTextPane sketchChatArea;
	private JTextArea sketchBox;
	private JButton sketchSendButton;

	Insets defaultMargin = new Insets(2,2,2,2);

	private javax.swing.Timer sessionPanelBlinker;
	private javax.swing.Timer sketchPanelBlinker;

	private WeSketchClient myClient;

	private JButton openerButton;

	private static final Color blinkColor=new Color(250,167,48);
	private static final Color lightColor=new Color(240,240,240);

	private static transient boolean blink = true;

	public ChatFrame(WeSketchClient myClient,JButton openerButton) {
		super("Chat");
		this.myClient = myClient;
		this.openerButton = openerButton;
		myClient.setChatFrame(this);
		initGUI();
	}

	private void initGUI() {
		try {
			this.setBorder(null);
			this.setDefaultCloseOperation(HIDE_ON_CLOSE);
			this.setClosable(true);
			this.setSize(280, 300);
			this.setMinimumSize(new Dimension(280,200));
//			javax.swing.plaf.InternalFrameUI ifu= this.getUI();
//			((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);
			{
				tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
				getContentPane().add(tabbedPane, BorderLayout.CENTER);
				{
					sessionChatPanel = new JPanel();
					sessionChatPanel.setBackground(new Color(200, 221, 242));
					BorderLayout panelSessionChatLayout = new BorderLayout();
					sessionChatPanel.setLayout(panelSessionChatLayout);
					tabbedPane.addTab("Con todos", null, sessionChatPanel, null);
					{
						sessionScrollPane = new JScrollPane();
						sessionChatPanel.add(sessionScrollPane,BorderLayout.CENTER);
						sessionChatArea = new AppendingTextPane();
						sessionChatArea.setContentType("text/html");
						sessionChatArea.setText(basehtml);
						sessionScrollPane.setViewportView(sessionChatArea);

						JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,1,1));
						southPanel.setBackground(new Color(200, 221, 242));
						{
							JScrollPane boxScrollPane = new JScrollPane();
							sessionBox = new JTextArea();
							sessionBox.setLineWrap(true);
							sessionBox.addFocusListener(this);
							sessionBox.addKeyListener(new KeyListener(){
								public void keyPressed(KeyEvent k) { }
								public void keyReleased(KeyEvent k) {
									if(k.getKeyCode()==KeyEvent.VK_ENTER){
										sendSessionChat();
									}
								}
								public void keyTyped(KeyEvent k) { }
							});
							boxScrollPane.setViewportView(sessionBox);
							southPanel.add(boxScrollPane);
							boxScrollPane.setPreferredSize(new java.awt.Dimension(190, 40));
						}
						{
							sessionSendButton = new JButton();
							southPanel.add(sessionSendButton);
							sessionSendButton.setText("Enviar");
							sessionSendButton.setSize(50, 40);
							sessionSendButton.setPreferredSize(new java.awt.Dimension(50, 40));
							sessionSendButton.setMargin(defaultMargin);
							sessionSendButton.setActionCommand("SendSessionChat");
							sessionSendButton.addActionListener(this);
						}
						sessionChatPanel.add(southPanel,BorderLayout.SOUTH);
					}
				}
				{
					sketchChatPanel = new JPanel();
					BorderLayout panelSketchChatLayout = new BorderLayout();
					sketchChatPanel.setBackground(new Color(200, 221, 242));
					sketchChatPanel.setLayout(panelSketchChatLayout);
					tabbedPane.addTab("Con Editores del Bosquejo", null, sketchChatPanel, null);
					{
						sketchScrollPane = new JScrollPane();
						sketchChatPanel.add(sketchScrollPane,BorderLayout.CENTER);
						sketchChatArea = new AppendingTextPane();
						sketchChatArea.setContentType("text/html");
						sketchChatArea.setText(basehtml);
						sketchScrollPane.setViewportView(sketchChatArea);

						JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,1,1));
						southPanel.setBackground(new Color(200, 221, 242));
						{
							JScrollPane boxScrollPane = new JScrollPane();
							sketchBox = new JTextArea();
							sketchBox.setLineWrap(true);
							sketchBox.addFocusListener(this);
							sketchBox.addKeyListener(new KeyListener(){
								public void keyPressed(KeyEvent k) { }
								public void keyReleased(KeyEvent k) {
									if(k.getKeyCode()==KeyEvent.VK_ENTER){
										sendSketchChat();
									}
								}
								public void keyTyped(KeyEvent k) { }
							});
							boxScrollPane.setViewportView(sketchBox);
							southPanel.add(boxScrollPane);
							boxScrollPane.setPreferredSize(new java.awt.Dimension(190, 40));
						}
						{
							sketchSendButton = new JButton();
							southPanel.add(sketchSendButton);
							sketchSendButton.setText("Enviar");
							sketchSendButton.setSize(50, 40);
							sketchSendButton.setPreferredSize(new java.awt.Dimension(50, 40));
							sketchSendButton.setMargin(defaultMargin);
							sketchSendButton.setActionCommand("SendSketchChat");
							sketchSendButton.addActionListener(this);
						}
						sketchChatPanel.add(southPanel,BorderLayout.SOUTH);
					}
				}
				tabbedPane.addChangeListener(this);
			}
			tabbedPane.setBackgroundAt(0, lightColor);
			tabbedPane.setBackgroundAt(1, lightColor);
			//sketch chat not available 
			tabbedPane.setEnabledAt(1, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void enableSketchChat(boolean value){
		sketchChatArea.setText(basehtml);
		tabbedPane.setEnabledAt(1, value);
	}
	private void sendSessionChat() {
		if(sessionBox.getText().length()==0){
			return;
		}
		String m=sessionBox.getText().trim();
		sessionBox.setText("");
		sessionBox.requestFocus();
		//TODO erase this line
		if(myClient==null){
			updateSketchChat(2, "User2",m);	//test
			updateSketchChat(3, "User3",m);	//test
			updateSketchChat(4, "User4",m);	//test
		}
		else{
			myClient.sender.sendGlobalChat(m);
		}
		updateSessionChat(myClient.getColor(),myClient.getMyLogin(),m);
	}
	private void sendSketchChat() {
		System.out.println("SendSketchChat");
		if(sketchBox.getText().length()==0){
			return;
		}
		String m=sketchBox.getText().trim();
		sketchBox.setText("");
		sketchBox.requestFocus();
		//TODO erase this line
		if(myClient==null){
			updateSessionChat(1, "User1",m);	//test
			updateSessionChat(0, "User0",m);	//test
			updateSessionChat(5, "User5",m);	//test
		}
		else{
			myClient.sender.sendLocalChat(m);
		}
		updateSketchChat(myClient.getColor(),myClient.getMyLogin(),m);
	}
	public void updateSessionChat(int colorIndex, String login, String t) {
		sessionChatArea.append("<p><span class='"+WeSketchConstants.COLORNAME[colorIndex]+"'>"+login+"</span>: "+t+"</p>");
		if(sessionPanelBlinker != null){
			sessionPanelBlinker.stop();
		}
		if(!this.isVisible() || tabbedPane.getSelectedIndex()!=0){
			sessionPanelBlinker = new Timer(1000, this);
			sessionPanelBlinker.start();
		}
	}
	public void updateSketchChat(int colorIndex, String login, String t) {
		System.out.println("updateSketchChat");
		sketchChatArea.append("<p><span class='"+WeSketchConstants.COLORNAME[colorIndex]+"'>"+login+"</span>: "+t+"</p>");
		if(sketchPanelBlinker != null){
			sketchPanelBlinker.stop();
		}
		if(!this.isVisible() || tabbedPane.getSelectedIndex()!=1){
			sketchPanelBlinker = new Timer(700, this);
			sketchPanelBlinker.start();
		}
	}
	//------------------------------------------------------------------
	// LISTENERS
	//------------------------------------------------------------------
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(sessionSendButton)){
			sendSessionChat();
			openerButton.setBackground( lightColor);
			return;
		}
		if(e.getSource().equals(sketchSendButton)){
			sendSketchChat();
			openerButton.setBackground( lightColor);
			return;
		}
		if(!this.isVisible() && (e.getSource().equals(sessionPanelBlinker)||
				e.getSource().equals(sketchPanelBlinker))){
			if(blink){
				openerButton.setBackground( blinkColor );
			}else{
				openerButton.setBackground( lightColor );
			}
			blink = !blink;
		}
		if(e.getSource().equals(sessionPanelBlinker)){
			if(blink){
				tabbedPane.setBackgroundAt(0, blinkColor );
			}else{
				tabbedPane.setBackgroundAt(0, lightColor );
			}
			blink = !blink;
			return;
		}
		if(e.getSource().equals(sketchPanelBlinker)){
			if(blink){
				tabbedPane.setBackgroundAt(1, blinkColor);
			}else{
				tabbedPane.setBackgroundAt(1, lightColor);
			}
			blink = !blink;
			return;
		}
	}
	/**
	 * When one tab of the tabbedPane has been selected
	 */
	@Override
	public void stateChanged(ChangeEvent ce) {
		System.out.println("State changed "+((JTabbedPane)ce.getSource()).getSelectedIndex());
		if(((JTabbedPane)ce.getSource()).getSelectedIndex()==0) {
			sessionBox.requestFocus();
			if(sessionPanelBlinker!=null){
				sessionPanelBlinker.stop();
				sessionPanelBlinker = null;
				tabbedPane.setBackgroundAt(0, lightColor);
				openerButton.setBackground( lightColor);
			}
		}else{
			sketchBox.requestFocus();
			if(sketchPanelBlinker!=null){
				sketchPanelBlinker.stop();
				sketchPanelBlinker = null;
				tabbedPane.setBackgroundAt(1, lightColor);
				openerButton.setBackground( lightColor );
			}
		}
	}

	@Override
	public void focusGained(FocusEvent fe) {
		System.out.println("focus gained "+fe.getSource().getClass().getSimpleName());
		if(fe.getSource().equals(sketchChatPanel) || fe.getSource().equals(sketchChatArea)){
			if(sketchPanelBlinker != null){
				sketchPanelBlinker.stop();
				sketchPanelBlinker = null;
				tabbedPane.setBackgroundAt(1, lightColor);
			}
			sketchBox.requestFocus();
		}
		if(fe.getSource().equals(sessionChatPanel) || fe.getSource().equals(sessionChatArea)){
			if(sessionPanelBlinker != null){
				sessionPanelBlinker.stop();
				sessionPanelBlinker = null;
				tabbedPane.setBackgroundAt(0, lightColor);
			}
			sessionBox.requestFocus();
		}
	}
	@Override
	public void focusLost(FocusEvent fe) { }
	
	@Override
	public void setVisible(boolean aFlag) {
		super.setVisible(aFlag);
	}
}
