package model;

import command.PlayTokenCommand;
import model.states.BeginningState;
import model.states.IStates;

import java.util.List;

public class TicTacToe {
    private TicTacToeData data;
    private IStates state;

    public TicTacToe() {
        data = new TicTacToeData();
        state = new BeginningState(data);
    }

    public TicTacToeData getData() {
        return data;
    }

    public void setData(TicTacToeData data) {
        this.data = data;
    }

    public IStates getState() {
        return state;
    }

    public void setState(IStates state) {
        this.state = state;
    }

    public List<String> getMsglog() {
        return data.getMsglog();
    }

    public void clearMsglog() {
        data.clearMsglog();
    }

    public List<Player> getPlayers() {
        return data.getPlayers();
    }

    public void start(int seqNum, int gridW, int gridH) {
        setState(state.start(seqNum, gridW, gridH));
    }

    public void playToken(int lin, int col) {
        setState(state.invokeCommand(new PlayTokenCommand(data, lin, col)));
    }

    public void undoPlayToken() {
        state.undoCommand();
    }

    public void redoPlayToken() {
        state.redoCommand();
    }

    public void addPlayer() {
        setState(state.addPlayer());
    }

    public void changePlayerName(int ind, String s) {
        setState(state.changePlayerName(ind, s));
    }

    public void imprimeTab() {
        Grid grid = data.getGrid();
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++)
                if (grid.getTokens()[i][j] == null)
                    System.out.print(" o ");
                else
                    System.out.print(" x ");
            System.out.println();
        }
    }

    public void imprimeCurPlayer() {
        System.out.println("Current player: " + data.getPlayer());
    }

    public List<Grid> getGrids() {
        return data.getGrids();
    }

    public int getMaxPlayers() {
        return data.getMaxPlayers();
    }

    public void restart() {
        setState(state.restart());
    }

    public void clear() {
        setState(state.clear());
    }
}
