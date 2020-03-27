package com.fly.ui

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import com.fly.graphic.Renderer

abstract class Widget(var x:Float = 0f,var y:Float = 0f,var width:Float = 0f,var height:Float = 0f)
{
    var alpha:Int = 255

    private var click : (event: MotionEvent?) -> Unit = {}
    private var up : (event: MotionEvent?) -> Unit = {}
    private var move:(event: MotionEvent?)->Unit = {}

    //fun SetRender(render: (canvas: Canvas,renderer: Renderer) -> Unit) { this.render = render }
    open fun Render(canvas: Canvas,renderer: Renderer) { renderer.SetAlpha(alpha) }
    open fun Render(canvas: Canvas,renderer: Renderer,x: Float,y: Float) { renderer.SetAlpha(alpha) }
    open fun Render(canvas: Canvas,renderer: Renderer,x:Float,y:Float,width:Float,height:Float) { renderer.SetAlpha(alpha) }

    fun SetClick(click:(event: MotionEvent?) ->Unit) { this.click = click }
    fun Click(event: MotionEvent?) { click(event) }
    fun SetUp(up:(event: MotionEvent?) ->Unit) { this.up = up }
    fun Up(event: MotionEvent?) { up(event) }
    fun SetMove(move:(event: MotionEvent?) ->Unit) { this.move = move }
    fun Move(event: MotionEvent?) { move(event) }
}
