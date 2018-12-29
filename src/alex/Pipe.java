package alex;

import java.awt.Graphics;

public class Pipe {
	private Game g;
	private int x;
	private int y;
	private boolean containsEntity;
	private boolean displayEntity;
	private boolean entityBlocking;
	private Entity entity;
	private int counter;
	
	Pipe(int x, int y) { //For pipes with no entity inside
		this.x = x;
		this.y = y;
		this.containsEntity = false;
		this.entity = null;
		this.displayEntity = true;
		this.entityBlocking = false;
	}
	
	Pipe(Game game, int x, int y, boolean containsEntity, EntityType entity) { //For pipes with an entity inside
		this.g = game;
		this.x = x;
		this.y = y;
		this.containsEntity = containsEntity;
		this.displayEntity = true;
		this.entityBlocking = false;
		this.entity = new Entity(entity, game, x + 16, y - 32);
	}
	
	private void checkForBlockage() {
		if(g.GetThePlayer().getCurrentRow() == ((y / Tile.TILE_SIZE) - 1) && (g.GetThePlayer().getCurrentCol() == x / Tile.TILE_SIZE || (g.GetThePlayer().getCurrentCol() + 1 == x / Tile.TILE_SIZE )))
			entityBlocking = true;
		else entityBlocking = false;
	}
	
	public void tick() {
		if(containsEntity == true) {
			counter++;
			checkForBlockage();
			entity.setAlive(displayEntity);
			entity.tick(g);
			if(displayEntity == false && entityBlocking == true) return;
			
			if(counter <= 300) displayEntity = false; 
			else if(counter < 700) displayEntity = true;
			else counter = 0;
		}
	}
	
	public boolean hasEntity() {
		return entity != null;
	}
	
	public void render(Graphics g) {
		if(displayEntity == true && entity != null) {
			entity.render(g);
		}
	}
	
	//Getters and Setters
	public Entity getEntity() { return entity; }
}
