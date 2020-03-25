package com.fly.graphic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class Scene(context : Context?,attrs:AttributeSet?) : View(context,attrs)
{
    private var click:(event: MotionEvent?)->Unit = {}
    private var up:(event: MotionEvent?)->Unit = {}
    private var move:(event: MotionEvent?)->Unit = {}

    private var FPS:Long? = null

    private var scene_renderer : SceneRenderer = SceneRenderer(Camera())
    private var bg_color : Int = Color.WHITE

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        val begin_time = System.currentTimeMillis() // the time when the cycle begun

        canvas?.drawColor(bg_color)

        if (canvas != null) { scene_renderer.Display(canvas,FPS) }

        if(scene_renderer.GetLayerType() != LAYER_TYPE_HARDWARE)
            setLayerType(LAYER_TYPE_SOFTWARE,scene_renderer.GetPaint())

        val diff_time:Long = System.currentTimeMillis() - begin_time // the time it took for the cycle to execute

        if(diff_time > 0)
            FPS = 1000 / diff_time

        //Log.e("FPS",FPS.toString())

        postInvalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean
    {
        if (event?.action == MotionEvent.ACTION_DOWN)
            click(event)
        if (event?.action == MotionEvent.ACTION_UP)
            up(event)
        if (event?.action == MotionEvent.ACTION_MOVE)
            move(event)

        return true
    }

    fun GetFPS() : Long? { return FPS }

    fun GetWidth() : Int { return width }
    fun GetHeight() : Int { return height }
    fun GetSceneRenderer() : SceneRenderer { return scene_renderer }

    fun SetClick(click:(event: MotionEvent?)->Unit){ this.click = click }
    fun SetUp(up:(event: MotionEvent?)->Unit){ this.up = up }
    fun SetMove(move:(event: MotionEvent?)->Unit){ this.move = move }

    fun SetBackGroundColor(color:Int) { bg_color = color }
    fun SetCamera(camera: Camera){ scene_renderer.SetCamera(camera) }
    fun SetSceneRenderer(r : SceneRenderer) { scene_renderer = r }

}
