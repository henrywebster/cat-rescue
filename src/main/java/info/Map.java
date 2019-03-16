package info;




import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;

public class Map {
	/*
	 * This class draws the map and is responsible with
	 * keeping track of all the entities/where they are
	 */
	
	/*
	 * keeps track of the dimensions of the map.
	 */
	private int dimensionX;
	private int dimensionY;
	private final int scale = 25; //define the scaling factor here
	int maxCats = 0;
	
	String[] colors = {"black","blue","cyan","green","magenta","red","white","yellow"};
	
	Game game = Game.getInstance();

	
	/* terrain code:
	 * 0: accessible land
	 * 1: accessible road
	 * 2: accessible water
	 * 3: unaccessible
	 * 4: end point
	 */
	
	private int terrain[][]; //codes the type of tile and what can move there
	private boolean explored[][]; //keeps track of the visited tile for the map
	private Entity board[][]; //knows the positions of all the entities in the game, this will get returned to the RULES class
	
	ArrayList<Entity> entityList = new ArrayList<Entity>();
	EntityFactory enemyFactory = new EnemyFactory();
	EntityFactory catFactory = new CatFactory();
	Point playerStart;
	
	public int getScale(){
		//returns the scale
		return scale;
	}
	
	public int getDimensionX(){
		//returns x dimension of map
		return dimensionX;
	}
	
	public int getDimensionY(){
		//returns y dimesnion of map
		return dimensionY;
	}
	
	public Entity[][] getBoard(){
		//returns the grid of entities
		return board;
	}
	
	public int[][] getTerrain(){
		//returns the grid of terrain values
		return terrain;
	}
	
	public ArrayList<Entity> getEntities(){
		//returns the list of entities that have been produced on the map to begin with
		return entityList;
	}

	public ObservableList<Node> processMapFile(){
		//reads a 24-bit bitmap file and creates a JavaFX representation to be added to the scene
		ObservableList<Node> sceneGraph = game.root.getChildren();

        URL url = getClass().getClassLoader().getResource("map.bmp");
		//URL url = Map.class.getResource("images" + File.separator + "map.bmp");
        try{
			BufferedImage img = ImageIO.read(url);
			dimensionY = img.getHeight();
			dimensionX = img.getWidth();
			
			int rgb;

			terrain = new int[dimensionX][dimensionY];
			explored = new boolean[dimensionX][dimensionY];
			board = new Entity[dimensionX][dimensionY];
			
			for(int i = 0;i < dimensionY;i++){
				for(int j = 0;j < dimensionX;j++){
					rgb = img.getRGB(i, j);
					Rectangle rect = new Rectangle(i*scale,j*scale,scale,scale);

					switch(rgb & 0x00FFFFFF){
						case(0x0000FF00):
							//ground
							rect.setFill(Color.LIMEGREEN);
							break;
						case(0x000000FF):
							//water
							rect.setFill(Color.PALETURQUOISE);
							terrain[i][j] = 2;
							break;
						case(0x00FFFF00):
							//beach
							rect.setFill(Color.BURLYWOOD);
							break;
						case(0x00888888):
							//road
							rect.setFill(Color.GRAY);
							terrain[i][j] = 1;
							break;
						case(0x00FF00FF):
							//trees
							rect.setFill(Color.GREEN);
							terrain[i][j] = 3;
							break;
						case(0x00FF0000):
							//end point
							rect.setFill(Color.RED);
							terrain[i][j] = 4;
							break;
						case(0x0000FFFF):
							//rocks
							rect.setFill(Color.NAVY);
							terrain[i][j] = 3;
							break;
						case(0x000000a0):
							//player
							rect.setFill(Color.BURLYWOOD);
							playerStart = new Point(i,j);
							break;
						case(0x000000a1):
							//cat
							terrain[i][j] = 3;
							Random ran = new Random();
							int ranColor = ran.nextInt(8);
							Entity cat = catFactory.makeEntity(i, j, colors[ranColor]);
							entityList.add(cat);
							board[i][j] = cat;
							maxCats++;
							rect.setFill(Color.GREEN);
							break;
						case(0x000000a2):
							//guard
							Entity guard = enemyFactory.makeEntity(i, j, "guard");
							entityList.add(guard);
							board[i][j] = guard;
							rect.setFill(Color.LIMEGREEN);
							break;
						case(0x000000a3):
							//shark
							terrain[i][j] = 3;
							Entity shark = enemyFactory.makeEntity(i, j, "shark");
							entityList.add(shark);
							rect.setFill(Color.PALETURQUOISE);
							break;
						case(0x000000a4):
							//car
							terrain[i][j] = 1;
							Entity car = enemyFactory.makeEntity(i, j, "car");
							entityList.add(car);
							board[i][j] = car;
							rect.setFill(Color.GRAY);
							break;
						case(0x000000a5):
							//boat
							terrain[i][j] = 1;
							Entity boat = enemyFactory.makeEntity(i,j, "boat");
							entityList.add(boat);
							board[i][j] = boat;
							rect.setFill(Color.PALETURQUOISE);
							break;
						case(0x000000a6):
							//raft
							Entity raft = new Raft(i,j);
							raft.addToPane(sceneGraph);
							board[i][j] = raft;
							entityList.add(raft);
							System.out.println("adding new raft");
							rect.setFill(Color.PALETURQUOISE);
							terrain[i][j] = 2;
							break;
					}	
					sceneGraph.add(rect);
					rect.toBack();
				}
			}
		} catch(IOException e) {
			System.out.println("Map processing error");
			e.printStackTrace();
		}
		return sceneGraph;
	}
	
	public void setExplored(int x, int y){
		//updates the grid of currently found tiles for the interactive map
		explored[x][y] = true;
		explored[x+1][y] = true;
		explored[x-1][y] = true;
		explored[x][y+1] = true;
		explored[x][y-1] = true;
		explored[x+1][y+1] = true;
		explored[x-1][y+1] = true;
		explored[x+1][y-1] = true;
		explored[x-1][y-1] = true;
	}
	
	public Image mapExplored(){
		//draws the explored map from where the player has visited.
		BufferedImage img = new BufferedImage(dimensionX,dimensionY,BufferedImage.TYPE_INT_RGB);
		for(int i = 0;i < dimensionX; i++){
			for(int j = 0;j < dimensionY; j++){
				if(explored[i][j] == true){
					switch(terrain[i][j]){
					case 0:
						img.setRGB(i, j, 0x0000FF00);
						break;
					case 1:
						img.setRGB(i, j, 0x00888888);
						break;
					case 2:
						img.setRGB(i, j, 0x000000FF);
						break;
					case 4:
						img.setRGB(i, j, 0x00FFFF00);
						break;
					default:
						img.setRGB(i, j, 0x00000000);
					}
				}

			}
		}
		return img;
	}
}
