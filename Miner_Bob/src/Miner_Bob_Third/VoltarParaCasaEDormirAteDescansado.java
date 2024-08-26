package Miner_Bob_Third;

public class VoltarParaCasaEDormirAteDescansado implements State<Bob>{
	
	private static VoltarParaCasaEDormirAteDescansado instance = null;
	
	public static VoltarParaCasaEDormirAteDescansado getInstance() {
		if(instance == null) {
			instance = new VoltarParaCasaEDormirAteDescansado();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		System.out.println("Bob: Bob está voltando para casa.");
		execute(bob);
	}
	
	@Override
	public void execute(Bob bob) {
		bob.Descansar();
		bob.mudarEstado(EntrarNaMinaECavarPorPepitas.getInstance());
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println("É um novo dia e bob está saindo de casa.");
	}
}
