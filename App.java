
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import Exceptions.DifferentLengthExp;
import Exceptions.TimeSignalIrregularity;

public class App
{

	public static void main(String[] args) throws TimeSignalIrregularity, DifferentLengthExp
	{
		CNO cno = new CNO("ToyModelPB.csv");
		try
		{
			cno.reload();
			
//			String temp = cno.data.get(0)[11];
//			System.out.println(temp);
			
//			System.out.println(cno.getFirstNameSignalIndex());
//			System.out.println(cno.getLastNameSignalIndex());
			
			
//			List<String> result= cno.namesSignals();
			
//			for (String s : result)
//			{
//				System.out.println(s);
//			}
			
			
			// Test: Time Signals
//			List<Float> result2= cno.timeSignals();
//			for (float f : result2)
//			{
//				System.out.println(f);
//			}
			
			//Test: Time Signals X Dimension
			//System.out.println(cno.getValueCuesYDimension());
			
			//Test: ValueCues
//			List<ArrayList<Float>> test_result = cno.valueCues();
//			System.out.println(test_result);
			
			//Test:
//			String[] arr01 = {"1","2","3","4"};
//			String[] arr02 = {"1","2","3","4"};
//			String[] arr03 = {"A","B","C","D"};
//			String[] arr04 = {"1","2","C","D"};
//			ArrayList<Integer> cols = new ArrayList<>();
//			cols.add(0);
//			cols.add(1);
//			cols.add(2);
			
			//System.out.println(cno.compareRows(arr01, arr04,cols));
			
			//Test:
//			List<Float> result04 = cno.timeSignals();
//			System.out.println(result04);
			
			//Test:
//			List<ArrayList<Integer>> result05 = cno.getSplitedValueCuesIndices();
//			System.out.println(result05);
			
			//Test:
//			List<ArrayList<Float>> result06 = cno.valueCues();
//			System.out.println(result06);
			
			//Test:
//			int result07 = cno.getValueCuesCombinationNumbers();
//			System.out.println(result07);
			
			
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
