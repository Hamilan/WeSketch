package sketching.widgets;

import java.util.StringTokenizer;


public class TestWidgets {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		javax.swing.JFrame frame = new javax.swing.JFrame("Testing Widgets");
		frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.getContentPane().setLayout(null);
		
		Button button = new Button(0,0,40,40,80,30,0,"Botón",null);
		
		Label label = new Label(0,0,40,40,80,30,0,"Label",(byte) 2);
		
		TextField textField= new TextField(0,0,40,40,80,30,0,"TextField"); 
		
		RadioButton radioButton= new RadioButton(0,0,40,40,80,30,0,"RadioButton",false); 
		RadioButton radioButton2= new RadioButton(0,0,40,90,80,30,0,"RadioButton",true);
		
		CheckBox checkBox= new CheckBox(0,0,40,40,80,30,0,"CheckBox",true);
		
		List list= new List(0,0,40,90,100,150,0,"Opcion1\nOpcion2\nOpcion3");
		
		ComboBox comboBox= new ComboBox(0,0,40,90,100,0,"Opcion1\nOpcion2\nOpcion3\nOpcion4",true);
		
		Image image= new Image(0,0,40,90,100,150,0, "url");
		
		Frame frame2 = new Frame(0, 0, 40, 90, 200, 200, 0, "window", (byte) 15);
		
		MessageDialog messageDialog= new MessageDialog(0, 0, 40, 90, 200, 200, 0, "window","Hola", (byte) 15); 
		
		frame.getContentPane().add(label);
		frame.getContentPane().add(button);
		//frame.getContentPane().add(textField);
		//frame.getContentPane().add(checkBox);
		//frame.getContentPane().add(radioButton2);
		//frame.getContentPane().add(list);
		//frame.getContentPane().add(comboBox);
		//frame.getContentPane().add(frame2);
		frame.getContentPane().add(messageDialog);
		
		
		frame.setVisible(true);
		
		esperar();
		label.setSize(100, 40);
		messageDialog.setSize(100, 40);
		esperar();
		label.setSize(120, 25);
		messageDialog.setSize(120, 25);
		esperar();
		label.setSize(50, 25);
		messageDialog.setSize(200, 100);

	}
	public static void esperar(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
