package ui.gui.right_panel;

import model.Player;
import model.TicTacToeCape;
import model.states.BeginningState;
import ui.gui.Images;
import ui.gui.Resources;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Map;

public class PlayerPanel extends ComponentRightPanel {
    private Player player;
    private ImagePlayerPanel image;
    private NamePlayerPanel namePanel;

    public PlayerPanel(TicTacToeCape _game, Player _player, RightPanel _myPlace) {
        super(_game, _myPlace);

        player = _player;
        image = new ImagePlayerPanel();

        namePanel = new NamePlayerPanel(getGame(), player);

        setBorder(
                BorderFactory.createCompoundBorder(
                        new EmptyBorder(new Insets(MARGIN, MARGIN, MARGIN, MARGIN)),
                        new LineBorder(Color.black, 1)));

        setLayout(new BorderLayout());

        add(BorderLayout.CENTER, image);

        Box south = Box.createHorizontalBox();
        south.add(Box.createHorizontalGlue());
        south.add(namePanel);
        south.add(Box.createHorizontalGlue());
        south.setBorder(new EmptyBorder(3, 0, 10, 0));
        add(BorderLayout.SOUTH, south);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private class ImagePlayerPanel extends JPanel {
        protected int MARGIN = 10;

        public ImagePlayerPanel(){
            addMouseListener(new ChooseAvatarListener());
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void paintComponent(Graphics g) {
            int myWidth = getWidth();
            int size = Math.min(myWidth, getHeight()) - 2 * MARGIN;
            String imageName = getMyPlace().getPlayerImage().getPlayerImage(player);
            if (imageName == null) {
                g.fillRect((myWidth - size) / 2 + (myWidth - size) % 2, MARGIN, size, size);
            } else {
                Image image = Images.getImage(imageName);
                g.drawImage(image, (myWidth - size) / 2 + (myWidth - size) % 2, MARGIN, size, size, this);
            }
        }

        private class ChooseAvatarListener extends MouseAdapter {
            @Override
            public void mousePressed(MouseEvent me) {
                if(!(getGame().getState() instanceof BeginningState))
                    return;

                final JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new File(Resources.getResourceFile("img").getFile() + "/"));
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String fName = fc.getSelectedFile().getName();
                    Map<Player, String> playersImages = getMyPlace().getPlayerImage().getPlayersImages();

                    if (!fName.matches(".*(.jpg|.png)$")) {
                        JOptionPane.showMessageDialog(null, "Select an image file!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else if (playersImages.get(player).equals(fName)) {
                        return;
                    } else if (playersImages.containsValue(fName)) {
                        JOptionPane.showMessageDialog(null, "Image already used for another player!", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        getMyPlace().getPlayerImage().addPlayerImage(player, fName);
                        getMyPlace().repaint();
                    }

                }
            }
        }
    }
}
