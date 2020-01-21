package jgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jgame.generics.FieldEvent;
import jgame.generics.FieldList;
import jgame.generics.FieldListener;
import jgame.generics.KeyStateEvent;
import jgame.generics.PhysicsEvent;
import main.GGame.GGame;

public class JGPhysicsManager {
    private static List<PhysicsEvent> listeners = new ArrayList<PhysicsEvent>();
    private static List<JGPhysics> physicslisteners = new ArrayList<JGPhysics>();

	//  Class used to help manager movement of sprites
	public FieldList<JGPhysics> physicsList = new FieldList<>();
//	private static HashMap<JGPhysics, List<FieldListener<JGSprite>>> physicsList = new HashMap<>();
	public JGPhysicsManager() {
		JGame.tick.addEventHandler((tick) -> {
//			System.out.println("LISTENERS " + listeners.size());
			physicsList.stream().filter(i -> i.active.get().equals(true)).collect(Collectors.toList()).forEach(physics -> {
				JGame.spriteManager.activeSprites.stream().filter(i -> i.active.get().equals(true) && i.canMove.get().equals(true))
				.collect(Collectors.toList()).forEach(sprite -> {
					listeners.forEach((item) -> {
						item.changed(physics, sprite);
					});
					sprite.onPhysics(physics);
				});
			});
			
//			for (JGPhysics physics : physicsList.keySet()) {
//				if (physics.active.get() == true) {
//					physicsList.get(physics).forEach(i -> {
//						System.out.println(i);
//					});
////					JGame.spriteManager.spriteList.forEach(sprite -> {
////						physics.handle(sprite);
////					});
//				}
//			}
		});
	}
	
//	public static void addEventHandler(JGPhysics physics,  FieldListener<JGSprite> toAdd) {
//		if (physicsList.containsKey(physics.name.get())) {
//			physicsList.get(physics.name.get()).add(toAdd);
//			return;
//		}
//		List<FieldListener<JGSprite>> list = new ArrayList<FieldListener<JGSprite>>();
//		list.add(toAdd);
//		physicsList.put(physics, list); 
//	}
	
//	public void add(JGPhysics physics) {
//		if (physicsList.containsKey(physics)) {
//			return;
//		}
//		List<FieldListener<JGSprite>> list = new ArrayList<FieldListener<JGSprite>>();
//		physicsList.put(physics, list);		 
//	}
	
	public static void addEventHandler(PhysicsEvent toAdd) {
		listeners.add(toAdd);
	}
	
	public void reset() {
		physicsList.clear();
		listeners.clear();
	}
}
