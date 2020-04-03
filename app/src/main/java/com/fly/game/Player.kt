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

class Player(scene: Scene? = null,path: String? = null,asset_manager: AssetManager? = null) : Object(scene)//人物类继承物体类
{
    var speed = 0.1f//人物速度

    var last_x = x * width_ratio//上次横坐标，
    var last_y = y * height_ratio//上次纵坐标
    var next_y:Float? = null//下一次的纵坐标

    var way = WayRight//人物方向
    var run = false//奔跑
    var jump = false//跳跃
    var drop = true//下落
    var attack = false//攻击

    val jump_height = 3f//能跳跃第高度
    var jump_first_height = y//跳跃时的高度

    var drop_start = false//下落状态
    var drop_start_time = System.currentTimeMillis()//下落开始时的时间

    var skill_button:ArrayList<Boolean> = ArrayList<Boolean>()//技能按钮是否被点击

    init
    {
        for (i in 0 until 13)//
            skill_button.add(false)//初始化技能按钮点击与否
        if (path != null && asset_manager != null)
            super.SetSprite(path,asset_manager,true,false)//初始化人物精灵
    }

    fun SetRun(way:Int,asset_manager: AssetManager)//修改人物奔跑方向
    {
        this.way = way
        run = true

        if (way == WayLeft)
            SetSprite("player/KakashiLeft.png",asset_manager,false,true)
        else if (way == WayRight)
            SetSprite("player/KakashiRight.png",asset_manager,false,true)
    }

    fun Run()//人物奔跑
    {
        Log.e("Run","奔跑")

        val will_x = if (way == WayLeft) x - speed else x + speed//下一次的x坐标

        collision_box?.SetRect(RectF(will_x * width_ratio,y * height_ratio,(will_x + width) * width_ratio,(y + height)*height_ratio))!!//修改人物碰撞检测
        if(collision_box?.Collision()!!)//判断人物是否碰撞
        {
            for (i in 0 until collision_box?.GetAllCollideRect()?.size!!)
            {
                if ((y + height) * height_ratio > collision_box?.GetCollideRect(i)?.top!!
                    && y * height_ratio < collision_box?.GetCollideRect(i)?.bottom!!)//碰撞且x与被碰撞物相交
                {
                    Log.e("Run","碰撞")
                    return//函数结束
                }
            }

        }

        /*未有碰撞改变X*/
        if (way == WayLeft)
            x -= speed//左
        if (way == WayRight)
            x += speed//右
    }

    fun Jump()//跳跃
    {
        Log.e("Jump","跳跃")

        if (y > jump_first_height - jump_height)//跳跃未开始时的Y- 能跳的高度 = 跳跃后的Y，人物Y大于 跳跃后的Y
            y -= speed//人物上升
        else if (y < jump_first_height - jump_height)//人物达到 跳跃后的Y，
        {
            jump = false//跳跃未假
            drop = true//掉落为真
        }
        collision_box?.SetRect(RectF(x * width_ratio,y * height_ratio,(x + width) * width_ratio,(y + height)*height_ratio))!!//修改人物碰撞
    }

