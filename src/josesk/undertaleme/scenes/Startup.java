/*
                                             
         ██                    ██            
       █▓▓▓▓█                █▓▓▓▓█          
      █▓▓▓▓▓█                █▓▓▓▓▓█         
     █▓▓▒ ▒▓▓█   ██████     █▓▓▒ ▒▓▓█        
     █▓▓░  ▓▓█ ██░░░░░░████ █▓█   ▓▓█        
    █▓▓░░  ░▓▓█░░░░░░░░░░░░█▓▓    ░▓▓█       
    █▓▓░░ ░ ▓▓▓█░░░░░░░░░░░█▓▓░░ ░░▓▓█       
    █▒▒░▒ ▒▒▒▒▒██░░░░░░░▒▒░░█▒▒▒░▒░▒▒█       
  █░▒▒▒▒▒▒▒░░░░░░███░░░░▒▒░░█░░▒▒▒▒▒▒▒ █     
  █ ▒▒▒▒▒▒░░░░░░░░░░████████░░░░▒▒▒▒▒▒ █     
   █ ▒▒▒▒▒░░░░░░░░░░░░░░░░░░░░░░▒▒▒▒▒ █      
 █░█░ ▒▒▒▒░░░░░░░░░░░░░░░░░░░░░░▒▒▒▒ ░█ █    
 █    ▒▒▒▒░░░░░░░░░░░░░░░░░░░░░░▒▒▒▒    █    
   █   ▒▒▒▒░██░░░░░░░░░░░░░░██░▒▒▒▒░  █      
 █  ░  █ ▒▒▒▒▒██░░░░░░░░░░██▒▒▒▒▒ █    ░█    
   ███    ▒▒▒▒▒▒▒▒░░▒▒░░▒▒▒▒▒▒▒▒    ███      
      ████  ▒▒▒▒▒▒░░▒▒░░▒▒▒▒▒▒  ████         
             █ ▒▒▒▒▒▒▒▒▒▒▒▒ █                
               █░▒▒▓▒▒▓▒▒ █                  
                █░▒▓▓▓▓▒░█                   
                 ▓ ████ █                    
                  ░█░██            

▓▓▓▓▓ ▓▓▓▓  ▓▓▓▓ ▓▓▓▓ ▓▓▓▓ ▓  ▓     ▓▓ ▓  
  ▓   ▓  ▓  ▓    ▓    ▓    ▓ ▓     ▓▓▓  ▓ 
  ▓   ▓  ▓   ▓   ▓▓▓▓  ▓   ▓▓         ▓ ▓ 
  ▓   ▓  ▓    ▓▓ ▓      ▓▓ ▓ ▓     ▓▓▓▓▓  
▓▓▓   ▓▓▓▓  ▓▓▓▓ ▓▓▓▓ ▓▓▓▓ ▓ ▓    ▓     ▓ 
*Art based from textart.sh/topic/fox
*/

package josesk.undertaleme.scenes;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import josesk.javameengine.LoadResources;
import josesk.javameengine.Sound;
import josesk.javameengine.Source;
import josesk.javameengine.Text;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;
import josesk.undertaleme.layout.ActionButton;
import josesk.undertaleme.layout.FightBar;
import josesk.undertaleme.layout.Player;
import josesk.undertaleme.secondscreenscenes.Loading;

public class Startup extends GameView implements Runnable{

	private Sprite josesk;
	private boolean loaded;
	
	public static final int headSize = 38;
	
	/**
	 * Startup GameView
	 */
	public Startup(){
		
		josesk = LoadResources.createSprite("/JoseskVolpe.png");
		
		loaded = false;
		
	}
	
	public void onGameViewBegin() {
		
		GameEngine.setScreenMode(GameEngine.SCREEN_DYNAMIC);
		GameView secondScreenView = new Loading(josesk);
		GameEngine.getCanvas().secondaryScreenAtach(secondScreenView);
		
		beginThread(new Thread(this));
		
	}
	
	public void onGameViewDestroy() {
		
		GameEngine.resetScreenMode();
		
	}
	
	public boolean onError(String error) throws Error, Exception {
		
		if(data!=null) {
			if(data.soundData[0]!=null) data.soundData[0].Close();
			data=null;
		}
		
		return true;
		
	}
	
	public void render(Graphics g){
		
		g.setColor(0x000000);
		g.fillRect(0, 0, GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight());
		
		int w = GameEngine.getCanvas().getResolutionWidth();
		int h = GameEngine.getCanvas().getResolutionHeight();
		
		if(josesk!=null) {
			josesk.setPosition((w/2)-(josesk.getWidth()/2), (h/2)-(josesk.getHeight()/2));
			josesk.paint(g);
		}

	}
	
	public void Update() {
		
		if(loaded) {
			
			GameView dialog = new DialogScene(data);
			GameEngine.getCanvas().attach(dialog);
			
		}
		
	}

	//Resource loading
	public void Run() throws Error, Exception{
		
		final long lastSec = System.currentTimeMillis();
		
		data.spriteData = new Sprite[8];
		data.soundData = new Sound[4];
		data.textData = new Text[1];
		
		data.spriteData[0] = LoadResources.createSprite("/buttons.jpg", 14, 19);
		data.spriteData[1] = LoadResources.createSprite("/selButtons.jpg", 14, 19);
		data.spriteData[2] = LoadResources.createSprite("/selBTitle.png", 42, 19);
		data.spriteData[3] = LoadResources.createSprite("/BTitle.png", 42, 19);
		ActionButton.setSprites(data.spriteData[1], data.spriteData[0], data.spriteData[2], data.spriteData[3]);
		data.spriteData[4] = LoadResources.createSprite("/scene.jpg");
		data.spriteData[5] = LoadResources.createSprite("/SansHead.png", headSize, headSize);
		data.spriteData[6] = new Player(LoadResources.createSprite("/player.png"));
		data.spriteData[7] = LoadResources.createSprite("/HP.png");
		Image bb = LoadResources.loadImage("/BattleBar.png");
		if(bb!=null) FightBar.assignImage(bb);
		
		data.soundData[0] = LoadResources.loadSound("/SansSpeak.mp3", true);
		data.soundData[1] = LoadResources.loadSound("/Megalovania.mid", true);
		data.soundData[2] = LoadResources.loadSound("/Determination.mid", true);
		data.soundData[3] = LoadResources.loadSound("/Encounter.mp3", false);
		
		data.textData[0] = new Text(new Source("/SansDialog/Dialog/init.txt"));
		
		System.out.println("System took "+(System.currentTimeMillis()-lastSec)+" milliseconds to load resources");
		
		while(System.currentTimeMillis()-lastSec<3000) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		
		loaded = true;
		
	}

}
