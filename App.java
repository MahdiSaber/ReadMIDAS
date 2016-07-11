import java.io.FileNotFoundException;
import java.io.IOException;

public class App
{

	public static void main(String[] args)
	{
		CNO cno = new CNO("ToyModelPB.csv");
		try
		{
			cno.reload();
			
//			String temp = cno.data.get(0)[11];
//			System.out.println(temp);
			
			cno.namesInhibitors();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		

	}

}
