package geometries;
import primitives.*;
/**
 * interface geometry with one function get normal.
 * @author USER
 *
 */
public interface Geometry {
    Vector getNormal(Point3D point);
}
