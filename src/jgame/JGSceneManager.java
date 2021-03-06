package jgame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.sun.javafx.perf.PerformanceTracker;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import jgame.generics.*;

public class JGSceneManager {
	public FieldReadOnly<VBox> vbox = new FieldReadOnly<>(new VBox());
	BorderPane borderPane = new BorderPane();
	public FieldEvent<Scene> scene = new FieldEvent<Scene>(new Scene(borderPane, JGame.width.get(), JGame.height.get()));
//	public FieldReadOnly<Scene> scene = new FieldReadOnly<>(new Scene(vbox, JGame.width.get(), JGame.height.get()));
	public static FieldEvent<JGScene> activeScene = new FieldEvent<>();
	FieldList<JGScene> sceneList = new FieldList<>();
//    private static List<JGScene> listeners = new ArrayList<JGScene>();

	
//	 private final static List<JGScene> GAME_GROUPS= new ArrayList<JGScene>();
	Label debugLabel;
//	FieldList<Scene> scenes = new FieldList<>();

	public JGSceneManager() {
//		this.activeScene.addEventHandler((JGScene b) -> {this.changeScenes(b);});
//		this.changeScenes(new JGScene());
//		this.activeScene.set(new Scene());
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
	
	public void changeScenes(JGScene jgscene) {

		if (JGame.tracker != null) { JGame.tracker.resetAverageFPS(); }

		
//		vbox.get().getChildren().clear();
//		jgscene.reset();
//		vbox.get().getChildren().add(JGame.menuBar.get());
//		vbox.get().getChildren().add(new Group(jgscene.stackPane.get()));
//		jgscene.stackPane.get().getChildren().add(new BorderPane());

//		JGame.stage.get().setScene(scene.get());
//		jgscene.initialize();
//		this.activeScene.set(jgscene);
	     
	     borderPane.setTop(JGame.menuBar.get());
	     borderPane.setCenter(jgscene.stackPane.get());
//	     borderPane.setBottom(statusbar);
	     
	    jgscene.reset();
	    jgscene.initialize();
//		jgscene.stackPane.get().getChildren();
		JGame.stage.get().setScene(scene.get());
		this.activeScene.set(jgscene);

		JGame.tracker = PerformanceTracker.getSceneTracker(scene.get());
		JGame.stage.get().setTitle(jgscene.name.get());

	}

	public void changeSceneByName(String n) {
		if (!activeScene.get().name.get().equals(n)) {
			JGScene scene = this.sceneList.stream().filter(p -> p.name.get().equals(n)).collect(Collectors.toList()).get(0);
			changeScenes(scene);
		}
	}
	
//	class ActiveScene implements FieldListener<Scene> {
//		@Override
//		public void changed(Scene obj) {
//			System.out.println("changed");
//		}
//	}
	
//	public static void addEventHandler(FieldEvent<JGScene> toAdd) {
//		listeners.add(toAdd);
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
				
	
		HBox,	 The HBox layout arranges all the nodes in our application in a single horizontal row.		
		VBox,	The VBox layout arranges all the nodes in our application in a single vertical column.	
		Border Pane,	The Border Pane layout arranges the nodes in our application in top, left, right, bottom and center positions.
		Stack Pane,		The stack pane layout arranges the nodes in our application on top of another just like in a stack. The node added first is placed at the bottom of the stack and the next node is placed on top of it.
		Text Flow,		The Text Flow layout arranges multiple text nodes in a single flow.
		Anchor Pane,	The Anchor pane layout anchors the nodes in our application at a particular distance from the pane.
		Title Pane,		The Tile Pane layout adds all the nodes of our application in the form of uniformly sized tiles.
		Grid Pane,		The Grid Pane layout arranges the nodes in our application as a grid of rows and columns. This layout comes handy while creating forms using JavaFX.
		Flow Panel		The flow pane layout wraps all the nodes in a flow. A horizontal flow pane wraps the elements of the pane at its height, while a vertical flow pane wraps the elements at its width.
	
 */

