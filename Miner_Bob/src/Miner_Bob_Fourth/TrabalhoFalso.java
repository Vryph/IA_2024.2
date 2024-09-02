package Miner_Bob_Fourth;;

public class TrabalhoFalso implements State<Billy> {

	
	private static TrabalhoFalso instance = null;
	private int workDoneChance = 10;
	
	public static TrabalhoFalso getInstance() {
		if(instance == null) {
			instance = new TrabalhoFalso();
		}
		return instance;
	}
	
	@Override
	public void enter(Billy billy) {
		billy.getStateMachine().Update();
	}
	
	
	public void execute(Billy billy) {
		billy.FingirTrabalho();
		if( billy.shouldChangeStates(workDoneChance)) billy.getStateMachine().RevertToLastState();
		else execute(billy);
	}
	
	@Override
	public void exit(Billy billy) {
		MessageDispatcher.getInstance().dispatchMessage(billy, EntityManager.getInstance().getEntity("Bob"), "Trabalho feito!", null);
	}
	
	public boolean atMessageReceived(Billy billy, Message message) {
		return true;
	}
}
