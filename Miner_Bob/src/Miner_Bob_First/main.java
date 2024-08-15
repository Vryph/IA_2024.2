package Miner_Bob_First;


public class main {
	static int sede, ouro, ouroNoBanco, tamanhoDosBorso = 20, dia = 1, fadiga = 0;
	static String local;

	
	
	public static void main(String[] args) {
		EntrarNaMinaECavarPorPepitas();

	}
	
	public static void EntrarNaMinaECavarPorPepitas() {
		if(dia == 3) {System.out.println("Hoje é terceiro dia, último dia do universo, e o fim do programa.");}
		else if(sede < 15 &&  ouro < tamanhoDosBorso && fadiga < 50) {
			if(local != "Mina de Ouro") {
				local = "Mina de Ouro";
				System.out.println("Indo para a Mina de Ouro...");	
				}
			else {
				System.out.print("Procurando Pepitas de Ouro - ");
				int hitRange = 100, goldChance = 50, goldHit = (int)(Math.random() * hitRange) + 1;
				//System.out.print(goldHit);
				if(goldHit <= goldChance) { 
					 int goldRange = 4, goldAmmount = (int)(Math.random() * goldRange) + 1;
					 ouro +=  goldAmmount;
					 System.out.println("Bob encontrou " + goldAmmount + " pepita(s) de ouro. - Ouro no Bolso = " + ouro);
					 if (ouro > 20) { System.out.println("Seu bolso está cheio demais.");ouro = 20;}
				}
				else {
						 System.out.println("Bob não encontrou ouro.");
					 }
				}
			sede += 1;
			fadiga += 1;
			EntrarNaMinaECavarPorPepitas();
		}
		else if (sede >= 15){
			System.out.println("Bob está com muita sede ;-;");
			SaciarSede();
		}
		else if (fadiga >= 50) {
			VoltarParaCasaEDormirAteDescansado();
		}
		else {
			VisitarBancoEDepositarOuro();
		}
		
	}

	public static void SaciarSede() {
		if (local != "Bar") {
			System.out.println("Indo de " + local + " para o Bar." );
			local = "Bar";
			SaciarSede();
		}
		else {
			if (sede > 0) {
				System.out.println("Bebendo uma...");
				sede -= 5;
				SaciarSede();
			}
			else {
				System.out.println("Saindo do Bar");
				EntrarNaMinaECavarPorPepitas();
			}
		}
	}
	
	public static void VisitarBancoEDepositarOuro() {
		if (local != "Banco") {
			local = "Banco";
			System.out.println("Indo para o Banco.");
			VisitarBancoEDepositarOuro();
		}
		else {
			System.out.println("Você guardou " + ouro + " pepitas de ouro no banco.");
			ouroNoBanco += ouro;
			ouro = 0;
			System.out.println("Você agora tem " + ouroNoBanco + " pepitas no banco.");
			if(ouroNoBanco / dia == 40) {
				VoltarParaCasaEDormirAteDescansado();
			}
			else {
				EntrarNaMinaECavarPorPepitas();
			}
		}
	}
	
	public static void VoltarParaCasaEDormirAteDescansado() {
		if (local != "Casa") {
			local = "Casa";
			System.out.println("Bob está voltando para casa.");
			VoltarParaCasaEDormirAteDescansado();
		}
		else {
			if (fadiga > 0) {
				fadiga = 0;
				dia += 1;
				System.out.println("Descansando...");
				VoltarParaCasaEDormirAteDescansado();
			}
			else {
				System.out.println("Bob está descansado.");
				EntrarNaMinaECavarPorPepitas();
			}
		}
	}
}