    fun Drop()
    {
        /*碰撞检测不使用场景大小*/
        val obj_x = x * width_ratio//人物x
        val obj_y = y * height_ratio//人物y
        val obj_width = width * width_ratio//人物宽
        val obj_height = height * height_ratio//人物高
        last_x = obj_x//上次X
        last_y = obj_y//上次Y

        collision_box?.SetRect(RectF(x * width_ratio,y * height_ratio,(x + width) * width_ratio,(y + height)*height_ratio))!!//修改人物碰撞
        if (rigid_body != null && collision_box!= null && collision_box!!.Collision())//人物是否碰撞
        {
            for(i in 0 until collision_box!!.GetAllCollideRect().size)//遍历被碰撞物
            {
                /*下落状态判断*/
                drop = !(y + height >= collision_box!!.GetCollideRect(i).top/ height_ratio//人物底部 >= 被碰撞物体 顶端
                        && x <= collision_box!!.GetCollideRect(i).right / width_ratio//X人物 <= 被碰撞物 右边
                        && x + width >= collision_box!!.GetCollideRect(i).left / width_ratio)//人物右边  >= 被碰撞物体 X
            }
            if (!drop)//下落为假
            {
                jump_first_height = y//跳跃开始Y = 人物现在Y
                drop_start = false//下落开始与否 = 假
            }
        }
        else if (rigid_body != null && collision_box!= null && !collision_box!!.Collision())//未与物体碰撞
            drop = true//下落为真

        if (rigid_body != null && drop && !drop_start)//下落为真，下落开始为假，即人物应下落，但未开始
        {
            drop_start_time = System.currentTimeMillis()//下落开始时间等于现在时间
            drop_start = true//下落开始为真
        }

        if (rigid_body != null && drop)//下落为真
        {
            val will_y = obj_y + GetRigid()!!.GetDropHeight(System.currentTimeMillis() - drop_start_time)//下一次纵坐标
            GetCollisionBox()!!.SetRect(RectF(obj_x,will_y,obj_x + obj_width,will_y + obj_height))//修改人物碰撞体为下一次的坐标
            if (GetCollisionBox()!!.Collision())//下一次是否碰撞
            {
                /*碰撞为真*/
                for (i in 0 until collision_box?.GetAllCollideRect()?.size!!)//遍历被碰撞物体
                {
                    if (will_y + obj_height > GetCollisionBox()!!.GetCollideRect(i).top)//下一次底部是否大于被碰撞物体的顶端
                    {
                        /*下一次人物底部大于被碰撞物体顶端*/
                        next_y = GetCollisionBox()!!.GetCollideRect(i).top / height_ratio - height //将下一次渲染人物时的人物纵坐标改为物体的顶端的纵坐标
                        drop = false//下落为假
                    }
                }
            }
            else
            {
                /*碰撞为假*/
                for (i in 0 until GetCollisionBox()!!.GetAllCollisionRect().size)//遍历可被碰撞物
                {
                    if (obj_y >= GetCollisionBox()!!.GetAllCollisionRect()[i].bottom  //||  obj_y > GetCollisionBox()!!.GetAllCollisionRect()[i].top
                        && last_y + obj_height < GetCollisionBox()!!.GetAllCollisionRect()[i].bottom )//若 人物纵坐标（顶端）曾大于可被碰撞物的底部 且 上一次的底部是在可被碰撞物的底部的上面
                    {
                        y = GetCollisionBox()!!.GetAllCollisionRect()[i].top / height_ratio - height//人物纵坐标 = 该碰撞物的顶部- 人物高
                        drop = false//下落为假
                        return//结束
                    }
                }
                /*人物未曾有上述情况，下落仍为真*/
                y += GetRigid()!!.GetDropHeight(System.currentTimeMillis() - drop_start_time) / height_ratio
            }
        }

        if (next_y != null)//下次渲染人物的y不为空
            y = next_y!!//人物y为下次渲染人物的y
        next_y = null//下次渲染人物的y设为空

        Log.e("drop",drop.toString())
    }

    fun Attack()//攻击
    {
        collision_box?.SetRect(RectF(x * width_ratio,y * height_ratio,(x + width) * width_ratio,(y + height)*height_ratio))!!//修改人物碰撞
        if(collision_box!!.Collision())//碰到物体
        {

        }
    }

    fun Skill(context: Context)//技能
    {
        Log.e("Skill","技能释放")

        if (skill_button[1] && skill_button[5] && skill_button[9])//1 5 9技能按钮被点击过
            Toast.makeText(context,"火球术",Toast.LENGTH_LONG).show()//释放技能

        for (i in 0 until 12)
            skill_button[i] = false//初始化技能按钮点击状态
    }

}
