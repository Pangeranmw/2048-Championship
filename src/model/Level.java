package model;

public class Level {
	
	private static Level l;
	private int level=1;
	
	public static Level getInstance(){
		if(l == null){
			l = new Level();
		}
		return l;
	}
	
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level=level;
	}
}
