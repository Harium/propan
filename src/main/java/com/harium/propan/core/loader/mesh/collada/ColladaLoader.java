package com.harium.propan.core.loader.mesh.collada;

import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.loader.VBOLoader;
import com.harium.propan.core.model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ColladaLoader implements VBOLoader {

    private ColladaParser parser = new ColladaParser();

    @Override
    public Model loadModel(URL url, String path) throws IOException {
        String fpath = url.getPath();
        String modelFolder = PathHelper.upperDirectory(fpath);

        Model model;
        SAXParser parser = null;
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
            parser.parse(new File(fpath), this.parser);

            return this.parser.getModel();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ColladaParser getParser() {
        return parser;
    }

}
