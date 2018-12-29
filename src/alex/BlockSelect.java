package alex;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

public class BlockSelect {

	//Hey. Smart guy. Use an array list next time. It'll be easier. I promise. - Guy who realizes hes not so smart.
	//Update: You did good switching it over buddy. 
	
	//private final int WIDTH = (int) ((int) Tile.TILE_SIZE * 16.5);
	//private final int HEIGHT = (int) ((int) Tile.TILE_SIZE * 4.5);
	private final int WIDTH = (int) ((int) Tile.TILE_SIZE * 20);
	private final int HEIGHT = (int) ((int) Tile.TILE_SIZE * 3);
	private Rectangle selectBoundingBox;

	private boolean spawnPointPlaced = false;
	private boolean polePlaced = false;
	private boolean bossPlaced = false;
	private boolean keyPlaced  = false;
	
	private int spawnPointX;
	private int spawnPointY;
	private int poleX = 0;
	private int poleY = 0;
	private int bossX = 0;
	private int bossY = 0;
	private int keyX = 0;
	private int keyY = 0;

	private Game g;
	
	private LevelType levelType;
	
	private ArrayList<Button> buttons = new ArrayList<>();
	
	
	BlockSelect(Game g) {
		this.g = g;
		levelType = Game.typeToMake;
		
		selectBoundingBox =  new Rectangle((World.SCREEN_SIZE_X / 2) - (WIDTH / 2) + g.GetCamera().GetX(), g.GetCamera().GetY(), WIDTH, HEIGHT);
		
		//Create buttons
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 1), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getBrickSprite(), Material.BRICK));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 1), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getStoneSprite(), Material.STONE));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 4), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getQuestionOneSprite(), Material.QUESTION));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 4), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getEmptyCoinSolidSprite(), Material.EMPTY_COIN_SOLID));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 7), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getHiddenBlockSprite(), Material.HIDDEN_BLOCK));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 7), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getDamageSprite(), Material.DAMAGE));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 10), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getBridgeSprite(), Material.BRIDGE));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 10), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getPipeIconSprite(), Material.PIPE_TOP_LEFT));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 13), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getGrassMiddleSprite(), Material.GRASS_MIDDLE));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 13), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getTrunkSprite(), Material.TRUNK));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 16), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getBulletStationIconSprite(), Material.BULLET_TOP));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 16), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getFireWheelIconSprite(), Material.FIREWHEEL_ICON));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 19), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getMushroomSprite(), Material.MUSHROOM_ICON));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 19), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getFlowerSprite(), Material.FLOWER_ICON));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 22), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getOneUpSprite(), Material.ONEUP_ICON));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 22), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getCoinSprite(), Material.COIN_ICON));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 25), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getGoombaSprite(), Material.GOOMBA_ICON));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 25), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getLavaBallIconSprite(), Material.LAVA_BALL_ICON));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 28), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getGreenTurtleSprite(), Material.GREEN_TURTLE_ICON));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 28), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getJumpGreenTurtleSprite(), Material.JUMP_GREEN_TURTLE_ICON));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 31), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getRedTurtleSprite(), Material.RED_TURTLE_ICON));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 31), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getJumpRedTurtleSprite(), Material.JUMP_RED_TURTLE_ICON));
		
		if(levelType == LevelType.CASTLE) 
			buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 34), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getKeySprite(), Material.KEY_ICON));
		else 
			buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 34), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getFlagSprite(), Material.POLE_TOP));
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 34), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getPlantSprite(), Material.PLANT_ICON));
		
		buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 37), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1), Sprite.getPlayerSprite(), Material.PLAYER_ICON));
		if(levelType == LevelType.CASTLE)
			buttons.add(new Button(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * 37), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7), Sprite.getBossIconSprite(), Material.BOSS_ICON));
	}
	
	public void updateBoundingBox() {
		selectBoundingBox.setLocation((World.SCREEN_SIZE_X / 2) - (selectBoundingBox.width / 2) + g.GetCamera().GetX(), g.GetCamera().GetY());
		//Buttons
		int x = 1;
		int count = 0;
		for(Button b : buttons) {
			count++;
			if(count == 1) 
				b.updateBoundingBox(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * x), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 1));
			else if(count == 2) {
				b.updateBoundingBox(selectBoundingBox.x + ((Tile.TILE_SIZE / 2) * x), selectBoundingBox.y + ((Tile.TILE_SIZE / 4) * 7));
				x = x + 3;
				count = 0;
			}
		}
	}
	
	public void render(Graphics g) {
		updateBoundingBox();
		if(Game.blockSelect == true) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(selectBoundingBox.x, selectBoundingBox.y, WIDTH, HEIGHT);
			g.setColor(Color.BLACK);
			g.drawRect(selectBoundingBox.x, selectBoundingBox.y, WIDTH, HEIGHT);
			
			//Draw sprites
			for(Button b : buttons)
				b.render(g);
		}
	}
	
	public void tick(Game g) {
		if(g.getClicked() == true) {
			if(Game.blockSelect == false) return;
			
			Point modMouseLoc = new Point(g.getMouseLoc());
			modMouseLoc.setLocation(g.getMouseLoc().getX() + g.GetCamera().GetX(), g.getMouseLoc().getY() + g.GetCamera().GetY());
			
			for(Button b : buttons) {
				
				if(b.getBoundingBox().contains(modMouseLoc)) {
					if(b.getButtonMaterial() != null) Game.matSelected = b.getButtonMaterial();
				}
				
			}
		}
	}
	
	public Rectangle getBoundingBox() {
		return selectBoundingBox;
	}

	public boolean isSpawnPointPlaced() {
		return spawnPointPlaced;
	}

	public void setSpawnPointPlaced(boolean spawnPointPlaced) {
		this.spawnPointPlaced = spawnPointPlaced;
	}

	public boolean isPolePlaced() {
		return polePlaced;
	}

	public void setPolePlaced(boolean polePlaced) {
		this.polePlaced = polePlaced;
	}

	public int getSpawnPointX() {
		return spawnPointX;
	}

	public void setSpawnPointX(int spawnPointX) {
		this.spawnPointX = spawnPointX;
	}

	public int getSpawnPointY() {
		return spawnPointY;
	}

	public void setSpawnPointY(int spawnPointY) {
		this.spawnPointY = spawnPointY;
	}

	public int getPoleX() {
		return poleX;
	}

	public void setPoleX(int poleX) {
		this.poleX = poleX;
	}

	public int getPoleY() {
		return poleY;
	}

	public void setPoleY(int poleY) {
		this.poleY = poleY;
	}
	
	public boolean isBossPlaced() {
		return bossPlaced;
	}

	public void setBossPlaced(boolean bossPlaced) {
		this.bossPlaced = bossPlaced;
	}

	public boolean isKeyPlaced() {
		return keyPlaced;
	}

	public void setKeyPlaced(boolean keyPlaced) {
		this.keyPlaced = keyPlaced;
	}

	public int getBossX() {
		return bossX;
	}

	public void setBossX(int bossX) {
		this.bossX = bossX;
	}

	public int getBossY() {
		return bossY;
	}

	public void setBossY(int bossY) {
		this.bossY = bossY;
	}

	public int getKeyX() {
		return keyX;
	}

	public void setKeyX(int keyX) {
		this.keyX = keyX;
	}

	public int getKeyY() {
		return keyY;
	}

	public void setKeyY(int keyY) {
		this.keyY = keyY;
	}
	
	public LevelType getLevelType() {
		return levelType;
	}
	
	public void setLevelType(LevelType levelType) {
		this.levelType = levelType;
	}
	
}
