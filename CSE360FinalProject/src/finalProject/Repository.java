package finalProject;

import java.awt.Component;

import java.io.File;

import java.util.Stack;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.jfree.data.xy.XYSeries;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;

public class Repository
{
	private Stack<Student> studentList;
	private Stack<String> dateList;
	
	
	/*
	 * Constructor for repository class
	 * @return Repository object
	*/
	public Repository()
	{
		studentList = new Stack<Student>();
		dateList = new Stack<String>();
	}
	
	/*
	 * gets the XY data for plotting the graph
	 * @return XYSeries containing data for graph
	 * @param date:String date to search for
	*/
	public XYSeries getData(String date)
	{
		int[] count = {0,0,0,0,0,0,0,0,0,0,0};
		XYSeries series1 = new XYSeries(date);
		for (Student s : studentList)
		{
			
			double percentage = (10*((double)(s.getDates().get(date)))/75.0);
			if(percentage > 10)
			{
				percentage = 10;
			}
			int finalPercentage = ((int)percentage);
			count[finalPercentage] = count[finalPercentage] +1;
		}
		int[] xAxis = {0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
		for(int i = 0; i< xAxis.length; i++)
		{
			series1.add(count[i],xAxis[i]);
		}
		return series1;
	}
	
	/*
	 * clears the student stack
	 * @return void
	*/
	public void clearStudents()
	{
		studentList = new Stack<Student>();
	}
	
	/*
	 * gets the stack of students stored in repository
	 * @return Stack<Student> list of students
	*/
	public Stack<Student> getStudents()
	{
		return studentList;
	}
	
	/*
	 * gets the stack of dates stored in the repository
	 * @return Stack<String> list of dates
	*/
	public Stack<String> getDateList()
	{
		return dateList;
	}
	
	/*
	 * Opens an attendance file and reads data
	 * @param date:String
	 * @param parent:Component
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @return String confirmation and error messages
	*/
	public String openAttendence(String date, Component parent) throws FileNotFoundException, IOException
	{
		int count = 0;
		Stack<String[]> strangers = new Stack<String[]>();
		Stack<String[]> not_strangers = new Stack<String[]>();
		dateList.push(date);
		System.out.println("Pushing " + date);
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
						not_strangers.push(lines);
						if(studentList.get(i).getDates().containsKey(date))
						{
							studentList.get(i).changeTime(date, Integer.valueOf(lines[1]));
						}
						else
						{
							System.out.println("Added "+ date + "To the student " + studentList.get(i).getASU());
							studentList.get(i).addDate(date, Integer.valueOf(lines[1]));
							count++;
						}
						
					}
					
					else
					{
							if(!strangers.contains(lines))
							{
								strangers.push(lines);
							}
						
					}
				}
			}
			
		}
		for (String[] s : not_strangers)
		{
			if(strangers.contains(s))
			{
				strangers.remove(s);
			}
		}
		br.close();
		String outString = "Data downloaded for " + count + " users in the Roster.\n"
							+strangers.size()+ " additional attendee was found:\n";
		for (String[] a : strangers)
		{
			outString += ""+ a[0] + ", connected for " + a[1] + " minutes\n"; 
		}
		return outString;
	}
	
	/*
	 * Opens a roster file and reads data
	 * @param parent:Component
	 * @return File that was loaded
	*/
	public File loadFile(Component parent)
	{
		//selector sets newFile = user chosen CSV file, otherwise newFile = null;
			File newFile = null;
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV files", "csv", "CSV"); 
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(parent);
			if(returnVal == JFileChooser.APPROVE_OPTION)
			{
				newFile = chooser.getSelectedFile();
			}
			
			return newFile;
	}
	/*
	 * Opens a roster file and reads data
	 * @param parent:Component
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws RuntimeException
	 * @return String confirmation and error messages
	*/
	public void openRoster(Component parent) throws IOException, FileNotFoundException, RuntimeException
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
	

}
