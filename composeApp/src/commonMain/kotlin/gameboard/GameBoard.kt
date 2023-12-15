package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData

@Composable
fun GameBoard(rows: List<RowData>) {
    Column(
        Modifier
            .wrapContentSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(5.dp))
        rows.forEach { rowData ->
            TileRow(rowData)
            Spacer(Modifier.height(5.dp))
        }
    }
}

//@Composable
//@Preview
//fun PreviewGameBoard() {
//    var rowDataList = remember {
//        mutableStateListOf(
//            RowData(
//                listOf(
//                    TileData('A', TileStatus.MATCH_IN_POSITION),
//                    TileData('B', TileStatus.MATCH_OUT_POSITION),
//                    TileData('C', TileStatus.MATCH_OUT_POSITION)
//                ), 0
//            ),
//            RowData(
//                listOf(
//                    TileData('D', TileStatus.NO_MATCH),
//                    TileData('E', TileStatus.MATCH_IN_POSITION),
//                    TileData('F', TileStatus.MATCH_OUT_POSITION)
//                ), 0
//            ),
//            RowData(
//                listOf(
//                    TileData('G', TileStatus.MATCH_IN_POSITION),
//                    TileData('A', TileStatus.MATCH_IN_POSITION),
//                    TileData('R', TileStatus.MATCH_OUT_POSITION),
//                    TileData('Y', TileStatus.NO_MATCH)
//                ), 0
//            ),
//            RowData(
//                listOf(
//                    TileData('J', TileStatus.MATCH_IN_POSITION),
//                    TileData('A', TileStatus.MATCH_IN_POSITION),
//                    TileData('C', TileStatus.MATCH_OUT_POSITION),
//                    TileData('O', TileStatus.NO_MATCH),
//                    TileData('B', TileStatus.MATCH_IN_POSITION)
//                ), 0
//            )
//
//
//        )
//    }
//    MaterialTheme {
//        Surface(Modifier.fillMaxSize()) {
//            GameBoard(rows = rowDataList)
//        }
//    }
//}
