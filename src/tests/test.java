package tests;

import org.junit.jupiter.api.Test;

import org.junit.Assert;
import main.Game;
import main.Handler;
import main.entities.creatures.Player;
import main.maps.Map;
import main.utilities.Utilities.Teams;

class test {
	

	@Test
	void testSolidTiles() {

		Handler handler = new Handler(new Game("Game", 1280, 720));
		Map map = new Map(handler, "resources/map/basicMap.txt");
		handler.setMap(map);
		Player player = new Player(handler, null, 0.0f, 0.0f, "Test", Teams.NONE);
		
		Assert.assertTrue(player.checkEntityCollisions(0, 0));
	}

}
