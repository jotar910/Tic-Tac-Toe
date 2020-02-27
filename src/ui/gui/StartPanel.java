package ui.gui;

import model.TicTacToeCape;

import javax.swing.*;
import java.awt.*;

public class StartPanel extends JPanel {
    private TicTacToeCape game;
    private StartTextFields tfW, tfH, tfSeq;

    public StartPanel(TicTacToeCape _game){
        game = _game;

        tfW = new StartTextFields();
        tfH = new StartTextFields();
        tfSeq = new StartTextFields();

        setLayout(new BorderLayout());

        add(BorderLayout.CENTER, setupTextFieldsLayout());

    }

    public String getW(){
        return tfW.getText();
    }

    public String getH(){
        return tfH.getText();
    }

    public String getSeq(){
        return tfSeq.getText();
    }

    private Box setupTextFieldsLayout(){
        Box bVertical = Box.createVerticalBox();
        Box b = Box.createHorizontalBox();
        int gapWidth = 10;

        b.add(Box.createHorizontalGlue());
        b.add(new JLabel("Cols: "));
        b.add(tfW);
        b.add(Box.createHorizontalStrut(gapWidth));
        b.add(new JLabel("Rows: "));
        b.add(tfH);
        b.add(Box.createHorizontalStrut(gapWidth));
        b.add(new JLabel("Sequence: "));
        b.add(tfSeq);
        b.add(Box.createHorizontalGlue());

        bVertical.add(Box.createVerticalGlue());
        bVertical.add(b);
        bVertical.add(Box.createVerticalGlue());
        return bVertical;
    }

    private class StartTextFields extends JTextField {
        public StartTextFields(){
            super("3");
            Dimension mSize = new Dimension(100, 25);
            setPreferredSize(mSize);
            setMaximumSize(mSize);
        }
    }
}
