package ui.gui.right_panel;

import model.Player;
import model.TicTacToeCape;
import model.states.PlayingState;
import ui.gui.CONST;
import ui.gui.PlayerImage;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RightPanel extends JPanel {
    private TicTacToeCape game;
    private PlayerImage playerImage;

    private MyScrollPane scrollPane;
    private Box playersContainer;
    private List<PlayerPanel> playerPanels;
    private AddPlayerPanel addPlayerBtn;

    public RightPanel(TicTacToeCape _game, PlayerImage _playerImage) {
        game = _game;
        playerImage = _playerImage;

        Dimension prefSize = new Dimension((int)((CONST.WND_WIDTH-10)*0.3), CONST.WND_HEIGHT);
        setPreferredSize(prefSize);
        setMinimumSize(prefSize);
        setMaximumSize(prefSize);

//        setBorder(BorderFactory.createLineBorder(Color.black, 2));

        playerPanels = new ArrayList<>();
        addPlayerBtn = new AddPlayerPanel(game, this);
        playersContainer = Box.createVerticalBox();
        scrollPane = new MyScrollPane(playersContainer, prefSize);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setupLayout();

        repaint();
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        playersContainer.add(addPlayerBtn);

        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalGlue());
        hBox.add(scrollPane);
        hBox.add(Box.createHorizontalGlue());
        add(BorderLayout.CENTER, hBox);
    }

    private void reArrangePlayers() {
        List<Player> players = game.getPlayers();

        if(players == null)  return;
        if(players.size() == playerPanels.size()) return;

        playerPanels.clear();
        playersContainer.removeAll();
        for(Player p : players){
            PlayerPanel playerPanel = new PlayerPanel(game, p, this);
            playerPanels.add(playerPanel);
            playersContainer.add(Box.createVerticalStrut(10));
            playersContainer.add(playerPanel);
        }
        if(playerPanels.size() < game.getMaxPlayers())
            playersContainer.add(addPlayerBtn);
        revalidate();
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        reArrangePlayers();

        if(game.getState() instanceof PlayingState)
            addPlayerBtn.setVisible(false);
        else
            addPlayerBtn.setVisible(true);
    }

    class MyScrollPane extends JScrollPane {
        private Insets margin = new Insets(10, 20, 20, 20);

        public MyScrollPane(Container c, Dimension _prefSize){
            super(c);
            Border prevBorder = getBorder();
            setBorder(
                    BorderFactory.createCompoundBorder(
                            new EmptyBorder(margin),
                            prevBorder));

            setPreferredSize(new Dimension(_prefSize));

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }
}