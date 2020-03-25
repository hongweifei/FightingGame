package com.fly.animation

import android.content.res.AssetManager
import android.graphics.*
import android.text.style.LineHeightSpan
import com.fly.graphic.Renderer
import com.fly.graphic.Scene

class Animation
{
    private var bitmap:ArrayList<Bitmap> = ArrayList<Bitmap>()
    private var frame:Int = 0
    private var index:Int = 0
    private var draw_n:Int = 0

    fun AddFrame(bitmap: Bitmap) { this.bitmap.add(bitmap);frame++ }
    fun AddFrame(bitmap: Bitmap,index:Int) { this.bitmap.add(index,bitmap);frame++ }
    fun AddFrame(path:String) { val bitmap:Bitmap = BitmapFactory.decodeFile(path);this.bitmap.add(bitmap);frame++ }
    fun AddFrame(path:String,index: Int) { val bitmap:Bitmap = BitmapFactory.decodeFile(path);this.bitmap.add(index,bitmap);frame++ }
    fun AddFrame(path:String,asset_manager:AssetManager) { val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path));this.bitmap.add(bitmap);frame++ }
    fun AddFrame(path:String,asset_manager:AssetManager,index: Int) { val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path));this.bitmap.add(index,bitmap);frame++ }

    fun Render(canvas: Canvas,renderer: Renderer,FPS:Int,x:Float,y:Float)
    {
        renderer.DrawBitmap(canvas,bitmap[index],null, RectF(x,y,bitmap[index].width.toFloat(),bitmap[index].height.toFloat()));
        if (draw_n >= FPS) { draw_n = 0;index++ }
        else if(draw_n < FPS) { draw_n++ }
    }
    fun Render(canvas: Canvas,renderer: Renderer,FPS:Int,x:Float,y:Float,width:Int,height:Int)
    {
        renderer.DrawBitmap(canvas,bitmap[index],null, RectF(x,y,width.toFloat(),height.toFloat()));
        if (draw_n >= FPS) { draw_n = 0;index++ }
        else if(draw_n < FPS) { draw_n++ }
    }
}
