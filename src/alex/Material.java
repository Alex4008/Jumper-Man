package alex;

import java.awt.image.BufferedImage;

public enum Material {
	
	//TYPE: BREAKABLE, SOLID, DAMAGE, BOUNCE, SPRITE
	AIR(false, false, false, false, null),
	BRICK(true, true, false, true, Sprite.getBrickSprite()),
	STONE(false, true, false, true, Sprite.getStoneSprite()),
	DAMAGE(false, false, true, false, Sprite.getDamageSprite()),
	FLOOR(false, true, false, false, Sprite.getFloorSprite()),
	EMPTY_COIN(false, true, false, true, Sprite.getEmptyCoinSprite()),
	EMPTY_COIN_SOLID(false, true, false, true, Sprite.getEmptyCoinSolidSprite()),
	//TYPE: BREAKABLE, SOLID, DAMAGE, BOUNCE, ANIMATE_COUNT, ANIMATE_START
	QUESTION(false, true, false, true, 3, 5, Sprite.getQuestionOneSprite(), Sprite.getQuestionTwoSprite(), Sprite.getQuestionThreeSprite()),
	POLE_TOP(false, false, false, false, Sprite.getPoleTop()),
	POLE_SECTION(false, false, false, false, Sprite.getPoleSection()),
	POLE_BOTTOM(false, true, false, false, Sprite.getPoleBottom()),
	BULLET_TOP(false, true, false, false, Sprite.getBulletTop()),
	BULLET_BOTTOM(false, true, false, false, Sprite.getBulletBottom()),
	BULLET_MIDDLE(false, true, false, false, Sprite.getBulletMiddleSprite()),
	PIPE_TOP_LEFT(false, true, false, false, Sprite.getPipeTopLeftSprite()),
	PIPE_TOP_RIGHT(false, true, false, false, Sprite.getPipeTopRightSprite()),
	PIPE_LEFT(false, true, false, false, Sprite.getPipeLeftSprite()),
	PIPE_RIGHT(false, true, false, false, Sprite.getPipeRightSprite()),
	GRASS_LEFT(false, true, false, false, Sprite.getGrassLeftSprite()),
	GRASS_MIDDLE(false, true, false, false, Sprite.getGrassMiddleSprite()),
	GRASS_RIGHT(false, true, false, false, Sprite.getGrassRightSprite()),
	TRUNK(false, true, false, false, Sprite.getTrunkSprite()),
	BRIDGE(false, true, false, false, Sprite.getBridgeSprite()),
	HIDDEN_BLOCK(false, false, false, false, null),
	//FOR LEVEL EDITOR: DO NOT USE IN GAME.
	MUSHROOM_ICON(false, false, false, false, Sprite.getMushroomSprite()),
	FLOWER_ICON(false, false, false, false, Sprite.getFlowerSprite()),
	ONEUP_ICON(false, false, false, false, Sprite.getOneUpSprite()),
	COIN_ICON(false, false, false, false, Sprite.getCoinSprite()),
	GOOMBA_ICON(false, false, false, false, Sprite.getGoombaSprite()),
	GREEN_TURTLE_ICON(false, false, false, false, Sprite.getGreenTurtleSprite()),
	RED_TURTLE_ICON(false, false, false, false, Sprite.getRedTurtleSprite()),
	JUMP_GREEN_TURTLE_ICON(false, false, false, false, Sprite.getJumpGreenTurtleSprite()),
	JUMP_RED_TURTLE_ICON(false, false, false, false, Sprite.getJumpRedTurtleSprite()),
	PLAYER_ICON(false, false, false, false, Sprite.getPlayerSprite()),
	KEY_ICON(false, false, false, false, Sprite.getKeySprite()),
	BOSS_ICON(false, false, false, false, Sprite.getBossIconSprite()),
	PLANT_ICON(false, false, false, false, Sprite.getPlantSprite()),
	FIREWHEEL_ICON(false, false, false, false, Sprite.getFireWheelIconSprite()),
	LAVA_BALL_ICON(false, false, false, false, Sprite.getLavaBallIconSprite());
	
	
	private final boolean isBreakable;
	private final boolean isSolid;
	private final boolean doesDamage;
	private final int animateStart;

	private final boolean allowBounce;
	
	private int animateCount;
	private int animateLocation;
	
	private BufferedImage imageOne;
	private BufferedImage imageTwo;
	private BufferedImage imageThree;
	
	//Partial Constructor
	Material(boolean breakable, boolean solid, boolean damage, boolean bounce, BufferedImage image) {
		this(breakable, solid, damage, bounce, 0, 0, image, null, null);
	}
	
	//Full Constructor
	Material(boolean breakable, boolean solid, boolean damage, boolean bounce, int animateCount, int animateStart, BufferedImage imageOne, BufferedImage imageTwo, BufferedImage imageThree) {
		this.isBreakable = breakable;
		this.isSolid = solid;
		this.doesDamage = damage;
		this.animateCount = animateCount;
		this.animateStart = animateStart;
		allowBounce = bounce;
		animateLocation = 0;
	}
	
