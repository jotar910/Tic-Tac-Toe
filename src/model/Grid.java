package model;

import model.exceptions.InvalidTokenPositionException;
import ui.gui.CONST;

import java.util.Arrays;

public class Grid {
    private boolean finished;
    private int cols, rows;

    private Player winner;
    private int indPlayer;
    private Player[] players;
    private Token[][] tokens;

    public Grid(int _rows, int _cols) {
        this.finished = false;
        this.cols = _cols;
        this.rows = _rows;
        this.indPlayer = 0;
        this.players = new Player[2];
        this.tokens = new Token[cols][rows];
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getIndPlayer() {
        return indPlayer;
    }

    public void setIndPlayer(int indPlayer) {
        this.indPlayer = indPlayer;
    }

    public void changePlayer() {
        this.indPlayer = indPlayer == 0 ? 1 : 0;
    }

    public Player getCurPlayer() {
        return players[indPlayer];
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        if (this.players.length == players.length)
            this.players = players;
    }

    public Token[][] getTokens() {
        return tokens;
    }

    public void setTokens(Token[][] tokens) {
        this.tokens = tokens;
    }

    public Token[][] getTokens(Player p) {
        Token[][] list = new Token[tokens.length][];
        for (int i = 0; i < tokens.length; i++) {
            list[i] = new Token[tokens[i].length];
            for (int j = 0; j < tokens[i].length; j++)
                if (tokens[i][j] != null && tokens[i][j].getPlayer().equals(p))
                    list[i][j] = tokens[i][j];
                else
                    list[i][j] = null;
        }
        return list;
    }

    public void addPlayer(Player player) {
        for (int i = 0; i < 2; i++) {
            if (players[i] == null) {
                players[i] = player;
                return;
            }
        }
    }

    void addToken(int col, int lin) {
        tokens[lin][col] = new Token(players[indPlayer]);
    }

    void remToken(int col, int lin) {
        tokens[lin][col] = null;
    }

    public boolean isPosValid(int col, int lin) {
        return col > -1 & lin > -1 && col < cols && lin < rows;
    }

    public boolean isPosEmpty(int col, int lin) throws InvalidTokenPositionException {
        if (!isPosValid(col, lin))
            throw new InvalidTokenPositionException("Invalid Token Position at isPosEmpty().");
        return tokens[lin][col] == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grid grid = (Grid) o;
        return cols == grid.cols &&
                rows == grid.rows &&
                Arrays.equals(players, grid.players);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(players);
    }

    public Token getToken(int lin, int col) {
        if(lin<tokens.length && col<tokens[lin].length)
            return tokens[lin][col];
        return null;
    }

    public void setWinner(CONST.GAMEOVER gameover) {
        if(gameover == CONST.GAMEOVER.YES)
            winner = getCurPlayer();
        else
            winner = null;
    }

    public Player getWinner() {
        return winner;
    }
}