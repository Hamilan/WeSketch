package ve.library;

import com.jme.image.Texture;
import com.jme.scene.Spatial;
import com.jme.util.TextureManager;

/**
 * Esta clase permite cargar ve.textures para a partir de imagenes
 * @author David Cadema
 * @version 1
 */
public class TextureLoader {
	
	/**
	 * este metodo recibe un String con la ubicacion de nuestra imagen
	 * y posteriormente nos retorna una Textura para ser agregada en
	 * donde sea requerida
	 * @author David Cadena
	 * @param String textureURL
	 * @return Texture
	 */
	static public Texture loadTexture(String textureURL)
	{
		return TextureManager.loadTexture(  
                TextureLoader.class.getClassLoader().getResource("ve/textures/"+textureURL),  
                Texture.MinificationFilter.BilinearNoMipMaps, Texture.MagnificationFilter.Bilinear);
	}
	
	static public void setTexture(Spatial spatial, String path) {
//	    TextureState ts = display.getRenderer().createTextureState();
//	    ts.setEnabled(true);
//	    Texture t1 = TextureManager.loadTexture(TextureLoader.class.getClassLoader().getResource(path));
//	    t1.setApply(Texture.ApplyMode.Replace);
//	    ts.setTexture(t1, 0);
//	    spatial.setRenderState(ts);
	  }


}
