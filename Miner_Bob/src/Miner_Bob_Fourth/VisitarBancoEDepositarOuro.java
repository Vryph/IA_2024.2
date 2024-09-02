package Miner_Bob_Fourth;

public class VisitarBancoEDepositarOuro implements State<Bob>{
	
	private static VisitarBancoEDepositarOuro instance = null;
	
	public static VisitarBancoEDepositarOuro getInstance() {
		if(instance == null) {
			instance = new VisitarBancoEDepositarOuro();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		System.out.println("Bob: Indo para o Banco.");
		bob.getStateMachine().Update();
	}
	
	@Override
	public void execute(Bob bob) {
		bob.GuardarOuro();
		
		if(bob.isThirsty()) bob.getStateMachine().ChangeState(SaciarSede.getInstance());
		else if (bob.isTired() || bob.isSatisfiedForTheDay()) bob.getStateMachine().ChangeState(VoltarParaCasaEDormirAteDescansado.getInstance());
		else bob.getStateMachine().ChangeState(EntrarNaMinaECavarPorPepitas.getInstance());
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Bob: Saindo do Banco.");
	}
	
	@Override
	public boolean atMessageReceived(Bob bob, Message message) {
		return false;
	}
}
