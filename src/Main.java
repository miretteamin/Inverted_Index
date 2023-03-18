import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList; 

public class Main {
	
	static String [] filesContent = new String[10]; 
	static void read_files() {
	    try {
    	 File path = new File(System.getProperty("user.dir")+"\\Documents");
    	 File [] files = path.listFiles();
    	    for (int i = 0; i < files.length; i++){
    	        if (files[i].isFile()){ //this line weeds out other directories/folders
    		      Scanner myReader = new Scanner(files[i]);
    		      //System.out.println(i);
    		      //String data;
    		      while (myReader.hasNextLine()) {
    		    	 filesContent[i] = myReader.nextLine();
    		      //System.out.println(data);
    		      }
    		      myReader.close();
    	        }
    	    }
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	   //System.out.println(filesContent[0]);
	  }
    public static void main(String[] args) {
    	read_files();
    }
}
