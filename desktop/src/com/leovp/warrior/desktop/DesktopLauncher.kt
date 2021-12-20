package com.leovp.warrior.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.leovp.warrior.LeoWarriorGame

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            width = 960 // 1280 // 1024 // 854 // 640
            height = 540 // 720 // 576 // 480 // 360
        }
        LwjglApplication(LeoWarriorGame(), config)
    }
}