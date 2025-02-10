package games;
/**
 * Classe simulant un lancé de dés
 */
public class Dice {

	private static final int DEFAULT_NB_FACES = 6 ;
	private int nb_faces;
	public int value ; 
	
	public Dice () {
		// Constructeur, 6 faces par défaut
		this(Dice.DEFAULT_NB_FACES);
	}
	
	public Dice (int nb_faces) {
		// Constructeur
		super();
		this.nb_faces = nb_faces;
	}
	 
	public int get_max(){
		// Retourne la valeur maximale
		return this.nb_faces;
	}

	public int launch(){
		// Lanc� de d�, retourne un nombre entre 1 et la valeur maximale
		this.value = new Double(this.nb_faces * Math.random() +1).intValue() ;
		return this.value;
	}

}