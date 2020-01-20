package jgame.generics;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import jgame.*;

public class CommonControls {
	//	On Initialize Function to call
	public CommonControls() {
		JGame.tick.addEventHandler((event) -> this.onGameLoop(event));
		JGKeyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (KeyCode k) -> this.onKeyPress(k));
		JGKeyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (KeyCode k) -> this.onKeyReleased(k));
		JGKeyboardManager.addEventHandler((KeyCode k, boolean b) -> this.onKeyEvent(k, b));
//		JGKeyboardManager.addEventHandler((KeyCodeCombination k, boolean b) -> this.onKeyEvent(k, b));
//		JGKeyboardManager.addEventHandler((KeyCodeCombination keyCode) -> this.onKeyCombination(combination));
	}
	
	public void initialize() {}
	
	//	JGKeyboardManager Callback
	public void onKeyPress(KeyCode key) {}
	public void onKeyReleased(KeyCode key) {}
	public void onKeyEvent(KeyCode key, boolean isPressed) {}
//	public void onKeyEvent(KeyCodeCombination key, boolean isPressed) {}
//	public void onKeyCombination(KeyCodeCombination combination) {}
	
	//	JGame Callback
	public void onGameLoop(ActionEvent event) {}
	
	//	JGMoustManager Callback
	//	ToDo: Create Mouse Manager
	
	public void onCollision(JGSprite a, JGSprite b) {}

	public void onCollision(JGSprite b) {}
}