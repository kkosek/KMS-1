public class Test
{
	private static int count = 0;
	
	public static void print(Vector [] vectorArray)
	{
		System.out.println("Count="+count);
		System.out.println("Printing started...");
		for (int i=0; i<vectorArray.length; i++)
		{	
			isNull(vectorArray[i]);
			vectorArray[i].print();
		}
		count +=1;
		System.out.println("Printing finished.");
		System.exit(0);
	}
	
	public static void isNull(Object o)
	{
		if(o==null)
		{
			System.out.println("Object is null. Exiting program...");
			System.exit(0);
		}
	}
	
	public static void isEqual(int l1, int l2)
	{
		if (l1!=l2)
		{
			System.out.println("Size is not equal ("+l1+", expected "+l2+")...");
			System.exit(0);
		}
	}
	
	public static void assertEquals(double a, double b)
	{
		if (a!=b)
		{
			System.out.println("Expected " + a +", but there is " + b + "...");
			System.exit(0);
		}
	}
	
	

}