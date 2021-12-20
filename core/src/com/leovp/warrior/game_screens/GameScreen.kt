package com.leovp.warrior.game_screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.leovp.warrior.LeoWarriorGame
import com.leovp.warrior.assets.characters.Baker
import com.leovp.warrior.assets.characters.ModernGuy02
import com.leovp.warrior.assets.characters.RussianF1
import com.leovp.warrior.assets.characters.player.LightYagami
import com.leovp.warrior.framework.LeoScreen

/**
 * Author: Michael Leo
 * Date: 2021/12/17 13:22
 */
class GameScreen(game: LeoWarriorGame) : LeoScreen(game, game.batch) {
    override fun getTagName() = TAG

    private var level = 1

    private val lightYagami = LightYagami(0f, 0f)
    private val baker = Baker(50f, 50f)
    private val modernGuy02 = ModernGuy02(150f, 50f)
    private val russianF1 = RussianF1(100f, 70f)

    init {
        Gdx.app.log(TAG, "=====> GameScreen <=====")
        createWorld(level)
    }

    private fun createWorld(level: Int) {
        Gdx.app.log(TAG, "Current level=$level")
    }

    private fun handleInput() {
        lightYagami.handleInput()
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) game.screen = MainMenuScreen(game)
    }

    override fun update(delta: Float) {
        handleInput()
        lightYagami.update(delta)
        baker.update(delta)
        modernGuy02.update(delta)
        russianF1.update(delta)
    }

    override fun drawForBlending() {
        lightYagami.render(batch)
        baker.render(batch)
        modernGuy02.render(batch)
        russianF1.render(batch)
    }

    override fun drawShapeRenderer() {
        lightYagami.drawShapeRenderer(game.camera.combined)
        baker.drawShapeRenderer(game.camera.combined)
        modernGuy02.drawShapeRenderer(game.camera.combined)
        russianF1.drawShapeRenderer(game.camera.combined)

        lightYagami.drawCollisionShapeRenderer(game.camera.combined)
        baker.drawCollisionShapeRenderer(game.camera.combined)
        modernGuy02.drawCollisionShapeRenderer(game.camera.combined)
        russianF1.drawCollisionShapeRenderer(game.camera.combined)
    }

    companion object {
        private const val TAG = "GameScreen"
    }
}