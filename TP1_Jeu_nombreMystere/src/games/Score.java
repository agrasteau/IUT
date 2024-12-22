package games;

public class Score {

	String who ;
	String when ;
	int value;
	
	// Constructeur par défaut, sans argument
	public Score() {
		super();
	}

	/**
	 * @param who
	 * @param when
	 * @param value
	 */
	public Score( int value, String who, String when) {
		super();
		this.who = who;
		this.when = when;
		this.value = value;
	}

	/**
	 */
	public String toString() {
		return this.who + " : " + this.value + " <" + this.when + ">";
	}
}