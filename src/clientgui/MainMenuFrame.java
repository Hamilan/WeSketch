package clientgui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import networking.WeSketchClient;
import ve.WeSketchGame;
import conference.ConferenceControlFrame;
import conference.ConferenceController;


@SuppressWarnings("serial")
public class MainMenuFrame extends JInternalFrame implements ActionListener {
	WeSketchClient myClient;
	WeSketchGame myGame;

	private JButton buttonChat;
	private JButton buttonMyInfo;
	private JButton buttonRequisites;
	private JButton buttonSketches;
	private JButton buttonConference;
	
	private JDesktopPane desktop;
	
	public static ChatFrame chatFrame;
	public static UserSettingsFrame userSettingsFrame;
	public static SketchesFrame sketchesFrame;
	public static RequisitesFrame requisitesFrame;
	public static ConferenceControlFrame conferenceControlFrame;
	public static ConferenceController conferenceController;
	
	Insets defaultMargin = new Insets(2,2,2,2);

	/**
	 * @param
	 */
	public MainMenuFrame(WeSketchClient cveguidClient) {
		super();
		myClient = cveguidClient;
		initGUI();
		this.setEnabled(false);
	}
	
	public void setAtBottomOf(JDesktopPane f) {
		desktop = f;
		int x, y;
		x = f.getWidth()/2-this.getWidth()/2;
		y = f.getHeight()-this.getHeight();
		this.setLocation(x, y);
		setFramesToDisplay();
	}
	private void setFramesToDisplay() {
		chatFrame = new ChatFrame(myClient,buttonChat);
		chatFrame.setLocation(this.getX(), this.getY()-chatFrame.getHeight());
		desktop.add( chatFrame );
		
		userSettingsFrame = new UserSettingsFrame();
		userSettingsFrame.setLocation(this.getX(), this.getY()-userSettingsFrame.getHeight());
		desktop.add( userSettingsFrame );
		
		sketchesFrame = new SketchesFrame();
		sketchesFrame.setLocation(this.getX(), this.getY()-sketchesFrame.getHeight());
		desktop.add( sketchesFrame );
		
		requisitesFrame = new RequisitesFrame();
		requisitesFrame.setLocation(this.getX()+40, this.getY()-requisitesFrame.getHeight());
		desktop.add( requisitesFrame );
		
		conferenceController=new ConferenceController(myClient.getSessionParticipants());
		conferenceControlFrame = new ConferenceControlFrame(conferenceController);
		conferenceControlFrame.setLocation(this.getX()+405, this.getY()-conferenceControlFrame.getHeight());
		desktop.add( conferenceControlFrame );
	}
	
	public ConferenceControlFrame getConferenceControlFrame() {
		return conferenceControlFrame;
	}
	public ConferenceController getConferenceController() {
		return conferenceController;
	}

	private void initGUI() {
		try {
			this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
			this.getContentPane().setBackground(new java.awt.Color(242,242,242));
			//this.setLocation(50, 50);
			this.setSize(500, 32);
			this.setPreferredSize(new Dimension(500,32));
			this.setResizable(false);
			//hides the title bar of the internalframe
			this.setBorder(null);
			javax.swing.plaf.InternalFrameUI ifu= this.getUI();
			((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);
			
			{
				buttonChat = new JButton("Chat");
				buttonChat.setPreferredSize(new Dimension(100, 32) );
				buttonChat.addActionListener(this);
				buttonChat.setMargin(defaultMargin);
				this.getContentPane().add(buttonChat);
			}
			{
				buttonMyInfo = new JButton("Mis datos");
				buttonMyInfo.setEnabled(false);
				buttonMyInfo .setPreferredSize(new Dimension(100, 32) );
				buttonMyInfo .addActionListener(this);
				buttonMyInfo.setMargin(defaultMargin);
				this.getContentPane().add(buttonMyInfo );
			}
			{
				buttonRequisites = new JButton("Requisitos");
				buttonRequisites.setPreferredSize(new Dimension(100, 32) );
				buttonRequisites.addActionListener(this);
				buttonRequisites.setMargin(defaultMargin);
				this.getContentPane().add(buttonRequisites);
			}
			{
				buttonSketches = new JButton("Bosquejos");
				buttonSketches.setEnabled(false);
				buttonSketches.setPreferredSize(new Dimension(100, 32) );
				buttonSketches.addActionListener(this);
				buttonSketches.setMargin(defaultMargin);
				this.getContentPane().add(buttonSketches);
			}
			{
				buttonConference= new JButton("Conferencia");
				buttonConference.setPreferredSize(new Dimension(100, 32) );
				buttonConference.addActionListener(this);
				buttonConference.setMargin(defaultMargin);
				this.getContentPane().add(buttonConference);
			}
			this.setVisible(true);
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if( e.getSource()==buttonChat ){
			chatFrame.setVisible(!chatFrame.isVisible());
			buttonChat.setBackground(Color.LIGHT_GRAY);
		}
		if( e.getSource()==buttonMyInfo ){
			userSettingsFrame.setVisible(true);
		}
		if( e.getSource().equals(buttonRequisites) ){
			requisitesFrame.setVisible(!requisitesFrame.isVisible());
		}
		if( e.getSource()==buttonSketches ){
			sketchesFrame.setVisible(true);
		}
		if( e.getSource()==buttonConference){
			conferenceControlFrame.setVisible(!conferenceControlFrame.isVisible());
		}
	}
	
}
