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

package josesk.undertaleme.layout;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import josesk.undertaleme.GameEngine;
import josesk.undertaleme.PaintableObject;
import josesk.undertaleme.scenes.cores.Fight;

public class Effect implements PaintableObject, Runnable{
	
	protected short x, y;
	protected byte frame;
	protected Sprite sprite;
	private boolean animated=false;
	private Fight fightscene;
	private static boolean loaded=false;

	public final void paint(Graphics g) throws Error, Exception {
		
		if(sprite!=null) {
			sprite.setFrame(frame);
			sprite.setPosition(x-(sprite.getWidth()/2), y-(sprite.getHeight()/2));
			sprite.paint(g);
		}
		
		render(g);
	}
	
	/**
	 * Alternative render
	 * @param Graphics
	 * @throws Error
	 * @throws Exception
	 */
	protected void render(Graphics g) throws Error, Exception{}

	public void Update() throws Error, Exception {}

	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) {}
	
	/**
	 * Begin effect
	 * @throws Error
	 * @throws Exception
	 */
	public final void begin() throws Error, Exception{
		animated = false;
		onBegin();
	}
	
	/**
	 * When effect is started
	 * @throws Error
	 * @throws Exception
	 */
	protected void onBegin() throws Error, Exception{}
	
	/**
	 * Load effect
	 * @throws Error
	 * @throws Exception
	 */
	public final void load() throws Error, Exception {
		
		Load();
		loaded=true;
		
	}
	
	/**
	 * Check if effect is loaded
	 * @return loaded
	 */
	public final boolean isLoaded() {
		return loaded;
	}
	
	/**
	 * Unload effect
	 * @throws Error
	 * @throws Exception
	 */
	public void Unload() throws Error, Exception{
		sprite = null;
	}
	
	/**
	 * Add the effects you need to load on this method
	 * @throws Error
	 * @throws Exception
	 */
	protected void Load() throws Error, Exception { //Can't make it static, unfortunately
		GameEngine.DebugWarn("It's advised to load your effect's resources");
	}
	
	/**
	 * Thread run
	 */
	public void run() {}

	/**
	 * Returns effect X position
	 * @return x
	 */
	public final short getX() {
		return x;
	}
	
	/**
	 * Return effect y position
	 * @return y
	 */
	public final short getY() {
		return y;
	}
	
	/**
	 * Tell the game engine this effect's animation is finished
	 */
	protected final void finishAnimation() {
		animated = true;
		fightscene.removeEffect(this);
	}
	
	/**
	 * Assign fight scene GameView
	 * @param fightscene
	 */
	public final void assignFightScene(Fight fightscene) {
		
		this.fightscene = fightscene;
		
	}
	
	/**
	 * Checks if this effect's animation is finished
	 * @return
	 */
	public final boolean isAnimationFinished() {
		return animated;
	}
	
	/**
	 * Sets this effect's spritesheet
	 * @param sprite
	 */
	protected final void startSprite(Sprite sprite) {
		
		this.sprite = new Sprite(sprite);
		
	}
	
}
