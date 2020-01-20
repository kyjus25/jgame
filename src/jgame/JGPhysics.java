package jgame;

import java.util.function.Function;

import jgame.generics.Field;
import main.GGame.GGame;

public class JGPhysics {
	public Field<String> name = new Field<>();
	public Field<Boolean> active = new Field<>();
//	public Function handle;
	
	public JGPhysics(String name) {
		this.name.set(name);
		GGame.physicsManager.physicsList.add(this);
	}
	
	public JGPhysics(String name, Boolean active) {
		this.name.set(name);
		this.active.set(active);
		GGame.physicsManager.physicsList.add(this);
	}
	
	public void handle(JGSprite sprite) {}
}
