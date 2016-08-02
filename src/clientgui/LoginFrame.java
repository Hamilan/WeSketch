package clientgui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import networking.WeSketchClient;



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
@SuppressWarnings("serial")
public class LoginFrame extends JInternalFrame implements ActionListener {
	WeSketchClient myClient;
	private JPanel panelServerData;

	private JLabel jLabelTitulo;
	private JLabel jLabelNombre;
	private JPanel panelUserData;
	private JLabel labelIconConecction;
	private JTextField hostBox;
	private JLabel labelHost;
	private JButton jButtonCancel;
	private JButton jButtonStart;
	private JCheckBox jCheckBoxRecordarContraseña;
	private JCheckBox jCheckBoxRecordarme;
	private JButton jButtonRegister;
	private JPasswordField passwordBox;
	private JLabel jLabelContraseña;
	private JTextField loginBox;
	private JLabel labelStatus;
	private JLabel jLabelLoginStatus;
	private JLabel jLabelTitle;

	// ||||||||||||||||||||||||||||||||||||||||||||||||||
	/**
	 * Just for testing. Should erase this method
	 * @param args
	 */
	public static void main(String[] args) {
		
		LoginFrame frame=new LoginFrame();
		frame.setVisible(true);
	}
	/**
	 * Just for testing. Should erase this method
	 */
	public LoginFrame() {
		super();
		initGUI();
	}
	private void loadLoginInfo() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("lastconnectiondata.cnf"));
			String host = reader.readLine();
			String login = reader.readLine();
			
			hostBox.setText(host);
			loginBox.setText(login);
			
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("There was no last connection login info");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// ||||||||||||||||||||||||||||||||||||||||||||||||||
	/**
	 * @param cveguidClient needed to let this window try to login
	 */
	public LoginFrame(WeSketchClient cveguidClient) {
		super();
		myClient = cveguidClient;
		initGUI();

		loadLoginInfo();

		LoginKeyListener keyL=new LoginKeyListener();
		passwordBox.addKeyListener(keyL);
		loginBox.addKeyListener(keyL);
		loginBox.grabFocus();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(242,242,242));
			this.setFont(new java.awt.Font("Calibri",0,10));
			this.setTitle("Inicio de Sesión");
			this.setSize(681, 549);
			{
				jLabelTitulo = new JLabel();
				getContentPane().add(jLabelTitulo);
				jLabelTitulo.setBounds(217, 42, 182, 28);
			}
			{
				jLabelTitle = new JLabel();
				getContentPane().add(jLabelTitle);
				jLabelTitle.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/tituloPrincipal.png")));
				jLabelTitle.setBounds(28, 3, 601, 179);
			}
			{
				jButtonRegister = new JButton();
				getContentPane().add(jButtonRegister);
				jButtonRegister.setText("Registrarse");
				jButtonRegister.setBounds(238, 474, 152, 28);
				jButtonRegister.setFont(new java.awt.Font("Calibri",1,18));
				jButtonRegister.addActionListener(this);
			}
			{
				jButtonStart = new JButton();
				getContentPane().add(jButtonStart);
				jButtonStart.setBounds(412, 474, 182, 28);
				jButtonStart.setFont(new java.awt.Font("Calibri",1,18));
				jButtonStart.setText("Iniciar Sesión >>");
				//jButtonIniciar.setBackground(new java.awt.Color(213,213,213));
				jButtonStart.addActionListener(this);
			}
			{
				jButtonCancel = new JButton();
				getContentPane().add(jButtonCancel);
				jButtonCancel.setBounds(84, 474, 119, 28);
				jButtonCancel.setText("Cancelar");
				jButtonCancel.setFont(new java.awt.Font("Calibri",1,18));
				//jButtonCancelar.setBackground(new java.awt.Color(213,213,213));
				jButtonCancel.addActionListener(this);
			}
			{
				panelServerData = new JPanel();
				getContentPane().add(panelServerData);
				panelServerData.setBounds(96, 194, 480, 68);
				panelServerData.setLayout(null);
				panelServerData.setBorder(BorderFactory.createTitledBorder("Conectarse al Servidor en:"));
				{
					labelHost = new JLabel();
					panelServerData.add(labelHost);
					labelHost.setText("Nombre del equipo o Dirección IP:");
					labelHost.setBounds(28, 27, 206, 16);
					labelHost.setHorizontalAlignment(JLabel.RIGHT);
				}
				{
					hostBox = new JTextField();
					panelServerData.add(hostBox);
					hostBox.setText(myClient.myIp);
					hostBox.setBounds(246, 24, 131, 28);
				}
				{
					labelIconConecction = new JLabel();
					panelServerData.add(labelIconConecction);
					labelIconConecction.setText("");
					labelIconConecction.setBounds(399, 12, 48, 48);
					//labelIconConecction.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/questionicon.png")));
				}
			}
			{
				panelUserData = new JPanel();
				getContentPane().add(panelUserData);
				panelUserData.setBounds(96, 282, 480, 180);
				panelUserData.setLayout(null);
				panelUserData.setBorder(BorderFactory.createTitledBorder("Datos de Usuario"));
				{
					jLabelNombre = new JLabel();
					panelUserData.add(jLabelNombre);
					jLabelNombre.setText("Nombre de Usuario");
					jLabelNombre.setBounds(5, 21, 164, 28);
					jLabelNombre.setFont(new java.awt.Font("Calibri",1,18));
					jLabelNombre.setHorizontalAlignment(JLabel.RIGHT);
				}
				{
					jLabelContraseña = new JLabel();
					jLabelContraseña.setHorizontalAlignment(JLabel.RIGHT);
					panelUserData.add(jLabelContraseña);
					jLabelContraseña.setText("Contraseña");
					jLabelContraseña.setBounds(5, 48, 164, 28);
					jLabelContraseña.setFont(new java.awt.Font("Calibri",1,18));
				}
				{
					loginBox = new JTextField();
					panelUserData.add(loginBox);
					loginBox.setBounds(181, 21, 203, 28);
				}
				{
					passwordBox = new JPasswordField();
					panelUserData.add(passwordBox);
					passwordBox.setBounds(181, 55, 203, 28);
				}
				{
					jCheckBoxRecordarme = new JCheckBox();
					panelUserData.add(jCheckBoxRecordarme);
					jCheckBoxRecordarme.setText("Recodarme (?)");
					jCheckBoxRecordarme.setBounds(183, 112, 182, 28);
					jCheckBoxRecordarme.setBackground(new java.awt.Color(242,242,242));
					jCheckBoxRecordarme.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jCheckBoxRecordarContraseña = new JCheckBox();
					panelUserData.add(jCheckBoxRecordarContraseña);
					jCheckBoxRecordarContraseña.setText("Recordar mi contraseña (?)");
					jCheckBoxRecordarContraseña.setBounds(183, 142, 245, 28);
					jCheckBoxRecordarContraseña.setBackground(new java.awt.Color(242,242,242));
					jCheckBoxRecordarContraseña.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelLoginStatus = new JLabel();
					panelUserData.add(jLabelLoginStatus);
					jLabelLoginStatus.setText("");
					//jLabelLoginStatus.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/questionicon.png")));
					jLabelLoginStatus.setBounds(399, 12, 48, 48);
				}
				{
					labelStatus = new JLabel();
					panelUserData.add(labelStatus);
					labelStatus.setBounds(26, 88, 429, 20);
					labelStatus.setHorizontalAlignment(SwingConstants.CENTER);
					labelStatus.setForeground(new java.awt.Color(255,0,0));
				}
			}
			this.setPreferredSize(new Dimension(681, 549));
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setServerConnectionStatus(boolean ok){
		if(ok){
			labelIconConecction.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/okicon.png")));			
		}else{
			labelIconConecction.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/erroricon.png")));
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jButtonRegister){
			RegisterUserFrame miVentanaRegistroUsuario= new RegisterUserFrame();
			miVentanaRegistroUsuario.setVisible(true);
			setVisible(false);
		}
		if(e.getSource()==jButtonStart){
			login();
		}
		if(e.getSource()==jButtonCancel) {
			//TODO Aquí falta un diálogo de confirmación
			System.exit(0);
		}
		
	}
	
	private void login() {
		String host = this.hostBox.getText();
		String name = this.loginBox.getText(); 
		String password = this.passwordBox.getText();
		if( host.trim().length()==0 ){
			hostBox.requestFocus();
			setServerConnectionStatus(false);
			return;
		}
		if( name.trim().length()==0 ){
			loginBox.requestFocus();
			setLoginConnectionStatus(false);
			return;
		}
		if(password.length()==0){
			passwordBox.requestFocus();
			//return;
		}
		myClient.login( host, name, password );
	}

	class LoginKeyListener extends KeyAdapter{
		public void keyReleased(KeyEvent k) {
			if(k.getKeyCode()==KeyEvent.VK_ENTER){
				login();
			}
		}
	}

	public void showNotification(String message, String title, int messageType) {
		jLabelLoginStatus.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/erroricon.png")));
		labelStatus.setText(message);
		//JOptionPane.showMessageDialog(this, message,title,messageType);
	}
	public void setLoginConnectionStatus(boolean b) {
		jLabelLoginStatus.setIcon(new ImageIcon(getClass().getClassLoader().getResource("images/okicon.png")));
	}
	public void focusOnLogin() {
		loginBox.requestFocus();
	}

}
