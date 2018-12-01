import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;

public class Game {

    //ilość pixeli okienka
    private static final int WIDTH = 657;
    private static final int HEIGH = 680;

    //start gry oraz zczytywanie akcji myszki
    private void run() {

        //tworzymy wszytskie potrzebne komponenty
        final Board board = new Board();
        final Human human = new Human(board);
        final AI ai = new AI(board);
        final GUI gui = new GUI(board);

        //wilekość, nazwa, możliwość rozszerzania i zamykanie
        JFrame frame = new JFrame("Connect Five");
        frame.setSize(WIDTH, HEIGH);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gui.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            //klikanie i faktyczny przebieg rozgrywki
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getY() / Board.SQUARE_SIZE;
                int y = e.getX() / Board.SQUARE_SIZE;
                if (board.getBoard(true)[x][y] >= 0 && board.getWinner() == 0){
                    human.move(x, y);
                    if (board.getWinner() == 0)
                        ai.move();
                    gui.repaint();
                }
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        frame.add(gui); //add popup menu
        frame.setVisible(true);
    }

    public static void main(String[] args){
        new Game().run();
    }
}
