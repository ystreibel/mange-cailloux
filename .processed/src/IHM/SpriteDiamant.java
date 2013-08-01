/**
 * @(#) SpriteDiamant.java
 */

package IHM;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import Metier.Diamant;

public class SpriteDiamant extends Sprite {
	
	private Diamant controle;

	public SpriteDiamant(Image image, Diamant diamant) {
		super(image);
		this.controle = diamant;
	}
}
