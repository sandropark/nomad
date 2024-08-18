package com.example.prac_timer

import android.os.Bundle
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

    private var mainButtonText = "Start"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startView()
    }

    private fun updateMainButtonTextUi(text: String) {
        mainButtonText = text
        updateMainButtonTextUi()
    }

    private fun updateMainButtonTextUi() {
        mainBinding.buttonText = mainButtonText
    }

    private fun startView() {
        val startBinding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(startBinding.root)
        startView = StartView(startBinding)
        startView.setOnclickListenerOnBtnStart {
            people = 1
            main()
        }
    }

    private fun main() {
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        updateMainButtonTextUi()
        mainBinding.people = "참가자 $people"
        updateTimerUi("0.00")
        initGoal()
        setButtonOnClickListener()
    }

    private fun endView() {
        val endBinding = ActivityEndBinding.inflate(layoutInflater)
        setContentView(endBinding.root)
        val maxScore = scores.max()
        endBinding.biggestScore = maxScore.toString()
        endBinding.endPeople = "참가자 ${scores.indexOf(maxScore) + 1}"
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
                    updateMainButtonTextUi("Stop")
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
        mainBinding.goal = goal.toString()
    }

    private fun start() {
        var sec = 0f
        timerTask = timer(period = 10) {
            runOnUiThread { // UI 변경은 runOnUiThread로 처리
                sec++
                tempScore = (sec / 100)
                if (isBlind) {
                    updateTimerUi("???")
                } else {
                    updateTimerUi(tempScore.toString())
                }
            }
        }
    }

    private fun updateTimerUi(text: String) {
        mainBinding.timer = text
    }

    private fun stop() {
        timerTask?.cancel()
        updateMainButtonTextUi("Next")

        val score = abs(goal - tempScore)
        scores.add(score)

        mainBinding.score = "score: ${String.format("%.2f", score)}"
    }

}