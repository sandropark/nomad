package com.example.prac_timer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var tvTimer: TextView;
    private var isRunning = false
    private var timerTask: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn: Button = findViewById(R.id.btn_command)
        tvTimer = findViewById(R.id.tv_timer)
        val goal = initGoal()

        btn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                startTimer()
                btn.text = "Stop"
            } else {
                timerTask?.cancel()
                btn.text = "Start"
                lenderScore(goal)
            }
        }
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun lenderScore(goal: Float) {
        val tvScore: TextView = findViewById(R.id.tv_score)
        tvScore.text =
            "score: " + String.format("%.2f", abs(goal - tvTimer.text.toString().toFloat()))
    }

    private fun initGoal(): Float {
        val tvGoal: TextView = findViewById(R.id.tv_goal)
        val goal = Random().nextInt(1001) / 100f
        tvGoal.text = goal.toString()
        return goal
    }

    private fun startTimer() {
        var sec: Float = 0f
        timerTask = timer(period = 10) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tvTimer.text = (sec / 100).toString()
            }
        }
    }
}