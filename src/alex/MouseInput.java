package alex;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener{

	Game game;
	
	public MouseInput(Game game) {
		this.game = game;
	}
	
	public void mouseClicked(MouseEvent e) {
		game.clickType = e.getButton();
		game.setClicked(true);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Game.mouseHeld = false;
		game.setMouseOnScreen(true);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		game.setMouseOnScreen(false);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		game.clickType = e.getButton();
		Game.mouseHeld = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Game.mouseHeld = false;
		game.setClicked(true);
		
	}
	
}
