package model;

import view.GuiTextField;

import java.awt.event.KeyEvent;

public class Keys {

	public static final boolean[] pressed = new boolean[222];
	public static final boolean[] lastPressed = new boolean[222];
	public static String type="";

	public static void update(){
            lastPressed[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
            lastPressed[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
            lastPressed[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
            lastPressed[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
	}

	public static void keyPressed(KeyEvent e){
		pressed[e.getKeyCode()] = true;
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && GuiTextField.clicked){
			if(type.length()>=1){
				type = type.substring(0, type.length() - 1);
			}
		}
	}

	public static void keyReleased(KeyEvent e){
		pressed[e.getKeyCode()] = false;
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && GuiTextField.clicked){
			if(type.length()>=1){
				type = type.substring(0, type.length() - 1);
			}
		}
	}

	public static String keyTyped(KeyEvent e){
		if(GuiTextField.clicked){
			type+=String.valueOf(e.getKeyChar());
		}
		return type;
	}
	
}
