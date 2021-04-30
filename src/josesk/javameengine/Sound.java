package josesk.javameengine;
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

/*
 * This shit gave me a lot of damn work UwU
 * Mas eu terminei essa merda del carajo, aleluia ÙwÚ
 */

//TODO: Compatible mode: Corrigir bug que faz com que a musica continue tocando depois de parada (ugh)
//TODO: Reescrever BestMode - Criar um Player estático em sincronia com o local que servirá para tocar o som. Se algum som for tocar junto com ele, ele será substituído pelo outro que chegou com ele na hora
//TODO: Test it and make a library version for other projects :3

import java.io.InputStream;

import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.VolumeControl;

import josesk.undertaleme.GameEngine;

public class Sound{
	
	public final static String sounds_folder="";
	
	public static final byte AUDIO_MODE_RIPPED=0; //Only music, no sounds. (low-end devices with low jar-size)
	public static final byte AUDIO_MODE_COMPATIBLE=1; //Music and sounds, but music is cut while SFX is played (low-end devices) [AUTO]
	public static final byte AUDIO_MODE_QUALITY=2; //Music and sounds, music keeps playing together with SFX, however, only 1 SFX is supported (mid-end devices with a yiffing glitchy MediaPlayer) [AUTO]
	public static final byte AUDIO_MODE_ULTRA=3; //Music and sounds are played together without limitation (high-end devices and emulators)
	public static final byte AUDIO_MODE_AUTO=-1; //Choose audio mode automatically
	
	public final static byte FORMAT_MIDI=0;
	public final static byte FORMAT_WAV=1;
	public final static byte FORMAT_UNSUPPORTED=-1; //Avoid-advised
	public final static byte FORMAT_INCOMPATIBLE=-2; //Will not play
	
	private static final byte CANCEL_LOAD=0;
	private static final byte CANCEL_START=1;
	
	private static boolean DEBUG_MODE=true;
	
	
	public final static boolean supportsMixing = System.getProperty("supports.mixing").toLowerCase().equals("true"); //Indicates if device supports audio mixing
	public final static String mmapiVersion = System.getProperty("microedition.media.version");
	
	private static Sound soundsInMemory[];
	private static byte audio_mode;
	private static boolean inited=false;
	private static int starts, rejects, errors;
	
	protected Player myPlayer;
	protected VolumeControl myVolumeControl;
	private long duration=-1;
	private long lastTime=0;
	private int volume = 100;
	private boolean callToLoad, load, loading, canceled;
	private boolean loop, playing;
	private String source;
	private byte format;
	private InputStream is;
	private SoundService soundService;
	private byte ID;
	
	/**
	Initialize sound engine
	OBLIGATORY
	
	AUDIO_MODE_RIPPED = plays only music and will not throw error if a SFX is missing on resources. Low-end devices with lower space
	AUDIO_MODE_COMPATIBLE = Pauses music and plays SFX. Low-end devices with no supports_mixing support
	AUDIO_MODE_QUALITY = Has 2 buffers and play 1 music and 1 SFC. Mid-end devices with low supports_mixing support
	AUDIO_MODE_ULTRA = Has multiple buffers and play 1 music and multiple SFX simultaneous. High-end devices
	AUDIO_MODE_AUTO = Automatically set by engine system
	@param audio_mode Audio mode it'll initialize. Check AUDIO_MODE
	*/
	public static final void initialize(byte audio_mode) {
		
		if(!inited) {
			GameEngine.DebugInfo("Welcome to J2ME Audio Engine by Josesk Volpe! OwO");
			GameEngine.DebugInfo("*MMAPI version: "+mmapiVersion);
			GameEngine.DebugInfo("*Supports mixing: "+supportsMixing);
			
			if(audio_mode == AUDIO_MODE_AUTO) {
				
				if(supportsMixing) {
					Sound.audio_mode = AUDIO_MODE_QUALITY;
				}else {
					Sound.audio_mode = AUDIO_MODE_COMPATIBLE;
				}
				
			}else {
				Sound.audio_mode = audio_mode;
			}
			
			switch(Sound.audio_mode) {
				case AUDIO_MODE_RIPPED:
					GameEngine.DebugInfo("*Starting in RIPPED MODE");
					soundsInMemory = new Sound[1];
				break;
				case AUDIO_MODE_COMPATIBLE:
					GameEngine.DebugInfo("*Starting in COMPATIBLE MODE");
					soundsInMemory = new Sound[2];
				break;
				case AUDIO_MODE_QUALITY:
					GameEngine.DebugInfo("*Starting in QUALITY MODE");
					soundsInMemory = new Sound[2];
				break;
				case AUDIO_MODE_ULTRA:
					GameEngine.DebugInfo("*Starting in ULTRA MODE");
					soundsInMemory = new Sound[20];
				break;
			}
			
			inited=true;
		}else {
			GameEngine.DebugError("Audio Engine already initied");
		}
		
	}
	
	/**
	Set debug mode
	Default: false
	True: Throws each loads and plays
	@param command	Key's name on array
	*/
	public final static void setDebugMode(boolean DEBUG_MODE) {
		Sound.DEBUG_MODE=DEBUG_MODE;
	}
	
	/**
	Get debug mode
	@return boolean - Debug mode
	*/
	public final static boolean getDebugMode() {
		return Sound.DEBUG_MODE;
	}
	
	/**
	Returns audio mode
	@return audio mode
	*/
	public final static byte getAudioMode() {
		return audio_mode;
	}
	
	/**
	Get decoder format from a extension
	@param extension	Extension
	@return String	Decoder
	*/
	public final static String getDecoder(String extension) {
		
		if(extension.equals("wav")||extension.equals("wave")) { //Use for SFX
            return "audio/X-wav";
        }
        if(extension.equals("midi") || extension.equals("mid")) { //Use for music
            return "audio/midi";
        }
        if(extension.equals("amr")) { //AVOID IT
            return "audio/amr";
        }
        if(extension.equals("mp3")) { //AVOID IT
        	return "audio/mpeg";
        }
        
        return null;
		
	}
	
	/**
	@param source	Sound source
	@param loop	loopable
	*/
	public Sound(String source, boolean loop) throws Error, Exception{
		
		this.source = source;
		this.loop = loop;
		
		initSound();
		
	}
	
	/**
	@param source	Sound source
	*/
	public Sound(String source) throws Error, Exception {
		
		this.source=source;
		loop=false;
		
		initSound();
		
	}
	
