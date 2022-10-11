package projet_Zeldiablo.classes.entites;

/**
 * Represente une entite dans le jeu
 */
public abstract class Entite {

	/**
	 * Coordonnees de l'entite et ancienne coordonnees
	 */
	private int x, y;

	/**
	 * Constructeur de l'interface Entite
	 * @param px coordonnées en x
	 * @param py coordonnées en y 
	 */
	public Entite(int px, py) {
		this.x = px;
		this.y = py;
	}

	/**
	 * Renvoit les coordonnees en x de l'entite
	 * 
	 * @return l'attribut x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Renvoit les coordonnees en y de l'entite
	 * 
	 * @return l'attribut y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Definit les coordonnees en x de l'entite
	 * 
	 * @param nx coordonnee en x
	 */
	public void setX(int nx) {
		this.x = nx;
	}

	/**
	 * Definit les coordonnees en y de l'entite
	 * 
	 * @param ny coordonnee en y
	 */
	public void setY(int ny) {
		this.y = ny;
	}

	/**
	 * Modifie les coordonnees de l'entite en tenant des comptes des parametres
	 * 
	 * @param px deplacement en x
	 * @param py deplacement en y
	 */
	public void seDeplacer(int px, int py) {
		this.x += px;
		this.y += py;
	}
}
