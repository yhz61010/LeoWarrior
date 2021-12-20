package com.leovp.warrior.assets.characters

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/12/20 09:14
 */
class Baker(x: Float, y: Float) : Character(
    x, y,
    Assets.bakerTextureRegion[0][0].regionWidth * SCALE, Assets.bakerTextureRegion[0][0].regionHeight * SCALE
) {
    override fun getTagName() = TAG

    override fun getFacingIdle(): TextureRegion = Assets.bakerTextureRegion[0][0]
    override fun getBackingIdle(): TextureRegion = Assets.bakerTextureRegion[3][0]
    override fun getLeftIdle(): TextureRegion = Assets.bakerTextureRegion[1][0]
    override fun getRightIdle(): TextureRegion = Assets.bakerTextureRegion[2][0]

    override fun getFacingWalkFrames(): Array<TextureRegion> = Assets.bakerTextureRegion[0]
    override fun getBackingWalkFrames(): Array<TextureRegion> = Assets.bakerTextureRegion[3]
    override fun getLeftWalkFrames(): Array<TextureRegion> = Assets.bakerTextureRegion[1]
    override fun getRightWalkFrames(): Array<TextureRegion> = Assets.bakerTextureRegion[2]

    companion object {
        private const val TAG = "Baker"
        const val FRAME_ROWS = 4
        const val FRAME_COLS = 4
    }
}