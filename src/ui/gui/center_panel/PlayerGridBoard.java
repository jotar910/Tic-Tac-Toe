package ui.gui.center_panel;

import model.Grid;
import model.Player;
import ui.gui.Images;
import ui.gui.PlayerImage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayerGridBoard extends JPanel {
    private Grid grid;
    private Component myPlace;
    private PlayerImage playersImages;

    public PlayerGridBoard(Grid _grid, PlayerImage _playersImages, Component _myPlace){
        grid = _grid;
        myPlace = _myPlace;
        playersImages = _playersImages;

        Dimension mySize = new Dimension(myPlace.getWidth(), (int)(myPlace.getHeight()*0.2));
        setSize(mySize);
        setPreferredSize(mySize);

        setLayout(new BorderLayout());

        JLabel lVs = new JLabel("vs.");
        lVs.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 32));

        JPanel panel = new JPanel();
        panel.add(new PlayerAvatar(grid.getPlayers()[0], this));
        panel.add(lVs);
        panel.add(new PlayerAvatar(grid.getPlayers()[1], this));

        add(BorderLayout.CENTER, panel);

       /* add(BorderLayout.CENTER, lVs);
        add(BorderLayout.WEST, new PlayerAvatar(grid.getPlayers()[0], this));
        add(BorderLayout.EAST, new PlayerAvatar(grid.getPlayers()[1], this));*/


    }

    public class PlayerAvatar extends JPanel {
        private int BORDER_SIZE = 10;
        private Player player;
        private Image image;
        private PlayerGridBoard board;

        public PlayerAvatar(Player _player, PlayerGridBoard _board){
            board = _board;
            player = _player;
            image = Images.getImage(playersImages.getPlayerImage(player));

            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            setMySize();
        }

        private void setMySize(){
            int length = Math.min(board.getWidth()/3 - BORDER_SIZE, board.getHeight() - BORDER_SIZE);
            Dimension mySize = new Dimension(length, length);
            setSize(mySize);
            setPreferredSize(mySize);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            setMySize();

            int size = getWidth();
            g.drawImage(image, BORDER_SIZE, BORDER_SIZE, size - BORDER_SIZE*2, size - BORDER_SIZE*2, this);

            if(grid.getCurPlayer().equals(player)) {
                Graphics2D g2 = (Graphics2D)g;
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(BORDER_SIZE));
                g2.setColor(Color.yellow);
                g2.drawRect(0, 0, size, size);
                g2.setStroke(oldStroke);
            }
        }
    }
}
