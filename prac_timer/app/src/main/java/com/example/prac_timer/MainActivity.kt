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
    private lateinit var tvTimer: TextView
    private lateinit var tvGoal: TextView
    private lateinit var btn: Button
    private var timerTask: Timer? = null
    private var totalPeople = 1
    private val scores: MutableList<Float> = mutableListOf()
    private var people = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startView()
    }

    private fun startView() {
        setContentView(R.layout.activity_start)
        val tvCount: TextView = findViewById(R.id.tv_count)
        findViewById<Button>(R.id.btn_minus).setOnClickListener {
            if (totalPeople > 1) {
                totalPeople--
                tvCount.text = totalPeople.toString()
            }
        }
        findViewById<Button>(R.id.btn_plus).setOnClickListener {
            if (totalPeople < 99) {
                totalPeople++
                tvCount.text = totalPeople.toString()
            }
        }
        findViewById<Button>(R.id.btn_start).setOnClickListener {
            people = 1
            main()
        }
    }

    private fun main() {
        setContentView(R.layout.activity_main)
        tvGoal = findViewById(R.id.tv_goal)
        tvTimer = findViewById(R.id.tv_timer)
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
                    if (people <= totalPeople) {
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