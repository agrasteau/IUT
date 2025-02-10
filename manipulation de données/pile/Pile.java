package pile; 

public interface Pile {
		//
		// S�lecteurs
		// Savoir si une pile est vide
		public boolean PileVide();
		// Savoir si une pile est pleine
		public boolean PilePlein();
		// Obtenir la longueur d�une pile
		public int longueur();
		// D�terminer la valeur du sommet d�une pile
		public Object sommet();
		//
		// Empiler une valeur au sommet d�une pile
		public void empiler(Object element);
		// D�piler la valeur au sommet d�une pile
		public Object depiler();
		

}
