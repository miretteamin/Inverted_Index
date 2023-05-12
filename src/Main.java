import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner; // Import the Scanner class to read text files
//import java.util.ArrayList;
/*
* Nader Atef 20190575
* Christina Montasser 20190382
* Mirette Amin 20190570
* */

public class Main {
	public static String removePunctuations(String source) {
		return source.replaceAll("\\p{Punct}", "");
	}
	static String [] filesContent = new String[10];
	static String [] fileNames = new String[10];
	static int [] filesLength = new int[10];
	static void read_files() {
	    try {
    	 File path = new File(System.getProperty("user.dir")+"\\Documents");
    	 File [] files = path.listFiles();
    	    for (int i = 0; i < files.length; i++){

    	        if (files[i].isFile()){ //this line weeds out other directories/folders
					fileNames[i] = files[i].getName();
    		      Scanner myReader = new Scanner(files[i]);
    		      while (myReader.hasNextLine()) {
    		    	 filesContent[i] = removePunctuations(myReader.nextLine()).toLowerCase(Locale.ROOT);
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
		int  k = 4;

		read_files();
		HashMap<String, DictEntry> index = new HashMap<>();
		for (int fIndex= 0; fIndex< filesContent.length; fIndex++) {
			String[] words = filesContent[fIndex].split(" ");
			filesLength[fIndex] = words.length;
			for (String word : words) {
				if (!index.containsKey(word)) {
					index.put(word, new DictEntry());
					index.get(word).setDoc_freq(1);
					index.get(word).setTerm_freq(1);
					index.get(word).setpList(new Posting(1, fIndex));
				}else{
					index.get(word).setTerm_freq(index.get(word).getTerm_freq()+1);
					Posting current = index.get(word).getpList();
					while (true){
						if (current.getDocId() != fIndex){
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

		System.out.println("Enter a query to search for and press enter: ");

		// String input
		String phrase = myObj.nextLine();
		String[] terms = phrase.split("\\W+");
		int N = fileNames.length;
		int len = terms.length;
		double scores[] = new double[N]; // N= collection size (10 files N =10)

		for (String term : terms){
			term = removePunctuations(term).toLowerCase();
			if (index.containsKey(term)) {

				int tdf = index.get(term).doc_freq; // number of documents that contains the term
				int ttf = index.get(term).term_freq; //
				double idf = Math.log10(N / (double) tdf);
				Posting current = index.get(term).getpList();
				while (current != null){
					scores[current.docId] +=  (1 +  Math.log10((double)current.dtf) * idf);
					current = current.next;
				}
			}
		}
		for (int i=0;i < N; i++){
			scores[i] = scores[i] / filesLength[i];
		}
		int docs[] = new int[k];

		for (int i=0; i <k; i++){
			int max_doc = -1;
			double max_score = -2;
			for (int n=0; n < N; n++){
				if (scores[n] > max_score){
					max_score = scores[n];
					max_doc = n;
				}
			}
			docs[i] = max_doc;
			scores[max_doc] = -20;
		}
		for (int doc: docs){
			System.out.println(fileNames[doc]);
		}

//		if (index.containsKey(phrase)){
//			Posting current = index.get(phrase).getpList();
//			while (current != null){
//				System.out.println(fileNames[current.getDocId()]+ " "+ current.getDtf());
//				current = current.next;
//			}
//		}else{
//			System.out.println(phrase+ " doesn't exist in index");
//		}
	}
}
