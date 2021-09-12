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

import java.io.Reader;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

import josesk.undertaleme.layout.Dialog;

import java.io.IOException;
import java.io.InputStreamReader;

public class Text {
	
	private String content;
	private String aphrase;
	private String speak;
	private String fileName;
	private int headFrame;
	private int phrase, nphrases, index;
	private int delay;
	private Dialog dialog;
	private boolean finished;
	private short times;
	
	/*
	 * Create text with given String
	 * @param Text, dialogName
	 */
	public Text(String s, String dialogName) {
		
		delay = 30;
		headFrame = 0;
		phrase = 0;
		
		content = s;
		
		nphrases = getPhrases();
		fileName = dialogName;
		
		finished = false;
		
	}
	
	/*
	 * Create text with given Source
	 * @param Source
	 * @throws IOException
	 */
	public Text(Source s) throws IOException {
		
		delay = 30;
		headFrame = 0;
		phrase = 0;
		
		content = getFileText(s);
		
		nphrases = getPhrases();
		fileName = s.getSource().substring(s.getSource().lastIndexOf('/')+1);
		
		finished = false;
		
	}
	
	/**
	 * Returns full file's content as a String
	 * @return content
	 * @throws IOException
	 * 
	 */
	public static final String getFileText(Source s) throws IOException{
		
		String source = s.getSource();
		
		String content = "";
		Reader in = new InputStreamReader(s.getClass().getResourceAsStream(source));
		int data = in.read();
		while(data != -1){
			char c = (char) data;
			data = in.read();
			content+=c;
		}
	
		in.close();
		
		return content;
		
	}
	
	private String readPhrase(String phrase) {
		String quote="";
		
		int i=0;
		while(i<=phrase.length()-1) {
			if(phrase.charAt(i)=='\\' && phrase.charAt(i+1)=='h') { // Frame of the head (\h##)
				
				i+=2;
				String number = "";
				number+=phrase.charAt(i);
				i++;
				number+=phrase.charAt(i);
				i++;
				
				headFrame = Integer.valueOf(number).intValue();
				number = null;
				//System.out.print("\\h##");
				
			}else if(phrase.charAt(i)=='\\' && phrase.charAt(i+1)=='d'){ //set delay (\d###)
				
				i+=2;
				
				String number = "";
				number+=phrase.charAt(i);
				i++;
				number+=phrase.charAt(i);
				i++;
				number+=phrase.charAt(i);
				i++;
				
				delay = Integer.valueOf(number).intValue();
				number = null;
				//System.out.print("\\d###");
				
			}else {
			
				quote+=phrase.charAt(i);
				//System.out.print(phrase.charAt(i));
				i++;
			}
			
		}
		
		if(this.phrase>0)quote = quote.substring(1, quote.length());
		
		aphrase = quote;
		//System.out.println();
		return quote;
		
	}

	private String getPhrase(int phrase) {
		
		int i=0;
		int f=0;
		
		while(f!=phrase && i<=content.length()-1) {
			
			i++;
			if(i<content.length()-1 && content.charAt(i)=='\\' && content.charAt(i+1)=='r') { //End of the phrase
				
				i+=2;
				f++;
				
			}
			
		}
		
		String quote="";
		
		while(i<=content.length()-1) {
			
			
			quote+=content.charAt(i);
			i++;
			if(i<content.length()-1 && content.charAt(i)=='\\' && content.charAt(i+1)=='r') { //End of the phrase
				if(quote.charAt(1)=='\n') {
					quote = quote.substring(1);
				}
				return quote;
				
			}
			
			
		}
		
		if(quote.charAt(1)=='\n') {
			quote = quote.substring(1);
		}
		return quote;
		
	}
	
	/**
	 * Returns number of phrases
	 * @return int	#phrases
	 */
	public int getPhrases() {
		
		int i=0, f=0;
		
		while(i<content.length()-1) {
			
			i++;
			if(content.charAt(i)=='\\' && content.charAt(i+1)=='r') {
				
				i+=2;
				f++;
				
			}
			
		}
		
		return f;
		
	}
	
	/**
	 * Jump to the next phrase
	 */
	public final void nextPhrase() {
		
		if(phrase<nphrases) {
			setPhrase(phrase+1) ;
		}else {
			finished = true;
		} 
		
	}
	
	
	/**
	 * Jump to the last phrase
	 */
	public final void lastPhrase() {
		if(phrase>0) setPhrase(phrase-1);
		
		finished = false;
	}
	
	/**
	 * Jump to the desired phrase
	 * @param desiredPhrase
	 */
	public final void setPhrase(int phrase) {
		
		this.phrase = phrase;
		speak = "";
		index=0;
		readPhrase(getPhrase(phrase));
		lastMillis = System.currentTimeMillis();
		
	}
	
