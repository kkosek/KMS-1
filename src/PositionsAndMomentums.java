
public class PositionsAndMomentums 
{
	private final double k;
	private final double T0; 
	private final Vector b0;
	private final Vector b1;
	private final Vector b2;
	private final double m;
	
	private Vector [] momentumValues;
	private Vector [] positions;
	
	private final int n;
	private int N;
	
	private SaveToFile positionsFile = new SaveToFile("positions.xyz");
	private SaveToFile momentumsFile = new SaveToFile("momentums.xyz");
	
	public PositionsAndMomentums(Parameters parameters) 
	{
		this.n = parameters.getN();
		this.N = (int) Math.pow(n, 3);
		this.k = parameters.getK();
		this.T0 = parameters.getT_0();
		this.m = parameters.getM();		
		this.b0 = parameters.getB0();
		this.b1 = parameters.getB1(); 
		this.b2 = parameters.getB2();
		
		positions = new Vector [N];
		momentumValues = new Vector [N];
	}
	
	public void setPositions()
	{
		int i = 0;

		for (int i2=0; i2<n; i2++)
		{
			for (int i1=0; i1<n; i1++)
			{
				for (int i0=0; i0<n; i0++)
				{
					i = i0 + i1*n + i2*n*n;
					positions[i] = setPosition(i0, i1, i2);
				}
			}
		}
	
		positionsFile.writeToXYZFormat(positions);
		positionsFile.closeFile();
	}
	
	private Vector setPosition(int i0, int i1, int i2)
	{
		Vector v1 = b0.getMultipliedByConst(i0 - (n-1)/2);
		Vector v2 = b1.getMultipliedByConst(i1 - (n-1)/2);
		Vector v3 = b2.getMultipliedByConst(i2 - (n-1)/2);
		
		Vector resultVector = Vector.sum(v1, v2, v3);
		return resultVector;
	}	
	
	private double generateKineticEnergy()
	{
		double randomNumber = Math.random();
		double kineticEnergy = -0.5*k*T0*Math.log(randomNumber);
		return kineticEnergy;
	}
	
	private double setSign()
	{
		double randomNumber = Math.random();
		if(randomNumber <0.5)
			return 1;
		else
			return -1;
	}
	
	private double setSingleMomentumCooridnate()
	{
		double momentumValue = 2*m*generateKineticEnergy();
		momentumValue = setSign()*Math.pow(momentumValue, 0.5);
		return momentumValue;
	}
	
	private Vector setMomentum()
	{
		Vector momentum = new Vector(setSingleMomentumCooridnate(), 
				setSingleMomentumCooridnate(), 
				setSingleMomentumCooridnate());
		return momentum;
	}
	
	public void setMomentumValues()
	{
		for (int i=0; i<N; i++)
			momentumValues[i] = setMomentum();
	
		momentumsFile.writeData(momentumValues);
		momentumsFile.closeFile();
	}
	
	public Vector [] getPositions()
	{
		return positions;
	}
	
	public void getPositions(Vector [] newPositions)
	{
		Test.isEqual(positions.length, newPositions.length);
		for (int i=0; i<positions.length; i++)
			newPositions[i] = new Vector(positions[i]);
	}
	
	public Vector [] getMomentum()
	{
		return momentumValues;
	}
	
	
}