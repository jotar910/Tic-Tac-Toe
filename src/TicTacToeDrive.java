import model.*;
import ui.gui.GUI;

public class TicTacToeDrive {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        TicTacToeData data = game.getData();
        game.addPlayer();
        game.addPlayer();
        game.start(4, 5, 5);
//        new GUI(game);
        //data.setPlayerName("Joao");
        
        /*int w = data.getGrid().getWidth(), h = data.getGrid().getHeight();
        while(true){
            game.imprimeCurPlayer();
            
            Scanner s = new Scanner(System.in);
            System.out.print("x: ");
            int x = s.nextInt();
            System.out.print("y: ");
            int y = s.nextInt();
            
            game.playToken(x, y);
            game.imprimeTab();
            
        }*/
    }
}