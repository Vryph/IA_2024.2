package Miner_Bob_Fourth;

public class EntrarNaMinaECavarPorPepitas implements State<Bob>{
	
	private static EntrarNaMinaECavarPorPepitas instance = null;
	
	public static EntrarNaMinaECavarPorPepitas getInstance() {
		if(instance == null) {
			instance = new EntrarNaMinaECavarPorPepitas();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		if(!bob.shouldEndIterations()) {
			System.out.println("Bob: Indo para a Mina de Ouro...");
			bob.getStateMachine().Update();
		}
	}
	
	@Override
	public void execute(Bob bob) {
		if (bob.isThirsty()) {
			bob.getStateMachine().ChangeState(SaciarSede.getInstance());
		}
		else if (bob.isTired()) bob.getStateMachine().ChangeState(VoltarParaCasaEDormirAteDescansado.getInstance());
		else if (bob.arePocketsFull()) bob.getStateMachine().ChangeState(VisitarBancoEDepositarOuro.getInstance());
		else if(!bob.shouldEndIterations()){
			bob.Minerar();
			bob.getStateMachine().Update();
		}
	}
	
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Bob: Saindo da Mina");
	}
	
	@Override
	public boolean atMessageReceived(Bob bob, Message message) {
		return false;
	}
}
