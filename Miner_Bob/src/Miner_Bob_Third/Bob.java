package Miner_Bob_Third;


public class Bob {
	int sede, ouro, ouroNoBanco, tamanhoDosBorso, dia, fadiga, ouroMaxDiario;
	private State<Bob> estadoAtual;
	

	public Bob() {
		 sede = 0;
		 ouro = 0;
		 ouroNoBanco = 0;
		 ouroMaxDiario = 60;
		 tamanhoDosBorso = 20;
		 dia = 1;
		 fadiga = 0;
		 estadoAtual = EntrarNaMinaECavarPorPepitas.getInstance();
	}
	
	
	public void mudarEstado(State<Bob> novoEstado) {
		
		estadoAtual.exit(this);

		estadoAtual = novoEstado;

		estadoAtual.enter(this);
		
		}
	
	public boolean shouldEndIterations() {
		if (dia >= 3) {System.out.println("Bob: Hoje é terceiro dia, último dia do universo, e o fim do programa."); return true;}
		else return false;
	}
	
	public boolean isThirsty() {
		if (sede >= 15) {
			System.out.println("Bob: Bob está com muita sede ;-;");
			return true;
		}
		else return false;
	}
	
	public boolean isThirstClenched() {
		if (sede <= 0) return true;
		else {System.out.println(" - Ainda estou com sede..."); return false;}
	}
	
	public boolean isTired() {
		if (fadiga >= 50) return true;
		else return false;
	}
	
	public boolean isSatisfiedForTheDay() {
		if (ouroNoBanco / dia == ouroMaxDiario) return true;
		else return false;
	}
	
	public boolean arePocketsFull() {
		if (ouro >= tamanhoDosBorso) return true;
		else return false;
	}
	
	public void Minerar() {
		
		System.out.print("Bob: Procurando Pepitas de Ouro - ");
		int hitRange = 100, goldChance = 50, goldHit = (int)(Math.random() * hitRange) + 1;
		//System.out.print(goldHit); - Randomness Debug
		
		if(goldHit <= goldChance) { 
				int goldRange = 4, goldAmmount = (int)(Math.random() * goldRange) + 1;
				ouro +=  goldAmmount;
				System.out.println("Bob: Bob encontrou " + goldAmmount + " pepita(s) de ouro. - Ouro no Bolso = " + ouro);
				if (ouro > 20) { System.out.println("Bob: O bolso do Bob está cheio demais.  - Ouro no Bolso = " + tamanhoDosBorso); ouro = 20;}
		}
		else {
					System.out.println("Bob: Bob não encontrou ouro.");
			 }
		
		sede += 1;
		fadiga += 1;
	}
	
	public void Beber() {
		System.out.print("Bob: Bebendo uma...");
		sede -= 5;
	}
	
	public void GuardarOuro() {
		System.out.print("Bob: Bob guardou " + ouro + " pepitas de ouro no banco.");
		ouroNoBanco += ouro;
		ouro = 0;
		System.out.println(" - Pepitas no Banco:  " + ouroNoBanco);
	}
	
	public void Descansar() {
		System.out.println("Bob: Descansando...");
		fadiga = 0;
		dia += 1;
		System.out.println("Bob: Bob está descansado.");
		System.out.print("Bob: DIA: " + dia + " - ");
	}
	
}
