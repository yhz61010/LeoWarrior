package com.leovp.warrior.assets.characters

import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 09:14
 */
class Baker(x: Float, y: Float) : Character(x, y, Assets.bakerTextureRegion) {
    override fun getTagName() = TAG

    companion object {
        private const val TAG = "Baker"
    }
}