	private final void initSound() {
		
		String ext = getExtension(source);
		String dec = getDecoder(ext);
		
		if(dec.equals("audio/X-wav")) {
			format = FORMAT_WAV;
		}else if(dec.equals("audio/midi")){
			format = FORMAT_MIDI;
		}else if(dec.equals(null)){
			format = FORMAT_INCOMPATIBLE;
			GameEngine.DebugError("."+ext.toUpperCase()+" is not compatible as audio!");
	        GameEngine.DebugError("Please, export as .MID (for music) or .WAV (for SFX)");
		}else {
			format = FORMAT_UNSUPPORTED;
			GameEngine.DebugWarn("."+ext.toUpperCase()+" sounds are not supported!");
        	GameEngine.DebugWarn("Sound Player will play it anyways as a MUSIC");
		}
		
		switch(audio_mode) {
		//TODO: audio services
			case AUDIO_MODE_RIPPED:
				soundService = new RippedModeService();
			break;
			case AUDIO_MODE_COMPATIBLE:
				soundService = new CompatibleModeService();
			break;
			case AUDIO_MODE_QUALITY:
				soundService = new QualityModeService();
			break;
			case AUDIO_MODE_ULTRA:
				soundService = new UltraModeService();
			break;
		}
		
		
	}
	
	/**
	Get extension from a source
	@param source	Sound source
	@return extension
	*/
	public final String getExtension(String source) { //Returns audio extension (.mid, .mp3, .wav, etc)
		
		return source.substring(source.lastIndexOf('.')+1);
		
	}
	
	/**
	Close ALL realized audios into buffer array
	*/
	public static final void closeAll() {
		
		if(DEBUG_MODE) GameEngine.DebugInfo("Closing all realized audios...");
		
		int closed=0;
		
		for(int i=0; i<soundsInMemory.length; i++) {
			if(soundsInMemory[i]==null) continue;
			try {
				
				if(soundsInMemory[i].Close()) {
					closed++;
				}
				
			}catch(Exception e) {
				GameEngine.DebugError("closeAll(): "+soundsInMemory[i].getSource()+" not closed!");
				GameEngine.DebugError(e.toString());
			}catch(Error e) {
				GameEngine.DebugError("closeAll(): "+soundsInMemory[i].getSource()+" not closed!");
				GameEngine.DebugError(e.toString());
			}
			
		}
		
		if(DEBUG_MODE) GameEngine.DebugInfo("Closed "+closed+" realized audios!");
		
	}
	
	private static final void garbageCollect(Sound sound) {
		
		byte errors=0, cleaned=0;
		
		for(int i=0; i<soundsInMemory.length; i++) {
			if(soundsInMemory[i]==null) continue;
			
			Player p = soundsInMemory[i].getPlayer();
			
			try {
			
				if(p==null) {
					soundsInMemory[i]=null;
					cleaned++;
					continue;
				}
				
				if(p.getState()==Player.CLOSED) {
					soundsInMemory[i]=null;
					cleaned++;
					continue;
				}
					
				Sound s = soundsInMemory[i];
					if(s!=null &&  !s.isLoading() && s.amIGarbage(sound)) {
						//if(!soundsInMemory[i].Close()) {  //TODO: unrealize()
						if(!s.Unrealize()) {
							if(DEBUG_MODE) {
								GameEngine.DebugWarn("Audio garbage collection erase warning:");
								GameEngine.DebugWarn("*"+s.getSource()+" not erased!");
							}
							
						}else {
							soundsInMemory[i] = null;
							cleaned++;
						}
					}
				
			}catch(Exception e) {
				errors++;
				GameEngine.DebugError("Audio garbage collection erase error:");
				if(soundsInMemory[i]!=null) GameEngine.DebugError("*"+soundsInMemory[i].getSource()+" not erased by:");
				GameEngine.DebugError("*"+e.toString());
			}catch(Error e) {
				errors++;
				GameEngine.DebugError("Audio garbage collection erase error:");
				if(soundsInMemory[i]!=null) GameEngine.DebugError("*"+soundsInMemory[i].getSource()+" not erased by:");
				GameEngine.DebugError("*"+e.toString());
			}
			
		}
		
		if((errors>0||cleaned>0) && DEBUG_MODE) {
			GameEngine.DebugInfo("Audio engine garbage collection report: ");
			GameEngine.DebugInfo("*"+cleaned+" freed from memory");
			String errorReport = "*"+errors+" errors";
			if(errors>0) {
				GameEngine.DebugWarn(errorReport);
			}else {
				GameEngine.DebugInfo(errorReport);
			}
		}
		
	}
	
	/**
	Force garbage collection in buffering
	*/
	public static final void garbageCollect() {
		
		garbageCollect(null);
		
	}
	
	/**
	Unrealize sound
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@throws Error, Exception
	*/
	public final boolean Unrealize() throws Error, Exception{
		
		if(DEBUG_MODE) GameEngine.DebugInfo("Unrealizing "+getSource());
		
		Close();
		if(myPlayer!=null && myPlayer.getState()>=Player.REALIZED) {
			if(DEBUG_MODE) GameEngine.DebugWarn("[a]Unrealize() failed: "+getSource());
			return false;
		}
		
		if(!loading) {
			loading=true;
			
			closeInputStream();
			
			is = getClass().getResourceAsStream(sounds_folder+getSource());
			
			try {
				myPlayer = Manager.createPlayer(is, getDecoder(getExtension(getSource())));
				myPlayer.addPlayerListener(soundService);
			}catch(Exception e) {
				loading=false;
				if(DEBUG_MODE) GameEngine.DebugError("Unrealize() exception: "+getSource());
				if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
				errors++;
				throw e;
			}catch(Error e) {
				loading=false;
				if(DEBUG_MODE) GameEngine.DebugError("Unrealize() error: "+getSource());
				if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
				errors++;
				throw e;
			}
			
			try {
				myVolumeControl=(VolumeControl)myPlayer.getControl("VolumeControl");
				myVolumeControl.setLevel(volume);
			}catch(Exception e) {myVolumeControl=null;};
			
			try {
				duration=myPlayer.getDuration();
			}catch(Exception e) {/*ignore*/}
			
			load=true;
			
			//GameEngine.DebugInfo("Audio loaded: "+getSource()); //TODO: Erase
			
			loading=false;
			
			if(DEBUG_MODE) GameEngine.DebugInfo(getSource()+" unrealized!");
			
			return true;
			}
		
		if(DEBUG_MODE) GameEngine.DebugWarn("[b]Unrealize() rejected: "+getSource());
		return false;
		
	}
	
	/**
	Reloads sound
	@throws Error, Exception
	*/
	public final boolean Reload() throws Error, Exception{
		
		if(load) return Load();
		
		return false;
		
	}
	
