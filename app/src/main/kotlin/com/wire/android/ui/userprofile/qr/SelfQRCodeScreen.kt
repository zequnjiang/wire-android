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
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import com.wire.android.ui.common.scaffold.WireScaffold
import com.wire.android.ui.common.spacers.VerticalSpace
import com.wire.android.ui.common.topappbar.WireCenterAlignedTopAppBar
import com.wire.android.ui.theme.WireTheme
import com.wire.android.util.ui.PreviewMultipleThemes
import com.wire.kalium.logic.data.user.UserId

@RootNavGraph
@Destination(
    style = PopUpNavigationAnimation::class,
    navArgsDelegate = SelfQrCodeNavArgs::class
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
    SelfQRCodeContent(viewModel.selfQRCodeState, navigator::navigateBack)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelfQRCodeContent(
    state: SelfQRCodeState,
    onBackClick: () -> Unit = {}
) {
    WireScaffold(
        topBar = {
            WireCenterAlignedTopAppBar(
                title = stringResource(id = R.string.user_profile_qr_code_title),
                onNavigationPressed = onBackClick
            )
        }
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorsScheme().background)
                .padding(internalPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = dimensions().spacing16x, vertical = dimensions().spacing48x)
                    .clip(RoundedCornerShape(dimensions().spacing8x))
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                VerticalSpace.x16()
                QrCodeView(
                    data = state.userProfileLink,
                    modifier = Modifier.size(dimensions().spacing300x),
                    dotShape = DotShape.Square
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                width = dimensions().spacing2x,
                                shape = CircleShape,
                                color = Color.White
                            )
                            .background(colorsScheme().primary)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            modifier = Modifier.background(color = colorsScheme().primary),
                            tint = Color.White,
                            contentDescription = null
                        )
                    }

                }
            }
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
