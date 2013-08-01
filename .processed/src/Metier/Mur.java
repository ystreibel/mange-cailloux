/**
 * @(#) Mur.java
 */

package Metier;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import Application.Factory;
import IHM.SpriteMur;

public class Mur {

	private SpriteMur presentation;

	private Image[] images;
	
	private String orientation;

	public Mur(String orientation) {
		try {
			this.orientation = orientation;
			images = new Image[2];
			for (int i = 0; i < images.length; i++) {
				images[i] = Image.createImage("/images/mur/mur" + orientation + i + ".png");
			}
			this.presentation = Factory.getInstance().creerSpriteMur(images[0],this);
		} catch (IOException e) {
		}
	}

	public void setPresentation(SpriteMur presentation) {
		this.presentation = presentation;
	}

	public SpriteMur getPresentation() {
		return presentation;
	}

	public String getOrientation() {
		return orientation;
	}

}
