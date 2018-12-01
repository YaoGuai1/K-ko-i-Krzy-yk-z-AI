import java.util.Arrays;


public class Board {

    public static final int SIZE = 50; //plansza SIZE * SIZE
    public static final int SQUARE_SIZE = 13; //wielkość jednej komórki planszy
    private int [][] ver1, ver2; //przechowywują dwie wersje planszy
    private int winner;

    public Board() {
        ver1 = new int[SIZE][SIZE];
        ver2 = new int[SIZE][SIZE];
        winner = 0;
    }


    //sprawdzenie która wersja planszy
    public int[][] getBoard(boolean isVer1){
        return isVer1 ? ver1 : ver2;
    }

    //skopiowanie planszy
    @Override
    public Board clone(){
            Board copy = new Board();
            for (int i = 0; i<SIZE; i++){
                copy.getBoard(true)[i] = Arrays.copyOf(getBoard(true)[i],SIZE); //kopjujemy po kolei tablicami
                copy.getBoard(false)[i] = Arrays.copyOf(getBoard(false)[i],SIZE);
            }
            copy.setWinner(winner);
            return copy;
    }

    //pobieranie zwycięzcy
    public int getWinner() {
        return winner;
    }

    //ustawianie zwycięzcy
    public void setWinner(int player){
        winner = player;
    }

    //zliczanie wartości planszy
    public int getBoardValue(){
        int value = 0;
        for (int i = 0; i < SIZE; i++){
            for (int j = 0; j < SIZE; j++){
                if (ver1[i][j] >= 0){
                    value = value + ver2[i][j] - ver1[i][j];
                }
            }
        }
        return value;
    }

    //sprawdzanie czy koordynaty znajdują się w zakresie planszy
    public static boolean isValid(int x, int y){
        return x>=0 && y>=0 && x < SIZE && y < SIZE;
    }


    //ruch
    public void move(int player, int x, int y){
        ver1[x][y] = player;
        ver2[x][y] = player;
    }

    //wpisywanie wartosci w pole
    public void setValue (int player, int x, int y, int value){
        if(player == Player.PLAYER1)
            ver1[x][y] = value;
        else if(player == Player.PLAYER2)
            ver2[x][y] = value;
    }

    //reprezentacja gry w postaci string
    /*@Override
    public String toString() {
        String print = " ";
        for (int i = 0; i < SIZE; i++)      //numerowanie kolumn
            print += String.format("%d", i);
        print += "\n";

        for (int i = 0; i < SIZE; i++){
            print += String.format("%d", i); //munerowanie wierszy
            for (int j = 0; j < SIZE; j++)
                print += String.format("%3s", ver1[i][j] >= 0 ? "-" : ver1[i][j] == Player.PLAYER1 ? "X" : "O");
            print += "\n";
        }
        return print;
    }*/


}
