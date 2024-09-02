package Miner_Bob_Fourth;

public class VisitarBanheiro implements State<Bob> {
	
	private static VisitarBanheiro instance = null;
	
	public static VisitarBanheiro getInstance() {
		if(instance == null) {
			instance = new VisitarBanheiro();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		bob.getStateMachine().Update();
	}
	
	
	public void execute(Bob bob) {
		bob.Banheiro();
		bob.getStateMachine().RevertToLastState();
	}
	
	@Override
	public void exit(Bob bob) {
		
	}
	
	@Override
	public boolean atMessageReceived(Bob bob, Message message) {
		return false;
	}
}
