package shared;

import java.util.ArrayList;
import java.util.Calendar;

import sketching.Sketch;
import sketching.widgets.WidgetData;

/**
 * This class represents the ongoing Session.
 * Objects of this class are sent to every participant only on their arrival to the session.
 * @author Hamilan
 */
public class Session {
	/**
	 * Brief description of the Session purpose,
	 * may mention the project or some of the requirements
	 */
	public String description="";
	/**
	 * Array holding the participants connections ids.
	 * This array is used to determine the available positions in the roundtable.
	 * Every position is related to one of the constant colors in CVEConstants
	 * A -1 value means available chair 
	 */
	public int chairsIds[]= {-1,-1,-1,-1,-1,-1};
	/**
	 * Information of participants in the session
	 */
	public SessionParticipant participants[] = new SessionParticipant[WeSketchConstants.MAX_SESSION_PARTICIPANTS];
	/**
	 * Requirements to sketch in this session
	 */
	public ArrayList<Requirement> requirements = new ArrayList<Requirement>();
	/**
	 * Sketches produced in this session
	 */
	public ArrayList<Sketch> sketches = new ArrayList<Sketch>();
	
	//--------------------------------------------------------------
	//The next fields are used to register measures of the session
	//--------------------------------------------------------------
	/** Initial date of the session in long value */
	private transient long startDate;
	/** Total time that the session has lasted */
	private transient long totalTime;
	/** messages sent trough the chat */
	private transient ArrayList<ChatMessage> sessionChatMessages = new ArrayList<ChatMessage>();
	/** changes made to widgets during the session */
	private transient ArrayList<Change> changes = new ArrayList<Change>();
	
	public transient static String tareas[] = {
		"RF01. Registrar un producto en un catálogo de venta de un supermercado.\n"+
"La información a ingresar para el producto es:\n"+
"-Código: numérico\n"+
"-Nombre: texto\n"+
"-Descripción: texto largo\n"+
"-Tipo producto: (Aseo, Comestible, Prenda, Licor, Abarrotes, Juguetería)\n"+
"-Incluido en canasta familiar: Si o No\n"+
"-Subir Fotografía del producto: Imagen\n"+
"  Mostrar un contenedor donde aparecerá la imagen\n"+
"Debe permitir cancelar la tarea",
			
"RF02. Registro en línea para un foro de tecnología\n"+
"-Nombre de Usuario(Login)\n"+
"-Contraseña\n"+
"-Confirmar contraseña\n"+
"-E-mail\n"+
"-País (Colombia, Venezuela, Ecuador, Perú, Brazil, Panamá)\n"+
"-Subir Imagen de Usuario/Avatar\n"+
"  (mostrar contenedor de imagen por defecto)\n"+
"-Acepto términos y condiciones\n"+
"Debe permitir cancelar la tarea",

"RF03. Diligenciar una Pregunta, Queja o Reclamo (PQR) en un sistema de Salud\n"+
"-Cédula\n"+
"-Nombre completo\n"+
"-Dirección\n"+
"-Teléfono\n"+
"-E-mail\n"+
"-Ciudad: (Armenia, Pereira, Manizales, Cali, Medellín, Ibagué)\n"+
"-Tipo de diligencia: (Pregunta, Queja, Reclamo, Denuncia)\n"+
"-Descripción\n"+
"Permitir cancelar la tarea"};
	
	public int indexTarea=0;
	
	public Session() {
		//TODO remover estas líneas, pues son solo para que una sesión siempre tenga sketches
//		Sketch defaultSketch1 = new Sketch(0,"Sketch de prueba1","Prueba1","N.N.","R99");		
//		Sketch defaultSketch2 = new Sketch(1,"Sketch de prueba2","Prueba2","N.N.","R98");
//		sketches.add(defaultSketch1);
//		sketches.add(defaultSketch2);
		//TODO remover las líneas anteriores
	}
	public Session(String description) {
		this.description = description;
	}
	public int getPositionOf(String login){
		for (int i = 0; i < participants.length; i++) {
			if(participants[i]!=null && participants[i].login.equals(login)){
				return i;
			}
		}
		return -1;
	}
	public int getPositionOf(int participantId) {
		int pos = 0;
		for (SessionParticipant participant :  participants ) {
			if(participant!=null && participant.id==participantId){
				return pos;
			}
			pos++;
		}
		return -1;
	}

