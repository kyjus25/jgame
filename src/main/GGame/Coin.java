package main.GGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
//import main.ID;
//import main.gameengine.nodes.Item;
import jgame.JGSprite;

public class Coin extends JGSprite {
    public Coin() {
    	Rectangle rectangle = new Rectangle(400, 400, 50, 50);
    	rectangle.setFill(Color.YELLOW);
    	node.set(rectangle);
    	
//    	setType(this.getClass().getSimpleName().toLowerCase());
//        setCanvasSize(10, 20);
//        positionX = x;
//        positionY = y;
//        gc.setFill(Color.YELLOW);
//        gc.fillRect(0, 0, 10, 20);
//        updatePosition();
//        setID(ID.Coin);
    }
}