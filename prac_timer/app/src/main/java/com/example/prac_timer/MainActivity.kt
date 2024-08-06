package com.example.prac_timer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    lateinit var tv: TextView;
    private var isRunning = false
    private var timerTask: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn: Button = findViewById(R.id.btn)
        tv = findViewById(R.id.main_view)

        btn.setOnClickListener {
            isRunning = !isRunning
            if (isRunning) {
                startTimer()
                btn.text = "Stop"
            } else {
                timerTask?.cancel()
                btn.text = "Start"
            }
        }
    }

    private fun startTimer() {
        var sec: Int = 0
        timerTask = timer(period = 1000) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tv.text = sec.toString()
            }
        }
    }
}