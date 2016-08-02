package ve.environment;

import ve.library.RotationModel;
import ve.library.XmlLoaderModels;

import com.jme.bounding.BoundingBox;
import com.jme.scene.Node;

public class Mesa 
{	
	public static final int TABLE_RADIUS=10;
	public static final String TABLE_PREFIX="TABLE"; 
	
	/**
	 * este metodo se encarga de agregar la mesa de trabajo al escenario
	 */
	public static Node cargarMesa(){
		//cargar la mesa desde el XML
		Node mesa=(Node)XmlLoaderModels.loadModel("mesa_xml.xml");
		mesa.setName(TABLE_PREFIX);
		//escalar la imagen en la escena
		mesa.setLocalScale(0.85f);
		//rotar la mesa para que quede correctamente
		mesa.setLocalRotation(RotationModel.rotateX(-90));
		//agregar dimensiones en el escenario
		mesa.setModelBound(new BoundingBox());
        mesa.updateModelBound();
		//retornar mesa creada
        return mesa;
	}
}
