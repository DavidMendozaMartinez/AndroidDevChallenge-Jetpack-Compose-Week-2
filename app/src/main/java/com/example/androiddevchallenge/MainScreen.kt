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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(viewModel: MainViewModel) {
    MainContent(
        hours = viewModel.hours,
        minutes = viewModel.minutes,
        seconds = viewModel.seconds,
        isEditStateEnabled = viewModel.isEditStateEnabled,
        isPlayButtonVisible = viewModel.isPlayButtonVisible,
        isPauseButtonVisible = viewModel.isPauseButtonVisible,
        isStopButtonVisible = viewModel.isStopButtonVisible,
        onHoursValueChange = viewModel::onHoursValueChange,
        onMinutesValueChange = viewModel::onMinutesValueChange,
        onSecondsValueChange = viewModel::onSecondsValueChange,
        onHoursFocused = viewModel::onHoursFocused,
        onMinutesFocused = viewModel::onMinutesFocused,
        onSecondsFocused = viewModel::onSecondsFocused,
        onEditDone = viewModel::onEditDone,
        onPlayButtonClick = viewModel::onPlayButtonClick,
        onPauseButtonClick = viewModel::onPauseButtonClick,
        onStopButtonClick = viewModel::onStopButtonClick
    )
}

@Composable
fun MainContent(
    hours: String,
    minutes: String,
    seconds: String,
    isEditStateEnabled: Boolean,
    isPlayButtonVisible: Boolean,
    isPauseButtonVisible: Boolean,
    isStopButtonVisible: Boolean,
    onHoursValueChange: (String) -> Unit,
    onMinutesValueChange: (String) -> Unit,
    onSecondsValueChange: (String) -> Unit,
    onHoursFocused: () -> Unit,
    onMinutesFocused: () -> Unit,
    onSecondsFocused: () -> Unit,
    onEditDone: () -> Unit,
    onPlayButtonClick: () -> Unit,
    onPauseButtonClick: () -> Unit,
    onStopButtonClick: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        CountDown(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            isEditStateEnabled = isEditStateEnabled,
            onHoursValueChange = onHoursValueChange,
            onMinutesValueChange = onMinutesValueChange,
            onSecondsValueChange = onSecondsValueChange,
            onHoursFocused = onHoursFocused,
            onMinutesFocused = onMinutesFocused,
            onSecondsFocused = onSecondsFocused,
            onEditDone = onEditDone,
            modifier = Modifier.weight(1f)
        )
        CountDownController(
            isPlayButtonVisible = isPlayButtonVisible,
            isPauseButtonVisible = isPauseButtonVisible,
            isStopButtonVisible = isStopButtonVisible,
            onPlayButtonClick = onPlayButtonClick,
            onPauseButtonClick = onPauseButtonClick,
            onStopButtonClick = onStopButtonClick
        )
    }
}

@Composable
fun CountDown(
    hours: String,
    minutes: String,
    seconds: String,
    isEditStateEnabled: Boolean,
    onHoursValueChange: (String) -> Unit,
    onMinutesValueChange: (String) -> Unit,
    onSecondsValueChange: (String) -> Unit,
    onHoursFocused: () -> Unit,
    onMinutesFocused: () -> Unit,
    onSecondsFocused: () -> Unit,
    onEditDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val textStyle = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onBackground)
        val timeSize = Modifier.width(72.dp)

        TimeField(
            value = hours,
            textStyle = textStyle,
            onValueChange = onHoursValueChange,
            onFocusActive = onHoursFocused,
            onDone = onEditDone,
            enabled = isEditStateEnabled,
            label = stringResource(id = R.string.label_hours),
            modifier = timeSize
        )
        Divider(style = textStyle)
        TimeField(
            value = minutes,
            textStyle = textStyle,
            onValueChange = onMinutesValueChange,
            onFocusActive = onMinutesFocused,
            onDone = onEditDone,
            enabled = isEditStateEnabled,
            label = stringResource(id = R.string.label_minutes),
            modifier = timeSize
        )
        Divider(style = textStyle)
        TimeField(
            value = seconds,
            textStyle = textStyle,
            onFocusActive = onSecondsFocused,
            onValueChange = onSecondsValueChange,
            onDone = onEditDone,
            enabled = isEditStateEnabled,
            label = stringResource(id = R.string.label_seconds),
            modifier = timeSize
        )
    }
}

@Composable
fun TimeField(
    value: String,
    textStyle: TextStyle,
    onValueChange: (String) -> Unit,
    onFocusActive: () -> Unit,
    onDone: () -> Unit,
    enabled: Boolean,
    label: String,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        value = value,
        textStyle = textStyle,
        onValueChange = onValueChange,
        enabled = enabled,
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
                focusManager.clearFocus()
            }
        ),
        modifier = modifier.onFocusChanged {
            when (it) {
                FocusState.Active -> onFocusActive()
                FocusState.Inactive -> onDone()
                else -> {
                }
            }
        }
    )
}

@Composable
fun Divider(
    style: TextStyle,
    modifier: Modifier = Modifier
) {
    Text(
        text = ":",
        style = style,
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    )
}

@Composable
fun CountDownController(
    isPlayButtonVisible: Boolean,
    isPauseButtonVisible: Boolean,
    isStopButtonVisible: Boolean,
    onPlayButtonClick: () -> Unit,
    onPauseButtonClick: () -> Unit,
    onStopButtonClick: () -> Unit,
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
                onClick = onStopButtonClick,
                modifier = buttonModifier
            )

        if (isPauseButtonVisible)
            PauseButton(
                onClick = onPauseButtonClick,
                modifier = buttonModifier
            )

        if (isPlayButtonVisible)
            PlayButton(
                onClick = onPlayButtonClick,
                modifier = buttonModifier
            )
    }
}

@Composable
fun PlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = stringResource(id = R.string.button_play_content_description),
        )
    }
}

@Composable
fun PauseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Pause,
            contentDescription = stringResource(id = R.string.button_pause_content_description),
        )
    }
}

@Composable
fun StopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Stop,
            contentDescription = stringResource(id = R.string.button_stop_content_description)
        )
    }
}
