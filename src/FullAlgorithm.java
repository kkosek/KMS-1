import java.util.ArrayList;

public class FullAlgorithm
{
	private Parameters params;
	private PositionsAndMomentums positionsAndMomentums;
	private PotentialEnergyAndForce potentialEnergyAndForce;
	private final int S_0;
	private final int S_d;
	private final int S_out;
	private final int S_xyz;
	private final double tau;
	private final double m;
	private final double k;
	private final int N;
	private Vector [] startPositions;
	private Vector [] startMomentum;
	private Vector [] force;
	private double T;
	private double t;
	private double H;
	private double V;
	private double E;
	private double meantT;
	private double meanP;
	private double meanH;
	private double startH = 0;
	
	private ArrayList<Double> characteristics;
	
	private SaveToFile characteristicsFile;
	private SaveToFile coordinatesFile;
	private SaveToFile multiplePositionsFile;
	
	
	public FullAlgorithm()
	{
		params = new Parameters();
		positionsAndMomentums = new PositionsAndMomentums(params);
		potentialEnergyAndForce = new PotentialEnergyAndForce(params);
		
		S_0 = (int) params.getS_0();
		S_d = (int) params.getS_d();
		S_out = (int) params.getS_out();
		S_xyz = (int) params.getS_xyz();
		tau = params.getTau();
		m = params.getM();
		k = params.getK();
		N = (int) Math.pow(params.getN(), 3);
		
		positionsAndMomentums.setPositions();
		startPositions = positionsAndMomentums.getPositions();
		positionsAndMomentums.setMomentumValues();
		startMomentum = positionsAndMomentums.getMomentum();
		potentialEnergyAndForce.setAll(startPositions);
		force = potentialEnergyAndForce.getForce();
		
		T = 0;
		t = tau;
		H = 0;
		V = 0;
		E = 0;
		characteristics = new ArrayList();
		characteristicsFile = new SaveToFile("characteristics.txt");
		coordinatesFile = new SaveToFile("avs.dat");
		coordinatesFile.writeCoordinatesHeadline();

		multiplePositionsFile = new SaveToFile("multiple.xyz");
	}
	
	public void start()
	{
		positionsAndMomentums.setPositions();
		positionsAndMomentums.setMomentumValues();
		potentialEnergyAndForce.setAll(startPositions);
		potentialEnergyAndForce.writeToFile();
		startAlgorithmLoop();
	}
	
	public void countTemperatureAndHamiltonian(Vector [] momentums)
	{
		T = 0;
		H = 0;
		E = 0;
		
		for (Vector v : momentums)
			E += v.moduleSquare();
		
		E/=(2*m);
		H += E;
		T = H*2/(3*N*k);
		V = potentialEnergyAndForce.getV();
		H += V;
	}
	
	public void updateCharacteristics()
	{
		characteristics.add(t);
		characteristics.add(H);
		characteristics.add(potentialEnergyAndForce.getV());
		characteristics.add(T);
		characteristics.add(potentialEnergyAndForce.getP());
		characteristics.add(E);
	}
	
	public void writeCoordinates(Vector [] v1, Vector [] v2)
	{
		double [] energies = Vector.moduleSquare(v2);
		for (int i=0; i<energies.length; i++)
			energies[i] = energies[i]/(2*m);
			
		coordinatesFile.writeSingleCoordinate(v1, energies);
	}
	
	public void startAlgorithmLoop()
	{
		countTemperatureAndHamiltonian(startMomentum);
		
		Vector [] halfMomentum = Vector.sum(startMomentum, Vector.mulitply(force, 0.5*tau));
		Vector [] nextPositions = Vector.sum(startPositions, Vector.mulitply(halfMomentum, tau/m));

		potentialEnergyAndForce.setAll(nextPositions);
		Vector [] nextForce = potentialEnergyAndForce.getForce();
		
		Vector [] nextMomentum = Vector.sum(halfMomentum, Vector.mulitply(nextForce, 0.5*tau));
		multiplePositionsFile.writeToXYZFormat(startPositions);
		updateCharacteristics();
		
		for (int i=1; i<S_0+S_d; i++)
		{
			t+=tau;
			halfMomentum = Vector.sum(nextMomentum, Vector.mulitply(nextForce, 0.5*tau));
			nextPositions = Vector.sum(nextPositions, Vector.mulitply(halfMomentum, tau/m));
			potentialEnergyAndForce.setAll(nextPositions);
			nextForce = potentialEnergyAndForce.getForce();

			nextMomentum = Vector.sum(halfMomentum, Vector.mulitply(nextForce, tau/2));
			countTemperatureAndHamiltonian(nextMomentum);
			
			if((i % S_out) == 0)
				updateCharacteristics();
			if((i % S_xyz) == 0)
			{
				writeCoordinates(nextPositions, nextMomentum);
				multiplePositionsFile.writeToXYZFormat(nextPositions);
			}
		}
		
		characteristicsFile.writeCharacteristics(characteristics);
		characteristicsFile.closeFile();
		coordinatesFile.closeFile();
		multiplePositionsFile.closeFile();
	}
	

}