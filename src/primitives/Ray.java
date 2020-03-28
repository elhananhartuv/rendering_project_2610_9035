package primitives;

public class Ray {
 Point3D p0;
 Vector direction;
 
 public Ray(Point3D p0,Vector direction) {
	 p0=new Point3D(p0);
	 this.direction=new Vector(direction.normalized());//make sure that the vector are normalize
 }
 
 public Point3D getP0() {
	 return p0; 
 }
 public Vector getDirection() {
	 return direction;
 }
 @Override
 public boolean equals(Object obj) {
	 if(this==obj)return true;
	 if(obj==null)return false;
	 if(!(obj instanceof Ray))return false;
	 Ray temp=(Ray)obj;
	 return p0.equals(temp.p0)&&direction.equals(temp.direction);		 
 }
 
 @Override 
 public String toString() {
	 return "point: "+p0.toString()+" direction: "+direction.toString();
 }
 
}
