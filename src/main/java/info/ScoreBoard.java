package info;

import java.util.Observable;
import java.util.Observer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.text.Text;

public class ScoreBoard implements Observer {
    // scoreboard class for keeping track of player score
    int x;
    int y;
    Text playerScore;
    Text maxScore;
    Game game = Game.getInstance();
    int scale = game.getScale();
    int max = game.maxScore();
    int catsGained;

    public ScoreBoard(int x, int y) {
        this.x = x;
        this.y = y;
        playerScore = new Text(x * scale, y * scale, "Score: " + 0);
        playerScore.setId("scoreboard");
        maxScore = new Text((x - 8) * scale, y * scale, "Max Score: " + max);
        maxScore.setId("scoreboard");
    }

    public void addToPane(ObservableList<Node> sceneGraph) {
        sceneGraph.add(maxScore);

        sceneGraph.add(playerScore);
    }

    public void updateScore() {
        Platform.runLater(
                new Runnable() {

                    @Override
                    public void run() {
                        playerScore.setText("Score: " + (catsGained * 10 - game.catsLost() * 5));
                    }
                });
    }

    public int getScore() {
        return (catsGained * 10 - game.catsLost() * 5);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Player) {
            catsGained = ((Player) o).getCatsGained();
        }
    }
}
