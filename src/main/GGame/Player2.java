package main.GGame;

import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGPhysics;
import jgame.JGSprite;
import jgame.JGame;

//import main.ID;
//import main.gameengine.nodes.Item;

public class Player2 extends JGSprite {
	public double speed = 5.0;

    public Player2() {
    	Rectangle rectangle = new Rectangle(0, 0, 50, 50);

    	type.set("player2");
    	canMove.set(false);
    	active.set(false);
    	rectangle.setFill(Color.BLUE);
    	node.set(rectangle);
    	positionX.set(200.0);
    	positionY.set(200.0);
    }
    
    public void initialize() {
    	System.out.println("Player2 initialized");
    }
 }

