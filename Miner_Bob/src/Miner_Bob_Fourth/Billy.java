package Miner_Bob_Fourth;

public class Billy extends Entity {
	
	private StateMachine<Billy> stateMachine;
	int iteration, maxIterations;

		public Billy() {
			super ("Billy");
			iteration = 0;
			maxIterations = 60;
			stateMachine = new StateMachine<Billy>(this);
			stateMachine.SetCurrentState(ObservarOTempo.getInstance());
			stateMachine.SetGlobalState(BillyGlobalState.getInstance());
		}
		
		public boolean shouldEndIterations() {
			if (iteration >= maxIterations) return true;
			else return false;
		}
		
		public void ObservarTempo() {
			iteration += 1;
			System.out.println("Billy: Observando o Tempo (iteração: " + iteration + ")...");

		}
		
		public void CaminharPelaFazenda() {
			iteration += 1;
			System.out.println("Billy: Caminhando pela fazenda (iteração: " + iteration + ")...");
		}
		
		public void FingirTrabalho() {
			iteration += 1;
			System.out.println("Billy: Fingindo que estou trabalhando... (iteração: " + iteration + ")...");
		}
		
		public boolean shouldChangeStates(int percentStateChangeChance) {
			int nextStateHit = (int)(Math.random() * 100) + 1;
			if (nextStateHit <= percentStateChangeChance) return true;
			else return false;
		}
		
		@Override
		public void Update() {
			stateMachine.Update();
		}
		
		public StateMachine<Billy> getStateMachine(){
			return stateMachine;
		}

		
	}

