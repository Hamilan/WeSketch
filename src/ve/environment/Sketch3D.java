package ve.environment;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import shared.WeSketchConstants;
import sketching.Sketch;
import sketching.widgets.Widget;
import sketching.widgets.WidgetData;
import ve.library.RotationModel;

import com.jme.bounding.BoundingBox;
import com.jme.image.Texture;
import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.scene.BillboardNode;
import com.jme.scene.Node;
import com.jme.scene.shape.Box;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;

public class Sketch3D {
	public final static float SKETCH_RADIUS=Mesa.TABLE_RADIUS*0.6f;
	public final static int SKETCH_RADIUS_CENTERED=Mesa.TABLE_RADIUS/3;
	public final static Vector3f MIN_POINT=new Vector3f(-0.5f,7.3f,-0.7f),
	MAX_POINT = new Vector3f(0.5f,7.33f,0.7f);
	public static final String SKETCHPREFIX = "3DSk "; 

	int position=0;
	long id;
	String title="";
	Box skBox;
	Node skModel = new Node();

	public Sketch3D(Sketch sketch) {
		this.title=sketch.title;
		this.id = sketch.id;
		skModel = new Node(SKETCHPREFIX+id);
		System.out.println("Created Sk3D with name "+(SKETCHPREFIX+id));
		
		skBox = new Box(SKETCHPREFIX+id, MIN_POINT, MAX_POINT);
		skBox.setModelBound(new BoundingBox());
		skBox.updateModelBound();
		
		skModel.attachChild(skBox);
		
		skModel.attachChild(getSketchLabel(skModel, title));
		skModel.setModelBound(new BoundingBox());
		skModel.updateModelBound();

		if(sketch.widgets.size()>0){
			setTexture(sketch);
		}
	}
	public void locateUnassigned(int position, int angle){
		this.position=position;
		float radius = SKETCH_RADIUS_CENTERED;
		double angleRad=(position*angle)*com.jme.math.FastMath.DEG_TO_RAD;
		
		skModel.setLocalTranslation((float)(radius*Math.cos(angleRad)),
				0,(float)(radius*Math.sin(angleRad)));
		skModel.setLocalRotation(RotationModel.rotateY((float)(position*-angle)));
		skModel.updateRenderState();
	}
	public void locateAssignedTo(int position){
		this.position=position;
		float radius = SKETCH_RADIUS;
		float angle = 360/WeSketchConstants.MAX_SESSION_PARTICIPANTS;
		double angleRad=(position*angle)*com.jme.math.FastMath.DEG_TO_RAD;
		
		skModel.setLocalTranslation((float)(radius*Math.cos(angleRad)),
				0,(float)(radius*Math.sin(angleRad)));
		skModel.setLocalRotation(RotationModel.rotateY((float)(position*-angle)));
		skModel.updateRenderState();
	}
	
