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

    var last_x = x * width_ratio
    var last_y = y * height_ratio
    var next_y:Float? = null

    var way = WayRight
    var run = false
    var jump = false
    var drop = true
    var attack = false

    val jump_height = 3f
    var jump_first_height = y

    var drop_start = false
    var drop_start_time = System.currentTimeMillis()

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
            for (i in 0 until collision_box?.GetAllCollideRect()?.size!!)
            {
                if ((y + height) * height_ratio > collision_box?.GetCollideRect(i)?.top!!
                    && y * height_ratio < collision_box?.GetCollideRect(i)?.bottom!!)
                {
                    Log.e("Run","碰撞")
                    return
                }
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

        if (y > jump_first_height - jump_height)
            y -= speed
        else if (y < jump_first_height - jump_height)
        {
            jump = false
            drop = true
        }
        collision_box?.SetRect(RectF(x * width_ratio,y * height_ratio,(x + width) * width_ratio,(y + height)*height_ratio))!!
    }

    fun Drop()
    {
        val obj_x = x * width_ratio
        val obj_y = y * height_ratio
        val obj_width = width * width_ratio
        val obj_height = height * height_ratio
        last_x = obj_x
        last_y = obj_y

        collision_box?.SetRect(RectF(x * width_ratio,y * height_ratio,(x + width) * width_ratio,(y + height)*height_ratio))!!
        if (rigid_body != null && collision_box!= null && collision_box!!.Collision())
        {
            for(i in 0 until collision_box!!.GetAllCollideRect().size)
            {
                drop = !(y + height >= collision_box!!.GetCollideRect(0).bottom / height_ratio
                        && x <= collision_box!!.GetCollideRect(0).right / width_ratio
                        && x + width >= collision_box!!.GetCollideRect(0).left / width_ratio)
            }
            if (!drop)
            {
                jump_first_height = y
                drop_start = false
            }
        }
        else if (rigid_body != null && collision_box!= null && !collision_box!!.Collision())
            drop = true

        if (rigid_body != null && drop && !drop_start)
        {
            drop_start_time = System.currentTimeMillis()
            drop_start = true
        }

        if (rigid_body != null && drop)
        {
            val will_y = obj_y + GetRigid()!!.GetDropHeight(System.currentTimeMillis() - drop_start_time)
            GetCollisionBox()!!.SetRect(RectF(obj_x,will_y,obj_x + obj_width,will_y + obj_height))
            if (GetCollisionBox()!!.Collision())
            {
                for (i in 0 until collision_box?.GetAllCollideRect()?.size!!)
                {
                    if (will_y + obj_height > GetCollisionBox()!!.GetCollideRect(i).top)//下一次底大于物体的顶
                    {
                        next_y = GetCollisionBox()!!.GetCollideRect(i).top / height_ratio - height //将下一次的底改为物体的顶
                        drop = false
                    }
                }
            }
            else
            {
                for (i in 0 until GetCollisionBox()!!.GetAllCollisionRect().size)
                {
                    if (obj_y >= GetCollisionBox()!!.GetAllCollisionRect()[i].bottom // ||  obj_y > obj.GetCollisionBox()!!.GetAllCollisionRect()[i].top
                        && last_y + obj_height < GetCollisionBox()!!.GetAllCollisionRect()[i].top)
                    {
                        y = GetCollisionBox()!!.GetAllCollisionRect()[i].top / height_ratio - height
                        drop = false
                        return
                    }
                }
                y += GetRigid()!!.GetDropHeight(System.currentTimeMillis() - drop_start_time) / height_ratio
            }
        }

        if (next_y != null)
            y = next_y!!
        next_y = null

        Log.e("drop",drop.toString())
    }

    fun Attack()
    {
        collision_box?.SetRect(RectF(x * width_ratio,y * height_ratio,(x + width) * width_ratio,(y + height)*height_ratio))!!
        if(collision_box!!.Collision())
        {

        }
    }

    fun Skill(context: Context)
    {
        Log.e("Skill","技能释放")

        if (skill_button[1] && skill_button[5] && skill_button[9])
            Toast.makeText(context,"火球术",Toast.LENGTH_LONG).show()

        for (i in 0 until 12)
            skill_button[i] = false
    }

}
