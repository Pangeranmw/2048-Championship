package view;

import model.Game;
import model.Level;
import model.Utils;

import java.awt.*;

import static model.ColorPallette.*;
import static model.FontSet.*;

public class LevelPanel extends GuiPanel{

	private String currentState = "easy";
	private final GuiButton hardButton;
	private final GuiButton mediumButton;
	private final GuiButton easyButton;
	
	public LevelPanel(){
		super();

		int buttonHeight = 50;
		int buttonY = 260;
		int buttonWidth = 100;
		mediumButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, buttonY, buttonWidth, buttonHeight);
		mediumButton.addActionListener(e -> currentState = "medium");
		mediumButton.setText("Medium");
		addButton(mediumButton);

		int buttonSpacing = 20;
		easyButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2 - mediumButton.getWidth() - buttonSpacing, buttonY, buttonWidth, buttonHeight);
		easyButton.addActionListener(e -> currentState = "easy");
		easyButton.setText("Easy");
		addButton(easyButton);
		
		hardButton = new GuiButton(Game.WIDTH / 2 + buttonWidth / 2 + buttonSpacing, buttonY, buttonWidth, buttonHeight);
		hardButton.addActionListener(e -> currentState = "hard");
		hardButton.setText("Hard");
		addButton(hardButton);

		int backButtonWidth = 220;
		GuiButton backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
		backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
		backButton.setText("Set Level");
		addButton(backButton);
	}
	
	private void drawLevel(Graphics2D g){
            switch (currentState) {
                case "medium":
                    mediumButton.setActivated(g);
                    Level.getInstance().setLevel(2);
                    break;
                case "easy":
                    easyButton.setActivated(g);
                    Level.getInstance().setLevel(1);
                    break;
                default:
                    hardButton.setActivated(g);
                    Level.getInstance().setLevel(3);
                    break;
            }
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(darkerGreen);
		String title = "Choose Level";
		g.drawString(title, Game.WIDTH / 2 - Utils.getWidth(title, leaderboardTitle, g) / 2, Game.HEIGHT/2-Utils.getHeight(title, leaderboardTitle, g)-30);
		drawLevel(g);
	}
}
