import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.util.StringTokenizer;


public class MultiThreadManyFiles{
	
	public static void main(String[] args) throws Exception{
		//Create an array housing all of the file names
		String fileName;
		File directoryPath = new File("C:/Users/Nico/Desktop/CSCI_4401/Assignment_3/textfiles");
		String[] fileNames = directoryPath.list();

		//Start timer
		Date start = new Date();
		//Loop through files, perform the "freqWord" method on each file 

		ThreadsCreator.freqWord(fileNames);		

		//End timer
		Date end = new Date();
		System.out.println("\nRuntime: " + ((end.getTime() - start.getTime())/1000) + " seconds");	
			
	}
}

class FindWords implements Runnable{
	String fileName;
	File directoryPath = new File("C:/Users/Nico/Desktop/CSCI_4401/Assignment_3/textfiles");

	public FindWords(String fileName){
		this.fileName = fileName;
	}

		//Implement run method to perform the search and create a HashMap linking each word to the 
		//number of times it's used, per file.
		public void run(){
		try{
			Path path = Paths.get(directoryPath.getAbsolutePath()).resolve(fileName);

			BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));
			Map<String, Integer> map = new HashMap<>();

			String line = reader.readLine();

			String pattern = "[a-zA-Z]+";
		    Pattern r = Pattern.compile(pattern);
		    Matcher m = r.matcher(line);

				while(line != null){
				        while(m.find()){
				        	String word = m.group();
				        	if(word == null || word.trim().equals("")){
				        		continue;
				        	}
				  			String lower = word.toLowerCase();
				        	if(map.containsKey(lower)){
				        		map.put(lower, map.get(lower) + 1);
				        	}
				        	else{
				        		map.put(lower, 1);
				        	}

				        }
				        line = reader.readLine();
				        if(line == null){
				        	break;
				        }
				        m = r.matcher(line);
					}
					int counter = 0;
					String finalWord = null;

					for(String word : map.keySet()){
						Integer maxCount = map.get(word);
						if(maxCount > counter && word.length() >= 5){
							counter = maxCount;
							finalWord = word;
						}
					}

					System.out.printf("%s: %s\n",fileName,finalWord);
					reader.close();
				}
				catch(FileNotFoundException ex){
					System.out.println("FileNotFoundException");
					}
				catch(IOException ex){
					System.out.println("IOException");
				}
	}
}


class ThreadsCreator {	
	public static void freqWord(String[] fileNames){
		
		//pass list files, for loop line 109-118. then call wait for threads (119-126)
			List<Thread> threads = new ArrayList<Thread>();	
			// Create a new task
			for(int i=0; i<fileNames.length; i++){
				FindWords task = new FindWords(fileNames[i]);
				// Create a new thread
				Thread thread = new Thread(task);
				//Initialize the thread
				thread.start();	
				threads.add(thread);	
			}		
				for (Thread t : threads) {
					try {
						t.join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				threads.clear();	
		
		}
	}
