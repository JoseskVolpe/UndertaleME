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
import javax.microedition.lcdui.Image;

import com.j2mearmyknife.image.sources.SmartImage;
import com.j2mearmyknife.image.transformations.ImageTransformationColorize;
import com.j2mearmyknife.image.transformations.ImageTransformationResize;
import com.j2mearmyknife.image.transformations.ImageTransformationSmooth;
import com.j2mearmyknife.interfaces.AbstractImage;

import josesk.javameengine.RandomAl;
import josesk.javameengine.Sync;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.scenes.cores.Fight;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;
import josesk.undertaleme.scenes.cores.fight.resources.Weapon;
import josesk.undertaleme.scenes.cores.fight.resources.Weapon.Bar;

public class FightBar {
	
	private static SmartImage sImage;
	private static AbstractImage renderImage;
	private static int originalWidth, originalHeight;
	private static ImageTransformationResize size;
	private static ImageTransformationColorize color;
	
	private int width, height, x, y, w, h;
	//private int color[];
	private boolean animating;
	private Weapon.Bar Bars[];
	private byte precision[];
	private short barIndex, hitIndex;
	private Weapon weapon;
	private Monster selectedMonster;
	private Fight fightscene;
	
	public FightBar() {
		
		height = originalHeight;
		animating = false;
		
		stretch(100);
		
		size.setTargetDimensions(originalWidth, originalHeight);
		
	}
	
	/**
	 * Assign loaded fight bar sprite
	 * @param battlebarSprite
	 */
	public static final void assignImage(Image battlebar) {;
		if(battlebar!=null) {
			originalWidth = battlebar.getWidth();
			originalHeight = battlebar.getHeight();
		}
		
		sImage = new SmartImage(battlebar);
		size = new ImageTransformationResize();
		color = new ImageTransformationColorize();
		//smooth = new ImageTransformationSmooth();
		size.setTargetDimensions(originalWidth, originalHeight);
		
	}
	
	/**
	 * Removes bar
	 * @param bar
	 */
	public final void removeBar(Weapon.Bar bar) {
		
		if(Bars!=null) {
			for(int i=0; i<Bars.length; i++) {
				if(Bars[i]==bar) {
					Bars[i]=null;
					return;
				}
			}
		}
		
	}
	
	public final void render(Graphics g) {
		
		int w2 = w;
		
		int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/120;
		if(bs<=0)bs=1;
		int frameSize = (int)(GameEngine.getCanvas().getResolutionWidth()/1.131f);
		x = (GameEngine.getCanvas().getResolutionWidth()/20)+bs+(frameSize/2)-(w2/2);
		y = GameEngine.getCanvas().getResolutionHeight()-114+bs;
		
		if(renderImage!=null) renderImage.drawOnGraphics(g, x, y, 0);
		
		if(Bars!=null) {
			try {
				for(int i=0; Bars!=null&&i<Bars.length; i++) {
					if(Bars==null) break;
					if(Bars[i]!=null) {
						try {
							Bars[i].render(g, (int)(x+(width*(Bars[i].getX()/100.f))-4), y);
						}catch(NullPointerException e){
							GameEngine.DebugWarn("*FightBar: SOS (1) >w<");
						}
					}
				}
			}catch(NullPointerException e){
				GameEngine.DebugWarn("*FightBar: SOS (2) >w<");
				GameEngine.DebugWarn(e.toString());
			}
		}
		
	}
	
	/**
	 * Fade out bar
	 */
	public void fadeOut() {
		new Thread(new fadeOutAnimation()).start();
		
	}
	
	/**
	 * begin challenge
	 * @param fightScene
	 * @param monster
	 * @param weapon
	 * @return precision
	 * @throws Error
	 * @throws Exception
	 */
	public double beginStrengthChallenge(Fight fightscene, Monster monster, Weapon weapon) throws Error, Exception {
		
		this.selectedMonster = monster;
		
		stretch(100);
		
		this.weapon = weapon;
		Bars = new Weapon.Bar[weapon.getBarCount()];
		precision = new byte[weapon.getBarCount()];
		barIndex = 0;
		hitIndex=0;
		
		new Thread(new StrengthMeter()).start();
		waitForAnimation();
		
		
		double p=0;
		for(int i=0; i<precision.length; i++) {
			p+=(precision[i]/100.d);
		}
		p/=(double)(precision.length);
		
		precision = null;
		
		weapon.attack(fightscene, monster, p);
		
		return p;
		
	}
	
	/**
	 * Hit (Player press Action button)
	 */
	public void hit() {
		
		if(Bars!=null && hitIndex<Bars.length) {
			try {
				Bars[hitIndex].hit();
			}catch(NullPointerException e){
				GameEngine.DebugWarn("*FightBar: SOS (3) >w<");
				GameEngine.DebugWarn(e.toString());
			}
		}
		
	}
	
