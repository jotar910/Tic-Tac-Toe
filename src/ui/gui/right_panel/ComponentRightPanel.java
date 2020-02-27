package ui.gui.right_panel;

import model.TicTacToeCape;

import javax.swing.*;
import java.awt.*;

public class ComponentRightPanel extends JPanel {
    protected static int MARGIN = 15;

    private RightPanel myPlace;
    private TicTacToeCape game;

    public ComponentRightPanel(TicTacToeCape _game, RightPanel _myPlace) {
        game = _game;
        myPlace = _myPlace;

        int minusSize = 50;
        int w = (int)(myPlace.getPreferredSize().getWidth() - minusSize - MARGIN*2 - 5);
        Dimension mySize = new Dimension(w, w);

        setPreferredSize(mySize);
        setMaximumSize(mySize);
        setMaximumSize(mySize);
    }

    public RightPanel getMyPlace() {
        return myPlace;
    }

    public TicTacToeCape getGame() {
        return game;
    }
}
