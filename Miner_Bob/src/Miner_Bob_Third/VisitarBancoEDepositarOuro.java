package Miner_Bob_Third;

public class VisitarBancoEDepositarOuro implements State<Bob>{
	
	private static VisitarBancoEDepositarOuro instance = null;
	
	public static VisitarBancoEDepositarOuro getInstance() {
		if(instance == null) {
			instance = new VisitarBancoEDepositarOuro();
		}
		return instance;
	}
	
	@Override
	public void enter(Bob bob) {
		System.out.println("Bob: Indo para o Banco.");
		execute(bob);
	}
	
	@Override
	public void execute(Bob bob) {
		bob.GuardarOuro();
		
		if(bob.isThirsty()) bob.mudarEstado(SaciarSede.getInstance());
		else if (bob.isTired() || bob.isSatisfiedForTheDay()) bob.mudarEstado(VoltarParaCasaEDormirAteDescansado.getInstance());
		else bob.mudarEstado(EntrarNaMinaECavarPorPepitas.getInstance());
	}
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Bob: Saindo do Banco.");
	}
}
