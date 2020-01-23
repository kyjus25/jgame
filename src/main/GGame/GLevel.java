package main.GGame;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;
import jgame.JGPhysics;
import jgame.JGScene;
import jgame.JGSprite;

public class GLevel extends JGScene {
	
	JGLayer background;
	JGLayer ground;
	Layer3 playerLayer;
	Layer4 enemyLayer;
	JGPhysics gravity;

	public GLevel() {
		networkSprite.set("player2");
	}

	public void initialize() {
		System.out.println("GLevel initialize");
		
		background = new Layer1();
		ground = new Layer2();
		playerLayer = new Layer3();
		enemyLayer  = new Layer4();
		gravity = new JGPhysics("gravity", true);
		
		background.zIndex.set(0);
		ground.zIndex.set(1);
		enemyLayer.zIndex.set(2);
		playerLayer.zIndex.set(3);
		
		layers.add(background);
		layers.add(ground);
		layers.add(enemyLayer);
		layers.add(playerLayer);
		
		
//		stackPane.get().translateXProperty().bind(playerLayer.player.node.get().translateXProperty().multiply(-1));
	}
	
	public void onKeyPress(KeyCode key) {
//		l1.zIndex.set(l1.zIndex.get()-1);
//		background.pane.get().setTranslateX(background.pane.get().getTranslateX() - 10);
	}
	
	public void onGameLoop(ActionEvent event) {
		
//		if (playerLayer != null) {
//			Bounds backgroundboundsInScene = background.pane.get().localToScene(background.pane.get().getBoundsInLocal());
////			Bounds backgroundboundsInScreen = background.pane.get().localToScreen(background.pane.get().getBoundsInLocal());
//			Bounds boundsInScene = playerLayer.player.node.get().localToScene(playerLayer.player.node.get().getBoundsInLocal());
////			Bounds boundsInScreen = playerLayer.player.node.get().localToScreen(playerLayer.player.node.get().getBoundsInLocal());
//			if (boundsInScene.getMinX() >= 400 && backgroundboundsInScene.getMaxX() >= 800) {
//				stackPane.get().setTranslateX(stackPane.get().getTranslateX() - playerLayer.player.speed);
//			}
//
//			if (boundsInScene.getMaxX() <= 200 && backgroundboundsInScene.getMinX() <= 0) {
//				stackPane.get().setTranslateX(stackPane.get().getTranslateX() + playerLayer.player.speed);
//			}
//		}
	}
}
