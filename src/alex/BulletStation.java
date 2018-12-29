package alex;

public class BulletStation {
	
	private final int FIRE_RATE = 270; //Fires every 270 ticks (4.5 seconds)
	
	private int x;
	private int y;
	String direction; //Can be 'left' 'right' or 'both'
	
	private int counter = 0;
	
	BulletStation(int x, int y, String direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
	
	
	public void tick(Game g) {
		if(counter != FIRE_RATE) {
			counter++;
			return;
		}
		
		counter = 0;
		int playerCol = g.GetThePlayer().getCurrentCol();
		int stationCol = x / Tile.TILE_SIZE;
		int stationRow = y / Tile.TILE_SIZE;
		
		if(Math.abs(playerCol - stationCol) <= 38) {
			g.getSoundManager().PlaySound(SoundEffect.FIREWORK);
			if(direction.toLowerCase().contains("left")) {
				if(World.tiledMap[stationRow][stationCol - 1].getMaterial().GetSolid() == false)
					g.getEntityList().add(new Entity(EntityType.BULLET, g, x - 32, y, "west"));
			}
			else if(direction.toLowerCase().contains("right")) {
				if(World.tiledMap[stationRow][stationCol + 1].getMaterial().GetSolid() == false)
					g.getEntityList().add(new Entity(EntityType.BULLET, g, x + 32, y, "east"));
			}
			else {
				if(World.tiledMap[stationRow][stationCol - 1].getMaterial().GetSolid() == false)
					g.getEntityList().add(new Entity(EntityType.BULLET, g, x - 32, y, "west"));
				
				if(World.tiledMap[stationRow][stationCol + 1].getMaterial().GetSolid() == false)
					g.getEntityList().add(new Entity(EntityType.BULLET, g, x + 32, y, "east"));
			}
		}
	}
	
}
