package alex;

public enum GameState {
	MAIN_MENU(),
	MAIN_SETTINGS(),
	MAIN_LEVEL_SELECT(),
	GAME_LEVEL(),
	GAME_PAUSED(),
	LEVEL_EDIT(),
	DEATH_SCENE(),
	GAMEOVER_SCENE(),
	LEVEL_LOAD(),
	LEVEL_FINISHED(),
	START_MAIN_GAME(),
	CREDITS(),
	
	CUSTOM_LEVEL_MENU(),
	LEVEL_TYPE_MENU(),
	LEVEL_EDIT_PAUSE();
}
