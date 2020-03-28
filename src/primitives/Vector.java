package primitives;

 
public class Vector{

	Point3D head;
	public Point3D getHead() {
		return head;
	}
	public Vector(Coordinate x, Coordinate y, Coordinate z) {
		Point3D temp=new Point3D(x,y,z);//just to check if the Vector equal to Zero
		if(temp.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error cannot accept the zero vector");
		head=temp;
	}
	public Vector(double x, double y, double z) {
		Point3D temp=new Point3D(x,y,z);//just to check if the Vector equal to Zero
		if(temp.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error cannot accept the zero vector");
		head=temp;
	}
	public Vector(Point3D point) {
		if(point.equals(Point3D.ZERO))
			throw new IllegalArgumentException("Error cannot accept the zero vector");
		this.head = new Point3D(point);
	}
	public Vector(Vector vector) {
		this.head=new Point3D(vector.head);
	}

	public Vector add(Vector vector) {
		return new Vector(this.head.x._coord+vector.head.x._coord,this.head.y._coord+vector.head.y._coord,this.head.z._coord+vector.head.z._coord);	
	}
	
	public Vector subtract(Vector vector) {
		return new Vector(this.head.x._coord-vector.head.x._coord,this.head.y._coord-vector.head.y._coord,this.head.z._coord-vector.head.z._coord);
	}
	public Vector scale(double num) {
		return new Vector(num*this.head.x._coord,num*this.head.y._coord,num*this.head.z._coord);		 
	}
	/**
	 * the function calculate dot product
	 * @param vector
	 * @return
	 */
	public double dotProduct(Vector vector) {
		return (this.head.x._coord*vector.head.x._coord+ this.head.y._coord*vector.head.y._coord+ this.head.z._coord*vector.head.z._coord);
	}

	public Vector crossProduct(Vector vec) {
		double x=this.head.y._coord*vec.head.z._coord-this.head.z._coord*vec.head.y._coord;
		double y=this.head.z._coord*vec.head.x._coord-this.head.x._coord*vec.head.z._coord;
		double z=this.head.x._coord*vec.head.y._coord-this.head.y._coord*vec.head.x._coord;
		return new Vector(x,y,z);
	}
	/**
	 *
	 * @return
	 */
	public double lengthSquared() {
		return  head.distanceSquared(Point3D.ZERO);//calculate the distance from (0,0,0)
	}
	public double length() {
		return this.head.distance(Point3D.ZERO);
	}
	/**
	 * The function normalizes and modifies! the vector
	 * @return
	 */
	public Vector normalize() {
		double size=this.length();
		this.head=scale(1/size).head;
		return this;	
	}
	/**
	 * The function returns a vector with the same direction and normalized
	 * @return
	 */
	public Vector normalized() {
		Vector newVec=new Vector(this);
		return newVec.normalize();
	}
	 @Override
	 public boolean equals(Object obj) {
		 if(this==obj)
			 return true;
		 if(obj==null)
			 return false;
		 if(!(obj instanceof Vector))
				 return false;
		 Vector temp=(Vector)obj;
		 return this.head.equals(temp.head);
	 }
	 @Override
	 public String toString() {
		 return "head: "+head.toString();
	 }
}
