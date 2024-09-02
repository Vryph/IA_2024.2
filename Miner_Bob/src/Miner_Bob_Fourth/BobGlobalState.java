package Miner_Bob_Fourth;

public class BobGlobalState implements State<Bob>{
	
	private static BobGlobalState instance = null;
	
	public static BobGlobalState getInstance() {
		if(instance == null) {
			instance = new BobGlobalState();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		
	}
	
	@Override
	public void execute(Bob bob) {

		int rand = (int)(Math.random() * 10) + 1;
		
		if (rand == 2 && bob.getStateMachine().GetCurrentState() != VisitarBanheiro.getInstance()) {
			bob.getStateMachine().ChangeState(VisitarBanheiro.getInstance());	
		}
		
	}
	
	@Override
	public void exit(Bob bob) {
		
	}
	
	@Override
	public boolean atMessageReceived(Bob bob, Message message) {
		if (message.getStringMessage().compareTo("Trabalho feito!") == 0) {
				bob.setBillyWork(true);
				return true;
			} 
		else return false;
	}
}
