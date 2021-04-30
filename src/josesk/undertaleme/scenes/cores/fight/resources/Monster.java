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

import josesk.javameengine.Sync;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.PaintableObject;
import josesk.undertaleme.scenes.cores.Fight;
import josesk.undertaleme.scenes.cores.fight.resources.act.Check;
import josesk.undertaleme.scenes.cores.fight.resources.monsterresources.Act;

/**
 * Extend this to create a new monster
 *
 */
public class Monster implements PaintableObject{

	public static final byte MODE_IDLE = 0;
	public static final byte MODE_ATTACK = 1;
	public static final byte MODE_DIALOG = 2;
	
	public static final byte RENDER_FRAME_NULL = 0;
	public static final byte RENDER_FRAME_BEHINDINTERFACE = 1;
	public static final byte RENDER_FRAME_FRONT = 2;
	
	
	protected int HP, MaxHP, atk, def;
	private Fight FightGameView;
	private byte mode;
	private byte renderFrame;
	protected String Name = "Cube owo";
	protected String Description = "A little cube from the developer. He lost it, give it back to Josesk Volpe! UwU";
	protected Sync sync;
	private boolean mercying, mercy;
	
	protected int x, y;
	protected int width, height;
	protected int effectX, effectY;

	protected final Check checkAct = new Check(this);
	
	private Act actList[] = {checkAct};
	
	public Monster(int HP, Fight FightGameView) {
		
		this.MaxHP = HP;
		this.HP = HP;
		this.FightGameView = FightGameView;
		this.mode = MODE_IDLE;
		this.atk = 0;
		effectX=20;
		effectY=20;
		width=40;
		height=40;
		
		this.renderFrame = RENDER_FRAME_BEHINDINTERFACE;
		
	}
	
	/**
	 * When fight GameView begin
	 */
	public void onFightGameViewBegin() {
		
		x = (GameEngine.getCanvas().getResolutionWidth()/2)-20;
		y = (int)(GameEngine.getCanvas().getResolutionHeight()/7.111f);
		
	}
	
