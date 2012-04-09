/*******************************************************************************
 * Copyright (c) 2012 Anthony Bennis
 * All rights reserved. This program and the accompanying materials
 * are fully owned by Anthony Bennis and cannot be used for commercial projects without prior permission from same.
 * http://www.anthonybennis.com
 *
 * Contributors:
 * Anthony Bennis.
 *******************************************************************************/
package com.bennis.minecart.client;

import com.bennis.minecart.client.ButtonPanel.ButtonClickEventType;
import com.bennis.minecart.client.engine.logic.CounterManager;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.logic.PlatformUtility;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.GamePointCounterSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Line;
import com.bennis.minecart.client.engine.model.Platform;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.Rectangle;
import com.bennis.minecart.client.engine.model.Scene;
import com.bennis.minecart.client.engine.model.Vector;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.touch.client.Point;

/**
 * Main Sprite.
 * @author abennis
 */
public class MineCartSprite extends BasicSprite 
{	
	private static final int MINE_CART_HEIGHT = 80;

	private Scene _scene;
	
	/*
	 * Wheels
	 */
	private Vector _platformAlignedLEFTWheelLocation;
	private Vector _platformAlignedRIGHTWheelLocation;
	private static final int WHEEL_AXIS_THICKNESS = 5;
	private static final double WHEEL_RADIUS = 20;
	
	/*
	 * States
	 */
	private enum Movement{LEFT, LEFT_JUMP,RIGHT,RIGHT_JUMP, FALL, NONE, STOP, BOUNCE_LEFT, BOUNCE_RIGHT, COLLIDE};
	private enum SpriteState{NORMAL, COLLIDE, FALLING};
	
