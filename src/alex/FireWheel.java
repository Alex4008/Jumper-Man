package alex;

import java.awt.Graphics;
import java.util.ArrayList;

public class FireWheel {
	
	//private final int SPEED = 5;
	
	private ArrayList<Fireball> fire;
	private int x;
	private int y;
	private int currentCol = (x / Tile.TILE_SIZE);
	private int currentRow = (y / Tile.TILE_SIZE);
	//private String rotationDirection;
	//private int numFireballs;
	private int count = 0;
	
	//Constructors
	
	FireWheel(Game game, int x, int y, int numFireballs, String rotationDirection) {
		this.x = x;
		this.y = y;
		//this.numFireballs = numFireballs;
		//this.rotationDirection = rotationDirection;
		
		fire = new ArrayList<Fireball>();
		
		for(int i = 0; i < numFireballs; i++) {
			//System.out.println(i);
			fire.add(new Fireball(game, x + 22, y - 8 + (i * 12), true));
		}
	}
	
	//Main functions

	public void tick(Game g) {
		count++;
		for(Fireball f : fire) {
			if(count == 1) {
				double newX = (x + 22) + (f.getRawX() - (x + 22))*Math.cos(0.5) - (f.getRawY() - (y - 8))*Math.sin(0.5);
				double newY = (y - 8) + (f.getRawX() - (x + 22))*Math.sin(0.5) + (f.getRawY() - (y - 8))*Math.cos(0.5);
				f.changeDirection(newX, newY);
			}
			f.tick(g);
		}
		
		if(count == 8) count = 0;
		if(fire.get(1).getRawY() == (y - 8) + (12)) {
			int i = 0;
			for(Fireball f : fire) {
				f.changeDirection(x + 22, y - 8 + (i * 12));
				f.updateBoundingBox();
				i++;
			}
		}
	}
	
	public void render(Graphics g) {
		for(Fireball f : fire) f.render(g);
	}
	
	//Other Methods
	
	

	//Getters and Setters
	public int getCurrentRow() { return currentRow; }
	public int getCurrentCol() { return currentCol; }
	public ArrayList<Fireball> getFireballList() { return fire; }
}
