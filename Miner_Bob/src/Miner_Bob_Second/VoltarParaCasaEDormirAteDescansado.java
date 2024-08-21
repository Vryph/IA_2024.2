package Miner_Bob_Second;

public class VoltarParaCasaEDormirAteDescansado implements State{
	@Override
	public void enter(Bob bob) {
		System.out.println("Bob está voltando para casa.");
		execute(bob);
	}
	
	@Override
	public void execute(Bob bob) {
		if (bob.fadiga > 0) {
			bob.fadiga = 0;
			System.out.println("Descansando...");
			execute(bob);
		}
		else {
			bob.dia += 1;
			System.out.println("Bob está descansado.");
			bob.mudarEstado(new EntrarNaMinaECavarPorPepitas());
		}
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println("É um novo dia e bob está saindo de casa.");
	}
}