	private final static byte reserveSoundBlock(Sound s) {
		
		for(byte i=0;i<soundsInMemory.length;i++) {
			if(soundsInMemory[i]==null || soundsInMemory[i].getPlayer()==null || (soundsInMemory[i].getPlayer().getState()>=Player.UNREALIZED && soundsInMemory[i].getPlayer().getState()<Player.REALIZED && !soundsInMemory[i].isLoading())) {
				if(soundsInMemory[i]!=null)soundsInMemory[i].Cancel();
				soundsInMemory[i] = s;
				return i;
			}
		}
		
		//Garbage Collect and try again
		garbageCollect(s);
		
		for(byte i=0;i<soundsInMemory.length;i++) {
			if(soundsInMemory[i]==null || soundsInMemory[i].getPlayer()==null || (soundsInMemory[i].getPlayer().getState()>=Player.UNREALIZED && soundsInMemory[i].getPlayer().getState()<Player.REALIZED && !soundsInMemory[i].isLoading())) {
				if(soundsInMemory[i]!=null)soundsInMemory[i].Cancel();
				soundsInMemory[i] = s;
				return i;
			}
		}
		
		return -1; //Not enough space
		
	}
	
	/**
	Cancel loading or playload
	Useful for multithreaded audios
	*/
	public final void Cancel() {
		canceled=true;
		
		if(myPlayer!=null && myPlayer.getState()>=Player.PREFETCHED) {
			try {
				myPlayer.stop();
			}catch(Exception e) {/*ignore*/}
			try {
				myPlayer.deallocate();
			}catch(Exception e) {/*ignore*/}
			
			playing=false;
		}
		
	}
	
	/**
	Check if audio load or playload was canceled
	Will automatically run soundService.onCanceled(task) if true
	@param task	CANCEL_LOAD CANCEL_START
	*/
	public final boolean isCanceled(byte task) {
		boolean c=canceled;
		canceled=false;
		
		if(c) {
			try {
				soundService.onCanceled(task);
			} catch (Error e) {} catch (Exception e) {}
		}
		
		return c;
	}
	
	/**
	Loads sound
	@throws Error, Exception
	*/
	public final boolean Load() throws Error, Exception{
		
		callToLoad=true;
		
		if(DEBUG_MODE) GameEngine.DebugInfo("Loading "+getSource());
		
		if(!soundService.onLoadCall()) {
			if(DEBUG_MODE) GameEngine.DebugWarn("[a]Load() rejected: "+getSource());
			return false;
		}
		
		//GameEngine.DebugInfo("Loading: "+getSource());
		canceled=false;
		
		if(loading) {
			if(DEBUG_MODE) GameEngine.DebugWarn("[b]Load() rejected: "+getSource());
			return false;
		}
		
		loading=true;
		
		if(format==FORMAT_INCOMPATIBLE) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[c]Load() rejected: "+getSource());
			return false;
		}
		
