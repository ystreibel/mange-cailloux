/**
 * @(#) SpriteMur.java
 */

package IHM;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import Metier.Mur;

public class SpriteMur extends Sprite {

	private Mur controle;

	public SpriteMur(Image image, Mur mur) {
		super(image);
		this.controle = mur;
	}

	public Mur getControle() {
		return controle;
	}

}
