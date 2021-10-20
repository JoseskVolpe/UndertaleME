package josesk.javameengine;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.j2mearmyknife.image.sources.SmartImage;
import com.j2mearmyknife.image.transformations.ImageTransformationResize;
import com.j2mearmyknife.image.transformations.ImageTransformationRotate;

import josesk.undertaleme.GameEngine;

public class Sprite{ //Size adapted sprite
	
	private static final boolean DEBUG_CACHE=true;
	public static boolean DEBUG_COLISION=true;
	
	private static ImageTransformationResize resize = new ImageTransformationResize();
	private static ImageTransformationRotate rotate = new ImageTransformationRotate();
	
	private static Hashtable framecache = new Hashtable(); //Image@frame##
	private static Hashtable cache = new Hashtable(); //Image@WIDTH##HEIGHT##ROT##
	private static Vector used = new Vector();
	public static void clearUnusedCache() {
		Stack remove = new Stack();
		for (Enumeration e = cache.keys() ; e.hasMoreElements() ;) {
	         String key = (String)e.nextElement();
	         if(!used.contains(key))
	        	 remove.addElement(key);
	    }
		
		int removed = remove.size();
		while(!remove.isEmpty())
			cache.remove(remove.pop());
		
		used.removeAllElements();
		if(DEBUG_CACHE) GameEngine.Debug("Removed "+removed+" unused cached sprites");
	}
	public static void clearCache() {
		int cached = cache.size();
		cache.clear();
		used.removeAllElements();
		if(DEBUG_CACHE) GameEngine.Debug("Removed "+cached+" cached sprites");
	}
	
	private Image image;
	private SmartImage render;
	private Image frames[];
	private byte sequence[];
	private Vector colisionBoxes = new Vector();
	
	private short naturalWidth, naturalHeight;
	
	private byte frame=0;
	private float width, height, rot=0, x=0, y=0;
	private short anchorX, anchorY, refPixelX, refPixelY;
	
	public Sprite(Sprite sprite) {
		setImage(image, image.getWidth()/frames[0].getWidth(), image.getHeight()/frames[0].getHeight());
		setX(sprite.getX());
		setY(sprite.getY());
		setWidth(sprite.getWidth());
		setHeight(sprite.getHeight());
		setRot(sprite.getRot());
		sequence = new byte[sprite.sequence.length];
		for(int i=0; i<sequence.length; i++)
			sequence[i]=sprite.sequence[i];
		setFrame(sprite.getFrame());
	}
	
	public Sprite(Image image) {
		setImage(image, 1, 1);
	}
	
	public Sprite(Image image, int widthTiles, int heightTiles) {
		setImage(image, widthTiles, heightTiles);
	}
	
