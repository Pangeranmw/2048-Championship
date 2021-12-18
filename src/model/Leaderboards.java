package model;

import helper.Koneksi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Leaderboards {

	private String name;
	
	private static Leaderboards lBoard;
	private ArrayList<Integer> topScores,topTiles;
	private ArrayList<String> topScoresName,topTilesName, topTimesName;
	private ArrayList<Long> topTimes;
	private int level, allScore;

	private Leaderboards(int level, int allScore, String name){
		this.allScore=allScore;
		this.name=name;
		this.level=level;
	}
	private Leaderboards(){
		topScores = new ArrayList<>();
		topTiles = new ArrayList<>();
		topTimes = new ArrayList<>();
		topScoresName = new ArrayList<>();
		topTilesName = new ArrayList<>();
		topTimesName = new ArrayList<>();
	}

	public void addLeaderboards(Board board) {
		long time=0;
		if(board.isWon()){
			time = board.getScores().getTime();
		}
		Koneksi.executeUpdate(String.format("insert into leaderboards (time, tiles, score, player_id, level) values" +
				"(%d, %d, %d, %d, %d)",
				time,
				board.getHighestTileValue(),
				board.getScores().getCurrentScore(),
				Player.getInstance().getId(),
				Level.getInstance().getLevel()));
	}

	public static ArrayList<Leaderboards> getAllTimeLeaderboards() {
		ArrayList<Leaderboards> allTimeLeaderboards = new ArrayList<>();
		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
				"leaderboards.time, " +
				"player.name " +
				"FROM leaderboards " +
				"JOIN player ON leaderboards.player_id=player.id WHERE leaderboards.time <> 0 ORDER BY leaderboards.time LIMIT 10");
		try {
			while (res.next()) {
				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
						res.getInt("time"),
						res.getString("name"));
				allTimeLeaderboards.add(leaderboard);
			}
			return allTimeLeaderboards;
		} catch (SQLException e) {
			return null;
		}
	}
	public static ArrayList<Leaderboards> getAllTilesLeaderboards() {
		ArrayList<Leaderboards> allTilesLeaderboards = new ArrayList<>();
		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
				"leaderboards.tiles, " +
				"player.name " +
				"FROM leaderboards " +
				"JOIN player ON leaderboards.player_id=player.id ORDER BY leaderboards.tiles DESC LIMIT 10");
		try {
			while (res.next()) {
				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
						res.getInt("tiles"),
						res.getString("name"));
				allTilesLeaderboards.add(leaderboard);
			}
			return allTilesLeaderboards;
		} catch (SQLException e) {
			return null;
		}
	}
	public static ArrayList<Leaderboards> getAllScoreLeaderboards() {
		ArrayList<Leaderboards> allScoreLeaderboards = new ArrayList<>();
		ResultSet res = Koneksi.executeQuery("SELECT leaderboards.level, " +
				"leaderboards.score, " +
				"player.name " +
				"FROM leaderboards " +
				"JOIN player ON leaderboards.player_id=player.id ORDER BY leaderboards.score DESC LIMIT 10");
		try {
			while (res.next()) {
				Leaderboards leaderboard = new Leaderboards(res.getInt("level"),
						res.getInt("score"),
						res.getString("name"));
				allScoreLeaderboards.add(leaderboard);
			}
			return allScoreLeaderboards;
		} catch (SQLException e) {
			return null;
		}
	}
	public static Leaderboards getInstance(){
		if(lBoard == null){
			lBoard = new Leaderboards();
		}
		return lBoard;
	}

	public void loadScores() {
		try {
			ArrayList<Leaderboards> scoreLeaderboard = getAllScoreLeaderboards();
			ArrayList<Leaderboards> tilesLeaderboard = getAllTilesLeaderboards();
			ArrayList<Leaderboards> timeLeaderboard = getAllTimeLeaderboards();

			topScores.clear();
			topTiles.clear();
			topTimes.clear();
			topTilesName.clear();
			topScoresName.clear();
			topTimesName.clear();

			for(Leaderboards lb:scoreLeaderboard){
				topScores.add(lb.getAllScore());
				topScoresName.add(String.format("%d [%s] [%s]", lb.getAllScore(),lb.getName(),Utils.setDifficutly(lb.getLevel())));
			}
			for(Leaderboards lb:tilesLeaderboard){
				topTiles.add(lb.getAllScore());
				topTilesName.add(String.format("%d [%s] [%s]", lb.getAllScore(),lb.getName(),Utils.setDifficutly(lb.getLevel())));
			}
			for(Leaderboards lb:timeLeaderboard){
				topTimesName.add(String.format(" [%s] [%s]", lb.getName(), Utils.setDifficutly(lb.getLevel())));
				topTimes.add((long) lb.getAllScore());
			}
		} catch (Exception e) {
		}
	}
	public void addScore(int score){
		for(int i = 0; i < topScores.size(); i++){
			if(score >= topScores.get(i)){
				topScores.add(i, score);
				topScores.remove(topScores.size() - 1);
				break;
			}
		}
	}

	public void addTile(int tileValue){
		for(int i = 0; i < topTiles.size(); i++){
			if(tileValue >= topTiles.get(i)){
				topTiles.add(i, tileValue);
				topTiles.remove(topTiles.size() - 1);
				break;
			}
		}
	}

	public void addTime(long millis){
		for(int i = 0; i < topTimes.size(); i++){
                        if(topTimes.get(i)>=0 && millis <= topTimes.get(i)){
                            topTimes.add(i, millis);
                            topTimes.remove(topTimes.size() - 1);
                            break;
			}
		}
	}
	public ArrayList<String> getTopScores() {
		return topScoresName;
	}

	public ArrayList<String> getTopTiles() {
		return topTilesName;
	}

	public ArrayList<String> getTopTimesName() {return topTimesName; }

	public ArrayList<Long> getTopTimes() {return topTimes; }
	public int getHighScore(){
            ArrayList<Leaderboards> scoreLeaderboard = getAllScoreLeaderboards();
            if(!scoreLeaderboard.isEmpty())
                return scoreLeaderboard.get(0).getAllScore();
            else
                return 0;
	}
	
	public long getFastestTime(){
            ArrayList<Leaderboards> timeLeaderboard = getAllTimeLeaderboards();
            if(!timeLeaderboard.isEmpty())
                return timeLeaderboard.get(0).getAllScore();
            else
                return 0;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public int getAllScore() {
		return allScore;
	}

}
