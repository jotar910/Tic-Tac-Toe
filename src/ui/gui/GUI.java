package ui.gui;

import model.TicTacToeCape;
import ui.gui.center_panel.CenterPanel;
import ui.gui.right_panel.RightPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class GUI extends JFrame implements Observer {
    private TicTacToeCape game;
    private MyMenuBar menu;

    private PlayerImage playerImage;

    private CenterPanel center;
    private RightPanel right;

    public GUI(TicTacToeCape game) {
        super("TicTacToe - João Rodrigues");
        Container cp = getContentPane();
        this.game = game;
        game.addObserver(this);

        playerImage = new PlayerImage(game);
        center = new CenterPanel(game, playerImage);
        right = new RightPanel(game, playerImage);
        menu = new MyMenuBar(game, this);

        setJMenuBar(menu);
        cp.add(BorderLayout.CENTER, center);
        cp.add(BorderLayout.EAST, right);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension mSize = new Dimension(CONST.WND_WIDTH, CONST.WND_HEIGHT);
        setPreferredSize(mSize);
        setMinimumSize(mSize);
        setLocation(CONST.WND_X0, CONST.WND_Y0);
        setVisible(true);

        validate();
        repaint();

        pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        repaint();
    }
}
