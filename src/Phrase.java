//Mendy Wu, 110483971
//Phrase Class: Creates list of phrases from given file

import java.util.*;
import java.io.*; 

public class Phrase {
	
	private ArrayList<String> phrases = new ArrayList<>();
	
	public Phrase (File file){
		readPhrases(file);
	}
	
	public void readPhrases(File file){
		try {
			Scanner input = new Scanner (file);
			while(input.hasNext()){
				phrases.add(input.nextLine());
			}
			input.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File not found");
		}
	}
	
	public String getRandomPhrase(){
		int randomIndex = new Random().nextInt(phrases.size());
		return phrases.get(randomIndex);
	}
	
}
