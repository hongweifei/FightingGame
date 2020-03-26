package com.fly.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.graphics.RectF
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import com.fly.fightinggame.R
import com.fly.graphic.Camera
import com.fly.graphic.Object
import com.fly.graphic.SceneRenderer
import com.fly.physics.CollisionBox
import com.fly.physics.RigidBody
import com.fly.ui.ImageButton
import kotlinx.android.synthetic.main.game.*


class Game : Activity() , View.OnTouchListener
{
    var camera: Camera = Camera(0f,0f)
    var renderer : SceneRenderer = SceneRenderer(camera)

    var width:Float = 0f
    var height:Float = 0f

    var touch_x:Float = 0f
    var touch_y:Float = 0f

    val dm = DisplayMetrics()

    lateinit var player:Player

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        val wm = this.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(dm)

        //width = dm.widthPixels // 屏幕宽度（像素）
        //height = dm.heightPixels // 屏幕高度（像素）

        //val density = dm.density // 屏幕密度（0.75 / 1.0 / 1.5）
        //val densityDpi = dm.densityDpi // 屏幕密度dpi（120 / 160 / 240）

        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //width = (width / density).toInt() // 屏幕宽度(dp)
        //height = (height / density).toInt() // 屏幕高度(dp)

        width = 10f
        height = 5f

