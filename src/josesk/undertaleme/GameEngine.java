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
*
*EXPERIMENTAL
*/

package josesk.undertaleme;
import java.util.Enumeration;
import java.util.Vector;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.media.Player;

import com.j2mearmyknife.image.sources.SmartImage;
import com.j2mearmyknife.image.transformations.ImageTransformationResize;

import josesk.javameengine.Control;
import josesk.javameengine.Sound;
import josesk.javameengine.sound.NonSyncSound;
import josesk.undertaleme.scenes.ErrorView;

/**
 * GameEngine class provides a basic game engine.
 */
public class GameEngine extends GameCanvas implements Runnable {

	public static final byte CONSOLE_TYPE_DEBUG=0;
	public static final byte CONSOLE_TYPE_INFO=1;
	public static final byte CONSOLE_TYPE_WARN=2;
	public static final byte CONSOLE_TYPE_ERROR=3;
	
	public static final boolean GRAPHICS_LOW=false;
	public static final boolean GRAPHICS_HIGH=true;
	
	public static final boolean SCREEN_DYNAMIC = false;
	public static final boolean SCREEN_FIXED = true;
	
	private static String console[] = new String[50];
	private static byte consoleType[] = new byte[50];
	private static byte ram[];
	private static byte ramLine=-1;
	private static int consoleLine=-1;
	private static long systemDebugTime;
	private static boolean debug=false;
	private static boolean showDebug=false;
	private static Font consoleFont = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL);
	
	private Vector 	  views;
	private int		  framelimit;
	private static boolean graphicsq;
	private static boolean definedScreenMode, screenMode;
	private GameView view;
	private GameView viewToChange;
	private GameView secondScreenView; //GameView for small screen on dual-screen flip phones
	private GameView secondViewToChange;
	private static GameEngine engine;
	private static long syncCurrentTimeMillis;
	
	private boolean paused;
	private boolean stoped;
	private boolean error;
	private boolean flipPhoneClosed;
	
	private int lastWidth, lastHeight;
	private int minRes;
	
	/**
	 * Creates a Game Engine with the specified
	 * Game model attached. The game runs at the
	 * specified fps.
	 * 
	 * @param model game model.
	 * @param fps frames per second.
	 */
	public GameEngine(int fps) {
		super(false);
		
		debug=Game.getGame().getAppProperty("Debug").toLowerCase().equals("true");
		showDebug = debug;
		
		if(debug) {
			DebugInfo("Press '#' to show/hide console");
			ram = new byte[21];
		}
		
		DebugInfo("Device: "+System.getProperty("microedition.platform"));
		DebugInfo("VM has "+(Runtime.getRuntime().totalMemory()/1000.f)+"kb RAM");
		
		Sound.initialize(Sound.AUDIO_MODE_ULTRA);
		
		minRes = Math.min(getWidth(), getHeight());
		
		syncCurrentTimeMillis = 0;
		engine = this;
		error = false;
		flipPhoneClosed = false;
		
		this.views  = new Vector();
		
		this.stoped	  = true;
		this.framelimit	  = fps;
		
		String gm = Game.getGame().getAppProperty("Graphics").toLowerCase();
		if(gm.equals("auto")) {
			if(Runtime.getRuntime().totalMemory()/1000.f <= 2000) {
				graphicsq = GameEngine.GRAPHICS_LOW;
				DebugInfo("Graphics set to AUTO (LOW)");
			}else {
				graphicsq = GameEngine.GRAPHICS_HIGH;
				DebugInfo("Graphics set to AUTO (HIGH)");
			}
		}else {
			setGraphicsQuality(gm.equals("high") || gm.equals("1")); 
		}
		
		
		gm = Game.getGame().getAppProperty("Screen").toLowerCase();
		if(gm.equals("dynamic")) {
			setScreenMode(GameEngine.SCREEN_DYNAMIC);
		}else {
			setScreenMode(GameEngine.SCREEN_FIXED);
		}
		definedScreenMode = screenMode;
		
		gm = null; //Free from memory
		
		Control.init();
		
		
	}
	
	private static final void ramConsumeCalc() {
		
		if(GameEngine.debug) {
			ramLine++;
			if(ramLine>=ram.length) {
				
				for(int i=0; i<ram.length-1; i++) {
					ram[i] = ram[i+1];
					
				}
				
				ramLine=(byte) (ram.length-1);
				
			}
			
			Runtime r = Runtime.getRuntime();
			
			ram[ramLine]=(byte)(((r.totalMemory()-r.freeMemory())/(float)(r.totalMemory()))*100);
		}
		
	}
	
	/**
	 * Debug
	 * @param Message
	 */
	public final static void Debug(Object debug) {
		
		System.out.println(debug);
		debug(debug, CONSOLE_TYPE_DEBUG);
		
	}
	
	/**
	 * Debug inform
	 * @param information
	 */
	public final static void DebugInfo(Object debug) {
		System.out.println(debug);
		debug(debug, CONSOLE_TYPE_INFO);
	}
	
	/**
	 * Debug warning
	 * @param warning
	 */
	public final static void DebugWarn(Object debug) {
		System.err.println(debug);
		debug(debug, CONSOLE_TYPE_WARN);
	}
	
	/**
	 * Debug error
	 * @param error
	 */
	public final static void DebugError(Object error) {
		
		System.err.println(error);
		debug(error, CONSOLE_TYPE_ERROR);
		
	}
	
	private static void debug(Object debug, byte type) {
		
		if(GameEngine.debug) {
			consoleLine++;
			if(consoleLine>=console.length) {
				
				for(int i=0; i<console.length-1; i++) {
					console[i] = console[i+1];
					consoleType[i] = consoleType[i+1];
					
				}
				
				consoleLine=console.length-1;
				
			}
			
			console[consoleLine]=String.valueOf(debug);
			consoleType[consoleLine]=type;
		}
		
	}
	
	/**
	 * Starts the Game.
	 * AUTOGENERATED
	 */
	public void start() {
		if (stoped) {	
			this.lastWidth = getWidth();
			this.lastHeight = getHeight();
			Thread t = new Thread(this);
			t.start();
		}
	}
	
	/**
	 * Stops the Game.
	 * AUTOGENERATED
	 */
	public void stop() {
		if (!stoped) {			
			this.stoped = true;
		}
	}

	/**
	 * Resumes the Game.
	 * AUTOGENERATED
	 */
	public void resume() {
		synchronized (this) {
			if (paused) {
				this.paused = false;
			}
			this.notifyAll();
		}
	}
	
	/**
	 * Pauses the Game.
	 * AUTOGENERATED
	 */
	public void pause() {
		synchronized (this) {			
			if (!paused) {
				this.paused = true;
			}
		}
	}
	
	/**
	 * Attaches the specified view to the
	 * engine.
	 * 
	 * AUTOGENERATED
	 * 
	 * @param view some view.
	 */
	public void attach(GameView view) {
		if(!error) viewToChange = view;
	}
	
	public void secondaryScreenAtach(GameView view) {
		
		if(!error) secondViewToChange = view;
		
	}
	
	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.game.GameCanvas#paint(javax.microedition.lcdui.Graphics)
	 */
	public void paint(Graphics g) {
		
		try {
		
			boolean lsm = screenMode;
			
			int keys = this.getKeyStates();
			
			Enumeration enumeration = this.views.elements();
			
			if(error|!flipPhoneClosed||secondScreenView==null) {
				
				Graphics dg = g;
				Image i = null;
				if(lsm==GameEngine.SCREEN_FIXED && (getWidth()!=320 || getHeight()!=240)) {
					i = Image.createImage(320, 240);
					dg = i.getGraphics();
				}
				
				if(view!=null) view.paint(dg);
				
				if(lsm==GameEngine.SCREEN_FIXED && (getWidth()!=320 || getHeight()!=240)) {
					SmartImage sI = new SmartImage(i);
					ImageTransformationResize iR = new ImageTransformationResize();
					
					if(getHeight()>getWidth()) { //Portrait screen
					
						g.setColor(0x000000);
						g.fillRect(0, 0, getWidth(), getHeight());
						g.setColor(0xffffff);
						
						int tH = (int)(240*(getWidth()/320.f));
						int yOff = (getHeight()/2)-(tH/2);
						
						g.fillRect(0, yOff-1, getWidth(), tH+2);
						
						iR.setTargetDimensions(getWidth(), tH);
						iR.process(sI).drawOnGraphics(g, 0, yOff, 0);
						
					}else { //Landscape screen
						
						g.setColor(0x000000);
						g.fillRect(0, 0, getWidth(), getHeight());
						g.setColor(0xffffff);
						
						int tW = (int)(320*(getHeight()/240.f));
						int xOff = (getWidth()/2)-(tW/2);
						
						g.fillRect(xOff-1, 0, tW+2, getHeight());
						
						iR.setTargetDimensions(tW, getHeight());
						iR.process(sI).drawOnGraphics(g, xOff, 0, 0);
						
					}
					
					sI = null; //free from memory uwu
					i = null;
				}
			}else {
				if(secondScreenView!=null) secondScreenView.paint(g);
				
			}
			
			if(showDebug) {
				
				int j=0;
				g.setFont(consoleFont);
				for(int i=consoleLine; i>=0; i--) {
					
					if(i>=console.length) i=console.length-1;
					
					if(console[i]!=null) {
						
						if(GameEngine.getCanvas().getHeight()-(j*12)<0) continue;
						
						g.setColor(0x000000);
						g.fillRect(0, GameEngine.getCanvas().getHeight()-((j+1)*12), console[i].length()*12, 12);
						
						switch(consoleType[i]) {
							case CONSOLE_TYPE_DEBUG:
								g.setColor(0xffffff);
							break;
							case CONSOLE_TYPE_INFO:
								g.setColor(0x0000ff);
							break;
							case CONSOLE_TYPE_WARN:
								g.setColor(0xffc90e);
							break;
							case CONSOLE_TYPE_ERROR:
								g.setColor(0xff0000);
							break;
							default:
								g.setColor(0xffffff);
							break;
						}
						
						
						g.drawString(console[i], 0,GameEngine.getCanvas().getHeight()-(j*12), Graphics.LEFT|Graphics.BOTTOM);
						j++;
					}
					
				}
				
				int soundDebugX = (int)(getResolutionWidth()*(183/320.f));
				
				byte as = (byte) ram.length;
				//int lastX=(GameEngine.getCanvas().getWidth()/(as-1))*(-1), lastY=40;
				int lastX=-1, lastY=0;
				for(int i=0; i<as; i++) {
					
					g.setColor(0x00ff00);
					int percent = ram[i];
					if(percent>=70) g.setColor(0xff0000);
					
					int x=(((soundDebugX)/(as-1))*(i))-1;
					int y = (int)(45-(45*(percent/100.f)));
					
					if(percent>0) g.drawLine(lastX, lastY, x, y);
					
					lastX=x;
					lastY=y;
					
				}
				
				int p=(int)(40-(40*(70/100.f)));
				
				g.setColor(0xff0000);
				g.drawLine(0, p, soundDebugX, p);
				
				g.setColor(0xffffff);
				g.drawLine(0, 45, GameEngine.getCanvas().getWidth(), 45);
				g.drawLine(soundDebugX,0, soundDebugX, 45);
				
				g.setColor(0xffffff);
				Runtime r = Runtime.getRuntime();
				g.drawString(((r.totalMemory()-r.freeMemory())/1000)+"kb/"+(r.totalMemory()/1000)+"kb", 0, 46, Graphics.LEFT|Graphics.TOP);
				
				byte unrealized=0;
				byte realized=0;
				byte prefetched=0;
				byte started=0;
				byte closed=0;
				
				for(byte i=0; i<Sound.getloadedSoundsSize(); i++) {
					
					Sound s = Sound.getSoundByID(i);
					
					if(s!=null && s.getPlayer()!=null) {
						int status = s.getPlayer().getState();
						
						if(status>=Player.CLOSED && status<Player.UNREALIZED) {
							closed++;
						}else if(status>=Player.UNREALIZED && status<Player.REALIZED) {
							unrealized++;
						}else if(status>=Player.REALIZED && status<Player.PREFETCHED) {
							realized++;
						}else if(status>=Player.PREFETCHED && status<Player.STARTED) {
							prefetched++;
						}else if(status>=Player.STARTED) {
							started++;
						}
						
					}
				}
				
				g.drawString("Closed: "+closed, soundDebugX+1, 0, Graphics.LEFT|Graphics.TOP);
				g.drawString("Unrealized: "+unrealized, soundDebugX+1, 8, Graphics.LEFT|Graphics.TOP);
				g.drawString("Realized: "+realized, soundDebugX+1, 16, Graphics.LEFT|Graphics.TOP);
				g.drawString("Prefetched: "+prefetched, soundDebugX+1, 24, Graphics.LEFT|Graphics.TOP);
				g.drawString("Started: "+started, soundDebugX+1, 32, Graphics.LEFT|Graphics.TOP);
				g.setColor(0x00ff00);
				g.drawString("S: "+Sound.getStarts(), getWidth(), 8, Graphics.RIGHT|Graphics.TOP);
				g.setColor(0xffc90e);
				g.drawString("TO: "+NonSyncSound.getTimeOuts(), getWidth(), 16, Graphics.RIGHT|Graphics.TOP);
				g.setColor(0xff6804);
				g.drawString("R: "+Sound.getRejects(), getWidth(), 24, Graphics.RIGHT|Graphics.TOP);
				g.setColor(0xff0000);
				g.drawString("E: "+Sound.getErrors(), getWidth(), 32, Graphics.RIGHT|Graphics.TOP);
				
				
				g.setColor(0xffffff);
				String mode="UNKNOWN ("+Sound.getAudioMode()+")";
				switch(Sound.getAudioMode()) {
					case Sound.AUDIO_MODE_RIPPED:
						if(Sound.supportsMixing) g.setColor(0xffc90e);
						mode="RIPPED";
					break;
					case Sound.AUDIO_MODE_COMPATIBLE:
						if(Sound.supportsMixing) g.setColor(0xffc90e);
						mode="COMPATIBLE";
					break;
					case Sound.AUDIO_MODE_QUALITY:
						if(!Sound.supportsMixing) g.setColor(0xff0000);
						mode="QUALITY";
					break;
					case Sound.AUDIO_MODE_ULTRA:
						if(!Sound.supportsMixing) g.setColor(0xff0000);
						mode="ULTRA";
					break;
				}
				
				g.drawString("Audio Mode: "+mode, GameEngine.getCanvas().getWidth(), 46, Graphics.RIGHT|Graphics.TOP);
				
			}
				
			
			repaint();
			
		}catch(Exception e) {
			forceError(e);
		}catch(Error e) {
			forceError(e);
		}
	}
	
	/**
	 * Get game's resolution width
	 * @return width
	 */
	public final int getResolutionWidth() {
		if(screenMode == GameEngine.SCREEN_DYNAMIC) {
			return getWidth(); 
		}
		
		return 320;
	}
	
	/**
	 * Get gane's resolution height
	 * @return height
	 */
	public final int getResolutionHeight() {
		if(screenMode == GameEngine.SCREEN_DYNAMIC) {
			return getHeight(); 
		}
		
		return 240;
	}
	
	public void onDestroy() {
		
		try {
			if(view!=null)view.onGameViewDestroy();
		}catch(Exception e) {
			forceError(e);
		}catch(Error e) {
			forceError(e);
		}
		
	}
	
	/**
	 * Game Loop
	 */
	public void run() {
		this.stoped = false;
		systemDebugTime=System.currentTimeMillis();
		while (!stoped) {
			
			try {
				
				if(viewToChange!=null) {
					
					if(view!=null)view.onGameViewDestroy();
					//Sound.garbageCollect();
					System.gc(); //Prevents java.lang.OutOfMemoryError
					viewToChange.onGameViewBegin();
					view = viewToChange;
					Control.assignGameView(view);
					
					viewToChange = null;
					
				}
				
				if(secondViewToChange!=null) {
					
					if(secondScreenView!=null)secondScreenView.onGameViewDestroy();
					System.gc();
					secondViewToChange.onGameViewBegin();
					secondScreenView = secondViewToChange;
					
					secondViewToChange = null;
					
				}
				
				synchronized(this) {
					
					if(paused) {
						this.wait();
					}
					
				}
				
				if(lastHeight!=getHeight() || lastWidth!=getWidth()) {
					
					final int wDif = (int)((double)(getWidth())-(double)(lastWidth));
					final int hDif = (int)((double)(getHeight())-(double)(lastHeight));
					
					if(view!=null) view.onDisplaySizeChange(lastWidth, lastHeight, wDif, hDif);
					this.lastWidth = getWidth();
					this.lastHeight = getHeight();
					
					if(getWidth()<=200 && getHeight()<=200 && Math.min(getWidth(), getHeight()) <= minRes) {
						flipPhoneClosed=true;
					}else {
						flipPhoneClosed=false;
					}
					
				}
				
				if(error||!flipPhoneClosed||secondScreenView==null) {
					if(view!=null) {
						
						view.update();
					}
				}else {
					
					if(secondScreenView!=null) secondScreenView.update();
					
				}
				
				long interval = System.currentTimeMillis();
				interval = System.currentTimeMillis() - interval;
				interval = ((1000 / framelimit) - interval);
				syncCurrentTimeMillis+=1000/framelimit;
				
				if(System.currentTimeMillis()-systemDebugTime>=1000) {
					systemDebugTime=System.currentTimeMillis();
					ramConsumeCalc();
				}
				
				Thread.sleep(interval > 0 ? interval : 0x00);
			} catch (Exception e) {
				forceError(e);
			} catch (Error e) {
				forceError(e);
			}
		}
		this.stoped = true;
	}
	
	private void waitGarbageCollection(int timeout) {
		
		long mem = Runtime.getRuntime().freeMemory();
		System.gc();
		long millis = System.currentTimeMillis();
		while(mem>=Runtime.getRuntime().freeMemory()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {}
			
			if(System.currentTimeMillis()-millis>=timeout) break;
		}
		
	}
	
	/**
	 * Force an error
	 * @param Error
	 */
	public void forceError(Error e) {
		forceError(e.toString());
		e.printStackTrace();
	}
	
	/**
	 * Force an error
	 * @param Exception
	 */
	public void forceError(Exception e) {
		forceError(e.toString());
		e.printStackTrace();
	}
	
	/**
	 * Force an custom error with a String message
	 * @param Error
	 */
	public void forceError(String error) {
		
		System.gc();
		stop();
		
		String throwError = error;
		if(throwError.indexOf(':')>=0)
			throwError = error.substring(0, error.indexOf(':'));
		
		boolean stopThreads = true;
		try {
			stopThreads=view.onerror(throwError);
			if(secondScreenView!=null) secondScreenView.onerror(throwError);
		}catch(Exception e) {}catch(Error e) {}
		
		secondScreenView = null;
		
		try {
			if(stopThreads) {
				viewToChange = null;
				view=null;
				this.error = true;
				waitGarbageCollection(5000);
				
				view = new ErrorView(error);
				view.onGameViewBegin();
			}
		}catch(Exception e) {}catch(Error e) {}
		
		start();
		
		
	}
	
	/**
	 * Get main game canvas
	 * @return canvas
	 */
	public static GameEngine getCanvas() {
		return engine;
	}
	
	/**
	 * Get defined FPS limit
	 * @return FPS limit
	 */
	public final int getFPSLimit(){
		
		return framelimit;
		
	}
	
	/**
	 * Set a FPS limit
	 * EXPERIMENTAL
	 * @param framelimit
	 */
	public final void setFPSLimit(int framelimit) { //NOT TESTED FEATURE
		this.framelimit = framelimit;
	}
	
	/**
	 * Set screen mode
	 * SCREEN_DYNAMIC = Adapts to screen resolution
	 * SCREEN_FIXED = Fix to 320x240
	 * @param screenMode
	 */
	public final static void setScreenMode(boolean screenMode) {
		if(GameEngine.screenMode!=screenMode) {
			GameEngine.screenMode = screenMode;
			if(screenMode == GameEngine.SCREEN_DYNAMIC) {
				DebugInfo("Screen Mode set to DYNAMIC");
			}else {
				DebugInfo("Screen Mode set to FIXED");
			}
		}
	}
	
	
	/**
	 * Returns screen mode
	 * @return screenMode
	 */
	public final static boolean getScreenMode() {
		return screenMode;
	}
	
	/**
	 * Set default's screen mode
	 * @param defaultScreenMode
	 */
	public final static void setDefinedScreenMode(boolean definedScreenMode) {
		GameEngine.definedScreenMode = definedScreenMode;
	}
	
	/**
	 * Get default screen mode
	 * @return defaultScreenMode
	 */
	public final static boolean getDefinedScreenMode() {
		return definedScreenMode;
	}
	
	/**
	 * Reset screen mode to default
	 */
	public final static void resetScreenMode() {
		
		setScreenMode(getDefinedScreenMode());
		
	}
	
	/**
	 * Sets graphics quality
	 * GRAPHICS_LOW = Low graphics
	 * GRAPHICS_HIGH = High graphics
	 * @param graphicsq
	 */
	public final static void setGraphicsQuality(boolean graphicsq) {
		
		GameEngine.graphicsq = graphicsq;
		
		if(graphicsq) {
			DebugInfo("Graphics set to HIGH");
		}else {
			DebugInfo("Graphics set to LOW");
		}
		
	}
	
	/**
	 * Returns graphics quality
	 * @return graphicsQuality
	 */
	public final static boolean getGraphicsQuality() {
		return graphicsq;
	}
	
	protected void keyPressed(int keyCode) {
		
		if(keyCode==35) {
			showDebug=!showDebug;
		}
		
		try {
			Control.keyDown(keyCode);
		} catch (Exception e) {
			forceError(e);
		} catch (Error e) {
			forceError(e);
		}
		
	}
	protected void keyReleased(int keyCode) {
		
		try {
			Control.keyUp(keyCode);
		} catch (Exception e) {
			forceError(e);
		} catch (Error e) {
			forceError(e);
		}
		
	}
	
	/**
	 * returns systemtime synchronized to FPS in millis
	 * @return syncSystemTimeMillis
	 */
	public static long syncCurrentTimeMillis() {
		
		return syncCurrentTimeMillis;
		
	};
	
}
