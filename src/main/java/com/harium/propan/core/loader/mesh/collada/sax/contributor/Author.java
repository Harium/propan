package com.harium.propan.core.loader.mesh.collada.sax.contributor;

import com.harium.propan.core.loader.mesh.collada.model.ColladaScene;
import com.harium.propan.core.loader.mesh.collada.model.Contributor;
import com.harium.propan.core.loader.mesh.collada.sax.SceneParser;

public class Author extends SceneParser {

    public static final String NAME = "author";
    private Contributor contributor;

    public Author(ColladaScene scene) {
        super(scene);
        this.contributor = scene.getContributor();
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String text = new String(ch, start, length);
        contributor.setAuthor(text);
    }
}
