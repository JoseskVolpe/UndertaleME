/*                                                                                                                                                                                         ▒▒▓▓▒▒░░  
                                             
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
*
*Pre-alpha version
*TO-DO:
*-Keymap adaptation (each device has different keymaps, check https://github.com/Nokia64/J2ME-phone-quirks/blob/master/J2ME-phone-quirks.csv)
*-Touchscreen support
*-Dynamic control map with settings
*
*/

package josesk.javameengine;

import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;

/**@deprecated this shall be rescripted with a completely new structure*/
public class Control {
	
	protected final static String[] CommandName = {"Left", "Right", "Up", "Down", "Ok", "Back", "Right Soft", "Left Soft"}; //Commands names
	protected final static int[] keys = { //Key codes
			-5, //Center
			-4, //Right
			-3, //Left
			-2, //Down
			-1, //Up
			-6, //Left action
			-6, //Left action
			-7, //Right action
			-7, //Right action
			8, //Clear
			119, //w
			97, //a
			115, //s
			100, //d
			50, //2
			52, //4
			56, //8
			54, //6
			53, //5
			49, //1
			49, //1
			51, //3
			51, //3
			55, //7
			55, //7
			57, //9
			57, //9
			122, //z
			120 //x
	}; 
	protected final static int[] keyName = { //Key index (CommandName)
			4, //Center
			1, //Right
			0, //Left
			3, //Down
			2, //Up
			4, //Left action
			7, //Left action
			5, //Right action
			6, //Right action
			5, //Clear
			2, //w
			0, //a
			3, //s
			1, //d
			2, //2
			0, //4
			3, //8
			1, //6
			4, //5
			0, //1
			2, //1
			1, //3
			2, //3
			3, //7
			0, //7
			3, //9
			1, //9
			4, //z
			5 //x
	}; 
	
	protected static boolean commandDown[], keyDown[]=new boolean[510];
	protected static boolean initied = false;
	protected static GameView gameview;
	
	/**
	Initialize control engine, OBLIGATORY
	*/
	public static void init() { //OBLIGATORY
		
		commandDown = new boolean[CommandName.length];
		for(int i=0; i<commandDown.length; i++) {
			commandDown[i] = false;
		}
		
		initied = true;
		
	}
	
	/**
	Set key as pressed
	ALPHA, MAY BE DEPRECATED
	@param command	Key's name on array
	*/
	public static void keyDown(String command)  throws Error, Exception{
		
		if(checkInitialization()) {
			
			for(int i=0; i<CommandName.length; i++) {
				if(CommandName[i].equals(command)) {
					
					commandDown[i] = true;
					
				}
			}
			
			if(gameview!=null) gameview.onkeydown(command);
			
		}
		
	}
	
	/**
	Set key as released
	ALPHA, MAY BE DEPRECATED
	@param command	Key's name on array
	*/
	public static void keyUp(String command)  throws Error, Exception{
		
		if(checkInitialization()) {
			
			for(int i=0; i<CommandName.length; i++) {
				if(CommandName[i].equals(command)) {
					
					commandDown[i] = false;
					
				}
			}
			
			if(gameview!=null) gameview.onkeyup(command);
			
		}
		
	}
	
	/**
	Set key as pressed by their ID
	ALPHA, MAY BE DEPRECATED
	NOT ADVISED
	@param key	Key's ID
	*/
	public static void keyDown(int key)  throws Error, Exception{
		
		if(checkInitialization()) {
			
			for(int i=0; i<keys.length; i++) {
				
				if(keys[i]==key) keyDown(CommandName[keyName[i]]);
				
			}
			
			
			
		}
		
		int i=key+255;
		if(i>=0&&i<keyDown.length)
			keyDown[i]=true;
	}
	
	/**
	Set key as released by their ID
	ALPHA, MAY BE DEPRECATED
	NOT ADVISED
	@param key	Key's ID
	*/
	public static void keyUp(int key)  throws Error, Exception{
		
		if(checkInitialization()) {
			
			for(int i=0; i<keys.length; i++) {
				
				if(keys[i]==key) keyUp(CommandName[keyName[i]]);
				
			}
			
			
		}
		int i=key+255;
		if(i>=0&&i<keyDown.length)
			keyDown[i]=false;
		
	}
	
	/**
	Check if key is pressed
	ALPHA, MAY BE DEPRECATED
	@param command	Key's name on array
	@return boolean - Key pressed
	*/
	public static boolean isKeyDown(String command) {
		
		if(checkInitialization()) {
			
			for(int i=0; i<CommandName.length; i++) {
				
				if(CommandName[i].equals(command)) {
					
					return commandDown[i];
					
				}
			}
			
		}
		
		return false;
		
	}
	
	public static boolean isKeyDown(int key) {
		int i=key+255;
		if(i>=0&&i<keyDown.length)
			return keyDown[i];
		
		return false;
	}
	
	public static boolean isKeyUp(int key) {
		int i=key+255;
		if(i>=0&&i<keyDown.length)
			return !keyDown[i];
		
		return true;
	}
	
	/**
	Check if key is released
	ALPHA, MAY BE DEPRECATED
	@param command	Key's name on array
	@return boolean - Key released
	*/
	public static boolean isKeyUp(String command) {
		
		return !isKeyDown(command);
		
	}
	
	/**
	Assign game view
	ALPHA, MAY BE DEPRECATED
	@param gameview Gameview
	*/
	public static void assignGameView(GameView gameview) {
		
		Control.gameview = gameview;
		
	}
	
	/**
	Returns gameview
	ALPHA, MAY BE DEPRECATED
	@return Gameview
	*/
	public static GameView getGameView() {
		return gameview;
	}
	
	/**
	Check keymapping and key-presses
	ALPHA, MAY BE DEPRECATED
	@param key	key's ID to be tested
	*/
	public static void testKey(int key) {
		
		GameEngine.DebugInfo("KEY TEST");
		boolean init = checkInitialization();
		GameEngine.Debug("#Key: "+key);
		GameEngine.Debug("Key names:");
		for(int i=0; i<keys.length; i++) {
			
			if(keys[i]==key) {
				GameEngine.Debug("*"+CommandName[keyName[i]]);
				if(init) GameEngine.Debug("Pressed: "+isKeyDown(CommandName[keyName[i]]));
			}
			
		}
		
		GameEngine.Debug("");
		
	}
	
	/**
	Check if the control engine was initied
	ALPHA, MAY BE DEPRECATED
	@return boolean - Engine initied
	*/
	private static boolean checkInitialization() {
		
		if(!initied) GameEngine.DebugWarn("CONTROL WAS NOT INITIED!!!!!!!!!! ÒwÓ");
		
		return initied;
		
	}

}
