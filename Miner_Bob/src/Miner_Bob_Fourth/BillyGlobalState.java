package Miner_Bob_Fourth;

public class BillyGlobalState implements State<Billy>{

	private static BillyGlobalState instance = null;
	
	public static BillyGlobalState getInstance() {
		if(instance == null) {
			instance = new BillyGlobalState();
		}
		return instance;
	}
	
	@Override
	public void enter(Billy billy) {
	}
	
	@Override
	public void execute(Billy billy) {

	}
	
	@Override
	public void exit(Billy billy) {

	}
	
	public boolean atMessageReceived(Billy billy, Message message) {
		if (message.getStringMessage().compareTo("Vai trabalhar!") == 0) {
				billy.getStateMachine().ChangeState(TrabalhoFalso.getInstance());
				return true;
			} 
			else return false;
		}
	}

