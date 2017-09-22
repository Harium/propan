package com.harium.propan.core.model;

import com.harium.propan.core.material.OBJMaterial;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String name;
    private List<Face> faces = new ArrayList<Face>();

    private OBJMaterial material = NULL_MATERIAL;

    public Group(String name) {
        super();

        this.name = name;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public void setFaces(List<Face> faces) {
        this.faces = faces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OBJMaterial getMaterial() {
        return material;
    }

    public void setMaterial(OBJMaterial material) {
        this.material = material;
    }

    public static final OBJMaterial NULL_MATERIAL = new OBJMaterial();
}
