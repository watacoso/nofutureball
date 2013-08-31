package nofutureball;

public class Panel extends GameObject {

	public Panel(Room room, float x,String type) {
		super(room, x, -40, 48, 70, false);
		setCollision(false);
		setAnimation("PANEL","TEMP");
		// TODO Auto-generated constructor stub
	}
	
	public void update(Game game){
		super.update(game);
		
		for(int i=0;i<game.players.size();i++){
			Player p=(Player) game.players.get(i);
			if(getDistance(p)<30 && p.room==room){
				System.out.println("TEST");
			}
		}
	}
}
