package alex;

import java.awt.image.BufferedImage;

public class Sprite {

	//Blocks
	private static BufferedImage brickSprite;
	private static BufferedImage stoneSprite;
	private static BufferedImage damageSprite;
	private static BufferedImage floorSprite;
	private static BufferedImage emptyCoinSprite;
	private static BufferedImage emptyCoinSolidSprite;
	private static BufferedImage questionOneSprite;
	private static BufferedImage questionTwoSprite;
	private static BufferedImage questionThreeSprite;
	private static BufferedImage poleTopSprite;
	private static BufferedImage poleSectionSprite;
	private static BufferedImage poleBottomSprite;
	private static BufferedImage bulletTopSprite;
	private static BufferedImage bulletBottomSprite;
	private static BufferedImage bulletMiddleSprite;
	private static BufferedImage pipeTopLeftSprite;
	private static BufferedImage pipeTopRightSprite;
	private static BufferedImage pipeLeftSprite;
	private static BufferedImage pipeRightSprite;
	private static BufferedImage grassLeftSprite;
	private static BufferedImage grassRightSprite;
	private static BufferedImage grassMiddleSprite;
	private static BufferedImage trunkSprite;
	private static BufferedImage bridgeSprite;
	private static BufferedImage hiddenBlockSprite;
	
	//Items
	private static BufferedImage mushroomSprite;
	private static BufferedImage flowerSprite;
	private static BufferedImage oneUpSprite;
	private static BufferedImage coinSprite;
	private static BufferedImage keySprite;
	
	//Entities
	private static BufferedImage playerSprite;
	private static BufferedImage flagSprite;
	private static BufferedImage goombaSprite;
	private static BufferedImage greenTurtleSprite;
	private static BufferedImage redTurtleSprite;
	private static BufferedImage jumpGreenTurtleSprite;
	private static BufferedImage jumpRedTurtleSprite;
	private static BufferedImage bulletSprite;
	private static BufferedImage plantSprite;
	private static BufferedImage bossSprite;
	
	//Icons
	private static BufferedImage treeIconSprite;
	private static BufferedImage bulletStationIconSprite;
	private static BufferedImage pipeIconSprite;
	private static BufferedImage bossIconSprite;
	private static BufferedImage fireWheelIconSprite;
	private static BufferedImage lavaBallIconSprite;
	private static BufferedImage overWorldIconSprite;
	private static BufferedImage underWorldIconSprite;
	private static BufferedImage castleIconSprite;
	private static BufferedImage extraCoinIconSprite;
	
	//Decoration
	private static BufferedImage cloudSprite;
	

	Sprite(Game game) {
		SpriteSheet ss = new SpriteSheet(game.getSpriteSheet());
		if(Game.currentLevelType == LevelType.OVERWORLD || Game.currentLevelType == null) {
			LoadOverworldSprites(game, ss);
		}
		else if(Game.currentLevelType == LevelType.UNDERWORLD) {
			LoadUnderworldSprites(game, ss);
		}
		else if(Game.currentLevelType == LevelType.CASTLE) {
			LoadCastleSprites(game, ss);
		}
		else {
			System.out.println("Error loading sprites. Could not find level.");
			System.out.println(Game.currentLevelType);
			System.out.println("This is probably going to crash. Please report this to the dev asap.");
		}
		
	}
	
