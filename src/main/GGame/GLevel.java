package main.GGame;

import javafx.scene.shape.Rectangle;
import jgame.JGScene;

public class GLevel extends JGScene {
	public GLevel() {
		Rectangle rectangle = new Rectangle(400, 400, 20, 20);
		
		stackPane.get().getChildren().add(rectangle);
	}
}
