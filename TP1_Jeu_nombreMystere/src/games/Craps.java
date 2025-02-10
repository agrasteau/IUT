package games;

import java.util.Random;

public class Craps {

    private Random random = new Random();

    // Méthode pour jouer une partie de Craps
    public int play() {
        int rollCount = 0;
        int point = 0; // Le point établi, initialement 0

        // Phase 1 : Premier lancer
        int firstRoll = rollDice();
        rollCount++;
        System.out.println("Premier lancer : " + firstRoll);

        // Vérifier si le joueur a gagné ou perdu immédiatement
        if (firstRoll == 7 || firstRoll == 11) {
            System.out.println("Félicitations ! Vous avez gagné sur le premier lancer !");
            return rollCount;
        } else if (firstRoll == 2 || firstRoll == 3 || firstRoll == 12) {
            System.out.println("Dommage, vous avez fait un Craps. Vous avez perdu.");
            return rollCount;
        } else {
            // Phase 2 : Un point est établi
            point = firstRoll;
            System.out.println("Vous avez établi un point avec le score " + point + ".");
            System.out.println("Essayez de refaire ce point avant de lancer un 7.");

            // Phase 3 : Lancer jusqu'à obtenir le point ou un 7
            while (true) {
                int roll = rollDice();
                rollCount++;
                System.out.println("Lancer " + rollCount + ": " + roll);

                // Vérifier si le joueur a gagné ou perdu
                if (roll == point) {
                    System.out.println("Félicitations ! Vous avez gagné en faisant " + point + " à nouveau.");
                    return rollCount;
                } else if (roll == 7) {
                    System.out.println("Dommage, vous avez perdu en faisant un 7.");
                    return rollCount;
                }
            }
        }
    }

    // Méthode pour lancer les dés
    private int rollDice() {
        // Lancer 2 dés à 6 faces
        int die1 = random.nextInt(6) + 1;
        int die2 = random.nextInt(6) + 1;
        return die1 + die2; // Retourner la somme des deux dés
    }

    public static void main(String[] args) {
        BestScores bestScores = new BestScores("Craps", BestScores.E_ORDER_BY.ASC, 10);

        // Charger les scores depuis la base de données au démarrage
        try {
            bestScores.load();
            System.out.println("Scores chargés avec succès depuis la base de données.");
        } catch (Exception e) {
            System.out.println("Impossible de charger les scores : " + e.getMessage());
        }

        boolean bEnd = false;
        while (!bEnd) {
            System.out.println("\n\nMenu :\n");
            System.out.println("1 - Jouer au Craps");
            System.out.println("2 - Afficher les meilleurs scores");
            System.out.println("q - Quitter");
            System.out.print("Votre choix : ");

            char sChoice = Lire.c();

            switch (sChoice) {
                case '1': // Jouer au Craps
                    playGame(bestScores);
                    break;
                case '2': // Afficher les meilleurs scores
                    displayScores(bestScores);
                    break;
                case 'q': // Quitter
                    bEnd = true;
                    saveScores(bestScores);
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
    private static void playGame(BestScores bestScores) {
        Craps craps = new Craps();
        int score = craps.play();

        if (score > 0) {
            System.out.print("Entrez votre nom : ");
            String name = Lire.S();
            bestScores.add_score(score, name);

            // Sauvegarder les scores après la partie
            try {
                bestScores.save();
                System.out.println("Scores sauvegardés avec succès.");
            } catch (Exception e) {
                System.out.println("Erreur lors de la sauvegarde des scores : " + e.getMessage());
            }
        }
    }

    // Méthode pour afficher tous les meilleurs scores
    private static void displayScores(BestScores bestScores) {
        System.out.println("\n--- Meilleurs Scores ---");
        bestScores.write();
    }

    // Méthode pour sauvegarder les scores
    private static void saveScores(BestScores bestScores) {
        try {
            bestScores.save();
            System.out.println("Scores sauvegardés avant de quitter.");
        } catch (Exception e) {
            System.out.println("Impossible de sauvegarder les scores : " + e.getMessage());
        }
    }
}
