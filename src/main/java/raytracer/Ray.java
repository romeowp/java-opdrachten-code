package raytracer;

public class Ray {

	private Point origin, vector;
	
	Ray(Point origin, Point direction){
		this.origin = origin;
		this.vector = direction.subtract(origin).product(1.0 / direction.subtract(origin).vectorLength());
	}
	
	Point getOrigin(){
		return this.origin;
	}
	
	Point getVector(){
		return this.vector;
	}
	
	boolean unblocked(Shape[] shapes, LightSource lightSource){
		Double closestFactor = null;
		for(Shape shape : shapes){
			Double directionFactor = shape.intersectionDirectionFactor(this);
			if(directionFactor != null && (closestFactor == null || directionFactor<closestFactor)){
				closestFactor = directionFactor;
			}
		}
		if(closestFactor == null){
			return true;
		}
		Point closestIntersection = intersectionPoint(closestFactor);
		if(closestIntersection.subtract(this.origin).vectorLength() > lightSource.subtract(this.origin).vectorLength()){
			return true;
		}
		return false;
	}
	
	Point intersectionPoint(Double directionFactor){
		if(directionFactor == null){
			return null;
		}
		return this.origin.add(this.vector.product(directionFactor));
	}
	
}
