package main.GGame;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGPhysics;
//import main.ID;
//import main.gameengine.nodes.Item;
import jgame.JGSprite;

public class Player extends JGSprite {
	private double speed = 5.0;

    public Player() {
    	Rectangle rectangle = new Rectangle(0, 0, 50, 50);
    	canMove.set(true);
    	active.set(true);
    	rectangle.setFill(Color.RED);
    	node.set(rectangle);
    	
//    	GGame.keyboardManager.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
//    		if (event == KeyCode.W) {this.velocityY.set(speed * -1.0);}
//    		if (event == KeyCode.S) {this.velocityY.set(speed * 1.0);}
//    		if (event == KeyCode.A) {this.velocityX.set(speed * -1.0);}
//    		if (event == KeyCode.D) {this.velocityX.set(speed * 1.0);}
//    	});
//
//    	GGame.keyboardManager.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
//    		if (event == KeyCode.W) {this.velocityY.set(0.0);}
//    		if (event == KeyCode.S) {this.velocityY.set(0.0);}
//    		if (event == KeyCode.A) {this.velocityX.set(0.0);}
//    		if (event == KeyCode.D) {this.velocityX.set(0.0);}
//    	});
    }
    
	public void onKeyPress(KeyCode key) {
		if (key == KeyCode.W) {velocityY.set(speed * -1.0);}
		if (key == KeyCode.S) {velocityY.set(speed * 1.0);}
		if (key == KeyCode.A) {velocityX.set(speed * -1.0);}
		if (key == KeyCode.D) {velocityX.set(speed * 1.0);}
		if (key == KeyCode.SPACE) {velocityY.set(-20.0);}
	}
	public void onKeyReleased(KeyCode key) {
		if (key == KeyCode.W) {velocityY.set(0.0);}
		if (key == KeyCode.S) {velocityY.set(0.0);}
		if (key == KeyCode.A) {velocityX.set(0.0);}
		if (key == KeyCode.D) {velocityX.set(0.0);}
	}
	public void onKeyEvent(KeyCode key, boolean isPressed) {
		
	}
	
	public void onCollision(JGSprite sprite) {
		if (velocityY.get() > 0) {
			velocityY.set(0.0);
		}
		
//		System.out.println(sprite.node.get().getBoundsInLocal());
//		System.out.println(node.get().getBoundsInLocal());
		// BoundingBox [minX:0.0, minY:500.0, minZ:0.0, width:5000.0, height:100.0, depth:0.0, maxX:5000.0, maxY:600.0, maxZ:0.0]
		// BoundingBox [minX:0.0, minY:0.0, minZ:0.0, width:50.0, height:50.0, depth:0.0, maxX:50.0, maxY:50.0, maxZ:0.0]
//		System.out.println(node.get().getBoundsInLocal());
//		positionY.set();
	}
	
	public void onPhysics(JGSprite b, JGPhysics p) {
		   b.velocityY.set(b.velocityY.get() + 1);
		    if(b.velocityY.get() > 20){
		        b.velocityY.set(20.0);
		    }		}
}
