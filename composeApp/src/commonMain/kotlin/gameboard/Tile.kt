package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
fun Tile(angle: Float, tileData: TileData = TileData('Q', TileKeyStatus.EMPTY)) {
    var previousTile by remember { mutableStateOf(tileData) }
    if (tileData.status != TileKeyStatus.MATCH_OUT_POSITION && tileData.status != TileKeyStatus.MATCH_IN_POSITION) {
        previousTile = tileData
    }
    if (angle <= 90f) {
        RenderTile(angle,previousTile)
    }
    else {
        RenderTile(angle,tileData)
    }
//    Box(
//        Modifier
//            .graphicsLayer {
//                rotationY = angle
//            }
//            .background(color = PieceColor.getColor(tileData).backGround)
//            .size(size = 70.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            tileData.char.toString(),
//            color = PieceColor.getColor(tileData).foreGround,
//            style = TextStyle(fontSize = 38.sp)
//        )
//    }
}

@Composable
private fun RenderTile(angle: Float, tileData: TileData) {
    val (background,foreground) = PieceColor.getColor(tileData)
    Box(
        Modifier
            .graphicsLayer {
                rotationY = angle
            }
            .background(color = background)
            .size(size = 70.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            tileData.char.toString(),
            color = foreground,
            style = TextStyle(fontSize = 38.sp)
        )
    }
}
//@Composable
//@Preview
//fun PreviewTileInitial() {
//    MaterialTheme {
//        Surface(Modifier.wrapContentSize()) {
//            Tile()
//        }
//    }
//}
//
//@Composable
//@Preview
//fun PreviewTileMatchInPosition() {
//    MaterialTheme {
//        Surface(Modifier.wrapContentSize()) {
//            Tile(TileData('Q',TileStatus.MATCH_IN_POSITION))
//        }
//    }
//}
//
//@Composable
//@Preview
//fun PreviewTileMatchOutPosition() {
//    MaterialTheme {
//        Surface(Modifier.wrapContentSize()) {
//            Tile(TileData('Q',TileStatus.MATCH_OUT_POSITION))
//        }
//    }
//}
