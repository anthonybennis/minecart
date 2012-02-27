package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.SpriteFactory;
import com.bennis.minecart.client.engine.model.Background;
import com.bennis.minecart.client.engine.model.Scene;

public class MineCartSpriteFactory extends SpriteFactory 
{
	public MineCartSpriteFactory(Scene scene)
	{
		super(scene);
		this.createInitialSprites(scene);
	}

	@Override
	public void update() 
	{
		// TODO AB - Create Sprites or we don't have a game :)
	}
	
	private void createInitialSprites(Scene scene)
	{
		Background background = new Background(this.getImageLoader());
		scene.storeSprite(background);
	}
}
