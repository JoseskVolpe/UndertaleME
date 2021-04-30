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

package josesk.undertaleme.weapons;

import josesk.undertaleme.scenes.cores.Fight;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;
import josesk.undertaleme.scenes.cores.fight.resources.Weapon;
import josesk.undertaleme.scenes.cores.fight.resources.attackeffects.KnifeEffect;

/**
 * Knife weapon
 *
 */
public class RealKnife extends Weapon{
	
	public RealKnife() {
		barDisapear = BAR_KEEP; //Will the bar disapear when hit?
		atk=99; //ATK level
		barCount=1; //How many bars
		barVel=0.8f; //Bar velocity
		bar_begin = BAR_BEGIN_RIGHT; //Side the bars will begin
		bar_mindelay = 80; //Min delay for add bar
		bar_maxdelay = 300; //Max delay for add ba
		Name = "Real Knife"; //Name of the weapon
		attackEffect = new KnifeEffect(); //Attack Effect of the weapon
	}

	//When it's time to attack
	protected void onAttack(Fight fightscene, Monster monster, double precision) {}
		
	//When player hit center during FightBar or he missed out
	public void onHit(Fight fightscene, Monster monster, int iD, double precision) {}
		
	//When player receives damage
	public void onPlayerDamage(Fight fightscene, Monster monster, int damage) {}
		
	//When attacked monster finalizes his attack
	public void onMonsterAttackFinish(Fight fightscene, Monster monster) {}
		
	protected void onEquip() throws Error, Exception {}
	public void onUnequip() throws Error, Exception {}
	
}
