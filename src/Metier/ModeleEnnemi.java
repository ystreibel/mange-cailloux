/**
 * @(#) ModeleEnnemi.java
 */

package Metier;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Image;

import Application.Factory;
import IHM.SpriteEnnemi;

public class ModeleEnnemi extends Modele {
	/**
	 * Booléen pour savoir si l'ennemi est endormi ou non
	 */
	private boolean actif = true;

	/**
	 * Présentation du srpite ennemi
	 */
	private SpriteEnnemi presentation;

	/**
	 * Tableau d'images de face de l'ennemi
	 */
	private Image[] imageFace;

	/**
	 * Tableau d'images de derrière de l'ennemi
	 */
	private Image[] imageBack;

	/**
	 * Tableau d'images de gauche de l'ennemi
	 */
	private Image[] imageLeft;

	/**
	 * Tableau d'images de droite de l'ennemi
	 */
	private Image[] imageRight;

	/**
	 * Tableau d'images de l'ennemi quand il dort
	 */
	private Image[] imageSleep;

	private String direction = "D";
	
	/**
	 * Constructeur de la classe ModeleEnnemi
	 */
	public ModeleEnnemi() {
		try {
			imageFace = new Image[3];
			imageBack = new Image[3];
			imageLeft = new Image[3];
			imageRight = new Image[3];
			imageSleep = new Image[3];

			for (int i = 0; i < 3; i++) {
				imageFace[i] = Image.createImage("/images/ennemi/face" + i+ ".png");
				imageBack[i] = Image.createImage("/images/ennemi/back" + i+ ".png");
				imageLeft[i] = Image.createImage("/images/ennemi/left" + i+ ".png");
				imageRight[i] = Image.createImage("/images/ennemi/right" + i+ ".png");
				imageSleep[i] = Image.createImage("/images/ennemi/sleep" + i+ ".png");
			}
			presentation = Factory.getInstance().creerSpriteEnnemi(imageFace[0], this);
		} catch (IOException e) {
		}
	}

	public SpriteEnnemi getPresentation() {
		return presentation;
	}

	public void setPresentation(SpriteEnnemi presentation) {
		this.presentation = presentation;
	}

	public void setActif(boolean actif) {
		this.actif = actif;
	}

	public boolean isActif() {
		return actif;
	}

	public void reveille() {
		//animation du reveille
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
		this.setActif(true);
	}

	public void dormir() {
		//animation dormir
		this.setActif(false);
		//l'ennemi se reveille automatiquement au bout de 5 secondes
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			public void run() {
				reveille();
			}
		}, 5000);
		//image quand il dort
		this.presentation.setImage(imageSleep[0], imageSleep[0].getWidth(), imageSleep[0].getHeight());
		this.presentation.setImage(imageSleep[1], imageSleep[1].getWidth(), imageSleep[1].getHeight());
		this.presentation.setImage(imageSleep[0], imageSleep[0].getWidth(), imageSleep[0].getHeight());
		this.presentation.setImage(imageSleep[2], imageSleep[2].getWidth(), imageSleep[2].getHeight());
	}
	
	public void regenerer() {
		//animation de regenerescence
		this.presentation.setImage(imageLeft[0], imageLeft[0].getWidth(), imageLeft[0].getHeight());
		this.presentation.setImage(imageBack[0], imageBack[0].getWidth(), imageBack[0].getHeight());
		this.presentation.setImage(imageRight[0], imageRight[0].getWidth(), imageRight[0].getHeight());
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
		this.setActif(true);
	}
	
	
	public void moveLeft() {
		
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[0], imageLeft[0].getWidth(), imageLeft[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[1], imageLeft[1].getWidth(), imageLeft[1].getHeight());
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[0], imageLeft[0].getWidth(), imageLeft[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[2], imageLeft[2].getWidth(), imageLeft[2].getHeight());
		this.direction = "L";
	}

	public void moveRight() {
		
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[0], imageRight[0].getWidth(), imageRight[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[1], imageRight[1].getWidth(), imageRight[1].getHeight());
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[0], imageRight[0].getWidth(), imageRight[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[2], imageRight[2].getWidth(), imageRight[2].getHeight());
		this.direction = "R";
	}

	public void moveUp() {

		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[0], imageBack[0].getWidth(), imageBack[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[1], imageBack[1].getWidth(), imageBack[1].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[0], imageBack[0].getWidth(), imageBack[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[2], imageBack[2].getWidth(), imageBack[2].getHeight());
		this.direction = "U";
	}

	public void moveDown() {

		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[1], imageFace[1].getWidth(), imageFace[1].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[2], imageFace[2].getWidth(), imageFace[2].getHeight());
		this.direction = "D";
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
