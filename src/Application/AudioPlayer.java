package Application;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

public class AudioPlayer {

	private InputStream sound;

	private Player audioPlayerGame;
	
	public AudioPlayer(String type, String soundUrl, int repetition) {
		this.sound = getClass().getResourceAsStream(soundUrl);
		try {
			this.audioPlayerGame = Manager.createPlayer(this.sound, type);
			audioPlayerGame.setLoopCount(repetition);
			audioPlayerGame.start();
		} catch (IOException e) {
		} catch (MediaException e) {}
	}
	
	public void stop(){
		this.audioPlayerGame.close();
	}
	
}
