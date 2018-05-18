package com.harium.propan.geometry;

import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

/**
 * AABB Bounding Box
 */
public class BoundingBox3D {

	private Vector3 minPoint;
	private Vector3 maxPoint;

	public BoundingBox3D() {
		super();
		this.minPoint = new Vector3(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
		this.maxPoint = new Vector3(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
	}
	
	public BoundingBox3D(Vector3 minPoint, Vector3 maxPoint) {
		super();
		this.minPoint = minPoint;
		this.maxPoint = maxPoint;
	}
	
	public BoundingBox3D(BoundingBox3D box) {
		super();
		this.minPoint = new Vector3(box.minPoint);
		this.maxPoint = new Vector3(box.maxPoint);
	}

	public void add(Vector3 point) {
		float px = point.x;
		float py = point.y;
		float pz = point.z;

		if(px < minPoint.x) {
			minPoint.x = px;
		} else if(px > maxPoint.x) {
			maxPoint.x = px;
		}

		if(py < minPoint.y) {
			minPoint.y = py;
		} else if(py > maxPoint.y) {
			maxPoint.y = py;
		}

		if(pz < minPoint.z) {
			minPoint.z = pz;
		} else if(pz > maxPoint.z) {
			maxPoint.z = pz;
		}
	}

	/**
	 * Test if another BoundingBox intersects
	 * Based on http://forum.devmaster.net/t/aabb-collision/12934/5
	 * @param box
	 * @return boolean
	 */
	public boolean intersects(BoundingBox3D box) {
		boolean intersectX = minPoint.x > box.minPoint.x || maxPoint.x < box.maxPoint.x;
		boolean intersectY = minPoint.y > box.minPoint.y || maxPoint.y < box.maxPoint.y;
		boolean intersectZ = minPoint.z > box.minPoint.z || maxPoint.z < box.maxPoint.z;
		
		return intersectX || intersectY || intersectZ;
	}

	/**
	 * Test if another BoundingBox is inside
	 * Based on http://forum.devmaster.net/t/aabb-collision/12934/5
	 * @param box
	 * @return boolean
	 */
	public boolean contains(BoundingBox3D box) {
		boolean containsX = minPoint.x <= box.minPoint.x && maxPoint.x >= box.maxPoint.x;
		boolean containsY = minPoint.y <= box.minPoint.y && maxPoint.y >= box.maxPoint.y;
		boolean containsZ = minPoint.z <= box.minPoint.z && maxPoint.z >= box.maxPoint.z;

		return containsX && containsY && containsZ;
	}

	public boolean contains(Vector3 point) {
		if(point.x >= minPoint.x && point.x <= maxPoint.x &&
				point.y >= minPoint.y && point.y <= maxPoint.y &&
				point.z >= minPoint.z && point.z <= maxPoint.z) {
			return true;
		}

		return false;
	}
	
	public Vector3 getMinPoint() {
		return minPoint;
	}

	public Vector3 getMaxPoint() {
		return maxPoint;
	}

	public float getVolume() {
		float dx = (maxPoint.x-minPoint.x)/2;
		float dy = (maxPoint.y-minPoint.y)/2;
		float dz = (maxPoint.z-minPoint.z)/2;

		return dx*dy*dz;
	}
	
	public Vector3 getCenter() {
		float cx = (maxPoint.x+minPoint.x)/2;
		float cy = (maxPoint.y+minPoint.y)/2;
		float cz = (maxPoint.z+minPoint.z)/2;
		
		return new Vector3(cx, cy, cz);
	}

	public List<Vector3> getVertexList() {
		
		List<Vector3> vertexList = new ArrayList<Vector3>();
		
		vertexList.add(minPoint);
		vertexList.add(new Vector3(maxPoint.x, minPoint.y, minPoint.z));
		vertexList.add(new Vector3(minPoint.x, maxPoint.y, minPoint.z));
		vertexList.add(new Vector3(minPoint.x, maxPoint.y, maxPoint.z));
		vertexList.add(new Vector3(maxPoint.x, maxPoint.y, minPoint.z));
		vertexList.add(new Vector3(maxPoint.x, minPoint.y, maxPoint.z));
		vertexList.add(new Vector3(minPoint.x, minPoint.y, maxPoint.z));
		vertexList.add(maxPoint);
		
		return vertexList;
	}
}
