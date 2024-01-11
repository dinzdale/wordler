package gameboard

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.ui.game_pieces.PieceColor
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus

@Composable
fun Tile(
    modifier: Modifier,
    angle: Float,
    tileData: TileData = TileData('Q', TileKeyStatus.EMPTY)
) {
    var previousTile by remember { mutableStateOf(tileData) }

    if (tileData.status != TileKeyStatus.MATCH_OUT_POSITION && tileData.status != TileKeyStatus.MATCH_IN_POSITION) {
        previousTile = tileData
    }
    when (tileData.status) {
        TileKeyStatus.MATCH_OUT_POSITION, TileKeyStatus.MATCH_IN_POSITION -> {
            if (angle <= 90f) {
                RenderTile(modifier, angle = angle, tileData = previousTile)
            } else {
                RenderTile(modifier, angle = angle, tileData = tileData)
            }
        }

        TileKeyStatus.NO_MATCH -> {
            var scaleState by remember { mutableStateOf(1f) }
            LaunchedEffect(Unit) {
                animate(1f, .5f, 3f) { scale, _ ->
                    scaleState = scale
                }
                animate(.5f, 1f) { scale, _ ->
                    scaleState = scale
                }
            }
            RenderTile(modifier, scale = scaleState, angle = angle, tileData = previousTile)
        }

        else -> {
            RenderTile(modifier, angle = angle, tileData = previousTile)
        }
    }

}

@Composable
private fun RenderTile(modifier: Modifier, scale: Float = 1f, angle: Float, tileData: TileData) {
    val (background, foreground) = PieceColor.getColor(tileData)
    Box(
        modifier
            .graphicsLayer {
                rotationY = angle
                scaleX = scale
                scaleY = scale
            }
            .background(color = background)
            .sizeIn(minHeight = 30.dp).aspectRatio(1.33f, matchHeightConstraintsFirst = true),

        contentAlignment = Alignment.Center
    ) {
        Text(
            tileData.char.toString(),
            color = foreground,
            style = TextStyle(fontSize = 30.sp),
            modifier = Modifier.graphicsLayer {
                rotationY = angle
            }
        )
    }
}
