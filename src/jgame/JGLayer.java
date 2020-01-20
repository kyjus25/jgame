package jgame;

import javafx.scene.layout.Pane;
import jgame.generics.*;

public class JGLayer extends CommonControls {
	public FieldEvent<Integer> zIndex = new FieldEvent<>(0);
	public Field<Pane> pane = new Field<>(new Pane());
}