	/**
	 * Returns full current phrase
	 * Example: "\h1OwO What's this? *Stares at your bulge*"
	 * @return fullspeak
	 */
	public final String getFullSpeak() {
		
		return aphrase;
		
	}
	
	
	/**
	 * Returns full current displayable phrase
	 * Example: "\h1OwO What's  this? *Stares"
	 * @return phrase
	 */
	public final int getPhrase() {
		
		return phrase;
		
	}
	
	/**
	 * Return displaying phrase
	 * Example: "OwO What's th"
	 * @return speak
	 */
	public final String getSpeak() {
		
		return speak;
		
	}
	
	/**
	 * Returns current head frame ID
	 * @return int headFrameID
	 */
	public final int getHeadFrame() {
		return headFrame;
	}
	
	/**
	 * Returns file name
	 * @return String fileName
	 */
	public final String getFileName() {
		return fileName;
	}
	
	/*
	 * Resets the text and returns to firs phrase
	 */
	public final void reset() {
		
		setPhrase(0);
		finished = false;
		index=0;
		speak="";
		times=0;
		
	}
	
	/*
	 * Begin the texting
	 */
	public final void start() {
		
		times=0;
		
	}
	
	/**
	 * Skips and stops texting
	 * @throws Exception
	 * @throws Error
	 */
	public final void skip(){
		
		speak = aphrase;
		index = aphrase.length();
		finished = true;
		if(dialog!=null) dialog.onTextFinish();
		
	}
	
	/*
	 * Set a dialog frame GUI
	 */
	public final void setDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	
	/*
	 * Render text
	 * @param Graphics	g
	 * @param int	color
	 * @param Font	font
	 * @param Text	text
	 * @param int	xLimit
	 * @param int	x
	 * @param int	y
	 * @param boolean	renderStar
	 */
	public static void DrawText(Graphics g, int color, Font font, Text text, int xlimit, int x, int y, boolean star) {
		
		int fontsizeheight=font.getHeight();
		
		int line = 0;
		int col = 0;
		
		g.setFont(font);
		g.setColor(color);
		
		if(star){
			g.drawString("*", x, y, Graphics.TOP | Graphics.LEFT);
			col+=font.charWidth('*');
		}
		
		//GameEngine.Debug("Speak: "+text.getSpeak());
		//GameEngine.Debug("FullSpeak: "+text.getFullSpeak());
		for(int i=0; i<text.getSpeak().length(); i++) {
			
			int j=i;
			int calcol = col;
			while(j<text.getFullSpeak().length()-1 && text.getFullSpeak().charAt(j)!=' ' && text.getFullSpeak().charAt(j)!='\n') {
				j++;
				if(x+calcol>=xlimit) {
					col=0;
					line++;
					break;
				}
			}
			if(i<text.getSpeak().length()-1 && text.getSpeak().charAt(i)=='\n') {
				col=0;
				line++;
				i++;
			}
			
			//GameEngine.Debug(text.getSpeak().charAt(i));
			g.drawChar(text.getSpeak().charAt(i), x+col, y+(fontsizeheight*line), Graphics.TOP | Graphics.LEFT);
			col+=font.charWidth(text.getSpeak().charAt(i));
			
		}
		
		//GameEngine.Debug();
		
	}
	
	public long lastMillis;
	/**
	 * Update texting
	 * @throws Error
	 * @throws Exception
	 */
	public void Update(float delta){
		
		times+=delta*1000;
		
		if(aphrase!=null && index<aphrase.length() && times>=delay) {
			
			if(speak.length()<=0 && dialog!=null) dialog.onTextStart();
			
			int lr = times/delay;
			
			if(times>delay) {
				if(dialog!=null) dialog.onTextResume();
				times=0;
			}
			
			write:{
				for(int i=0; i<lr; i++) {
				
					if(index<aphrase.length()) {
						
						speak+=aphrase.charAt(index);
						index++;
						//System.out.println(index);
						//sync.reset(sync.late());
						
						if(speak.endsWith(",") | speak.endsWith("!") | speak.endsWith("?")) {
							times=-200;
							if(dialog!=null) dialog.onTextPause();
							break write;
						}
						
						if(index>=aphrase.length()) {
							if(dialog!=null) dialog.onTextFinish();
							break write;
						}
					
					}
				
				}
			}
			
		}
		
	}
	
	/**
	 * Checks if all the speak is finished
	 * @return boolean	finished
	 */
	public final boolean isFinished() {
		return finished;
	}
	
}
