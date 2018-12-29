package alex;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class MouseMove extends JPanel implements MouseMotionListener{

	private static final long serialVersionUID = 1L;
	Game game;
	
	public MouseMove(Game game) {
		this.game = game;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		game.mouseLoc.setLocation((int) e.getX(), (int) e.getY());
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		game.mouseLoc.setLocation((int) e.getX(), (int) e.getY());
		
	}

}
