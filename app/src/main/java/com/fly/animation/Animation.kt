package com.fly.animation

import android.content.res.AssetManager
import android.graphics.*
import com.fly.graphic.Renderer


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

    fun AddFrame(bitmap: Bitmap,src: Rect)
    {
        val b = Bitmap.createBitmap(bitmap,src.left,src.top,src.right - src.left,src.bottom - src.top,null,false)
        this.bitmap.add(b)
    }
    fun AddFrame(bitmap: Bitmap,x:Int,y:Int,width:Int,height:Int)
    {
        val b = Bitmap.createBitmap(bitmap,x, y, width, height,null,false)
    }
    fun AddFrame(path:String,asset_manager:AssetManager,src:Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path))
        val b = Bitmap.createBitmap(bitmap,src.left,src.top,src.right - src.left,src.bottom - src.top,null,false)
        this.bitmap.add(b)
    }
    fun AddFrame(path:String,asset_manager:AssetManager,x:Int,y:Int,width:Int,height:Int)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(path))
        val b = Bitmap.createBitmap(bitmap,x, y, width, height,null,false)
        this.bitmap.add(b)
    }

    fun Render(canvas: Canvas,renderer: Renderer,FPS:Int,x:Float,y:Float)
    {
        if (index == FPS)
            index = 0
        renderer.DrawBitmap(canvas,bitmap[index],null, RectF(x,y,x + bitmap[index].width.toFloat(),y + bitmap[index].height.toFloat()));
        if (draw_n == FPS) { draw_n = 0;index++ }
        else if(draw_n < FPS) { draw_n++ }
    }
    fun Render(canvas: Canvas,renderer: Renderer,FPS:Int,x:Float,y:Float,width:Float,height:Float)
    {
        if (index == FPS)
            index = 0
        renderer.DrawBitmap(canvas,bitmap[index],null, RectF(x,y,x + width,y + height));
        if (draw_n >= FPS) { draw_n = 0;index++ }
        else if(draw_n < FPS) { draw_n++ }
    }
}
