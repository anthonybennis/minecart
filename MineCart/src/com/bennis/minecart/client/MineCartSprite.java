package com.bennis.minecart.client;

import com.bennis.minecart.client.ButtonPanel.BUTTON_TYPE;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.Vector;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;

/**
 * Main Sprite.
 * @author abennis
 */
public class MineCartSprite extends BasicSprite 
{	
	/*
	 * States
	 */
	private enum Movement{LEFT, LEFT_JUMP,RIGHT,RIGHT_JUMP, FALL, NONE};
	private enum SpriteState{NORMAL, COLLIDE, FALLING};
	
	/*
	 * Animation
	 */
	private ImageElement[] _currentAnmationSequence;
	private ImageElement[] _movingRightAnmationSequence;
	private ImageElement[] _movingLeftAnmationSequence;
	private ImageElement[] _collidingAnmationSequence;
	private int _currentAnimationFrame = 0;
	private SpriteState _spriteState = SpriteState.NORMAL;
	private boolean _updateFrame;
	/*
	 * Collision
	 */
	private ISprite _collidingSprite;
	private Collision _collisionType = Collision.NONE;
	/*
	 * Movement
	 */
	private Movement _movement = Movement.NONE;
	private double _startX;
	private double _startY;
	private double _endX;
	private double _endY;
	
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		
		this.loadAllImageSequences();
		this.setLocation(150, 377); // Starting position
	}
	
	/**
	 * 
	 */
	private void loadAllImageSequences()
	{
		_movingRightAnmationSequence = this.createMovingRightAnimationSequence();
		_movingLeftAnmationSequence = this.createMovingLeftAnimationSequence();
		_collidingAnmationSequence = this.createCollidingAnimationSequence();
		
		_currentAnmationSequence = _movingRightAnmationSequence;
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
		ImageElement[] animationSequence = new ImageElement[imageNames.length];
		
		for (int i = 0; i < imageNames.length; i++) 
		{
			animationSequence[i] = this.getImageLoader().getImage(imageNames[i]);
		}
		
		return animationSequence;
	}

	@Override
	public void update(InputEvent event) 
	{		
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
			 * Check to see if we're moving already. 
			 * If we are then we just ignore any new instructions to move.
			 * Only a collision can change a movement that's in progress.
			 */
			if (_movement == Movement.NONE) // MineCart isn't in the middle of something, so we'll accept new movement.
			{
				_movement = this.convertInputEventToMovement(event); // Find out what movement to do.
				
				if (_movement != Movement.NONE) // If there's a movement, start it.
				{
					this.startNewMovement(event);
				}
				else
				{
					/*
					 * NO MOVEMENT, SO WE DON'T CONTINUE UPDATE
					 */
					return;
				}
			}
	
			
			boolean endofScreen = this.isSpriteAtEndOfScreen();
			boolean startofScreen = this.isSpriteAtStartOfScreen();
			
			/*
			 * TODO AB - 
			 * 1. Is SpriteState right here? Should this be done in handleCollision.
			 * 2. Collisions can interrupt a movement in progress!!!
			 */
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
				case FALL: // TODO A Jump movement ends transforms to a Fall movement
				{
					_spriteState = this.fall(endofScreen, startofScreen);
					break;
				}
				case RIGHT:
				{
					_spriteState = this.moveRight(endofScreen, startofScreen);
					break;
				}
				default: // NONE
				{
					break;
				}
			}
			
			if (this.getLocation().x == _endX
					&& this.getLocation().y == _endY)
			{
				this.endMovement();
			}
		}
	}
	
	/**
	 * Set up variables for a new movement.
	 * @param event
	 */
	private void startNewMovement(InputEvent event)
	{
		
		/*
		 * New Animation sequence is set by the current movement of the sprite.
		 */
		_currentAnmationSequence = this.getAppropriateAnimationSequence(_movement, _spriteState);
		_currentAnimationFrame = 0; 
		_startX = this.getLocation().x;
		_startY = this.getLocation().y;
		Vector endPosition = this.calculateEndPosition(_movement);
		_endX = endPosition.x;
		_endY = endPosition.y;
	}
	
	/**
	 * 
	 */
	private void endMovement()
	{
		_movement = Movement.NONE;
	}
	
	
	private Vector calculateEndPosition(Movement movement)
	{
		Vector vector = new Vector();
		
		return vector;
	}
	
	/**
	 * Move left a few paces.
	 * @param endofScreen
	 * @param startofScreen
	 * @return
	 */
	private SpriteState moveLeft(boolean endofScreen, boolean startofScreen)
	{
//		if (!startofScreen && )
		// When movement is complete, set to NONE
		return _spriteState;
	}
	
	/**
	 * 
	 * @param endofScreen
	 * @param startofScreen
	 * @return
	 */
	private SpriteState jumpLeft(boolean endofScreen, boolean startofScreen)
	{
		// TODO AB - Falling is part of Jump cycle
		// When movement is complete, set to NONE
		return _spriteState;
	}
	
	/**
	 * Move right a few paces
	 * @param endofScreen
	 * @param startofScreen
	 * @return
	 */
	private SpriteState moveRight(boolean endofScreen, boolean startofScreen)
	{
		// When movement is complete, set to NONE
		return _spriteState;
	}
	
	/**
	 * Jump to the Right
	 */
	private SpriteState jumpRight(boolean endofScreen, boolean startofScreen)
	{
		// TODO AB - Falling is part of Jump cycle
		// When movement is complete, set to NONE
		// How do we cycle through a JUMP????
		return _spriteState;
	}
	
	private SpriteState fall(boolean endofScreen, boolean startofScreen)
	{
		return _spriteState;
	}
	
	@Override
	public void handleCollision(ISprite collisionSprite,Collision collisionType) 
	{
		_collidingSprite = collisionSprite; // TODO AB - This needs to be set to NULL after collision.
		_collisionType = collisionType;// TODO AB - This needs to be set to NONE after collision.
		
		
		switch (collisionSprite.getType()) 
		{
			case GOODIE:
			{	
				// TODO Increase counter and destroy Sprite.
				break;
			}
			case ENEMY:
			{	
				// TODO Decrease Life, and change movement to fall.
				break;
			}
			case OBSTACLE:
			{	
				// Movement will be prevented in move methods.
				// What happens if Sprite lands on top of Obstacle??
				break;
			}
			case PLATFORM:
			{	
				// Change movement to None and ensure location of MineCart is on top of Platrform.
				break;
			}
			default:
			{
				// We don't care about other types.
				break;
			}
		}
	}

	@Override
	protected String[] getImageNames() 
	{
		/*
		 * TODO AB
		 * This is a multi animation Sprite. We should
		 * examine extending BasicSprite to handle this.
		 */
		String[] imageNames = new String[4];
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
//			canvas.getContext2d().rotate(rotationAngle);
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
//			canvas.getContext2d().rotate(-rotationAngle);
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
					 * FALL, NONE
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
