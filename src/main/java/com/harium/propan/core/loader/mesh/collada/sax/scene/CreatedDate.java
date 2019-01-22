package com.harium.propan.core.loader.mesh.collada.sax.scene;

import com.harium.propan.core.loader.mesh.collada.model.ColladaScene;
import com.harium.propan.core.loader.mesh.collada.sax.SceneParser;

public class CreatedDate extends SceneParser {

    public static final String NAME = "created";

    public CreatedDate(ColladaScene scene) {
        super(scene);
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text = new String(ch, start, length);
        scene.setCreated(text);
    }
}
