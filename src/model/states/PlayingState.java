package model.states;

import command.ICommand;
import model.TicTacToeData;

import java.util.ArrayList;
import java.util.List;

public class PlayingState extends StateAdapter {
    private List<ICommand> historyList;
    private List<ICommand> redoList;

    public PlayingState(TicTacToeData data) {
        super(data);
        historyList = new ArrayList<>();
        redoList = new ArrayList<>();
    }

    @Override
    public IStates invokeCommand(ICommand command) {
        IStates newState;
        if ((newState = command.execute(this)) != null) {
            redoList.clear();
            historyList.add(command);
            return newState;
        }
        return this;
    }

    @Override
    public IStates undoCommand() {
        IStates newState = this;
        if (!historyList.isEmpty()) {
            ICommand cmd = historyList.remove(historyList.size() - 1);
            if ((newState = cmd.undo(this)) != null)
                redoList.add(cmd);
            else
                historyList.clear();
        }
        return newState == null ? this : newState;
    }

    @Override
    public IStates redoCommand() {
        IStates newState = this;
        if (!redoList.isEmpty()) {
            ICommand cmd = redoList.remove(redoList.size() - 1);
            if ((newState = cmd.execute(this)) != null)
                historyList.add(cmd);
            else
                redoList.clear();
        }
        return newState == null ? this : newState;
    }

    @Override
    public IStates restart() {
        if (getData().restartGame())
            return new PlayingState(getData());
        return new BeginningState(getData());
    }

    public int getNoUndoCommands() {
        return historyList.size();
    }

    public int getNoRedoCommands() {
        return redoList.size();
    }

    @Override
    public IStates clear() {
        getData().clearGameData(true);
        return new BeginningState(getData());
    }
}
