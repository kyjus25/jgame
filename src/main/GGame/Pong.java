package main.GGame;

import javafx.event.ActionEvent;
import javafx.geometry.Bounds;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jgame.JGLayer;
import jgame.JGPhysics;
import jgame.JGScene;
import jgame.JGSprite;

import java.util.Random;

public class Pong extends JGScene {
    // ongLayer main;
    // JGLayer ground;
    // PlayerLayer playerLayer;
    // Layer4 enemyLayer;

    public Pong() {}

    public void initialize() {
        System.out.println("Pong initialize");

        // main = ;
        // ground = new Layer2();
        // playerLayer = new PlayerLayer();
        // enemyLayer  = new Layer4();

        // background.zIndex.set(0);
        // ground.zIndex.set(1);
        // enemyLayer.zIndex.set(2);
        // playerLayer.zIndex.set(3);

        layers.add(new PongLayer());
        // layers.add(ground);
        // layers.add(enemyLayer);
        // layers.add(playerLayer);
    }


}

class PongLayer extends JGLayer {
    public void initialize() {
        pane.get().getChildren().clear();
        Rectangle rectangle = new Rectangle(0, 0, 800, 600);
        rectangle.setFill(Color.BLACK);
        pane.get().getChildren().add(rectangle);

        PongBall ball = new PongBall();

        addToLayer(new PongPlayer());
        addToLayer(ball);
        addToLayer(new PongPaddle(ball));
    }
}

class PongPlayer extends JGSprite {
    public double speed = 5.0;

    public PongPlayer() {
        Rectangle rectangle = new Rectangle(0, 0, 20, 100);

        canMove.set(true);
        active.set(false);
        rectangle.setFill(Color.WHITE);
        node.set(rectangle);
        positionX.set(40.0);
        positionY.set(400.0 - 100.0);
    }
    public void onKeyPress(KeyCode key) {
        if (key == KeyCode.W) {velocityY.set(speed * -1.0);}
        if (key == KeyCode.S) {velocityY.set(speed * 1.0);}
    }
    public void onKeyReleased(KeyCode key) {
        if (key == KeyCode.W) {velocityY.set(0.0);}
        if (key == KeyCode.S) {velocityY.set(0.0);}
    }
}

class PongPaddle extends JGSprite {
    public double speed = 5.0;
    public PongBall ball;

    public PongPaddle(PongBall ball) {
        Rectangle rectangle = new Rectangle(0, 0, 20, 100);
        this.ball = ball;

        canMove.set(true);
        active.set(false);
        rectangle.setFill(Color.WHITE);
        node.set(rectangle);
        positionX.set(800.0 - 40);
        positionY.set(400.0 - 100.0);
    }

    public void onGameLoop(ActionEvent e) {
        if (ball.positionY.get() > positionY.get()) {
            velocityY.set(speed);
        } else {
            velocityY.set(-speed);
        }
    }
}

class PongBall extends JGSprite {
    public double speed = 5.0;

    public PongBall() {
        Rectangle rectangle = new Rectangle(0, 0, 20, 20);

        canMove.set(true);
        active.set(false);
        rectangle.setFill(Color.WHITE);
        node.set(rectangle);
        positionX.set(400.0);
        positionY.set(300.0);

        velocityX.set(5.0);
        velocityY.set(3.0);
    }

    public void onGameLoop(ActionEvent e) {
        if (node != null) {
            collidesWall();
        }
    }

    public void onCollision(JGSprite sprite) {
        if (sprite instanceof PongPlayer || sprite instanceof PongPaddle) {
            velocityX.set(velocityX.get() * -1.0);
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

        if (bounds.getMaxX() >= 800) {
            velocityX.set(speed * -1.0);
        }

        if (bounds.getMinX() <= 0) {
            velocityX.set(speed * 1.0);
        }

    }
}
