package com.mygdx.game.entities;

import javax.swing.GroupLayout.SequentialGroup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.world.GameMap;

public class Player extends Entity {
	private static final float JUMP_FORCE = 3.5f;
	private static final float MOVE_FORCE = 2.5f;
	private static final float MOVE_SPEED = 100;
	private static final float DECELERATION_FORCE = 0.8f;
	private static final float AIR_CONTROL = 0.66f;
	
	Texture image;
	
	public Player (float x, float y, GameMap map) {
		super (x, y, EntityType.PLAYER, map);
		image = new Texture("player.png");
	}
	
	public void update (float deltaTime, float gravity) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded) {
			this.velocityY += JUMP_FORCE * getWeight();
		}
		else if (Gdx.input.isKeyPressed(Keys.SPACE) && !grounded && this.velocityY > 0) {
			this.velocityY += JUMP_FORCE * getWeight() * deltaTime;
		}
		
		// if only left is pressed
		if (Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)) {
			// if player is on the ground and moving the "wrong direction", decelerate
			if (this.velocityX > 0 && this.grounded) {
				this.velocityX = this.velocityX * DECELERATION_FORCE;
			}
			
			// if player is moving left at max speed, cap the speed
			if (this.velocityX < -MOVE_SPEED)
				this.velocityX = -MOVE_SPEED;
			else if (this.grounded) // if player is grounded, move left, normally
				this.velocityX -= MOVE_FORCE;
			else // if player is in the air, move left, modified by air control capability
				this.velocityX -= MOVE_FORCE * AIR_CONTROL;
		}
		
		// same as above but for moving right
		if (Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)) {
			if (this.velocityX < 0 && this.grounded) {
				this.velocityX = this.velocityX * DECELERATION_FORCE;
			}
			
			if (this.velocityX > MOVE_SPEED)
				this.velocityX = MOVE_SPEED;
			else if (this.grounded)
				this.velocityX += MOVE_FORCE;
			else
				this.velocityX += MOVE_FORCE * AIR_CONTROL;
		}
		
		// if both left and right or neither are pressed
		if ((Gdx.input.isKeyPressed(Keys.A) && Gdx.input.isKeyPressed(Keys.D)) || (!Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D))) {
			// if movement is close to zero, make it zero
			if (Math.abs(this.velocityX) < 1)
				this.velocityX = 0;
			
			// if player's moving, slow down using acceleration force
			if (this.velocityX != 0 && this.grounded) {
				this.velocityX = this.velocityX * DECELERATION_FORCE;
			}
		}
		
		super.update(deltaTime, gravity);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		batch.draw(image, pos.x, pos.y, getWidth(), getHeight());
	}

}
