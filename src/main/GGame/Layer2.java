package main.GGame;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;
import jgame.JGSprite;

public class Layer2 extends JGLayer {
	public Layer2() {

//    	node.set(rectangle);
	}
	
	public void initialize() {
		System.out.println("Layer2 initialize");
		addToLayer(create("ground"));
	}

	public JGSprite create(String type) {
		JGSprite sprite = null;
		switch (type) {
			case "ground":
				sprite = new JGSprite();
				sprite.type.set("ground");
				Group group = new Group();
				sprite.node.set(group);
				for (int x = 0; x < 50; x++) {
					Rectangle rectangle = new Rectangle(x * 100, 500, 100, 100);
					rectangle.setFill(Color.BLACK);
					group.getChildren().add(rectangle);
				}
				break;
		}
		return sprite;
	}
}
