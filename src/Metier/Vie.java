package Metier;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import Application.Factory;
import IHM.SpriteVie;

public class Vie {
	private Image imageVie;

	private SpriteVie presentation;

	public Vie(String possesseur){
		try {
			imageVie = Image.createImage("/images/vie/vie"+possesseur+".png");
		} catch (IOException e) {}
		presentation = Factory.getInstance().creerSpriteVie(imageVie, this);
	}

	public SpriteVie getPresentation() {
		return presentation;
	}

	public void setPresentation(SpriteVie presentation) {
		this.presentation = presentation;
	}
}
