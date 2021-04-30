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

import javax.microedition.lcdui.game.Sprite;

import josesk.javameengine.Control;
import josesk.javameengine.Sync;
import josesk.javameengine.sound.AutoDestructiveSound;
import josesk.undertaleme.Game;
import josesk.undertaleme.layout.cores.Bot;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;
import josesk.undertaleme.scenes.cores.fight.resources.Weapon;
import josesk.undertaleme.scenes.cores.fight.resources.playerresources.Backpack;
import josesk.undertaleme.weapons.RealKnife;

public class Player extends Bot{

	private int hp, maxhp, LOVE; //LOVE = Level Of ViolencE, not "love"
	private Backpack backpack;
	private Weapon eqquipedWeapon = new Weapon();
	private byte state;
	int vel = 1;
	
	public final static byte STATE_FREE=0; //Red, moves in the scenario
	public final static byte STATE_FREEZE=1; //Conserve color, player cannot control and it's controlled by the FightScene
	public final static byte STATE_GRAVITY=2; //Blue, player can control himself, but he has a gravity
	public final static byte STATE_DEATH=3; //Broke, player is freeze in his last position
	
	private Sync controlSync;
	
	
	public Player(Sprite s) throws Exception, Error {
		super(s);
		
		maxhp = Integer.valueOf(Game.getGame().getAppProperty("max_hp")).intValue();
		LOVE = Integer.valueOf(Game.getGame().getAppProperty("LOVE")).intValue();
		hp = maxhp;
		
		backpack = new Backpack();
		
		state=STATE_FREE;
		initState(state);
		
		equipWeapon(new RealKnife());
		
	}
	
	private void initState(byte state) { //When changing player's state
		
		switch(state) {
		
		case STATE_FREE:
			
			controlSync = new Sync(10, true); //Move time, synchronized
			controlSync.begin();
			
		break;
		
		case STATE_FREEZE:
			
		break;
		
		case STATE_GRAVITY:
			
		break;
		
		case STATE_DEATH:
			
		break;
		
		}
		
	}
	
	private void updateState(byte state) { //Updating player's according to his state
		
		switch(state) {
		
		case STATE_FREE:
			
			if(Control.isKeyDown("Left")) {
				int maxTransform=0;
				if(getFightScene()==null || getFightScene().getFightBox()==null) {
					maxTransform=vel;
					}else {
					for(int i=1; i<=vel; i++) {
						
						if(getFightScene().getFightBox().isColliding(getRelativeX()-i, getRelativeY(), 8, 8)) break;
						maxTransform=i;
						
					}
				}
				setRelativePosition(getRelativeX()-maxTransform, getRelativeY());
			}
			if(Control.isKeyDown("Right")) {
				int maxTransform=0;
				if(getFightScene()==null || getFightScene().getFightBox()==null) {
					maxTransform=vel;
					}else {
					for(int i=1; i<=vel; i++) {
						
						if(getFightScene().getFightBox().isColliding(getRelativeX()+i, getRelativeY(), 8, 8)) break;
						maxTransform=i;
						
					}
				}
				setRelativePosition(getRelativeX()+maxTransform, getRelativeY());
			}
			if(Control.isKeyDown("Up")) {
				int maxTransform=0;
				if(getFightScene()==null || getFightScene().getFightBox()==null) {
					maxTransform=vel;
					}else {
					for(int i=1; i<=vel; i++) {
						
						if(getFightScene().getFightBox().isColliding(getRelativeX(), getRelativeY()-i, 8, 8)) break;
						maxTransform=i;
						
					}
				}
				setRelativePosition(getRelativeX(), getRelativeY()-maxTransform);
			}
			if(Control.isKeyDown("Down")) {
				int maxTransform=0;
				if(getFightScene()==null || getFightScene().getFightBox()==null) {
					maxTransform=vel;
					}else {
					for(int i=1; i<=vel; i++) {
						
						if(getFightScene().getFightBox().isColliding(getRelativeX(), getRelativeY()+i, 8, 8)) break;
						maxTransform=i;
						
					}
				}
				setRelativePosition(getRelativeX(), getRelativeY()+maxTransform);
			}
			
		break;
		
		case STATE_FREEZE:
			//Simply, do nothing x3
		break;
		
		case STATE_GRAVITY:
			
		break;
		
		case STATE_DEATH:
			
		break;
		
		}

	}
	
	private void endState(byte state) { //Ending player's state
		
		switch(state) {
		
		case STATE_FREE:
			
		break;
		
		case STATE_FREEZE:
			
		break;
		
		case STATE_GRAVITY:
			
		break;
		
		case STATE_DEATH:
			
		break;
		
		}
		
	}
	
	
	
	public final int getHP() {
		return hp;
	}
	public final int getMaxHP() {
		return maxhp;
	}
	
	public final int getLOVE() {
		return LOVE;
	}
	public void setLOVE(int LOVE) {
		this.LOVE = LOVE;
	}
	
	public final Weapon getWeapon() {
		return eqquipedWeapon;
	}
	
	protected final void equipWeapon(Weapon weapon) throws Exception, Error {
		
		this.eqquipedWeapon.unequip();
		this.eqquipedWeapon = weapon;
		weapon.equip(this);
		
	}
	
	public boolean CheckAttackPossibility(Monster monster, double strength) throws Error, Exception {
		
		return true;
		
	}
	
	public void Damage(int damage) throws Error, Exception {
		
		AutoDestructiveSound s = new AutoDestructiveSound("/takingDamage.wav");
		s.Start();
		
		hp-=damage;
		if(hp<=0) {
			kill();
		}
		
	}
	
	public void kill() {
		
		hp=0;
		
	}
	
	public void heal(int heal) throws Error, Exception {
		
		AutoDestructiveSound s = new AutoDestructiveSound("/Heal.wav");
		s.Start();
		
		hp+=heal;
		if(hp>=maxhp) {
			hp=maxhp;
		}
		
	}
	
	public void setHP(int hp) {
		this.hp = hp;
	}
	
	public void setState(byte state) {
		
		endState(this.state);
		this.state = state;
		initState(state);
		
	}
	
	public byte getState() {
		return state;
	}
	
	public final Backpack getBackpack() {
		return backpack;
	}
	
	public void update() throws Error, Exception{
		
		controlSync.Update();
		
		if(controlSync==null) {
			updateState(state);
		}
		else if(controlSync.isTimedOut()) {
			final int lateRate = controlSync.lateRate();
			final float late = controlSync.late();
		
			for(int i=0; i<lateRate; i++) {
				
				updateState(state);
				
			}
			
			controlSync.reset(late);
			
		}
		
		
		
	}

}
