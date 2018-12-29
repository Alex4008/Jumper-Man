package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PauseEditMenu {
	
	private Point pauseIconLoc;
	private Point resumeIconLoc;
	private Point discardLevelIconLoc;
	private Point saveLevelIconLoc;
	private Point controlsIconLoc;
	
	private BufferedImage pauseIcon;
	private BufferedImage resumeIcon;
	private BufferedImage resumeBlackIcon;
	private BufferedImage discardLevelIcon;
	private BufferedImage discardLevelBlackIcon;
	private BufferedImage saveLevelIcon;
	private BufferedImage saveLevelBlackIcon;
	private BufferedImage controlsIcon;
	
	private Rectangle resumeBox;
	private Rectangle discardBox;
	private Rectangle saveBox;
	
	private boolean hoverResume;
	private boolean hoverDiscard;
	private boolean hoverSave;
	
	
	PauseEditMenu() {
		//Load images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			pauseIcon = loader.loadImage("/pauseMenuIcons/pauseIcon.png");
			resumeIcon = loader.loadImage("/pauseEditIcons/resumeLevelIcon.png");
			resumeBlackIcon = loader.loadImage("/pauseEditIcons/resumeLevel-black.png");
			discardLevelIcon = loader.loadImage("/pauseEditIcons/discardLevelIcon.png");
			discardLevelBlackIcon = loader.loadImage("/pauseEditIcons/discardLevel-black.png");
			saveLevelIcon = loader.loadImage("/pauseEditIcons/saveLevelIcon.png");
			saveLevelBlackIcon = loader.loadImage("/pauseEditIcons/saveLevel-black.png");
			controlsIcon = loader.loadImage("/pauseEditIcons/controlsIcon.png");
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
		discardLevelIconLoc = new Point((Game.WIDTH / 2) - (discardLevelIcon.getWidth() / 2), yValue);
		yValue = yValue + discardLevelIcon.getHeight() + 50;
		saveLevelIconLoc = new Point((Game.WIDTH / 2) - (saveLevelIcon.getWidth() / 2), yValue);

		//Is located at the bottom of the screen
		controlsIconLoc = new Point((Game.WIDTH / 2) - (controlsIcon.getWidth() / 2), Game.HEIGHT - controlsIcon.getHeight() - 25);
		
		//Calculate bounding boxes
		resumeBox = new Rectangle(resumeIconLoc.x, resumeIconLoc.y, resumeIcon.getWidth(), resumeIcon.getHeight());
		discardBox = new Rectangle(discardLevelIconLoc.x, discardLevelIconLoc.y, discardLevelIcon.getWidth(), discardLevelIcon.getHeight());
		saveBox = new Rectangle(saveLevelIconLoc.x, saveLevelIconLoc.y, saveLevelIcon.getWidth(), saveLevelIcon.getHeight());
		
	}


	public Point getPauseIconLoc() { return pauseIconLoc; }
	public Point getResumeIconLoc() { return resumeIconLoc; }
	public Point getDiscardLevelIconLoc() { return discardLevelIconLoc; }
	public Point getSaveLevelIconLoc() { return saveLevelIconLoc; }
	public Point getControlsIconLoc() { return controlsIconLoc; }
	public BufferedImage getPauseIcon() { return pauseIcon; }
	public BufferedImage getResumeIcon() { return resumeIcon; }
	public BufferedImage getResumeBlackIcon() { return resumeBlackIcon; }
	public BufferedImage getDiscardLevelIcon() { return discardLevelIcon; }
	public BufferedImage getDiscardLevelBlackIcon() { return discardLevelBlackIcon; }
	public BufferedImage getSaveLevelIcon() { return saveLevelIcon; }
	public BufferedImage getSaveLevelBlackIcon() { return saveLevelBlackIcon; }
	public BufferedImage getControlsIcon() { return controlsIcon; }
	public Rectangle getResumeBox() { return resumeBox; }
	public Rectangle getDiscardLevelBox() { return discardBox; }
	public Rectangle getSaveLevelBox() { return saveBox; }
	public boolean isHoverResume() { return hoverResume; }
	public boolean isHoverDiscardLevel() { return hoverDiscard; }
	public boolean isHoverSaveLevel() { return hoverSave; }

	public void setHoverResume(boolean hoverResume) {
		this.hoverResume = hoverResume;
	}
	public void setHoverDiscardLevel(boolean hoverDiscard) {
		this.hoverDiscard = hoverDiscard;
	}
	public void setHoverSaveLevel(boolean hoverSave) {
		this.hoverSave = hoverSave;
	}
	
	
	
}
