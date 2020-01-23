package main.GGame;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import jgame.JGPhysics;
//import main.ID;
//import main.gameengine.nodes.Item;
import jgame.JGSprite;
import jgame.JGame;

public class Arrow extends JGSprite {
	Rectangle rectangle;
    public Arrow() {
		rectangle = new Rectangle(0, 0, 50, 10);
		rectangle.setFill(Color.RED);

		type.set("arrow");
    	canMove.set(true);
    	active.set(true);
    	node.set(rectangle);
		velocityX.set(10.0);
		velocityY.set(-10.0);
		rotate.set(-30.0);
	    RotateTransition rt = new RotateTransition(Duration.millis(1000), node.get());
	    rt.setToAngle(45);
	    rt.setCycleCount(1);
	    rt.setAutoReverse(false);
	    rt.play();
//    	System.out.println(node.get().localToParent(node.get().getBoundsInLocal()));
    }
    
    public void onGameLoop(ActionEvent event) {
    	if (node != null) {
//    		node.get().setTranslateX(node.get().getTranslateX() + 10);
//    		node.get().setTranslateY(node.get().getTranslateY() + 10);
//    		velocityX.set(10.0);
//    		velocityY.set(-10.0);
    		if (node.get().getTranslateX() > 800) {
    			active.set(false);
    		}
    		if (node.get().getTranslateY() > 600) {
    			active.set(false);
    		}
    	}
  

    }
    
	public void onCollision(JGSprite sprite) {
		if (sprite.type.get().equals("enemy")) {
			JGame.networkManager.sendEvent("HEALTH", sprite.uuid.get());
			JGame.spriteManager.deleteSprite(this);
		}
		if (sprite.type.get().equals("ground")) {
			JGame.spriteManager.deleteSprite(this);
		}
	}
	
	public void onPhysics(JGPhysics p) {
//		System.out.println("Player onPhysics");
//		System.out.println(this.velocityY.get());
		velocityY.set(velocityY.get() + .5);

		if(velocityY.get() > 20){
			velocityY.set(20.0);
		}
    }
    
    public void collidesWall() {
//    	Bounds bounds = this.node.get().localToScene(this.node.get().getBoundsInLocal());
//		if (bounds.getMaxY() >= 600) {
//			velocityY.set(speed * -1.0);
//		}
//	
//		if (bounds.getMinY() <= 0) {
//			velocityY.set(speed * 1.0);
//		}
    }
}