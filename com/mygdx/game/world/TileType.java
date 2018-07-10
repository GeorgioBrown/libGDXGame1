package com.mygdx.game.world;

import java.util.HashMap;

import com.badlogic.gdx.ApplicationAdapter;

public enum TileType {

	GRASS(1, "Grass", true),
	DIRT(2, "Dirt", true),
	SKY(3, "Sky", false),
	LAVA(4, "Lava", true),
	CLOUD(5, "Cloud", false),
	STONE(6, "Stone", true);

	public static final int TILE_SIZE = 16;
	
	private int id;
	private String name;
	private boolean collidable;
	private float damage;
	
	private TileType (int id, String name, boolean collidable) {
		this(id, name, collidable, 0);
	}
	
	private TileType (int id, String name, boolean collidable, float damage) {
		this.id = id;
		this.name = name;
		this.collidable = collidable;
		this.damage = damage;
	}

	
	// HashMap for storing list of all tiles and their IDs, and a getTileTypeById method using the map
	//
	private static HashMap<Integer, TileType> tileMap;
	
	static {
		tileMap = new HashMap<Integer, TileType>();
		for (TileType tileType : TileType.values()) {
			tileMap.put(tileType.getId(), tileType);
		}
	}
	
	public static TileType getTileTypeById (int id) {
		return tileMap.get(id);
	}
	////
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public boolean isCollidable() {
		return collidable;
	}

	public float getDamage() {
		return damage;
	}
}
