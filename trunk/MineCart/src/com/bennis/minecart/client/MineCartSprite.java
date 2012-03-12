package com.bennis.minecart.client;

import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;

/**
 * Main Sprite.
 * @author abennis
 */
public class MineCartSprite extends BasicSprite 
{
	/*
	 * Temp
	 */
	private String _input = "Not set yet";
	private ImageElement _image;
	
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		_image = imageLoader.getImage("images/MineCart.png");
	}

	@Override
	public void update(InputEvent event) 
	{
		if (event != null)
		{
			_input = event.getEventID();
		}
		else
		{
			_input = "RESET";
		}
		
		/*
		 * Temp
		 */
		this.setLocation(150, 377);
			
		
		/*
		 * User Input + Current State + Collision will decide
		 * what this Sprite will do.
		 * At the moment, I'm thinking the states are:
		 * 
		 * A movement controller (which in turn is either controlled
		 * by user inputs or AI) informs us what direction to go
		 * in and whether to jump or not.
		 * 
		 * Question? Do we allow movement of left/right during a jump? Yes.
		 * 
		 * 
		 * TODO AB consider refactoring out to moveableSprite upper class,
		 * so Blimp and bad guys can re use this.
		 * 
		 * TODO AB We want to be able to shoot enemies eventually?
		 * 
		 * SET MOVEMENT DIRECTION: 
		 * MOVING_LEFT - Obstacles and start of canvas will prevent this. Check collisions.
		 * MOVING_RIGHT - Obstacles and end of canvas will prevent this. Check collisions.
		 * FALLING_DOWN - Can collide with enemies and platform stops falling movement.
		 * JUMPING (DON'T COLLIDE WITH PLATFORM) - Hitting obstacle from below and enemies.
		 * 
		 * SET STATES:
		 * NORMAL - NO EFFECT
		 * LOOSING LIFE - FLASHING - Inform Game Life Counter
		 * DEAD - EXPLODE? - Inform Game to End.
		 * 
		 * SET ANIMATIONS:
		 * The combination of states above will dictate the Image sequence we'll use.
		 * Image sequences:
		 * ImageElement[] MOVING_RIGHT & JUMPING
		 * ImageElement[] MOVING_LEFT & JUMPING
		 * ImageElement[] MOVING_LEFT & JUMPING
		 */
	}

	@Override
	public void handleCollision(ISprite collisionSprite) 
	{

	}

	@Override
	protected String[] getImageNames() 
	{
		return null;
	}

	@Override
	public void draw(Canvas canvas) 
	{
		/*
		 * Calculate Rotation
		 */
		
		/*
		 * TODO AB -  Get the appropriate image from the current image sequence
		 */
		
		/*
		 * Draw 
		 */
		if (_image == null)
		{
			_image = this.getImageLoader().getImage("images/MineCart.png");	
		}
		else
		{
			canvas.getContext2d().drawImage(_image, this.getLocation().x, this.getLocation().y);
		}
		
		/*
		 * End rotation
		 */

	}
	
	//////////// Everything above this line should be generic, so we can refactor to base class.
}
