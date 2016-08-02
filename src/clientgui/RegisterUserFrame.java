package clientgui;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class RegisterUserFrame extends javax.swing.JFrame implements ActionListener{
	private JPanel jPanelDatosPersonales;
	private JLabel jLabelNombre;
	private JLabel jLabelApellido;
	private JLabel jLabelCompañia;
	private JButton jButtonRegistrar;
	private JTextField jTextField9;
	private JTextField jTextField8;
	private JTextField jTextField7;
	private JLabel jLabelConfiContraseña;
	private JLabel jLabelContraseña;
	private JLabel jLabelNomUsuario;
	private JPanel jPanelDatosCuenta;
	private JTextField jTextField6;
	private JTextField jTextField5;
	private JTextField jTextField4;
	private JTextField jTextField3;
	private JTextField jTextField2;
	private JTextField jTextField1;
	private JLabel jLabelEdad;
	private JLabel jLabelCiudad;
	private JLabel jLabelEmail;
	
	/**
	 * Just for testing. Should erase this method
	 * @param args
	 */
	public static void main(String[] args) {
		new RegisterUserFrame().setVisible(true);
	}
	public RegisterUserFrame() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			this.setTitle("EC-PIG --> Registro De Usuario");
			getContentPane().setLayout(null);
			getContentPane().setBackground(new java.awt.Color(242,242,242));
			{
				jPanelDatosPersonales = new JPanel();
				getContentPane().add(jPanelDatosPersonales);
				jPanelDatosPersonales.setLayout(null);
				jPanelDatosPersonales.setBounds(35, 35, 581, 266);
				Border etched = BorderFactory.createEtchedBorder();
				 Border titled = BorderFactory.createTitledBorder(etched, "Datos Personales",TitledBorder.ABOVE_BOTTOM,TitledBorder.CENTER,new java.awt.Font("Calibri",1,18),Color.BLACK);
			    jPanelDatosPersonales.setBorder(titled);
			    jPanelDatosPersonales.setBackground(new java.awt.Color(242,242,242));

				{
					jLabelNombre = new JLabel();
					jPanelDatosPersonales.add(jLabelNombre);
					jLabelNombre.setText("Nombre(s):");
					jLabelNombre.setBounds(105, 28, 105, 28);
					jLabelNombre.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelApellido = new JLabel();
					jPanelDatosPersonales.add(jLabelApellido);
					jLabelApellido.setText("Apellido(s):");
					jLabelApellido.setBounds(105, 63, 98, 28);
					jLabelApellido.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelCompañia = new JLabel();
					jPanelDatosPersonales.add(jLabelCompañia);
					jLabelCompañia.setText("Compañia:");
					jLabelCompañia.setBounds(105, 98, 105, 28);
					jLabelCompañia.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelEmail = new JLabel();
					jPanelDatosPersonales.add(jLabelEmail);
					jLabelEmail.setText("E-mail:");
					jLabelEmail.setBounds(133, 133, 70, 28);
					jLabelEmail.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelCiudad = new JLabel();
					jPanelDatosPersonales.add(jLabelCiudad);
					jLabelCiudad.setText("Ciudad:");
					jLabelCiudad.setBounds(133, 168, 77, 28);
					jLabelCiudad.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelEdad = new JLabel();
					jPanelDatosPersonales.add(jLabelEdad);
					jLabelEdad.setText("Edad:");
					jLabelEdad.setBounds(147, 203, 56, 28);
					jLabelEdad.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField1 = new JTextField();
					jPanelDatosPersonales.add(jTextField1);
					jTextField1.setBounds(210, 28, 231, 28);
					jTextField1.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField2 = new JTextField();
					jPanelDatosPersonales.add(jTextField2);
					jTextField2.setBounds(210, 63, 231, 28);
					jTextField2.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField3 = new JTextField();
					jPanelDatosPersonales.add(jTextField3);
					jTextField3.setBounds(210, 98, 231, 28);
					jTextField3.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField4 = new JTextField();
					jPanelDatosPersonales.add(jTextField4);
					jTextField4.setBounds(210, 133, 231, 28);
					jTextField4.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField5 = new JTextField();
					jPanelDatosPersonales.add(jTextField5);
					jTextField5.setBounds(210, 168, 231, 28);
					jTextField5.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField6 = new JTextField();
					jPanelDatosPersonales.add(jTextField6);
					jTextField6.setBounds(210, 203, 231, 28);
					jTextField6.setFont(new java.awt.Font("Calibri",0,18));
				}

			}
			{
				jPanelDatosCuenta = new JPanel();
				getContentPane().add(jPanelDatosCuenta);
				jPanelDatosCuenta.setBounds(35, 329, 476, 175);
				Border etched = BorderFactory.createEtchedBorder();
			    Border titled = BorderFactory.createTitledBorder(etched, "Datos De Cuenta Del Usuario",TitledBorder.ABOVE_BOTTOM,TitledBorder.CENTER,new java.awt.Font("Calibri",1,18),Color.BLACK);
			    jPanelDatosCuenta.setBorder(titled);
			    jPanelDatosCuenta.setLayout(null);
			    jPanelDatosCuenta.setBackground(new java.awt.Color(242,242,242));
				{
					jLabelNomUsuario = new JLabel();
					jPanelDatosCuenta.add(jLabelNomUsuario);
					jLabelNomUsuario.setText("Nombre de Usuario:");
					jLabelNomUsuario.setBounds(42, 42, 182, 28);
					jLabelNomUsuario.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelContraseña = new JLabel();
					jPanelDatosCuenta.add(jLabelContraseña);
					jLabelContraseña.setText("Contraseña:");
					jLabelContraseña.setBounds(98, 77, 112, 28);
					jLabelContraseña.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jLabelConfiContraseña = new JLabel();
					jPanelDatosCuenta.add(jLabelConfiContraseña);
					jLabelConfiContraseña.setText("Confirmar Contraseña:");
					jLabelConfiContraseña.setBounds(21, 112, 210, 28);
					jLabelConfiContraseña.setFont(new java.awt.Font("Calibri",0,18));
				}
				{
					jTextField7 = new JTextField();
					jPanelDatosCuenta.add(jTextField7);
					jTextField7.setBounds(217, 42, 224, 28);
				}
				{
					jTextField8 = new JTextField();
					jPanelDatosCuenta.add(jTextField8);
					jTextField8.setBounds(217, 77, 224, 28);
				}
				{
					jTextField9 = new JTextField();
					jPanelDatosCuenta.add(jTextField9);
					jTextField9.setBounds(217, 112, 224, 28);
				}
			}
			{
				jButtonRegistrar = new JButton();
				getContentPane().add(jButtonRegistrar);
				jButtonRegistrar.setText("Registrar >>");
				jButtonRegistrar.setBounds(525, 413, 126, 28);
				jButtonRegistrar.setFont(new java.awt.Font("Calibri",1,18));
				jButtonRegistrar.setBackground(new java.awt.Color(213,213,213));
				jButtonRegistrar.addActionListener(this);
			}

			pack();
			this.setSize(681, 577);
			setLocationRelativeTo(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==jButtonRegistrar) {
		}
		
	}

}
