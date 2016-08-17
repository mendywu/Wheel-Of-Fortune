//Mendy Wu, 110483971
//Player Class: creates new Player

public class Player {
	
	private String name = "Player"; 
	private int score = 0; 
	
	public Player(){
	}
	
	public Player(String name){
		this.name = name; 
	}
	
	public void lose (int amount){
		if (score - Math.abs(amount) < 0)
			score = 0; 
		else
			score -= Math.abs(amount); 
	}
	
	public void gain (int amount, int numOfLetters){
		score += (amount*numOfLetters); 
	}
	
	public void gain (int amount){
		score += (amount); 
	}
	
	public void millionGain (int numOfLetters){
		score += (1000000*numOfLetters);
	}
	
	public void bankrupt(){
		score = 0; 
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name; 
	}
	
	public int getScore(){
		return score; 
	}
	
}
