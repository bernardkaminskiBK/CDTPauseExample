package com.android10_kotlin.cdtpauseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isPaused = false
    private var isStarted = true

    private var resumeFromMillis: Long? = 0
    private var timeForCountDown: Int = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startTimer()
        btnStart.setOnClickListener(this)
        llTimer.setOnClickListener(this)
    }

    private fun startTimer() {
        isPaused = false
        btnStart.isEnabled = false
        progressBar.max = timeForCountDown
        timer((timeForCountDown * 1000).toLong(), 1000).start()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnStart -> {
                isPaused = false
                timer((timeForCountDown * 1000).toLong(), 1000).start()
                llPauseStart.isEnabled = true
                llTimer.isEnabled = true
            }
            R.id.llTimer -> {
                if (isStarted) {
                    isPaused = true
                    isStarted = false
                    Toast.makeText(this, "paused", Toast.LENGTH_SHORT).show()
                    timerAnimation(1000, 100)
                } else {
                    isPaused = false
                    isStarted = true
                    timer(resumeFromMillis!!, 1000).start()
                    Toast.makeText(this, "started", Toast.LENGTH_SHORT).show()
                    timerAnimation(1000, 100)
                }
            }
        }
    }

    private fun timer(millisInFuture: Long, countDownInterval: Long): CountDownTimer {
        btnStart.isEnabled = false
        return object : CountDownTimer(millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val timeRemaining = millisUntilFinished / 1000
                if (isPaused) {
                    tvTimer.text = tvTimer.text
                    cancel()
                } else {
                    resumeFromMillis = millisUntilFinished
                    progressBar.incrementProgressBy(-1)
                    progressBar.progress = timeRemaining.toInt()
                    tvTimer.text = timeRemaining.toString()
                }
            }

            override fun onFinish() {
                progressBar.progress = 0
                btnStart.isEnabled = true
                llPauseStart.isEnabled = false
                llTimer.isEnabled = false
            }
        }
    }

    private fun timerAnimation(timerAnimDuration: Long, pauseStartAnimDuration: Long) {
        llTimer.animate().apply {
            duration = timerAnimDuration
            alpha(0f)
            llPauseStart.animate().apply {
                duration = pauseStartAnimDuration
                alpha(1f)
                if (isPaused) {
                    ivPauseStart.setImageResource(R.drawable.ic_baseline_pause_24)
                } else {
                    ivPauseStart.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                }
            }
        }.withEndAction {
            llPauseStart.animate().apply {
                duration = pauseStartAnimDuration
                alpha(0f)
                llTimer.animate().apply {
                    duration = timerAnimDuration
                    alpha(1f)
                }
            }
        }
    }

}