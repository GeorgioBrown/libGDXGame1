package com.mygdx.game.world;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Entity;
import com.mygdx.game.entities.Player;

public abstract class GameMap {

	public ArrayList<Entity> entities;
	
	public GameMap() {
		entities = new ArrayList<Entity>();
		entities.add(new Player(960, 350, this));
	}
	
	public void render (OrthographicCamera camera, SpriteBatch batch) {
		for (Entity entity : entities) {
			entity.render(batch);
		}
	}
	
	public void update (float delta) {
		for (Entity entity : entities) {
			entity.update(delta, -9.8f);
		}
	}
	
	public abstract void dispose ();
	
	/**
	 * Gets tile using pixel position
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	public TileType getTileTypeByLocation (int layer, float x, float y) {
		return this.getTileTypeByCoordinate(
				layer,
				(int) (x / TileType.TILE_SIZE),
				(int) (y / TileType.TILE_SIZE));
	}
	
	/**
	 * Gets tile using pixel position
	 * @param layer
	 * @param x
	 * @param y
	 * @return
	 */
	public abstract TileType getTileTypeByCoordinate (int layer, int col, int row);
	
	public abstract float getVerticalOverlapByCoordinate(int layer, int col, int row, float entityY, int entityHeight);
	
	public boolean isCollidingWithMap(float x, float y, int width, int height) {
		if (x < 0 || y < 0 || x + width > getPixelWidth() || y + height > getPixelHeight()) {
			return true;
		}
		
		for (int row = (int) (y / TileType.TILE_SIZE); row < Math.ceil((y + height) / TileType.TILE_SIZE); row++) {
			for (int col = (int) (x / TileType.TILE_SIZE); col < Math.ceil((x + width) / TileType.TILE_SIZE); col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TileType type = getTileTypeByCoordinate(layer, col, row);
					if (type != null && type.isCollidable())
						return true;
				}
			}
		}
		
		return false;
	}
	
	public float getVerticalOverlap (float entityX, float entityY, int entityWidth, int entityHeight) {
		float tileTop = 0;
		float tileBottom = 0;
		
		if (entityY < 0) {
			return 0 - entityY;
		}
		
		if (entityY + entityHeight > getPixelHeight()) {
			return getPixelHeight() - (entityY + entityHeight);
		}
		
		for (int row = (int) (entityY / TileType.TILE_SIZE); row < Math.ceil((entityY + entityHeight) / TileType.TILE_SIZE); row++) {
			for (int col = (int) (entityX / TileType.TILE_SIZE); col < Math.ceil((entityX + entityWidth) / TileType.TILE_SIZE); col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TileType type = getTileTypeByCoordinate(layer, col, row);
					if (type != null && type.isCollidable()) {
						tileTop = (TileType.TILE_SIZE * row) + TileType.TILE_SIZE;
						tileBottom = (TileType.TILE_SIZE * row);
						if (Math.abs(tileTop - entityY) < (Math.abs((entityY + entityHeight) - tileBottom))) {
							if (entityY < tileTop) {
								return tileTop - entityY;
							}
						}
						else {
							if (entityY + entityHeight > tileBottom) {
								return -((entityY + entityHeight) - tileBottom);
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	public float getHorizontalOverlap (float entityX, float entityY, int entityWidth, int entityHeight) {
		float tileLeft = 0;
		float tileRight = 0;
		
		if (entityX < 0) {
			return 0 - entityX;
		}
		
		if (entityX + entityWidth > getPixelWidth()) {
			return getPixelWidth() - (entityX + entityWidth);
		}
		
		for (int row = (int) (entityY / TileType.TILE_SIZE); row < Math.ceil((entityY + entityHeight) / TileType.TILE_SIZE); row++) {
			for (int col = (int) (entityX / TileType.TILE_SIZE); col < Math.ceil((entityX + entityWidth) / TileType.TILE_SIZE); col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TileType type = getTileTypeByCoordinate(layer, col, row);
					if (type != null && type.isCollidable()) {
						tileLeft = (TileType.TILE_SIZE * col) + TileType.TILE_SIZE;
						tileRight = (TileType.TILE_SIZE * col);
						if (Math.abs(tileLeft - entityX) < (Math.abs((entityX + entityWidth) - tileRight))) {
							if (entityX < tileLeft) {
								return tileLeft - entityX;
							}
						}
						else {
							if (entityX + entityWidth > tileRight) {
								return -((entityX + entityWidth) - tileRight);
							}
						}
					}
				}
			}
		}
		return 0;
	}
	
	public abstract int getWidth();
	public abstract int getHeight();
	public abstract int getLayers();
	
	public int getPixelWidth() {
		return this.getWidth() * TileType.TILE_SIZE;
	}
	
	public int getPixelHeight() {
		return this.getHeight() * TileType.TILE_SIZE;
	}
}
