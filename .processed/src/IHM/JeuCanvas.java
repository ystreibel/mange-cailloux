/**
 * @(#) JeuCanvas.java
 */

package IHM;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import Application.Factory;
import Metier.Jeu;

public class JeuCanvas extends GameCanvas implements CommandListener,Runnable {
	
	// the game boundary
	/**
	 * Limite de la map sur le jeu
	 */
	public static final int GAME_WIDTH = 168;
	public static final int GAME_HEIGHT = 168 ;
	/**
	 * coordonnée d'origine de la map sur le jeu
	 */
	public final int GAME_ORIGIN_X = (getWidth() - GAME_WIDTH)/2;
	public final int GAME_ORIGIN_Y = (getHeight() - GAME_HEIGHT)/2;
	
	/**
	 * Commandes du jeu
	 */
	private Command exitCmd = new Command ("Exit", Command.EXIT, 5);
	private Command pauseCmd = new Command ("Pause", Command.STOP, 5);
	private Command restartCmd = new Command ("Restart", Command.STOP, 5);
	
	/**
	 * boolean permettant de savoir si la partie est en cours
	 */
	private boolean partieEnCours = true;
	
	/**
	 * Controle
	 */
	private Jeu controle;
	/**
	 * Permet de lancer le jeu
	 */
	private Thread t;
	/**
	 * Presentation du heros
	 */
	private SpriteHeros heros;
	
	/**
	 * Map de sprite du jeu
	 */
	private Sprite[][] mapNiveau;
		
	/**
	 * Gestionnaire des couches de sprites
	 */
	private LayerManager mLayerManager;
	
	/**
	 * Timer permettant le deplacement des ennemis
	 */
	private Timer deplacementEnnemi;

	/**
	 * Constructeur
	 * 
	 * @param arg0
	 */
	public JeuCanvas(boolean arg0, Jeu controle) {
		super(arg0);
		this.start();
		this.controle = controle;
		this.mLayerManager = new LayerManager();
		//Ajout de la presentation des vie
		for (int i = 0; i < this.controle.getViesHero().length; i++) {
			this.controle.getViesHero()[i].getPresentation().setPosition(GAME_ORIGIN_X+i*20, GAME_ORIGIN_Y-17);
			this.mLayerManager.append(this.controle.getViesHero()[i].getPresentation());
		}
		// ajout de la presentation du heros a la presentation du jeu
		this.heros = controle.getHeros().getPresentation();
		//Chargement du Niveau
		this.loadNiveau(this.controle.getNiveauCourant().getNumNiveau());
		
		//ajout des commande au canvas
		this.addCommand (exitCmd);
		this.addCommand(pauseCmd);
		this.setCommandListener (this);
	}

