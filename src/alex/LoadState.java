package alex;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

//This class is used to load the start of a game state. Load whatever that state needs to have loaded before moving on to rendering and ticking
public class LoadState {

	public static void Load(Game game, GameState state) {
		
		switch (state) {
		
		case MAIN_MENU:
			LoadMainMenu(game);
			return;
		
		case MAIN_SETTINGS:
			LoadSettingsMenu(game);
			return;
			
		case MAIN_LEVEL_SELECT:
			LoadLevelSelectMenu(game);
			return;
		
		case GAME_LEVEL:
			LoadGameLevel(game);
			return;
			
		case GAME_PAUSED:
			LoadPauseMenu(game);
			return;

		case LEVEL_EDIT:
			LoadLevelEdit(game);
			return;
			
		case DEATH_SCENE:
			LoadDeathScene(game);
			break;
			
		case GAMEOVER_SCENE:
			game.setCounter(0);
			if(Game.mainGame == true) {
				Game.topScore = game.GetThePlayer().getPlayerScore();
				game.WriteData(game.getSettingsFile());
			}
			game.currentState = GameState.GAMEOVER_SCENE;
			break;
			
		case LEVEL_LOAD:
			game.setCounter(0);
			game.currentState = GameState.LEVEL_LOAD;
			break;
			
		case LEVEL_FINISHED:
			game.setCounter(0);
			game.currentState = GameState.LEVEL_FINISHED;
			break;
			
		case START_MAIN_GAME:
			Game.mainGame = true;
			Game.setLevel(0, 0, "notset");
			LoadGameLevel(game);
			break;
			
		case CREDITS:
			game.setCounter(0);
			game.currentState = GameState.CREDITS;
			break;
			
		case CUSTOM_LEVEL_MENU:
			LoadCustomLevelMenu(game);
			break;
			
		case LEVEL_TYPE_MENU:
			LoadLevelTypeMenu(game);
			break;
			
		case LEVEL_EDIT_PAUSE:
			LoadPauseEditMenu(game);
			break;
			
		default:
			break;
			
		}
		
	}
	
	private static void LoadPauseEditMenu(Game game) {
		game.pauseEditMenu = new PauseEditMenu();
		game.currentState = GameState.LEVEL_EDIT_PAUSE;
	}
	
	private static void LoadLevelTypeMenu(Game game) {
		game.typeSelectionMenu = new TypeSelectMenu();
		game.currentState = GameState.LEVEL_TYPE_MENU;
	}
	
	private static void LoadCustomLevelMenu(Game game) {
		
		if(game.currentState == GameState.MAIN_MENU) Game.inEditMenu = true;
		else Game.inEditMenu = false;
		
		game.customLevelMenu = new CustomLevelMenu();
		game.currentState = GameState.CUSTOM_LEVEL_MENU;
	}

	private static void LoadLevelSelectMenu(Game game) {
		game.levelSelectMenu = new LevelSelectMenu();
		game.currentState = GameState.MAIN_LEVEL_SELECT;
	}
	
	private static void LoadSettingsMenu(Game game) {
		game.settingsMenu = new SettingsMenu(game);
		game.currentState = GameState.MAIN_SETTINGS;
	}
	
	private static void LoadDeathScene(Game game) {
		game.setCounter(0);
		game.currentState = GameState.DEATH_SCENE;
		
		game.setTimer(300);
		
		//Create arraylists
		game.SetEntityList(new ArrayList<Entity>());
		game.SetFireballList(new ArrayList<Fireball>());
		game.SetItemList(new ArrayList<Item>());
		game.SetBulletStationList(new ArrayList<BulletStation>());
		game.SetPipeList(new ArrayList<Pipe>());
		game.SetFireWheelList(new ArrayList<FireWheel>());
		
		//Load data for world and create world
		game.LoadLevelData(Game.levelToLoad);
		
		//Stop the player movement
		game.GetThePlayer().SetLeftHeld(false);
		game.GetThePlayer().SetRightHeld(false);
		game.GetThePlayer().setLastDirection(KeyEvent.VK_RIGHT);

	}
	
	private static void LoadPauseMenu(Game game) {
		game.getSoundManager().stopMusic();
		game.pauseMenu = new PauseMenu();
		game.currentState = GameState.GAME_PAUSED;
	}
	
	private static void LoadMainMenu(Game game)  {
		game.getSoundManager().stopMusic();
		Game.currentLevel = 0;
		Game.mainGame = false;
		game.mainMenu = new MainMenu();
		game.currentState = GameState.MAIN_MENU;
		World.tiledMap = null;
	}
	
