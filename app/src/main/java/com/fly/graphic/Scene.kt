package com.fly.graphic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat.getSystemService

class Scene(context : Context?,attrs:AttributeSet?) : View(context,attrs)
{
    private var click:(event: MotionEvent?)->Unit = {}
    private var up:(event: MotionEvent?)->Unit = {}
    private var move:(event: MotionEvent?)->Unit = {}

    private var scene_renderer : SceneRenderer? = null
    private var bg_color : Int = Color.WHITE

    private val wm = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val dm = DisplayMetrics()
    protected var width_ratio:Float
    protected var height_ratio:Float
    private var FPS:Float = wm.defaultDisplay.refreshRate

    protected var scene_width = 16f
    protected var scene_height = 9f

    init
    {
        wm.defaultDisplay.getMetrics(dm)
        width_ratio = dm.widthPixels / scene_width
        height_ratio = dm.heightPixels / scene_height
        scene_renderer?.SetWidthRatio(width_ratio)
        scene_renderer?.SetHeightRatio(height_ratio)
    }

    override fun onDraw(canvas: Canvas?)
    {
        super.onDraw(canvas)

        FPS = wm.defaultDisplay.refreshRate

        //val begin_time = System.currentTimeMillis() // the time when the cycle begun

        canvas?.drawColor(bg_color)

        if (canvas != null) { scene_renderer?.Display(canvas) }

        if(scene_renderer?.GetLayerType() != LAYER_TYPE_HARDWARE)
            setLayerType(LAYER_TYPE_SOFTWARE,scene_renderer?.GetPaint())

        //val diff_time:Long = System.currentTimeMillis() - begin_time // the time it took for the cycle to execute
/*
        if(diff_time > 0)
            FPS = 1000 / diff_time
*/
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

    fun GetFPS() : Float { return wm.defaultDisplay.refreshRate }

    fun GetSceneWidth() : Float { return scene_width }
    fun GetSceneHeight() : Float { return scene_height }
    fun GetSceneWidthRatio() : Float { return width_ratio }
    fun GetSceneHeightRatio() : Float { return height_ratio }
    fun GetSceneRenderer() : SceneRenderer? { return scene_renderer }

    fun SetSceneWidth(width:Float) { scene_width = width;width_ratio = dm.widthPixels / scene_width;scene_renderer?.SetWidthRatio(width_ratio) }
    fun SetSceneHeight(height:Float) { scene_width = height;height_ratio = dm.heightPixels / scene_height;scene_renderer?.SetHeightRatio(height_ratio) }

    fun SetClick(click:(event: MotionEvent?)->Unit){ this.click = click }
    fun SetUp(up:(event: MotionEvent?)->Unit){ this.up = up }
    fun SetMove(move:(event: MotionEvent?)->Unit){ this.move = move }

    fun SetBackGroundColor(color:Int) { bg_color = color }
    fun SetCamera(camera: Camera){ scene_renderer?.SetCamera(camera) }
    fun SetSceneRenderer(r : SceneRenderer) { scene_renderer = r;scene_renderer!!.SetWidthRatio(width_ratio);scene_renderer!!.SetHeightRatio(height_ratio) }

}
