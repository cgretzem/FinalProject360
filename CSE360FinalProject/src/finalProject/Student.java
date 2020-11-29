package finalProject;

import java.util.HashMap;

public class Student 
{
	private int id;
	private String firstName, lastName, program, level, asurite;
	private HashMap<String, Integer> dateMap;
	
	public Student(int id, String firstName, String lastName, String program, String level, String asurite)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.program = program;
		this.level = level;
		this.asurite = asurite;
		dateMap = new HashMap<String, Integer>();
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	
	public String getLastName()
	{
		return lastName;
	}
	
	public int getID()
	{
		return id;
	}
	
	public String getProg()
	{
		return program;
	}
	
	public String getASU()
	{
		return asurite;
	}
	
	public String getLevel()
	{
		return level;
	}
	

	public HashMap<String, Integer> getDates()
	{
		return dateMap;
	}
	
	public void addDate(String date, int time)
	{
		Integer newTime = (Integer)time;
		dateMap.put(date, newTime);
		
	}
}

