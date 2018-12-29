package alex;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Button {
	
	private final int WIDTH = 34;
	private final int HEIGHT = 34;
	
	private int x;
	private int y;
	private boolean isClicked;
	private Rectangle boundingBox;
	private BufferedImage image;
	private LevelType levelType;
	
	private Material buttonMat;
	
	Button(int x, int y, BufferedImage image, Material bm) {
		this.x = x;
		this.y = y;
		isClicked = false;
		this.image = image;
		boundingBox = new Rectangle(x, y, WIDTH, HEIGHT);
		buttonMat = bm;
	}
	
	Button(int x, int y, BufferedImage image, LevelType lt) {
		this.x = x;
		this.y = y;
		isClicked = false;
		this.image = image;
		boundingBox = new Rectangle(x, y, WIDTH, HEIGHT);
		buttonMat = null;
		levelType = lt;
	}
	
	public void updateBoundingBox(int x, int y) {
		this.x = x;
		this.y = y;
		boundingBox.setLocation(x, y);
	}
	
	public boolean isClicked() {
		return isClicked;
	}
	
	public void setClicked(boolean clicked) {
		isClicked = clicked;
	}
	
	public void render(Graphics g) {
		g.drawImage(image, x, y, null);
		/*
		if(isClicked == true) g.setColor(Color.YELLOW);
		else g.setColor(Color.GRAY);
		g.drawRect(x, y, WIDTH, HEIGHT);
		 */
	}
	
	public void tick(Game g) {
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	public Material getButtonMaterial() {
		return buttonMat;
	}
	
	public LevelType getLevelType() {
		return levelType;
	}
	
}
