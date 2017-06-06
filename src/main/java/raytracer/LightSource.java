package raytracer;

public class LightSource extends Point {
	
	private double red, green, blue;
	
	LightSource(double xx, double yy, double zz, double red, double green, double blue){
		super(xx, yy, zz);
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	LightSource(double xx, double yy, double zz){
		super(xx, yy, zz);
		this.red = 0;
		this.green = 0;
		this.blue = 0;
	}
	
	double[] illuminate(double[] rgb){
		return illuminate(rgb, 1.0);
	}
	
	double[] illuminate(double[] rgb, double diffusion){
		double[] newRGB = new double[3];
		newRGB[0] = Math.min(255.0, rgb[0] + this.red*diffusion);
		newRGB[1] = Math.min(255.0, rgb[1] + this.green*diffusion);
		newRGB[2] = Math.min(255.0, rgb[2] + this.blue*diffusion);
		return newRGB;
	}

}
