
public class PotentialEnergyAndForce 
{
	private final double epsilon;
	private final double L;
	private final double R;
	private final double f;
	private final int N;
	
	private Vector [] force;
	private double P;
	private double V;
	
	private SaveToFile saveForces;
 	
	public PotentialEnergyAndForce(Parameters parameters)
	{
		L = parameters.getL();
		R = parameters.getR();
		f = parameters.getF();
		N = (int) Math.pow(parameters.getN(), 3);
		epsilon = parameters.getEpsilon();
		force = new Vector [N];
		P = 0;
		V = 0;
		saveForces = new SaveToFile("forces.xyz");
	}
		
	private double getPotentialFromWalls(double moduleOfPosition)
	{
		if (moduleOfPosition<L)
			return 0;
		else
			return 0.5*f*(moduleOfPosition-L)*(moduleOfPosition-L);
	}
	
	private Vector getRepulsionFromWallsForce(int index, double moduleOfPosition, Vector [] positions)
	{
		if (moduleOfPosition<L)
			return new Vector(0,0,0);
		else
			return positions[index].getMultipliedByConst(f*(L-moduleOfPosition)/moduleOfPosition);
	}
	
	private double getTemporaryPressure(int index)
	{
		return 1/(4*Math.PI*L*L)*force[index].module();
	}
	
	private double getVanDerWaalsForce(int i, int j, Vector [] positions)
	{
		double moduleR = Vector.subtract(positions[i], positions[j]).module();
		double c = Math.pow(R/moduleR, 6);
		return epsilon*(Math.pow(c, 2) - 2*c);
	}
	
	private Vector getForcesBeetwenAtoms(int i, int j, double moduleOfPosition, Vector [] positions)
	{
		double moduleR = Vector.subtract(positions[i], positions[j]).module();
		double c = Math.pow(R/moduleR, 6);
		Vector forceValue = Vector.subtract(positions[i], positions[j]).getMultipliedByConst(12*epsilon*(c*c - c)*1/(moduleR*moduleR));
		return forceValue;
	}
	
	public void setAll(Vector [] positions)
	{
		double moduleOfPosition = 0;
		Vector forcesBetweenAtoms;
		P = 0;
		V = 0;
		force = Vector.getInitializedArray(force.length);
		
		for (int i=0; i<N; i++)
		{
			moduleOfPosition = positions[i].module();
			V += getPotentialFromWalls(moduleOfPosition);
			
			force[i].add(getRepulsionFromWallsForce(i, moduleOfPosition, positions));
			//getRepulsionFromWallsForce(i, moduleOfPosition, positions).print();
			
			P += getTemporaryPressure(i);
			for (int j=0; j<i; j++)
			{
				V += getVanDerWaalsForce(i, j, positions);
				forcesBetweenAtoms = getForcesBeetwenAtoms(i, j, moduleOfPosition, positions);
				force[i].add(forcesBetweenAtoms);
				force[j].add(forcesBetweenAtoms.getMultipliedByConst(-1));
			}
		}
		
	}
	
	public void writeToFile()
	{	
		saveForces.writeToXYZFormat(force);
		saveForces.closeFile();
	}
	
	public Vector [] getForce()
	{
		return force;
	}
	
	public double getV()
	{
		return V;
	}
	
	public double getP()
	{
		return P;
	}
}