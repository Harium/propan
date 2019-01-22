package com.harium.propan.core.loader.mesh;

import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.loader.MeshLoader;
import com.harium.propan.core.model.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public void testLoadModel() {
        //Load a cube made with triangles
        String filename = "cube.obj";

        Model vbo = MeshLoaderTest.loadModel(filename, loader);
        Assert.assertNotNull(vbo);
        Assert.assertEquals(12, vbo.getFaces().size());
        Assert.assertEquals(8, vbo.getVertices().size());
    }

    @Test
    public void testLoadModelWithTwoMaterials() {

        // Harium Logo
        String filename = "logo/logo.obj";

        Model vbo = MeshLoaderTest.loadModel(filename, loader);

        Assert.assertNotNull(vbo);
        Assert.assertEquals(36, vbo.getFaces().size());
        Assert.assertEquals(24, vbo.getVertices().size());
        Assert.assertEquals(3, vbo.getMaterials().size());

        // Test Materials
        Assert.assertEquals("left", vbo.getGroups().get(0).getMaterial().getName());
        Assert.assertEquals("right", vbo.getGroups().get(1).getMaterial().getName());
    }
}
