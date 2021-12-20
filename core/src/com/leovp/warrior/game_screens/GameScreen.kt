package com.leovp.warrior.game_screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.leovp.warrior.LeoWarriorGame
import com.leovp.warrior.assets.characters.Baker
import com.leovp.warrior.assets.player.Caverman
import com.leovp.warrior.framework.LeoScreen

/**
 * Author: Michael Leo
 * Date: 2021/12/17 13:22
 */
class GameScreen(game: LeoWarriorGame) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    private var level = 1
    private val caverman = Caverman(0f, 0f)
    private val baker = Baker(50f, 50f)

    init {
        Gdx.app.log(TAG, "=====> GameScreen <=====")
        createWorld(level)
    }

    private fun createWorld(level: Int) {
        Gdx.app.log(TAG, "Current level=$level")
    }

    private fun handleInput() {
        baker.status = when (baker.status) {
            Baker.Status.FACING_WALK -> Baker.Status.FACING_IDLE
            Baker.Status.BACKING_WALK -> Baker.Status.BACKING_IDLE
            Baker.Status.LEFT_WALK -> Baker.Status.LEFT_IDLE
            Baker.Status.RIGHT_WALK -> Baker.Status.RIGHT_IDLE
            else -> baker.status
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            baker.status = Baker.Status.FACING_WALK
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            baker.status = Baker.Status.BACKING_WALK
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            baker.status = Baker.Status.LEFT_WALK
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            baker.status = Baker.Status.RIGHT_WALK
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.screen = MainMenuScreen(game)
    }

    override fun update(delta: Float) {
        handleInput()
        caverman.update(delta)
        baker.update(delta)
    }

    override fun drawForBlending() {
        caverman.render(batch)
        baker.render(batch)
    }

    override fun drawShapeRenderer() {
        super.drawShapeRenderer()
        caverman.drawShapeRenderer(game.camera.combined)
        caverman.drawCollisionShapeRenderer(game.camera.combined)
        baker.drawShapeRenderer(game.camera.combined)
    }

    companion object {
        private const val TAG = "GameScreen"
    }
}