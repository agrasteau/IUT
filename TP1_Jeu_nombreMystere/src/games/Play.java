package games;


public class Play {

	public static void main(String[] args) {

		// Nombre mystère - jeux et meilleurs scores
		MysteryNumber mystery_level1 = new MysteryNumber(10);
		BestScores mystery_level1_sc = new BestScores(BestScores.E_ORDER_BY.ASC, 10);

		boolean bEnd = false;
		while (!bEnd) {

			System.out.println("\n\nMenu :\n");
			System.out.println("q-Quitter");
			System.out.print("Votre choix : ");

			// Récupération saisie utilisateur
			char sChoice = Lire.c();

			// Analyse saisie utilisateur
			switch (sChoice) {

			case 'q': // Choix 'q' : Fin
				bEnd = true;
				break;
			default:
				System.out.println("Saisie incorrecte !!!");
				break;
			}
			if (!bEnd) {
				System.out.println("<Enter> pour revenir au menu principal");
				Lire.c();
			}
		}
		System.out.println("Bye !");
	}

}
