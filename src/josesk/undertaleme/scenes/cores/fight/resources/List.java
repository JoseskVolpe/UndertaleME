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

import josesk.undertaleme.GameEngine;
import josesk.undertaleme.layout.Player;
import josesk.undertaleme.scenes.cores.Fight;

public class List {
	
	private int maxPage, selected, cols;
	private Object list[];
	protected Fight fightscene;
	
	public List(Object list[], Fight fightscene) throws EmptyListException {
		
		this.fightscene = fightscene;
		
		selected = 0;
		this.list = list;
		
		if(list==null || list.length<=0) throw new EmptyListException();
		
		calculateMaxPages();
		
	}
	
	final public void render(Graphics g) throws Error, Exception{
		
		int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/120;
		if(bs<=0)bs=1;
		
		final int minX = (int)(GameEngine.getCanvas().getResolutionWidth()/16.842f);
		final int y = (GameEngine.getCanvas().getResolutionHeight()-45-bs)-65;
		
		final int distX = 128;
		int x=minX+31;
		
		final int page = (int)(Math.ceil((selected+0.05)/((float)(2*cols))));
		final int minIndex = (page-1)*2*cols;
		
		drawCols:{
			for(int i=minIndex; i<minIndex+(2*cols); i++) {
				
				
				if(i>=list.length) break drawCols;
				renderObject(g, selected==i, list[i], i, x, y+13);
				if(selected==i) fightscene.getPlayer().setRelativePosition(x-18, y+13);
				
				i++;
				if(i>=list.length) break drawCols;
				renderObject(g, selected==i, list[i], i, x, y+29);
				if(selected==i) fightscene.getPlayer().setRelativePosition(x-18, y+29);
				
				x+=distX;
				
			}
		}
		
		g.setColor(0xffffff);
		g.setFont(fightscene.font); //TODO CRIAR FONTE PERSONALIZADA
		g.drawString("PAGE "+page, (int)(GameEngine.getCanvas().getResolutionWidth()/1.391f), y+43, Graphics.TOP|Graphics.LEFT);
		
	}
	
	final public void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) throws Error, Exception{
		
		calculateMaxPages();
		
	}
	
	final private int calculateMaxPages() {
		
		int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/120;
		if(bs<=0)bs=1;
		
		final int minX = (int)(GameEngine.getCanvas().getResolutionWidth()/16.842f);
		final int maxX = GameEngine.getCanvas().getResolutionWidth()-minX;
		
		final int distX = 128;
		int x=minX+31;
		
		cols = 1;
		
		colsCalc:{
			
			do{
				
				if(x+(distX*(cols+1))<maxX+6) {
					cols++;
				}else {
					break colsCalc;
				}
				
			}while(x+(distX*cols)<maxX);
			
		}
		
		maxPage = (int)Math.ceil((selected+0.05)/((float)(2*cols)));
		
		return maxPage;
		
	}
	
	final public void onKeyDown(String command) throws Exception, Error {
		
		if(command.equals("Left") && selected-2>=0) {
			selected-=2;
		}
		if(command.equals("Right") && selected+2<list.length) {
			selected+=2;
		}
		if(command.equals("Down") && selected+1<list.length) {
			selected++;
		}
		if(command.equals("Up") && selected-1>=0) {
			selected--;
		}
		if(command.equals("Ok")) {
			selectObject(list[selected], selected);
		}
		
	}
	
	protected void renderObject(Graphics g, boolean selected, Object object, int i, int x, int y) throws Error, Exception{
		
		g.setFont(fightscene.font);
		g.setColor(0xffffff);
		g.drawString("* Obj #"+i, x, y, Graphics.TOP|Graphics.LEFT);
		
	}
	
	protected void selectObject(Object object, int index) throws Exception, Error {
		if(fightscene.select!=null) fightscene.select.Play();
	}
	
}
