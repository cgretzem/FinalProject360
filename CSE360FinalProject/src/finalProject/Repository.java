package finalProject;

import java.awt.Component;

import java.io.File;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class Repository
{
	private Stack<Student> studentList;
	private Stack<String> dateList;
	
	public Repository()
	{
		studentList = new Stack<Student>();
		dateList = new Stack<String>();
	}
	
	public void openAttendence(String date, Component parent) throws FileNotFoundException, IOException
	{
		dateList.add(date);
		File newFile = loadFile(parent);
		if(newFile == null)
		{
			throw new FileNotFoundException("Wrong type of file");
		}
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		
		br = new BufferedReader(new FileReader(newFile));
		while((line = br.readLine()) != null)
		{
			String[] lines = line.split(delimiter);
			if (lines.length != 2)
			{
				br.close();
				throw new RuntimeException("File was not formatted correctly");
			}
			else
			{
				for(int i = 0; i < studentList.size(); i++)
				{
					if(studentList.get(i).getASU().equals(lines[0]))
					{
						if(studentList.get(i).getDates().containsKey(date))
						{
							studentList.get(i).changeTime(date, Integer.valueOf(lines[1]));
						}
						else
						{
							studentList.get(i).addDate(date, Integer.valueOf(lines[1]));
						}
						
					}
				}
			}
			
		}
		br.close();
	}
	
	
	public File loadFile(Component parent)
	{
		//selector sets newFile = user chosen CSV file, otherwise newFile = null;
			File newFile = null;
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", ".csv"); 
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(parent);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				newFile = chooser.getSelectedFile();
			}
			
			return newFile;
	}
	
	public void openRoster(Component parent) throws IOException, FileNotFoundException
	{
		File newFile = loadFile(parent);
		
		if(newFile == null) // if loadFile fails
		{
			throw new FileNotFoundException("Wrong type of file");
		}
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		
		br = new BufferedReader(new FileReader(newFile));
		while((line = br.readLine()) != null)
		{
			String[] student = line.split(delimiter);
			if (student.length != 6)
			{
				br.close();
				throw new RuntimeException("File was not formatted correctly");
			}
			else
			{
				//create a new student object with the line of values
				studentList.add(new Student(Integer.valueOf(student[0]),student[1],student[2],student[3],student[4],student[5]));
			}
			
		}
		br.close();
			
		
		
	}
	
	//public Stack getPoints(){} TODO

}
