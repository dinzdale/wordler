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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.RowData

@Composable
fun GameBoard(modifier: Modifier,rows: List<RowData>, onRowUpdateFinish: () -> Unit) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color.Transparent),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(5.dp))
        rows.forEach { rowData ->
            TileRow(rowData,onRowUpdateFinish)
            Spacer(Modifier.height(5.dp))
        }
    }
}
