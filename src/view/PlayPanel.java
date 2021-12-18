package view;

import model.*;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import static model.ColorPallette.*;
import static model.FontSet.*;

public class PlayPanel extends GuiPanel {

	private final Board board;
	private final BufferedImage info;
	private final ScoreManager scores;
	private String timeF;

	private final GuiButton tryAgain;
	private final GuiButton mainMenu;
	private boolean added;

	public PlayPanel() {
		System.out.println();
		board = new Board(Game.WIDTH / 2 - Board.getInstance().getWIDTH() / 2, Game.HEIGHT - Board.getInstance().getHEIGHT() - 20);
		scores = board.getScores();
		info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

		int buttonHeight = 50;
		int largeButtonWidth = 160;
		mainMenu = new GuiButton(Game.WIDTH / 2 - largeButtonWidth / 2, Game.HEIGHT-150, largeButtonWidth, buttonHeight);
		int spacing = 20;
		tryAgain = new GuiButton(mainMenu.getX(), mainMenu.getY() - spacing - buttonHeight, largeButtonWidth, buttonHeight);

		tryAgain.setText("Try Again");
		mainMenu.setText("Main Menu");

		tryAgain.addActionListener(e -> {
			board.getScores().reset();
			board.reset();

			removeButton(tryAgain);
			removeButton(mainMenu);

			added = false;
		});

		mainMenu.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
	}

	private void drawGui(Graphics2D g) {
		timeF = Utils.formatTime(scores.getTime());
		String bestTimeF = Utils.formatTime(scores.getBestTime());

		Graphics2D g2d = (Graphics2D) info.getGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(white);
		g2d.fillRect(0, 0, info.getWidth(), info.getHeight());

		Utils.setFont(g2d,infoFont,lightGreen);
		g2d.drawString("Point: " + scores.getCurrentScore(), 30, 40);
		g2d.drawString("Time: " + timeF, Game.WIDTH - Utils.getWidth("Time: " + timeF, infoFont, g2d) - 20, 40);

		Utils.setFont(g2d,infoFont,t2048);
		g2d.drawString("Best: " + scores.getCurrentTopScore(),  30, 90);
		g2d.drawString("Best: " + bestTimeF, Game.WIDTH - Utils.getWidth("Best: " + bestTimeF, infoFont, g2d) - 20, 90);
		
		int x = (Level.getInstance().getLevel()==2) ? 
				(Game.WIDTH - Utils.getWidth(Utils.setDifficutly(Level.getInstance().getLevel()), infoFont, g2d))/2-12 :
					(Game.WIDTH - Utils.getWidth(Utils.setDifficutly(Level.getInstance().getLevel()), infoFont, g2d))/2-27;
		
		RoundRectangle2D box = new RoundRectangle2D.Double(x,37,100,30,20,20);
		g2d.setColor(lightGreen);
		g2d.fill(box);
		g2d.setColor(darkGreen);
		g2d.drawString(Utils.setDifficutly(Level.getInstance().getLevel()), (Game.WIDTH - Utils.getWidth(Utils.setDifficutly(Level.getInstance().getLevel()), infoFont, g2d))/2, 60);
		g.drawImage(info, 0, 0, null);
	}
	public void drawGameOver(Graphics2D g) {
		String text;
		g.setColor(transparentBG);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g.setColor(red);
		text = "{ "+Player.getInstance().getName()+" }";
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, endGameName), 90);

		g.setColor(red);
		text = "You Lose!";
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, endGameTitle), 150);

		g.setColor(darkerGreen);
		g.setFont(infoFont);
		text = "Your Point: " + scores.getCurrentScore();
		g.drawString(text,Utils.centerX(Game.WIDTH, text, g, infoFont), Utils.centerY(Game.HEIGHT, text, g, infoFont)-50);

		text = "Time: " + timeF;
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, infoFont), Utils.centerY(Game.HEIGHT, text, g, infoFont)+20);
	}

	public void drawWin(Graphics2D g) {
		String text;
		g.setColor(transparentBG);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

		g.setColor(lightGreen);
		text = "{ "+Player.getInstance().getName()+" }";
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, endGameName), 90);
		text = "You Win!";
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, endGameTitle), 150);

		Utils.setFont(g,infoFont,darkerGreen);
		text = "Your Point: " + scores.getCurrentScore();
		g.drawString(text,Utils.centerX(Game.WIDTH, text, g, infoFont), Utils.centerY(Game.HEIGHT, text, g, infoFont)-50);
		text = "Time: " + timeF;
		g.drawString(text, Utils.centerX(Game.WIDTH, text, g, infoFont), Utils.centerY(Game.HEIGHT, text, g, infoFont)+20);
	}

	@Override
	public void update() {
		board.update();
	}
	@Override
	public void render(Graphics2D g) {
		drawGui(g);
		board.render(g);
		if (board.isDead() && !board.isWon()) {
			tryAgain.setText("Try Again");
			if (!added) {
				added = true;
				addButton(mainMenu);
				addButton(tryAgain);
			}
			drawGameOver(g);
		} else if(board.isDead() && board.isWon()){
			tryAgain.setText("Play Again");
			if (!added) {
				added = true;
				addButton(mainMenu);
				addButton(tryAgain);
			}
			drawWin(g);
		}
		super.render(g);
	}
}