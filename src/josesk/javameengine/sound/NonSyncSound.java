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

//Use it if you have problems of content delay while a sound is loading
//THE NONSYNC-SOUND IS AUTODESTRUCTIVE ONLY, IT CANNOT BE USED ON MUSICS

package josesk.javameengine.sound;

import josesk.javameengine.Sound;
import josesk.undertaleme.GameEngine;

public class NonSyncSound {
	
	public final static byte ERROR_LOADING=0;
	public final static byte ERROR_PLAYING=1;
	public final static byte ERROR_STOPPING=2;
	public final static byte ERROR_TIMEOUT=3;
	
	public final static long NO_TIMEOUT=-1;
	
	private Thread t;
	private Sound s;
	private String source;
	private long timeout_millis;
	private boolean canceled;
	
	private static int timeouts=0;
	
	/**
	 * Sound class that'll Load, Play and Close on a new thread, without timeouts. Useful for SFX that need to be played quickly, without delaying the frame
	 * @param Source
	 * @throws Error
	 * @throws Exception
	 */
	public NonSyncSound(String source) throws Error, Exception{
		
		timeout_millis = NO_TIMEOUT;
		
		this.source = source;
		reload();
	}
	
	/**
	 * Sound class that'll Load, Play and Close on a new thread. Useful for SFX that need to be played quickly, without delaying the frame
	 * @param Source
	 * @param Timeout time in millis
	 * @throws Error
	 * @throws Exception
	 */
	public NonSyncSound(String source, long timeout_millis) throws Error, Exception {
		
		this.timeout_millis = timeout_millis;
		
		this.source = source;
		reload();
		
	}
	
	/**
	 * Sets timeout time in millis
	 * @param timeout_millis
	 */
	public final void setTimeoutMillis(long timeout_millis) {
		this.timeout_millis = timeout_millis;
	}
	
	/**
	 * Returns timeout time
	 * @return timeout_millis
	 */
	public final long getTimeoutMillis() {
		return timeout_millis;
	}
	
	/**
	 * Returns number of timeouts from systemtime. For debugging purposes
	 * @return timeouts
	 */
	public final static int getTimeOuts() {
		return timeouts;
	}
	
	/**
	 * Reload the sound in a new thread
	 */
	public final void reload() {
		
		new Threader(new Object[] {source}, Threader.THREAD_LOAD);
		
	}
	
	/**
	 * Returns the original Sound class
	 * @return Sound
	 * @throws Error
	 * @throws Exception
	 */
	public final Sound getSound()  throws Error, Exception{
		
		while(s==null && !canceled) {
			Thread.sleep(1);
		}
		
		return s;
		
	}
	
	/**
	 * Play audio in a new thread
	 */
	public final void Play() {
		new Threader(null, Threader.THREAD_PLAY);
	}
	
	/**
	 * Stop audio in a new thread
	 */
	public final void Stop() {
		
		t = new Thread(new Stop());
		t.start();
	}
	
	private final void onerror(byte error) {
		onError(error);
	}
	protected void onError(byte error) {}
	protected void onLoad() {}
	protected void onPlay() {}
	protected void onStop() {}
	
	private class TimedThread implements Runnable{
		
		private boolean finished=false, error=false;
		
		public void run() {}
		
		protected final void finish() {
			finished = true;
		}
		protected final void error() {
			error = true;
		}
		protected final boolean isError() {
			return error;
		}
		private final boolean isFinished() {
			return finished;
		}
		
		public boolean InterruptMe() {
			return true;
		}
		
		public void timeoutException() {
			GameEngine.DebugWarn("Unknown Timeout exception");
		}
		
	}
	
	private class Load extends TimedThread{

		String source;
		private boolean interrupted=false;
		
		private Load(String source) {
			this.source = source;
			
			new Thread(this).start();
			
		}
		
		public void run() {
			
			try {
				if(s==null) s = new Sound(source);
				if (s.Load()) {
					finish();
					onLoad();
				}else {
					error();
				}
			} catch (Error e) {
				GameEngine.DebugError("Error loading desync sound: "+source);
				GameEngine.DebugError(e.toString());
				error();
				onerror(ERROR_LOADING);
			} catch (Exception e) {
				GameEngine.DebugError("Exception loading desync sound: "+source);
				GameEngine.DebugError(e.toString());
				error();
				onerror(ERROR_LOADING);
			}
			
		}
		
		
		public void timeoutException() {
			//GameEngine.DebugWarn("TimeoutException loading desync sound: "+source);
			interrupted=true;
		}
	}
	
	private class Play extends TimedThread{

		private boolean interrupted=false;
		
		public void run() {
			
			try {
				Sound s2 = getSound();
				if(s2!=null) {
					if(s2.Start()) {
						finish();
						onPlay();
					}else {
						error();
					}
				}
			}catch (Exception e) {
				GameEngine.DebugError("Error playing desync sound: "+source);
				GameEngine.DebugError(e.toString());
				error();
				onerror(ERROR_PLAYING);
			} catch (Error e) {
				GameEngine.DebugError("Exception playing desync sound: "+source);
				GameEngine.DebugError(e.toString());
				error();
				onerror(ERROR_PLAYING);
			}
			
		}
		
		public void timeoutException() {
			//GameEngine.DebugWarn("TimeoutException playing desync sound: "+source);
			interrupted=true;
			
		}
		
		public boolean InterruptMe() {
			
			if(s==null) return true;
			
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {/*ignore*/}
			
			try {
				if(!s.isPlaying()) return true;
			}catch(Exception e) {
				return true;
			}catch(Error e) {
				return true;
			}
			
			return false;
		}
		
	}
	
	private class Stop extends TimedThread{
		
		public void run() {
			
			try {
				Sound s2 = getSound();
				s2.Stop();
				s2.Close();
				finish();
				onStop();
			}catch (Exception e) {
				GameEngine.DebugError("Error stopping desync sound: "+source);
				GameEngine.DebugError(e.toString());
				error();
				onerror(ERROR_STOPPING);
			} catch (Error e) {
				GameEngine.DebugError("Exception stopping desync sound: "+source);
				GameEngine.DebugError(e.toString());
				error();
				onerror(ERROR_STOPPING);
			}
			
		}
		
	}
	
	private class Threader implements Runnable{
		
		public final static byte THREAD_LOAD=0;
		public final static byte THREAD_PLAY=1;
		
		private long threadBegin;
		private byte thread;
		private Object[] vars;
		private TimedThread tt;
		private Thread t;
		private Threader(Object[] vars, byte thread) {
			this.thread = thread;
			this.vars = vars;
			
			new Thread(this).start();
			
		}
		
		public void run() {
			
			canceled=false;
			
			switch(thread) {
				case THREAD_LOAD:
					tt = new Load((String)vars[0]);
				break;
				case THREAD_PLAY:
					tt = new Play();
				break;
			}
			
			t = new Thread(tt);
			threadBegin = System.currentTimeMillis();
			t.start();
			while(!tt.isFinished() && !tt.isError()) {
				if(timeout_millis!=NO_TIMEOUT && System.currentTimeMillis()-threadBegin>timeout_millis && tt.InterruptMe()) {
					timeouts++;
					if(s!=null) s.Cancel();
					canceled=true;
					tt.timeoutException();
					onerror(ERROR_TIMEOUT);
					break;
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {/*ignore*/}
			}
			
			tt = null;
			t = null;
			
		}
		
		
		
	}

}
