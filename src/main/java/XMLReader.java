import com.harium.propan.core.loader.mesh.collada.ColladaParser;
import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.model.Face;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;

public class XMLReader {
	
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, XMLStreamException {

        String ASSETS = PathHelper.currentDirectory().substring(5)+"../assets/";
        String ASSIMP = ASSETS+"assimp/Collada/";

        String path = ASSETS+"model.dae";
        //String path = ASSIMP+"cube.dae";
        //String path = ASSIMP+"cube_triangulate.dae";
        //String path = ASSIMP+"COLLADA.dae";

        ColladaParser colladaParser = new ColladaParser();

        SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
        parser.parse(new File(path), colladaParser);

        Model vbo = colladaParser.getModel();
        printVBO(vbo);

    }

    private static void printVBO(Model vbo) {
        System.out.println("Groups: "+vbo.getGroups());
        System.out.println("Faces: "+vbo.getFaces());
        System.out.println("Vertices: "+vbo.getVertices());

        for(Group group: vbo.getGroups()) {
            System.out.println("Name: "+group.getName());
            System.out.println("Faces: "+group.getFaces().size());
            for(Face face: group.getFaces()) {
                for(int index : face.vertexIndex) {
                    System.out.print(index);
                    System.out.print(" ");
                }
                System.out.println(" ");
            }
        }
    }

}