package Miner_Bob_Fourth;

public class SaciarSede implements State<Bob> {
	
	private static SaciarSede instance = null;
	
	public static SaciarSede getInstance() {
		if(instance == null) {
			instance = new SaciarSede();
		}
		return instance;
	}
	
	
	@Override
	public void enter(Bob bob) {
		System.out.println("Bob: Indo para o Bar.");
		bob.getStateMachine().Update();
	}
	
	@Override
	public void execute(Bob bob) {
		bob.Beber();
		if (!bob.isThirstClenched()) execute(bob);
		else bob.getStateMachine().ChangeState(EntrarNaMinaECavarPorPepitas.getInstance());
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println(" - Saindo do Bar");
	}
	
	@Override
	public boolean atMessageReceived(Bob bob, Message message) {
		return false;
	}
}
