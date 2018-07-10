package com.mygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.world.GameMap;

public abstract class Entity {

	protected Vector2 pos;
	protected EntityType type;
	protected float velocityY = 0;
	protected float velocityX = 0;
	protected GameMap map;
	protected boolean grounded = false;
	
	public Entity(float x, float y, EntityType type, GameMap map) {
		this.pos = new Vector2(x, y);
		this.type = type;
		this.map = map;
	}
	
	public void update (float deltaTime, float gravity) {
		// apply weighted gravity to velocity
		velocityY += gravity * deltaTime * getWeight();
		
		// update y position based on gravity
		pos.y += velocityY * deltaTime;
		
		float overlapY = map.getVerticalOverlap(pos.x, pos.y, getWidth(), getHeight());
		if (overlapY != 0) {
			if (velocityY < 0) {
				grounded = true;
			}
			velocityY = 0;
			pos.y += overlapY;
		}
		else {
			grounded = false;
		}
		
		pos.x += velocityX * deltaTime;
		float overlapX = map.getHorizontalOverlap(pos.x, pos.y, getWidth(), getHeight());
		if (overlapX != 0) {
			velocityX = 0;
			pos.x += overlapX;
		}
	}
	
	public abstract void render (SpriteBatch batch);

	protected void moveX (float amount) {
		float newX = pos.x + amount;
		if (!map.isCollidingWithMap(newX, pos.y, getWidth(), getHeight())){
			this.pos.x = newX;
		}
	}
	
	public Vector2 getPos() {
		return pos;
	}

	public float getX() {
		return pos.x;
	}
	
	public float getY() {
		return pos.y;
	} 
	
	public EntityType getType() {
		return type;
	}

	public boolean isGrounded() {
		return grounded;
	}
	
	public int getHeight() {
		return type.getHeight();
	}
	
	public int getWidth() {
		return type.getWidth();
	}
	
	public float getWeight() {
		return type.getWeight();
	}

	public void setPos(float x, float y) {
		// TODO Auto-generated method stub
		this.pos.x = x;
		this.pos.y = y;
	}
}
