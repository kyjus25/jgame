package jgame;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import jgame.generics.*;

public class JGScene {
	public Field<StackPane> stackPane = new Field<>(new StackPane());
	Field<Scene> scene = new Field<>(new Scene(this.stackPane.get(), JGame.width.get(), JGame.height.get()));
//	protected static JGKeyboardManager keyboardManager;
	public JGScene() {
		System.out.println(this.getClass().getSimpleName().toLowerCase());
//		JGame.sceneManager.sceneList.add(this);
	}
}
