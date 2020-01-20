package jgame.generics;

import javafx.scene.input.KeyCode;

public interface KeyStateEvent {
    void changed(KeyCode key , boolean isPressed);
}