	/*
	 * Animation
	 */
	private ImageElement[] _currentAnmationSequence;
	private ImageElement[] _movingRightAnmationSequence;
	private ImageElement[] _movingLeftAnmationSequence;
	private ImageElement[] _collidingAnmationSequence;
	private ImageElement[] _wheelAnmationSequence;
	private ImageElement _blinkImage;
	private int _currentAnimationFrame = 0;
	private int _currentWheelAnimationFrame = 0;
	private int _blinkCounter = 0;
	private SpriteState _spriteState = SpriteState.NORMAL;
	private boolean _updateFrame;
	
	
	/*
	 * Movement
	 */
	private Movement _movement = Movement.NONE;
	private double _endX;
	private double _endY;
	private double _startX;
	private double _startY;
	private final double MOVE_DISTANCE = 50; 
	private final double MOVE_SPEED = 5;
	private final double JUMP_HORIZONTAL_DISTANCE = 150;
	private final double FALL_SPEED = MOVE_SPEED*2;
	private enum Bounce{LEFT,RIGHT};
	private final int BOUNCE_HEIGHT = 50;
	/*
	 * Game counters
	 */
	GamePointCounterSprite _livesCounter;
	GamePointCounterSprite _scoreCounter;
	
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader,Scene scene)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		_scene = scene;
		this.loadAllImageSequences();
		this.setInitialLocation();
		_scoreCounter = CounterManager.getInstance().getCounter(GUIConstants.GAME_POINTS_COUNTER_NAME);
		_livesCounter = CounterManager.getInstance().getCounter(GUIConstants.GAME_LIVES_COUNTER_NAME);
	}
	
	private void setInitialLocation()
	{
		/*
		 * Set wheel Location
		 */
		_platformAlignedLEFTWheelLocation = new Vector();
		_platformAlignedLEFTWheelLocation.x = 150;
		_platformAlignedLEFTWheelLocation.y = 497;
		
		_platformAlignedRIGHTWheelLocation = new Vector();
		_platformAlignedRIGHTWheelLocation.x = _platformAlignedLEFTWheelLocation.x + 125;
		_platformAlignedRIGHTWheelLocation.y = 497;
		
		/*
		 * Set Mine Cart Location
		 */
		this.setLocationOfCartBasedOnWheelLocation();
	}
	
	/**
	 * 
	 */
	private void loadAllImageSequences()
	{
		_movingRightAnmationSequence = this.createMovingRightAnimationSequence();
		_movingLeftAnmationSequence = this.createMovingLeftAnimationSequence();
		_collidingAnmationSequence = this.createCollidingAnimationSequence();
		_wheelAnmationSequence = this.createWheelAnimationSequence();
		_blinkImage = this.getImageLoader().getImage("images/minecart/Podge00Blink.png");
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
	
	
	private ImageElement[] createWheelAnimationSequence()
	{
		String[] imageNames = new String[3];
		imageNames[0] = "images/minecart/Wheel00.png";
		imageNames[1] = "images/minecart/Wheel01.png";
		imageNames[2] = "images/minecart/Wheel02.png";
		
		return this.createAnimationSequence(imageNames);
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
					 * NO MOVEMENT
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
				case BOUNCE_LEFT:
				{
					_spriteState = this.bounceLeft(endofScreen, startofScreen);
					break;	
				}
				case BOUNCE_RIGHT:
				{
					_spriteState = this.bounceRight(endofScreen, startofScreen);
					break;	
				}
				case COLLIDE:
				{
					_spriteState = this.collideWithObstacle(endofScreen, startofScreen);
					break;
				}
				case NONE:
				{
					/*
					 * The Platform moves, even if the cart doesn't.
					 * The carts position needs to respond to the Platform.
					 */
					this.alignWheelsToPlatform();
					this.setLocationOfCartBasedOnWheelLocation();
					
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
	 * Wheels of the MineCart must always touch the mine cart.
	 */
	private void alignWheelsToPlatform()
	{
		_platformAlignedLEFTWheelLocation = PlatformUtility.alignVectorToNearestPlatform(_scene, _platformAlignedLEFTWheelLocation);
		_platformAlignedRIGHTWheelLocation = PlatformUtility.alignVectorToNearestPlatform(_scene, _platformAlignedRIGHTWheelLocation);
		/*
		 * We move the wheels y position up, as we don't want the center of the wheel 
		 * on the Platform, we want the wheel to look like it's resting on the Platform
		 */
		_platformAlignedLEFTWheelLocation.y = _platformAlignedLEFTWheelLocation.y - WHEEL_RADIUS;
		_platformAlignedRIGHTWheelLocation.y = _platformAlignedRIGHTWheelLocation.y - WHEEL_RADIUS;
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
		Vector alignedVector = PlatformUtility.alignVectorToNearestPlatform(_scene, this.getLocation());
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
					/*
					 *  We don't calculate an end point for Jumping. The movement
					 *  ends when the left wheel intersects with a Platform line.
					 */
				}
				else
				{
					// There's no platform to move to! 
					this.startNewMovement(Movement.FALL, _spriteState);
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
					// There's no platform to move to! 
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
					this.startNewMovement(Movement.FALL, _spriteState);
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
			case BOUNCE_LEFT:
			{
				/*
				 * Note: We don't use the current aligned values,
				 * as we bounce up in the air, then fall. So
				 * our end point is not on the platform, it's
				 * somewhere in the air.
				 */				
				_endX = this.getLocation().x - JUMP_HORIZONTAL_DISTANCE;
				_endY = this.getLocation().y - BOUNCE_HEIGHT;
				
				break;
			}
			case BOUNCE_RIGHT:
			{
				/*
				 * Note: We don't use the current aligned values,
				 * as we bounce up in the air, then fall. So
				 * our end point is not on the platform, it's
				 * somewhere in the air.
				 */	
				_endX = this.getLocation().x + JUMP_HORIZONTAL_DISTANCE;
				_endY = this.getLocation().y - BOUNCE_HEIGHT;
			}
			case COLLIDE:
			{
				/*
				 * TODO AB Collide
				 */
				break;
			}
			default:
			{
				break;
			}
		}
	}
	
	/**
	 * The wheels dictate the relative position of 
	 * the cart and character in Mine.
	 */
	private void setLocationOfCartBasedOnWheelLocation()
	{
		double leftWheelHeight = _platformAlignedLEFTWheelLocation.y;
		double rightWheelHeight = _platformAlignedRIGHTWheelLocation.y;
		
		double height = this.getBounds().getHeight();
		double xLocation = _platformAlignedLEFTWheelLocation.x; // TODO AB Revisit this relative position. Maybe move it over a bit?
		double yLocation = (leftWheelHeight <  rightWheelHeight)?leftWheelHeight:rightWheelHeight;
		
		yLocation =  yLocation - (height); // TODO AB Review: Is this okay? Do we need to make it higher?
		this.setLocation(xLocation,yLocation);
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
			 * Move the wheels horizontally to the LEFT
			 */
			
			// LEFT wheel
			double x = _platformAlignedLEFTWheelLocation.x;
			x = x - MOVE_SPEED;
			_platformAlignedLEFTWheelLocation.x = x;
			
			// Right Wheel
			x = _platformAlignedRIGHTWheelLocation.x;
			x = x - MOVE_SPEED;
			_platformAlignedRIGHTWheelLocation.x = x;
			
			this.alignWheelsToPlatform();
			this.setLocationOfCartBasedOnWheelLocation();
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
		/*
		 * TODO AB
		 * Jump needs to end when cart intersects Platform.
		 */
		if (!startofScreen)
		{			
			_platformAlignedLEFTWheelLocation.x = _platformAlignedLEFTWheelLocation.x - MOVE_SPEED; 
			_platformAlignedRIGHTWheelLocation.x = _platformAlignedRIGHTWheelLocation.x - MOVE_SPEED;
			
			
			double distanceTravelled = (this._startX - _platformAlignedLEFTWheelLocation.x);
			
			if (distanceTravelled < JUMP_HORIZONTAL_DISTANCE/2)
			{
				/*
				 * Jump up
				 */
				_platformAlignedLEFTWheelLocation.y = _platformAlignedLEFTWheelLocation.y - MOVE_SPEED; 
				_platformAlignedRIGHTWheelLocation.y = _platformAlignedRIGHTWheelLocation.y - MOVE_SPEED;
			}
			else
			{
				/*
				 * Fall back down
				 */
				_platformAlignedLEFTWheelLocation.y = _platformAlignedLEFTWheelLocation.y + MOVE_SPEED; 
				_platformAlignedRIGHTWheelLocation.y = _platformAlignedRIGHTWheelLocation.y + MOVE_SPEED;
			}
			
			this.setLocationOfCartBasedOnWheelLocation();
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
		 * TODO AB Stop when left wheel intersects with a Platform line
		 */		
		if (this._platformAlignedLEFTWheelLocation.y == _endY
				&& this._platformAlignedLEFTWheelLocation.x == _endX)
		{
			/*
			 * Ensure we land on track correctly.
			 */
			
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
			 * Move the wheels horizontally to the RIGHT
			 */
			
			// LEFT wheel
			double x = _platformAlignedLEFTWheelLocation.x;
			x = x + MOVE_SPEED;
			_platformAlignedLEFTWheelLocation.x = x;
			
			// Right Wheel
			x = _platformAlignedRIGHTWheelLocation.x;
			x = x + MOVE_SPEED;
			_platformAlignedRIGHTWheelLocation.x = x;
			
			this.alignWheelsToPlatform();
			this.setLocationOfCartBasedOnWheelLocation();
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
			/*
			 * TODO AB
			 * Jump needs to end when cart intersects Platform.
			 */
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
	
	/**
	 * When we hit an enemy or obstacle, we must bounce
	 * back/forward (depending on location of obstacle and cart).
	 * This allows the cart to escape danger, and getting hit multiple
	 * times by the same collision.
	 * 
	 * A "Bounce" pushes the MineCart up and back, and then it just falls.
	 * 
	 * @return SpriteState
	 */
	private SpriteState bounceLeft(boolean endofScreen, boolean startofScreen)
	{
		if (!startofScreen)
		{	
			this.getLocation().y = this.getLocation().y - FALL_SPEED;
			this.getLocation().x = this.getLocation().x - FALL_SPEED;

			/*
			 * Conditions to end  movement 
			 */
			if (this.getLocation().y <= _endY)
			{
				this.startNewMovement(Movement.FALL, _spriteState);
			}
		}
		else
		{
			/*
			 * Reached end/start of screen. Just fall down.
			 */
			this.startNewMovement(Movement.FALL, _spriteState);
		}
		
		return _spriteState;
	}
	
	/**
	 * When we hit an enemy or obstacle, we must bounce
	 * back/forward (depending on location of obstacle and cart).
	 * This allows the cart to escape danger, and getting hit multiple
	 * times by the same collision.
	 * 
	 * A "Bounce" pushes the MineCart up and back, and then it just falls.
	 * 
	 * 
	 * @return SpriteState
	 */
	private SpriteState bounceRight(boolean endofScreen, boolean startofScreen)
	{
		if (!startofScreen)
		{	
			this.getLocation().y = this.getLocation().y - FALL_SPEED;
			this.getLocation().x = this.getLocation().x + FALL_SPEED;

			/*
			 * Conditions to end  movement 
			 */
			if (this.getLocation().y <= _endY)
			{
				this.startNewMovement(Movement.FALL, _spriteState);
			}
		}
		else
		{
			/*
			 * Reached end/start of screen. Just fall down.
			 */
			this.startNewMovement(Movement.FALL, _spriteState);
		}
		
		return _spriteState;
	}
	
	private SpriteState collideWithObstacle(boolean endofScreen, boolean startofScreen)
	{
		/*
		 * TODO AB collideWithObstacle
		 * See: handleCollison for pseudo code.
		 */
		return _spriteState;
	}
	
	@Override
	public void handleCollision(ISprite collisionSprite,Collision collisionType) 
	{
		
		if (collisionSprite != null)
		{
			switch (collisionSprite.getType()) 
			{
				case GOODIE:
				{	
					_scoreCounter.incrementValue(1); // All Goodies currently have the same value.
					collisionSprite.dispose();
					break;
				}
				case ENEMY:
				{	
					_livesCounter.decrementValue(1);
					
					if (_livesCounter.getValue() <= 0)
					{
						// TODO AB End Game.
					}
					else
					{
						Bounce bounceDirection = this.calculateBounceDirection(collisionSprite);
						
						if (bounceDirection == Bounce.LEFT)
						{
							this.startNewMovement(Movement.BOUNCE_LEFT, SpriteState.COLLIDE);	
						}
						else
						{
							this.startNewMovement(Movement.BOUNCE_RIGHT, SpriteState.COLLIDE);
						}
					}
					
					break;
				}
				case OBSTACLE:
				{	
					/*
					 * TODO AB Handle Collide with obstacle
					 * - stop a current movement
					 * - Push MineCart Left if obstacle is on the right
					 * - Squash/Kill if at the start of the screen
					 * 
					 */
					this.startNewMovement(Movement.COLLIDE, SpriteState.COLLIDE);
					break;
				}
				case PLATFORM:
				{	
					/*
					 *  We don't collide with the Platform. It has no "Bounds".
					 *  Instead, we move and align.
					 * 
					 * TODO AB - When we're jumping/Falling, we need to know if we hit
					 * the Platform. Falling is easy, but Jumping? Maybe see if the wheel,
					 * taking it's radius into account, intersects a Platform line?
					 */
					
					break;
				}
				default:
				{
					// Nothing else to do.
					break;
				}
			}
		}
	}

	@Override
	protected String[] getImageNames() 
	{
		/*
		 * TODO AB Refactor In Future?
		 * This is a multi animation Sprite. We should
		 * examine extending BasicSprite to handle this.
		 * 
		 * TODO AB - Animate
		 */
		String[] imageNames = new String[5];
		imageNames[0] = "images/minecart/Podge00.png";
		imageNames[1] = "images/minecart/Podge01.png";
		imageNames[2] = "images/minecart/Podge02.png";
		imageNames[3] = "images/minecart/Podge01.png";
		imageNames[4] = "images/minecart/Podge00.png";
		
		return imageNames;
	}

	@Override
	public void draw(Canvas canvas) 
	{
		/*
		 * TODO AB Optimise MineCartSprite by reducing calculations
		 * Maybe we can optimise this, by calculating alignment with Platform
		 * every four updates or something?
		 */
		
		/*
		 * Ensure current images are loaded.
		 */
		if (!this.haveAllImagesLoaded(_currentAnmationSequence)
				|| !this.haveAllImagesLoaded(_wheelAnmationSequence))
		{
			this.loadAllImageSequences();
		}
		else
		{
			/*
			 * Draw Podge Sprite
			 */
			if (_currentAnimationFrame >= _currentAnmationSequence.length)
			{
				_currentAnimationFrame = 0; //Reset animation.
			}
			final int PODGE_SPRITE_POS_Y_OFFSET = 120;
			canvas.getContext2d().setFillStyle("white");
			ImageElement currentFrame;

			/*
			 * Podge sprite blinks every few seconds.
			 */
			if (_currentAnimationFrame == 0 && _blinkCounter ==10) 
			{
				currentFrame = _blinkImage;
			}
			else
			{
				currentFrame = _currentAnmationSequence[_currentAnimationFrame];
			}
			
			_blinkCounter = (_blinkCounter == 60)?0:_blinkCounter; // Reset Blink counter when it hits 30
			
			canvas.getContext2d().drawImage(currentFrame, this.getLocation().x, (this.getLocation().y - (PODGE_SPRITE_POS_Y_OFFSET)));
			
			/*
			 * Draw Mine Cart Side Panel
			 */
			canvas.getContext2d().setFillStyle("brown");
//			CanvasGradient gradient = canvas.getContext2d().createLinearGradient(0, 0, 10, 20);
//			gradient.addColorStop(0.5f, "#ffaaff");
//			canvas.getContext2d().setFillStyle(gradient);
			
			canvas.getContext2d().beginPath();
			canvas.getContext2d().setLineWidth(WHEEL_AXIS_THICKNESS);
			canvas.getContext2d().moveTo(_platformAlignedLEFTWheelLocation.x, _platformAlignedLEFTWheelLocation.y);
			canvas.getContext2d().lineTo((_platformAlignedLEFTWheelLocation.x - WHEEL_AXIS_THICKNESS), _platformAlignedLEFTWheelLocation.y - MINE_CART_HEIGHT);
			canvas.getContext2d().lineTo((_platformAlignedRIGHTWheelLocation.x + WHEEL_AXIS_THICKNESS), _platformAlignedRIGHTWheelLocation.y - MINE_CART_HEIGHT);
			canvas.getContext2d().lineTo((_platformAlignedRIGHTWheelLocation.x), _platformAlignedRIGHTWheelLocation.y);
			canvas.getContext2d().closePath();
			canvas.getContext2d().fill();
			
			
			/*
			 * Draw Mine Cart Border
			 */
			canvas.getContext2d().setFillStyle("#000000");
			canvas.getContext2d().beginPath();
			canvas.getContext2d().setLineWidth(WHEEL_AXIS_THICKNESS);
			canvas.getContext2d().moveTo(_platformAlignedLEFTWheelLocation.x, _platformAlignedLEFTWheelLocation.y);
			canvas.getContext2d().lineTo((_platformAlignedLEFTWheelLocation.x - (WHEEL_AXIS_THICKNESS + 2)), _platformAlignedLEFTWheelLocation.y - MINE_CART_HEIGHT);
			canvas.getContext2d().lineTo((_platformAlignedRIGHTWheelLocation.x + (WHEEL_AXIS_THICKNESS + 2)), _platformAlignedRIGHTWheelLocation.y - MINE_CART_HEIGHT);
			canvas.getContext2d().lineTo((_platformAlignedRIGHTWheelLocation.x), _platformAlignedRIGHTWheelLocation.y);
			canvas.getContext2d().closePath();
			canvas.getContext2d().stroke();
			
			canvas.getContext2d().setFillStyle("white");
			
			/*
			 * TODO AB Refactor frame rate code into BasicSprite as it's used twice now.
			 */
			if (_currentWheelAnimationFrame >= _wheelAnmationSequence.length)
			{
				_currentWheelAnimationFrame = 0; //Reset animation.
			}
			
			currentFrame = _wheelAnmationSequence[_currentWheelAnimationFrame];
			
			/*
			 * Draw Left wheel
			 */
			double wheelTopLeftXPos = _platformAlignedLEFTWheelLocation.x - (currentFrame.getWidth()/2);
			double wheelTopLeftYPos = _platformAlignedLEFTWheelLocation.y - ((currentFrame.getHeight()/2) + 3);
			
			canvas.getContext2d().drawImage(currentFrame, wheelTopLeftXPos, wheelTopLeftYPos);
			
			/*
			 * Draw Right wheel
			 */
			wheelTopLeftXPos = _platformAlignedRIGHTWheelLocation.x - (currentFrame.getWidth()/2);
			wheelTopLeftYPos = _platformAlignedRIGHTWheelLocation.y - ((currentFrame.getHeight()/2) + 3);
			
			canvas.getContext2d().drawImage(currentFrame, wheelTopLeftXPos, wheelTopLeftYPos);
			
			/*
			 * Draw wheel axis
			 * TODO AB - Change this to draw Wheel Spokes,
			 * or create images for the wheels and animate those.
			 * Dont' forget animate forward and back!!
			 */
//			Line leftWheelAxis = new Line();
//			leftWheelAxis.setX(_platformAlignedLEFTWheelLocation.x);
//			leftWheelAxis.setY(_platformAlignedLEFTWheelLocation.y);
//			leftWheelAxis.setX1(this.getBounds().getCenter().x);
//			leftWheelAxis.setY1(this.getBounds().getCenter().y);
//			
//			Line rightWheelAxis = new Line();
//			rightWheelAxis.setX(_platformAlignedRIGHTWheelLocation.x);
//			rightWheelAxis.setY(_platformAlignedRIGHTWheelLocation.y);
//			rightWheelAxis.setX1(this.getBounds().getCenter().x);
//			rightWheelAxis.setY1(this.getBounds().getCenter().y);
//			
//			canvas.getContext2d().beginPath();
//			canvas.getContext2d().setLineWidth(WHEEL_AXIS_THICKNESS);
//			canvas.getContext2d().moveTo(leftWheelAxis.getX(), leftWheelAxis.getY());
//			canvas.getContext2d().lineTo(leftWheelAxis.getX1(), leftWheelAxis.getY1());
//			canvas.getContext2d().closePath();
//			canvas.getContext2d().stroke();
//			
//			canvas.getContext2d().beginPath();
//			canvas.getContext2d().setLineWidth(WHEEL_AXIS_THICKNESS);
//			canvas.getContext2d().moveTo(rightWheelAxis.getX(), rightWheelAxis.getY());
//			canvas.getContext2d().lineTo(rightWheelAxis.getX1(), rightWheelAxis.getY1());
//			canvas.getContext2d().closePath();
//			canvas.getContext2d().stroke();
			// End Draw Wheel axis.
			
			/*
			 * Temp - Draw Bounds
			 */
//			Rectangle bounds = this.getBounds();
//			canvas.getContext2d().strokeRect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
			
			// We update animation frame every second refresh.
			_updateFrame = !_updateFrame; 
			
			if (_updateFrame)
			{
				_currentAnimationFrame++;
				_currentWheelAnimationFrame++;
				_blinkCounter++;
			}
		}
	}
	
	/**
	 * The Mine cart will bounce LEFT/RIGHT
	 * depending on the location of the cart and
	 * the sprite it collides with.
	 * 
	 * @return
	 */
	private Bounce calculateBounceDirection(ISprite collisionSprite)
	{
		Bounce direction = Bounce.LEFT;
		
		Vector collisionSpriteCenter = collisionSprite.getBounds().getCenter();
		Vector mineCartCenter = this.getBounds().getCenter();
		
		if (collisionSpriteCenter.x < mineCartCenter.x)
		{
			direction = Bounce.RIGHT;
		}
		
		return direction;
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
					 * FALL, NONE, BOUNCE, COLLIDE
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
	
	@Override
	public Rectangle getBounds() 
	{
		_bounds.setX(this.getLocation().x);
		_bounds.setY(this.getLocation().y);
		/*
		 * Optimised: Calculate Image width and height only once!
		 */
		if (_bounds.getWidth() <= 0 || _bounds.getHeight() <= 0)
		{
			_bounds.setWidth(150);
			/*
			 * TODO AB Calculate height with Podge Sprite in Cart
			 * Delaying this until I think about how Mine cart will be rendered (image or 2D)
			 */
			_bounds.setHeight(MINE_CART_HEIGHT);
		}
		
		return _bounds;
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
