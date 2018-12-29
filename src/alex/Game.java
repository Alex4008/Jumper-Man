/**
 * @author Alex Gray
 * Website: alexdgray.com
 * This product includes software written by Alex Gray (alexgray2017@gmail.com)
 * All assets in the /res directory related to Super Mario Bros are properties of Nintendo.
 * I do not claim to own the rights to any of the textures or sprites in this game.
 * This game is fan recreation of Super Mario Bros created for educational purpose and is not for sale anywhere.
 */
package alex;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

public class Game extends Canvas implements Runnable {
	
	//Dev control panel
	public static double version = 1.2;
	public static boolean devMode = false;
	public static boolean levelEdit = false;
	public static int levelEditWorldSize = 200; //Change this to ask the user for a size upon loading
	public final String TITLE = "Jumper Man - v" + Double.toString(version) + " - Created By Alex Gray - 2018";
	//End dev control panel
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 704;
	static JFrame frame;
	public GameState currentState;
	private BufferedImage icon = null;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet;
	public MainMenu mainMenu;
	public PauseMenu pauseMenu;
	public SettingsMenu settingsMenu;
	public LevelSelectMenu levelSelectMenu;
	public CustomLevelMenu customLevelMenu;
	public TypeSelectMenu typeSelectionMenu;
	public PauseEditMenu pauseEditMenu;
	public ArrayList<Entity> entityList;
	public ArrayList<Fireball> fireballList;
	public ArrayList<Item> itemList;
	public ArrayList<BulletStation> bulletStations;
	public ArrayList<Pipe> pipeList;
	public ArrayList<FireWheel> fireWheelList;
	public static boolean displayFPS = false;
	public static boolean hitBoxes = false;
	public static int spawnWidth;
	public static int spawnHeight;
	private boolean running = false;
	private Thread thread;
	private Player thePlayer;
	private Sprite sprites;
	private World world;
	private int currentTicks;
	private int currentFrames;
	private Camera playerCamera = new Camera(0, 0);
	public final Color skyBlue = new Color(161, 162, 255);
	public final Color caveBlack = new Color(1, 0, 1);
	Color currentBackgroundColor = skyBlue;
	Point mouseLoc = new Point(0, 0);
	private boolean clicked = false;
	private boolean mouseOnScreen = false;
	private int timer;
	private int counter;
	public static boolean musicToggle = true;
	public static boolean soundsToggle = true;
	public boolean vSyncToggle = true;
	public static int topScore = 0;
	private RandomAccessFile settingsData;
	private Path path;
	private int userRefreshRate = 240;
	private Sounds soundManager;
	public static int levelEditorLocX = 0;
	public static int levelEditorLocY = 0;
	public static boolean mouseHeld = false;
	int clickType = 0;
	public static Material matSelected;
	public static boolean blockSelect = true;
	public BlockSelect blockSelector;
	public static String levelToLoad = "";
	public static int currentLevel = 0;
	public static int currentWorld = 0;
	public static boolean mainGame = false;
	public static LevelType currentLevelType;
	private long lastClick;
	public static boolean inEditMenu;
	public static boolean customOneExists = false;
	public static boolean customTwoExists = false;
	public static boolean customThreeExists = false;
	public static boolean customFourExists = false;
	public static boolean loadPreviousLevel = false;
	public static String levelToEdit = "";
	public static LevelType typeToMake;
	
	//Loading / Main Methods
	
