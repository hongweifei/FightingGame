package com.fly.physics

import android.graphics.RectF
import android.util.Log

class CollisionBox(r:RectF? = null)
{
    var rect: RectF? = r
    private var collide_rect:ArrayList<RectF> = ArrayList<RectF>()

    init
    {

    }

    fun SetRect(r: RectF) { rect = r }
    fun SetRect(left:Float,top:Float,right:Float,bottom:Float) { rect = RectF(left,top, right, bottom) }

    fun AddCollide(r: RectF) { collide_rect.add(r) }
    fun AddCollide(collision_box:CollisionBox) { if (collision_box.rect != null) { collide_rect.add(collision_box.rect!!) } }

    fun Collision() : Boolean { for (i in 0 until collide_rect.size) { return Collision(collide_rect[i]) };return false }
    fun Collision(r:RectF) : Boolean { if (rect != null) { if (rect!!.left <= r.right && rect!!.right >= r.left && rect!!.bottom >= r.top && rect!!.top <= r.bottom) { return true } };return false }
    fun Collision(x:Float,y:Float,width:Float,height:Float) : Boolean
    { if (rect != null) { if (rect!!.left <= x + width && rect!!.right >= x && rect!!.bottom >= y && rect!!.top <= y + height) { return true } };return false }
}
