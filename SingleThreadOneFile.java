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

public class SingleThreadOneFile{
	
	public static void main(String[] args) throws Exception{
		//Create a string with the name of the file to be analyzed and path to directory
		String fileName = "all_anonymized_2015_11_2017_03.csv";
		File directoryPath = new File("C:/Users/Nico/Desktop/CSCI_4401/Assignment_3/textfiles");
		
		//Perform the search and create a HashMap linking each word to the 
		//number of times it's used, per file.
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

