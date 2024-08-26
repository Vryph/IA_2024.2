package Miner_Bob_Third;


public class main3 {


	
	
	public static void main(String[] args) {
		Bob bob = new Bob();
		Billy billy = new Billy();
		bob.mudarEstado(EntrarNaMinaECavarPorPepitas.getInstance());
		billy.mudarEstado(ObservarOTempo.getInstance());
		
	}
}
	
	