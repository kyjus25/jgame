package main.GGame;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
		Canvas canvas = new Canvas();
		canvas.setWidth(50);
		canvas.setHeight(50);
		width.set(50.0);
		height.set(50.0);
		GraphicsContext gc = canvas.getGraphicsContext2D();


    	type.set("player2");
    	canMove.set(false);
    	active.set(false);
    	node.set(canvas);
    	positionX.set(200.0);
    	positionY.set(200.0);

    	JGame.tick.addEventHandler(tick -> {
			gc.setFill(Color.BLUE);
			gc.fillRect(0, 0, 50, 50);
			gc.setFill(Color.BLACK);
			gc.fillText(uuid.get(), 8, 25);
		});
    }
    
    public void initialize() {
    	System.out.println("Player2 initialized");
    }
 }

