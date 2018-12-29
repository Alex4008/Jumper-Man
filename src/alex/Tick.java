package alex;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Tick {

	public static void ticking(Game game) {
		
		switch (game.currentState) {
		
		case MAIN_MENU:
			//Render main menu code here
			TickMainMenu(game);
			return;
		
		case MAIN_SETTINGS:
			TickSettingsMenu(game);
			return;
			
		case MAIN_LEVEL_SELECT:
			TickLevelSelectMenu(game);
			return;
		
		case GAME_LEVEL:
			TickGameLevel(game);
			return;
			
		case GAME_PAUSED:
			TickPauseMenu(game);
			return;
			
		case LEVEL_EDIT:
			TickLevelEdit(game);
			break;
			
		case DEATH_SCENE:
			TickBlackScene(game);
			break;
			
		case GAMEOVER_SCENE:
			TickBlackScene(game);
			break;
			
		case LEVEL_LOAD:
			TickBlackScene(game);
			break;
			
		case LEVEL_FINISHED:
			TickLevelFinished(game);
			break;
			
		case CREDITS:
			TickBlackScene(game);
			break;
			
		case CUSTOM_LEVEL_MENU:
			TickCustomLevelMenu(game);
			break;
			
		case LEVEL_TYPE_MENU:
			TickLevelTypeMenu(game);
			break;
			
		case LEVEL_EDIT_PAUSE:
			TickPauseEditMenu(game);
			break;
			
		default:
			break;

		}
		
	}
	
	private static void TickPauseEditMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		PauseEditMenu menu = game.getPauseEditMenu();
		
		try {
			//Detect hovers
			if(menu.getResumeBox().contains(game.getMousePosition()) == true) {
				menu.setHoverResume(true);
			}
			else {
				menu.setHoverResume(false);
			}
			
			if(menu.getDiscardLevelBox().contains(game.getMousePosition()) == true) {
				menu.setHoverDiscardLevel(true);
			}
			else {
				menu.setHoverDiscardLevel(false);
			}
			
			if(menu.getSaveLevelBox().contains(game.getMousePosition()) == true) {
				menu.setHoverSaveLevel(true);
			}
			else {
				menu.setHoverSaveLevel(false);
			}
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		
		if(game.getClicked() == true) {
			if(menu.isHoverResume() == true) {
				game.currentState = GameState.LEVEL_EDIT;
			}
			else if(menu.isHoverDiscardLevel()) {
				game.DiscardLevel();
				game.WriteData(game.getSettingsFile());
				LoadState.Load(game, GameState.MAIN_MENU);
			}
			else if(menu.isHoverSaveLevel() == true) {
				game.SaveLevelEditor();
				game.WriteData(game.getSettingsFile());
				LoadState.Load(game, GameState.MAIN_MENU);
			}
			
			game.setClicked(false);
		}
		
	}
	
	private static void TickLevelTypeMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		TypeSelectMenu menu = game.typeSelectionMenu;
		
		try {
			//Detect hovers
			if(menu.getOverWorldBox().contains(game.getMousePosition()) == true) {
				menu.setHoverOverWorld(true);
			}
			else {
				menu.setHoverOverWorld(false);
			}
			
			if(menu.getUnderGroundBox().contains(game.getMousePosition()) == true) {
				menu.setHoverUnderGround(true);
			}
			else {
				menu.setHoverUnderGround(false);
			}
			
			if(menu.getCastleBox().contains(game.getMousePosition()) == true) {
				menu.setHoverCastle(true);
			}
			else {
				menu.setHoverCastle(false);
			}
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		
		if(game.getClicked() == true) {
			if(menu.isHoverOverWorld() == true) {
				Game.typeToMake = LevelType.OVERWORLD;
				Game.currentLevelType = Game.typeToMake;
				LoadState.Load(game, GameState.LEVEL_EDIT);
				Game.matSelected = Material.BRICK;
			}
			else if(menu.isHoverUnderGround()) {
				Game.typeToMake = LevelType.UNDERWORLD;
				Game.currentLevelType = Game.typeToMake;
				LoadState.Load(game, GameState.LEVEL_EDIT);
				Game.matSelected = Material.BRICK;
			}
			else if(menu.isHoverCastle() == true) {
				Game.typeToMake = LevelType.CASTLE;
				Game.currentLevelType = Game.typeToMake;
				LoadState.Load(game, GameState.LEVEL_EDIT);
				Game.matSelected = Material.BRICK;
			}
			
			game.setClicked(false);
		}
	}
	
	private static void TickCustomLevelMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		CustomLevelMenu menu = game.getCustomLevelMenu();
		
		try {
			if(menu.getLevelOneBox().contains(game.getMousePosition()) == true)
				menu.setHoverLevelOne(true);
			else
				menu.setHoverLevelOne(false);

			if(menu.getLevelTwoBox().contains(game.getMousePosition()) == true)
				menu.setHoverLevelTwo(true);
			else
				menu.setHoverLevelTwo(false);
			
			if(menu.getLevelThreeBox().contains(game.getMousePosition()) == true)
				menu.setHoverLevelThree(true);
			else
				menu.setHoverLevelThree(false);
			
			if(menu.getLevelFourBox().contains(game.getMousePosition()) == true)
				menu.setHoverLevelFour(true);
			else
				menu.setHoverLevelFour(false);
			
			//Detect Hover Main Menu Return
			if(menu.getMainReturnBox().contains(game.getMousePosition()) == true)
				menu.setHoverMainReturn(true);
			else 
				menu.setHoverMainReturn(false);
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		if(game.getClicked() == true) {
			if(Game.inEditMenu == true) {
				Game.loadPreviousLevel = false;
				if(menu.isHoverLevelOne() == true) {
					Game.levelToEdit = "custom/levelOne";
					Game.currentLevel = 1;
					if(Game.customOneExists == true) {
						Game.loadPreviousLevel = true;
						LoadState.Load(game, GameState.LEVEL_EDIT);
						Game.matSelected = Material.BRICK;
					}
					else {
						LoadState.Load(game, GameState.LEVEL_TYPE_MENU);
					}
				}
				else if(menu.isHoverLevelTwo() == true) {
					Game.levelToEdit = "custom/levelTwo";
					Game.currentLevel = 2;
					if(Game.customTwoExists == true) {
						Game.loadPreviousLevel = true;
						LoadState.Load(game, GameState.LEVEL_EDIT);
						Game.matSelected = Material.BRICK;
					}
					else {
						LoadState.Load(game, GameState.LEVEL_TYPE_MENU);
					}
				}
				else if(menu.isHoverLevelThree() == true) {
					Game.levelToEdit = "custom/levelThree";
					Game.currentLevel = 3;
					if(Game.customThreeExists == true) {
						Game.loadPreviousLevel = true;
						LoadState.Load(game, GameState.LEVEL_EDIT);
						Game.matSelected = Material.BRICK;
					}
					else {
						LoadState.Load(game, GameState.LEVEL_TYPE_MENU);
					}
				}
				else if(menu.isHoverLevelFour() == true) {
					Game.levelToEdit = "custom/levelFour";
					Game.currentLevel = 4;
					if(Game.customFourExists == true) {
						Game.loadPreviousLevel = true;
						LoadState.Load(game, GameState.LEVEL_EDIT);
						Game.matSelected = Material.BRICK;
					}
					else {
						LoadState.Load(game, GameState.LEVEL_TYPE_MENU);
					}
				}
			}
			else {
				if(menu.isHoverLevelOne() == true && Game.customOneExists == true) {
					Game.setLevel(5, 1, "custom/levelOne");
					LoadState.Load(game, GameState.GAME_LEVEL);
				}
				else if(menu.isHoverLevelTwo() == true&& Game.customTwoExists == true) {
					Game.setLevel(5, 2, "custom/levelTwo");
					LoadState.Load(game, GameState.GAME_LEVEL);
				}
				else if(menu.isHoverLevelThree() == true && Game.customThreeExists == true) {
					Game.setLevel(5, 3, "custom/levelThree");
					LoadState.Load(game, GameState.GAME_LEVEL);
				}
				else if(menu.isHoverLevelFour() == true && Game.customFourExists == true) {
					Game.setLevel(5, 4, "custom/levelFour");
					LoadState.Load(game, GameState.GAME_LEVEL);
				}
			}
			
			if(menu.getHoverMainReturn() == true) {
				if(Game.inEditMenu == false) {
					LoadState.Load(game, GameState.MAIN_LEVEL_SELECT);
				}
				else {
					LoadState.Load(game, GameState.MAIN_MENU);
				}
			}
			
			game.setClicked(false);
		}
		
	}
	
	private static void TickLevelSelectMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		LevelSelectMenu menu = game.getLevelSelectMenu();
		
		try {
			//Detect Hovers World One
			if(menu.getWorldOneLevelOneBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldOneLevelOne(true);
			else
				menu.setHoverWorldOneLevelOne(false);

			if(menu.getWorldOneLevelTwoBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldOneLevelTwo(true);
			else
				menu.setHoverWorldOneLevelTwo(false);
			
			if(menu.getWorldOneLevelThreeBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldOneLevelThree(true);
			else
				menu.setHoverWorldOneLevelThree(false);
			
			if(menu.getWorldOneLevelFourBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldOneLevelFour(true);
			else
				menu.setHoverWorldOneLevelFour(false);
			
			//Detect Hovers World Two
			if(menu.getWorldTwoLevelOneBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldTwoLevelOne(true);
			else
				menu.setHoverWorldTwoLevelOne(false);

			if(menu.getWorldTwoLevelTwoBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldTwoLevelTwo(true);
			else
				menu.setHoverWorldTwoLevelTwo(false);
			
			if(menu.getWorldTwoLevelThreeBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldTwoLevelThree(true);
			else
				menu.setHoverWorldTwoLevelThree(false);
			
			if(menu.getWorldTwoLevelFourBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldTwoLevelFour(true);
			else
				menu.setHoverWorldTwoLevelFour(false);
			
			//Detect Hovers World Three
			if(menu.getWorldThreeLevelOneBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldThreeLevelOne(true);
			else
				menu.setHoverWorldThreeLevelOne(false);

			if(menu.getWorldThreeLevelTwoBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldThreeLevelTwo(true);
			else
				menu.setHoverWorldThreeLevelTwo(false);
			
			if(menu.getWorldThreeLevelThreeBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldThreeLevelThree(true);
			else
				menu.setHoverWorldThreeLevelThree(false);
			
			if(menu.getWorldThreeLevelFourBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldThreeLevelFour(true);
			else
				menu.setHoverWorldThreeLevelFour(false);
			
			//Detect Hovers World Four
			if(menu.getWorldFourLevelOneBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldFourLevelOne(true);
			else
				menu.setHoverWorldFourLevelOne(false);

			if(menu.getWorldFourLevelTwoBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldFourLevelTwo(true);
			else
				menu.setHoverWorldFourLevelTwo(false);
			
			if(menu.getWorldFourLevelThreeBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldFourLevelThree(true);
			else
				menu.setHoverWorldFourLevelThree(false);
			
			if(menu.getWorldFourLevelFourBox().contains(game.getMousePosition()) == true)
				menu.setHoverWorldFourLevelFour(true);
			else
				menu.setHoverWorldFourLevelFour(false);
			
			//Detect Hover Main Menu Return
			if(menu.getMainReturnBox().contains(game.getMousePosition()) == true)
				menu.setHoverMainReturn(true);
			else 
				menu.setHoverMainReturn(false);
			
			//Detect Hover Custom Level button
			if(menu.getCustomLevelBox().contains(game.getMousePosition()) == true)
				menu.setHoverCustomLevel(true);
			else 
				menu.setHoverCustomLevel(false);
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		if(game.getClicked() == true) {
			if(menu.isHoverWorldOneLevelOne() == true) { //World One
				Game.setLevel(1, 1, "worldOne/levelOne");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldOneLevelTwo() == true) {
				Game.setLevel(1, 2, "worldOne/levelTwo");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldOneLevelThree() == true) {
				Game.setLevel(1, 3, "worldOne/levelThree");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldOneLevelFour() == true) {
				Game.setLevel(1, 4, "worldOne/levelFour");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldTwoLevelOne() == true) { //World Two
				Game.setLevel(2, 1, "worldTwo/levelOne");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldTwoLevelTwo() == true) {
				Game.setLevel(2, 2, "worldTwo/levelTwo");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldTwoLevelThree() == true) {
				Game.setLevel(2, 3, "worldTwo/levelThree");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldTwoLevelFour() == true) {
				Game.setLevel(2, 4, "worldTwo/levelFour");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldThreeLevelOne() == true) { //World Three
				Game.setLevel(3, 1, "worldThree/levelOne");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldThreeLevelTwo() == true) {
				Game.setLevel(3, 2, "worldThree/levelTwo");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldThreeLevelThree() == true) {
				Game.setLevel(3, 3, "worldThree/levelThree");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldThreeLevelFour() == true) {
				Game.setLevel(3, 4, "worldThree/levelFour");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldFourLevelOne() == true) { //World Four
				Game.setLevel(4, 1, "worldFour/levelOne");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldFourLevelTwo() == true) {
				Game.setLevel(4, 2, "worldFour/levelTwo");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldFourLevelThree() == true) {
				Game.setLevel(4, 3, "worldFour/levelThree");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.isHoverWorldFourLevelFour() == true) {
				Game.setLevel(4, 4, "worldFour/levelFour");
				LoadState.Load(game, GameState.GAME_LEVEL);
			}
			else if(menu.getHoverMainReturn() == true) {
				LoadState.Load(game, GameState.MAIN_MENU);
			}
			else if(menu.getHoverCustomLevel() == true) {
				LoadState.Load(game, GameState.CUSTOM_LEVEL_MENU);
			}
			
			game.setClicked(false);
		}
		
	}
	
	private static void TickSettingsMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		try {
			//Detect hovers
			if(game.getSettingsMenu().getMusicToggleBox().contains(game.getMousePosition()) == true) {
				game.getSettingsMenu().setHoverMusicToggle(true);
			}
			else {
				game.getSettingsMenu().setHoverMusicToggle(false);
			}
			
			if(game.getSettingsMenu().getSoundToggleBox().contains(game.getMousePosition()) == true) {
				game.getSettingsMenu().setHoverSoundToggle(true);
			}
			else {
				game.getSettingsMenu().setHoverSoundToggle(false);
			}
			
			if(game.getSettingsMenu().getvSyncToggleBox().contains(game.getMousePosition()) == true) {
				game.getSettingsMenu().setHoverVSyncToggle(true);
			}
			else {
				game.getSettingsMenu().setHoverVSyncToggle(false);
			}
			
			if(game.getSettingsMenu().getMenuReturnBox().contains(game.getMousePosition()) == true) {
				game.getSettingsMenu().setHoverMenuReturn(true);
			}
			else {
				game.getSettingsMenu().setHoverMenuReturn(false);
			}
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		if(game.getClicked() == true) {
			if(game.getSettingsMenu().isHoverMusicToggle() == true) {
				Game.musicToggle = !Game.musicToggle;
				game.getSettingsMenu().CalculateLocations(game);
				game.WriteData(game.getSettingsFile());
			}
			else if(game.getSettingsMenu().isHoverSoundToggle() == true) {
				Game.soundsToggle = !Game.soundsToggle;
				game.getSettingsMenu().CalculateLocations(game);
				game.WriteData(game.getSettingsFile());
			}
			else if(game.getSettingsMenu().isHoverVSyncToggle() == true) {
				game.setvSyncToggle(!(game.isvSyncToggle()));
				game.getSettingsMenu().CalculateLocations(game);
				game.WriteData(game.getSettingsFile());
			}
			else if(game.getSettingsMenu().isHoverMenuReturn() == true) {
				if(Game.currentLevel == 0) {
					LoadState.Load(game, GameState.MAIN_MENU);
				}
				else {
					//If accessed from in game, return to the pause menu
					LoadState.Load(game, GameState.GAME_PAUSED);
				}
			}
			
			game.setClicked(false);
		}
		
	}
	
	private static void TickLevelFinished(Game game) {
		Player thePlayer = game.GetThePlayer();
		thePlayer.tick(); //Allows the player to finish falling from the flag pole.
		
		//Ticks the fireworks ONLY
		for(int i = 0; i < game.getItemList().size(); i++) {
			if(game.getItemList().get(i).GetItemType() == ItemType.FIREWORK) {
				game.getItemList().get(i).tick();
			}
		}
		
		//Tick the boss if its level 4. This is so it can fall if it wasnt killed
		if(Game.currentLevelType == LevelType.CASTLE) {
			for(Entity e : game.getEntityList()) {
				if(e.getEntityType() == EntityType.BOSS) {
					e.tick(game);
				}
			}
		}
		
		if(game.getTimer() > 0) {
			if(game.getTimer() > 0) {
				game.setTimer(game.getTimer() - 1);
				thePlayer.setPlayerScore(thePlayer.getPlayerScore() + 10);
			}
		}
		else {
			if(thePlayer.getLastTimeDigit() == 1) {
				if(game.getCounter() == 30) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX(), thePlayer.getY() - (Tile.TILE_SIZE * 10), "east"));
				}
				else if(game.getCounter() > 150) {
					if(Game.mainGame == true) LoadState.Load(game, GameState.LEVEL_LOAD);
					else LoadState.Load(game, GameState.MAIN_MENU);
				}
			}
			else if (thePlayer.getLastTimeDigit() == 3) {
				if(game.getCounter() == 30) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX(), thePlayer.getY() - (Tile.TILE_SIZE * 10), "east"));
				}
				else if(game.getCounter() == 60) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX() + (Tile.TILE_SIZE * 2), thePlayer.getY() - (Tile.TILE_SIZE * 9), "east"));
				}
				else if(game.getCounter() == 90) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX() - (Tile.TILE_SIZE * 3), thePlayer.getY() - (Tile.TILE_SIZE * 9), "east"));
				}
				else if(game.getCounter() > 180) {
					if(Game.mainGame == true) LoadState.Load(game, GameState.LEVEL_LOAD);
					else LoadState.Load(game, GameState.MAIN_MENU);
				}
			}
			else if (thePlayer.getLastTimeDigit() == 6) {
				if(game.getCounter() == 30) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX(), thePlayer.getY() - (Tile.TILE_SIZE * 10), "east"));
				}
				else if(game.getCounter() == 60) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX() + (Tile.TILE_SIZE * 2), thePlayer.getY() - (Tile.TILE_SIZE * 8), "east"));
				}
				else if(game.getCounter() == 90) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX() - (Tile.TILE_SIZE * 3), thePlayer.getY() - (Tile.TILE_SIZE * 8), "east"));
				}
				else if(game.getCounter() == 120) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX(), thePlayer.getY() - (Tile.TILE_SIZE * 8), "east"));
				}
				else if(game.getCounter() == 150) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX() - (Tile.TILE_SIZE * 4), thePlayer.getY() - (Tile.TILE_SIZE * 10), "east"));
				}
				else if(game.getCounter() == 180) {
					game.getItemList().add(new Item(ItemType.FIREWORK, game, thePlayer.getX() + (Tile.TILE_SIZE * 2), thePlayer.getY() - (Tile.TILE_SIZE * 12), "east"));
				}
				else if(game.getCounter() > 270) {
					if(Game.mainGame == true) LoadState.Load(game, GameState.LEVEL_LOAD);
					else LoadState.Load(game, GameState.MAIN_MENU);
				}
			}
			else {
				if(game.getCounter() > 60) {
					if(Game.mainGame == true) LoadState.Load(game, GameState.LEVEL_LOAD);
					else LoadState.Load(game, GameState.MAIN_MENU);
				}
			}
			
			game.setCounter(game.getCounter() + 1);
		}
	}
	
	private static void TickBlackScene(Game game) {
		if(game.currentState == GameState.LEVEL_LOAD) {
			if(game.getCounter() > 90) {
				LoadState.Load(game, GameState.GAME_LEVEL);
				if(Game.musicToggle == true) game.getSoundManager().restartMusic();
			}
			else {
				game.setCounter(game.getCounter() + 1);
			}
		}
		else if(game.currentState == GameState.CREDITS) {
			if(game.getClicked() == true) {
				LoadState.Load(game, GameState.MAIN_MENU);
			}
		}
		else {
			if(game.getCounter() > 180) {
				if(game.currentState == GameState.DEATH_SCENE) {
					game.currentState = GameState.GAME_LEVEL;
					if(Game.musicToggle == true) game.getSoundManager().restartMusic();
				}
				else if(game.currentState == GameState.GAMEOVER_SCENE){
					LoadState.Load(game, GameState.MAIN_MENU);
				}
			}
			else {
				game.setCounter(game.getCounter() + 1);
			}
		}
	}
	
	private static void TickPauseMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		try {
			//Detect hovers
			if(game.GetPauseMenu().getResumeBox().contains(game.getMousePosition()) == true) {
				game.GetPauseMenu().setHoverResume(true);
			}
			else {
				game.GetPauseMenu().setHoverResume(false);
			}
			
			if(game.GetPauseMenu().getSettingsBox().contains(game.getMousePosition()) == true) {
				game.GetPauseMenu().setHoverSettings(true);
			}
			else {
				game.GetPauseMenu().setHoverSettings(false);
			}
			
			if(game.GetPauseMenu().getReturnMenuBox().contains(game.getMousePosition()) == true) {
				game.GetPauseMenu().setHoverReturnMenu(true);
			}
			else {
				game.GetPauseMenu().setHoverReturnMenu(false);
			}
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		
		if(game.getClicked() == true) {
			if(game.GetPauseMenu().isHoverResume() == true) {
				game.currentState = GameState.GAME_LEVEL;
				game.getSoundManager().resumeMusic();
				
				if(Game.musicToggle == true) {
					if(game.getSoundManager().isRunning() == false) {
						if(Game.currentLevelType == LevelType.OVERWORLD) game.getSoundManager().PlayMusic(MusicEffect.OVER_THEME);
						else if(Game.currentLevelType == LevelType.UNDERWORLD) game.getSoundManager().PlayMusic(MusicEffect.UNDER_THEME);
						else if(Game.currentLevelType == LevelType.CASTLE) game.getSoundManager().PlayMusic(MusicEffect.CASTLE_THEME);
						else System.out.println("Invalid level: " + Game.currentLevelType.toString());
					}
				}
				
			}
			else if(game.GetPauseMenu().isHoverSettings()) LoadState.Load(game, GameState.MAIN_SETTINGS);
			else if(game.GetPauseMenu().isHoverReturnMenu() == true) LoadState.Load(game, GameState.MAIN_MENU);
			
			game.setClicked(false);
		}
		
	}
	
	private static void TickGameLevel(Game game) {
		Player thePlayer = game.GetThePlayer();
		
		if(Game.musicToggle == false) {
			game.getSoundManager().stopMusic();
		}
		
		if(game.getCounter() > 60) {
			game.setCounter(0);
			game.setTimer(game.getTimer() - 1);
		}
		else {
			game.setCounter(game.getCounter() + 1);
		}
		
		if(game.getTimer() == 60 && game.getCounter() == 0) {
			game.getSoundManager().PlayMusic(MusicEffect.TIME_WARNING);
		}
		else if(game.getTimer() == 57 && game.getCounter() == 0) {
			if(Game.currentLevelType == LevelType.OVERWORLD) game.getSoundManager().PlayMusic(MusicEffect.FAST_OVER_THEME);
			else if(Game.currentLevelType == LevelType.UNDERWORLD) game.getSoundManager().PlayMusic(MusicEffect.FAST_UNDER_THEME);
			else if(Game.currentLevelType == LevelType.CASTLE) game.getSoundManager().PlayMusic(MusicEffect.FAST_CASTLE_THEME);
		}
		
		if(thePlayer.isLevelDone() == true) {
			if(Game.currentLevelType != LevelType.CASTLE) {
				game.getSoundManager().PlayMusic(MusicEffect.STAGE_CLEAR);
			}
			else {
				if(Game.currentWorld == 3) { //3 because the max worlds are 3
					game.getSoundManager().PlayMusic(MusicEffect.BEAT_GAME);
					LoadState.Load(game, GameState.CREDITS);
					return;
				}
				else {
					game.getSoundManager().PlayMusic(MusicEffect.CASTLE_CLEAR);
				}
				
				for(Entity e : game.getEntityList()) {
					if(e.getEntityType() == EntityType.BOSS && e.isAlive() == true) {
						game.getSoundManager().PlaySound(SoundEffect.BOSS_FALL);
					}
				}
				
			}
			LoadState.Load(game, GameState.LEVEL_FINISHED);
			return;
		}
		else if(thePlayer.isAlive() == false) {
			if(game.GetThePlayer().getPlayerLives() <= 0) {
				LoadState.Load(game, GameState.GAMEOVER_SCENE);
				game.getSoundManager().PlayMusic(MusicEffect.GAME_OVER);
			}
			else {
				thePlayer.setAlive(true);
				LoadState.Load(game, GameState.DEATH_SCENE);
				game.getSoundManager().PlayMusic(MusicEffect.PLAYER_DIE);
			}
		}
		
		thePlayer.tick();
		
		//Tick each bulletstation
		for(BulletStation b : game.getBulletStations()) {
			b.tick(game);
		}
		
		//Tick each entity
		ArrayList<Entity> tempEntity = game.getEntityList();
		for(Entity e : tempEntity) {
			e.tick(game);
		}
		game.SetEntityList(tempEntity);
		
		//Tick each fireball
		ArrayList<Fireball> tempFire = game.getFireballList();
		for(Fireball f : tempFire) {
			f.tick(game);
		}
		game.SetFireballList(tempFire);
		
		//Tick each item
		ArrayList<Item> tempItem = game.getItemList();
		for(Item i : tempItem) {
			i.tick();
		}
		game.SetItemList(tempItem);
		
		//Tick each pipe
		ArrayList<Pipe> tempPipe = game.getPipeList();
		for(Pipe p : tempPipe) {
			p.tick();
		}
		game.SetPipeList(tempPipe);
		
		//Tick each fireWheel
		ArrayList<FireWheel> tempFireWheel = game.getFireWheelList();
		for(FireWheel fw : tempFireWheel) {
			fw.tick(game);
		}
		game.SetFireWheelList(tempFireWheel);
		
		//Tick blocks (for bouncing animation)
		for(int i = 0; i < World.ROWS; i++){
			for(int j = 0; j < World.cols; j++){
				if(World.tiledMap[i][j].getMaterial() != Material.AIR) {
					World.tiledMap[i][j].tick(game);
				}
			}
		}
		
		if(thePlayer.GetRightHeld() == true) {
			thePlayer.move(KeyEvent.VK_RIGHT);
			thePlayer.SetCrouchHeld(false);
		}
		
		if(thePlayer.GetLeftHeld() == true) {
			thePlayer.move(KeyEvent.VK_LEFT);
			thePlayer.SetCrouchHeld(false);
		}
		
		thePlayer.setCrouch(thePlayer.GetCrouchHeld());

		if(thePlayer.GetLeftHeld() == false && thePlayer.GetRightHeld() == false) {
			thePlayer.stop();
		}
		
		int counter = thePlayer.GetFireballCounter();
		
		thePlayer.SetFireballCounter(counter + 1);
		if(counter >= 25) {
			thePlayer.SetCanShootFireball(true);
			thePlayer.SetFireballCounter(0);
		}
		
		//Run animations for the blocks that have it.
		for(int i = 0; i < World.ROWS; i++) {
			for(int j = 0; j < World.cols; j++) {
				if(World.tiledMap[i][j].getMaterial().isAnimated() == true) {
					if(World.tiledMap[i][j].GetAnimationCounter() > 10) {
						World.tiledMap[i][j].SetAnimationCounter(0);
						World.tiledMap[i][j].RunAnimation();
					}
					else {
						World.tiledMap[i][j].SetAnimationCounter(World.tiledMap[i][j].GetAnimationCounter() + 1);
					}
				}
			}
		}
		
		thePlayer = null;
	}
	
	private static void TickMainMenu(Game game) {
		//Only process mouse activity if the mouse is on screen
		if(game.getMousePosition() == null) return;
		
		try {
			//Detect hovers
			if(game.GetMainMenu().getStartGameBox().contains(game.getMousePosition()) == true) {
				game.GetMainMenu().setHoverStart(true);
			}
			else {
				game.GetMainMenu().setHoverStart(false);
			}
			
			if(game.GetMainMenu().getLevelSelectBox().contains(game.getMousePosition()) == true) {
				game.GetMainMenu().setHoverLevelSelect(true);
			}
			else {
				game.GetMainMenu().setHoverLevelSelect(false);
			}
			
			if(game.GetMainMenu().getLevelEditBox().contains(game.getMousePosition()) == true) {
				game.GetMainMenu().setHoverLevelEdit(true);
			}
			else {
				game.GetMainMenu().setHoverLevelEdit(false);
			}
			
			if(game.GetMainMenu().getSettingsBox().contains(game.getMousePosition()) == true) {
				game.GetMainMenu().setHoverSettings(true);
			}
			else {
				game.GetMainMenu().setHoverSettings(false);
			}
			
			if(game.GetMainMenu().getExitGameBox().contains(game.getMousePosition()) == true) {
				game.GetMainMenu().setHoverExitGame(true);
			}
			else {
				game.GetMainMenu().setHoverExitGame(false);
			}
		}
		catch(NullPointerException e){
			System.out.println("Null caught");
		}
		
		
		if(game.getClicked() == true) {
			if(game.GetMainMenu().isHoverStart() == true) {
				LoadState.Load(game, GameState.START_MAIN_GAME);
			}
			else if(game.GetMainMenu().isHoverLevelSelect() == true) {
				LoadState.Load(game, GameState.MAIN_LEVEL_SELECT);
			}
			else if(game.GetMainMenu().isHoverLevelEdit() == true) {
				LoadState.Load(game, GameState.CUSTOM_LEVEL_MENU);
			}
			else if(game.GetMainMenu().isHoverSettings() == true) {
				LoadState.Load(game, GameState.MAIN_SETTINGS);
			}
			else if(game.GetMainMenu().isHoverExitGame() == true) {
				game.CloseGame();
			}
			
			game.setClicked(false);
		}
	}
	
	private static void TickLevelEdit(Game game) {
		
		if(Game.mouseHeld == true || game.getClicked()) {
			game.getBlockSelector().tick(game);
			game.setClicked(false);
			
			if(game.getBlockSelector().getBoundingBox().contains(game.getMouseLoc().getX() + game.GetCamera().GetX(), game.getMouseLoc().getY() + game.GetCamera().GetY()) && Game.blockSelect == true) return;
			
			//Checking pole status
			int poleTopX = game.getBlockSelector().getPoleX();
			int poleTopY = game.getBlockSelector().getPoleY();
			if(game.getBlockSelector().isPolePlaced() == true) {
				if(World.tiledMap[poleTopX][poleTopY].getMaterial() != Material.POLE_TOP || World.tiledMap[poleTopX + 1][poleTopY].getMaterial() != Material.POLE_SECTION || 
						World.tiledMap[poleTopX + 2][poleTopY].getMaterial() != Material.POLE_SECTION || World.tiledMap[poleTopX + 3][poleTopY].getMaterial() != Material.POLE_SECTION || 
						World.tiledMap[poleTopX + 4][poleTopY].getMaterial() != Material.POLE_SECTION || World.tiledMap[poleTopX + 5][poleTopY].getMaterial() != Material.POLE_SECTION || 
						World.tiledMap[poleTopX + 6][poleTopY].getMaterial() != Material.POLE_SECTION || World.tiledMap[poleTopX + 7][poleTopY].getMaterial() != Material.POLE_SECTION || 
						World.tiledMap[poleTopX + 8][poleTopY].getMaterial() != Material.POLE_BOTTOM)  {
					World.tiledMap[poleTopX][poleTopY] = new Block(Material.AIR, poleTopX, poleTopY);
					World.tiledMap[poleTopX + 1][poleTopY] = new Block(Material.AIR, poleTopX + 1, poleTopY);
					World.tiledMap[poleTopX + 2][poleTopY] = new Block(Material.AIR, poleTopX + 2, poleTopY);
					World.tiledMap[poleTopX + 3][poleTopY] = new Block(Material.AIR, poleTopX + 3, poleTopY);
					World.tiledMap[poleTopX + 4][poleTopY] = new Block(Material.AIR, poleTopX + 4, poleTopY);
					World.tiledMap[poleTopX + 5][poleTopY] = new Block(Material.AIR, poleTopX + 5, poleTopY);
					World.tiledMap[poleTopX + 6][poleTopY] = new Block(Material.AIR, poleTopX + 6, poleTopY);
					World.tiledMap[poleTopX + 7][poleTopY] = new Block(Material.AIR, poleTopX + 7, poleTopY);
					World.tiledMap[poleTopX + 8][poleTopY] = new Block(Material.AIR, poleTopX + 7, poleTopY);
					game.getBlockSelector().setPolePlaced(false);
				}
			}
			
			for(int i = 0; i < World.ROWS; i++) {
				for(int j = 0; j < World.cols; j++) {
					
					Material m = World.tiledMap[i][j].getMaterial();
					
					//Checking to make sure multiblock structures are whole
					if(m == Material.BULLET_TOP && i < World.ROWS) {
						if(World.tiledMap[i + 1][j].getMaterial() != Material.BULLET_BOTTOM) {
							World.tiledMap[i][j] = new Block(Material.AIR, i, j);
							World.tiledMap[i + 1][j] = new Block(Material.AIR, i + 1, j);
						}
					}
					else if(m == Material.BULLET_BOTTOM && i > 0) {
						if(World.tiledMap[i - 1][j].getMaterial() != Material.BULLET_TOP) {
							World.tiledMap[i][j] = new Block(Material.AIR, i, j);
							World.tiledMap[i - 1][j] = new Block(Material.AIR, i - 1, j);
						}
					}
					else if(m == Material.PIPE_TOP_LEFT) {
						if(World.tiledMap[i][j + 1].getMaterial() != Material.PIPE_TOP_RIGHT) {
							World.tiledMap[i][j] = new Block(Material.AIR, i, j);
							World.tiledMap[i][j + 1] = new Block(Material.AIR, i, j + 1);
							int distDown = 1;
							while(World.tiledMap[i + distDown][j].getMaterial() == Material.PIPE_LEFT || World.tiledMap[i + distDown][j + 1].getMaterial() == Material.PIPE_RIGHT) {
								World.tiledMap[i + distDown][j] = new Block(Material.AIR, i + distDown, j);
								World.tiledMap[i + distDown][j + 1] = new Block(Material.AIR, i + distDown, j + 1);
								distDown++;
							}
						}
					}
					else if(m == Material.PIPE_TOP_RIGHT) {
						if(World.tiledMap[i][j - 1].getMaterial() != Material.PIPE_TOP_LEFT) {
							World.tiledMap[i][j] = new Block(Material.AIR, i, j);
							World.tiledMap[i][j - 1] = new Block(Material.AIR, i, j - 1);
							int distDown = 1;
							while(World.tiledMap[i + distDown][j].getMaterial() == Material.PIPE_RIGHT || World.tiledMap[i + distDown][j - 1].getMaterial() == Material.PIPE_LEFT) {
								World.tiledMap[i + distDown][j] = new Block(Material.AIR, i + distDown, j);
								World.tiledMap[i + distDown][j - 1] = new Block(Material.AIR, i + distDown, j - 1);
								distDown++;
							}
						}
					}
					else if(m == Material.PIPE_LEFT || m == Material.PIPE_RIGHT) {
						
						if(m == Material.PIPE_LEFT) {
							if(World.tiledMap[i][j + 1].getMaterial() != Material.PIPE_RIGHT) {
								World.tiledMap[i][j] = new Block(Material.AIR, i, j);
							}
						}
						else if(m == Material.PIPE_RIGHT) {
							if(World.tiledMap[i][j - 1].getMaterial() != Material.PIPE_LEFT) {
								World.tiledMap[i][j] = new Block(Material.AIR, i, j);
							}
						}
						
						boolean flag = false;
						for(int x = 0; i - x > 0; x++) {
							if(World.tiledMap[i - x][j].getMaterial() == Material.PIPE_TOP_LEFT || World.tiledMap[i - x][j].getMaterial() == Material.PIPE_TOP_RIGHT) {
								flag = true;
								break;
							}
							else if(World.tiledMap[i - x][j].getMaterial() == Material.AIR) {
								flag = false;
								break;
							}
						}
						if(flag == false) {
							World.tiledMap[i][j] = new Block(Material.AIR, i, j);
						}
					}
					else if(m == Material.GRASS_MIDDLE) {
						if(World.tiledMap[i][j + 1].getMaterial() == Material.GRASS_LEFT || World.tiledMap[i][j - 1].getMaterial() == Material.GRASS_RIGHT) {
							World.tiledMap[i][j] = new Block(Material.GRASS_MIDDLE, i, j);
						}
						else if(World.tiledMap[i][j - 1].getMaterial() != Material.GRASS_MIDDLE && World.tiledMap[i][j - 1].getMaterial() != Material.GRASS_LEFT && World.tiledMap[i][j + 1].getMaterial() == Material.GRASS_MIDDLE) {
							World.tiledMap[i][j] = new Block(Material.GRASS_LEFT, i, j);
						}
						else if(World.tiledMap[i][j + 1].getMaterial() != Material.GRASS_MIDDLE && World.tiledMap[i][j + 1].getMaterial() != Material.GRASS_RIGHT && World.tiledMap[i][j - 1].getMaterial() == Material.GRASS_MIDDLE) {
							World.tiledMap[i][j] = new Block(Material.GRASS_RIGHT, i, j);
						}
					}
					else if(m == Material.GRASS_RIGHT) {
						if(World.tiledMap[i][j + 1].getMaterial() == Material.GRASS_RIGHT) {
							World.tiledMap[i][j] = new Block(Material.GRASS_MIDDLE, i, j);
						}
						else if(World.tiledMap[i][j + 1].getMaterial() == Material.GRASS_MIDDLE) {
							World.tiledMap[i][j] = new Block(Material.GRASS_MIDDLE, i, j);
						}
					}
					else if(m == Material.GRASS_LEFT) {
						if(World.tiledMap[i][j - 1].getMaterial() == Material.GRASS_LEFT) {
							World.tiledMap[i][j] = new Block(Material.GRASS_MIDDLE, i, j);
						}
						else if(World.tiledMap[i][j - 1].getMaterial() == Material.GRASS_MIDDLE) {
							World.tiledMap[i][j] = new Block(Material.GRASS_MIDDLE, i, j);
						}
					}
					
					//Checking click status
					if(World.tiledMap[i][j].getBoundingBox().contains(game.getMouseLoc().getX() + game.GetCamera().GetX(), game.getMouseLoc().getY() + game.GetCamera().GetY())) {
						if(game.GetClickType() == 1) { //Left click
							
							if(Game.matSelected == Material.BULLET_TOP) {
								if(m == Material.BULLET_TOP) { //Change the direction
									if(System.currentTimeMillis() -  game.getTimeLastClicked() < 250) return;
									if(World.tiledMap[i][j].getDirection().toLowerCase().contains("left")) World.tiledMap[i][j].setDirection("right");
									else if(World.tiledMap[i][j].getDirection().toLowerCase().contains("right")) World.tiledMap[i][j].setDirection("both");
									else if(World.tiledMap[i][j].getDirection().toLowerCase().contains("both")) World.tiledMap[i][j].setDirection("left");
								}
								else { //Place the bullet station
									if(i + 1 < World.ROWS) {
										World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
										World.tiledMap[i][j].setDirection("both");
										World.tiledMap[i + 1][j] = new Block(Material.BULLET_BOTTOM, i + 1, j);
									}
								}
							}
							else if(Game.matSelected == Material.POLE_TOP) {
								
								if(i + 8 < World.ROWS) {
									
									//Delete old pole if it exists
									if(game.getBlockSelector().isPolePlaced() == true) {
										int poleTopLocX = game.getBlockSelector().getPoleX();
										int poleTopLocY = game.getBlockSelector().getPoleY();
										World.tiledMap[poleTopLocX][poleTopLocY] = new Block(Material.AIR, poleTopLocX, poleTopLocY);
										World.tiledMap[poleTopLocX + 1][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 1, poleTopLocY);
										World.tiledMap[poleTopLocX + 2][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 2, poleTopLocY);
										World.tiledMap[poleTopLocX + 3][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 3, poleTopLocY);
										World.tiledMap[poleTopLocX + 4][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 4, poleTopLocY);
										World.tiledMap[poleTopLocX + 5][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 5, poleTopLocY);
										World.tiledMap[poleTopLocX + 6][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 6, poleTopLocY);
										World.tiledMap[poleTopLocX + 7][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 7, poleTopLocY);
										World.tiledMap[poleTopLocX + 8][poleTopLocY] = new Block(Material.AIR, poleTopLocX + 8, poleTopLocY);
									}
									
									//Add new pole
									World.tiledMap[i][j] = new Block(Material.POLE_TOP, i, j);
									World.tiledMap[i + 1][j] = new Block(Material.POLE_SECTION, i + 1, j);
									World.tiledMap[i + 2][j] = new Block(Material.POLE_SECTION, i + 2, j);
									World.tiledMap[i + 3][j] = new Block(Material.POLE_SECTION, i + 3, j);
									World.tiledMap[i + 4][j] = new Block(Material.POLE_SECTION, i + 4, j);
									World.tiledMap[i + 5][j] = new Block(Material.POLE_SECTION, i + 5, j);
									World.tiledMap[i + 6][j] = new Block(Material.POLE_SECTION, i + 6, j);
									World.tiledMap[i + 7][j] = new Block(Material.POLE_SECTION, i + 7, j);
									World.tiledMap[i + 8][j] = new Block(Material.POLE_BOTTOM, i + 8, j);
									game.getBlockSelector().setPolePlaced(true);
									game.getBlockSelector().setPoleX(i);
									game.getBlockSelector().setPoleY(j);
								}
							}
							else if(Game.matSelected == Material.PLAYER_ICON) {
								if(game.getBlockSelector().isSpawnPointPlaced() == true) {
									int spawnLocX = game.getBlockSelector().getSpawnPointX();
									int spawnLocY = game.getBlockSelector().getSpawnPointY();
									if(World.tiledMap[spawnLocX][spawnLocY].getMaterial() == Material.PLAYER_ICON)
										World.tiledMap[spawnLocX][spawnLocY] = new Block(Material.AIR, spawnLocX, spawnLocY);
								}
								World.tiledMap[i][j] = new Block(Material.PLAYER_ICON, i, j);
								game.getBlockSelector().setSpawnPointX(i);
								game.getBlockSelector().setSpawnPointY(j);
								game.getBlockSelector().setSpawnPointPlaced(true);
							}
							else if(Game.matSelected == Material.KEY_ICON) {
								if(game.getBlockSelector().isKeyPlaced() == true) {
									int keyX = game.getBlockSelector().getKeyX();
									int keyY = game.getBlockSelector().getKeyY();
									World.tiledMap[keyX][keyY] = new Block(Material.AIR, keyX, keyY);
								}
								World.tiledMap[i][j] = new Block(Material.KEY_ICON, i, j);
								game.getBlockSelector().setKeyX(i);
								game.getBlockSelector().setKeyY(j);
								game.getBlockSelector().setKeyPlaced(true);
							}
							else if(Game.matSelected == Material.BOSS_ICON) {
								if(game.getBlockSelector().isBossPlaced() == true) {
									int bossX = game.getBlockSelector().getBossX();
									int bossY = game.getBlockSelector().getBossY();
									World.tiledMap[bossX][bossY] = new Block(Material.AIR, bossX, bossY);
								}
								World.tiledMap[i][j] = new Block(Material.BOSS_ICON, i, j);
								game.getBlockSelector().setBossX(i);
								game.getBlockSelector().setBossY(j);
								game.getBlockSelector().setBossPlaced(true);
							}
							else if(Game.matSelected == Material.MUSHROOM_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsItem(true);
									World.tiledMap[i][j].setItemType(ItemType.MUSHROOM);
								}
							}
							else if(Game.matSelected == Material.FLOWER_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsItem(true);
									World.tiledMap[i][j].setItemType(ItemType.FLOWER);
								}
							}
							else if(Game.matSelected == Material.ONEUP_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsItem(true);
									World.tiledMap[i][j].setItemType(ItemType.ONEUP);
								}
							}
							else if(Game.matSelected == Material.GOOMBA_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsEntity(true);
									World.tiledMap[i][j].setEntityType(EntityType.GOOMBA);
								}
								else World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
							}
							else if(Game.matSelected == Material.GREEN_TURTLE_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsEntity(true);
									World.tiledMap[i][j].setEntityType(EntityType.GREEN_TURTLE);
								}
								else World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
							}
							else if(Game.matSelected == Material.JUMP_GREEN_TURTLE_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsEntity(true);
									World.tiledMap[i][j].setEntityType(EntityType.JUMP_GREEN_TURTLE);
								}
								else World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
							}
							else if(Game.matSelected == Material.RED_TURTLE_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsEntity(true);
									World.tiledMap[i][j].setEntityType(EntityType.RED_TURTLE);
								}
								else World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
							}
							else if(Game.matSelected == Material.JUMP_RED_TURTLE_ICON) {
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									World.tiledMap[i][j].setContainsEntity(true);
									World.tiledMap[i][j].setEntityType(EntityType.JUMP_RED_TURTLE);
								}
								else World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
							}
							else if(Game.matSelected == Material.COIN_ICON) {
								//System.out.println("Current: " + System.currentTimeMillis() + " LAST: " + game.getTimeLastClicked() + " SUB: " + (System.currentTimeMillis() -  game.getTimeLastClicked()));
								
								if(m == Material.QUESTION || m == Material.BRICK || m == Material.HIDDEN_BLOCK) {
									
									if(World.tiledMap[i][j].GetHoldItem() == true && World.tiledMap[i][j].getItemType() == ItemType.COIN) { //If there is already a coin in there apply extra coin.
										if(System.currentTimeMillis() -  game.getTimeLastClicked() < 500) return;
										World.tiledMap[i][j].setExtraCoin(true);
									}
									else {
										World.tiledMap[i][j].setContainsItem(true);
										World.tiledMap[i][j].setItemType(ItemType.COIN);
									}
								}
								else {
									World.tiledMap[i][j] = new Block(Material.COIN_ICON, i, j);
								}
							}
							else if(Game.matSelected == Material.PLANT_ICON) {
								if(m == Material.PIPE_TOP_LEFT) {
									World.tiledMap[i][j].setContainsEntity(true); 
									World.tiledMap[i][j].setEntityType(EntityType.PLANT);
								}
								else if(m == Material.PIPE_TOP_RIGHT) {
									World.tiledMap[i][j - 1].setContainsEntity(true); 
									World.tiledMap[i][j - 1].setEntityType(EntityType.PLANT);
								}
							}
							else if(Game.matSelected == Material.PIPE_TOP_LEFT) {
								if(i + 2 >= World.ROWS) return;
								World.tiledMap[i][j] = new Block(Material.PIPE_TOP_LEFT, i, j);
								World.tiledMap[i][j + 1] = new Block(Material.PIPE_TOP_RIGHT, i, j + 1);
								World.tiledMap[i + 1][j] = new Block(Material.PIPE_LEFT, i + 1, j);
								World.tiledMap[i + 1][j + 1] = new Block(Material.PIPE_RIGHT, i + 1, j + 1);
								
								int distDown = 2;
								while(World.tiledMap[i + distDown][j].getMaterial() == Material.AIR && World.tiledMap[i + distDown][j + 1].getMaterial() == Material.AIR && distDown + i < World.ROWS) {
									World.tiledMap[i + distDown][j] = new Block(Material.PIPE_LEFT, i + distDown, j);
									World.tiledMap[i + distDown][j + 1] = new Block(Material.PIPE_RIGHT, i + distDown, j + 1);
									distDown++;
								}
								
							}
							else {
								World.tiledMap[i][j] = new Block(Game.matSelected, i, j);
							}
							
							game.setTimeLastClicked(System.currentTimeMillis());
						}
						else if(game.GetClickType() == 3) { //Right click
							
							if(m == Material.BULLET_TOP) {
								World.tiledMap[i][j] = new Block(Material.AIR, i, j);
								World.tiledMap[i + 1][j] = new Block(Material.AIR, i + 1, j);
							}
							else if(m == Material.BULLET_BOTTOM) {
								World.tiledMap[i][j] = new Block(Material.AIR, i, j);
								World.tiledMap[i - 1][j] = new Block(Material.AIR, i - 1, j);
							}
							else {
								if(Game.devMode == true) {
									World.tiledMap[i][j] = new Block(Material.FLOOR, i, j);
								}
								else {
									World.tiledMap[i][j] = new Block(Material.AIR, i, j);
								}
							}
						}
						else if(game.GetClickType() == 2) { //Middle Click
							if(m != Material.AIR) {
								if(m == Material.BULLET_BOTTOM) Game.matSelected = Material.BULLET_TOP;
								else if(m == Material.POLE_SECTION || m == Material.POLE_BOTTOM) Game.matSelected = Material.POLE_TOP;
								else if(m == Material.PIPE_TOP_RIGHT || m == Material.PIPE_LEFT || m == Material.PIPE_RIGHT) Game.matSelected = Material.PIPE_TOP_LEFT;
								else Game.matSelected = m;
							}
						}
						else {
							System.out.println("Unknown click type");
						}
					}
				}
			}
		}
		
	}
}
