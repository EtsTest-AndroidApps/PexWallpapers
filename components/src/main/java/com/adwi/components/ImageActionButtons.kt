package com.adwi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.adwi.components.theme.paddingValues

@Composable
fun ImageActionButtons(
    modifier: Modifier = Modifier,
    onUrlClick: () -> Unit,
    onSaveClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
//    val favoriteTransition = updateTransition(targetState = isFavorite, label = "Card")
//
//    val tintColor by favoriteTransition.animateColor(label = "Favorite icon color") { state ->
//        if (state) Color.Red else MaterialTheme.colors.primary
//    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(
            icon = Icons.Outlined.Link,
            modifier = Modifier.clickable { onUrlClick() }
        )
        ActionButton(
            icon = Icons.Outlined.Save,
            modifier = Modifier.clickable { onSaveClick() }
        )
        if (isFavorite) {
            ActionButton(
                icon = Icons.Outlined.Favorite,
                modifier = Modifier.clickable { onFavoriteClick() },
//            tint = tintColor
            )
        } else {
            ActionButton(
                icon = Icons.Outlined.Bookmark,
                modifier = Modifier.clickable { onFavoriteClick() },
//            tint = tintColor
            )
        }

    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Save,
    tint: Color = MaterialTheme.colors.onBackground,
    contentDescription: String = ""
) {
    IconButton(
        onClick = {}
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = modifier
                .padding(horizontal = paddingValues / 2)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImageActionButtonsPreview() {
    MaterialTheme {
        ImageActionButtons(
            onUrlClick = {},
            onSaveClick = {},
            onFavoriteClick = {},
            isFavorite = true
        )
    }
}