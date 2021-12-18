package view;

import model.Game;
import model.Player;
import model.Utils;

import java.awt.*;

import static model.ColorPallette.*;
import static model.FontSet.*;

public class LoginPanel extends GuiPanel{

	private final int textFieldHeight = 50;
	private boolean cekPlayer, addSuccess, loginPressed, signUpPressed;
	private final GuiTextField tfName;

	public LoginPanel(){
		super();
		int buttonHeight = 50;
		int buttonWidth = 100;
		GuiButton signUpButton = new GuiButton(Game.WIDTH / 2 - buttonWidth / 2, Game.HEIGHT - 250, buttonWidth, buttonHeight);
		int buttonSpacing = 20;
		GuiButton loginButton = new GuiButton(signUpButton.getX(), signUpButton.getY() - buttonSpacing - buttonHeight, buttonWidth, buttonHeight);
		int textFieldWidth = Game.WIDTH / 2 + 100;
		tfName = new GuiTextField(Game.WIDTH / 2 - textFieldWidth / 2, loginButton.getY()-2* buttonSpacing - buttonHeight, textFieldWidth, textFieldHeight);

		tfName.setPlaceholderText("Name");
		loginButton.setText("Login");
		signUpButton.setText("Sign Up");

		addButton(loginButton);
		addButton(signUpButton);
		addTextField(tfName);

		GuiButton backButton = new GuiButton(Game.WIDTH / 2 - 400 / 2, 520, 400, 40);
		backButton.addActionListener(e -> GuiScreen.getInstance().setCurrentPanel("Menu"));
		backButton.setText("Back to Main Menu");
		addButton(backButton);
		loginButton.addActionListener(e -> {
			if(tfName.getText()!=null){
				loginPressed=true;
				signUpPressed=false;
				if(Player.getInstance().getPlayerByName(tfName.getText()) != null){
					cekPlayer=true;
					Player.getInstance().getPlayerByName(tfName.getText());
				} else{
					cekPlayer=false;
				}
			}
		});
		signUpButton.addActionListener(e -> {
			if(tfName.getText()!=null){
				signUpPressed=true;
				loginPressed=false;
				if(Player.getInstance().getPlayerByName(tfName.getText()) == null){
					Player.getInstance().addPlayer(tfName.getText());
					addSuccess=true;
					Player.getInstance().getPlayerByName(tfName.getText());
				} else{
					addSuccess=false;
				}
			}
		});
	}

	private void drawSignUpCheck(Graphics2D g){
		String cekText;
		if(!addSuccess){
			Utils.setFont(g,warningFont,t1024);
			cekText="Player Sudah Terdaftar";
		} else{
			Utils.setFont(g,warningFont,lightGreen);
			cekText="Berhasil Mendaftar dan Login sebagai "+ Player.getInstance().getName();
		}
		g.drawString(cekText, tfName.getX(), tfName.getY()+textFieldHeight+Utils.getHeight(cekText,warningFont,g)+5);
	}

	private void drawLoginCheck(Graphics2D g){
		String cekText;
		if(!cekPlayer){
			Utils.setFont(g,warningFont,t1024);
			cekText="Gagal Login, Tekan Tombol SignUp";
		} else{
			Utils.setFont(g,warningFont,lightGreen);
			cekText="Berhasil Login sebagai " + Player.getInstance().getName();
		}
		g.drawString(cekText, tfName.getX(), tfName.getY()+textFieldHeight+Utils.getHeight(cekText,warningFont,g)+5);
	}

	@Override
	public void render(Graphics2D g){
		super.render(g);
		g.setColor(darkerGreen);
		String title = "Login Player";
		g.drawString(title, Game.WIDTH / 2 - Utils.getWidth(title, leaderboardTitle, g) / 2, Game.HEIGHT/2-Utils.getHeight(title, leaderboardTitle, g)-120);
		if(loginPressed){
			drawLoginCheck(g);
		}if (signUpPressed){
			drawSignUpCheck(g);
		}
	}
}