	public void LoadOverworldSprites(Game game, SpriteSheet ss) {
		floorSprite = ss.grabImage(10, 13);
		stoneSprite = ss.grabImage(11, 13);
		brickSprite = ss.grabImage(12, 13);
		emptyCoinSprite = ss.grabImage(13, 13);
		emptyCoinSolidSprite = ss.grabImage(9, 13);
		questionOneSprite = ss.grabImage(14, 13);
		questionTwoSprite = ss.grabImage(15, 13);
		questionThreeSprite = ss.grabImage(16, 13);
		poleTopSprite = ss.grabImage(12, 10);
		poleSectionSprite = ss.grabImage(12, 11);
		poleBottomSprite = ss.grabImage(12, 12);
		bulletTopSprite = ss.grabImage(10, 3);
		bulletBottomSprite = ss.grabImage(10, 4);
		bulletMiddleSprite = ss.grabImage(9, 3);
		damageSprite = ss.grabImage(1, 3);
		bridgeSprite = ss.grabImage(4, 3);
		grassLeftSprite = ss.grabImage(5, 3);
		grassMiddleSprite = ss.grabImage(6, 3);
		grassRightSprite = ss.grabImage(7, 3);
		trunkSprite = ss.grabImage(8, 3);
		pipeTopLeftSprite = ss.grabImage(15, 3);
		pipeTopRightSprite = ss.grabImage(16, 3);
		pipeLeftSprite = ss.grabImage(15, 4);
		pipeRightSprite = ss.grabImage(16, 4);
		hiddenBlockSprite = ss.grabImage(3, 4);
		
		mushroomSprite = ss.grabImage(6, 4);
		oneUpSprite = ss.grabImage(6, 5);
		flowerSprite = ss.grabImage(6, 6);
		coinSprite = ss.grabImage(6, 7);
		keySprite = ss.grabImage(6, 12);
		
		playerSprite = ss.grabImage(1, 1);
		flagSprite = ss.grabImage(9, 11);
		goombaSprite = ss.grabImage(12, 1);
		greenTurtleSprite = ss.grabImage(11, 1);
		redTurtleSprite = ss.grabImage(17, 5);
		jumpGreenTurtleSprite = ss.grabImage(17, 1);
		jumpRedTurtleSprite = ss.grabImage(19, 1);
		bulletSprite = ss.grabImage(10, 1);
		plantSprite = ss.grabImage(15, 1);
		bossSprite = ss.grabImage(3, 15, 64, 64);
		
		bossIconSprite = ss.grabImage(4, 4);
		treeIconSprite = ss.grabImage(9, 2);
		bulletStationIconSprite = ss.grabImage(1, 4);
		pipeIconSprite = ss.grabImage(9, 1);
		fireWheelIconSprite = ss.grabImage(5, 4);
		lavaBallIconSprite = ss.grabImage(20, 5);
		overWorldIconSprite = ss.grabImage(7, 4);
		underWorldIconSprite = ss.grabImage(8, 4);
		castleIconSprite = ss.grabImage(9, 4);
		extraCoinIconSprite = ss.grabImage(2, 4);
		
		cloudSprite = ss.grabImage(15, 7, 64, 64);
	}
	
	public void LoadUnderworldSprites(Game game, SpriteSheet ss) {
		floorSprite = ss.grabImage(10, 14);
		stoneSprite = ss.grabImage(11, 14);
		brickSprite = ss.grabImage(12, 14);
		emptyCoinSprite = ss.grabImage(13, 14);
		emptyCoinSolidSprite = ss.grabImage(9, 14);
		questionOneSprite = ss.grabImage(14, 14);
		questionTwoSprite = ss.grabImage(15, 14);
		questionThreeSprite = ss.grabImage(16, 14);
		poleTopSprite = ss.grabImage(12, 10);
		poleSectionSprite = ss.grabImage(12, 11);
		poleBottomSprite = ss.grabImage(12, 12);
		bulletTopSprite = ss.grabImage(10, 3);
		bulletBottomSprite = ss.grabImage(10, 4);
		bulletMiddleSprite = ss.grabImage(9, 3);
		damageSprite = ss.grabImage(1, 3);
		bridgeSprite = ss.grabImage(4, 3);
		grassLeftSprite = ss.grabImage(5, 3);
		grassMiddleSprite = ss.grabImage(6, 3);
		grassRightSprite = ss.grabImage(7, 3);
		trunkSprite = ss.grabImage(8, 3);
		pipeTopLeftSprite = ss.grabImage(15, 3);
		pipeTopRightSprite = ss.grabImage(16, 3);
		pipeLeftSprite = ss.grabImage(15, 4);
		pipeRightSprite = ss.grabImage(16, 4);
		hiddenBlockSprite = ss.grabImage(3, 4);
		
		mushroomSprite = ss.grabImage(6, 4);
		oneUpSprite = ss.grabImage(6, 5);
		flowerSprite = ss.grabImage(6, 6);
		coinSprite = ss.grabImage(6, 8);
		keySprite = ss.grabImage(6, 12);
		
		playerSprite = ss.grabImage(1, 1);
		flagSprite = ss.grabImage(9, 11);
		goombaSprite = ss.grabImage(14, 1);
		greenTurtleSprite = ss.grabImage(13, 1);
		redTurtleSprite = ss.grabImage(18, 5);
		jumpGreenTurtleSprite = ss.grabImage(18, 1);
		jumpRedTurtleSprite = ss.grabImage(19, 5);
		bulletSprite = ss.grabImage(10, 1);
		plantSprite = ss.grabImage(15, 1);
		bossSprite = ss.grabImage(3, 15, 64, 64);
		
		bossIconSprite = ss.grabImage(4, 4);
		treeIconSprite = ss.grabImage(9, 2);
		bulletStationIconSprite = ss.grabImage(1, 4);
		pipeIconSprite = ss.grabImage(9, 1);
		fireWheelIconSprite = ss.grabImage(5, 4);
		lavaBallIconSprite = ss.grabImage(20, 5);
		overWorldIconSprite = ss.grabImage(7, 4);
		underWorldIconSprite = ss.grabImage(8, 4);
		castleIconSprite = ss.grabImage(9, 4);
		extraCoinIconSprite = ss.grabImage(2, 4);
	}
	
