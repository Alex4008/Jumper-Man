package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SettingsMenu {

	private Point settingsLogoLoc;
	private Point musicLoc;
	private Point soundLoc;
	private Point vSyncLoc;
	private Point menuReturnLoc;
	
	private BufferedImage settingsLogo;
	private BufferedImage musicOnLogo;
	private BufferedImage musicOffLogo;
	private BufferedImage soundOnLogo;
	private BufferedImage soundOffLogo;
	private BufferedImage vSyncOnLogo;
	private BufferedImage vSyncOffLogo;
	private BufferedImage menuReturnLogo;
	private BufferedImage settingsBlackLogo;
	private BufferedImage musicOnBlackLogo;
	private BufferedImage musicOffBlackLogo;
	private BufferedImage soundOnBlackLogo;
	private BufferedImage soundOffBlackLogo;
	private BufferedImage vSyncOnBlackLogo;
	private BufferedImage vSyncOffBlackLogo;
	private BufferedImage menuReturnBlackLogo;
	
	private Rectangle musicToggleBox;
	private Rectangle soundToggleBox;
	private Rectangle vSyncToggleBox;
	private Rectangle menuReturnBox;
	
	private boolean hoverMusicToggle;
	private boolean hoverSoundToggle;
	private boolean hoverVSyncToggle;
	private boolean hoverMenuReturn;
	
	//private InputStream settingsData;
	
	SettingsMenu(Game game) {
		//Load Images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			settingsLogo = loader.loadImage("/settingsMenuIcons/settingsLogo.png");
			musicOnLogo = loader.loadImage("/settingsMenuIcons/musicOnLogo.png");
			musicOffLogo = loader.loadImage("/settingsMenuIcons/musicOffLogo.png");
			soundOnLogo = loader.loadImage("/settingsMenuIcons/soundsOnLogo.png");
			soundOffLogo = loader.loadImage("/settingsMenuIcons/soundsOffLogo.png");
			vSyncOnLogo = loader.loadImage("/settingsMenuIcons/vSyncOnLogo.png");
			vSyncOffLogo = loader.loadImage("/settingsMenuIcons/vSyncOffLogo.png");
			menuReturnLogo = loader.loadImage("/settingsMenuIcons/menuReturnLogo.png");
			
			musicOnBlackLogo = loader.loadImage("/settingsMenuIcons/musicOnBlackLogo.png");
			musicOffBlackLogo = loader.loadImage("/settingsMenuIcons/musicOffBlackLogo.png");
			soundOnBlackLogo = loader.loadImage("/settingsMenuIcons/soundsOnBlackLogo.png");
			soundOffBlackLogo = loader.loadImage("/settingsMenuIcons/soundsOffBlackLogo.png");
			vSyncOnBlackLogo = loader.loadImage("/settingsMenuIcons/vSyncOnBlackLogo.png");
			vSyncOffBlackLogo = loader.loadImage("/settingsMenuIcons/vSyncOffBlackLogo.png");
			menuReturnBlackLogo = loader.loadImage("/settingsMenuIcons/menuReturnBlackLogo.png");
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		loader = null;
		
		//Calculate locations
		CalculateLocations(game);
		
		
	}

	public void CalculateLocations(Game game) {
		int yValue = 20;
		settingsLogoLoc = new Point((Game.WIDTH / 2) - (settingsLogo.getWidth() / 2), yValue);
		yValue = yValue + settingsLogo.getHeight() + 50;
		
		if(Game.musicToggle == true) {
			musicLoc = new Point((Game.WIDTH / 2) - (musicOnLogo.getWidth() / 2), yValue);
			yValue = yValue + musicOnLogo.getHeight() + 50;
			//Calculate bounding box
			musicToggleBox = new Rectangle(musicLoc.x, musicLoc.y, musicOnLogo.getWidth(), musicOnLogo.getHeight());
		}
		else {
			musicLoc = new Point((Game.WIDTH / 2) - (musicOffLogo.getWidth() / 2), yValue);
			yValue = yValue + musicOffLogo.getHeight() + 50;
			//Calculate bounding box
			musicToggleBox = new Rectangle(musicLoc.x, musicLoc.y, musicOffLogo.getWidth(), musicOffLogo.getHeight());
		}
		
		if(Game.soundsToggle == true) {
			soundLoc = new Point((Game.WIDTH / 2) - (soundOnLogo.getWidth() / 2), yValue);
			yValue = yValue + soundOnLogo.getHeight() + 50;
			//Calculate bounding box
			soundToggleBox = new Rectangle(soundLoc.x, soundLoc.y, soundOnLogo.getWidth(), soundOnLogo.getHeight());
		}
		else {
			soundLoc = new Point((Game.WIDTH / 2) - (soundOffLogo.getWidth() / 2), yValue);
			yValue = yValue + soundOffLogo.getHeight() + 50;
			//Calculate bounding box
			soundToggleBox = new Rectangle(soundLoc.x, soundLoc.y, soundOffLogo.getWidth(), soundOffLogo.getHeight());
		}
		
		if(game.vSyncToggle == true) {
			vSyncLoc = new Point((Game.WIDTH / 2) - (vSyncOnLogo.getWidth() / 2), yValue);
			yValue = yValue + vSyncOnLogo.getHeight() + 50;
			//Calculate bounding box
			vSyncToggleBox = new Rectangle(vSyncLoc.x, vSyncLoc.y, vSyncOffLogo.getWidth(), vSyncOffLogo.getHeight());
		}
		else {
			vSyncLoc = new Point((Game.WIDTH / 2) - (vSyncOffLogo.getWidth() / 2), yValue);
			yValue = yValue + vSyncOffLogo.getHeight() + 50;
			//Calculate bounding box
			vSyncToggleBox = new Rectangle(vSyncLoc.x, vSyncLoc.y, vSyncOffLogo.getWidth(), vSyncOffLogo.getHeight());
		}
		
		menuReturnLoc = new Point((Game.WIDTH / 2) - (menuReturnLogo.getWidth() / 2), yValue);
		
		//Calculate bounding box
		menuReturnBox = new Rectangle(menuReturnLoc.x, menuReturnLoc.y, menuReturnLogo.getWidth(), menuReturnLogo.getHeight());
	}
	
	//Getters and setters
	public Point getSettingsLogoLoc() { return settingsLogoLoc; }
	public Point getMusicLoc() { return musicLoc; }
	public Point getSoundLoc() { return soundLoc; }
	public Point getvSyncLoc() { return vSyncLoc; }
	public Point getMenuReturnLoc() { return menuReturnLoc; }
	public BufferedImage getSettingsLogo() { return settingsLogo; }
	public BufferedImage getMusicOnLogo() { return musicOnLogo; }
	public BufferedImage getMusicOffLogo() { return musicOffLogo; }
	public BufferedImage getSoundOnLogo() { return soundOnLogo; }
	public BufferedImage getSoundOffLogo() { return soundOffLogo; }
	public BufferedImage getvSyncOnLogo() { return vSyncOnLogo; }
	public BufferedImage getvSyncOffLogo() { return vSyncOffLogo; }
	public BufferedImage getMenuReturnLogo() { return menuReturnLogo; }
	public BufferedImage getSettingsBlackLogo() { return settingsBlackLogo; }
	public BufferedImage getMusicOnBlackLogo() { return musicOnBlackLogo; }
	public BufferedImage getMusicOffBlackLogo() { return musicOffBlackLogo; }
	public BufferedImage getSoundOnBlackLogo() { return soundOnBlackLogo; }
	public BufferedImage getSoundOffBlackLogo() { return soundOffBlackLogo; }
	public BufferedImage getvSyncOnBlackLogo() { return vSyncOnBlackLogo; }
	public BufferedImage getvSyncOffBlackLogo() { return vSyncOffBlackLogo; }
	public BufferedImage getMenuReturnBlackLogo() { return menuReturnBlackLogo; }
	public Rectangle getMusicToggleBox() { return musicToggleBox; }
	public Rectangle getSoundToggleBox() { return soundToggleBox; }
	public Rectangle getvSyncToggleBox() { return vSyncToggleBox; }
	public Rectangle getMenuReturnBox() { return menuReturnBox; }
	
	public boolean isHoverMusicToggle() { return hoverMusicToggle; }
	public boolean isHoverSoundToggle() { return hoverSoundToggle; }
	public boolean isHoverVSyncToggle() { return hoverVSyncToggle; }
	public boolean isHoverMenuReturn() { return hoverMenuReturn; }
	
	public void setHoverMusicToggle(boolean hoverMusicToggle) {
		this.hoverMusicToggle = hoverMusicToggle;
	}
	
	public void setHoverSoundToggle(boolean hoverSoundToggle) {
		this.hoverSoundToggle = hoverSoundToggle;
	}
	
	public void setHoverVSyncToggle(boolean hoverVSyncToggle) {
		this.hoverVSyncToggle = hoverVSyncToggle;
	}
	
	public void setHoverMenuReturn(boolean hoverMenuReturn) {
		this.hoverMenuReturn = hoverMenuReturn;
	}
	
}
