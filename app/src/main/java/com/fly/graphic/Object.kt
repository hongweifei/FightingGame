package com.fly.graphic

import android.content.res.AssetManager
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.RectF
import android.view.MotionEvent
import com.fly.animation.Animation
import com.fly.animation.Animator
import com.fly.physics.CollisionBox
import com.fly.physics.RigidBody

open class Object(scene: Scene? = null,var x:Float = 0f, var y:Float = 0f)
{
    var width:Float = 0f
    var height:Float = 0f

    protected var width_ratio:Float = scene?.GetSceneWidthRatio() ?: 1f
    protected var height_ratio:Float = scene?.GetSceneHeightRatio() ?: 1f

    private var click : (event: MotionEvent?) -> Unit = {}
    private var up : (event: MotionEvent?) -> Unit = {}
    private var move:(event: MotionEvent?)->Unit = {}

    private var render_animation = false
    private var render_n:Int = 0
    private var render_index = 0
    private var render_last_begin:Int? = null
    private var render_last_end:Int? = null

    protected var sprite:Sprite? = null
    protected var animator:Animator? = null
    protected var rigid_body: RigidBody? = null
    protected var collision_box:CollisionBox? = null

    fun SetClick(click:(event: MotionEvent?) ->Unit) { this.click = click }
    fun Click(event: MotionEvent?) { click(event) }
    fun SetUp(up:(event: MotionEvent?) ->Unit) { this.up = up }
    fun Up(event: MotionEvent?) { up(event) }
    fun SetMove(move:(event: MotionEvent?) ->Unit) { this.move = move }
    fun Move(event: MotionEvent?) { move(event) }

    fun SetSprite(sprite: Sprite,init_w_h:Boolean = true,sprite_rect_continue:Boolean = false)
    {
        if (sprite_rect_continue)
            sprite.SetSrcRect(this.sprite?.GetSrcRect()!!)
        this.sprite = sprite
        if (init_w_h) { width = this.sprite!!.width;height = this.sprite!!.height }
    }
    fun SetSprite(path: String,asset_manager: AssetManager,init_w_h:Boolean = true,sprite_rect_continue:Boolean = false)
    {
        val sprite = Sprite(path,asset_manager)

        if (sprite_rect_continue)
            sprite.SetSrcRect(this.sprite?.GetSrcRect()!!)
        this.sprite = sprite

        if (init_w_h) { width = this.sprite!!.width;height = this.sprite!!.height }
    }

    fun SetAnimator(animator: Animator) { this.animator = animator }

    fun SetRigidBody(rigid_body: RigidBody) { this.rigid_body = rigid_body }
    fun SetCollisionBox(collision_box: CollisionBox)
    {
        val box:CollisionBox = collision_box
        if (box.rect != null)
        {
            box.rect = RectF(box.rect!!.left * width_ratio,box.rect!!.top * height_ratio,
                box.rect!!.right * width_ratio,box.rect!!.bottom * height_ratio)
        }
        this.collision_box = box
    }

    fun GetSprite() : Sprite? { return sprite }
    fun GetAnimator() : Animator? { return animator }
    fun GetRigid() : RigidBody? { return rigid_body }
    fun GetCollisionBox() : CollisionBox? { return collision_box }

    fun AddAnimation(animation: Animation) { animator?.AddAnimation(animation) }

    fun AddCollide(r:RectF)
    {
        val rect = RectF(r.left * width_ratio,r.top * height_ratio,r.right * width_ratio,r.bottom * height_ratio)
        collision_box?.AddCollide(rect)
    }
    fun AddCollide(collision_box: CollisionBox)
    {
        val box:CollisionBox = collision_box
        if (box.rect != null)
            box.rect = RectF(box.rect!!.left * width_ratio,box.rect!!.top * height_ratio, box.rect!!.right * width_ratio,box.rect!!.bottom * height_ratio)
        this.collision_box?.AddCollide(box)
    }
    fun AddCollide(obj:Object) { collision_box?.AddCollide(obj.collision_box!!) }

    fun InitSrcRect(rect_list:ArrayList<Rect>) { sprite?.GetSrcRect()?.clear();sprite?.SetSrcRect(rect_list) }
    fun InitSpriteSrcRect(start_x:Int,start_y:Int,width:Int,height:Int,horizontal:Int,vertical:Int)
    { sprite?.InitSrcRect(start_x,start_y, width, height, horizontal, vertical) }

    fun RenderSpriteAnimation(canvas: Canvas,renderer: Renderer,begin_index:Int,end_index:Int,FPS:Int)
    { sprite?.RenderSpriteAnimation(canvas,renderer, x, y, width, height,begin_index, end_index, FPS) }
    fun RenderSpriteAnimation(canvas: Canvas,renderer: Renderer,width: Float,height: Float,begin_index:Int,end_index:Int,FPS:Int)
    { sprite?.RenderSpriteAnimation(canvas,renderer, x, y, width, height,begin_index, end_index, FPS) }

    fun RenderAnimation(canvas: Canvas, renderer: Renderer, FPS:Int,index:Int) { animator?.RenderAnimation(canvas,renderer,FPS,x * width_ratio, y * height_ratio, index) }
    fun RenderAnimation(canvas: Canvas, renderer: Renderer, width: Float,height: Float,FPS:Int,index:Int)
    { animator?.RenderAnimation(canvas, renderer, FPS, x * width_ratio, y * height_ratio, width * width_ratio, height * height_ratio, index) }

    fun RenderObjectAnimation(canvas: Canvas,renderer: Renderer,begin_index:Int = 0,end_index:Int = 0,FPS:Int = 0)
    { RenderObjectAnimation(canvas, renderer, width, height, begin_index, end_index, FPS) }
    fun RenderObjectAnimation(canvas: Canvas,renderer: Renderer,width: Float = this.width,height: Float = this.height,begin_index:Int = 0,end_index:Int = 0,FPS:Int = 0)
    {
        if (!render_animation) { render_index = begin_index;render_last_begin = begin_index;render_last_end = end_index;render_animation = true }
        else { if (render_last_begin != begin_index || render_last_end != end_index) { render_index = begin_index;render_last_begin = begin_index;render_last_end = end_index } }
        renderer.DrawObject(canvas,this,width,height,render_index)
        if (render_n >= FPS) { render_n = 0;render_index++ }
        else if (render_n < FPS) { render_n++ }
        if (render_index > end_index)
            render_animation = false
    }

    //fun Render(canvas: Canvas, renderer: Renderer) { renderer.DrawObject(canvas,this) }
    fun Render(canvas: Canvas, renderer: Renderer) { renderer.DrawObject(canvas,this) }
    fun Render(canvas: Canvas, renderer: Renderer,index: Int) { renderer.DrawObject(canvas,this,width, height,index) }
    fun Render(canvas: Canvas, renderer: Renderer, width: Float = this.width, height: Float = this.height, index: Int)
    { renderer.DrawObject(canvas,this,width,height,index) }
}
