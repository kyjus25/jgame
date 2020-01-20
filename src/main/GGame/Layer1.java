package main.GGame;

import java.util.Random;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;

public class Layer1 extends JGLayer {
	public Layer1() {

//    	node.set(rectangle);
	}
	
	public void initialize() {
		System.out.println("Layer1 initialize");
		for (int x = 0; x < 500; x++) {
			for (int y = 0; y < 60; y++) {
				Random rand = new Random();
				int r = rand.nextInt(255);
				int g = rand.nextInt(255);
				int b = rand.nextInt(255);
				Color randomColour = Color.rgb(r, g, b);
				Rectangle rectangle = new Rectangle(x * 10, y * 10, 10, 10);
		    	rectangle.setFill(randomColour);
		    	pane.get().getChildren().add(rectangle);
			}
		}
	}
}
