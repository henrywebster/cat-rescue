package info;




import java.awt.image.BufferedImage;
import java.awt.Point;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Game {
	
	/*
	 * Game is the facade class that all objects will interact with each other in
	 */
	
	private static Game instance;
	Rules rules;
	Map map;

	boolean gameRunning = true;
	
	ArrayList<Entity> entityList = new ArrayList<Entity>();
	Player player;
	
	int offsetX = 0;
	int offsetY = 0;
	int scale;
	int maxCats = 0;
	
	Image mapPopUp;
	ImageView mapImage;
	boolean mapUp = false;
	
	boolean cadence = false;
	ScoreBoard scoreBoard;
	
	Pane root = new AnchorPane();
	Scene scene = new Scene(root, 625,625);

	public static Game getInstance() {
		//singleton pattern
		if(instance == null)
			instance = new Game();
		return instance;
	}
	
	private Game(){

	}
	
	public int getScale(){
		return map.getScale();
	}
	
	public void gameOver(){
		//signals the end of the game, tells the score if you completed the game
		
		Pane overPane = new AnchorPane();
		Stage overStage = new Stage();
		Scene overScene = new Scene(overPane, 400,200);
		String endMessage = (rules.gameWon) ? "You've won cat rescue with a score of " + scoreBoard.getScore():"You've lost cat rescue";
		Text title = new Text(20,20,endMessage);
		overStage.setScene(overScene);
		
		overPane.getChildren().add(title);
		
		Button button = new Button("Exit");
		button.setLayoutX(25);
		button.setLayoutY(50);
		
		overPane.getChildren().add(button);
		overStage.show();
		
		button.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	Platform.exit();
	
		    }
		});
	}
	
	public void removeFromBoard(Point p){
		//takes an object off of the board controlling the entities
		rules.removeFromBoard(p);
	}
	
	public boolean canMove(Entity ent, int x, int y){
		//for obejcts that need to see if they can move without actually moving
		return rules.canMove(ent, x, y);
	}
	
	public void startGame(){
		//initializing game elements
		scene.getStylesheets().add("style.css");
		map = new Map();
		scale = map.getScale();
		map.processMapFile();
		rules = new Rules(map.getTerrain(),map.getBoard());
		entityList = new ArrayList<Entity>();
		entityList = map.getEntities();
		

		player = new Player(map.playerStart.x,map.playerStart.y);
		player.addToPane(root.getChildren());
		
		offsetX = (int)(player.getLocation().x - ((scene.getWidth()/scale)/2)) + 1;
		offsetY = (int)(player.getLocation().y - ((scene.getHeight()/scale)/2)) + 1;
		
		for(Node n: root.getChildren()){
			n.setTranslateX(-1 * offsetX * scale);
			n.setTranslateY(-1 * offsetY * scale);
			}
		
		scoreBoard = new ScoreBoard(20,1);
		scoreBoard.addToPane(root.getChildren());
		player.addObserver(scoreBoard);
	
		
		/*
		 *  attaches the entities that need to observe the player to the player
		 */
		for(Entity e: entityList){
			if(e instanceof Cat){
				player.addObserver((Cat)e);
			}
			else if(e instanceof Raft){
				player.addObserver((Raft)e);
			}
			else if(e instanceof Shark){
				player.addObserver((Shark)e);
			}
		}
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			//keyboard control
			public void handle(KeyEvent ke){
				Point initial = player.getLocation();
				switch(ke.getCode()){
				case M:
					//this key controls the interactive map
					if(mapUp){
						//hides map if the map is already up
						mapImage.setX(-1);
						mapImage.setY(-1);
						root.getChildren().remove(mapImage);
						mapUp = false;
					}
					else{
						//displays map
						mapUp = true;
						mapPopUp = SwingFXUtils.toFXImage((BufferedImage) map.mapExplored(),null);
						mapImage = new ImageView(mapPopUp);
						mapImage.setX((5+offsetX)*scale);		
						mapImage.setY((4+offsetY)*scale);
						mapImage.setFitHeight(400);
						mapImage.setFitWidth(400);
						root.getChildren().add(mapImage);
					}
					break;
				default:
					player.move(ke);
					map.setExplored(player.getLocation().x, player.getLocation().y);
					if(mapUp){
						//hides map if the player moves while the map is up
						mapImage.setX(-1);
						mapImage.setY(-1);
						root.getChildren().remove(mapImage);
						mapUp = false;
					}
					break;
				}
				
				//calculates which way the map should move according to how the player is moving, then moves the map that way
				offsetX = (initial.x == player.getLocation().x) ? offsetX:offsetX+(player.getLocation().x - initial.x);
				offsetY = (initial.y == player.getLocation().y) ? offsetY:offsetY+(player.getLocation().y - initial.y);
				
				for(Node n: root.getChildren()){
					if(!(n instanceof Text)){
						//according to the offset, move the nodes so that the map moves with the player
					n.setTranslateX(-1 * offsetX * scale);
					n.setTranslateY(-1 * offsetY * scale);
					}
				}
			
				
				
			}
		});

		gameRunner();
		return;
	}
	
	
	
	public void gameRunner(){
		//this is the game loop that tells the entities to move
		
		
		new AnimationTimer()
	    {
			private long lastUpdate = 0;
	        public void handle(long currentNanoTime)
	        {
                if (System.nanoTime() - lastUpdate >= 300000000) {
    	        	double t = (System.nanoTime() - lastUpdate);
    	        	update(t);
                    lastUpdate = currentNanoTime;
                }
                if(!gameRunning){
    	        	stop();
    	        	return;
                }
	        }
	        
	    }.start();
	}

	
	public Scene getScene(){
		//returns scene for display on the stage
		return scene;
	}
	
	public Pane getPane(){
		//returns pane so factories can add to scene graph
		return root;
	}
	
	public int maxScore(){
		//returns the amx score for the scoreboard
		return map.maxCats*10;
	}
	
	public int catsLost(){
		//returns the
		return rules.catsLost;
	}
	
	public boolean movement(Entity ent, int x, int y){
		//returns whether an entity can move to the target coordinate
		//has side effects, if the target coordinate is 'hostile', the entities are hit
		if(!rules.checkOccupied(ent, x, y)){
			rules.setOccupied(ent, x, y);
			return true;
		}
		return false;
	}
	
	public void update(double t){
		//updates all the GUI elements and positions in the internal grid
		if(rules.gameWon || rules.gameLost){
			gameRunning = false;
			gameOver();
		}

		ArrayList<Entity> toRemove = new ArrayList<Entity>();
		for(Entity e: entityList){
			if(e.getState().equals("Inactive")){
				toRemove.add(e);
			}
			else if((e instanceof Guard) && cadence){
				((Enemy)e).move();
			}
			else if(e instanceof Car || e instanceof Shark){
				((Enemy)e).move();
			}
			scoreBoard.updateScore();
			
		}

		cadence = !cadence; //the cadence creates 2 speeds. some enemies move every update, some only move every other
		entityList.removeAll(toRemove); //removes all the entities that are no longer active on the map
	}	
}
