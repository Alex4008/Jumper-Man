package alex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Render {

	public static void rendering(Game game, Graphics g) {
		
		switch (game.currentState) {
		
		case MAIN_MENU:
			//Render main menu code here
			RenderMainMenu(game, g);
			return;
		
		case MAIN_SETTINGS:
			RenderSettingsMenu(game, g);
			return;
			
		case MAIN_LEVEL_SELECT:
			RenderLevelSelectMenu(game, g);
			return;
		
		case GAME_LEVEL:
			RenderGameLevel(game, g);
			return;
			
		case GAME_PAUSED:
			RenderPauseMenu(game, g);
			return;
			
		case LEVEL_EDIT:
			RenderLevelEdit(game, g);
			break;
			
		case DEATH_SCENE:
			RenderBlackScene(game, g, "You died! Lives Remaining: " + game.GetThePlayer().getPlayerLives());
			break;
			
		case GAMEOVER_SCENE:
			RenderBlackScene(game, g, "Game Over! Final Score: " + game.GetThePlayer().getPlayerScore());
			break;
			
		case LEVEL_LOAD:
			RenderBlackScene(game, g, "Loading Level...");
			break;
			
		case LEVEL_FINISHED:
			RenderGameLevel(game, g); //Continue regular game rendering until level is over.
			break;
			
		case CREDITS:
			RenderBlackScene(game, g, "You have beat Jumper Man! Thank you for playing!");
			break;
			
		case CUSTOM_LEVEL_MENU:
			RenderCustomLevelMenu(game, g);
			break;
			
		case LEVEL_TYPE_MENU:
			RenderLevelTypeMenu(game, g);
			break;
			
		case LEVEL_EDIT_PAUSE:
			RenderPauseEditMenu(game, g);
			break;
			
		default:
			break;

		}
		
	}
	
	private static void RenderPauseEditMenu(Game game, Graphics g) {
		PauseEditMenu menu = game.getPauseEditMenu();
		
		g.drawImage(menu.getPauseIcon(), menu.getPauseIconLoc().x, menu.getPauseIconLoc().y, null);
		g.drawImage(menu.getControlsIcon(), menu.getControlsIconLoc().x, menu.getControlsIconLoc().y, null);
		
		if(menu.isHoverResume() == true) {
			g.drawImage(menu.getResumeBlackIcon(), menu.getResumeIconLoc().x, menu.getResumeIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getResumeIcon(), menu.getResumeIconLoc().x, menu.getResumeIconLoc().y, null);
		}
		
		if(menu.isHoverDiscardLevel() == true) {
			g.drawImage(menu.getDiscardLevelBlackIcon(), menu.getDiscardLevelIconLoc().x, menu.getDiscardLevelIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getDiscardLevelIcon(), menu.getDiscardLevelIconLoc().x, menu.getDiscardLevelIconLoc().y, null);
		}
		
		if(menu.isHoverSaveLevel() == true) {
			g.drawImage(menu.getSaveLevelBlackIcon(), menu.getSaveLevelIconLoc().x, menu.getSaveLevelIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getSaveLevelIcon(), menu.getSaveLevelIconLoc().x, menu.getSaveLevelIconLoc().y, null);
		}
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			Font f = new Font("Arial", Font.PLAIN, 18);
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderLevelTypeMenu(Game game, Graphics g) {
		TypeSelectMenu menu = game.getTypeSelectionMenu();
		
		g.drawImage(menu.getWorldTypeIcon(), menu.getWorldTypeIconLoc().x, menu.getWorldTypeIconLoc().y, null);
		
		if(menu.isHoverOverWorld() == true) {
			g.drawImage(menu.getOverWorldBlackIcon(), menu.getOverWorldIconLoc().x, menu.getOverWorldIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getOverWorldIcon(), menu.getOverWorldIconLoc().x, menu.getOverWorldIconLoc().y, null);
		}
		
		if(menu.isHoverUnderGround() == true) {
			g.drawImage(menu.getUnderGroundBlackIcon(), menu.getUnderGroundIconLoc().x, menu.getUnderGroundIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getUnderGroundIcon(), menu.getUnderGroundIconLoc().x, menu.getUnderGroundIconLoc().y, null);
		}
		
		if(menu.isHoverCastle() == true) {
			g.drawImage(menu.getCastleBlackIcon(), menu.getCastleIconLoc().x, menu.getCastleIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getCastleIcon(), menu.getCastleIconLoc().x, menu.getCastleIconLoc().y, null);
		}
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			Font f = new Font("Arial", Font.PLAIN, 18);
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderCustomLevelMenu(Game game, Graphics g) {
		CustomLevelMenu menu = game.getCustomLevelMenu();
		
		g.drawImage(menu.getLevelSelectLogo(), menu.getLevelSelectLogoLoc().x, menu.getLevelSelectLogoLoc().y, null);

		if(Game.inEditMenu == true) {
			if(Game.customOneExists == true) {
				if(menu.isHoverLevelOne() == true) g.drawImage(menu.getLevelOneBlackLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
				else g.drawImage(menu.getLevelOneLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
			}
			else {
				if(menu.isHoverLevelOne() == true) g.drawImage(menu.getEmptyBlackLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
				else g.drawImage(menu.getEmptyLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
			}
				
			if(Game.customTwoExists == true) {
				if(menu.isHoverLevelTwo() == true) g.drawImage(menu.getLevelTwoBlackLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
				else g.drawImage(menu.getLevelTwoLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
			}
			else {
				if(menu.isHoverLevelTwo() == true) g.drawImage(menu.getEmptyBlackLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
				else g.drawImage(menu.getEmptyLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
			}
			
			if(Game.customThreeExists == true) {
				if(menu.isHoverLevelThree() == true) g.drawImage(menu.getLevelThreeBlackLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
				else g.drawImage(menu.getLevelThreeLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
			}
			else {
				if(menu.isHoverLevelThree() == true) g.drawImage(menu.getEmptyBlackLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
				else g.drawImage(menu.getEmptyLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
			}
			
			if(Game.customFourExists == true) {
				if(menu.isHoverLevelFour() == true) g.drawImage(menu.getLevelFourBlackLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
				else g.drawImage(menu.getLevelFourLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
			}
			else {
				if(menu.isHoverLevelFour() == true) g.drawImage(menu.getEmptyBlackLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
				else g.drawImage(menu.getEmptyLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
			}
		}
		else {
			if(Game.customOneExists == true) {
				if(menu.isHoverLevelOne() == true) g.drawImage(menu.getLevelOneBlackLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
				else g.drawImage(menu.getLevelOneLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
			}
			else g.drawImage(menu.getEmptyGrayLogo(), menu.getLevelOneLoc().x, menu.getLevelOneLoc().y, null);
				
			if(Game.customTwoExists == true) {
				if(menu.isHoverLevelTwo() == true) g.drawImage(menu.getLevelTwoBlackLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
				else g.drawImage(menu.getLevelTwoLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
			}
			else g.drawImage(menu.getEmptyGrayLogo(), menu.getLevelTwoLoc().x, menu.getLevelTwoLoc().y, null);
			
			if(Game.customThreeExists == true) {
				if(menu.isHoverLevelThree() == true) g.drawImage(menu.getLevelThreeBlackLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
				else g.drawImage(menu.getLevelThreeLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
			}
			else g.drawImage(menu.getEmptyGrayLogo(), menu.getLevelThreeLoc().x, menu.getLevelThreeLoc().y, null);
			
			if(Game.customFourExists == true) {
				if(menu.isHoverLevelFour() == true) g.drawImage(menu.getLevelFourBlackLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
				else g.drawImage(menu.getLevelFourLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
			}
			else g.drawImage(menu.getEmptyGrayLogo(), menu.getLevelFourLoc().x, menu.getLevelFourLoc().y, null);
		}

		//Return to Main Menu Icon
		if(menu.getHoverMainReturn() == true)
			g.drawImage(menu.getMainReturnBlackLogo(), menu.getMainReturnLogoLoc().x, menu.getMainReturnLogoLoc().y, null);
		else
			g.drawImage(menu.getMainReturnLogo(), menu.getMainReturnLogoLoc().x, menu.getMainReturnLogoLoc().y, null);
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			Font f = new Font("Arial", Font.PLAIN, 18);
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderLevelSelectMenu(Game game, Graphics g) {
		LevelSelectMenu menu = game.getLevelSelectMenu();
		
		g.drawImage(menu.getLevelSelectLogo(), menu.getLevelSelectLogoLoc().x, menu.getLevelSelectLogoLoc().y, null);
		g.drawImage(menu.getWorldOneLogo(), menu.getWorldOneLoc().x, menu.getWorldOneLoc().y, null);
		g.drawImage(menu.getWorldTwoLogo(), menu.getWorldTwoLoc().x, menu.getWorldTwoLoc().y, null);
		g.drawImage(menu.getWorldThreeLogo(), menu.getWorldThreeLoc().x, menu.getWorldThreeLoc().y, null);
		g.drawImage(menu.getWorldFourLogo(), menu.getWorldFourLoc().x, menu.getWorldFourLoc().y, null);
		
		//World One Icons
		if(menu.isHoverWorldOneLevelOne() == true)
			g.drawImage(menu.getLevelOneBlackLogo(), menu.getWorldOneLevelOneLoc().x, menu.getWorldOneLevelOneLoc().y, null);
		else 
			g.drawImage(menu.getLevelOneLogo(), menu.getWorldOneLevelOneLoc().x, menu.getWorldOneLevelOneLoc().y, null);
			
		if(menu.isHoverWorldOneLevelTwo() == true) 
			g.drawImage(menu.getLevelTwoBlackLogo(), menu.getWorldOneLevelTwoLoc().x, menu.getWorldOneLevelTwoLoc().y, null);
		else
			g.drawImage(menu.getLevelTwoLogo(), menu.getWorldOneLevelTwoLoc().x, menu.getWorldOneLevelTwoLoc().y, null);
		
		if(menu.isHoverWorldOneLevelThree() == true)
			g.drawImage(menu.getLevelThreeBlackLogo(), menu.getWorldOneLevelThreeLoc().x, menu.getWorldOneLevelThreeLoc().y, null);
		else 
			g.drawImage(menu.getLevelThreeLogo(), menu.getWorldOneLevelThreeLoc().x, menu.getWorldOneLevelThreeLoc().y, null);
		
		if(menu.isHoverWorldOneLevelFour() == true) 
			g.drawImage(menu.getLevelFourBlackLogo(), menu.getWorldOneLevelFourLoc().x, menu.getWorldOneLevelFourLoc().y, null);
		else 
			g.drawImage(menu.getLevelFourLogo(), menu.getWorldOneLevelFourLoc().x, menu.getWorldOneLevelFourLoc().y, null);
		
		//World Two Icons
		if(menu.isHoverWorldTwoLevelOne() == true) 
			g.drawImage(menu.getLevelOneBlackLogo(), menu.getWorldTwoLevelOneLoc().x, menu.getWorldTwoLevelOneLoc().y, null);
		else 
			g.drawImage(menu.getLevelOneLogo(), menu.getWorldTwoLevelOneLoc().x, menu.getWorldTwoLevelOneLoc().y, null);
		
		if(menu.isHoverWorldTwoLevelTwo() == true)
			g.drawImage(menu.getLevelTwoBlackLogo(), menu.getWorldTwoLevelTwoLoc().x, menu.getWorldTwoLevelTwoLoc().y, null);
		else
			g.drawImage(menu.getLevelTwoLogo(), menu.getWorldTwoLevelTwoLoc().x, menu.getWorldTwoLevelTwoLoc().y, null);
		
		if(menu.isHoverWorldTwoLevelThree() == true)
			g.drawImage(menu.getLevelThreeBlackLogo(), menu.getWorldTwoLevelThreeLoc().x, menu.getWorldTwoLevelThreeLoc().y, null);
		else 
			g.drawImage(menu.getLevelThreeLogo(), menu.getWorldTwoLevelThreeLoc().x, menu.getWorldTwoLevelThreeLoc().y, null);
		
		if(menu.isHoverWorldTwoLevelFour() == true) 
			g.drawImage(menu.getLevelFourBlackLogo(), menu.getWorldTwoLevelFourLoc().x, menu.getWorldTwoLevelFourLoc().y, null);
		else 
			g.drawImage(menu.getLevelFourLogo(), menu.getWorldTwoLevelFourLoc().x, menu.getWorldTwoLevelFourLoc().y, null);
		
		//World Three Icons
		if(menu.isHoverWorldThreeLevelOne() == true)
			g.drawImage(menu.getLevelOneBlackLogo(), menu.getWorldThreeLevelOneLoc().x, menu.getWorldThreeLevelOneLoc().y, null);
		else
			g.drawImage(menu.getLevelOneLogo(), menu.getWorldThreeLevelOneLoc().x, menu.getWorldThreeLevelOneLoc().y, null);
		
		if(menu.isHoverWorldThreeLevelTwo() == true) 
			g.drawImage(menu.getLevelTwoBlackLogo(), menu.getWorldThreeLevelTwoLoc().x, menu.getWorldThreeLevelTwoLoc().y, null);
		else
			g.drawImage(menu.getLevelTwoLogo(), menu.getWorldThreeLevelTwoLoc().x, menu.getWorldThreeLevelTwoLoc().y, null);
		
		if(menu.isHoverWorldThreeLevelThree() == true) 
			g.drawImage(menu.getLevelThreeBlackLogo(), menu.getWorldThreeLevelThreeLoc().x, menu.getWorldThreeLevelThreeLoc().y, null);
		else 
			g.drawImage(menu.getLevelThreeLogo(), menu.getWorldThreeLevelThreeLoc().x, menu.getWorldThreeLevelThreeLoc().y, null);
		
		if(menu.isHoverWorldThreeLevelFour() == true)
			g.drawImage(menu.getLevelFourBlackLogo(), menu.getWorldThreeLevelFourLoc().x, menu.getWorldThreeLevelFourLoc().y, null);
		else
			g.drawImage(menu.getLevelFourLogo(), menu.getWorldThreeLevelFourLoc().x, menu.getWorldThreeLevelFourLoc().y, null);
		
		//World Four Icons
		if(menu.isHoverWorldFourLevelOne() == true)
			g.drawImage(menu.getLevelOneBlackLogo(), menu.getWorldFourLevelOneLoc().x, menu.getWorldFourLevelOneLoc().y, null);
		else 
			g.drawImage(menu.getLevelOneLogo(), menu.getWorldFourLevelOneLoc().x, menu.getWorldFourLevelOneLoc().y, null);
		
		if(menu.isHoverWorldFourLevelTwo() == true)
			g.drawImage(menu.getLevelTwoBlackLogo(), menu.getWorldFourLevelTwoLoc().x, menu.getWorldFourLevelTwoLoc().y, null);
		else 
			g.drawImage(menu.getLevelTwoLogo(), menu.getWorldFourLevelTwoLoc().x, menu.getWorldFourLevelTwoLoc().y, null);
		
		if(menu.isHoverWorldFourLevelThree() == true) 
			g.drawImage(menu.getLevelThreeBlackLogo(), menu.getWorldFourLevelThreeLoc().x, menu.getWorldFourLevelThreeLoc().y, null);
		else 
			g.drawImage(menu.getLevelThreeLogo(), menu.getWorldFourLevelThreeLoc().x, menu.getWorldFourLevelThreeLoc().y, null);
		
		if(menu.isHoverWorldFourLevelFour() == true) 
			g.drawImage(menu.getLevelFourBlackLogo(), menu.getWorldFourLevelFourLoc().x, menu.getWorldFourLevelFourLoc().y, null);
		else
			g.drawImage(menu.getLevelFourLogo(), menu.getWorldFourLevelFourLoc().x, menu.getWorldFourLevelFourLoc().y, null);

		//Return to Main Menu Icon
		if(menu.getHoverMainReturn() == true)
			g.drawImage(menu.getMainReturnBlackLogo(), menu.getMainReturnLogoLoc().x, menu.getMainReturnLogoLoc().y, null);
		else
			g.drawImage(menu.getMainReturnLogo(), menu.getMainReturnLogoLoc().x, menu.getMainReturnLogoLoc().y, null);
		
		//Custom Levels Icon
		if(menu.getHoverCustomLevel() == true)
			g.drawImage(menu.getCustomLevelBlackLogo(), menu.getCustomLevelLogoLoc().x, menu.getCustomLevelLogoLoc().y, null);
		else
			g.drawImage(menu.getCustomLevelLogo(), menu.getCustomLevelLogoLoc().x, menu.getCustomLevelLogoLoc().y, null);
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			Font f = new Font("Arial", Font.PLAIN, 18);
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderSettingsMenu(Game game, Graphics g) {
		SettingsMenu menu = game.getSettingsMenu();
		
		g.drawImage(menu.getSettingsLogo(), menu.getSettingsLogoLoc().x, menu.getSettingsLogoLoc().y, null);
		//Music
		if(game.isMusicToggle() == true) {
			if(menu.isHoverMusicToggle() == true) {
				g.drawImage(menu.getMusicOnBlackLogo(), menu.getMusicLoc().x, menu.getMusicLoc().y, null);
			}
			else {
				g.drawImage(menu.getMusicOnLogo(), menu.getMusicLoc().x, menu.getMusicLoc().y, null);
			}
		}
		else {
			if(menu.isHoverMusicToggle() == true) {
				g.drawImage(menu.getMusicOffBlackLogo(), menu.getMusicLoc().x, menu.getMusicLoc().y, null);
			}
			else {
				g.drawImage(menu.getMusicOffLogo(), menu.getMusicLoc().x, menu.getMusicLoc().y, null);
			}
		}
		
		//Sounds
		if(game.isSoundsToggle() == true) {
			if(menu.isHoverSoundToggle() == true) {
				g.drawImage(menu.getSoundOnBlackLogo(), menu.getSoundLoc().x, menu.getSoundLoc().y, null);
			}
			else {
				g.drawImage(menu.getSoundOnLogo(), menu.getSoundLoc().x, menu.getSoundLoc().y, null);
			}
		}
		else {
			if(menu.isHoverSoundToggle() == true) {
				g.drawImage(menu.getSoundOffBlackLogo(), menu.getSoundLoc().x, menu.getSoundLoc().y, null);
			}
			else {
				g.drawImage(menu.getSoundOffLogo(), menu.getSoundLoc().x, menu.getSoundLoc().y, null);
			}
		}
		
		if(game.isvSyncToggle() == true) {
			if(menu.isHoverVSyncToggle() == true) {
				g.drawImage(menu.getvSyncOnBlackLogo(), menu.getvSyncLoc().x, menu.getvSyncLoc().y, null);
			}
			else {
				g.drawImage(menu.getvSyncOnLogo(), menu.getvSyncLoc().x, menu.getvSyncLoc().y, null);
			}
		}
		else {
			if(menu.isHoverVSyncToggle() == true) {
				g.drawImage(menu.getvSyncOffBlackLogo(), menu.getvSyncLoc().x, menu.getvSyncLoc().y, null);
			}
			else {
				g.drawImage(menu.getvSyncOffLogo(), menu.getvSyncLoc().x, menu.getvSyncLoc().y, null);
			}
		}
		
		if(menu.isHoverMenuReturn() == true) {
			g.drawImage(menu.getMenuReturnBlackLogo(), menu.getMenuReturnLoc().x, menu.getMenuReturnLoc().y, null);
		}
		else {
			g.drawImage(menu.getMenuReturnLogo(), menu.getMenuReturnLoc().x, menu.getMenuReturnLoc().y, null);
		}
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			Font f = new Font("Arial", Font.PLAIN, 18);
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderBlackScene(Game game, Graphics g, String str) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		Font f = new Font("Arial", Font.PLAIN, 18);
		g.setColor(Color.WHITE);
		g.setFont(f);
		g.drawString(str, (Game.WIDTH / 2) - (str.length() * 4), (Game.HEIGHT / 2) - 22);
		
		if(game.currentState == GameState.CREDITS) {
			String finalScore = "Your final score was: " + game.GetThePlayer().getPlayerScore();
			String clickAnywhere = "Click anywhere to return to the main menu!";
			String devBy = "Jumper Man v" + Game.version + " Developed By Alex Gray - All sprites/sounds are properties of Nintendo";
			
			g.drawString(finalScore, (Game.WIDTH / 2) - (finalScore.length() * 4), (Game.HEIGHT / 2));
			g.drawString(clickAnywhere, (Game.WIDTH / 2) - (clickAnywhere.length() * 4), (Game.HEIGHT / 2) + 22);
			g.drawString(devBy, (Game.WIDTH / 2) - (devBy.length() * 4), Game.HEIGHT - 18);
		}
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
	}
	
	private static void RenderPauseMenu(Game game, Graphics g) {
		PauseMenu menu = game.GetPauseMenu();
		
		g.drawImage(menu.getPauseIcon(), menu.getPauseIconLoc().x, menu.getPauseIconLoc().y, null);
		g.drawImage(menu.getControlsIcon(), menu.getControlsIconLoc().x, menu.getControlsIconLoc().y, null);
		
		if(menu.isHoverResume() == true) {
			g.drawImage(menu.getResumeHoverIcon(), menu.getResumeIconLoc().x, menu.getResumeIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getResumeIcon(), menu.getResumeIconLoc().x, menu.getResumeIconLoc().y, null);
		}
		
		if(menu.isHoverSettings() == true) {
			g.drawImage(menu.getSettingsHoverIcon(), menu.getSettingsIconLoc().x, menu.getSettingsIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getSettingsIcon(), menu.getSettingsIconLoc().x, menu.getSettingsIconLoc().y, null);
		}
		
		if(menu.isHoverReturnMenu() == true) {
			g.drawImage(menu.getReturnMenuHoverIcon(), menu.getReturnMenuIconLoc().x, menu.getReturnMenuIconLoc().y, null);
		}
		else {
			g.drawImage(menu.getReturnMenuIcon(), menu.getReturnMenuIconLoc().x, menu.getReturnMenuIconLoc().y, null);
		}
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			Font f = new Font("Arial", Font.PLAIN, 18);
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderGameLevel(Game game, Graphics g) {
		Camera camera = game.GetCamera();
		Player thePlayer = game.GetThePlayer();
		
		//Camera Calculation
		camera.UpdateCamera(thePlayer);
		
		g.translate(-camera.GetX(), 0);
		
		//Draw the clouds
		if(Game.currentLevelType == LevelType.OVERWORLD) {
			int y = 90;
			for(int i = 75; i < World.worldSizeX; i += 550) {
				if(y > Tile.TILE_SIZE * 7 || y <= 10) y = 90;
				g.drawImage(Sprite.getCloudSprite(), i, y, null);
				y -= 20;
			}
		}
		
		//Draw the entities
		ArrayList<Entity> tempEntity = game.getEntityList();
		for(Entity e : tempEntity) {
			if(e.getEntityType() == EntityType.LAVA_BALL) e.render(g);
		}
		
		//Draw the world
		for(int i = 0; i < World.ROWS; i++){
			for(int j = 0; j < World.cols; j++){
				if(World.tiledMap[i][j].getMaterial() != Material.AIR && World.tiledMap[i][j].getMaterial() != Material.HIDDEN_BLOCK){
					if(World.tiledMap[i][j].getMaterial() != Material.BRIDGE) {
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, (i  * Tile.TILE_SIZE) - (World.tiledMap[i][j].getBounceCounter()), null);
					}
					else if(Game.currentLevelType == LevelType.CASTLE){
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, (i  * Tile.TILE_SIZE) - (World.tiledMap[i][j].getBounceCounter()), null);
					}
					else {
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, (i  * Tile.TILE_SIZE) - (World.tiledMap[i][j].getBounceCounter() + 16), null);
					}
					if(Game.hitBoxes == true) {
						g.setColor(Color.WHITE);
						g.drawRect(World.tiledMap[i][j].getBoundingBox().x, World.tiledMap[i][j].getBoundingBox().y, World.tiledMap[i][j].getBoundingBox().width, World.tiledMap[i][j].getBoundingBox().height);
					}
				}
			}
		}
		//Finish drawing the world
		
		//Draw the items
		ArrayList<Item> tempItem = game.getItemList();
		for(Item i : tempItem) {
			i.render(g);
		}
		
		//Draw the rest of the entities
		for(Entity e : tempEntity) {
			if(e.getEntityType() != EntityType.LAVA_BALL) e.render(g);
		}
		
		//Draw the fireballs
		ArrayList<Fireball> tempFire = game.getFireballList();
		for(Fireball f : tempFire) {
			f.render(g);
		}
		
		//Draw pipe (plants inside them)
		ArrayList<Pipe> tempPipe = game.getPipeList();
		for(Pipe p : tempPipe) {
			p.render(g);
		}
		
		//Draw FireWheels
		ArrayList<FireWheel> tempFireWheel = game.getFireWheelList();
		for(FireWheel fw : tempFireWheel) {
			fw.render(g);
		}
		
		//Draw the player
		thePlayer.render(g);
		
		//Draw the Score, Coins, Level, timer and lives
		Font f = new Font("Arial", Font.PLAIN, 18);
		g.setFont(f);
		String str = "Score: " + game.GetThePlayer().getPlayerScore() + "   Coins: " + game.GetThePlayer().getPlayerCoins() + "   World " + Game.currentWorld + "-" + Game.currentLevel + "  Time: " + game.getTimer() + "   Lives: " + game.GetThePlayer().getPlayerLives();
		if(Game.currentLevelType == LevelType.CASTLE)  {
			g.setColor(Color.DARK_GRAY);
			g.fillRect((Game.WIDTH / 2) + camera.GetX() - (str.length() * 4) - 8, 10, (str.length() * 8), 20);
		}
		g.setColor(Color.WHITE);
		g.drawString(str, (Game.WIDTH / 2) + camera.GetX() - (str.length() * 4), 27);
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0 + game.GetCamera().GetX(), 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0 + game.GetCamera().GetX(), 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0 + game.GetCamera().GetX(), 18 * 2);
		}
		
		g = null;
		camera = null;
		thePlayer = null;
	}
	
	private static void RenderMainMenu(Game game, Graphics g) {
		
		MainMenu menu = game.GetMainMenu();
		
		g.drawImage(menu.getTitleLogo(), menu.getTitleLogoLoc().x, menu.getTitleLogoLoc().y, null);
		g.drawImage(menu.getDevLogoImage(), menu.getDevLogoLoc().x, menu.getDevLogoLoc().y, null);
		if(menu.isHoverStart() == true) {
			g.drawImage(menu.getStartImageBlack(), menu.getStartGameLoc().x, menu.getStartGameLoc().y, null);
		}
		else {
			g.drawImage(menu.getStartImage(), menu.getStartGameLoc().x, menu.getStartGameLoc().y, null);
		}
		
		if(menu.isHoverLevelSelect() == true) {
			g.drawImage(menu.getLevelSelectImageBlack(), menu.getLevelSelectLoc().x, menu.getLevelSelectLoc().y, null);
		}
		else {
			g.drawImage(menu.getLevelSelectImage(), menu.getLevelSelectLoc().x, menu.getLevelSelectLoc().y, null);
		}

		if(menu.isHoverLevelEdit() == true) {
			g.drawImage(menu.getLevelEditImageBlack(), menu.getLevelEditLoc().x, menu.getLevelEditLoc().y, null);
		}
		else {
			g.drawImage(menu.getLevelEditImage(), menu.getLevelEditLoc().x, menu.getLevelEditLoc().y, null);
		}
		
		if(menu.isHoverSettings() == true) {
			g.drawImage(menu.getSettingsImageBlack(), menu.getSettingsLoc().x, menu.getSettingsLoc().y, null);
		}
		else {
			g.drawImage(menu.getSettingsImage(), menu.getSettingsLoc().x, menu.getSettingsLoc().y, null);
		}
		
		if(menu.isHoverExitGame() == true) {
			g.drawImage(menu.getExitGameImageBlack(), menu.getExitGameLoc().x, menu.getExitGameLoc().y, null);
		}
		else {
			g.drawImage(menu.getExitGameImage(), menu.getExitGameLoc().x, menu.getExitGameLoc().y, null);
		}
		
		//Render FPS and Ticks
		Font f = new Font("Arial", Font.PLAIN, 18);
		if(Game.displayFPS == true || Game.devMode == true) {
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0, 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0, 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0, 18 * 2);
		}
		
		//Draw High Score
		g.setFont(f);
		g.setColor(new Color(98, 64, 35)); //Brown
		g.drawString("High Score: " + Game.topScore, 10, World.SCREEN_SIZE_Y - 10);
		
		//Clean up
		g = null;
		menu = null;
	}
	
	private static void RenderLevelEdit(Game game, Graphics g) {
		Camera camera = game.GetCamera();
		
		//Camera Calculation
		camera.UpdateCamera(Game.levelEditorLocX, Game.levelEditorLocY);
		g.translate(-camera.GetX(), 0);
		
		//Draw the world
		for(int i = 0; i < World.ROWS; i++){
			for(int j = 0; j < World.cols; j++){ 
				if(World.tiledMap[i][j].getMaterial() != Material.AIR){
					if(World.tiledMap[i][j].getItemType() != ItemType.EMPTY) {
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE, null);
						if(World.tiledMap[i][j].getItemType() == ItemType.MUSHROOM) {
							g.drawImage(Sprite.getMushroomSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getItemType() == ItemType.FLOWER) {
							g.drawImage(Sprite.getFlowerSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getItemType() == ItemType.ONEUP) {
							g.drawImage(Sprite.getOneUpSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getItemType() == ItemType.COIN) {
							
							if(World.tiledMap[i][j].isExtraCoin() == true) g.drawImage(Sprite.getExtraCoinIconSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
							else g.drawImage(Sprite.getCoinSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
					}
					else if(World.tiledMap[i][j].isContainsEntity() == true) {
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE, null);
						if(World.tiledMap[i][j].getEntityType() == EntityType.GOOMBA) {
							g.drawImage(Sprite.getGoombaSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getEntityType() == EntityType.GREEN_TURTLE) {
							g.drawImage(Sprite.getGreenTurtleSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_GREEN_TURTLE) {
							g.drawImage(Sprite.getJumpGreenTurtleSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getEntityType() == EntityType.RED_TURTLE) {
							g.drawImage(Sprite.getRedTurtleSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getEntityType() == EntityType.JUMP_RED_TURTLE) {
							g.drawImage(Sprite.getJumpRedTurtleSprite(), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE - 4, null);
						}
						else if(World.tiledMap[i][j].getEntityType() == EntityType.PLANT) {
							g.drawImage(Sprite.getPlantSprite(), j * Tile.TILE_SIZE + 16, i  * Tile.TILE_SIZE - 28, null);
						}
					}
					else if(World.tiledMap[i][j].getMaterial() == Material.BULLET_TOP) {
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE, null);
						
						if(World.tiledMap[i][j].getDirection().toLowerCase().contains("both")) {
							g.drawImage(Sprite.getBulletSprite(), j * Tile.TILE_SIZE - 16, i * Tile.TILE_SIZE, null);
							// Flip the image horizontally
							BufferedImage flippedImage = Sprite.getBulletSprite();
							AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
							tx.translate(-flippedImage.getWidth(null), 0);
							AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
							flippedImage = op.filter(flippedImage, null);

							g.drawImage(flippedImage, j * Tile.TILE_SIZE + 16, i * Tile.TILE_SIZE, null);
						}
						else if(World.tiledMap[i][j].getDirection().toLowerCase().contains("left"))  {
							g.drawImage(Sprite.getBulletSprite(), j * Tile.TILE_SIZE - 16, i * Tile.TILE_SIZE, null);
						}
						else if(World.tiledMap[i][j].getDirection().toLowerCase().contains("right"))  {
							BufferedImage flippedImage = Sprite.getBulletSprite();
							AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
							tx.translate(-flippedImage.getWidth(null), 0);
							AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
							flippedImage = op.filter(flippedImage, null);
							g.drawImage(flippedImage, j * Tile.TILE_SIZE + 16, i * Tile.TILE_SIZE, null);
						}
					}
					else {
						g.drawImage(World.tiledMap[i][j].getImage(World.tiledMap[i][j].GetAnimationLocation()), j * Tile.TILE_SIZE, i  * Tile.TILE_SIZE, null);
					}
				}
				
				g.setColor(Color.GRAY);
				g.drawRect(World.tiledMap[i][j].getBoundingBox().x, World.tiledMap[i][j].getBoundingBox().y, World.tiledMap[i][j].getBoundingBox().width, World.tiledMap[i][j].getBoundingBox().height);
				
			}
		}
		//Finish drawing the world
		
		if(Game.matSelected == Material.BULLET_TOP) {
			g.drawImage(Sprite.getBulletTop(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() - 16, null);
			g.drawImage(Sprite.getBulletBottom(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 16, null);
		}
		else if(Game.matSelected == Material.POLE_TOP) {
			g.drawImage(Sprite.getPoleTop(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() - 16, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 16, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 48, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 80, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 112, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 144, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 176, null);
			g.drawImage(Sprite.getPoleSection(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 208, null);
			g.drawImage(Sprite.getPoleBottom(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() + 240, null);
		}
		else if(Game.matSelected == Material.PIPE_TOP_LEFT) {
			g.drawImage(Sprite.getPipeTopLeftSprite(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() - 32, null);
			g.drawImage(Sprite.getPipeLeftSprite(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY(), null);
			g.drawImage(Sprite.getPipeTopRightSprite(), game.getMouseLoc().x + game.GetCamera().GetX() + 16, game.getMouseLoc().y + game.GetCamera().GetY() - 32, null);
			g.drawImage(Sprite.getPipeRightSprite(), game.getMouseLoc().x + game.GetCamera().GetX() + 16, game.getMouseLoc().y + game.GetCamera().GetY(), null);
		}
		else if(Game.matSelected == Material.BOSS_ICON) {
			g.drawImage(Sprite.getBossSprite(), game.getMouseLoc().x + game.GetCamera().GetX() - 16, game.getMouseLoc().y + game.GetCamera().GetY() - 16, null);
		}
		else {
			g.drawImage(Game.matSelected.GetImageOne(), (int) game.getMouseLoc().getX() + game.GetCamera().GetX() - 16, (int) game.getMouseLoc().getY() + game.GetCamera().GetY() - 16, null);
		}
		
		
		//Set Font
		Font f = new Font("Arial", Font.PLAIN, 18);
		g.setFont(f);
		
		//Draw block selecter
		game.getBlockSelector().render(g);
		
		//Render FPS and Ticks
		if(Game.displayFPS == true || Game.devMode == true) {
			if(Game.devMode == false) {
				g.setColor(Color.WHITE);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version), 0 + game.GetCamera().GetX(), 18 * 1);
			}
			else {
				g.setColor(Color.DARK_GRAY);
				g.setFont(f);
				g.drawString("Jumper Man v" + Double.toString(Game.version) + " - DEV MODE ACTIVE", 0 + game.GetCamera().GetX(), 18 * 1);
			}
			g.drawString("FPS: " + game.GetCurrentFrames() + " TPS: " + game.GetCurrentTicks() + "/60", 0 + game.GetCamera().GetX(), 18 * 2);
		}
		
		g = null;
		camera = null;
	}
	
}
