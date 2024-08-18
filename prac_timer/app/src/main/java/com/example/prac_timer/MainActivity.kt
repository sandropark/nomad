package com.example.prac_timer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.prac_timer.databinding.ActivityEndBinding
import com.example.prac_timer.databinding.ActivityMainBinding
import com.example.prac_timer.databinding.ActivityStartBinding
import java.util.Random
import java.util.Timer
import kotlin.concurrent.timer
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    private lateinit var startView: StartView
    private lateinit var mainBinding: ActivityMainBinding

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
        val startBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(startBinding.root)
        startView = StartView(startBinding, 2)
        startView.setOnclickListenerOnBtnStart {
            people = 1
            main()
        }
    }

    private fun main() {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        mainBinding.tvPeople.text = "참가자 $people"
        initGoal()
        setButtonOnClickListener()
    }

    private fun endView() {
        val endBinding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(endBinding.root)
        val maxScore = scores.max()
        endBinding.tvBiggestScore.text = maxScore.toString()
        endBinding.tvEndPeople.text = "참가자 ${scores.indexOf(maxScore) + 1}"
        endBinding.btnRestart.setOnClickListener {
            scores.clear()
            startView()
        }
    }

    private fun setButtonOnClickListener() {
        var stage = 1
        mainBinding.btnCommand.setOnClickListener {
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
                    mainBinding.btnCommand.text = "Stop"
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
        mainBinding.tvGoal.text = goal.toString()
    }

    private fun start() {
        var sec = 0f
        timerTask = timer(period = 10) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tempScore = (sec / 100)
                if (isBlind) {
                    mainBinding.tvTimer.text = "???"
                } else {
                    mainBinding.tvTimer.text = tempScore.toString()
                }
            }
        }
    }

    private fun stop() {
        timerTask?.cancel()
        mainBinding.btnCommand.text = "Next"

        val score = abs(goal - tempScore)
        scores.add(score)

        val tvScore: TextView = findViewById(R.id.tv_score)
        tvScore.text = "score: ${String.format("%.2f", score)}"
    }

}