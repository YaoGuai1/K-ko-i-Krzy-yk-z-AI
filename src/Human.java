public class Human extends Player {
    private Board game;
    public Human (Board game){
        this.game = game;
    }

    //ruch gracza (człowieka) w zależności gdzie kliknie/co wpisze
    public void move(int x, int y){
        super.move(game, Player.PLAYER1, x, y); //super wywołuje odpowiednik metody o tej samej nazwie i podanych parametrach
    }
}
