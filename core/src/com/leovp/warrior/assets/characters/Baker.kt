package com.leovp.warrior.assets.characters

import com.badlogic.gdx.math.MathUtils
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 09:14
 */
class Baker(x: Float, y: Float) : Character(x, y, Assets.bakerTextureRegion) {
    override fun getTagName() = TAG

    init {
        velocity.set(8f, 8f)
        updateFrameDuration(1f / 6)
    }

    override fun update(dt: Float) {
        stateTime += dt
        val dist = (MathUtils.sin(stateTime) + MathUtils.cos(stateTime)) * velocity.y * dt
//        Gdx.app.log(TAG, "${MathUtils.sin(stateTime)}")

        status = if (dist >= 0) {
            Status.RIGHT_WALK
        } else {
            Status.LEFT_WALK
        }
        position.add(dist, 0f)
        adjustPosAndUpdate()
    }

    companion object {
        private const val TAG = "Baker"
    }
}