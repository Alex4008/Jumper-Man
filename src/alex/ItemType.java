package alex;

public enum ItemType {

	//Type: 
	EMPTY(-1, false, false),
	MUSHROOM(4, false, true),
	FLOWER(6, true, false),
	FLAG(11, false, false),
	ONEUP(5, false, true),
	COIN(7, true, false),
	COIN_FRAGMENT(10, true, false),
	FIREWORK(11, true, false),
	KEY(12, true, false);
	
	private int imageSpot;
	private boolean animated;
	private boolean allowsMoving;
	
	ItemType(int imageSpot, boolean animated, boolean allowedMovement) {
		this.imageSpot = imageSpot;
		this.animated = animated;
		this.allowsMoving = allowedMovement;
	}

	public int GetImageSpot() { return imageSpot; }
	public boolean getAnimated() { return animated; }
	public boolean allowedMovement() { return allowsMoving; }
}
