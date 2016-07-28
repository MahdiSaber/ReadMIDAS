import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

import com.opencsv.CSVReader;

import Exceptions.DifferentLengthExp;
import Exceptions.TimeSignalIrregularity;

public class CNO
{
	private final int HEAD_INDEX = 0;
	private final int FIRST_DATA_ROW_INDEX = 1;

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
		reader.close();
	}

	public List<String> namesCues()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> indices = getNamesCuesIndices();
		List<String> result = new ArrayList<String>();

		for (Integer i : indices)
		{
			String s = headRow[i].replaceAll("TR:", "");
			if (s.endsWith("i"))
			{
				result.add(s.substring(0, s.length() - 1));
			}
			else
			{
				result.add(s);
			}
		}

		return result;
	}

	public List<String> namesStimuli()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> indices = getNamesStimuliIndices();
		List<String> result = new ArrayList<>();

		for (Integer i : indices)
		{
			result.add(headRow[i].replaceAll("TR:", ""));
		}
		return result;
	}

	public List<String> namesInhibitors()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> indices = getNamesInhibitorsIndices();
		List<String> result = new ArrayList<>();

		for (Integer i : indices)
		{
			String s = headRow[i].replaceAll("TR:", "");
			result.add(s.substring(0, s.length() - 1));
		}
		return result;
	}

	public List<String> namesSignals()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> indices = getNamesSignalsIndices();
		List<String> result = new ArrayList<String>();

		for (Integer i : indices)
		{
			result.add(headRow[i].replaceAll("DV:", ""));
		}

		return result;
	}

	public List<Float> timeSignals() throws TimeSignalIrregularity
	{
		//Mahdi: Complete it Tomorrow!
//		List<Integer> time_indices = getTimesSignalsIndices();
//		List<ArrayList<Integer>> all_desired_rows = getSplitedValueCuesIndices();
//		List<Float> result = new ArrayList<>();
//		
//		for (int k = 0; k < getValueCuesCombinationNumbers(); k++)
//		{
//			List<Integer> one_row = valueCues().get(k);
//			
//			for (Float f : one_combination_value_cues)
//			{
////				float candidate_number = data.get(f);
//				for (Integer i : time_indices)
//				{
//
//				}
//			}
//		}

		List<Integer> indices = getTimesSignalsIndices();
		String[] currentRow = null;

		int firstCol = getFirstNameSignalIndex();
		int lastCol = getLastNameSignalIndex();

		List<Float> result = new ArrayList<>();
		for (int i = 1; i < data.size(); i++)
		{
			currentRow = data.get(i);
			float candidate_number = Float.parseFloat(currentRow[indices.get(0)]);

			for (int j = firstCol + 1; j < lastCol; j++)
			{
				if (Float.parseFloat(currentRow[j]) != candidate_number)
				{
					throw new TimeSignalIrregularity();
				}
			}
			if (!result.contains(candidate_number))
			{
				result.add(candidate_number);
			}
		}

		return result;
	}

	public List<ArrayList<Float>> valueCues()
	{
		List<ArrayList<Float>> result = new ArrayList<>();
		ArrayList<Integer> first_combination_indices = getSplitedValueCuesIndices().get(0);
		List<Integer> desired_names_cues_columns = getNamesCuesIndices();
		for (Integer i : first_combination_indices)
		{
			ArrayList<Float> one_row = new ArrayList<>();
			for (Integer j : desired_names_cues_columns)
			{
				one_row.add(Float.parseFloat(data.get(i)[j]));
			}
			result.add(one_row);
		}
		// int x = getValueCuesXDimension();
		// int y = getValueCuesYDimension();
		// int firstCol = getFirstNameSignalIndex();
		// int lastCol = getLastNameSignalIndex();
		//
		// List<ArrayList<Float>> result = new ArrayList<>();
		//
		// for (int i = 1; i <= x; i++)
		// {
		// String[] current_row = data.get(i);
		// ArrayList<Float> a_result_row = new ArrayList<>();
		// for (int j = 1; j <= 4; j++)
		// {
		// a_result_row.add(Float.parseFloat(current_row[j]));
		// }
		// result.add(a_result_row);
		// }
		return result;
	}

	protected int getValueCuesXDimension()
	{
		int result = 0;

		String[] currentRow = null;

		int firstCol = getFirstNameSignalIndex();

		boolean flag_first_row_passed = false;
		String[] first_data_row = data.get(1);
		float candidate_num = Float.parseFloat(first_data_row[firstCol]);

		for (int i = 1; i < data.size() && flag_first_row_passed == false; i++)
		{
			currentRow = data.get(i);

			if (Float.parseFloat(currentRow[firstCol]) == candidate_num)
			{
				result = i;
			}
			else
			{
				flag_first_row_passed = true;
			}
		}

		return result;
	}

	protected int getValueCuesYDimension()
	{
		return (getLastNameSignalIndex() - getFirstNameSignalIndex() + 1);
	}

	protected int getFirstNameSignalIndex()
	{
		String[] headRow = data.get(HEAD_INDEX);
		boolean flag = false;
		int result = -1;

		for (int i = 0; i < headRow.length && flag == false; i++)
		{
			if (headRow[i].contains("DA:"))
			{
				result = i;
				flag = true;
			}
		}

		return result;
	}

	protected int getLastNameSignalIndex()
	{
		String[] headRow = data.get(HEAD_INDEX);
		int result = -1;

		for (int i = 0; i < headRow.length; i++)
		{
			if (headRow[i].contains("DA:"))
			{
				result = i;
			}
		}

		return result;
	}

	public List<Integer> getNamesCuesIndices()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < headRow.length; i++)
		{
			if (!headRow[i].startsWith("TR:Cell") && headRow[i].startsWith("TR:"))
			{
				result.add(i);
			}
		}

		return result;
	}

	public List<Integer> getNamesInhibitorsIndices()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < headRow.length; i++)
		{
			if (!headRow[i].startsWith("TR:Cell") && headRow[i].startsWith("TR:") && headRow[i].endsWith("i"))
			{
				result.add(i);
			}
		}

		return result;
	}

	public List<Integer> getNamesStimuliIndices()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < headRow.length; i++)
		{
			if (!headRow[i].startsWith("TR:Cell") && headRow[i].startsWith("TR:") && !headRow[i].endsWith("i"))
			{
				result.add(i);
			}
		}

		return result;
	}

	public List<Integer> getNamesSignalsIndices()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < headRow.length; i++)
		{
			if (headRow[i].startsWith("DV:"))
			{
				result.add(i);
			}
		}

		return result;
	}

	public List<Integer> getTimesSignalsIndices()
	{
		String[] headRow = data.get(HEAD_INDEX);
		List<Integer> result = new ArrayList<>();

		for (int i = 0; i < headRow.length; i++)
		{
			if (headRow[i].startsWith("DA:"))
			{
				result.add(i);
			}
		}

		return result;
	}

	public int getValueCuesCombinationNumbers()
	{
		return this.getSplitedValueCuesIndices().size();
	}

	public boolean compareRows(String[] row1, String[] row2, int beginCol, int endCol)
	{
		boolean flag_equal = true;

		for (int i = beginCol; i <= endCol && flag_equal == true; i++)
		{
			if (!row1[i].equals(row2[i]))
				flag_equal = false;
		}

		return flag_equal;
	}

	public boolean compareRows(String[] row1, String[] row2) throws DifferentLengthExp
	{
		int last_index = 0;
		if (row1.length == row2.length)
		{
			last_index = row1.length - 1;
		}
		else
		{
			throw new DifferentLengthExp();
		}

		return this.compareRows(row1, row2, 0, last_index);
	}

	public boolean compareRows(String[] row1, String[] row2, List<Integer> columns)
	{
		boolean flag_equal = true;

		for (Integer i : columns)
		{
			if (!row1[i].equals(row2[i]))
				flag_equal = false;
		}

		return flag_equal;
	}

	public boolean compareRows(int row_number1, int row_number2, List<Integer> columns)
	{
		boolean flag_equal = true;
		String[] row1 = data.get(row_number1);
		String[] row2 = data.get(row_number2);

		for (Integer i : columns)
		{
			if (!row1[i].equals(row2[i]))
				flag_equal = false;
		}

		return flag_equal;
	}

	public boolean compareRows(int row_number1, int row_number2, int beginCol, int endCol)
	{
		boolean flag_equal = true;
		String[] row1 = data.get(row_number1);
		String[] row2 = data.get(row_number2);

		for (int i = beginCol; i <= endCol && flag_equal == true; i++)
		{
			if (!row1[i].equals(row2[i]))
				flag_equal = false;
		}

		return flag_equal;
	}

	public List<ArrayList<Integer>> getSplitedValueCuesIndices()
	{

		List<ArrayList<Integer>> result = new ArrayList<>();
		List<Integer> desired_columns = getNamesCuesIndices();
		int upperbound = data.size();
		for (int i = FIRST_DATA_ROW_INDEX; i < upperbound;)
		{
			ArrayList<Integer> one_section = new ArrayList<>();
			one_section.add(i);
			int j = i + 1;
			boolean end_section_happened = false;
			while (j < upperbound && !compareRows(i, j, desired_columns))
			{
				one_section.add(j);
				j++;
			}
			result.add(one_section);
			i = j;
		}
		return result;
	}

}
