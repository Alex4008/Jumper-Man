package alex;

import java.awt.Rectangle;

public class Block extends Tile {

	private final int EXTRA_COIN_TIMER = 60;
	
	private int x;
	private int y;
	private Material mat;
	private int animationCount;
	
	private int animateLocation;
	
	private boolean containsItem;
	private ItemType item;
	
	private boolean containsEntity;
	private EntityType entity;
	
	private int bounceCounter = 0;
	private boolean onFall = false;
	
	private boolean extraCoin;
	private int counter;
	private boolean countRunning;
	
	private String direction = ""; //For the level editor for bullet stations
	
	public Block(Material matType, int i, int j) {
		this(matType, i, j, ItemType.EMPTY, false);
	}
	
	public Block(Material matType, int i, int j, ItemType item) {
		this(matType, i, j, item, false);
	}
	
	public Block(Material matType, int i, int j, ItemType item, boolean extraCoin) {
		super(i, j);
		this.mat = matType;
		this.item = item;
		
		if(item != null && item != ItemType.EMPTY) setContainsItem(true);
		else setContainsItem(false);
		
		animationCount = 0;
		animateLocation = 0;
		this.extraCoin = extraCoin;
		counter = 0;
		countRunning = false;
		loadInfo();
	}
	
	@Override
	protected void init() {
		x = col * TILE_SIZE;
		y = row * TILE_SIZE;
		boundingBox = new Rectangle(x, y, TILE_SIZE, TILE_SIZE);
	}

	protected void loadInfo() {
		if(mat.isAnimated() == false) image = mat.GetImageOne();
		else { //This has been hard coded for the QUESTION block because I am lazy. It will need rewritten if I add more animated blocks >_<
			image = mat.GetImageOne();
			imageTwo = mat.GetImageTwo();
			imageThree = mat.GetImageThree();
		}
		
	}
	
	public void tick(Game g) {
		//For extra coin
		if(countRunning == true) {
			counter++;
			if(counter > EXTRA_COIN_TIMER) {
				extraCoin = false;
				countRunning = false;
			}
		}
		
		//For block bouncing.
		if(onFall == false) {
			if(bounceCounter > 0 && bounceCounter < 8) bounceCounter++;
			else if(bounceCounter >= 8) onFall = true;
		}
		
		if(onFall == true) {
			bounceCounter--;
			
			//If its destoyable, destroy it before it comes back down
			if(mat.GetBreakable() == true && g.GetThePlayer().getGrown() == true && extraCoin == false) {
				if(counter >= EXTRA_COIN_TIMER) { //The block had extra coins in it which means it does not break.
					setMaterial(Material.EMPTY_COIN_SOLID);
				}
				else {	
					mat = Material.AIR;
					g.getSoundManager().PlaySound(SoundEffect.BREAK_BLOCK);
				}
			}
			
			if(bounceCounter <= 0) {
				bounceCounter = 0;
				onFall = false;
			}
		}
		boundingBox = new Rectangle(x, y - bounceCounter, TILE_SIZE, TILE_SIZE);
	}
	
	public void setBounceCounter(int bounceCounter) {
		this.bounceCounter = bounceCounter;
	}
	
	public boolean isBouncing() {
		if(bounceCounter != 0) return true;
		else return false;
	}
	
	public boolean isOnFall() {
		return onFall;
	}
	
	public int getBounceCounter() {
		return bounceCounter;
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}
	
	public Material getMaterial() {
		return mat;
	}
	
	public void setMaterial(Material newMat) {
		mat = newMat;
		loadInfo();
		animationCount = 0;
		animateLocation = 0;
	}
	
	public void RunAnimation() {
		if(animateLocation < 4) animateLocation++;
		else animateLocation = 1;
	}
	
	public int GetAnimationLocation() {
		return animateLocation;
	}
	
	public int GetAnimationCounter() {
		return animationCount;
	}
	
	public void SetAnimationCounter(int animationCount) {
		this.animationCount = animationCount;
	}
	
	public void setItemType(ItemType item) {
		this.item = item;
	}
	
	public ItemType getItemType() {
		return item;
	}
	
	public boolean GetHoldItem()  {
		if(item != ItemType.EMPTY) return true;
		else return false;
	}

	public boolean isContainsItem() {
		return containsItem;
	}

	public void setContainsItem(boolean containsItem) {
		this.containsItem = containsItem;
	}
	
	public boolean isContainsEntity() {
		return containsEntity;
	}

	public void setContainsEntity(boolean containsEntity) {
		this.containsEntity = containsEntity;
	}
	
	public void setEntityType(EntityType entity) {
		this.entity = entity;
	}
	
	public EntityType getEntityType() {
		return entity;
	}
	
	public boolean isExtraCoin() {
		return extraCoin;
	}
	
	public void setExtraCoin(boolean extraCoin) {
		this.extraCoin = extraCoin;
	}
	
	public int getCounter() {
		return counter;
	}
	
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void startCounter() {
		countRunning = true;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
}
