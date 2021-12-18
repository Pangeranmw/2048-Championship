package model;

import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import static model.ColorPallette.white;

public class Game extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = Board.getInstance().getWIDTH()+40,
			HEIGHT = 580;

	private boolean running;
	private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private final GuiScreen screen;
        
	public Game() {
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		screen = GuiScreen.getInstance();
		screen.add("Menu", new MainMenuPanel());
		screen.add("Play", new PlayPanel());
		screen.add("Leaderboards", new LeaderboardsPanel());
		screen.add("Level", new LevelPanel());
		screen.add("Login", new LoginPanel());
		screen.setCurrentPanel("Menu");
	}

	private void update() {
		screen.update();
		Keys.update();
	}

	private void render() {
		Graphics2D g = (Graphics2D) image.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		screen.render(g);

		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.drawImage(image, 0, 0, null);
	}
        
	public void start() {
		running = true;
		Thread game = new Thread(this);
		game.start();
	}
        
        @Override
	public void run() {
		while(running) {
			update();
                        try {
                                Thread.sleep(10);
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
			render();
                        
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		Keys.keyTyped(e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Keys.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		Keys.keyReleased(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		screen.mouseClicked(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		screen.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		screen.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		screen.mouseDragged(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		screen.mouseMoved(e);
	}
}
