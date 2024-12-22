package games;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BestScores {

    // Nb scores max
    private int max_scores;
    // Nb scores max par défaut
    public static int DEFAULT_MAX_SCORES = 10;
    // Les scores
    Score[] scores;
    // Nb scores courant
    int nb_scores = 0;
    // Ordre des scores : croissant, décroissant
    private E_ORDER_BY order_by;

    public static enum E_ORDER_BY {
        ASC, DESC
    }

    // Constructeur par défaut, sans argument
    public BestScores() {
        super();
    }

    // Constructeur avec ordre
    public BestScores(E_ORDER_BY order_by) {
        this(order_by, BestScores.DEFAULT_MAX_SCORES);
    }

    // Constructeur avec ordre et taille maximale
    public BestScores(E_ORDER_BY order_by, int max_scores) {
        super();
        this.order_by = order_by;
        this.max_scores = max_scores;
        this.scores = new Score[this.max_scores];
        this.nb_scores = 0;
    }

    // Compare deux scores A et B
    private boolean is_better(int valueA, int valueB) {
        if (this.order_by == BestScores.E_ORDER_BY.ASC && valueA < valueB) {
            return true;
        }
        if (this.order_by == BestScores.E_ORDER_BY.DESC && valueA > valueB) {
            return true;
        }
        return false;
    }

    // Position du score dans la liste
    private int get_position(int new_score) {
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            if (this.is_better(new_score, score.value)) {
                return i;
            }
        }
        return this.nb_scores;
    }

    // Définit si un score fait partie des meilleurs scores
    public boolean is_scoring(int new_score) {
        return this.nb_scores < this.max_scores || this.is_better(new_score, this.scores[this.nb_scores - 1].value);
    }

    // Ajout d'un score
    public void add_score(int value, String who, String when) {
        int pos = this.get_position(value);

        // Si le score n'est pas dans les meilleurs, ignorer
        if (pos >= this.max_scores) {
            return;
        }

        // Décaler les scores pour faire de la place
        for (int i = this.max_scores - 1; i > pos; i--) {
            this.scores[i] = this.scores[i - 1];
        }

        // Insérer le score à la position trouvée
        this.scores[pos] = new Score(value, who, when);

        // Incrémenter le compteur si la liste n'est pas pleine
        if (this.nb_scores < this.max_scores) {
            this.nb_scores++;
        }
    }

    // Ajout d'un score avec date actuelle
    public void add_score(int value, String who) {
        this.add_score(value, who, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    // Ajout d'un score à partir d'un objet Score
    public void add_score(Score score) {
        this.add_score(score.value, score.who, score.when);
    }

    // Conversion String pour affichage
    public String toString() {
        String s = "";
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            s += (i + 1) + " - " + score.value + ", " + score.who + ", " + score.when + "\n";
        }
        return s;
    }

    // Affichage des scores sur console
    public void write() {
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            System.out.println((i + 1) + " - " + score.value + ", " + score.who + ", " + score.when);
        }
    }

    // Chargement des scores depuis la sauvegarde
    public void load() throws Exception {
        throw new Exception("Not yet implemented !!! A vous de compléter, y compris la signature / les paramètres selon vos besoins !!!");
    }

    // Sauvegarde des scores
    public void save() throws Exception {
        throw new Exception("Not yet implemented !!! A vous de compléter, y compris la signature / les paramètres selon vos besoins !!!");
    }

    public static void main(String[] args) throws Exception {
        /* Unit tests */

        // Init d'un BestScores de 5 scores, dans l'ordre croissant
        BestScores best_scores = new BestScores(BestScores.E_ORDER_BY.ASC, 5);

        // Ajout d'un score
        best_scores.add_score(12, "A");
        best_scores.add_score(15, "B");

        // Teste si un score peut rentrer dans les meilleurs scores
        System.out.println(best_scores.is_scoring(16)); // true
        System.out.println(best_scores.is_scoring(14)); // true
        System.out.println(best_scores.is_scoring(11)); // true

        // Ajout de scores
        best_scores.add_score(21, "C");
        best_scores.add_score(14, "D");
        best_scores.add_score(33, "E");

        // Affichage
        best_scores.write();
        System.out.println(best_scores);

        // Sauvegarde XML (non implémentée)
        try {
            best_scores.save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Chargement XML (non implémenté)
        try {
            best_scores.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
