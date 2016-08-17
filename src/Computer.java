//Mendy Wu, 110483971
//Computer Class: constructs new Computer and possible letter guesses

import java.util.Random;

public class Computer extends Player {
	
	private String guesses = "";
	private int guessNum = 0;
	
	public Computer() {
		super.setName("Computer");
		constructGuesses();
	}

	public Computer(String name) {
		super(name);
		constructGuesses();
	}
	
	public void constructGuesses(){
		String vowels = "AEIOU";
		String mostLikely = "STRHLCBDMN";
		String likely = "KPQWFJG";
		String unlikely = "ZQVYX";
		guesses += (shuffle(vowels));
		guesses += shuffle(mostLikely);
		guesses += shuffle(likely);
		guesses += shuffle(unlikely);
	}
	
	public String getGuesses(){
		return guesses;
	}
	
	public String shuffle(String string ){
		Random random = new Random();
	    char a[] = string.toCharArray();

	    for( int i=0 ; i<a.length-1 ; i++ ){
	        int j = random.nextInt(a.length-1);
	        char temp = a[i]; 
	        a[i] = a[j];  
	        a[j] = temp;
	    }       
	    return new String( a );
	}
	
	public void incrementGuessNum(){
		guessNum++;
	}
	
	public int getGuessNum(){
		return guessNum;
	}

}
