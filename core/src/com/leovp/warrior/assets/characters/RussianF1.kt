package com.leovp.warrior.assets.characters

import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 15:27
 */
class RussianF1(x: Float, y: Float) : Character(x, y, Assets.russianF1TextureRegion) {
    override fun getTagName() = TAG

    companion object {
        private const val TAG = "RussianF1"
    }
}