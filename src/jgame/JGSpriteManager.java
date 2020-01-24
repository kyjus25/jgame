package jgame;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jgame.generics.*;

public class JGSpriteManager {
//	Field<List<Integer>> list = new Field<>(new ArrayList<Integer>());
//	FieldList<Integer> list = new FieldList<>();
	public FieldList<JGSprite> spriteList = new FieldList<>();
	public FieldList<JGSprite> activeSprites = new FieldList<>();
	public FieldList<JGSprite> cleanupSprites = new FieldList<>();
//    private static List<KeyStateEvent> collisionListener = new ArrayList<KeyStateEvent>();

	public JGSpriteManager() {
		JGSceneManager.activeScene.addEventHandler(scene -> {
			activeSprites.clear();
			spriteList.forEach(sprite -> {
				if (sprite.node.get().getScene() != null) {
					sprite.initialize();
					activeSprites.add(sprite);
				} else {
//					sprite.destroy();
//					activeSprites.remove(sprite);
//					spriteList.remove(sprite);
				}
			});
			
			// System.out.println("layers" +scene.layers);
			// System.out.println("activeSprites" + activeSprites);
			// System.out.println("spriteList" + spriteList);
		});
		JGame.tick.addEventHandler((event) -> {
			cleanup();
			checkCollisions();
			activeSprites.forEach((sprite) -> {
				sprite.update();
			});
			JGame.menuBar.sprites.setText("Active Sprites: " + activeSprites.size());
		});
	}

	public JGSprite getSpriteByUUID(String uuid) {
		List<JGSprite> array = activeSprites.stream().filter(p -> p.uuid.get().equals(uuid)).collect(Collectors.toList());
		if (array.size() > 0) {
			return array.get(0);
		} else {
			return null;
		}
	}

	public List<JGSprite> getSpritesByType(String type) {
		return activeSprites.stream().filter(p -> p.type.get().equals(type)).collect(Collectors.toList());

	}
	
    public void checkCollisions() {
    	List<JGSprite> copy = new ArrayList<>();
    	copy.addAll(activeSprites);
        for (JGSprite spriteA : copy) {
            for (JGSprite spriteB : copy) {
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

    public void deleteSprite(JGSprite sprite) {
		cleanupSprites.add(sprite);
	}

    public void cleanup() {
    	List<JGSprite> copy = new ArrayList<>();
    	copy.addAll(spriteList);
    	copy.forEach(sprite -> {
    		if (JGame.sceneManager.activeScene.get().stackPane.get().getChildren().contains(sprite.node.get().getParent())) {
    			sprite.active.set(true);
    		} else {
    			sprite.active.set(false);
    			activeSprites.remove(sprite);
    		}
    	});

    	if (cleanupSprites.size() > 0) {
			try {
				JGSprite sprite = cleanupSprites.get(0);
				if (sprite != null) {
					System.out.println("DELETING " + sprite.uuid.get() + " - " + sprite.type.get());
					JGSceneManager.activeScene.get().layers.forEach(layer -> {
						layer.removeFromLayer(sprite);
					});
					sprite.active.set(false);
					sprite.removeSpriteFromManager(true);
					cleanupSprites.remove(sprite);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
    }
}