	private boolean tryingAgain=false;
	private final static double rad = 180/Math.PI;
	public void paint(Graphics g) {
		
		try {
		
			int width = (int) this.width;
			int height = (int) this.height;
			int x = (int) this.x;
			int y = (int) this.y;
			
			if(GameEngine.getCanvas().getWidth()!=GameEngine.getOriginalResolutionWidth() || GameEngine.getCanvas().getHeight()!=GameEngine.getOriginalResolutionHeight()) {
				//Rescale and reposition sprite if not the desired resolution
				
				width=GameEngine.adaptX(this.width);
				height=GameEngine.adaptY(this.height);
				x=GameEngine.adaptX(this.x);
				y=GameEngine.adaptY(this.y);
			}
			
			if(width<=0||height<=0) return;
			int refPixelX = (int)(width/(float)frames[sequence[frame]-Byte.MIN_VALUE].getWidth()*this.refPixelX);
			int refPixelY = (int)(height/(float)frames[sequence[frame]-Byte.MIN_VALUE].getHeight()*this.refPixelY);
			//GameEngine.Debug(refPixelX+"X"+refPixelY);
			if(GameEngine.hasResolutionChanged())
				render=null;
			
			String key = frames[sequence[frame]-Byte.MIN_VALUE]+"@WIDTH"+width+"HEIGHT"+height+"ROT"+rot;
			if(render!=null) {
				render.drawOnGraphics(g, x, y, Graphics.HCENTER | Graphics.VCENTER, true);
				if(!used.contains(key)) used.addElement(key);
				return;
			}
			
			SmartImage render = (SmartImage)cache.get(key);
			if(render==null) {
				render=new SmartImage(frames[sequence[frame]-Byte.MIN_VALUE]);
				resize.setTargetDimensions(width, height);
				rotate.setAngle((int) rot);
				render=(SmartImage) resize.process(render);
				render=(SmartImage) rotate.process(render);
				cache.put(key, render);
				if(DEBUG_CACHE) GameEngine.Debug("Cached "+key);
			}
			
			int tx=g.getTranslateX(); //Fix for some phones and MicroEmulator
			int ty=g.getTranslateY();
			g.translate(-tx, -ty);
			
			int px = getRelativeRotX(refPixelX, refPixelY, rot);
			int py = getRelativeRotY(refPixelX, refPixelY, rot);
			
			int dx = (int)(x+anchorX  +tx);
			int dy = (int)(y+anchorY  +ty);
			try {
				render.drawOnGraphics(g, dx-px, dy-py, Graphics.HCENTER | Graphics.VCENTER, true);
			}catch(ArrayIndexOutOfBoundsException e) {
				//Ignore for the damn annoying MicroEmulator glitch
			}
			
			
			
			/*//DEBUG
			g.setColor(0xffffff); //White
			g.drawLine(anchorX+tx, anchorY+ty, dx, dy); //Anchor to refPixel
			g.drawLine(dx, dy, dx-px, dy-py); //refPixel to sprite
			g.fillRect(dx+px, dy+py, 1, 1); //sprite
			g.setColor(0xffff00); //Yellow
			g.fillRect(dx,dy, 1, 1); //refPixel
			//*/
			
			g.translate(tx,  ty);
			
			if(DEBUG_COLISION) {
				g.setColor(0xa5a5ff);
				ColisionBox cb;
				int b[][], cbx, cby, cbw, cbh;
				int scx=x+anchorX;
				int scy=y+anchorY;
				for(int i=0; i<colisionBoxes.size(); i++) {
					cb = (ColisionBox)colisionBoxes.elementAt(i);
					
					cbw=GameEngine.adaptX(cb.width);
					cbh=GameEngine.adaptY(cb.height);
					
					cbx=(int) (GameEngine.adaptX(cb.x)-(this.width/2));
					cby=(int) (GameEngine.adaptY(cb.y)-(this.height/2));
					
					b=new int[][]{
						{getRelativeRotX(cbx, cby, rot), getRelativeRotY(cbx, cby, rot)}, //top-left
						{getRelativeRotX(cbx+cbw, cby, rot), getRelativeRotY(cbx+cbw, cby, rot)}, //top-right
						{getRelativeRotX(cbx, cby+cbh, rot), getRelativeRotY(cbx, cby+cbh, rot)}, //bottom-left
						{getRelativeRotX(cbx+cbw, cby+cbh, rot), getRelativeRotY(cbx+cbw, cby+cbh, rot)} //bottom-right
					};
					
					g.drawLine(scx-px+b[0][0], scy-py+b[0][1], scx-px+b[2][0], scy-py+b[2][1]); //Left
					g.drawLine(scx-px+b[0][0], scy-py+b[0][1], scx-px+b[1][0], scy-py+b[1][1]); //Top
					g.drawLine(scx-px+b[1][0], scy-py+b[1][1], scx-px+b[3][0], scy-py+b[3][1]); //Right
					g.drawLine(scx-px+b[2][0], scy-py+b[2][1], scx-px+b[3][0], scy-py+b[3][1]); //Bottom
					
				}
			}
			
			if(!used.contains(key)) used.addElement(key);
			this.render=render;
		}catch(OutOfMemoryError e) {
			if(tryingAgain)
				throw e;
			
			tryingAgain=true;
			clearCache(); //TODO: Universal GameEngine's API call to clear all cached stuff (Sprite, shader, sounds etc)
			paint(g);
		}
		tryingAgain=false;
	}
	
	private static int getRelativeRotX(int refPixelX, int refPixelY, float rot) {
		return (int)(refPixelX*Math.cos(-rot/rad)+refPixelY*Math.sin(-rot/rad));
	}
	
	public static int getRelativeRotY(int refPixelX, int refPixelY, float rot) {
		return (int)(refPixelY*Math.cos(rot/rad)+refPixelX*Math.sin(rot/rad));
	}
	
	public void setFrameSequence(int[] sequence) {
		if(sequence==null) {
			this.sequence=new byte[frames.length];
			for(int i=0; i<frames.length; i++) 
				this.sequence[i]=(byte) (i+Byte.MIN_VALUE);
			setFrame(0);
			return;
		}
		
		
		this.sequence=new byte[sequence.length];
		for(int i=0; i<sequence.length; i++) 
			this.sequence[i]=(byte)(sequence[i]+Byte.MIN_VALUE);
		setFrame(0);
		
	}
	
	public int getFrameSequenceLength() {
		return sequence.length;
	}
	
	public void setFrame(int frame) {
		this.frame = (byte)frame;
		render=null;
	}
	
