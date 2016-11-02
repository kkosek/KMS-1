import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class SaveToFile 
{
	private PrintWriter writer;
	private String fileName;
	
	public SaveToFile(String fileName) 
	{
		this.fileName = fileName;
		createFile();
	}
	
	private void createFile()
	{
		try
		{
			writer = new PrintWriter(fileName);
			
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public void writeData(Vector [] vectors)
	{
		for (Vector vector : vectors)
			writer.println(vector.toString());
	}
	
	public void writeToXYZFormat(Vector [] vectors)
	{
		writer.println(125);
		writer.println("");
		for (int i=0; i<vectors.length; i++)
			writer.println("Ar"+(i+1)+" "+vectors[i].toString());
	}
	
	public void writeCharacteristics(ArrayList<Double> values)
	{
		if(values.size()%6!=0)
			System.out.println("To się wywali, zła liczba charakterystyk.");
		writer.println("t\tH\tV\tT\tP\t\tE");
		for (int i=0; i<values.size(); i+=6)
			writer.println(values.get(i)+"\t"+values.get(i+1)+"\t"+values.get(i+2)+"\t"+values.get(i+3)+"\t"+values.get(i+4)+"\t"+values.get(i+5));
	}
	
	public void writeCoordinatesHeadline()
	{
		writer.println("x_i\ty_i\tz_i\tEkin");
	}
	
	public void writeSingleCoordinate(Vector [] positions, double [] energy)
	{
		writer.println("");
		for (int i=0; i<positions.length; i++)
		{	
			
			writer.print(positions[i].toString());
			writer.print("\t"+energy[i]+"\n");
		}
	}
	
	public void printHeadlineMultiplePositions()
	{
		writer.println(125);
		writer.println();
	}
	
	public void writeMultiplePositions(Vector [] positions)
	{
		for (int i=0; i<positions.length; i++)
			writer.println("Ar"+(i+1)+" "+positions[i].toString());
	}

	
	public void closeFile()
	{
		writer.close();
	}
}