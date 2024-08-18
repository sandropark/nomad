package com.example.prac_timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EndViewModel : ViewModel() {
    val liveDataBiggestScore = MutableLiveData<Float>()
    val liveDataEndPeople = MutableLiveData<String>()
}