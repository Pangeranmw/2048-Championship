package view;

import model.Game;
import model.Leaderboards;
import model.Utils;

import java.awt.*;
import java.util.ArrayList;

import static model.ColorPallette.*;
import static model.FontSet.*;

public class LeaderboardsPanel extends GuiPanel{

	private final int buttonY = 120;
	private final int buttonHeight = 50;
	private String currentState = "score";
	private final GuiButton timeButton;
	private final GuiButton scoreButton;
	private final GuiButton tileButton;
	
	public LeaderboardsPanel(){
		super();
		Leaderboards.getInstance().loadScores();

		int buttonWidth = 100;
		tileButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, buttonY, buttonWidth, buttonHeight);
		tileButton.addActionListener(e -> currentState = "tile");
		tileButton.setText("Tiles");
		addButton(tileButton);

		int buttonSpacing = 20;
		scoreButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2 - tileButton.getWidth() - buttonSpacing, buttonY, buttonWidth, buttonHeight);
		scoreButton.addActionListener(e -> currentState = "score");
		scoreButton.setText("Scores");
		addButton(scoreButton);
		
		timeButton = new GuiButton(Game.WIDTH / 2 + buttonWidth / 2 + buttonSpacing, buttonY, buttonWidth, buttonHeight);
		timeButton.addActionListener(e -> currentState = "time");
		timeButton.setText("Times");
		addButton(timeButton);

		int backButtonWidth = 220;
		GuiButton backButton = new GuiButton(Game.WIDTH / 2 - backButtonWidth / 2, 500, backButtonWidth, 60);
		backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
		backButton.setText("Back");
		addButton(backButton);
	}
	
	private void drawLeaderboards(Graphics2D g){
		ArrayList<String> strings = new ArrayList<>();
            switch (currentState) {
                case "score":
                    scoreButton.setActivated(g);
                    strings = Leaderboards.getInstance().getTopScores();
                    break;
                case "tile":
                    tileButton.setActivated(g);
                    strings = Leaderboards.getInstance().getTopTiles();
                    break;
                default:
                    timeButton.setActivated(g);
                    if(!Leaderboards.getInstance().getTopTimes().isEmpty()){
                            for (int i=0; i<Leaderboards.getInstance().getTopTimes().size(); i++){
                                    strings.add(Utils.formatTime(Leaderboards.getInstance().getTopTimes().get(i)) + Leaderboards.getInstance().getTopTimesName().get(i));			
                                    }
                            }
                    break;
            }

		Utils.setFont(g,scoreFont,darkerGreen);
		String leaderboard;
		int leaderboardsY = buttonY + buttonHeight + 35;
		int leaderboardsX = 80;
		if(!strings.isEmpty()) {
                    for (int i = 0; i < strings.size(); i++) {
                            leaderboard = String.format("%d. %s", i + 1, strings.get(i));
                            g.drawString(leaderboard, leaderboardsX, leaderboardsY + i * 30);
                    }
		} else{
			leaderboard = "Belum Ada Highscore";
			g.drawString(leaderboard, leaderboardsX, leaderboardsY);
		}
	}

	@Override
	public void render(Graphics2D g){
		Leaderboards.getInstance().loadScores();
		super.render(g);
		g.setColor(darkerGreen);
		String title = "Leaderboards";
		g.drawString(title, Utils.centerX(Game.WIDTH, title, g, leaderboardTitle), Utils.getHeight(title, leaderboardTitle, g) + 40);
		drawLeaderboards(g);
	}
}
