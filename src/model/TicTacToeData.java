package model;

import model.exceptions.InvalidTokenPositionException;
import ui.gui.CONST;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeData {
    public static int MAX_PLAYERS = 5;
    private boolean newGame;

    private int seqNum;
    private List<Player> players;
    private int indGrid;
    private List<Grid> grids;

    private List<String> msglog;

    public TicTacToeData() {
        players = new ArrayList<>();
        grids = new ArrayList<>();
        msglog = new ArrayList<>();

        newGame = false;
    }

    public boolean isNewGame() {
        return newGame;
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Grid getGrid() {
        if (grids.size() == 0)
            return null;
        return grids.get(indGrid);
    }

    public Player getPlayer() {
        return grids.get(indGrid).getCurPlayer();
    }

    public Player getPlayer(int ind) throws IndexOutOfBoundsException {
        return players.get(ind);
    }

    public void setNewGame(boolean _newGame) {
        newGame = _newGame;
    }

    public void setPlayerName(Player p, String s) {
        p.setName(s);
    }

    public void addMsglog(String s) {
        msglog.add(s);
    }

    public void clearMsglog() {
        msglog.clear();
    }

    public List<String> getMsglog() {
        return msglog;
    }

    public List<Grid> getGrids() {
        return grids;
    }

    public void addToken(int lin, int col) throws InvalidTokenPositionException {
        Grid curGrid = grids.get(indGrid);
        if (!curGrid.isPosValid(lin, col))
            throw new InvalidTokenPositionException("Invalid Token Position at addToken().");

        curGrid.addToken(lin, col);
    }

    public void remToken(int lin, int col) throws InvalidTokenPositionException {
        Grid curGrid = grids.get(indGrid);
        if (!curGrid.isPosValid(lin, col))
            throw new InvalidTokenPositionException("Invalid Token Position at addToken().");

        curGrid.remToken(lin, col);
    }

    private boolean hasHorizontalSeq(Token[][] tab) {
        for (int i = 0; i < tab.length; i++) {
            for (int j = 0, seqTotal = 0; j < tab[i].length; j++) {
                if (tab[i][j] != null) {
                    if (++seqTotal == seqNum)
                        return true;
                } else
                    seqTotal = 0;
            }
        }
        return false;
    }

    private boolean hasVerticalSeq(Token[][] tab) {
        for (int j = 0; j < tab[0].length; j++) {
            for (int i = 0, seqTotal = 0; i < tab.length; i++) {
                if (tab[i][j] != null) {
                    if (++seqTotal == seqNum)
                        return true;
                } else
                    seqTotal = 0;
            }
        }
        return false;
    }

    private boolean hasDiagonalSeq(Token[][] tab) {
        Grid grid = getGrid();
        int w = grid.getCols(), h = grid.getRows();
        for (int i = 0, j = 0; i < h && j < w; j++) {
            while (j < w - seqNum + 1 && tab[i][j] == null) {
                j++;
            }
            if (j >= w - seqNum + 1) {
                if (i >= h - seqNum + 1)
                    return false;
                j = 0;
                i++;
                continue;
            }
            for (int lin = i, col = j, seqTotal = 0; lin < h && col < w && tab[lin][col] != null; lin++, col++) {
                seqTotal++;
                if (seqTotal == seqNum)
                    return true;
            }
            for (int lin = i, col = j, seqTotal = 0; lin < h && col > 0 && tab[lin][col] != null; lin++, col--) {
                seqTotal++;
                if (seqTotal == seqNum)
                    return true;
            }
        }
        return false;
    }

    private boolean existFreePositions(Token[][] tab) {
        for (int i = 0; i < tab.length; i++)
            for (int j = 0; j < tab[i].length; j++)
                if (tab[i][j] == null)
                    return true;
        return false;
    }

    public CONST.GAMEOVER isGameOver() {
        Grid curGrid = grids.get(indGrid);
        if (!existFreePositions(curGrid.getTokens())) {
            return CONST.GAMEOVER.DRAW;
        }
        for (Player p : curGrid.getPlayers()) {
            Token[][] tab = curGrid.getTokens(p);
            if (hasDiagonalSeq(tab) || hasHorizontalSeq(tab) || hasVerticalSeq(tab)) {
                return CONST.GAMEOVER.YES;
            }
        }
        return CONST.GAMEOVER.NO;
    }

    public boolean nextPlayerPlaying() {
        Grid curGrid = grids.get(indGrid);
        curGrid.changePlayer();
        if (curGrid.getIndPlayer() == 0 || isGameOver() != CONST.GAMEOVER.NO) {
            for (int i = 0; i < grids.size(); i++) {
                indGrid = (indGrid + 1) % grids.size();
                if (!grids.get(indGrid).isFinished())
                    return true;
            }
            return false;
        }
        return true;
    }

    public void prevPlayerPlaying() {
        Grid curGrid = grids.get(indGrid);
        curGrid.changePlayer();
        if (curGrid.getIndPlayer() == 0) {
            indGrid = (grids.size() + indGrid - 1) % grids.size();
        }
    }

    public void addNewPlayer() {
        if (players.size() < MAX_PLAYERS)
            players.add(new Player());
    }

    public boolean isPlayerNameAvailable(String s) {
        for (Player p : players)
            if (p.getName().equals(s))
                return false;
        return true;
    }

    public void configNewGame(int seqNum, int gridW, int gridH) {
        this.seqNum = seqNum;
        indGrid = 0;
        for (int i = players.size() - 1; i > 0; i--)
            for (int j = 0; j < i; j++)
                grids.add(new Grid(gridH, gridW));

        distributePlayersOnGrids();
        newGame = true;
    }

    public boolean restartGame() {
        try {
            int gridW = grids.get(0).getCols();
            int gridH = grids.get(0).getRows();
            grids.clear();
            configNewGame(seqNum, gridW, gridH);
            return true;
        } catch (IndexOutOfBoundsException iobe) {
            grids.clear();
            players.clear();
            return false;
        }
    }

    private void distributePlayersOnGrids() {
        for (int i = 0, c = 0; i < players.size() - 1 && c < grids.size(); i++) {
            for (int j = i + 1; j < players.size(); j++) {
                grids.get(c).addPlayer(players.get(i));
                grids.get(c).addPlayer(players.get(j));
                c++;
            }
        }
    }

    public void clearGameData(boolean clearPlayers) {
        if (clearPlayers) {
            players = new ArrayList<>();
        }

        grids = new ArrayList<>();
        seqNum = 0;
        indGrid = 0;
    }

}
