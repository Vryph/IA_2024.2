package Miner_Bob_Second;

public class EntrarNaMinaECavarPorPepitas implements State{
	@Override
	public void enter(Bob bob) {
		if(bob.dia == 3) {System.out.println("Hoje é terceiro dia, último dia do universo, e o fim do programa.");}
		else {
		System.out.println("Indo para a Mina de Ouro...");	
		execute(bob);}
	}
	
	@Override
	public void execute(Bob bob) {
		if(bob.sede < 15 &&  bob.ouro < bob.tamanhoDosBorso && bob.fadiga < 50) {
			System.out.print("Procurando Pepitas de Ouro - ");
			int hitRange = 100, goldChance = 50, goldHit = (int)(Math.random() * hitRange) + 1;
			//System.out.print(goldHit);
			if(goldHit <= goldChance) { 
				 int goldRange = 4, goldAmmount = (int)(Math.random() * goldRange) + 1;
				 bob.ouro +=  goldAmmount;
				 System.out.println("Bob encontrou " + goldAmmount + " pepita(s) de ouro. - Ouro no Bolso = " + bob.ouro);
				 if (bob.ouro > 20) { System.out.println("Seu bolso está cheio demais.");bob.ouro = 20;}
			}
			else {
					 System.out.println("Bob não encontrou ouro.");
				 }
			
			bob.sede += 1;
			bob.fadiga += 1;
			execute(bob);
		}
		else if (bob.sede >= 15){
			System.out.println("Bob está com muita sede ;-;");
			bob.mudarEstado(new SaciarSede());
		}
		else if (bob.fadiga >= 50) {
			bob.mudarEstado(new VoltarParaCasaEDormirAteDescansado());
		}
		else {
			bob.mudarEstado(new VisitarBancoEDepositarOuro());
		}
		}
	
	
	@Override
	public void exit(Bob bob) {
		System.out.println("Saindo da Mina");
	}
}
