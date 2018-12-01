import javax.swing.JComponent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GUI  extends JComponent{

    private Board game;

    //konstruktor
    public GUI(Board game){
        this.game = game;
    }

    //tworzenie gui gry

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white); //ustawienie używanego właśnie koloru
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (game.getBoard(true)[i][j] == Player.PLAYER1)
                    g2.setColor(Color.red);
                else if (game.getBoard(false)[i][j] == Player.PLAYER2)
                    g2.setColor(Color.blue);
                //wypełnienie aktualnie używanym kolorem
                g2.fillRect(j * Board.SQUARE_SIZE, i * Board.SQUARE_SIZE, Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setColor(Color.white);
            }
        }

        //siatka
        g2.setColor(Color.black);
        for (int i = 0; i <= Board.SIZE; i++){
            //od punktu (x1,y1) do (x2, y2)
            g2.drawLine(0, i * Board.SQUARE_SIZE, Board.SIZE * Board.SQUARE_SIZE, i * Board.SQUARE_SIZE);
            g2.drawLine(i * Board.SQUARE_SIZE, 0, i * Board.SQUARE_SIZE, Board.SQUARE_SIZE * Board.SIZE);
        }
    }
}
