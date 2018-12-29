package alex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {

	private int x;
	private int y;
	
	private BufferedImage player;
	private SpriteSheet ss;
	
	private Rectangle boundingBox;
	
	private boolean jumping;
	private int jumpCount;

	private int lastDirection = KeyEvent.VK_RIGHT;
	
	private boolean moving;
	
	private boolean isFalling;
	
	private static final int JUMP_THRESH = 28; //Was 22
	private static final int JUMP_THRESH_SPRINT = 35; // 29
	private static final int JUMP_THRESH_GROWN = JUMP_THRESH_SPRINT;
	private static final int JUMP_THRESH_SPRINT_GROWN = 38; //32;
	public static final int MOVE_DISPLACEMENT = 4;
	public static final int SPRINT_DISPLACEMENT = 5;
	
	private int currentCol = (x / Tile.TILE_SIZE);
	private int currentRow = (y / Tile.TILE_SIZE);
	
	private int collisionsRow;
	
	private int ticksSinceFall = 0;
	
	private int animationState = 0;
	
	private Material matUnder;
	
	private boolean grown = false;
	private boolean crouch = false;
	private boolean fireMan = false;
	
	private Game g;
	
	private boolean rightHeld = false;
	private boolean leftHeld = false;
	private boolean crouchHeld = false;
	
	private boolean sprinting = false;
	
	private int fireballCounter = 0;
	private boolean canShootFireball = true;
	
	private boolean levelDone = false;
	
	private boolean inv = false;
	private int invCount = 0;
	
	private boolean alive;
	
	private int playerLives;
	private int playerCoins;
	private int playerScore;
	
	private int lastTimeDigit;
	
	public Player(int x, int y, Game game) {
		this.x = x;
		this.y = y;
		g = game;
		ss = new SpriteSheet(game.getSpriteSheet());
		
		player = ss.grabImage(1, 1);
		
		boundingBox = new Rectangle(x,y,21,28);
		setJumping(false);
		jumpCount = 0;
		ticksSinceFall = 0;
		alive = true;
		
		setGrown(false);
		fireMan = false;
		moving = false;
		rightHeld = false;
		leftHeld = false;
		changeLocation(Game.spawnWidth, Game.spawnHeight, true);
		
		setPlayerLives(3);
		setPlayerCoins(0);
		setPlayerScore(0);
	}
	
	public void checkFireballCollisions(Game g) {
		for(Fireball f : g.getFireballList()) {
			if(f.isHidden() == false && f.getFromBoss() == true) {
				if(boundingBox.intersects(f.getBoundingBox())) {
					dealPlayerDamage();
					f.setHidden(true);
					f.killFireball();
					f.tick(g);
					return;
				}
			}
		}
	}
	
	public void checkFireWheelCollisions(Game g) {
		for(FireWheel fw : g.getFireWheelList()) {
			for(Fireball f : fw.getFireballList()) {
				if(f.getBoundingBox().intersects(boundingBox)) {
					if(inv == false) dealPlayerDamage();
				}
			}
		}
	}
	
	public void checkJumpState() {
		if(jumping == true) {
			
			if(y <= 16) {
				setJumping(false);
				jumpCount = 0;
				isFalling = true;
				ticksSinceFall = 20;
			}
			
			if(grown == true) {
				if(sprinting == true) {
					if(jumpCount < JUMP_THRESH_SPRINT_GROWN) {
						y -= (JUMP_THRESH_SPRINT_GROWN - jumpCount) / 4;
						updateBoundingBox("y");
					}
				}
				else {
					if(jumpCount < JUMP_THRESH_GROWN) {
						y -= (JUMP_THRESH_GROWN - jumpCount) / 4;
						updateBoundingBox("y");
					}
				}
			}
			else {
				if(sprinting == true) {
					if(jumpCount < JUMP_THRESH_SPRINT) {
						y -= (JUMP_THRESH_SPRINT - jumpCount) / 4;
						updateBoundingBox("y");
					}
				}
				else {
					if(jumpCount < JUMP_THRESH) {
						y -= (JUMP_THRESH - jumpCount) / 4;
						updateBoundingBox("y");
					}
				}
			}
			
			jumpCount++;
			
			if(grown == true && fireMan == true) { //Sprite for fireman
				if(lastDirection == KeyEvent.VK_RIGHT) {
					player = ss.grabImage(5, 9, 32, 64);
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					player = ss.grabImage(5, 11, 32, 64); 
				}
			}
			else if(grown == true) { //Sprite for grown mario
				if(lastDirection == KeyEvent.VK_RIGHT) {
					player = ss.grabImage(5, 5, 32, 64);
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					player = ss.grabImage(5, 7, 32, 64); 
				}
			}
			else { //Sprite for normal mario
				if(lastDirection == KeyEvent.VK_RIGHT) {
					player = ss.grabImage(5, 1);
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					player = ss.grabImage(5, 2); 
				}
			}
			
			if(grown == true) {
				if(sprinting == true) {
					if(jumpCount >= JUMP_THRESH_SPRINT_GROWN) {
						setJumping(false);
						jumpCount = 0;
						isFalling = true;
						ticksSinceFall = 0;
					}
				}
				else {
					if(jumpCount >= JUMP_THRESH_GROWN) {
						setJumping(false);
						jumpCount = 0;
						isFalling = true;
						ticksSinceFall = 0;
					}
				}
			}
			else {
				if(sprinting == true) {
					if(jumpCount >= JUMP_THRESH_SPRINT) {
						setJumping(false);
						jumpCount = 0;
						isFalling = true;
						ticksSinceFall = 0;
					}
				}
				else {
					if(jumpCount >= JUMP_THRESH) {
						setJumping(false);
						jumpCount = 0;
						isFalling = true;
						ticksSinceFall = 0;
					}
				}
			}	
		}
	}
	
	public void checkFallingState() {
		if(boundingBox.getMaxY() / Tile.TILE_SIZE >= World.ROWS) {
			playerRespawn();
		}
		
		if(jumping == true) { //Do not run this if jumping is in progress
			return;
		}
	
		if(isFalling == true) {
			
			if(grown == true) y += 0 + (ticksSinceFall / 4);
			else y += 0 + (ticksSinceFall / 5);
			
			currentRow = y / Tile.TILE_SIZE;
			updateBoundingBox("y");
			
			ticksSinceFall++;
			
			if(grown == true && fireMan == true) { //Sprite for fireman
				if(lastDirection == KeyEvent.VK_RIGHT) {
					player = ss.grabImage(5, 9, 32, 64);
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					player = ss.grabImage(5, 11, 32, 64); 
				}
			}
			else if(grown == true) { //Sprite for grown mario
				if(lastDirection == KeyEvent.VK_RIGHT) {
					player = ss.grabImage(5, 5, 32, 64);
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					player = ss.grabImage(5, 7, 32, 64); 
				}
			}
			else { //Sprite for regular mario
				if(lastDirection == KeyEvent.VK_RIGHT) {
					player = ss.grabImage(5, 1);
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					player = ss.grabImage(5, 2); 
				}
			}
		}
		
		int lowerLeftX = (int) boundingBox.getMinX() + 1;
		int lowerRightX = (int) boundingBox.getMaxX() - 1;
		
		int underTileXL = lowerLeftX / Tile.TILE_SIZE;
		int underTileXR = lowerRightX / Tile.TILE_SIZE;
		
		if(currentRow + 1 >= World.ROWS || underTileXR >= World.cols) {
			return;
		}
		
		if((World.tiledMap[currentRow + 1][underTileXR].getMaterial().GetSolid() == false) && (World.tiledMap[currentRow + 1][underTileXL].getMaterial().GetSolid() == false)){
			isFalling = true;
			return;
		}
			
		isFalling = false;
		
	}
	
	public void move(int direction) {
		if(direction == KeyEvent.VK_RIGHT) {
			lastDirection = KeyEvent.VK_RIGHT;
		}
		else if (direction == KeyEvent.VK_LEFT) {
			lastDirection = KeyEvent.VK_LEFT;
		}
		moving = true;
	}
	
	public void changeLocation(int x, int y, boolean isFalling) {
		this.isFalling = isFalling;
		ticksSinceFall = 0;
		this.x = x;
		this.y = y;
	}
	
	public void collisionsJumpingCheck(Game g, int row, int corner) {
		if(World.tiledMap[row][corner].getMaterial().GetSolid() == true || World.tiledMap[row][corner].getMaterial() == Material.HIDDEN_BLOCK) { //If that position on the tile map is a block then test to see if the player intersects it
			if(World.tiledMap[row][corner].getBoundingBox().intersects(boundingBox)) {
				//Start the animation for the block ONLY if there is AIR or HIDDEN BLOCK above it.
				if(row - 1 >= 0) {
					
					if((World.tiledMap[row - 1][corner].getMaterial() == Material.AIR || World.tiledMap[row - 1][corner].getMaterial() == Material.HIDDEN_BLOCK) && World.tiledMap[row][corner].getMaterial().allowedBounce() == true) {
						World.tiledMap[row][corner].setBounceCounter(1); //This starts the bounce process for the block.
					}
					else if(World.tiledMap[row][corner].getMaterial().GetBreakable() == true && grown == true) {
						World.tiledMap[row][corner].setMaterial(Material.AIR);
						g.getSoundManager().PlaySound(SoundEffect.BREAK_BLOCK);
					}
				}
				
				//Play sound for hitting a block
				g.getSoundManager().PlaySound(SoundEffect.BUMP);
				
				//Handle block change
				if(World.tiledMap[row][corner].getMaterial().GetBreakable() == true) { 
					//Change block from BRICK to EMPTY_COIN_SOLID if it is a BRICK and contains an item or entity.
					if(World.tiledMap[row][corner].getItemType() != ItemType.EMPTY || World.tiledMap[row][corner].isContainsEntity() == true) {
						if(World.tiledMap[row][corner].isExtraCoin() == false) {
							World.tiledMap[row][corner].setMaterial(Material.EMPTY_COIN_SOLID);
						}
					}
					//Smashing bricks used to happen here. It now happens in the block class. Might move more of this over as time goes on.
				}
				//Change block from QUESTION to EMPTY_COIN if it is a QUESTION block
				else if(World.tiledMap[row][corner].getMaterial() == Material.QUESTION) {
					World.tiledMap[row][corner].setMaterial(Material.EMPTY_COIN);
				}
				//Change block from HIDDEN_BLOCK to EMPTY_COIN_SOLID if it is a HIDDEN_BLOCK
				else if(World.tiledMap[row][corner].getMaterial() == Material.HIDDEN_BLOCK) {
					World.tiledMap[row][corner].setMaterial(Material.EMPTY_COIN_SOLID);
				}
				
				//Handle item dropping.
				if(World.tiledMap[row][corner].GetHoldItem() == true) { //Drop the item it contains if it contains one
					ItemType itemHeld = World.tiledMap[row][corner].getItemType();
					
					if(itemHeld == ItemType.COIN_FRAGMENT) {
						if(World.tiledMap[row][corner].isExtraCoin() == true) World.tiledMap[row][corner].startCounter();
						
						g.AddItem(ItemType.COIN_FRAGMENT, (corner * Tile.TILE_SIZE), ((row - 1) * Tile.TILE_SIZE));
						addPoints(ItemType.COIN);
						playerCoins++;
						g.getSoundManager().PlaySound(SoundEffect.COIN);
					}
					else if(itemHeld == ItemType.FLOWER) {
						g.AddItem(ItemType.FLOWER, (corner * Tile.TILE_SIZE), ((row - 1) * Tile.TILE_SIZE));
						g.getSoundManager().PlaySound(SoundEffect.APPEAR_POWER_UP);
					}
					else if(itemHeld == ItemType.MUSHROOM) {
						if(grown == true) { //If the player is already grown, instead spit out a flower
							g.AddItem(ItemType.FLOWER, (corner * Tile.TILE_SIZE), ((row - 1) * Tile.TILE_SIZE));
							g.getSoundManager().PlaySound(SoundEffect.APPEAR_POWER_UP);
						}
						else { //If they are not grown, then just send it with a mushroom
							g.AddItem(ItemType.MUSHROOM, (corner * Tile.TILE_SIZE), ((row - 1) * Tile.TILE_SIZE));
							g.getSoundManager().PlaySound(SoundEffect.APPEAR_POWER_UP);
						}
					}
					else if(itemHeld == ItemType.ONEUP) {
						g.AddItem(ItemType.ONEUP, (corner * Tile.TILE_SIZE), ((row - 1) * Tile.TILE_SIZE));
						g.getSoundManager().PlaySound(SoundEffect.APPEAR_POWER_UP);
					}
					
					if(World.tiledMap[row][corner].isExtraCoin() == false) World.tiledMap[row][corner].setItemType(ItemType.EMPTY);
				}
				
				//Handle entity spawning (If there was an entity in the block)
				if(World.tiledMap[row][corner].isContainsEntity() == true) { //Spawn the entity if it contains one
					EntityType entityHeld = World.tiledMap[row][corner].getEntityType();
					g.AddEntity(entityHeld, (corner * Tile.TILE_SIZE), ((row - 1) * Tile.TILE_SIZE - 16));
					g.getSoundManager().PlaySound(SoundEffect.APPEAR_POWER_UP);
				}
				
				//Push the player out
				while(World.tiledMap[row][corner].getBoundingBox().intersects(boundingBox) == true) {
					y+= 1;
					updateBoundingBox();
				}
				
				setJumping(false);
				jumpCount = 0;
				isFalling = true;
				ticksSinceFall = 20;
				return;
			}
		}
	}
	
	public void checkCollisions(Game g) {
		if(jumping == true) { //If the player was jumping
			int upperRow = (int)((boundingBox.getMinY()-1) / Tile.TILE_SIZE); //Row position of the cell above the character's head (in the tiled map)
			int upperLeftCorner = (int)(boundingBox.getMinX() / Tile.TILE_SIZE); //Tile position relative to the upper-left corner of the character's bounding box
			int upperRightCorner = (int)((boundingBox.getMaxX()) / Tile.TILE_SIZE); //Tile position relative to the upper-right corner of the character's bounding box
			
			if(currentRow >= 0) {
				collisionsJumpingCheck(g, upperRow, upperLeftCorner);
				collisionsJumpingCheck(g, upperRow, upperRightCorner);
			}	
		} 
		
		//If the last direction was right check for the left side
		else if(lastDirection == KeyEvent.VK_RIGHT) {
			int bottomY = (int)(boundingBox.getMaxY());
			int bottomX = (int) boundingBox.getMinX(); //Left side of bounding box
			
			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) + 1;
			
			if(grown == true) { //Only run if they are grown AND they havent already been pushed out due to the bottom section
				frontTileRow = frontTileRow - 1;
				if(frontTileCol < World.cols) {
					//If the tile in front of the player is a solid block
					if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
						//If the players bounding box intersects that blocks bounding block
						if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
							//Push the player out
							if(sprinting == true) {
								x -= SPRINT_DISPLACEMENT;
							}
							else {
								x -= MOVE_DISPLACEMENT;
							}
							updateBoundingBox("x");
						}
					}				
				}
			}
			
		}
		//else the last direction was left
		else if(lastDirection == KeyEvent.VK_LEFT){
			int bottomY = (int)(boundingBox.getMaxY());
			int bottomX = (int) boundingBox.getMaxX(); //Right side of bounding box
			
			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) - 1;

			if(grown == true) { //Only run if they are grown AND they havent already been pushed out due to the bottom section
				frontTileRow = frontTileRow - 1;
				if(frontTileCol >= 0) {
					//If the tile in front of the player is a solid block
					if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
						//If the players bounding box intersects that blocks bounding block
						if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
							//Push the player out
							if(sprinting == true) {
								x += SPRINT_DISPLACEMENT;
							}
							else {
								x += MOVE_DISPLACEMENT;
							}
							updateBoundingBox("x");
						}
					}	
				}
			}
			
		}
		
		//Code for bottom of blocks
		
		//Row position of the cell BELOW the character's feet (in the tiled map)
		int lowerRow = (int)((boundingBox.getMaxY() - 1) / Tile.TILE_SIZE);
		
		//Tile position relative to the upper-left corner of the character's bounding box
		int lowerLeftCorner = (int)((boundingBox.getMinX())/ Tile.TILE_SIZE);
		
		//Tile position relative to the upper-right corner of the character's bounding box
		int lowerRightCorner = (int)((boundingBox.getMaxX()) / Tile.TILE_SIZE);
		
		if(currentRow >= 0) {
			
			//Upper left
			if(World.tiledMap[lowerRow][lowerLeftCorner].getMaterial().GetSolid() == true) { //If that position on the tile map is a block then test to see if the player intersects it
				if(World.tiledMap[lowerRow][lowerLeftCorner].getBoundingBox().intersects(boundingBox)) {
					y = currentRow * Tile.TILE_SIZE;
					updateBoundingBox("y");
				}
			}
			
			if(World.tiledMap[lowerRow][lowerRightCorner].getMaterial().GetSolid() == true) {
				if(World.tiledMap[lowerRow][lowerRightCorner].getBoundingBox().intersects(boundingBox)) {
					y = currentRow * Tile.TILE_SIZE;
					updateBoundingBox("y");
				}
			}	
		}	
		
	}
	
	public void updateBoundingBox() {
		if(grown == true) { //Bigger "grown" bounding box
			if(crouch == true) { //Crouching (Small size)
				boundingBox.setLocation(this.x + 5, this.y + 3);
			}
			else { //Not crouching (Large size)
				boundingBox.setLocation(this.x + 5, this.y - 16);
			}
		}
		else { //Default bounding box
			boundingBox.setLocation(this.x + 5, this.y + 3);
		}
		
		currentCol = Math.round(x / Tile.TILE_SIZE);
		collisionsRow = Math.round( (int) boundingBox.getCenterY() / Tile.TILE_SIZE);
		currentRow = (int) (y / Tile.TILE_SIZE);
	}
	
	public void updateBoundingBox(String xOrY) {
		if(grown == true) { //Bigger "grown" bounding box
			if(crouch == true) { //Crouching (Small size)
				boundingBox.setLocation(this.x + 5, this.y + 3);
			}
			else { //Not crouching (Large size)
				boundingBox.setLocation(this.x + 5, this.y - 16);
			}
		}
		else { //Default bounding box
			boundingBox.setLocation(this.x + 5, this.y + 3);
		}
		
		if(xOrY.equalsIgnoreCase("x")) {
			currentCol = Math.round(x / Tile.TILE_SIZE);
		}
		else {
			currentRow = Math.round(y / Tile.TILE_SIZE);
			collisionsRow = Math.round( (int) boundingBox.getCenterY() / Tile.TILE_SIZE);
		}
	}
	
	public void UpdateUnderMaterial() {
		matUnder = World.tiledMap[currentRow][currentCol].getMaterial();	
	}
	
	public void checkEntityCollisions(Game g) {
		
		//Check for plants
		for(Pipe p : g.getPipeList()) {
			if(p.hasEntity() != false) {
				if(p.getEntity().isAlive() == true) {
					Rectangle bBox = p.getEntity().getBoundingBox();
					if(boundingBox.intersects(bBox)) { //You cannot jump on the heads of these plants to kill them.
						if(inv == false) dealPlayerDamage();
					}
				}
			}
		}
		
		//All other entities
		for(int i = 0; i < g.getEntityList().size(); i++) {		
			if(g.getEntityList().get(i).isAlive() == true) {
				Rectangle bBox = g.getEntityList().get(i).getBoundingBox();
				
				if(g.getEntityList().get(i).getEntityType() == EntityType.BOSS || g.getEntityList().get(i).getEntityType() == EntityType.LAVA_BALL) {
					if(bBox.intersects(boundingBox)) { //You cannot jump on the heads of these to kill them.
						if(inv == false) dealPlayerDamage();
						return;
					}
				}
				
				if(boundingBox.getMaxY() >= (bBox.getMaxY() - 12) && boundingBox.intersects(bBox)) {
					
					if((g.getEntityList().get(i).getEntityType() == EntityType.GREEN_SHELL || g.getEntityList().get(i).getEntityType() == EntityType.RED_SHELL) && g.getEntityList().get(i).isMoving() == false) {
						g.getEntityList().get(i).playerHitShell(lastDirection);
						//Give the player short inv to prevent the shell from hurting them
						inv = true;
						//Push the player out
						if(sprinting == true) {
							if(lastDirection == KeyEvent.VK_RIGHT) {
								x -= SPRINT_DISPLACEMENT * 2;
							}
							else {
								x += SPRINT_DISPLACEMENT * 2;
							}
						}
						else {
							if(lastDirection == KeyEvent.VK_RIGHT) {
								x -= MOVE_DISPLACEMENT * 2;
							}
							else {
								x += MOVE_DISPLACEMENT * 2;
							}
						}
						updateBoundingBox("x");
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.GREEN_SHELL || g.getEntityList().get(i).getEntityType() == EntityType.RED_SHELL) { //Bounce the shell for hitting the player
						g.getEntityList().get(i).playerHitShell(lastDirection);
						dealPlayerDamage();
						//Push the player out
						if(sprinting == true) {
							if(lastDirection == KeyEvent.VK_RIGHT) {
								x -= SPRINT_DISPLACEMENT;
							}
							else {
								x += SPRINT_DISPLACEMENT;
							}
						}
						else {
							if(lastDirection == KeyEvent.VK_RIGHT) {
								x -= MOVE_DISPLACEMENT;
							}
							else {
								x += MOVE_DISPLACEMENT;
							}
						}
						updateBoundingBox("x");
					}
					else {
						if(inv == false) {
							dealPlayerDamage();
						}
					}
				}
				else if(boundingBox.getMaxY() < (bBox.getMaxY() - 12) && boundingBox.intersects(bBox)) {
					if(g.getEntityList().get(i).getEntityType() == EntityType.GOOMBA) {
						addPoints(EntityType.GOOMBA);
						g.getEntityList().get(i).setAlive(false);
						g.getSoundManager().PlaySound(SoundEffect.STOMP);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.BULLET)  {
						addPoints(EntityType.BULLET);
						g.getEntityList().get(i).setAlive(false);
						g.getSoundManager().PlaySound(SoundEffect.STOMP);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.JUMP_GREEN_TURTLE) {
						addPoints(EntityType.JUMP_GREEN_TURTLE);
						g.getEntityList().get(i).setAlive(false);
						g.getEntityList().add(new Entity(EntityType.GREEN_TURTLE, g, g.getEntityList().get(i).getX(), g.getEntityList().get(i).getY()));
						g.getSoundManager().PlaySound(SoundEffect.STOMP);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.JUMP_RED_TURTLE) {
						addPoints(EntityType.JUMP_RED_TURTLE);
						g.getEntityList().get(i).setAlive(false);
						g.getEntityList().add(new Entity(EntityType.RED_TURTLE, g, g.getEntityList().get(i).getX(), g.getEntityList().get(i).getY()));
						g.getSoundManager().PlaySound(SoundEffect.STOMP);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.GREEN_TURTLE) {
						addPoints(EntityType.GREEN_TURTLE);
						g.getEntityList().get(i).setAlive(false);
						g.getEntityList().add(new Entity(EntityType.GREEN_SHELL, g, g.getEntityList().get(i).getX(), g.getEntityList().get(i).getY()));
						g.getSoundManager().PlaySound(SoundEffect.STOMP);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.RED_TURTLE) {
						addPoints(EntityType.RED_TURTLE);
						g.getEntityList().get(i).setAlive(false);
						g.getEntityList().add(new Entity(EntityType.RED_SHELL, g, g.getEntityList().get(i).getX(), g.getEntityList().get(i).getY()));
						g.getSoundManager().PlaySound(SoundEffect.STOMP);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.GREEN_SHELL) {
						addPoints(EntityType.GREEN_SHELL);
						g.getEntityList().get(i).playerHitShell(lastDirection);
						g.getSoundManager().PlaySound(SoundEffect.KICK);
					}
					else if(g.getEntityList().get(i).getEntityType() == EntityType.RED_SHELL) {
						addPoints(EntityType.RED_SHELL);
						g.getEntityList().get(i).playerHitShell(lastDirection);
						g.getSoundManager().PlaySound(SoundEffect.KICK);
					}
					
					setJumping(true);
					jumpCount = JUMP_THRESH / 2;
					return;
				}
			}
		}
	}
	
	public void checkItemCollisions(Game g) {
		for(int i = 0; i < g.getItemList().size(); i++) {
			Rectangle bBox = g.getItemList().get(i).getBoundingBox();
			
			if(boundingBox.intersects(bBox) && g.getItemList().get(i).isHidden() == false) {
				g.getItemList().get(i).setHidden(true);
				if(g.getItemList().get(i).GetItemType() == ItemType.COIN) {
					playerCoins++;
					addPoints(ItemType.COIN);
					g.getSoundManager().PlaySound(SoundEffect.COIN);
				}
				else if(g.getItemList().get(i).GetItemType() == ItemType.MUSHROOM) {
					if(grown == false) {
						grown = true;
					}
					addPoints(ItemType.MUSHROOM);
					g.getSoundManager().PlaySound(SoundEffect.GRAB_POWER_UP);
				}
				else if(g.getItemList().get(i).GetItemType() == ItemType.FLOWER) {
					if(grown == false) {
						grown = true;
					}
					else if(fireMan == false) {
						fireMan = true;
					}
					addPoints(ItemType.FLOWER);
					g.getSoundManager().PlaySound(SoundEffect.GRAB_POWER_UP);
				}
				else if(g.getItemList().get(i).GetItemType() == ItemType.ONEUP) {
					addPoints(ItemType.ONEUP);
					playerLives++;
					g.getSoundManager().PlaySound(SoundEffect.ONE_UP);
				}
				else if(g.getItemList().get(i).GetItemType() == ItemType.FLAG) {
					addPoints(750);
					lastTimeDigit = g.getTimer() % 10;
					levelDone = true;
					g.getSoundManager().PlaySound(SoundEffect.FLAGPOLE_DROP);
				}
				else if(g.getItemList().get(i).GetItemType() == ItemType.KEY) {
					addPoints(1000);
					lastTimeDigit = g.getTimer() % 10;
					levelDone = true;
					//Delete all bridges on screen
					for(int o = 0; o < World.ROWS; o++) {
						for(int j = 0; j < World.cols; j++) {
							if(World.tiledMap[o][j].getMaterial() == Material.BRIDGE) {
								World.tiledMap[o][j] = new Block(Material.AIR, o * Tile.TILE_SIZE, j * Tile.TILE_SIZE);
							}
						}
					}
				}
			}
			
		}
	}
	
	public void runAnimation() {
		if(moving == true) {
			//Handle running animation
			if(animationState != 40) {
				if(sprinting == true) {
					animationState += 2;
				}
				else {
					animationState++;
				}
			}
			else {
				animationState = 20;
			}

			if(lastDirection == KeyEvent.VK_RIGHT) {
				if(jumping == false && isFalling == false) {
					if(grown == true && fireMan == true) {
						player = ss.grabImage(animationState / 10, 9, 32, 64); //Change animation to RIGHT for LARGE FIRE	
					}
					else if(grown == true) {
						player = ss.grabImage(animationState / 10, 5, 32, 64); //Change animation to RIGHT for LARGE
					}
					else {
						player = ss.grabImage(animationState / 10, 1); //Change animation to RIGHT for SMALL
					}
				}
			}
			else if(lastDirection == KeyEvent.VK_LEFT) {
				if(jumping == false && isFalling == false)  {
					if(grown == true && fireMan == true) {
						player = ss.grabImage(animationState / 10, 11, 32, 64); //Change animation to LEFT for LARGE FIRE
					}
					else if (grown == true) {
						player = ss.grabImage(animationState / 10, 7, 32, 64); //Change animation to LEFT for LARGE
					}
					else {
						player = ss.grabImage(animationState / 10, 2); //Change animation to LEFT for SMALL
					}
				}
			}
		}
		else {
			animationState = 10;

			if(jumping == false && isFalling == false) {
				if(lastDirection == KeyEvent.VK_RIGHT) {
					if(sprinting == true) {
						if(grown == true && fireMan == true) {
							player = ss.grabImage(10, 9, 32, 64); //Sprinting image for right FIRE
						}
						else if(grown == true) {
							player = ss.grabImage(10, 5, 32, 64); //Sprinting image for right BIG
						}
						else {
							player = ss.grabImage(8, 1); //Sprinting image for right SMALL
						}
					}
					else if(grown == true && fireMan == true) {
						if(crouch == true) {
							player = ss.grabImage(7, 1); //CROUCH image for right FIRE
						}
						else {
							player = ss.grabImage(animationState / 10, 9, 32, 64); //Standing image for right LARGE FIRE
						}
					}
					else if(grown == true) {
						if(crouch == true) {
							player = ss.grabImage(6, 1); //CROUCH image for right
						}
						else {
							player = ss.grabImage(animationState / 10, 5, 32, 64); //Standing image for right LARGE
						}
					}
					else {
						player = ss.grabImage(animationState / 10, 1); //Standing image for right SMALL
					}
				}
				else if(lastDirection == KeyEvent.VK_LEFT) {
					if(sprinting == true) {
						if(grown == true && fireMan == true) {
							player = ss.grabImage(10, 11, 32, 64); //Sprinting image for left FIRE
						}
						else if(grown == true) {
							player = ss.grabImage(10, 7, 32, 64); //Sprinting image for left BIG
						}
						else {
							player = ss.grabImage(8, 2); //Sprinting image for left SMALL
						}
					}
					else if(grown == true && fireMan == true) {
						if(crouch == true && fireMan == true) {
							player = ss.grabImage(7, 2); //CROUCH image for left FIRE
						}
						else {
							player = ss.grabImage(animationState / 10, 11, 32, 64); //Standing image for left LARGE FIRE
						}
					}
					else if(grown == true) {
						if(crouch == true) {
							player = ss.grabImage(6, 2); //CROUCH image for left
						}
						else {
							player = ss.grabImage(animationState / 10, 7, 32, 64); //Standing image for left LARGE
						}
					}
					else {
						player = ss.grabImage(animationState / 10, 2); //Standing image for left SMALL
					}
				}
			}
		}
	}
	
	public void sprintingCheck() {
		if(isFalling == true || crouch == true) {
			sprinting = false;
		}
	}
	
	public void tick() {
		checkFireballCollisions(g);
		checkFireWheelCollisions(g);
		checkJumpState();
		checkFallingState();

		sprintingCheck();
		
		if(g.getTimer() <= 0 && levelDone == false) {
			playerRespawn();
		}
		
		if(playerCoins >= 100) {
			playerLives++;
			g.getSoundManager().PlaySound(SoundEffect.ONE_UP);
			playerCoins = 0;
		}
		
		if(moving == true) {
			int pixelsToMove = 0;
			if(lastDirection == KeyEvent.VK_RIGHT) {	
				if(isFalling == true) pixelsToMove = MOVE_DISPLACEMENT / 2;
				else if(sprinting == true) pixelsToMove = SPRINT_DISPLACEMENT;
				else pixelsToMove = MOVE_DISPLACEMENT;
				
				while(World.tiledMap[collisionsRow][currentCol + 1].getMaterial().GetSolid() == false && pixelsToMove > 0) {
					x+= 1;
					updateBoundingBox();
					pixelsToMove--;
				}
			}
			else if(lastDirection == KeyEvent.VK_LEFT) {	
				if(isFalling == true) pixelsToMove = MOVE_DISPLACEMENT / 2;
				else if(sprinting == true) pixelsToMove = SPRINT_DISPLACEMENT;
				else pixelsToMove = MOVE_DISPLACEMENT;
				
				while(World.tiledMap[collisionsRow][currentCol].getMaterial().GetSolid() == false && pixelsToMove > 0) {
					x-= 1;
					updateBoundingBox();
					pixelsToMove--;
				}
			}
		}

		if(x <= 4) {
			x += SPRINT_DISPLACEMENT;
		}
		else if(x >= World.worldSizeX - 64) {
			x -= SPRINT_DISPLACEMENT;
		}
		
		//Ok so this used to kill the player if they fell out of the world. But I guess something else is doing it. And I am too lazy to find it out. And it crashes with out return.. so.. here it is.
		if(y > World.SCREEN_SIZE_Y - 32) {
			return;
		}
		
		runAnimation();
		
		updateBoundingBox();

		checkCollisions(g);
		checkItemCollisions(g);
		checkEntityCollisions(g);
		UpdateUnderMaterial();

		//Kill the player if on illegal block
		if(matUnder.GetDoesDamage() == true) {
			playerRespawn();
			return;
		}
		//End the level if they have reached the flag pole
		else if(matUnder == Material.POLE_SECTION || matUnder == Material.POLE_BOTTOM) {
			if(levelDone == false) {
				lastTimeDigit = g.getTimer() % 10;
				//System.out.println(lastTimeDigit);
			}
			stop();
			levelDone = true;
		}
		
		if(inv == true) {
			invCount++;
			if(invCount > 60) {
				inv = false;
				invCount = 0;
			}
		}

	}
	
	public void render(Graphics g) {
		if(grown == true && crouch == false) {
			g.drawImage(player, x, y - 32, null);
		}
		else {
			g.drawImage(player, x, y, null);
		}
		if(Game.hitBoxes == true) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
		}
	}
	
	public void playerRespawn() {
		
		if(Game.devMode == true) return;
		
		if(playerScore < 0) playerScore = 0;
		
		alive = false;
		setGrown(false);
		fireMan = false;
		stop();
		rightHeld = false;
		leftHeld = false;
		sprinting = false;
		playerLives--;
		changeLocation(Game.spawnWidth, Game.spawnHeight, true);
	}
	
	public void addPoints(int p) {
		playerScore += p;
	}
	
	public void addPoints(EntityType e) {
		switch(e) {
		case GOOMBA:
			playerScore += 50;
			break;
		case JUMP_GREEN_TURTLE:
			playerScore += 20;
			break;
		case JUMP_RED_TURTLE:
			playerScore += 25;
			break;
		case GREEN_TURTLE:
			playerScore += 50;
			break;
		case RED_TURTLE:
			playerScore += 70;
			break;
		case BULLET:
			playerScore += 10;
			break;
		case PLANT:
			playerScore += 75;
			break;
		case GREEN_SHELL:
			playerScore += 20;
			break;
		case RED_SHELL:
			playerScore += 25;
			break;
		case BOSS:
			playerScore += 500;
			break;
		default:
			System.out.println("Error: Cannot find points for Entity - " + e.toString());
		}
	}
	
	public void addPoints(ItemType i) {
		switch(i) {
		case COIN:
			playerScore += 10;
			break;
		case MUSHROOM:
			playerScore += 150;
			break;
		case FLOWER:
			playerScore += 250;
			break;
		case ONEUP:
			playerScore += 400;
			break;	
		default:
			System.out.println("Error: Could not find points for Item - " + i.toString());
			break;
		}
	}
	
	public void jump() {
		if(this.jumping == false && this.isFalling == false && getCrouch() == false) {
			jumping = true;
			this.jumpCount = 0;
			if(grown == true) {
				g.getSoundManager().PlaySound(SoundEffect.LARGE_JUMP);
			}
			else {
				g.getSoundManager().PlaySound(SoundEffect.SMALL_JUMP);
			}
		}
	}
	
	public void sprint() {
		if(getJumping() == false || isMoving() == true) { //You cannot sprint while in mid air
			setSprinting(true);
		}
	}
	
	public void crouch() {
		if(GetRightHeld() == false && GetLeftHeld() == false) {
			SetCrouchHeld(true);
		}
	}
	
	public void shootFireball() {
		if(isFireMan() == true && getGrown() == true) {
			if(GetCanShootFireball() == true) {
				g.getSoundManager().PlaySound(SoundEffect.FIREBALL);
				SetCanShootFireball(false);
				if(GetLastDirection() == 37)
					g.getFireballList().add(new Fireball(g, getX(), getY() - 24, "west"));
				else if (GetLastDirection() == 39)
					g.getFireballList().add(new Fireball(g, getX(), getY() - 24, "east"));
			}
		}
	}
	
	public void stop() {
		moving = false;
	}
	
	//Getters and Setters
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setX(int x) {	
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setJumping(boolean value) {
		if(crouch == true) {
			jumping = false;
		}
		else {
			jumping = value;
			if(grown == true) {
				if(value == true) g.getSoundManager().PlaySound(SoundEffect.LARGE_JUMP);
			}
			else {
				if(value == true) g.getSoundManager().PlaySound(SoundEffect.SMALL_JUMP);
			}
		}
	}
	
	public boolean getJumping() {
		return jumping;
	}
	
	public void setLastDirection(int dir) {
		lastDirection = dir;
	}
	
	public int GetLastDirection() {
		return lastDirection;
	}
	
	public void setMoving(boolean value) {
		moving = value;
	}
	
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	public int getCurrentRow() {
		return currentRow;
	}
	
	public int getCurrentCol() {
		return currentCol;
	}
	
	public Material getUnderMaterial() {
		return matUnder;
	}
	
	public void setGrown(boolean grown) {
		
		this.grown = grown;
		updateBoundingBox();
		
		if(grown == true) {
			boundingBox = new Rectangle(x,y,23,47);
			updateBoundingBox();
		}
		else {
			setCrouch(false);
			boundingBox = new Rectangle(x,y,21,28);
			updateBoundingBox();
		}
	}
	
	public boolean getGrown() {
		return grown;
	}
	
	public void setCrouch(boolean crouch) {
		if(grown == true && jumping == false && isFalling == false) {
			this.crouch = crouch;
			
			if(crouch == true) {
				boundingBox = new Rectangle(x,y,21,28);
				runAnimation();
				updateBoundingBox();
			}
			else {
				boundingBox = new Rectangle(x,y,23,47);
				runAnimation();
				updateBoundingBox();
			}	
		}
		else {
			crouch = false;
		}
	}
	
	public boolean getCrouch() {
		return crouch;
	}
	
	public boolean isFireMan() {
		return fireMan;
	}
	
	public boolean GetRightHeld() {
		return rightHeld;
	}
	
	public void SetRightHeld(boolean rightHeld) {
		this.rightHeld = rightHeld;
	}
	
	public boolean GetLeftHeld() {
		return leftHeld;
	}
	
	public void SetLeftHeld(boolean leftHeld) {
		this.leftHeld = leftHeld;
	}
	
	public boolean GetCrouchHeld() {
		return crouchHeld;
	}
	
	public void SetCrouchHeld(boolean crouchHeld) {
		this.crouchHeld = crouchHeld;
	}
	
	public int GetFireballCounter() {
		return fireballCounter;
	}
	
	public void SetFireballCounter(int fireballCounter) {
		this.fireballCounter = fireballCounter;
	}
	
	public boolean GetCanShootFireball() {
		return canShootFireball;
	}
	
	public void SetCanShootFireball(boolean canShootFireball) {
		this.canShootFireball = canShootFireball;
	}
	
	public boolean isLevelDone() {
		return levelDone;
	}
	
	public void dealPlayerDamage() {
		inv = true;
		if(fireMan == true) {
			g.getSoundManager().PlaySound(SoundEffect.HURT);
			fireMan = false;
		}
		else if (grown == true) {
			g.getSoundManager().PlaySound(SoundEffect.HURT);
			setGrown(false);
		}
		else {
			playerRespawn();
		}
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getPlayerLives() {
		return playerLives;
	}

	public void setPlayerLives(int playerLives) {
		this.playerLives = playerLives;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}

	public int getPlayerCoins() {
		return playerCoins;
	}

	public void setPlayerCoins(int playerCoins) {
		this.playerCoins = playerCoins;
	}

	public int getLastTimeDigit() {
		return lastTimeDigit;
	}

	public void setLastTimeDigit(int lastTimeDigit) {
		this.lastTimeDigit = lastTimeDigit;
	}

	public boolean isSprinting() {
		return sprinting;
	}

	public void setSprinting(boolean sprinting) {
		if(moving == true && sprinting == true) {
			return;
		}
		else {
			this.sprinting = sprinting;
		}
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	public void setLevelDone(boolean levelDone) {
		this.levelDone = levelDone;
	}
	
	public boolean isFireman() {
		return fireMan;
	}
	
	public void setFireman(boolean fireMan) {
		this.fireMan = fireMan;
	}
	
	
}
