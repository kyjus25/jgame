package main.GGame;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGSprite;
import jgame.generics.Field;
import jgame.generics.FieldEvent;

public class Enemy extends JGSprite {
	public double speed = 1.0;
//	public double health = 100;
	public FieldEvent<Double> health = new FieldEvent<>(100.0);
	
	
	StackPane healthBar = new StackPane();
	Rectangle healthRed;
	Rectangle healthGreen;
    public Enemy(double x, double y, int cx, int cy) {
    	BorderPane panel = new BorderPane();
    	Random rand = new Random();
    	speed = rand.nextInt(4) + 1;
    	Rectangle rectangle = new Rectangle(x, y, cx, cy);
    	canMove.set(false);
    	active.set(true);
    	rectangle.setFill(Color.WHITE);
    	velocityY.set(speed);

    	healthRed = new Rectangle(0, 0, cx, 10);
    	healthRed.setFill(Color.RED);
    	
    	healthGreen = new Rectangle(0, 0, cx, 10);
    	healthGreen.setFill(Color.GREEN);
    	
    	healthBar.getChildren().addAll(healthRed, healthGreen);
    	
    	panel.setTop(healthBar);
    	panel.setCenter(rectangle);
    	panel.setTranslateX(x);
    	panel.setTranslateY(y);
    	positionX.set(x);
    	positionY.set(y);
    	node.set(panel);
//    	System.out.println(speed);
    }
    
    public void onGameLoop(ActionEvent event) {
    	if (node != null) {
    		collidesWall();
    		healthGreen.setScaleX(health.get() * .01);
    	}

    }
    
    public void collidesWall() {
    	Bounds bounds = this.node.get().localToScene(this.node.get().getBoundsInLocal());
		if (bounds.getMaxY() >= 600) {
			velocityY.set(speed * -1.0);
		}
	
		if (bounds.getMinY() <= 0) {
			velocityY.set(speed * 1.0);
		}

    }
}
