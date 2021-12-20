package com.leovp.warrior

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.game_screens.MainMenuScreen

class LeoWarriorGame : Game() {
    lateinit var batch: SpriteBatch
    lateinit var assets: Assets

    /** Active camera. */
    lateinit var camera: OrthographicCamera

    /** Game viewport. */
    lateinit var viewport: Viewport

    override fun create() {
        camera = OrthographicCamera().apply { setToOrtho(false, World.WORLD_WIDTH, World.WORLD_HEIGHT) }
        viewport = FitViewport(World.WORLD_WIDTH, World.WORLD_HEIGHT, camera).apply { apply(true) }
        batch = SpriteBatch()

        assets = Assets()
        Gdx.app.log(TAG, "Loading MainMenuScreen assets...")
        assets.loadMainMenuScreenAssets()

        Gdx.app.log(TAG, "Prepare to goto MainMenuScreen.")
        setScreen(MainMenuScreen(this))
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 0f)
        super.render()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        viewport.update(width, height)
    }

    override fun dispose() {
        getScreen().dispose()
        batch.dispose()
    }

    companion object {
        private const val TAG = "LeoWarriorGame"
        const val DEBUG = true
    }
}