package Miner_Bob_Second;

public class VisitarBancoEDepositarOuro implements State{
	@Override
	public void enter(Bob bob) {
		System.out.println("Indo para o Banco.");
		execute(bob);
	}
	
	@Override
	public void execute(Bob bob) {
		System.out.println("Você guardou " + bob.ouro + " pepitas de ouro no banco.");
		bob.ouroNoBanco += bob.ouro;
		bob.ouro = 0;
		System.out.println("Você agora tem " + bob.ouroNoBanco + " pepitas no banco.");
		if(bob.ouroNoBanco / bob.dia == 40) {
			bob.mudarEstado(new VoltarParaCasaEDormirAteDescansado());
		}
		else {
			bob.mudarEstado(new EntrarNaMinaECavarPorPepitas());
		}
	}
	
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Saindo do Banco.");
	}
}
