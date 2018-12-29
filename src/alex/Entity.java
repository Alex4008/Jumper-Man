package alex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Entity {

	//Ok let me explain: When I first wrote this class I did not think that I would ever have as many entities as I ended up having. So. As I got more and more
	//This class became more and more disorganized and gross to look at. As it stands now. I do not think that I will rewrite it, although I am fully aware that this implementation is AWFUL.
	//Thanks and please forgive me. - A noob game dev.
	
	private static final int DISPLACEMENT = 1;
	public static final int ENTITY_SIZE_X = 19;
	public static final int ENTITY_SIZE_Y = 27;
	public static final int BOSS_SIZE_X = 32;
	public static final int BOSS_SIZE_Y = 32;
	public static final int JUMP_THRESH = 24;
	public static final int BOSS_JUMP_THRESH = 35;
	public static final int SPEED = 1;
	private static final int GREEN_MAX_HITS = 8;
	private static final int RED_MAX_HITS = 12;
	
	Game g;
	private int x;
	private int y;
	private BufferedImage imageOneRight;
	private BufferedImage imageTwoRight;
	private BufferedImage imageOneLeft;
	private BufferedImage imageTwoLeft;
	private BufferedImage imageThreeLeft;
	private BufferedImage imageFourLeft;
	private BufferedImage currentImage;
	private Rectangle boundingBox;
	private int currentRow;
	private int currentCol;
	private EntityType eType;
	private int ticksSinceFall;
	private Material matUnder;
	private boolean isFalling;
	private boolean moving;
	private boolean jumping;
	private String direction = "east";
	private boolean render;
	private int animationTimer;
	private int speedAddition = 0; //For shells
	private int shellHits;
	private boolean nearLeftEdge = false;
	private boolean nearRightEdge = false;
	private int jumpCount = 0;
	private Random rnd;
	public boolean bossAttack = false;
	private boolean setToAttack = false;
	private int numberHits = 0;
	private int startingRow;
	private int counter;
	
	//Constructors
	
	public Entity(EntityType eType, Game game, int x, int y, String direction) {
		g = game;
		this.x = x;
		this.y = y;
		this.startingRow = y / Tile.TILE_SIZE;
		render = true;
		this.direction = direction;
		ticksSinceFall = 0;
		isFalling = false;
		setMoving(false);
		jumping = false;
		this.eType = eType;
		animationTimer = 0;
		shellHits = 0;
		rnd = new Random();
		counter = 0;
		init();
		loadInfo(game);
	}
	
	public Entity(EntityType eType, Game game, int x, int y) {
		this(eType, game, x, y, "east");
	}

	//Loading main functions
	
	private void init() {
		if(eType == EntityType.BULLET) speedAddition = 3; //For faster bullets
		
		if(eType == EntityType.RED_TURTLE || eType == EntityType.JUMP_RED_TURTLE) speedAddition = 1; //Red turtles are faster
		
		moving = true;
		
		if(eType == EntityType.GREEN_SHELL || eType == EntityType.RED_SHELL) moving = false; //Shells do not move at start.
		
		if(eType == EntityType.BOSS) boundingBox = new Rectangle(x, y, BOSS_SIZE_X, BOSS_SIZE_Y);
		else boundingBox = new Rectangle(x, y, ENTITY_SIZE_X, ENTITY_SIZE_Y);
	}
	
	private void loadInfo(Game game) {
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		if(eType.equals(EntityType.GREEN_TURTLE)) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(13, 1);
				imageTwoLeft = ss.grabImage(13, 2);
				imageOneRight = ss.grabImage(13, 3);
				imageTwoRight = ss.grabImage(13, 4);
			}
			else {
				imageOneLeft = ss.grabImage(11, 1);
				imageTwoLeft = ss.grabImage(11, 2);
				imageOneRight = ss.grabImage(11, 3);
				imageTwoRight = ss.grabImage(11, 4);
			}
		}
		else if(eType.equals(EntityType.RED_TURTLE)) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(18, 5);
				imageTwoLeft = ss.grabImage(18, 6);
				imageOneRight = ss.grabImage(18, 7);
				imageTwoRight = ss.grabImage(18, 8);
			}
			else {
				imageOneLeft = ss.grabImage(17, 5);
				imageTwoLeft = ss.grabImage(17, 6);
				imageOneRight = ss.grabImage(17, 7);
				imageTwoRight = ss.grabImage(17, 8);
			}
		}
		else if(eType.equals(EntityType.JUMP_GREEN_TURTLE)) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(18, 1);
				imageTwoLeft = ss.grabImage(18, 2);
				imageOneRight = ss.grabImage(18, 3);
				imageTwoRight = ss.grabImage(18, 4);
			}
			else {
				imageOneLeft = ss.grabImage(17, 1);
				imageTwoLeft = ss.grabImage(17, 2);
				imageOneRight = ss.grabImage(17, 3);
				imageTwoRight = ss.grabImage(17, 4);
			}
		}
		else if(eType.equals(EntityType.JUMP_RED_TURTLE)) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(19, 5);
				imageTwoLeft = ss.grabImage(19, 6);
				imageOneRight = ss.grabImage(19, 7);
				imageTwoRight = ss.grabImage(19, 8);
			}
			else {
				imageOneLeft = ss.grabImage(19, 1);
				imageTwoLeft = ss.grabImage(19, 2);
				imageOneRight = ss.grabImage(19, 3);
				imageTwoRight = ss.grabImage(19, 4);
			}
		}
		else if(eType == EntityType.GREEN_SHELL) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(14, 4);
				imageTwoLeft = ss.grabImage(14, 4);
				imageOneRight = ss.grabImage(14, 4);
				imageTwoRight = ss.grabImage(14, 4);
			}
			else {
				imageOneLeft = ss.grabImage(12, 4);
				imageTwoLeft = ss.grabImage(12, 4);
				imageOneRight = ss.grabImage(12, 4);
				imageTwoRight = ss.grabImage(12, 4);
			}
		}
		else if(eType == EntityType.RED_SHELL) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(20, 4);
				imageTwoLeft = ss.grabImage(20, 4);
				imageOneRight = ss.grabImage(20, 4);
				imageTwoRight = ss.grabImage(20, 4);
			}
			else {
				imageOneLeft = ss.grabImage(20, 2);
				imageTwoLeft = ss.grabImage(20, 2);
				imageOneRight = ss.grabImage(20, 2);
				imageTwoRight = ss.grabImage(20, 2);
			}
		}
		else if(eType == EntityType.PLANT) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(16, 1);
				imageTwoLeft = ss.grabImage(16, 2);
			}
			else {
				imageOneLeft = ss.grabImage(15, 1);
				imageTwoLeft = ss.grabImage(15, 2);
			}
		}
		else if(eType == EntityType.BOSS) {
			imageOneLeft = ss.grabImage(1, 15, 64, 64);
			imageTwoLeft = ss.grabImage(3, 15, 64, 64);
			imageThreeLeft = ss.grabImage(5, 15, 64, 64);
			imageFourLeft = ss.grabImage(7, 15, 64, 64);
		}
		else if(eType == EntityType.GOOMBA) {
			if(Game.currentLevelType == LevelType.UNDERWORLD) {
				imageOneLeft = ss.grabImage(14, 1);
				imageTwoLeft = ss.grabImage(14, 1);
				imageOneRight = ss.grabImage(14, 2);
				imageTwoRight = ss.grabImage(14, 2);
			}
			else {
				imageOneLeft = ss.grabImage(12, 1);
				imageTwoLeft = ss.grabImage(12, 1);
				imageOneRight = ss.grabImage(12, 2);
				imageTwoRight = ss.grabImage(12, 2);
			}
		}
		else if(eType == EntityType.LAVA_BALL) {
			imageOneLeft = ss.grabImage(20, 5);
			imageTwoLeft = ss.grabImage(20, 6);
			imageOneRight = ss.grabImage(20, 5);
			imageTwoRight = ss.grabImage(20, 6);
		}
		else {
			imageOneLeft = ss.grabImage(eType.GetImageSpot(), 1);
			imageTwoLeft = ss.grabImage(eType.GetImageSpot(), 1);
			imageOneRight = ss.grabImage(eType.GetImageSpot(), 2);
			imageTwoRight = ss.grabImage(eType.GetImageSpot(), 2);
		}
	}
	
	public void tick(Game g) {
		if(render == true) {
			runAnimations();
			UpdateUnderMaterial();
			if(matUnder.GetDoesDamage() == true && eType != EntityType.LAVA_BALL) render = false;
			
			checkCollisions();
			updateBoundingBoxes();
			checkEntityCollisions(g);
			checkFireballCollisions(g);
			checkJumpState();
			checkFallingState();
			
			if(eType != EntityType.BULLET && eType != EntityType.GREEN_SHELL && eType != EntityType.RED_SHELL && eType != EntityType.BOSS && eType != EntityType.JUMP_GREEN_TURTLE && eType != EntityType.JUMP_RED_TURTLE && eType != EntityType.LAVA_BALL) {
				checkNearEdge();
				if(nearLeftEdge == true && nearRightEdge == true) {
					moving = false;
				}
				else if(nearLeftEdge == true) {
					direction = "east";
					moving = true;
				}
				else if(nearRightEdge == true) {
					direction = "west";
					moving = true;
				}
			}
			else if(eType == EntityType.BOSS) {
				if(jumping == false && isFalling == false) {
					checkNearEdgeBoss();
					if(nearLeftEdge == true) {
						direction = "east";
						moving = true;
					}
					else if(nearRightEdge == true) {
						direction = "west";
						moving = true;
					}
				}
				
				int playerCol = g.GetThePlayer().getCurrentCol();
				
				if(Math.abs(playerCol - currentCol) <= 24) { //Only shoot if the player is within X tiles.
					if(rnd.nextInt(400) < 5 && jumping == false && isFalling == false && direction.equalsIgnoreCase("west")) {
						jumping = true;
					}
					
					if(rnd.nextInt(550) < 5) {
						setToAttack = true;
					}
				}
			}
				
			if(moving == true && eType != EntityType.PLANT && eType != EntityType.LAVA_BALL) {
				int pixelsToMove = 0;
				if(direction.equalsIgnoreCase("east")) {
					pixelsToMove = SPEED + speedAddition;
					
					while(World.tiledMap[currentRow][currentCol + 1].getMaterial().GetSolid() == false && pixelsToMove > 0) {
						x+= 1;
						updateBoundingBoxes();
						pixelsToMove--;
					}
					
					if(pixelsToMove > 0 && eType != EntityType.BOSS && eType != EntityType.GREEN_SHELL && eType != EntityType.RED_SHELL) flipDirection();
					if(pixelsToMove > 0 && (eType == EntityType.GREEN_SHELL || eType == EntityType.RED_SHELL)) bounceShell();
				}
				else if(direction.equalsIgnoreCase("west")) {
					pixelsToMove = SPEED + speedAddition;
					
					while(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == false && pixelsToMove > 0) {
						x-= 1;
						updateBoundingBoxes();
						pixelsToMove--;
					}
					
					if(pixelsToMove > 0 && eType != EntityType.BOSS && eType != EntityType.GREEN_SHELL && eType != EntityType.RED_SHELL) flipDirection();
					if(pixelsToMove > 0 && (eType == EntityType.GREEN_SHELL || eType == EntityType.RED_SHELL)) bounceShell();
				}
			}
			
			if(eType == EntityType.JUMP_GREEN_TURTLE || eType == EntityType.JUMP_RED_TURTLE) {
				if(jumping == false && isFalling == false) jumping = true;
			}
			else if(eType == EntityType.LAVA_BALL) {
				counter++;
				if(counter > 180) {
					counter = 0;
					if(jumping == false) {
						y -= 16;
						jumping = true;
						moving = true;
					}
				}
			}
			
			//For the bouncing blocks
			if(World.tiledMap[currentRow + 1][currentCol].isBouncing() == true) {
				flipDirection();
				
				if(World.tiledMap[currentRow + 1][currentCol].isBouncing()) { //If the block below an entity is bouncing, then they die
					killEntity();
					g.GetThePlayer().addPoints(eType);
				}
			}
			
			if(x <= 4) {
				if(eType == EntityType.BULLET) render = false;
				x = x + (SPEED + speedAddition);
				g.getSoundManager().PlaySound(SoundEffect.BUMP);
				flipDirection();
			}
			else if(x >= World.worldSizeX - 64) {
				if(eType == EntityType.BULLET) render = false;
				x = x - (SPEED + speedAddition);
				g.getSoundManager().PlaySound(SoundEffect.BUMP);
				flipDirection();
			}
			
			if(eType != EntityType.LAVA_BALL) {
				if(y > World.SCREEN_SIZE_Y - 48) { //To prevent the game from crashing if they fall off the screen
					render = false;
					return;
				}
			}
			else {
				if(y > World.SCREEN_SIZE_Y - 40) { //To prevent the game from crashing if they fall off the screen
					moving = false;
					isFalling = false;
					jumping = false;
					return;
				}
			}
			
			if(eType != EntityType.BOSS) {
				if(animationTimer > 60) {
					animationTimer = 0;
				}
				
				animationTimer++;
			}
			else {
				if(animationTimer > 90) {
					animationTimer = 0;
				}
				
				animationTimer++;
			}
			
			if(y <= 0) {
				y = 0;
			}
		}	
	}
	
	public void render(Graphics g) {
		if(render == true) {
			if(eType != EntityType.BOSS) g.drawImage(currentImage, x, y, null);
			else g.drawImage(currentImage, x - 16, y - 32, null);
			if(Game.hitBoxes == true) {
				g.setColor(Color.DARK_GRAY);
				g.drawRect(boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
			}
		}
	}
	
	//Other methods
	
	private void checkNearEdge() {
		if(currentRow < World.ROWS && currentCol + 1 < World.cols) {
			if(World.tiledMap[currentRow + 1][currentCol].getMaterial().GetSolid() == false) nearLeftEdge = true;
			else nearLeftEdge = false;
				
			if(World.tiledMap[currentRow + 1][currentCol + 1].getMaterial().GetSolid() == false && matUnder.GetSolid() == true) nearRightEdge = true;
			else nearRightEdge = false;
		}
		else {
			nearLeftEdge = false;
			nearRightEdge = false;
		}
	}

	private void checkNearEdgeBoss() {
		if(World.tiledMap[currentRow + 1][currentCol].getMaterial() != Material.BRIDGE) nearLeftEdge = true;
		else nearLeftEdge = false;
		
		if(World.tiledMap[currentRow + 1][currentCol + 1].getMaterial() != Material.BRIDGE) nearRightEdge = true;
		else nearRightEdge = false;
	}
	
	public void runAnimations() {
		if(eType == EntityType.GREEN_TURTLE || eType == EntityType.RED_TURTLE) {
			if(animationTimer < 30) {
				if(direction == "east") currentImage = imageOneRight;
				else currentImage = imageOneLeft;
			}
			else {
				if(direction == "east") currentImage = imageTwoRight;
				else currentImage = imageTwoLeft;
			}
		}
		else if(eType == EntityType.JUMP_GREEN_TURTLE || eType == EntityType.JUMP_RED_TURTLE) {
			if(animationTimer < 30) {
				if(direction == "east") currentImage = imageOneRight;
				else currentImage = imageOneLeft;
			}
			else {
				if(direction == "east") currentImage = imageTwoRight;
				else currentImage = imageTwoLeft;
			}
		}
		else if(eType == EntityType.GREEN_SHELL || eType == EntityType.RED_SHELL || eType == EntityType.BULLET) {
			if(direction == "east") currentImage = imageOneRight;
			else currentImage = imageOneLeft;
		}
		else if(eType == EntityType.PLANT) {
			if(animationTimer < 30) currentImage = imageOneLeft;
			else currentImage = imageTwoLeft;
		}
		else if(eType == EntityType.LAVA_BALL) {
			if(jumping == true) currentImage = imageOneLeft;
			else currentImage = imageTwoLeft;
		}
		else if(eType == EntityType.BOSS) {
			if(animationTimer <= 30) currentImage = imageTwoLeft;
			else if(animationTimer <= 60){
				if(setToAttack == true) {
					bossAttack();
					setToAttack = false;
				}
				if(bossAttack = true) currentImage = imageOneLeft;
				else currentImage = imageThreeLeft;
				
				if(animationTimer == 60) bossAttack = false;
				
			}
			else if(animationTimer <= 90) currentImage = imageFourLeft;
		}
		else {
			if(animationTimer < 30) currentImage = imageOneRight;
			else currentImage = imageTwoLeft;
		}
	}
	
	public void collisionsJumpingCheck(Game g, int row, int corner) {
		if(World.tiledMap[row][corner].getMaterial().GetSolid() == true || World.tiledMap[row][corner].getMaterial() == Material.HIDDEN_BLOCK) { //If that position on the tile map is a block then test to see if the entity intersects it
			if(World.tiledMap[row][corner].getBoundingBox().intersects(boundingBox)) {

				//Push the entity out
				while(World.tiledMap[row][corner].getBoundingBox().intersects(boundingBox) == true) {
					y+= 1;
					updateBoundingBoxes();
				}
				
				jumping = false;
				jumpCount = 0;
				isFalling = true;
				ticksSinceFall = 20;
				return;
			}
		}
	}
	
	public void checkCollisions() {
		
		if(eType == EntityType.LAVA_BALL) return; //Lava balls ignore collisions.
		
		int bottomY = (int)(boundingBox.getMaxY());
		
		if(jumping == true) { //If the entity was jumping
			int upperRow = (int)((boundingBox.getMinY()-1) / Tile.TILE_SIZE); //Row position of the cell above the character's head (in the tiled map)
			int upperLeftCorner = (int)(boundingBox.getMinX() / Tile.TILE_SIZE); //Tile position relative to the upper-left corner of the character's bounding box
			int upperRightCorner = (int)((boundingBox.getMaxX()) / Tile.TILE_SIZE); //Tile position relative to the upper-right corner of the character's bounding box
			
			if(currentRow >= 0) {
				collisionsJumpingCheck(g, upperRow, upperLeftCorner);
				collisionsJumpingCheck(g, upperRow, upperRightCorner);
			}
		} 

		//If the last direction was right check for the left side
		if(direction.equalsIgnoreCase("east")) {	
			int bottomX = (int) boundingBox.getMinX(); //Left side of bounding box
			
			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) + 1;
			
			if(frontTileCol < World.cols) {
				//If the tile in front of the entity is a solid block
				if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
					//If the entity bounding box intersects that blocks bounding block
					if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
						x -= SPEED + speedAddition;
						updateBoundingBoxes();
						if(eType == EntityType.GREEN_SHELL || eType == EntityType.RED_SHELL) {
							bounceShell();
						}
						else if(eType == EntityType.BULLET) {
							setAlive(false);
						}
						else {
							direction = "west"; //Switch the direction to west
						}
					}
				}
				
				//To detect if the entity is in a block
				if(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == true) {
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())) {
						direction = "west"; //Switch the direction to west
						x -= SPEED + speedAddition;
						updateBoundingBoxes();
						if(eType == EntityType.BULLET) setAlive(false);
					}
				}
			}
		}
		//else the last direction was left
		else if(direction.equalsIgnoreCase("west")){
			int bottomX = (int) boundingBox.getMaxX(); //Right side of bounding box
			
			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) - 1;
			
			if(frontTileCol >= 0) {
				//If the tile in front of the entity is a solid block
				if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
					//If the entity bounding box intersects that blocks bounding block
					if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
						x += SPEED + speedAddition;
						updateBoundingBoxes();
						if(eType == EntityType.GREEN_SHELL || eType == EntityType.RED_SHELL) {
							bounceShell();
						}
						else if(eType == EntityType.BULLET) {
							g.getSoundManager().PlaySound(SoundEffect.BUMP);
							setAlive(false);
						}
						else {
							direction = "east"; //Switch the direction to east
						}
					}
				}
				
				//To detect if the entity is in a block
				if(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == true) {
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())) {
						direction = "east"; //Switch the direction to east
						x += SPEED + speedAddition;
						updateBoundingBoxes();
						if(eType == EntityType.BULLET) setAlive(false);
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
		
		if(currentRow >= 0 && currentRow < World.ROWS && currentCol >= 0 && currentCol < World.cols) {
			//Upper left
			if(World.tiledMap[lowerRow][lowerLeftCorner].getMaterial().GetSolid() == true) { //If that position on the tile map is a block then test to see if the entity intersects it
				if(World.tiledMap[lowerRow][lowerLeftCorner].getBoundingBox().intersects(boundingBox)) {
					y = currentRow * Tile.TILE_SIZE;
					updateBoundingBoxes();
				}
			}
			
			if(World.tiledMap[lowerRow][lowerRightCorner].getMaterial().GetSolid() == true) {
				if(World.tiledMap[lowerRow][lowerRightCorner].getBoundingBox().intersects(boundingBox)) {
					y = currentRow * Tile.TILE_SIZE;
					updateBoundingBoxes();
				}
			}	
		}	
	}
	
	public void checkJumpState() {
		if(jumping == true) {	
			if(y <= 16) {
				jumping = false;
				jumpCount = 0;
				isFalling = true;
				ticksSinceFall = 20;
			}
			
			if(eType == EntityType.BOSS) {
				if(jumpCount < BOSS_JUMP_THRESH) {
					y -= (BOSS_JUMP_THRESH - jumpCount) / 4;
					updateBoundingBoxes();
				}
				
				jumpCount++;
				
				if(jumpCount >= BOSS_JUMP_THRESH) {
					jumping = false;
					jumpCount = 0;
					isFalling = true;
					ticksSinceFall = 0;
				}
			}
			else if(eType != EntityType.LAVA_BALL){
				if(jumpCount < JUMP_THRESH) {
					y -= (JUMP_THRESH - jumpCount) / 4;
					updateBoundingBoxes();
				}
				
				jumpCount++;
				
				if(jumpCount >= JUMP_THRESH) {
					jumping = false;
					jumpCount = 0;
					isFalling = true;
					ticksSinceFall = 0;
				}
			}
			else {
				if(currentRow > startingRow) {
					y -= (jumpCount / 2);
					updateBoundingBoxes();
				}
				
				jumpCount++;
				
				if(currentRow <= startingRow) {
					jumping = false;
					jumpCount = 0;
					isFalling = true;
					ticksSinceFall = 0;
				}
			}
		}
	}
	
	public void checkFallingState() {
		if(eType == EntityType.BULLET) return; //Bullets dont fall
		if(boundingBox.getMaxY() / Tile.TILE_SIZE >= World.ROWS) render = false;
		if(jumping == true || (moving == false && (eType != EntityType.GREEN_SHELL || eType != EntityType.RED_SHELL))) return; //Do not run this method if jumping is in progress
	
		if(isFalling == true) {
			y += 0 + (ticksSinceFall / 7);
			currentRow = y / Tile.TILE_SIZE;
			updateBoundingBoxes();
			
			ticksSinceFall++;
		}

		int underTileXL = ((int) boundingBox.getMinX() + 1) / Tile.TILE_SIZE;
		int underTileXR = ((int) boundingBox.getMaxX() - 1) / Tile.TILE_SIZE;
		
		if(currentRow + 1 >= World.ROWS || underTileXR >= World.cols) return;
		
		if((World.tiledMap[currentRow + 1][underTileXR].getMaterial().GetSolid() == false) && (World.tiledMap[currentRow + 1][underTileXL].getMaterial().GetSolid() == false)) {
			isFalling = true;
			return;
		}
		
		if(eType == EntityType.LAVA_BALL && (World.tiledMap[currentRow + 1][underTileXR].getMaterial() == Material.BRIDGE) && (World.tiledMap[currentRow + 1][underTileXL].getMaterial() == Material.BRIDGE)) {
			isFalling = true;
			return;
		}
			
		if(eType == EntityType.BOSS && isFalling == true) flipDirection();
		
		isFalling = false;
	}
	
	public void bossAttack() {
		this.g.getFireballList().add(new Fireball(this.g, x - 16, y - 24, "west", true, false));
		g.getSoundManager().PlaySound(SoundEffect.BOSS_ATTACK);
		bossAttack = true;
	}
	
	public void checkFireballCollisions(Game g) {
		for(Fireball b : g.getFireballList()) {
			if(b.isHidden() == false && b.getFromBoss() == false) {
				if(boundingBox.intersects(b.getBoundingBox())) {
					numberHits++;
					if(numberHits >= 8 || eType != EntityType.BOSS) {
						killEntity();
						g.getSoundManager().PlaySound(SoundEffect.KICK);
						g.GetThePlayer().addPoints(this.eType);
						b.killFireball();
						b.tick(g);
						b.setHidden(true);
						return;
					}
					else {
						b.killFireball();
						b.tick(g);
						b.setHidden(true);
						return;
					}
				}
			}
		}
	}
	
	public void updateBoundingBoxes() {
		if(eType != EntityType.BOSS) {
			boundingBox.setLocation(this.x + 6, this.y + 4);
			currentCol = (int) (x /Tile.TILE_SIZE);
			currentRow = (int) (y /Tile.TILE_SIZE);
		}
		else {
			boundingBox.setLocation(this.x, this.y);
			currentCol = (int) (x /Tile.TILE_SIZE);
			currentRow = (int) (y /Tile.TILE_SIZE);
		}
	}
	
	public void checkEntityCollisions(Game g) {
		if(eType != EntityType.GREEN_SHELL && eType != EntityType.RED_SHELL) return;
		for(Entity e : g.getEntityList()) {
			if(e != this) {
				if(boundingBox.intersects(e.getBoundingBox()) && e.isAlive()) {
					if(direction == "east") {
						x -= DISPLACEMENT;
						updateBoundingBoxes();
					}
					else if(direction == "west") {
						x += DISPLACEMENT;
						updateBoundingBoxes();
					}
					
					if(e.getEntityType() == EntityType.GREEN_SHELL || e.getEntityType() == EntityType.RED_SHELL) {
						g.getSoundManager().PlaySound(SoundEffect.KICK);
						e.bounceShell();
						bounceShell();
					}
					else {
						if(e.getEntityType() == EntityType.LAVA_BALL) {
							killEntity();
						}
						else {
							g.getSoundManager().PlaySound(SoundEffect.KICK);
							e.killEntity();
							bounceShell();
						}
					}				
				}
			}
		}
		
	}
	
	public void playerHitShell(int direction) {
		if(moving == false) {
			if(direction == KeyEvent.VK_RIGHT) {
				moving = true;
				this.direction = "east";
				speedAddition += 2;
				x += (DISPLACEMENT + speedAddition) * 2;
				updateBoundingBoxes();
				g.getSoundManager().PlaySound(SoundEffect.KICK);
			}
			else if(direction == KeyEvent.VK_LEFT) {
				moving = true;
				this.direction = "west";
				speedAddition += 2;
				x -= (DISPLACEMENT + speedAddition) * 2;
				updateBoundingBoxes();
				g.getSoundManager().PlaySound(SoundEffect.KICK);
			}
		}
		else {
			if(direction == KeyEvent.VK_RIGHT) {
				x += DISPLACEMENT;
				updateBoundingBoxes();
				bounceShell();
			}
			else if(direction == KeyEvent.VK_LEFT) {
				x -= DISPLACEMENT;
				updateBoundingBoxes();
				bounceShell();
			}
		}
	}
	
	public void bounceShell() {
		moving = true;
		g.getSoundManager().PlaySound(SoundEffect.BUMP);
		flipDirection();
		increaseShellHits();
		increaseSpeed();
		if(eType == EntityType.GREEN_SHELL) {
			if(shellHits > GREEN_MAX_HITS) {
				killEntity();
				g.getSoundManager().PlaySound(SoundEffect.BREAK_BLOCK);
			}
		}
		else if(eType == EntityType.RED_SHELL){
			if(shellHits > RED_MAX_HITS) {
				killEntity();
				g.getSoundManager().PlaySound(SoundEffect.BREAK_BLOCK);
			}
		}
		else {
			System.out.println("Error: Trying to bounce non shell entity.");
		}
	}
	
	public void killEntity() {
		render = false;
	}
	
	public void UpdateUnderMaterial() {
		matUnder = World.tiledMap[currentRow + 1][currentCol].getMaterial();
	}
	
	public void flipDirection() {
		
		if(eType == EntityType.BULLET) killEntity();
		
		if(direction == "east") direction = "west";
		else direction = "east";
	}
	
	public void increaseSpeed() {
		if(shellHits % 2 == 1) speedAddition++;
	}

	//Getters and Setters
	public Rectangle getBoundingBox() { return boundingBox; }
	public boolean isAlive() { return render; }
	public void setAlive(boolean status) { render = status; }
	public int getCurrentCol() { return currentCol; }
	public int getCurrentRow() { return currentRow; }
	public boolean isMoving() { return moving; }
	public void setMoving(boolean moving) { this.moving = moving; }
	public EntityType getEntityType() { return eType; }
	public int getX() { return x; }
	public int getY() { return y; }
	public int getShellHits() { return shellHits; }
	public void setShellHits(int shellHits) { this.shellHits = shellHits; }
	public void increaseShellHits() { this.shellHits++; }
	
}
