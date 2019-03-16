package code;




import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Explorer extends Application{
	Game game;
	Pane root = new AnchorPane();
	Scene scene = new Scene(root, 400,300);

	/*
	 * (non-Javadoc)
	 * Henry Webster
	 * SE-350
	 * 3 19 2016
	 * final extended project
	 * 
	 * brief directions:
	 * player is controlled by W (up) S (down) A (left) D (right) keys
	 * M key opens a map of explored tiles
	 * 
	 * player must rescue cats by standing in an adjacent tile and finding the red end point
	 * once the end point is reached, the game is over and the player's score is tallied
	 * 
	 * collecting a cat is worth 10 points
	 * losing a cat is worth -5 points
	 * 
	 * player must avoid enemies
	 */
	@Override
	public void start(Stage startStage) throws Exception {
		//GUI start method

		
		scene.getStylesheets().add("style.css"); // stylesheet to make the buttons and text look better
		//start menu
		startStage.setScene(scene);
		startStage.setTitle("Welcome to Cat Rescue!");
		Text title = new Text(100,100,"Cat Rescue");
		title.setId("score");
		
		Button button = new Button("Start");
		button.setLayoutX(100);
		button.setLayoutY(130);
		button.setId("start_button");
		
		button.setOnAction(new EventHandler<ActionEvent>() {
			//event handler for start button
		    @Override public void handle(ActionEvent e) {
		    	startStage.close();
		    	Stage mapStage = new Stage();
				game = Game.getInstance();

				mapStage.setScene(game.getScene());
				mapStage.setTitle("Cat Rescue");
				mapStage.show();
				game.startGame();
				
				
		    }
		});
		
		
		root.getChildren().add(title);
		root.getChildren().add(button);
		startStage.show();

	}

	public static void main(String[] args){
		launch(args);
	}

}