	/**
	 * When game resolution changes
	 * @param lastWidth
	 * @param lastHeight
	 * @param widthDifference
	 * @param heightDifference
	 */
	public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) {
				
		StandardUpdateRelativePosition(lastWidth, lastHeight, wDif, hDif);
		y = (int)(GameEngine.getCanvas().getResolutionHeight()/7.111f);
		
	}
	
	protected final void StandardUpdateRelativePosition(int lastWidth, int lastHeight, int wDif, int hDif) {
		
		final int centerX = GameEngine.getCanvas().getResolutionWidth()/2;
		final int centerY = GameEngine.getCanvas().getResolutionHeight()/2;
		
		final int lcX = lastWidth/2;
		final int lcY = lastHeight/2;
		
		final int px = x-lcX;
		final int py = y-lcY;
		
		x=centerX+px;
		y=centerY+py;
		
	}
	
	/**
	 * Paint monster
	 * @param Graphics
	 */
	public void paint(Graphics g) {
		
		g.setColor(0xffffff);
		g.fillRect(x, y, width, height); //TODO implementar câmera
		
	}
	
	/**
	 * Paint monster when it's mercy
	 * @param Graphics
	 */
	public void paintMercy(Graphics g) {
		
		g.setColor(0xffffff);
		g.fillArc(0, 0, width, height, 0, 360); //Josesk's cube has become round O3O
		
	}

	/**
	 * Update monster
	 */
	public final void Update() {
		
		if(sync!=null) sync.Update();
		
		update();
		
	}
	
	protected void update() {}
	
	/**
	 * When it's monster's turn
	 */
	public void onMonsterTurn() {}
	
	/**
	 * When player is sparing monster
	 * @return acceptSpare
	 */
	public boolean onPlayerSpare() {
		
		if(isSparing()) return true;
		
		SparePlayer();
		
		return false;
		
	}
	
	/**
	 * Spare player
	 */
	protected final void SparePlayer() {
		
		mercying=true;
		
	}
	
	/**
	 * Give-up sparing player
	 */
	protected final void GiveUpSparingPlayer() {
		
		mercying=false;
		
	}
	
	/**
	 * Check if monster is sparing player
	 * @return sparing
	 */
	public final boolean isSparing() {
		
		return mercying;
		
	}
	
	/**
	 * Check if monster is mercy
	 * @return mercy
	 */
	public final boolean isMercy() {
		return mercy;
	}
	
	/**
	 * Sets mercy state
	 * @param mercy
	 */
	public final void setMercy(boolean mercy) {
		this.mercy = mercy;
	}
	
	/**
	 * Damage monster
	 * @param damage
	 * @return damageSuccess
	 * @throws Error
	 * @throws Exception
	 */
	public final boolean Damage(int damage) throws Error, Exception {
		
		if(onDamage(damage)) {
			
			HP-=damage;
			if(HP<=0) {
				if(!kill()) {
					HP+=damage;
					return false;
				}
			}
			
			FightGameView.onMonsterDamage(this, damage);
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * When monsters is going to be damaged
	 * @param damage
	 * @return damageSucess
	 */
	protected boolean onDamage(int damage) {
		
		return true;
		
	}
	
	/**
	 * Kills monster
	 * @return kill sucess
	 * @throws Error
	 * @throws Exception
	 */
	public final boolean kill() throws Error, Exception {
		
		if(onKill()) {
			
			HP=0;
			FightGameView.onMonsterKill(this);
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * When monster is going to be killed
	 * @return killSucess
	 */
	protected boolean onKill() {
		
		return true;
		
	}
	
	/**
	 * Heal monster
	 * @param heal
	 * @return healSucess
	 */
	public final boolean heal(int heal) {
		
		if(onHeal(heal)) {
			
			HP+=heal;
			
			if(HP>=MaxHP) HP=MaxHP;
			
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * When monster is going to be healed
	 * @param heal
	 * @return healSucess
	 */
	protected boolean onHeal(int heal) {
		
		return true;
		
	}
	
	/**
	 * Sets monster HP
	 * @param HP
	 * @return setSucess
	 */
	public final boolean setHP(int HP) {
		
		if(onHPSet(HP)) {
			
			this.HP = HP;
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * When monster is going to have their HP updated
	 * @param HP
	 * @return HPUpdateSucess
	 */
	protected boolean onHPSet(int HP) {
		
		return true;
		
	}
	
	/**
	 * Returns HP
	 * @return HP
	 */
	public final int getHP() {
		return HP;
	}
	
	/**
	 * Set monster's max HP and fill it if true
	 * @param MaxHP
	 * @param fillHP
	 * @return updateSucess
	 */
	public final boolean setMaxHP(int MaxHP, boolean fillHP) {
		
		if(onMaxHPSet(MaxHP, fillHP)) {
			
			this.MaxHP = MaxHP;
			if(fillHP) setHP(MaxHP);
			
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * When monster is going to have their MaxHP updated
	 * @param MaxHP
	 * @param fillHP
	 * @return updateSucess
	 */
	protected boolean onMaxHPSet(int MaxHP, boolean fillHP) {
		
		return true;
		
	}
	
	/**
	 * Set mode
	 * @param mode
	 */
	protected final void setMode(byte mode) {
		
		this.mode = mode;
		
	}
	
	/**
	 * returns mode
	 * @return mode
	 */
	public final byte getMode() {
		
		return mode;
		
	}
	
	/**
	 * Returns monster's name
	 * @return name
	 */
	public final String getName() {
		
		return Name;
		
	}
	
	/**
	 * Returns monster's description
	 * @return description
	 */
	public final String getDescription() {
		
		return Description;
		
	}
	
	/**
	 * Returns monster's X position
	 * @return x
	 */
	public final int getX() {
		
		return x;
		
	}
	
	/**
	 * Returns monster's Y position
	 * @return y
	 */
	public final int getY() {
		
		return y;
		
	}
	
	/**
	 * Returns monster's width size
	 * @return width
	 */
	public final int getWidth() {
		return width;
	}
	
	/**
	 * Returns monster's height size
	 * @return height
	 */
	public final int getHeight() {
		return height;
	}
	
	/**
	 * Returns effect's x position for this monster
	 * @return effectX
	 */
	public final int getEffectX() {
		return effectX;
	}
	
	/**
	 * Returns effect's y position for this monster
	 * @return effectY
	 */
	public final int getEffectY() {
		return effectY;
	}
	
	/**
	 * Returns mercy render frame Z-Index
	 * @return Z-Index
	 */
	public final byte getRenderFrame() {
		return renderFrame;
	}
	
	/**
	 * Sets mercy render frame Z-Index
	 * @param RENDER_FRAME
	 */
	public final void setRenderFrame(byte renderFrame) {
		
		this.renderFrame = renderFrame;
		
	}
	
	/**
	 * get ATK level
	 * @return atk
	 */
	public final int getAtk() {
		
		return atk;
	}
	
	/**
	 * Set monster's ATK level
	 * @param atk
	 * @return updateSucess
	 */
	public final boolean setAtk(int atk) {
		
		if(onAtkSet(atk)) {
			
			this.atk = atk;
			return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * When monster is going to have their ATK level updated
	 * @param atk
	 * @return updateSucess
	 */
	protected boolean onAtkSet(int atk) {
		
		return true;
		
	}
	
	/**
	 * Set monster's DEF level
	 * @param def
	 * @return updateSucess
	 */
	public final boolean setDef(int def) {
		if(onDefSet(def)) {
			this.def = def;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns monster's DEF level
	 * @return def
	 */
	public final int getDef() {
		return def;
	}
	
	/**
	 * When monster is going to have their DEF level updated
	 * @param def
	 * @return updateSucess
	 */
	protected boolean onDefSet(int def) {
		
		return true;
		
	}
	
	/**
	 * Set monster's Act list
	 * @param actList
	 */
	protected final void setActList(Act[] actList) {
		this.actList = actList;
	}
	
	/**
	 * Get monster's Act list
	 * @return actList
	 */
	public final Act[] getActList() {
		return actList;
	}

}
