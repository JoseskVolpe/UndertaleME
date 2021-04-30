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
*
*FLAGGED FOR DELETION
*/

package josesk.undertaleme.scenes.cores.fight.resources;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import josesk.javameengine.LoadResources;
import josesk.javameengine.Sync;
import josesk.javameengine.sound.AutoDestructiveSound;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.PaintableObject;

/**
 * @deprecated
 *
 */
public class AttackEffectDeprecated implements PaintableObject, Runnable{

	private static Sprite sprite;
	private Monster monster;
	private Sync sync;
	private boolean animated;
	
	public AttackEffectDeprecated(Monster monster) throws Exception, Error {
		
		GameEngine.DebugWarn("AttackEffectDeprecated is DEPRECATED");
		
		this.monster = monster;
		
		AutoDestructiveSound attackSound = new AutoDestructiveSound("/Attack.wav");
		attackSound.Start();
		
		sync = new Sync();
		
		if(sprite==null) {
			
			sprite= LoadResources.createSprite("/AttackEffect.png", 18, 66);
			
		}
		
		sprite.setFrame(0);
		animated = false;
		
		new Thread(this).start();
		
	}
	
	public void paint(Graphics g) throws Error, Exception {
		
		if(!animated) {
			sprite.setPosition(monster.getX()+monster.getEffectX()-9, monster.getY()+monster.getEffectY()-33);
			sprite.paint(g);
		}
		
	}
	
	public void run() {
		
		for(int i=1; i<=5; i++) {
			
			try {
				sync.syncSleep(70);
			} catch (InterruptedException e) {}
			sprite.setFrame(i);
			
		}
		
		try {
			sync.syncSleep(100);
		} catch (InterruptedException e) {}
		
		animated = true;
		
	}
	
	public void waitForAnimation() {
		
		while(!animated) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		
	}

	public void Update() throws Error, Exception {
		
		if(sync!=null) sync.Update();
		
	}

	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) {}

}
