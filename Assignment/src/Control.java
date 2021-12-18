/********************
	Description
This is the Control class which will handle the other classes in the application
	Author
Paul Geoghegan
	Date
Started 20/11/21

********************/


public class Control
{
	public static void main(String[] args)
	{

		//Creates new FileManager
		FileManager FM = new FileManager("C:\\Users\\paul2\\OneDrive - Technological University Dublin\\Current Work\\MS Dev\\Assignment\\Assignment\\src\\data.csv");

		//Reads CSV
		FM.readFile();

		//Creates UI and passes FileManager to it
		Screen ui = new Screen("Crash Report", FM);

	} //End main
} //End Control