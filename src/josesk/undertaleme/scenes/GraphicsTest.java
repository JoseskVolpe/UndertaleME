package josesk.undertaleme.scenes;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import josesk.javameengine.Control;
import josesk.javameengine.Sprite;
import josesk.undertaleme.GameEngine;
import josesk.undertaleme.GameView;

public class GraphicsTest extends GameView{

	Sprite sp, sp2;
	int oW, oH;
	
	public GraphicsTest() {
		try {
			sp = new Sprite(Image.createImage("/buttons.jpg"), 4, 1);
			sp.setFrameSequence(new int[] {0,3,2});
			sp.addColisionBox(0, 0, sp.getOriginalWidth()/2, sp.getOriginalHeight()/2);
			sp.addColisionBox(sp.getOriginalWidth()/2, sp.getOriginalHeight()/2, sp.getOriginalWidth()/2, sp.getOriginalHeight()/2);
			
			sp2 = new Sprite(Image.createImage("/player.png"));
			sp2.addColisionBox(0, 0, sp2.getOriginalWidth(), sp2.getOriginalHeight());
			
			oW = (int) sp.getWidth();
			oH = (int) sp.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	long lastT=System.currentTimeMillis();
	protected void render(Graphics g) {
		
		g.setColor(0x050505);
		g.fillRect(0, 0, g.getClipWidth(),g.getClipHeight());
		
		/*//Órbita
		sp.setAnchor(g.getClipWidth()/2, g.getClipHeight()/2);
		sp.setRot((int)System.currentTimeMillis()/10);
		sp.setX((GameEngine.getOriginalResolutionWidth()/4.f)*Math.cos(System.currentTimeMillis()/500.d));
		sp.setY((GameEngine.getOriginalResolutionHeight()/4.f)*Math.sin(System.currentTimeMillis()/500.d));
		sp.setWidth(oW*(Math.cos(System.currentTimeMillis()/900.d)+1.2f));
		sp.setHeight(oH*(Math.cos(System.currentTimeMillis()/900.d)+1.2f));
		sp.setRefPixelPosition((int)((sp.getOriginalWidth()/2)*Math.cos(System.currentTimeMillis()/200.d)), (int)((sp.getOriginalHeight()/2)*Math.sin(System.currentTimeMillis()/200.d)));
		*/
		
		/*//Rotação na referência
		sp.setAnchor(g.getClipWidth()/2, g.getClipHeight()/2);
		sp.setRot(0);
		sp.setX(0);
		sp.setY(0);
		sp.setRefPixelPosition((int)((sp.getOriginalWidth()/2)*Math.cos(System.currentTimeMillis()/500.d)), (int)((sp.getOriginalHeight()/2)*Math.sin(System.currentTimeMillis()/500.d)));
		*/
		
		//Rotação no canto
		sp.setAnchor((g.getClipWidth()/2), (g.getClipHeight()/2));
		sp.setRot((int)System.currentTimeMillis()/10);
		sp.setX(0);
		sp.setY(0);
		//sp.setRefPixelPosition((int)((sp.getOriginalWidth()/2)), (int)((sp.getOriginalHeight()/2)));
		
		sp2.setAnchor((g.getClipWidth()/2), (g.getClipHeight()/2));
		sp.paint(g);
		sp2.paint(g);
		
	}

	protected void Update(float delta) {
		
		if(System.currentTimeMillis()-lastT>=3000) {
			sp.nextFrame();
			lastT=System.currentTimeMillis();
		}
		
		float vel=100;
		if(Control.isKeyDown(119)) {//w
			sp2.move(0, -vel*delta);
		}
		if(Control.isKeyDown(115)) {//s
			sp2.move(0, vel*delta);
		}
		if(Control.isKeyDown(97)) {//a
			sp2.move(-vel*delta, 0);
		}
		if(Control.isKeyDown(100)) {//d
			sp2.move(vel*delta, 0);
		}
		if(Control.isKeyDown(-4)) {
			sp2.setRot(sp2.getRot()+vel*delta);
		}
		if(Control.isKeyDown(-3)) {
			sp2.setRot(sp2.getRot()-vel*delta);
		}
		
	}

}
