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

package josesk.undertaleme.scenes.cores;

import java.io.IOException;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.j2mearmyknife.image.sources.SmartImage;
import com.j2mearmyknife.image.transformations.ImageTransformationColorize;
import com.j2mearmyknife.interfaces.AbstractImage;
import com.j2mearmyknife.utils.ImageHelpers;

import josesk.javameengine.Control;
import josesk.javameengine.LoadResources;
import josesk.javameengine.Sound;
import josesk.javameengine.Source;
import josesk.javameengine.Sync;
import josesk.javameengine.Text;
import josesk.javameengine.sound.AutoDestructiveSound;
import josesk.javameengine.sound.NonSyncSound;
import josesk.undertaleme.Game;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;
import josesk.undertaleme.GameViewData;
import josesk.undertaleme.layout.ActionButton;
import josesk.undertaleme.layout.Effect;
import josesk.undertaleme.layout.FightBar;
import josesk.undertaleme.layout.FightBox;
import josesk.undertaleme.layout.Player;
import josesk.undertaleme.scenes.cores.fight.resources.AttackEffect;
import josesk.undertaleme.scenes.cores.fight.resources.AttackEffectDeprecated;
import josesk.undertaleme.scenes.cores.fight.resources.EmptyMonsterArrayException;
import josesk.undertaleme.scenes.cores.fight.resources.List;
import josesk.undertaleme.scenes.cores.fight.resources.Monster;
import josesk.undertaleme.scenes.cores.fight.resources.MonsterNotFoundException;
import josesk.undertaleme.scenes.cores.fight.resources.monsterresources.Act;
import josesk.undertaleme.scenes.cores.fight.resources.list.*;
import josesk.undertaleme.scenes.cores.fight.resources.list.mercyactions.Spare;

public class Fight extends GameView implements Runnable{
	
	public static final int MODE_IDLE = 0;
	public static final int MODE_FREEMOVE = 1;
	public static final int MODE_SELECTBUTTON = 2;
	public static final int MODE_DIALOG = 3;
	public static final int MODE_FIGHTSELECT = 4;
	public static final int MODE_FIGHTBAR = 5;
	public static final int MODE_MONSTERTURN = 6;
	public static final int MODE_ACTMONSTERSELECT = 7;
	public static final int MODE_ACTSELECT = 8;
	public static final int MODE_ACTDIALOG = 9;
	public static final int MODE_ITEMSELECT = 10;
	public static final int MODE_ITEMFINISH = 11;
	public static final int MODE_MERCYSELECT = 12;
	public static final int MODE_SPAREFINISH = 13;
	
	protected static final int MAX_MODE_NUMBER = 13;
	
	public static final byte FINISH_WON = 0;
	public static final byte FINISH_FLEE=1;
	public static final byte FINISH_OTHER=2;
	
	protected byte lastPlayerAction;
	
	private int mode;
	private boolean finished;
	
	protected Player player;
	protected String playerName;
	protected Sprite HP;
	public NonSyncSound pushButton;
	private Sound music;
	public NonSyncSound select;
	public Sound damage;
	
	protected ActionButton actionButtons[];
	public Font font; //TODO Criar fonte personalizada do Undertale
	private Sync coresync;
	
	private FightBar fightbar;
	protected FightBox fightbox;
	private InterfaceAnimation interfaceanimation;
	private Thread threadinterfaceanimation;
	protected Text textinformation;
	private int lastMode = -1;
	private int nextMode;
	
	private Effect effects[] = new Effect[100];
	
	protected Monster monsters[];
	protected AbstractImage mercyMonsterSprite[];
	private boolean monsterTurn;
	private Monster selectedMonster;
	private List list;
	protected final Fight fightscene = this;
	
	public Fight(GameViewData data) throws Error, Exception {
		super(data);
		
		font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
		
		finished = false;
		
		playerName = Game.getGame().getAppProperty("PlayerName");
		
		player = (Player)data.spriteData[6];
		actionButtons = new ActionButton[4] ;
		
		actionButtons[0] = new ActionButton(ActionButton.BUTTON_FIGHT);
		actionButtons[1] = new ActionButton(ActionButton.BUTTON_ACT);
		actionButtons[2] = new ActionButton(ActionButton.BUTTON_ITEM);
		actionButtons[3] = new ActionButton(ActionButton.BUTTON_MERCY);
		
		monsterTurn=false;
		
		HP = data.spriteData[7];
		fightbox = new FightBox();
		fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,false,0);
		
		pushButton = new NonSyncSound("/snd_pushButton.wav", 300);
		select = new NonSyncSound("/snd_select.wav", 300);
		
