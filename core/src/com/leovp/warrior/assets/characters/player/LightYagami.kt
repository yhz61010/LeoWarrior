package com.leovp.warrior.assets.characters.player

import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 16:04
 */
class LightYagami(x: Float, y: Float) : Character(x, y, Assets.lightYagamiTextureRegion) {
    override fun getTagName() = TAG

    companion object {
        private const val TAG = "LightYagami"
    }
}