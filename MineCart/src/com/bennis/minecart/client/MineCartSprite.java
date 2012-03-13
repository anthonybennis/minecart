package com.bennis.minecart.client;

import com.bennis.minecart.client.ButtonPanel.BUTTON_TYPE;
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
	private enum Movement{LEFT, LEFT_JUMP,RIGHT,RIGHT_JUMP, NONE};
	private enum SpriteState{NORMAL, COLLIDE, FALLING};
	
	private ImageElement[] _currentAnmationSequence;
	private ImageElement[] _movingRightAnmationSequence;
	private ImageElement[] _movingLeftAnmationSequence;
	private ImageElement[] _collidingAnmationSequence;
	private int _currentAnimationFrame = 0;
	private SpriteState _spriteState = SpriteState.NORMAL;
	private Movement _movement = Movement.NONE;
	private boolean _updateFrame;
	/*
	 * Collision
	 */
	private ISprite _collidingSprite;
	private Collision _collisionType = Collision.NONE;
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		
		this.loadAllImageSequences();
		this.setLocation(150, 377); // Starting position
		_currentAnmationSequence = _movingRightAnmationSequence;
	}
	
	/**
	 * 
	 */
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
//		String[] imageNames = new String[5];
//		imageNames[0] = "images/minecart/left00.png";
//		imageNames[1] = "images/minecart/left01.png";
//		imageNames[2] = "images/minecart/left02.png";
//		imageNames[3] = "images/minecart/left03.png";
//		imageNames[4] = "images/minecart/left04.png";
//		return this.createAnimationSequence(imageNames);
		/*
		 * TEMP
		 */
		return this.createMovingRightAnimationSequence();
	}
	
	/**
	 * 
	 * @return
	 */
	private ImageElement[] createCollidingAnimationSequence()
	{
//		String[] imageNames = new String[5];
//		imageNames[0] = "images/minecart/collide00.png";
//		imageNames[1] = "images/minecart/collide01.png";
//		imageNames[2] = "images/minecart/collide02.png";
//		imageNames[3] = "images/minecart/collide03.png";
//		imageNames[4] = "images/minecart/collide04.png";
//		return this.createAnimationSequence(imageNames);
		/*
		 * TEMP
		 */
		return this.createMovingRightAnimationSequence();
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
		this.setLocation(150, 377); 
		
		if (event != null)
		{
			/*
			 * User Input + Current State + Collision will decide
			 * what this Sprite will do.
			 * 
			 * SET MOVEMENT DIRECTION: 
			 * MOVING_LEFT - Obstacles and start of canvas will prevent this. Check collisions.
			 * MOVING_RIGHT - Obstacles and end of canvas will prevent this. Check collisions.
			 * FALLING_DOWN - Can collide with enemies and platform stops falling movement.
			 * JUMPING (DON'T COLLIDE WITH PLATFORM) - Hitting obstacle from below and enemies.
			 * DONT_MOVE - No input from user, just stay where you are.
			 */
			
			/*
			 * Check to see if we're moving already. If we
			 * are then we just ignore any new instructions to move.
			 */
			if (_movement == Movement.NONE)
			{
				_movement = this.convertInputEventToMovement(event);
				/*
				 * New Animation sequence is set by the current movement of the sprite.
				 */
				_currentAnmationSequence = this.getAppropriateAnimationSequence(_movement, _spriteState);
				_currentAnimationFrame = 0; 
			}
			
			boolean endofScreen = this.isSpriteAtEndOfScreen();
			boolean startofScreen = this.isSpriteAtStartOfScreen();
			
			switch (_movement) 
			{
				case LEFT:
				{
					_spriteState = this.moveLeft(endofScreen, startofScreen);
					break;
				}
				case LEFT_JUMP:
				{
					_spriteState = this.jumpLeft(endofScreen, startofScreen);
					break;
				}
				case RIGHT_JUMP:
				{
					_spriteState = this.jumpRight(endofScreen, startofScreen);
					break;
				}
				default: // RIGHT
				{
					_spriteState = this.moveRight(endofScreen, startofScreen);
					break;
				}
			}
			
			/*
			 * TODO AB When a movement is complete, set Movement to NONE
			 */
		}
	}
	
	private SpriteState moveLeft(boolean endofScreen, boolean startofScreen)
	{
//		if (!startofScreen && )
		// When movement is complete, set to NONE
		return _spriteState;
	}
	
	private SpriteState jumpLeft(boolean endofScreen, boolean startofScreen)
	{
		// TODO AB - Falling is part of Jump cycle
		// When movement is complete, set to NONE
		return _spriteState;
	}
	
	private SpriteState moveRight(boolean endofScreen, boolean startofScreen)
	{
		// When movement is complete, set to NONE
		return _spriteState;
	}
	
	private SpriteState jumpRight(boolean endofScreen, boolean startofScreen)
	{
		// TODO AB - Falling is part of Jump cycle
		// When movement is complete, set to NONE
		// How do we cycle through a JUMP????
		return _spriteState;
	}
	
	
	@Override
	public void handleCollision(ISprite collisionSprite,Collision collisionType) 
	{
		_collidingSprite = collisionSprite; // TODO AB - This needs to be set to NULL after collision.
		_collisionType = collisionType;// TODO AB - This needs to be set to NONE after collision.
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
		imageNames[0] = "images/minecart/MineCartRight00.png";
		imageNames[1] = "images/minecart/MineCartRight01.png";
		imageNames[2] = "images/minecart/MineCartRight02.png";
		imageNames[3] = "images/minecart/MineCartRight03.png";
		
		return imageNames;
	}

	@Override
	public void draw(Canvas canvas) 
	{
		/*
		 * Ensure current images are loaded.
		 */
		if (!this.haveAllImagesLoaded(_currentAnmationSequence))
		{
			this.loadAllImageSequences();
		}
		else
		{
			double rotationAngle = this.calculateRotationAngle();
			
			/*
			 * Draw 
			 * (Drawing Style depends on SpriteState)
			 */
			if (_currentAnimationFrame >= _currentAnmationSequence.length)
			{
				_currentAnimationFrame = 0; //Reset animation.
			}
			
			ImageElement currentFrame = _currentAnmationSequence[_currentAnimationFrame];
			canvas.getContext2d().rotate(rotationAngle);
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
	
	/**
	 * 
	 * @return
	 */
	private double calculateRotationAngle()
	{
		/*
		 * TODO AB (Needed when track moves)
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
					/*
					 * Don't change the current animation sequence if
					 * we don't know what to do.
					 */
					imageSequence = _currentAnmationSequence;
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
	
	private Movement convertInputEventToMovement(InputEvent event)
	{
		Movement movement = Movement.RIGHT;
		
		if (event.getEventID() == BUTTON_TYPE.LEFT.name())
		{
			movement = Movement.LEFT;
		}
		else if (event.getEventID() == BUTTON_TYPE.RIGHT.name())
		{
			movement = Movement.RIGHT;
		}
		else if (event.getEventID() == BUTTON_TYPE.LEFT_JUMP.name())
		{
			movement = Movement.LEFT_JUMP;
		}
		else if (event.getEventID() == BUTTON_TYPE.RIGHT_JUMP.name())
		{
			movement = Movement.RIGHT_JUMP;
		}

		return movement;		
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isSpriteAtEndOfScreen()
	{
		/*
		 * TODO AB
		 * Do we need to add buffer of image width?
		 */
		return (this.getLocation().x > GUIConstants.WIDTH);
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isSpriteAtStartOfScreen()
	{
		/*
		 * TODO AB
		 * Do we need to add buffer of image width?
		 */
		return (this.getLocation().x <= 0);
	}
	
	
	//////////// Everything above this line should be generic, so we can refactor to base class.
}
