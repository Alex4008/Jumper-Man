package alex;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TypeSelectMenu {
	
	private Point worldTypeIconLoc;
	private Point overWorldIconLoc;
	private Point underGroundIconLoc;
	private Point castleIconLoc;
	
	private BufferedImage worldTypeIcon;
	private BufferedImage overWorldIcon;
	private BufferedImage underGroundIcon;
	private BufferedImage castleIcon;
	private BufferedImage overWorldBlackIcon;
	private BufferedImage underGroundBlackIcon;
	private BufferedImage castleBlackIcon;
	
	private Rectangle overWorldBox;
	private Rectangle underGroundBox;
	private Rectangle castleBox;
	
	private boolean hoverOverWorld;
	private boolean hoverUnderGround;
	private boolean hoverCastle;
	
	
	TypeSelectMenu() {
		//Load images
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			worldTypeIcon = loader.loadImage("/typeSelectMenuIcons/worldTypeLogo.png");
			overWorldIcon = loader.loadImage("/typeSelectMenuIcons/overWorldLogo.png");
			underGroundIcon = loader.loadImage("/typeSelectMenuIcons/underGroundLogo.png");
			castleIcon = loader.loadImage("/typeSelectMenuIcons/castleLogo.png");
			
			overWorldBlackIcon = loader.loadImage("/typeSelectMenuIcons/overWorld-black.png");
			underGroundBlackIcon = loader.loadImage("/typeSelectMenuIcons/underGround-black.png");
			castleBlackIcon = loader.loadImage("/typeSelectMenuIcons/castle-black.png");
		}
		catch(IOException e) {
			e.printStackTrace(); 
		}
		loader = null;
		
		//Calculate Locations
		int yValue = 20;
		worldTypeIconLoc = new Point((Game.WIDTH / 2) - (worldTypeIcon.getWidth() / 2), yValue);
		yValue = yValue + worldTypeIcon.getHeight() + 50;
		overWorldIconLoc = new Point((Game.WIDTH / 2) - (overWorldIcon.getWidth() / 2), yValue);
		yValue = yValue + overWorldIcon.getHeight() + 50;
		underGroundIconLoc = new Point((Game.WIDTH / 2) - (underGroundIcon.getWidth() / 2), yValue);
		yValue = yValue + underGroundIcon.getHeight() + 50;
		castleIconLoc = new Point((Game.WIDTH / 2) - (castleIcon.getWidth() / 2), yValue);
		
		//Calculate bounding boxes
		overWorldBox = new Rectangle(overWorldIconLoc.x, overWorldIconLoc.y, overWorldIcon.getWidth(), overWorldIcon.getHeight());
		underGroundBox = new Rectangle(underGroundIconLoc.x, underGroundIconLoc.y, underGroundIcon.getWidth(), underGroundIcon.getHeight());
		castleBox = new Rectangle(castleIconLoc.x, castleIconLoc.y, castleIcon.getWidth(), castleIcon.getHeight());
		
	}


	public Point getWorldTypeIconLoc() { return worldTypeIconLoc; }
	public Point getOverWorldIconLoc() { return overWorldIconLoc; }
	public Point getUnderGroundIconLoc() { return underGroundIconLoc; }
	public Point getCastleIconLoc() { return castleIconLoc; }
	public BufferedImage getWorldTypeIcon() { return worldTypeIcon; }
	public BufferedImage getOverWorldIcon() { return overWorldIcon; }
	public BufferedImage getUnderGroundIcon() { return underGroundIcon; }
	public BufferedImage getCastleIcon() { return castleIcon; }
	public BufferedImage getOverWorldBlackIcon() { return overWorldBlackIcon; }
	public BufferedImage getUnderGroundBlackIcon() { return underGroundBlackIcon; }
	public BufferedImage getCastleBlackIcon() { return castleBlackIcon; }
	public Rectangle getOverWorldBox() { return overWorldBox; }
	public Rectangle getUnderGroundBox() { return underGroundBox; }
	public Rectangle getCastleBox() { return castleBox; }
	public boolean isHoverOverWorld() { return hoverOverWorld; }
	public boolean isHoverUnderGround() { return hoverUnderGround; }
	public boolean isHoverCastle() { return hoverCastle; }

	public void setHoverOverWorld(boolean hoverOverWorld) {
		this.hoverOverWorld = hoverOverWorld;
	}
	public void setHoverUnderGround(boolean hoverUnderGround) {
		this.hoverUnderGround = hoverUnderGround;
	}
	public void setHoverCastle(boolean hoverCastle) {
		this.hoverCastle = hoverCastle;
	}
	
	
	
}
