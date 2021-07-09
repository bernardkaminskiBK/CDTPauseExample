package com.android10_kotlin.cdtpauseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var restProgress = 0
    private var isPaused = false
    private var isStarted = true
    private var timer: CountDownTimer? = null

    private var resumeFromMillis: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        llStartTimer.setOnClickListener {
//            if (isStarted) {
//                isPaused = true
//                isStarted = false
//            } else {
//                isPaused = false
//                isStarted = true
//                timer(15000, 1000)
//                toast("Started")
//            }
//        }

        isPaused = false
        btnStart.isEnabled = false
        btnResume.isEnabled = false
        timer(15000, 1000).start()

        btnStart.setOnClickListener {
            isPaused = false
            timer(15000, 1000).start()
            btnPause.isEnabled = true
        }

        btnPause.setOnClickListener {
            isPaused = true
            btnResume.isEnabled = true
            btnPause.isEnabled = false
        }

        btnResume.setOnClickListener {
            timer(resumeFromMillis!!, 1000).start()
            isPaused = false
            btnResume.isEnabled = false
            btnPause.isEnabled = true
        }

    }

    private fun timer(millisInFuture: Long, countDownInterval: Long): CountDownTimer {
        btnStart.isEnabled = false
        timer = object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = "${millisUntilFinished / 1000}"
                if (isPaused) {
                    tvTimer.text = "${tvTimer.text}"
                    restProgress--
                    cancel()
                } else {
                    restProgress++
                    resumeFromMillis = millisUntilFinished
                    progressBar.progress = 15 - restProgress
                    tvTimer.text = timeRemaining
                }
            }

            override fun onFinish() {
                restProgress = 0
                progressBar.progress = 15
                tvTimer.text = 15.toString()
                btnStart.isEnabled = true
                btnPause.isEnabled = false
            }
        }
        return timer!!
    }

}