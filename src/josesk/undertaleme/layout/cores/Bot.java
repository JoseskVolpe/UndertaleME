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

package josesk.undertaleme.layout.cores;

import javax.microedition.lcdui.game.Sprite;

import josesk.javameengine.Sync;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.scenes.cores.Fight;

public class Bot  extends Sprite{

	private int move_times, move_time, move_moved, move_targetX, move_targetY;
	private float move_transX, move_transY;
	private float move_x, move_y;
	private Sync move_sync;
	
	private int blink_times, blinks;
	private Sync blink_sync;
	
	private boolean blinking, updating;
	private Fight fightScene;
	
	private int x, y;
	
	public Bot(Sprite s) {
		super(s);
		
		blinking = false;
		updating = false;
		x = 0;
		y = 0;
		
	}
	
	public final void setRelativePosition(int x, int y) {
		
		/*while(updating)
		{
			try{Thread.sleep(1);}catch(InterruptedException e) {}
		}*/
		move_sync = null;
		
		this.x = x;
		this.y = y;
		
	}
	
	public final int getRelativeX() {
		
		return x;
		
	}
	
	public final int getRelativeY() {
		
		return y;
		
	}
	
	/**
	 * Drag bot to
	 * TODO: Fix velocity calculation
	 * @param tx
	 * @param ty
	 * @param time
	 * @param sync
	 */
	public final void moveTo(int tx, int ty, int time, boolean sync) { //TODO CONSERTAR DELAY
		
		move_sync = null;
		
		float fps = GameEngine.getCanvas().getFPSLimit();
		move_sync = new Sync(time/(int)fps, sync);
		
		
		move_x = x;
		move_y = y;
		
		move_targetX = tx;
		move_targetY = ty;
		
		move_time = time;
		
		float ftime = time;
		move_transX = tx-move_x;
		move_transY = ty-move_y;
		
		move_transX = move_transX/(fps/(1000.f/ftime));
		move_transY = move_transY/(fps/(1000.f/ftime));
		
		move_times = (int) (fps/(1000.f/ftime));
		move_moved = 0;
		
		move_sync.begin();
		
	}
	
	/**
	 * Blink bot
	 * @param times
	 * @param time
	 * @param sync
	 */
	public final void blink (int times, int time, boolean sync) {
		
		if(!blinking) {
			
			blink_times = times*2;
			blink_sync = new Sync(time/blink_times, sync);
			blinks = 0;
			blink_sync.begin();
			blinking = true;
			
		}
		
	}
	
	public final void Update() throws Error, Exception {
		updating = true;
		
		if(move_sync != null && move_sync.isTimedOut()) {
				
			int lateRate = move_sync.lateRate();
			float late = move_sync.late();
				
			for(int i=0; i<lateRate; i++) {
				
				move_x+=move_transX;
				move_y+=move_transY;
					
				x = (int)Math.ceil(move_x);
				y = (int)Math.ceil(move_y);
					
				move_moved++;
				if(move_moved>=move_times) {
					x=move_targetX;
					y=move_targetY;
					move_sync = null;
					break;
				}
				
			}
				
			if(move_sync!=null) move_sync.reset(late);
				
		}
		
		if(blinking) {
			
			if(blink_sync.isTimedOut()) {
				int lateRate = blink_sync.lateRate();
				float late = blink_sync.late();
				
				for(int i=0; i<lateRate; i++) {
					this.setVisible(!this.isVisible());
					blinks++;
					
					
					if(blinks==blink_times) {
						blinking=false;
						blink_sync = null;
						break;
					}
				}
				if(blink_sync!=null)blink_sync.reset(late);
				
			}
			
		}
		
		setPosition(x, y);
		
		update();
		
		updating = false;
		
	}
	
	protected void update() throws Error, Exception {}
	
	/**
	 * Returns if bot is moving
	 * @return moving
	 */
	public final boolean isMoving() {
		
		if(move_sync!=null) return true;
		
		return false;
		
	}
	
	/**
	 * Stops movements immediately
	 * @param Skip to position
	 */
	public final void stopMoving(boolean teleport) {
		
		move_sync=null;
		
		if(teleport) {
			
			x = move_targetX;
			y = move_targetY;
			
		}
		
	}
	
	/**
	 * Returns time left to finish movement
	 * @return timeLeft
	 */
	public final int getMoveTimeLeft() {
		
		int time_left = move_time - (move_time * (move_moved/move_times));
		if(time_left<=0) time_left = 0;
		
		return time_left;
		
	}
	
	/**
	 * Get movement time
	 * @return moveTime
	 */
	public final int getMoveTime() {
		
		return move_time;
		
	}
	
	/**
	 * Check if movement is synchronized
	 * @return synchronized
	 */
	public final boolean isMoveSynchronized() {
		
		return move_sync.isSync();
		
	}
	
	/**
	 * Get movement target X
	 * @return targetX
	 */
	public final int getMoveTargetX() {
		return move_targetX;
	}
	
	/**
	 * Get movement target y
	 * @return targetY
	 */
	public final int getMoveTargetY() {
		return move_targetY;
	}
	
	/**
	 * Update player relative position
	 * @param lastWidth
	 * @param lastHeight
	 * @param wDif
	 * @param hDif
	 */
	public final void updateRelativePlayerPosition(int lastWidth, int lastHeight, int wDif, int hDif) {

		final int centerX = GameEngine.getCanvas().getResolutionWidth()/2;
		final int centerY = GameEngine.getCanvas().getResolutionHeight()/2;
		
		final int lcX = lastWidth/2;
		final int lcY = lastHeight/2;
		
		final int px = getRelativeX()-lcX;
		final int py = getRelativeY()-lcY;
		
		int mx=0, my=0;
		
		final boolean updateMoving = isMoving();
		boolean sync = false;
		
		if(isMoving()) {
			
			sync = isMoveSynchronized();
			
			stopMoving(false);
			mx = move_targetX-lcX;
			my = move_targetY-lcY;
			
		}
		
		setRelativePosition(centerX+px, centerY+py);
		
		if(updateMoving) {
			
			moveTo(centerX+mx, centerY+my, getMoveTimeLeft(),sync);
			
		}
		
	}
	
	/**
	 * Set current fight scene
	 * @param fightScene
	 */
	public final void setFightScene(Fight fightScene) {
		this.fightScene = fightScene;
	}
	
	/**
	 * Returns defined fightScene
	 * @return fightScene
	 */
	public final Fight getFightScene() {
		return fightScene;
	}

}
