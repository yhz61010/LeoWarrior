package com.leovp.warrior.framework

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.leovp.warrior.LeoWarriorGame
import com.leovp.warrior.framework.utils.humanReadableByteCount
import com.leovp.warrior.gfx.Font

/**
 * Author: Michael Leo
 * Date: 2021/11/22 13:30
 */
abstract class LeoScreen(protected val game: LeoWarriorGame, protected val batch: SpriteBatch) : Screen { // or implements ScreenAdapter
    abstract fun getTagName(): String
    private val tag: String by lazy { getTagName() }

    private var debugStage: Stage? = null
    private var debugInfoLabel: Label? = null

    init {
        if (LeoWarriorGame.DEBUG) {
            val labelDebugStyle = Label.LabelStyle(Font(16).font, Color.RED)
            debugInfoLabel = Label("Debug Information", labelDebugStyle).apply {
                setAlignment(Align.topLeft)
                setSize(Gdx.graphics.width * 1f, Gdx.graphics.height * 1f)
                setPosition(10f, -10f)
            }
            debugStage = Stage(ScreenViewport(OrthographicCamera()), batch).apply { addActor(debugInfoLabel) }
        }
    }

    /** Update logic */
    protected abstract fun update(delta: Float)
    protected open fun drawForDisableBlending() {}
    protected open fun drawForBlending() {}
    protected open fun drawWithoutBatchAround() {}
    protected open fun drawShapeRenderer() {}

    override fun render(delta: Float) {
        update(delta)

        // Clear the screen
        ScreenUtils.clear(0f, 0f, 0f, 1f)
        game.camera.update()
        batch.projectionMatrix = game.camera.combined

        batch.disableBlending()
        batch.begin()
        drawForDisableBlending()
        batch.end()

        batch.enableBlending()
        batch.begin()
        if (LeoWarriorGame.DEBUG) debugInfoLabel?.setText(getDebugInfoString())
        drawForBlending()
        batch.end()

        if (LeoWarriorGame.DEBUG) debugStage?.draw()

        drawWithoutBatchAround()

        if (LeoWarriorGame.DEBUG) drawShapeRenderer()
    }

    override fun show() {
        Gdx.app.log(tag, "=====> show() <=====")
    }

    override fun pause() {
        Gdx.app.log(tag, "=====> pause() <=====")
    }

    override fun resume() {
        Gdx.app.log(tag, "=====> resume() <=====")
    }

    override fun hide() {
        Gdx.app.log(tag, "=====> hide() <=====")
    }

    override fun dispose() {
        Gdx.app.log(tag, "=====> dispose() <=====")
        if (LeoWarriorGame.DEBUG) debugStage?.dispose()
    }

    override fun resize(width: Int, height: Int) {
        Gdx.app.log(tag, "=====> resize($width, $height) <=====")
        game.viewport.update(width, height)
        if (LeoWarriorGame.DEBUG) debugStage?.viewport?.update(width, height, true)
    }

    private fun getDebugInfoString(): String {
        return String.format(
            "FPS=%d Sprites=%d\nrenderCalls=%d totalRenderCalls=%d\njavaHeap=%s nativeHeap=%s",
            Gdx.graphics.framesPerSecond, game.batch.maxSpritesInBatch, game.batch.renderCalls, game.batch.totalRenderCalls,
            Gdx.app.javaHeap.humanReadableByteCount(), Gdx.app.nativeHeap.humanReadableByteCount()
        )
    }
}