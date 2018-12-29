package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PauseMenu {
	
	private Point pauseIconLoc;
	private Point resumeIconLoc;
	private Point settingsIconLoc;
	private Point returnMenuIconLoc;
	private Point controlsIconLoc;
	
	private BufferedImage pauseIcon;
	private BufferedImage resumeIcon;
	private BufferedImage resumeIconHover;
	private BufferedImage settingsIcon;
	private BufferedImage settingsIconHover;
	private BufferedImage returnMenuIcon;
	private BufferedImage returnMenuIconHover;
	private BufferedImage controlsIcon;
	
	private Rectangle resumeBox;
	private Rectangle settingsBox;
	private Rectangle returnMenuBox;
	
	private boolean hoverResume;
	private boolean hoverSettings;
	private boolean hoverReturnMenu;
	
	
	PauseMenu() {
		//Load images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			pauseIcon = loader.loadImage("/pauseMenuIcons/pauseIcon.png");
			resumeIcon = loader.loadImage("/pauseMenuIcons/resumeGameIcon.png");
			resumeIconHover = loader.loadImage("/pauseMenuIcons/resumeGameIcon-Hover.png");
			settingsIcon = loader.loadImage("/pauseMenuIcons/settingsIcon.png");
			settingsIconHover = loader.loadImage("/pauseMenuIcons/settingsIcon-Hover.png");
			returnMenuIcon = loader.loadImage("/pauseMenuIcons/mainMenuIcon.png");
			returnMenuIconHover = loader.loadImage("/pauseMenuIcons/mainMenuIcon-Hover.png");
			controlsIcon = loader.loadImage("/pauseMenuIcons/controlsIcon.png");
		}
		catch(IOException e) {
			e.printStackTrace(); 
		}
		loader = null;
		
		//Calculate Locations
		int yValue = 20;
		pauseIconLoc = new Point((Game.WIDTH / 2) - (pauseIcon.getWidth() / 2), yValue);
		yValue = yValue + pauseIcon.getHeight() + 50;
		resumeIconLoc = new Point((Game.WIDTH / 2) - (resumeIcon.getWidth() / 2), yValue);
		yValue = yValue + resumeIcon.getHeight() + 50;
		settingsIconLoc = new Point((Game.WIDTH / 2) - (settingsIcon.getWidth() / 2), yValue);
		yValue = yValue + settingsIcon.getHeight() + 50;
		returnMenuIconLoc = new Point((Game.WIDTH / 2) - (returnMenuIcon.getWidth() / 2), yValue);

		//Is located at the bottom of the screen
		controlsIconLoc = new Point((Game.WIDTH / 2) - (controlsIcon.getWidth() / 2), Game.HEIGHT - controlsIcon.getHeight() - 25);
		
		//Calculate bounding boxes
		resumeBox = new Rectangle(resumeIconLoc.x, resumeIconLoc.y, resumeIcon.getWidth(), resumeIcon.getHeight());
		settingsBox = new Rectangle(settingsIconLoc.x, settingsIconLoc.y, settingsIcon.getWidth(), settingsIcon.getHeight());
		returnMenuBox = new Rectangle(returnMenuIconLoc.x, returnMenuIconLoc.y, returnMenuIcon.getWidth(), returnMenuIcon.getHeight());
		
	}


	public Point getPauseIconLoc() { return pauseIconLoc; }
	public Point getResumeIconLoc() { return resumeIconLoc; }
	public Point getSettingsIconLoc() { return settingsIconLoc; }
	public Point getReturnMenuIconLoc() { return returnMenuIconLoc; }
	public Point getControlsIconLoc() { return controlsIconLoc; }
	public BufferedImage getPauseIcon() { return pauseIcon; }
	public BufferedImage getResumeIcon() { return resumeIcon; }
	public BufferedImage getResumeHoverIcon() { return resumeIconHover; }
	public BufferedImage getSettingsIcon() { return settingsIcon; }
	public BufferedImage getSettingsHoverIcon() { return settingsIconHover; }
	public BufferedImage getReturnMenuIcon() { return returnMenuIcon; }
	public BufferedImage getReturnMenuHoverIcon() { return returnMenuIconHover; }
	public BufferedImage getControlsIcon() { return controlsIcon; }
	public Rectangle getResumeBox() { return resumeBox; }
	public Rectangle getSettingsBox() { return settingsBox; }
	public Rectangle getReturnMenuBox() { return returnMenuBox; }
	public boolean isHoverResume() { return hoverResume; }
	public boolean isHoverSettings() { return hoverSettings; }
	public boolean isHoverReturnMenu() { return hoverReturnMenu; }

	public void setHoverResume(boolean hoverResume) {
		this.hoverResume = hoverResume;
	}
	public void setHoverSettings(boolean hoverSettings) {
		this.hoverSettings = hoverSettings;
	}
	public void setHoverReturnMenu(boolean hoverReturnMenu) {
		this.hoverReturnMenu = hoverReturnMenu;
	}
	
	
	
}
