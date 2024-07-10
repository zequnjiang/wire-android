/*
 * Wire
 * Copyright (C) 2024 Wire Swiss GmbH
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see http://www.gnu.org/licenses/.
 */
package com.wire.android.ui.userprofile.qr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.lightspark.composeqr.DotShape
import com.lightspark.composeqr.QrCodeView
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.wire.android.R
import com.wire.android.navigation.Navigator
import com.wire.android.navigation.style.PopUpNavigationAnimation
import com.wire.android.ui.common.colorsScheme
import com.wire.android.ui.common.dimensions
import com.wire.android.ui.theme.WireTheme
import com.wire.android.util.ui.PreviewMultipleThemes
import com.wire.kalium.logic.data.user.UserId

@RootNavGraph
@Destination(
    style = PopUpNavigationAnimation::class,
)
@Composable
fun SelfQRCodeScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
    viewModel: SelfQRCodeViewModel = hiltViewModel()
) {
    if (viewModel.selfQRCodeState.hasError) {
        navigator.navigateBack()
    }
    SelfQRCodeContent(viewModel.selfQRCodeState)
}

@Composable
private fun SelfQRCodeContent(state: SelfQRCodeState) {
    QrCodeView(
        data = state.userProfileLink,
        modifier = Modifier.size(dimensions().spacing200x),
        dotShape = DotShape.Square
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(colorsScheme().primary)
                .padding(dimensions().spacing8x)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_wire_logo),
                modifier = Modifier.background(color = colorsScheme().primary),
                contentDescription = null
            )
        }

    }
}

@PreviewMultipleThemes
@Composable
fun PreviewSelfQRCodeContent() {
    WireTheme {
        SelfQRCodeContent(
            SelfQRCodeState(
                userId = UserId("userId", "wire.com")
            )
        )
    }
}
