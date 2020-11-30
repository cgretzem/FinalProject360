package finalProject;

import java.awt.BorderLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PanelFrame extends JPanel implements ActionListener
{
	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel pan;
	private Repository repos;
	private ArrayList<String> columnNames;
	private ArrayList<ArrayList<Object>> data;
	private JList<String> months; 
	private JList<Integer >days;
	private JTable tableSave;
	public PanelFrame(JFrame frame)
	{

		repos = new Repository();
		columnNames = new ArrayList<String>();
		data = new ArrayList<ArrayList<Object>>();
		this.frame = frame;
		//creating file and about tabs on menu
		JMenuBar menuBar = new JMenuBar();
		JMenu fileHeader = new JMenu("File");
		
		JMenuItem aboutHeader = new JMenuItem("About");
		aboutHeader.addActionListener(this);
		aboutHeader.setActionCommand("About");
		
		menuBar.add(fileHeader);
		menuBar.add(aboutHeader);
		
		
		//creating file Submenu
		JMenuItem loadRoster = new JMenuItem("Load a Roster");
		JMenuItem addAttendence = new JMenuItem("Add Attendence");
		JMenuItem save = new JMenuItem("Save");
		JMenuItem plotData = new JMenuItem("Plot Data");
		
		loadRoster.addActionListener(this);
		addAttendence.addActionListener(this);
		save.addActionListener(this);
		plotData.addActionListener(this);
		
		loadRoster.setActionCommand("loadRoster");
		addAttendence.setActionCommand("addAttendence");
		save.setActionCommand("save");
		plotData.setActionCommand("plotData");
		
		fileHeader.add(loadRoster);
		fileHeader.add(addAttendence);
		fileHeader.add(save);
		fileHeader.add(plotData);
		
		frame.setJMenuBar(menuBar);
		
	}
	
	public void save()
	{
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("savedTable.csv"));
			for(int i = 0; i < tableSave.getColumnCount(); i++)
			{
				bw.write(String.valueOf(tableSave.getColumnName(i)) + ",");
			}
			bw.append("\n");
			for(int j = 0; j < tableSave.getRowCount(); j++)
			{
				for(int i = 0; i < tableSave.getColumnCount(); i++)
				{
					bw.write(String.valueOf(tableSave.getValueAt(j, i)) + ",");
				}
				bw.append("\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void createDateChooser()
	{
		pan = new JPanel();
		String[] monthList = {"January","February", "March", "April","May","June","July","August","September", "October","November","December"}; 
		Integer[] dayList = new Integer[31];
		for(int i = 0; i <=30; i++)
		{
			dayList[i] = i+1;
		}
		pan.setLayout(new BorderLayout());
		JButton button = new JButton("Submit date");
		button.addActionListener(this);
		button.setActionCommand("date");
		months = new JList<String>(monthList);
		days = new JList<Integer>(dayList);
		JScrollPane monthScroll = new JScrollPane(months);
		JScrollPane dayScroll = new JScrollPane(days);
		pan.add(monthScroll, BorderLayout.WEST);
		pan.add(dayScroll, BorderLayout.EAST);
		pan.add(button, BorderLayout.SOUTH);
		add(pan,BorderLayout.EAST);
		pan.setVisible(true);
		

	}
	
	public void createJTable()
	{
		if(columnNames.isEmpty())
		{
			//adding base columns no dates
			columnNames.add("ID");
			columnNames.add("First Name");
			columnNames.add("Last Name");
			columnNames.add("Program");
			columnNames.add("Level");
			columnNames.add("ASURITE");
		}
		//adding dates 
		System.out.println(repos.getDateList().size());
		for(String date : repos.getDateList())
		{
			System.out.println("Added " + date);
			columnNames.add(date);
		}
		
		
		for (Student s:repos.getStudents())
		{
			//adding student base data to array list
			ArrayList<Object> temp = new ArrayList<Object>();
			temp.add(s.getID());
			temp.add(s.getFirstName());
			temp.add(s.getLastName());
			temp.add(s.getProg());
			temp.add(s.getLevel());
			temp.add(s.getASU());
			//adding dates to arraylist
			if(!s.getDates().isEmpty())
			{
				for(String date : repos.getDateList())
				{
					if(s.getDates().containsKey(date))
					{
						temp.add(s.getDates().get(date));
					}
					else
					{
						temp.add(0);
					}
					
				}
					
			}
			data.add(temp);
		}
		//converting from arraylist to array
		Object[][] tableData = new Object[data.size()][columnNames.size()];
		String[] tableNames = new String[columnNames.size()];
	
		
		for(int i = 0; i < data.size(); i++)
		{
			for(int j = 0; j < data.get(i).size(); j++)
			{
				tableData[i][j] = data.get(i).get(j);
			}
		}
		
		for(int i = 0; i < columnNames.size(); i++)
		{
			tableNames[i] = columnNames.get(i);
		}
		
		data = new ArrayList<ArrayList<Object>>();
		DefaultTableModel model = new DefaultTableModel(tableData, tableNames);
		JTable table = new JTable(model);
		this.tableSave = table;
		scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setVisible(true);
		
		this.add(scrollPane, BorderLayout.WEST);
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) 
		{
			case "About":
				JOptionPane.showMessageDialog(frame, "Team comprised of Cooper Gretzema, James Sun");
				break;
			case "loadRoster":
				try 
				{
					repos.openRoster(frame);

				} 
				catch (IOException e1) 
				{
					if(e1.getMessage().contentEquals("Wrong type of file"))
					{
						JOptionPane.showMessageDialog(frame, "Error loading file");
					}
						
				}
				catch (RuntimeException e1)
				{
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
				
				createJTable();
				revalidate();
			
				break;
			case "addAttendence":
				
				createDateChooser();
				this.remove(scrollPane);
				revalidate();
				
				
				
				
				break;
			case "save":
				save();
				break;
				
			case "date":
				pan.setVisible(false);
				
				String outDate = months.getSelectedValue() +" " +days.getSelectedValue();
				System.out.println("outDate is " + outDate);
				try 
				{
					JOptionPane.showMessageDialog(frame, repos.openAttendence(outDate, frame));
					
				} 
				catch (IOException e1) 
				{
					if(e1.getMessage().contentEquals("Wrong type of file"))
					{
						JOptionPane.showMessageDialog(frame, "Error loading file");
					}
						
				}
				catch (RuntimeException e1)
				{
					JOptionPane.showMessageDialog(frame, e1.getMessage());
				}
				createJTable();
				revalidate();
			break;
			case "plotData":
				break;
			default:
				break;
		}
		
	}

	
		
		
}
