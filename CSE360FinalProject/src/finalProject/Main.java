package finalProject;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;



@SuppressWarnings("serial")
public class Main extends JFrame
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("CSE360 Final Project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		Dimension maxSize = new Dimension(800,800);
		Dimension minSize = new Dimension(500,500);
		frame.setMaximumSize(maxSize);
		frame.setMinimumSize(minSize);
		PanelFrame panel = new PanelFrame(frame);
		panel.setVisible(true);
		frame.getContentPane().add(panel, BorderLayout.WEST);
	


		frame.pack();
		frame.setVisible(true);
	}
			
}
