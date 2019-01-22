package com.harium.propan.core.loader.mesh.collada.sax;


import com.harium.propan.core.loader.mesh.collada.model.ColladaScene;

public abstract class SceneParser implements CharacterListener {

    protected ColladaScene scene;

    public SceneParser (ColladaScene scene) {
        this.scene = scene;
    }

}
