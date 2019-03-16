package info;




import java.awt.Point;
import java.util.Observable;

public class Rules extends Observable{
	int totalCats;
	int catsLost = 0;
	boolean gameWon = false;
	boolean gameLost = false;
	Entity[][] board;
	int[][] terrain;
	
	
	public Rules(int[][] terrain, Entity[][] board){
		this.terrain = terrain;
		this.board = board;
	}
	
	public boolean checkOccupied(Entity ent, int x, int y){
		//makes sure the target tile is not occupied by another entity, as well as controlling the interaction between objects
		if(ent instanceof Player) isTrapped(ent); //if the player cannot move, game is over
		if(ent instanceof Player && terrain[x][y] == 2)
			if(board[x][y] instanceof Raft){ 
				//the player has found a raft and embarks
				((Player)ent).embark((Raft)board[x][y]);
				return false;
			}
		if(ent instanceof Player && ((Player)ent).getState().equals("Swimming") && terrain[x][y] != 2 && terrain[x][y] != 3){
				//the player has run into land while on the raft and disembarks
				board[((Player)ent).raft.x][((Player)ent).raft.y] = ((Player)ent).raft;
				System.out.println("next x y " + x + " " + y + "raft " + ((Player)ent).raft.x + " " + ((Player)ent).raft.y);
				((Player)ent).disembark();
		}
			
		if(!canMove(ent,x,y)) //if the terrain prevents movement, cannot move
			return true;
		if(board[x][y] == null || board[x][y].getState().equals("Inactive")) //due to some cleanup time, an entity may still appear on the board when it is inactive, therefore this check is needed
			return false;
		
		if(ent instanceof Player){
			if(board[x][y] instanceof Enemy){
				//the player has hit an enemy. player moves forwards, but a cat is lost
				ent.setHit();
				board[x][y].setHit();
				catsLost++;
				return false;
			}
			if(board[x][y] instanceof Cat){
				//the player cannot move into a cat
				return true;
			}	
		}
		
		else if(ent instanceof Enemy){
			if(board[x][y] instanceof Cat){
				//enemy hits a cat
				System.out.println("taking out " + ent + " " + board[x][y] + "is hit");
				ent.setHit();
				board[x][y].setHit();
				catsLost++;
				return true;
			}
			else if(board[x][y] instanceof Player){
				//enemy hits a player
				ent.setHit();
				board[x][y].setHit();
				catsLost++;
				return true;
			}
			else if(board[x][y] instanceof Raft){
				//enemy cannot travel past raft
				return true;
			}
			else if(board[x][y] instanceof Enemy){
				//enemy cannot collide with another enemy
				return true;
			}
		}
		return false;
		
	}
	
	public boolean canMove(Entity ent, int x, int y){
		//returns whether an entity can move according to its terrain
		String state = ent.getState();
		switch(terrain[x][y]){
			case 0:
				//grass
				return (state.equals("Driving") || state.equals("Swimming")) ? false: true;
			case 1:
				//road
				return (state.equals("Driving") || ent instanceof Player || ent instanceof Cat) ? true: false;
			case 3:
				//cannot move
				return false;
			case 2:
				//water
				return (state.equals("Swimming")) ? true: false;
			case 4:
				//end point, if player walks there the game is over
				if(ent instanceof Player){
					gameWon = true;
				}
			default:
				return true;
		}
	}
	
	private boolean isTrapped(Entity ent){
		//makes sure the player can move, if not the game is over
		if((board[ent.getLocation().x-1][ent.getLocation().y] instanceof Cat || !canMove(ent,ent.getLocation().x-1,ent.getLocation().y)) &&
				(board[ent.getLocation().x+1][ent.getLocation().y] instanceof Cat || !canMove(ent,ent.getLocation().x+1,ent.getLocation().y)) &&
				(board[ent.getLocation().x][ent.getLocation().y-1] instanceof Cat || !canMove(ent,ent.getLocation().x,ent.getLocation().y-1)) &&
				(board[ent.getLocation().x][ent.getLocation().y+1] instanceof Cat || !canMove(ent,ent.getLocation().x,ent.getLocation().y+1))
				){
				gameLost = true;
				return true;
		}
		return false;
	}
	
	public void setOccupied(Entity ent, int x, int y){
		//updates the board of entities
		board[ent.getLocation().x][ent.getLocation().y] = null;
		board[x][y] = ent;
	}

	public void removeFromBoard(Point p) {
		//removes the entity at a certain location in the board
		board[p.x][p.y] = null;
	}
	
	
	
	
}
