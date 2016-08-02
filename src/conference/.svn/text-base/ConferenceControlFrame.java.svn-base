package conference;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;

import clientgui.IconCheckboxList;
import clientgui.IconCheckboxList.CheckboxListListener;

import shared.WeSketchConstants;


public class ConferenceControlFrame extends JInternalFrame implements CheckboxListListener, ActionListener, ItemListener {

	IconCheckboxList targetsList;
	JButton enableAudioButton;
	JCheckBox enableAllCheckBox;
	
	ImageIcon audioEnabledIcon= new ImageIcon(getClass().getClassLoader().getResource("images/audioenabled.png"));
	ImageIcon audioDisabledIcon= new ImageIcon(getClass().getClassLoader().getResource("images/audiodisabled.png"));
	
	ConferenceController myController;
	public Vector<String> logins = new Vector<String>();
	public Vector<Integer> usersColors = new Vector<Integer>();
	
	public ConferenceControlFrame( ConferenceController con ) {
		myController = con;
		init();
		con.setMyConferenceControlFrame(this);
	}
	private void init() {
		this.setSize(160,230);
		this.setClosable(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
		this.getContentPane().setLayout(new FlowLayout());
		//hides the border of the InternalFrame
		this.setBorder(null);
		//hides the title bar of the internalframe
//		javax.swing.plaf.InternalFrameUI ifu= this.getUI();
//		((javax.swing.plaf.basic.BasicInternalFrameUI)ifu).setNorthPane(null);
		
		enableAudioButton = new JButton("Activar",audioDisabledIcon);
		enableAudioButton.setToolTipText("Activar Audio y Voz");
		enableAudioButton.setActionCommand("enableaudio");
		enableAudioButton.addActionListener(this);
		this.getContentPane().add(enableAudioButton);

		targetsList = new IconCheckboxList(getLoginsArray(), getIconsForList());
		targetsList.setCheckboxListListener(this);
		targetsList.setEnabled(true);
		targetsList.setPreferredSize(new Dimension(150, 125) );
		this.getContentPane().add(targetsList);
		
		enableAllCheckBox = new JCheckBox("Habilitar todos");
		enableAllCheckBox.setEnabled(true);
		enableAllCheckBox.addItemListener(this);
		
		this.getContentPane().add(enableAllCheckBox);
		
		targetsList.checkAll();
	}
	/**
	 * El vector logins será el mismo de la clase ConferenceController
	 * Aquí no hay necesidad de agregar o remover elementos de este Vector pues
	 * la clase ConferenceController lo hace previamente
	 * @param logins
	 * @param usersColors
	 */
	public void update(Vector<String> logins, Vector<Integer> usersColors) {
		this.logins = logins;
		//System.out.println("Actualicé conferenceControlFrame con "+this.logins.size()+" logins");
		this.usersColors= usersColors;
		targetsList.setData(getLoginsArray(), getIconsForList());
	}
	public void addTarget(String login, int color){
		System.out.println("Added login "+login);
		this.logins.add(login);
		this.usersColors.add(color);
		targetsList.setData(getLoginsArray(), getIconsForList());
	}
	public void removeTarget(String login){
		int index=-1;
		for (int i = 0; i < logins.size(); i++) {
			if(logins.get(i).equals(login)){
				index =i;
				break;
			}
		}
		if(index==-1)
			return;
		this.logins.remove(index);
		this.usersColors.remove(index);
		if(targetsList!=null){
			targetsList.setData(getLoginsArray(), getIconsForList());
		}
	}

	private String[] getLoginsArray() {
		String[] a = new String[ logins.size() ];
		return logins.toArray(a);
	}
	private Map<Object, Icon> getIconsForList() {
		Map<Object, Icon> icons;
		icons = new HashMap<Object, Icon>();
		for (int i = 0; i < logins.size(); i++) {
			icons.put(logins.get(i), new ImageIcon(getClass().getClassLoader().
					getResource("images/user"+WeSketchConstants.COLORNAME[usersColors.get(i).intValue()]+"16x16.png")) );
		}
		return icons;
	}
	
	//--------------------------------------------------------------------
	//	LISTENERS - EVENTS MANAGERS
	//--------------------------------------------------------------------
	
	public void checkboxListClicked(int index, boolean value){
		System.out.println("Item "+index+" changed to "+value);
		if( value ){
			addTargetToConference( index );
		}else{
			removeTargetFromConference( index );
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(enableAudioButton)){
			
			if(enableAudioButton.getText().equals("Activar")){
				enableAudioButton.setText("Desactivar");
				enableAudioButton.setIcon(audioEnabledIcon);
				enableAudioButton.setToolTipText("Desactivar Audio y Voz");
				targetsList.setEnabled(true);
				enableAllCheckBox.setEnabled(false);
				enableConference();
			}else{
				enableAudioButton.setText("Activar");
				enableAudioButton.setIcon(audioDisabledIcon);
				enableAudioButton.setToolTipText("Activar Audio y Voz");
				targetsList.setEnabled(true);
				enableAllCheckBox.setEnabled(true);
				disableConference();
			}
		}
	}

	public void itemStateChanged(ItemEvent e) {
		if(e.getSource().equals(enableAllCheckBox)){
			if(enableAllCheckBox.isSelected()){
				addAllTargets();
				targetsList.checkAll();
				enableAllCheckBox.setText("Deshabilitar todos");
			}else{
				removeAllTargets();
				targetsList.uncheckAll();
				enableAllCheckBox.setText("Habilitar todos");
			}
		}
	}
	
	private void disableConference() {
		myController.stopSound();
	}

	private void enableConference() {
		myController.updateConferenceIPs(targetsList.getSelectedIndexes());
		myController.startSound();
	}

	private void removeAllTargets() {
		myController.removeAllTargets();
	}

	private void addAllTargets() {
		myController.addAllTargets();
	}

	private void removeTargetFromConference(int index) {
		myController.removeTarget( index );
	}

	private void addTargetToConference(int index) {
		myController.addIp( index );
	}
}
