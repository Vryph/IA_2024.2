package Miner_Bob_Second;

public class SaciarSede implements State {
	@Override
	public void enter(Bob bob) {
		System.out.println("Indo para o Bar.");
		execute(bob);
	}
	
	@Override
	public void execute(Bob bob) {
		if (bob.sede > 0) {
			System.out.println("Bebendo uma...");
			bob.sede -= 5;
			execute(bob);
		}
		else {
			bob.mudarEstado(new EntrarNaMinaECavarPorPepitas());
		}
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Saindo do Bar");
	}
}
