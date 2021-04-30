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

package josesk.undertaleme.secondscreenscenes;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;

/**
 * Loading default secondary screen
 *
 */
public class Loading extends GameView{
	
	private Sprite josesk;
	private String loading;
	private Font font;
	
	public Loading(Sprite josesk) {
		
		this.josesk = josesk;
		loading = "Loading...";
		
		font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		
	}
	
	public void onGameViewBegin() {
		
		beginThread(new Thread(this));
		
	}
	
	public void Run() throws InterruptedException {
		
		while(true) {
			Thread.sleep(300);
			loading="Loading..";
			Thread.sleep(300);
			loading="Loading.";
			Thread.sleep(300);
			loading="Loading";
			Thread.sleep(300);
			loading="Loading.";
			Thread.sleep(300);
			loading="Loading..";
			Thread.sleep(300);
			loading="Loading...";
		}
		
	}
	
	public void render(Graphics g) {
		
		g.setColor(0x000000);
		g.fillRect(0, 0, GameEngine.getCanvas().getWidth(), GameEngine.getCanvas().getHeight());
		
		int w = GameEngine.getCanvas().getWidth();
		int h = GameEngine.getCanvas().getHeight();
		
		if(josesk!=null) {
			josesk.setPosition((w/2)-(josesk.getWidth()/2), (h/2)-(josesk.getHeight()/2));
			josesk.paint(g);
		}
		
		g.setColor(0xffffff);
		g.setFont(font);
		g.drawString(loading, GameEngine.getCanvas().getWidth()/2, GameEngine.getCanvas().getHeight(), Graphics.HCENTER|Graphics.BOTTOM);
		
	}

}
