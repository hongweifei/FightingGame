package com.fly.graphic


import android.content.res.AssetManager
import android.graphics.*
import android.util.Log
import android.view.View.LAYER_TYPE_HARDWARE
import android.view.View.LAYER_TYPE_SOFTWARE


open class Renderer()
{
    private var render : (canvas : Canvas,FPS:Long?) -> Unit = { canvas: Canvas, FPS: Long? -> };
    protected var paint : Paint = Paint()
    private var layer_type = LAYER_TYPE_HARDWARE

    protected var FPS:Long? = null

    init
    {
        paint.strokeWidth = 2f
        paint.isAntiAlias = true
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL_AND_STROKE;

        paint.textAlign = Paint.Align.LEFT;//设置文字对齐方式  取值：align.CENTER、align.LEFT或align.RIGHT 默认align.LEFT
        paint.textSize = 12F;//设置文字大小
        paint.isFakeBoldText = false;//设置是否为粗体文字
        paint.isUnderlineText = false;//设置下划线
        paint.textSkewX = 0f;//设置字体水平倾斜度  普通斜体字是-0.25
        paint.textScaleX = 1f;//只会将水平方向拉伸  高度不会变
    }

    fun SetDisplay(Display: (canvas : Canvas,FPS:Long?) -> Unit) { render = Display }
    fun Display(canvas: Canvas,FPS:Long?) { this.FPS = FPS;render(canvas,FPS) }

    fun SetStrokeWidth(stroke_width:Float) { paint.strokeWidth = stroke_width }
    fun SetAntiAlias(aa:Boolean) { paint.isAntiAlias = aa }
    fun SetColor(color:Int) { paint.setColor(color) }
    fun SetAlpha(a:Int) { paint.alpha = a }//设置透明程度
    fun SetStyle(style:Paint.Style) { paint.style = style }
    fun SetShader(shader:Shader) { paint.setShader(shader) }
    fun SetShadowLayer(radius:Float,dx:Float,dy:Float,shadow_color:Int) { paint.setShadowLayer(radius,dx,dy,shadow_color);layer_type = LAYER_TYPE_SOFTWARE }

    fun GetHeight(canvas: Canvas) : Int { return canvas.height }
    fun GetWidth(canvas: Canvas) : Int { return canvas.width }
    fun GetPaint() : Paint { return paint }
    fun GetLayerType() : Int { return layer_type }

    open fun DrawPoint(canvas: Canvas, x:Float, y:Float){ canvas.drawPoint(x,y,paint) }
    open fun DrawPoints(canvas: Canvas, pts:FloatArray) { canvas.drawPoints(pts,paint) }

    open fun DrawLine(canvas: Canvas, start_x:Float, start_y:Float, stop_x:Float, stop_y:Float) { canvas.drawLine(start_x,start_y,stop_x,stop_y,paint) }
    open fun DrawPath(canvas: Canvas, path:Path) { canvas.drawPath(path,paint) }

    open fun DrawRect(canvas: Canvas, left:Float, top:Float, right:Float, bottom:Float) { canvas.drawRect(left, top, right, bottom, paint) }
    open fun DrawRect(canvas: Canvas, rect:Rect) { canvas.drawRect(rect, paint) }
    open fun DrawRect(canvas: Canvas, rect:RectF) { canvas.drawRect(rect, paint) }
    open fun DrawRect(canvas: Canvas, x:Int, y:Int, width: Int, height: Int) { canvas.drawRect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat(),paint) }

    open fun DrawRegion(canvas: Canvas, region:Region)
    {
        val iterator = RegionIterator(region)
        val rect = Rect()
        while (iterator.next(rect)) { canvas.drawRect(rect, paint) }
    }

    open fun DrawText(canvas: Canvas, text:String, x:Float, y:Float) { canvas.drawText(text,x,y,paint) }

    open fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, matrix: Matrix) { canvas.drawBitmap(bitmap,matrix,paint) }
    open fun DrawBitmap(canvas: Canvas, bitmap: Bitmap, src: Rect? = null, dst: Rect) { canvas.drawBitmap(bitmap,src,dst,paint) }
    open fun DrawBitmap(canvas: Canvas,bitmap: Bitmap,src: Rect? = null,dst: RectF) { canvas.drawBitmap(bitmap,src,dst,paint) }
    open fun DrawBitmap(canvas: Canvas,bitmap:Bitmap,left:Float,top:Float) { canvas.drawBitmap(bitmap,left,top,paint) }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,matrix,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,src: Rect? = null,dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,src: Rect? = null,dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,left:Float,top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeFile(bitmap_path)
        canvas.drawBitmap(bitmap,left,top,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,matrix: Matrix)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,matrix,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,src: Rect? = null,dst: Rect)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,src: Rect? = null,dst: RectF)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,src,dst,paint)
    }
    open fun DrawBitmap(canvas: Canvas,bitmap_path: String,asset_manager: AssetManager,left:Float,top:Float)
    {
        val bitmap:Bitmap = BitmapFactory.decodeStream(asset_manager.open(bitmap_path))
        canvas.drawBitmap(bitmap,left,top,paint)
    }

    open fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float = 0f, y: Float = 0f, width:Int = sprite.width, height:Int = sprite.height, index:Int = 0)
    {
        val dst:RectF = RectF(x,y,x + width.toFloat(),y + height.toFloat())
        sprite.GetBitmap()?.let { canvas.drawBitmap(it,sprite.GetSrcRect(index),dst,paint) }
    }
    open fun DrawSprite(canvas: Canvas, sprite:Sprite, x:Float = 0f, y: Float = 0f, width:Int = sprite.width, height:Int = sprite.height, index:Int = 0, flip_x:Float = 1f, flip_y: Float = 1f)
    {
        val dst:RectF = RectF(x,y,x + width.toFloat(),y + height.toFloat())
        val matrix:Matrix = Matrix()

        matrix.setScale(flip_x,flip_y)
        matrix.postTranslate(x, y)
        matrix.setRectToRect(sprite.GetSrcRectF(index),dst,null)

        sprite.GetBitmap()?.let { canvas.drawBitmap(it,matrix,paint) }
    }

    open fun DrawObject(canvas: Canvas, obj: Object, width: Int = obj.GetSprite()?.width!!, height: Int = obj.GetSprite()?.height!!, index: Int = 0)
    {
        obj.GetSprite()?.let { DrawSprite(canvas, it,obj.x,obj.y,width,height,index) }
        if (obj.GetCollisionBox() != null)
        {
            obj.GetCollisionBox()!!.SetRect(RectF(obj.x,obj.y,obj.x + width,obj.y + height))
            if (obj.GetRigid() != null && FPS != null && !obj.GetCollisionBox()!!.Collision())
                obj.y += obj.GetRigid()!!.GetDropHeight((1000 / FPS!!).toFloat())
        }
    }

}