	/**
	 * Stretch fight bar
	 * @param stretchPercent
	 */
	public final void stretch(int stretch) {
		
		width = (int)((GameEngine.getCanvas().getResolutionWidth()/1.131f)*(stretch/100.f));
		
		w = width;
		h = height;
		
		//Gotta render it from the Update() method, we don't want it to render unnecessary from the Draw() disturbing the player with lag during the bar hitting, ¿eh?
		size.setTargetDimensions(w, h);
		//smooth.setMode(ImageTransformationSmooth.FAVOR_SPEED);
		//smooth.setPasses(1);
		color.setAlphaOffset(-1*(255-(int)(255*(stretch/100.f))));
		
		AbstractImage tR = sImage.clone();
		
		tR= size.process(sImage);
		//if(GameEngine.getGraphicsQuality()) tR = smooth.process(tR); //Smooth it if graphics quality is set to HIGH (disabled - looks so ugly when smooth from real hardware lol)
		if(GameEngine.getGraphicsQuality()) tR = color.process(tR);
		renderImage = tR;
		tR = null;
		
	}
	
	/**
	 * Wait for fade animation
	 */
	public final void waitForAnimation() {
		
		while(animating) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
		}
		
	}
	
	/**
	 * Returns this
	 * @return FightBar
	 */
	private final FightBar getActualFightBar() {
		return this;
	}
	
	/**
	 * Adds bar
	 * @param bar
	 */
	private final void addBar(Weapon.Bar bar) {
		Bars[barIndex] = bar;
		barIndex++;
	}
	
	/**
	 * Bar hit. Don't use this
	 * @param precision
	 */
	public final void barHit(double precision) {
		this.precision[hitIndex] = (byte)(precision*100);
		hitIndex++;
		weapon.onHit(fightscene, selectedMonster, hitIndex-1, precision);
	}
	
	class fadeOutAnimation implements Runnable{
		
		public fadeOutAnimation() {
			
			animating = true;
			
		}
		
		public void run() {
			
			int stretch=100;
			Bars = null;
			Sync sync = new Sync(4, true);
			sync.begin();
			
			while(stretch>0) {
				if(sync.isTimedOut()) {
					
					int lateRate = sync.lateRate();
					
					for(int i=0; i<lateRate; i++) {
						
						stretch--;
						if(stretch<=0) {
							stretch=0;
							sync=null;
							break;
						}
						
					}
					if(stretch<=0) stretch=0;
					
					if(sync!=null) sync.reset(lateRate);
					
					stretch(stretch);
					
				}
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
			}
			animating=false;
			renderImage = null; //Free from the memory
			Bars = null;
			
		}
		
	}
	
	class StrengthMeter implements Runnable {
		
		public StrengthMeter() {
			
			animating = true;
			
			new Thread(new BarsUpdate()).start();
			
		}

		public void run(){
			
			Sync sync = new Sync();
			
			for(int i=0; i<Bars.length; i++) {
				
				try {
					
					Bar bar = new Weapon.Bar(weapon.getBarBegin(), weapon, getActualFightBar());
					
					addBar(bar);
					
					sync = new Sync(RandomAl.generateRandom(weapon.getDelay()[0], weapon.getDelay()[1]), true);
					sync.begin();
					
					while(!sync.isTimedOut()) {
						Thread.sleep(1);
					}
					
				} catch (InterruptedException e) {
					GameEngine.DebugError("Interruption Error on FightBar");
					GameEngine.DebugError(e.toString());
					hitIndex++;
				} catch(Exception e) {
					GameEngine.DebugError("Unexpected exception when adding bar #"+barIndex); //This kind of error or exception doesn't need to crash, only warn it
					GameEngine.DebugError(e.toString());
					hitIndex++;
				} catch(Error e) {
					GameEngine.DebugError("Unexpected error when adding bar #"+barIndex);
					GameEngine.DebugError(e.toString());
					hitIndex++;
				}
				
			}
			
			try {
				
				while(Bars!=null && Bars[Bars.length-1]!=null && !Bars[Bars.length-1].isFinished())
					Thread.sleep(1);
				
			} catch (InterruptedException e) {
				GameEngine.DebugError(e.toString());
			}
			
			animating=false;
		
		}
		
	}
	
	class BarsUpdate implements Runnable{

		private Sync sync;
		
		public void run() {
			
			sync = new Sync((int)(10/weapon.getBarVel()), true);
			sync.begin();
			
			try {
				loop:
				while(Bars!=null) {
					
					for(int i=0; i<hitIndex; i++) {
						if(Bars==null) break loop;
						if(Bars[i]!=null) {
							try {
									
								Bars[i].UpdateHit();
								
							}catch(NullPointerException e){
								//Ignore
							}
						}
					}
				
					if(Bars!=null && hitIndex< Bars.length && sync.isTimedOut()) {
						
						int lateRate = sync.lateRate();
					
						for(int i=hitIndex; Bars!=null&&i<Bars.length; i++) {
							if(Bars==null) break loop;
							if(Bars[i]!=null) {
								try {
									for(int j=0; j<lateRate; j++) {
											if(Bars==null) break;
											Bars[i].UpdateMove();
									}
								}catch(NullPointerException e){
									GameEngine.DebugWarn("*FightBar: SOS (4) >w<");
									GameEngine.DebugWarn(e.toString());
								}
							}
						}
						
						sync.reset(lateRate);
						
					}
				
				}
			
			}catch(NullPointerException e){/*Ignore*/}
			
			sync = null;
			
		}
		
	}

}


