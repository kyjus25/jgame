package jgame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCodeCombination;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jgame.generics.*;


public class JGKeyboardManager {
	
	private static HashMap<EventType<KeyEvent>, List<FieldListener<KeyCode>>> eventListeners = new HashMap<>();
	private HashMap<KeyCode, List<FieldListener<KeyCode>>> keyListeners = new HashMap<>();
	private HashMap<KeyCodeCombination, List<FieldListener<KeyCodeCombination>>> comboListeners = new HashMap<>();
    private static List<KeyStateEvent> listeners = new ArrayList<KeyStateEvent>();
	
	public JGKeyboardManager() {
		JGame.sceneManager.scene.addEventHandler((Scene b) -> {this.setScene(b);});
	}
	
	public void setScene(Scene scene) {
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
			KeyCode key = event.getCode();
			if (eventListeners.containsKey(KeyEvent.KEY_PRESSED)) {
				eventListeners.get(KeyEvent.KEY_PRESSED).forEach((n) -> n.changed(key));
			}

			if (keyListeners.containsKey(key)) {
				keyListeners.get(key).forEach((n) -> n.changed(key));
			}

			comboListeners.forEach((k , list) -> {
				if (k.match(event)) {
		            list.forEach((n) -> n.changed(k));
		        }
			});
			
			listeners.forEach((item) -> {
				item.changed(key, true);
			});
        });

		scene.addEventHandler(KeyEvent.KEY_RELEASED, (event) -> {
			KeyCode key = event.getCode();
			if (eventListeners.containsKey(KeyEvent.KEY_RELEASED)) {
				eventListeners.get(KeyEvent.KEY_RELEASED).forEach((n) -> n.changed(key));
			}
			
			listeners.forEach((item) -> {
				item.changed(key, false);
			});
        });
	}
	
	public static void addEventHandler(EventType<KeyEvent> eventType,  FieldListener<KeyCode> toAdd) {
		if (eventListeners.containsKey(eventType)) {
			eventListeners.get(eventType).add(toAdd);
			return;
		}
		List<FieldListener<KeyCode>> list = new ArrayList<FieldListener<KeyCode>>();
		list.add(toAdd);
		eventListeners.put(eventType, list);		 
	}
	
	public void addEventHandler(KeyCode keyCode,  FieldListener<KeyCode> toAdd) {
		if (keyListeners.containsKey(keyCode)) {
			keyListeners.get(keyCode).add(toAdd);
			return;
		}
		List<FieldListener<KeyCode>> list = new ArrayList<FieldListener<KeyCode>>();
		list.add(toAdd);
		keyListeners.put(keyCode, list);		 
	}
	
	public void addEventHandler(KeyCodeCombination keyCode,  FieldListener<KeyCodeCombination> toAdd) {
		if (comboListeners.containsKey(keyCode)) {
			comboListeners.get(keyCode).add(toAdd);
			return;
		}
		List<FieldListener<KeyCodeCombination>> list = new ArrayList<FieldListener<KeyCodeCombination>>();
		list.add(toAdd);
		comboListeners.put(keyCode, list);		 
	}

	public static void addEventHandler(KeyStateEvent toAdd) {
		if (!listeners.contains(toAdd)) {
			listeners.add(toAdd);
		}
	}
	
}
