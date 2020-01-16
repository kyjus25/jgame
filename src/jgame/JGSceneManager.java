package jgame;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import jgame.generics.*;

public class JGSceneManager {
	public FieldEvent<JGScene> activeScene = new FieldEvent<>();
	FieldList<JGScene> sceneList = new FieldList<>();
	
//	 private final static List<JGScene> GAME_GROUPS= new ArrayList<JGScene>();
	Label debugLabel;
//	FieldList<Scene> scenes = new FieldList<>();

	public JGSceneManager() {
		this.activeScene.addEventHandler((JGScene b) -> {this.changeScenes(b);});
		this.activeScene.set(new JGScene());
//		JGame.debug.addEventHandler((b) -> {
//			if (b) {
//				debugLabel = new Label("");
//				Label label = new Label("label");
//				debugLabel.setText(
//						String.format("Averate FPS: %.3f \n", tracker.getAverageFPS()) +
//						String.format("Instant FPS rate: %.3f \n", tracker.getInstantFPS()) +
//						"Rendered Objects: " + spriteManager.getActiveSprites().size()
//					);
//				this.activeScene.get().stackPane.get().getChildren().add(label);
//				return;
//			}
//			debugLabel = null;
//		});
//		this.scenes.addAll(new Scene(new Group(), 1, 1), new Scene(new Group(), 1, 1));
		
	}
	
	private void changeScenes(JGScene b) {
		System.out.println("JGSceneManager got an active scene");
		JGame.stage.get().setScene(b.scene.get());
		if (debugLabel != null) {
			Label label = new Label("label");
			b.stackPane.get().getChildren().add(label);
		}
	}
	
//	class ActiveScene implements FieldListener<Scene> {
//		@Override
//		public void changed(Scene obj) {
//			System.out.println("changed");
//		}
//	}

}

/*
 * 				if (debug) {
					debugLabel.setText(
							String.format("Averate FPS: %.3f \n", tracker.getAverageFPS()) +
							String.format("Instant FPS rate: %.3f \n", tracker.getInstantFPS()) +
							"Rendered Objects: " + spriteManager.getActiveSprites().size()
						);
				}
 */

