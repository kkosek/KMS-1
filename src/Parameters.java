import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class Parameters
{
	private Scanner in;
	private String fileName;
	
	private final int n;
	private final double m;
	private final double e;
	private final double R;
	private final double f;
	private final double L;
	private final double a;
	private final double T_0;
	private final double tau;
	private final double S_0;
	private final double S_d;
	private final double S_out;
	private final double S_xyz;
	private final double k;
	private final double epsilon;
	private final Vector b0;
	private final Vector b1;
	private final Vector b2;
	
	public Parameters()
	{
		fileName = "params.txt";
		try
		{
			in = new Scanner(Paths.get(fileName));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		n = (int) getNextNumberValue();
		m = getNextNumberValue();
		e = getNextNumberValue();
		R = getNextNumberValue();
		f = getNextNumberValue();
		L = getNextNumberValue();
		a = getNextNumberValue();
		T_0 = getNextNumberValue();
		tau = getNextNumberValue();
		S_0 = getNextNumberValue();
		S_d = getNextNumberValue();
		S_out = getNextNumberValue();
		S_xyz = getNextNumberValue();
		k = 8.31/1000;
		epsilon = 1;
		b0 = new Vector(a, 0, 0);
		b1 = new Vector(a/2, a*Math.sqrt(3)/2, 0);
		b2 = new Vector(a/2, a*Math.sqrt(3)/6, a*Math.sqrt((double) 2/3));
		in.close();
	}
	
	
	public Vector getB0()
	{
		return b0;
	}


	public Vector getB1()
	{
		return b1;
	}


	public Vector getB2()
	{
		return b2;
	}


	public double getNextNumberValue()
	{
		String stringValue = in.nextLine().split(" ")[0].trim();
		double numberValue = Double.parseDouble(stringValue);
		return numberValue;
	}


	public int getN()
	{
		return n;
	}


	public double getM()
	{
		return m;
	}


	public double getE()
	{
		return e;
	}


	public double getR()
	{
		return R;
	}


	public double getF()
	{
		return f;
	}


	public double getL()
	{
		return L;
	}

	public double getA()
	{
		return a;
	}

	public double getT_0()
	{
		return T_0;
	}

	public double getTau()
	{
		return tau;
	}

	public double getS_0()
	{
		return S_0;
	}

	public double getS_d()
	{
		return S_d;
	}

	public double getS_out()
	{
		System.out.println("S_out="+S_out);
		return S_out;
	}

	public double getS_xyz()
	{
		System.out.println("S_xyz="+S_xyz);
		return S_xyz;
	}
	
	public double getK()
	{
		return k;
	}
	
	public double getEpsilon()
	{
		return epsilon;
	}
}