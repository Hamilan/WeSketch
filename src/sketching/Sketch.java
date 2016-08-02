package sketching;

import java.util.ArrayList;

import shared.WeSketchConstants;
import shared.SessionParticipant;
import sketching.widgets.WidgetData;

/**
 * This class represents the Sketch properties.
 * Objects of this class can be sent through the net with Kryonet.
 * Includes:<br/>
 * id, title, description, author, requisiteRelated<br/>
 * widgets collection, collaborators collection
 * @author Hamilan
 */
public class Sketch {
	/** This Sketch's id. To solve problems of concurrency at assigning id, should
	 *  be currentTimeInMillis*100+creatorUserId, later, id%100 will give the creator's userId */
	public long id;
	/** Name of the sketch, e.g: Login Window */
	public String title;
	/** Brief description of the sketch */
	public String description;
	/** Designer who created it */
	public String author;
	/** Name of the requirement for whom this sketch is being developed. */
	public String requisiteRelated;

	/** Set of widgets contained in this sketch */
	public ArrayList<WidgetData> widgets = new ArrayList<WidgetData>();
	/** Group of participants working on the same sketch */
	public ArrayList<SessionParticipant> collaborators = new ArrayList<SessionParticipant>();

	/** Necessary non-arg constructor for Kryonet */
	public Sketch() {
	}	
	/**
	 * Only constructor
	 * @param title
	 * @param description
	 * @param author
	 * @param requisiteRelated
	 */
	public Sketch(long id,String title, String description, String author,
			String requisiteRelated) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.author = author;
		this.requisiteRelated = requisiteRelated;
	}
	/** adds the widget w to the widgets list in order according to w.zOrder */
	public void addWidget(WidgetData w){
		int i = 0;
		for (WidgetData wd : widgets) {
			if(wd.zOrder>=w.zOrder){
				widgets.add(i,w);
				break;
			}
			i++;
		}
	}
	/** removes from the list the widget with id = widgetID */
	public void removeWidget(long widgetId){
		//TODO: do something to allow Undo?
		for (WidgetData w : widgets) {
			if(w.id==widgetId){
				widgets.remove(w);
				return;
			}
		}
	}
	public void addCollaborator(SessionParticipant c){
		collaborators.add(c);
	}

	/** removes the participant with id = collaboratorId */
	public void removeCollaborator(int collaboratorId){
		for (SessionParticipant p : collaborators) {
			if(p.id==collaboratorId){
				collaborators.remove(p);
				System.out.println("Removed "+p.id+" from sketch");
				return;
			}
		}
	}
	/** Updates the widget w.id with the information received
	 * if the widget didn't exist before, then adds it to the list*/
	public void updateWidget(WidgetData w) {
		for (WidgetData wd : widgets) {
			if(wd.id==w.id){
				wd.update(w);
				return;
			}
		}
		//if widget didn't exist
		addWidget(w);
		return;
	}
	/**
	 * finds the minimum zOrder in sketch minus 1
	 * @return
	 */
	public int getNewZorder() {
		int newZOrder = WeSketchConstants.MAX_ZORDER/2;
		for (WidgetData w : widgets) {
			if(w.zOrder<newZOrder){
				newZOrder = w.zOrder;
			}
		}
		return newZOrder-1;
	}
	/**
	 * finds the maximum zOrder in sketch plus 1
	 * @return
	 */
	public int getMaxZorder(){
		int maxZOrder = WeSketchConstants.MAX_ZORDER/2;
		for (WidgetData w : widgets) {
			if(w.zOrder>maxZOrder){
				maxZOrder = w.zOrder;
			}
		}
		return maxZOrder+1;
	}
	public void moveToBottom(WidgetData data) {
		int i;
		for ( i = 0; i < widgets.size(); i++) {
			if(widgets.get(i).id==data.id){
				break;
			}
		}
		if( widgets.remove(i)==null ){
			System.out.println("Couldn't move to bottom");;
		}else{
			widgets.add(0,data);
		}
		listarWidgets();
	}
	public void moveToFront(WidgetData data) {
		int i;
		for ( i = 0; i < widgets.size(); i++) {
			if(widgets.get(i).id==data.id){
				break;
			}
		}
		if( widgets.remove(i)==null ){
			System.out.println("couldn't move to front");;
		}else{
			widgets.add(data);
		}
		listarWidgets();
	}
	public void listarWidgets(){
		int i = 0;
		for (WidgetData w : widgets) {
			System.out.println(w.widgetType+" at "+i);
			i++;
		}
	}
	public void moveToFront(long wId) {
		for (int i = 0; i < widgets.size(); i++) {
			WidgetData wd = widgets.get(i);
			moveToFront(wd);
			break;
		}
	}
	public void moveToBottom(long wId) {
		for (int i = 0; i < widgets.size(); i++) {
			WidgetData wd = widgets.get(i);
			moveToBottom(wd);
			break;
		}
	}
}