	public void loadNiveau(int niveau){
		//Positionnement et ajout du hero au niveau
		this.heros.setPosition((GAME_ORIGIN_X+4)+5*16, (GAME_ORIGIN_Y+4)+5*16);
		this.mLayerManager.append(heros);
		mapNiveau = this.controle.getNiveauCourant().getMap().getMatrice();
		for (int i = 0; i < mapNiveau.length; i++) {
			for (int j = 0; j < mapNiveau[i].length; j++) {
				if(mapNiveau[i][j] != null){
					if(mapNiveau[i][j].getClass() == IHM.SpriteMur.class){
						if(((SpriteMur)mapNiveau[i][j]).getControle().getOrientation()=="horizontal"){
							if(j == 0 && i <= 11){
								mapNiveau[i][j].setPosition(GAME_ORIGIN_X - 12+i*16, GAME_ORIGIN_Y);
							}
							if(j == 11 && i <= 11){
								mapNiveau[i][j].setPosition(GAME_ORIGIN_X - 12+i*16, GAME_ORIGIN_Y + GAME_HEIGHT - 4);
							}
						}else{
							if(i == 0 && j <= 11){
								mapNiveau[i][j].setPosition(GAME_ORIGIN_X, GAME_ORIGIN_Y - 12+j*16);
							}
							if(i == 11 && j <= 11){
								mapNiveau[i][j].setPosition(GAME_ORIGIN_X + GAME_WIDTH - 4, GAME_ORIGIN_Y - 12+j*16);
							}
						}
						
					}else{
						mapNiveau[i][j].setPosition((GAME_ORIGIN_X-12)+i*16, (GAME_ORIGIN_Y-12)+j*16);
					}
					mLayerManager.append(mapNiveau[i][j]);
				}
			}
		}
		//ajout de l affichage des vies des ennemis du niveau
		for (int i = 0; i < this.controle.getNiveauCourant().getViesEnnemi().length; i++) {
			this.controle.getNiveauCourant().getViesEnnemi()[i].getPresentation().setPosition(GAME_ORIGIN_X + GAME_WIDTH +3, GAME_ORIGIN_Y + GAME_HEIGHT - (i+1)*9);
			this.mLayerManager.append(this.controle.getNiveauCourant().getViesEnnemi()[i].getPresentation());
		}
		//demarage du deplacement periodique des ennemis
		this.deplacementEnnemi = new Timer();
		this.deplacementEnnemi.schedule(new TimerTask() {
			public void run() {
				monitorEnnemi();
			}
		},0,200);
		//Ajout du heros dans la map
		this.mapNiveau[this.convertXtoI(this.heros.getX())][this.convertYtoJ(this.heros.getY())] = this.heros;
		
	}
	public void clearNiveau(){
		for (int i = 0; i < mapNiveau.length; i++) {
			for (int j = 0; j < mapNiveau[i].length; j++) {
				if(mapNiveau[i][j] != null){
					mLayerManager.remove(mapNiveau[i][j]);
				}
			}
		}
		//suppression de l affichage des vies des ennemis
		for (int i = 0; i < this.controle.getNiveauCourant().getViesEnnemi().length; i++) {
			if (this.controle.getNiveauCourant().getViesEnnemi()[i]!=null) {
				this.mLayerManager.remove(this.controle.getNiveauCourant().getViesEnnemi()[i].getPresentation());	
			}
		}
		this.deplacementEnnemi.cancel();
	}
	
	public void spriteSurCaseVide(Sprite sprite){
		boolean trouve = false;
		for (int g = 1; g < mapNiveau.length-1; g++) {
			for (int h = 1; h < mapNiveau[g].length-1; h++) {
				if(mapNiveau[g][h] == null && !trouve){
					mapNiveau[g][h] = sprite;
					sprite.setPosition((GAME_ORIGIN_X-12)+g*16, (GAME_ORIGIN_Y-12)+h*16);
					trouve = true;
				}
			}
		}
	}
	
