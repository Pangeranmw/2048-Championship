package view;

import model.Utils;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

import static model.ColorPallette.*;
import static model.FontSet.*;

public class GuiButton {

	private String currentState = "released",
			text = "";
	private final RoundRectangle2D clickBox;
	private final ArrayList<ActionListener> actionListeners= new ArrayList<>();

	public GuiButton(int x, int y, int width, int height){
		clickBox = new RoundRectangle2D.Double(x, y, width, height, 30,30);
	}

	public void render(Graphics2D g){
            switch (currentState) {
                case "released":
                    Utils.setRectangle(g,t64, clickBox);
                    break;
                case "pressed":
                    Utils.setRectangle(g, red, clickBox);
                    break;
                default:
                    Utils.setRectangle(g, t32, clickBox);
                    break;
            }
		Utils.setFont(g, buttonFont, tFont8);
		g.drawString(text, Utils.centerX(getWidth(), text, g, buttonFont)+getX(), Utils.centerY(getHeight(), text, g, buttonFont)+getY());
	}
	
	public void addActionListener(ActionListener listener){
		actionListeners.add(listener);
	}
	
	public void mousePressed(MouseEvent e) {
		if(clickBox.contains(e.getPoint()))
			currentState = "pressed";
	}

	public void mouseReleased(MouseEvent e) {
		if(clickBox.contains(e.getPoint()))
			for(int i = 0; i < actionListeners.size(); i++){
				actionListeners.get(i).actionPerformed(null);
			}
		currentState = "released";
	}

	public void mouseDragged(MouseEvent e) {
		currentState = clickBox.contains(e.getPoint()) ? "pressed" : "released";
	}

	public void mouseMoved(MouseEvent e) {
		currentState = clickBox.contains(e.getPoint()) ? "hover" : "released";
	}

	public int getX(){
		return (int) clickBox.getX();
	}

	public int getY(){
		return (int) clickBox.getY();
	}
	
	public int getWidth(){
		return (int) clickBox.getWidth();
	}
	
	public int getHeight(){
		return (int) clickBox.getHeight();
	}
	
	public void setText(String text){
		this.text = text;
	}
	public String getText(){
		return text;
	}
	public void setActivated(Graphics2D g){
		Utils.setRectangle(g, white, clickBox);
		Utils.setRectangle(g, t2048, clickBox);
		Utils.setFont(g, buttonFont, darkerGreen);
		g.drawString(text, Utils.centerX(getWidth(), text, g, buttonFont)+getX(), Utils.centerY(getHeight(), text, g, buttonFont)+getY());
	}

}
