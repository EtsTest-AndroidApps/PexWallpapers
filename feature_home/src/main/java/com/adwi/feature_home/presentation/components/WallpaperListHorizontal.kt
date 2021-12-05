package com.adwi.feature_home.presentation.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.adwi.components.CategoryPanel
import com.adwi.components.PexAnimatedHeart
import com.adwi.components.PexCoilImage
import com.adwi.components.neumorphicShadow
import com.adwi.components.theme.PexWallpapersTheme
import com.adwi.components.theme.paddingValues
import com.adwi.core.DataState
import com.adwi.pexwallpapers.domain.model.Wallpaper

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun WallpaperListHorizontalPanel(
    modifier: Modifier = Modifier,
    verticalScrollState: ScrollState,
    horizontalScrollState: LazyListState,
    wallpapers: DataState<List<Wallpaper>>,
    listState: LazyListState = rememberLazyListState(),
    panelName: String = "",
    onWallpaperClick: (Int) -> Unit,
    onShowMoreClick: () -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        CategoryPanel(
            categoryName = panelName,
            onShowMoreClick = onShowMoreClick,
            showMore = true,
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        ShimmerRow(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error == null
        )
        ShimmerErrorMessage(
            visible = wallpapers.data.isNullOrEmpty() && wallpapers.error != null,
            message = wallpapers.error?.localizedMessage
                ?: "Unknown error occurred",
            modifier = Modifier.padding(horizontal = paddingValues)
        )
        wallpapers.data?.let { list ->
            WallpaperListHorizontal(
                listState = listState,
                verticalScrollState = verticalScrollState,
                horizontalScrollState = horizontalScrollState,
                onWallpaperClick = onWallpaperClick,
                onLongPress = onLongPress,
                wallpapers = list
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
private fun WallpaperListHorizontal(
    modifier: Modifier = Modifier,
    verticalScrollState: ScrollState,
    horizontalScrollState: LazyListState,
    listState: LazyListState = rememberLazyListState(),
    wallpapers: List<Wallpaper>,
    onWallpaperClick: (Int) -> Unit,
    onLongPress: (Wallpaper) -> Unit
) {
    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            start = paddingValues,
            end = paddingValues
        ),
        horizontalArrangement = Arrangement.spacedBy(paddingValues)
    ) {
        itemsIndexed(items = wallpapers, itemContent = { index, wallpaper ->
            val offset = horizontalScrollState.firstVisibleItemScrollOffset - (index * 400)
            WallpaperItem(
                wallpaper = wallpaper,
                verticalScrollState = verticalScrollState,
                onWallpaperClick = { onWallpaperClick(wallpaper.id) },
                onLongPress = { onLongPress(wallpaper) },
                isHeartEnabled = wallpaper.isFavorite,
                modifier = Modifier
            )
        })
    }
}

@ExperimentalMaterialApi
@ExperimentalCoilApi
@Composable
private fun WallpaperItem(
    modifier: Modifier = Modifier,
    verticalScrollState: ScrollState,
    shape: Shape = MaterialTheme.shapes.small,
    wallpaper: Wallpaper,
    onWallpaperClick: () -> Unit,
    onLongPress: () -> Unit,
    isHeartEnabled: Boolean,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    Column(
        modifier = modifier.neumorphicShadow(
            cornerRadius = 10.dp,
            offset = (-5).dp,
            isPressed = isPressed
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            onClick = { onWallpaperClick() },
            shape = shape,
            backgroundColor = MaterialTheme.colors.primary,
            modifier = modifier
                .size(100.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = { onLongPress() },
//                        onTap = { onWallpaperClick() },
                    )
                },
            interactionSource = interactionSource
        ) {
            Box {
                PexCoilImage(
                    imageUrl = wallpaper.imageUrlTiny,
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .graphicsLayer {
                            val scale = 1.6f
                            scaleY = scale
                            scaleX = scale
                            translationY = (-verticalScrollState.value) * 0.1f
                        },
                    wallpaperId = wallpaper.id,
                    contentScale = ContentScale.Crop
                )
                PexAnimatedHeart(
                    state = isHeartEnabled,
                    size = 64.dp,
                    speed = 1.5f,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
        Spacer(modifier = Modifier.size(paddingValues / 4))
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Light")
@Composable
private fun WallpaperListPanelPreviewLight() {
    PexWallpapersTheme(darkTheme = false) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            WallpaperItem(
                onLongPress = {},
                verticalScrollState = rememberScrollState(),
                onWallpaperClick = {},
                wallpaper = Wallpaper.defaultDaily,
                isHeartEnabled = true
            )
        }
    }
}

@ExperimentalCoilApi
@ExperimentalMaterialApi
@Preview(showBackground = true, name = "Dark")
@Composable
private fun WallpaperListPanelPreviewDark() {
    PexWallpapersTheme(darkTheme = true) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .padding(paddingValues)
        ) {
            WallpaperItem(
                onLongPress = {},
                verticalScrollState = rememberScrollState(),
                onWallpaperClick = {},
                wallpaper = Wallpaper.defaultDaily,
                isHeartEnabled = true
            )
        }
    }
}