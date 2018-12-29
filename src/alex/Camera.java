package alex;

public class Camera {

	//private final int OFFSET_MAX_X = World.WORLD_SIZE_X - World.SCREEN_SIZE_X;
	private final int OFFSET_MAX_Y = World.WORLD_SIZE_Y - World.SCREEN_SIZE_Y;
	private final int OFFSET_MIN_X = 0;
	private final int OFFSET_MIN_Y = 0;
	
	private int camX;
	private int camY;
	
	Camera(int camX, int camY) {
		this.camX = camX;
		this.camY = camY;
	}
	
	public int GetX() { return camX; }
	public int GetY() { return camY; }
	
	public void UpdateCamera(Player thePlayer) {
		camX = thePlayer.getX() - World.SCREEN_SIZE_X / 2;
		camY = thePlayer.getY() - World.SCREEN_SIZE_Y / 2;
		
		int offsetMaxX = World.GetWorldSizeX() - World.SCREEN_SIZE_X;
		
		if(camX > offsetMaxX) camX = offsetMaxX;
		else if(camX < OFFSET_MIN_X) camX = OFFSET_MIN_X;
		
		if(camY > OFFSET_MAX_Y) camY = OFFSET_MAX_Y;
		else if(camY < OFFSET_MIN_Y) camY = OFFSET_MIN_Y;
	}
	
	//This is for the level editor. DO NOT use in game!
	public void UpdateCamera(int locX, int locY) {
		camX = locX;
		camY = locY;
		
		int offsetMaxX = World.GetWorldSizeX() - World.SCREEN_SIZE_X;
		
		if(camX > offsetMaxX) camX = offsetMaxX;
		else if(camX < OFFSET_MIN_X) camX = OFFSET_MIN_X;
		
		if(camY > OFFSET_MAX_Y) camY = OFFSET_MAX_Y;
		else if(camY < OFFSET_MIN_Y) camY = OFFSET_MIN_Y;
		
		Game.levelEditorLocX = camX;
		Game.levelEditorLocY = camY;
	}
	
}
