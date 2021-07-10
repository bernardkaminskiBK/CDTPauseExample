package com.android10_kotlin.cdtpauseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isPaused = false
    private var isStarted = true

    private var resumeFromMillis: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llStartTimer.setOnClickListener {
            if (isStarted) {
                isPaused = true
                isStarted = false
                Toast.makeText(this, "paused", Toast.LENGTH_SHORT).show()
                ivPauseStart.setImageResource(R.drawable.ic_baseline_play_arrow_24)
//                tvTimer.visibility = View.GONE
//                ivPauseStart.visibility = View.VISIBLE
            } else {
                isPaused = false
                isStarted = true
                timer(resumeFromMillis!!, 1000).start()
                Toast.makeText(this, "started", Toast.LENGTH_SHORT).show()
                ivPauseStart.setImageResource(R.drawable.ic_baseline_pause_24)
//                tvTimer.visibility = View.GONE
//                ivPauseStart.visibility = View.VISIBLE
            }
        }

        isPaused = false
        btnStart.isEnabled = false
        timer(15000, 1000).start()

        btnStart.setOnClickListener {
            isPaused = false
            timer(15000, 1000).start()
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
            }
        }
    }

}