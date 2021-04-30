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
import javax.microedition.lcdui.game.Sprite;

import josesk.javameengine.Sound;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;
import josesk.undertaleme.GameViewData;
import josesk.undertaleme.layout.Dialog;
import josesk.undertaleme.secondscreenscenes.DialogScreen;

public class DialogScene extends GameView{

	private Sprite scene, head;
	private Sound sans;
	private DialogScreen ds;
	
	public DialogScene(GameViewData data) {
		super(data);
		
		scene = data.spriteData[4];
		sans = data.soundData[0];
		head = data.spriteData[5];
		
		ds = new DialogScreen();
		
	}
	
	public void onGameViewBegin() {
		
		GameEngine.setScreenMode(GameEngine.SCREEN_DYNAMIC);
		
		GameEngine.getCanvas().secondaryScreenAtach(ds);
		
		dialog = new Dialog(data.textData[0], sans, Dialog.ANCHOR_BOTTOM, head, this);
		dialog.setFrame(0);
		dialog.start();
		
		ds.assignDialog(dialog);
		
	}
	
	public void onGameViewDestroy() throws Error, Exception {
		
		if(sans!=null) sans.Close();
		
	}
	
	public boolean onError(String error) throws Error, Exception {
		
		if(sans!=null) sans.Close();
		return true;
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(0x000000);
		g.fillRect(0, 0, GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight());
		
		if(scene!=null) {
			int widthSobra = GameEngine.getCanvas().getResolutionWidth() - scene.getWidth();
			final int x = widthSobra/2;
			
			scene.setPosition(x, 0);
			scene.paint(g);
		}
		
		
		
	}

	public void Update() {
		
		
		
	}
	
	public boolean onKeyDown(String command) {
		
		return true;
		
	}
	
	public boolean onKeyUp(String command) {
		
		return true;
		
	}
	
	public boolean onDialogEnd(String fileName) {
		
		ds.assignDialog(null);
		
		GameView BattleEnter = new BattleEnter(data, (GameEngine.getCanvas().getResolutionWidth()/2)-4, (int)(GameEngine.getCanvas().getResolutionHeight()/1.33f)-16-3);
		GameEngine.getCanvas().attach(BattleEnter);
		
		return true;
		
	}

}
