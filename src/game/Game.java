package game;

import main.*;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.util.ArrayList;

public class Game
{
    // instance variables - replace the example below with your own
    MouseHandler mouseH;
    public enum color {RED, GREEN, BLUE, YELLOW};
    private ArrayList<UnoCard> computerHand = new ArrayList<UnoCard>();
    private ArrayList<UnoCard> playerHand = new ArrayList<UnoCard>();
    private int actionCooldown = 0;
    private UnoCard topCard = UnoCard.createCard();
    private boolean isPlaying = true, isPlayerTurn = true, playerHasDrawn, playerWon, computerWon;

    public Game(MouseHandler mouseHandler) {
        this.mouseH = mouseHandler;

        reset();        
    }

    public void reset() {
        computerHand = new ArrayList<UnoCard>();
        playerHand = new ArrayList<UnoCard>();
        topCard = UnoCard.createCard();
        isPlaying = true;
        isPlayerTurn = true;
        playerHasDrawn = false;
        playerWon = false; 
        computerWon = false;
        for (int i = 0; i < 7; i++) {
            computerHand.add(UnoCard.createCard());
            playerHand.add(UnoCard.createCard());
        }
    }

    public void resetVars(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
        actionCooldown = 0;
        playerHasDrawn = false;
        if (!isPlayerTurn)
            actionCooldown = (int) (Math.random()*500+500);
    }
    
    public void addCard(int ID) {
        assert ID == 0 || ID == 1 : "list variable is null or empty";
        if (ID == 0)
            computerHand.add(UnoCard.createCard());
        else
            playerHand.add(UnoCard.createCard());
    }

    public boolean checkIfPlayerMatch() {
        for (int i = 0; i < playerHand.size(); i++) {
            if (playerHand.get(i).matches(topCard)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfComputerMatch() {
        for (int i = 0; i < computerHand.size(); i++) {
            if (computerHand.get(i).matches(topCard)) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        if (isPlaying) {
            if (playerHand.size() == 0) {
                playerWon = true;
                isPlaying = false;
                mouseH.mouseLeftPressed = false;
            }
            if (computerHand.size() == 0) {
                computerWon = true;
                isPlaying = false;
                mouseH.mouseLeftPressed = false;
            }

            if (--actionCooldown < 0)
                actionCooldown = 0;

            //Players
            if (isPlayerTurn) {
                boolean playerHasMatch = checkIfPlayerMatch();

                if (playerHasDrawn && !playerHasMatch) {
                    resetVars(false);
                    return;
                }
                
                if (mouseH.mouseLeftPressed && !playerHasDrawn && mouseH.mouseX >= 25 && mouseH.mouseX <= 125 && mouseH.mouseY >= 375 && mouseH.mouseY <= 525) {
                    playerHand.add(UnoCard.createCard());
                    playerHasDrawn = true;
                    return;
                }
                
                if (mouseH.mouseLeftPressed && actionCooldown <= 0) {
                    for (int i = 0; i < playerHand.size(); i++) {
                        if (playerHand.get(i).matches(topCard) && mouseH.mouseX >= 25+125*i && mouseH.mouseX <= 125+125*i && mouseH.mouseY >= 725 && mouseH.mouseY <= 875) {
                            topCard = playerHand.remove(i);
                            actionCooldown = 500;
                            resetVars(false);
                            System.out.println("SUSSY");
                            break;
                        }
                    }
                }
            }
            //Computer's turn
            else if (actionCooldown <= 0) {
                for (int i = 0; i < computerHand.size(); i++) {
                    if (computerHand.get(i).matches(topCard)) {
                        topCard = computerHand.remove(i);
                        actionCooldown = 500;
                        resetVars(true);
                        return;
                    }
                }

                computerHand.add(UnoCard.createCard());
                resetVars(true);
            }
        }
        else {
            if (mouseH.mouseLeftPressed)
                reset();
        }
    }
    
    public void draw(Graphics2D g2) {
        //Background
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, 1600, 900);

        if (isPlaying) {
            g2.setColor(Color.white);
            g2.fillRect(25, 375, 100, 150);
            g2.setColor(Color.black);
            g2.fillOval(30, 380, 90, 140);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g2.setColor(Color.white);
            g2.drawString("?", 50, 480);
            
            g2.setColor(topCard.getCardColorAsColor());
            g2.fillRect(150, 375, 100, 150);
            g2.setColor(Color.white);
            g2.fillOval(155, 380, 90, 140);
            g2.setColor(Color.black);
            g2.fillOval(160, 385, 80, 130);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 80));
            g2.setColor(topCard.getCardColorAsColor());
            g2.drawString(topCard.getNumber()+"", 180, 475);
            
            
            for (int i = 0; i < computerHand.size(); i++) {
                g2.setColor(Color.white);
                g2.fillRect(25+125*i, 25, 100, 150);
                g2.setColor(Color.black);
                g2.fillOval(30+125*i, 30, 90, 140);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
                g2.setColor(Color.white);
                g2.drawString("?", 50+125*i, 130);
                //If not commented out, computer's cards are visible MIGHT BE BUGGY
                /*
                UnoCard card = computerHand.get(i);
                g2.setColor(card.getCardColorAsColor());
                g2.fillRect(25+125*i, 25, 100, 150);
                g2.setColor(Color.white);
                g2.fillOval(30+125*i, 30, 90, 140);
                g2.setColor(Color.black);
                g2.fillOval(35+125*i, 35, 80, 130);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 80));
                g2.setColor(card.getCardColorAsColor());
                g2.drawString(card.getNumber()+"", 55+125*i, 125);
                */
            }
            
            for (int i = 0; i < playerHand.size(); i++) {
                UnoCard card = playerHand.get(i);
                g2.setColor(card.getCardColorAsColor());
                g2.fillRect(25+125*i, 725, 100, 150);
                g2.setColor(Color.white);
                g2.fillOval(30+125*i, 730, 90, 140);
                g2.setColor(Color.black);
                g2.fillOval(35+125*i, 735, 80, 130);
                g2.setFont(new Font("TimesRoman", Font.PLAIN, 80));
                g2.setColor(card.getCardColorAsColor());
                g2.drawString(card.getNumber()+"", 55+125*i, 825);
            }
        }
        else if (playerWon) {
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 300));
            g2.setColor(Color.yellow);
            g2.drawString("You won!", 200, 300);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g2.setColor(Color.white);
            g2.drawString("(click to play again)", 400, 700);
        }
        else if (computerWon) {
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 200));
            g2.setColor(Color.red);
            g2.drawString("You lost!", 425, 175);
            g2.drawString("git gud", 525, 350);
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 100));
            g2.setColor(Color.white);
            g2.drawString("(click to play again)", 400, 700);
        }
    }
}