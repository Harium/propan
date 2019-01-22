package com.harium.propan.core.loader.mesh;

import com.harium.propan.TestUtils;
import com.harium.propan.core.loader.MeshLoader;
import com.harium.propan.core.loader.VBOLoader;
import com.harium.propan.core.model.Model;
import org.junit.Assert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MeshLoaderTest {

    public static final String PROPAN_MODELS = "propan/assets/models/";

    public static Model loadModel(String filename, VBOLoader loader) {
        URL dir = null;
        try {
            dir = MeshLoader.getInstance().getFullURL(filename);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {

            if (TestUtils.isTestEnvironment(dir)) {
                dir = new URL(MeshLoader.getInstance().getUrl(), PROPAN_MODELS + filename);
            }

            Model vbo = loader.loadModel(dir, filename);
            return vbo;

        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }

        return null;
    }

}
