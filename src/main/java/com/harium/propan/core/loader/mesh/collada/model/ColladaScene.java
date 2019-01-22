package com.harium.propan.core.loader.mesh.collada.model;

import com.harium.propan.core.loader.mesh.collada.node.GeometryNode;
import com.harium.propan.core.loader.mesh.collada.node.InputNode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ColladaScene {

    private Contributor contributor;
    private String created;
    private String modified;

    public Map<String, GeometryNode> geometries = new HashMap<String, GeometryNode>();
    public Map<Integer, InputNode> inputs = new LinkedHashMap<Integer, InputNode>();
    public Map<String, Integer> sourceOffsets = new HashMap<String, Integer>();

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
