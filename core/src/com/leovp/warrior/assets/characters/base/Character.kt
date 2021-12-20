package com.leovp.warrior.assets.characters.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.warrior.World
import com.leovp.warrior.framework.DynamicGameObject

/**
 * Author: Michael Leo
 * Date: 2021/12/20 13:32
 */
abstract class Character(x: Float, y: Float, width: Float, height: Float) : DynamicGameObject(x, y, width, height) {
    abstract fun getTagName(): String
//    val tag: String by lazy { getTagName() }

    abstract fun getFacingIdle(): TextureRegion
    abstract fun getBackingIdle(): TextureRegion
    abstract fun getLeftIdle(): TextureRegion
    abstract fun getRightIdle(): TextureRegion

    abstract fun getFacingWalkFrames(): Array<TextureRegion>
    abstract fun getBackingWalkFrames(): Array<TextureRegion>
    abstract fun getLeftWalkFrames(): Array<TextureRegion>
    abstract fun getRightWalkFrames(): Array<TextureRegion>

    private val facingWalkAnim: Animation<TextureRegion> by lazy { Animation(1f / 10, *getFacingWalkFrames()).apply { playMode = Animation.PlayMode.LOOP } }
    private val backingWalkAnim: Animation<TextureRegion> by lazy { Animation(1f / 10, *getBackingWalkFrames()).apply { playMode = Animation.PlayMode.LOOP } }
    private val leftWalkAnim: Animation<TextureRegion> by lazy { Animation(1f / 10, *getLeftWalkFrames()).apply { playMode = Animation.PlayMode.LOOP } }
    private val rightWalkAnim: Animation<TextureRegion> by lazy { Animation(1f / 10, *getRightWalkFrames()).apply { playMode = Animation.PlayMode.LOOP } }

    /** Time since the animation has started. */
    private var stateTime = 0f

    var status = Status.FACING_IDLE

    init {
        velocity.set(22f, 22f)
        accel.set(4f, 4f)
    }

    fun handleInput() {
        status = when (status) {
            Status.FACING_WALK -> Status.FACING_IDLE
            Status.BACKING_WALK -> Status.BACKING_IDLE
            Status.LEFT_WALK -> Status.LEFT_IDLE
            Status.RIGHT_WALK -> Status.RIGHT_IDLE
            else -> status
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            status = Status.FACING_WALK
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            status = Status.BACKING_WALK
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            status = Status.LEFT_WALK
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            status = Status.RIGHT_WALK
        }
    }

    fun update(dt: Float) {
        stateTime += dt

        when (status) {
            Status.FACING_WALK -> position.add(0f, -(velocity.y * dt + accel.y * dt))
            Status.BACKING_WALK -> position.add(0f, velocity.y * dt + accel.y * dt)
            Status.LEFT_WALK -> position.add(-(velocity.x * dt + accel.x * dt), 0f)
            Status.RIGHT_WALK -> position.add(velocity.x * dt + accel.x * dt, 0f)
            else -> Unit
        }

        if (position.x + bounds.width >= World.WORLD_WIDTH) position.x = World.WORLD_WIDTH - bounds.width
        if (position.x <= 0) position.x = 0f

        if (position.y + bounds.height >= World.WORLD_HEIGHT) position.y = World.WORLD_HEIGHT - bounds.height
        if (position.y <= 0) position.y = 0f

        bounds.setPosition(position)
    }

    fun render(sb: SpriteBatch) {
        val keyFrame = when (status) {
            Status.FACING_WALK -> facingWalkAnim.getKeyFrame(stateTime)
            Status.BACKING_WALK -> backingWalkAnim.getKeyFrame(stateTime)
            Status.LEFT_WALK -> leftWalkAnim.getKeyFrame(stateTime)
            Status.RIGHT_WALK -> rightWalkAnim.getKeyFrame(stateTime)

            Status.FACING_IDLE -> getFacingIdle()
            Status.BACKING_IDLE -> getBackingIdle()
            Status.LEFT_IDLE -> getLeftIdle()
            Status.RIGHT_IDLE -> getRightIdle()
        }
        sb.draw(keyFrame, position.x, position.y, bounds.width, bounds.height)
    }

    enum class Status {
        FACING_IDLE,
        BACKING_IDLE,
        LEFT_IDLE,
        RIGHT_IDLE,

        FACING_WALK,
        BACKING_WALK,
        LEFT_WALK,
        RIGHT_WALK
    }

    companion object {
        /** Scaling factor for the texture. */
        const val SCALE = 0.2f
    }
}