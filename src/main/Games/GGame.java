package main.Games;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jgame.JGame;

public class GGame extends JGame {
	public GGame(Stage stage) {
		super(60, "Another", stage, 800, 600);
		
//		keyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
//			System.out.println("1" + KeyEvent.KEY_PRESSED + " " + event);
//		});
//		keyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
//			System.out.println("2" + KeyEvent.KEY_RELEASED + " " + event);
//		});
//		
//		keyboardManager.addEventHandler(KeyCode.C, (event) -> {
//			System.out.println("3" + KeyCode.C + " " + event);
//		});
//		
//		keyboardManager.addEventHandler(new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN), (event) -> {
//			System.out.println("4" + "SHIFT C " + event);
//		});
	}
	
	public void handleKeyPress(KeyCode key) {
		System.out.println("5" + "handleKeyPress" + key);
	}
	
	public void handleKeyReleased(KeyCode key) {
		System.out.println("6" + "handleKeyRelease" + key);
	}
	
	public void handleKey(KeyCode key, boolean isPressed) {
		System.out.println("7" + key + isPressed);
	}
	
//	private void ggameHandleKeyPress(KeyCode key) {
//		System.out.println("2. GGame addListener handled" + key);
//	}
//	
//	private void ggameHandleKeyPress2(KeyCode key) {
//		System.out.println("3. GGame2 addListener handled" + key);
//	}

	@Override
	public void initialize(Stage stage) {
		// TODO Auto-generated method stub
		System.out.println("GGAME initialize");
	}
}
