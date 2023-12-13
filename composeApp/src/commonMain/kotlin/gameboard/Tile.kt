package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.ui.game_pieces.PieceColor
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileStatus

@Composable
fun Tile(tileData: TileData = TileData('Q',TileStatus.EMPTY)) {
//    var tiledata by remember { mutableStateOf(tileData) }
    Box(
        Modifier
            .background(color = PieceColor.getColor(tileData).backGround)
            .size(size = 70.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(tileData.char.toString(), color = PieceColor.getColor(tileData).foreGround, style = TextStyle(fontSize = 38.sp))
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
