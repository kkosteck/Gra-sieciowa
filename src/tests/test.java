package tests;

import java.awt.Graphics;
import java.awt.Rectangle;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import main.Game;
import main.Handler;
import main.entities.creatures.Creature;
import main.maps.Map;

class test {

	// entity without texture for collision testing
	class TestEntity extends Creature {

		public TestEntity(Handler handler, float x, float y, int width, int height) {
			super(handler, x, y, width, height);
		}

		@Override
		public void tick() {
		}

		@Override
		public void render(Graphics g) {
		}
	}

	@Test
	void testEntityInEntityCollision() {
		Handler handler = new Handler(new Game("Game", 1280, 720));
		Map map = new Map(handler, "/map/basicMap.txt");
		handler.setMap(map);
		TestEntity testEntity = new TestEntity(handler, 0, 0, 20, 20);
		map.getEntityManager().addEntity(testEntity);
		Assert.assertFalse(testEntity.checkEntityCollisions(0, 0));

		TestEntity testEntity2 = new TestEntity(handler, 0, 0, 20, 20);
		map.getEntityManager().addEntity(testEntity2);
		Assert.assertTrue(testEntity.checkEntityCollisions(0, 0));
	}

	@Test
	void testEntityHitBox() {
		Handler handler = new Handler(new Game("Game", 1280, 720));
		Map map = new Map(handler, "/map/basicMap.txt");
		handler.setMap(map);
		TestEntity testEntity = new TestEntity(handler, 0, 0, 20, 20);
		map.getEntityManager().addEntity(testEntity);
		Rectangle testRectangle = new Rectangle(0, 0, 20, 20);

		Assert.assertEquals(testRectangle, testEntity.getCollisionBounds(0, 0));
		Assert.assertNotEquals(testRectangle, testEntity.getCollisionBounds(1, 1));
	}

	@Test
	void testTileCollision() {
		Handler handler = new Handler(new Game("Game", 1280, 720));
		Map map = new Map(handler, "/map/basicMap.txt");
		handler.setMap(map);
		TestEntity testEntity = new TestEntity(handler, 32, 40, 20, 20);
		map.getEntityManager().addEntity(testEntity);
		testEntity.setxMove(-20);
		testEntity.move();
		Assert.assertEquals(16, testEntity.getX(), 0);
		testEntity.setxMove(-20);
		testEntity.move();
		Assert.assertEquals(16, testEntity.getX(), 0);
		testEntity.setxMove(20);
		testEntity.move();
		Assert.assertEquals(36, testEntity.getX(), 0);
	}

	@Test
	void testMovingEntityCollisionInXAxis() {
		Handler handler = new Handler(new Game("Game", 1280, 720));
		Map map = new Map(handler, "/map/basicMap.txt");
		handler.setMap(map);
		TestEntity testEntity1 = new TestEntity(handler, 40, 40, 20, 20);
		map.getEntityManager().addEntity(testEntity1);
		testEntity1.setxMove(5);
		TestEntity testEntity2 = new TestEntity(handler, 50, 40, 20, 20);
		map.getEntityManager().addEntity(testEntity2);
		
		testEntity1.move();
		Assert.assertEquals(40, testEntity1.getX(), 0);
		
		testEntity1.setxMove(-10);
		testEntity1.move();
		Assert.assertEquals(30, testEntity1.getX(), 0);
	}
	
	@Test
	void testMovingEntityCollisionInYAxis() {
		Handler handler = new Handler(new Game("Game", 1280, 720));
		Map map = new Map(handler, "/map/basicMap.txt");
		handler.setMap(map);
		TestEntity testEntity1 = new TestEntity(handler, 40, 40, 20, 20);
		map.getEntityManager().addEntity(testEntity1);
		testEntity1.setyMove(5);
		TestEntity testEntity2 = new TestEntity(handler, 40, 50, 20, 20);
		map.getEntityManager().addEntity(testEntity2);
		
		testEntity1.move();
		Assert.assertEquals(40, testEntity1.getY(), 0);
		
		testEntity1.setyMove(-10);
		testEntity1.move();
		Assert.assertEquals(30, testEntity1.getY(), 0);
	}
}
