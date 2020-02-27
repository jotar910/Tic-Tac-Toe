package model.states;

import command.ICommand;
import model.TicTacToeData;

public interface IStates {
    TicTacToeData getData();

    IStates start(int seqNum, int gridW, int gridH);

    IStates invokeCommand(ICommand command);

    IStates undoCommand();

    IStates redoCommand();

    IStates addPlayer();

    IStates changePlayerName(int ind, String s);

    IStates restart();

    IStates clear();
}
