package ui.gui;

import model.TicTacToeCape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartOptionFrame extends JDialog {
    static final String mFrameTitle = "Start Menu";

    private TicTacToeCape game;
    private StartPanel startPanel;
    JButton bStart, bClose;

    public StartOptionFrame(TicTacToeCape game, JFrame _parentFrame) {
        super(_parentFrame, mFrameTitle, true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension mSize = new Dimension(CONST.WND_START_WIDTH, CONST.WND_START_HEIGHT);

        this.game = game;
        startPanel = new StartPanel(game);
        bStart = new JButton("Start");
        bClose = new JButton("Close");

        add(BorderLayout.CENTER, startPanel);
        add(BorderLayout.SOUTH, setupButtonsPanel());

        setupListeners();

        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        setBounds(100, 100, 300, 200);

        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        setPreferredSize(mSize);
        setMinimumSize(mSize);

        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        validate();

        bStart.requestFocus();
        pack();
    }

    private void setupListeners() {
        bStart.addActionListener((e) -> {
            try {
                int w, h, seq;
                seq = Integer.parseInt(startPanel.getSeq());
                w = Integer.parseInt(startPanel.getW());
                h = Integer.parseInt(startPanel.getH());
                game.start(seq, w, h);
                dispose();
            } catch (NumberFormatException nfe){
                JOptionPane.showConfirmDialog(null,
                        "Please insert integer on the fields!",
                        "Error",
                        JOptionPane.OK_OPTION,
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        bClose.addActionListener((e) -> dispose());
    }

    private JPanel setupButtonsPanel() {
        JPanel bPanel = new JPanel();
        Box b = Box.createHorizontalBox();

        b.add(bStart);
        b.add(Box.createHorizontalStrut(10));
        b.add(bClose);

        bPanel.setLayout(new BorderLayout());
        bPanel.add(BorderLayout.EAST, b);
        bPanel.setBorder(new EmptyBorder(new Insets(5, 0, 15, 35)));

        return bPanel;
    }
}