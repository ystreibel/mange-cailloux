/**
 * @(#) SpriteBrique.java
 */

package IHM;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import Metier.Brique;
import Metier.ModeleHeros;

public class SpriteBrique extends Sprite {
	private Brique controle;

	public SpriteBrique(Image image, Brique brique) {
		super(image);
		this.controle = brique;
	}

}
