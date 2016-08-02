package ve.library;

import java.net.URL;
import java.util.ArrayList;

import com.jme.scene.Spatial;
import com.jme.util.export.xml.XMLImporter;

/**
 * This class allows the creation of JME working models from XML files created
 * with blender's plugin HottBJ Exporter
 * @author David Cadena
 * @author Hamilan
 * @version 1
 */
public class XmlLoaderModels
{
	private static XMLImporter xmlImporter = XMLImporter.getInstance();
		
	/**
	 * Este metodo recibe un String con la ubicacion de nuestro modelo
	 * y posteriormente nos retorna un Spatial para ser agregado en
	 * cualquier nodo que sea requerido
	 * @author David Cadena
	 * @param String modelURL
	 * @return Spatial
	 */
	public static Spatial loadModel(String modelURL) 
	{
		Spatial loadedSpatial = null;
		//System.out.println("loading modelURL: "+modelURL);
		try {
			loadedSpatial = (Spatial) xmlImporter.load(new URL(""+XmlLoaderModels.class.getClassLoader().getResource("ve/models/")+modelURL));
		}
		catch (Exception e) {
			System.out.print("failed to load modelURL: "+modelURL);
		}
		return loadedSpatial;
	}

	/**
	 * Este metodo recibe un ArrayList de String con las ubicaciones de
	 * nuestros modelo y posteriormente nos retorna un ArrayList de
	 * Spatial en igual orden para ser agregado en cualquier nodo que
	 * sea requerido
	 * @author David Cadena
	 * @param String modelURL
	 * @return Spatial
	 */
	public static ArrayList<Spatial> loadModels(ArrayList<String> modelsURL) 
	{
		Spatial loadedSpatial = null;
		ArrayList<Spatial> spatialList=new ArrayList<Spatial>();
				
		for(int i=0;i<modelsURL.size();i++) {
			System.out.println("loading modelURL: "+modelsURL.get(i));
			try {
				loadedSpatial = (Spatial) xmlImporter.load(new URL(""+XmlLoaderModels.class.getClassLoader().getResource("ve/models/")+modelsURL.get(i)));
				spatialList.add(loadedSpatial);
			}
			catch (Exception e) {
				System.out.print("failed to load modelURL: "+modelsURL.get(i));
				return null;
			}	
			
		}
		return spatialList;
	}
}
