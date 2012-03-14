package com.bennis.minecart.client;

import com.bennis.minecart.client.ButtonPanel.BUTTON_TYPE;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.logic.PlatformUtility;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Platform;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.Scene;
import com.bennis.minecart.client.engine.model.Vector;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;

/**
 * Main Sprite.
 * @author abennis
 */
public class MineCartSprite extends BasicSprite 
{	
	private Scene _scene;
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
	private double _endX;
	private double _endY;
	private final double MOVE_DISTANCE = 50; 
	private final double MOVE_SPEED = 3;
	
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader,Scene scene)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		_scene = scene;
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
		this.setImageElements(_currentAnmationSequence); // Super class needs this to calculate bounds.
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
		/*
		 * User Input + Current State + Collision will decide
		 * what this Sprite will do.
		 */
		if (event != null)
		{
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
					this.startNewMovement(_movement, _spriteState);
				}
				else
				{
					/*
					 * NO MOVEMENT, SO WE DON'T CONTINUE UPDATE
					 */
					return;
				}
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
			
			
		
	}
	
	/**
	 * Set up variables for a new movement.
	 * @param event
	 */
	private void startNewMovement(Movement movement, SpriteState state)
	{
		/*
		 * New Animation sequence is set by the current movement of the sprite.
		 */
		_currentAnmationSequence = this.getAppropriateAnimationSequence(movement, state);
		_currentAnimationFrame = 0; 
		this.calculateEndPosition(_movement);
		
	}
	
	/**
	 * 
	 */
	private void endMovement()
	{
		_movement = Movement.NONE;
	}
	
	/**
	 * Every movement results in the Sprite ending up
	 * in a new position. We need to know this position
	 * before we start the movement, as the movement action
	 * just iteratively moves the sprite in that direction.
	 * 
	 * @param movement
	 * @return
	 */
	private void calculateEndPosition(Movement movement)
	{
		Vector alignedVector = this.alignVectorToNearestPlatform(this.getLocation());
		switch (movement) 
		{
			case LEFT:
			{
				if (alignedVector != null)
				{
					_endX = alignedVector.x - MOVE_DISTANCE;
					_endY = alignedVector.y;
				}
				else
				{
					// TODO AB - There's no platform to move to! 
					this.endMovement();
				}
				
				break;
			}
			case LEFT_JUMP:
			{
				break;
			}
			case RIGHT:
			{
				if (alignedVector != null)
				{
					_endX = alignedVector.x + MOVE_DISTANCE;
					_endY = alignedVector.y;
				}
				else
				{
					// TODO AB - There's no platform to move to! 
					this.endMovement();
				}
				
				break;
			}
			case RIGHT_JUMP:
			{
				break;
			}
			case FALL:
			{
				break;
			}
			default:
			{
				break;
			}
		}
	}
	
	/**
	 * Move left a few paces.
	 * @param endofScreen
	 * @param startofScreen
	 * @return
	 */
	private SpriteState moveLeft(boolean endofScreen, boolean startofScreen)
	{
		/*
		 * We can move LEFT if we're not at the start of the screen,
		 * and there isn't any obstacle or enemy in our way.
		 */
		boolean canMoveLeft = (_collidingSprite == null) || (_collidingSprite != null && _collidingSprite.getBounds().getCenter().x >=
				this.getBounds().getCenter().x);
		
		if (!startofScreen && canMoveLeft)
		{			
			/*
			 * Move a few steps towards the end x
			 * We only move horizontally
			 */
			this.getLocation().x = this.getLocation().x - MOVE_SPEED;
		}
		
		/*
		 * Conditions to end  movement 
		 */
		if (this.getLocation().x <= _endX)
		{
			this.endMovement();
		}
		
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
		/*
		 * We can move LEFT if we're not at the start of the screen,
		 * and there isn't any obstacle or enemy in our way.
		 */
		boolean canMoveRight = (_collidingSprite == null) || (_collidingSprite != null && _collidingSprite.getBounds().getCenter().x <=
				this.getBounds().getCenter().x);
		
		if (!endofScreen && canMoveRight)
		{			
			/*
			 * Move a few steps towards the end x
			 * We only move horizontally
			 */
			this.getLocation().x = this.getLocation().x + MOVE_SPEED;
		}
		
		/*
		 * Conditions to end  movement 
		 */
		if (this.getLocation().x >= _endX)
		{
			this.endMovement();
		}
		
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
		/*
		 * Not used.
		 * TODO AB Remove from API?
		 */
		_collisionType = collisionType;
		
		switch (collisionSprite.getType()) 
		{
			case GOODIE:
			{	
				// TODO Increase counter
				collisionSprite.dispose();
				_collidingSprite = null;
				break;
			}
			case ENEMY:
			{	
				// TODO Decrease Life
				_collidingSprite = collisionSprite;
				this.startNewMovement(Movement.FALL, SpriteState.COLLIDE);
				break;
			}
			case OBSTACLE:
			{	
				_collidingSprite = collisionSprite;
				_collidingSprite = null;
				break;
			}
			case PLATFORM:
			{	
				Platform platform = (Platform)collisionSprite;
				this.alignVectorToPlatform(this.getLocation(),platform);
				_collidingSprite = null;
				break;
			}
			default:
			{
				/*
				 * We only set the colliding sprite if it's a Sprite
				 * that will affect movement of this sprite.
				 */
				_collidingSprite = null;
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param vector
	 * @param platform
	 * @return
	 */
	private Vector alignVectorToNearestPlatform(Vector vector)
	{
		Vector aligendVector = null;
		
		Platform endPositionPlatform = PlatformUtility.getNearestPlatform(_scene, vector.x, vector.y, Layers.MIDDLE);
		if (endPositionPlatform != null)
		{
			aligendVector = this.alignVectorToPlatform(vector, endPositionPlatform);
		}
		
		return aligendVector;
	}
	
	/**
	 * 
	 * @param vector
	 * @param platform
	 * @return
	 */
	private Vector alignVectorToPlatform(Vector vector, Platform platform)
	{
		Vector aligendVector = new Vector(vector.x, vector.y);
		aligendVector.y = platform.getBounds().getY();
		return aligendVector;
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
		return ((this.getLocation().x + this.getBounds().getWidth()) > GUIConstants.WIDTH);
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
