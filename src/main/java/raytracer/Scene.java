package raytracer;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Scene {
	
	private Color[][] pixels = new Color[640][640];

	public static void main(String args[]) throws IOException{
		Point topLeft = new Point(-50.0, 50.0, 50.0);
		Point topRight = new Point(50.0, 50.0, 50.0);
		Point bottomLeft = new Point(-50.0, -50.0, 50.0);
		Point viewPoint = new Point(0.0, 0.0, 0.0);
		Shape[] shapes = new Shape[] {
				new Sphere(new Point(-20.0, -20.0, 100.0), 10.0, 0.8), 
				new Sphere(new Point(50.0, 0.0, 300.0), 100.0, 0.8)};
		LightSource[] lightSources = new LightSource[] {
				new LightSource(-30.0, -20.0, 50.0, 255.0, 0.0, 0.0), 
				new LightSource(0.0, 100.0, 150.0, 0.0, 0.0, 255.0),
				new LightSource(0.0, 0.0, 0.0, 100.0, 100.0, 100.0)};
		Scene scene = new Scene(topLeft, topRight, bottomLeft, viewPoint, shapes, lightSources);
		scene.createImage();
	}
	
	Scene(Point topLeft, Point topRight, Point bottomLeft, Point viewPoint, Shape[] shapes, LightSource[] lightSources){
		Point xDifference = topRight.subtract(topLeft);
		Point yDifference = bottomLeft.subtract(topLeft);
		for(int xx = 0; xx < this.pixels.length; xx++){
			for(int yy = 0; yy < this.pixels[0].length; yy++){
				Point direction = topLeft.add(xDifference.product((double) xx/(this.pixels.length-1)))
						.add(yDifference.product((double) yy/(this.pixels[0].length-1)));
				Ray lineOfSight = new Ray(viewPoint, direction);
				Double closestFactor = null;
				Shape closestShape = null;
				for(Shape shape : shapes){
					Double directionFactor = shape.intersectionDirectionFactor(lineOfSight);
					if(directionFactor != null && (closestFactor == null || directionFactor<closestFactor)){
						closestFactor = directionFactor;
						closestShape = shape;
					}
				}
				Point inSight = lineOfSight.intersectionPoint(closestFactor);
				double[] rgb = {0.0, 0.0, 0.0};
				if(inSight != null){
					for(LightSource lightSource : lightSources){
						Ray lightBeam = new Ray(inSight, lightSource);
						if(lightBeam.unblocked(shapes, lightSource)){
							Ray normal = closestShape.normal(inSight);
							double diffusion = Math.max(0, closestShape.getDiffuseCoefficient() * normal.getVector().dotProduct(lightBeam.getVector()));
							rgb = lightSource.illuminate(rgb, diffusion);
						}
					}
				}
				this.pixels[xx][yy] = new Color((int)(rgb[0]+0.5), (int)(rgb[1]+0.5), (int)(rgb[2]+0.5));
			}
		}
	}
	
	void createImage() throws IOException {
		BufferedImage image = new BufferedImage(this.pixels.length, this.pixels[0].length, BufferedImage.TYPE_INT_RGB);
		for (int ii = 0; ii < this.pixels.length; ii++) {
		    for (int jj = 0; jj < this.pixels[0].length; jj++) {
		        image.setRGB(ii, jj, pixels[ii][jj].getRGB());
		    }
		}
		ImageIO.write(image, "png", new File("image.png"));
	}
}
