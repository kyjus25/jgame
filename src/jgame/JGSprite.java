package jgame;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import jgame.generics.*;

public class JGSprite extends CommonControls {
	public FieldEvent<Double> positionX = new FieldEvent<>(0.0);
	public FieldEvent<Double> positionY = new FieldEvent<>(0.0);
	public Field<Double> velocityX = new Field<>(0.0);
	public Field<Double> velocityY = new Field<>(0.0);
	public Field<Double> width = new Field<>();
	public Field<Double> height = new Field<>();
	public Field<Boolean> active = new Field<>(true);
	public Field<Boolean> canMove = new Field<>(false);
	public Field<Node> node = new Field<>(new ImageView());

	public JGSprite() {
//		JGame.tick.addEventHandler((event) -> {
//			if (active.get()) {
//				if (velocityX.get() != 0) {
//					positionX.set(positionX.get() + velocityX.get());
//				}
//				if (velocityY.get() != 0) {
//					positionY.set(positionY.get() + velocityY.get());
//
//				}
//			}
//		});
		JGame.spriteManager.spriteList.add(this);
		
		positionX.addEventHandler((x) -> {
			node.get().setTranslateX(x);
		});
		
		positionY.addEventHandler((y) -> {
			node.get().setTranslateY(y);
		});
	}
	
	public void setVelocity(double x, double y) {
		velocityX.set(x);
		velocityY.set(y);
	}
	
	public void addToScene() {
		JGame.sceneManager.activeScene.get().stackPane.get().getChildren().add(node.get());
		active.set(true);
	}
	
	public void update() {
		if (active.get()) {
			if (velocityX.get() != 0) {
				positionX.set(positionX.get() + velocityX.get());
			}
			if (velocityY.get() != 0) {
				positionY.set(positionY.get() + velocityY.get());
	
			}
		}
	}
	
    public boolean intersects(JGSprite s)
    {
        return s.node.get().getBoundsInParent().intersects(node.get().getBoundsInParent());
    }
}