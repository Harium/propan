/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.harium.propan.math;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.harium.propan.geometry.BaseCylinder;
import com.harium.propan.geometry.OrientedBoundingBox;

/**
 * Class offering various static methods for intersection testing between different geometric objects.
 *
 * @author badlogicgames@gmail.com
 * @author jan.stria
 * @author Nathan Sweet
 */
public class Intersector extends com.badlogic.gdx.math.Intersector {

    static public float intersectRayBounds(Ray ray, OrientedBoundingBox obb) {
        return intersectRayBounds(ray, obb, obb.transform);
    }

    public static boolean intersectRayCylinderFast(Ray ray, BaseCylinder cylinder) {

        Vector3 position = cylinder.transform.getTranslation(new Vector3());

        Vector3 minimum = new Vector3(position).sub(cylinder.radius);
        Vector3 maximum = new Vector3(position).add(cylinder.radius);

        if (!intersectRayBoundsFast(ray, new BoundingBox(minimum, maximum))) {
            return false;
        }

        final float D3_EPSILON = 0.0001f;
        double lambda;

        Vector3 RC = new Vector3(ray.origin).sub(position);
        Vector3 dir = new Vector3(ray.direction).sub(position);

        float d;
        float t;
        double s;
        Vector3 n, D, O;
        float ln;
        double in, out;

        Vector3 cylinderAxis = Vector3.Y;
        n = new Vector3(dir).crs(cylinderAxis);
        ln = n.len();

        if ((ln < D3_EPSILON) && (ln > -D3_EPSILON))
            return false;

        n.nor();

        d = Math.abs(new Vector3(RC).dot(n));

        if (d <= cylinder.radius) {
            O = new Vector3(RC).crs(cylinderAxis);
            //TVector::cross(RC,cylinder._Axis,O);
            t = -O.dot(n) / ln;
            //TVector::cross(n,cylinder._Axis,O);
            O = new Vector3(n).crs(cylinderAxis);
            O.nor();
            s = Math.abs(Math.sqrt(cylinder.radius * cylinder.radius - d * d) / dir.dot(O));

            in = t - s;
            out = t + s;

            if (in < -D3_EPSILON) {
                if (out < -D3_EPSILON)
                    return false;
                else lambda = out;
            } else if (out < -D3_EPSILON) {
                lambda = in;
            } else if (in < out) {
                lambda = in;
            } else {
                lambda = out;
            }

            // Calculate intersection point
            /*Vector3 intersection = new Vector3(ray.origin);
			intersection.add((float)(dir.x*lambda), (float)(dir.y*lambda), (float)(dir.z*lambda));
			intersection.sub(cylinder.position);
			
			//Calculate the normal
			Vector3 HB = intersection;
			
			float scale=HB.Dot(&cylinder.axis);
			normal.x=HB.x-cylinder.axis.x*scale;
			normal.y=HB.y-cylinder.axis.y*scale;
			normal.z=HB.z-cylinder.axis.z*scale;
			normal.Normalize();*/
            return true;
        }

        return false;
    }

}
