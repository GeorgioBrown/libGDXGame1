
// Next Tutorial: https://www.youtube.com/watch?v=IQMFiJSPVcY&t=

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.entities.Player;
import com.mygdx.game.world.GameMap;
import com.mygdx.game.world.TileType;
import com.mygdx.game.world.TiledGameMap;

public class MyGdxGame extends ApplicationAdapter {
	OrthographicCamera cam;
	int cameraWidth = 1280;
	int cameraHeight = 720;
	SpriteBatch batch;
	
	GameMap gameMap;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		
		Gdx.graphics.setWindowedMode(cameraWidth, cameraHeight);
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		
		// Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		cam = new OrthographicCamera();
		cam.setToOrtho(false, width / 2, height / 2);
		cam.update();
		
		gameMap = new TiledGameMap();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		////
		
		Vector3 pos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
		
//		if (Gdx.input.isTouched()) {
//			cam.translate(-Gdx.input.getDeltaX(), Gdx.input.getDeltaY());
//			cam.update();
//		}
		
		cam.translate(
				Math.subtractExact((long)gameMap.entities.get(0).getX(), (long)cam.position.x) * 0.05f,
				Math.subtractExact((long)gameMap.entities.get(0).getY(), (long)cam.position.y) * 0.05f,
				0
		);
		
		if (cam.position.x + (cam.viewportWidth / 2) > gameMap.getPixelWidth()) {
			System.out.println("Overlap on the right!");
			cam.position.x = gameMap.getPixelWidth() - (cam.viewportWidth / 2);
		} else if (cam.position.x - (cam.viewportWidth / 2) < 0) {
			System.out.println("Overlap on the left!");
			cam.position.x = 0 + (cam.viewportWidth / 2);
		}
		
		if (cam.position.y + (cam.viewportHeight / 2) > gameMap.getPixelHeight()) {
			System.out.println("Overlap on the top!");
			cam.position.y = gameMap.getPixelHeight() - (cam.viewportHeight / 2);
		} else if (cam.position.y - (cam.viewportHeight / 2) < 0) {
			System.out.println("Overlap on the bottom!");
			cam.position.y = 0 + (cam.viewportHeight / 2);
		}
		
		cam.update();
		
		if (Gdx.input.justTouched()) {
			TileType type = gameMap.getTileTypeByLocation(1, pos.x, pos.y);
			
			if (type != null) {
				System.out.println("Id: " + type.getId()
				+ " Name:" + type.getName() + " Damage:" + type.getDamage() + " Collidable: " + type.isCollidable());
			} else {
				System.out.println("No tile clicked!");
			}
			
			if (gameMap.entities.get(0) != null) {
				System.out.println("Is grounded? " + gameMap.entities.get(0).isGrounded());
			}
			
			gameMap.entities.get(0).setPos(pos.x, pos.y);
		}
		gameMap.update(Gdx.graphics.getDeltaTime());
		gameMap.render(cam, batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		gameMap.dispose();
	}
}
