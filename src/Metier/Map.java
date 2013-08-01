/**
 * @(#) Map.java
 */

package Metier;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.lcdui.game.Sprite;

import Application.Factory;

public class Map {

	private Sprite[][] matrice;
	
	private int nbEnnemis = 0;
	
	public Map(int idLevel) {

		InputStream is = getClass().getResourceAsStream("/maps/map" + idLevel + ".map");
		matrice = new Sprite[12][12];
		int c ;
		int i=0;
		int j=0;
		try {
			while ((c = is.read()) != -1) {
				switch (c) {
				case '\n':
					i=0;
					j++;
					break;
				case '0':
					matrice[i][j] = null;
					i++;
					break;
				case '1':
					matrice[i][j] = Factory.getInstance().creerBrique().getPresentation();
					i++;
					break;
				case '2':
					matrice[i][j] = Factory.getInstance().creerDiamant().getPresentation();
					i++;
					break;
				case '3':
					matrice[i][j] = Factory.getInstance().creerModeleEnnemi().getPresentation();
					nbEnnemis++;
					i++;
					break;
				case '4':
					matrice[i][j] = Factory.getInstance().creerMur("horizontal").getPresentation();
					i++;
					break;
				case '5':
					matrice[i][j] = Factory.getInstance().creerMur("vertical").getPresentation();
					i++;
					break;
				}
			}
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNbEnnemis() {
		return nbEnnemis;
	}

	public Sprite[][] getMatrice() {
		return matrice;
	}

	public boolean checkDiamant() {
		return false;
	}
}
