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
//    	Rectangle rectangle = new Rectangle(0, 0, 100, 100);
//    	rectangle.setFill(Color.BLUE);
		JGSprite ground = new JGSprite();
    	Group group = new Group();
    	ground.node.set(group);
		for (int x = 0; x < 50; x++) {
//			for (int y = 0; y < 60; y++) {
//				Random rand = new Random();
//				int r = rand.nextInt(255);
//				int g = rand.nextInt(255);
//				int b = rand.nextInt(255);
//				Color randomColour = Color.rgb(r, g, b);
				Rectangle rectangle = new Rectangle(x * 100, 500, 100, 100);
		    	rectangle.setFill(Color.BLACK);
		    	group.getChildren().add(rectangle);
//			}
		}
		pane.get().getChildren().add(ground.node.get());
    
	}
}
