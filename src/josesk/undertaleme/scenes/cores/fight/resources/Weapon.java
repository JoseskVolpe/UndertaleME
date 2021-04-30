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

import javax.microedition.lcdui.Graphics;

import com.j2mearmyknife.image.sources.SmartImage;
import com.j2mearmyknife.image.transformations.ImageTransformationColorize;
import com.j2mearmyknife.image.transformations.ImageTransformationResize;

import josesk.javameengine.RandomAl;
import josesk.javameengine.Sync;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.layout.FightBar;
import josesk.undertaleme.layout.Player;
import josesk.undertaleme.scenes.cores.Fight;

public class Weapon {
	
	public final static byte BAR_BEGIN_LEFT=0;
	public final static byte BAR_BEGIN_RIGHT=1;
	public final static byte BAR_BEGIN_RANDOM=2;
	
	public final static boolean BAR_KEEP = false;
	public final static boolean BAR_DISAPEAR=true;
	
	protected boolean barDisapear = BAR_KEEP; //Will the bar disapear when hit?
	protected short atk=1; //ATK level
	protected short barCount=1; //How many bars
	protected float barVel=0.8f; //Bar velocity
	protected byte bar_begin = BAR_BEGIN_RANDOM; //Side the bars will begin
	protected short bar_mindelay = 80; //Min delay for add bar
	protected short bar_maxdelay = 300; //Max delay for add ba
	protected String Name = "Weapon"; //Name of the weapon
	protected AttackEffect attackEffect = new AttackEffect(); //Attack Effect of the weapon
	
	private double lastPrecision=0;
	private Player player;
	
	/**
	 * Attack monster
	 * @param fightscene
	 * @param monster
	 * @param precision
	 * @throws Error
	 * @throws Exception
	 */
	public final void attack(Fight fightscene, Monster monster, double precision) throws Error, Exception {
		
		onAttack(fightscene, monster, precision);
		
		lastPrecision = precision;
		
	}
	
	/**
	 * When it's time to attack
	 * @param fightscene
	 * @param monster
	 * @param precision
	 */
	protected void onAttack(Fight fightscene, Monster monster, double precision) {
		
		GameEngine.DebugWarn("Weapon "+Name+"'s onAttack() not defined");
		
	}
	
	/**
	 * When player hit center during FightBar or he missed out
	 * @param fightscene
	 * @param monster
	 * @param iD
	 * @param precision
	 */
	public void onHit(Fight fightscene, Monster monster, int iD, double precision) {
		
		GameEngine.DebugWarn("Weapon "+Name+"'s onHit() not defined");
		
	}
	
	/**
	 * When player receives damage
	 * @param fightscene
	 * @param monster
	 * @param damage
	 */
	public void onPlayerDamage(Fight fightscene, Monster monster, int damage) {
		
		GameEngine.DebugWarn("Weapon "+Name+"'s onPlayerDamage() not defined");
		
	}
	
	/**
	 * When attacked monster finalizes his attack
	 * @param fightscene
	 * @param monster
	 */
	public void onMonsterAttackFinish(Fight fightscene, Monster monster) {
		
		GameEngine.DebugWarn("Weapon "+Name+"'s onMonsterAttackFinish() not defined");
		
	}
	
	/**
	 * When weapon is equipped
	 * @throws Exception
	 * @throws Error
	 */
	protected void onEquip() throws Exception, Error{
		GameEngine.DebugWarn("Weapon "+Name+"'s onEquip() not defined");
	}
	/**
	 * When weapon is unequipped
	 * @throws Exception
	 * @throws Error
	 */
	protected void onUnequip() throws Exception, Error{
		GameEngine.DebugWarn("Weapon "+Name+"'s onUnequip() not defined");
	}
	
	/**
	 * Equip weapon
	 * @param player
	 * @throws Exception
	 * @throws Error
	 */
	public final void equip(Player player) throws Exception, Error {
		this.player = player;
		attackEffect.load();
		onEquip();
	}
	
	/**
	 * Unequip weapon
	 * @throws Exception
	 * @throws Error
	 */
	public final void unequip() throws Exception, Error{
		
		attackEffect.unload();
		onUnequip();
		
	}
	
	/**
	 * Returns attack effect
	 * @return
	 */
	public final AttackEffect getAttackEffect() {
		return attackEffect;
	}
	
	/**
	 * Returns player
	 * @return player
	 */
	public final Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns barCount
	 * @return barCount
	 */
	public final short getBarCount() {
		return barCount;
	}
	
	/**
	 * Returns ATK level
	 * @return ATK
	 */
	public final short getATK() {
		return atk;
	}
	
	/**
	 * Returns name
	 * @return name
	 */
	public final String getName() {
		return Name;
	}
	
	/**
	 * Returns bar beginning side
	 * @return barBeginSide
	 */
	public final byte getBarBegin() {
		if(bar_begin<0) return 0;
		if(bar_begin>2) return 2;
		
		return bar_begin;
	}
	
	/**
	 * Returns bar velocity
	 * @return barVelocity
	 */
	public final float getBarVel() {
		return barVel;
	}
	
	/**
	 * Returns last precision
	 * @return lastPrecision
	 */
	protected final double getLastPrecision() {
		return lastPrecision;
	}
	
	
	/**
	 * Returns bar minimum and maximum delay
	 * @return min and max delay
	 */
	public final short[] getDelay() {
		return new short[] {bar_mindelay, bar_maxdelay};
	}
	
