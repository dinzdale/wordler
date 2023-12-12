package gameboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileStatus

@Composable
fun TileRow(
    rowData: RowData
) {
    Row(
        Modifier.height(60.dp).wrapContentSize().padding(vertical = 5.dp, horizontal = 5.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val tileData = rowData.tileData
        for (i in 0..tileData.lastIndex) {
            Tile(tileData[i])
            Spacer(Modifier.width(5.dp))
        }
        for (i in rowData.tileData.size..4) {
            Tile(TileData('X', TileStatus.EMPTY, i))
            if (i < 4) {
                Spacer(Modifier.width(5.dp))
            }
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
