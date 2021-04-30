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
*TODO: Free and dynamic button mode for creative developers
*/

package josesk.undertaleme.layout;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.Sprite;

import josesk.undertaleme.GameEngine;
import josesk.undertaleme.PaintableObject;

public class ActionButton implements PaintableObject{
	
	public static final byte BUTTON_FIGHT = 0;
	public static final byte BUTTON_ACT = 1;
	public static final byte BUTTON_ITEM = 2;
	public static final byte BUTTON_MERCY = 3;
	
	private static boolean compact = false;
	public final static int width = 54;
	public final static int height = 19;
	protected final static int min_dist = 15;
	
	private static Sprite sel, unsel, selTitle, Title;
	private Sprite tSel, tUnsel, tSelTitle, tTitle;
	
	private boolean selected;
	private boolean active;
	private static boolean showIcon = false;
	private byte button;
	private int x, y;
	private static int w;
	
	/**
	 * 
	 * @param Button type (BUTTON_####)
	 */
	public ActionButton(byte button) {
		
		selected = false;
		this.button = button;
		active = true;
		
		updateSprites();
		UpdatePos();
		
	}
	
	/**
	 * 
	 * @param Button type (BUTTON_####)
	 * @param Visible
	 */
	public ActionButton(byte button, boolean active) {
		
		selected = false;
		this.button = button;
		this.active = active;
		
		updateSprites();
		UpdatePos();
		
	}
	
	private void updateSprites() {
		
		tSel = new Sprite(sel);
		tUnsel = new Sprite(unsel);
		tSelTitle = new Sprite(selTitle);
		tTitle = new Sprite(Title);
		
		tSel.setFrame(button);
		tUnsel.setFrame(button);
		tSelTitle.setFrame(button);
		tTitle.setFrame(button);
		
	}
	
	/*
	 * Update position to screen's resolution
	 */
	public void UpdatePos() {

		y = GameEngine.getCanvas().getResolutionHeight()-height-6;
		
		switch(button) {
		
			case BUTTON_FIGHT:
				x = GameEngine.getCanvas().getResolutionWidth()/27;
			break;
				
			case BUTTON_ACT:
				x = (int)(GameEngine.getCanvas().getResolutionWidth()/3.5556f);
			break;
				
			case BUTTON_ITEM:
				x = GameEngine.getCanvas().getResolutionWidth() - ((int)(GameEngine.getCanvas().getResolutionWidth()/3.5556f)) - w;
			break;
				
			case BUTTON_MERCY:
				x = GameEngine.getCanvas().getResolutionWidth()- (GameEngine.getCanvas().getResolutionWidth()/27) - w;
			break;
		
		}
		
	}

	public void paint(Graphics g) {
		
		if(active) {	
			
			Sprite icon = tUnsel;
			Sprite title = tTitle;
			if(selected) {
				icon=tSel; 
				title = tSelTitle;
			}
			
			if((icon!=null && title!=null)) {
				
				if(showIcon||!selected) {
					icon.setPosition(x, y);
					icon.paint(g);
				}
				
				if(!compact) {
					title.setPosition(x+15, y);
					title.paint(g);
				}
			}
			
			if(selected) g.setColor(0xf3fb03); else g.setColor(0xfa8132);
			g.drawRect(x-1, y-1, w+2, height+2);
		}
		
	}

	public void Update() {
		
		
		
	}
	
	/**
	 * Compact button if needed
	 */
	public static void autoCompact() {
		
		int testx1 = GameEngine.getCanvas().getResolutionWidth()/27;
		int testx2 = (int)(GameEngine.getCanvas().getResolutionWidth()/3.5556f);
		w = width;
		
		if(testx1+width+min_dist>=testx2) compact=true; else compact=false;
		if(compact) w = 16;
		
	}
	
	/**
	 * Check if button is compact
	 * @return compact
	 */
	public static boolean isCompact() {
		
		return compact;
		
	}
	
	/**
	 * Set button sprites
	 * @param selected
	 * @param unselected
	 * @param selectedTitle
	 * @param unselectedTitle
	 */
	public static void setSprites(Sprite sel, Sprite unsel, Sprite selTitle, Sprite Title) {
		ActionButton.sel = sel;
		ActionButton.unsel = unsel;
		ActionButton.selTitle = selTitle;
		ActionButton.Title = Title;
	}
	
	/**
	 * Set button active/unactive
	 * @param active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * Check if button is active
	 * @return
	 */
	public boolean isActive() {
		return active;
	}
	
	/**
	 * Check if button is selected
	 * @return selected
	 */
	public boolean isSelected() {
		return selected;
	}
	
	/**
	 * Select button
	 */
	public void select() {
		selected = true;
	}
	
	/**
	 * Deselect button
	 */
	public void unselect() {
		selected = false;
	}
	
	/**
	 * Check if something is inside this button
	 * @param object_x
	 * @param object_y
	 * @param object_width
	 * @param object_height
	 * @return insideMe
	 */
	public boolean insideMe(int x, int y, int width, int height) {
		
		if(x+width>=this.x && x<=this.x+ActionButton.w && y+height>=this.y && y<this.y+ActionButton.height) {
			return true;
		}
		
		return false;
		
	}
	
	public final int getRelativeX() {
		return x;
	}
	public final int getRelativeY() {
		return y;
	}
	
	/**
	 * Returns button type
	 * @return buttonType
	 */
	public final byte getButtonType() {
		return button;
	}

	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) {
		
		autoCompact();
		UpdatePos();
		
	}
	
	/**
	 * Show icon
	 */
	public static void showIcon() {
		
		showIcon = true;
		
	}
	
	/**
	 * Hide icon
	 */
	public static void hideIcon() {
		
		showIcon = false;
		
	}

}
