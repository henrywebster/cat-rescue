package info;




public class CatFactory extends EntityFactory{
	//abstract factory class for cats
	@Override
	Entity createEntity(int x, int y, String type){

		Entity e = new Cat(x,y,type);

		return e;
		
	}
	
}
