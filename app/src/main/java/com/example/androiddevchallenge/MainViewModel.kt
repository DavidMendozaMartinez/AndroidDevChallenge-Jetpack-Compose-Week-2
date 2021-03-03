/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.CountDownTimer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    companion object {
        const val TIME_FORMAT: String = "%02d"
        val COUNT_DOWN_INTERVAL: Long = TimeUnit.SECONDS.toMillis(1)
        val TIME_PLACEHOLDER: String = String.format(TIME_FORMAT, 0)
    }

    private lateinit var countDownTimer: CountDownTimer

    private val _isPlaying: MutableLiveData<Boolean> = MutableLiveData()
    private val isPlaying: LiveData<Boolean> get() = _isPlaying

    private val _isFinished: MutableLiveData<Boolean> = MutableLiveData()
    private val isFinished: LiveData<Boolean> get() = _isFinished

    var hours: String by mutableStateOf(TIME_PLACEHOLDER)
        private set
    var minutes: String by mutableStateOf(TIME_PLACEHOLDER)
        private set
    var seconds: String by mutableStateOf(TIME_PLACEHOLDER)
        private set

    fun onPlayButtonClicked(hours: Int, minutes: Int, seconds: Int) {
        _isPlaying.value = true
        val millis = convertTimeToMillis(hours, minutes, seconds)
        startCountDown(millis)
    }

    private fun startCountDown(millis: Long) {
        countDownTimer = object : CountDownTimer(millis, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                hours = formatTime(millisUntilFinished, TimeUnit.MILLISECONDS::toHours)
                minutes = formatTime(millisUntilFinished, TimeUnit.MILLISECONDS::toMinutes)
                seconds = formatTime(millisUntilFinished, TimeUnit.MILLISECONDS::toSeconds)
            }

            override fun onFinish() {
                _isPlaying.value = false
                _isFinished.value = true
            }
        }.start()
    }

    private fun formatTime(millis: Long, convert: (Long) -> Long) =
        String.format(TIME_FORMAT, convert(millis))

    private fun convertTimeToMillis(hours: Int, minutes: Int, seconds: Int): Long =
        TimeUnit.HOURS.toMillis(hours.toLong())
            .plus(TimeUnit.MINUTES.toMillis(minutes.toLong()))
            .plus(TimeUnit.SECONDS.toMillis(seconds.toLong()))
}
