package Miner_Bob_Second;


public class Bob {
	int sede, ouro, ouroNoBanco, tamanhoDosBorso, dia, fadiga;
	private State estadoAtual;
	

	public Bob() {
		 sede = 0;
		 ouro = 0;
		 ouroNoBanco = 0;
		 tamanhoDosBorso = 20;
		 dia = 1;
		 fadiga = 0;
		 estadoAtual = new EntrarNaMinaECavarPorPepitas();
	}
	
	
	public void mudarEstado(State novoEstado) {
		
		estadoAtual.exit(this);

		estadoAtual = novoEstado;

		estadoAtual.enter(this);
		
		}

	
}
