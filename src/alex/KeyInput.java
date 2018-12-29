package alex;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{

	Game game;

	public KeyInput(Game game) {
		this.game = game;
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		GameState gameState = game.currentState;
		
		switch(key) {
		case KeyEvent.VK_ESCAPE:
			if(gameState == GameState.MAIN_MENU) game.CloseGame();
			else if(gameState == GameState.GAME_LEVEL) {
				game.getSoundManager().PlaySound(SoundEffect.GAME_PAUSE);
				LoadState.Load(game, GameState.GAME_PAUSED);
			}
			else if(gameState == GameState.GAME_PAUSED) {
				game.currentState = GameState.GAME_LEVEL;
				game.getSoundManager().PlaySound(SoundEffect.GAME_PAUSE);
				game.getSoundManager().resumeMusic();
			}
			else if(gameState == GameState.MAIN_SETTINGS) {
				if(Game.currentLevel == 0) LoadState.Load(game, GameState.MAIN_MENU);
				else LoadState.Load(game, GameState.GAME_PAUSED);
			}
			else if(gameState == GameState.MAIN_LEVEL_SELECT) LoadState.Load(game, GameState.MAIN_MENU);
			else if(gameState == GameState.LEVEL_EDIT) LoadState.Load(game, GameState.LEVEL_EDIT_PAUSE);
			else if(gameState == GameState.LEVEL_EDIT_PAUSE) game.currentState = GameState.LEVEL_EDIT;
			else if(gameState == GameState.CUSTOM_LEVEL_MENU) {
				if(Game.inEditMenu == true) {
					LoadState.Load(game, GameState.MAIN_MENU);
				}
				else {
					LoadState.Load(game, GameState.MAIN_LEVEL_SELECT);
				}
			}
			break;
			
		case KeyEvent.VK_F3:
			Game.displayFPS = !Game.displayFPS;
			break;
		}		
		
		if(gameState == GameState.LEVEL_EDIT) {
			if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) Game.levelEditorLocX += 16;
			else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) Game.levelEditorLocX -= 16;
			else if(key == KeyEvent.VK_F4) Game.hitBoxes = !Game.hitBoxes;
			else if(key == KeyEvent.VK_SPACE) Game.blockSelect = !Game.blockSelect;
		}
		
		//Return if we are not in the game
		if(gameState != GameState.GAME_LEVEL) return;
		
		//Keys for in game use
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) game.GetThePlayer().SetRightHeld(true);
		else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) game.GetThePlayer().SetLeftHeld(true);
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_SHIFT) game.GetThePlayer().sprint();
		if(key == KeyEvent.VK_SPACE) game.GetThePlayer().jump();
		if(key == KeyEvent.VK_S) game.GetThePlayer().crouch();
		if(key == KeyEvent.VK_F4) Game.hitBoxes = !Game.hitBoxes;
		if(key == KeyEvent.VK_E) game.GetThePlayer().shootFireball();
		
		//Keys for if dev mode is active
		if(Game.devMode == true) {
			if(key == KeyEvent.VK_R) game.GetThePlayer().playerRespawn();
			if(key == KeyEvent.VK_G) game.GetThePlayer().setGrown(!game.GetThePlayer().getGrown());
			if(key == KeyEvent.VK_F)game.GetThePlayer().setFireman(!game.GetThePlayer().isFireMan());
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(game.currentState != GameState.GAME_LEVEL) return;
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP || key == KeyEvent.VK_SHIFT) game.GetThePlayer().setSprinting(false);
		
		if(key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D) {
			game.GetThePlayer().SetRightHeld(false);
			game.GetThePlayer().setSprinting(false);
		}
		else if(key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A) {
			game.GetThePlayer().SetLeftHeld(false);
			game.GetThePlayer().setSprinting(false);
		}
		else if(key == KeyEvent.VK_S) game.GetThePlayer().SetCrouchHeld(false);
	}
	
}
