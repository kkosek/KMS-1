public class Main 
{
	public static double getCalculationTime(long startTime)
	{
		return (System.currentTimeMillis()-startTime)/1000;
	}
	
	public static void main (String [] args)
	{
		long startTime = System.currentTimeMillis();
		System.out.println("Application started...");
		FullAlgorithm algorithm = new FullAlgorithm();
		algorithm.start();
		System.out.print("Finished. Time: ");
		System.out.print(getCalculationTime(startTime) + "s");


	}
}