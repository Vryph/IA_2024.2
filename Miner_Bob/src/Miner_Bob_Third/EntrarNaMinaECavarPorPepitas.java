package Miner_Bob_Third;

public class EntrarNaMinaECavarPorPepitas implements State<Bob>{
	
	private static EntrarNaMinaECavarPorPepitas instance = null;
	
	public static EntrarNaMinaECavarPorPepitas getInstance() {
		if(instance == null) {
			instance = new EntrarNaMinaECavarPorPepitas();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		if(!bob.shouldEndIterations()){
		System.out.println("Bob: Indo para a Mina de Ouro...");	
		execute(bob); }
	}
	
	@Override
	public void execute(Bob bob) {
		if (bob.isThirsty()) {
			bob.mudarEstado(SaciarSede.getInstance());
		}
		else if (bob.isTired()) bob.mudarEstado(VoltarParaCasaEDormirAteDescansado.getInstance());
		else if (bob.arePocketsFull()) bob.mudarEstado(VisitarBancoEDepositarOuro.getInstance());
		else {
			bob.Minerar();
			execute(bob);
		}
	}
	
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Bob: Saindo da Mina");
	}
}