	public void LoadCastleSprites(Game game, SpriteSheet ss) {
		floorSprite = ss.grabImage(10, 15);
		stoneSprite = ss.grabImage(11, 15);
		brickSprite = ss.grabImage(12, 15);
		emptyCoinSprite = ss.grabImage(13, 15);
		emptyCoinSolidSprite = ss.grabImage(9, 15);
		questionOneSprite = ss.grabImage(14, 15);
		questionTwoSprite = ss.grabImage(15, 15);
		questionThreeSprite = ss.grabImage(16, 15);
		poleTopSprite = ss.grabImage(12, 10);
		poleSectionSprite = ss.grabImage(12, 11);
		poleBottomSprite = ss.grabImage(12, 12);
		bulletTopSprite = ss.grabImage(10, 3);
		bulletBottomSprite = ss.grabImage(10, 4);
		bulletMiddleSprite = ss.grabImage(9, 3);
		damageSprite = ss.grabImage(2, 3);
		bridgeSprite = ss.grabImage(3, 3);
		grassLeftSprite = ss.grabImage(5, 3);
		grassMiddleSprite = ss.grabImage(6, 3);
		grassRightSprite = ss.grabImage(7, 3);
		trunkSprite = ss.grabImage(8, 3);
		pipeTopLeftSprite = ss.grabImage(15, 5);
		pipeTopRightSprite = ss.grabImage(16, 5);
		pipeLeftSprite = ss.grabImage(15, 6);
		pipeRightSprite = ss.grabImage(16, 6);
		hiddenBlockSprite = ss.grabImage(3, 4);
		
		mushroomSprite = ss.grabImage(6, 4);
		oneUpSprite = ss.grabImage(6, 5);
		flowerSprite = ss.grabImage(6, 6);
		coinSprite = ss.grabImage(6, 9);
		keySprite = ss.grabImage(6, 12);
		
		playerSprite = ss.grabImage(1, 1);
		flagSprite = ss.grabImage(9, 11);
		goombaSprite = ss.grabImage(12, 1);
		greenTurtleSprite = ss.grabImage(11, 1);
		redTurtleSprite = ss.grabImage(17, 5);
		jumpGreenTurtleSprite = ss.grabImage(17, 1);
		jumpRedTurtleSprite = ss.grabImage(19, 1);
		bulletSprite = ss.grabImage(10, 1);
		plantSprite = ss.grabImage(16, 1);
		bossSprite = ss.grabImage(3, 15, 64, 64);
		
		bossIconSprite = ss.grabImage(4, 4);
		treeIconSprite = ss.grabImage(9, 2);
		bulletStationIconSprite = ss.grabImage(1, 4);
		pipeIconSprite = ss.grabImage(9, 1);
		fireWheelIconSprite = ss.grabImage(5, 4);
		lavaBallIconSprite = ss.grabImage(20, 5);
		overWorldIconSprite = ss.grabImage(7, 4);
		underWorldIconSprite = ss.grabImage(8, 4);
		castleIconSprite = ss.grabImage(9, 4);
		extraCoinIconSprite = ss.grabImage(2, 4);
	}
	