		if(load && myPlayer!=null && myPlayer.getState()!=Player.CLOSED) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugInfo(getSource()+" already loaded!");
			return true;
		}
		
		if(ID>=0 && soundsInMemory[ID]==this) soundsInMemory[ID].Close();
	
		if(isCanceled(CANCEL_LOAD)) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[d]Load() canceled: "+getSource());
			return false;
		}
		
		if(!soundService.preload()) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[e]Load() failed: "+getSource());
			return false;
		}
		
		if(isCanceled(CANCEL_LOAD)) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[f]Load() canceled: "+getSource());
			return false;
		}
		
		ID = reserveSoundBlock(this);
		if(ID<0) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[g]Load() rejected: "+getSource());
			return false;
		}
		
		closeInputStream();
		
		is = getClass().getResourceAsStream(sounds_folder+getSource());
		
		try {
			myPlayer = Manager.createPlayer(is, getDecoder(getExtension(getSource())));
			myPlayer.addPlayerListener(soundService);
		}catch(Exception e) {
			soundsInMemory[ID]=null;
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugError("Load() exception: "+getSource());
			if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
			errors++;
			throw e;
		}catch(Error e) {
			soundsInMemory[ID]=null;
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugError("Load() error: "+getSource());
			if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
			errors++;
			throw e;
		}
		
		if(myPlayer==null) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[h]Load() failed: "+getSource());
			return false;
		}
		
		soundService.Loaded();
		
		if(myPlayer==null) {
			loading=false;
			if(DEBUG_MODE) GameEngine.DebugWarn("[i]Load() failed: "+getSource());
			return false;
		}
		
		try {
			myVolumeControl=(VolumeControl)myPlayer.getControl("VolumeControl");
			myVolumeControl.setLevel(volume);
		}catch(Exception e) {myVolumeControl=null;};
		
		try {
			duration=myPlayer.getDuration();
		}catch(Exception e) {/*ignore*/}
		
		load=true;
		
		//GameEngine.DebugInfo("Audio loaded: "+getSource()); //TODO: Erase
		
		loading=false;
		
		//.DebugInfo("Loaded ID "+ID+": "+getSource());
		
		if(ID<0 || soundsInMemory[ID]!=this) {
			
			Unrealize();
			if(DEBUG_MODE) GameEngine.DebugWarn("[j]Load() rejected: "+getSource());
			return false;
			
		}
		
		if(DEBUG_MODE) GameEngine.DebugInfo(getSource()+" loaded!");
		return true;
		
	}
	
	/**
	Check if sound is set as loopable
	@returns boolean	loop
	*/
	public boolean isLooping() {
		return loop;
	}
	
	/**
	Set sound loopable/unloopable
	@param loop	loopable
	*/
	public void setLoop(boolean loop){
		this.loop = loop;
	}
	
	/**
	Get sound's volume
	NOT COMPATIBLE ON EVERY DEVICE
	Not compatible: Just returns 100
	
	@return int	volume
	
	*/
	public int getVolume() {
		if(myVolumeControl!=null) {
			return myVolumeControl.getLevel();
		}
		return 100;
	}

	
	/**
	Set sound's volume
	NOT COMPATIBLE ON EVERY DEVICE
	Not compatible: Do nothing
	
	@param volume	Desired volume
	
	*/
	public void setVolume(int volume) {
		this.volume = volume;
		if(myVolumeControl!=null) {
			myVolumeControl.setLevel(volume);
		}
	}
	
	/**
	Get sound's duration
	MUST be realized at least once and have duration greater than 1 second
	
	@return duration
	*/
	public long getDuration() {
		return duration;
	}
	
	/**
	Get sounds plays on systemtime
	Debugging purposes
	
	@return int	starts
	
	*/
	public final static int getStarts() {
		return starts;
	}
	
	/**
	Get sounds rejects on systemtime
	Debugging purposes
	
	@return int	rejects
	
	*/
	public final static int getRejects() {
		return rejects;
	}
	
	/**
	Get sounds errors on systemtime
	Debugging purposes
	
	@return int	errors
	
	*/
	public final static int getErrors() {
		return errors;
	}
	
	/**
	Check if audio was loaded
	
	@return boolean loaded
	
	*/
	public final boolean isLoaded() {
		return load;
	}
	
	/**
	Check if audio is loading
	
	@return boolean loading
	
	*/
	public final boolean isLoading() {
		return loading;
	}
	
	private final boolean closeInputStream() {
		if(is!=null) {
			try {
				is.close();
				load=false;
				is=null;
				return true;
			}catch(Exception e) {/*ignore*/}
		}
		
		return false;
	}
	
	/**
	Check if sound is garbage
	
	@return boolean garbage
	
	*/
	public final boolean amIGarbage(Sound sound) {
		
		if(soundService==null || myPlayer==null) return true;
		
		return soundService.amIGarbage(sound);
	}
	
	/**
	Close sound
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@throws Error, Exception
	*/
	public final boolean Close() throws Error, Exception{
		
		if(DEBUG_MODE) GameEngine.DebugInfo("Closing "+getSource());
		
		if(myPlayer!=null) {
			
			if(myPlayer.getState() == Player.STARTED) {
				try {
					myPlayer.stop();
					Thread.sleep(50);
				}catch(Exception e) {/*ignore*/}
			}
			
			if(myPlayer.getState() == Player.PREFETCHED) {
				try {
					myPlayer.deallocate();
					Thread.sleep(50);
				}catch(Exception e) {/*ignore*/}
			}
			
			try {
				myPlayer.close();
				Thread.sleep(50);
				closeInputStream();
				if(ID>=0 && soundsInMemory[ID]==this) {
					soundsInMemory[ID] = null;
					myPlayer=null;
				}
				if(DEBUG_MODE) GameEngine.DebugInfo("[a]"+getSource()+" closed!");
				return true;
			}catch(Exception e) {
				if(DEBUG_MODE) GameEngine.DebugError("Close() error: "+getSource());
				if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
				errors++;
				return false;
			}
		}else {
			if(closeInputStream()) {
				if(ID>=0 && soundsInMemory[ID]==this) soundsInMemory[ID] = null;
				if(DEBUG_MODE) GameEngine.DebugInfo("[b]"+getSource()+" closed!");
				return true;
			}
			
			if(DEBUG_MODE) GameEngine.DebugWarn("Close() failed: "+getSource());
			return false;
		}
		
	}
	
	/**
	Play sound on determined time
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@param Media time in microseconds
	@throws Error, Exception
	*/
	public final boolean Start(long time) throws Exception, Error {
		
		try {
			
			if(DEBUG_MODE) GameEngine.DebugInfo("Starting "+getSource());
			
			if(!soundService.onStartCall(time)) {
				if(DEBUG_MODE) GameEngine.DebugWarn("[a] Start() rejected: "+getSource());
				rejects++;
				return false;
			}
			
			canceled=false;
			
			if(!load) {
				
				if(!callToLoad) {
					GameEngine.DebugWarn("Load() the following sound before Start()ing it: ");
					GameEngine.DebugWarn("*"+getSource());
				}
				
				if(!Load()) {
					rejects++;
					if(DEBUG_MODE) GameEngine.DebugWarn("[b] Start() rejected: "+getSource());
					return false;
				}
				
			}
			
			if(myPlayer==null || (myPlayer.getState()>=Player.CLOSED && myPlayer.getState()<Player.UNREALIZED)) {
				if(!Reload()) {
					rejects++;
					if(DEBUG_MODE) GameEngine.DebugWarn("[c] Start() rejected: "+getSource());
					return false;
				}
			}
			
			if(isCanceled(CANCEL_START)) {
				if(DEBUG_MODE) GameEngine.DebugWarn("[d] Start() canceled: "+getSource());
				return false;
			}
			
			if(!soundService.preStart(time)) {
				if(DEBUG_MODE) GameEngine.DebugWarn("[e] Start() rejected: "+getSource());
				rejects++;
				return false;
			}
			
			if(isCanceled(CANCEL_START)) {
				Unrealize();
				if(DEBUG_MODE) GameEngine.DebugWarn("[f] Start() canceled: "+getSource());
				return false;
			}
			
			if(ID<0 || soundsInMemory[ID]!=this) {
				boolean found=false;
				for(byte i=0; i<soundsInMemory.length; i++) {
					if(soundsInMemory[i]==this) {
						ID=i;
						found=true;
						break;
					}
						
				}
				if(!found) {
					byte tempID=reserveSoundBlock(this);
					if(tempID>=0) {
						ID = tempID;
					}else {
						Unrealize();
						rejects++;
						if(DEBUG_MODE) GameEngine.DebugWarn("[g] Start() rejected: "+getSource());
						return false;
					}
				}
				
			}
			
			boolean sucess = soundService.Start(time);
			if(!sucess) {
				rejects++;
				if(DEBUG_MODE) GameEngine.DebugWarn("Start() failed: "+getSource());
			}else {
				starts++;
				if(DEBUG_MODE) GameEngine.DebugInfo(getSource()+" started!");
			}
			
			return sucess;
			
		}catch(Exception e) {
			if(DEBUG_MODE) GameEngine.DebugError("Start() exception: "+getSource());
			if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
			errors++;
			throw e;
		}catch(Error e) {
			if(DEBUG_MODE) GameEngine.DebugError("Start() error: "+getSource());
			if(DEBUG_MODE) GameEngine.DebugError("*"+e.toString());
			errors++;
			throw e;
		}
		
	}
	
	protected void onPlayerUpdate(Player player, String event, Object eventData) {}
	
	/**
	Play sound
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@throws Error, Exception
	*/
	public final boolean Start() throws Exception, Error {
		
		return Start(0);
		
	}
	
	/**
	Stop sound
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@throws Error, Exception
	*/
	public final boolean Stop() throws Exception, Error {
		
		try {
		
			if(DEBUG_MODE) GameEngine.DebugInfo("Stopping "+getSource());
			
			if(!soundService.onStopCall()) {
				if(DEBUG_MODE) GameEngine.DebugWarn("[a] Stop() rejected: "+getSource());
				return false;
			}
			
			boolean sucess = soundService.Stop();
			
			if(sucess) {
				if(DEBUG_MODE) GameEngine.DebugInfo(getSource()+" stoped!");
			}else {
				if(DEBUG_MODE) GameEngine.DebugWarn("[b] Stop() failed: "+getSource());
			}
			
			return sucess;
			
		}catch(Exception e) {
			if(DEBUG_MODE) GameEngine.DebugError("Stop() exception: "+getSource());
			if(DEBUG_MODE) GameEngine.DebugError(e.toString());
			errors++;
			throw e;
		}catch(Error e) {
			if(DEBUG_MODE) GameEngine.DebugError("Stop() error: "+getSource());
			if(DEBUG_MODE) GameEngine.DebugError(e.toString());
			errors++;
			throw e;
		}
		
	}
	
	/**
	Pause sound
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@throws Error, Exception
	*/
	public final boolean Pause() throws Exception, Error {
		
		if(DEBUG_MODE) GameEngine.DebugInfo("Pausing "+getSource());
		
		if(!soundService.onPauseCall()) {
			if(DEBUG_MODE) GameEngine.DebugWarn("[a]Pause() rejected: "+getSource());
			return false;
		}
		
		if(soundService.Pause()) {
			
			try {
				lastTime=myPlayer.getMediaTime();
			}catch(Exception e) {/*ignore*/}
			
			if(DEBUG_MODE) GameEngine.DebugInfo(getSource()+" paused!");
			
			return true;
		}
		
		if(DEBUG_MODE) GameEngine.DebugWarn("[b]Pause() failed: "+getSource());
		return false;
		
	}
	
	/**
	Resume sound from where it was paused last time
	Check https://nikita36078.github.io/J2ME_Docs/docs/jsr135/ (Although it don't say about their damn glitches on this technlogy. Ugh, so much headache)
	@throws Error, Exception
	*/
	public final boolean Resume() throws Exception, Error{
		
		return Start(lastTime);
		
	}
	
	/**
	Check is audio is playing
	@return playing
	*/
	public final boolean isPlaying() {
		
		return playing;
		
	}
	
	/**
	Returns last media time since last pause()
	@return lastTime
	*/
	public final long getLastTime() {
		return lastTime;
	}
	
	/**
	Returns audio's source
	@return String	source
	*/
	public final String getSource() {
		
		return source;
		
	}
	
	/**
	Return sound's format
	FORMAT_MIDI = Music, MIDI files
	FORMAT_WAV = SFX, WAVE files
	FORMAT_UNSUPPORTED = Unsupported glitcy audio files, will try to play anyways, music
	FORMAT_INCOMPATIBLE = Incompatible audio files or innexistent. Avoid that
	@return format
	*/
	public final byte getFormat() {
		return format;
	}
	
	private final Sound getSound() {
		return this;
	}
	
	/**
	 Returns default MMAPI player
	 ADVANCED DEVELOPERS
	 @return Player
	 */
	public final Player getPlayer() {
		return myPlayer;
	}
	
	/**
	 Returns current media time
	 Unsupported or not realized = -1
	 @return long	mediaTime
	 */
	public final long catchMediaTime() {
		try {
			
			lastTime = myPlayer.getMediaTime();
			return lastTime;
			
		}catch(Exception e) {/*ignore*/}
		
		return -1;
	}
	
	/**
	 Returns buffer array length
	 * @return int	bufferLength
	 */
	public final static int getloadedSoundsSize() {
		return soundsInMemory.length;
	}
	
	/**
	 Returns sound by their ID
	 * @param ID	Sound ID
	 * @return Sound
	 */
	public final static Sound getSoundByID(byte ID) {
		
		return soundsInMemory[ID];
		
	}
	
	private static interface SoundService extends PlayerListener{
		
		public boolean preload() throws Error, Exception;
		public boolean onLoadCall() throws Error, Exception;
		public void Loaded() throws Error, Exception;
		public boolean preStart(long time) throws Error, Exception;
		public boolean Start(long time) throws Error, Exception; //Play the audio - Returns success/failure
		public boolean onStartCall(long time) throws Error, Exception;
		public boolean Stop() throws Error, Exception; //Stop the audio - Returns success/failure
		public boolean onStopCall() throws Error, Exception;
		public boolean Pause() throws Error, Exception; //Pause the audio - Returns success/failure
		public boolean onPauseCall() throws Error, Exception;
		public void onCanceled(byte task) throws Error, Exception;
		public boolean amIGarbage(Sound sound);
		public void playerUpdate(Player player, String event, Object eventData); //When the audio's player gets updated
		
	}
	
	private final class UltraModeService implements SoundService{

		public boolean preStart(long time) throws Error, Exception{
			
			if(myPlayer.getState()>=Player.UNREALIZED && myPlayer.getState()<Player.REALIZED) {
				try {
					myPlayer.realize();
					Thread.sleep(50);
				}catch(Exception e) {
					Unrealize();
					if(!Reload()) {
						return false;
					}
				}
			}
			
			return true;
		}
		
		public boolean Start(long time) throws Error, Exception {
			
			long t = time;
			if(t<=0) t=0;
			
			if(isCanceled(CANCEL_START)) {
				Close();
				return false;
			}
			
			if(myPlayer.getState()==Player.REALIZED && (format==FORMAT_MIDI || format==FORMAT_UNSUPPORTED)) {
				try{
					myPlayer.prefetch();
					Thread.sleep(50);
				}catch(Exception e) {/*ignore*/}
			}
			
			if(isCanceled(CANCEL_START)) {
				Close();
				return false;
			}
			
			boolean timeSet=false;
			if(t>0) {
				try{
					myPlayer.setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {/*ignore*/}
			}
			
			try {
				myPlayer.start();
				playing=true;
			}catch(Exception e) {
				try {
					myPlayer.deallocate();
				}catch(Exception e2) {}
				return false;
			}catch(Error e) {
				GameEngine.DebugError("Error playing sound: "+getSource());
				GameEngine.DebugError(e.toString());
				return false;
			}
			
			if(!timeSet && t>0) {
				try{
					myPlayer.setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {/*ignore*/}
			}
			
			return true;
		}

		public boolean Stop() throws Error, Exception {
			
			if(myPlayer==null) return false;
			if(myPlayer.getState()==Player.STARTED) {
				try {
					myPlayer.stop();
					Thread.sleep(50);;
				}catch (Exception e){
					return false;
				}
			}
			
			try {
				myPlayer.deallocate();
				Thread.sleep(50);
			}catch(Exception e) {/*ignore*/}
			
			playing=false;
			return true;
		}

		public boolean Pause() throws Error, Exception {
			return Stop();
		}

		public boolean amIGarbage(Sound sound) {
			if(loading) return false;
			if(!(myPlayer.getState()>=Player.STARTED)) return true;
			return false;
		}
		
		public void playerUpdate(Player player, String event, Object eventData) {
			
			if(event.equals(PlayerListener.END_OF_MEDIA)) {
				if(loop && playing) {
					try {
						myPlayer.stop(); //Make sure the audio stop
						Thread.sleep(50);
					}catch(Exception e) {/*ignore*/}
					
					if(myPlayer!=null && playing) { //Make sure it was not changed during the wait
						
						try {
							myPlayer.start();
						}catch(Exception e) {
							try {
								Stop();
							} catch (Error e1) {/*ignore*/}
							catch (Exception e1) {/*ignore*/}
							playing=false;
						}
						
					}
				}else {
					try {
						Stop();
					} catch (Error e1) {/*ignore*/}
					catch (Exception e1) {/*ignore*/}
					playing=false;
				}
			}
			
			onPlayerUpdate(player, event, eventData);
			
		}

		public boolean preload() throws Error, Exception {
			return true;
		}

		public void Loaded() throws Error, Exception {
			
			try {
				myPlayer.realize();
			}catch(Exception e) {/*ignore*/}
			
		}

		public void onCanceled(byte task) throws Error, Exception {}

		public boolean onLoadCall() throws Error, Exception {
			return true;
		}

		public boolean onStartCall(long time) throws Error, Exception {
			return true;
		}

		public boolean onStopCall() throws Error, Exception {
			return true;
		}

		public boolean onPauseCall() throws Error, Exception {
			return true;
		}
		
	}
	
	private final class QualityModeService implements SoundService{
		
		public boolean preStart(long time) throws Error, Exception{
			
			if(myPlayer.getState()>=Player.UNREALIZED && myPlayer.getState()<Player.REALIZED) {
				try {
					myPlayer.realize();
					Thread.sleep(50);
				}catch(Exception e) {
					Unrealize();
					if(!Reload()) {
						return false;
					}
				}
			}
			
			return true;
		}

		public boolean Start(long time) throws Error, Exception {
			
			if(format==FORMAT_WAV) {
				
				long t = time;
				if(t<=0) t=0;
				
				if(QualityServiceVars.music!=null && QualityServiceVars.music.getPlayer()!=null) {
					QualityServiceVars.music.catchMediaTime();
				}
				
				if(isCanceled(CANCEL_START)) {
					Unrealize();
					return false;
				}
				
				boolean timeSet=false;
				if(t>0) {
					try{
						myPlayer.setMediaTime(t);
						timeSet=true;
					}catch(Exception e) {/*ignore*/}
				}
				
				try {
					myPlayer.start();
					playing=true;
				}catch(Exception e) {
					try {
						myPlayer.start(); //Second try uwu
						playing=true;
					}catch(Exception e2) {
						try {
							myPlayer.deallocate();
						}catch(Exception e3) {}
						return false;
					}
				}catch(Error e) {
					GameEngine.DebugError("Error playing sound: "+getSource());
					GameEngine.DebugError(e.toString());
					return false;
				}
				
				if(!timeSet && t>0) {
					try{
						myPlayer.setMediaTime(t);
						timeSet=true;
					}catch(Exception e) {/*ignore*/}
				}
				
				Thread.sleep(50);
				
				if(QualityServiceVars.music!=null && QualityServiceVars.music.getPlayer()!=null && QualityServiceVars.music.isPlaying() && QualityServiceVars.music.getPlayer().getState()<Player.STARTED) {
					
					t=QualityServiceVars.music.getLastTime();
					if(t<=0) t=0;
					timeSet=false;
					if(t>0) {
						try{
							QualityServiceVars.music.getPlayer().setMediaTime(t);
							timeSet=true;
							Thread.sleep(50);
						}catch(Exception e) {/*ignore*/}
					}
					
					try {
						QualityServiceVars.music.getPlayer().start();
					}catch(Exception e) {
						try {
							QualityServiceVars.music.getPlayer().start(); //Try again uwu
						}catch(Exception e2) {
							return true;
						}
					}
					
					if(t>0 && !timeSet) {
						try{
							QualityServiceVars.music.getPlayer().setMediaTime(t);
							timeSet=true;
						}catch(Exception e) {/*ignore*/}
					}
					
				}
				
				return true;
				
			}else {
				
				if(QualityServiceVars.music!=null && QualityServiceVars.music!=getSound()) {
					QualityServiceVars.music.Unrealize();
					if(QualityServiceVars.music.getPlayer() != null && QualityServiceVars.music.getPlayer().getState()>=Player.REALIZED) {
						return false;
					}
				}
				
				QualityServiceVars.music = getSound();
				
				long t = time;
				if(t<=0) t=0;
				
				if(myPlayer.getState()==Player.REALIZED) {
					try{
						myPlayer.prefetch();
						Thread.sleep(50);
					}catch(Exception e) {/*ignore*/}
				}
				
				if(isCanceled(CANCEL_START)) {
					Unrealize();
					return false;
				}
				
				boolean timeSet=false;
				try{
					myPlayer.setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {/*ignore*/}
				
				try {
					myPlayer.start();
					playing=true;
				}catch(Exception e) {
					try {
						myPlayer.start(); //Second try uwu
						playing=true;
					}catch(Exception e2) {
						try {
							myPlayer.deallocate();
						}catch(Exception e3) {}
						return false;
					}
				}catch(Error e) {
					GameEngine.DebugError("Error playing music: "+getSource());
					GameEngine.DebugError(e.toString());
					return false;
				}
				
				if(!timeSet) {
					try{
						myPlayer.setMediaTime(t);
						timeSet=true;
					}catch(Exception e) {/*ignore*/}
				}
				
				return true;
				
			}
			
		}

		public boolean Stop() throws Error, Exception {
			if(myPlayer==null) {
				return false;
			}
			if(myPlayer.getState()==Player.STARTED) {
				try {
					myPlayer.stop();
					Thread.sleep(50);
				}catch (Exception e){
					return false;
				}
			}
			
			try {
				myPlayer.deallocate();
				Thread.sleep(50);
			}catch(Exception e) {/*ignore*/}
			
			playing=false;
			return true;
		}

		public boolean Pause() throws Error, Exception {
			return Stop();
		}
		
		public boolean amIGarbage(Sound sound) {
			
			if(loading) return false;
			
			if(!(myPlayer.getState()>=Player.STARTED)) {
				if(format==Sound.FORMAT_WAV || format==Sound.FORMAT_INCOMPATIBLE || format==Sound.FORMAT_UNSUPPORTED) return true;
				
				if(format==Sound.FORMAT_MIDI) {
					
					if(sound == null && QualityServiceVars.music!=getSound()) return true;
					if(sound == null && QualityServiceVars.music==getSound()) return false;
					if(sound == null && !playing) return true;
					
					if(sound!=null && sound.getFormat() == Sound.FORMAT_MIDI) {
						return true;
					}else {
						return false;
					}
					
				}
				
				return true;
			}
			
			return false;
			
		}

		public void playerUpdate(Player player, String event, Object eventData) {
			
			if(event.equals(PlayerListener.END_OF_MEDIA)) {
				if(loop && playing) {
					try {
						myPlayer.stop(); //Make sure the audio stop
						Thread.sleep(50);
					}catch(Exception e) {/*ignore*/}
					
					if(myPlayer!=null && playing) { //Make sure it was not changed during the wait
						
						try {
							myPlayer.start();
						}catch(Exception e) {
							try {
								Stop();
							} catch (Error e1) {/*ignore*/}
							catch (Exception e1) {/*ignore*/}
							playing=false;
						}
						
					}
				}else {
					try {
						Stop();
					} catch (Error e1) {/*ignore*/}
					catch (Exception e1) {/*ignore*/}
					playing=false;
				}
			}
			
			onPlayerUpdate(player, event, eventData);
			
		}

		public boolean preload() throws Error, Exception {
			
			return true;
			
		}

		public void Loaded() throws Error, Exception {
			
			if(QualityServiceVars.music!=null && QualityServiceVars.music.getPlayer()!=null) {
				QualityServiceVars.music.catchMediaTime();
			}
			
			try {
				myPlayer.realize();
			}catch(Exception e) {/*ignore*/}
			
			Thread.sleep(50);
			
			if(QualityServiceVars.music!=null && QualityServiceVars.music.getPlayer()!=null && QualityServiceVars.music.isPlaying() && QualityServiceVars.music.getPlayer().getState()<Player.STARTED) {
				try {
					QualityServiceVars.music.getPlayer().start();
				}catch(Exception e) {
					try {
						QualityServiceVars.music.getPlayer().start(); //Try again uwu
					}catch(Exception e2) {/*ignore*/}
				}
			}
			
		}

		public void onCanceled(byte task) throws Error, Exception {}

		public boolean onLoadCall() throws Error, Exception {
			return true;
		}

		public boolean onStartCall(long time) throws Error, Exception {
			return true;
		}

		public boolean onStopCall() throws Error, Exception {
			return true;
		}

		public boolean onPauseCall() throws Error, Exception {
			return true;
		}
		
	}
	
	private final static class QualityServiceVars{
		
		private static Sound music;
		
	}
	
	private final static class CompatibleServiceVars{
		
		private static Sound coreSound;
		private static Sound music;
		
		private static long lastInterrupt;
		
	}
	
	private final class CompatibleModeService implements SoundService{

		public boolean preload() throws Error, Exception {
			return true;
		}
		
		public boolean amIGarbage(Sound sound) {
			
			if(format==FORMAT_INCOMPATIBLE || format==FORMAT_WAV) return true;
			
			if(format==FORMAT_MIDI || format==FORMAT_UNSUPPORTED) {
				
				return true;
				
			}
			
			return false;
			
		}

		public void Loaded() throws Error, Exception {
			
			if(format==FORMAT_MIDI || format==FORMAT_UNSUPPORTED) {
				CompatibleServiceVars.music = getSound();
			}
			
		}
		
		public boolean preStart(long time) throws Error, Exception{
			
			if(CompatibleServiceVars.coreSound!=null && CompatibleServiceVars.coreSound!=getSound() && CompatibleServiceVars.coreSound.getPlayer()!=null && CompatibleServiceVars.coreSound.getPlayer().getState()>=Player.PREFETCHED && CompatibleServiceVars.coreSound!=CompatibleServiceVars.music) return false;
			
			if(CompatibleServiceVars.coreSound==CompatibleServiceVars.music && format==FORMAT_WAV) {
				
				Player p = CompatibleServiceVars.music.getPlayer();
				if(p!=null && p.getState()>=Player.PREFETCHED) {
					try {
						CompatibleServiceVars.music.catchMediaTime();
						p.stop();
					}catch(Exception e) {}
					
					CompatibleServiceVars.lastInterrupt = System.currentTimeMillis();
					
					try {
						p.deallocate();
						Thread.sleep(50);
					}catch(Exception e) {}
				}
				
			}
			
			if(myPlayer.getState()>=Player.UNREALIZED && myPlayer.getState()<Player.REALIZED) {
				try {
					myPlayer.realize();
					Thread.sleep(50);
				}catch(Exception e) {
					if(CompatibleServiceVars.music!=null) {
						try {
							myPlayer.realize();
							Thread.sleep(50);
						}catch(Exception e2) {
							Stop();
							return false;
						}
					}
				}
				
				if(myPlayer.getState()<Player.REALIZED) {
					
					if(CompatibleServiceVars.music!=null) {
						try {
							myPlayer.realize();
							Thread.sleep(50);
						}catch(Exception e2) {
							Stop();
							return false;
						}
					}
					
				}
			}
			
			return true;
			
		}
		
		private final boolean play() throws Error, Exception {
			try {
				CompatibleServiceVars.coreSound.getPlayer().start();
				return true;
			}catch(Exception e) {
				GameEngine.DebugError("Could not play: "+CompatibleServiceVars.coreSound.getSource());
				CompatibleServiceVars.coreSound.Unrealize();
				returnMusic();
				return false;
			}
		}

		public boolean Start(long time) throws Error, Exception {
			
			CompatibleServiceVars.coreSound = getSound();
			
			try {
				CompatibleServiceVars.coreSound.getPlayer().prefetch();
				Thread.sleep(5);
			}catch(Exception e) {
				
				if(CompatibleServiceVars.coreSound!=CompatibleServiceVars.music) {
					CompatibleServiceVars.music.Unrealize();
				}
				
			}
			
			try {
				duration=myPlayer.getDuration();
			}catch(Exception e) {/*ignore*/}
			
			long t = time;
			if(t<0) t=0;
			boolean timeSet=false;
			
			if(t>0) {
				try {
					CompatibleServiceVars.coreSound.getPlayer().setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {}
			}
			
			play();
			
			if(CompatibleServiceVars.coreSound.getPlayer().getState()<Player.STARTED) {
				if(!play()) {
					GameEngine.DebugError("Could not play: "+CompatibleServiceVars.coreSound.getSource());
					CompatibleServiceVars.coreSound.Unrealize();
					returnMusic();
					return false;
				}
			}
			
			playing=true;
			
			if(t>0&&!timeSet) {
				
				try {
					CompatibleServiceVars.coreSound.getPlayer().setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {}
				
			}
			
			return true;
		}

		public boolean Stop() throws Error, Exception {
			
			playing=false;
			if(CompatibleServiceVars.coreSound == getSound()) {
				
				Unrealize();
				if(CompatibleServiceVars.coreSound.getFormat()==FORMAT_WAV) {
					returnMusic();
				}
				
				return true;
				
			}
			
			return false;
			
		}
		
		private final void returnMusic() {
			
			if(CompatibleServiceVars.music!=null && CompatibleServiceVars.music.isPlaying()) {
				CompatibleServiceVars.coreSound = null;
				
				Sound m = CompatibleServiceVars.music;
				
				long t = m.getLastTime()+((System.currentTimeMillis() - CompatibleServiceVars.lastInterrupt)*1000);
				if(m.getDuration()>0) {
					t-=m.getDuration()*((t/m.getDuration()));
				}else {
					t=0;
				}
				
				try {
					CompatibleServiceVars.music.Start(t);
				} catch (Exception e) {
					GameEngine.DebugError("Critical exception resuming music:");
					GameEngine.DebugError("*"+e.toString());
				} catch (Error e) {
					GameEngine.DebugError("Critical error resuming music:");
					GameEngine.DebugError("*"+e.toString());
				}
				
			}
			
		}

		public boolean Pause() throws Error, Exception {
			return Stop();
		}

		public void playerUpdate(Player player, String event, Object eventData) {
			
			if(event.equals(PlayerListener.END_OF_MEDIA)) {
				if(loop && playing) {
					try {
						player.stop(); //Make sure the audio stop
						Thread.sleep(50);
					}catch(Exception e) {/*ignore*/}
					
					if(player!=null && playing) { //Make sure it was not changed during the wait
						
						if(CompatibleServiceVars.coreSound==getSound()) {
						
							try {
								player.start();
							}catch(Exception e) {
								try {
									Stop();
								} catch (Error e1) {/*ignore*/}
								catch (Exception e1) {/*ignore*/}
								playing=false;
							}
							
						}
						
					}
				}else {
					try {
						Stop();
					} catch (Error e1) {/*ignore*/}
					catch (Exception e1) {/*ignore*/}
					playing=false;
				}
			}
			
			/*
			if(event.equals(PlayerListener.CLOSED)){
				
				if(CompatibleServiceVars.coreSound != null && CompatibleServiceVars.music.getPlayer()==player) {
					CompatibleServiceVars.coreSound=null;
				}
				
			}*/
			
			onPlayerUpdate(player, event, eventData);
			
		}

		public void onCanceled(byte task) throws Error, Exception {
			
			
			if(myPlayer!=null && myPlayer.getState()>=Player.REALIZED)Unrealize();
			
			if(task==CANCEL_START && CompatibleServiceVars.music!=getSound() && CompatibleServiceVars.music.isPlaying() && CompatibleServiceVars.music.getPlayer()!=null && CompatibleServiceVars.music.getPlayer().getState()<Player.STARTED) {
				
				returnMusic();
				
			}
			
		}

		public boolean onLoadCall() throws Error, Exception {
			return true;
		}

		public boolean onStartCall(long time) throws Error, Exception {
			return true;
		}

		public boolean onStopCall() throws Error, Exception {
			return true;
		}

		public boolean onPauseCall() throws Error, Exception {
			return true;
		}
		
	}
	
	private static final class RippedServiceVars{
		
		private final static long lastMusicLoadTimeout=1000;
		
		private static Sound music;
		private static long lastMusicLoad;
		
	}
	
	private class RippedModeService implements SoundService{
		
		boolean play=false;
		
		public boolean preload() throws Error, Exception {
			
			if(format==FORMAT_INCOMPATIBLE) return false;
			if(format==FORMAT_WAV && (RippedServiceVars.music!=null && (RippedServiceVars.music.isPlaying() || RippedServiceVars.music.isLoading()) || System.currentTimeMillis()-RippedServiceVars.lastMusicLoad<RippedServiceVars.lastMusicLoadTimeout)) return false;
			
			InputStream test = getClass().getResourceAsStream(sounds_folder+getSource());
			try {
				test.available();
				test.close();
				test=null;
			}catch(Exception e){
				test=null;
				return false;
			}
			
			if(format==FORMAT_MIDI || format==FORMAT_UNSUPPORTED) RippedServiceVars.music = getSound();
			
			return true;
		}

		public void Loaded() throws Error, Exception {
			
			try {
				myPlayer.realize();
				Thread.sleep(50);
			}catch(Exception e) {}
			
			RippedServiceVars.lastMusicLoad = System.currentTimeMillis();
			
		}

		public boolean preStart(long time) throws Error, Exception {
			
			if(format==FORMAT_INCOMPATIBLE) return false;
			if(format==FORMAT_WAV && (RippedServiceVars.music!=null && RippedServiceVars.music.isPlaying())) return false;
			return true;
		}

		public boolean Start(long time) throws Error, Exception {
			
			long t = time;
			if(t<=0) t=0;
			try{
				myPlayer.prefetch();
				Thread.sleep(50);
			}catch(Exception e) {/*ignore*/}
			
			if(isCanceled(CANCEL_START)) {
				Unrealize();
				return false;
			}
			
			boolean timeSet=false;
			if(t>0) {
				try{
					myPlayer.setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {/*ignore*/}
			}
			
			try{
				myPlayer.start();
			}catch(Exception e) {
				return false;
			}
			
			playing=true;
			
			if(t>0 && !timeSet) {
				try{
					myPlayer.setMediaTime(t);
					timeSet=true;
				}catch(Exception e) {/*ignore*/}
			}
			
			try {
				duration=myPlayer.getDuration();
			}catch(Exception e) {}
			
			return true;
			
		}

		public boolean Stop() throws Error, Exception {
			
			playing=false;
			
			try{
				myPlayer.stop();
			}catch(Exception e) {
				return false;
			}
			
			return true;
		}

		public boolean Pause() throws Error, Exception {
			return Stop();
		}

		public void onCanceled(byte task) throws Error, Exception {}

		public boolean amIGarbage(Sound sound) {
			
			if((format==FORMAT_MIDI || format==FORMAT_UNSUPPORTED)&&loading) return false;
			
			return true;
		}

		public void playerUpdate(Player player, String event, Object eventData) {
			
			if(event.equals(PlayerListener.END_OF_MEDIA)) {
				
				if(loop && playing) {
					
					try {
						player.stop(); //Make sure the audio stop
						Thread.sleep(50);
					}catch(Exception e) {/*ignore*/}
					
					if(player!=null && playing) { //Make sure it was not changed during the wait
						
							try {
								player.start();
							}catch(Exception e) {
								try {
									Stop();
								} catch (Error e1) {/*ignore*/}
								catch (Exception e1) {/*ignore*/}
								playing=false;
							}
							
						
					}
				}else {
					try {
						Stop();
					} catch (Error e1) {/*ignore*/}
					catch (Exception e1) {/*ignore*/}
					playing=false;
				}
			}
			
			if(event.equals(PlayerListener.CLOSED)) {
				playing=false;
			}
			
			onPlayerUpdate(player, event, eventData);
			
		}

		public boolean onLoadCall() throws Error, Exception {
			if(format==FORMAT_WAV && !play) return false;
			return true;
		}

		public boolean onStartCall(long time) throws Error, Exception {
			play=true;
			return true;
		}

		public boolean onStopCall() throws Error, Exception {
			return true;
		}

		public boolean onPauseCall() throws Error, Exception {
			return true;
		}
		
	}
}