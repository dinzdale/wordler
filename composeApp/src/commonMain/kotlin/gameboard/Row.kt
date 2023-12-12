package gameboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileStatus

@Composable
fun TileRow(
    tiles: List<TileData> = listOf(
        TileData('A', TileStatus.MATCH_OUT_POSITION, 0),
        TileData('B', TileStatus.MATCH_IN_POSITION, 1),
        TileData('C', TileStatus.NO_MATCH, 2),
        TileData('E', TileStatus.NO_MATCH, 3),
        TileData('D', TileStatus.EMPTY, 4),
    )
) {
    val myTiles = remember {
        mutableStateListOf(
            tiles[0],
            tiles[1],
            tiles[2],
            tiles[3],
            tiles[4]
        )
    }
    myTiles.clear()
    myTiles.addAll(tiles)
    Row(Modifier.height(60.dp).wrapContentSize().padding(vertical = 5.dp, horizontal = 5.dp), horizontalArrangement = Arrangement.SpaceAround) {
        Tile(myTiles[0])
        Spacer(Modifier.width(5.dp))
        Tile(myTiles[1])
        Spacer(Modifier.width(5.dp))
        Tile(myTiles[2])
        Spacer(Modifier.width(5.dp))
        Tile(myTiles[3])
        Spacer(Modifier.width(5.dp))
        Tile(myTiles[4])
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
