package jgame;

import java.util.HashMap;
import java.util.List;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import jgame.generics.FieldReadOnly;

public class JGMenuBar extends FieldReadOnly<MenuBar> {
	public JGMenuBar() {
		super(new MenuBar());
		addEvents();
		listenToScenes();	
	}
	
	private void listenToScenes() {
		Menu m = new Menu("Scenes");
		super.get().getMenus().add(m);
		ToggleGroup toggleGroup = new ToggleGroup();
		JGame.sceneManager.sceneList.addEventHandler((list, changed) -> {
			if (changed != null) {
				RadioMenuItem mi = new RadioMenuItem(changed.name.get());
				mi.setOnAction((event) -> {
					JGame.sceneManager.changeScenes(changed);
				});
				toggleGroup.getToggles().add(mi);
				m.getItems().add(mi);
			}
		});		
	}
	
	private void addEvents() {
		Menu m = new Menu("Events");
		
		MenuItem pause = new MenuItem("Pause");
		pause.setOnAction((event) -> {
			JGame.gameLoop.get().pause();
		});
		
		MenuItem play = new MenuItem("Play");
		play.setOnAction((event) -> {
			JGame.gameLoop.get().play();
		});
		
		MenuItem stop = new MenuItem("Stop");
		stop.setOnAction((event) -> {
			JGame.gameLoop.get().stop();
		});
		
		MenuItem restart = new MenuItem("Restart");
		restart.setOnAction((event) -> {
//			JGame.stage.get().close();
//			JGame.reset();
			JGScene scene = JGame.sceneManager.activeScene.get();
			JGame.sceneManager.changeScenes(scene);
//			scene.reset();
//			scene.initialize();
//			scene = new JGScene(scene);
//			scene.stackPane.get().getChildren().clear();
//			JGame.sceneManager.activeScene.get().initialize();
		});
		
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction((event) -> {
			JGame.stage.get().close();
		});
		
		m.getItems().addAll(play, pause, stop, restart, exit);
		super.get().getMenus().add(m);
	}

	
}
