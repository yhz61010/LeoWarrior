package com.leovp.warrior.framework

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

open class GameObject(x: Float, y: Float, width: Float, height: Float) {
    val position: Vector2 = Vector2(x, y)
    val bounds: Rectangle = Rectangle(x - width / 2, y - height / 2, width, height)
    var collisionBounds: Rectangle = Rectangle(x - width / 2, y - height / 2, width, height)

    var x: Float
        get() = position.x
        set(x) {
            position.x = x
        }

    var y: Float
        get() = position.y
        set(y) {
            position.y = y
        }

    fun scaleCollisionBounds(scaleWidth: Float, scaleHeight: Float) {
        collisionBounds.setSize(collisionBounds.width * scaleWidth, collisionBounds.height * scaleHeight)
    }

    fun updateCollisionPosition(pos: Vector2, offsetX: Float, offsetY: Float) {
        collisionBounds.setPosition(pos.x + offsetX, pos.y + offsetY)
    }
}