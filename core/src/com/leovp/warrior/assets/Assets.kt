package com.leovp.warrior.assets

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.leovp.warrior.assets.characters.base.Character

/**
 * Author: Michael Leo
 * Date: 2021/11/17 10:05
 *
 * https://github.com/libgdx/libgdx/wiki/Managing-your-assets
 * https://github.com/libgdx/libgdx/wiki/Textures%2C-textureregion-and-spritebatch
 */
class Assets {
    companion object {
        lateinit var titleFont: Array<Array<TextureRegion>>
        lateinit var mainMenuBg: Texture

        lateinit var bakerTextureRegion: Array<Array<TextureRegion>>
        lateinit var modernGuy02TextureRegion: Array<Array<TextureRegion>>
        lateinit var russianF1TextureRegion: Array<Array<TextureRegion>>
        lateinit var lightYagamiTextureRegion: Array<Array<TextureRegion>>

        fun loadTexture(file: String): Texture = Texture(Gdx.files.internal(file))
        fun loadAtlas(file: String): TextureAtlas = TextureAtlas(file)

        fun playSound(sound: Sound, vol: Float = 1f): Long = sound.play(vol)
        fun stopSound(sound: Sound, soundId: Long = -1) = if (soundId > -1) sound.stop(soundId) else sound.stop()
    }

    val manager = AssetManager()

    /**
     * Make sure only the necessary assets are loaded for the first screen.
     * Other assets should be loaded when [Loading Screen] are displayed.
     */
    fun loadMainMenuScreenAssets() {
        manager.load("mainmenu_background.png", Texture::class.java)
        manager.load("title-font.png", Texture::class.java)
        manager.finishLoading()

        mainMenuBg = manager.get("mainmenu_background.png", Texture::class.java)
        titleFont = TextureRegion.split(manager.get("title-font.png"), 544, 129)
    }

    fun enqueueGameScreenAssets() {
    }

    fun loadGameScreenAssets() {
        val bakerSheet = loadTexture("characters/baker.png")
        bakerTextureRegion = TextureRegion.split(bakerSheet, bakerSheet.width / Character.FRAME_COLS, bakerSheet.height / Character.FRAME_ROWS)

        val modernGuy02Sheet = loadTexture("characters/modernguy02.png")
        modernGuy02TextureRegion = TextureRegion.split(modernGuy02Sheet, modernGuy02Sheet.width / Character.FRAME_COLS, modernGuy02Sheet.height / Character.FRAME_ROWS)

        val russianF1Sheet = loadTexture("characters/russian_f1.png")
        russianF1TextureRegion = TextureRegion.split(russianF1Sheet, russianF1Sheet.width / Character.FRAME_COLS, russianF1Sheet.height / Character.FRAME_ROWS)

        val lightYagamiSheet = loadTexture("characters/lightyagami.png")
        lightYagamiTextureRegion = TextureRegion.split(lightYagamiSheet, lightYagamiSheet.width / Character.FRAME_COLS, lightYagamiSheet.height / Character.FRAME_ROWS)
    }

    fun dispose() {
        manager.dispose()
    }
}