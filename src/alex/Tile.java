package alex;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Tile {
	
	protected int row;
	protected int col;
	protected BufferedImage image;
	protected BufferedImage imageTwo;
	protected BufferedImage imageThree;
	protected Rectangle boundingBox;
	public static final int TILE_SIZE = 32;
	
	public Tile(int i, int j) {
		this.row = i;
		this.col = j;
		init();
	}
	
	protected abstract void init();
	
	protected abstract void loadInfo();
	
	public BufferedImage getImage(int number) { //Non animated images use 0.
		if(number <= 2) {
			return image;
		}
		else if (number == 3) {
			return imageTwo;
		}
		else if(number == 4) {
			return imageThree;
		}
		else {
			return image;
		}
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	public void tick() {
		
	}
	
}
