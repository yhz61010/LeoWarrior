package com.leovp.warrior.game_screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.leovp.warrior.LeoWarriorGame
import com.leovp.warrior.World
import com.leovp.warrior.assets.Assets
import com.leovp.warrior.framework.LeoScreen
import com.leovp.warrior.framework.utils.round
import com.leovp.warrior.gfx.Font

/**
 * Author: Michael Leo
 * Date: 2021/11/16 17:41
 */
class MainMenuScreen(game: LeoWarriorGame) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    private val mainMenuStage: Stage
    private val gameNameLabel: Label
    private val gameStartLabel: Label
    private val loadingLabel: Label

    init {
        val labelGameNameStyle = Label.LabelStyle(Font(48).font, Color.GOLD)
        val labelStartStyle = Label.LabelStyle(Font(24).font, Color.GOLDENROD)
        gameNameLabel = Label(GAME_NAME, labelGameNameStyle).apply {
            setAlignment(Align.top)
            setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 0.8f)
            setPosition(0f, 0f)
        }
        gameStartLabel = Label(TEXT_START_GAME, labelStartStyle).apply {
            setAlignment(Align.top)
            setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 0.25f)
            setPosition(0f, 0f)
        }
        loadingLabel = Label("0%", labelStartStyle).apply {
            setAlignment(Align.top)
            setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 0.10f)
            setPosition(0f, 0f)
        }
        mainMenuStage = Stage(ScreenViewport(OrthographicCamera()), batch).apply {
            addActor(gameNameLabel)
            addActor(gameStartLabel)
            addActor(loadingLabel)
        }
    }

    override fun show() {
        super.show()
        game.assets.enqueueGameScreenAssets()
    }

    private fun handlerInput() {
        if (Gdx.input.justTouched()) {
            game.assets.loadGameScreenAssets()
            game.screen = GameScreen(game)
            dispose()
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit()
        }
    }

    override fun update(delta: Float) {
        if (game.assets.manager.update(30)) {
            handlerInput()
        }
    }

    override fun drawForBlending() {
    }

    override fun drawForDisableBlending() {
        batch.draw(Assets.mainMenuBg, 0f, 0f, World.WORLD_WIDTH, World.WORLD_HEIGHT)

        val loadingProgress = game.assets.manager.progress.round()
        if (loadingProgress < 1f) {
            loadingLabel.setText("${loadingProgress * 100}%")
        } else {
            loadingLabel.remove()
        }
    }

    override fun drawAfterBatchEnd() {
        mainMenuStage.draw()
    }

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        mainMenuStage.viewport.update(width, height, true)
    }

    override fun dispose() {
        super.dispose()
        mainMenuStage.dispose()
    }

    companion object {
        private const val TAG = "MainMenuScreen"
        private const val GAME_NAME = "Leo Warrior"
        private const val TEXT_START_GAME = "Click to start"
    }
}