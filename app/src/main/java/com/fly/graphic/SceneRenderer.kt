package com.fly.graphic

import android.graphics.Canvas

class SceneRenderer(private var renderer: Renderer,private var camera: Camera = Camera())
{
    fun SetCamera(camera: Camera) { this.camera = camera }
    fun GetCamera() : Camera { return camera }

    fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float = 0f, y: Float = 0f, width:Int = sprite.width, height:Int = sprite.height, index:Int = 0)
    { renderer.DrawSprite(canvas,sprite,x + camera.look_at_x, y + camera.look_at_y, width, height, index) }
    fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float = 0f, y: Float = 0f, width:Int = sprite.width, height:Int = sprite.height, index:Int = 0, flip_x:Float = 1f, flip_y: Float = 1f)
    { renderer.DrawSprite(canvas,sprite,x + camera.look_at_x, y + camera.look_at_y, width, height, index, flip_x, flip_y) }

    fun DrawObject(canvas: Canvas, object_: Object, width: Int = object_.GetSprite()?.width!!, height: Int = object_.GetSprite()?.height!!, index: Int = 0)
    {
        object_.GetSprite()?.let{
            renderer.DrawSprite(canvas, it,object_.x + camera.look_at_x,object_.y + camera.look_at_y,width,height,index)
        }
    }

}
