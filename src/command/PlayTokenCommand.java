package command;

import model.Grid;
import model.TicTacToeData;
import model.exceptions.InvalidTokenPositionException;
import model.states.BeginningState;
import model.states.IStates;
import ui.gui.CONST;

public class PlayTokenCommand implements ICommand {
    private TicTacToeData data;
    private int col, lin;

    public PlayTokenCommand(TicTacToeData data, int col, int lin) {
        this.data = data;
        this.lin = lin;
        this.col = col;
    }

    @Override
    public IStates execute(IStates curState) {
        try {
            if (!data.getGrid().isPosEmpty(lin, col))
                return null;
            data.addToken(lin, col);
            Grid curGrid = data.getGrid();
            CONST.GAMEOVER gameoverState = data.isGameOver();
            if (gameoverState != CONST.GAMEOVER.NO) {
                curGrid.setFinished(true);
                curGrid.setWinner(gameoverState);
                data.addMsglog("Grid finished!");
            }

            if (!data.nextPlayerPlaying()) {
                data.addMsglog("Game over! No more grids available.");
                data.clearGameData(false);
                return new BeginningState(curState.getData());
            }
        } catch (InvalidTokenPositionException e) {
            return null;
        }
        return curState;
    }

    @Override
    public IStates undo(IStates curState) {
        try {
            if (data.getGrid().isPosEmpty(lin, col))
                return null;
            data.remToken(lin, col);

            data.prevPlayerPlaying();

            Grid curGrid = data.getGrid();
            if (curGrid.isFinished())
                curGrid.setFinished(false);

        } catch (InvalidTokenPositionException e) {
            return null;
        }
        return curState;
    }

}
