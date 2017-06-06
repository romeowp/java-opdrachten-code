package raytracer;

public class Point {
	
	final static double toleratedError = 0.01;
	private double xx, yy, zz;
	
	Point(double xx, double yy, double zz){
		this.xx = xx;
		this.yy = yy;
		this.zz = zz;
	}
	
	double getX(){
		return this.xx;
	}
	
	double getY(){
		return this.yy;
	}
	
	double getZ(){
		return this.zz;
	}
	
	boolean sameCoordinates(Point point){
		if(Math.abs(this.xx - point.xx) < toleratedError
				&& Math.abs(this.yy - point.yy) < toleratedError
				&& Math.abs(this.zz - point.zz) < toleratedError){
			return true;
		}
		return false;
	}
	
	Point subtract(Point point){
		return new Point(this.xx-point.xx, this.yy-point.yy, this.zz-point.zz);
	}
	
	Point add(Point point){
		return new Point(this.xx+point.xx, this.yy+point.yy, this.zz+point.zz);
	}
	
	Point product(double factor){
		return new Point(this.xx*factor, this.yy*factor, this.zz*factor);
	}
	
	double dotProduct(Point point){
		return this.xx*point.xx + this.yy*point.yy + this.zz*point.zz;
	}
	
	double vectorLength(){
		return Math.sqrt(this.getX()*this.getX() + this.getY()*this.getY() + this.getZ()*this.getZ());
	}
	
}
