package alex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Item {

	private final int SPEED = 1;
	private final int STILL_TIME = 15;
	
	private int x;
	private int y;
	private int currentCol = (x / Tile.TILE_SIZE);
	private int currentRow = (y / Tile.TILE_SIZE);
	private String direction;
	private BufferedImage imageOne;
	private BufferedImage imageTwo;
	private BufferedImage imageThree;
	private BufferedImage imageFour;
	private BufferedImage currentImage;
	private Rectangle boundingBox;
	private ItemType iType;
	private boolean isFalling;
	private int ticksSinceFall = 0;
	private int ticksExisted = 0;
	private int animationCount = 0;
	private boolean hidden;
	private Material matUnder;
	private boolean gettingBounced = false;
	private boolean hasBeenFlipped = false;
	
	//Constructor
	
	Item(ItemType iType, Game game, int x, int y, String direction) {
		this.iType = iType;
		this.x = x;
		this.y = y;
		this.direction = direction;
		hidden = false;
		isFalling = false;
		boundingBox = new Rectangle(x, y, 24, 24);
		loadInfo(game);
		flipDirection(); //To counteract the bounce that happens when the item appears.
		if(iType.equals(ItemType.FIREWORK)) game.getSoundManager().PlaySound(SoundEffect.FIREWORK);	//When a firework appears, it makes a sound effect.
	}
	
	//Loading / Main Methods
	
	protected void loadInfo(Game game) {
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		
		if(iType == ItemType.FLAG) {
			imageOne = ss.grabImage(9, 11);
		}
		else if(iType == ItemType.COIN) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOne = ss.grabImage(6, iType.GetImageSpot() + 1);
				imageTwo = ss.grabImage(7, iType.GetImageSpot() + 1);
				imageThree = ss.grabImage(8, iType.GetImageSpot() + 1);
				imageFour = ss.grabImage(9, iType.GetImageSpot() + 1);
			}
			else if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOne = ss.grabImage(6, iType.GetImageSpot() + 2);
				imageTwo = ss.grabImage(7, iType.GetImageSpot() + 2);
				imageThree = ss.grabImage(8, iType.GetImageSpot() + 2);
				imageFour = ss.grabImage(9, iType.GetImageSpot() + 2);
			}
			else {
				imageOne = ss.grabImage(6, iType.GetImageSpot());
				imageTwo = ss.grabImage(7, iType.GetImageSpot());
				imageThree = ss.grabImage(8, iType.GetImageSpot());
				imageFour = ss.grabImage(9, iType.GetImageSpot());
			}
		}
		else {
			imageOne = ss.grabImage(6, iType.GetImageSpot());
			
			if(iType.getAnimated() == true) {
				imageTwo = ss.grabImage(7, iType.GetImageSpot());
				imageThree = ss.grabImage(8, iType.GetImageSpot());
				imageFour = ss.grabImage(9, iType.GetImageSpot());
			}
		}
		
		currentImage = imageOne;
	}
	
	public void tick() {
		if(hidden == false && iType != ItemType.FLAG) {
			if(y > World.SCREEN_SIZE_Y - 32) { //To prevent the game from crashing if they fall off the screen
				hidden = true;
				return;
			}
			
			if(x <= 4) { //For the edges of the screen
				x = x + (SPEED);
				flipDirection();
			}
			else if(x >= World.worldSizeX - 2) {
				x = x - (SPEED);
				flipDirection();
			}
			
			if(iType.allowedMovement() == true) {
				if(ticksExisted > STILL_TIME) { //Still time is the amount of time I want to wait before the item moves
					if(direction.equalsIgnoreCase("east")) x = x + SPEED;
					else if(direction.equalsIgnoreCase("west")) x = x - SPEED;
				}
			}
			
			UpdateUnderMaterial();
			if(matUnder.GetDoesDamage() == true | currentRow >= World.ROWS || currentRow < 0 || currentCol >= World.cols || currentCol < 0) hidden = true;
			
			//For the bouncing blocks
			if(currentCol - 1 > 0 && iType != ItemType.COIN) { //Coins don't bounce
				if(World.tiledMap[currentRow + 1][currentCol].isBouncing() == true) {
					if(World.tiledMap[currentRow + 1][currentCol].isOnFall() == true) { //If its falling, then the item needs to be too
						if(hasBeenFlipped == false && gettingBounced == true) {
							flipDirection();
							hasBeenFlipped = true;
						}
						
						if(iType == ItemType.MUSHROOM || iType == ItemType.ONEUP) {
							gettingBounced = false;
						}
						else {
							gettingBounced = true;
							y--;
						}
					}
					else {
						gettingBounced = true;
						y--;
					}
				}
				else {					
					gettingBounced = false;
					hasBeenFlipped = false;
				}
			}
			
			checkCollisions();
			checkFallingState();
			updateBoundingBox();
			checkCollisions();
			
			ticksExisted++;
			
			if(iType.getAnimated() == true) {
				if(animationCount < 10) currentImage = imageOne;
				else if(animationCount >= 10 && animationCount < 20) currentImage = imageTwo;
				else if(animationCount >= 20 && animationCount < 30) currentImage = imageThree;
				else if(animationCount >= 30 && animationCount < 40) {
					if(iType == ItemType.FIREWORK) hidden = true;
					else currentImage = imageFour;
				}
				else {
					animationCount = 0;
					if(iType == ItemType.COIN_FRAGMENT) {
						hidden = true;
					}
				}
				
				animationCount++;
			}
		}
	}
	
	public void render(Graphics g) {
		if(hidden == false) {
			g.drawImage(currentImage, x, y, null);
			if(Game.hitBoxes == true) {
				g.setColor(Color.DARK_GRAY);
				g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
			}
		}
	}
	
	//Other Methods
	
	public void updateBoundingBox() {
		boundingBox.setLocation(this.x + 4, this.y + 7);
		
		currentCol = (int) (x /Tile.TILE_SIZE);
		
		if(gettingBounced == true) return; //I don't want the row updated if it is bouncing
		
		currentRow = (int) (y /Tile.TILE_SIZE);
	}
	
	public void UpdateUnderMaterial() {
		matUnder = World.tiledMap[currentRow][currentCol].getMaterial();
	}
	
	public void checkCollisions() {
		if(gettingBounced == true || iType == ItemType.FIREWORK) return; //If its getting bounced, collisions don't matter / Fireworks ignore collisions.

		//If the last direction was right check for the left side
		int bottomY = (int)(boundingBox.getMaxY());
		
		if(direction.equalsIgnoreCase("east")) {	
			int bottomX = (int) boundingBox.getMinX(); //Left side of bounding box

			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) + 1;

			if(frontTileRow < 0 || frontTileCol < 0 || frontTileRow >= World.ROWS || frontTileCol >= World.cols) return;
			
			if(frontTileCol < World.cols) {
				//If the tile in front of the item is a solid block
				if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
					//If the items bounding box intersects that blocks bounding block
					if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
						direction = "west"; //Switch the direction to west
						x -= SPEED;
						updateBoundingBox();
					}
				}

				//To detect if item is in a block
				if(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == true) {
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())) {
						direction = "west"; //Switch the direction to west
						x -= SPEED;
						updateBoundingBox();
					}
				}

			}

		}
		//else the last direction was left
		else if(direction.equalsIgnoreCase("west")){
			int bottomX = (int) boundingBox.getMaxX(); //Right side of bounding box

			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) - 1;

			if(frontTileRow < 0 || frontTileCol < 0 || frontTileRow >= World.ROWS || frontTileCol >= World.cols) return;
			
			if(frontTileCol >= 0) {
				//If the tile in front of the item is a solid block
				if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
					//If the players bounding box intersects that blocks bounding block
					if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
						direction = "east"; //Switch the direction to east
						x += SPEED;
						updateBoundingBox();
					}
				}

				//To detect if the item is in a block
				if(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == true) {
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())) {
						direction = "east"; //Switch the direction to east
						x += SPEED;
						updateBoundingBox();
					}
				}

			}
		}
		
		//Row position of the cell BELOW the item's feet (in the tiled map)
		int lowerRow = (int)((boundingBox.getMaxY() - 1) / Tile.TILE_SIZE);
		
		//Tile position relative to the upper-left corner of the item's bounding box
		int lowerLeftCorner = (int)((boundingBox.getMinX())/ Tile.TILE_SIZE);
		
		//Tile position relative to the upper-right corner of the item's bounding box
		int lowerRightCorner = (int)((boundingBox.getMaxX()) / Tile.TILE_SIZE);

		if(currentRow >= 0 && gettingBounced == false) { //You can't have the row getting snapped into place if its getting bounced by a block
			//Upper left
			if(World.tiledMap[lowerRow][lowerLeftCorner].getMaterial().GetSolid() == true) { //If that position on the tile map is a block then test to see if the player intersects it
				if(World.tiledMap[lowerRow][lowerLeftCorner].getBoundingBox().intersects(boundingBox)) {
					y = currentRow * Tile.TILE_SIZE;
					updateBoundingBox();
				}
			}
			
			if(World.tiledMap[lowerRow][lowerRightCorner].getMaterial().GetSolid() == true) {
				if(World.tiledMap[lowerRow][lowerRightCorner].getBoundingBox().intersects(boundingBox)) {
					y = currentRow * Tile.TILE_SIZE;
					updateBoundingBox();
				}
			}	
		}	
	}

	public void flipDirection() {
		if(direction == "east") direction = "west";
		else direction = "east";
	}
	
	public void checkFallingState() {
		if(iType == ItemType.COIN || iType == ItemType.FIREWORK) return; //Coins do not have gravity
		
		if(gettingBounced == true) return; //The item shouldn't be falling if its in mid bounce
	
		if(isFalling == true) {
			y += 0 + (ticksSinceFall / 7);
			currentRow = y / Tile.TILE_SIZE;
			updateBoundingBox();
			
			ticksSinceFall++;
		}
		
		int lowerLeftX = (int) boundingBox.getMinX() + 1;
		int lowerRightX = (int) boundingBox.getMaxX() - 1;
		
		int underTileXL = lowerLeftX / Tile.TILE_SIZE;
		int underTileXR = lowerRightX / Tile.TILE_SIZE;
		
		if(currentRow + 1 >= World.ROWS || underTileXR >= World.cols) return;
		
		if((World.tiledMap[currentRow + 1][underTileXR].getMaterial().GetSolid() == false) && (World.tiledMap[currentRow + 1][underTileXL].getMaterial().GetSolid() == false)){
			isFalling = true;
			return;
		}
			
		isFalling = false;	
	}
	
	//Getters and Setters
	
	public Rectangle getBoundingBox() { return boundingBox; }
	public void setHidden(boolean hidden) { this.hidden = hidden; }
	public boolean isHidden() { return hidden; }
	public ItemType GetItemType() { return iType; }
}
