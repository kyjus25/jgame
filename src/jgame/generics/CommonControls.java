package jgame.generics;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import jgame.*;

public class CommonControls {
	//	On Initialize Function to call
	public CommonControls() {
		JGame.tick.addEventHandler((event) -> {
			if (JGame.networkManager.hosting.get()) {
				this.onGameLoop(event);
			}
		});
		JGKeyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (KeyCode k) -> this.onKeyPress(k));
		JGKeyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (KeyCode k) -> this.onKeyReleased(k));
		JGKeyboardManager.addEventHandler((KeyCode k, boolean b) -> this.onKeyEvent(k, b));
		JGPhysicsManager.addEventHandler((JGPhysics p, JGSprite s) -> onPhysics(p, s));
		JGSceneManager.activeScene.addEventHandler((JGScene scene) -> onScene(scene));
		JGMouseManager.addEventHandler((MouseEvent me, boolean b) -> onMouseEvent(me, b));
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
	public void onMouseEvent(MouseEvent mouseEvent, boolean isPressed) {}
	//	ToDo: Create Mouse Manager
	
	public void onCollision(JGSprite spriteA, JGSprite spriteB) {}
	public void onCollision(JGSprite sprite) {}
	public void onPhysics(JGPhysics physics, JGSprite sprite) {}
	public void onPhysics(JGPhysics physics) {}
	public void onScene(JGScene scene) {}
}