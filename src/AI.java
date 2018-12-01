import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class AI extends Player {

    private static final int BRANCHING_FACTOR = 5;  //ile najlepszych ruchów w danej chwili sprawdzamy
    private static final int LOOK_AHEAD = 7;        //ile poziomów w drzewie przeszukujemy

    private Board game;
    public AI(Board game){
        this.game = game;
    }

    //klasa wewnętrzna służąca do zapisywania wartości heurestyki i porównywania ich
    private class Point3D extends Point implements Comparable<Point3D> {
        private int z;
        private Point3D (int x, int y, int z){
            super(x, y);
            this.z = z;
        }

        @Override
        public int compareTo(Point3D pt){  //zwraca liczbę ujemną, zero lub dodatnią, w zależności czy wartość byłą mniejsza, równa bądź większa
            return pt.z - z;
        }
    }

    //lista dostępnych punktów posortowana wedłóg wartości
    private ArrayList<Point> getPossibleMoves (Board game){
        ArrayList<Point3D> moves = new ArrayList<>();
        int [][] human = game.getBoard(true);
        int[][] ai = game.getBoard(false);

         //dodawanie do listy tablic punktów dostępnych punktów
        for (int i = 0; i < Board.SIZE; i++){
            for (int j = 0; j < Board.SIZE; j++){
                moves.add(new Point3D(i, j, human[i][j] + ai[i][j]));
            }
        }
        //sortujemy po z liste
        Collections.sort(moves);
        //zwracamy nową listę punktów o dwóch współżędnych
        return new ArrayList<Point>(moves);
    }

    //minimax z algorytmem obcinającym niewpływające na rozgrywkę gałęzie alfa-beta
    //alfa - wartość maksymalna jaką gracz maksymilizujący może mieć zagwarantowaną w danym momencie
    //beta  - analogicznie minimalna

    private int minimax(Board game, Point move, int depth, int alpha, int beta, boolean max){
        //jeżeli depth jest 0
        if (depth == 0)
            return game.getBoardValue();
        //zwiększanie maksymalnie gdy ktoś może wygrać
        else if (game.getWinner() == -1)
            return -depth * game.getBoardValue(); //gdy wygrywa player1 (human) to value będzie ujemne więc return dodatnie
        else if (game.getWinner() == -2)
            return depth * game.getBoardValue(); //gdy wygrywa player2 (ai) to value będzie dodatnie więc return dodatnie

        Board copy = game.clone();
        super.move(copy, max ? Player.PLAYER2 : Player.PLAYER1,  move.x, move.y);  //symulacja ruchu danego gracza
        ArrayList<Point> moves = getPossibleMoves(game);
        if (max){
            int best = Integer.MIN_VALUE;       //ustalenie początkowej wartości best na najmniejszą możliwą liczbę (2^31 -1)
            for (int i = 0; i < Math.min(moves.size(), BRANCHING_FACTOR); i++){
                best = Math.max(best, minimax(copy, moves.get(i), depth - 1, alpha, beta, false));  //max z dotychczasowego best lub minimax z niższego poziomu
                alpha = Math.max(best, alpha);  //ustalenie nowego alpha jeżeli best przekroczył dotychczasowe alpha
                if (beta <= alpha)
                    break;
            }
            return best;
        }else{
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < Math.min(moves.size(), BRANCHING_FACTOR); i++){
                best = Math.min(best, minimax(copy, moves.get(i), depth - 1, alpha, beta, true));
                beta = Math.min(best, beta);
                if (beta <= alpha)
                    break;
            }
            return best;
        }

    }

    //move ai, wywołanie minimax dla najlepszych kandydatów
    public void move(){
        ArrayList<Point> moves = getPossibleMoves(game);
        int max = Integer.MIN_VALUE;
        Point bestMove = null;
        for (int i = 0; i < Math.min(moves.size(), BRANCHING_FACTOR); i++){
            int value = minimax(game, moves.get(i), LOOK_AHEAD, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            System.out.println(moves.get(i) + " " + value);
            if (value > max){
                max = value;
                bestMove = moves.get(i);
            }
        }
        super.move(game, Player.PLAYER2, bestMove.x, bestMove.y);
    }

}