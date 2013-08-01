package IHM;

import Application.AudioPlayer;
import Application.Factory;
import Application.JeuMiDLet;

public class ManagerCanvas implements Runnable {
	
	private IntroductionCanvas intro;
	
	private HighScoreCanvas score;
	
	private JeuMiDLet parent;
	
	private AudioPlayer player;
	
	private boolean commence = false;
	
	public ManagerCanvas(JeuMiDLet parent){
		this.parent = parent;
		intro = Factory.getInstance().creerIntroductioncanvas(this.parent, this);
		score = Factory.getInstance().creerHighScoreCanvas(this.parent, this);
		player = Factory.getInstance().creerAudioPlayer("audio/x-wav", "/audio/menu.wav", -1); 
		
	}
	
	public AudioPlayer getPlayer() {
		return player;
	}

	public void setPlayer(AudioPlayer player) {
		this.player = player;
	}

	public boolean isCommence() {
		return commence;
	}

	public void setCommence(boolean commence) {
		this.commence = commence;
	}

	/**
	 * Demarrage de l'application
	 */
	public void start() {
		Thread t = new Thread(this);
        t.start();
	}
	
	public void run() {
			try {
				while (!commence) {
					if(!commence){
						parent.setDisplayable(intro);
						Thread.sleep(5000);
					}
					if(!commence){
						parent.setDisplayable(score);
						Thread.sleep(5000);
					}
					
				}
			} catch (InterruptedException e) {}
		}
	
}

