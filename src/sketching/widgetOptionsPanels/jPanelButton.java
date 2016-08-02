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
public class jPanelButton extends javax.swing.JPanel {
	private JLabel jLabelTitulo;
	private JLabel jLabelImagen;
	private JLabel jLabelGrupo;
	private JLabel jLabelPosicion;
	private JLabel jLabelTamaño;
	private JLabel jLabelHabilitado;
	private JLabel jLabelOrden;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new jPanelButton());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public jPanelButton() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			setPreferredSize(new Dimension(184, 364));
			this.setLayout(null);
			{
				jLabelTitulo = new JLabel();
				this.add(jLabelTitulo);
				jLabelTitulo.setText("Título:");
				jLabelTitulo.setBounds(14, 147, 168, 28);
			}
			{
				jLabelImagen = new JLabel();
				this.add(jLabelImagen);
				jLabelImagen.setText("Imagen:");
				jLabelImagen.setBounds(14, 175, 147, 28);
			}
			{
				jLabelGrupo = new JLabel();
				this.add(jLabelGrupo);
				jLabelGrupo.setText("Grupo:");
				jLabelGrupo.setBounds(14, 119, 161, 28);
			}
			{
				jLabelOrden = new JLabel();
				this.add(jLabelOrden);
				jLabelOrden.setText("Orden:");
				jLabelOrden.setBounds(14, 91, 161, 28);
			}
			{
				jLabelHabilitado = new JLabel();
				this.add(jLabelHabilitado);
				jLabelHabilitado.setText("Habilitado:");
				jLabelHabilitado.setBounds(14, 63, 161, 28);
			}
			{
				jLabelTamaño = new JLabel();
				this.add(jLabelTamaño);
				jLabelTamaño.setText("Tamaño:");
				jLabelTamaño.setBounds(14, 35, 161, 28);
			}
			{
				jLabelPosicion = new JLabel();
				this.add(jLabelPosicion);
				jLabelPosicion.setText("Posición:");
				jLabelPosicion.setBounds(14, 7, 63, 28);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
