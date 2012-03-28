package com.bennis.minecart.client;

import com.bennis.minecart.client.ButtonPanel.ButtonClickEventType;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.logic.PlatformUtility;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.Platform;
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
	private enum Movement{LEFT, LEFT_JUMP,RIGHT,RIGHT_JUMP, FALL, NONE, STOP};
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
	private double _startX;
	private double _startY;
	private final double MOVE_DISTANCE = 50; 
	private final double MOVE_SPEED = 3;
	private final double JUMP_HORIZONTAL_DISTANCE = 150;
	private final double FALL_SPEED = MOVE_SPEED*2;
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader,Scene scene)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		_scene = scene;
		this.loadAllImageSequences();
		this.setLocation(150, 371.01); // Starting position
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
		 * TODO AB - Use Correct Sprite Image
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
		 * TODO AB - Use Correct Sprite Image
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
			Movement newMovement = this.convertInputEventToMovement(event); // Find out what movement to do.
			
			if (_movement == Movement.NONE || _movement == Movement.LEFT ||  _movement == Movement.RIGHT) 
			{
				if (_movement != newMovement) // If there's a movement, start it.
				{
					this.startNewMovement(newMovement, _spriteState);
				}
				else
				{
					/*
					 * NO MOVEMENT, SO WE DON'T CONTINUE UPDATE
					 */
					return;
				}
			}
			// Movement has been interrupted (Mouse Up/Collision)
			else if (newMovement == Movement.STOP)
			{
				this.startNewMovement(Movement.FALL, _spriteState);
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
				case FALL:
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
		_movement = movement;
		_currentAnmationSequence = this.getAppropriateAnimationSequence(movement, state);
		_currentAnimationFrame = 0; 
		_startX = this.getLocation().x;
		_startY = this.getLocation().y;
		this.calculateEndPosition(movement);
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
					// There's no platform to move to! 
					this.startNewMovement(Movement.NONE, _spriteState);
				}
				
				break;
			}
			case LEFT_JUMP:
			{
				if (alignedVector != null)
				{
					_endX = alignedVector.x - JUMP_HORIZONTAL_DISTANCE;
					_endY = alignedVector.y;
				}
				else
				{
					// There's no platform to move to! 
					this.startNewMovement(Movement.NONE, _spriteState);
				}
				
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
					this.startNewMovement(Movement.NONE, _spriteState);
				}
				
				break;
			}
			case RIGHT_JUMP:
			{
				if (alignedVector != null)
				{
					_endX = alignedVector.x + JUMP_HORIZONTAL_DISTANCE;
					_endY = alignedVector.y;
				}
				else
				{
					// There's no platform to move to! 
					this.startNewMovement(Movement.NONE, _spriteState);
				}
				
				break;
			}
			case FALL:
			{
				if (alignedVector != null)
				{
					_endX = alignedVector.x;
					_endY = alignedVector.y;
				}
				else
				{
					// There's no platform to move to! 
					this.startNewMovement(Movement.NONE, _spriteState);
				}
				
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
		if (!startofScreen)
		{			
			/*
			 * Move a few steps towards the end x
			 * We only move horizontally
			 */
			this.getLocation().x = this.getLocation().x - MOVE_SPEED;
		}
		else
		{
			this.startNewMovement(Movement.NONE, _spriteState);
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
		if (!startofScreen)
		{			
			this.getLocation().x = this.getLocation().x - MOVE_SPEED;
			
			double distanceTravelled = this._startX - this.getLocation().x;
			
			if (distanceTravelled < JUMP_HORIZONTAL_DISTANCE/2)
			{
				/*
				 * Jump up
				 */
				this.getLocation().y = this.getLocation().y - MOVE_SPEED;
			}
			else
			{
				/*
				 * Fall back down
				 */
				this.getLocation().y = this.getLocation().y + MOVE_SPEED;
			}
		}
		else
		{
			/*
			 * Start of screen. Just fall now.
			 */
			this.startNewMovement(Movement.FALL, _spriteState);
		}
		
		/*
		 * Conditions to end  movement 
		 */
		if ((this.getLocation().y + this.getBounds().getHeight()) >= _endY)
		{
			this.startNewMovement(Movement.NONE, _spriteState);
		}
		
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
		 * We can move LEFT if we're not at the start of the screen
		 */
		if (!endofScreen)
		{			
			/*
			 * Move a few steps towards the end x
			 * We only move horizontally
			 */
			this.getLocation().x = this.getLocation().x + MOVE_SPEED;
		}
		else
		{
			this.startNewMovement(Movement.NONE, _spriteState);
		}
		
		return _spriteState;
	}
	
	/**
	 * Jump to the Right
	 */
	private SpriteState jumpRight(boolean endofScreen, boolean startofScreen)
	{
		if (!endofScreen)
		{			
			this.getLocation().x = this.getLocation().x + MOVE_SPEED;
			
			double distanceTravelled = this.getLocation().x - this._startX;
			
			if (distanceTravelled < JUMP_HORIZONTAL_DISTANCE/2)
			{
				/*
				 * Jump up
				 */
				this.getLocation().y = this.getLocation().y - MOVE_SPEED;
			}
			else
			{
				/*
				 * Fall back down
				 */
				this.getLocation().y = this.getLocation().y + MOVE_SPEED;
			}
		}
		else
		{
			/*
			 * Reached end of screen. Just fall down.
			 */
			this.startNewMovement(Movement.FALL, _spriteState);
		}
		
		/*
		 * Conditions to end  movement 
		 */
		System.out.println("Starting Y point is: " + this.getLocation().y + this.getBounds().getHeight());
		if ((this.getLocation().y + this.getBounds().getHeight()) >= _endY)
		{
			this.startNewMovement(Movement.NONE, _spriteState);
		}
		
		return _spriteState;
	}
	
	/**
	 * Drags Sprite down at fast speed until it hits a Platform.
	 * 
	 * @param endofScreen
	 * @param startofScreen
	 * @return
	 */
	private SpriteState fall(boolean endofScreen, boolean startofScreen)
	{		
		this.getLocation().y = this.getLocation().y + FALL_SPEED; 

		/*
		 * Conditions to end  movement 
		 */
		if ((this.getLocation().y + this.getBounds().getHeight()) >= _endY)
		{
			this.startNewMovement(Movement.NONE, _spriteState);
		}
		
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
		
		if (collisionSprite != null)
		{
			switch (collisionSprite.getType()) 
			{
				case GOODIE:
				{	
					// TODO AB Increase counter
					collisionSprite.dispose();
					_collidingSprite = null;
					break;
				}
				case ENEMY:
				{	
					// TODO AB Decrease Life
					_collidingSprite = collisionSprite;
					this.startNewMovement(Movement.FALL, SpriteState.COLLIDE);
					break;
				}
				case OBSTACLE:
				{	
					_collidingSprite = collisionSprite;
					this.startNewMovement(Movement.FALL, SpriteState.COLLIDE);
					
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
		aligendVector.y = platform.getBounds().getY() - 1;
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
		
		if (event.getEventID() == ButtonClickEventType.LEFT.name())
		{
			movement = Movement.LEFT;
		}
		else if (event.getEventID() == ButtonClickEventType.RIGHT.name())
		{
			movement = Movement.RIGHT;
		}
		else if (event.getEventID() == ButtonClickEventType.LEFT_JUMP.name())
		{
			movement = Movement.LEFT_JUMP;
		}
		else if (event.getEventID() == ButtonClickEventType.RIGHT_JUMP.name())
		{
			movement = Movement.RIGHT_JUMP;
		}
		else if (event.getEventID() == ButtonClickEventType.MOUSE_UP.name())
		{
			/*
			 * Current movement should be stopped.
			 */
			movement = Movement.NONE;
		}


		return movement;		
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isSpriteAtEndOfScreen()
	{
		return ((this.getLocation().x + this.getBounds().getWidth()) > GUIConstants.WIDTH);
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isSpriteAtStartOfScreen()
	{
		return (this.getLocation().x <= 0);
	}
}
