package command;

import model.states.IStates;

public interface ICommand {
    IStates execute(IStates curState);

    IStates undo(IStates curState);
}