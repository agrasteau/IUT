package TD2;

public class ex3 {

	public static void main(String[] args) {
		int n; 
		
		while (true) {
		n=Clavier.lireInt();
		if (n<10 && n>=0) {
			System.out.println("insufisant en dessous de 10");	
		}
		else if (n<12) {
			System.out.println("passable de 10 � 12");	
		}
		else if (n<14){
			System.out.println("assez bien de 12 � 14");
		}
		else if (n<16) {
			System.out.println("bien de 14 � 16");	

		}
		else if (n<20) {
			System.out.println("tr�s bien de 16 � 20");	
		}	
		else 
			System.out.println("erreur note non comprise entre 0 et 20");	
	}
	
		
	}
}
// d�terminer la mention d'un utilisateur en fonction de sa note	
