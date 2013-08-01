/**
 * @(#) ModeleHeros.java
 */

package Metier;

import java.io.IOException;

import javax.microedition.lcdui.Image;

import Application.Factory;
import IHM.SpriteHeros;

public class ModeleHeros extends Modele {
	
	private SpriteHeros presentation;
	
	private Image[] imageFace;
	
	private Image[] imageBack;
	
	private Image[] imageLeft;
	
	private Image[] imageRight;
	
	private Image imagePousse;
	
	private String direction = "D";
	
	public ModeleHeros(){
		try {
			imageFace = new Image[3];
			imageBack = new Image[3];
			imageLeft = new Image[3];
			imageRight = new Image[3];
			
			for (int i = 0; i < 3; i++) {
				imageFace[i] = Image.createImage("/images/hero/face"+i+".png");
				imageBack[i] = Image.createImage("/images/hero/back"+i+".png");
				imageLeft[i] = Image.createImage("/images/hero/left"+i+".png");
				imageRight[i] = Image.createImage("/images/hero/right"+i+".png");
			}
			presentation = Factory.getInstance().creerSpriteHeros(imageFace[0], this);
		} catch (IOException e) {}
	}
	
	public SpriteHeros getPresentation() {
		return presentation;
	}

	public void setPresentation(SpriteHeros presentation) {
		this.presentation = presentation;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public void mangerParEnnemi() {
		this.presentation.setImage(imageLeft[0], imageLeft[0].getWidth(), imageLeft[0].getHeight());
		this.presentation.setImage(imageBack[0], imageBack[0].getWidth(), imageBack[0].getHeight());
		this.presentation.setImage(imageRight[0], imageRight[0].getWidth(), imageRight[0].getHeight());
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
	}
	
	public void pousseCaillou() {
		try {
			imagePousse = Image.createImage("/images/hero/pousse"+this.direction+".png");
			this.presentation.setImage(imagePousse, imagePousse.getWidth(), imagePousse.getHeight());
		} catch (IOException e) {}
	}

	public void moveLeft() {
		
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[0], imageLeft[0].getWidth(), imageLeft[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[1], imageLeft[1].getWidth(), imageLeft[1].getHeight());
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[0], imageLeft[0].getWidth(), imageLeft[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()-4,this.presentation.getY());
		this.presentation.setImage(imageLeft[2], imageLeft[2].getWidth(), imageLeft[2].getHeight());
		this.direction = "L";
	}

	public void moveRight() {
		
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[0], imageRight[0].getWidth(), imageRight[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[1], imageRight[1].getWidth(), imageRight[1].getHeight());
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[0], imageRight[0].getWidth(), imageRight[0].getHeight());
		this.presentation.setPosition(this.presentation.getX()+4,this.presentation.getY());
		this.presentation.setImage(imageRight[2], imageRight[2].getWidth(), imageRight[2].getHeight());
		this.direction = "R";
	}

	public void moveUp() {

		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[0], imageBack[0].getWidth(), imageBack[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[1], imageBack[1].getWidth(), imageBack[1].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[0], imageBack[0].getWidth(), imageBack[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()-4);
		this.presentation.setImage(imageBack[2], imageBack[2].getWidth(), imageBack[2].getHeight());
		this.direction = "U";
	}

	public void moveDown() {

		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[1], imageFace[1].getWidth(), imageFace[1].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[0], imageFace[0].getWidth(), imageFace[0].getHeight());
		this.presentation.setPosition(this.presentation.getX(),this.presentation.getY()+4);
		this.presentation.setImage(imageFace[2], imageFace[2].getWidth(), imageFace[2].getHeight());
		this.direction = "D";
		
	}

}
