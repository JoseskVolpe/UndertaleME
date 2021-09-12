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
import josesk.javameengine.sound.AutoDestructiveSound;
import josesk.undertaleme.Game;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;
import josesk.undertaleme.scenes.cores.fight.resources.Weapon;
import josesk.undertaleme.scenes.cores.fight.resources.playerresources.Backpack;
import josesk.undertaleme.weapons.RealKnife;

public class Player{ //TODO: Reprogram

	private int hp, maxhp, LOVE; //LOVE = Level Of ViolencE, not "love"
	private Backpack backpack;
	private Weapon eqquipedWeapon = new Weapon();
	private byte state;
	int vel = 1;
	
	public final static byte STATE_FREE=0; //Red, moves in the scenario
	public final static byte STATE_FREEZE=1; //Conserve color, player cannot control and it's controlled by the FightScene
	public final static byte STATE_GRAVITY=2; //Blue, player can control himself, but he has a gravity
	public final static byte STATE_DEATH=3; //Broke, player is freeze in his last position
	
	
	public Player(){
		
		maxhp = Integer.valueOf(Game.getGame().getAppProperty("max_hp")).intValue();
		LOVE = Integer.valueOf(Game.getGame().getAppProperty("LOVE")).intValue();
		hp = maxhp;
		
		backpack = new Backpack();
		
		equipWeapon(new RealKnife());
		
	}
	
	public void paint(Graphics g) {
		
	}
	
	public void update(float delta){
		
		
		
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
	
	protected final void equipWeapon(Weapon weapon){
		
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
		
		this.state = state;
		
	}
	
	public byte getState() {
		return state;
	}
	
	public final Backpack getBackpack() {
		return backpack;
	}

}
