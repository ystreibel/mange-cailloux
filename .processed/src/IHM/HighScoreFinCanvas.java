package IHM;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;

import Application.Factory;
import Application.JeuMiDLet;
import Metier.HighScore;
import Metier.Jeu;

public class HighScoreFinCanvas extends Canvas{

	private JeuMiDLet jeu;
	
	private Image highScore;

	public HighScoreFinCanvas(JeuMiDLet jeu){
		this.jeu = jeu;
		jeu.setDisplayable(this);
		try {
			highScore = Image.createImage("/images/highscore.png");
		} 
		catch (IOException e) {
		}

	}
	protected void paint(Graphics g) {
		// get the dimensions of the screen:
		int width = getWidth();
		int height = getHeight();
		// clear the screen (paint it white):
		g.setColor(0, 0, 99);
		// to the top left corner of the screen.
		g.fillRect(0, 0, width, height);
		g.setColor(255, 255, 255);
		// write the string in the center of the screen
		 g.drawImage(highScore, (width - highScore.getWidth()) / 2,
		 (height - highScore.getHeight()) / 2, g.TOP | g.LEFT);
		try {
			// Intentionally make this too small to test code below
			RecordStore  rs = HighScore.getSingletonInstance().getRs();
			rs = RecordStore.openRecordStore(HighScore.getSingletonInstance().getREC_STORE(), true);
			byte[] recData = new byte[10];
			int len;
			for (int i = 1; i <= rs.getNumRecords(); i++) {
				if (rs.getRecordSize(i) > recData.length) {
					recData = new byte[rs.getRecordSize(i)];
				}
				len = rs.getRecord(i, recData, 0);
				if(i%2 == 0){
					g.drawString(new String(recData, 0, len), 130, 70 + ((i-1) * 10),
						g.TOP | g.LEFT);
				}else{
					g.drawString(new String(recData, 0, len), 70, 70 + (i * 10),
						g.TOP | g.LEFT);
				}
			}
			rs.closeRecordStore();
		} 
		catch (Exception e) {}
	}

	protected void keyReleased(int keyCode) {
		Jeu.init();
		ManagerCanvas mc = Factory.getInstance().creerManagerCanvas(this.jeu);
		mc.start();
	}
}
