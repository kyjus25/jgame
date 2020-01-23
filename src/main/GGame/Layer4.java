package main.GGame;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;
import jgame.JGSprite;

public class Layer4 extends JGLayer {
	public Layer4() {
		
//		pane.get().getChildren().clear();
//		pane.get().getChildren().add(player.node.get());
	}
	public void initialize() {
		System.out.println("Layer4 initialize");
//		pane.get().getChildren().clear();
//		Random rand = new Random();
//		for (int i = 0; i < 20; i++) {
//			int x = rand.nextInt(4000);
//			int y = rand.nextInt(400);
//			Enemy enemy = new Enemy(x + 800, y, 50, 50);
//			enemy.health.addEventHandler(health -> {
//				if (health <= 0) {
//					for (int ii = 0; ii < 5; ii++) {
//						Coin coin = new Coin(enemy.positionX.get(), enemy.positionY.get());
//						addToLayer(coin);
//						coin.active.addEventHandler(isActive -> {
//							if (!isActive) {
//								removeFromLayer(coin);
//							}
//						});
//					}
//					removeFromLayer(enemy);
//				}
//			});
////	    	rectangle.setFill(Color.PURPLE);
////	    	pane.get().getChildren().add(enemy.node.get());
//	    	addToLayer(enemy);
//		}
	}
}
