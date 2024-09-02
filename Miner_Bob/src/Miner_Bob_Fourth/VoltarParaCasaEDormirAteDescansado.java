package Miner_Bob_Fourth;

public class VoltarParaCasaEDormirAteDescansado implements State<Bob>{
	
	private static VoltarParaCasaEDormirAteDescansado instance = null;
	
	public static VoltarParaCasaEDormirAteDescansado getInstance() {
		if(instance == null) {
			instance = new VoltarParaCasaEDormirAteDescansado();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		Entity billy = EntityManager.getInstance().getEntity("Billy");
		bob.setBillyWork(false);
		MessageDispatcher.getInstance().dispatchMessage(bob, billy, "Vai trabalhar!", null);
		System.out.println("Bob: Bob está voltando para casa.");
		bob.getStateMachine().Update();
	}
	
	@Override
	public void execute(Bob bob) {
		if(!bob.isTired() && bob.didBillyWork()) {
			bob.getStateMachine().ChangeState(EntrarNaMinaECavarPorPepitas.getInstance());
		}
		else if (!bob.isTired()) {
			System.out.println("Estou descansado! Esperando meu irmão preguiçoso terminar seu trabalho...");
		}
		else bob.Descansar();
		
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println("É um novo dia e bob está saindo de casa.");
	}
	
	@Override
	public boolean atMessageReceived(Bob bob, Message message) {
		return false;
	}
}
