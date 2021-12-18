/********************
	Author:

	Description:
This class will be used to manage the CSV that will be displayed for the user
The readFile method will read the CSV line by line until the entire thing has been stored in a hashmap using the line numbers as indexes
	Date:
Started: 13/12/2021
Last Edited: 13/12/2021
********************/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;

public class FileManager
{

	private String filePath;
	private File data;
	private HashMap<Integer,String> crashes = new HashMap<Integer,String>();

	//Constructor
	public FileManager(String filePath) {
		this.filePath = filePath;
		data= new File(filePath);
	} //End constructor


	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	//
	public File getData() {
		return this.data;
	}
	public void setData(File data) {
		this.data = data;
	}

	//Gets and sets hashmap
	public HashMap<Integer,String> getCrashes() {
		return this.crashes;
	}
	public void setCrashes(HashMap<Integer,String> crashes) {
		this.crashes = crashes;
	}


	//This method will read a file
	public void readFile() 
	{

		String line;
		int i = 0;

		//Tries to read a file line
		try 
		{
			Scanner scan = new Scanner(data);

			//Lets user know that file is being read
			System.out.println("Reading File");

			//This will loop until it reaches the end of the file
			while (scan.hasNextLine()) 
			{

				//Writes a line from the file to the line variable
				line = scan.nextLine();

				//Adds line to hashmap
				crashes.put(i,line);

				//Adds 1 to i
				i++;

			} //End while loop

			//Lets user know the file is finished being read
			System.out.println("Finished Reading File");

			//Closes scanner
			scan.close();

		} //End try
		catch (FileNotFoundException e) 
		{
			System.out.println("could not find the file specified");
			e.printStackTrace();
		} //End catch
	} //End readFile

	//This method will write to a file
	public void writeFile(String str)
	{

		try {
			FileWriter writing = new FileWriter(filePath, true);
			writing.write(str);
			writing.close();
		} //End try
			catch (IOException e1) {
			// Lets the user know that something went wrong
			System.out.println("Sorry something went wrong");
			e1.printStackTrace();
		} //End catch

	} //End writeFile

	//This will get 10 rows for the screen along with the column names
	public LinkedList<String[]> getRows(int index)
	{

		//Creates new linked list
		LinkedList<String[]> rows = new LinkedList<String[]>();
		//Creates limit
		int limit = index+10;

		//Adds column names to linked list
		rows.add(crashes.get(0).split(","));
		System.out.println(crashes.get(0).split(",")[0]);

		//Loops through hashmap
		while(index < limit && index < crashes.size())
		{

			//Splits crashes and adds to rows
			rows.add(crashes.get(index).split(","));

			System.out.println(index+" "+rows.get(index-limit+11)[0]);

			//Increases index by 1
			index++;

		} //End while

		//Returns rows
		return rows;

	} //End getRows


	//This method will remove the row entered by the user
	public void removeRow(int num)
	{

		//Loops through hashmap and removes key while moving the other keys
		for(int i = num;i < (crashes.size())-1;i++)
		{

			//Removes key and value
			crashes.remove(i);
			crashes.put(i, crashes.get(i+1));

		} //End for

		//Removes last entry in hashmap
		crashes.remove(crashes.size()-1);

	} //End removeRow

	public void updateRow(int num, String newRow)
	{

		//Updates hashmap
		crashes.put(num, newRow);

	} //End updateRow


	//Adds row to hashmap
	public void addRow(String newRow)
	{

		//Adds new Row
		crashes.put(crashes.size(), newRow);

	} //End addRow

} //End FileManager
