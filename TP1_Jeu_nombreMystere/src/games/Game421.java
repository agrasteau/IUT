package games;

import java.util.Random;

public class Game421 {

    private static final int TARGET = 4; // 4-2-1
    private Random random = new Random();

    public int play(int diceFaces) {
        int[] dice = new int[3];
        int rolls = 0;
        boolean[] keep = new boolean[3]; // Tableau pour savoir quels dés conserver

        while (true) {
            rolls++;
            // Lancer les dés
            for (int i = 0; i < 3; i++) {
                if (!keep[i]) {
                    dice[i] = random.nextInt(diceFaces) + 1; // Générer un nombre entre 1 et diceFaces
                }
            }

            // Afficher les résultats des dés
            System.out.println("Lancer " + rolls + ": " + dice[0] + " " + dice[1] + " " + dice[2]);

            // Vérifier si la combinaison 4-2-1 est obtenue
            if (dice[0] == TARGET && dice[1] == 2 && dice[2] == 1) {
                System.out.println("Félicitations ! Vous avez obtenu la combinaison 4-2-1 en " + rolls + " lancers.");
                break;
            }

            // Demander à l'utilisateur quels dés conserver
            System.out.print("Quels dés voulez-vous conserver ? (1-3 pour les dés, 0 pour ne rien conserver) : ");
            String choice = Lire.S();

            for (int i = 0; i < 3; i++) {
                keep[i] = choice.contains(String.valueOf(i + 1));
            }
        }

        return rolls;
    }

    public static void main(String[] args) {
        // Objets BestScores pour chaque niveau
        BestScores bestScoresLevel1 = new BestScores("Game421_Level1", BestScores.E_ORDER_BY.ASC, 10);
        BestScores bestScoresLevel2 = new BestScores("Game421_Level2", BestScores.E_ORDER_BY.ASC, 10);
        BestScores bestScoresLevel3 = new BestScores("Game421_Level3", BestScores.E_ORDER_BY.ASC, 10);

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
            System.out.println("1 - Jouer au niveau Débutant (6 faces)");
            System.out.println("2 - Jouer au niveau Normal (10 faces)");
            System.out.println("3 - Jouer au niveau Expert (15 faces)");
            System.out.println("4 - Afficher les meilleurs scores");
            System.out.println("q - Quitter");
            System.out.print("Votre choix : ");

            char sChoice = Lire.c();

            switch (sChoice) {
                case '1': // Jouer au niveau Débutant
                    playGame(6, "Débutant", bestScoresLevel1);
                    break;
                case '2': // Jouer au niveau Normal
                    playGame(10, "Normal", bestScoresLevel2);
                    break;
                case '3': // Jouer au niveau Expert
                    playGame(15, "Expert", bestScoresLevel3);
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
    private static void playGame(int diceFaces, String levelName, BestScores bestScores) {
        Game421 game421 = new Game421();
        int score = game421.play(diceFaces);

        if (score > 0) {
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
        System.out.println("\n--- Meilleurs Scores Niveau Débutant (6 faces) ---");
        level1.write();

        System.out.println("\n--- Meilleurs Scores Niveau Normal (10 faces) ---");
        level2.write();

        System.out.println("\n--- Meilleurs Scores Niveau Expert (15 faces) ---");
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
