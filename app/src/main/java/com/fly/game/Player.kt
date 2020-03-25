package com.fly.game

import android.content.Context
import android.content.res.AssetManager
import android.graphics.RectF
import android.util.Log
import android.widget.Toast
import com.fly.graphic.Object

val None = 0
val WayLeft = -1
val WayRight = 1

class Player(path: String,asset_manager: AssetManager) : Object()
{
    var width : Int = 0
    var height : Int = 0

    var speed = 5

    var way = WayRight
    var run = None
    var jump = false
    var drop = true

    var skill_button_1 = false
    var skill_button_2 = false
    var skill_button_3 = false
    var skill_button_4 = false
    var skill_button_5 = false
    var skill_button_6 = false
    var skill_button_7 = false
    var skill_button_8 = false
    var skill_button_9 = false
    var skill_button_10 = false
    var skill_button_11 = false
    var skill_button_12 = false

    init
    {
        super.SetSprite(path,asset_manager)
    }

    fun Run()
    {
        Log.e("Run","奔跑")
        if (way == WayLeft)
        {
            x -= speed
        }
        if (way == WayRight)
        {
            x += speed
        }
    }

    fun Jump()
    {
        Log.e("Jump","跳跃")
    }

    fun Skill(context: Context)
    {
        Log.e("Skill","技能释放")

        if (skill_button_1 && skill_button_5 && skill_button_9)
            Toast.makeText(context,"火球术",Toast.LENGTH_LONG).show()

        skill_button_1 = false
        skill_button_2 = false
        skill_button_3 = false
        skill_button_4 = false
        skill_button_5 = false
        skill_button_6 = false
        skill_button_7 = false
        skill_button_8 = false
        skill_button_9 = false
        skill_button_10 = false
        skill_button_11 = false
        skill_button_12 = false
    }

    fun Drop()
    {
        if (collision_box!= null && collision_box!!.Collision())
        {
            drop = false
        }
    }
}
