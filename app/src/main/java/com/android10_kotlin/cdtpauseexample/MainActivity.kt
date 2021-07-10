package com.android10_kotlin.cdtpauseexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isPaused = false
    private var isStarted = true

    private var resumeFromMillis: Long? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llTimer.setOnClickListener {
            if (isStarted) {
                isPaused = true
                isStarted = false
                Toast.makeText(this, "paused", Toast.LENGTH_SHORT).show()

                llTimer.animate().apply {
                    duration = 1000
                    alpha(0f)
                    llPauseStart.animate().apply {
                        duration = 500
                        alpha(1f)
                        ivPauseStart.setImageResource(R.drawable.ic_baseline_pause_24)
                    }
                }.withEndAction {
                    llPauseStart.animate().apply {
                        duration = 500
                        alpha(0f)
                        llTimer.animate().apply {
                            duration = 1000
                            alpha(1f)
                        }
                    }
                }
            } else {
                isPaused = false
                isStarted = true
                timer(resumeFromMillis!!, 1000).start()
                Toast.makeText(this, "started", Toast.LENGTH_SHORT).show()

                llTimer.animate().apply {
                    duration = 1000
                    alpha(0f)
                    llPauseStart.animate().apply {
                        duration = 500
                        alpha(1f)
                        ivPauseStart.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    }
                }.withEndAction {
                    llPauseStart.animate().apply {
                        duration = 500
                        alpha(0f)
                        llTimer.animate().apply {
                            duration = 1000
                            alpha(1f)
                        }
                    }
                }
            }
        }

        isPaused = false
        btnStart.isEnabled = false
        timer(15000, 1000).start()

        btnStart.setOnClickListener {
            isPaused = false
            timer(15000, 1000).start()
            llPauseStart.isEnabled = true
            llTimer.isEnabled = true
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

}