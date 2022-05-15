import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //create new game
        Game game = new Game(20, 20);
        game.populateBoard(1);
        JFrame parent = new JFrame();
        parent.add(game);
        parent.setVisible(true);
        parent.pack();
    }
}
