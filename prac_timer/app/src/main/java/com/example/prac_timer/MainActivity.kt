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
    private lateinit var tvGoal: TextView;
    private lateinit var btn: Button
    private var timerTask: Timer? = null
    private var stage = 1
    private var k = 1
    private val scores: MutableList<Float> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main()
    }

    private fun main() {
        setContentView(R.layout.activity_main)
        tvGoal = findViewById(R.id.tv_goal)
        tvTimer = findViewById(R.id.tv_timer)
        btn = findViewById(R.id.btn_command)
        val tvPeople: TextView = findViewById(R.id.tv_people)
        tvPeople.text = "참가자 $k"
        initGoal()
        setButtonOnClickListener()
    }

    private fun setButtonOnClickListener() {
        btn.setOnClickListener {
            when (stage % 3) {
                0 -> {
                    if (k < 3) {
                        k++
                        main()
                        stage++
                    } else {
                        println(scores)
                    }
                }

                1 -> {
                    start()
                    btn.text = "Stop"
                    stage++
                }

                2 -> {
                    stop()
                    lenderScore()
                    stage++
                }
            }
        }
    }

    private fun initGoal() {
        val goal = Random().nextInt(1001) / 100f
        tvGoal.text = goal.toString()
    }

    private fun start() {
        var sec = 0f
        timerTask = timer(period = 10) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tvTimer.text = (sec / 100).toString()
            }
        }
    }

    private fun stop() {
        timerTask?.cancel()
        btn.text = "Next"
    }

    @SuppressLint("DefaultLocale", "SetTextI18n")
    private fun lenderScore() {
        val tvScore: TextView = findViewById(R.id.tv_score)
        val score = abs(tvGoal.text.toString().toFloat() - tvTimer.text.toString().toFloat())
        scores.add(score)
        tvScore.text = "score: ${String.format("%.2f", score)}"
    }
}