        setContentView(R.layout.game)
        InitGame()
        Game()
    }

    @SuppressLint("SetTextI18n")
    fun InitGame()
    {
        val wall = Object(scene)
        wall.SetSprite("ui/RockerBackGround.png",assets)
        wall.width = width
        wall.height = height / 6
        wall.y = height - wall.height
        wall.SetCollisionBox(CollisionBox(RectF(wall.x,wall.y + wall.height / 2,wall.x + wall.width,wall.y + wall.height / 2 + wall.height)))

        player = Player(scene,"player/Kakashi_Right.png",assets)
        player.width = width / 10
        player.height = height / 5
        player.x = width / 2 - player.width / 2
        player.y = 0f
        //player.SetSprite()
        player.SetRigidBody(RigidBody(0f,9.8f))
        player.SetCollisionBox(CollisionBox(RectF(player.x,player.y,player.x + player.width,player.y + player.height)))
        player.AddCollide(wall)
        player.InitSpriteSrcRect(0,0,80,80,10,7)

        scene.SetSceneRenderer(renderer)
        scene.SetCamera(camera)

        //renderer.SetShader()
        renderer.SetDisplay {
            wall.Render(it,renderer)
            if (player.way == WayLeft && player.run == None)
                player.RenderObjectAnimation(it,renderer,6,9,4)
            else if(player.way == WayRight && player.run == None)
                player.RenderObjectAnimation(it,renderer,0,3,4)
            if (player.jump)
                player.Jump()
            if (player.run != None)
            {
                player.Run()
                if (player.way == WayLeft)
                    player.RenderObjectAnimation(it,renderer,2,5,4)
                else if(player.way == WayRight)
                    player.RenderObjectAnimation(it,renderer,4,7,4)
            }
            if (player.drop)
                player.Drop()

            //textView.text = "FPS:" + (1000 / renderer.GetRenderTime()).toString()
        }


        InitSkillButton()
    }

    fun Game()
    {
        JumpButton.setOnTouchListener(this)
        DownButton.setOnTouchListener(this)
        LeftButton.setOnTouchListener(this)
        RightButton.setOnTouchListener(this)

        ui_view.SetClick { touch_x = it?.rawX!!;touch_y = it.rawY }

        ui_view.SetMove {
            if (it != null)
            {
                val dx = it.rawX - touch_x
                val dy = it.rawY - touch_y

                camera.look_at_x += dx / 1000
                camera.look_at_y += dy / 1000

                Log.e("ViewMove","X:" + camera.look_at_x + "Y:" + camera.look_at_y)
            }
        }
    }

    fun InitSkillButton()
    {
        val button_width:Float = (dm.widthPixels / 2 / 6).toFloat()
        val button_height:Float = (dm.heightPixels / 2 / 5).toFloat()

        val skill_button:ImageButton = ImageButton("ui/RockerButton.png",assets,dm.widthPixels - button_width,dm.heightPixels.toFloat()/2,button_width,button_height)
        val button_1:ImageButton = ImageButton("ui/RockerButton.png",assets,0 * button_width,0 * button_height,button_width,button_height)
        val button_2:ImageButton = ImageButton("ui/RockerButton.png",assets,1 * button_width,0 * button_height,button_width,button_height)
        val button_3:ImageButton = ImageButton("ui/RockerButton.png",assets,2 * button_width,0 * button_height,button_width,button_height)
        val button_4:ImageButton = ImageButton("ui/RockerButton.png",assets,3 * button_width,0 * button_height,button_width,button_height)
        val button_5:ImageButton = ImageButton("ui/RockerButton.png",assets,0 * button_width,1 * button_height,button_width,button_height)
        val button_6:ImageButton = ImageButton("ui/RockerButton.png",assets,1 * button_width,1 * button_height,button_width,button_height)
        val button_7:ImageButton = ImageButton("ui/RockerButton.png",assets,2 * button_width,1 * button_height,button_width,button_height)
        val button_8:ImageButton = ImageButton("ui/RockerButton.png",assets,3 * button_width,1 * button_height,button_width,button_height)
        val button_9:ImageButton = ImageButton("ui/RockerButton.png",assets,0 * button_width,2 * button_height,button_width,button_height)
        val button_10:ImageButton = ImageButton("ui/RockerButton.png",assets,1 * button_width,2 * button_height,button_width,button_height)
        val button_11:ImageButton = ImageButton("ui/RockerButton.png",assets,2 * button_width,2 * button_height,button_width,button_height)
        val button_12:ImageButton = ImageButton("ui/RockerButton.png",assets,3 * button_width,2 * button_height,button_width,button_height)

        button_1.SetAlpha(40)
        button_2.SetAlpha(40)
        button_3.SetAlpha(40)
        button_4.SetAlpha(40)
        button_5.SetAlpha(40)
        button_6.SetAlpha(40)
        button_7.SetAlpha(40)
        button_8.SetAlpha(40)
        button_9.SetAlpha(40)
        button_10.SetAlpha(40)
        button_11.SetAlpha(40)
        button_12.SetAlpha(40)

        skill_button.SetClick { player.Skill(this) }
        button_1.SetClick { player.skill_button_1 = true;button_1.SetAlpha(255) }
        button_2.SetClick { player.skill_button_2 = true;button_2.SetAlpha(255) }
        button_3.SetClick { player.skill_button_3 = true }
        button_4.SetClick { player.skill_button_4 = true }
        button_5.SetClick { player.skill_button_5 = true }
        button_6.SetClick { player.skill_button_6 = true }
        button_7.SetClick { player.skill_button_7 = true }
        button_8.SetClick { player.skill_button_8 = true }
        button_9.SetClick { player.skill_button_9 = true }
        button_10.SetClick { player.skill_button_10 = true }
        button_11.SetClick { player.skill_button_11 = true }
        button_12.SetClick { player.skill_button_12 = true }

        ui_view.AddWidget(skill_button)
        ui_view.AddWidget(button_1)
        ui_view.AddWidget(button_2)
        ui_view.AddWidget(button_3)
        ui_view.AddWidget(button_4)
        ui_view.AddWidget(button_5)
        ui_view.AddWidget(button_6)
        ui_view.AddWidget(button_7)
        ui_view.AddWidget(button_8)
        ui_view.AddWidget(button_9)
        ui_view.AddWidget(button_10)
        ui_view.AddWidget(button_11)
        ui_view.AddWidget(button_12)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean
    {
        if (event?.action == MotionEvent.ACTION_DOWN)
        {
            touch_x = event.rawX
            touch_y = event.rawY
            when (v?.id)
            {
                R.id.JumpButton -> player.jump = true
                R.id.LeftButton -> player.SetRun(WayLeft,assets)
                R.id.RightButton -> player.SetRun(WayRight,assets)
            }
        }
        if (event?.action == MotionEvent.ACTION_UP)
        {
            when(v?.id)
            {
                R.id.JumpButton -> player.jump = false
                R.id.LeftButton -> player.run = None
                R.id.RightButton -> player.run = None
            }
        }

        return true
    }

}
