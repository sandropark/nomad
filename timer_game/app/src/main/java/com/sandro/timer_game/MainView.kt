package com.sandro.timer_game

import android.widget.Button
import android.widget.TextView

class MainView(tvGoal: TextView, tvTimer: TextView, tvScore: TextView, btn: Button) {
    private var goal: Float = 0f

    init {
        initGoal(tvGoal)
    }

    private fun initGoal(tvGoal: TextView) {
        goal = (1..1001).random().toFloat() / 100
        tvGoal.text = goal.toString()
    }
}