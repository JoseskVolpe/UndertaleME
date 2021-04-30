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

package josesk.javameengine;

import java.io.IOException;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import josesk.undertaleme.GameEngine;

public class LoadResources {
	
	/**
	Load image
	@param source	Image's source
	@return Image
	*/
	public static Image loadImage(String source) {
		
		Image i = null;
		
		try {
			i = Image.createImage(source);
		} catch (IOException e) {
			GameEngine.DebugError(e.toString());
			e.printStackTrace();
		}
		
		return i;
		
	}
	
	/**
	Create Sprite from a spritesheet Image
	@param image	Image
	@param n	Sprite width
	@param n2	Sprite height
	@return Sprite
	*/
	public static Sprite createSprite(Image image, int n, int n2) {
		
		return new Sprite(image, n, n2);
		
	}
	
	/**
	Create Sprite from a spritesheet Image
	@param image	Image
	@return Sprite
	*/
	public static Sprite createSprite(Image image) {
		
		return new Sprite(image);
		
	}
	
	/**
	Create Sprite from a spritesheet Image
	@param source Image's source
	@return Sprite
	*/
	public static Sprite createSprite(String source) {
		
		Sprite s = null;
		
		try {
			s= createSprite(loadImage(source));
		}catch(Exception e) {
			GameEngine.DebugError(e.toString());
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	
	/**
	Create Sprite from a spritesheet Image
	@param source	Image's source
	@param n	Sprite width
	@param n2	Sprite height
	@return Sprite
	*/
	public static Sprite createSprite(String source, int n, int n2) {
		
		Sprite s = null;
		
		try {
			s= createSprite(loadImage(source), n, n2);
		}catch(Exception e) {
			GameEngine.DebugError(e.toString());
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	/**
	Creates a new sound from source
	WARNING: ¡¡¡Only .WAV and .MIDI are supported!!!!
	Other formats may work, but AREN'T supported (They will be identified as music and unsupported)
	Use .WAV for sound effects and .MIDI for music
	@param sound Sound's source
	@param loop	Loop sound
	@return Sound
	*/
	public static Sound loadSound(String sound, boolean loop) {
		
		Sound s = null;
		
		try {
			s = new Sound(sound, loop);
		}catch(Exception e) {
			GameEngine.DebugError(e.toString());
			e.printStackTrace();
		}
		
		return s;
		
	}
	
	/**
	Creates a new unloopable sound from source
	WARNING: ¡¡¡Only .WAV and .MIDI are supported!!!!
	Other formats may work, but AREN'T supported (They will be identified as music and unsupported)
	Use .WAV for sound effects and .MIDI for music
	@param sound Sound's source
	@return Sound
	*/
	public static Sound loadSound(String sound) {
		
		return loadSound(sound);
		
	}

}
