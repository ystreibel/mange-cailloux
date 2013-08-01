/**
 * @(#) Niveau.java
 */

package Metier;

import java.util.Timer;
import java.util.TimerTask;

import Application.Factory;

public class Niveau {
	/**
	 * Map contenant les infos sur la map du niveau
	 */
	private Map map;

	/**
	 * Temps limite pour finir le niveau
	 */
	private int tempsLimite;

	/**
	 * Numéro du niveau
	 */
	private int numNiveau;

	/**
	 * Nombre de fois ou les ennemis peuvent renaitre
	 */
	private int nbRennaissance;
	
	/**
	 * Tableau des vies ennemis
	 */
	private Vie[] viesEnnemi;

	/**
	 * Etat du niveau : Fini (true) ou non (false)
	 */
	private boolean levelDone = false;

	/**
	 * Temps de jeu
	 */
	private Timer tempsJeu;
	
	/**
	 * Permet de savoir si les diamants sont alignés
	 */
	private boolean diamantsAlignes;
	
	/**
	 * Constructeur de la classe Niveau
	 * 
	 * @param numNiveau
	 *            qui contient le numéro du niveau
	 * @param tempsLimite
	 *            qui contient le temps limite avant de finir le niveau
	 * @param nbRenaissanceEnnemis
	 *            qui contient le nombre maximum de vies pour les ennemis
	 * @param nbEnnemis
	 *            nombre d'ennemis sur pour le niveau
	 */
	public Niveau(int numNiveau, int tempsLimite, int nbRenaissanceEnnemis) {

		this.numNiveau = numNiveau;
		this.map = Factory.getInstance().creerMap(this.getNumNiveau());
		this.tempsLimite = tempsLimite;
		this.nbRennaissance = nbRenaissanceEnnemis;
		this.viesEnnemi = new Vie[this.nbRennaissance];
		for (int i = 0; i < this.viesEnnemi.length; i++) {
			this.viesEnnemi[i] = Factory.getInstance().creerVie("Ennemi");
		}
		this.diamantsAlignes = false;
		this.levelDone = false;
		this.tempsJeu = new Timer();
		
	}

	/**
	 * Méthode startNiveau qui démarre le niveau
	 * 
	 * @param tempsLimite
	 *            contient en millisecondes le temps au bout duquel le niveau
	 *            prend fin
	 */
	public void startNiveau() {
		this.getTempsJeu().schedule(new TimerTask() {
			public void run() {
				levelTermine();
			}
		}, (long) this.tempsLimite);
	}

	public Map getMap() {
		return map;
	}

	public int getTempsLimite() {
		return tempsLimite;
	}

	public int getNumNiveau() {
		return numNiveau;
	}

	public boolean isDiamantsAlignes() {
		return diamantsAlignes;
	}

	public void setDiamantsAlignes(boolean diamantsAlignes) {
		this.diamantsAlignes = diamantsAlignes;
	}

	public Vie[] getViesEnnemi() {
		return viesEnnemi;
	}

	public void setViesEnnemi(Vie[] viesEnnemi) {
		this.viesEnnemi = viesEnnemi;
	}

	public int getNbRennaissance() {
		return nbRennaissance;
	}

	public void setNbRennaissance(int nbRennaissance) {
		this.nbRennaissance = nbRennaissance;
	}

	public void setLevelDone(boolean levelDone) {
		this.levelDone = levelDone;
	}

	public boolean isLevelDone() {
		return levelDone;
	}
	
	public Timer getTempsJeu() {
		return tempsJeu;
	}

	public void levelTermine() {
		this.setLevelDone(true);
	}
}
