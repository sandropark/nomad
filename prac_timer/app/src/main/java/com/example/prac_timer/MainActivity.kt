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
    private lateinit var startBinding: ActivityStartBinding
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var endBinding: ActivityEndBinding

    private var timerTask: Timer? = null
    private val scores: MutableList<Float> = mutableListOf()
    private var people = 1
    private var isBlind = true
    private var tempScore: Float = 0.0f
    private var goal: Float = 0.0f
    private var stage = 1

    // startView
    private var totalPeople: Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startBinding = ActivityStartBinding.inflate(layoutInflater)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        endBinding = ActivityEndBinding.inflate(layoutInflater)

        // startView
        setOnclickListenerOnBtnMinus()
        setOnclickListenerOnBtnPlus()
        setStartBtnOnclickListener()

        // mainView
        setButtonOnClickListener()

        startView()
    }

    private fun startView() {
        setContentView(startBinding.root)
        updateTotalPeopleUi()
    }

    // startView
    private fun setStartBtnOnclickListener() {
        startBinding.btnStart.setOnClickListener {
            people = 1
            mainView()
        }
    }

    private fun setOnclickListenerOnBtnMinus() {
        startBinding.btnMinus.setOnClickListener {
            minus()
        }
    }

    private fun minus() {
        if (totalPeople > 1) {
            totalPeople--
            updateTotalPeopleUi()
        }
    }

    private fun setOnclickListenerOnBtnPlus() {
        startBinding.btnPlus.setOnClickListener {
            plus()
        }
    }

    private fun plus() {
        if (totalPeople < 99) {
            totalPeople++
            updateTotalPeopleUi()
        }
    }

    private fun updateTotalPeopleUi() {
        startBinding.totalPeople = totalPeople.toString()
    }

    // mainView
    private fun mainView() {
        setContentView(mainBinding.root)
        initMainView()
    }

    private fun initMainView() {
        updateMainButtonTextUi("Start")
        mainBinding.score = ""
        mainBinding.people = "참가자 $people"
        updateTimerUi("0.00")
        initGoal()
        stage = 1
    }

    private fun updateMainButtonTextUi(text: String) {
        mainBinding.buttonText = text
    }

    private fun setButtonOnClickListener() {
        mainBinding.btnCommand.setOnClickListener {
            when (stage % 3) {
                0 -> {
                    if (people < totalPeople) {
                        people++
                        mainView()
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

    // endView
    private fun endView() {
        setContentView(endBinding.root)
        val maxScore = scores.max()
        endBinding.biggestScore = maxScore.toString()
        endBinding.endPeople = "참가자 ${scores.indexOf(maxScore) + 1}"
        endBinding.btnRestart.setOnClickListener {
            scores.clear()
            startView()
        }
    }
}