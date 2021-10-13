package josesk.undertaleme.scenes;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import josesk.javameengine.Sprite;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;

public class GraphicsTest extends GameView{

	Sprite sp;
	int oW, oH;
	
	public GraphicsTest() {
		try {
			sp = new Sprite(Image.createImage("/buttons.jpg"), 4, 1);
			sp.setFrameSequence(new int[] {0,3,2});
			oW = (int) sp.getWidth();
			oH = (int) sp.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	long lastT=System.currentTimeMillis();
	protected void render(Graphics g) {
		
		g.setColor(0xaaaaaa);
		g.fillRect(0, 0, g.getClipWidth(),g.getClipHeight());
		g.translate(g.getClipWidth()/2, g.getClipHeight()/2);
		
		sp.setRot((int)System.currentTimeMillis()/10);
		sp.setX((GameEngine.getOriginalResolutionWidth()/4.f)*Math.cos(System.currentTimeMillis()/500.d));
		sp.setY((GameEngine.getOriginalResolutionHeight()/4.f)*Math.sin(System.currentTimeMillis()/500.d));
		sp.setWidth(oW*(Math.cos(System.currentTimeMillis()/900.d)+1.2f));
		sp.setHeight(oH*(Math.cos(System.currentTimeMillis()/900.d)+1.2f));
		
		try{
			sp.paint(g);
		}catch(ArrayIndexOutOfBoundsException e) {
			//Ignore for the damn annoying MicroEmulator glitch
		}
		
		
		
	}

	protected void Update(float delta) {
		
		if(System.currentTimeMillis()-lastT>=3000) {
			sp.nextFrame();
			lastT=System.currentTimeMillis();
		}
		
	}

}
