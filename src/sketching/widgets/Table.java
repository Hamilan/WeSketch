package sketching.widgets;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This class represents the Table widget to use in the Sketching system.
 * Objects of this class can be sent through the net with Kryonet.
 * @author Hamilan
 */
public class Table extends ComponentWidget {
	/**
	 * data.selected :  Defines whether the first row of the table should be highlighted as title or not.
	 * data.text : content
	 * Based on Balsamiq way of storing.
	 * Every line (divided by \n) represents a row.
	 * Every comma divides a cell of the same row.
	 * Example (4 rows each with 4 cells):
	 *  A, B, C, D\n1, 2, 3, 4\nRed, Green, Blue, White\n,Pear, Grape, Apple, Orange  
	 */


	public Table(int id, int sketchId, int x, int y, int width, int height, int zOrder,boolean hasTitleRow,String content){
		this.data.widgetType="Table";
		this.data.id = id;
		this.data.sketchId = sketchId;
		this.data.zOrder = zOrder;
		this.data.enabled = true;
		this.data.groupId = -1;
		setSize(width,height);
		setLocation(x, y);
		this.data.selected=hasTitleRow;
		this.data.text=content;
	}

	@Override
	public void drawWidgetInside(Graphics2D gContext, WidgetData data) {
//		gContext.setBackground(BACKGROUNDCOLOR);
//		gContext.clearRect(0, 0, data.width, data.height);
//		//BUtton Text
//		gContext.setColor(BORDERCOLOR);
//		gContext.drawRoundRect(1, 1, data.width-3, data.height-3,5,5);

		ArrayList<ArrayList<String>> tabla = new ArrayList<ArrayList<String>>();
		StringTokenizer tokenizer= new StringTokenizer(data.text,"\n");
		//	int textH =	gContext.getFontMetrics().getHeight();
		int i=0;
		int j=0;
		while(tokenizer.hasMoreTokens())
		{
			String opcion= tokenizer.nextToken();
			StringTokenizer tokenizer2= new StringTokenizer(opcion,",");
			int j1=0;
			while (tokenizer2.hasMoreElements())
			{
				tokenizer2.nextToken();
				j1++;
			}
			
			if(j<j1)
			{
				j=j1;
			}

			i++;
		}
		
		for (int j3 = 0; j3 < j; j3++) {
			for (int j2 = 0; j2 < tabla.get(j3).size(); j2++)
				{
					tabla.get(j3).set(j2," $ ");
				}
			
		}
		
		System.out.println("tamaño de token \n :"+ tokenizer.countTokens());
		tokenizer= new StringTokenizer(data.text,"\n");
		
		i=0;
		
		while(tokenizer.hasMoreTokens())
		{
			j=0;
			String opcion= tokenizer.nextToken();
			StringTokenizer tokenizer2= new StringTokenizer(opcion,",");
						
			ArrayList<String> fila= new ArrayList<String>();
			while (tokenizer2.hasMoreElements())
			{
				String itemFila = tokenizer2.nextToken();
				tabla.get(i).set(j," $ ");
				j++;
			}
		
			i++;
		}
		
		
		
		for (int k = 0; k < j; k++) {
			for (int j2 = 0; j2 < tabla.get(k).size(); j2++) {
				
					System.out.print(tabla.get(k).get(j2));
				
			}
			System.out.println("");
		}
		
		
//		ArrayList<Integer> tamañoColumnas= new ArrayList<Integer>();	
//		System.out.println("j1: "+j1);
//		
//		for (int j = 0; j < j1; j++) {
//			int num=0;
//			for (int j2 = 0; j2 <i; j2++) 
//			{				
//				if(num <gContext.getFontMetrics().charsWidth(tabla.get(j2).get(j).toCharArray(), 0, data.text.length()))
//				{
//					num=gContext.getFontMetrics().charsWidth(tabla.get(j2).get(j).toCharArray(), 0, data.text.length());
//				}
//			}
//			tamañoColumnas.add(num);
//		 
//			
//		}
	}


	@Override
	public Widget getClone() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(WidgetData widgetData) {
		// TODO Auto-generated method stub

	}

}
