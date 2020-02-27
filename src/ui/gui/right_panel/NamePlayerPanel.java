package ui.gui.right_panel;

import model.Player;
import model.TicTacToeCape;
import model.states.BeginningState;
import model.states.PlayingState;

import javax.swing.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

class NamePlayerPanel extends JPanel implements Observer {
    private TicTacToeCape game;
    private Player player;

    private JLabel name;
    private JTextField nameEdit;

    public NamePlayerPanel(TicTacToeCape _game, Player _player){
        game = _game;
        game.addObserver(this);
        player = _player;
        name = new JLabel();
        nameEdit = new JTextField();
        nameEdit.setVisible(false);

        add(name);
        add(nameEdit);

        name.addMouseListener(new ChangeNameListener());
        nameEdit.addKeyListener(new KeyboardListener());

        update(game, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(game.getState() instanceof PlayingState){
            nameEdit.setVisible(false);
            name.setVisible(true);
        }

        name.setText(player.getName());
    }

    private class KeyboardListener extends KeyAdapter {

        @Override
        public void keyTyped(KeyEvent e) {
            if(e.getKeyChar() == '\n') {
                game.changePlayerName(game.getPlayers().indexOf(player), nameEdit.getText());
                nameEdit.setVisible(false);
                name.setVisible(true);
            }
        }
    }

    private class ChangeNameListener extends MouseAdapter {
        public void mousePressed(MouseEvent me){
            if(!(game.getState() instanceof BeginningState) || !name.isVisible())
                return;
            name.setVisible(false);
            nameEdit.setVisible(true);
            nameEdit.setText(player.getName());
            nameEdit.requestFocus();
            nameEdit.selectAll();
        }
    }

}
