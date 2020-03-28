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
    var renderer : SceneRenderer = SceneRenderer(null,camera)

    var width:Float = 0f
    var height:Float = 0f

    var touch_x:Float = 0f
    var touch_y:Float = 0f

    val dm = DisplayMetrics()

    lateinit var player1:Player
    lateinit var player2:Player

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

        width = 16f
        height = 9f

        setContentView(R.layout.game)
        InitGame()
        Game()
    }

    @SuppressLint("SetTextI18n")
    fun InitGame()
    {
        val wall = Object(scene)
        wall.SetSprite("map/01.png",assets)
        wall.width = width
        wall.height = height / 6
        wall.y = height - wall.height
        wall.SetCollisionBox(CollisionBox(RectF(wall.x,wall.y + wall.height / 2,wall.x + wall.width,wall.y + wall.height / 2)))

        /*
        val wall2 = Object(scene)
        wall2.SetSprite("map/01.png",assets)
        wall2.width = width
        wall2.height = height / 6
        wall2.y = 0f
        wall2.SetCollisionBox(CollisionBox(RectF(wall2.x,wall2.y,wall2.x + wall2.width,wall2.y + wall2.height)))
        */

        player1 = Player(scene,"player/KakashiRight.png",assets)
        player1.width = width / 10
        player1.height = height / 5
        player1.x = width / 2 - player1.width / 2
        player1.y = 1f
        //player1.SetSprite()
        player1.SetRigidBody(RigidBody(0f,9.8f))
        player1.SetCollisionBox(CollisionBox(RectF(player1.x,player1.y,player1.x + player1.width,player1.y + player1.height)))
        player1.AddCollide(wall)
        //player1.AddCollide(RectF(0f,0f,0.1f,height))
        //player1.AddCollide(RectF(0f,0f,width,0.1f))
        //player1.AddCollide(RectF(width,0f,width + 0.1f,height))
        //player1.AddCollide(wall2)
        player1.InitSpriteSrcRect(0,0,80,80,10,7)


        player2 = Player(scene,"player/KakashiRight.png",assets)
        player2.width = width / 10
        player2.height = height / 5
        player2.x = width / 2 - player2.width / 2
        player2.y = 1f
        //player2.SetSprite()
        player2.SetRigidBody(RigidBody(0f,9.8f))
        player2.SetCollisionBox(CollisionBox(RectF(player2.x,player2.y,player2.x + player2.width,player2.y + player2.height)))
        player2.AddCollide(wall)
        //player2.AddCollide(RectF(0f,0f,0.1f,height))
        //player2.AddCollide(RectF(0f,0f,width,0.1f))
        //player2.AddCollide(RectF(width,0f,width + 0.1f,height))
        //player2.AddCollide(wall2)
        player2.InitSpriteSrcRect(0,0,80,80,10,7)

        scene.SetSceneRenderer(renderer)
        scene.SetCamera(camera)

        //renderer.SetShader()
        renderer.SetDisplay {
            wall.Render(it,renderer)
            //wall2.Render(it,renderer)
            if (player1.way == WayLeft && !player1.run)
                player1.RenderObjectAnimation(it,renderer,6,9,4)
            else if(player1.way == WayRight && !player1.run)
                player1.RenderObjectAnimation(it,renderer,0,3,4)
            if (player1.jump)
                player1.Jump()
            if (player1.run)
            {
                player1.Run()
                if (player1.way == WayLeft)
                    player1.RenderObjectAnimation(it,renderer,2,5,4)
                else if(player1.way == WayRight)
                    player1.RenderObjectAnimation(it,renderer,4,7,4)
            }

            player1.Drop()

            if(player1.attack)
            {
                player1.Attack()
            }

            if (player2.way == WayLeft && !player2.run)
                player2.RenderObjectAnimation(it,renderer,6,9,4)
            else if(player2.way == WayRight && !player2.run)
                player2.RenderObjectAnimation(it,renderer,0,3,4)
            if (player2.jump)
                player2.Jump()
            if (player2.run)
            {
                player2.Run()
                if (player2.way == WayLeft)
                    player2.RenderObjectAnimation(it,renderer,2,5,4)
                else if(player2.way == WayRight)
                    player2.RenderObjectAnimation(it,renderer,4,7,4)
            }

            player2.Drop()

            if(player2.attack)
            {
                player2.Attack()
            }

            Log.e("X", player1.x.toString())
            Log.e("Y",player1.y.toString())

            textView.text = "FPS:" + scene.GetFPS().toInt().toString()
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

                Log.e("CameraMove","CameraX:" + camera.look_at_x + "CameraY:" + camera.look_at_y)
            }
        }
    }

    fun InitSkillButton()
    {
        val button_width:Float = (dm.widthPixels / 2 / 6).toFloat()
        val button_height:Float = (dm.heightPixels / 2 / 5).toFloat()

        val skill_button:ImageButton = ImageButton("ui/SkillButton.png",assets,dm.widthPixels - button_width,dm.heightPixels.toFloat()/2,button_width,button_height)
        val button_1:ImageButton = ImageButton("ui/SkillButton.png",assets,0 * button_width,0 * button_height,button_width,button_height)
        val button_2:ImageButton = ImageButton("ui/SkillButton.png",assets,1 * button_width,0 * button_height,button_width,button_height)
        val button_3:ImageButton = ImageButton("ui/SkillButton.png",assets,2 * button_width,0 * button_height,button_width,button_height)
        val button_4:ImageButton = ImageButton("ui/SkillButton.png",assets,3 * button_width,0 * button_height,button_width,button_height)
        val button_5:ImageButton = ImageButton("ui/SkillButton.png",assets,0 * button_width,1 * button_height,button_width,button_height)
        val button_6:ImageButton = ImageButton("ui/SkillButton.png",assets,1 * button_width,1 * button_height,button_width,button_height)
        val button_7:ImageButton = ImageButton("ui/SkillButton.png",assets,2 * button_width,1 * button_height,button_width,button_height)
        val button_8:ImageButton = ImageButton("ui/SkillButton.png",assets,3 * button_width,1 * button_height,button_width,button_height)
        val button_9:ImageButton = ImageButton("ui/SkillButton.png",assets,0 * button_width,2 * button_height,button_width,button_height)
        val button_10:ImageButton = ImageButton("ui/SkillButton.png",assets,1 * button_width,2 * button_height,button_width,button_height)
        val button_11:ImageButton = ImageButton("ui/SkillButton.png",assets,2 * button_width,2 * button_height,button_width,button_height)
        val button_12:ImageButton = ImageButton("ui/SkillButton.png",assets,3 * button_width,2 * button_height,button_width,button_height)

        button_1.alpha = 40
        button_2.alpha = 40
        button_3.alpha = 40
        button_4.alpha = 40
        button_5.alpha = 40
        button_6.alpha = 40
        button_7.alpha = 40
        button_8.alpha = 40
        button_9.alpha = 40
        button_10.alpha = 40
        button_11.alpha = 40
        button_12.alpha = 40

        skill_button.SetClick { player1.Skill(this) }
        button_1.SetClick { player1.skill_button[1] = true;button_1.alpha = 255;Log.e("SkillButton1Alpha",button_1.alpha.toString()) }
        button_2.SetClick { player1.skill_button[2] = true;button_2.alpha = 255 }
        button_3.SetClick { player1.skill_button[3] = true }
        button_4.SetClick { player1.skill_button[4] = true }
        button_5.SetClick { player1.skill_button[5] = true }
        button_6.SetClick { player1.skill_button[6] = true }
        button_7.SetClick { player1.skill_button[7] = true }
        button_8.SetClick { player1.skill_button[8] = true }
        button_9.SetClick { player1.skill_button[9] = true }
        button_10.SetClick { player1.skill_button[10] = true }
        button_11.SetClick { player1.skill_button[11] = true }
        button_12.SetClick { player1.skill_button[12] = true }

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
                R.id.JumpButton -> player1.jump = true
                R.id.LeftButton -> player1.SetRun(WayLeft,assets)
                R.id.RightButton -> player1.SetRun(WayRight,assets)
            }
        }
        if (event?.action == MotionEvent.ACTION_UP)
        {
            when(v?.id)
            {
                R.id.JumpButton -> player1.jump = false
                R.id.LeftButton -> player1.run = false
                R.id.RightButton -> player1.run = false
            }
        }

        return true
    }

}
