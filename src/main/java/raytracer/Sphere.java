package raytracer;

public class Sphere implements Shape {
	
	private Point center;
	private double radius;
	private double diffuseCoefficient;
	
	Sphere(Point centre, double radius){
		this.center = centre;
		this.radius = radius;
		this.diffuseCoefficient = 1.0;
	}
	
	Sphere(Point centre, double radius, double diffuseCoefficient){
		this.center = centre;
		this.radius = radius;
		this.diffuseCoefficient = diffuseCoefficient;
	}
	
	public double getDiffuseCoefficient(){
		return this.diffuseCoefficient;
	}

	public Double intersectionDirectionFactor(Ray ray){
		Point ll = ray.getOrigin().subtract(this.center);
		double aa = ray.getVector().dotProduct(ray.getVector());
		double bb = 2*ray.getVector().dotProduct(ll);
		double cc = ll.dotProduct(ll) - this.radius*this.radius;
		Double[] roots = solveQuadratic(aa, bb, cc);
		if(roots != null){
		    if(roots[0]>0.1 && (roots[0]<roots[1] || roots[1]<0.1)){
		    	return roots[0];
		    }else if(roots[1]>0.1 && (roots[1]<roots[0] || roots[0]<0.1)){
		    	return roots[1];
		    }
		}
		return null;
	}	
		
	private static Double[] solveQuadratic(double aa, double bb, double cc){
		double discriminant = (bb*bb) - (4*aa*cc);
		Double[] roots = new Double[2];
	    if(discriminant < 0){
	    	return null; 
	    }else if(discriminant == 0){
	    	roots[0] = roots[1] = -0.5*bb/aa; 
	    }else{ 
	        double qq = -0.5*(bb + (bb>0 ? 1 : -1)*Math.sqrt(discriminant));
	        roots[0] = qq/aa;
	        roots[1] = cc/qq; 
	    }
	    return roots;
	}

	public Ray normal(Point intersectionPoint) {
		return new Ray(this.center, intersectionPoint);
	} 
	
	
}
