package raytracer;

public interface Shape {
	
	double getDiffuseCoefficient();
	Double intersectionDirectionFactor(Ray ray);
	Ray normal(Point intersectionPoint);
	
}
