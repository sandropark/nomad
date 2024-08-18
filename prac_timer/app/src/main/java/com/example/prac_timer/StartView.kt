package com.example.prac_timer

import com.example.prac_timer.databinding.ActivityStartBinding

class StartView(
    private val binding: ActivityStartBinding,
) {
    private var totalPeople: Int = 2

    init {
        updateTotalPeopleUi()
        setOnclickListenerOnBtnMinus()
        setOnclickListenerOnBtnPlus()
    }

    private fun setOnclickListenerOnBtnMinus() {
        binding.btnMinus.setOnClickListener {
            if (totalPeople > 1) {
                totalPeople--
                updateTotalPeopleUi()
            }
        }
    }

    private fun setOnclickListenerOnBtnPlus() {
        binding.btnPlus.setOnClickListener {
            if (totalPeople < 99) {
                totalPeople++
                updateTotalPeopleUi()
            }
        }
    }

    fun setOnclickListenerOnBtnStart(function: () -> Unit) {
        binding.btnStart.setOnClickListener {
            function()
        }
    }

    private fun updateTotalPeopleUi() {
        binding.totalPeople = totalPeople.toString()
    }

    fun isTotalPeopleBiggerThan(people: Int): Boolean {
        return totalPeople > people
    }

}