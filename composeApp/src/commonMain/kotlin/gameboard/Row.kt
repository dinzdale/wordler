package gameboard

import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus

@Composable
fun TileRow(
    rowData: RowData,
    onRowUpdateFinish: () -> Unit
) {
    var angleStates = remember { mutableStateListOf(0f, 0f, 0f, 0f, 0f) }

    @Composable
    fun AnimateTiles(doList: List<Boolean> = animateState(rowData).value) {
        LaunchedEffect(doList) {
            for (i in 0..angleStates.lastIndex) {
                angleStates[i] = 0f
            }
            doList.forEachIndexed { index, value ->
                if (value) {
                    animate(0f, 180f, animationSpec = tween(500)) { nxtVal, _ ->
                        angleStates[index] = nxtVal
                    }
                }
            }
            onRowUpdateFinish()
        }
    }
    AnimateTiles()
    Row(Modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        val tileData = rowData.tileData
        Spacer(Modifier.width(5.dp))
        for (i in 0..tileData.lastIndex) {
            Tile(Modifier.weight(1f),
                angleStates[i],
                tileData[i]
            )
            if (i <= rowData.tileData.lastIndex) {
                Spacer(Modifier.width(5.dp))
            }
        }
        for (i in tileData.size..4) {
            Tile(Modifier.weight(1f), angleStates[i], TileData('X', TileKeyStatus.EMPTY))
            Spacer(Modifier.width(5.dp))
        }
    }

}

@Composable
private fun animateState(rowData: RowData) = produceState(
    listOf(false, false, false, false, false),
    rowData.tileData[0].status,
    rowData.tileData[1].status,
    rowData.tileData[2].status,
    rowData.tileData[3].status,
    rowData.tileData[4].status,
) {
    val listOfMatches = mutableListOf(false, false, false, false, false)
    rowData.tileData.forEachIndexed { index, tileData ->
        listOfMatches[index] =
            rowData.tileData[index].status == TileKeyStatus.MATCH_IN_POSITION || rowData.tileData[index].status == TileKeyStatus.MATCH_OUT_POSITION
    }
    value = listOfMatches.toList()
}

