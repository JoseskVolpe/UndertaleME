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

package josesk.undertaleme.scenes;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import josesk.javameengine.LoadResources;
import josesk.javameengine.RandomAl;
import josesk.javameengine.Sound;
import josesk.javameengine.Sync;
import josesk.javameengine.Vibrator;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;

public class ErrorView extends GameView implements Runnable{
	
	private String error, SError, errorName;
	private boolean glitchText, ready=false, showContent;
	private Image ErrorSans;
	
	private byte posText[][];
	private byte posRect[][];
	private Sync sync2;
	private Sound teleport;
	
	/**
	 * Crash game scene
	 * @param errorMessage
	 */
	public ErrorView(String error) {
		
		this.error = error;
		SError = "* "+error;
		errorName = error.substring(error.lastIndexOf('.')+1, error.length());
		if(errorName.indexOf(':')>=0)
			errorName = errorName.substring(0, errorName.indexOf(':'));
		
		glitchText = false;
		sync = new Sync(RandomAl.generateRandom(0, 2000), false);
		sync2 = new Sync(300, false);
		
		ErrorSans = LoadResources.loadImage("/ErrorSans.png");
		
		posText= new byte[3][2];
		posRect = new byte[8][4];
		
		showContent = false;
		
		updateTextAndRect();
		
		sync.begin();
		sync2.begin();
		
		
	}
	
	public void onGameViewBegin() {
		
		Sound.closeAll();
		Sound.garbageCollect();
		
		teleport = LoadResources.loadSound("/Teleport.mp3", false);
		GameEngine.setScreenMode(GameEngine.SCREEN_DYNAMIC);
		
		GameEngine.DebugError("* "+errorName+" Sans destroyed your universe");
		GameEngine.DebugError("* I have to destroy this stupid anomaly");
		
		beginThread(new Thread(this));
		
	}
	
	public void Run() throws Exception, Error {
		
		if(teleport!=null) teleport.Start();
		ready=true;
		Vibrator.Vibrate(300);
		Thread.sleep(1000);
		
		if(teleport!=null) teleport.Start();
		Vibrator.Vibrate(300);
		showContent = true;
		Thread.sleep(1000);
		//if(teleport!=null) teleport.Close();
		
		teleport = null;

	}
	
	public final boolean onError(String error) {
		
		return false;
		
	}
	
	private void updateTextAndRect() {
		
		for(int i=0; i<posText.length; i++) {
			
			posText[i][0] = (byte)RandomAl.generateRandom(-36, 36);
			posText[i][1] = (byte)RandomAl.generateRandom(-35, 35);
			
		}
		
		for(int i=0; i<posRect.length; i++) {
			
			posRect[i][0] = (byte)RandomAl.generateRandom(-16, 16);
			posRect[i][1] = (byte)RandomAl.generateRandom(-16, 16);
			posRect[i][2] = (byte)RandomAl.generateRandom(1,10);
			posRect[i][3] = (byte)RandomAl.generateRandom(1,10);
			
		}
		
	}
	
	public void Update() {
		
		if(sync.isTimedOut() && !glitchText) {
			
			for(byte i=0; i<SError.length()-1; i++) {
				byte randomindex = (byte)RandomAl.generateRandom(1, 6);
				if(RandomAl.generateRandom(0, (int)(randomindex))==randomindex/2) {
					StringBuffer b = new StringBuffer(SError.length());
					b.insert(0, SError);
					b.setCharAt(i, (char)(RandomAl.generateRandom(0, 255)));
					SError = b.toString();
					b = null;
				}
			}
			
			glitchText = true;
			
			sync = new Sync(150, false);
			sync.begin();
			
		}else if(sync.isTimedOut() && glitchText) {
			
			glitchText = false;
			
			SError = "* "+error;
			sync = new Sync(RandomAl.generateRandom(0, 2000), false);
			sync.begin();
			
		}
		
		if(sync2.isTimedOut()) {
			updateTextAndRect();
			sync2.reset();
		}
		
	}
	
	public void render(Graphics g) {
		
		if(ready) {
			g.setColor(0x000000);
			g.fillRect(0, 0, GameEngine.getCanvas().getWidth(), GameEngine.getCanvas().getHeight());
			g.drawRect(0, 0, 1, 1);//Glitch prevent
			
			if(showContent) {
			
				g.setColor(0xff0000);
				g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
				
				g.drawString(SError, 3, GameEngine.getCanvas().getHeight()-3, Graphics.LEFT|Graphics.BOTTOM);
				
				final int xOff = RandomAl.generateRandom(-2, 2);
				final int yOff = RandomAl.generateRandom(-2, 2);
				
				g.setFont(Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL));
				g.setColor(0xffffff);
				for(int i=0; i<posText.length/2; i++) {
					
					g.drawString(errorName, (GameEngine.getCanvas().getWidth()/2)+xOff+posText[i][0], (GameEngine.getCanvas().getHeight()/2)+yOff+posText[i][1], Graphics.HCENTER|Graphics.TOP);
					
				}
				
				for(int i=0; i<posRect.length/2; i++) {
					
					g.fillRect(((GameEngine.getCanvas().getWidth()/2)+xOff)+posRect[i][0], ((GameEngine.getCanvas().getHeight()/2)+yOff)+posRect[i][1], (int)(posRect[i][2]), (int)(posRect[i][3]));
					
				}
				
				g.drawImage(ErrorSans, (GameEngine.getCanvas().getWidth()/2)+xOff, (GameEngine.getCanvas().getHeight()/2)+yOff, Graphics.HCENTER|Graphics.VCENTER);
				
				g.setColor(0x000000);
				for(int i=posRect.length/2; i<posRect.length; i++) {
					
					g.fillRect(((GameEngine.getCanvas().getWidth()/2)+xOff)+posRect[i][0], ((GameEngine.getCanvas().getHeight()/2)+yOff)+posRect[i][1], (int)(posRect[i][2]), (int)(posRect[i][3]));
					
				}
				
				g.setColor(0xffffff);
				for(int i=posText.length/2; i<posText.length; i++) {
					
					g.drawString(errorName, (GameEngine.getCanvas().getWidth()/2)+xOff+posText[i][0], (GameEngine.getCanvas().getHeight()/2)+yOff+posText[i][1], Graphics.HCENTER|Graphics.TOP);
					
				}
				
			}
		}
		
	}

}
