package com.adwi.pexwallpapers.ui.screens.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.ExperimentalPagingApi
import coil.annotation.ExperimentalCoilApi
import com.adwi.pexwallpapers.R
import com.adwi.pexwallpapers.model.Wallpaper
import com.adwi.pexwallpapers.ui.components.*
import com.adwi.pexwallpapers.ui.theme.paddingValues
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@ExperimentalPagingApi
@Composable
fun PreviewScreen(
    viewModel: PreviewViewModel,
    onGoToUrlClick: (String) -> Unit,
    onShareClick: (Wallpaper) -> Unit,
    onDownloadClick: (Wallpaper) -> Unit,
    onSetWallpaperClick: (url: String, home: Boolean, lock: Boolean) -> Unit,
    upPress: () -> Unit,
) {
    val wallpaper by viewModel.wallpaper.collectAsState()

    PexScaffold(
        viewModel = viewModel
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Header(
                modifier = Modifier.padding(
                    horizontal = paddingValues,
                    vertical = paddingValues / 2
                ),
                title = stringResource(id = R.string.preview),
                icon = Icons.Outlined.Image,
                actionIcon = null
            )
            wallpaper?.let {
                PreviewCard(
                    wallpaper = it,
                    modifier = Modifier
                        .padding(horizontal = paddingValues)
                        .padding(vertical = paddingValues / 2)
                        .weight(1f)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(paddingValues / 2),
                    text = stringResource(id = R.string.photo_by, it.photographer)
                )

                ImageActionButtons(
                    modifier = Modifier.fillMaxWidth(),
                    onGoToUrlClick = { onGoToUrlClick(it.url) },
                    onShareClick = { onShareClick(it) },
                    onDownloadClick = { onDownloadClick(it) },
                    onFavoriteClick = { viewModel.onFavoriteClick(it) },
                    isFavorite = it.isFavorite
                )
                PexButton(
                    onClick = {
                        onSetWallpaperClick(
                            it.imageUrlPortrait,
                            true,
                            false
                        )
                    },
                    text = stringResource(id = R.string.set_wallpaper),
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxWidth()
                )
            }
        }
    }
}