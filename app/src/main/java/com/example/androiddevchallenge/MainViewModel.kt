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
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {
    companion object {
        const val TIME_FORMAT: String = "%02d"
        val COUNT_DOWN_INTERVAL: Long = TimeUnit.SECONDS.toMillis(1)
        val TIME_PLACEHOLDER: String = String.format(TIME_FORMAT, 0)
    }

    private lateinit var countDownTimer: CountDownTimer

    var hours: String by mutableStateOf(TIME_PLACEHOLDER)
        private set
    var minutes: String by mutableStateOf(TIME_PLACEHOLDER)
        private set
    var seconds: String by mutableStateOf(TIME_PLACEHOLDER)
        private set

    var isEditStateEnabled: Boolean by mutableStateOf(true)
        private set

    var isPlayButtonVisible: Boolean by mutableStateOf(true)
        private set
    var isPauseButtonVisible: Boolean by mutableStateOf(false)
        private set
    var isStopButtonVisible: Boolean by mutableStateOf(false)
        private set

    private fun startCountDown(millis: Long) {
        countDownTimer = object : CountDownTimer(millis, COUNT_DOWN_INTERVAL) {
            override fun onTick(millisUntilFinished: Long) {
                hours = formatTime(getHours(millisUntilFinished))
                minutes = formatTime(getMinutes(millisUntilFinished))
                seconds = formatTime(getSeconds(millisUntilFinished))
            }

            override fun onFinish() {
                updateViewState(CountDownState.STOP)
            }
        }.start()
    }

    fun onPlayButtonClick() {
        updateViewState(CountDownState.PLAY)

        val millis = convertTimeToMillis(
            hours.toIntOrNull() ?: 0,
            minutes.toIntOrNull() ?: 0,
            seconds.toIntOrNull() ?: 0
        )
        startCountDown(millis)
    }

    fun onPauseButtonClick() {
        updateViewState(CountDownState.PAUSE)
        countDownTimer.cancel()
    }

    fun onStopButtonClick() {
        updateViewState(CountDownState.STOP)
        countDownTimer.cancel()

        hours = TIME_PLACEHOLDER
        minutes = TIME_PLACEHOLDER
        seconds = TIME_PLACEHOLDER
    }

    fun onHoursValueChange(value: String) {
        hours = getValidInput(value, 99)
    }

    fun onMinutesValueChange(value: String) {
        minutes = getValidInput(value, 59)
    }

    fun onSecondsValueChange(value: String) {
        seconds = getValidInput(value, 59)
    }

    fun onHoursFocused() {
        hours = ""
    }

    fun onMinutesFocused() {
        minutes = ""
    }

    fun onSecondsFocused() {
        seconds = ""
    }

    fun onEditDone() {
        hours = if (hours.isNotEmpty()) formatTime(hours.toLong()) else TIME_PLACEHOLDER
        minutes = if (minutes.isNotEmpty()) formatTime(minutes.toLong()) else TIME_PLACEHOLDER
        seconds = if (seconds.isNotEmpty()) formatTime(seconds.toLong()) else TIME_PLACEHOLDER
    }

    private fun getValidInput(value: String, max: Int): String {
        return value
            .replace("\\D".toRegex(), "")
            .takeLast(2)
            .toIntOrNull()?.run {
                coerceIn(0..max).toString()
            } ?: ""
    }

    private fun updateViewState(state: CountDownState) {
        when (state) {
            CountDownState.PLAY -> {
                isPlayButtonVisible = false
                isPauseButtonVisible = true
                isStopButtonVisible = true
                isEditStateEnabled = false
            }
            CountDownState.PAUSE -> {
                isPlayButtonVisible = true
                isPauseButtonVisible = false
                isStopButtonVisible = true
                isEditStateEnabled = true
            }
            CountDownState.STOP -> {
                isPlayButtonVisible = true
                isPauseButtonVisible = false
                isStopButtonVisible = false
                isEditStateEnabled = true
            }
        }
    }

    private fun getHours(millis: Long): Long =
        TimeUnit.MILLISECONDS.toHours(millis)

    private fun getMinutes(millis: Long): Long =
        TimeUnit.MILLISECONDS.toMinutes(millis)
            .minus(TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)))

    private fun getSeconds(millis: Long) =
        TimeUnit.MILLISECONDS.toSeconds(millis)
            .minus(TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))

    private fun formatTime(time: Long) = String.format(TIME_FORMAT, time)

    private fun convertTimeToMillis(hours: Int, minutes: Int, seconds: Int): Long =
        TimeUnit.HOURS.toMillis(hours.toLong())
            .plus(TimeUnit.MINUTES.toMillis(minutes.toLong()))
            .plus(TimeUnit.SECONDS.toMillis(seconds.toLong()))
}
