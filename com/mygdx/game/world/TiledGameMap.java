package com.mygdx.game.world;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.world.GameMap;
import com.mygdx.game.world.TileType;

public class TiledGameMap extends GameMap {

	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRender;
	
	public TiledGameMap() {
		tiledMap = new TmxMapLoader().load("assets/map.tmx");
		tiledMapRender = new OrthogonalTiledMapRenderer(tiledMap);
	}

	@Override
	public void render(OrthographicCamera camera, SpriteBatch batch) {
		// TODO Auto-generated method stub
		tiledMapRender.setView(camera);
		tiledMapRender.render();
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		super.render(camera, batch);
		batch.end();
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		super.update(delta);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		tiledMap.dispose();
	}

	@Override
	public TileType getTileTypeByCoordinate(int layer, int col, int row) {
		// TODO Auto-generated method stub
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);
		
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			
			if (tile != null) {
				int id = tile.getId();
				return TileType.getTileTypeById(id);
			}
		}
		return null;
	}

	public float getVerticalOverlapByCoordinate(int layer, int col, int row, float entityY, int entityHeight) {
		float zero = (TileType.TILE_SIZE * row) + TileType.TILE_SIZE;
		if (entityY < zero) {
			return zero - entityY;
		}
		
		float tileTop = (TileType.TILE_SIZE * row) + TileType.TILE_SIZE + TileType.TILE_SIZE;
		if (entityY + entityHeight > tileTop) {
			return -(entityY + entityHeight - tileTop);
		}
		
		System.out.println(TileType.TILE_SIZE * row);
		return 1f;
	}
	
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight();
	}

	@Override
	public int getLayers() {
		// TODO Auto-generated method stub
		return tiledMap.getLayers().getCount();
	}

}
