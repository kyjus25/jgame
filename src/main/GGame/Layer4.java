package main.GGame;

import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;
import jgame.JGSprite;
import jgame.JGame;

public class Layer4 extends JGLayer {
	public Layer4() {
		
//		pane.get().getChildren().clear();
//		pane.get().getChildren().add(player.node.get());
	}
	public void initialize() {
		System.out.println("Layer4 initialize");
		pane.get().getChildren().clear();
		Random rand = new Random();
		for (int i = 0; i < 20; i++) {
			int x = rand.nextInt(4000);
			int y = rand.nextInt(400);
			Enemy enemy = (Enemy) create("enemy");
			enemy.positionX.set(x + 800.0);
			enemy.positionY.set(y + 0.0);
			enemy.health.addEventHandler(health -> {
				if (health <= 0) {
					for (int ii = 0; ii < 5; ii++) {
						Coin coin = (Coin) create("coin");
						coin.positionX.set(enemy.positionX.get());
						coin.positionY.set(enemy.positionY.get());
						addToLayer(coin);
					}
					JGame.networkManager.sendDelete(enemy.uuid.get());
				}
			});
//	    	rectangle.setFill(Color.PURPLE);
//	    	pane.get().getChildren().add(enemy.node.get());
	    	addToLayer(enemy);
		}
	}

	@Override
	public JGSprite create(String type) {
		JGSprite sprite = null;
		switch (type) {
			case "enemy":
				sprite = new Enemy();
				break;
			case "coin":
				sprite = new Coin();
				break;
		}
		return sprite;
	}
}
