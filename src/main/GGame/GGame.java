package main.GGame;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import jgame.JGPhysics;
import jgame.JGScene;
import jgame.JGame;

public class GGame extends JGame {
	public GGame(Stage stage) {
		super(60, "Another", stage, 800, 600);

//		keyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
//			System.out.println("here");
//			if (event == KeyCode.NUMPAD1) {
//				sceneManager.changeScenes(level1);
//			}
//			if (event == KeyCode.NUMPAD2) {
//				sceneManager.changeScenes(level2);
//			}
////			System.out.println("1" + KeyEvent.KEY_PRESSED + " " + event);
//		});
	}
	
//	public void handleKeyPress(KeyCode key) {
////		System.out.println("5" + "handleKeyPress" + key);
//	}
//	
//	public void handleKeyReleased(KeyCode key) {
////		System.out.println("6" + "handleKeyRelease" + key);
//	}
//	
//	public void handleKey(KeyCode key, boolean isPressed) {
////		System.out.println("7" + key + isPressed);
//	}
//	
//	public void handleTick(ActionEvent event) {
////		System.out.println("tick2");
//	}
	
//	private void ggameHandleKeyPress(KeyCode key) {
//		System.out.println("2. GGame addListener handled" + key);
//	}
//	
//	private void ggameHandleKeyPress2(KeyCode key) {
//		System.out.println("3. GGame2 addListener handled" + key);
//	}

	public void initialize() {
		// TODO Auto-generated method stub
		System.out.println("GGAME initialize");
		Circle circle = new Circle(150.0f, 150.0f, 80.f); 
		
		
//		JGPhysics gravity = new JGPhysics("gravity", false);
		
		GLevel level1 = new GLevel();
		JGScene level2 = new JGScene();

		level2.stackPane.get().getChildren().add(circle);
		
//		keyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
//			System.out.println("2" + KeyEvent.KEY_RELEASED + " " + event);
//		});
//		
//		keyboardManager.addEventHandler(KeyCode.W, (event) -> {
//			System.out.println("3" + KeyCode.W + " " + event);
//		});
//		
//		keyboardManager.addEventHandler(new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN), (event) -> {
//			System.out.println("4" + "SHIFT C " + event);
//		});
//		tick.addEventHandler((event) -> {
//			System.out.println("tick");
//		});
	}

//	public void onKeyPress(KeyCode key) {
//		System.out.println("GGame onKeyPress" + key);
//	}
//	public void onKeyReleased(KeyCode key) {
//		System.out.println("GGame onKeyReleased" + key);
//	}
//	public void onKeyEvent(KeyCode key, boolean isPressed) {
//		System.out.println("GGame onKeyEvent" + key + " " + isPressed);
//	}
	public void onGameLoop(ActionEvent event) {
//		System.out.println("GGame onTick" + event);
	}
}
