package jgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jgame.generics.*;
import jgame.JGLayer;

public class JGScene extends CommonControls {
	protected FieldList<JGLayer> layers = new FieldList<>();
//	protected FieldList<JGLayer> layers = new FieldList<>();
//	List<JGLayer> layers = new ArrayList<JGLayer>();
	private Group group = new Group();
	public Field<StackPane> stackPane = new Field<>(new StackPane());
	Field<Scene> scene = new Field<>(new Scene(this.stackPane.get(), JGame.width.get(), JGame.height.get()));
	public Field<String> name = new Field<>("Default");
		
	public JGScene() {
		if (this.getClass().getSimpleName().toLowerCase() != null) {
			name.set(this.getClass().getSimpleName());
		}
		JGame.sceneManager.sceneList.add(this);

		layers.addEventHandler((list, changed) -> {
			if (!stackPane.get().getChildren().contains(changed.pane.get())) {
				stackPane.get().getChildren().add(changed.pane.get());
				changed.initialize();
				changed.zIndex.addEventHandler(i -> this.orderLayers());
			}
			this.orderLayers();
		});
	}
	
	public JGScene(String name) {
		this.name.set(name);
		scene.get().setFill(Color.RED);
		JGame.sceneManager.sceneList.add(this);
	}
	
	private void orderLayers() {
		Collections.sort(layers, (JGLayer o1, JGLayer o2) -> o1.zIndex.get().compareTo( o2.zIndex.get()));
		layers.forEach(i -> {
			stackPane.get().getChildren().remove(i.pane.get());
			stackPane.get().getChildren().add(i.pane.get());
		});
	}
}
