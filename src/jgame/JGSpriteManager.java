package jgame;

import java.util.ArrayList;
import java.util.List;

import jgame.generics.*;

public class JGSpriteManager {
//	Field<List<Integer>> list = new Field<>(new ArrayList<Integer>());
	FieldList<Integer> list = new FieldList<>();
	public FieldList<JGSprite> spriteList = new FieldList<>();
//    private static List<KeyStateEvent> collisionListener = new ArrayList<KeyStateEvent>();

	public JGSpriteManager() {
		JGame.tick.addEventHandler((event) -> {
			spriteList.forEach((sprite) -> {
				checkCollisions();
				sprite.update();
			});
		});
//		System.out.println(list);
//		list.addEventHandler((list, changed) -> {
//			System.out.println("HERE " + list);
//		});
//		list.add(1);
//		list.add(2);
	}
	
    public void checkCollisions() {
        for (JGSprite spriteA : spriteList) {
            for (JGSprite spriteB : spriteList) {
                if (spriteA != spriteB && spriteA.intersects(spriteB)) {
                    spriteA.onCollision(spriteA, spriteB);
                    spriteA.onCollision(spriteB);
                }
//                if (!spriteA.getActive()) {
//                	addSpritesToBeRemoved(spriteA);
//                	main.gameengine.Game.getSceneNodes().getChildren().remove(spriteA.getNode());
//                }
            }
        }
    }
}
