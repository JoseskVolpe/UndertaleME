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

public class Sprite { //Size adapted sprite
	
	private static final boolean DEBUG=true;
	
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
		if(DEBUG) GameEngine.Debug("Removed "+removed+" unused cached sprites");
	}
	
	private Image image;
	private SmartImage render;
	private Image frames[];
	
	private short naturalWidth, naturalHeight;
	
	private byte frame=0;
	private short width, height, rot, x, y;
	
	public Sprite(Image image, int tileWidth, int tileHeight) {
		this.image=image;
		if(image==null) throw new NullPointerException("Image is NULL");
		int frameWidth = image.getWidth()/tileWidth;
		int frameHeight = image.getHeight()/tileHeight;
		this.naturalHeight = (short)frameHeight;
		this.naturalWidth = (short)frameWidth;
		width = naturalWidth;
		height = naturalHeight;
		rot = 0;
		
		if(frameHeight<1) throw new IllegalArgumentException("frameHeight is less than 1");
		if(frameWidth<1) throw new IllegalArgumentException("frameWidth is less than 1");
		if(image.getWidth()%frameWidth != 0.f) throw new IllegalArgumentException("Image width is not an integer multiple of the tileWidth");
		if(image.getHeight()%frameHeight != 0.f) throw new IllegalArgumentException("Image height is not an integer multiple of the tileHeight");
		
		frames = new Image[(image.getHeight()/frameHeight)*(image.getWidth()/frameWidth)];
		
		int i=0;
		Image cachedFrame=null;
		for(int y=0; y<image.getHeight()/frameHeight; y++)
			for(int x=0; x<image.getWidth()/frameWidth; x++) {
				
				try {
					cachedFrame = (Image)framecache.get(image+"@frame"+i);
					if(cachedFrame==null) {
						frames[i]=Image.createImage(image, x*frameWidth, y*frameHeight, frameWidth, frameHeight, javax.microedition.lcdui.game.Sprite.TRANS_NONE);
						framecache.put(image+"@frame"+i, frames[i]);
						if(DEBUG) GameEngine.Debug("Cached frame "+(image+"@frame"+i));
						i++;
						continue;
					}
				}catch(ClassCastException e) {
					GameEngine.DebugError("Invalid Sprite frame cache!");
					GameEngine.DebugError(image+"@frame"+i);
				}
				
				frames[i]=cachedFrame;
				i++;
			}
	}
	
	public void paint(Graphics g) {
		String key = frames[frame]+"@WIDTH"+width+"HEIGHT"+height+"ROT"+rot;
		if(render!=null) {
			render.drawOnGraphics(g, x, y, Graphics.HCENTER | Graphics.VCENTER, true);
			if(!used.contains(key)) used.addElement(key);
			return;
		}
		
		SmartImage render = (SmartImage)cache.get(key);
		if(render==null) {
			render=new SmartImage(frames[frame]);
			resize.setTargetDimensions(width, height);
			rotate.setAngle(rot);
			render=(SmartImage) resize.process(render);
			render=(SmartImage) rotate.process(render);
			cache.put(key, render);
			if(DEBUG) GameEngine.Debug("Cached "+key);
		}
		
		render.drawOnGraphics(g, x, y, Graphics.HCENTER | Graphics.VCENTER, true);
		if(!used.contains(key)) used.addElement(key);
		this.render=render;
	}
	
	public void setFrame(int frame) {
		this.frame = (byte)frame;
	}
	
	public void nextFrame() {
		byte frame=(byte) (this.frame+1);
		if(frame>=frames.length) frame=0;
		this.frame=frame;
	}
	
	public void lastFrame() {
		byte frame=(byte) (this.frame-1);
		if(frame<0) frame=(byte) (frames.length-1);
		this.frame=frame;
	}
	
	public int getFrame() {
		return frame;
	}
	
	public void setRot(int rot) {
		this.rot = (short)(Math.abs(rot) - 360*(Math.abs(rot)/360));
		render=null;
	}
	
	public int getRot() {
		return rot;
	}
	
	public void setWidth(int width) {
		this.width=(short)width;
		render=null;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height=(short)height;
		render=null;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setX(int x) {
		this.x = (short)x;
	}
	
	public int getX() {
		return x;
	}
	
	public void setY(int y) {
		this.y = (short)y;
	}
	
	public int getY() {
		return y;
	}

}
