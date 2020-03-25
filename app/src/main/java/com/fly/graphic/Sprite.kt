package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.*

open class Sprite(path:String? = null, asset_manager: AssetManager? = null)
{
    private var bitmap: Bitmap? = null

    var width:Int = 0
    var height:Int = 0

    private var rect_list:ArrayList<Rect> = ArrayList<Rect>()

    init
    {
        if(path != null)
        {
            if (asset_manager == null)
                SetBitmap(path)
            else
                SetBitmap(path,asset_manager)
        }
    }

    fun SetBitmap(bitmap: Bitmap) { this.bitmap = bitmap }
    fun SetBitmap(bitmap_path: String)
    {
        val bitmap = BitmapFactory.decodeFile(bitmap_path)
        this.bitmap = bitmap

        width = bitmap.width
        height = bitmap.height

        rect_list.clear()
        rect_list.add(Rect(0,0,width,height))
    }
    fun SetBitmap(bitmap_path: String,asset_manager:AssetManager)
    {
        val bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        this.bitmap = bitmap

        width = bitmap.width
        height = bitmap.height

        rect_list.clear()
        rect_list.add(Rect(0,0,width,height))
    }
    fun SetSrcRect(rect_list: ArrayList<Rect>) { this.rect_list = rect_list }

    fun GetBitmap() : Bitmap? { return bitmap }
    fun GetSrcRect() : ArrayList<Rect> { return rect_list }
    fun GetSrcRect(index:Int) : Rect { return rect_list[index] }
    fun GetSrcRectF(index:Int) : RectF { val rectf:RectF = RectF(rect_list[index]);return rectf }

    fun InitSrcRect(rect_list:ArrayList<Rect>) { this.rect_list.clear();this.rect_list = rect_list }
    fun InitSrcRect(start_x:Int,start_y:Int,width:Int,height:Int,horizontal:Int,vertical:Int)
    {
        this.width = width
        this.height = height

        rect_list.clear()

        for (i in 0 until vertical)
        {
            for (j in 0 until horizontal)
            {
                rect_list.add(Rect(start_x + width * j,start_y + height * i,start_x + width * j + width,start_y + height * i + height))
            }
        }
    }

    //fun render(canvas: Canvas, renderer:Renderer) { renderer.DrawSprite(canvas,this) }
    fun render(canvas: Canvas, renderer:SceneRenderer) { renderer.DrawSprite(canvas,this) }
    fun render(canvas: Canvas,x:Float,y:Float,width: Int = this.width,height: Int = this.height,index:Int = 0)
    { bitmap?.let { canvas.drawBitmap(it,rect_list.get(index),RectF(x,y,x + width.toFloat(),y + height.toFloat()),null) } }
}
