package alex;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import javax.swing.filechooser.FileSystemView;

public class World {
	
	public static Block[][] tiledMap;
	public static final int ROWS = 22;
	public static final int SCREEN_WIDTH_COLS = 40;
	//public static final int COLS = 80;
	
	public static int cols = 0;
	
	//public static final int WORLD_SIZE_X = cols * Tile.TILE_SIZE;
	
	public static int worldSizeX = cols * Tile.TILE_SIZE;
	public static final int WORLD_SIZE_Y = ROWS * Tile.TILE_SIZE;
	
	public static final int SCREEN_SIZE_X = Game.WIDTH;
	public static final int SCREEN_SIZE_Y = Game.HEIGHT;
	
	private Game g;
	
	public World(Game game, int width) {
		cols = width;
		tiledMap = new Block[ROWS][cols];
		g = game;
	}
	
	public void initStage(String level) {
		InputStream in = null;
		try {
			
			BufferedReader reader = null;
			if(level.contains("custom")) {
				level = level.replace("/", File.separator);
				reader = new BufferedReader(new FileReader(Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/JumperMan") + File.separator + level + ".txt"));
			}
			else {
				in = this.getClass().getResourceAsStream("/levels/" + level + ".txt");
				reader = new BufferedReader(new InputStreamReader(in));
			}
			
			int i = 0;
			int blocksNum = 0;
			int currentRow = 0;
			String line = reader.readLine();
			while(line != null) {
				//System.out.println((int) line.charAt(0));
				while(i < (cols * 2) ) {
					if(line.charAt(i) != ' ' && line.charAt(i) != 0) {
						tiledMap[currentRow][blocksNum] = newTileInstance(line.charAt(i), currentRow, blocksNum);
						blocksNum++;
					}
					i++;
					//System.out.println(i);
				}
				i = 0;
				blocksNum = 0;
				currentRow++;
				line = reader.readLine();
			//	System.out.println(line);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(!level.contains("custom")) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Block newTileInstance(char name, int i, int j) {
		
		switch (name) {
		
		case 'e':
			return new Block(Material.AIR, i, j);
		
		case 'L':
			return new Block(Material.FLOOR, i, j);
			
		case '0':
			return new Block(Material.BRICK, i, j);
			
		case '1':
			return new Block(Material.STONE, i, j);
			
		case 'S':
			return new Block(Material.DAMAGE, i, j);
			
		case '!':
			return new Block(Material.DAMAGE, i, j);
		
		case '?':
			return new Block(Material.QUESTION, i, j);
			
		case 'M':
			return new Block(Material.QUESTION, i, j, ItemType.MUSHROOM);
			
		case 'F':
			return new Block(Material.QUESTION, i, j, ItemType.FLOWER);
			
		case 'U':
			return new Block(Material.QUESTION, i, j, ItemType.ONEUP);
			
		case 'E':
			return new Block(Material.EMPTY_COIN_SOLID, i, j);
			
		case '~':
			return new Block(Material.EMPTY_COIN, i, j);
			
		case 'H':
			return new Block(Material.HIDDEN_BLOCK, i, j);
			
		case 'R':
			return new Block(Material.BRIDGE, i, j);
			
		case '7':
			return new Block(Material.GRASS_LEFT, i, j);
			
		case '8':
			return new Block(Material.GRASS_MIDDLE, i, j);
			
		case '9':
			return new Block(Material.GRASS_RIGHT, i, j);
			
		case 'N':
			return new Block(Material.TRUNK, i, j);
			
		case '5':
			return new Block(Material.PIPE_TOP_LEFT, i, j);
			
		case '6':
			return new Block(Material.PIPE_TOP_RIGHT, i, j);
			
		case '2':
			return new Block(Material.PIPE_LEFT, i, j);
			
		case '3': 
			return new Block(Material.PIPE_RIGHT, i, j);
			
		case 'P':
			return new Block(Material.POLE_SECTION, i, j);
			
		case 'T':
			if(Game.levelEdit == false) {
				g.getItemList().add(new Item(ItemType.FLAG, g, (j * 32) - 14, (i * 32) + 9, "east"));
			}
			else {
				g.blockSelector.setPolePlaced(true);
				g.blockSelector.setPoleX(i);
				g.blockSelector.setPoleY(j);
			}
			
			return new Block(Material.POLE_TOP, i, j);
			
		case 'B':
			return new Block(Material.POLE_BOTTOM, i, j);
			
		case 'C':
			if(Game.levelEdit == false) {
				g.getItemList().add(new Item(ItemType.COIN, g, (j * 32), (i * 32), "east"));
				return new Block(Material.AIR, i, j);
			}
			else {
				return new Block(Material.COIN_ICON, i, j);
			}
			
		case '.':
			return new Block(Material.QUESTION, i, j, ItemType.COIN_FRAGMENT);
			
		case '-':
			//g.getBulletStations().add(new BulletStation((j * 32), (i * 32), "both"));
			return new Block(Material.BULLET_TOP, i, j);
			
		case '=':
			return new Block(Material.BULLET_BOTTOM, i, j);
			
		case 'f':
			if(Game.levelEdit == false) {
				g.getFireWheelList().add(new FireWheel(g, (j * 32) - 14, (i * 32), 7, "east"));
				return new Block(Material.EMPTY_COIN_SOLID, i, j);
			}
			else {
				return new Block(Material.FIREWHEEL_ICON, i, j);
			}
			
		}
		
		System.out.println("Error reading from file.");
		return null;
		
	}
	
	public void BuildLevelEditStage() {
		int height = 22; //Max supported height is 22 atm. I think. Honestly I don't know I just know thats what the screen can display.
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < cols; j++) {
				if(i == World.ROWS - 1) {
					tiledMap[i][j] = new Block(Material.FLOOR, i, j);
				}
				else {
					tiledMap[i][j] = new Block(Material.AIR, i, j);
				}
			}
		}
	}
	
	public static int GetWorldSizeX() {
		worldSizeX = (cols * Tile.TILE_SIZE);
		return worldSizeX;
	}
	
}
