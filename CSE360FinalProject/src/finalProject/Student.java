package finalProject;

import java.util.HashMap;

public class Student 
{
	private int id;
	private String firstName, lastName, program, level, asurite;
	private HashMap<String, Integer> dateMap;
	
	/*
	 * Method to create a student object
	 * @param id:int
	 * @param firstName:String
	 * @param lastName:String
	 * @param program:String
	 * @param level:String
	 * @param asurite:String
	 * @return Student object
	 */
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
	
	/*
	 * Method to get first name
	 * @return String firstName
	 */
	public String getFirstName()
	{
		return firstName;
	}
	
	/*
	 * Method to get last name
	 * @return String lastName
	 */
	public String getLastName()
	{
		return lastName;
	}
	
	/*
	 * Method to get ID
	 * @return int ID
	 */
	public int getID()
	{
		return id;
	}
	
	/*
	 * Method to get program name
	 * @return String program
	 */
	public String getProg()
	{
		return program;
	}
	
	/*
	 * Method to get ASURITE login
	 * @return String asurite
	 */
	public String getASU()
	{
		return asurite;
	}
	/*
	 * Method to get level
	 * @return String level
	 */
	public String getLevel()
	{
		return level;
	}
	
	
	/*
	 * Method to update the students time on an attendance file if they are logged more than once
	 * @param date:String
	 * @param time:int
	 * @return void
	 */
	public void changeTime(String date, int time)
	{
		Integer newTime = (Integer)time;
		dateMap.replace(date, dateMap.get(date)+newTime);
	}
	
	/*
	 * Method to return a hashmap of dates with times for each student
	 * @return HashMap<String, Integer>
	 */
	public HashMap<String, Integer> getDates()
	{
		return dateMap;
	}
	
	/*
	 * Method to add a date to a students attendance record
	 * @param date:String
	 * @param time:int
	 * @return void
	 */
	public void addDate(String date, int time)
	{
		Integer newTime = (Integer)time;
		dateMap.put(date, newTime);
		
	}
}

