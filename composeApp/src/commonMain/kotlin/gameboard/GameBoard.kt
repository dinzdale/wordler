package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus


private val stateMap = mapOf(
            0 to mutableStateListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            1 to mutableStateListOf<TileData>().apply {
               IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            2 to mutableStateListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            3 to mutableStateListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            4 to mutableStateListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            }
        )



@Composable
fun GameBoard(modifier: Modifier, rows: List<RowData>, onRowUpdateFinish: (Boolean) -> Unit) {

    Column(
        modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(5.dp))
        rows.forEach { rowData ->
            TileRow(rowData, onRowUpdateFinish)
            Spacer(Modifier.height(5.dp))
        }
    }
}

@Composable
fun CheckGameBoardHasMatch(guess: Char, onResult: (Boolean) -> Unit) {
    val boolList = mutableListOf<Boolean>()
        stateMap.flatMap {
        it.value.forEach { tileData ->
            boolList.add(tileData.char == guess && tileData.status == TileKeyStatus.MATCH_IN_POSITION)
        }
        boolList
    }.contains(true).also {
        onResult(it)
    }
}


fun initGameBoardStates() {
    stateMap.keys.forEach { nxtKey ->
        val lastIndex = stateMap[nxtKey]?.lastIndex ?: 0
        IntRange(0, lastIndex).forEach { nxtIndex ->
            stateMap[nxtKey]?.set(nxtIndex, TileData('X', TileKeyStatus.EMPTY))
        }
    }
}

fun setTileData(row:Int, column:Int, tileData: TileData) {
    stateMap[row]?.set(column,
        tileData
    )
}

fun getRowData() = stateMap.map { entry->
    RowData(rowPosition = entry.key, tileData = entry.value)
}
@Composable
fun isKeyBoardStateInitialized() = produceState(false) {
    value = stateMap.isNotEmpty()
}