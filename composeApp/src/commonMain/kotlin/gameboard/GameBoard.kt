package gameboard

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
import androidx.compose.ui.graphics.StrokeCap
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
                
                // Wood screws in the corners
                val screwColor = Color(0xFFB0BEC5)
                val screwShadow = Color(0xFF37474F)
                val screwRadius = 14f
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
                        color = screwShadow,
                        radius = screwRadius + 1f,
                        center = center + Offset(1f, 1f)
                    )
                    // The screw head
                    drawCircle(
                        color = screwColor,
                        radius = screwRadius,
                        center = center
                    )
                    
                    // The screw slot (Phillips head look)
                    val slotColor = Color(0xFF455A64)
                    val slotSize = screwRadius * 0.7f
                    
                    drawLine(
                        color = slotColor,
                        start = center - Offset(slotSize, 0f),
                        end = center + Offset(slotSize, 0f),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )
                    drawLine(
                        color = slotColor,
                        start = center - Offset(0f, slotSize),
                        end = center + Offset(0f, slotSize),
                        strokeWidth = 3f,
                        cap = StrokeCap.Round
                    )

                    // Reflection
                    drawCircle(
                        color = Color.White.copy(alpha = 0.4f),
                        radius = 3f,
                        center = center - Offset(screwRadius/2, screwRadius/2)
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
