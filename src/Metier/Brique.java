/**
 * @(#) Brique.java
 */

package Metier;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import Application.Factory;
import IHM.SpriteBrique;

public class Brique extends Caillou {
	private boolean active = true;

	private SpriteBrique presentation;

	private Image[] images;

	public Brique(){
		try {
			images = new Image[5];
			for (int i = 0; i < 5; i++) {
				images[i] = Image.createImage("/images/caillou/brique"+i+".png");
			}
			presentation = Factory.getInstance().creerSpriteBrique(images[0], this);
		} catch (IOException e) {}
	}
	
	public SpriteBrique getPresentation() {
		return presentation;
	}

	public void setPresentation(SpriteBrique presentation) {
		this.presentation = presentation;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isActive() {
		return active;
	}
}
