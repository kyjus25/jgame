package main.GGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;

public class Layer3 extends JGLayer {
	public void initialize() {
		System.out.println("Layer3 initialize");
//    	Rectangle rectangle = new Rectangle(0, 0, 50, 50);
//    	rectangle.setFill(Color.GREEN);
		Player player = new Player();
    	pane.get().getChildren().add(player.node.get());
	}
}
