package sketching.widgetOptionsPanels;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
public class jPanelLabelYTextArea extends javax.swing.JPanel {
	private JPanel jPanelFuente;
	private JLabel jLabelTitulo;
	private JPanel jPanelAlineacion;
	private JLabel jLabelDerecho;
	private JLabel jLabelPosicion;
	private JLabel jLabelTamaño;
	private JLabel jLabelHabilitado;
	private JLabel jLabelOrden;
	private JLabel jLabelGrupo;
	private JLabel jLabelCentro;
	private JLabel jLabelIzquierda;
	private JLabel jLabelSubrayado;
	private JLabel jLabelItalica;
	private JLabel jLabelNegrita;
	private JLabel jLabelTamañoFuente;
	private JLabel jLabelColor;

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new jPanelLabelYTextArea());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public jPanelLabelYTextArea() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(184, 364));
			this.setLayout(null);
			{
				jPanelFuente = new JPanel();
				Border etched = BorderFactory.createEtchedBorder();
			    Border titled = BorderFactory.createTitledBorder(etched, "  Fuente  ",TitledBorder.ABOVE_BOTTOM,TitledBorder.CENTER);
			    jPanelFuente.setBorder(titled);
				jPanelFuente.setBounds(7, 140, 168, 126);
				this.add(jPanelFuente);
				jPanelFuente.setLayout(null);
				{
					jLabelColor = new JLabel();
					jPanelFuente.add(jLabelColor);
					jLabelColor.setText("Color:");
					jLabelColor.setBounds(14, 14, 147, 28);
				}
				{
					jLabelTamañoFuente = new JLabel();
					jPanelFuente.add(jLabelTamañoFuente);
					jLabelTamañoFuente.setText("Tamaño:");
					jLabelTamañoFuente.setBounds(14, 35, 147, 28);
				}
				{
					jLabelNegrita = new JLabel();
					jPanelFuente.add(jLabelNegrita);
					jLabelNegrita.setText("Negrita:");
					jLabelNegrita.setBounds(14, 56, 147, 28);
				}
				{
					jLabelItalica = new JLabel();
					jPanelFuente.add(jLabelItalica);
					jLabelItalica.setText("Itálica:");
					jLabelItalica.setBounds(14, 77, 147, 28);
				}
				{
					jLabelSubrayado = new JLabel();
					jPanelFuente.add(jLabelSubrayado);
					jLabelSubrayado.setText("Subrayado:");
					jLabelSubrayado.setBounds(14, 98, 147, 28);
				}
			}
			{
				jLabelTitulo = new JLabel();
				this.add(jLabelTitulo);
				jLabelTitulo.setText("Título:");
				jLabelTitulo.setBounds(21, 112, 147, 28);
			}
			{
				jPanelAlineacion = new JPanel();
				Border etched = BorderFactory.createEtchedBorder();
			    Border titled = BorderFactory.createTitledBorder(etched, "  Alineación  ",TitledBorder.ABOVE_BOTTOM,TitledBorder.CENTER);
			    jPanelAlineacion.setBorder(titled);
				jPanelAlineacion.setBounds(7, 273, 168, 84);
				this.add(jPanelAlineacion);
				jPanelAlineacion.setLayout(null);
				{
					jLabelIzquierda = new JLabel();
					jPanelAlineacion.add(jLabelIzquierda);
					jLabelIzquierda.setText("Izquierda:");
					jLabelIzquierda.setBounds(14, 21, 147, 21);
				}
				{
					jLabelCentro = new JLabel();
					jPanelAlineacion.add(jLabelCentro);
					jLabelCentro.setText("Centro:");
					jLabelCentro.setBounds(14, 35, 147, 28);
				}
				{
					jLabelDerecho = new JLabel();
					jPanelAlineacion.add(jLabelDerecho);
					jLabelDerecho.setText("Derecho:");
					jLabelDerecho.setBounds(14, 56, 147, 28);
				}
			}
			{
				jLabelGrupo = new JLabel();
				this.add(jLabelGrupo);
				jLabelGrupo.setText("Grupo:");
				jLabelGrupo.setBounds(21, 91, 147, 28);
			}
			{
				jLabelOrden = new JLabel();
				this.add(jLabelOrden);
				jLabelOrden.setText("Orden:");
				jLabelOrden.setBounds(21, 70, 63, 28);
			}
			{
				jLabelHabilitado = new JLabel();
				this.add(jLabelHabilitado);
				jLabelHabilitado.setText("Habilitado:");
				jLabelHabilitado.setBounds(21, 49, 63, 28);
			}
			{
				jLabelTamaño = new JLabel();
				this.add(jLabelTamaño);
				jLabelTamaño.setText("Tamaño:");
				jLabelTamaño.setBounds(21, 28, 63, 28);
			}
			{
				jLabelPosicion = new JLabel();
				this.add(jLabelPosicion);
				jLabelPosicion.setText("Posición:");
				jLabelPosicion.setBounds(21, 7, 63, 28);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
