package jgame;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jgame.generics.*;

public abstract class JGame {
	static Field<Integer> width = new Field<>();
	static Field<Integer> height = new Field<>();
	static Field<Stage> stage = new Field<>();
	Field<Integer> fps = new Field<>();
	Field<String> title = new Field<>();
	static JGSceneManager sceneManager;
	protected static JGKeyboardManager keyboardManager;
	
	public JGame(final int fps, final String title, Stage stage, int width, int height) {
		this.width.set(width);
		this.height.set(height);
		this.fps.set(fps);
		this.title.set(title);
		this.stage.set(stage);
		this.stage.get().setTitle(this.title.get());
		sceneManager = new JGSceneManager();
		keyboardManager = new JGKeyboardManager();
		keyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (KeyCode k) -> this.handleKeyPress(k));
		keyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (KeyCode k) -> this.handleKeyReleased(k));
		this.stage.get().show();
	}
	public abstract void initialize(final Stage stage);

	public void handleKeyPress(KeyCode key) {}
	
	public void handleKeyReleased(KeyCode key) {}

	public void handleKey(KeyCode key, boolean isPressed) {}
}
