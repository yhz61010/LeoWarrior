package com.leovp.warrior.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.leovp.warrior.LeoWarriorGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            width = 1280
            height = 720
        }
        LwjglApplication(LeoWarriorGame(), config)
    }
}