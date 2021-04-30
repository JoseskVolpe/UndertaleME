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

package josesk.undertaleme.scenes.cores.fight.resources.list;

import javax.microedition.lcdui.Graphics;

import josesk.undertaleme.scenes.cores.Fight;
import josesk.undertaleme.scenes.cores.fight.resources.EmptyListException;
import josesk.undertaleme.scenes.cores.fight.resources.List;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;

public final class MonsterList extends List{
	
	public static final int MODE_FIGHT=0;
	public static final int MODE_ACT=1;
	public static final int MODE_ITEM=2;
	public static final int MODE_MERCY=3;
	public static final int MODE_OTHER=4;
	
	private int mode;
	
	public MonsterList(Monster[] list, Fight fightscene, int mode) throws EmptyListException {
		super(list, fightscene);
		
		this.mode = mode;
		
	}

	protected void renderObject(Graphics g, boolean selected, Object object, int i, int x, int y) throws Error, Exception{
		
		Monster monster = (Monster)object;
		g.setFont(fightscene.font);
		if(monster.isSparing()) {
			//Mercying
			g.setColor(0xffff00);
		}else{
			//Not mercying
			g.setColor(0xffffff);
		}
		g.drawString("* "+monster.getName(), x, y, Graphics.TOP|Graphics.LEFT);
		
	}
	
	protected void selectObject(Object object, int index) throws Exception, Error {
		
		Monster monster = (Monster)object;
		
		switch(mode) {
		
			case MODE_FIGHT:
				if(fightscene.select!=null) fightscene.select.Play();
				fightscene.preAttackMonster(monster);
			break;
			case MODE_ACT:
				if(fightscene.select!=null) fightscene.select.Play();
				fightscene.preActMonster(monster);
			break;
		}
		
	}
	
}