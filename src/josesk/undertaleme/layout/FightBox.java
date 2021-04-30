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

import josesk.javameengine.Sync;
import josesk.undertaleme.GameEngine;

public class FightBox{
	
	public final static int DEFAULT_DELAY = 800;
	
	private boolean bottom_visible;
	private boolean top_visible;
	private boolean left_visible;
	private boolean right_visible;
	
	private int xoffset, yoffset;
	private int width, height;
	private boolean background;
	
	private int toX, toY, timesX, timesY;
	private int transTimes;
	private float transX, transY;
	private int toWidth, toHeight;
	private float transW, transH;
	
	private boolean updating;
	private boolean visible;
	private Sync sync;
	
	public FightBox() {
		
		xoffset=0;
		yoffset=0;
		width = 100;
		height = 100;
		toWidth = width;
		toHeight = height;
		toX = xoffset;
		toY = yoffset;
		background = true;
		updating = false;
		visible=true;
		
		bottom_visible=true;
		top_visible=true;
		left_visible=true;
		right_visible=true;
		
	}
	
	public void Update() {
		
		if(sync!=null) {
			updating=true;
			sync.Update();
			
			if(sync.isTimedOut()) {
				
				float late = sync.late();
				int lateRate = sync.lateRate();
				
				for(int i=0; i<lateRate; i++) {
					
					if(timesX<transTimes) {
						
						width+=transW;
						xoffset+=transX;
						timesX++;
						
						if(timesX>=transTimes) {
							xoffset=toX;
							width=toWidth;
						}
						
					}else if(timesY<transTimes){
						
						height+=transH;
						yoffset+=transY;
						timesY++;
						
						if(timesY>=transTimes) {
							yoffset=toY;
							height=toHeight;
						}
						
					}else {
						
						xoffset = toX;
						yoffset = toY;
						width = toWidth;
						height = toHeight;
						sync = null;
						break;
						
					}
					
					
					
				}
				
				if(sync!=null) sync.reset(late);
				
			}
			updating=false;
			
		}
		
	}
	
	public void render(Graphics g) {
		
		if(isVisible()) {
			int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/120;
			if(bs<=0)bs=1;
			
			int centerx = GameEngine.getCanvas().getResolutionWidth()/2;
			//int centery = GameEngine.getCanvas().getResolutionHeight()/2;
			int x = centerx-(width/2);
			int y = (GameEngine.getCanvas().getResolutionHeight()-45-bs)-height;
			
			/*
			if(background) {
				g.setColor(0xffffff);
				g.fillRect(x-bs+xoffset, y-bs+yoffset, width+(bs*2), height+(bs*2));
				g.setColor(0x000000);
				g.fillRect(x+xoffset, y+yoffset, width, height);
			}else {
				g.setColor(0xffffff);
				for(int i=bs; i>0; i--) {
					g.drawRect(x-i+xoffset, y-i+yoffset, width+(i*2), height+(i*2));
				}
			}
			*/
			
			if(background) {
				g.setColor(0x000000);
				g.fillRect(x+xoffset, y+yoffset, width, height);
			}
			g.setColor(0xffffff);
			
			if(isLeftVisible()) {
				for(int i=bs; i>0; i--) {
					
					g.drawLine(x-i+xoffset, y+yoffset-bs, x-i+xoffset, y+yoffset+height);
					
				}
			}
			
			if(isRightVisible()) {
				for(int i=bs; i>0; i--) {
					
					g.drawLine(x+width+i+xoffset, y+yoffset-bs, x+width+i+xoffset, y+yoffset+height);
					
				}
			}
			
			if(isTopVisible()) {
				for(int i=bs; i>0; i--) {
					
					g.drawLine(x+xoffset-bs, y+yoffset-i, x+xoffset+width+bs, y+yoffset-i);
					
				}
			}
			
			if(isBottomVisible()) {
				for(int i=bs; i>0; i--) {
					
					g.drawLine(x+xoffset-bs, y+yoffset+i+height, x+xoffset+width+bs, y+yoffset+i+height);
					
				}
			}
			
		}
			
	}
	
	/**
	 * Enables/disables black background
	 * @param background
	 */
	public void setBackground(boolean background) {
		this.background = background;
	}
	
	/**
	 * Check if background is shown
	 * @return backgroundShown
	 */
	public boolean getBackground() {
		return background;
	}
	
	/**
	 * Transform FightBox's size
	 * @param xoffset
	 * @param yoffset
	 * @param width
	 * @param height
	 * @param animate
	 * @param delay
	 */
	public void transform(int xoffset, int yoffset, int width, int height, boolean animate, int delay) {
		
		if(animate && ((xoffset!=this.xoffset || width!=this.width)||(yoffset!=this.yoffset || height!= this.height))) {
			//System.out.println("OwO");
			toX=xoffset;
			toY=yoffset;
			toWidth=width;
			toHeight=height;
			
			if((xoffset==this.xoffset && width==this.width)||(yoffset==this.yoffset && height==this.height)) delay/=2;
			float fdelay = delay/2;
			if((xoffset==this.xoffset && width==this.width)||(yoffset==this.yoffset && height==this.height)) fdelay=delay;
			float fps = GameEngine.getCanvas().getFPSLimit();
			
			transX = xoffset-this.xoffset;
			transY = yoffset-this.yoffset;
			transW = width-this.width;
			transH = height-this.height;
			transX = transX/(fps/(1000.f/fdelay));
			transY = transY/(fps/(1000.f/fdelay));
			transW = transW/(fps/(1000.f/fdelay));
			transH = transH/(fps/(1000.f/fdelay));
			
			sync = new Sync(delay/(int)fps, true);
			
			transTimes = (int) (fps/(1000.f/fdelay));
			
			timesX=0;
			if(xoffset==this.xoffset && width==this.width) timesX=transTimes;
			timesY=0;
			if(yoffset==this.yoffset && height==this.height) timesY=transTimes;
			
			sync.begin();
		
		}else {
			//System.out.println("UwU");
			this.xoffset = xoffset;
			this.yoffset = yoffset;
			this.width = width;
			this.height = height;
			toWidth = width;
			toHeight = height;
			toX = xoffset;
			toY = yoffset;
			
		}
		
	}
	
