package com.fly.game

import android.content.Context
import android.content.res.AssetManager
import android.graphics.RectF
import android.util.Log
import android.widget.Toast
import com.fly.graphic.Object
import com.fly.graphic.Scene

val None = 0
val WayLeft = -1
val WayRight = 1

class Player(scene: Scene? = null,path: String? = null,asset_manager: AssetManager? = null) : Object(scene)
{
    var speed = 0.1f

    var way = WayRight
    var run = false
    var jump = false
    var drop = true
    var attack = false

    var skill_button:ArrayList<Boolean> = ArrayList<Boolean>()

    init
    {
        for (i in 0 until 13)
            skill_button.add(false)
        if (path != null && asset_manager != null)
            super.SetSprite(path,asset_manager,true,false)
    }

    fun SetRun(way:Int,asset_manager: AssetManager)
    {
        this.way = way
        run = true

        if (way == WayLeft)
            SetSprite("player/KakashiLeft.png",asset_manager,false,true)
        else if (way == WayRight)
            SetSprite("player/KakashiRight.png",asset_manager,false,true)
    }

    fun Run()
    {
        Log.e("Run","奔跑")

        val will_x = if (way == WayLeft) x - speed else x + speed

        collision_box?.SetRect(RectF(will_x * width_ratio,y * height_ratio,(will_x + width) * width_ratio,(y + height)*height_ratio))!!
        if(collision_box?.Collision()!!)
        {
            if ((y + height) * height_ratio > collision_box?.GetCollisionRect()?.top!!
                && y * height_ratio < collision_box?.GetCollisionRect()?.bottom!!)
            {
                Log.e("Run","碰撞")
                x = x
                y = y
                return
            }
        }

        if (way == WayLeft)
            x -= speed
        if (way == WayRight)
            x += speed
    }

    fun Jump()
    {
        Log.e("Jump","跳跃")

        val will_y = y - speed

        collision_box?.SetRect(RectF(x * width_ratio,will_y * height_ratio,(x + width) * width_ratio,(will_y + height)*height_ratio))!!
        if(collision_box?.Collision()!!)
        {
            if (will_y * height_ratio > collision_box?.GetCollisionRect()?.bottom!!)
            {
                Log.e("Run","碰撞")
                y = y
                return
            }
        }

        y -= speed
    }

    fun Skill(context: Context)
    {
        Log.e("Skill","技能释放")

        if (skill_button[1] && skill_button[5] && skill_button[9])
            Toast.makeText(context,"火球术",Toast.LENGTH_LONG).show()

        for (i in 0 until 12)
            skill_button[i] = false
    }

    fun Drop() { if (collision_box!= null && collision_box!!.Collision()) { drop = y + height < collision_box?.GetCollisionRect()?.top!!/height_ratio } }
}
