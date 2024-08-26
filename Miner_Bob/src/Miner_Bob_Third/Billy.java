package Miner_Bob_Third;

public class Billy {
	
	private State<Billy> estadoAtual;
	int iteration, maxIterations;

		public Billy() {
			iteration = 0;
			maxIterations = 60;
			estadoAtual = ObservarOTempo.getInstance();
		}
		
		
		public void mudarEstado(State<Billy> novoEstado) {
			
			estadoAtual.exit(this);

			estadoAtual = novoEstado;

			estadoAtual.enter(this);
			
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
		
		public boolean shouldChangeStates(int percentStateChangeChance) {
			int nextStateHit = (int)(Math.random() * 100) + 1;
			if (nextStateHit > percentStateChangeChance) return true;
			else return false;
		}

		
	}

