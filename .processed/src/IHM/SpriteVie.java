/**
 * @(#) SpriteBarreHaut.java
 */

package IHM;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import Metier.Vie;

public class SpriteVie extends Sprite{

	private Vie controle;
	
	public SpriteVie(Image image, Vie controle){
		super(image);
		this.controle = controle;
	}

	public Vie getControle() {
		return controle;
	}
}
