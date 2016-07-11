import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.CSVReader;

public class CNO
{
	private final int HEAD_INDEX = 0;

	private String location;
	// !!! Change to private later!
	public List<String[]> data;

	public CNO(String location)
	{
		this.location = location;
	}

	public void reload() throws FileNotFoundException, IOException
	{
		CSVReader reader = new CSVReader(new FileReader(location));
		data = reader.readAll();
	}

	public List<String> namesCues()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<String> result = new ArrayList<String>(4);
		
		for (int i = 0; i < headRow.length; i++)
		{
			if (headRow[i].contains("TR:") && !headRow[i].contains("TR:Cell"))
			{
				result.add(headRow[i].replaceAll("TR:", ""));
			}
		}
		
		return result;
	}
	
	public List<String> namesStimuli()
	{
		List<String> result = new ArrayList<>(2);
		
		for (String s:namesCues())
		{
			if(!s.endsWith("i"))
			{
				result.add(s);
			}
		}
		
		return result;
	}
	
	public List<String> namesInhibitors()
	{
		List<String> result = new ArrayList<>(2);
		
		for (String s:namesCues())
		{
			if(s.endsWith("i"))
			{
				result.add(s.substring(0, s.length()-1));
			}
		}
		return result;
	}
	
	

}
