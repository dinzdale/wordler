package gameboard

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileStatus

@Composable
fun TileRow(
    tiles: List<TileData> = listOf(
        TileData('A', TileStatus.MATCH_OUT_POSITION, 0),
        TileData('B', TileStatus.MATCH_IN_POSITION, 1),
        TileData('C', TileStatus.NO_MATCH, 2),
        TileData('D', TileStatus.EMPTY, 2),
    )
) {
    var myTiles = remember {
        mutableListOf(
            tiles[0],
            tiles[1],
            tiles[2],
            tiles[3]
        )
    }
    Row(Modifier.wrapContentSize().padding(5.dp)) {
        Tile(myTiles[0])
        Tile(myTiles[1])
        Tile(myTiles[2])
        Tile(myTiles[3])
    }
}

@Composable
@Preview
fun PreviewRow() {
    MaterialTheme {
        Surface(Modifier.wrapContentSize()) {
            TileRow()
        }
    }
}
