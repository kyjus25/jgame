package jgame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jgame.generics.*;

public class JGMouseManager extends CommonControls {
    private static List<MoustStateEvent> listeners = new ArrayList<MoustStateEvent>();
    private static List<MoustStateEvent> toBeAdded = new ArrayList<MoustStateEvent>();

	public JGMouseManager() {
		JGame.sceneManager.scene.addEventHandler((Scene b) -> {this.setScene(b);});
	}
	
	public void setScene(Scene scene) {
		scene.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> {
//			MouseButton k = event.getButton();
//			KeyCode key = event.getCode();
//			if (eventListeners.containsKey(KeyEvent.KEY_PRESSED)) {
//				eventListeners.get(KeyEvent.KEY_PRESSED).forEach((n) -> n.changed(key));
//			}
//
//			if (keyListeners.containsKey(key)) {
//				keyListeners.get(key).forEach((n) -> n.changed(key));
//			}
//
//			comboListeners.forEach((k , list) -> {
//				if (k.match(event)) {
//		            list.forEach((n) -> n.changed(k));
//		        }
//			});
//			
//			listeners.forEach((item) -> {
//				item.changed(key, true);
//			});
			handleEvent(event, true);

        });

		scene.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
			handleEvent(event, false);
//			MouseButton k = event.getButton();
//			KeyCode key = event.getCode();
//			if (eventListeners.containsKey(KeyEvent.KEY_RELEASED)) {
//				eventListeners.get(KeyEvent.KEY_RELEASED).forEach((n) -> n.changed(key));
//			}
//			
//			listeners.forEach((item) -> {
//				item.changed(key, false);
//			});
			
//			List<MoustStateEvent> copy = new ArrayList<MoustStateEvent>();
//			copy.addAll(listeners);
//			copy.forEach((item) -> {
//				item.changed(event, false);
//			});
//		    Iterator<MoustStateEvent> iterator = listeners.iterator();
//		    
//		    while(iterator.hasNext()){
//		    	System.out.println("Itterator" + iterator.next());
//		    	iterator.next().changed(event, false);
//		    }
        });
	}
	
	public static void addEventHandler(MoustStateEvent toAdd) {
		if (!listeners.contains(toAdd)) {
			listeners.add(toAdd);
		}
	}
	
	private void handleEvent(MouseEvent event, boolean isPressed) {
		List<MoustStateEvent> copy = new ArrayList<MoustStateEvent>();
		copy.addAll(listeners);
		copy.forEach((item) -> {
			item.changed(event, isPressed);
		});
	}
}

/*
this.scene.addEventHandler(MouseEvent.MOUSE_PRESSED, (mouseEvent) -> {
MouseButton k = mouseEvent.getButton();
if (keyState.containsKey(k)) {
	keyState.replace(k, mouseEvent);
} else {
	keyState.put(k, mouseEvent);
}

for (Sprite gameActor : main.gameengine.Game.spriteManager.getAllSprites()) {
    gameActor.handleMouseEvent(mouseEvent, true);
}
});

this.scene.addEventHandler(MouseEvent.MOUSE_RELEASED, (mouseEvent) -> {
MouseButton k = mouseEvent.getButton();
if (keyState.containsKey(k)) {
	keyState.replace(k, false);
} else {
	keyState.put(k, false);
}
for (Sprite gameActor : main.gameengine.Game.spriteManager.getAllSprites()) {
	gameActor.handleMouseEvent(mouseEvent, false);
}
});
*/