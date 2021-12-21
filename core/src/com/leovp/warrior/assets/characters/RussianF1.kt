package com.leovp.warrior.assets.characters

import com.badlogic.gdx.math.MathUtils
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 15:27
 */
class RussianF1(x: Float, y: Float) : Character(x, y, Assets.russianF1TextureRegion) {
    override fun getTagName() = TAG

    init {
        velocity.set(10f, 10f)
        updateFrameDuration(1f / 6)
    }

    override fun update(dt: Float) {
        stateTime += dt
        val dist = (MathUtils.sin(stateTime) + MathUtils.cos(stateTime)) * velocity.y * dt
//        Gdx.app.log(TAG, "${MathUtils.sin(stateTime)}")

        status = if (dist >= 0) {
            Status.BACKING_WALK
        } else {
            Status.FACING_WALK
        }
        position.add(0f, dist)
        adjustPosAndUpdate()
    }

    companion object {
        private const val TAG = "RussianF1"
    }
}