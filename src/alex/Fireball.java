package alex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Fireball {
	private BufferedImage imageOne;
	private BufferedImage imageTwo;
	private BufferedImage imageThree;
	private BufferedImage imageFour;
	private BufferedImage deathImage;
	private BufferedImage currentImage;
	private Rectangle boundingBox;
	private Game g;
	private int x;
	private int y;
	private int currentCol = (x / Tile.TILE_SIZE);
	private int currentRow = (y / Tile.TILE_SIZE);
	private int speed;
	private String direction;
	private boolean hidden;
	private boolean isFalling;
	private boolean jumping;
	private int jumpCount;
	private int timer;
	static final int JUMP_THRESH = 15;
	private Material matUnder;
	private boolean setToExpire = false;
	private int counter = 0;
	private int totalJumps;
	private boolean fromBoss;
	private boolean fireWheel;
	
	//Constructors
	
	Fireball(Game game, int x, int y, String direction) {
		this(game, x, y, direction, false, false);
	}

	Fireball(Game game, int x, int y, String direction, boolean fromBoss, boolean fireWheel) {
		g = game;
		this.x = x;
		this.y = y;
		this.fromBoss = fromBoss;
		isFalling = true;
		this.fireWheel = fireWheel;
		if(fromBoss == false && fireWheel == false) {
			if(g.GetThePlayer().isSprinting() == true) this.speed = Player.SPRINT_DISPLACEMENT + 1;
			else this.speed = Player.MOVE_DISPLACEMENT + 1;
			
			loadInfo(game);
		}
		else if(fromBoss == true) {
			this.speed = 5;
			loadInfoShot(game);
		}
		else if(fireWheel == true) {
			loadInfo(game);
		}
		else {
			System.out.println("Error: Fireball is not a valid type.");
		}
		
		this.direction = direction;
		hidden = false;
	}
	
	Fireball(Game game, int x, int y, boolean fireWheel) {
		this(game, x, y, "east", false, fireWheel);
	}
	
	//Loading / Main functions
	
	protected void loadInfoShot(Game game) {
		boundingBox = new Rectangle(x,y,30,16);
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		imageOne = ss.grabImage(11, 6);
		imageTwo = ss.grabImage(11, 6);
		imageThree = ss.grabImage(13, 6);
		imageFour = ss.grabImage(13, 6);
		deathImage = ss.grabImage(7, 11);
		currentImage = imageOne;
	}
	
	protected void loadInfo(Game game) {
		boundingBox = new Rectangle(x,y,10,10);
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		imageOne = ss.grabImage(11, 5);
		imageTwo = ss.grabImage(12, 5);
		imageThree = ss.grabImage(13, 5);
		imageFour = ss.grabImage(14, 5);
		deathImage = ss.grabImage(7, 11);
		currentImage = imageOne;
	}

	public void tick(Game g) {
		if(hidden == false) {
			if(setToExpire == false) {
				
				if(fireWheel == true) {
					runAnimation();
					updateBoundingBox();
					return;
				}
				
				if(direction.equalsIgnoreCase("east")) x += speed;
				else if(direction.equalsIgnoreCase("west")) x -= speed;
				
				if(matUnder != Material.AIR) hop();
				
				if(x <= 4 || x >= World.worldSizeX - 2 | y > World.SCREEN_SIZE_Y - 48) hidden = true;
				
				runAnimation();
				checkCollisions(g);
				checkHopState();
				checkFallingState();
				updateBoundingBox();
				UpdateUnderMaterial();
				
				if(matUnder.GetDoesDamage() == true) hidden = true; //Kill the fireball if on illegal block
				
				if(totalJumps > JUMP_THRESH) setToExpire = true; //After the total jumps passes the jump thresh, kill the fireball
			}
			else {
				counter++;
				if(counter > 10) hidden = true;
				runAnimation();
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
	
	public void runAnimation() {
		if(setToExpire == false) {
			timer++;
			if(timer <= 5) currentImage = imageOne;
			else if(timer <= 10) currentImage = imageTwo;
			else if(timer <= 15) currentImage = imageThree;
			else if(timer <= 20) currentImage = imageFour;
			else timer = 0;
		}
		else currentImage = deathImage;
	}
	
	public void updateBoundingBox() {
		if(fromBoss == false) boundingBox.setLocation(this.x + 2, this.y + 20);
		else boundingBox.setLocation(this.x + 4, this.y + 8);
		currentCol = (int) (x /Tile.TILE_SIZE);
		currentRow = (int) (y /Tile.TILE_SIZE);
	}
	
	public void UpdateUnderMaterial() {
		if(currentRow + 1 < World.ROWS && currentRow + 1 > 0)
			matUnder = World.tiledMap[currentRow + 1][currentCol].getMaterial();
	}
	
	public void checkCollisions(Game g) {
		//If the last direction was right check for the left side
		int bottomY = (int)(boundingBox.getMaxY());
		
		if(direction.equalsIgnoreCase("east")) {	
			int bottomX = (int) boundingBox.getMinX(); //Left side of bounding box

			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) + 1;

			if(frontTileCol < World.cols && frontTileRow < World.ROWS) {
				//If the tile in front of the fireball is a solid block
				if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
					//If the fireballs bounding box intersects that blocks bounding block
					if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
						killFireball();
					}
				}

				//To detect if the fireball is in a block
				if(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == true) {
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())) {
						killFireball();
					}
				}
			}
		}
		//else the last direction was left
		else if(direction.equalsIgnoreCase("west")){
			int bottomX = (int) boundingBox.getMaxX(); //Right side of bounding box

			int frontTileRow = (bottomY - 1) / Tile.TILE_SIZE;
			int frontTileCol = (bottomX / Tile.TILE_SIZE) - 1;

			if(frontTileCol >= 0 && frontTileRow >= 0) {
				//If the tile in front of the fireball is a solid block
				if(World.tiledMap[frontTileRow][frontTileCol].getMaterial().GetSolid() == true) {
					//If the fireball's bounding box intersects that blocks bounding block
					if(boundingBox.intersects(World.tiledMap[frontTileRow][frontTileCol].getBoundingBox())) {
						killFireball();
					}
				}

				//To detect if the fireball is in a block
				if(World.tiledMap[currentRow][currentCol].getMaterial().GetSolid() == true) {
					if(boundingBox.intersects(World.tiledMap[currentRow][currentCol].getBoundingBox())) {
						killFireball();
					}
				}

			}
		}
		
		int lowerRow = (int)((boundingBox.getMaxY() - 1) / Tile.TILE_SIZE); //Row position of the cell BELOW the fireball's feet (in the tiled map)
		int lowerLeftCorner = (int)((boundingBox.getMinX())/ Tile.TILE_SIZE); //Tile position relative to the upper-left corner of the fireball's bounding box
		int lowerRightCorner = (int)((boundingBox.getMaxX()) / Tile.TILE_SIZE); //Tile position relative to the upper-right corner of the fireball's bounding box
		
		if(currentRow >= 0) {
			//Upper left
			if(World.tiledMap[lowerRow][lowerLeftCorner].getMaterial().GetSolid() == true) //If that position on the tile map is a block then test to see if the fireball intersects it
				if(World.tiledMap[lowerRow][lowerLeftCorner].getBoundingBox().intersects(boundingBox)) y = currentRow * Tile.TILE_SIZE;
			
			if(World.tiledMap[lowerRow][lowerRightCorner].getMaterial().GetSolid() == true)
				if(World.tiledMap[lowerRow][lowerRightCorner].getBoundingBox().intersects(boundingBox)) y = currentRow * Tile.TILE_SIZE;
			
			updateBoundingBox();
		}
	}
	
	public void checkFallingState() {
		
		if(fromBoss == true | jumping == true) return; //Shots from the boss do not fall / do not run if mid jump
		
		if(isFalling == true) {
			y += speed - 1;
			currentRow = y / Tile.TILE_SIZE;
			updateBoundingBox();
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
	
	public void checkHopState() {
		if(jumping == true) {
			if(jumpCount < (JUMP_THRESH - totalJumps)) {
				y -= speed;
				updateBoundingBox();
			}
			
			jumpCount++;
			
			if(jumpCount >=  (JUMP_THRESH - totalJumps)) {
				jumping = false;
				jumpCount = 0;
				isFalling = true;
			}
		}
	}
	
	public void hop() {
		if(this.jumping == false && this.isFalling == false) {
			this.jumping = true;
			this.jumpCount = 0;
			totalJumps++;
		}
	}
	
	public void killFireball() {
		setToExpire = true;
		g.getSoundManager().PlaySound(SoundEffect.BUMP);
	}
	
	public void changeDirection(double x, double y) {
		this.x = (int) Math.round(x);
		this.y = (int) Math.round(y);
	}

	//Getters and Setters
	public void setHidden(boolean hidden) { this.hidden = hidden; }
	public boolean isHidden() { return hidden; }
	public int getCurrentRow() { return currentRow; }
	public int getCurrentCol() { return currentCol; }
	public Rectangle getBoundingBox() { return boundingBox; }
	public boolean getFromBoss() { return fromBoss; }
	public double getRawX() { return x; }
	public double getRawY() { return y; }
	
	}
