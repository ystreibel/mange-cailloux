/**
 * @(#) SpriteHeros.java
 */

package IHM;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import Metier.ModeleHeros;

public class SpriteHeros extends Sprite {

	private ModeleHeros controle;

	public SpriteHeros(Image image, ModeleHeros modeleHeros) {
		super(image);
		this.controle = modeleHeros;
	}

	public void mangerParEnnemi() {
		this.controle.mangerParEnnemi();
	}
	
	public void pousseCaillou() {
		this.controle.pousseCaillou();
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
	
	public String getDirection() {
		return this.controle.getDirection();
	}
	public void setDirection(String direction) {
		this.controle.setDirection(direction);
	}
}
