package Miner_Bob_Fourth;
import java.util.HashMap;

public class EntityManager {

	private static EntityManager instance = null;
	
	public static EntityManager getInstance() {
		if(instance == null) {
			instance = new EntityManager();
		}
		return instance;
	}
	
	private HashMap<String, Entity> map;
	
	private EntityManager() {
		map = new HashMap<String, Entity>();
	}
	
	public void registerEntity(Entity entity) {
		map.put(entity.getName(), entity);
	}
	
	public Entity getEntity(String entityName) {
		Entity entity = map.get(entityName);
		return entity;
	}
	
}
