package ui.gui.center_panel;

import model.Grid;
import model.TicTacToeCape;
import model.states.BeginningState;
import model.states.IStates;
import model.states.PlayingState;
import ui.gui.CONST;
import ui.gui.Images;
import ui.gui.PlayerImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class CenterPanel extends JPanel implements Observer {
    private TicTacToeCape game;
    private PlayerImage playerImage;
    private JPanel holdGridsPanel;
    private BackgroundLayer bgPanel;
    private List<GameGridPanel> gridPanels;

    public CenterPanel(TicTacToeCape game, PlayerImage _playerImage) {
        playerImage = _playerImage;

        setLayout(new BorderLayout());

        setSize((int)(CONST.WND_WIDTH * 0.7), (int)(CONST.WND_HEIGHT * 0.85));

        this.game = game;
        this.gridPanels = new ArrayList<>();
        this.bgPanel = new BackgroundLayer(this);

        game.addObserver(this);

        holdGridsPanel = new JPanel();

        holdGridsPanel.add(BorderLayout.CENTER, bgPanel);
        add(BorderLayout.CENTER, holdGridsPanel);

        update(game, null);
    }

    public void setupGrids(){
        List<Grid> grids = game.getGrids();
        for (Grid g : grids) {
            GameGridPanel gp = new GameGridPanel(game, g, this);
            gridPanels.add(gp);
            gp.setVisible(false);
            holdGridsPanel.add(gp);
        }
    }

    public void resetPanel(){
        gridPanels.clear();
        holdGridsPanel.removeAll();
        holdGridsPanel.add(bgPanel);
    }

    public PlayerImage getPlayerImage() {
        return playerImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setSize(getWidth(), getHeight());
    }

    @Override
    public void update(Observable o, Object arg) {
        if(game.getData().isNewGame()){
            resetPanel();
            setupGrids();
            game.getData().setNewGame(false);

        } else if(gridPanels.size() == 0 && game.getState() instanceof PlayingState) {
            setupGrids();
        }else if(gridPanels.size() != 0 && game.getState() instanceof BeginningState) {
            resetPanel();
        }

        IStates state = game.getState();
        if(state instanceof PlayingState) {
            Grid curGrid = game.getGrid();
            if (curGrid != null) {
                for (GameGridPanel gp : gridPanels) {
                    if (gp.getGrid().equals(curGrid)) {
                        gp.setVisible(true);
                    } else {
                        if (gp.isVisible()) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) { e.printStackTrace(); }
                        }
                        gp.setVisible(false);
                    }
                }
                bgPanel.setVisible(false);
            }
        }else if(state instanceof BeginningState){
            for (GameGridPanel gp : gridPanels)
                gp.setVisible(false);
            bgPanel.setVisible(true);
        }

        repaint();
    }

    private class BackgroundLayer extends JPanel {
        protected int MARGIN = 50;
        private CenterPanel myPlace;
        private Image image = Images.getImage(CONST.TICTACTOE_IMAGE);

        public BackgroundLayer(CenterPanel _myPlace){
            myPlace = _myPlace;
            setPreferredSize(myPlace.getSize());
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            setPreferredSize(myPlace.getSize());
            g.drawImage(image, MARGIN, MARGIN/2, getWidth()-MARGIN*2, getHeight()-MARGIN*2, this);
        }
    }
}
