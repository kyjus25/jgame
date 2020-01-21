package jgame.generics;

import jgame.JGPhysics;
import jgame.JGSprite;

public interface PhysicsEvent {
	void changed(JGPhysics physics, JGSprite sprite);
}
