package TD4;

import TD2.Clavier;

public class ex5 {

	public static void main(String[] args) {
		String a;
		int size;
		while (true) {
		System.out.println("saisir un mot");	
		a = Clavier.lireLigne();
		size = a.length();
		System.out.println(size + " caract�res");	
	}
	}
}
//r�cup�ration da la longueur d'une chaine de caract�re