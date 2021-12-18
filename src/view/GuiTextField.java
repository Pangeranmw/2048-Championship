package view;

import model.Keys;
import model.Utils;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import static model.ColorPallette.*;
import static model.FontSet.buttonFont;

public class GuiTextField{

	private String currentState="notClicked";
	private String text = "";
	private String placeholderText;
	private final RoundRectangle2D textBox;
	public static boolean clicked = false;

	public GuiTextField(int x, int y, int width, int height){
		textBox = new RoundRectangle2D.Double(x, y, width, height, 30,30);
	}

	public void render(Graphics2D g){
		String type = Keys.type;
		if(clicked && type != null){
			setText(type);
		}
		int height = getHeight(),width = getWidth(),x = getX(),y = getY();
		if(currentState.equals("clicked")) Utils.setRectangle(g, t128, x,y,width,height, t64);
		else Utils.setRectangle(g, tFColor, textBox);
		if (getText().length()==0) {
			Utils.setFont(g, buttonFont, tFPlaceholder);
			g.drawString(placeholderText, getX()+20, Utils.centerY(getHeight(), placeholderText, g, buttonFont)+getY());
		}else{
				Utils.setFont(g, buttonFont, tFFont);
				g.drawString(text, getX()+20, Utils.centerY(getHeight(), text, g, buttonFont)+getY());
		}
	}

	public void mouseClicked(MouseEvent e){
		if(textBox.contains(e.getPoint())){
			clicked=true;
			currentState = "clicked";
		} else{
			clicked=false;
			currentState = "notClicked";
		}
	}

	public int getX(){
		return (int) textBox.getX();
	}
	public int getY(){
		return (int) textBox.getY();
	}

	public int getWidth(){
		return (int) textBox.getWidth();
	}

	public int getHeight(){
		return (int) textBox.getHeight();
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	public void setPlaceholderText(String placeholderText) {
		this.placeholderText = placeholderText;
	}

}