	/**
	 * Create a new 3D Sketch and place it around the center pile according to a position and angle  
	 * @param posicion
	 * @param object
	 * @param title
	 * @param angle
	 */
	public Sketch3D(int position, Sketch sketch, int angle) {
		this.position=position;
		this.title=sketch.title;
		this.id = sketch.id;
		float radius = SKETCH_RADIUS_CENTERED;
		double angleRad=position*angle*com.jme.math.FastMath.DEG_TO_RAD;

		skModel = new Node(SKETCHPREFIX+id);
		skModel.setName(SKETCHPREFIX+id);
		skBox = new Box(SKETCHPREFIX+id, MIN_POINT, MAX_POINT);
		skBox.setModelBound(new BoundingBox());
		skBox.updateModelBound();

		skModel.attachChild(skBox);
		skModel.setLocalTranslation((float)(radius*Math.cos(angleRad)),
				0,(float)(radius*Math.sin(angleRad)));
		skModel.setLocalRotation(RotationModel.rotateY((float)(position*-angle)));
		//		skModel.setLocalRotation(RotationModel.rotateY((float)(-60)));
		skModel.updateModelBound();
		skModel.attachChild(getSketchLabel(skModel, title));
		skModel.setModelBound(new BoundingBox());
		skModel.updateModelBound();

		if(sketch.widgets.size()>0){
			setTexture(sketch);
		}
	}
	/**
	 * Create a new Sketch and place it close to the avatar in the position specified 
	 * @param position
	 * @param sketch
	 */
	public Sketch3D(int position, Sketch sketch ) {
		this.position=position;
		this.title = sketch.title;
		this.id = sketch.id;
		float radius = SKETCH_RADIUS;
		float angle = 360/WeSketchConstants.MAX_SESSION_PARTICIPANTS;
		double angleRad=(position*angle)*com.jme.math.FastMath.DEG_TO_RAD;

		skModel = new Node(SKETCHPREFIX+id);
		skModel.setName(SKETCHPREFIX+id);
		skBox = new Box(SKETCHPREFIX+sketch.id, MIN_POINT, MAX_POINT);
		skModel.attachChild(skBox);

		skModel.setLocalTranslation((float)(radius*Math.cos(angleRad)),
				0,(float)(radius*Math.sin(angleRad)));
		skModel.setLocalRotation(RotationModel.rotateY((float)(position*-angle)));
		skModel.attachChild(getSketchLabel(skModel, title));
		skModel.setModelBound(new BoundingBox());
		skModel.updateModelBound();

		if(sketch.widgets.size()>0){
			setTexture(sketch);
		}
	}

	private BillboardNode getSketchLabel(Node modelToLabel, String text){
		float labelSize = 0.023f;
		TextLabel2D label = new TextLabel2D(text);
		label.setBlurStrength(1f);
		BillboardNode quadLabel = label.getBillboard(labelSize,SKETCHPREFIX+"Label"+id);
		quadLabel.setLocalScale(10f/modelToLabel.getLocalScale().x);
		quadLabel.setLocalTranslation(0f,7.45f,0f);
		return quadLabel;
	}

	public Node getModelo() {
		return skModel;
	}

	public void setTexture(Sketch sk){
		int w=0,h=0;//required dimension for the image to generate
		int minX=65535,minY=65535;
		for (WidgetData wd: sk.widgets) {
			if(wd.x+wd.width>w){
				w = wd.x+wd.width;
			}
			if(wd.y+wd.height>h){
				h= wd.y+wd.height;
			}
			if(wd.x<minX){
				minX=wd.x;
			}
			if(wd.y<minY){
				minY=wd.y;
			}
		}
		//w-=minX;
		//h-=minY;
		w = FastMath.nearestPowerOfTwo(w);
		h = FastMath.nearestPowerOfTwo(h);
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setColor(java.awt.Color.WHITE);
		g.fillRect(0, 0, w, h);
		
		//for (int i = sk.widgets.size()-1; i >=0; i--) {
		for ( int i = 0; i <sk.widgets.size(); i++ ) {
			WidgetData wd = sk.widgets.get(i);
			try {
				Class widgetClass = Class.forName("sketching.widgets."+wd.widgetType);
				Widget widget = (Widget)widgetClass.newInstance();
				widget.data.setFirst(wd.id, wd.sketchId, widget);
				widget.data.update(wd);
				BufferedImage img = new BufferedImage(wd.width, wd.height, BufferedImage.TYPE_INT_RGB);
				Graphics2D gw = (Graphics2D) img.getGraphics();
				widget.drawWidgetInside(gw,wd);
				g.drawImage(img,null, wd.x-minX, wd.y-minY);
			} catch (Exception e) {
				System.err.println("Couldn't include widget "+wd.widgetType+" in sketch texture.");
				e.printStackTrace();
			}
		}
		
		TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
		Texture t = TextureManager.loadTexture(image,
				Texture.MinificationFilter.BilinearNearestMipMap,
		        Texture.MagnificationFilter.Bilinear,true);
		ts.setTexture(t, 0);
        ts.setTexture(t);
		
		skBox.setRenderState(ts);
		skBox.updateRenderState();
	}
}
