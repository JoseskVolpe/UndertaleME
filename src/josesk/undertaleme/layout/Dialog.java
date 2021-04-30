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

import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import josesk.javameengine.Sound;
import josesk.javameengine.Text;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;
import josesk.undertaleme.PaintableObject;
import josesk.undertaleme.scenes.Startup;

public class Dialog implements PaintableObject{
	
	public static final boolean ANCHOR_BOTTOM = false;
	public static final boolean ANCHOR_TOP = true;
	
	private boolean anchor, finished;
	private Sprite head;
	private GameView gameView;
	private Text text;
	private Sound dSound;
	private int phrases;
	private Font font;
	
	/**
	 * Anchor mode:
	 * *ANCHOR_BOTTOM
	 * *ANCHOR_TOP
	 * 
	 * Creates dialog UI
	 * @param text
	 * @param dialogSound
	 * @param anchorMode
	 * @param headSprite
	 * @param gameView
	 */
	public Dialog(Text text, Sound dSound, boolean anchor, Sprite head, GameView gameView) {
		
		this.anchor = anchor;
		this.head = head;
		this.gameView = gameView;
		this.text = text;
		this.dSound = dSound;
		finished = false;
		font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		
		text.setDialog(this);
		
		text.reset();
		phrases = text.getPhrases();
		
	}

	public void paint(Graphics g) {
		
		if(anchor) {
			
			renderTop(g);
			
		}else {
			
			renderBottom(g);
			
		}
		
	}
	
	private void renderTop(Graphics g) {
		
		final float div = 20.f;
		final int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/80;
		final int h = 32+Startup.headSize;
		final int x = (int) Math.floor(GameEngine.getCanvas().getResolutionWidth()/div);
		final int w = GameEngine.getCanvas().getResolutionWidth()-x*2;
		final int y=(GameEngine.getCanvas().getResolutionHeight()/80);
		
		g.setColor(0xffffff);
		g.fillRect(x, y, w, h);
		g.setColor(0x000000);
		g.fillRect(x+bs, y+bs, w-bs*2, h-bs*2);
		
		if(head!=null) {
			
			head.setFrame(text.getHeadFrame());
			head.setPosition(x+(w/20), y+16);
			head.paint(g);
			
		}
		
		renderText(g, x, y, w, h);
		
	}
	
	private void renderBottom(Graphics g) {
		
		final float div = 20.f;
		final int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/80;
		final int h = 32+Startup.headSize;
		final int x = (int) Math.floor(GameEngine.getCanvas().getResolutionWidth()/div);
		final int w = GameEngine.getCanvas().getResolutionWidth()-x*2;
		final int y=GameEngine.getCanvas().getResolutionHeight()-(GameEngine.getCanvas().getResolutionHeight()/80)-h;
		
		g.setColor(0xffffff);
		g.fillRect(x, y, w, h);
		g.setColor(0x000000);
		g.fillRect(x+bs, y+bs, w-bs*2, h-bs*2);
		
		if(head!=null) {
			
			head.setFrame(text.getHeadFrame());
			head.setPosition(x+(w/20), y+16);
			head.paint(g);
			
		}
		
		renderText(g, x, y, w, h);
		
	}
	
	private void renderText(Graphics g, int x, int y, int w, int h) {
		
		int textlim = x+w;
		if(GameEngine.getCanvas().getResolutionWidth()>GameEngine.getCanvas().getResolutionHeight()) {
			textlim = x+(w-(w/3));
		}
		
		final int textx = x+(w/20)+Startup.headSize+(w/18);
		final int texty = y+(h/6);
		final int fontsize = 6;
		
		Text.DrawText(g, 0xffffff, font, text, textlim, textx, texty, true);
		
	}

	public void Update() throws Error, Exception {

		text.Update();
		
	}
	
	/**
	 * Set head frame
	 * @param headFrame
	 */
	public final void setFrame(int frame) {
		
		head.setFrame(frame);
		
	}
	
	/**
	 * Returns headframe
	 * @return headFrame
	 */
	public final int getFrame() {
		
		return head.getFrame();
		
	}
	
	/**
	 * Start texting
	 */
	public final void start() {
		text.start();
	}
	
	public final void onTextStart() throws Exception, Error {
		
		if(dSound!=null)
			dSound.Start();
		
		finished = false;
		
	}
	
	public final void onTextPause() throws Exception, Error{
		
		if(dSound!=null)
			dSound.Stop();
		
	}
	
	public final void onTextResume() throws Exception, Error {
		
		if(dSound!=null)
			dSound.Start();
		
	}
	
	public final void onTextFinish() throws Exception, Error {
		
		if(dSound!=null)
			dSound.Stop();
		
		finished = true;
		
	}
	
	public final void actionButton() throws Exception, Error {
		
		if(finished) {
			
			if(text.getPhrase()<phrases-1) {
			
				text.nextPhrase();
				text.start();
				
			}else {
				
				gameView.ondialogend(text.getFileName());
				
			}
			
		}else {
			
			text.skip();
			
		}
		
	}
	
	/**
	 * Get head spritesheet
	 * @return headSritesheet
	 */
	public Sprite getHeadSprite() {
		
		return head;
		
	}

	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) {}

}
