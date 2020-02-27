package ui.text;

import model.*;
import model.states.*;

import java.util.List;
import java.util.Scanner;

public class TextUI {
    private TicTacToe game;
    private boolean quit;

    public TextUI(TicTacToe game) {
        this.game = game;
        this.quit = false;
        go();
    }

    public void go() {
        while (!quit) {
            IStates state = game.getState();
            for (String s : game.getMsglog())
                System.out.println("> " + s);
            game.clearMsglog();

            if (state instanceof BeginningState)
                uiBeginningState();
            else if (state instanceof PlayingState)
                uiPlayingState();
            else
                quit = true;
        }
    }

    public void uiBeginningState() {
        Scanner sc = new Scanner(System.in);
        char ch;

        do {
            System.out.println("\n0 - Quit\n");
            System.out.println("1 - Start");
            System.out.println("2 - Add new player");
            System.out.println("3 - Change player name");
            System.out.println();
            try {
                ch = sc.nextLine().toCharArray()[0];
            } catch (Exception e) {
                ch = ' ';
            }
        } while (ch < '0' || ch > '3');
        switch (ch) {
            case '0':
                quit = true;
                return;
            case '1':
                System.out.print("Grid size (width height): ");
                int gridW = sc.nextInt();
                int gridH = sc.nextInt();
                System.out.print("Line sequence: ");
                int seq = sc.nextInt();
                game.start(seq, gridW, gridH);
                return;
            case '2':
                game.addPlayer();
                return;
            case '3':
                List<Player> players = game.getPlayers();
                if (players.size() == 0) {
                    System.out.println("No players available!");
                    return;
                }
                try {
                    int ind = 0;

                    System.out.println();
                    for (Player p : game.getPlayers())
                        System.out.println(ind++ + " > " + p);

                    System.out.print("Choose player: ");
                    ind = sc.nextInt();
                    String name;
                    System.out.print("Name: ");
                    do {
                        name = sc.nextLine();
                    } while (name.length() < 1);
                    game.changePlayerName(ind, name);

                } catch (Exception e) {
                }
                return;

        }
    }

    public void uiPlayingState() {
        Scanner sc = new Scanner(System.in);
        char ch;

        game.imprimeCurPlayer();
        do {
            System.out.println("\n0 - Quit\n");
            System.out.println("1 - Play Token");
            System.out.println("2 - Undo play");
            System.out.println("3 - Redo play");
            System.out.println();
            try {
                ch = sc.nextLine().toCharArray()[0];
            } catch (Exception e) {
                ch = ' ';
            }
        } while (ch < '0' || ch > '3');

        switch (ch) {
            case '0':
                quit = true;
                return;
            case '1':
                try {
                    System.out.print("Coordenates (x y): ");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    game.playToken(x, y);
                    game.imprimeTab();

                } catch (Exception e) {
                }
                return;
            case '2':
                game.undoPlayToken();
                game.imprimeTab();
                return;
            case '3':
                game.redoPlayToken();
                game.imprimeTab();
                return;
        }
    }
}