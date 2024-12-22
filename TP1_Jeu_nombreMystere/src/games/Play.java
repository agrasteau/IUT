package games;

public class Play {

    public static void main(String[] args) {
        MysteryNumber mystery_level1 = new MysteryNumber(10);
        BestScores mystery_level1_sc = new BestScores(BestScores.E_ORDER_BY.ASC, 10);

        try {
            mystery_level1_sc.load(); // Charger les scores au démarrage
        } catch (Exception e) {
            System.out.println("Impossible de charger les scores : " + e.getMessage());
        }

        boolean bEnd = false;
        while (!bEnd) {
            System.out.println("\n\nMenu :\n");
            System.out.println("1 - Jouer au niveau débutant");
            System.out.println("2 - Afficher les meilleurs scores");
            System.out.println("q - Quitter");
            System.out.print("Votre choix : ");

            char sChoice = Lire.c();

            switch (sChoice) {
                case '1': // Jouer au niveau débutant
                    int score = mystery_level1.play();
                    if (score != MysteryNumber.ABORT_VALUE) {
                        System.out.print("Entrez votre nom : ");
                        String name = Lire.S();
                        mystery_level1_sc.add_score(score, name);
                    }
                    break;
                case '2': // Afficher les meilleurs scores
                    mystery_level1_sc.write();
                    break;
                case 'q': // Quitter
                    bEnd = true;
                    try {
                        mystery_level1_sc.save(); // Sauvegarder les scores avant de quitter
                    } catch (Exception e) {
                        System.out.println("Impossible de sauvegarder les scores : " + e.getMessage());
                    }
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
