import model.TicTacToeCape;
import ui.gui.GUI;

public class Main {
    public static void main(String[] args) {
        //new TextUI(new TicTacToe());
        TicTacToeCape game = new TicTacToeCape();
        /*game.addPlayer();
        game.addPlayer();
        game.start(3, 3, 3);*/
        new GUI(game);
    }
}