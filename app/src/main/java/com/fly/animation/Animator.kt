package com.fly.animation

import android.graphics.Canvas
import com.fly.graphic.Renderer

class Animator
{
    private var animation_list:ArrayList<Animation> = ArrayList<Animation>()

    fun AddAnimation(animation: Animation) { animation_list.add(animation) }

    fun RenderAnimation(canvas: Canvas, renderer: Renderer, FPS:Int, x:Float, y:Float,index:Int) { animation_list[index].Render(canvas, renderer, FPS, x, y) }
    fun RenderAnimation(canvas: Canvas,renderer: Renderer,FPS:Int,x:Float,y:Float,width:Float,height:Float,index: Int)
    { animation_list[index].Render(canvas, renderer, FPS, x, y,width, height) }
}
