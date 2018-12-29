package alex;

public enum MusicEffect {
	
	GAME_OVER("music/gameover.wav", false),
	PLAYER_DIE("music/playerDie.wav", false),
	STAGE_CLEAR("music/stageClear.wav", false),
	WORLD_CLEAR("music/worldClear.wav", false),
	TIME_WARNING("music/timeWarning.wav", false),
	CASTLE_CLEAR("music/castleClear.wav", false),
	OVER_THEME("music/overTheme.wav", true),
	UNDER_THEME("music/underTheme.wav", true),
	CASTLE_THEME("music/castleTheme.wav", true),
	FAST_OVER_THEME("music/fastOverTheme.wav", true),
	FAST_UNDER_THEME("music/fastUnderTheme.wav", true),
	FAST_CASTLE_THEME("music/fastCastleTheme.wav", true),
	BEAT_GAME("music/beatGame.wav", true);
	
	private String fileName;
	private boolean repeated;
	
	MusicEffect(String fileName, boolean repeated) {
		this.fileName = fileName;
		this.repeated = repeated;
	}
	
	public String GetFileName() {
		return fileName;
	}
	
	public boolean isRepeated() {
		return repeated;
	}
}
