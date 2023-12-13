package gameboard

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus

@Composable
fun TileRow(
    rowData: RowData
) {
    Row(
        Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val tileData = rowData.tileData
        Spacer(Modifier.width(5.dp))
        for (i in 0..tileData.lastIndex) {
            Tile(tileData[i])
            if (i <= tileData.lastIndex) {
                Spacer(Modifier.width(5.dp))
            }
        }
        for (i in rowData.tileData.size..4) {
            Tile(TileData('X', TileKeyStatus.EMPTY, i))
            Spacer(Modifier.width(5.dp))
        }
    }
}

//@Composable
//@Preview
//fun PreviewRow() {
//    MaterialTheme {
//        Surface(Modifier.wrapContentSize()) {
//            TileRow()
//        }
//    }
//}
