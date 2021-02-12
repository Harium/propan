package com.harium.propan.core.material;

import com.badlogic.gdx.math.Vector3;

public class OBJMaterial {

	protected String name = "";
	
	//Ambient Color
	protected Vector3 ka;
	protected String mapKa = "";
	
	//Diffuse Color
	protected Vector3 kd;
	protected String mapKd = "";
	
	//Specular Color
	protected Vector3 ks;
	protected String mapKs = "";
	
	//Specular Highlight
	protected Float ns;
	protected String mapNs = "";
	
	protected Float d;
	protected String mapD = "";
	
	protected Integer illum;
		
	public OBJMaterial() {
		super();
	}

	public OBJMaterial(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector3 getKa() {
		return ka;
	}

	public void setKa(Vector3 ka) {
		this.ka = ka;
	}

	public String getMapKa() {
		return mapKa;
	}

	public void setMapKa(String mapKa) {
		this.mapKa = mapKa;
	}

	public Vector3 getKd() {
		return kd;
	}

	public void setKd(Vector3 kd) {
		this.kd = kd;
	}

	public String getMapKd() {
		return mapKd;
	}

	public void setMapKd(String mapKd) {
		this.mapKd = mapKd;
	}

	public Vector3 getKs() {
		return ks;
	}

	public void setKs(Vector3 ks) {
		this.ks = ks;
	}

	public String getMapKs() {
		return mapKs;
	}

	public void setMapKs(String mapKs) {
		this.mapKs = mapKs;
	}

	public Float getNs() {
		return ns;
	}

	public void setNs(float ns) {
		this.ns = ns;
	}

	public String getMapNs() {
		return mapNs;
	}

	public void setMapNs(String mapNs) {
		this.mapNs = mapNs;
	}

	public Float getD() {
		return d;
	}

	public void setD(float d) {
		this.d = d;
	}

	public String getMapD() {
		return mapD;
	}

	public void setMapD(String mapD) {
		this.mapD = mapD;
	}

	public Integer getIllum() {
		return illum;
	}

	public void setIllum(int illum) {
		this.illum = illum;
	}
	
}
