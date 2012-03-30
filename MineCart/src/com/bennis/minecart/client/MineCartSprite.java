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
import com.bennis.minecart.client.engine.logic.Geometry;
import com.bennis.minecart.client.engine.logic.ImageLoader;
import com.bennis.minecart.client.engine.logic.InputEvent;
import com.bennis.minecart.client.engine.logic.PlatformUtility;
import com.bennis.minecart.client.engine.model.BasicSprite;
import com.bennis.minecart.client.engine.model.ISprite;
import com.bennis.minecart.client.engine.model.Layer.Layers;
import com.bennis.minecart.client.engine.model.GamePointCounterSprite;
import com.bennis.minecart.client.engine.model.Line;
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
	/*
	 * Game counters
	 */
	GamePointCounterSprite _livesCounter;
	GamePointCounterSprite _scoreCounter;
	
	/*
	 * Test code
	 */
	private Line _cartAxisLine;
	private Line _horizontalLine;
	
	/**
	 * Constructor
	 */
	public MineCartSprite(ImageLoader imageLoader,Scene scene)
	{
		super(Layers.FRONT,imageLoader, Type.USER_MOVEABLE);
		_scene = scene;
		this.loadAllImageSequences();
		this.setLocation(150, 372); // Starting position
		_scoreCounter = CounterManager.getInstance().getCounter(GUIConstants.GAME_POINTS_COUNTER_NAME);
		_livesCounter = CounterManager.getInstance().getCounter(GUIConstants.GAME_LIVES_COUNTER_NAME);
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
						this.collide(_movement);
					}
					
					
					break;
				}
				case OBSTACLE:
				{	
					this.startNewMovement(Movement.FALL, SpriteState.COLLIDE);
					
					break;
				}
				case PLATFORM:
				{	
					Platform platform = (Platform)collisionSprite;
					PlatformUtility.alignVectorToPlatform(this.getLocation(),platform);
					
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
	
	/**
	 * Collide with MineCark when you hit an ENEMY
	 * or OBSTACLE.
	 * 
	 * Hitting these sprites affect the current movement of the Mine Cart.
	 * This is handled here.
	 * 
	 * For example, if the cart was moving right, hitting an obstacle would cause it
	 * to bounce back to the left.
	 * 
	 * Note: MineCart will FALL once it's new movement is set.
	 * 
	 * @param movement
	 */
	private void collide(Movement movement)
	{
		
		double x = this.getLocation().x;
		double y = this.getLocation().y;
		
		final double BUMP_HEIGHT = 60;
		
		/*
		 * TODO AB 
		 * If we make the scroll speed changeable, this needs to be updated.
		 * For example: 
		 * this.setLocation(x + GUIConstants.MEDIUM_SCROLL_SPEED*2, y - BUMP_HEIGHT);
		 * should become:
		 * this.setLocation(x + scrollSpeed*2, y - BUMP_HEIGHT);
		 * or something like that.
		 */
		switch (_movement) 
		{
			case NONE:
			{
				this.setLocation(x, y + 20); // TODO AB Test this
				break;
			}
			case LEFT:
			{
				this.setLocation((x + GUIConstants.MEDIUM_SCROLL_SPEED*2), y - BUMP_HEIGHT);
				break;
			}
			case LEFT_JUMP:
			{
				this.setLocation((x + GUIConstants.MEDIUM_SCROLL_SPEED*2), y - BUMP_HEIGHT);
				break;
			}
			case RIGHT:
			{
				this.setLocation((x - GUIConstants.MEDIUM_SCROLL_SPEED*2), y - BUMP_HEIGHT);
				break;
			}
			case RIGHT_JUMP:
			{
				this.setLocation((x - GUIConstants.MEDIUM_SCROLL_SPEED*2), y - BUMP_HEIGHT);
				break;
			}
			default:
			{
				break;
			}
		}
		
		/*
		 * TODO AB Check if Sprite is now crushed against the start of the screen.
		 * If so, he must die.
		 */
		
		this.startNewMovement(Movement.FALL, SpriteState.COLLIDE);
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
			
			/*
			 * Rotate MineCart so it sits on the Platform
			 * TODO AB - What about collisions? The bounds need to be rotated too.
			 */
			
			/*
			 * TODO AB - Translate to the center of the mine cart, as we want to rotate around that.
			 */
			canvas.getContext2d().setFillStyle("white");
			canvas.getContext2d().fillText("Current Rotation: " + rotationAngle, 10, 200);
			
			canvas.getContext2d().save();
			canvas.getContext2d().translate(this.getLocation().x, this.getLocation().y);
			canvas.getContext2d().rotate(rotationAngle);

			/*
			 * Draw Sprite Image
			 */
			canvas.getContext2d().drawImage(currentFrame, 0,0);//this.getLocation().x, this.getLocation().y);
			
			/*
			 * End rotation
			 */
			canvas.getContext2d().rotate(-rotationAngle);
			canvas.getContext2d().restore();
			
			/*
			 * Test
			 * Draw the Cart Axis as we've calculated it.
			 */
			canvas.getContext2d().setFillStyle("red");
			canvas.getContext2d().beginPath();
			canvas.getContext2d().setLineWidth(90);
			canvas.getContext2d().beginPath();
			canvas.getContext2d().moveTo(_cartAxisLine.getX(), _cartAxisLine.getY());
			canvas.getContext2d().lineTo(_cartAxisLine.getX1(), _cartAxisLine.getY1());
			canvas.getContext2d().fill();
			canvas.getContext2d().stroke();
			canvas.getContext2d().closePath();
			
			canvas.getContext2d().setFillStyle("green");
			canvas.getContext2d().beginPath();
			canvas.getContext2d().setLineWidth(70);
			canvas.getContext2d().beginPath();
			canvas.getContext2d().moveTo(_horizontalLine.getX(), _horizontalLine.getY());
			canvas.getContext2d().lineTo(_horizontalLine.getX1(), _horizontalLine.getY1());
			canvas.getContext2d().stroke();
			canvas.getContext2d().closePath();
			
			/*
			 * END TEST
			 */
			
			
			// We update animation every second refresh.
			_updateFrame = !_updateFrame; 
			if (_updateFrame)
			{
				_currentAnimationFrame++;
			}
			
			
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private double calculateRotationAngle()
	{
		double angle = 0;
		
		/*
		 * There's only rotation when moving left or right, and NONE.
		 * 0 rotation for Jumping or falling. 
		 * 
		 * When fall ends, there's a rotation, so
		 * Movement.NONE has a rotation.
		 */
		if (_movement == Movement.NONE || _movement == Movement.LEFT || _movement == Movement.RIGHT)
		{
			/*
			 * Find back wheel x and y point (on Platform directly below)
			 */
			Vector alignedBackWheelPoint = PlatformUtility.alignVectorToNearestPlatform(_scene, this.getLocation());
			double x = alignedBackWheelPoint.x;
			double y = alignedBackWheelPoint.y + this.getBounds().getHeight();
			
			/*
			 * Find front wheel x and y point (on Platform directly below)
			 */
			double x1 = this.getLocation().x + this.getBounds().getWidth();
			
			Vector alignedFrontWheelPoint = PlatformUtility.alignVectorToNearestPlatform(_scene, x1,y);
			
			double x2 = alignedFrontWheelPoint.x;
			double y2 = alignedFrontWheelPoint.y + this.getBounds().getHeight();
			
			Line horizontalLine = new Line(this.getLocation().x,this.getLocation().y,x1,this.getLocation().y); // Cart axis before it's aligned to Platform
			Line cartAxisLine = new Line(x,y,x2, y2 + 50); // Cart axis aligned to Platform slope.
			
			/*
			 * Test
			 */
			_cartAxisLine = cartAxisLine;
			_horizontalLine = horizontalLine;
			
			angle = Geometry.angleBetween2Lines(cartAxisLine, horizontalLine);
//			angle = Geometry.invertAngle(angle);
		}
		
		return angle;
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
