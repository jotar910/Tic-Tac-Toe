package model.states;

import model.TicTacToeData;

public class BeginningState extends StateAdapter {

    public BeginningState(TicTacToeData data) {
        super(data);
    }

    @Override
    public IStates start(int seqNum, int gridW, int gridH) {
        TicTacToeData data = getData();
        if (data.getPlayers().size() > 1 && seqNum <= Math.max(gridH, gridW)) {
            try {
                data.configNewGame(seqNum, gridW, gridH);
                return new PlayingState(data);
            } catch (Exception e) {
                return this;
            }
        }
        return this;
    }

    @Override
    public IStates addPlayer() {
        TicTacToeData data = getData();
        data.addNewPlayer();
        return this;
    }

    @Override
    public IStates changePlayerName(int ind, String s) {
        TicTacToeData data = getData();
        try {
            if (s != null && data.isPlayerNameAvailable(s))
                data.setPlayerName(data.getPlayer(ind), s);
        } catch (IndexOutOfBoundsException e) {
        }
        return this;
    }

    @Override
    public IStates clear() {
        getData().clearGameData(true);
        return this;
    }

}
