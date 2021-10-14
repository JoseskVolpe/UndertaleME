package josesk.javameengine.hecl.commands;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import org.hecl.HeclException;
import org.hecl.IntThing;
import org.hecl.Interp;
import org.hecl.ListThing;
import org.hecl.NumberThing;
import org.hecl.ObjectThing;
import org.hecl.Operator;
import org.hecl.Properties;
import org.hecl.RealThing;
import org.hecl.Thing;

import josesk.javameengine.Sprite;

public class SpriteCmd extends Operator{
	
	public static final byte SPRITE_CREATE=0;
	public static final byte SPRITE_SEQUENCE=1;
	public static final byte SPRITE_SEQUENCE_LENGTH=2;
	public static final byte SPRITE_FRAME=3;
	public static final byte SPRITE_ROT=4;
	public static final byte SPRITE_WIDTH=5;
	public static final byte SPRITE_HEIGHT=6;
	public static final byte SPRITE_SIZE=7;
	public static final byte SPRITE_X=8;
	public static final byte SPRITE_Y=9;
	public static final byte SPRITE_POS=10;
	
	
	protected SpriteCmd(int cmdcode, int minargs, int maxargs) {
		super(cmdcode, minargs, maxargs);
	}

	public Thing operate(int cmdcode, Interp interp, Thing[] argv) throws HeclException {
		
		Properties props;
		Sprite sp=null;
		Vector v;
		int seq[];
		
		switch(cmdcode) {
			case SPRITE_CREATE:
				switch(argv.length) {
					case 2:
						RealThing t = argv[1].getVal();
						if(!(t instanceof ObjectThing) || !( ((ObjectThing)t).get() instanceof Image || ((ObjectThing)t).get() instanceof Sprite)) throw new HeclException("Argument must be a image or sprite");
						Object obj = ((ObjectThing)t).get();
						
						try {
							if(obj instanceof Image) {
								sp = new Sprite((Image)obj);
							}else if(obj instanceof Sprite) {
								sp = new Sprite((Sprite)obj);
							}
						}catch(Throwable e) {
							throw new HeclException(e.toString());
						}
						
					return ObjectThing.create(sp);
					case 4:
						if(!(argv[1].getVal() instanceof ObjectThing) || !(((ObjectThing)argv[1].getVal()).get() instanceof Image)) throw new HeclException("Argument 1 must be a image");
						if(!(argv[2].getVal() instanceof IntThing)) throw new HeclException("Argument 2 must be a int");
						if(!(argv[3].getVal() instanceof IntThing)) throw new HeclException("Argument 3 must be a int");
						
						try {
							sp = new Sprite((Image)((ObjectThing)argv[1].getVal()).get(), ((IntThing)argv[2].getVal()).intValue(), ((IntThing)argv[3].getVal()).intValue());
						}catch(Throwable e) {
							throw new HeclException(e.toString());
						}
						
					return ObjectThing.create(sp);
					default:
						throw new HeclException(""
								+ "Must have 1 or 3 arguments!\n"
								+ "Usages:\n"
								+ "set spritevar [sprite.create sprite]\n"
								+ "set spritevar [sprite.create image]\n"
								+ "set spritevar [sprite.create image int int]");
				}
			
			case SPRITE_SEQUENCE:
				RealThing t = argv[1].getVal();
				if(!(t instanceof ObjectThing) || !(((ObjectThing)t).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)t).get();
				v = ListThing.get(argv[2]);
				seq = new int[v.size()];
				for(int i=0; i<v.size(); i++) {
					if(!NumberThing.isNumber((Thing)v.elementAt(i))) 
						throw new HeclException("List element #"+i+" is not a number");
					
					seq[i]=NumberThing.asNumber(((Thing)v.elementAt(i))).intValue();
				}
				
				sp.setFrameSequence(seq);
				
			break;
			
			//TODO: Other commands
			
		}
		
		return null;
	}

	//Module loader
	private static Hashtable cmdtable = new Hashtable();
	static {
		cmdtable.put("sprite.create", new SpriteCmd(SPRITE_CREATE, 1, 3));
		cmdtable.put("sprite.sequence", new SpriteCmd(SPRITE_SEQUENCE, 2, 2));
		cmdtable.put("sprite.frame", new SpriteCmd(SPRITE_FRAME, 1, 3));
		cmdtable.put("sprite.rot", new SpriteCmd(SPRITE_ROT, 1, 3));
		cmdtable.put("sprite.width", new SpriteCmd(SPRITE_WIDTH, 1, 3));
		cmdtable.put("sprite.height", new SpriteCmd(SPRITE_HEIGHT, 1, 3));
		cmdtable.put("sprite.size", new SpriteCmd(SPRITE_SIZE, 1, 3));
		cmdtable.put("sprite.x", new SpriteCmd(SPRITE_X, 1, 3));
		cmdtable.put("sprite.y", new SpriteCmd(SPRITE_Y, 1, 3));
		cmdtable.put("sprite.pos", new SpriteCmd(SPRITE_POS, 1, 4));
	}
	
	public static void load(Interp ip) throws HeclException {
		Operator.load(ip,cmdtable);
	}

	public static void unload(Interp ip) throws HeclException {
		Operator.unload(ip,cmdtable);
	}
	
}