	/**
	 * Set offset position
	 * @param xoffset
	 * @param yoffset
	 */
	public void setOffsetPosition(int xoffset, int yoffset) {
		this.xoffset=xoffset;
		this.yoffset=yoffset;
	}
	
	/**
	 * Returns offset X position
	 * @return xOffset
	 */
	public int getXOffset() {
		return this.xoffset;
	}
	
	/**
	 * Returns offset y position
	 * @return yOffset
	 */
	public int getYOffset() {
		return this.yoffset;
	}
	
	/**
	 * Sets size immediately
	 * @param width
	 * @param height
	 */
	public void setSize(int width, int height) {
		
		this.width = width;
		this.height = height;
		
	}
	
	/**
	 * Wait for transform animation
	 */
	public void waitForAnimation() {
		
		while(sync!=null) {
			try{Thread.sleep(1);}catch(InterruptedException e) {}
		}
		
	}
	
	/**
	 * Interrupt transform animation
	 * @param Maintain current size and position
	 */
	public void interruptAnimation(boolean keep) {
		
		while(updating) {
			try{Thread.sleep(1);}catch(InterruptedException e) {}
		}
		
		if(sync!=null) {
			sync = null;
			if(!keep) {
				xoffset = toX;
				yoffset = toY;
				width = toWidth;
				height = toHeight;
			}
			updating = false;
		}
		
	}
	
	/**
	 * Returns center
	 * @param GameWidth
	 * @param GameHeight
	 * @return centerPos
	 */
	public int[] getCenter(int GameWidth, int GameHeight) {
		int centerPos[] = new int[2];
		
		int bs = Math.min(GameWidth, GameHeight)/120;
		if(bs<=0)bs=1;
		
		int centerx = GameWidth/2;
		int x = centerx-(toWidth/2);
		int y = (GameHeight-45-bs)-toHeight;
		
		centerPos[0] = x+xoffset;
		centerPos[1] = y+yoffset;
		
		return centerPos;
		
	}
	
	/**
	 * Sets FightBox visible true/false
	 * @param visible
	 */
	public void setVisible(boolean visible) {
		this.visible=visible;
	}
	/**
	 * Checks if box is visible
	 * @return fightBoxVisible
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Set top line visible
	 * @param visible
	 */
	public void setTopVisible(boolean visible) {
		this.top_visible=visible;
	}
	/**
	 * Check if top is visible true/false
	 * @return visible
	 */
	public boolean isTopVisible() {
		return top_visible;
	}
	/**
	 * Set bottom line visible
	 * @param visible
	 */
	public void setBottomVisible(boolean visible) {
		this.bottom_visible=visible;
	}
	/**
	 * Check if bottom is visible true/false
	 * @return visible
	 */
	public boolean isBottomVisible() {
		return bottom_visible;
	}
	/**
	 * Set left line visible
	 * @param visible
	 */
	public void setLeftVisible(boolean visible) {
		this.left_visible=visible;
	}
	/**
	 * Check if left is visible true/false
	 * @return visible
	 */
	public boolean isLeftVisible() {
		return left_visible;
	}
	/**
	 * Set right line visible
	 * @param visible
	 */
	public void setRightVisible(boolean visible) {
		this.right_visible=visible;
	}
	/**
	 * Check if right is visible true/false
	 * @return visible
	 */
	public boolean isRightVisible() {
		return right_visible;
	}
	
	/**
	 * Checks if object is hitting FightBox's borders
	 * @param object_x
	 * @param object_y
	 * @param object_width
	 * @param object_height
	 * @return colliding true/false
	 */
	public boolean isColliding(int px, int py, int pwidth, int pheight) {
		
		if(isVisible()) {
			int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/120;
			if(bs<=0)bs=1;
			
			int centerx = GameEngine.getCanvas().getResolutionWidth()/2;
			int x = centerx-(toWidth/2)+toX;
			int y = (GameEngine.getCanvas().getResolutionHeight()-45-bs)-toHeight+toY;
			
			if(isLeftVisible() && px>=x-bs && px<=x && py+pheight>=y-bs && py<=y+toHeight) return true;
			if(isLeftVisible() && px+pwidth>=x-bs && px+pwidth<=x && py+pheight>=y-bs && py<=y+toHeight) return true;
			if(isRightVisible() && px+pwidth>=x+toWidth && px+pwidth<=x+toWidth+bs && py+pheight>=y-bs && py<=y+toHeight) return true;
			if(isRightVisible() && px>=x+toWidth && px<=x+toWidth+bs && py+pheight>=y-bs && py<=y+toHeight) return true;
			if(isTopVisible() && py>=y-bs && py<=y && px+pwidth>=x && px<=x+toWidth) return true;
			if(isTopVisible() && py+pheight>=y-bs && py+pheight<=y && px+pwidth>=x && px<=x+toWidth) return true;
			if(isBottomVisible() && py+pheight>=y+toHeight && py+pheight<=y+bs+toHeight && px+pwidth>=x && px<=x+toWidth) return true;
			if(isBottomVisible() && py>=y+toHeight && py<=y+bs+toHeight && px+pwidth>=x && px<=x+toWidth) return true;
		}
		
		return false;
		
	}

}
