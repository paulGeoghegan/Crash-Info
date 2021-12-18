/********************
	Author:
Paul Geoghegan
	Description:
This class will display the information from the CSV
	Date:
Started: 14/12/2021

********************/

//Awt packages
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//Swing packages
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

//Util packages
import java.util.LinkedList;

public class Screen extends JFrame implements ActionListener, MouseListener
{

	private static final long serialVersionUID = 1L;
	JPanel textPanel, buttonPanel;
	private FileManager FM;
	private LinkedList<String[]> rows = new LinkedList<String[]>();
	private JLabel[][] textGrid = new JLabel[11][13];
	private JButton scrollUpButton, scrollDownButton, insertCrashButton, updateCrashButton, deleteCrashButton;
	private int rowNumber = 1;

	//Constructor
	public Screen(String title, FileManager FM)
	{
		//super for title
		super(title);
		//Sets screen size
		setSize(800,800);
		//Sets layout for screen
		setLayout(new GridLayout());

		//Sets file Manager
		this.FM = FM;
		//Gets initial rows
		rows = FM.getRows(rowNumber);

		//Creates new panels for displaying text and buttons
		textPanel = new JPanel(new GridLayout(11, rows.getFirst().length+1));
		textPanel.setBackground(Color.white);
		buttonPanel = new JPanel(new GridLayout(5, 1));
		buttonPanel.setBackground(Color.lightGray);

		//Creates new JButtons
		scrollUpButton = new JButton("Scroll Up");
		scrollUpButton.addActionListener(this);
		buttonPanel.add(scrollUpButton);
		insertCrashButton = new JButton("Add New");
		insertCrashButton.addActionListener(this);
		buttonPanel.add(insertCrashButton);
		updateCrashButton = new JButton("Update Crash");
		updateCrashButton.addActionListener(this);
		buttonPanel.add(updateCrashButton);
		deleteCrashButton = new JButton("Delete Crash");
		deleteCrashButton.addActionListener(this);
		buttonPanel.add(deleteCrashButton);
		scrollDownButton = new JButton("Scroll Down");
		scrollDownButton.addActionListener(this);
		buttonPanel.add(scrollDownButton);

		//Adds Panels to screen
		add(textPanel);
		add(buttonPanel);

		//Adds text to grid
		createGrid();

		//Makes things visible
		setVisible(true);

	} //End Constructor

	//Method for createGrid in the text on the grid
	public void createGrid()
	{

		//Adds labels to grid
		for(short i = 0;i < 11;i++)
		{

			//Adds row numbers
			textPanel.add(new JLabel(String.valueOf(i)));

			for(short j = 0;j < rows.getFirst().length;j++)
			{

				//Adds text to label
				textGrid[i][j] = new JLabel(rows.get(i)[j]);
				//Adds element to JPanel
				textPanel.add(textGrid[i][j]);

			} //End inner for
		} //End outer for

	} //End createGrid


	//Method for filling in the text on the grid
	public void fillGrid()
	{

		int crashesSize = FM.getCrashes().size();

		//Changes labels on grid
		for(short i = 0;i < 11;i++)
		{

			for(short j = 0;j < rows.getFirst().length;j++)
			{

				//Checks if values are null
				if((i+rowNumber)-1 < crashesSize && i < rows.size())
				{

					//Changes text on label
					textGrid[i][j].setText(rows.get(i)[j]);

				} //End if
				else
				{

					//Changes text on label to be blank
					textGrid[i][j].setText(" ");

				} //End else

			} //End inner for
		} //End outer for

	} //End fillGrid


	public void actionPerformed(ActionEvent event) 
	{

		//Checks what the event was
		if(event.getSource() == scrollUpButton)
		{

			//Scrolls up
			if(rowNumber == 1)
			{

				JOptionPane.showMessageDialog(textPanel, "This is the top of the list");

			} //End if
			else
			{

				rowNumber-=10;
				rows.clear();
				rows = FM.getRows(rowNumber);
				fillGrid();
				System.out.println("Scrolling to row:"+rowNumber);

			} //End else

		} //End if
		else if(event.getSource() == scrollDownButton)
		{

			//Scrolls down
			if(rowNumber+10 > FM.getCrashes().size())
			{

				JOptionPane.showMessageDialog(textPanel, "This is the bottom of the list");

			} //End if
			else
			{

				//Scrolls down
				rowNumber+=10;
				rows.clear();
				rows = FM.getRows(rowNumber);
				fillGrid();
				System.out.println("Scrolling to row:"+rowNumber);

			} //End else

		} //End else if
		else if(event.getSource() == insertCrashButton)
		{

			//Gets value for the new row
			String newRow = JOptionPane.showInputDialog(this, "Please enter your new row", FM.getCrashes().get(0));

			//This will add the new row to the hashmap
			FM.addRow(newRow);

			float finalRow = (FM.getCrashes().size())/10;
			System.out.println(finalRow);
			String tempString = String.valueOf(finalRow);
			tempString = tempString.substring(tempString.indexOf("."));
			System.out.println(tempString);

			//Checks if the size of crashes ends with a 0
			if(tempString.contains(".0"))
			{

				int finalInt = (int) finalRow*10;
				rowNumber = finalInt-9;

			} //End if
			else
			{

				//Gets last rows
				int finalInt = (int) finalRow* 10;
				finalInt++;
				rowNumber = finalInt;

			} //End else

			System.out.println(rowNumber+" "+FM.getCrashes().size());

			//Updates rows and moves to end of hashmap to display new row
			rows = FM.getRows(rowNumber);

			//Fills grid
			fillGrid();

		} //End else if
		else if(event.getSource() == updateCrashButton)
		{

		//Gets row that user wants to remove
		int num = Integer.parseInt(JOptionPane.showInputDialog(this, "Please enter the number of the row you want to update"));

		//Removes row
		if(num <1 || num > 10)
		{

			//Lets the user know the number is invalid
			JOptionPane.showMessageDialog(this, "Sorry you need to enter a value from 1 to 10");

		} //End if
		else
		{

			//Asks what the user wants to change
			String newRow = JOptionPane.showInputDialog(this, "Please change the values between the ,s", FM.getCrashes().get(num+rowNumber-1));


			//Prints the number of the row being updated and then the new row
			System.out.println("Updating row number:"+(num+rowNumber-1));
			System.out.println(newRow);

			//Updates row in hashmap
			FM.updateRow(num+rowNumber-1, newRow);

			//Updates rows LinkedList
			rows = FM.getRows(rowNumber);

			//Fills out grid
			fillGrid();

		} //End else

		} //End else if
		else if(event.getSource() == deleteCrashButton)
		{

		//Gets row that user wants to remove
		int num = Integer.parseInt(JOptionPane.showInputDialog(this, "Please enter the number of the row you want to delete"));

		//Removes row
		if(num <1 || num > 10)
		{

			//Lets the user know the number is invalid
			JOptionPane.showMessageDialog(this, "Sorry you need to enter a value from 1 to 10");

		} //End if
		else
		{

			//Updates num to be a valid number
			num = (num+rowNumber)-1;

			//Prints the row being removed
			System.out.println("Deleting row:"+num);

			//Updates hashmap to reflect changes
			FM.removeRow(num);

			//Updates rows with new values if there are any
			rows = FM.getRows(rowNumber);

			//Fills out grid
			fillGrid();

		} //End else

		} //End else if

	} //End actionPerformed

	@Override
	public 	void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

} //End Screen
