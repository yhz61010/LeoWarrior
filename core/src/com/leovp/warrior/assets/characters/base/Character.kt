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
abstract class Character(x: Float, y: Float, private val textureRegion: Array<Array<TextureRegion>>) :
    DynamicGameObject(x, y, textureRegion[0][0].regionWidth * SCALE, textureRegion[0][0].regionHeight * SCALE) {

    abstract fun getTagName(): String
//    val tag: String by lazy { getTagName() }

    private val facingWalkAnim: Animation<TextureRegion> = Animation(1f / 10, *textureRegion[0]).apply { playMode = Animation.PlayMode.LOOP }
    private val backingWalkAnim: Animation<TextureRegion> = Animation(1f / 10, *textureRegion[3]).apply { playMode = Animation.PlayMode.LOOP }
    private val leftWalkAnim: Animation<TextureRegion> = Animation(1f / 10, *textureRegion[1]).apply { playMode = Animation.PlayMode.LOOP }
    private val rightWalkAnim: Animation<TextureRegion> = Animation(1f / 10, *textureRegion[2]).apply { playMode = Animation.PlayMode.LOOP }

    /** Time since the animation has started. */
    private var stateTime = 0f

    var status = Status.FACING_IDLE

    init {
        velocity.set(22f, 22f)
        accel.set(4f, 4f)

        scaleCollisionBounds(0.72f, 0.95f)
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

    fun moveLeft(destX: Float): Boolean {
        return if (x <= destX) {
            status = Status.LEFT_IDLE
            true
        } else {
            status = Status.LEFT_WALK
            false
        }
    }

    fun moveRight(destX: Float): Boolean {
        return if (x >= destX) {
            status = Status.RIGHT_IDLE
            true
        } else {
            status = Status.RIGHT_WALK
            false
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
        updateCollisionPosition(position, 1.1f, 0f)
    }

    fun render(sb: SpriteBatch) {
        val keyFrame = when (status) {
            Status.FACING_WALK -> facingWalkAnim.getKeyFrame(stateTime)
            Status.BACKING_WALK -> backingWalkAnim.getKeyFrame(stateTime)
            Status.LEFT_WALK -> leftWalkAnim.getKeyFrame(stateTime)
            Status.RIGHT_WALK -> rightWalkAnim.getKeyFrame(stateTime)

            Status.FACING_IDLE -> textureRegion[0][0]
            Status.BACKING_IDLE -> textureRegion[3][0]
            Status.LEFT_IDLE -> textureRegion[1][0]
            Status.RIGHT_IDLE -> textureRegion[2][0]
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

        const val FRAME_ROWS = 4
        const val FRAME_COLS = 4
    }
}