package ui.gui;

import model.Player;
import model.TicTacToeCape;
import model.states.BeginningState;
import model.states.PlayingState;

import java.util.*;

public class PlayerImage implements Observer {
    private Boolean flagPlayed = false;
    private TicTacToeCape game;
    private Map<Player, String> playersImages = new HashMap<>();

    public PlayerImage(TicTacToeCape _game) {
        game = _game;
        game.addObserver(this);
    }

    public String getPlayerImage(Player p) {
        return playersImages.get(p);
    }

    public void addPlayerImage(Player p, String i) {
        if (p == null || i == null)
            return;
        playersImages.put(p, i);
    }

    public synchronized Map<Player, String> getPlayersImages() {
        return playersImages;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (!flagPlayed && game.getState() instanceof PlayingState) {
            flagPlayed = true;
        } else if (flagPlayed && game.getState() instanceof BeginningState) {
            flagPlayed = false;
        }

        List<Player> playersOnGame = game.getPlayers();

        if (playersOnGame.isEmpty()) {
            playersImages.clear();
            return;
        }

        for (Player player : playersOnGame) {
            if (!getPlayersImages().containsKey(player)) {
                String imageName;
                do {
                    imageName = CONST.PLAYER_ICONS[(int) (Math.random() * CONST.PLAYER_ICONS.length)];
                } while (playersImages.containsValue(imageName));
                playersImages.put(player, imageName);
            }
        }

        for (Player player : playersImages.keySet()) {
            if (!playersOnGame.contains(player)) {
                playersImages.remove(player);
            }
        }
    }
}
