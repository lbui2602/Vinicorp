package com.example.freshervnc.luckychoice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.freshervnc.R
import kotlinx.coroutines.*

class LuckyChoiceViewModel : ViewModel() {
    val resultLiveData = MutableLiveData<Int>()
    val imageListLiveData = MutableLiveData<List<Int>>()
    val isPlayingLiveData = MutableLiveData<Boolean>()
    val choiceLiveData = MutableLiveData<Int>()
    val betLiveData = MutableLiveData<Int>()
    val moneyLiveData = MutableLiveData<Int>()
    val evenImagesLiveData = MutableLiveData<List<Int>>()
    val oddImagesLiveData = MutableLiveData<List<Int>>()
    val state = MutableLiveData<Boolean>()

    init {
        moneyLiveData.value = 100000
    }

    private var job: Job? = null
    private val imageList = listOf(
        R.drawable.ic_running_1,
        R.drawable.ic_running_2,
        R.drawable.ic_running_3,
        R.drawable.ic_running_4,
        R.drawable.ic_running_5,
        R.drawable.ic_running_6,
        R.drawable.ic_running_7,
        R.drawable.ic_running_8,
        R.drawable.ic_running_9,
        R.drawable.ic_running_10,
        R.drawable.ic_running_11,
        R.drawable.ic_running_12,
        R.drawable.ic_running_13,
        R.drawable.ic_running_14,
        R.drawable.ic_running_15,
        R.drawable.ic_running_16,
        R.drawable.ic_running_17,
        R.drawable.ic_running_19,
        R.drawable.ic_running_20,
        R.drawable.ic_running_21,
        R.drawable.ic_running_22,
        R.drawable.ic_running_23,
        R.drawable.ic_running_24,
        R.drawable.ic_running_25,
        R.drawable.ic_running_26,
        R.drawable.ic_running_27,
        R.drawable.ic_running_28,
        R.drawable.ic_running_29,
        R.drawable.ic_running_30
    )

    fun startGame() {
        evenImagesLiveData.value = emptyList()
        oddImagesLiveData.value = emptyList()
        if (job?.isActive == true) {
            job?.cancel()
        }
        job = CoroutineScope(Dispatchers.Main).launch {
            isPlayingLiveData.value = true
            imageListLiveData.value = imageList
            delay(3000)
            val chan = (1..4).random()
            val chanImages = List(chan) { R.drawable.ic_heads }
            val leImages = List(4 - chan) { R.drawable.ic_tails }

            evenImagesLiveData.value = chanImages
            oddImagesLiveData.value = leImages

            resultLiveData.value = if (chan % 2 == 0) 2 else 1
            isPlayingLiveData.value = false
        }

    }

    fun showResult() {
        val result = resultLiveData.value
        val choice = choiceLiveData.value
        val bet = betLiveData.value ?: 0
        val currentMoney = moneyLiveData.value ?: 0
        if (result == choice) {
            state.value = true
            moneyLiveData.value = currentMoney + bet
        } else {
            state.value = false
            moneyLiveData.value = currentMoney - bet
        }
    }
    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
