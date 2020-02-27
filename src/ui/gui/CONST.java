package ui.gui;

public interface CONST {
    int WND_WIDTH = 960;
    int WND_HEIGHT = 760;
    int WND_X0 = 20;
    int WND_Y0 = 10;

    int WND_START_WIDTH = 550;
    int WND_START_HEIGHT = 130;

    String TICTACTOE_IMAGE = "tic-tac-toe.jpg";
    String[] PLAYER_ICONS = {"0.jpg", "1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg", "9.jpg", "10.jpg", "11.jpg"};

    enum GAMEOVER {NO, YES, DRAW};
}
