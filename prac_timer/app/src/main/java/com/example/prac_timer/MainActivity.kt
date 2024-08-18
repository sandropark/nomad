package com.example.prac_timer

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var startView: StartView
    private lateinit var tvTimer: TextView
    private lateinit var tvGoal: TextView
    private lateinit var btn: Button

    private var timerTask: Timer? = null
    private val scores: MutableList<Float> = mutableListOf()
    private var people = 1
    private var isBlind = true
    private var tempScore: Float = 0.0f
    private var goal: Float = 0.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startView()
    }

    private fun startView() {
        setContentView(R.layout.activity_start)
        startView = StartView(
            findViewById(R.id.tv_count),
            findViewById(R.id.btn_minus),
            findViewById(R.id.btn_plus),
            findViewById(R.id.btn_start),
            2
        )
        startView.setOnclickListenerOnBtnStart {
            people = 1
            main()
        }
    }

    private fun main() {
        setContentView(R.layout.activity_main)
        tvTimer = findViewById(R.id.tv_timer)
        tvGoal = findViewById(R.id.tv_goal)
        btn = findViewById(R.id.btn_command)

        findViewById<TextView>(R.id.tv_people).text = "참가자 $people"
        initGoal()
        setButtonOnClickListener()
    }

    private fun endView() {
        setContentView(R.layout.activity_end)
        val maxScore = scores.max()
        findViewById<TextView>(R.id.tv_biggest_score).text = maxScore.toString()
        findViewById<TextView>(R.id.tv_end_people).text = "참가자 ${scores.indexOf(maxScore) + 1}"
        findViewById<Button>(R.id.btn_restart).setOnClickListener {
            scores.clear()
            startView()
        }
    }

    private fun setButtonOnClickListener() {
        var stage = 1
        btn.setOnClickListener {
            when (stage % 3) {
                0 -> {
                    if (startView.isTotalPeopleBiggerThan(people)) {
                        people++
                        main()
                        stage++
                    } else {
                        endView()
                    }
                }

                1 -> {
                    start()
                    btn.text = "Stop"
                    stage++
                }

                2 -> {
                    stop()
                    stage++
                }
            }
        }
    }

    private fun initGoal() {
        goal = Random().nextInt(1001) / 100f
        tvGoal.text = goal.toString()
    }

    private fun start() {
        var sec = 0f
        timerTask = timer(period = 10) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tempScore = (sec / 100)
                if (isBlind) {
                    tvTimer.text = "???"
                } else {
                    tvTimer.text = tempScore.toString()
                }
            }
        }
    }

    private fun stop() {
        timerTask?.cancel()
        btn.text = "Next"

        val score = abs(goal - tempScore)
        scores.add(score)

        val tvScore: TextView = findViewById(R.id.tv_score)
        tvScore.text = "score: ${String.format("%.2f", score)}"
    }

}