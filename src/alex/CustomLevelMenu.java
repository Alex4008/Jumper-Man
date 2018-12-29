package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CustomLevelMenu {
	
	private Point levelSelectLogoLoc;
	private Point mainReturnLogoLoc;
	
	private Point levelOneLoc;
	private Point levelTwoLoc;
	private Point levelThreeLoc;
	private Point levelFourLoc;
	
	private BufferedImage levelSelectLogo;
	private BufferedImage mainReturnLogo;
	private BufferedImage emptyLogo;
	private BufferedImage levelOneLogo;
	private BufferedImage levelTwoLogo;
	private BufferedImage levelThreeLogo;
	private BufferedImage levelFourLogo;
	
	private BufferedImage mainReturnBlackLogo;
	private BufferedImage emptyBlackLogo;
	private BufferedImage levelOneBlackLogo;
	private BufferedImage levelTwoBlackLogo;
	private BufferedImage levelThreeBlackLogo;
	private BufferedImage levelFourBlackLogo;
	
	private BufferedImage emptyGrayLogo;
	
	private Rectangle mainReturnBox;
	private Rectangle levelOneBox;
	private Rectangle levelTwoBox;
	private Rectangle levelThreeBox;
	private Rectangle levelFourBox;
	
	private boolean hoverMainReturn;
	private boolean hoverlevelOne;
	private boolean hoverlevelTwo;
	private boolean hoverlevelThree;
	private boolean hoverlevelFour;
	
	CustomLevelMenu() {
		//Load images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			mainReturnLogo = loader.loadImage("/settingsMenuIcons/menuReturnLogo.png");
			levelSelectLogo = loader.loadImage("/customEditLevel/levelSelectLogo.png");
			levelOneLogo = loader.loadImage("/levelSelectIcons/levelOne.png");
			levelTwoLogo = loader.loadImage("/levelSelectIcons/levelTwo.png");
			levelThreeLogo = loader.loadImage("/levelSelectIcons/levelThree.png");
			levelFourLogo = loader.loadImage("/levelSelectIcons/levelFour.png");
			emptyLogo = loader.loadImage("/customEditLevel/emptyLogo.png");
			
			mainReturnBlackLogo = loader.loadImage("/settingsMenuIcons/menuReturnBlackLogo.png");
			levelOneBlackLogo = loader.loadImage("/levelSelectIcons/levelOne-black.png");
			levelTwoBlackLogo = loader.loadImage("/levelSelectIcons/levelTwo-black.png");
			levelThreeBlackLogo = loader.loadImage("/levelSelectIcons/levelThree-black.png");
			levelFourBlackLogo = loader.loadImage("/levelSelectIcons/levelFour-black.png");
			emptyBlackLogo = loader.loadImage("/customEditLevel/empty-black.png");
			
			emptyGrayLogo = loader.loadImage("/customEditLevel/empty-gray.png");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		loader = null;
		
		//Calculate locations
		int yValue = 20;
		levelSelectLogoLoc = new Point((Game.WIDTH / 2) - (levelSelectLogo.getWidth() / 2), yValue);
		
		yValue = yValue + levelSelectLogo.getHeight() + (Game.HEIGHT / 3);
		levelOneLoc = new Point((Game.WIDTH / 7) - (levelOneLogo.getWidth() / 20), yValue); 
		int xValue = (Game.WIDTH / 7) - (levelOneLogo.getWidth() / 20) + levelOneLogo.getWidth() + 30;
		levelTwoLoc = new Point(xValue, yValue); 
		int xValue2 = xValue + levelTwoLogo.getWidth() + 30;
		levelThreeLoc = new Point(xValue2, yValue);
		int xValue3 = xValue2 + levelThreeLogo.getWidth() + 30;
		levelFourLoc = new Point(xValue3, yValue); 
		
		mainReturnLogoLoc = new Point((Game.WIDTH / 2) - (mainReturnLogo.getWidth() / 2), Game.HEIGHT - 65);
		
		//Calculate bounding boxes
		levelOneBox = new Rectangle(levelOneLoc.x, levelOneLoc.y, levelOneLogo.getWidth(), levelOneLogo.getHeight());
		levelTwoBox = new Rectangle(levelTwoLoc.x, levelTwoLoc.y, levelTwoLogo.getWidth(), levelTwoLogo.getHeight());
		levelThreeBox = new Rectangle(levelThreeLoc.x, levelThreeLoc.y, levelThreeLogo.getWidth(), levelThreeLogo.getHeight());
		levelFourBox = new Rectangle(levelFourLoc.x, levelFourLoc.y, levelFourLogo.getWidth(), levelFourLogo.getHeight());
		
		mainReturnBox = new Rectangle(mainReturnLogoLoc.x, mainReturnLogoLoc.y, mainReturnLogo.getWidth(), mainReturnLogo.getHeight());
		
	}

	
	//Getters and setters
	public Point getLevelSelectLogoLoc() { return levelSelectLogoLoc; }
	public Point getLevelOneLoc() { return levelOneLoc; }
	public Point getLevelTwoLoc() { return levelTwoLoc; }
	public Point getLevelThreeLoc() { return levelThreeLoc; }
	public Point getLevelFourLoc() { return levelFourLoc; }
	public BufferedImage getLevelSelectLogo() { return levelSelectLogo; }
	public BufferedImage getLevelOneLogo() { return levelOneLogo; }
	public BufferedImage getLevelTwoLogo() { return levelTwoLogo; }
	public BufferedImage getLevelThreeLogo() { return levelThreeLogo; }
	public BufferedImage getLevelFourLogo() { return levelFourLogo; }
	public BufferedImage getEmptyLogo() { return emptyLogo; }
	public BufferedImage getLevelOneBlackLogo() { return levelOneBlackLogo; }
	public BufferedImage getLevelTwoBlackLogo() { return levelTwoBlackLogo; }
	public BufferedImage getLevelThreeBlackLogo() { return levelThreeBlackLogo; }
	public BufferedImage getLevelFourBlackLogo() { return levelFourBlackLogo; }
	public BufferedImage getEmptyBlackLogo() { return emptyBlackLogo; }
	public BufferedImage getEmptyGrayLogo() { return emptyGrayLogo; }
	public Rectangle getLevelOneBox() { return levelOneBox; }
	public Rectangle getLevelTwoBox() { return levelTwoBox; }
	public Rectangle getLevelThreeBox() { return levelThreeBox; }
	public Rectangle getLevelFourBox() { return levelFourBox; }
	public Point getMainReturnLogoLoc() { return mainReturnLogoLoc; }
	public BufferedImage getMainReturnLogo() { return mainReturnLogo; }
	public BufferedImage getMainReturnBlackLogo() { return mainReturnBlackLogo; }
	public Rectangle getMainReturnBox() { return mainReturnBox; }
	
	public boolean isHoverLevelOne() {return hoverlevelOne; }
	public boolean isHoverLevelTwo() { return hoverlevelTwo; }
	public boolean isHoverLevelThree() { return hoverlevelThree; }
	public boolean isHoverLevelFour() { return hoverlevelFour; }
	public boolean getHoverMainReturn() { return hoverMainReturn; }


	public void setHoverMainReturn(boolean hoverMainReturn) {
		this.hoverMainReturn = hoverMainReturn;
	}
	
	public void setHoverLevelOne(boolean hoverlevelOne) {
		this.hoverlevelOne = hoverlevelOne;
	}
	
	public void setHoverLevelTwo(boolean hoverlevelTwo) {
		this.hoverlevelTwo = hoverlevelTwo;
	}

	public void setHoverLevelThree(boolean hoverlevelThree) {
		this.hoverlevelThree = hoverlevelThree;
	}

	public void setHoverLevelFour(boolean hoverlevelFour) {
		this.hoverlevelFour = hoverlevelFour;
	}
}
