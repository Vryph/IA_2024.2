package Miner_Bob_Fourth;

public class ObservarOTempo implements State<Billy> {
	
	private static ObservarOTempo instance = null;
	private int percentStateChangeChance = 25;
	
	
	public static ObservarOTempo getInstance() {
		if(instance == null) {
			instance = new ObservarOTempo();
		}
		return instance;
	}
	
	@Override
	public void enter(Billy billy) {
		execute(billy);
	}
	
	@Override
	public void execute(Billy billy) {
		billy.ObservarTempo();
		if (billy.shouldEndIterations()) System.out.println("Billy: Fim das Iterações");
		else if(billy.shouldChangeStates(percentStateChangeChance)) billy.getStateMachine().ChangeState(CaminharPelaFazenda.getInstance());
		else execute(billy);
		
	}
	
	@Override
	public void exit(Billy billy) {

	}
	
	@Override
	public boolean atMessageReceived(Billy billy, Message message) {
		return false;
	}
	
}
