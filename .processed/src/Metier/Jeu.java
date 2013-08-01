/**
 * @(#) Jeu.java
 */

package Metier;

import java.util.Random;

import javax.microedition.rms.RecordStore;

import Application.Factory;
import Application.JeuMiDLet;
import IHM.JeuCanvas;

public class Jeu {
	private static Jeu singletonInstance;

	private JeuMiDLet midlet;

	private Niveau niveauCourant;

	private int vieJoueur = 3;

	private Vie[] viesHero;

	private long score;

	private JeuCanvas presentation;

	private ModeleHeros heros;

	private boolean termine;

	protected Jeu() {
		Random generator = new Random();
		this.score = 0;
		this.termine = false;
		this.heros = Factory.getInstance().creerModeleHeros();
		this.viesHero = new Vie[vieJoueur];
		for (int i = 0; i < this.viesHero.length; i++) {
			this.viesHero[i] = Factory.getInstance().creerVie("Hero");
		}
		this.niveauCourant = Factory.getInstance().creerNiveau(0,
				60000 + Math.abs((generator.nextInt()) % 30000),
				3 + Math.abs((generator.nextInt()) % 7));
		this.niveauCourant.startNiveau();
		this.presentation = Factory.getInstance().creerJeuCanvas(this);
	}

	public static Jeu getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new Jeu();
		}
		return singletonInstance;
	}

	public static void init(){
		System.gc();
		singletonInstance = null;
	}
	
	
	public void setMidlet(JeuMiDLet midlet) {
		this.midlet = midlet;
	}

	public JeuMiDLet getMidlet() {
		return midlet;
	}

	public boolean isTermine() {
		return termine;
	}

	public void setTermine(boolean termine) {
		this.termine = termine;
	}

	public int getVieJoueur() {
		return vieJoueur;
	}

	public void setVieJoueur(int vieJoueur) {
		this.vieJoueur = vieJoueur;
	}

	public Vie[] getViesHero() {
		return viesHero;
	}

	public void setViesHero(Vie[] vies) {
		this.viesHero = vies;
	}

	public JeuCanvas getPresentation() {
		return presentation;
	}

	public void setPresentation(JeuCanvas presentation) {
		this.presentation = presentation;
	}

	public void setScore(long score) {
		this.score = score;
	}

	public long getScore() {
		return score;
	}

	public void setHeros(ModeleHeros heros) {
		this.heros = heros;
	}

	public ModeleHeros getHeros() {
		return this.heros;
	}

	public Niveau getNiveauCourant() {
		return niveauCourant;
	}

	public void setNiveauCourant(Niveau niveauCourant) {
		this.niveauCourant = niveauCourant;
	}

	public void nextLevel() {
		Random generator = new Random();
		try {
			this.niveauCourant = Factory.getInstance().creerNiveau(
					this.niveauCourant.getNumNiveau() + 1,
					60000 + Math.abs((generator.nextInt()) % 30000),
					3 + Math.abs((generator.nextInt()) % 7));
			this.niveauCourant.startNiveau();
		} catch (Exception e) {
			this.setTermine(true);
			System.out
					.println("il n'y a pas de niveau suivant, le jeu est donc termine");
		}
	}

	public void saveHS() {

		try {
			RecordStore  rs = HighScore.getSingletonInstance().getRs();
			rs = RecordStore.openRecordStore(HighScore.getSingletonInstance().getREC_STORE(), true);
			String appt = "" + this.getScore();
			byte bytes[] = appt.getBytes();
			rs.addRecord(bytes, 0, bytes.length);
			appt = "Player";
			byte bytes2[] = appt.getBytes();
			rs.addRecord(bytes2, 0, bytes2.length);
			
			rs.closeRecordStore();
			
		} catch (Exception e) {
		}
	}
}
