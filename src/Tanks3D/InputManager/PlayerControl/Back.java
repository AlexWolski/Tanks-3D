package Tanks3D.InputManager.PlayerControl;

import Tanks3D.Player;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Back extends AbstractAction {
    private Player player;
    private boolean keyPressed;

    public Back(Player player, boolean keyPressed) {
        this.player = player;
        this.keyPressed = keyPressed;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.back(keyPressed);
    }
}
