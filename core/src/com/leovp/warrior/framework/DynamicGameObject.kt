package com.leovp.warrior.framework

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector2
import com.leovp.warrior.LeoWarriorGame

open class DynamicGameObject(x: Float, y: Float, width: Float, height: Float) : GameObject(x, y, width, height) {
    val velocity: Vector2 = Vector2()
    val accel: Vector2 = Vector2()

    private var shapeRenderer: ShapeRenderer? = null

    init {
        if (LeoWarriorGame.DEBUG) shapeRenderer = ShapeRenderer()
    }

    open fun drawShapeRenderer(combined: Matrix4, color: Color = Color(0f, 1f, 0f, 0f)) {
        if (LeoWarriorGame.DEBUG) shapeRenderer?.let { sr ->
            sr.projectionMatrix = combined
            sr.begin(ShapeRenderer.ShapeType.Line)
            sr.color = color
            sr.rect(bounds.x, bounds.y, bounds.width, bounds.height)
            sr.end()
        }
    }

    open fun drawCollisionShapeRenderer(combined: Matrix4, color: Color = Color(1f, 0f, 0f, 0f)) {
        if (LeoWarriorGame.DEBUG) shapeRenderer?.let { sr ->
            sr.projectionMatrix = combined
            sr.begin(ShapeRenderer.ShapeType.Line)
            sr.color = color
            sr.rect(collisionBounds.x, collisionBounds.y, collisionBounds.width, collisionBounds.height)
            sr.end()
        }
    }
}