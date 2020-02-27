package model;

import model.states.IStates;

import java.util.List;
import java.util.Observable;

public class TicTacToeCape extends Observable {
    private TicTacToe game;

    public TicTacToeCape() {
        game = new TicTacToe();
    }

    public TicTacToeData getData() {
        return game.getData();
    }

    public void setData(TicTacToe data) {
        game.setData(game.getData());
    }

    public IStates getState() {
        return game.getState();
    }

    public void setState(IStates state) {
        game.setState(state);
    }

    public List<String> getMsglog() {
        return game.getMsglog();
    }

    public void clearMsglog() {
        game.clearMsglog();
    }

    public synchronized List<Player> getPlayers() {
        return game.getPlayers();
    }

    public void start(int seqNum, int gridW, int gridH) {
        game.start(seqNum, gridW, gridH);

        setChanged();
        notifyObservers();
    }

    public void playToken(int lin, int col) {
        game.playToken(lin, col);

        setChanged();
        notifyObservers();
    }

    public void undoPlayToken() {
        game.undoPlayToken();

        setChanged();
        notifyObservers();
    }

    public void redoPlayToken() {
        game.redoPlayToken();

        setChanged();
        notifyObservers();
    }

    public void addPlayer() {
        game.addPlayer();

        setChanged();
        notifyObservers();
    }

    public void changePlayerName(int ind, String s) {
        game.changePlayerName(ind, s);

        setChanged();
        notifyObservers();
    }

    public void imprimeTab() {
        game.imprimeTab();
    }

    public void imprimeCurPlayer() {
        game.imprimeCurPlayer();
    }

    public List<Grid> getGrids() {
        return game.getGrids();
    }

    public Grid getGrid() {
        return getData().getGrid();
    }

    public int getMaxPlayers() {
        return game.getMaxPlayers();
    }

    public void restart() {
        game.restart();

        setChanged();
        notifyObservers();
    }

    public synchronized void clear() {
        game.clear();

        setChanged();
        notifyObservers();
    }
}