	/**
	 * Finds an available chair for the user and occupies it
	 * @param id
	 * @param part 
	 * @return
	 */
	public synchronized int sitParticipant(int id, SessionParticipant part) {
		for (int i = 0; i < chairsIds.length; i++) {
			if( chairsIds[i]==-1 ){
				chairsIds[i]= id;
				part.chairIndexOrColor = i;
				participants[i] = part;
				return i;	//found available chair
			}
			if( chairsIds[i]==id ){
				return -1; //client already sitted
			}
		}
		return -1;	//couldn't find a seat (room is full)
	}
	/**
	 * Removes the participant indicated by id from every sketch and array
	 * all over the session.
	 * @param id
	 * @return the position (index) where the user was sitted
	 */
	public int removeParticipant(int id) {
		//remove it from every sketch
		for (Sketch sk : sketches) {
			sk.removeCollaborator(id);
		}
		//remove it from array
		int pos = 0;
		for (pos = 0; pos < participants.length; pos++) {
			if(participants[pos]!=null && participants[pos].id==id){
				participants[pos]=null;
				//release chair
				chairsIds[pos]=-1;
				return pos;
			}
		}
		return -1;
	}
	/**
	 * removes from the session the requirement indicated by id
	 * @param id
	 */
	public void removeRequirement(long id) {
		for (Requirement req : requirements) {
			if(req.id==id){
				requirements.remove(req);
			}
		}
	}
	/**
	 * removes from the session the sketch indicated by id
	 * @param id
	 */
	public void removeSketch(long id) {
		for (Sketch sk: sketches) {
			if(sk.id==id){
				sketches.remove(sk);
			}
		}
	}
	/**
	 * Removes widget from the sketch
	 * @param sketchID
	 * @param widgetID
	 */
	public void removeWidget(long sketchID, long widgetID) {
		for (Sketch sk: sketches) {
			if(sk.id==sketchID){
				sk.removeWidget(widgetID);
				return;
			}
		}
		
	}
	/**
	 * Removes the participant from a Sketch
	 * @param skid
	 * @param partID
	 */
	public void removeParticipantFromSketch(long skid, int partID) {
		for (Sketch sk: sketches) {
			if(sk.id==skid){
				sk.removeCollaborator(partID);
				return;
			}
		}
	}
	/**
	 * Searches an sketch among the sketches using it's Id and
	 * returns the list of participants involved in it
	 * @param groupId
	 * @return list of participants involved in the sketch or null if not found
	 */
	public ArrayList<SessionParticipant> getParticipantsInSketch(long groupId) {
		for (Sketch sk : sketches) {
			if(sk.id==groupId){
				return sk.collaborators;
			}
		}
		return null;
	}
	/**
	 * adds the participant specified by participantID to
	 * the sketch specified by sketchID
	 * @param sketchID
	 * @param participantID
	 */
	public void addParticipantToSketch(long sketchID, int participantID) {
		for (Sketch sk : sketches) {
			if(sk.id==sketchID){
				for (int i = 0; i < chairsIds.length; i++) {
					if( chairsIds[i]==participantID ){
						sk.collaborators.add( participants[i] );
						return;
					}
				}
				return;
			}
		}
	}
	public void updateWidget(WidgetData w) {
		for (Sketch sk : sketches) {
			if(sk.id==w.sketchId){
				sk.updateWidget(w);
			}
		}
	}
//--------------------------------------------------------------------------------
	/**
	 * Methods to get statistics about System use
	 */
	public void startTime() {
		totalTime = 0;
		startDate = System.currentTimeMillis();
	}
	public void pauseTime(){
		totalTime += System.currentTimeMillis()-startDate;
		startDate=0;
	}
	public void continueTime(){
		if(startDate==0){
			startDate = System.currentTimeMillis();
		}
	}
	public void resetTime(){
		totalTime = 0;
		startDate = 0;
	}
	public String getTimeInfo(){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(startDate);
		return "Session started on: "+c.get(Calendar.YEAR)+"/"+
			c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH)+
			" at "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+
			c.get(Calendar.SECOND)+" total time was "+(totalTime/1000/60)+"\""+(totalTime/1000%60)+"'";
	}
	public void resetChatMessages(){
		sessionChatMessages = new ArrayList<ChatMessage>();
	}
	public void addChatMessage(ChatMessage c){
		sessionChatMessages.add(c);
	}
	public ArrayList<ChatMessage> getSessionChatMessages() {
		return sessionChatMessages;
	}
	public void resetChanges(){
		changes = new ArrayList<Change>();
	}
	/**
	 * In order to avoid registering lots of changes related to position of one widget
	 * we look for the last change made by the same user and compare all the attributes
	 * if the only different ones are position or size, the change is ignored.  
	 * @param wd
	 */
	public void addChange(WidgetData wd, long userId){
		changes.add( new Change(wd.id, wd.sketchId,wd.x,wd.y,wd.width,wd.height,wd.text,wd.enabled,wd.zOrder,wd.title,wd.type,wd.value,wd.selected,wd.alignment,userId) );
	}
	public  ArrayList<Change> getFilteredChanges(){
		ArrayList<Change> filteredChanges = new ArrayList<Change>();
		for (Change change : changes) {
			if(filteredChanges.size()==0){
				filteredChanges.add(change);
			}
			else{
				if( isWorthSaving(filteredChanges,change) ){
					filteredChanges.add(change);
				}
			}
		}
		return filteredChanges;
	}
	private boolean isWorthSaving(ArrayList<Change> filteredChanges, Change change) {
		for (int i = filteredChanges.size()-1; i >=0; i--) {
			Change prevChange = filteredChanges.get(i);
			//if there was a change in the same widget
			if(change.id == prevChange.id){
				if( change.enabled!=prevChange.enabled || 
					change.selected!=prevChange.selected ||
					change.alignment!=prevChange.alignment||
					change.text!=prevChange.text ||
					change.title!=prevChange.title ||
					change.value!=prevChange.value ||
					change.zOrder!=prevChange.zOrder ||
					change.userId!=prevChange.userId )
				return true;
				if( change.x!=prevChange.x && Math.abs(prevChange.x-change.x)>10){
					return true;
				}
				if( change.y!=prevChange.y && Math.abs(prevChange.y-change.y)>10){
					return true;
				}
				if( change.width!=prevChange.width && Math.abs(prevChange.width-change.width)>10){
					return true;
				}
				if( change.height!=prevChange.height && Math.abs(prevChange.height-change.height)>10){
					return true;
				}
			}
		}
		return true;
	}
	class Change{
		/** widget id */
		public long id;
		/** id of the sketch to which this widget is attached */
		public long sketchId;
		/** Position of the widget inside the canvas (virtual sheet) */
		public int x, y;
		/** Rectangular size of the widget. */
		public int width, height;
		/** Text to display in the widget. Not every widget displays text, but most do. */
		public String text;
		/** defines whether the widget should look like available or not */
		public boolean enabled=true;
		/** Order of the widget among the others.
		 * 0 is the first widget drawn, higher orders are drawn later and may occlude prior ones. */
		public int zOrder;
		/** Some widgets don't use this attribute */
		public String title;
		/** Some widgets don't use this attribute
		 * Frame should use this to determine the buttons to draw in the border
		 * ConfirmDialog should use this to determine the buttons to draw in the Dialog */
		public byte type;
		/** Some widgets don't use this attribute */
		public byte value;
		/** Some widgets don't use this attribute */
		public boolean selected;
		/** Some widgets don't use this attribute */
		public byte alignment;
		private long userId;
		public Change(long id, long sketchId, int x, int y, int width,
				int height, String text, boolean enabled, int zOrder,
				String title, byte type, byte value, boolean selected,
				byte alignment, long userId) {
			this.id = id;
			this.sketchId = sketchId;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.text = text;
			this.enabled = enabled;
			this.zOrder = zOrder;
			this.title = title;
			this.type = type;
			this.value = value;
			this.selected = selected;
			this.alignment = alignment;
			this.userId = userId;
		}
	}
	public int countParticipants() {
		int count=0;
		for (int i = 0; i < participants.length; i++) {
			if(participants[i]!=null){
				count++;
			}
		}
		return count;
	}
}
