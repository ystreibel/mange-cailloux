/**
 * @(#) JeuMiDLet.java
 */

package Application;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import IHM.ManagerCanvas;

public class JeuMiDLet extends MIDlet{
	
	private Display display;
	
	private ManagerCanvas mc;
	
	public JeuMiDLet(){
		this.display = Display.getDisplay (this);
		this.mc = Factory.getInstance().creerManagerCanvas(this);

	}

	protected void startApp() throws MIDletStateChangeException {
		if (display == null) {
			display = Display.getDisplay(this);
		}
		this.mc.start();
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		display.setCurrent (null);		
	}
	
	protected void pauseApp() {
		
	}
	
	public void setDisplayable(Displayable dl){
		display.setCurrent(dl);
	}
}