	public void init() {
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spriteSheet = loader.loadImage("/sprite_sheet.png");
			icon = loader.loadImage("/icon.png");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		setSprites(new Sprite(this)); //Load all the sprites into memory from the sprite sheet
		
		frame.setIconImage(icon);
		
		addKeyListener(new KeyInput(this));
		addMouseListener(new MouseInput(this));
		addMouseMotionListener(new MouseMove(this));
		
		setSoundManager(new Sounds());
		
		//Loading Settings File
		path = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/JumperMan");
		if(Files.notExists(path)) {
			System.out.println("File not found! Making file!");
			new File(path.toString()).mkdirs();
		}
		
		path = Paths.get(path.toString() + "/settings.txt");
		try {
			settingsData = new RandomAccessFile(path.toString(), "rw");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		//Reading from file
		ReadData(settingsData);

		//Getting display refresh rate
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();

		for (int i = 0; i < gs.length; i++) {
			DisplayMode dm = gs[i].getDisplayMode();

			userRefreshRate = dm.getRefreshRate();
			if (userRefreshRate == DisplayMode.REFRESH_RATE_UNKNOWN) {
				System.out.println("Unknown refresh rate. Leaving it at default: " + userRefreshRate); 
			}
		}
		
		//Loading the main menu
		if(levelEdit == true) {
			LoadState.Load(this, GameState.LEVEL_EDIT);
			matSelected = Material.BRICK;
		}
		else {
			LoadState.Load(this, GameState.MAIN_MENU);
		}
		
	}
	
	private synchronized void start() {
		if(running) return;
		System.out.println("Start");
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running) return;
		running = false;
		try { thread.join(); } catch (InterruptedException e) { e.printStackTrace(); }
		System.exit(1);
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		game.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame = new JFrame(game.TITLE);
		frame.setResizable(false);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.start();
	}
	
	public void run() {	
		init();
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		double frameDelta = 0;
		double frameNS = 1000000000 / userRefreshRate;
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			frameDelta += (now - lastTime) / frameNS;
			lastTime = now;
			if(delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			
			if(vSyncToggle == true) {
				if(frameDelta >= 1) {
					render();
					frames++;
					frameDelta--;
				}
			}
			else {
				render();
				frames++;
				frameDelta = 0;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				currentTicks = updates;
				currentFrames = frames;
				updates = 0;
				frames = 0;
			}

		}
		stop();
	}
	
	private void tick() {
		updateBackgroundColor();
		
		Tick.ticking(this);
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		////////////////////
		//Draw the background
		g.setColor(currentBackgroundColor);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//Render the scene
		Render.rendering(this, g);
		
		////////////////////
		g.dispose();
		bs.show();
		
	}

	//Other Methods
	
