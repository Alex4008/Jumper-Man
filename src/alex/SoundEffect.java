package alex;

public enum SoundEffect {
	
	SMALL_JUMP("sounds/smallJump.wav"),
	ONE_UP("sounds/oneUp.wav"),
	BREAK_BLOCK("sounds/breakBlock.wav"),
	COIN("sounds/coin.wav"),
	FIREBALL("sounds/fireball.wav"),
	FIREWORK("sounds/firework.wav"),
	FLAGPOLE_DROP("sounds/flagpoleDrop.wav"),
	LARGE_JUMP("sounds/largeJump.wav"),
	GAME_PAUSE("sounds/gamePause.wav"),
	GRAB_POWER_UP("sounds/grabPowerUp.wav"),
	APPEAR_POWER_UP("sounds/showPowerUp.wav"),
	STOMP("sounds/stomp.wav"),
	HURT("sounds/hurtAndPipe.wav"),
	BOSS_FALL("sounds/bossFall.wav"),
	BOSS_ATTACK("sounds/bossAttack.wav"),
	BUMP("sounds/bump.wav"),
	KICK("sounds/kick.wav");
	
	
	private String fileName;
	
	SoundEffect(String fileName) {
		this.fileName = fileName;
	}
	
	public String GetFileName() {
		return fileName;
	}
	
}
