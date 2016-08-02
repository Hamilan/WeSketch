package sketching.widgets;

public class WidgetData{
	/** the widget that holds this data */
	public transient Widget widget;
	
	/** This widget id. To solve problems of concurrency at assigning id,
	 * should be currentTimeInMillis*100+creatorUserId, later, id%100 will give the creator's userId */
	public long id;
	/** id of the sketch to which this widget is attached */
	public long sketchId;
	/** Position of the widget inside the canvas (virtual sheet) */
	public int x, y;
	/** Rectangular size of the widget. */
	public int width, height;
	/** Text to display in the widget. Not every widget displays text, but most do. */
	public String text;
	/** represents the url of the file to show as icon in the left side of the button */
	public String image;
	/** defines whether the widget should look like available or not */
	public boolean enabled=true;
	/** Order of the widget among the others.
	 * 0 is the last widget drawn, lower orders are drawn later and may occlude prior ones. */
	public int zOrder;
	/** Grouped elements have the same groupId which allows them to be located at the same time */
	public int groupId;
	/** remotely focused */
	public int remotelyFocused=-1;

	//---------THE NEXT ATTRIBUTES ARE HERE TO ALLOW EASY TRANSMISSION WITH KRYONET
	//---------NOT ALL OF THEM ARE COMMON
	/** Some widgets don't use this attribute */
	public String title;
	/** Some widgets don't use this attribute
	 * Frame should use this to determine the buttons to draw in the border
	 * ConfirmDialog should use this to determine the buttons to draw in the Dialog */
	public byte type;
	/** Some widgets don't use this attribute */
	public byte iconType;
	/** Some widgets don't use this attribute */
	public byte value;
	/** Some widgets don't use this attribute */
	public boolean selected;
	/** Some widgets don't use this attribute */
	public WidgetFont font;
	/** Some widgets don't use this attribute */
	public byte alignment;
	/** Holds the name of the widget class */
	public String widgetType;

	//Needed for kryonet
	public WidgetData() {}
	
	public void setFirst(long id, long sketchId, Widget widget){
		this.id = id;
		this.sketchId = sketchId;
		this.widget = widget;
	}
	
	/** updates data */
	public void update(WidgetData widgetData) {
		enabled = widgetData.enabled;
		zOrder = widgetData.zOrder;
		groupId = widgetData.groupId;
		text = widgetData.text;
		
		title = widgetData.title;
		type = widgetData.type;
		iconType = widgetData.type;
		value = widgetData.value;
		selected = widgetData.selected;
		font = widgetData.font;
		alignment = widgetData.alignment;
		widgetType = widgetData.widgetType;
		remotelyFocused = widgetData.remotelyFocused;
		//verification needed because this method is called even when there is no SketchingFrame
		if(widget!=null){	
			widget.setLocation(widgetData.x,widgetData.y);
			widget.setSize(widgetData.width,widgetData.height);
		}
	}
}