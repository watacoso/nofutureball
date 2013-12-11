package json;

public class GameSettings {
	public int nPlayers,
			   nCompounds,
			   maxNEnemies,
			   minSpawnRate,
			   maxSpawnRate,
			   spawnRateIncrementer,
			   incrementRate;
	
	public GameSettings(int nPlayers, int nCompounds, int maxNEnemies, int minSpawnRate, int maxSpawnRate, int spawnRateIncrementer, int incrementRate){
		this.nPlayers=nPlayers;
		this.nCompounds=nCompounds;
		this.maxNEnemies=maxNEnemies;
		this.minSpawnRate=minSpawnRate;
		this.maxSpawnRate=maxSpawnRate;
		this.spawnRateIncrementer=spawnRateIncrementer;
		this.incrementRate=incrementRate;
	}
}
