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

package josesk.undertaleme;
import javax.microedition.lcdui.Graphics;

import josesk.undertaleme.layout.Dialog;

/**
 * GameView class provides an interface
 * for the game objects views.
 */
public abstract class GameView implements Runnable{
	
	protected Dialog dialog;
	private Thread thisThread;
	
	
	public GameView() {
	}
	
	/**
	 * Pulls data to a new GameView
	 * @param data
	 */
	public GameView(GameViewData data) {
		this.data = data;
	}
	
	protected GameViewData data = new GameViewData();
	
	/**
	 * Renders the object's view.
	 * 
	 * @param g Graphics instance.
	 * @throws Exception 
	 * @throws Error 
	 */
	
	/**
	 * When the game engine swaps to this GameView
	 * @throws Error
	 * @throws Exception
	 */
	public void onGameViewBegin() throws Error, Exception {
		
		
		
	}
	
	/**
	 * When the game engine finish this GameView
	 * @throws Error
	 * @throws Exception
	 */
	public void onGameViewDestroy() throws Error, Exception{}
	
	public final void beginThread(Thread thisThread) {
		this.thisThread = thisThread;
		this.thisThread.start();
	}
	
	/**
	 * Returns main thread
	 * @return Thread
	 */
	public final Thread getThread() {
		return thisThread;
	}
	
	public final boolean onerror(String error)  throws Exception, Error{
		
		boolean stop = onError(error);
		if(stop) {
			dialog = null;
			if(thisThread!=null) {
				thisThread.interrupt();
				thisThread = null;
			}
		}
		return stop;
		
	}
	
	/**
	 * When the engine returns an error (returns Crash game)
	 * @param error
	 * @return Crash game
	 * @throws Exception
	 * @throws Error
	 */
	protected boolean onError(String error) throws Exception, Error{
		return true;
	}
	
	
	/**
	 * When game resolution changes
	 * @param lastWidth
	 * @param lastHeight
	 * @param widthDifference
	 * @param heightDifference
	 * @throws Exception
	 * @throws Error
	 */
	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) throws Exception, Error {}
	
	public final void run() {
		
		try {
			Run();
		}catch(Exception e){
			if(!e.toString().equals("java.lang.InterruptedException")) GameEngine.getCanvas().forceError(e);
		}catch(Error e) {
			GameEngine.getCanvas().forceError(e);
		}
		
	}
	
	/**
	 * Thread methods
	 * @throws Error
	 * @throws Exception
	 */
	protected void Run() throws Error, Exception{
		
		
		
	}
	
	public final void paint(Graphics g) throws Error, Exception{
		
		render(g);
		
		if(dialog!=null) dialog.paint(g);
		
	}
	public final void update(float delta) throws Error, Exception{
		
		Update(delta);
		
		if(dialog!=null) dialog.Update(delta);
		
	}
	
	/**
	 * Use it to render stuff
	 * @param g
	 * @throws Error
	 * @throws Exception
	 */
	protected abstract void render(Graphics g);
	
	/**
	 * Use it to update frames
	 * Hint: Use Sync class for FPS-synchronization
	 * TODO: Delta-time
	 * @throws Error
	 * @throws Exception
	 */
	protected abstract void Update(float delta);
	
	public final GameViewData getData() {
		
		return data;
		
	}
	
	public final void onkeydown(String command) throws Error, Exception{
		
		if(onKeyDown(command)) {
			
			if(command=="Ok" && dialog!=null) {
				
				dialog.actionButton();
				
			}
			
		}
		
	}
	
	public final void onkeyup(String command) throws Error, Exception{
		
		if(onKeyUp(command)) {
			
			
			
		}
		
	}
	
	public final void ondialogend(String filename) {
		
		if(onDialogEnd(filename)) {
			
			dialog = null;
			
		}
		
	}
	
	/**
	 * When dialog ends
	 * @param fileName
	 * @return
	 */
	protected boolean onDialogEnd(String fileName) {
		
		return true;
		
	}
	
	/**
	 * When player presses another key (return true to run main onkeyDown method)
	 * @param command
	 * @return Run main onKeyDown method
	 * @throws Error
	 * @throws Exception
	 */
	protected boolean onKeyDown(String command) throws Error, Exception{
		
		return true;
		
	}
	
	/**
	 * When player releases another key (return true to run main onkeyUp method)
	 * @param command
	 * @return Run main onKeyUp method
	 * @throws Error
	 * @throws Exception
	 */
	protected boolean onKeyUp(String command) throws Error, Exception{
		
		return true;
		
	}
	
}
