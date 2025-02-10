package games;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BestScores {

    private int max_scores;
    public static int DEFAULT_MAX_SCORES = 10;
    private Score[] scores;
    private int nb_scores = 0;
    private E_ORDER_BY order_by;
    private String gameType; // Type de jeu (par ex. "MysteryNumber")
    private static final String DB_NAME = "game_scores_db";
    private static final String COLLECTION_NAME = "best_scores";

    public static enum E_ORDER_BY {
        ASC, DESC
    }

    public BestScores(String gameType, E_ORDER_BY order_by, int max_scores) {
        this.gameType = gameType;
        this.order_by = order_by;
        this.max_scores = max_scores;
        this.scores = new Score[this.max_scores];
    }

    public BestScores(String gameType, E_ORDER_BY order_by) {
        this(gameType, order_by, DEFAULT_MAX_SCORES);
    }

    private boolean is_better(int valueA, int valueB) {
        return this.order_by == E_ORDER_BY.ASC ? valueA < valueB : valueA > valueB;
    }

    private int get_position(int new_score) {
        for (int i = 0; i < this.nb_scores; i++) {
            if (this.is_better(new_score, this.scores[i].value)) {
                return i;
            }
        }
        return this.nb_scores;
    }

    public boolean is_scoring(int new_score) {
        return this.nb_scores < this.max_scores || this.is_better(new_score, this.scores[this.nb_scores - 1].value);
    }

    public void add_score(int value, String who, String when) {
        int pos = this.get_position(value);
        if (pos >= this.max_scores) {
            return;
        }
        for (int i = this.max_scores - 1; i > pos; i--) {
            this.scores[i] = this.scores[i - 1];
        }
        this.scores[pos] = new Score(value, who, when);
        if (this.nb_scores < this.max_scores) {
            this.nb_scores++;
        }
    }

    public void add_score(int value, String who) {
        this.add_score(value, who, new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
    }

    public void add_score(Score score) {
        this.add_score(score.value, score.who, score.when);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Game: " + this.gameType + "\n");
        for (int i = 0; i < this.nb_scores; i++) {
            Score score = this.scores[i];
            sb.append((i + 1)).append(" - ").append(score.value).append(", ").append(score.who).append(", ").append(score.when).append("\n");
        }
        return sb.toString();
    }

    public void write() {
        System.out.println(this.toString());
    }

    public void save() throws Exception {
        try (MongoClient client = new MongoClient("localhost", 27017)) {
            MongoDatabase database = client.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            collection.deleteMany(new Document("gameType", this.gameType)); // Clear previous data for the game type

            for (int i = 0; i < this.nb_scores; i++) {
                Score score = this.scores[i];
                Document doc = new Document("gameType", this.gameType)
                        .append("position", i + 1)
                        .append("value", score.value)
                        .append("who", score.who)
                        .append("when", score.when);
                collection.insertOne(doc);
            }
        }
    }

    public void load() throws Exception {
        try (MongoClient client = new MongoClient("localhost", 27017)) {
            MongoDatabase database = client.getDatabase(DB_NAME);
            MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

            var cursor = collection.find(new Document("gameType", this.gameType)).sort(new Document("position", 1)).iterator();
            this.nb_scores = 0;

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                int value = doc.getInteger("value");
                String who = doc.getString("who");
                String when = doc.getString("when");

                this.scores[this.nb_scores++] = new Score(value, who, when);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // Test for MysteryNumber
        BestScores best_scores = new BestScores("MysteryNumber", E_ORDER_BY.ASC, 5);
        best_scores.write();

        // Save scores
        best_scores.save();
        System.out.println("Scores saved to MongoDB.");

        // Load scores
        BestScores loaded_scores = new BestScores("MysteryNumber", E_ORDER_BY.ASC, 5);
        loaded_scores.load();
        System.out.println("Loaded scores:");
        loaded_scores.write();
    }
}
