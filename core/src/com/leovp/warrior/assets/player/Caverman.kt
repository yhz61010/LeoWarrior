package com.leovp.warrior.assets.player

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.leovp.warrior.World
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.framework.DynamicGameObject

class Caverman(x: Float, y: Float) : DynamicGameObject(
    x, y,
    Assets.cavermanIdle[0].regionWidth * SCALE, Assets.cavermanIdle[0].regionHeight * SCALE
) {
    /** Animation representing the bomb. */
    private val idleAnim: Animation<TextureAtlas.AtlasRegion> = Animation(1f / 4, Assets.cavermanIdle).apply { playMode = Animation.PlayMode.LOOP }
    private val walkAnim: Animation<TextureAtlas.AtlasRegion> = Animation(1f / 9, Assets.cavermanWalk).apply { playMode = Animation.PlayMode.LOOP }

    /** Time since the animation has started. */
    private var stateTime = 0f

    var status = Status.IDLE

    init {
        scaleCollisionBounds(0.34f, 0.43f)
//        velocity.y = GRAVITY
    }

    fun update(dt: Float) {
        stateTime += dt

        when (status) {
            Status.WALK -> {
                velocity.set(8f, 0f)
                accel.add(0.02f, 0f)
            }
            else -> {
                velocity.set(0f, 0f)
                accel.add(0f, 0f)
            }
        }
        position.add(velocity.x * dt + accel.x * dt, 0f)

        if (position.x > World.WORLD_WIDTH) position.x = 0f
        bounds.setPosition(position)
        updateCollisionPosition(position, 8.5f, 6.4f)
    }

    fun render(sb: SpriteBatch) {
        when (status) {
            Status.WALK -> sb.draw(walkAnim.getKeyFrame(stateTime), position.x, position.y, bounds.width, bounds.height)
            else -> sb.draw(idleAnim.getKeyFrame(stateTime), position.x, position.y, bounds.width, bounds.height)
        }
    }

    enum class Status {
        IDLE,
        WALK
    }

    companion object {
        /** Scaling factor for the texture. */
        private const val SCALE = 0.2f

        /** Amount of gravity. */
        private const val GRAVITY = -1f
    }
}