	public boolean GetBreakable() { return isBreakable; }
	public boolean GetSolid() { return isSolid; }
	public boolean GetDoesDamage() { return doesDamage; }
	public int GetImageSpot() { return animateStart; }
	public int GetAnimateCount() { return animateLocation; }
	
	public BufferedImage GetImageOne() { return imageOne; }
	public BufferedImage GetImageTwo() { return imageTwo; }
	public BufferedImage GetImageThree() { return imageThree; }
	
	public boolean isAnimated() {
		if(animateCount == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean allowedBounce() {
		return allowBounce;
	}
	
	public static void getSprite(Material m) {
		switch(m) {
		case AIR:
			m.imageOne = null;
			return;
		case BRICK:
			m.imageOne = Sprite.getBrickSprite();
			return;
		case STONE:
			m.imageOne = Sprite.getStoneSprite();
			return;
		case DAMAGE:
			m.imageOne = Sprite.getDamageSprite();
			return;
		case FLOOR:
			m.imageOne = Sprite.getFloorSprite();
			return;
		case EMPTY_COIN:
			m.imageOne = Sprite.getEmptyCoinSprite();
			return;
		case EMPTY_COIN_SOLID:
			m.imageOne = Sprite.getEmptyCoinSolidSprite();
			return;
		case QUESTION:
			m.imageOne = Sprite.getQuestionOneSprite();
			m.imageTwo = Sprite.getQuestionTwoSprite();
			m.imageThree = Sprite.getQuestionThreeSprite();
			return;
		case POLE_TOP:
			m.imageOne = Sprite.getPoleTop();
			return;
		case POLE_SECTION:
			m.imageOne = Sprite.getPoleSection();
			return;
		case POLE_BOTTOM:
			m.imageOne = Sprite.getPoleBottom();
			return;
		case BULLET_TOP:
			m.imageOne = Sprite.getBulletTop();
			return;
		case BULLET_MIDDLE:
			m.imageOne = Sprite.getBulletMiddleSprite();
			return;		
		case BULLET_BOTTOM:
			m.imageOne = Sprite.getBulletBottom();
			return;		
		case PIPE_TOP_LEFT:
			m.imageOne = Sprite.getPipeTopLeftSprite();
			return;		
		case PIPE_TOP_RIGHT:
			m.imageOne = Sprite.getPipeTopRightSprite();
			return;		
		case PIPE_LEFT:
			m.imageOne = Sprite.getPipeLeftSprite();
			return;		
		case PIPE_RIGHT:
			m.imageOne = Sprite.getPipeRightSprite();
			return;		
		case GRASS_LEFT:
			m.imageOne = Sprite.getGrassLeftSprite();
			return;		
		case GRASS_MIDDLE:
			m.imageOne = Sprite.getGrassMiddleSprite();
			return;		
		case GRASS_RIGHT:
			m.imageOne = Sprite.getGrassRightSprite();
			return;		
		case TRUNK:
			m.imageOne = Sprite.getTrunkSprite();
			return;		
		case BRIDGE:
			m.imageOne = Sprite.getBridgeSprite();
			return;	
		case HIDDEN_BLOCK:
			m.imageOne = Sprite.getHiddenBlockSprite();
			return;		
		case MUSHROOM_ICON:
			m.imageOne = Sprite.getMushroomSprite();
			return;		
		case FLOWER_ICON:
			m.imageOne = Sprite.getFlowerSprite();
			return;		
		case ONEUP_ICON:
			m.imageOne = Sprite.getOneUpSprite();
			return;		
		case COIN_ICON:
			m.imageOne = Sprite.getCoinSprite();
			return;		
		case GOOMBA_ICON:
			m.imageOne = Sprite.getGoombaSprite();
			return;		
		case GREEN_TURTLE_ICON:
			m.imageOne = Sprite.getGreenTurtleSprite();
			return;		
		case RED_TURTLE_ICON:
			m.imageOne = Sprite.getRedTurtleSprite();
			return;	
		case JUMP_GREEN_TURTLE_ICON:
			m.imageOne = Sprite.getJumpGreenTurtleSprite();
			return;	
		case JUMP_RED_TURTLE_ICON:
			m.imageOne = Sprite.getJumpRedTurtleSprite();
			return;	
		case PLAYER_ICON:
			m.imageOne = Sprite.getPlayerSprite();
			return;	
		case KEY_ICON:
			m.imageOne = Sprite.getKeySprite();
			return;	
		case BOSS_ICON:
			m.imageOne = Sprite.getBossSprite();
			return;	
		case PLANT_ICON:
			m.imageOne = Sprite.getPlantSprite();
			return;
		case FIREWHEEL_ICON:
			m.imageOne = Sprite.getFireWheelIconSprite();
			return;
		case LAVA_BALL_ICON:
			m.imageOne = Sprite.getLavaBallIconSprite();
			return;
		default:
			System.out.println("Error loading a sprite: " + m.toString());
			break;	
		}
	}
	
	public static void refreshAll() {
	    for (Material e : values()) {
	        getSprite(e);
	    }
	}
	
}
