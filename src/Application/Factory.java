/**
 * @(#) Factory.java
 */

package Application;

import javax.microedition.lcdui.Image;

import IHM.HighScoreCanvas;
import IHM.HighScoreFinCanvas;
import IHM.IntroductionCanvas;
import IHM.JeuCanvas;
import IHM.ManagerCanvas;
import IHM.SpriteBrique;
import IHM.SpriteDiamant;
import IHM.SpriteEnnemi;
import IHM.SpriteHeros;
import IHM.SpriteMur;
import IHM.SpriteVie;
import Metier.Brique;
import Metier.Diamant;
import Metier.HighScore;
import Metier.Jeu;
import Metier.Map;
import Metier.ModeleEnnemi;
import Metier.ModeleHeros;
import Metier.Mur;
import Metier.Niveau;
import Metier.Vie;

public class Factory {
	private static Factory singletonInstance;

	protected Factory() {}

	public static Factory getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new Factory();
		}
		return singletonInstance;
	}

	public Niveau creerNiveau(int numNiveau, int tempsLimite, int nbEnnemisBarre) {
		return new Niveau(numNiveau, tempsLimite, nbEnnemisBarre);
	}

	public Map creerMap(int idLevel) {
		return new Map(idLevel);
	}

	public Mur creerMur(String orientation){
		return new Mur(orientation);
	}

	public Diamant creerDiamant() {
		return new Diamant();
	}

	public Brique creerBrique() {
		return new Brique();
	}

	public ModeleHeros creerModeleHeros() {
		return new ModeleHeros();
	}

	public ModeleEnnemi creerModeleEnnemi() {
		return new ModeleEnnemi();
	}

	public JeuCanvas creerJeuCanvas(Jeu controle) {
		return new JeuCanvas(true, controle);
	}

	public SpriteMur creerSpriteMur(Image image, Mur mur) {
		return new SpriteMur(image, mur);
	}

	public SpriteEnnemi creerSpriteEnnemi(Image image, ModeleEnnemi modeleEnnemi) {
		return new SpriteEnnemi(image, modeleEnnemi);
	}

	public SpriteHeros creerSpriteHeros(Image image, ModeleHeros modeleHeros) {
		return new SpriteHeros(image, modeleHeros);
	}

	public SpriteBrique creerSpriteBrique(Image image, Brique brique) {
		return new SpriteBrique(image, brique);
	}

	public SpriteDiamant creerSpriteDiamant(Image image, Diamant diamant) {
		return new SpriteDiamant(image, diamant);
	}

	public IntroductionCanvas creerIntroductioncanvas(JeuMiDLet jeuMIDlet, ManagerCanvas manager) {
		return new IntroductionCanvas(jeuMIDlet, manager);
	}

	public HighScoreCanvas creerHighScoreCanvas(JeuMiDLet jeuMIDlet, ManagerCanvas manager) {
		return new HighScoreCanvas(jeuMIDlet, manager);
	}
	
	public ManagerCanvas creerManagerCanvas(JeuMiDLet jeuMIDlet){
		return new ManagerCanvas(jeuMIDlet);
	}
	
	public AudioPlayer creerAudioPlayer(String type, String soundUrl, int repetition){
		return new AudioPlayer(type, soundUrl, repetition);
	}

	public SpriteVie creerSpriteVie(Image imageVie, Vie vie) {
		return new SpriteVie(imageVie, vie);
	}

	public Vie creerVie(String possesseur) {
		return new Vie(possesseur);
	}

	public HighScoreFinCanvas creerHighScoreFinCanvas(JeuMiDLet jeuMIDlet){
		return new HighScoreFinCanvas(jeuMIDlet);
	}
}
