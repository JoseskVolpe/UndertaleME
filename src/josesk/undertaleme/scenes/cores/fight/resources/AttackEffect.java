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

package josesk.undertaleme.scenes.cores.fight.resources;

import josesk.javameengine.Sync;
import josesk.javameengine.sound.NonSyncSound;
import josesk.undertaleme.layout.Effect;

public class AttackEffect extends Effect{
	
	protected static NonSyncSound sound;
	protected Sync sync;
	protected Monster monster; 
	
	public AttackEffect(){
		
		sync = new Sync();
		
	}
	
	/**
	 * Assign monster to this effect
	 * @param monster
	 */
	public final void assignMonster(Monster monster) {
		this.monster = monster;
	}
	
	protected void onBegin() throws Error, Exception{
		
		if(sound!=null) {
			sound.Play();
		}
		
	}
	
	public final void waitForAnimation() {
		
		while(!isAnimationFinished()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		
	}
	
	public final void Update() throws Error, Exception {
		
		if(sync!=null) sync.Update();
		update();
	}
	
	protected void update()  throws Error, Exception{}
	
	/**
	 * Load sound without timeout
	 * @param source
	 * @throws Error
	 * @throws Exception
	 */
	public final void loadSound(String source) throws Error, Exception {
		
		loadSound(source, NonSyncSound.NO_TIMEOUT);
		
	}
	
	/**
	 * Load sound
	 * @param source
	 * @param timeout_millis
	 * @throws Error
	 * @throws Exception
	 */
	public final void loadSound(String source, long timeout_millis) throws Error, Exception {
		
		sound = new NonSyncSound(source, timeout_millis);
		
	}
	
	public final void Unload() throws Error, Exception{
		sound = null;
		unload();
	}
	
	protected void unload() throws Error, Exception{
		
	}
	
	public void run() {
		finishAnimation();
	}

}
