package clientgui;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

public class IconCheckboxList extends JScrollPane {
	
	private JList listCheckBox;		//This listbox holds only the checkboxes
	private JList listDescription;	//This listbox holds the actual descriptions of list items.
	private Map<Object, Icon> icons;//Icons por every item
	private String[] listData;		//Labels for every item
	
	CheckboxListListener listener;
	
	public IconCheckboxList(String[] listData, Map<Object, Icon> icons) {
		this.icons = icons;
		this.listData = listData;
		setCheckList(listData, icons);
	}
	
	private void setCheckList(String[] listData, Map<Object, Icon> icons) {
		this.icons = icons;
		this.listData = listData;
		
		listCheckBox = new JList(buildCheckBoxItems(listData.length));
		listDescription = new JList(listData);
		listDescription.setCellRenderer(new IconListRenderer(icons));
		
		listDescription.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDescription.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(listDescription.isEnabled()==false) return;
				if (me.getClickCount() != 2)
					return;
				int selectedIndex = listDescription.locationToIndex(me.getPoint());
				if (selectedIndex < 0)
					return;
				CheckBoxItem item = (CheckBoxItem)listCheckBox.getModel().getElementAt(selectedIndex);
				item.setChecked(!item.isChecked());
				listCheckBox.repaint();
			}
		});

		listCheckBox.setCellRenderer(new CheckBoxRenderer());
		listCheckBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listCheckBox.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if(listCheckBox.isEnabled()==false) return;
				int selectedIndex = listCheckBox.locationToIndex(me.getPoint());
				if (selectedIndex < 0)
					return;
				CheckBoxItem item = (CheckBoxItem)listCheckBox.getModel().getElementAt(selectedIndex);
				item.setChecked(!item.isChecked());
				listDescription.setSelectedIndex(selectedIndex);
				listCheckBox.repaint();
				if(listener!=null){
					listener.checkboxListClicked(selectedIndex,item.isChecked());
				}
			}
		});
		// Align both the checkbox height and widths
		listDescription.setFixedCellHeight(20);
		listCheckBox.setFixedCellHeight(listDescription.getFixedCellHeight());
		listCheckBox.setFixedCellWidth(20);

		this.setRowHeaderView(listCheckBox);
		this.setViewportView(listDescription);
	}
	
	private CheckBoxItem[] buildCheckBoxItems(int totalItems) {
		CheckBoxItem[] checkboxItems = new CheckBoxItem[totalItems];
		for (int counter=0;counter<totalItems;counter++) {
			checkboxItems[counter] = new CheckBoxItem();
		}
		return checkboxItems;
	}
	
	public void setCheckboxListListener( CheckboxListListener l ){
		listener = l;
	}
	
	public void setData(String[] strings, Map<Object, Icon> icons2) {
		setCheckList(strings, icons2);
	}
	
	@Override
	public void setEnabled(boolean value){
		listCheckBox.setEnabled(value);
		listDescription.setEnabled(value);
	}
	public void checkAll() {
		for (int i = 0; i < listCheckBox.getModel().getSize(); i++) {
			CheckBoxItem item = (CheckBoxItem)listCheckBox.getModel().getElementAt(i);
			item.setChecked(true);
		}
		listCheckBox.repaint();
	}
	public void uncheckAll() {
		for (int i = 0; i < listCheckBox.getModel().getSize(); i++) {
			CheckBoxItem item = (CheckBoxItem)listCheckBox.getModel().getElementAt(i);
			item.setChecked(false);
		}
		listCheckBox.repaint();
	}

	//-------------------------------------------------------------------
	/* Inner class to hold data for JList with checkboxes */
	class CheckBoxItem {
		private boolean isChecked;

		public CheckBoxItem() {
			isChecked = false;
		}
		public boolean isChecked() {
			return isChecked;
		}
		public void setChecked(boolean value) {
			isChecked = value;
		}
	}

	/* Inner class that renders JCheckBox to JList*/
	class CheckBoxRenderer extends JCheckBox implements ListCellRenderer {
		public CheckBoxRenderer() {
			setBackground(UIManager.getColor("List.textBackground"));
			setForeground(UIManager.getColor("List.textForeground"));
		}
		public Component getListCellRendererComponent(JList listBox, Object obj, int currentindex, 
				boolean isChecked, boolean hasFocus) {
			setSelected(((CheckBoxItem)obj).isChecked());
			return this;
		}
		@Override
		public void setEnabled(boolean b) {
			super.setEnabled(b);
		}
	}
	/**
	 * Allows a JList to show Icons
	 * @author Hamilan
	 */
	class IconListRenderer extends DefaultListCellRenderer {

		private Map<Object, Icon> icons = null;
		public IconListRenderer(Map<Object, Icon> icons) {
			this.icons = icons;
		}

		@Override
		public Component getListCellRendererComponent(
			JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
			// Get the renderer component from parent class
			JLabel label = (JLabel) super.getListCellRendererComponent(list,
					value, index, isSelected, cellHasFocus);
			// Get icon to use for the list item value
			Icon icon = icons.get(value);
			// Set icon to display for value
			label.setIcon(icon);
			return label;
		}
	}
	public interface CheckboxListListener {
		public void checkboxListClicked(int index, boolean value);
	}
	public ArrayList<Integer> getSelectedIndexes() {
		ArrayList<Integer> selected = new ArrayList<Integer>();
		ListModel model = listCheckBox.getModel();
		for(int i = 0; i < model.getSize(); i++) {
		    if( ((CheckBoxItem)model.getElementAt(i)).isChecked() )
		    	selected.add(i);
		}
		return selected;
	}
}
