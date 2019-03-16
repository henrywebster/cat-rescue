package info;





public class EnemyFactory extends EntityFactory{
	//abstract factory class for enemy class
	
		Entity createEntity(int x, int y, String type){

		Entity npc = null;
		if(type.equals("guard"))
				npc = new Guard(x,y,type);
		else if(type.equals("car"))
				npc = new Car(x,y,type);
		else if(type.equals("shark"))
			npc = new Shark(x,y,type);
		
		return npc;
	}

	
}
