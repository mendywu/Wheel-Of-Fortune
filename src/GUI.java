//Mendy Wu, 110483971
//GUI Class: constructs specific GUIs used by WheelOfFortune Class

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.HashMap;

public class GUI {

	static Text board = new Text(WheelOfFortune.getBoard());
	static HBox letters = new HBox(5);
	static HashMap<Character, Text> displayLetters = new HashMap<Character, Text>();
	
	static Button btngLetter = new Button("Guess a Letter");
	static Button btngPhrase = new Button("Guess Phrase");
	
	static TextArea gameActions = new TextArea("Game Actions:\n");
	static Text hScore = new Text();
	static Text cScore = new Text();
	static Text tPlayer = new Text();

	
	public static Pane makeContents() {
		
		Text welcome = new Text("Wheel Of Fortune Game");
		makeLetters();

		HBox scoreBox = new HBox(10);
		scoreBox.setAlignment(Pos.CENTER);
		scoreBox.getChildren().addAll(hScore, cScore);

		HBox guessBtns = new HBox(10);
		guessBtns.setAlignment(Pos.CENTER);
		
		Button btnGuessL = new Button("Guess Letter");
		TextField tfGuessL = new TextField();
		HBox guessL = new HBox(tfGuessL, btnGuessL);
		guessL.setAlignment(Pos.CENTER);
		
		Button btnGuessP = new Button("Guess Phrase");
		TextField tfGuessP = new TextField();
		HBox guessP = new HBox(tfGuessP, btnGuessP);
		guessP.setAlignment(Pos.CENTER);
		
		HBox guessBox = new HBox(guessL, guessP);
		guessBtns.getChildren().addAll(btngLetter, btngPhrase);
		
		guessBox.setAlignment(Pos.CENTER);
		
		btnGuessL.setOnAction(e -> {
			if (tfGuessL.getText().isEmpty())
				return;
			char guess = tfGuessL.getText().toUpperCase().charAt(0);
			WheelOfFortune.humanGuessL(guess);
			guessL.setVisible(false);
			tfGuessL.clear();
			btngLetter.setVisible(false);
			btngPhrase.setVisible(false);
		});
		
		btnGuessP.setOnAction(e -> {
			String guess = tfGuessP.getText();
			WheelOfFortune.guessPhrase(guess);
			guessP.setVisible(false);
			tfGuessP.clear();
			btngLetter.setVisible(false);
			btngPhrase.setVisible(false);

		});
		
		btngLetter.setOnAction(e -> {
			guessL.setVisible(true);
			guessP.setVisible(false);
		});
		btngPhrase.setOnAction(e -> {
			guessP.setVisible(true);
			guessL.setVisible(false);
		});
		
		Button spinBtn = new Button("SPIN");
		spinBtn.setOnAction(e -> {
			WheelOfFortune.wedge = WheelOfFortune.wheel.spin();
			tPlayer.setText("");
			if (btngLetter.isVisible() || WheelOfFortune.Won())
				return;
			WheelOfFortune.playerTurn();
		});
		
		HBox greetings = new HBox(5);
		greetings.setAlignment(Pos.CENTER);
		Label label = new Label("Enter Your Name: ");
		TextField tfName = new TextField("John");
		Button btnName = new Button("Submit");
		greetings.getChildren().addAll(label, tfName, btnName);
		
		btnName.setOnAction(e -> {
			WheelOfFortune.human.setName(tfName.getText());
			tPlayer.setText(WheelOfFortune.human.getName() + "'s turn:  ");
			addGameAction("Welcome "+ WheelOfFortune.human.getName()+"!\n\n"+ tPlayer.getText());
			board.setVisible(true);
			letters.setVisible(true);
			spinBtn.setVisible(true);
			WheelOfFortune.updateScores();
			scoreBox.setVisible(true);
			greetings.setVisible(false);

		});
		
		VBox contents = new VBox(5);
		board.setFont(new Font("Veronica", 35));
		contents.getChildren().addAll(welcome, greetings, board, letters,tPlayer);
		contents.getChildren().addAll(spinBtn, guessBtns, guessBox, scoreBox);
		contents.setAlignment(Pos.CENTER);
		
		HBox game = new HBox(5);
		game.getChildren().addAll(gameActions, contents);
		game.setAlignment(Pos.CENTER);
		
		spinBtn.setVisible(false);
		guessL.setVisible(false);
		guessP.setVisible(false);
		btngLetter.setVisible(false);
		btngPhrase.setVisible(false);
		board.setVisible(false);
		gameActions.setEditable(false);
		
		return game;
	}
	

	public static void makeLetters() {
		letters = new HBox(5);
		letters.setAlignment(Pos.CENTER);
		letters.setVisible(false);
		for (char i = 'A'; i <= 'Z'; i++) {
			Text letter = new Text(String.valueOf(i));
			displayLetters.put(i, letter);
			letter.setFont(new Font("Book Antiqua", 37));
			letter.setFill(Color.MIDNIGHTBLUE);
			letters.getChildren().add(letter);
		}
	}
	
	public static void addGameAction(String string){
		gameActions.setText(gameActions.getText() + string + "\n");
		gameActions.positionCaret(gameActions.getLength());

	}

	public static void updateBoard() {
		board.setText(WheelOfFortune.getBoard());
	}
	
	

}
