package com.demirli.a44highstriker

import android.graphics.Color
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.SeekBar
import androidx.core.graphics.ColorUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var runnable = Runnable {  }
    private var handler = Handler()

    private var score = 0
    private var attempts = 0

    private lateinit var sound: Ringtone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            sound = RingtoneManager.getRingtone(this, notification)
        }catch (e: Exception){
            e.printStackTrace()
        }

        strike_btn.setOnClickListener {

            handler.removeCallbacks(runnable)

            result_tv.setText("")

            strike_btn.isClickable = false
            strike_btn.setBackgroundColor(Color.parseColor("#FF8DCEE6"))

            var winingRate = 50
            try {
                winingRate = wining_rate_et.text.toString().toInt()
            }catch (e: Exception){
            }

            val winOrLoseRng = (0..100).random()

            if(winOrLoseRng <= winingRate){
                seekBarProgress(true)
            }else{
                seekBarProgress(false)
            }
        }

        reset_btn.setOnClickListener {
            result_tv.setText("")
            wining_rate_et.setText("")
            score = 0
            attempts = 0
            seekBar.progress = 0
            startingState()
        }
    }

    fun seekBarProgress(win: Boolean){
        var progress = 100
        var startedProgress = 0

        if(win == false){
            progress = 80
        }

        var finishFlag = false

        runnable = object: Runnable{
            override fun run() {

                if(finishFlag == false){
                    if(startedProgress < progress){
                        seekBar.progress = startedProgress
                        startedProgress += 2
                    }else{
                        if(win == true){
                            result_tv.setText("KAZANDINIZ!")
                            finishFlag = true
                            score++
                            attempts++
                            sound.play()
                            startingState()
                        }else{
                            result_tv.setText("KAYBETTİNİZ :(")
                            finishFlag = true
                            attempts++
                            startingState()
                        }
                    }
                }
                handler.postDelayed(runnable, 1)
            }
        }
        handler.postDelayed(runnable, 500)
    }

    fun startingState(){
        strike_btn.isClickable = true
        strike_btn.setBackgroundColor(Color.parseColor("#FF39A5CC"))
        score_tv.setText("Score: " + score.toString() + " (" + attempts + ")" )

    }
}
