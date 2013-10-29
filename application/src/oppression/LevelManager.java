package oppression;

public class LevelManager {

	public LevelGen levelGen;
	
	private  int level;
	private Container entities;
	private Container players;
	private int nPlayers=1;
	public static int nEnemies;
	private int max_enemies=30;
	private int timer=0;
	
	public Camera cam;
	
	public LevelManager(Game game){
		this.entities=game.entities;
		players=new Container();
		levelGen=new LevelGen(game.entities,game.mapContainer);
		cam=new Camera(game.gameContainer);
		
	}
	
	public void setNumPlayers(int nPlayers){
		players.list.clear();
		if(nPlayers>=1){
			Player p = new Sharpshooter(levelGen.getStartRoom(),300, 300, KeySet.ONE);
			players.add(p);
		}
		if(nPlayers>=2){
			Player p = new Sharpshooter(levelGen.getStartRoom(),300, 600, KeySet.TWO);
			players.add(p);
		}
		//if(nPlayers>=3){
		//	Player p = new Sharpshooter(levelGen.getRandomRoom(),600, 300, KeySet.THREE);
		//	players.add(p);
		//}
		//if(nPlayers==4){
		//	Player p = new Sharpshooter(levelGen.getRandomRoom(),600, 600, KeySet.FOUR);
		//	players.add(p);
		//}
		
		this.nPlayers=nPlayers;
	}
	
	public void newGame(){
		
	}
	
	public void nextLevel(){
		
	}
	
	public void initLevel(){
		levelGen.generateMap(level);
		setNumPlayers(nPlayers);
		for (int i=0;i<players.size();i++) {
			entities.add(players.get(i));
			cam.addTarget(players.get(i));
		}
		
		for(int i=0;i<0;i++){
			Enemy e = new Enemy(levelGen.getRandomRoom(), 256, 256,(Player) players.get(0));
			entities.add(e);
		}
	}
	
	public void pause(boolean value){
		
	}
	

	
	public void update(){
		timer++;
		if(timer>10){
			timer=0;
			for(int i=0;i<10;i++){
				//System.out.println(nEnemies);
				if(nEnemies<max_enemies){
				Enemy e = new Enemy(levelGen.getRandomRoom(), 256, 256,(Player) players.get(0));
				entities.add(e);
				}
			}
			
		}
		cam.update();
	}
	
}
