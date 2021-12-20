package view;

import model.Game;
import model.Utils;

import java.awt.*;
import java.io.File;

public class MainMenuPanel extends GuiPanel {

	private Image logo;

	public MainMenuPanel() {
		super();
                String f = new File("").getAbsolutePath();
		logo = Utils.scaleImage(logo, f+"/src/Logo2048.png", 3, 3);
		int buttonWidth = 180;
		GuiButton playButton = new GuiButton(50, 330, buttonWidth,  60);
		playButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Play"));
		playButton.setText("Play");
		addButton(playButton);
		
		GuiButton scoresButton = new GuiButton(Game.WIDTH / 2 + 25, 330, buttonWidth, 60);
		scoresButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Leaderboards"));
		scoresButton.setText("Leaderboards");
		addButton(scoresButton);
		
		GuiButton levelButton = new GuiButton(50, 420, buttonWidth, 60);
		levelButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Level"));
		levelButton.setText("Set Level");
		addButton(levelButton);
		
		GuiButton playerButton = new GuiButton(Game.WIDTH / 2 + 25, 420, buttonWidth, 60);
		playerButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Login"));
		playerButton.setText("Login");
		addButton(playerButton);
	}

	public void render(Graphics2D g){
		super.render(g);
		int logoWidth = logo.getWidth(null);
		g.drawImage(logo, Game.WIDTH / 2 - logoWidth / 2, 60, null);
	}
}
