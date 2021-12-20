package com.leovp.warrior.assets.characters

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.warrior.World
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.framework.DynamicGameObject

/**
 * Author: Michael Leo
 * Date: 2021/12/20 09:14
 */
class Baker(x: Float, y: Float) : DynamicGameObject(
    x, y,
    Assets.bakerTextureRegion[0][0].regionWidth * SCALE, Assets.bakerTextureRegion[0][0].regionHeight * SCALE
) {
    private val facingIdle: TextureRegion = Assets.bakerTextureRegion[0][0]
    private val backingIdle: TextureRegion = Assets.bakerTextureRegion[3][0]
    private val leftIdle: TextureRegion = Assets.bakerTextureRegion[1][0]
    private val rightIdle: TextureRegion = Assets.bakerTextureRegion[2][0]

    private val facingWalkFrames: Array<TextureRegion> = Assets.bakerTextureRegion[0]
    private val backingWalkFrames: Array<TextureRegion> = Assets.bakerTextureRegion[3]
    private val leftWalkFrames: Array<TextureRegion> = Assets.bakerTextureRegion[1]
    private val rightWalkFrames: Array<TextureRegion> = Assets.bakerTextureRegion[2]

    private val facingWalkAnim: Animation<TextureRegion> = Animation(1f / 9, *facingWalkFrames).apply { playMode = Animation.PlayMode.LOOP }
    private val backingWalkAnim: Animation<TextureRegion> = Animation(1f / 9, *backingWalkFrames).apply { playMode = Animation.PlayMode.LOOP }
    private val leftWalkAnim: Animation<TextureRegion> = Animation(1f / 9, *leftWalkFrames).apply { playMode = Animation.PlayMode.LOOP }
    private val rightWalkAnim: Animation<TextureRegion> = Animation(1f / 9, *rightWalkFrames).apply { playMode = Animation.PlayMode.LOOP }

    /** Time since the animation has started. */
    private var stateTime = 0f

    var status = Status.FACING_IDLE

    init {
        velocity.set(14f, 14f)
        accel.set(0.05f, 0.05f)
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
        bounds.setPosition(position)
    }

    fun render(sb: SpriteBatch) {
        val keyFrame = when (status) {
            Status.FACING_WALK -> facingWalkAnim.getKeyFrame(stateTime)
            Status.BACKING_WALK -> backingWalkAnim.getKeyFrame(stateTime)
            Status.LEFT_WALK -> leftWalkAnim.getKeyFrame(stateTime)
            Status.RIGHT_WALK -> rightWalkAnim.getKeyFrame(stateTime)

            Status.FACING_IDLE -> facingIdle
            Status.BACKING_IDLE -> backingIdle
            Status.LEFT_IDLE -> leftIdle
            Status.RIGHT_IDLE -> rightIdle
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
        private const val TAG = "Baker"

        /** Scaling factor for the texture. */
        private const val SCALE = 0.2f
        val FRAME_ROWS = 4
        val FRAME_COLS = 4
    }
}