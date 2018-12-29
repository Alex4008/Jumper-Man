package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MainMenu {

	private Point titleLogoLoc;
	private Point startGameLoc;
	private Point levelSelectLoc;
	private Point levelEditLoc;
	private Point settingsLoc;
	private Point exitGameLoc;
	private Point devLogoLoc;
	
	private BufferedImage titleLogo;
	private BufferedImage startImageBlack;
	private BufferedImage levelSelectImageBlack;
	private BufferedImage levelEditImageBlack;
	private BufferedImage settingsImageBlack;
	private BufferedImage exitGameImageBlack;
	private BufferedImage startImage;
	private BufferedImage levelSelectImage;
	private BufferedImage levelEditImage;
	private BufferedImage settingsImage;
	private BufferedImage exitGameImage;
	private BufferedImage devImage;
	
	private Rectangle startGameBox; //Starts the game
	private Rectangle levelSelectBox; //Level select brings you to a screen in which you can select which level to play.
	private Rectangle levelEditBox; //Takes you to the level editor
	private Rectangle settingsBox; //Shows the settings page. 
	private Rectangle exitGameBox; //Exits the game. Same as closing the window manually.
	
	private boolean hoverStart;
	private boolean hoverLevelSelect;
	private boolean hoverLevelEdit;
	private boolean hoverSettings;
	private boolean hoverExitGame;
	
	MainMenu() {
		//Load images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			titleLogo = loader.loadImage("/mainMenuIcons/titleLogo.png");
			startImageBlack = loader.loadImage("/mainMenuIcons/start-black.png");
			levelSelectImageBlack = loader.loadImage("/mainMenuIcons/levelSelect-black.png");
			levelEditImageBlack = loader.loadImage("/mainMenuIcons/levelEdit-black.png");
			settingsImageBlack = loader.loadImage("/mainMenuIcons/setting-black.png");
			exitGameImageBlack = loader.loadImage("/mainMenuIcons/exit-black.png");
			
			startImage = loader.loadImage("/mainMenuIcons/startLogo.png");
			levelSelectImage = loader.loadImage("/mainMenuIcons/levelSelectLogo.png");
			levelEditImage = loader.loadImage("/mainMenuIcons/levelEditLogo.png");
			settingsImage = loader.loadImage("/mainMenuIcons/settingsLogo.png");
			exitGameImage = loader.loadImage("/mainMenuIcons/exitLogo.png");
			devImage = loader.loadImage("/mainMenuIcons/devBy.png");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		loader = null;
		
		//Calculate locations
		int yValue = 15;
		titleLogoLoc = new Point((Game.WIDTH / 2) - (titleLogo.getWidth() / 2), yValue);
		yValue = yValue + titleLogo.getHeight() + 50;
		startGameLoc = new Point((Game.WIDTH / 2) - (startImage.getWidth() / 2), yValue);
		yValue = yValue + startImageBlack.getHeight() + 50;
		levelSelectLoc = new Point((Game.WIDTH / 2) - (levelSelectImage.getWidth() / 2), yValue);
		yValue = yValue + levelSelectImageBlack.getHeight() + 50;
		levelEditLoc = new Point((Game.WIDTH / 2) - (levelEditImage.getWidth() / 2), yValue);
		yValue = yValue + levelEditImageBlack.getHeight() + 50;
		settingsLoc = new Point((Game.WIDTH / 2) - (settingsImage.getWidth() / 2), yValue);
		yValue = yValue + settingsImageBlack.getHeight() + 50;
		exitGameLoc = new Point((Game.WIDTH / 2) - (exitGameImage.getWidth() / 2), yValue);
		devLogoLoc = new Point((Game.WIDTH / 2) - (devImage.getWidth() / 2), Game.HEIGHT - 50);
		
		//Calculate bounding boxes
		startGameBox = new Rectangle(startGameLoc.x, startGameLoc.y, startImage.getWidth(), startImage.getHeight());
		levelSelectBox = new Rectangle(levelSelectLoc.x, levelSelectLoc.y, levelSelectImage.getWidth(), levelSelectImage.getHeight());
		levelEditBox = new Rectangle(levelEditLoc.x, levelEditLoc.y, levelEditImage.getWidth(), levelEditImage.getHeight());
		settingsBox = new Rectangle(settingsLoc.x, settingsLoc.y, settingsImage.getWidth(), settingsImage.getHeight());
		exitGameBox = new Rectangle(exitGameLoc.x, exitGameLoc.y, exitGameImage.getWidth(), exitGameImage.getHeight());
	}

	//Getters and setters
	public Point getTitleLogoLoc() { return titleLogoLoc; }
	public Point getStartGameLoc() { return startGameLoc; }
	public Point getLevelSelectLoc() { return levelSelectLoc; }
	public Point getLevelEditLoc() { return levelEditLoc; }
	public Point getSettingsLoc() { return settingsLoc; }
	public Point getExitGameLoc() { return exitGameLoc; }
	public Point getDevLogoLoc() { return devLogoLoc; }
	public BufferedImage getTitleLogo() { return titleLogo; }
	public BufferedImage getStartImageBlack() { return startImageBlack; }
	public BufferedImage getLevelSelectImageBlack() { return levelSelectImageBlack; }
	public BufferedImage getLevelEditImageBlack() { return levelEditImageBlack; }
	public BufferedImage getSettingsImageBlack() { return settingsImageBlack; }
	public BufferedImage getExitGameImageBlack() { return exitGameImageBlack; }
	public BufferedImage getStartImage() { return startImage; }
	public BufferedImage getLevelSelectImage() { return levelSelectImage; }
	public BufferedImage getLevelEditImage() { return levelEditImage; }
	public BufferedImage getSettingsImage() { return settingsImage; }
	public BufferedImage getExitGameImage() { return exitGameImage; }
	public BufferedImage getDevLogoImage() { return devImage; }
	public Rectangle getStartGameBox() { return startGameBox; }
	public Rectangle getLevelSelectBox() { return levelSelectBox; }
	public Rectangle getLevelEditBox() { return levelEditBox; }
	public Rectangle getSettingsBox() { return settingsBox; }
	public Rectangle getExitGameBox() { return exitGameBox; }

	public boolean isHoverStart() { return hoverStart; }
	public boolean isHoverLevelSelect() { return hoverLevelSelect; }
	public boolean isHoverLevelEdit() { return hoverLevelEdit; }
	public boolean isHoverSettings() { return hoverSettings; }
	public boolean isHoverExitGame() { return hoverExitGame; }
	
	public void setHoverStart(boolean hoverStart) {
		this.hoverStart = hoverStart;
	}
	
	public void setHoverLevelSelect(boolean hoverLevelSelect) {
		this.hoverLevelSelect = hoverLevelSelect;
	}

	public void setHoverLevelEdit(boolean hoverLevelEdit) {
		this.hoverLevelEdit = hoverLevelEdit;
	}
	
	public void setHoverSettings(boolean hoverSettings) {
		this.hoverSettings = hoverSettings;
	}

	public void setHoverExitGame(boolean hoverExitGame) {
		this.hoverExitGame = hoverExitGame;
	}

	
}
