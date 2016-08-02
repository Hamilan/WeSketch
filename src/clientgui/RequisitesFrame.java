package clientgui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import networking.WeSketchClient;
import shared.Session;

public class RequisitesFrame extends javax.swing.JInternalFrame {

	private JScrollPane jScrollPane1;
	private JTextArea jTextArea1;

	public static void main(String[] args) {
		//		JFrame frame = new JFrame();
		//		frame.setBounds(1,1,320,340);
		//		frame.add(new RequisitesFrame());
		//		frame.setVisible(true);
	}
	public RequisitesFrame() {
		super("Tarea");
		initGUI();
	}

	private void initGUI() {
		try {
			setClosable(true);
			setDefaultCloseOperation(HIDE_ON_CLOSE);
			setPreferredSize(new Dimension(400, 300));
			setBounds(new Rectangle(0, 0, 400, 300));
			{
				jScrollPane1 = new JScrollPane();
				getContentPane().add(jScrollPane1, BorderLayout.CENTER);
				{
					jTextArea1 = new JTextArea();
					jScrollPane1.setViewportView(jTextArea1);
					jTextArea1.setText(Session.tareas[WeSketchClient.currentSession.indexTarea]);
					jTextArea1.setLineWrap(true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
