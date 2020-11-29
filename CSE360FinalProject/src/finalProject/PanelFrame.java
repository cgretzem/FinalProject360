package finalProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
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
	private Repository repos;
	private ArrayList<String> columnNames;
	private ArrayList<ArrayList<Object>> data;

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
		for(String date : repos.getDateList())
		{
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
		Object[][] tableData = new Object[data.size()][data.get(0).size()];
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
		
		
		DefaultTableModel model = new DefaultTableModel(tableData, tableNames);
		JTable table = new JTable(model);
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		scrollPane.setVisible(true);
		
		this.add(scrollPane, BorderLayout.PAGE_START);
		
		revalidate();
		repaint();
		frame.revalidate();
		frame.repaint();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) 
		{
			case "About":
				JOptionPane.showMessageDialog(frame, "Team comprised of Cooper Gretzema");
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
				repaint();
				frame.pack();
				break;
			/*case "addAttendence":
				
				
				try 
				{
					repos.openAttendence(date, frame);
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
				
				
				break;*/
			case "save":
				break;
			case "plotData":
				break;
			default:
				break;
		}
		
	}

	
		
		
}
