package com.example.prac_timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val timer = MutableLiveData<String>().apply { value = "0.00" }
    val mainButton = MutableLiveData<String>().apply { value = "Start" }
    val goal = MutableLiveData<Float>()
    val people = MutableLiveData<String>()
    val score = MutableLiveData<String>().apply { value = "" }
}