package Miner_Bob_Fourth;

public class CaminharPelaFazenda implements State<Billy> {
	private int percentStateChangeChance = 75;


	private static CaminharPelaFazenda instance = null;
	
	public static CaminharPelaFazenda getInstance() {
		if(instance == null) {
			instance = new CaminharPelaFazenda();
		}
		return instance;
	}
	
	@Override
	public void enter(Billy billy) {
		execute(billy);
	}
	
	@Override
	public void execute(Billy billy) {
		billy.CaminharPelaFazenda();
		if(billy.shouldEndIterations()) System.out.println("Billy: Fim das Iterações");
		else if (billy.shouldChangeStates(percentStateChangeChance)) billy.getStateMachine().ChangeState(ObservarOTempo.getInstance());
		else billy.Update();
	}
	
	@Override
	public void exit(Billy billy) {

	}
	
	@Override
	public boolean atMessageReceived(Billy billy, Message message) {
		return false;
	}
}

