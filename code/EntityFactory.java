package code;




import javafx.collections.ObservableList;
import javafx.scene.Node;

public abstract class EntityFactory{
	
	Game game = Game.getInstance();
	ObservableList<Node> sceneGraph =  game.getPane().getChildren();
	
	public Entity makeEntity(int x, int y, String type){
		//abstract factory design pattern
		
		Entity entity;
			
		entity = createEntity(x,y,type);
		entity.addToPane(sceneGraph);
		return entity;
	}
	
	abstract Entity createEntity(int x, int y, String type);
}
