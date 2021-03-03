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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
        onPlayButtonClicked = viewModel::onPlayButtonClicked
    )
}

@Composable
fun MainContent(
    hours: String,
    minutes: String,
    seconds: String,
    onPlayButtonClicked: (Int, Int, Int) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        CountDown(
            hours = hours,
            minutes = minutes,
            seconds = seconds,
            modifier = Modifier.weight(1f)
        )
        CountDownController(
            onPlayButtonClicked = onPlayButtonClicked
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
        modifier = modifier
    )
}

@Composable
fun Divider(modifier: Modifier = Modifier) {
    Text(
        text = ":",
        modifier = modifier.padding(start = 8.dp, end = 8.dp)
    )
}

@Composable
fun CountDownController(
    onPlayButtonClicked: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayButton(onClick = onPlayButtonClicked, Modifier.padding(48.dp))
    }
}

@Composable
fun PlayButton(
    onClick: (Int, Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    CircularIconButton(
        onClick = { onClick(0, 0, 10) },
        icon = Icons.Default.PlayArrow,
        contentDescription = stringResource(id = R.string.button_play_content_description),
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