	public static BufferedImage getBrickSprite() { return brickSprite; }
	public static BufferedImage getStoneSprite() { return stoneSprite; }
	public static BufferedImage getDamageSprite() { return damageSprite; }
	public static BufferedImage getFloorSprite() { return floorSprite; }
	public static BufferedImage getEmptyCoinSprite() { return emptyCoinSprite; }
	public static BufferedImage getEmptyCoinSolidSprite() { return emptyCoinSolidSprite; }
	public static BufferedImage getPoleTopSprite() { return poleTopSprite; }
	public static BufferedImage getPoleSectionSprite() { return poleSectionSprite; }
	public static BufferedImage getPoleBottomSprite() { return poleBottomSprite; }
	public static BufferedImage getBulletTopSprite() { return bulletTopSprite; }
	public static BufferedImage getBulletBottomSprite() { return bulletBottomSprite; }
	public static BufferedImage getBulletMiddleSprite() { return bulletMiddleSprite; }
	public static BufferedImage getPipeTopLeftSprite() { return pipeTopLeftSprite; }
	public static BufferedImage getPipeTopRightSprite() { return pipeTopRightSprite; }
	public static BufferedImage getPipeLeftSprite() { return pipeLeftSprite; }
	public static BufferedImage getPipeRightSprite() { return pipeRightSprite; }
	public static BufferedImage getGrassLeftSprite() { return grassLeftSprite; }
	public static BufferedImage getGrassRightSprite() { return grassRightSprite; }
	public static BufferedImage getGrassMiddleSprite() { return grassMiddleSprite; }
	public static BufferedImage getTrunkSprite() { return trunkSprite; }
	public static BufferedImage getBridgeSprite() { return bridgeSprite; }
	public static BufferedImage getHiddenBlockSprite() { return hiddenBlockSprite; }

	public static BufferedImage getQuestionOneSprite() { return questionOneSprite; }
	public static BufferedImage getQuestionTwoSprite() { return questionTwoSprite; }
	public static BufferedImage getQuestionThreeSprite() { return questionThreeSprite; }
	public static BufferedImage getPoleTop() { return poleTopSprite; }
	public static BufferedImage getPoleSection() { return poleSectionSprite; }
	public static BufferedImage getPoleBottom() { return poleBottomSprite; }
	public static BufferedImage getBulletTop() { return bulletTopSprite; }
	public static BufferedImage getBulletBottom() { return bulletBottomSprite; }
	
	public static BufferedImage getMushroomSprite() { return mushroomSprite; }
	public static BufferedImage getFlowerSprite() { return flowerSprite; }
	public static BufferedImage getOneUpSprite() { return oneUpSprite; }
	public static BufferedImage getCoinSprite() { return coinSprite; }
	public static BufferedImage getKeySprite() { return keySprite; }
	
	public static BufferedImage getPlayerSprite() { return playerSprite; }
	public static BufferedImage getFlagSprite() { return flagSprite; }
	public static BufferedImage getGoombaSprite() { return goombaSprite; }
	public static BufferedImage getGreenTurtleSprite() { return greenTurtleSprite; }
	public static BufferedImage getRedTurtleSprite() { return redTurtleSprite; }
	public static BufferedImage getJumpGreenTurtleSprite() { return jumpGreenTurtleSprite; }
	public static BufferedImage getJumpRedTurtleSprite() { return jumpRedTurtleSprite; }
	public static BufferedImage getBulletSprite() { return bulletSprite; }
	public static BufferedImage getPlantSprite() { return plantSprite; }
	public static BufferedImage getBossSprite() { return bossSprite; }
	
	public static BufferedImage getTreeIconSprite() { return treeIconSprite; }
	public static BufferedImage getBulletStationIconSprite() { return bulletStationIconSprite; }
	public static BufferedImage getPipeIconSprite() { return pipeIconSprite; }
	public static BufferedImage getBossIconSprite() { return bossIconSprite; }
	public static BufferedImage getFireWheelIconSprite() { return fireWheelIconSprite; }
	public static BufferedImage getLavaBallIconSprite() { return lavaBallIconSprite; }
	public static BufferedImage getOverWorldIconSprite() { return overWorldIconSprite; }
	public static BufferedImage getUnderWorldIconSprite() { return underWorldIconSprite; }
	public static BufferedImage getCastleIconSprite() { return castleIconSprite; }
	public static BufferedImage getExtraCoinIconSprite() { return extraCoinIconSprite; }
	
	public static BufferedImage getCloudSprite() { return cloudSprite; }
	
}
