
public class Vector {

	private double x;
	private double y;
	private double z;
	
	public Vector()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public Vector(double x, double y,double z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vector(Vector vector)
	{
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}
	
	public Vector getMultipliedByConst(double constant)
	{
		double x = this.x * constant;
		double y = this.y * constant;
		double z = this.z * constant;
		return new Vector(x, y, z);
	}
	
	public void multiplyThis(double constant)
	{
		this.x = this.x * constant;
		this.y = this.y * constant;
		this.z = this.z * constant;
	}
	
	public static Vector sum(Vector v1, Vector v2, Vector v3)
	{
		Vector resultVector = new Vector(
				v1.x+v2.x+v3.x, 
				v1.y+v2.y+v3.y, 
				v1.z+v2.z+v3.z);
		
		return resultVector;
	}

	public void print()
	{
		System.out.println(this.toString());
	}
	
	@Override
	public String toString()
	{
		String vector = this.x + "\t" + this.y + "\t" + this.z;
		return vector;
	}
	
	public double module()
	{
		double x = this.x*this.x;
		double y = this.y*this.y;
		double z = this.z*this.z;
		
		double module = Math.sqrt(x+y+z);
		return module;
	}
	
	public static Vector subtract(Vector v1, Vector v2)
	{
		double x = v1.x - v2.x;
		double y = v1.y - v2.y;
		double z = v1.z - v2.z;
		return new Vector(x, y, z);
	}
	
	public void add(Vector secondVector)
	{
		this.x += secondVector.x;
		this.y += secondVector.y;
		this.z += secondVector.z;
	}
	
	public static Vector sum(Vector v1, Vector v2)
	{
		Vector v3 = new Vector(v1.x + v2.x,
				v1.y+v2.y,
				v1.z+v2.z);
		return v3;
	}
		
	public static Vector [] sum(Vector [] v1, Vector [] v2)
	{
		if (v1.length!=v2.length)
			System.out.println("Nie zgadza się rozmiar tablic.");
		
		Vector [] v3 = new Vector [v1.length]; 
		
		for (int i=0; i<v1.length; i++)
			v3[i] = sum(v1[i], v2[i]);
		
		return v3;
	}
	
	public static Vector [] mulitply(Vector [] v, double constant)
	{
		Vector [] v2 = new Vector[v.length];
		for(int i=0; i<v.length; i++)
			v2[i] = v[i].getMultipliedByConst(constant);
		
		return v2;
	}
	
	public static Vector [] getInitializedArray(int size)
	{
		Vector [] vectors = new Vector [size];
		for (int i=0; i<size; i++)
			vectors[i] = new Vector();
		
		return vectors;
	}
	
	public double moduleSquare()
	{
		return Math.pow(this.module(), 2);
	}
	
	public static double [] moduleSquare(Vector [] vectors)
	{
		double [] modulesSquare = new double [vectors.length];
		for (int i=0; i<vectors.length; i++)
			modulesSquare[i] = vectors[i].moduleSquare();
		
		return modulesSquare;
	}
}