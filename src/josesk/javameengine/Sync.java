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
Useful as delta-time alternative for FPS synchronization
*
*/

package josesk.javameengine;

import josesk.undertaleme.GameEngine;

public class Sync {
	
	private long millis;
	private int sleepDelay;
	private boolean waiting = false;
	
	private boolean sync;
	private long time_millis;
	private int time;
	private int realtime;
	private long lastMillis;
	private boolean checked, begin;
	
	
	/**
	 * Use this for syncSleep()
	 */
	public Sync() {}
	
	/**
	 * 
	 * Use this for loops, then use begin()
	 * 
	 * @param time	Desired time to delay
	 * @param sync	Synchronized
	 */
	public Sync(int time, boolean sync) {
		this.time = time;
		this.sync = sync;
		realtime = time;
		checked = false;
		begin = false;
	}
	
	/**
	 * Begin clock
	 */
	public void begin() {
		
		if(sync) {
			time_millis = GameEngine.syncCurrentTimeMillis();
		}else {
			time_millis = System.currentTimeMillis();
		}
		
		begin=true;
		
	}
	
	/**
	 * Check if time is out since begin() or last reset()
	 * @return boolean	timed_out
	 */
	public boolean isTimedOut() {
		
		if(begin) {
			long cMillis;
			if(sync) {
				cMillis = GameEngine.syncCurrentTimeMillis();
			}else {
				cMillis = System.currentTimeMillis();
			}
			
			lastMillis = cMillis;
			checked = true;
			
			if(sync) {
				
				if(realtime<=cMillis-time_millis) return true;
				
			}else {
				
				if(realtime<=cMillis-time_millis) return true;
				
			}
		}
		
		return false;
		
	}
	
	/**
	 * Returns late percent, use it for reset()
	 * @return float	late percent
	 */
	public float late() {
		
		float lateRate=0;
		long cMillis;
		
		if(!checked){
			if(sync) {
				cMillis = GameEngine.syncCurrentTimeMillis();
			}else {
				cMillis = System.currentTimeMillis();
			}
		}else cMillis = lastMillis;
		
		cMillis-=time_millis;
		
		lateRate = cMillis/(float)time;
		if(lateRate<1)lateRate=1;
		
		return lateRate;
		
	}
	
	/**
	 * Returns late rate. use it for loop index
	 * @return int	late rate
	 */
	public int lateRate() {
		
		return (int)Math.floor(late());
		
	}
	
	/**
	 * 
	 * @return int	Synchronized time
	 */
	public int getRealTime() {
		
		return realtime;
		
	}
	
	/**
	 * Reset count and ignore late percent
	 */
	public void reset() {
		
		reset(1);
		
	}
	
	/**
	 * Reset count
	 * @param late percent
	 */
	public void reset(float late) {
		
		checked = false;
		
		begin();
		
		realtime=time;
		realtime/=(late);
		
	}
	
	/**
	 * Returns desired time
	 * @return int	time
	 */
	public int getTime() {
		
		return time;
		
	}
	
	/**
	 * Check synchronization
	 * @return boolean	sync
	 */
	public boolean isSync() {
		
		return sync;
	}
	
	/**
	 Updates clock. That's obligatory to update the clock each frame if you're using syncSleep()
	 */
	public void Update() {
		
		if(waiting) {
			
			if(sleepDelay<=GameEngine.syncCurrentTimeMillis()-millis) waiting = false;
			
		}
		
	}

	/**
	 Synchronized alternative for Thread.sleep()
	 USE UPDATE()
	 
	 @throws InterruptedException
	 */
	public void syncSleep(int delay) throws InterruptedException {
		
		waiting = true;
		sleepDelay = delay;
		millis = GameEngine.syncCurrentTimeMillis();
		while(waiting) {
			Thread.sleep(1);
		}
		
	}
	
}