	//Writes data for the settings file
	public void WriteData(RandomAccessFile raf) {
		try {
			raf.seek(0);
			raf.writeBoolean(musicToggle);
			raf.writeBoolean(soundsToggle);
			raf.writeBoolean(vSyncToggle);
			raf.writeInt(topScore);
			raf.writeBoolean(customOneExists);
			raf.writeBoolean(customTwoExists);
			raf.writeBoolean(customThreeExists);
			raf.writeBoolean(customFourExists);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Reads data from the settings file
	public void ReadData(RandomAccessFile raf) {
		try {
			raf.seek(0);
			musicToggle = raf.readBoolean();
			soundsToggle = raf.readBoolean();
			vSyncToggle = raf.readBoolean();
			topScore = raf.readInt();
			customOneExists = raf.readBoolean();
			customTwoExists = raf.readBoolean();
			customThreeExists = raf.readBoolean();
			customFourExists = raf.readBoolean();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//Loads a file into the game to play the level
	public void LoadLevelData(String levelName) {
		InputStream in = null;
		
		try {
			BufferedReader reader = null;
			if(levelName.contains("custom")) {
				path = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/JumperMan");
				levelName = levelName.replace("/", File.separator);
				reader = new BufferedReader(new FileReader(path + File.separator + levelName + "-data.txt"));
			}
			else {
				in = this.getClass().getResourceAsStream("/levels/" + levelName + "-data.txt");
				reader = new BufferedReader(new InputStreamReader(in));
			}
			//System.out.println(reader.readLine());
			World.cols = Integer.parseInt(reader.readLine()); //Setting world size

			String strLevelType = reader.readLine().toLowerCase();

			if(strLevelType.contains("overworld")) currentLevelType = LevelType.OVERWORLD;
			else if(strLevelType.contains("underworld")) currentLevelType = LevelType.UNDERWORLD;
			else if(strLevelType.contains("castle")) currentLevelType = LevelType.CASTLE;
			else System.out.println("Error reading LEVELTYPE to load level: " + strLevelType);
			
			//System.out.println(strLevelType);
			
			//Load all the sprites into memory from the sprite sheet
			setSprites(new Sprite(this));
			Material.refreshAll();
			
			//Create the world
			SetWorld(new World(this, World.cols));
			GetWorld().initStage(levelName); 
			
			String line;
			
			try {
				while((line = reader.readLine()) != null) {
					String args[] = line.split(" ");
					int rowValue = Integer.parseInt(args[1]);
					int colValue = Integer.parseInt(args[2]);
					if(line.contains("MUSHROOM")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.MUSHROOM);
					}
					else if(line.contains("FLOWER")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.FLOWER);
					}
					else if(line.contains("ONEUP")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.ONEUP);
					}
					else if(line.contains("COIN")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.COIN_FRAGMENT);
					}
					else if(line.contains("GOOMBA")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.GOOMBA);
						}
						else {
							entityList.add(new Entity(EntityType.GOOMBA, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("JUMP_RED_TURTLE")) { //Jumping Red turtle
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.JUMP_RED_TURTLE);
						}
						else {
							entityList.add(new Entity(EntityType.JUMP_RED_TURTLE, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("RED_TURTLE")) { //Red turtle
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.RED_TURTLE);
						}
						else {
							entityList.add(new Entity(EntityType.RED_TURTLE, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("JUMP_TURTLE")) { //Jumping Green turtle
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.JUMP_GREEN_TURTLE);
						}
						else {
							entityList.add(new Entity(EntityType.JUMP_GREEN_TURTLE, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("TURTLE")) { //Green turtle
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.GREEN_TURTLE);
						}
						else {
							entityList.add(new Entity(EntityType.GREEN_TURTLE, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("BULLET")) {
						bulletStations.add(new BulletStation(colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE, args[3]));
					}
					else if(line.contains("PLANT")) {
						if(args.length != 4) {
							entityList.add(new Entity(EntityType.PLANT, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("SPAWN")) {
						spawnWidth = colValue * Tile.TILE_SIZE;
						spawnHeight = rowValue * Tile.TILE_SIZE;
					}
					else if(line.contains("KEY")) {
						itemList.add(new Item(ItemType.KEY, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE, "east"));
					}
					else if(line.contains("BOSS")) {
						entityList.add(new Entity(EntityType.BOSS, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
					}
					else if(line.contains("PIPE")) {
						if(args.length == 4) {
							pipeList.add(new Pipe(this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE, true, EntityType.PLANT));
						}
						else {
							pipeList.add(new Pipe(colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
						}
					}
					else if(line.contains("LAVABALL")) {
						entityList.add(new Entity(EntityType.LAVA_BALL, this, colValue * Tile.TILE_SIZE, rowValue * Tile.TILE_SIZE));
					}
					else if(line.contains("EXTRAC")) {
						World.tiledMap[rowValue][colValue].setExtraCoin(true);
					}
					else {
						System.out.println("Error reading data file: " + line);
					}
				}
			}
			catch(Exception e) {
				//System.out.println("Done reading data");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(!levelName.contains("custom")) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Loads a file into the level editor
	public void LoadLevelEditor(String levelName) {
		InputStream in = null;
		try {
			
			BufferedReader reader = null;
			if(levelName.contains("custom")) {
				path = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/JumperMan");
				levelName = levelName.replace("/", File.separator);
				reader = new BufferedReader(new FileReader(path + File.separator + levelName + "-data.txt"));
			}
			else {
				in = this.getClass().getResourceAsStream("/levels/" + levelName + "-data.txt");
				reader = new BufferedReader(new InputStreamReader(in));
			}
			
			World.cols = Integer.parseInt(reader.readLine()); //Setting world size

			String strLevelType = reader.readLine().toLowerCase();
			
			if(strLevelType.contains("overworld")) {
				currentLevelType = LevelType.OVERWORLD;
				typeToMake = LevelType.OVERWORLD;
			}
			else if(strLevelType.contains("underworld")) {
				currentLevelType = LevelType.UNDERWORLD;
				typeToMake = LevelType.UNDERWORLD;
			}
			else if(strLevelType.contains("castle")) {
				currentLevelType = LevelType.CASTLE;
				typeToMake = LevelType.CASTLE;
			}
			else System.out.println("Error reading LEVELTYPE into level editor: " + strLevelType);
			
			setSprites(new Sprite(this)); //Load all the sprites into memory from the sprite sheet
			Material.refreshAll();
			
			//Create the world
			SetWorld(new World(this, World.cols));
			GetWorld().initStage(levelName); 
			
			String line;
			
			try {
				while((line = reader.readLine()) != null) {
					String args[] = line.split(" ");
					int rowValue = Integer.parseInt(args[1]);
					int colValue = Integer.parseInt(args[2]);
					if(line.contains("MUSHROOM")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.MUSHROOM);
					}
					else if(line.contains("FLOWER")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.FLOWER);
					}
					else if(line.contains("ONEUP")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.ONEUP);
					}
					else if(line.contains("COIN")) {
						World.tiledMap[rowValue][colValue].setContainsItem(true);
						World.tiledMap[rowValue][colValue].setItemType(ItemType.COIN);
					}
					else if(line.contains("GOOMBA")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.GOOMBA);
						}
						else {
							World.tiledMap[rowValue][colValue] = new Block(Material.GOOMBA_ICON, rowValue, colValue);
						}
					}
					else if(line.contains("PLANT")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.PLANT);
						}
						else {
							World.tiledMap[rowValue][colValue] = new Block(Material.PLANT_ICON, rowValue, colValue);
						}
					}
					else if(line.contains("JUMP_RED_TURTLE")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.JUMP_RED_TURTLE);
						}
						else {
							World.tiledMap[rowValue][colValue] = new Block(Material.JUMP_RED_TURTLE_ICON, rowValue, colValue);
						}
					}
					else if(line.contains("RED_TURTLE")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.RED_TURTLE);
						}
						else {
							World.tiledMap[rowValue][colValue] = new Block(Material.RED_TURTLE_ICON, rowValue, colValue);
						}
					}
					else if(line.contains("JUMP_TURTLE")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.JUMP_GREEN_TURTLE);
						}
						else {
							World.tiledMap[rowValue][colValue] = new Block(Material.JUMP_GREEN_TURTLE_ICON, rowValue, colValue);
						}
					}
					else if(line.contains("TURTLE")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.GREEN_TURTLE);
						}
						else {
							World.tiledMap[rowValue][colValue] = new Block(Material.GREEN_TURTLE_ICON, rowValue, colValue);
						}
					}
					else if(line.contains("SPAWN")) {
						World.tiledMap[rowValue][colValue] = new Block(Material.PLAYER_ICON, rowValue, colValue);
						blockSelector.setSpawnPointPlaced(true);
						blockSelector.setSpawnPointX(rowValue);
						blockSelector.setSpawnPointY(colValue);
					}
					else if(line.contains("KEY")) {
						World.tiledMap[rowValue][colValue] = new Block(Material.KEY_ICON, rowValue, colValue);
						blockSelector.setKeyPlaced(true);
						blockSelector.setKeyX(rowValue);
						blockSelector.setKeyY(colValue);
					}
					else if(line.contains("BOSS")) {
						World.tiledMap[rowValue][colValue] = new Block(Material.BOSS_ICON, rowValue, colValue);
						blockSelector.setBossPlaced(true);
						blockSelector.setBossX(rowValue);
						blockSelector.setBossY(colValue);
					}
					else if(line.contains("PIPE")) {
						if(args.length == 4) {
							World.tiledMap[rowValue][colValue].setContainsEntity(true);
							World.tiledMap[rowValue][colValue].setEntityType(EntityType.PLANT);
						}
					}
					else if(line.contains("HIDDEN")) {
						World.tiledMap[rowValue][colValue] = new Block(Material.HIDDEN_BLOCK, rowValue, colValue);
					}
					else if(line.contains("BULLET")) {
						//Do nothing, cause you don't need to
					}
					else if(line.contains("LAVABALL")) {
						World.tiledMap[rowValue][colValue] = new Block(Material.LAVA_BALL_ICON, rowValue, colValue);
					}
					else if(line.contains("EXTRAC")) {
						World.tiledMap[rowValue][colValue].setExtraCoin(true);
					}
					else {
						System.out.println("Error reading data to load world for level editor: " + line);
					}
				}
			}
			catch(Exception e) {
				//System.out.println("Done reading data");
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(!levelName.contains("custom")) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Deletes a custom level
	public void DiscardLevel() {
		
		if(currentLevel == 1) Game.customOneExists = false;
		if(currentLevel == 2) Game.customTwoExists = false;
		if(currentLevel == 3) Game.customThreeExists = false;
		if(currentLevel == 4) Game.customFourExists = false;
		
		Path mainPath = Paths.get(path.toString() + "/" + levelToEdit + ".txt");
		Path dataPath = Paths.get(path.toString() + "/" + levelToEdit + "-data.txt");
		
		File main = new File(mainPath.toString());
		File data = new File(dataPath.toString());
		
		main.delete();
		data.delete();
		
		System.out.println("Level " + currentLevel + " was deleted. " + levelToEdit);
	}
	
	//Saves the level editor world to a file.
	public void SaveLevelEditor() {
		
		RandomAccessFile mainRAF = null;
		RandomAccessFile dataRAF = null;
		
		path = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/JumperMan/custom");
		if(Files.notExists(path)) {
			System.out.println("File not found! Making file!");
			new File(path.toString()).mkdirs();
		}
		
		path = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "/JumperMan");
		if(Files.notExists(path)) {
			System.out.println("File not found! Making file!");
			new File(path.toString()).mkdirs();
		}
		if(currentLevel == 1) Game.customOneExists = true;
		if(currentLevel == 2) Game.customTwoExists = true;
		if(currentLevel == 3) Game.customThreeExists = true;
		if(currentLevel == 4) Game.customFourExists = true;
		
		Path mainPath = Paths.get(path.toString() + "/" + levelToEdit + ".txt");
		Path dataPath = Paths.get(path.toString() + "/" + levelToEdit + "-data.txt");
		
		File main = new File(mainPath.toString());
		File data = new File(dataPath.toString());
		
		main.delete();
		data.delete();
		
		try {
			mainRAF = new RandomAccessFile(mainPath.toString(), "rw");
			dataRAF = new RandomAccessFile(dataPath.toString(), "rw");
			
			//Writing size of world
			dataRAF.writeBytes(Integer.toString(levelEditWorldSize));
			dataRAF.writeBytes(System.getProperty("line.separator"));
			dataRAF.writeBytes(blockSelector.getLevelType().toString());
			dataRAF.writeBytes(System.getProperty("line.separator"));
			
			System.out.println(blockSelector.getLevelType().toString());
			
			for(int i = 0; i < World.ROWS; i++) {
				for(int j = 0; j < World.cols; j++) {
					
					Material mat = World.tiledMap[i][j].getMaterial();
					
					switch (mat) {
					
					case AIR:
						mainRAF.writeChar('e');
						break;
						
					case FLOOR:
						mainRAF.writeChar('L');
						break;
						
					case BRICK:
						mainRAF.writeChar('0');
						if(World.tiledMap[i][j].isContainsEntity() == true) {
							if(World.tiledMap[i][j].getEntityType() == EntityType.GOOMBA) {
								dataRAF.writeBytes("GOOMBA");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.GREEN_TURTLE) {
								dataRAF.writeBytes("TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.RED_TURTLE) {
								dataRAF.writeBytes("RED_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_GREEN_TURTLE) {
								dataRAF.writeBytes("JUMP_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_RED_TURTLE) {
								dataRAF.writeBytes("JUMP_RED_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.PLANT) {
								dataRAF.writeBytes("PLANT");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
						}
						else if(World.tiledMap[i][j].getItemType() != ItemType.EMPTY) {
							if(World.tiledMap[i][j].getItemType() == ItemType.MUSHROOM) {
								dataRAF.writeBytes("MUSHROOM");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.FLOWER) {
								dataRAF.writeBytes("FLOWER");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.ONEUP) {
								dataRAF.writeBytes("ONEUP");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.COIN) {
								dataRAF.writeBytes("COIN");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
								
								if(World.tiledMap[i][j].isExtraCoin() == true) {
									dataRAF.writeBytes("EXTRAC");
									dataRAF.writeBytes(" " + i + " " + j);
									dataRAF.writeBytes(System.getProperty("line.separator"));
								}
								
							}
						}
						break;
						
					case STONE:
						mainRAF.writeChar('1');
						break;
						
					case DAMAGE:
						mainRAF.writeChar('!');
						break;
						
					case QUESTION:
						mainRAF.writeChar('?');
						if(World.tiledMap[i][j].isContainsEntity() == true) {
							if(World.tiledMap[i][j].getEntityType() == EntityType.GOOMBA) {
								dataRAF.writeBytes("GOOMBA");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.GREEN_TURTLE) {
								dataRAF.writeBytes("TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.RED_TURTLE) {
								dataRAF.writeBytes("RED_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_GREEN_TURTLE) {
								dataRAF.writeBytes("JUMP_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_RED_TURTLE) {
								dataRAF.writeBytes("JUMP_RED_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.PLANT) {
								dataRAF.writeBytes("PLANT");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
						}
						else if(World.tiledMap[i][j].getItemType() != ItemType.EMPTY) {
							if(World.tiledMap[i][j].getItemType() == ItemType.MUSHROOM) {
								dataRAF.writeBytes("MUSHROOM");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.FLOWER) {
								dataRAF.writeBytes("FLOWER");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.ONEUP) {
								dataRAF.writeBytes("ONEUP");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.COIN) {
								dataRAF.writeBytes("COIN");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
								
								if(World.tiledMap[i][j].isExtraCoin() == true) {
									dataRAF.writeBytes("EXTRAC");
									dataRAF.writeBytes(" " + i + " " + j);
									dataRAF.writeBytes(System.getProperty("line.separator"));
								}
							}
						}
						break;
						
					case EMPTY_COIN_SOLID:
						mainRAF.writeChar('E');
						break;
						
					case EMPTY_COIN:
						mainRAF.writeChar('~');
						break;
						
					case HIDDEN_BLOCK:
						mainRAF.writeChar('H');
						if(World.tiledMap[i][j].isContainsEntity() == true) {
							if(World.tiledMap[i][j].getEntityType() == EntityType.GOOMBA) {
								dataRAF.writeBytes("GOOMBA");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.GREEN_TURTLE) {
								dataRAF.writeBytes("TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.RED_TURTLE) {
								dataRAF.writeBytes("RED_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_GREEN_TURTLE) {
								dataRAF.writeBytes("JUMP_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_RED_TURTLE) {
								dataRAF.writeBytes("JUMP_RED_TURTLE");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getEntityType() == EntityType.PLANT) {
								dataRAF.writeBytes("PLANT");
								dataRAF.writeBytes(" " + i + " " + j + " INBLOCK");
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
						}
						else if(World.tiledMap[i][j].getItemType() != ItemType.EMPTY) {
							if(World.tiledMap[i][j].getItemType() == ItemType.MUSHROOM) {
								dataRAF.writeBytes("MUSHROOM");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.FLOWER) {
								dataRAF.writeBytes("FLOWER");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.ONEUP) {
								dataRAF.writeBytes("ONEUP");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
							}
							else if(World.tiledMap[i][j].getItemType() == ItemType.COIN) {
								dataRAF.writeBytes("COIN");
								dataRAF.writeBytes(" " + i + " " + j);
								dataRAF.writeBytes(System.getProperty("line.separator"));
								
								if(World.tiledMap[i][j].isExtraCoin() == true) {
									dataRAF.writeBytes("EXTRAC");
									dataRAF.writeBytes(" " + i + " " + j);
									dataRAF.writeBytes(System.getProperty("line.separator"));
								}
							}
						}
						break;
						
					case BRIDGE:
						mainRAF.writeChar('R');
						break;
						
					case GRASS_LEFT:
						mainRAF.writeChar('7');
						break;
						
					case GRASS_MIDDLE:
						mainRAF.writeChar('8');
						break;
						
					case GRASS_RIGHT:
						mainRAF.writeChar('9');
						break;
						
					case TRUNK:
						mainRAF.writeChar('N');
						break;
						
					case PIPE_TOP_LEFT:
						mainRAF.writeChar('5');
						dataRAF.writeBytes("PIPE");
						dataRAF.writeBytes(" " + i + " " + j);
						if(World.tiledMap[i][j].isContainsEntity() == true) {
							dataRAF.writeBytes(" HASP");
						}
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case PIPE_TOP_RIGHT:
						mainRAF.writeChar('6');
						break;
					
					case PIPE_LEFT:
						mainRAF.writeChar('2');
						break;
						
					case PIPE_RIGHT:
						mainRAF.writeChar('3');
						break;
						
					case KEY_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("KEY");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case POLE_TOP:
						mainRAF.writeChar('T');
						break;
						
					case POLE_SECTION:
						mainRAF.writeChar('P');
						break;
						
					case POLE_BOTTOM:
						mainRAF.writeChar('B');
						break;
						
					case BULLET_TOP:
						mainRAF.writeChar('-');
						dataRAF.writeBytes("BULLET");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(" " + World.tiledMap[i][j].getDirection());
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case BULLET_BOTTOM:
						mainRAF.writeChar('=');
						break;
					
					case COIN_ICON:
						mainRAF.writeChar('C');
						break;
						
					case GOOMBA_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("GOOMBA");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case GREEN_TURTLE_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("TURTLE");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case RED_TURTLE_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("RED_TURTLE");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case JUMP_GREEN_TURTLE_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("JUMP_TURTLE");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case JUMP_RED_TURTLE_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("JUMP_RED_TURTLE");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case PLANT_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("PLANT");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case BOSS_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("BOSS");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case PLAYER_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("SPAWN");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					case FIREWHEEL_ICON:
						mainRAF.writeChar('f');
						break;
						
					case LAVA_BALL_ICON:
						mainRAF.writeChar('e');
						dataRAF.writeBytes("LAVABALL");
						dataRAF.writeBytes(" " + i + " " + j);
						dataRAF.writeBytes(System.getProperty("line.separator"));
						break;
						
					default:
						System.out.println("Error writing level to file");
						mainRAF.writeChar('X');
						break;
					}
					
				}
				mainRAF.writeBytes(System.getProperty("line.separator"));
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				mainRAF.close();
				dataRAF.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void CloseGame() {
		WriteData(settingsData);
		running = false;
		try {
			settingsData.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	public static void setLevel(int world, int level, String filename) {
		currentWorld = world;
		currentLevel = level;
		levelToLoad = filename;
	}
	
	public void updateBackgroundColor() {
		if(currentState != GameState.GAME_LEVEL && currentState != GameState.LEVEL_EDIT && currentState != GameState.LEVEL_FINISHED) {
			currentBackgroundColor = skyBlue;
		}
		else {
			if(currentLevelType == LevelType.OVERWORLD) currentBackgroundColor = skyBlue;
			else if(currentLevelType == LevelType.UNDERWORLD) currentBackgroundColor = caveBlack;
			else if(currentLevelType == LevelType.CASTLE) currentBackgroundColor = caveBlack;
		}
	}
	
	//Getters and setters
	
	public BufferedImage getSpriteSheet() { return spriteSheet; }
	public ArrayList<Entity> getEntityList() { return entityList; }
	public ArrayList<Fireball> getFireballList() { return fireballList; }
	public ArrayList<Item> getItemList() { return itemList; }
	public ArrayList<BulletStation> getBulletStations() { return bulletStations; }
	public ArrayList<Pipe> getPipeList() { return pipeList; }
	public ArrayList<FireWheel> getFireWheelList() { return fireWheelList; }
	public void AddItem(ItemType item, int x, int y) { itemList.add(new Item(item, this, x, y, "east")); }
	public void AddEntity(EntityType entity, int x, int y) { entityList.add(new Entity(entity, this, x, y, "east")); }
	public double GetVersion() { return version; }
	public boolean GetShowHitBoxes() { return hitBoxes; }
	public boolean GetShowFPS() { return displayFPS; }
	public Player GetThePlayer() { return thePlayer; }
	public Camera GetCamera() { return playerCamera; }
	public int GetCurrentTicks() { return currentTicks; }
	public int GetCurrentFrames() { return currentFrames; }
	public MainMenu GetMainMenu() { return mainMenu; }
	public World GetWorld() { return world; }
	public void SetWorld(World world) { this.world = world; }
	public boolean getClicked() { return clicked; }
	public void setClicked(boolean clicked) { this.clicked = clicked; }
	public Point getMouseLoc() { return mouseLoc; }
	public void setMouseOnScreen(boolean mouseOnScreen) { this.mouseOnScreen = mouseOnScreen; }
	public boolean getMouseOnScreen() { return mouseOnScreen; }
	public void SetPlayer(Player thePlayer) { this.thePlayer = thePlayer; }
	public void SetEntityList(ArrayList<Entity> entityList) { this.entityList = entityList; }
	public void SetFireballList(ArrayList<Fireball> fireballList) { this.fireballList = fireballList; }
	public void SetItemList(ArrayList<Item> itemList) { this.itemList = itemList; }
	public void SetBulletStationList(ArrayList<BulletStation> bulletStations) { this.bulletStations = bulletStations; }
	public void SetPipeList(ArrayList<Pipe> pipeList) { this.pipeList = pipeList; }
	public void SetFireWheelList(ArrayList<FireWheel> fireWheelList) { this.fireWheelList = fireWheelList; }
	public PauseMenu GetPauseMenu() { return pauseMenu; }
	public Sprite getSprites() { return sprites; }
	public void setSprites(Sprite sprites) { this.sprites = sprites; }
	public int getTimer() { return timer; }
	public void setTimer(int timer) { this.timer = timer; }
	public int getCounter() { return counter; }
	public void setCounter(int counter) { this.counter = counter; }
	public SettingsMenu getSettingsMenu() { return settingsMenu; }
	public boolean isMusicToggle() { return musicToggle; }
	public boolean isSoundsToggle() { return soundsToggle; }
	public boolean isvSyncToggle() { return vSyncToggle; }
	public void setvSyncToggle(boolean vSyncToggle) { this.vSyncToggle = vSyncToggle; }
	public RandomAccessFile getSettingsFile() { return settingsData; }
	public Sounds getSoundManager() { return soundManager; }
	public void setSoundManager(Sounds soundManager) { this.soundManager = soundManager; }
	public int GetClickType() { return clickType; }
	public BlockSelect getBlockSelector() { return blockSelector; }
	public void setBlockSelector(BlockSelect blockSelector) { this.blockSelector = blockSelector; }
	public LevelSelectMenu getLevelSelectMenu() { return levelSelectMenu; }
	public Color getBackgroundColor() { return currentBackgroundColor; }
	public long getTimeLastClicked() { return lastClick; }
	public void setTimeLastClicked(long time) { this.lastClick = time; }
	public CustomLevelMenu getCustomLevelMenu() { return customLevelMenu; }
	public TypeSelectMenu getTypeSelectionMenu() { return typeSelectionMenu; }
	public PauseEditMenu getPauseEditMenu() { return pauseEditMenu; }
}
