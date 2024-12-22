package games;

public class Play {

    public static void main(String[] args) {
        // Objets BestScores pour chaque niveau
        BestScores bestScoresLevel1 = new BestScores("MysteryNumber_Level1", BestScores.E_ORDER_BY.ASC, 10);
        BestScores bestScoresLevel2 = new BestScores("MysteryNumber_Level2", BestScores.E_ORDER_BY.ASC, 10);
        BestScores bestScoresLevel3 = new BestScores("MysteryNumber_Level3", BestScores.E_ORDER_BY.ASC, 10);

        // Charger les scores depuis la base de données au démarrage
        try {
            bestScoresLevel1.load();
            bestScoresLevel2.load();
            bestScoresLevel3.load();
            System.out.println("Scores chargés avec succès depuis la base de données.");
        } catch (Exception e) {
            System.out.println("Impossible de charger les scores : " + e.getMessage());
        }

        boolean bEnd = false;
        while (!bEnd) {
            System.out.println("\n\nMenu :\n");
            System.out.println("1 - Jouer au niveau débutant (1 à 10)");
            System.out.println("2 - Jouer au niveau normal (1 à 100)");
            System.out.println("3 - Jouer au niveau expert (1 à 1000)");
            System.out.println("4 - Afficher les meilleurs scores");
            System.out.println("q - Quitter");
            System.out.print("Votre choix : ");

            char sChoice = Lire.c();

            switch (sChoice) {
                case '1': // Jouer au niveau débutant
                    playGame(10, "Débutant", bestScoresLevel1);
                    break;
                case '2': // Jouer au niveau normal
                    playGame(100, "Normal", bestScoresLevel2);
                    break;
                case '3': // Jouer au niveau expert
                    playGame(1000, "Expert", bestScoresLevel3);
                    break;
                case '4': // Afficher les meilleurs scores
                    displayScores(bestScoresLevel1, bestScoresLevel2, bestScoresLevel3);
                    break;
                case 'q': // Quitter
                    bEnd = true;
                    saveAllScores(bestScoresLevel1, bestScoresLevel2, bestScoresLevel3);
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

    // Méthode pour jouer une partie
    private static void playGame(int maxRange, String levelName, BestScores bestScores) {
        MysteryNumber mysteryNumber = new MysteryNumber(maxRange);
        int score = mysteryNumber.play();

        if (score != MysteryNumber.ABORT_VALUE) {
            System.out.print("Entrez votre nom : ");
            String name = Lire.S();
            bestScores.add_score(score, name);

            // Sauvegarder les scores après la partie
            try {
                bestScores.save();
                System.out.println("Scores sauvegardés avec succès pour le niveau " + levelName + ".");
            } catch (Exception e) {
                System.out.println("Erreur lors de la sauvegarde des scores : " + e.getMessage());
            }
        }
    }

    // Méthode pour afficher tous les meilleurs scores
    private static void displayScores(BestScores level1, BestScores level2, BestScores level3) {
        System.out.println("\n--- Meilleurs Scores Niveau Débutant (1 à 10) ---");
        level1.write();

        System.out.println("\n--- Meilleurs Scores Niveau Normal (1 à 100) ---");
        level2.write();

        System.out.println("\n--- Meilleurs Scores Niveau Expert (1 à 1000) ---");
        level3.write();
    }

    // Méthode pour sauvegarder tous les scores
    private static void saveAllScores(BestScores level1, BestScores level2, BestScores level3) {
        try {
            level1.save();
            level2.save();
            level3.save();
            System.out.println("Scores sauvegardés avant de quitter.");
        } catch (Exception e) {
            System.out.println("Impossible de sauvegarder les scores : " + e.getMessage());
        }
    }
}
