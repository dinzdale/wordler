package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus


private val stateMap = mapOf(
            0 to mutableListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            1 to mutableListOf<TileData>().apply {
               IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            2 to mutableListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            3 to mutableListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            },
            4 to mutableListOf<TileData>().apply {
                IntRange(0, 4).forEach {
                    add(TileData('X', TileKeyStatus.EMPTY))
                }
            }
        )



@Composable
fun GameBoard(modifier: Modifier, rows: List<RowData>, onRowUpdateFinish: (Boolean) -> Unit) {
    val metalGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFCFD8DC),
            Color(0xFF90A4AE),
            Color(0xFF78909C),
            Color(0xFF90A4AE),
            Color(0xFFCFD8DC)
        ),
        start = Offset(0f, 0f),
        end = Offset.Infinite
    )

    Column(
        modifier
            .fillMaxSize()
            .drawBehind {
                // Background metal sheet
                drawRect(brush = metalGradient)
                
                // Rivets in the corners
                val rivetColor = Color(0xFF546E7A)
                val rivetShadow = Color(0xFF37474F)
                val rivetRadius = 12f
                val padding = 30f
                
                val corners = listOf(
                    Offset(padding, padding),
                    Offset(size.width - padding, padding),
                    Offset(padding, size.height - padding),
                    Offset(size.width - padding, size.height - padding)
                )
                
                corners.forEach { center ->
                    // Shadow for 3D effect
                    drawCircle(
                        color = rivetShadow,
                        radius = rivetRadius + 2f,
                        center = center + Offset(2f, 2f)
                    )
                    // The rivet
                    drawCircle(
                        color = rivetColor,
                        radius = rivetRadius,
                        center = center
                    )
                    // Reflection
                    drawCircle(
                        color = Color.White.copy(alpha = 0.3f),
                        radius = rivetRadius / 2,
                        center = center - Offset(3f, 3f)
                    )
                }
            }
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(5.dp))
        rows.forEach { rowData ->
            TileRow(rowData, onRowUpdateFinish)
            Spacer(Modifier.height(5.dp))
        }
    }
}

fun checkGameBoardHasMatch(guess: Char, onResult: (Boolean) -> Unit) {
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
