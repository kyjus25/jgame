package main.GGame;

import java.util.Random;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import jgame.JGPhysics;
//import main.ID;
//import main.gameengine.nodes.Item;
import jgame.JGSprite;

public class Coin extends JGSprite {
    public Coin() {
    	Rectangle rectangle = new Rectangle(0, 0, 20, 40);
    	rectangle.setFill(Color.YELLOW);
    	type.set("coin");
    	node.set(rectangle);
    	Random rand = new Random();
    	canMove.set(true);
    	active.set(true);
    	velocityX.set((double) rand.nextInt(10) - 5);
    	velocityY.set((double) rand.nextInt(10) - 5);
    	
//	    RotateTransition rt = new RotateTransition(Duration.millis(1000), node.get());
//	    rt.setToAngle(45);
//	    rt.setCycleCount(1);
//	    rt.setAutoReverse(false);
//	    rt.play();
	    
//	    RotateTransition rt = new RotateTransition(Duration.millis(1000), node.get());
//	    rt.setToAngle(45);
//	    rt.setCycleCount(1);
//	    rt.setAutoReverse(false);
//	    rt.play();
    	
//    	setType(this.getClass().getSimpleName().toLowerCase());
//        setCanvasSize(10, 20);
//        positionX = x;
//        positionY = y;
//        gc.setFill(Color.YELLOW);
//        gc.fillRect(0, 0, 10, 20);
//        updatePosition();
//        setID(ID.Coin);
    }
    
	public void onPhysics(JGPhysics p) {
//		System.out.println("Player onPhysics");
//		System.out.println(this.velocityY.get());
		velocityY.set(velocityY.get() + .5);

		if(velocityY.get() > 20){
			velocityY.set(20.0);
		}
    }
	
    public void onGameLoop(ActionEvent event) {
//    	if (node != null) {
//    		collidesWall();
//    		healthGreen.setScaleX(health * .01);
//    	}

    }
    
	public void onCollision(JGSprite sprite) {
		if (velocityY.get() > 0) {
			velocityY.set(0.0);
		}	
	}
}