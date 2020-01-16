package jgame;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import jgame.generics.*;

public abstract class JGame {
	static Field<Integer> width = new FieldEvent<>();
	static Field<Integer> height = new FieldEvent<>();
	static Field<Stage> stage = new FieldEvent<>();

	protected static Field<Integer> fps = new Field<>();
	protected static Field<String> title = new Field<>("JGame");
	protected static FieldEvent<Boolean> debug = new FieldEvent<>(true);
	protected static Field<Timeline> gameLoop = new Field<>();
	protected static FieldEvent<ActionEvent> tick = new FieldEvent<>();
	
	protected static JGSceneManager sceneManager;
	protected static JGKeyboardManager keyboardManager;
	protected static JGSpriteManager spriteManager;
	
	public JGame(final int fps, final String title, Stage stage, int width, int height) {
		JGame.width.set(width);
		JGame.height.set(height);
		this.fps.set(fps);
		this.title.set(title);
		JGame.stage.set(stage);
		JGame.stage.get().setTitle(this.title.get());
		sceneManager = new JGSceneManager();
		spriteManager = new JGSpriteManager();
		keyboardManager = new JGKeyboardManager();
		keyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (KeyCode k) -> this.handleKeyPress(k));
		keyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (KeyCode k) -> this.handleKeyReleased(k));
		tick.addEventHandler((event) -> this.handleTick(event));
		JGame.stage.get().show();
		run();
	}
	
    private void run() {
    	final Duration oneFrameAmt = Duration.millis(1000/fps.get());
    	final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
			new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				tick.set(event);
//				if (debug.get()) {
//					debugLabel.setText(
//							String.format("Averate FPS: %.3f \n", tracker.getAverageFPS()) +
//							String.format("Instant FPS rate: %.3f \n", tracker.getInstantFPS()) +
//							"Rendered Objects: " + spriteManager.getActiveSprites().size()
//						);
//				}
//				updateSprites();
				// check for collision
//				checkCollisions();
				// removed dead things
//				cleanupSprites();
			}
      }); // oneFrame

      Timeline timeline = new Timeline();
      timeline.setCycleCount(Timeline.INDEFINITE);
      timeline.getKeyFrames().add(oneFrame);
      gameLoop.set(timeline);
      gameLoop.get().play();
//      gg.set();
    }
	
	public abstract void initialize(final Stage stage);
	public void handleKeyPress(KeyCode key) {}
	public void handleKeyReleased(KeyCode key) {}
	public void handleTick(ActionEvent event) {}
}
