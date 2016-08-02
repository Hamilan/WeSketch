package sketching.widgetOptionsPanels;

import java.awt.Dimension;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;

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
public class JPanelTextField extends javax.swing.JPanel {
	private JLabel jLabelPosicion;
	private JLabel jLabelTamaño;
	private JLabel jLabelHabilitado;
	private JLabel jLabelGrupo;
	private JLabel jLabelTexto;
	private JLabel jLabelOrden;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JPanelTextField());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public JPanelTextField() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(184, 364));
			this.setLayout(null);
			{
				jLabelPosicion = new JLabel();
				this.add(jLabelPosicion);
				jLabelPosicion.setText("Posición:");
				jLabelPosicion.setBounds(21, 21, 161, 28);
			}
			{
				jLabelTamaño = new JLabel();
				this.add(jLabelTamaño);
				jLabelTamaño.setText("Tamaño:");
				jLabelTamaño.setBounds(21, 49, 161, 28);
			}
			{
				jLabelHabilitado = new JLabel();
				this.add(jLabelHabilitado);
				jLabelHabilitado.setText("Habilitado:");
				jLabelHabilitado.setBounds(21, 77, 161, 28);
			}
			{
				jLabelOrden = new JLabel();
				this.add(jLabelOrden);
				jLabelOrden.setText("Orden:");
				jLabelOrden.setBounds(21, 105, 161, 28);
			}
			{
				jLabelGrupo = new JLabel();
				this.add(jLabelGrupo);
				jLabelGrupo.setText("Grupo:");
				jLabelGrupo.setBounds(21, 133, 161, 28);
			}
			{
				jLabelTexto = new JLabel();
				this.add(jLabelTexto);
				jLabelTexto.setText("Texto:");
				jLabelTexto.setBounds(21, 161, 161, 28);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
