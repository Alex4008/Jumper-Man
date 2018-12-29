package alex;

public enum EntityType {
	GOOMBA(12),
	GREEN_TURTLE(11),
	RED_TURTLE(0),
	JUMP_GREEN_TURTLE(0),
	JUMP_RED_TURTLE(0),
	HAMMER_BRO(0),
	BULLET(10),
	GREEN_SHELL(12),
	RED_SHELL(12),
	PLANT(0),
	LAVA_BALL(0),
	BOSS(0);
	
	private final int imageSpot;
	
	EntityType(int imageSpot) {
		this.imageSpot = imageSpot;
	}
	
	public int GetImageSpot() { return imageSpot; }
}
