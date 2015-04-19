package io.zonk.jbomberman.game.client;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpriteManager {
	
	private Map<Integer, Sprite> spriteId;
	private Map<Integer, List<Sprite>> spriteLayer;
	
	
	public SpriteManager() {
		spriteId = new HashMap<Integer, Sprite>();
		spriteLayer = new HashMap<Integer, List<Sprite>>();
	}
	
	public Sprite getById(int id) {
		return spriteId.get(id);
	}
	
	public List<Sprite> getByLayer(int layer) {
		return spriteLayer.get(layer);
	}
	
	public void add(Sprite sprite) {
		spriteId.put(sprite.getId(), sprite);
		if(spriteLayer.get(sprite.getLayer()) == null)
			spriteLayer.put(sprite.getLayer(), new ArrayList<Sprite>());
		spriteLayer.get(sprite.getLayer()).add(sprite);
	}
	
	public Sprite remove(int id) {
		Sprite sprite = spriteId.remove(id);
		for(List<Sprite> list : spriteLayer.values()) {
			if(list.remove(sprite))
				break;
		}
		return sprite;
	}
	
	public Collection<Sprite> getAll() {
		return spriteId.values();
	}
}
