package sketching;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import networking.WeSketchClient;
import ve.Control;


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
 * This class represents the New Sketch Wizard which allows to gather information for the creation of a new Sketch. 
 * @author Hamilan
 */
@SuppressWarnings("serial")
public class NewSketchFrame extends JInternalFrame implements ActionListener{
	private JPanel jPanelBosquejo;
	private JLabel jLabelNombre;
	private JLabel jLabelDescripcion;
	private JTextField cajaNombre;
	private JScrollPane jScrollPane1;
	private JButton jButtonCancelar;
	private JButton jButtonCrear;
	private JTextField cajaRequisite;
	private JTextField cajaAuthor;
	private JTextArea cajaDescription;
	private JLabel jLabelRequisite;
	private JLabel jLabelAuthor;
	private WeSketchClient myClient;
	/**
	 * Temporal method for isolated testing. Should be erased later. 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame f = new JFrame();
		NewSketchFrame w = new NewSketchFrame( null );
		w.setDefaultCloseOperation(EXIT_ON_CLOSE);
		w.setVisible(true);
		f.add(w);
		f.setVisible(true);
		f.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public NewSketchFrame(WeSketchClient cveguidClient) {
		super("Creando un nuevo Bosquejo...");
		myClient = cveguidClient;
		initialize();
	}
	private void initialize() {
		try {
			setClosable(true);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			getContentPane().setLayout(null);
			//getContentPane().setBackground(new java.awt.Color(242,242,242));
			{
				jPanelBosquejo = new JPanel();
				getContentPane().add(jPanelBosquejo);
				Border etched = BorderFactory.createEtchedBorder();
				Border titled = BorderFactory.createTitledBorder(etched, " Datos Del Bosquejo ",TitledBorder.ABOVE_BOTTOM,TitledBorder.CENTER,new java.awt.Font("Trebuchet",1,14),Color.BLACK);
				jPanelBosquejo.setBorder(titled);
				jPanelBosquejo.setLayout(null);
				//jPanelBosquejo.setBackground(new java.awt.Color(242,242,242));
				jPanelBosquejo.setBounds(35, 28, 378, 289);
				{
					jLabelNombre = new JLabel();
					jPanelBosquejo.add(jLabelNombre);
					jLabelNombre.setText("Título del Bosquejo:");
					jLabelNombre.setBounds(10, 50, 130, 25);
					jLabelNombre.setHorizontalAlignment(SwingConstants.RIGHT);
				}
				{
					jLabelAuthor = new JLabel();
					jPanelBosquejo.add(jLabelAuthor);
					jLabelAuthor.setText("Creador del bosquejo:");
					jLabelAuthor.setBounds(10, 90, 130, 25);
					jLabelAuthor.setHorizontalAlignment(SwingConstants.RIGHT);
				}
				{
					jLabelDescripcion = new JLabel();
					jPanelBosquejo.add(jLabelDescripcion);
					jLabelDescripcion.setText("Descripción:");
					jLabelDescripcion.setBounds(10, 130, 130, 25);
					jLabelDescripcion.setHorizontalAlignment(SwingConstants.RIGHT);
				}
				{
					jLabelRequisite = new JLabel();
					jPanelBosquejo.add(jLabelRequisite);
					jLabelRequisite.setText("Requisito relacionado:");
					jLabelRequisite.setBounds(10, 245, 130, 25);
				}
				{
					cajaNombre = new JTextField();
					jPanelBosquejo.add(cajaNombre);
					cajaNombre.setBounds(146, 50, 210, 25);
				}
				{
					if(myClient!=null)
						cajaAuthor = new JTextField( myClient.getMyLogin() );
					else
						cajaAuthor = new JTextField( "Yo");
					jPanelBosquejo.add(cajaAuthor);
					cajaAuthor.setEditable(false);
					cajaAuthor.setBounds(146, 90, 210, 25);
				}
				{
					jScrollPane1 = new JScrollPane();
					jPanelBosquejo.add(jScrollPane1);
					jScrollPane1.setBounds(145, 130, 210, 100);
					{
						cajaDescription = new JTextArea("No importante para esta prueba");
						cajaDescription.setEditable(false);
						jScrollPane1.setViewportView(cajaDescription);
					}
				}
				{
					cajaRequisite = new JTextField("No importante para esta prueba ");
					jPanelBosquejo.add(cajaRequisite);
					cajaRequisite.setBounds(145, 247, 210, 25);
					cajaRequisite.setEditable(false);
				}
			}
			{
				jButtonCrear = new JButton();
				getContentPane().add(jButtonCrear);
				jButtonCrear.setText("Crear >>");
				jButtonCrear.setBounds(295, 329, 112, 28);
				jButtonCrear.addActionListener(this);
			}
			{
				jButtonCancelar = new JButton();
				getContentPane().add(jButtonCancelar);
				jButtonCancelar.setText("Cancelar ");
				jButtonCancelar.setBounds(37, 329, 105, 28);
				jButtonCancelar.addActionListener(this);
			}
			pack();
			this.setBounds(0, 0, 460, 400);
			this.setPreferredSize(new Dimension(460,400));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jButtonCancelar) {
			Control.setNewSketchFlag(false);
			this.dispose();
			return;
		}
		if(e.getSource()==jButtonCrear) {
			String name = cajaNombre.getText();
			String description= cajaDescription.getText();
			String goal = cajaAuthor.getText();
			String relatedRequisite = cajaRequisite.getText();
			myClient.setSketchingFrame(name,description,goal,relatedRequisite);
			return;
		}
	}

}
