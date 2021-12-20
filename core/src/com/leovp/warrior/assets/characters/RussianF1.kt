package com.leovp.warrior.assets.characters

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 15:27
 */
class RussianF1(x: Float, y: Float) : Character(
    x, y,
    Assets.russianF1TextureRegion[0][0].regionWidth * SCALE, Assets.russianF1TextureRegion[0][0].regionHeight * SCALE
) {
    override fun getTagName() = TAG

    override fun getFacingIdle(): TextureRegion = Assets.russianF1TextureRegion[0][0]
    override fun getBackingIdle(): TextureRegion = Assets.russianF1TextureRegion[3][0]
    override fun getLeftIdle(): TextureRegion = Assets.russianF1TextureRegion[1][0]
    override fun getRightIdle(): TextureRegion = Assets.russianF1TextureRegion[2][0]

    override fun getFacingWalkFrames(): Array<TextureRegion> = Assets.russianF1TextureRegion[0]
    override fun getBackingWalkFrames(): Array<TextureRegion> = Assets.russianF1TextureRegion[3]
    override fun getLeftWalkFrames(): Array<TextureRegion> = Assets.russianF1TextureRegion[1]
    override fun getRightWalkFrames(): Array<TextureRegion> = Assets.russianF1TextureRegion[2]

    companion object {
        private const val TAG = "RussianF1"
        const val FRAME_ROWS = 4
        const val FRAME_COLS = 4
    }
}