	public void nextFrame() {
		byte frame=(byte) (this.frame+1);
		if(frame>=sequence.length) frame=0;
		this.frame=frame;
		render=null;
	}
	
	public void prevFrame() {
		byte frame=(byte) (this.frame-1);
		if(frame<0) frame=(byte) (sequence.length-1);
		this.frame=frame;
		render=null;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void setRot(double rot) {
		this.rot = (float)(Math.abs(rot) - 360*(Math.abs((int)rot)/360));
		render=null;
	}
	
	public float getRot() {
		return rot;
	}
	
	public void resetRot() {
		setRot(0);
	}
	
	public void setWidth(double width) {
		this.width=(float)width;
		render=null;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void resetWidth() {
		width=naturalWidth;
	}
	
	public void setHeight(double height) {
		this.height=(float) height;
		render=null;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void resetHeight() {
		height=naturalHeight;
	}
	
	public void resetSize() {
		resetHeight();
		resetWidth();
	}
	
	public int getOriginalWidth() {
		return frames[0].getWidth();
	}
	
	public int getOriginalHeight() {
		return frames[0].getHeight();
	}
	
	public void setX(double x) {
		this.x = (float) x;
	}
	
	public float getX() {
		return x;
	}
	
	public void setY(double y) {
		this.y = (float) y;
	}
	
	public float getY() {
		return y;
	}
	
	public void setAnchor(int x, int y) {
		anchorX=(short)x;
		anchorY=(short)y;
	}
	
	public int getAnchorX() {
		return anchorX;
	}
	
	public int getAnchorY() {
		return anchorY;
	}
	
	public void setRefPixelPosition(int x, int y) {
		refPixelX = (short)x;
		refPixelY = (short)y;
	}
	
	public int getRefPixelX() {
		return refPixelX;
	}
	
	public int getRefPixelY() {
		return refPixelY;
	}
	
	public void addColisionBox(int x, int y, int width, int height) {
		colisionBoxes.addElement(new ColisionBox(x, y, width, height));
	}
	
	public void clearColisionBoxes() {
		colisionBoxes.removeAllElements();
	}
	
	public Image getSpriteSheet() {
		return image;
	}
	
	public void setImage(Image image, int widthTiles, int heightTiles) {
		this.image=image;
		if(image==null) throw new NullPointerException("Image is NULL");
		int frameWidth = image.getWidth()/widthTiles;
		int frameHeight = image.getHeight()/heightTiles;
		this.naturalHeight = (short)frameHeight;
		this.naturalWidth = (short)frameWidth;
		width = naturalWidth;
		height = naturalHeight;
		rot = 0;
		
		if(frameHeight<1) throw new IllegalArgumentException("frameHeight is less than 1");
		if(frameWidth<1) throw new IllegalArgumentException("frameWidth is less than 1");
		if(image.getWidth()%frameWidth != 0.f) throw new IllegalArgumentException("Image width is not an integer multiple of the widthTiles");
		if(image.getHeight()%frameHeight != 0.f) throw new IllegalArgumentException("Image height is not an integer multiple of the heightTiles");
		
		frames = new Image[(image.getHeight()/frameHeight)*(image.getWidth()/frameWidth)];
		sequence = new byte[frames.length];
		
		int i=0;
		Image cachedFrame=null;
		for(int y=0; y<image.getHeight()/frameHeight; y++)
			for(int x=0; x<image.getWidth()/frameWidth; x++) {
				
				try {
					cachedFrame = (Image)framecache.get(image+"@frame"+i);
					if(cachedFrame==null) {
						frames[i]=Image.createImage(image, x*frameWidth, y*frameHeight, frameWidth, frameHeight, javax.microedition.lcdui.game.Sprite.TRANS_NONE);
						framecache.put(image+"@frame"+i, frames[i]);
						if(DEBUG_CACHE) GameEngine.Debug("Cached frame "+(image+"@frame"+i));
						sequence[i]=(byte) (i+Byte.MIN_VALUE);
						i++;
						continue;
					}
				}catch(ClassCastException e) {
					GameEngine.DebugError("Invalid Sprite frame cache!");
					GameEngine.DebugError(image+"@frame"+i);
				}
				
				frames[i]=cachedFrame;
				sequence[i]=(byte) (i+Byte.MIN_VALUE);
				i++;
			}
	}
	
	private static class ColisionBox{
		short x, y, width, height;
		
		ColisionBox(int x, int y, int width, int height){
			this.x=(short)x;
			this.y=(short)y;
			this.width=(short)width;
			this.height = (short)height;
		}
	}

}
