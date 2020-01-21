package jgame;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import jgame.generics.*;

public class JGLayer extends CommonControls {
	public FieldEvent<Integer> zIndex = new FieldEvent<>(0);
	public Field<Pane> pane = new Field<>(new Pane());
	
	public void addToLayer(Node node) {
		pane.get().getChildren().add(node);
	}
	
	public void addToLayer(JGSprite sprite) {
		pane.get().getChildren().add(sprite.node.get());
	}
	
	public void removeFromLayer(Node node) {
		pane.get().getChildren().remove(node);
	}
	
	public void removeFromLayer(JGSprite sprite) {
		pane.get().getChildren().remove(sprite.node.get());
	}
}
