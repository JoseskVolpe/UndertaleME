package josesk.javameengine.hecl.commands;

import java.util.Hashtable;
import java.util.Vector;

import javax.microedition.lcdui.Image;

import org.hecl.DoubleThing;
import org.hecl.HeclException;
import org.hecl.IntThing;
import org.hecl.Interp;
import org.hecl.ListThing;
import org.hecl.NumberThing;
import org.hecl.ObjectThing;
import org.hecl.Operator;
import org.hecl.Properties;
import org.hecl.RealThing;
import org.hecl.StringThing;
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
	public static final byte SPRITE_X=7;
	public static final byte SPRITE_Y=8;
	public static final byte SPRITE_POS=9;
	
	
	protected SpriteCmd(int cmdcode, int minargs, int maxargs) {
		super(cmdcode, minargs, maxargs);
	}

	//FIXME: Commands not tested yet
	public Thing operate(int cmdcode, Interp interp, Thing[] argv) throws HeclException {
		
		Properties props;
		Sprite sp=null;
		Vector v;
		int seq[];
		RealThing rt;
		Thing t;
		int in;
		
		switch(cmdcode) {
		/* sprite.create $sprite
		 * sprite.create $image
		 * sprite.create $image $int $int
		 * returns $sprite
		 */
			case SPRITE_CREATE:
				if(argv.length<2) throw new HeclException("Specify a sprite or image\n"
						+ "Usages:\n"
						+ "* sprite.create $sprite\n"
						+ "* sprite.create $image\n"
						+ "* sprite.create $image $int $int");
				switch(argv.length) {
					case 2:
						rt = argv[1].getVal();
						if(!(rt instanceof ObjectThing) || !( ((ObjectThing)rt).get() instanceof Image || ((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument must be a image or sprite");
						Object obj = ((ObjectThing)rt).get();
						
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
			
			/* sprite.sequence $sprite [reset | $intArray]
			 */
			case SPRITE_SEQUENCE:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				if(StringThing.get(argv[2]).equals("reset")) {
					sp.setFrameSequence(null);
					return null;
				}
				v = ListThing.get(argv[2]);
				seq = new int[v.size()];
				for(int i=0; i<v.size(); i++) {
					if(!NumberThing.isNumber((Thing)v.elementAt(i))) 
						throw new HeclException("List element #"+i+" is not a number");
					
					seq[i]=NumberThing.asNumber(((Thing)v.elementAt(i))).intValue();
				}
				
				sp.setFrameSequence(seq);
				
			break;
			
			/* sprite.sequence.length $sprite
			 * returns $int
			 */
			case SPRITE_SEQUENCE_LENGTH:
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
			return IntThing.create(sp.getFrameSequenceLength());
			
			/* sprite.frame $sprite [next | prev | reset | set $int]
			 * returns $int
			 */
			case SPRITE_FRAME:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				props = new Properties();
				props.setProps(argv, 2);
				if(argv.length>=3) {
					if(StringThing.get(argv[2]).equals("next"))
						sp.nextFrame();
					if(StringThing.get(argv[2]).equals("prev"))
						sp.nextFrame();
					if(StringThing.get(argv[2]).equals("reset"))
						sp.setFrame(0);
					if(StringThing.get(argv[2]).equals("set")) {
						if(argv.length<=3)
							throw new HeclException("set must have a int property");
						t = argv[3];
						if(!NumberThing.isNumber(t)) throw new HeclException("set property must be a number");
						in = NumberThing.asNumber(t).intValue();
						sp.setFrame(in);
					}
				}
			return IntThing.create(sp.getFrame());
			
			/* sprite.rot $sprite [reset | + $double | - $double | set $double]
			 * returns $double
			 */
			case SPRITE_ROT:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				if(argv.length>2) {
					if(StringThing.get(argv[2]).equals("reset")) {
						sp.resetRot();
						return DoubleThing.create(sp.getRot());
					}
					
					if(argv.length==3) throw new HeclException(StringThing.get(argv[2])+" must have a double property");
					t = argv[3];
					if(!NumberThing.isNumber(t)) throw new HeclException("set property must be a number");
					if(StringThing.get(argv[2]).equals("+")) {
						sp.setRot(sp.getRot()+NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("-")) {
						sp.setRot(sp.getRot()-NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("set")) {
						sp.setRot(NumberThing.asNumber(t).doubleValue());
					}
				}
				
			return DoubleThing.create(sp.getRot());
			
			/* sprite.width $sprite [reset | + $double | - $double | set $double]
			 * returns $double
			 */
			case SPRITE_WIDTH:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				if(argv.length>2) {
					if(StringThing.get(argv[2]).equals("reset")) {
						sp.resetWidth();
						return DoubleThing.create(sp.getWidth());
					}
					if(argv.length==3) throw new HeclException(StringThing.get(argv[2])+" must have a double property");
					t = argv[3];
					if(!NumberThing.isNumber(t)) throw new HeclException("set property must be a number");
					if(StringThing.get(argv[2]).equals("+")) {
						sp.setWidth(sp.getWidth()+NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("-")) {
						sp.setWidth(sp.getWidth()-NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("set")) {
						sp.setWidth(NumberThing.asNumber(t).doubleValue());
					}
				}
				
			return DoubleThing.create(sp.getWidth());
			
			/* sprite.height $sprite [reset | + $double | - $double | set $double]
			 * returns $double
			 */
			case SPRITE_HEIGHT:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				if(argv.length>2) {
					if(StringThing.get(argv[2]).equals("reset")) {
						sp.resetHeight();
						return DoubleThing.create(sp.getHeight());
					}
					if(argv.length==3) throw new HeclException(StringThing.get(argv[2])+" must have a double property");
					t = argv[3];
					if(!NumberThing.isNumber(t)) throw new HeclException("set property must be a number");
					if(StringThing.get(argv[2]).equals("+")) {
						sp.setHeight(sp.getHeight()+NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("-")) {
						sp.setHeight(sp.getHeight()-NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("set")) {
						sp.setHeight(NumberThing.asNumber(t).doubleValue());
					}
				}
				
			return DoubleThing.create(sp.getHeight());
			
			/* sprite.x $sprite [+ $double | - $double | set $double]
			 * returns $double
			 */
			case SPRITE_X:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				if(argv.length>2) {
					if(argv.length==3) throw new HeclException(StringThing.get(argv[2])+" must have a double property");
					t = argv[3];
					if(!NumberThing.isNumber(t)) throw new HeclException("set property must be a number");
					if(StringThing.get(argv[2]).equals("+")) {
						sp.setX(sp.getX()+NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("-")) {
						sp.setX(sp.getX()-NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("set")) {
						sp.setX(NumberThing.asNumber(t).doubleValue());
					}
				}
				
			return DoubleThing.create(sp.getX());
			
			/* sprite.y $sprite [+ $double | - $double | set $double]
			 * returns $double
			 */
			case SPRITE_Y:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				if(argv.length>2) {
					if(argv.length==3) throw new HeclException(StringThing.get(argv[2])+" must have a double property");
					t = argv[3];
					if(!NumberThing.isNumber(t)) throw new HeclException("set property must be a number");
					if(StringThing.get(argv[2]).equals("+")) {
						sp.setY(sp.getY()+NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("-")) {
						sp.setY(sp.getY()-NumberThing.asNumber(t).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("set")) {
						sp.setY(NumberThing.asNumber(t).doubleValue());
					}
				}
				
			return DoubleThing.create(sp.getY());
			
			/* sprite.pos $sprite [+ $double $double | - $double $double | set $double $double]
			 * returns $doubleArray
			 */
			case SPRITE_POS:
				if(argv.length<2) throw new HeclException("Specify a sprite");
				rt = argv[1].getVal();
				if(!(rt instanceof ObjectThing) || !(((ObjectThing)rt).get() instanceof Sprite)) throw new HeclException("Argument 1 must be an sprite");
				sp = (Sprite) ((ObjectThing)rt).get();
				
				if(argv.length>2) {
					if(argv.length<5) throw new HeclException(StringThing.get(argv[2])+" must have a double property");
					if(!NumberThing.isNumber(argv[3])) throw new HeclException("set property 1 must be a number");
					if(!NumberThing.isNumber(argv[4])) throw new HeclException("set property 2 must be a number");
					if(StringThing.get(argv[2]).equals("+")) {
						sp.setX(sp.getX()+NumberThing.asNumber(argv[3]).doubleValue());
						sp.setY(sp.getY()+NumberThing.asNumber(argv[4]).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("-")) {
						sp.setX(sp.getX()-NumberThing.asNumber(argv[3]).doubleValue());
						sp.setY(sp.getY()-NumberThing.asNumber(argv[4]).doubleValue());
					}
					if(StringThing.get(argv[2]).equals("set")) {
						sp.setX(NumberThing.asNumber(argv[3]).doubleValue());
						sp.setY(NumberThing.asNumber(argv[4]).doubleValue());
					}
				}
				
				v=new Vector();
				v.addElement(DoubleThing.create(sp.getX()));
				v.addElement(DoubleThing.create(sp.getY()));
			return ListThing.create(v);
			
			//TODO: More commands
			
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
