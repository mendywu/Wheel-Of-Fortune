//Mendy Wu, 110483971
//Wheel Class: constructs new wheel and wedge values from given file 

import java.io.*;
import java.util.*;

public class Wheel {
	
	private static ArrayList<String> values = new ArrayList<>(); 
	
	public Wheel(File file){
		readValues(file); 
	}
	
	public String spin(){
		int index = new Random().nextInt(values.size()); 
		return values.get(index);
	}
	
	public void readValues (File file){
		try {
			Scanner input = new Scanner (file);
			while (input.hasNext()){
				values.add(input.next()); 
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not Found");
		}
	}

}