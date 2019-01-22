package com.harium.propan.core.loader.mesh;

import com.harium.etyl.util.PathHelper;
import com.harium.propan.core.loader.MeshLoader;
import com.harium.propan.core.loader.mesh.collada.ColladaLoader;
import com.harium.propan.core.loader.mesh.collada.model.ColladaScene;
import com.harium.propan.core.model.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class ColladaLoaderTest {

    private ColladaLoader loader;

    @Before
    public void setUp() throws MalformedURLException {
        String path = PathHelper.currentFileDirectory();
        URL url = new URL(path);

        MeshLoader.getInstance().setUrl(url.toString());
        MeshLoader.getInstance().addLoader("dae", new ColladaLoader());

        loader = (ColladaLoader) MeshLoader.getInstance().getLoader("dae");
    }

    @Test
    public void testLoadModel() {
        // Load a cube
        String filename = "assimp/collada/cube.dae";

        Model model = MeshLoaderTest.loadModel(filename, loader);
        Assert.assertNotNull(model);
        Assert.assertEquals(12, model.getFaces().size());
        Assert.assertEquals(8, model.getVertices().size());
    }

    @Test
    public void testLoadBlenderScene() {
        // Load a cube
        String filename = "blender/blender.dae";

        Model model = MeshLoaderTest.loadModel(filename, loader);

        ColladaScene scene = loader.getParser().getScene();
        Assert.assertNotNull(scene.getContributor());
        Assert.assertEquals("Blender User", scene.getContributor().getAuthor());
        Assert.assertEquals("Blender 2.79.0 commit date:1970-01-01, commit time:00:00, hash:unknown", scene.getContributor().getAuthoringTool());
        Assert.assertEquals("2019-01-21T23:45:03", scene.getCreated());
        Assert.assertEquals("2019-01-21T23:45:03", scene.getModified());
        Assert.assertEquals(1, scene.geometries.size());

        // Assert Model
        Assert.assertNotNull(model);
        Assert.assertEquals(12, model.getFaces().size());
        Assert.assertEquals(8, model.getVertices().size());
    }

}
