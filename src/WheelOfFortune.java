//Mendy Wu, 110483971
/*WheelOfFortune Class: RUN THIS CODE TO START GAME. 
 *Needs GUI, Phrase, Wheel, Player, and Computer in the same package to start game.   
*/

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WheelOfFortune extends Application {
	
	//Edit files if they have a different name. 
	private static String phrase = new Phrase(new File("test examples.txt")).getRandomPhrase().toUpperCase();
	static Wheel wheel = new Wheel(new File("wheel values.txt"));

	private static int turn = 0;
	static Player human = new Player();
	static Computer computer = new Computer();

	static String guessedLetters = " ";
	static String wedge = "";

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println(phrase);
		
		Scene scene = new Scene(GUI.makeContents(), 1100, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Wheel Of Fortune - Mendy Wu");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void playerTurn() {
		GUI.tPlayer.setText(human.getName() + " spun a " + wedge + " wedge!");
		GUI.addGameAction(GUI.tPlayer.getText());
		if (wedge.equals("Bankruptcy")) {
			playerBankrupt();
			wedge = wheel.spin();
			playComputerTurn();
		} else if (isNumeric(wedge) || wedge.equals("1Million")) {
			if (wedge.equals("1Million")) {
				GUI.btngLetter.setVisible(true);
				GUI.btngPhrase.setVisible(true);
			} else if (Integer.parseInt(wedge) > 0) {
				GUI.btngLetter.setVisible(true);
				GUI.btngPhrase.setVisible(true);
			} else if (Integer.parseInt(wedge) < 0) {
				playerLose(Integer.parseInt(wedge));
				wedge = wheel.spin();
				playComputerTurn();
			}
		}
	}
	
	public static void playComputerTurn() {
		int i = (int)((Math.random()*3)+1);
		if (wedge.equals("Bankruptcy")){
			GUI.addGameAction(computer.getName() + " spun a " + wedge + " wedge!");
			playerBankrupt();
		}else if (isNumeric(wedge) || wedge.equals("1Million")) {
			char guess = computer.getGuesses().charAt(computer.getGuessNum());
			if (wedge.equals("1Million"))
				compGuessL(guess);
			else if (Integer.parseInt(wedge) > 0)
				if (numOfOccurances(getBoard(),'_') <= i)
					guessPhrase(phrase);
				else
					compGuessL(guess);
			else if (Integer.parseInt(wedge) < 0){
				GUI.addGameAction(computer.getName() + " spun a " + wedge + " wedge!");
				playerLose(Integer.parseInt(wedge));
			}
		}
	}
	
	public static void humanGuessL(char guess) {
		GUI.addGameAction(getWhoseTurn() + " guessed \"" + guess + "\".");
		if ((guess < 'A' || guess > 'Z')) {
			GUI.addGameAction("\"" + guess + "\" is an Invalid Character\n");
			turn++;
			playComputerTurn();
		} else {
			Text t = GUI.displayLetters.get(guess);
			if (t.isStrikethrough()) {
				GUI.addGameAction("\"" + guess + "\" was already guessed\n");
				turn++;
				playComputerTurn();
			} else {
				t.setFill(Color.RED);
				t.setStrikethrough(true);
				if (correct(guess))
					displayCorrect(guess);
				else {
					GUI.addGameAction("No Matches with letter \"" + guess + "\"\n");
					turn++;
					wedge = wheel.spin();
					GUI.tPlayer.setText(getWhoseTurn() + "'s Turn:");
					GUI.addGameAction(GUI.tPlayer.getText());
					playComputerTurn();
				}
			}
		}
	}

	public static void compGuessL(char guess) {
		Text t = GUI.displayLetters.get(guess);
		if (t.isStrikethrough()) {
			computer.incrementGuessNum();
			playComputerTurn();
		} else {
			GUI.addGameAction(computer.getName() + " spun a " + wedge + " wedge!");
			GUI.addGameAction(getWhoseTurn() + " guessed \"" + guess + "\".");
			t.setFill(Color.RED);
			t.setStrikethrough(true);
			if (correct(guess)) {
				displayCorrect(guess);
				if (!Won()) {
					computer.incrementGuessNum();
					wedge = wheel.spin();
					playComputerTurn();
				}
			} else {
				GUI.addGameAction("No Matches with letter \"" + guess + "\"\n");
				wedge = wheel.spin();
				turn++;
				computer.incrementGuessNum();
				GUI.tPlayer.setText(getWhoseTurn() + "'s Turn:");
				GUI.addGameAction(GUI.tPlayer.getText());
			}
		}
	}

	public static void guessPhrase(String guess) {
		if (!isHumanTurn())
			GUI.addGameAction(getWhoseTurn() + " spun a " + wedge + " wedge!");
		GUI.addGameAction(getWhoseTurn() + " guessed the phrase!\n\"" + guess.toUpperCase() + "\"" );
		if (guess.equalsIgnoreCase(phrase)) {
			revealWord();
			GUI.tPlayer.setText(getWhoseTurn() + " wins!");
			if (!wedge.equals("1Million"))
				playerGain(Integer.parseInt(wedge));
			else
				if (isHumanTurn())human.millionGain(1);
				else computer.millionGain(1);
			GUI.addGameAction(GUI.tPlayer.getText());
		} else {
			GUI.addGameAction("Incorrect Phrase.\n" + getWhoseTurn() + "'s Turn.");
			turn++;
			playComputerTurn();
		}
		updateScores();
	}
	
	public static void playerGain(int wedgeValue, char guess) {
		if (isHumanTurn())
			human.gain(wedgeValue, numOfOccurances(phrase,guess));
		else
			computer.gain(wedgeValue, numOfOccurances(phrase,guess));
		GUI.addGameAction(getWhoseTurn() + " gains " + wedgeValue + "* " + numOfOccurances(phrase,guess) + " matches = "
				+ (wedgeValue * numOfOccurances(phrase,guess)) + ".\n");
	}

	public static void playerGain(int wedgeValue) {
		if (isHumanTurn())
			human.gain(wedgeValue);
		else
			computer.gain(wedgeValue);
		GUI.addGameAction(getWhoseTurn() + " gains " + wedgeValue + ".\n");
	}

	public static void playerLose(int wedgeValue) {
		GUI.addGameAction("Sorry! " + getWhoseTurn() + " loses " + Math.abs(wedgeValue) + ".\n");
		if (isHumanTurn())
			human.lose(wedgeValue);
		else
			computer.lose(wedgeValue);
		updateScores();
		turn++;
		GUI.tPlayer.setText(getWhoseTurn() + "'s Turn:");
		GUI.addGameAction(GUI.tPlayer.getText());
	}

	public static void playerBankrupt() {
		GUI.addGameAction(getWhoseTurn() + "'s totals drop to 0.\n");
		if (isHumanTurn())
			human.bankrupt();
		else
			computer.bankrupt();
		updateScores();
		turn++;
		GUI.tPlayer.setText(getWhoseTurn() + "'s Turn:");
		GUI.addGameAction(GUI.tPlayer.getText());
	}

	public static void playerMillion(char guess) {
		if (isHumanTurn())
			human.millionGain(numOfOccurances(phrase, guess));
		else
			computer.millionGain(numOfOccurances(phrase,guess));
		GUI.addGameAction(getWhoseTurn() + " gains 1Million * " + numOfOccurances(phrase,guess) + " matches = "
				+ (1000000 * numOfOccurances(phrase,guess)) + ".\n");
	}

	public static boolean correct(char letter) {
		for (char a : phrase.toCharArray())
			if (letter == a)
				return true;
		return false;
	}
	
	public static void displayCorrect(char guess) {
		guessedLetters += guess;
		GUI.updateBoard();
		if (wedge.equals("1Million"))
			playerMillion(guess);
		else
			playerGain(Integer.parseInt(wedge), guess);
		updateScores();
		if (Won())
			GUI.tPlayer.setText(getWhoseTurn() + " wins!");
		else
			GUI.tPlayer.setText(getWhoseTurn() + " spins again.");
		GUI.addGameAction(GUI.tPlayer.getText());
	}
	
	public static boolean isHumanTurn() {
		if (turn % 2 == 0)
			return true;
		else
			return false;
	}

	public static String getWhoseTurn() {
		if (isHumanTurn())
			return human.getName();
		else
			return computer.getName();
	}

	
	public static int numOfOccurances(String s, char ch){
		return numOfOccurances (s, ch, s.length()-1);
	}
	
	public static int numOfOccurances(String s, char ch, int high){
		if (high == -1)
			return 0; 
		if (s.charAt(high) == ch)
			return 1 + numOfOccurances(s,ch,high-1);
		return numOfOccurances (s,ch,high-1);
		
	}

	public static boolean isNumeric(String value) {
		try {
			Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
	
	public static String getBoard() {
		String board = "";
		for (char letter : phrase.toCharArray())
			if (guessedLetters.indexOf(letter) == -1)
				board += ("_ ");
			else
				board += (letter + " ");
		return board;
	}
	
	public static void updateScores() {
		GUI.hScore.setText(human.getName() + ": " + human.getScore());
		GUI.cScore.setText(computer.getName() + ": " + computer.getScore());
	}
	
	public static boolean Won() {
		for (char letter : phrase.toCharArray())
			if (guessedLetters.indexOf(letter) == -1)
				return false;
		return true;
	}

	public static void revealWord() {
		guessedLetters += phrase;
		GUI.updateBoard();
	}

}
