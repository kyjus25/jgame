package jgame;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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
		addMetrics();
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
			JGame.exit();
		});
		
		m.getItems().addAll(play, pause, stop, restart, exit);
		super.get().getMenus().add(m);
	}

	private void addMetrics() {
		Menu m = new Menu("Metrics");
		super.get().getMenus().add(m);
		ToggleGroup toggleGroup = new ToggleGroup();

		RadioMenuItem mode = new RadioMenuItem(); toggleGroup.getToggles().add(mode); m.getItems().add(mode);
		RadioMenuItem avgFPS = new RadioMenuItem(); toggleGroup.getToggles().add(avgFPS); m.getItems().add(avgFPS);
		RadioMenuItem currFPS = new RadioMenuItem(); toggleGroup.getToggles().add(currFPS); m.getItems().add(currFPS);
		RadioMenuItem name = new RadioMenuItem(); toggleGroup.getToggles().add(name); m.getItems().add(name);
		RadioMenuItem sprites = new RadioMenuItem(); toggleGroup.getToggles().add(sprites); m.getItems().add(sprites);
		RadioMenuItem players = new RadioMenuItem(); toggleGroup.getToggles().add(players); m.getItems().add(players);

		JGame.tick.addEventHandler(tick -> {
			DecimalFormat df = new DecimalFormat("#");
			df.setRoundingMode(RoundingMode.CEILING);
			avgFPS.setText("Avg FPS: " + df.format(JGame.tracker.getAverageFPS()));
			currFPS.setText("Current FPS: " + df.format(JGame.tracker.getInstantFPS()));
		});

		JGame.networkManager.users.addEventHandler((list, changed) -> {
			mode.setText("Mode: " + (JGame.networkManager.hosting.get() ? "Host" : "Client"));
			name.setText("Name: " + JGame.networkManager.self.nick.get());
			players.setText("Player: " + JGame.networkManager.getPlayerNumber() + " of " + list.size());
		});

		JGame.spriteManager.activeSprites.addEventHandler((list, changed) -> {
			if (changed != null) {
				sprites.setText("Active Sprites: " + list.size());
			}
		});
	}

	
}
