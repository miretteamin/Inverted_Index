import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.HashMap;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;


public class Main {
	
	static String [] filesContent = new String[10];
	static String [] fileNames = new String[10];
	static void read_files() {
	    try {
    	 File path = new File(System.getProperty("user.dir")+"\\Documents");
    	 File [] files = path.listFiles();
    	    for (int i = 0; i < files.length; i++){

    	        if (files[i].isFile()){ //this line weeds out other directories/folders
					fileNames[i] = files[i].getName();
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
		HashMap<String, DictEntry> index = new HashMap<>();
		for (int fIndex= 0; fIndex< filesContent.length; fIndex++) {
			String[] words = filesContent[fIndex].split(" ");
			for (String word : words) {
				if (!index.containsKey(word)) {
					index.put(word, new DictEntry());
					index.get(word).setDoc_freq(1);
					index.get(word).setTerm_freq(1);
					index.get(word).setpList(new Posting(1, fIndex));
				}else{
					index.get(word).setTerm_freq(index.get(word).getTerm_freq()+1);
					Posting previous = null;
					Posting current = index.get(word).getpList();
					while (true){
						if (current.getDocId() != fIndex){
							previous = current;
							if (current.next == null){
								current.setNext(new Posting(1, fIndex));
								break;
							}
							current = current.next;

						}else {
							current.setDtf(current.getDtf()+1);
							break;
						}
					}
				}
			}
		}
		Scanner myObj = new Scanner(System.in);

		System.out.println("Enter a word to search for and press enter: ");

		// String input
		String word = myObj.nextLine();

		if (index.containsKey(word)){
			Posting current = index.get(word).getpList();
			while (current != null){
				System.out.println(fileNames[current.getDocId()]+ " "+ current.getDtf());
				current = current.next;
			}
		}else{
			System.out.println(word+ " doesn't exist in index");
		}

    }
}
