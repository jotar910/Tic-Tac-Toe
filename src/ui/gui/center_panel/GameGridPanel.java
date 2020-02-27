package ui.gui.center_panel;

import model.Grid;
import model.Player;
import model.TicTacToeCape;
import model.Token;
import model.states.PlayingState;
import ui.gui.Images;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class GameGridPanel extends JPanel implements Observer {
    private final int MARGIN = 25;
    private Boolean flagFinished = false;

    private CenterPanel myPlace;
    private TicTacToeCape game;

    private JLabel title;
    private PlayerGridBoard playersBoard;

    private Grid grid;
    private GridCell[][] cells;

    public GameGridPanel(TicTacToeCape _game, Grid _grid, CenterPanel _myPlace) {
        game = _game;
        game.addObserver(this);
        myPlace = _myPlace;
        grid = _grid;

        setMySize();
        cells = new GridCell[grid.getRows()][grid.getCols()];
        title = new JLabel();
        playersBoard = new PlayerGridBoard(grid, myPlace.getPlayerImage(), myPlace);

        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 32));

        setLayout(new BorderLayout());

        Box boxCenter = Box.createVerticalBox();
        JPanel mainPanel = new JPanel(new GridLayout(grid.getRows(), grid.getCols(), 10, 10));

        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                cells[i][j] = new GridCell(i, j);
                cells[i][j].addActionListener(new GridCellListener(i, j));
                mainPanel.add(cells[i][j]);
            }
        }

        boxCenter.add(Box.createVerticalGlue());
        boxCenter.add(mainPanel);
        boxCenter.add(Box.createVerticalGlue());

        add(BorderLayout.CENTER, boxCenter);
        add(BorderLayout.NORTH, title);
        add(BorderLayout.SOUTH, playersBoard);

        update(game, null);

    }

    private void setMySize() {
        Dimension mySize = new Dimension(myPlace.getWidth() - MARGIN * 2, myPlace.getHeight() - MARGIN * 2);
        setSize(mySize);
        setPreferredSize(mySize);
    }

    public Grid getGrid() {
        return grid;
    }

    private void updateTitle() {
        title.setText("Grid " + (game.getGrids().indexOf(game.getGrid()) + 1));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setMySize();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (grid.isFinished() && !flagFinished) {
            flagFinished = true;

            final Player theWinner = grid.getWinner();
            String title, msg;
            if (theWinner == null) {
                title = "Draw";
                msg = "This grid game ended in a draw!";
            } else {
                title = "Winner";
                msg = "The player " + theWinner.getName() + " won this grid game!";
            }
            JOptionPane.showMessageDialog(null,
                    msg,
                    title,
                    JOptionPane.INFORMATION_MESSAGE);
        }

        if (!(game.getState() instanceof PlayingState))
            return;

        updateTitle();
    }

    public class GridCell extends JButton {
        private int lin, col;

        public GridCell(int pLin, int pCol) {

            lin = pLin;
            col = pCol;

            setMySize();

        }

        private void setMySize() {
            if (game.getGrid() == null)
                return;

            int gaps = title.getFont().getSize() + playersBoard.getHeight();

            int w = (myPlace.getWidth() - gaps) / (game.getGrid().getCols() + 1) - 10;
            int h = (myPlace.getHeight() - gaps) / (game.getGrid().getRows() + 1) - 10;
            Dimension preferredSize = new Dimension(w, h);
            setPreferredSize(preferredSize);
            setMinimumSize(preferredSize);
            setMaximumSize(preferredSize);

            revalidate();
        }

        private void playTokenNoIcon(Graphics g, Token token) {
            if (token != null) {
                //TODO:jpmr - Each player has his own icon
                if (token.getPlayer().equals(grid.getPlayers()[0])) {
                    g.setColor(Color.red);
                    g.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    g.setColor(Color.blue);
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        }

        private void playerTokenWithIcon(Graphics g, String imageName) {
            g.drawImage(Images.getImage(imageName), 0, 0, getWidth(), getHeight(), this);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setMySize();

            Token token = grid.getToken(lin, col);
            if (token != null) {
                String imageName = myPlace.getPlayerImage().getPlayerImage(token.getPlayer());
                if (imageName == null) {
                    playTokenNoIcon(g, token);
                } else {
                    playerTokenWithIcon(g, imageName);
                }
            }

        }
    }

    private class GridCellListener implements ActionListener {
        private int lin, col;

        public GridCellListener(int pLin, int pCol) {
            lin = pLin;
            col = pCol;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.playToken(lin, col);
        }
    }
}
