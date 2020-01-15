package jgame;

import javafx.scene.Group;
import javafx.scene.Scene;
import jgame.generics.*;

public class JGSceneManager {
	Field<Scene> activeScene = new Field<>();
//	FieldList<Scene> scenes = new FieldList<>();

	public JGSceneManager() {
		this.activeScene.addListener((Scene b) -> {this.changeScenes(b);});
		this.activeScene.set(new Scene(new Group(), JGame.width.get(), JGame.height.get()));
		FieldListener<Scene> bla = (Scene b) -> {};
//		this.scenes.addAll(new Scene(new Group(), 1, 1), new Scene(new Group(), 1, 1));
		
	}
	
	public void changeScenes(Scene b) {
		System.out.println("JGSceneManager got an active scene");
		JGame.stage.get().setScene(b);
	}
	
//	class ActiveScene implements FieldListener<Scene> {
//		@Override
//		public void changed(Scene obj) {
//			System.out.println("changed");
//		}
//	}

}

