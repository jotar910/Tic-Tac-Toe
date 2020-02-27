package model.states;

import command.ICommand;
import model.TicTacToeData;

public class StateAdapter implements IStates {
    private TicTacToeData data;

    public StateAdapter(TicTacToeData data) {
        this.data = data;
    }

    public TicTacToeData getData() {
        return this.data;
    }

    public void setData(TicTacToeData data) {
        this.data = data;
    }

    @Override
    public IStates start(int seqNum, int gridW, int gridH) {
        return this;
    }

    @Override
    public IStates invokeCommand(ICommand command) {
        return this;
    }

    @Override
    public IStates undoCommand() {
        return this;
    }

    @Override
    public IStates redoCommand() {
        return this;
    }

    @Override
    public IStates addPlayer() {
        return this;
    }

    @Override
    public IStates changePlayerName(int ind, String s) {
        return this;
    }

    @Override
    public IStates restart() {
        return this;
    }

    @Override
    public IStates clear() {
        return this;
    }

}
