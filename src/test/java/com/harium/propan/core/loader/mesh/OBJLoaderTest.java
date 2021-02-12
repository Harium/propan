package com.harium.propan.core.loader.mesh;

import com.harium.etyl.util.PathHelper;
import com.harium.propan.TestUtils;
import com.harium.propan.core.loader.MeshLoader;
import com.harium.propan.core.model.Group;
import com.harium.propan.core.model.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class OBJLoaderTest {

    private OBJLoader loader;

    @Before
    public void setUp() throws MalformedURLException {
        String path = PathHelper.currentFileDirectory();
        URL url = new URL(path);

        MeshLoader.getInstance().setUrl(url.toString());

        loader = (OBJLoader) MeshLoader.getInstance().getLoader("obj");
    }

    @Test
    public void testLoadModel() throws IOException {
        //Load a cube made with triangles
        String filename = "cube.obj";

        URL dir = MeshLoader.getInstance().getFullURL(filename);

            /*if (TestUtils.isTestEnvironment(dir)) {
                dir = new URL(MeshLoader.getInstance().getUrl(), "propan/assets/models/" + filename);
            }*/

            Model vbo = loader.loadModel(dir, filename);
            Assert.assertNotNull(vbo);
            Assert.assertEquals(12, vbo.getFaces().size());
            Assert.assertEquals(8, vbo.getVertices().size());

    }

    @Test
    public void testParseFaceLine() {
        Model vbo = new Model();
        Group group = new Group("group");

        loader.parseFace(vbo, group, "f 1//1 3//1 4//1");
        Assert.assertArrayEquals(new int[]{0, 2, 3}, vbo.getFaces().get(0).vertexIndex);
    }

    @Test
    public void testLoadModelWithTwoMaterials() {

        // Harium Logo
        String filename = "logo/logo.obj";

        URL dir = null;
        try {
            dir = MeshLoader.getInstance().getFullURL(filename);
        } catch (MalformedURLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {

            if (TestUtils.isTestEnvironment(dir)) {
                dir = new URL(MeshLoader.getInstance().getUrl(), "propan/assets/models/" + filename);
            }

            Model vbo = loader.loadModel(dir, filename);
            Assert.assertNotNull(vbo);
            Assert.assertEquals(36, vbo.getFaces().size());
            Assert.assertEquals(24, vbo.getVertices().size());
            Assert.assertEquals(3, vbo.getMaterials().size());

            // Test Materials
            Assert.assertEquals("left", vbo.getGroups().get("left").getMaterial().getName());
            Assert.assertEquals("right", vbo.getGroups().get("right").getMaterial().getName());

        } catch (FileNotFoundException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }

    }
}