	public final static class Bar{
		
		private float x;
		private int size;
		private float vel;
		private boolean hit, invertColor, finished;
		private FightBar fb;
		private Sync animationSync;
		private Weapon w;
		private byte Cr,Cg,Cb;
		
		public Bar(byte bar_begin, Weapon weapon, FightBar fb) {
			
			this.fb = fb;
			this.w = weapon;
			
			hit = false;
			invertColor=false;
			finished=false;
			size=0;
			
			switch(bar_begin) {
				case BAR_BEGIN_LEFT:
					x=0;
					vel=(weapon.getBarVel()/10.f)+1;
					if(vel==0.f) vel=1;
				break;
				case BAR_BEGIN_RIGHT:
					x=100;
					vel=((weapon.getBarVel()/10.f)+1)*-1;
					if(vel==0.f) vel=-1;
				break;
				case BAR_BEGIN_RANDOM:
					int r = RandomAl.generateRandom(0, 1);
					vel=(weapon.getBarVel()/10.f)+1;
					if(vel<=0) vel=1;
					if(r==0) {
						x=0;
					}else {
						x=100;
						vel=vel*-1;
					}
				break;
			}
			
		}
		
		public final boolean isFinished() {
			return finished;
		}
		
		protected final void finish() {
			finished=true;
		}
		
		protected final void removeBar() { //Useful to free memory
			
			fb.removeBar(this);
			
		}
		
		public void hit() {
			
			if(!hit) {
				
				int d = 50;
				if(w.getBarCount()>1) d=10;
				
				animationSync = new Sync(d, true);
				animationSync.begin();
				
				Cr = (byte) RandomAl.generateRandom(0, 255);
				Cg = (byte) RandomAl.generateRandom(Cr, 255);
				Cb = (byte) RandomAl.generateRandom(Cr+Cg, 255);
				
				hit=true;
				fb.barHit((Math.min(x, 50d)/Math.max(x, 50d)));
				finish();
			}
			
		}
		
		public void cancel() {
			
			if(w.barDisapear) {
				finish();
				fb.barHit(0);
				removeBar();
			}else{
				//Single bar
				
				animationSync = new Sync(50, true);
				animationSync.begin();
				
				hit=true;
				
				finish();
				fb.barHit(0);
			}
			
		}
		
		public void UpdateMove() {

			x+=vel;
			if(x>100 || x<0) {
				if(x>100) x=100;
				if(x<0) x=0;
					
				cancel();
					
			}
			
		}
		
		public void UpdateHit() {
			
			if(animationSync.isTimedOut()) {
				int lateRate = animationSync.lateRate();
				
				for(int j=0; j<lateRate; j++) {
					if(w.barDisapear) {
						
						size++;
						if(size>=25) {
						//if((GameEngine.getGraphicsQuality()==GameEngine.GRAPHICS_HIGH && size>=25)||(GameEngine.getGraphicsQuality()==GameEngine.GRAPHICS_LOW&&size>=Math.max(GameEngine.getCanvas().getWidth(), GameEngine.getCanvas().getHeight()))) {
							removeBar();
						}
						
					}else {
						invertColor=!invertColor;
					}
				}
				
				animationSync.reset(lateRate);
				
			}
			
		}
		
		public final float getX() {
			return x;
		}
		
		public void render(Graphics g, int x, int y) { //'x' is calculated by the core, FightBar.java
			
			if(!(w.barDisapear)||!hit) {
				if(!invertColor)g.setColor(0x000000);else g.setColor(0xffffff);
				g.fillRect(x, y, 8, 65);
				if(!invertColor)g.setColor(0xffffff);else g.setColor(0x000000);
				g.fillRect(x+2, y+2, 4, 61);
			}else {
				
				int color = 0x30ff00;
				
				if(GameEngine.getGraphicsQuality()==GameEngine.GRAPHICS_HIGH) {
					//High graphics
					SmartImage si = new SmartImage(8, 65);
					si.fillRect(0, 0, 8, 65, 0xffffffff);
					ImageTransformationColorize c = new ImageTransformationColorize();
					ImageTransformationResize r = new ImageTransformationResize();
					r.setTargetDimensions(8+size, 65+size);
					si = (SmartImage)r.process(si);
					c.setAlphaOffset((int)(-1*(255*size/25.f)));
					c.setRedOffset(Cr*-1);
					c.setGreenOffset(Cg*-1);
					c.setBlueOffset(Cb*-1);
					si = (SmartImage)c.process(si);
					si.drawOnGraphics(g, x-(size/2), y-(size/2), 0);
					si = null;
					r = null;
					c = null;
				}else {
					//Low graphics
					byte cX = 8/2;
					byte cY = 65/2;
					
					byte jump=(byte)Math.ceil(size/5.f);
					if(jump<=0) jump=1;
					
					for(byte Dy=0; Dy<65; Dy+=jump) {
						for(byte Dx=0; Dx<8; Dx+=jump) {
							g.fillRect((int)((x+Dx)+(((Dx-cX)/(float)cX)*size)), (int)((y+Dy)+(((Dy-cY)/(float)cY)*size)), 1 ,1);
						}
					}
					
				}
			}
			
		}
		
	}

}
