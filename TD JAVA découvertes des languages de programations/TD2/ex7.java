package TD2;
public class ex7 {
	public static void main(String[] args) {
		while (true) {
		int a, b;
		a=Clavier.lireInt();
		b=Clavier.lireInt();
		if (a>0 && b<0 || a<0 && b>0) {
			System.out.println("le produit est n�gatif");
		}
		else {
			System.out.println("le produit est positif");
		}
		}
	}

}