package game;

import main.*;

import java.awt.Graphics2D;
import java.awt.Color;

import java.util.ArrayList;

public class Game
{
    // instance variables - replace the example below with your own
    MouseHandler mouseH;
    public enum color {RED, GREEN, BLUE, YELLOW};
    private ArrayList<UnoCard> computerHand = new ArrayList<UnoCard>();
    private ArrayList<UnoCard> playerHand = new ArrayList<UnoCard>();

    public Game(MouseHandler mouseHandler) {
        this.mouseH = mouseHandler;
        for (int i = 0; i < 7; i++) {
            computerHand.add(UnoCard.createCard());
            playerHand.add(UnoCard.createCard());
        }
        
    }
    
    public void addCard(int ID) {
        assert ID == 0 || ID == 1 : "list variable is null or empty";
        if (ID == 0)
            computerHand.add(UnoCard.createCard());
        else
            playerHand.add(UnoCard.createCard());
    }

    public void update() {
        
    }
    
    public void draw(Graphics2D g2) {
        //Background
        g2.setColor(new Color(25, 25, 25));
        g2.fillRect(0, 0, 1600, 900);
        
        for (int i = 0; i < computerHand.size(); i++) {
            UnoCard card = computerHand.get(i);
            g2.setColor(card.getCardColorAsColor());
            g2.fillRect(25+125*i, 25, 100, 150);
        }
        
        for (int i = 0; i < playerHand.size(); i++) {
            UnoCard card = playerHand.get(i);
            g2.setColor(card.getCardColorAsColor());
            g2.fillRect(25+125*i, 725, 100, 150);
        }
            
    }
}
