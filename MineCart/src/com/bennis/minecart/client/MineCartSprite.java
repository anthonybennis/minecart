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
	// END TEMP
	
	private enum Movement{LEFT, LEFT_JUMP,RIGHT,RIGHT_JUMP};
	private enum SpriteState{NORMAL, COLLIDE};
	private ImageElement[] _currentAnmationSequence;
	private ImageElement[] _movingRightAnmationSequence;
	private ImageElement[] _movingLeftAnmationSequence;
	private ImageElement[] _collidingAnmationSequence;
	private int _currentAnimationFrame = 0;
	private SpriteState _spriteState = SpriteState.NORMAL;
	private Movement _movement = Movement.RIGHT;
	private boolean _updateFrame;
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		_image = imageLoader.getImage("images/MineCart.png");
		this.loadAllImageSequences();
		this.setLocation(150, 377); // Starting position
		_currentAnmationSequence = _movingRightAnmationSequence;
	}
	
	private void loadAllImageSequences()
	{
		_movingRightAnmationSequence = this.createMovingRightAnimationSequence();
		_movingLeftAnmationSequence = this.createMovingLeftAnimationSequence();
		_collidingAnmationSequence = this.createCollidingAnimationSequence();
	}
	
	/**
	 * 
	 * @return
	 */
	private ImageElement[] createMovingRightAnimationSequence()
	{
		String[] imageNames = this.getImageNames();
		return this.createAnimationSequence(imageNames);
	}
	
	/**
	 * 
	 * @return
	 */
	private ImageElement[] createMovingLeftAnimationSequence()
	{
		String[] imageNames = new String[5];
		imageNames[0] = "images/minecart/left00.png";
		imageNames[1] = "images/minecart/left01.png";
		imageNames[2] = "images/minecart/left02.png";
		imageNames[3] = "images/minecart/left03.png";
		imageNames[4] = "images/minecart/left04.png";
		return this.createAnimationSequence(imageNames);
	}
	
	/**
	 * 
	 * @return
	 */
	private ImageElement[] createCollidingAnimationSequence()
	{
		String[] imageNames = new String[5];
		imageNames[0] = "images/minecart/collide00.png";
		imageNames[1] = "images/minecart/collide01.png";
		imageNames[2] = "images/minecart/collide02.png";
		imageNames[3] = "images/minecart/collide03.png";
		imageNames[4] = "images/minecart/collide04.png";
		return this.createAnimationSequence(imageNames);
	}
	
	/**
	 * Generic ImageSequence method.
	 * TODO Move to Basic Sprite.
	 * @param imageNames
	 * @return
	 */
	private ImageElement[] createAnimationSequence(String[] imageNames)
	{
		ImageElement[] normalSequence = new ImageElement[0];
		return normalSequence;
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
		 * DONT_MOVE - No input from user, just stay where you are.
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
		/*
		 * TODO AB
		 * This is a multi animation Sprite. We should
		 * examine extending BasicSprite to handle this.
		 */
		String[] imageNames = new String[5];
		imageNames[0] = "images/minecart/right00.png";
		imageNames[1] = "images/minecart/right01.png";
		imageNames[2] = "images/minecart/right02.png";
		imageNames[3] = "images/minecart/right03.png";
		imageNames[4] = "images/minecart/right04.png";
		
		return imageNames;
	}

	@Override
	public void draw(Canvas canvas) 
	{
		double rotationAngle = this.calculateRotationAngle();
		
		_currentAnmationSequence = this.getAppropriateAnimationSequence(_movement, _spriteState);
		
		/*
		 * Ensure current images are loaded.
		 */
		if (!this.haveAllImagesLoaded(_currentAnmationSequence))
		{
			this.loadAllImageSequences();
		}
		else
		{
			/*
			 * Draw 
			 * (Drawing Style depends on SpriteState)
			 */
			if (_currentAnimationFrame >= _currentAnmationSequence.length)
			{
				_currentAnimationFrame = 0; //Reset animation.
			}
			
			ImageElement currentFrame = _currentAnmationSequence[_currentAnimationFrame];
			canvas.getContext2d().drawImage(currentFrame, this.getLocation().x, this.getLocation().y);
			
			// Update animation every second refresh.
			_updateFrame = !_updateFrame; 
			if (_updateFrame)
			{
				_currentAnimationFrame++;
			}
			
			/*
			 * End rotation
			 */
			canvas.getContext2d().rotate(-rotationAngle);
		}
	}
	
	private double calculateRotationAngle()
	{
		/*
		 * TODO AB
		 */
		return 0;
	}
	
	/**
	 * Assigns the appropriate animation sequence to sprite, depending
	 * on it's movement and current state.
	 * 
	 * @param movement
	 * @param state
	 * @return
	 */
	private ImageElement[] getAppropriateAnimationSequence(Movement movement, SpriteState state)
	{
		ImageElement[] imageSequence = null;
		
		if (state == SpriteState.NORMAL)
		{
			switch (movement) 
			{
				case LEFT:
				{	
					imageSequence = _movingLeftAnmationSequence;
					break;
				}
				case LEFT_JUMP:
				{	
					imageSequence = _movingLeftAnmationSequence;
					break;
				}
				case RIGHT:
				{	
					imageSequence = _movingRightAnmationSequence;
					break;
				}
				case RIGHT_JUMP:
				{	
					imageSequence = _movingRightAnmationSequence;
					break;
				}
				default:
				{
					imageSequence = _movingRightAnmationSequence;
					break;
				}
			}
		}
		else
		{
			imageSequence = _collidingAnmationSequence;
		}
		
		return imageSequence;
	}
	
	
	//////////// Everything above this line should be generic, so we can refactor to base class.
}
