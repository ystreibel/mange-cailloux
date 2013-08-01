package IHM;
import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import Application.Factory;
import Application.JeuMiDLet;
import Metier.Jeu;


public class IntroductionCanvas extends Canvas{

	private Image introduction;
	
	private JeuMiDLet parent;
	
	private ManagerCanvas manager;
	
	public IntroductionCanvas(JeuMiDLet jeuMIDlet, ManagerCanvas manager){
		this.parent = jeuMIDlet;
		this.manager = manager;
		try {
			introduction = Image.createImage("/images/introduction.png");
		} catch (IOException e) {}
	}
	
	protected void paint(Graphics g) {
		// get the dimensions of the screen:
		int width = getWidth();
		int height = getHeight();
		// clear the screen (paint it blue):
		g.setColor(0, 0, 99);
		// to the top left corner of the screen.
		g.fillRect(0, 0, width, height);
		// write the string in the center of the screen
		g.drawImage(introduction, (width - introduction
								.getWidth()) / 2, (height - introduction
								.getHeight()) / 2, g.TOP | g.LEFT);
	}
	
	protected void keyReleased(int keyCode) {
		this.manager.getPlayer().stop();
		this.manager.setCommence(true);
		this.manager.setPlayer(Factory.getInstance().creerAudioPlayer("audio/x-wav", "/audio/comLev.wav", 1));
		parent.setDisplayable(Jeu.getInstance().getPresentation());
		Jeu.getInstance().setMidlet(this.parent);
		
	}

}
