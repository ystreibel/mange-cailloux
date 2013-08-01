/**
 * @(#) SpriteEnnemi.java
 */

package IHM;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import Metier.ModeleEnnemi;

public class SpriteEnnemi extends Sprite {

	private ModeleEnnemi controle;

	public SpriteEnnemi(Image image, ModeleEnnemi modeleEnnemi) {
		super(image);
		this.controle = modeleEnnemi;
	}

	public void moveLeft() {
		this.controle.moveLeft();
	}

	public void moveRight() {
		this.controle.moveRight();
	}

	public void moveUp() {
		this.controle.moveUp();
	}

	public void moveDown() {
		this.controle.moveDown();
	}

	public boolean isActif() {
		return this.controle.isActif();
	}
	
	public void dormir() {
		this.controle.dormir();
	}

	public void reveille() {
		this.controle.reveille();
	}
	
	public void regenerer() {
		this.controle.regenerer();
	}

	public String getDirection() {
		return this.controle.getDirection();
	}
	
	public void setDirection(String direction) {
		this.controle.setDirection(direction);
	}

}
