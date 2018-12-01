
public class Player {
    public static final int PLAYER1 = -1;  //Human
    public static final int PLAYER2 = -2; //AI

    private static final int WINNING_POINT = 5;
    private static final int NUM_DIR = 8;
    private static final int BASE = 10;
    private static final int[][] DIRECTION = { {-1, 1, 0, 0, 1, -1, -1, 1}, {-1, 1, -1, 1, -1, 1, 0, 0}}; // pary dostępnych możliwości {x}{y}, [0][x], [1][y]

    //ruch gracza na podane koordynaty
    protected static void move(Board game, int player, int x, int y){
        int[][] board = game.getBoard(player == PLAYER1); //wczytanie do board odpowiedniej planszy
        game.move(player, x, y); //wpisanie w obie plansze wartości danego gracza w odpowiednie koordynaty


        for (int i = 0; i < NUM_DIR; i++){
            int xx = x + DIRECTION[0][i];
            int yy = y + DIRECTION[1][i];

            //ustawienie parametrów xx i yy o jeden za linię
            while (Board.isValid(xx, yy) && board[xx][yy] == player){
                xx += DIRECTION[0][i];
                yy += DIRECTION[1][i];
            }

            //ustawianie zwycięzcy jeśli jest 5 pod rząd, powrót na ostatnią pozycję w linii, odwrócenie kierunku przez zmiane indeksu
            if (i%2 == 0) {
                if (countRow(board, player, xx - DIRECTION[0][i], yy - DIRECTION[1][i], i + 1) == WINNING_POINT)
                    game.setWinner(player);
            } else {
                if (countRow(board, player, xx - DIRECTION[0][i], yy - DIRECTION[1][i], i - 1) == WINNING_POINT)
                    game.setWinner(player);
            }

            if(Board.isValid(xx, yy) && board[xx][yy] >= 0)  //ustawianie liczby na danym pustym polu jeśli nie znajduje sie w nim żaden gracz
                game.setValue(player, xx, yy, getValue(board, player, xx, yy));
        }
    }

    //zliczanie długości linii
    private static int countRow(int[][] board, int player, int x, int y, int index){
        int row = 0;
        while (Board.isValid(x, y) && board[x][y] == player) {
            row++;
            x += DIRECTION[0][index];
            y += DIRECTION[1][index];
        }
        return row;
    }

    //wartość heurystyczna danego pola, wartość pustego pola to BASE podniesiona do ilości zajętych pól przez danego gracza w obu kierunkach
    private static int getValue(int [][] board, int player, int x, int y){
        int totalValue = 0;
        for (int i = 0; i < NUM_DIR; i+=2){
            int xx = x + DIRECTION[0][i];
            int yy = y + DIRECTION[1][i];
            int row1 = countRow(board, player, xx, yy, i);

            xx = x + DIRECTION[0][i+1];
            yy = y + DIRECTION[1][i+1];
            int row2 = countRow(board, player, xx, yy, i + 1);

            if (row1 + row2 > 0)
                totalValue += Math.pow(BASE, row1 + row2);
        }
        return totalValue;
    }


}