		setMode(MODE_IDLE);
		
	}
	
	//MODES
	
	private final void renderMonster(Graphics g, byte renderFrame) {
		
		if(monsters!=null && monsters.length>0) {
			for(int i=0; i<monsters.length;i++) {
				if(monsters[i].getRenderFrame() == renderFrame && !monsters[i].isMercy()) monsters[i].paint(g);
			}
		}
		
	}
	
	public final void render(Graphics g) throws Error, Exception {
		
		g.setColor(0x000000);
		g.fillRect(0, 0, GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight());
		g.drawRect(0, 0, 1, 1); //Glitch prevent
		
		renderBackground(g);
		
		
		//RENDER MERCY MONSTERS
		/*
		if(monsters!=null && monsters.length>0) {
			for(int i=0; i<monsters.length;i++) {
				if(mercyMonsters[i]) {
					monsters[i].paint(g);
					g.setColor(0x000000);
					boolean d=false;
					for(int j=0; j<monsters[i].getHeight();j++) {
						if(d=!d) {
								
							g.fillRect(monsters[i].getX(), monsters[i].getY()+j, monsters[i].getWidth(), 1); //TODO implementar câmera
								
						}
					}
				}
			}
		}
		*/
		
		if(monsters!=null && monsters.length>0) {
			for(int i=0; i<monsters.length;i++) {
				if(monsters[i].isMercy()) {
					mercyMonsterSprite[i].drawOnGraphics(g, monsters[i].getX(), monsters[i].getY(), 0);
				}
			}
		}
		
		//
		
		renderMonster(g, Monster.RENDER_FRAME_BEHINDINTERFACE);
		
		fightbox.render(g);
		
		switch(mode) {
		
			case MODE_IDLE:
				renderModeIdle(g);
			break;
			
			case MODE_FREEMOVE:
				renderModeFreeMove(g);
			break;
			
			case MODE_SELECTBUTTON:
				renderModeSelectButton(g);
			break;
			
			case MODE_DIALOG:
				renderModeDialog(g);
			break;
			
			case MODE_FIGHTSELECT:
				renderBasicInterface(g);
				if(list!=null)list.render(g);
			break;
			
			case MODE_FIGHTBAR:
				renderBasicInterface(g);
				
			break;
			
			case MODE_MONSTERTURN:
				renderBasicInterface(g);
			break;
			
			case MODE_ACTMONSTERSELECT:
				renderBasicInterface(g);
				if(list!=null)list.render(g);
			break;
			
			case MODE_ACTSELECT:
				renderBasicInterface(g);
				if(list!=null)list.render(g);
			break;
			
			case MODE_ACTDIALOG:
				renderModeDialog(g);
			break;
			
			case MODE_ITEMSELECT:
				renderBasicInterface(g);
				if(list!=null)list.render(g);
			break;
			
			case MODE_MERCYSELECT:
				renderBasicInterface(g);
				if(list!=null)list.render(g);
			break;
			
			default:
				CustomModePaint(mode, g);
			break;
		
		}
		
		player.paint(g);
		
		renderMonster(g, Monster.RENDER_FRAME_FRONT);
		
		if(fightbar!=null) fightbar.render(g);
		
		for(int i=0; i<effects.length; i++) {
			
			try {
				if(effects[i]!=null) {
					effects[i].paint(g);
				}
			}catch(NullPointerException e) {/*ignore*/}
			
		}
		
	}
	protected void CustomModePaint(int mode, Graphics g) {}
	
	public final void Update() throws Error, Exception {
		
		if(textinformation!=null)textinformation.Update();
		if(fightbox!=null) fightbox.Update();
		for(int i=0; i<effects.length; i++) {
			
			try {
				if(effects[i]!=null) {
					effects[i].Update();
				}
			}catch(NullPointerException e) {/*ignore*/}
			
		}
		if(coresync!=null) coresync.Update();
		
		switch(mode) {
		
			case MODE_IDLE:
			break;
			
			case MODE_FREEMOVE:
				checkActionButtonSelections();
			break;
			
			case MODE_SELECTBUTTON:
			break;
			
			case MODE_DIALOG:
				if(textinformation!=null && textinformation.isFinished()) {
					setMode(nextMode);
				}
			break;
			
			case MODE_FIGHTSELECT:
			break;
			
			case MODE_FIGHTBAR:
				//if(fightbar!=null) fightbar.Update(); TODO ADICIONAR UPDATE NA BARRA DE MEDIDOR DE FORÇA, CASO NECESSÁRIO
			break;
			
			case MODE_MONSTERTURN:
				checkActionButtonSelections();
			break;
			
			default:
				CustomModeUpdate(mode);
			break;
	
		}
		
		player.Update();
		
	}
	protected void CustomModeUpdate(int mode) {}
	
	
	private final void modeDestroy(int mode) throws Error, Exception {
		
		switch(mode) {
		
			case MODE_IDLE:
				break;
			case MODE_FREEMOVE:
				break;
			case MODE_SELECTBUTTON:
				break;
			case MODE_DIALOG:
				break;
			case MODE_FIGHTSELECT:
				list = null;
			break;
			
			case MODE_FIGHTBAR:
				
			break;
			
			case MODE_MONSTERTURN:
				
			break;
			
			case MODE_ACTMONSTERSELECT:
				
			break;
			
			case MODE_ACTDIALOG:
				player.setVisible(true);
			break;
		
			default:
				CustomModeDestroy(mode);
			break;
		
		}
		
	}
	protected void CustomModeDestroy(int mode) {}
	
	/**
	 * Set music from Sound class
	 * @param music
	 * @throws MusicLoadingError
	 */
	public final void setMusic(Sound music) throws MusicLoadingError{
		
		if(music==null) {
			GameEngine.DebugError("There was an error setting this music");
			throw new MusicLoadingError();
		}
		
		this.music = music;
		music.setLoop(true);
		
	}
	
	
	/**
	 * Set music from source
	 * @param source
	 * @throws MusicLoadingError
	 */
	public final void setMusic(String source)  throws MusicLoadingError{
		
		setMusic(LoadResources.loadSound(source, true));
		
	}
	
	/**
	 * Stop and close music
	 * @throws Exception
	 * @throws Error
	 */
	public final void stopMusic() throws Exception, Error {
		
		if(music==null) {
			GameEngine.DebugWarn("Music is NULL, set it before stoping it");
			return;
		}
		
		music.Stop();
		music.Close();
		
	}
	
	/**
	 * Pause music
	 * @throws Exception
	 * @throws Error
	 */
	public final void pauseMusic() throws Exception, Error {
		
		if(music==null) {
			GameEngine.DebugWarn("Music is NULL, set it before pausing it");
			return;
		}
		
		music.Pause();
		
	}
	
	/**
	 * Resume music
	 * @throws Exception
	 * @throws Error
	 */
	public final void resumeMusic() throws Exception, Error{
		if(music==null) {
			GameEngine.DebugWarn("Music is NULL, set it before resuming it");
			return;
		}
		
		music.Resume();
	}
	
	/**
	 * Play music
	 * @throws Exception
	 * @throws Error
	 */
	public final void playMusic() throws Exception, Error {
		
		if(music==null) {
			GameEngine.DebugWarn("Music is NULL, set it before playing it");
			return;
		}
		
		if(!music.Start())
			if(!music.Start())//Try again UwU
				if(!music.Start()) //And try again ÒwÓ
					GameEngine.DebugError("Couldn't play fight music"); //Inform error :c
			
		
	}
	
	/**
	 * Returns music's sound
	 * @return
	 */
	public final Sound getMusic() {
		
		if(music==null) {
			GameEngine.DebugWarn("Music is NULL");
		}
		
		return music;
	}
	
	/**
	 * Add and begin effect
	 * @param effect
	 * @throws Error
	 * @throws Exception
	 */
	public final void addEffect(Effect effect) throws Error, Exception{
		
		for(int i=0; i<effects.length; i++) {
			if(effects[i]==null) {
				
				if(!effect.isLoaded()) effect.load();
				effect.assignFightScene(this);
				effect.begin();
				new Thread(effect).start();
				
				effects[i] = effect;
				return;
			}
		}
		
		throw new FullEffectsArrayException();
		
	}
	
	/**
	 * Remove effect
	 * @param effect
	 */
	public final void removeEffect(Effect effect) {
		
		for(int i=0; i<effects.length; i++) {
			if(effects[i]==effect) {
				effects[i] = null;
				return;
			}
		}
		
	}
	
	/**
	 * retuns last mode
	 * @return
	 */
	public final int getLastMode() {
		return lastMode;
	}
	
	/**
	 * Sets Fight scene mode
	 * @param mode
	 * @throws Error
	 * @throws Exception
	 */
	public final void setMode(int mode) throws Error, Exception {
		
		lastMode = this.mode;
		
		modeDestroy(mode);
		
		System.gc();
		
		this.mode = mode;
		//if(threadinterfaceanimation!=null) threadinterfaceanimation.interrupt();
		//if(fightbox!=null) fightbox.interruptAnimation();
		
		//Setup mode
		switch(mode) {
		
			case MODE_IDLE:
				
			break;
			case MODE_FREEMOVE:
				ActionButton.showIcon();
				fightbox.setVisible(false);
				player.setState(Player.STATE_FREE);
			break;
			case MODE_SELECTBUTTON:
				ActionButton.hideIcon();
				
				player.setState(Player.STATE_FREEZE);
				
				if(pushButton!=null) pushButton.reload();
				
				byte selected = -1;
				for(int i=0; i<actionButtons.length; i++) {
					if(actionButtons[i].isActive()) {
						selected = (byte)i;
						break;
					}
				}
				
				if(selected!=-1) {
					player.setRelativePosition(actionButtons[selected].getRelativeX()+3, actionButtons[selected].getRelativeY()+6);
				}
				
				beginInterfaceAnimation();
				
				checkActionButtonSelections();
				
			break;
			
			case MODE_DIALOG:
					
				player.setVisible(false);
				beginInterfaceAnimation();
				
			break;
			
			case MODE_FIGHTSELECT:
				list = new MonsterList(getActiveMonsters(), this, MonsterList.MODE_FIGHT);
				beginInterfaceAnimation();
			break;
			
			case MODE_FIGHTBAR:
				coresync = new Sync();
				player.setVisible(false);
				beginInterfaceAnimation();
			break;
			
			case MODE_MONSTERTURN:
				ActionButton.showIcon();
				if(!monsterTurn) {
					MonsterTurn(false);
				}
			break;
			
			case MODE_ACTMONSTERSELECT:
				list = new MonsterList(getActiveMonsters(), this, MonsterList.MODE_ACT);
				beginInterfaceAnimation();
			break;
			
			case MODE_ACTSELECT:
				list = new ActList(selectedMonster.getActList(), this, selectedMonster);
			break;
			
			case MODE_ACTDIALOG:
				player.setVisible(false);
				player.setState(Player.STATE_FREEZE);
				beginInterfaceAnimation();
			break;
			
			case MODE_ITEMSELECT:
				list = new ItemList(player.getBackpack().getItems(), this);
				beginInterfaceAnimation();
			break;
			
			case MODE_ITEMFINISH:
				MonsterTurn(true);
			break;
			
			case MODE_MERCYSELECT:
				
				beginInterfaceAnimation();
			break;
			
			case MODE_SPAREFINISH:
				finish(FINISH_OTHER);
			break;
		
			default:
				CustomModeSetup(mode);
			break;
		
		}
		
	}
	protected void CustomModeSetup(int mode) {};
	
	public final boolean onKeyDown(String command) throws Error, Exception {
		
		switch(mode) {
		
			case MODE_IDLE:
			
			break;
			
			case MODE_FREEMOVE:

				if(command.equals("Ok")) {
					
					pressSelectedActionButton();
					
				}
				
			break;
			
			case MODE_SELECTBUTTON:
				
				byte selected = -1;
				for(int i=0; i<actionButtons.length; i++) {
					
					if(actionButtons[i].isSelected()) {
						selected = (byte)i;
						break;
					}
					
				}
				
				if(command.equals("Right")) {
					if(pushButton!=null) pushButton.Play();
					if(selected<3 && actionButtons[selected+1].isActive()) {
						player.setRelativePosition(actionButtons[selected+1].getRelativeX()+3, actionButtons[selected+1].getRelativeY()+6);
					}
					
				}
				
				if(command.equals("Left")) {
					if(pushButton!=null) pushButton.Play();
					if(selected>0 && actionButtons[selected-1].isActive()) {
						player.setRelativePosition(actionButtons[selected-1].getRelativeX()+3, actionButtons[selected-1].getRelativeY()+6);
					}
					
				}
				
				checkActionButtonSelections();
				
				if(command.equals("Ok")) {
					
					if(select!=null) select.Play();
					
					pressSelectedActionButton();
					
				}
				
			break;
			
			case MODE_DIALOG:
				if(textinformation!=null) {
					textinformation.nextPhrase();
				}
			break;
			
			case MODE_FIGHTSELECT:
				if(command.equals("Back") && getLastMode()!=-1) {
					if(textinformation!=null) textinformation.reset();
					setMode(getLastMode());
				}
				
				if(list!=null)list.onKeyDown(command);
				
			break;
			
			case MODE_FIGHTBAR:
				if(command.equals("Ok") && fightbar!=null) {
					fightbar.hit();
				}
			break;
			
			case MODE_MONSTERTURN:
				if(command.equals("Ok")) {
					
					checkActionButtonSelections();
					pressSelectedActionButton();
					
				}
			break;
			
			case MODE_ACTMONSTERSELECT:
				if(command.equals("Back") && getLastMode()!=-1) {
					if(textinformation!=null) textinformation.reset();
					setMode(getLastMode());
				}
				
				if(list!=null)list.onKeyDown(command);
				
			break;
			
			case MODE_ACTSELECT:
				if(command.equals("Back") && getLastMode()!=-1) {
					if(textinformation!=null) textinformation.reset();
					setMode(getLastMode());
				}
				
				if(list!=null)list.onKeyDown(command);
				
			break;
			
			case MODE_ACTDIALOG:
				if(command.equals("Ok") || command.equals("Back")) {
					textinformation.nextPhrase();
					if(textinformation.isFinished()) {
						MonsterTurn(true);
					}else {
						textinformation.start();
					}
				}
			break;
			
			case MODE_ITEMSELECT:
				if(command.equals("Back") && getLastMode()!=-1) {
					if(textinformation!=null) textinformation.reset();
					setMode(getLastMode());
				}
				
				if(list!=null)list.onKeyDown(command);
			break;
			
			case MODE_MERCYSELECT:
				if(command.equals("Back") && getLastMode()!=-1) {
					if(textinformation!=null) textinformation.reset();
					setMode(getLastMode());
				}
				
				if(list!=null)list.onKeyDown(command);
			break;
		
			default:
				CustomModeOnKeyDown(mode, command);
			break;
	
		}
		
		return onKeyDownAnyMode(command);
		
	}
	protected boolean CustomModeOnKeyDown(int mode, String command) {
		
		return true;
		
	}
	protected boolean onKeyDownAnyMode(String command) {
		return true;
	}

	public final boolean onKeyUp(String command) {
		
		switch(mode) {
		
			case MODE_IDLE:
			
			break;
			
			case MODE_FREEMOVE:
				
			break;
			
			case MODE_SELECTBUTTON:
				
			break;
			
			case MODE_FIGHTSELECT:
				
			break;
			
			case MODE_FIGHTBAR:
				
			break;
			
			case MODE_MONSTERTURN:
				
			break;
			
			case MODE_ACTMONSTERSELECT:
				
			break;
		
			default:
				CustomModeOnKeyUp(mode, command);
			break;
		
		}
		
		return onKeyUpAnyMode(command);
		
	}
	protected boolean CustomModeOnKeyUp(int mode, String command) {
		
		return true;
		
	}
	protected boolean onKeyUpAnyMode(String command) {
		return true;
	}

	public final void onDisplaySizeChange(int lastWidth, int lastHeight, int wDif, int hDif) throws Exception, Error{
		
		switch(mode) {
		
			case MODE_IDLE:
		
				updateActionButtonPositions(lastWidth, lastHeight, wDif, hDif);
				updateMonstersPositions(lastWidth, lastHeight, wDif, hDif);
			
			break;
			
			case MODE_FREEMOVE:
				
				updateActionButtonPositions(lastWidth, lastHeight, wDif, hDif);
				updateMonstersPositions(lastWidth, lastHeight, wDif, hDif);
				player.updateRelativePlayerPosition(lastWidth, lastHeight, wDif, hDif);
				//updatePlayerPositionInsideBox(lastWidth, lastHeight, wDif, hDif);
				
			break;
			
			case MODE_SELECTBUTTON:
				
				updateActionButtonPositions(lastWidth, lastHeight, wDif, hDif);
				updateMonstersPositions(lastWidth, lastHeight, wDif, hDif);
				
				byte selected = -1;
				for(int i=0; i<actionButtons.length; i++) {
					
					if(actionButtons[i].isSelected()) {
						selected = (byte)i;
						break;
					}
					
				}
				
				if(selected>=0) player.setRelativePosition(actionButtons[selected].getRelativeX()+3, actionButtons[selected].getRelativeY()+6);
				fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,400);
				
				
			break;
			
			case MODE_DIALOG:
				
				updateActionButtonPositions(lastWidth, lastHeight, wDif, hDif);
				updateMonstersPositions(lastWidth, lastHeight, wDif, hDif);
				fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,400);
				
			break;
			
			case MODE_FIGHTSELECT:
				
				updateActionButtonPositions(lastWidth, lastHeight, wDif, hDif);
				updateMonstersPositions(lastWidth, lastHeight, wDif, hDif);
				fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,400);
				list.onDisplaySizeChange(lastWidth, lastHeight, wDif, hDif);
				
			break;
			
			case MODE_FIGHTBAR:
				updateActionButtonPositions(lastWidth, lastHeight, wDif, hDif);
				updateMonstersPositions(lastWidth, lastHeight, wDif, hDif);
				fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,400);
			break;
			
			case MODE_MONSTERTURN:
				
			break;
			
			default:
				CustomModeDisplayResolutionChange(lastWidth, lastHeight, wDif, hDif);
			break;
		
		}
		
	}
	protected void CustomModeDisplayResolutionChange(int lastWidth, int lastHeight, int wDif, int hDif) throws Exception, Error {}
	//
	
	public final void onGameViewBegin() throws Error, Exception {
		
		player.setFightScene(this);
		
		damage = new Sound("/snd_damage.wav");
		damage.Load();
		
		ActionButton.autoCompact();
		for(int i=0; i<actionButtons.length; i++) {
			actionButtons[i].UpdatePos();
		}
		
		onBegin();
		
		if(monsters!=null) {
			mercyMonsterSprite = new AbstractImage[monsters.length];
		}
		//mercyMonsters[0]=true; TODO TESTAR ISSO DENOVO
		
		if(monsters!=null && monsters.length>0) {
			for(int i=0; i<monsters.length;i++) {
				monsters[i].onFightGameViewBegin();
			}
		}
		
		beginThread(new Thread(this));
		
	}
	
	public final void onGameViewDestroy() throws Error, Exception{
		
		if(select!=null) select.Play();
		
		destroy();
		
	}
	
	protected void renderBackground(Graphics g) {}
	
	protected final void checkActionButtonSelections() {
		
		for(int i=0; i<actionButtons.length;i++) {
			if(actionButtons[i].isActive() && actionButtons[i].insideMe(player.getRelativeX(), player.getRelativeY(), 8, 8)) {
				actionButtons[i].select();
			}else {
				actionButtons[i].unselect();
			}
		}
		
	}
	
	protected final void updateActionButtonPositions(int lastWidth, int lastHeight, int wDif, int hDif) {
		
		for(int i=0; i<actionButtons.length; i++) {
			
			actionButtons[i].onDisplaySizeChange(lastWidth, lastHeight, wDif, hDif);
			
		}
		
	}
	
	protected final void updatePlayerPositionInsideBox(int lastWidth, int lastHeight, int wDif, int hDif) {
		
		final int[] center = fightbox.getCenter(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight());
		
		final int[] lc = fightbox.getCenter(lastWidth, lastHeight);
		
		final int px = player.getRelativeX()-lc[0];
		final int py = player.getRelativeY()-lc[1];
		
		int mx=0, my=0;
		
		final boolean updateMoving = player.isMoving();
		boolean sync = false;
		
		if(player.isMoving()) {
			
			sync = player.isMoveSynchronized();
			
			player.stopMoving(false);
			mx = player.getMoveTargetX()-lc[0];
			my = player.getMoveTargetY()-lc[1];
			
		}
		
		player.setRelativePosition(center[0]+px, center[1]+py);
		
		if(updateMoving) {
			
			player.moveTo(center[0]+mx, center[1]+my, player.getMoveTimeLeft(),sync);
			
		}
		
	}
	
	protected final void updateMonstersPositions(int lastWidth, int lastHeight, int wDif, int hDif) {
		
		if(monsters!=null) {
			
			for(int i=0; i<monsters.length; i++) {
				
				if(monsters[i]!=null) monsters[i].onDisplaySizeChange(lastWidth, lastHeight, wDif, hDif);
				
			}
			
		}
		
	}
	
	/**
	 * Press the selected action button (Fight, Act, Item, Mercy)
	 * @throws Error
	 * @throws Exception
	 */
	public final void pressSelectedActionButton() throws Error, Exception {
		
		for(int i=0; i<actionButtons.length;i++) {
			if(actionButtons[i].isActive() && actionButtons[i].isSelected()) {
				if(select!=null) select.Play();
				
				lastPlayerAction=actionButtons[i].getButtonType();
				onActionButtonPress(actionButtons[i].getButtonType());
			}
		}
		
	}
	
	protected void onActionButtonPress(byte buttonType) throws Error, Exception {
		
		switch(buttonType) {
		
			case ActionButton.BUTTON_FIGHT:
				ButtonFightPressed();
			break;
			
			case ActionButton.BUTTON_ACT:
				ButtonActPressed();
			break;
			
			case ActionButton.BUTTON_ITEM:
				ButtonItemPressed();
			break;
			
			case ActionButton.BUTTON_MERCY:
				ButtonMercyPressed();
			break;
			
			default:
				onOtherActionButtonPress(buttonType);
			break;
	
		}
	}
	
	protected final void fight() throws Error, Exception {
		setMode(MODE_FIGHTSELECT);
	}
	protected final void act() throws Error, Exception{
		setMode(MODE_ACTMONSTERSELECT);
	}
	protected final void item() throws Error, Exception{
		if(player.getBackpack()!=null && player.getBackpack().getItems()!= null && player.getBackpack().getItems().length>0) setMode(MODE_ITEMSELECT);
	}
	protected final void mercy() throws Error, Exception{
		
		list = new MercyList(new MercyList.MercyAction[] {new Spare(this)}, this);
		
		setMode(MODE_MERCYSELECT);
	}
	
	protected void ButtonFightPressed() throws Error, Exception {fight();}
	protected void ButtonActPressed() throws Error, Exception {act();}
	protected void ButtonItemPressed() throws Error, Exception {item();}
	protected void ButtonMercyPressed() throws Error, Exception {mercy();}
	
	protected final void onOtherActionButtonPress(byte buttonType) {}
	
	public void Run()  throws Error, Exception{
		
		if(monsters==null || monsters.length<=0) {
			
			GameEngine.DebugWarn("Run() function and monsters not assigned on the Fight scene, please assign them");
		
			setTextInformation(new Text(new Source("/FightDefaultText/ButNobodyCame.txt")));
			
			setMode(MODE_DIALOG);
			
			waitForKeyPress(new String[] {"Ok", "Back"});
			finish(Fight.FINISH_OTHER);
		}
		
	}
	
	protected final void setTextInformation(Text textinformation) {
		this.textinformation = textinformation;
		textinformation.reset();
	}
	
	
	/**
	 * Show information
	 * 
	 * @param textinformation
	 * @param nextMode
	 * @throws Error
	 * @throws Exception
	 */
	public final void showMessageInformation(Text textinformation, int nextMode) throws Error, Exception {
		setTextInformation(textinformation);
		this.nextMode = nextMode;
		setMode(MODE_DIALOG);
	}
	
	/**
	 * Finish fighting
	 * @param finishreason
	 * @throws Error
	 * @throws Exception
	 */
	protected final void finish(byte finishreason) throws Error, Exception {
		
		finished = true;
		onFinished(finishreason);
		
	}
	
	/**
	 * Check if fighting is finished
	 * @return
	 */
	public final boolean isFinished() {
		return finished;
	}
	
	protected final void destroy() throws Error, Exception {
		
		if(threadinterfaceanimation!=null) threadinterfaceanimation.interrupt();
		
		onDestroy();
	}
	
	protected void onBegin() throws Error, Exception{};
	
	protected void onFinished(byte finishreason) throws Error, Exception{
		destroy();
	}
	protected void onDestroy() throws Error, Exception{
		Game.getGame().closeGame();
	}
	
	/**
	 * When monster receives damage
	 * @param monster
	 * @param damage
	 * @throws Error
	 * @throws Exception
	 */
	public void onMonsterDamage(Monster monster, int damage) throws Error, Exception{}
	
	/**
	 * When monster is killed
	 * @param monster
	 * @throws Error
	 * @throws Exception
	 */
	public void onMonsterKill(Monster monster) throws Error, Exception{}
	
	/**
	 * Returns monster's designed ID
	 * @param monster
	 * @return monter's ID
	 * @throws EmptyMonsterArrayException
	 * @throws MonsterNotFoundException
	 */
	public final int getMonsterID(Monster monster) throws EmptyMonsterArrayException, MonsterNotFoundException{
		
		if(monsters==null || monsters.length<=0) throw new EmptyMonsterArrayException();
		
		for(int i=0; i<monsters.length; i++) {
			
			if(monsters[i]==monster) return i;
			
		}
		
		throw new MonsterNotFoundException();
		
	}
	
	/**
	 * Returns how much monsters are in this fight
	 * @return #monsters
	 */
	public final int getMonstersQuantity() {
		return monsters.length;
	}
	
	/**
	 * Returns monster from their ID
	 * @param ID
	 * @return monster
	 * @throws EmptyMonsterArrayException
	 * @throws ArrayIndexOutOfBoundsException
	 */
	public final Monster getMonsterFromID(int ID) throws EmptyMonsterArrayException, ArrayIndexOutOfBoundsException{
		
		if(monsters==null || monsters.length<=0) throw new EmptyMonsterArrayException();
		
		return monsters[ID];
		
	}
	
	/**
	 * Desire to attack  monster
	 * @param monster
	 * @throws Error
	 * @throws Exception
	 */
	public final void preAttackMonster(Monster monster) throws Error, Exception {
		
		selectedMonster = monster;
		setMode(MODE_FIGHTBAR);
		
	}
	
	/**
	 * Desire to act monster
	 * @param monster
	 * @throws Error
	 * @throws Exception
	 */
	public final void preActMonster(Monster monster) throws Error, Exception{
		
		selectedMonster = monster;
		setMode(MODE_ACTSELECT);
		
	}
	
	/**
	 * Act
	 * @param monster
	 * @param act
	 * @throws Error
	 * @throws Exception
	 */
	public final void act(Monster monster, Act act) throws Error, Exception {
		
		act.select(this);
		
	}
	
	/**
	 * Show act message
	 * @param message
	 * @throws Error
	 * @throws Exception
	 */
	public final void actMessage(Text message) throws Error, Exception {
		
		list = null;
		setTextInformation(message);
		setMode(Fight.MODE_ACTDIALOG);
		
	}
	
	/**
	 * Attack monster
	 * @param monster
	 * @param strength
	 * @return
	 * @throws Error
	 * @throws Exception
	 */
	public final boolean attackMonster(Monster monster, double strength) throws Error, Exception {
		
		if(strength>0.0d) {
			if(player.CheckAttackPossibility(monster, strength)) {
				
				AttackEffect attackEffect = player.getWeapon().getAttackEffect();
				attackEffect.assignMonster(monster);
				addEffect(attackEffect);
				attackEffect.waitForAnimation();
				if(monster.Damage((int)((25*strength)*player.getLOVE()))) {
					 //TODO: Colocar som na classe do monstro, ao invés daqui
					damage.Start();
				}
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	protected final void waitForKeyRelease(String command[]) {
		
		waitloop:
		do{
			
			for(int i=0; i<command.length; i++) {
				
				if(Control.isKeyUp(command[i])) break waitloop;
				
			}
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {}
			
		}while(true);
		
	}
	
	protected final void waitForKeyPress(String command[]) {
		
		waitloop:
		do{
			
			for(int i=0; i<command.length; i++) {
				
				if(Control.isKeyDown(command[i])) break waitloop;
				
			}
			
			try {
				Thread.sleep(30);
			} catch (InterruptedException e) {}
			
		}while(true);
		
	}
	
	/**
	 * Returns mode
	 * @return mode
	 */
	public final int getMode() {
		return mode;
	}
	
	/**
	 * Returns player
	 * @return player
	 */
	public final Player getPlayer() {
		return player;
	}
	
	/**
	 * Returns fightbox
	 * @return fightbox
	 */
	public final FightBox getFightBox() {
		return fightbox;
	}
	
	/**
	 * returns active monsters array
	 * @return monsters
	 */
	public final Monster[] getActiveMonsters() {
		
		byte mercies = 0;
		for(int i=0; i<monsters.length; i++) {
			if (monsters[i].isMercy()) mercies++;
		}
		
		Monster monstersToMercy[] = new Monster[monsters.length - mercies];
		
		byte j=0;
		for(int i=0; i<monsters.length; i++) {
			if (!monsters[i].isMercy()) {
				monstersToMercy[j] = monsters[i];
				j++;
			}
		}
		
		return monstersToMercy;
		
	}
	
	/**
	 * Teel fight scene it's monter's turn
	 * @param setToMonsterTurnMode
	 * @throws Error
	 * @throws Exception
	 */
	public final void MonsterTurn(boolean setToMonsterTurnMode) throws Error, Exception {
		
		monsterTurn=true;
		
		if(setToMonsterTurnMode) {
			setMode(MODE_MONSTERTURN);
		}
		
		for(int i=0; i<monsters.length; i++) {
			
			if(!monsters[i].isMercy())monsters[i].onMonsterTurn();
			
		}
		
		onMonsterTurnBegin();
		
	}
	
	protected void onMonsterTurnBegin() {
		
		fightbox.transform(0, 0, 100, 100, true, FightBox.DEFAULT_DELAY);
		player.setState(Player.STATE_FREE);
		player.setVisible(true);
		
	}
	
	protected void MonsterTurnUpdate() {}
	
	public final void onspare(Monster monster) throws Exception, Error {
		
		ImageTransformationColorize it = new ImageTransformationColorize();
		it.setAlphaOffset(-100);
		
		Image image = Image.createImage(monster.getWidth(), monster.getHeight());
		Graphics g = image.getGraphics();
		g.setColor(0xff00ff);
		g.fillRect(0, 0, monster.getWidth(), monster.getHeight()); //Chroma-keying
		monster.paintMercy(g);
		
		image = ImageHelpers.makeTransparent(image, 0xff00ff);
		
		mercyMonsterSprite[getMonsterID(monster)] = it.process(new SmartImage(image));
		
		image=null;
		it = null;
		
		onSpare(monster);
		
	}
	
	protected void onSpare(Monster monster) throws Exception, Error {}
	
	protected final void beginInterfaceAnimation() {
		
		if(threadinterfaceanimation!=null) threadinterfaceanimation.interrupt();
		
		interfaceanimation = new InterfaceAnimation(mode);
		threadinterfaceanimation = new Thread(interfaceanimation);
		threadinterfaceanimation.start();
		
	}
	
	public final boolean onError(String error) throws Error, Exception {
		if(threadinterfaceanimation!=null) threadinterfaceanimation.interrupt();
		threadinterfaceanimation = null;
		interfaceanimation = null;
		
		if(pushButton!=null) {
			pushButton.Stop();
			pushButton = null;
		}
		if(select!=null) {
			select=null;
		}
		
		return onFightError(error);
		
	}
	
	protected boolean onFightError(String error) throws Error, Exception {
		return true;
	}
	
	protected final void renderBasicInterface(Graphics g) {
		
		renderActionButtons(g);
		
		int ay = GameEngine.getCanvas().getResolutionHeight()-31;
		int ax = (int)(GameEngine.getCanvas().getResolutionWidth()/2.831f);
		
		g.setFont(font);
		g.setColor(0xffffff);
		g.drawString(playerName+"    lv "+player.getLOVE(), (int)(GameEngine.getCanvas().getResolutionWidth()/21.333f), ay, Graphics.BOTTOM|Graphics.LEFT);
		
		if(HP!=null) {
			HP.setPosition(ax, ay-7);
			HP.paint(g);
		}
		
		g.setColor(0xca020c);
		int maxbarsize = (int)((61*(player.getMaxHP()/99.f))*(GameEngine.getCanvas().getResolutionWidth()/320.f));
		g.fillRect(ax+13, ay-10, (int)(maxbarsize), 10);
		g.setColor(0xfafe3b);
		int barsize = (int)((61*(player.getHP()/99.f))*(GameEngine.getCanvas().getResolutionWidth()/320.f));
		g.fillRect(ax+13, ay-10, (int)(barsize), 10);
		
		g.setColor(0xffffff);
		g.drawString("kr "+player.getHP()+"/"+player.getMaxHP(), ax+13+maxbarsize+4, ay, Graphics.BOTTOM|Graphics.LEFT);
		
		
		
	}
	
	protected final void renderActionButtons(Graphics g) {
		
		if(actionButtons!=null) {
			
			for(int i=0; i<4; i++) {
				if(actionButtons[i]!=null) actionButtons[i].paint(g);
			}
			
		}
		
	}
	
	private final void renderModeIdle(Graphics g) {
		
		renderBasicInterface(g);
	}
	
	private final void renderModeFreeMove(Graphics g) {
		
		renderBasicInterface(g);
		
	}
	
	private final void renderModeSelectButton(Graphics g) {
		
		renderBasicInterface(g);
		renderTextInformation(g);
		
	}
	
	private final void renderModeDialog(Graphics g) {
		
		renderBasicInterface(g);
		
		renderTextInformation(g);
		
	}
	
	protected final void renderTextInformation(Graphics g) {
		
		if(textinformation!=null) {
			int bs = Math.min(GameEngine.getCanvas().getResolutionWidth(), GameEngine.getCanvas().getResolutionHeight())/120;
			int y = (GameEngine.getCanvas().getResolutionHeight()-45-bs)-65;
			if(bs<=0)bs=1;
			
			Text.DrawText(g, 0xffffff, font, textinformation, GameEngine.getCanvas().getResolutionWidth()-(int)(GameEngine.getCanvas().getResolutionWidth()/12.8f)-bs, (int)(GameEngine.getCanvas().getResolutionWidth()/12.8f)+bs, y+11, true);
		}
		
	}
	
	protected void CustomInterfaceAnimation(int mode) {
		
		
		
	}
	
	private class InterfaceAnimation implements Runnable{
		
		private int mode;
		
		public InterfaceAnimation(int mode) {
			
			this.mode = mode;
			
		}
		
		public void run() {

			
			try {
				switch(mode) {
				
					case MODE_IDLE:
						
					break;
					
					case MODE_FREEMOVE:
						
					break;
						
					case MODE_SELECTBUTTON:
						if(textinformation!=null) textinformation.start();
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_DIALOG:
						if(textinformation!=null) textinformation.start();
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_FIGHTSELECT:
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_FIGHTBAR:
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
						fightbox.waitForAnimation();
						fightbar = new FightBar();
						fightbar.waitForAnimation();
						attackMonster(selectedMonster, fightbar.beginStrengthChallenge(fightscene, selectedMonster, player.getWeapon()));
						coresync.syncSleep(700);
						fightbar.fadeOut();
						MonsterTurn(true);
						fightbar.waitForAnimation();
						fightbar=null;
						
					break;
					
					case MODE_MONSTERTURN:
						
					break;
					
					case MODE_ACTMONSTERSELECT:
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_ACTSELECT:
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_ACTDIALOG:
						if(textinformation!=null) textinformation.start();
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_ITEMSELECT:
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
					
					case MODE_MERCYSELECT:
						fightbox.transform(0, 0, GameEngine.getCanvas().getResolutionWidth()-(int)((GameEngine.getCanvas().getResolutionWidth()/17.777f)*2), 65,true,FightBox.DEFAULT_DELAY);
					break;
				
					default:
						CustomInterfaceAnimation(mode);
					break;
				
				}
			
			}catch(Exception e) {
				GameEngine.getCanvas().forceError(e);
			}catch(Error e) {
				GameEngine.getCanvas().forceError(e);
			}
			
		}
		
	}
	
	class MusicLoadingError extends Error{
		
		public MusicLoadingError() {}
		
	}
	
	class FullEffectsArrayException extends Exception{
		
		public FullEffectsArrayException() {}
		
	}

}
