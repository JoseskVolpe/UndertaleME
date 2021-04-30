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

import josesk.javameengine.Backlight;
import josesk.javameengine.Sound;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;
import josesk.undertaleme.GameViewData;
import josesk.undertaleme.layout.Player;
import josesk.undertaleme.secondscreenscenes.FightScreen;

public class BattleEnter extends GameView implements Runnable{
	
	private Sound encounter;
	private Player player;
	private int gotoX, gotoY;

	/**
	 * Battle entering scene
	 * (Blinking hearth animation)
	 * @param data
	 * @param gotoX
	 * @param gotoY
	 */
	public BattleEnter(GameViewData data, int gotoX, int gotoY) {
		super(data);
		
		this.gotoX = gotoX;
		this.gotoY = gotoY;
		
		player = (Player) data.spriteData[6];
		player.setState(Player.STATE_FREEZE);
		
		int widthSobra = GameEngine.getCanvas().getResolutionWidth() - data.spriteData[4].getWidth();
		final int x = widthSobra/2;
		
		player.setRelativePosition(x+51, 102);
		
	}
	
	public void onGameViewBegin() throws Exception, Error {
		
		GameEngine.setScreenMode(GameEngine.SCREEN_DYNAMIC);
		
		encounter = data.soundData[3];
		if(encounter!=null) encounter.Start();
		
		GameView FightScreen = new FightScreen(player);
		GameEngine.getCanvas().secondaryScreenAtach(FightScreen);
		
		beginThread(new Thread(this));
		
	}
	
	public boolean onError(String error) throws Error, Exception {
		
		encounter.Close();
		return true;
		
	}
	
	public void onGameViewDestroy() throws Error, Exception {
		
		GameEngine.resetScreenMode();
		
		encounter.Close();
		
	}
	
	public void render(Graphics g) throws Error, Exception {
		
		g.setColor(0x000000);
		g.drawRect(0, 0, 1, 1); //Glitch prevent
		g.fillRect(0, 0, GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight());
		
		if(player!=null & player.isVisible()) {
			
			player.paint(g);
			
		}
		
	}
	
	public void Update() throws Error, Exception {
		
		if(player!=null) player.Update();
		
	}

	public void Run()  throws Error, Exception{
		
		player.blink(3, 410, false);
		Backlight.flash(3, 410);
		Thread.sleep(410);
		player.moveTo(gotoX, gotoY, 340, true);
		sync.syncSleep(340);
		encounter.Close();
			
		GameView f = new SansFight(data);
		GameEngine.getCanvas().attach(f);
		
	}
	
	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) {
		
		if(player!=null) {
			player.updateRelativePlayerPosition(lastWidth, lastHeight, wDif, hDif);
			
		}
		
	}

}
