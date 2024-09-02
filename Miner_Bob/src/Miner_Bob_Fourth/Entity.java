package Miner_Bob_Fourth;

public abstract class Entity {
	private String name;
	
	protected StateMachine<?> stateMachine;
	
	public Entity(String name) {
		this.name = name;
		
		EntityManager.getInstance().registerEntity(this);
	}
	
	public String getName() {
		return name;
	}
	
	public abstract void Update();
	public abstract StateMachine<?> getStateMachine();
	public boolean treatMessage(Message message) {
		return getStateMachine().treatMessage(message);
	}
}
