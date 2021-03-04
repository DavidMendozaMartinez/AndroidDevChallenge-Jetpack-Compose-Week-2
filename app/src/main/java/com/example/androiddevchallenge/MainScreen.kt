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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    viewModel: MainViewModel
) {
    MainContent(
        hours = viewModel.hours,
        minutes = viewModel.minutes,
        seconds = viewModel.seconds,
        isPlayButtonVisible = viewModel.isPlayButtonVisible,
        isPauseButtonVisible = viewModel.isPauseButtonVisible,
        isStopButtonVisible = viewModel.isStopButtonVisible,
        onPlayButtonClicked = viewModel::onPlayButtonClicked,
        onPauseButtonClicked = viewModel::onPauseButtonClicked,
        onStopButtonClicked = viewModel::onStopButtonClicked
    )
}

@Composable
fun MainContent(
    hours: String,
    minutes: String,
    seconds: String,
    isPlayButtonVisible: Boolean,
    isPauseButtonVisible: Boolean,
    isStopButtonVisible: Boolean,
    onPlayButtonClicked: (Int, Int, Int) -> Unit,
    onPauseButtonClicked: () -> Unit,
    onStopButtonClicked: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        CountDown(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            modifier = Modifier.weight(1f)
        )
        CountDownController(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            isPlayButtonVisible = isPlayButtonVisible,
            isPauseButtonVisible = isPauseButtonVisible,
            isStopButtonVisible = isStopButtonVisible,
            onPlayButtonClicked = onPlayButtonClicked,
            onPauseButtonClicked = onPauseButtonClicked,
            onStopButtonClicked = onStopButtonClicked
        )
    }
}

@Composable
fun CountDown(
    hours: String,
    minutes: String,
    seconds: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Time(hours)
        Divider()
        Time(minutes)
        Divider()
        Time(seconds)
    }
}

@Composable
fun Time(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.h2,
        modifier = modifier
    )
}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Text(
        text = ":",
        style = MaterialTheme.typography.h2,
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    )
}

@Composable
fun CountDownController(
    hours: String,
    minutes: String,
    seconds: String,
    isPlayButtonVisible: Boolean,
    isPauseButtonVisible: Boolean,
    isStopButtonVisible: Boolean,
    onPlayButtonClicked: (Int, Int, Int) -> Unit,
    onPauseButtonClicked: () -> Unit,
    onStopButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val buttonModifier = Modifier.padding(
            start = 24.dp,
            end = 24.dp,
            bottom = 48.dp
        )

        if (isStopButtonVisible)
            StopButton(
                onClick = onStopButtonClicked,
                modifier = buttonModifier
            )

        if (isPauseButtonVisible)
            PauseButton(
                onClick = onPauseButtonClicked,
                modifier = buttonModifier
            )

        if (isPlayButtonVisible)
            PlayButton(
                hours = hours,
                minutes = minutes,
                seconds = seconds,
                onClick = onPlayButtonClicked,
                modifier = buttonModifier
            )
    }
}

@Composable
fun PlayButton(
    hours: String,
    minutes: String,
    seconds: String,
    onClick: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val second = if (seconds.toInt() == 0) 10 else seconds.toInt()
    CircularIconButton(
        onClick = { onClick(hours.toInt(), minutes.toInt(), second) },
        icon = Icons.Default.PlayArrow,
        contentDescription = stringResource(id = R.string.button_play_content_description),
        modifier = modifier
    )
}

@Composable
fun PauseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CircularIconButton(
        onClick = { onClick() },
        icon = Icons.Default.Pause,
        contentDescription = stringResource(id = R.string.button_pause_content_description),
        modifier = modifier
    )
}

@Composable
fun StopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    CircularIconButton(
        onClick = { onClick() },
        icon = Icons.Default.Stop,
        contentDescription = stringResource(id = R.string.button_stop_content_description),
        modifier = modifier
    )
}

@Composable
fun CircularIconButton(
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}
