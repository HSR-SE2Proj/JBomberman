package jbomberman.game.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jbomberman.game.GameObjectType;

public class GameObjectManager {
	
	private Map<Integer, GameObject> objectId;
	private Map<GameObjectType, List<GameObject>> objectType;
	
	public GameObjectManager() {
		objectId = new HashMap<Integer, GameObject>();
		objectType = new HashMap<GameObjectType, List<GameObject>>();
	}
	
	public GameObject getById(int id) {
		return objectId.get(id);
	}
	
	public List<GameObject> getByType(GameObjectType... type) {
		List<GameObject> list = new ArrayList<>();
		for(GameObjectType t : type) {
			if(objectType.get(t) != null)
				list.addAll(objectType.get(t));
		}
		return list;
	}
	
	public void add(GameObject object) {
		objectId.put(object.getId(), object);
		if(objectType.get(object.getType()) == null)
			objectType.put(object.getType(), new ArrayList<GameObject>());
		objectType.get(object.getType()).add(object);
	}
	
	public GameObject remove(int id) {
		GameObject object = objectId.remove(id);
		for(List<GameObject> list : objectType.values()) {
			if(list.remove(object))
				break;
		}
		return object;
	}
	
	public Collection<GameObject> getAll() {
		return objectId.values();
	}

}
