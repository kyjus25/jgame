package main.GGame;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jgame.JGLayer;
import jgame.JGSprite;
import jgame.JGame;

public class Layer3 extends JGLayer {
	Player player;
	
	public Layer3() {
		
//		pane.get().getChildren().clear();
//		pane.get().getChildren().add(player.node.get());
	}
	public void initialize() {
		System.out.println("Layer3 initialize");
		pane.get().getChildren().clear();
	}

	public JGSprite create(String type) {
		JGSprite sprite = null;
		switch (type) {
			case "player":
				sprite = new Player();
				break;
			case "player2":
				sprite = new Player2();
				break;
			case "arrow":
				sprite = new Arrow();
				break;
		}
		return sprite;
	}
	
    public void onGameLoop(ActionEvent event) {
    	
//    	if (node != null) {
//    		collidesWall();
//    	}    	
    }
}
