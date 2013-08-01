/**
 * @(#) Diamant.java
 */

package Metier;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import Application.Factory;
import IHM.SpriteDiamant;

public class Diamant extends Caillou {

	private SpriteDiamant presentation;

	private Image image;

	public Diamant(){
		try {
			image = Image.createImage("/images/caillou/diamant0.png");
			presentation = Factory.getInstance().creerSpriteDiamant(image, this);
		} catch (IOException e) {}
	}
	
	public SpriteDiamant getPresentation() {
		return presentation;
	}

	public void setPresentation(SpriteDiamant presentation) {
		this.presentation = presentation;
	}
	
}
