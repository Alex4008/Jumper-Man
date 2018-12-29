package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LevelSelectMenu {

	/*
	 * Ok. Look. Yes, I realize that this is awful practice, and I realize that all these buttons should have been
	 * made using the button class. But the fact is I forgot until it was too late. So don't hate on it too much
	 * ok thanks bye <3
	 */
	
	private Point levelSelectLogoLoc;
	private Point mainReturnLogoLoc;
	private Point customLevelLogoLoc;
	private Point worldOneLoc;
	private Point worldTwoLoc;
	private Point worldThreeLoc;
	private Point worldFourLoc;
	
	private Point worldOneLevelOneLoc;
	private Point worldOneLevelTwoLoc;
	private Point worldOneLevelThreeLoc;
	private Point worldOneLevelFourLoc;
	
	private Point worldTwoLevelOneLoc;
	private Point worldTwoLevelTwoLoc;
	private Point worldTwoLevelThreeLoc;
	private Point worldTwoLevelFourLoc;
	
	private Point worldThreeLevelOneLoc;
	private Point worldThreeLevelTwoLoc;
	private Point worldThreeLevelThreeLoc;
	private Point worldThreeLevelFourLoc;
	
	private Point worldFourLevelOneLoc;
	private Point worldFourLevelTwoLoc;
	private Point worldFourLevelThreeLoc;
	private Point worldFourLevelFourLoc;
	
	
	private BufferedImage levelSelectLogo;
	private BufferedImage mainReturnLogo;
	private BufferedImage customLevelLogo;
	private BufferedImage worldOneLogo;
	private BufferedImage worldTwoLogo;
	private BufferedImage worldThreeLogo;
	private BufferedImage worldFourLogo;
	private BufferedImage levelOneLogo;
	private BufferedImage levelTwoLogo;
	private BufferedImage levelThreeLogo;
	private BufferedImage levelFourLogo;
	
	private BufferedImage mainReturnBlackLogo;
	private BufferedImage customLevelBlackLogo;
	private BufferedImage levelOneBlackLogo;
	private BufferedImage levelTwoBlackLogo;
	private BufferedImage levelThreeBlackLogo;
	private BufferedImage levelFourBlackLogo;
	
	private Rectangle mainReturnBox;
	private Rectangle customLevelBox;
	private Rectangle worldOneLevelOneBox;
	private Rectangle worldOneLevelTwoBox;
	private Rectangle worldOneLevelThreeBox;
	private Rectangle worldOneLevelFourBox;
	
	private Rectangle worldTwoLevelOneBox;
	private Rectangle worldTwoLevelTwoBox;
	private Rectangle worldTwoLevelThreeBox;
	private Rectangle worldTwoLevelFourBox;
	
	private Rectangle worldThreeLevelOneBox;
	private Rectangle worldThreeLevelTwoBox;
	private Rectangle worldThreeLevelThreeBox;
	private Rectangle worldThreeLevelFourBox;
	
	private Rectangle worldFourLevelOneBox;
	private Rectangle worldFourLevelTwoBox;
	private Rectangle worldFourLevelThreeBox;
	private Rectangle worldFourLevelFourBox;
	
	private boolean hoverMainReturn;
	private boolean hoverCustomLevel;
	private boolean hoverWorldOneLevelOne;
	private boolean hoverWorldOneLevelTwo;
	private boolean hoverWorldOneLevelThree;
	private boolean hoverWorldOneLevelFour;
	
	private boolean hoverWorldTwoLevelOne;
	private boolean hoverWorldTwoLevelTwo;
	private boolean hoverWorldTwoLevelThree;
	private boolean hoverWorldTwoLevelFour;
	
	private boolean hoverWorldThreeLevelOne;
	private boolean hoverWorldThreeLevelTwo;
	private boolean hoverWorldThreeLevelThree;
	private boolean hoverWorldThreeLevelFour;
	
	private boolean hoverWorldFourLevelOne;
	private boolean hoverWorldFourLevelTwo;
	private boolean hoverWorldFourLevelThree;
	private boolean hoverWorldFourLevelFour;
	
	LevelSelectMenu() {
		//Load images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			mainReturnLogo = loader.loadImage("/settingsMenuIcons/menuReturnLogo.png");
			levelSelectLogo = loader.loadImage("/levelSelectIcons/levelSelectIcon.png");
			customLevelLogo = loader.loadImage("/levelSelectIcons/customLevelLogo.png");
			worldOneLogo = loader.loadImage("/levelSelectIcons/worldOne.png");
			worldTwoLogo = loader.loadImage("/levelSelectIcons/worldTwo.png");
			worldThreeLogo = loader.loadImage("/levelSelectIcons/worldThree.png");
			worldFourLogo = loader.loadImage("/levelSelectIcons/worldFour.png");
			levelOneLogo = loader.loadImage("/levelSelectIcons/levelOne.png");
			levelTwoLogo = loader.loadImage("/levelSelectIcons/levelTwo.png");
			levelThreeLogo = loader.loadImage("/levelSelectIcons/levelThree.png");
			levelFourLogo = loader.loadImage("/levelSelectIcons/levelFour.png");
			
			mainReturnBlackLogo = loader.loadImage("/settingsMenuIcons/menuReturnBlackLogo.png");
			customLevelBlackLogo = loader.loadImage("/levelSelectIcons/customLevel-black.png");
			levelOneBlackLogo = loader.loadImage("/levelSelectIcons/levelOne-black.png");
			levelTwoBlackLogo = loader.loadImage("/levelSelectIcons/levelTwo-black.png");
			levelThreeBlackLogo = loader.loadImage("/levelSelectIcons/levelThree-black.png");
			levelFourBlackLogo = loader.loadImage("/levelSelectIcons/levelFour-black.png");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		loader = null;
		
		//Calculate locations
		int yValue = 20;
		levelSelectLogoLoc = new Point((Game.WIDTH / 2) - (levelSelectLogo.getWidth() / 2), yValue);
		
		yValue = yValue + levelSelectLogo.getHeight() + 50;
		worldOneLoc = new Point((Game.WIDTH / 20) - (worldOneLogo.getWidth() / 20), yValue);
		int xValue = (Game.WIDTH / 20) - (worldOneLogo.getWidth() / 20) + worldOneLogo.getWidth() + 30;
		worldOneLevelOneLoc = new Point(xValue, yValue); 
		int xValue2 = xValue + levelOneLogo.getWidth() + 30;
		worldOneLevelTwoLoc = new Point(xValue2, yValue); 
		int xValue3 = xValue2 + levelTwoLogo.getWidth() + 30;
		worldOneLevelThreeLoc = new Point(xValue3, yValue);
		int xValue4 = xValue3 + levelThreeLogo.getWidth() + 30;
		worldOneLevelFourLoc = new Point(xValue4, yValue); 
		
		yValue = yValue + worldOneLogo.getHeight() + 75;
		worldTwoLoc = new Point((Game.WIDTH / 20) - (worldTwoLogo.getWidth() / 20), yValue);
		worldTwoLevelOneLoc = new Point(xValue, yValue); 
		worldTwoLevelTwoLoc = new Point(xValue2, yValue); 
		worldTwoLevelThreeLoc = new Point(xValue3, yValue);
		worldTwoLevelFourLoc = new Point(xValue4, yValue); 
		
		yValue = yValue + worldTwoLogo.getHeight() + 75;
		worldThreeLoc = new Point((Game.WIDTH / 20) - (worldThreeLogo.getWidth() / 20), yValue);
		worldThreeLevelOneLoc = new Point(xValue, yValue); 
		worldThreeLevelTwoLoc = new Point(xValue2, yValue); 
		worldThreeLevelThreeLoc = new Point(xValue3, yValue);
		worldThreeLevelFourLoc = new Point(xValue4, yValue); 
		
		yValue = yValue + worldThreeLogo.getHeight() + 75;
		worldFourLoc = new Point((Game.WIDTH / 20) - (worldFourLogo.getWidth() / 20), yValue);
		worldFourLevelOneLoc = new Point(xValue, yValue); 
		worldFourLevelTwoLoc = new Point(xValue2, yValue); 
		worldFourLevelThreeLoc = new Point(xValue3, yValue);
		worldFourLevelFourLoc = new Point(xValue4, yValue); 
		
		yValue = yValue + worldFourLogo.getHeight() + 75;
		mainReturnLogoLoc = new Point((Game.WIDTH / 3) - (mainReturnLogo.getWidth() / 2), yValue);
		customLevelLogoLoc = new Point((Game.WIDTH / 2) - (mainReturnLogo.getWidth() / 2), yValue);
		
		//Calculate bounding boxes
		worldOneLevelOneBox = new Rectangle(worldOneLevelOneLoc.x, worldOneLevelOneLoc.y, levelOneLogo.getWidth(), levelOneLogo.getHeight());
		worldOneLevelTwoBox = new Rectangle(worldOneLevelTwoLoc.x, worldOneLevelTwoLoc.y, levelTwoLogo.getWidth(), levelTwoLogo.getHeight());
		worldOneLevelThreeBox = new Rectangle(worldOneLevelThreeLoc.x, worldOneLevelThreeLoc.y, levelThreeLogo.getWidth(), levelThreeLogo.getHeight());
		worldOneLevelFourBox = new Rectangle(worldOneLevelFourLoc.x, worldOneLevelFourLoc.y, levelFourLogo.getWidth(), levelFourLogo.getHeight());
		
		worldTwoLevelOneBox = new Rectangle(worldTwoLevelOneLoc.x, worldTwoLevelOneLoc.y, levelOneLogo.getWidth(), levelOneLogo.getHeight());
		worldTwoLevelTwoBox = new Rectangle(worldTwoLevelTwoLoc.x, worldTwoLevelTwoLoc.y, levelTwoLogo.getWidth(), levelTwoLogo.getHeight());
		worldTwoLevelThreeBox = new Rectangle(worldTwoLevelThreeLoc.x, worldTwoLevelThreeLoc.y, levelThreeLogo.getWidth(), levelThreeLogo.getHeight());
		worldTwoLevelFourBox = new Rectangle(worldTwoLevelFourLoc.x, worldTwoLevelFourLoc.y, levelFourLogo.getWidth(), levelFourLogo.getHeight());
		
		worldThreeLevelOneBox = new Rectangle(worldThreeLevelOneLoc.x, worldThreeLevelOneLoc.y, levelOneLogo.getWidth(), levelOneLogo.getHeight());
		worldThreeLevelTwoBox = new Rectangle(worldThreeLevelTwoLoc.x, worldThreeLevelTwoLoc.y, levelTwoLogo.getWidth(), levelTwoLogo.getHeight());
		worldThreeLevelThreeBox = new Rectangle(worldThreeLevelThreeLoc.x, worldThreeLevelThreeLoc.y, levelThreeLogo.getWidth(), levelThreeLogo.getHeight());
		worldThreeLevelFourBox = new Rectangle(worldThreeLevelFourLoc.x, worldThreeLevelFourLoc.y, levelFourLogo.getWidth(), levelFourLogo.getHeight());
		
		worldFourLevelOneBox = new Rectangle(worldFourLevelOneLoc.x, worldFourLevelOneLoc.y, levelOneLogo.getWidth(), levelOneLogo.getHeight());
		worldFourLevelTwoBox = new Rectangle(worldFourLevelTwoLoc.x, worldFourLevelTwoLoc.y, levelTwoLogo.getWidth(), levelTwoLogo.getHeight());
		worldFourLevelThreeBox = new Rectangle(worldFourLevelThreeLoc.x, worldFourLevelThreeLoc.y, levelThreeLogo.getWidth(), levelThreeLogo.getHeight());
		worldFourLevelFourBox = new Rectangle(worldFourLevelFourLoc.x, worldFourLevelFourLoc.y, levelFourLogo.getWidth(), levelFourLogo.getHeight());
		
		mainReturnBox = new Rectangle(mainReturnLogoLoc.x, mainReturnLogoLoc.y, mainReturnLogo.getWidth(), mainReturnLogo.getHeight());
		customLevelBox = new Rectangle(customLevelLogoLoc.x, customLevelLogoLoc.y, customLevelLogo.getWidth(), customLevelLogo.getHeight());
		
	}

	
	//Getters and setters
	public Point getLevelSelectLogoLoc() { return levelSelectLogoLoc; }
	public Point getMainReturnLogoLoc() { return mainReturnLogoLoc; }
	public Point getCustomLevelLogoLoc() { return customLevelLogoLoc; }
	public Point getWorldOneLoc() { return worldOneLoc; }
	public Point getWorldTwoLoc() { return worldTwoLoc; }
	public Point getWorldThreeLoc() { return worldThreeLoc; }
	public Point getWorldFourLoc() { return worldFourLoc; }
	public Point getWorldOneLevelOneLoc() { return worldOneLevelOneLoc; }
	public Point getWorldOneLevelTwoLoc() { return worldOneLevelTwoLoc; }
	public Point getWorldOneLevelThreeLoc() { return worldOneLevelThreeLoc; }
	public Point getWorldOneLevelFourLoc() { return worldOneLevelFourLoc; }
	public Point getWorldTwoLevelOneLoc() { return worldTwoLevelOneLoc; }
	public Point getWorldTwoLevelTwoLoc() { return worldTwoLevelTwoLoc; }
	public Point getWorldTwoLevelThreeLoc() { return worldTwoLevelThreeLoc; }
	public Point getWorldTwoLevelFourLoc() { return worldTwoLevelFourLoc; }
	public Point getWorldThreeLevelOneLoc() { return worldThreeLevelOneLoc; }
	public Point getWorldThreeLevelTwoLoc() { return worldThreeLevelTwoLoc; }
	public Point getWorldThreeLevelThreeLoc() { return worldThreeLevelThreeLoc; }
	public Point getWorldThreeLevelFourLoc() { return worldThreeLevelFourLoc; }
	public Point getWorldFourLevelOneLoc() { return worldFourLevelOneLoc; }
	public Point getWorldFourLevelTwoLoc() { return worldFourLevelTwoLoc; }
	public Point getWorldFourLevelThreeLoc() { return worldFourLevelThreeLoc; }
	public Point getWorldFourLevelFourLoc() { return worldFourLevelFourLoc; }
	public BufferedImage getLevelSelectLogo() { return levelSelectLogo; }
	public BufferedImage getWorldOneLogo() { return worldOneLogo; }
	public BufferedImage getWorldTwoLogo() { return worldTwoLogo; }
	public BufferedImage getWorldThreeLogo() { return worldThreeLogo; }
	public BufferedImage getWorldFourLogo() { return worldFourLogo; }
	public BufferedImage getLevelOneLogo() { return levelOneLogo; }
	public BufferedImage getLevelTwoLogo() { return levelTwoLogo; }
	public BufferedImage getLevelThreeLogo() { return levelThreeLogo; }
	public BufferedImage getLevelFourLogo() { return levelFourLogo; }
	public BufferedImage getLevelOneBlackLogo() { return levelOneBlackLogo; }
	public BufferedImage getLevelTwoBlackLogo() { return levelTwoBlackLogo; }
	public BufferedImage getLevelThreeBlackLogo() { return levelThreeBlackLogo; }
	public BufferedImage getLevelFourBlackLogo() { return levelFourBlackLogo; }
	public Rectangle getWorldOneLevelOneBox() { return worldOneLevelOneBox; }
	public Rectangle getWorldOneLevelTwoBox() { return worldOneLevelTwoBox; }
	public Rectangle getWorldOneLevelThreeBox() { return worldOneLevelThreeBox; }
	public Rectangle getWorldOneLevelFourBox() { return worldOneLevelFourBox; }
	public Rectangle getWorldTwoLevelOneBox() { return worldTwoLevelOneBox; }
	public Rectangle getWorldTwoLevelTwoBox() { return worldTwoLevelTwoBox; }
	public Rectangle getWorldTwoLevelThreeBox() { return worldTwoLevelThreeBox; }
	public Rectangle getWorldTwoLevelFourBox() { return worldTwoLevelFourBox; }
	public Rectangle getWorldThreeLevelOneBox() { return worldThreeLevelOneBox; }
	public Rectangle getWorldThreeLevelTwoBox() { return worldThreeLevelTwoBox; }
	public Rectangle getWorldThreeLevelThreeBox() { return worldThreeLevelThreeBox; }
	public Rectangle getWorldThreeLevelFourBox() { return worldThreeLevelFourBox; }
	public Rectangle getWorldFourLevelOneBox() { return worldFourLevelOneBox; }
	public Rectangle getWorldFourLevelTwoBox() { return worldFourLevelTwoBox; }
	public Rectangle getWorldFourLevelThreeBox() { return worldFourLevelThreeBox; }
	public Rectangle getWorldFourLevelFourBox() { return worldFourLevelFourBox; }
	public BufferedImage getMainReturnLogo() { return mainReturnLogo; }
	public BufferedImage getMainReturnBlackLogo() { return mainReturnBlackLogo; }
	public BufferedImage getCustomLevelLogo() { return customLevelLogo; }
	public BufferedImage getCustomLevelBlackLogo() { return customLevelBlackLogo; }
	public Rectangle getMainReturnBox() { return mainReturnBox; }
	public Rectangle getCustomLevelBox() { return customLevelBox; }
	
	public boolean isHoverWorldOneLevelOne() {return hoverWorldOneLevelOne; }
	public boolean isHoverWorldOneLevelTwo() { return hoverWorldOneLevelTwo; }
	public boolean isHoverWorldOneLevelThree() { return hoverWorldOneLevelThree; }
	public boolean isHoverWorldOneLevelFour() { return hoverWorldOneLevelFour; }
	public boolean isHoverWorldTwoLevelOne() { return hoverWorldTwoLevelOne; }
	public boolean isHoverWorldTwoLevelTwo() { return hoverWorldTwoLevelTwo; }
	public boolean isHoverWorldTwoLevelThree() { return hoverWorldTwoLevelThree; }
	public boolean isHoverWorldTwoLevelFour() { return hoverWorldTwoLevelFour; }
	public boolean isHoverWorldThreeLevelOne() { return hoverWorldThreeLevelOne; }
	public boolean isHoverWorldThreeLevelTwo() { return hoverWorldThreeLevelTwo; }
	public boolean isHoverWorldThreeLevelThree() { return hoverWorldThreeLevelThree; }
	public boolean isHoverWorldThreeLevelFour() { return hoverWorldThreeLevelFour; }
	public boolean isHoverWorldFourLevelOne() { return hoverWorldFourLevelOne; }
	public boolean isHoverWorldFourLevelTwo() { return hoverWorldFourLevelTwo; }
	public boolean isHoverWorldFourLevelThree() { return hoverWorldFourLevelThree; }
	public boolean isHoverWorldFourLevelFour() { return hoverWorldFourLevelFour; }
	public boolean getHoverMainReturn() { return hoverMainReturn; }
	public boolean getHoverCustomLevel() { return hoverCustomLevel; }
	

	public void setHoverMainReturn(boolean hoverMainReturn) {
		this.hoverMainReturn = hoverMainReturn;
	}
	
	public void setHoverCustomLevel(boolean hoverCustomLevel) {
		this.hoverCustomLevel = hoverCustomLevel;
	}
	
	public void setHoverWorldOneLevelOne(boolean hoverWorldOneLevelOne) {
		this.hoverWorldOneLevelOne = hoverWorldOneLevelOne;
	}
	
	public void setHoverWorldOneLevelTwo(boolean hoverWorldOneLevelTwo) {
		this.hoverWorldOneLevelTwo = hoverWorldOneLevelTwo;
	}

	public void setHoverWorldOneLevelThree(boolean hoverWorldOneLevelThree) {
		this.hoverWorldOneLevelThree = hoverWorldOneLevelThree;
	}

	public void setHoverWorldOneLevelFour(boolean hoverWorldOneLevelFour) {
		this.hoverWorldOneLevelFour = hoverWorldOneLevelFour;
	}
	
	public void setHoverWorldTwoLevelOne(boolean hoverWorldTwoLevelOne) {
		this.hoverWorldTwoLevelOne = hoverWorldTwoLevelOne;
	}

	public void setHoverWorldTwoLevelTwo(boolean hoverWorldTwoLevelTwo) {
		this.hoverWorldTwoLevelTwo = hoverWorldTwoLevelTwo;
	}

	public void setHoverWorldTwoLevelThree(boolean hoverWorldTwoLevelThree) {
		this.hoverWorldTwoLevelThree = hoverWorldTwoLevelThree;
	}

	public void setHoverWorldTwoLevelFour(boolean hoverWorldTwoLevelFour) {
		this.hoverWorldTwoLevelFour = hoverWorldTwoLevelFour;
	}

	public void setHoverWorldThreeLevelOne(boolean hoverWorldThreeLevelOne) {
		this.hoverWorldThreeLevelOne = hoverWorldThreeLevelOne;
	}

	public void setHoverWorldThreeLevelTwo(boolean hoverWorldThreeLevelTwo) {
		this.hoverWorldThreeLevelTwo = hoverWorldThreeLevelTwo;
	}
	
	public void setHoverWorldThreeLevelThree(boolean hoverWorldThreeLevelThree) {
		this.hoverWorldThreeLevelThree = hoverWorldThreeLevelThree;
	}
	
	public void setHoverWorldThreeLevelFour(boolean hoverWorldThreeLevelFour) {
		this.hoverWorldThreeLevelFour = hoverWorldThreeLevelFour;
	}

	public void setHoverWorldFourLevelOne(boolean hoverWorldFourLevelOne) {
		this.hoverWorldFourLevelOne = hoverWorldFourLevelOne;
	}
	
	public void setHoverWorldFourLevelTwo(boolean hoverWorldFourLevelTwo) {
		this.hoverWorldFourLevelTwo = hoverWorldFourLevelTwo;
	}

	public void setHoverWorldFourLevelThree(boolean hoverWorldFourLevelThree) {
		this.hoverWorldFourLevelThree = hoverWorldFourLevelThree;
	}

	public void setHoverWorldFourLevelFour(boolean hoverWorldFourLevelFour) {
		this.hoverWorldFourLevelFour = hoverWorldFourLevelFour;
	}

	
	
}
