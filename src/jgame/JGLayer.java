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
		if (JGame.networkManager.hosting.get()) {
			pane.get().getChildren().add(sprite.node.get());
		}
	}

	public void addToLayer(JGSprite sprite, boolean force) {
		if (force) {
			pane.get().getChildren().add(sprite.node.get());
		}
	}

	public JGSprite create(String type) { return null; }
	
	public void removeFromLayer(Node node) {
		pane.get().getChildren().remove(node);
	}
	
	public void removeFromLayer(JGSprite sprite) {
		if (sprite != null) {
			try {
				pane.get().getChildren().remove(sprite.node.get());
			} catch(Exception e) { }
		}

	}
}
