package ui.gui.right_panel;

import model.TicTacToeCape;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddPlayerPanel extends ComponentRightPanel {
    public AddPlayerPanel(TicTacToeCape _game, RightPanel _myPlace) {
        super(_game, _myPlace);

        setBorder(
                BorderFactory.createCompoundBorder(
                        new EmptyBorder(new Insets(MARGIN, MARGIN, MARGIN, MARGIN)),
                        BorderFactory.createDashedBorder(Color.gray, 10, 5)));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                getGame().addPlayer();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlusSign(g);
    }

    private void drawPlusSign(Graphics g) {
        g.drawLine(getWidth()/2, getHeight()/4, getWidth()/2, getHeight() - getHeight()/4);
        g.drawLine(getWidth()/4, getHeight()/2, getWidth() - getWidth()/4, getHeight()/2);
    }

}
