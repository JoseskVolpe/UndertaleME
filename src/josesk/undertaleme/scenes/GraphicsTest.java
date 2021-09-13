package josesk.undertaleme.scenes;

import java.io.IOException;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import josesk.javameengine.Sprite;
import josesk.undertaleme.GameView;

public class GraphicsTest extends GameView{

	Sprite sp;
	
	public GraphicsTest() {
		try {
			sp = new Sprite(Image.createImage("/buttons.jpg"), 4, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	long lastT=System.currentTimeMillis();
	protected void render(Graphics g) {
		
		g.setColor(0xaaaaaa);
		g.fillRect(0, 0, g.getClipWidth(),g.getClipHeight());
		
		sp.setX(g.getClipWidth()/2);
		sp.setY(g.getClipHeight()/2);
		sp.setRot((int)System.currentTimeMillis()/1);
		sp.paint(g);
		
		if(System.currentTimeMillis()-lastT>=3000) {
			Sprite.clearUnusedCache();
			lastT=System.currentTimeMillis();
		}
		
	}

	protected void Update(float delta) {
		
		
		
	}

}
