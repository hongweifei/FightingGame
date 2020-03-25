package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import com.fly.physics.CollisionBox
import com.fly.physics.RigidBody

open class Object(var x:Float = 0f, var y:Float = 0f)
{
    var dx:Float = 0f
    var dy:Float = 0f

    protected var sprite:Sprite? = null
    protected var rigid_body: RigidBody? = null
    protected var collision_box:CollisionBox? = null

    init
    {

    }

    fun SetSprite(sprite: Sprite) { this.sprite = sprite }
    fun SetSprite(path: String,asset_manager: AssetManager) { sprite = Sprite(path,asset_manager) }

    fun SetRigidBody(rigid_body: RigidBody) { this.rigid_body = rigid_body }
    fun SetCollisionBox(collision_box: CollisionBox) { this.collision_box = collision_box }

    fun GetSprite() : Sprite? { return sprite }
    fun GetRigid() : RigidBody? { return rigid_body }
    fun GetCollisionBox() : CollisionBox? { return collision_box }

    fun AddCollide(r:RectF) { collision_box?.AddCollide(r) }
    fun AddCollide(collision_box: CollisionBox) { this.collision_box?.AddCollide(collision_box) }

    fun InitSrcRect(rect_list:ArrayList<Rect>) { sprite?.GetSrcRect()?.clear();sprite?.SetSrcRect(rect_list) }
    fun InitSpriteSrcRect(start_x:Int,start_y:Int,width:Int,height:Int,horizontal:Int,vertical:Int)
    { sprite?.InitSrcRect(start_x,start_y, width, height, horizontal, vertical) }

    //fun Render(canvas: Canvas, renderer: Renderer) { renderer.DrawObject(canvas,this) }
    fun Render(canvas: Canvas, renderer: SceneRenderer) { renderer.DrawObject(canvas,this) }
    fun Render(canvas: Canvas, renderer: SceneRenderer, width: Int = sprite?.width!!, height: Int = sprite?.height!!, index: Int)
    { renderer.DrawObject(canvas,this,width,height,index) }
}