	/**
	 * Methode de gestion de l affichage de l ecran
	 * 
	 * @param g
	 */
	public void paint(Graphics g) {
		// clear the screen (paint it white):
		g.setColor(0, 0, 99);
		// to the top left corner of the screen.
		g.fillRect(0, 0, getWidth(), getHeight());
		//Affichage du score
		g.setColor(255, 255, 255);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD, Font.SIZE_SMALL));
		g.drawString(""+this.controle.getScore(), GAME_ORIGIN_X + (2*GAME_WIDTH)/3 , GAME_ORIGIN_Y-1, g.BOTTOM | g.RIGHT);	
		//Ajout du numero de niveau
		g.setColor(255, 0, 0);
		g.setFont(Font.getFont(Font.FACE_PROPORTIONAL,Font.STYLE_BOLD, Font.SIZE_SMALL));
		g.drawString("Acte "+this.controle.getNiveauCourant().getNumNiveau(),  GAME_ORIGIN_X + GAME_WIDTH, GAME_ORIGIN_Y-1, g.BOTTOM | g.RIGHT);
		// draw the surrounding rectangle
		g.setColor(255, 255, 255);
		g.drawRect(GAME_ORIGIN_X-1, GAME_ORIGIN_Y-1, GAME_WIDTH+1, GAME_HEIGHT+1);
		// write the string in the center of the screen
		mLayerManager.paint(g, 0, 0);
		flushGraphics();

	}


	private void monitorHero() {
		int keyState = this.getKeyStates();
		int heroI = this.convertXtoI(this.heros.getX());
		int heroJ = this.convertYtoJ(this.heros.getY());
		
			// La touche action est pressée
			if ((keyState & GameCanvas.FIRE_PRESSED) != 0) {
				lancerMangerCaillouHero(this.heros.getX(), this.heros.getY());
			}
			// La touche gauche est pressée
			else if ((keyState & GameCanvas.LEFT_PRESSED) != 0) {
				this.heros.setDirection("L");
				this.heros.moveLeft();
				this.checkCollisionHero(heroI, heroJ);
			}
			// La touche droite est pressée
			else if ((keyState & GameCanvas.RIGHT_PRESSED) != 0) {
				this.heros.setDirection("R");
				this.heros.moveRight();
				this.checkCollisionHero(heroI, heroJ);
			}
			// La touche haut est pressée
			else if ((keyState & GameCanvas.UP_PRESSED) != 0) {
				this.heros.setDirection("U");
				this.heros.moveUp();
				this.checkCollisionHero(heroI, heroJ);
			}
			// La touche bas est pressée
			else if ((keyState & GameCanvas.DOWN_PRESSED) != 0) {
				this.heros.setDirection("D");
				this.heros.moveDown();
				this.checkCollisionHero(heroI, heroJ);
			}
			
	}
	/**
	 * Methode permettant de gerer le deplacement des ennemis
	 */
	private void monitorEnnemi() {
		Random generator = new Random();
		int f;
		int caseI=0;
		int caseJ=0;
		
		//Recuperation des ennemies present sur la map
		SpriteEnnemi[] spritesEnnemis = new SpriteEnnemi[this.controle.getNiveauCourant().getMap().getNbEnnemis()];   
		int cmptEnnemi = 0;
		for (int g = 0; g < mapNiveau.length; g++) {
			for (int h = 0; h < mapNiveau[g].length; h++) {
				if(mapNiveau[g][h] != null){
					if(mapNiveau[g][h].getClass() == IHM.SpriteEnnemi.class ){
						spritesEnnemis[cmptEnnemi] = (SpriteEnnemi) mapNiveau[g][h];
						cmptEnnemi++;
					}
				}
			}
		}
		
		//deplacement de tout les ennemies
		for (int i = 0; i < spritesEnnemis.length; i++) {
			//Initialisation des variables de position de l ennemi sur la map
			caseI = this.convertXtoI(spritesEnnemis[i].getX());
			caseJ = this.convertYtoJ(spritesEnnemis[i].getY()); 
			//Generer l action a effectuer pour l'ennemi de facon aleatoir
		    f = generator.nextInt();

		    //Verification de l etat des ennemies
		    if(((SpriteEnnemi)mapNiveau[caseI][caseJ]).isActif()){
				switch (Math.abs((f)%5)) {
				case 0:
					this.mangerCaillouEnnemi(caseI,caseJ);
					break;
					
				case 1:
					((SpriteEnnemi)mapNiveau[caseI][caseJ]).setDirection("L");
					if(!this.checkCollisionEnnemi(caseI,caseJ)){
						mapNiveau[caseI-1][caseJ]=mapNiveau[caseI][caseJ];
						((SpriteEnnemi) mapNiveau[caseI][caseJ]).moveLeft();
						mapNiveau[caseI][caseJ]=null;
					}
					break;
					
				case 2:
					((SpriteEnnemi)mapNiveau[caseI][caseJ]).setDirection("R");
					if(!this.checkCollisionEnnemi(caseI, caseJ)){
						mapNiveau[caseI+1][caseJ]=mapNiveau[caseI][caseJ];
						((SpriteEnnemi) mapNiveau[caseI][caseJ]).moveRight();
						mapNiveau[caseI][caseJ]=null;
					}
					break;
					
				case 3:
					((SpriteEnnemi)mapNiveau[caseI][caseJ]).setDirection("U");
					if(!this.checkCollisionEnnemi(caseI, caseJ)){
						mapNiveau[caseI][caseJ-1]=mapNiveau[caseI][caseJ];
						((SpriteEnnemi) mapNiveau[caseI][caseJ]).moveUp();
						mapNiveau[caseI][caseJ]=null;
					}
					break;
					
				case 4:
					((SpriteEnnemi)mapNiveau[caseI][caseJ]).setDirection("D");
					if(!this.checkCollisionEnnemi(caseI, caseJ)){
						mapNiveau[caseI][caseJ+1]=mapNiveau[caseI][caseJ];
						((SpriteEnnemi) mapNiveau[caseI][caseJ]).moveDown();
						mapNiveau[caseI][caseJ]=null;
					}
					break;
				}
	
			}
		}
	}
	
	public int convertXtoI(int postionX){
		return (postionX+(2*16)-GAME_ORIGIN_X-12)/16;
	}
	
	public int convertYtoJ(int postionY){
		return (postionY+(2*16)-GAME_ORIGIN_Y-12)/16;
	}

	public int convertItoX(int i){
		return (GAME_ORIGIN_X-12)+i*16;
	}
	
	public int convertJtoY(int j){
		return (GAME_ORIGIN_Y-12)+j*16;
	}
	
	public void lancerMangerCaillouHero(int hero_X, int hero_Y){
		int i = 0;
		int j = 0;
		//calcule de la case a proximité du heros suivant sa direction
		if(this.heros.getDirection().equals("L")){
			i=this.convertXtoI(hero_X)-1;
			j=this.convertYtoJ(hero_Y);
		}else if(this.heros.getDirection().equals("R")){
			i=this.convertXtoI(hero_X)+1;
			j=this.convertYtoJ(hero_Y);
		}else if(this.heros.getDirection().equals("U")){
			i=this.convertXtoI(hero_X);
			j=this.convertYtoJ(hero_Y)-1;
		}else if(this.heros.getDirection().equals("D")){
			i=this.convertXtoI(hero_X);
			j=this.convertYtoJ(hero_Y)+1;
		}
		
		if(mapNiveau[i][j] != null){
			if(mapNiveau[i][j].getClass() == IHM.SpriteBrique.class){
				//Si la brique n'est pas bloqué on la bouge
				if(!caillouBloque(i,j,this.heros.getDirection())){
					this.bougerCaillou(i, j, this.heros.getDirection());
					this.heros.pousseCaillou();
					this.lancerMangerCaillouHero(convertItoX(i), convertJtoY(j));
				}else{
					//Si la brique est bloqué par un mur ou une autre brique, on la casse
					if((this.heros.getDirection().equals("L") && this.convertXtoI(this.heros.getX())-1 == i)
					||(this.heros.getDirection().equals("R")&& this.convertXtoI(this.heros.getX())+1 == i)
					||(this.heros.getDirection().equals("U")&& this.convertYtoJ(this.heros.getY())-1 == j)
					||(this.heros.getDirection().equals("D")&& this.convertYtoJ(this.heros.getY())+1 == j)					
					){
						this.casserBrique(i,j);
					}
				}
			}else if(mapNiveau[i][j].getClass() == IHM.SpriteDiamant.class){
				//si le diamant n est pas bloqué on le deplace
				if(!caillouBloque(i,j,this.heros.getDirection())){
					this.bougerCaillou(i, j, this.heros.getDirection());
					this.heros.pousseCaillou();
					lancerMangerCaillouHero(convertItoX(i), convertJtoY(j));
				}
			}
		}
	}
	
	public void mangerCaillouEnnemi(int i, int j){
		int g=0;
		int h=0;
		if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("L")){
			g=i-1;
			h=j;
		}else if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("R")){
			g=i+1;
			h=j;
		}else if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("U")){
			g=i;
			h=j-1;
		}else if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("D")){
			g=i;
			h=j+1;
		}
		if(mapNiveau[g][h] != null){
			if(mapNiveau[g][h].getClass() == IHM.SpriteBrique.class){
				this.mLayerManager.remove(mapNiveau[g][h]);
				mapNiveau[g][h]=null;
			}
		}
	}
	
	public void casserBrique(int i, int j){
		this.mLayerManager.remove(mapNiveau[i][j]);
		mapNiveau[i][j]=null;
		//Ajout du score pour avoir manger une brique
		this.controle.setScore(this.controle.getScore()+10);
	}
	/**
	 * Deaplcement du caillou dans la direction souhaitée
	 * @param i
	 * @param j
	 * @param direction
	 */
	public void bougerCaillou(int i, int j, String direction){
			if(direction.equals("L")){
				if(this.mapNiveau[i-1][j]!=null){
					if(this.mapNiveau[i-1][j].getClass() == IHM.SpriteEnnemi.class){
						//Suppression d'une vie des ennemis
						this.mLayerManager.remove((this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1].getPresentation()));
						this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1]=null;
						this.controle.getNiveauCourant().setNbRennaissance(this.controle.getNiveauCourant().getNbRennaissance()-1);
						//Ajout du score pour avoir manger l'ennemi
						if(this.mapNiveau[i][j].getClass() == IHM.SpriteBrique.class){
							this.controle.setScore(this.controle.getScore()+100);
						}else if(this.mapNiveau[i][j].getClass() == IHM.SpriteDiamant.class){
							this.controle.setScore(this.controle.getScore()+300);
						}
						//Faire renaitre un ennemi
						if(this.controle.getNiveauCourant().getNbRennaissance()<= 0){
							//Suppression du SpriteEnnemi sur la map
							this.mLayerManager.remove(this.mapNiveau[i-1][j]);
							this.mapNiveau[i-1][j]=null;
						}else{
							//Rennaissance de l'ennemi sur une case vide
							this.spriteSurCaseVide(this.mapNiveau[i-1][j]);
							((SpriteEnnemi)this.mapNiveau[i-1][j]).regenerer();
						}
					}
				}
				this.mapNiveau[i-1][j]=mapNiveau[i][j];
				this.mapNiveau[i][j]=null;
				mapNiveau[i-1][j].setPosition((GAME_ORIGIN_X-12)+(i-1)*16, (GAME_ORIGIN_Y-12)+j*16);
			}else if(direction.equals("R")){
				if(this.mapNiveau[i+1][j]!=null){
					if(this.mapNiveau[i+1][j].getClass() == IHM.SpriteEnnemi.class){
						//Suppression d'une vie des ennemis
						this.mLayerManager.remove((this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1].getPresentation()));
						this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1]=null;
						this.controle.getNiveauCourant().setNbRennaissance(this.controle.getNiveauCourant().getNbRennaissance()-1);
						//Ajout du score pour avoir manger l'ennemi
						if(this.mapNiveau[i][j].getClass() == IHM.SpriteBrique.class){
							this.controle.setScore(this.controle.getScore()+100);
						}else if(this.mapNiveau[i][j].getClass() == IHM.SpriteDiamant.class){
							this.controle.setScore(this.controle.getScore()+300);
						}
						//Faire renaitre un ennemi
						if(this.controle.getNiveauCourant().getNbRennaissance()<= 0){
							//Suppression du SpriteEnnemi sur la map
							this.mLayerManager.remove(this.mapNiveau[i+1][j]);
							this.mapNiveau[i+1][j]=null;
						}else{
							//Rennaissance de l'ennemi sur une case vide
							this.spriteSurCaseVide(this.mapNiveau[i+1][j]);
							((SpriteEnnemi)this.mapNiveau[i+1][j]).regenerer();
						}
					}
				}
				this.mapNiveau[i+1][j]=mapNiveau[i][j];
				this.mapNiveau[i][j]=null;
				mapNiveau[i+1][j].setPosition((GAME_ORIGIN_X-12)+(i+1)*16, (GAME_ORIGIN_Y-12)+j*16);
			}else if(direction.equals("U")){
				if(this.mapNiveau[i][j-1]!=null){
					if(this.mapNiveau[i][j-1].getClass() == IHM.SpriteEnnemi.class){
						//Suppression d'une vie des ennemis
						this.mLayerManager.remove((this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1].getPresentation()));
						this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1]=null;
						this.controle.getNiveauCourant().setNbRennaissance(this.controle.getNiveauCourant().getNbRennaissance()-1);
						//Ajout du score pour avoir manger l'ennemi
						if(this.mapNiveau[i][j].getClass() == IHM.SpriteBrique.class){
							this.controle.setScore(this.controle.getScore()+100);
						}else if(this.mapNiveau[i][j].getClass() == IHM.SpriteDiamant.class){
							this.controle.setScore(this.controle.getScore()+300);
						}
						//Faire renaitre un ennemi
						if(this.controle.getNiveauCourant().getNbRennaissance()<= 0){
							//Suppression du SpriteEnnemi sur la map
							this.mLayerManager.remove(this.mapNiveau[i][j-1]);
							this.mapNiveau[i][j-1]=null;
						}else{
							//Rennaissance de l'ennemi sur une case vide
							this.spriteSurCaseVide(this.mapNiveau[i][j-1]);
							((SpriteEnnemi)this.mapNiveau[i][j-1]).regenerer();
						}
					}
				}
				this.mapNiveau[i][j-1]=mapNiveau[i][j];
				this.mapNiveau[i][j]=null;
				mapNiveau[i][j-1].setPosition((GAME_ORIGIN_X-12)+i*16, (GAME_ORIGIN_Y-12)+(j-1)*16);
			}else if(direction.equals("D")){
				if(this.mapNiveau[i][j+1]!=null){
					if(this.mapNiveau[i][j+1].getClass() == IHM.SpriteEnnemi.class){
						//Suppression d'une vie des ennemis
						this.mLayerManager.remove((this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1].getPresentation()));
						this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1]=null;
						this.controle.getNiveauCourant().setNbRennaissance(this.controle.getNiveauCourant().getNbRennaissance()-1);
						//Ajout du score pour avoir manger l'ennemi
						if(this.mapNiveau[i][j].getClass() == IHM.SpriteBrique.class){
							this.controle.setScore(this.controle.getScore()+100);
						}else if(this.mapNiveau[i][j].getClass() == IHM.SpriteDiamant.class){
							this.controle.setScore(this.controle.getScore()+300);
						}
						//Faire renaitre un ennemi
						if(this.controle.getNiveauCourant().getNbRennaissance()<= 0){
							//Suppression du SpriteEnnemi sur la map
							this.mLayerManager.remove(this.mapNiveau[i][j+1]);
							this.mapNiveau[i][j+1]=null;
						}else{
							//Rennaissance de l'ennemi sur une case vide
							this.spriteSurCaseVide(this.mapNiveau[i][j+1]);
							((SpriteEnnemi)this.mapNiveau[i][j+1]).regenerer();
						}
					}
				}
				this.mapNiveau[i][j+1]=mapNiveau[i][j];
				this.mapNiveau[i][j]=null;
				mapNiveau[i][j+1].setPosition((GAME_ORIGIN_X-12)+i*16, (GAME_ORIGIN_Y-12)+(j+1)*16);
			}
	}
	/**
	 * Verification si une brique ou un diamant est bloqué
	 * @param i
	 * @param j
	 * @param direction
	 * @return
	 */
	public boolean caillouBloque(int i, int j, String direction){
		boolean ret = false;
		if(direction.equals("L")){
			if(mapNiveau[i-1][j] != null && mapNiveau[i-1][j].getClass() != IHM.SpriteEnnemi.class){
				ret = true;
			}
			
		}else if(direction.equals("R")){
			if(mapNiveau[i+1][j] != null && mapNiveau[i+1][j].getClass() != IHM.SpriteEnnemi.class){
				ret = true;
			}
		}else if(direction.equals("U")){
			if(mapNiveau[i][j-1] != null && mapNiveau[i][j-1].getClass() != IHM.SpriteEnnemi.class){
				ret = true;
			}
		}else if(direction.equals("D")){
			if(mapNiveau[i][j+1] != null && mapNiveau[i][j+1].getClass() != IHM.SpriteEnnemi.class){
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 */
	public void checkCollisionHero(int i, int j) {
		int g=0;
		int h=0;
		if(this.heros.getDirection().equals("L")){
			g=i-1;
			h=j;
		}else if(this.heros.getDirection().equals("R")){
			g=i+1;
			h=j;
		}else if(this.heros.getDirection().equals("U")){
			g=i;
			h=j-1;
		}else if(this.heros.getDirection().equals("D")){
			g=i;
			h=j+1;
		}
		
		
		if(mapNiveau[g][h] != null){
				if(mapNiveau[g][h].getClass() == IHM.SpriteBrique.class 
						|| mapNiveau[g][h].getClass() == IHM.SpriteDiamant.class
						|| mapNiveau[g][h].getClass() == IHM.SpriteMur.class){
					this.heros.setPosition(this.convertItoX(i), this.convertJtoY(j));
				}else if(mapNiveau[g][h].getClass() == IHM.SpriteEnnemi.class){
						if(((SpriteEnnemi)mapNiveau[g][h]).isActif()){
							this.perdVieHeros();
							this.heros.setPosition(this.convertItoX(i), this.convertJtoY(j));
						}else{
							//Si l ennemi dors
							//Suppression d'une vie des ennemis
							this.mLayerManager.remove((this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1].getPresentation()));
							this.controle.getNiveauCourant().getViesEnnemi()[this.controle.getNiveauCourant().getNbRennaissance()-1]=null;
							this.controle.getNiveauCourant().setNbRennaissance(this.controle.getNiveauCourant().getNbRennaissance()-1);
							//Ajout du score pour avoir manger l'ennemi
							this.controle.setScore(this.controle.getScore()+60);
							//Faire renaitre un ennemi
							if(this.controle.getNiveauCourant().getNbRennaissance()<= 0){
								//Suppression du SpriteEnnemi sur la map
								this.mLayerManager.remove(mapNiveau[g][h]);
								this.mapNiveau[g][h]=null;
							}else{
								//Rennaissance de l'ennemi sur une case vide
								this.spriteSurCaseVide(mapNiveau[g][h]);
								((SpriteEnnemi)mapNiveau[g][h]).regenerer();
							}
							//changer la positon sur la map du heros
							mapNiveau[g][h] = mapNiveau[i][j];
							mapNiveau[i][j]=null;
						}
				}
		}else{
			//Il n'y a pas de collisions
			//changer la positon sur la map du heros
			mapNiveau[g][h] = mapNiveau[i][j];
			mapNiveau[i][j]=null;
		}
		
	}
	/**
	 * 
	 */
	private void perdVieHeros() {
		this.mLayerManager.remove((this.controle.getViesHero()[this.controle.getVieJoueur()-1].getPresentation()));
		this.controle.getViesHero()[this.controle.getVieJoueur()-1]=null;
		this.controle.setVieJoueur(this.controle.getVieJoueur()-1);
		this.heros.mangerParEnnemi();
		//Tout les ennemis se mettent a dormir
		for (int g = 0; g < mapNiveau.length; g++) {
			for (int h = 0; h < mapNiveau[g].length; h++) {
				if(mapNiveau[g][h] != null){
					if(mapNiveau[g][h].getClass() == IHM.SpriteEnnemi.class){
						((SpriteEnnemi)mapNiveau[g][h]).dormir();
					}
				}
			}
		}
		
	}
	
	/**
	 * 
	 * @param i
	 * @param j
	 * @return
	 */
	public  boolean checkCollisionEnnemi(int i, int j){
		int g=0;
		int h=0;
		boolean ret = false;
		if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("L")){
			g=i-1;
			h=j;
		}else if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("R")){
			g=i+1;
			h=j;
		}else if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("U")){
			g=i;
			h=j-1;
		}else if(((SpriteEnnemi) mapNiveau[i][j]).getDirection().equals("D")){
			g=i;
			h=j+1;
		}
		if(mapNiveau[g][h] != null){
				if(mapNiveau[g][h].getClass() == IHM.SpriteBrique.class 
						|| mapNiveau[g][h].getClass() == IHM.SpriteDiamant.class
						|| mapNiveau[g][h].getClass() == IHM.SpriteMur.class
						|| mapNiveau[g][h].getClass() == IHM.SpriteEnnemi.class){
					ret = true;
				}else if(mapNiveau[g][h].getClass() == IHM.SpriteHeros.class){
					this.perdVieHeros();
					ret = true;
				}
		}
		return ret;
	}

	/**
	 * 
	 */
	public void checkDiamant(){
		for (int g = 0; g < mapNiveau.length; g++) {
			for (int h = 0; h < mapNiveau[g].length; h++) {
				if((mapNiveau[g][h]!=null && mapNiveau[g][h].getClass()==IHM.SpriteDiamant.class)&&
					(mapNiveau[g+1][h]!=null && mapNiveau[g+1][h].getClass()==IHM.SpriteDiamant.class)&&
					(mapNiveau[g+2][h]!=null &&	mapNiveau[g+2][h].getClass()==IHM.SpriteDiamant.class)){
					//les diamants sont alignés et ajout du bonus score
					this.controle.getNiveauCourant().setDiamantsAlignes(true);
					this.controle.setScore(this.controle.getScore()+5000*(this.controle.getNiveauCourant().getNumNiveau()+1));
				}else if((mapNiveau[g][h]!=null && mapNiveau[g][h].getClass()==IHM.SpriteDiamant.class)&&
						(mapNiveau[g][h+1]!=null && mapNiveau[g][h+1].getClass()==IHM.SpriteDiamant.class)&&
						(mapNiveau[g][h+2]!=null &&	mapNiveau[g][h+2].getClass()==IHM.SpriteDiamant.class)){
						//les diamants sont alignés et ajout du bonus score
						this.controle.getNiveauCourant().setDiamantsAlignes(true);
						this.controle.setScore(this.controle.getScore()+5000*(this.controle.getNiveauCourant().getNumNiveau()+1));
				}
			}
		}
	}
	/**
	 * Demarrage de l'application
	 */
	public void start() {
		t = new Thread(this);
        t.start();
	}
	
	/**
	 * 
	 */
	public void run() {
			Graphics g = getGraphics();
			while (partieEnCours) {
				//gestion Du Hero
				monitorHero();
				try {
					if(!this.controle.isTermine() && (this.controle.getNiveauCourant().isLevelDone() || this.controle.getNiveauCourant().getNbRennaissance()<=0)){
						this.clearNiveau();
						this.controle.nextLevel();
						this.loadNiveau(this.controle.getNiveauCourant().getNumNiveau());
					}
					if(this.controle.getVieJoueur() == 0 || this.controle.isTermine()){
						this.clearNiveau();
						//this.controle.getMidlet().setDisplayable(null);
						
						//Enregistrement du score si il est superieur a un des HighScore
						this.controle.saveHS();
						this.partieEnCours = false;
						Factory.getInstance().creerHighScoreFinCanvas(this.controle.getMidlet());
					}
					//verifie si les diamants sont alignés
					if(!this.controle.getNiveauCourant().isDiamantsAlignes()){
						this.checkDiamant();
					}
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// mise a jour de l affichage
				paint(g);
			}
		}

	/**
	 * 
	 */
	public void commandAction(Command c, Displayable d) {
		if (c == exitCmd) {
            this.controle.getMidlet().notifyDestroyed ();
        }
		if(c == pauseCmd){
			this.removeCommand(pauseCmd);
			this.addCommand(restartCmd);
			this.partieEnCours = false;
			try {this.deplacementEnnemi.wait();} catch (Exception e) {}
		}
		if(c == restartCmd){
			this.removeCommand(restartCmd);
			this.addCommand(pauseCmd);
			this.partieEnCours =true;
			this.start();
		}
	}

}