	private static void LoadGameLevel(Game game) {
		game.currentState = GameState.GAME_LEVEL;
		
		game.setTimer(300);
		
		//Create arraylists
		game.SetEntityList(new ArrayList<Entity>());
		game.SetFireballList(new ArrayList<Fireball>());
		game.SetItemList(new ArrayList<Item>());
		game.SetBulletStationList(new ArrayList<BulletStation>());
		game.SetPipeList(new ArrayList<Pipe>());
		game.SetFireWheelList(new ArrayList<FireWheel>());
		if(Game.mainGame == true) {
			if(Game.currentWorld == 0) {
				Game.setLevel(1, 1, "worldOne/levelOne");
				//Create the player
				game.SetPlayer(new Player(Game.spawnWidth, Game.spawnHeight, game));
			}
			else if(Game.currentWorld == 1) {
				if(Game.currentLevel == 1) Game.setLevel(1, 2, "worldOne/levelTwo");
				else if(Game.currentLevel == 2) Game.setLevel(1, 3, "worldOne/levelThree");
				else if(Game.currentLevel == 3) Game.setLevel(1, 4, "worldOne/levelFour");
				else if(Game.currentLevel == 4) Game.setLevel(2, 1, "worldTwo/levelOne");
			}
			else if(Game.currentWorld == 2) {
				if(Game.currentLevel == 1) Game.setLevel(2, 2, "worldTwo/levelTwo");
				else if(Game.currentLevel == 2) Game.setLevel(2, 3, "worldTwo/levelThree");
				else if(Game.currentLevel == 3) Game.setLevel(2, 4, "worldTwo/levelFour");
				else if(Game.currentLevel == 4) Game.setLevel(3, 1, "worldThree/levelOne");
			}
			else if(Game.currentWorld == 3) {
				if(Game.currentLevel == 1) Game.setLevel(3, 2, "worldThree/levelTwo");
				else if(Game.currentLevel == 2) Game.setLevel(3, 3, "worldThree/levelThree");
				else if(Game.currentLevel == 3) Game.setLevel(3, 4, "worldThree/levelFour");
				else if(Game.currentLevel == 4) Game.setLevel(4, 1, "worldFour/levelOne");
			}
			else if(Game.currentWorld == 4) {
				if(Game.currentLevel == 1) Game.setLevel(4, 2, "worldFour/levelTwo");
				else if(Game.currentLevel == 2) Game.setLevel(4, 3, "worldFour/levelThree");
				else if(Game.currentLevel == 3) Game.setLevel(4, 4, "worldFour/levelFour");
				else if(Game.currentLevel == 4) {
					game.currentState = GameState.CREDITS;
					return;
				}	
			}
			else {
				System.out.println("Error finding next level. World: " + Game.currentWorld + " Level: " + Game.currentLevel);
			}
		}
		
		//Load data for world and create world
		game.LoadLevelData(Game.levelToLoad);
		
		if(Game.currentLevelType == LevelType.OVERWORLD) game.getSoundManager().PlayMusic(MusicEffect.OVER_THEME);
		else if(Game.currentLevelType == LevelType.UNDERWORLD) game.getSoundManager().PlayMusic(MusicEffect.UNDER_THEME);
		else if(Game.currentLevelType == LevelType.CASTLE) game.getSoundManager().PlayMusic(MusicEffect.CASTLE_THEME);
		else System.out.println("Invalid level: " + Game.currentLevelType.toString());

		if(Game.mainGame == false) {
			//Create the player
			game.SetPlayer(new Player(Game.spawnWidth, Game.spawnHeight, game));
		}
		else {
			//Reset some of the settings.
			game.GetThePlayer().changeLocation(Game.spawnWidth, Game.spawnHeight, true);
			game.GetThePlayer().setLevelDone(false);
			game.GetThePlayer().setMoving(false);
			game.GetThePlayer().SetLeftHeld(false);
			game.GetThePlayer().SetRightHeld(false);
			game.GetThePlayer().setLastDirection(KeyEvent.VK_RIGHT);
		}
		
	}
	
	private static void LoadLevelEdit(Game game) {
		game.currentState = GameState.LEVEL_EDIT;
		
		Game.levelEditorLocX = 0;
		Game.levelEditorLocY = 0;
		
		game.setSprites(new Sprite(game)); //Load all the sprites into memory from the sprite sheet
		Material.refreshAll();
		game.SetWorld(new World(game, Game.levelEditWorldSize));
		
		if(Game.loadPreviousLevel == true) {
			game.GetWorld().initStage(Game.levelToEdit);
			game.LoadLevelEditor(Game.levelToEdit);
		}
		else {
			game.GetWorld().BuildLevelEditStage();
		}
		
		game.setBlockSelector(new BlockSelect(game));
	}
	
}
