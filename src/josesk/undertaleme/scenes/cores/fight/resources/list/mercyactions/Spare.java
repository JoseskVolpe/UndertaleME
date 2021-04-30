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

package josesk.undertaleme.scenes.cores.fight.resources.list.mercyactions;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;

import josesk.javameengine.Source;
import josesk.javameengine.Text;
import josesk.javameengine.sound.AutoDestructiveSound;
import josesk.undertaleme.scenes.cores.Fight;
import josesk.undertaleme.scenes.cores.fight.resources.EmptyMonsterArrayException;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;
import josesk.undertaleme.scenes.cores.fight.resources.list.MercyList.MercyAction;

/**
 * Spare action - Mercy list
 *
 */
public final class Spare extends MercyAction{
	
	private static Source wonSource = new Source("/FightDefaultText/YouWon.txt");
	
	public final static void setWonSource(Source wonSource) {
		Spare.wonSource = wonSource;
	}
	public final static Source getWonSource() {
		return wonSource;
	}
	
	public Spare(Fight fightscene) throws IOException {
		super(fightscene);
		
		this.name = Text.getFileText(new Source("/ActionNames/Spare.txt"));
	}
	
	public void render(Graphics g, boolean selected, Object object, int i, int x, int y) throws ArrayIndexOutOfBoundsException, EmptyMonsterArrayException {
		
		g.setColor(0xffffff);
		for(int j=0; j<fightscene.getMonstersQuantity(); j++) {
			if(fightscene.getMonsterFromID(j).isSparing() && !fightscene.getMonsterFromID(j).isMercy()) {
				g.setColor(0xffff00);
				break;
			}
		}
		
		g.setFont(fightscene.font);
		g.drawString("* "+getName(), x, y, Graphics.TOP|Graphics.LEFT);
		
	}
	
	public void select() throws Exception, Error{
		
		boolean mercy=false;
		
		for(int i=0; i<fightscene.getMonstersQuantity(); i++) {
			
			Monster monster = fightscene.getMonsterFromID(i);
			
			if(monster.onPlayerSpare() && !monster.isMercy()) {
				
				fightscene.onspare(monster);
				
				mercy = true;
				monster.setMercy(true);
				
			}
		}
		
		if(fightscene.getActiveMonsters().length<=0) { //All monsters spared
			
			fightscene.stopMusic();
			mercySound(mercy);
			fightscene.showMessageInformation(new Text(wonSource), Fight.MODE_SPAREFINISH);
			
		}else {
			
			mercySound(mercy);
			fightscene.MonsterTurn(true);
			
		}
	}
	
	private final void mercySound(boolean mercy) throws Exception, Error {
		
		if(mercy) {
			new AutoDestructiveSound("/dust.wav").Start();
		}else {
			if(fightscene.select!=null) fightscene.select.Play();
		}
		
	}
	
}
