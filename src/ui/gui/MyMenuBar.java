package ui.gui;

import model.TicTacToeCape;
import model.states.BeginningState;
import model.states.IStates;
import model.states.PlayingState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MyMenuBar extends JMenuBar implements Observer {
    private TicTacToeCape game;
    private JMenuItem start, restart, clear, quit;
    private JMenuItem undo, redo;

    public MyMenuBar(TicTacToeCape _game, JFrame _frame) {
        game = _game;
        game.addObserver(this);

        JMenu menuGame;

        start = new JMenuItem("Start");
        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_MASK));
        restart = new JMenuItem("Restart");
        restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
        clear = new JMenuItem("Clear");
        restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK));
        quit = new JMenuItem("Quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));

        menuGame = new JMenu("Game");
        menuGame.add(start);
        menuGame.add(restart);
        menuGame.add(clear);
        menuGame.add(quit);

        start.addActionListener((e) -> new StartOptionFrame(game, _frame));
        restart.addActionListener((e) -> game.restart());
        clear.addActionListener((e) -> game.clear());
        quit.addActionListener((e) -> {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Quit?", "Quit Game", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (dialogResult == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        JMenu menuEdit;

        undo = new JMenuItem("Undo");
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
        redo = new JMenuItem("Redo");
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));

        menuEdit = new JMenu("Edit");
        menuEdit.add(undo);
        menuEdit.add(redo);

        undo.addActionListener((e) -> game.undoPlayToken());
        redo.addActionListener((e) -> game.redoPlayToken());


        add(menuGame);
        add(menuEdit);

        validate();
        update(game, null);
    }

    @Override
    public void update(Observable o, Object arg) {
        IStates state = game.getState();
        if (state instanceof BeginningState) {
            start.setEnabled(game.getPlayers().size() > 1);
            restart.setEnabled(false);
            undo.setEnabled(false);
            redo.setEnabled(false);
        } else {
            start.setEnabled(false);
            restart.setEnabled(true);
            if (state instanceof PlayingState) {
                if (((PlayingState) state).getNoUndoCommands() > 0)
                    undo.setEnabled(true);
                if (((PlayingState) state).getNoRedoCommands() > 0)
                    redo.setEnabled(true);
            }
        }
    }
}
