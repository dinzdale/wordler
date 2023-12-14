package keyboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.KeyBoardRowData
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.TileKeyStatus

@Composable
fun KeyBoard(
    topRow: KeyBoardRowData,
    middleRow: KeyBoardRowData,
    bottomRow: KeyBoardRowData,
    onSelectedKey: (KeyData) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KeyBoardRow(keyList = topRow.keyData, onSelectedKey)
        Spacer(Modifier.height(5.dp))
        KeyBoardRow(keyList = middleRow.keyData, onSelectedKey)
        Spacer(Modifier.height(5.dp))
        KeyBoardRow(keyList = bottomRow.keyData, onSelectedKey)
    }
}

@Composable
//@Preview
fun PreviewKeyBoard() {
    var rowData = remember {
        mutableStateListOf(
            KeyBoardRowData(
                mutableStateListOf(
                    KeyData('Q', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 0),
                    KeyData('W', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 1),
                    KeyData('E', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 2),
                    KeyData('R', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 3),
                    KeyData('T', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 4),
                    KeyData('Y', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 5),
                    KeyData('U', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 6),
                    KeyData('I', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 7),
                    KeyData('O', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 8),
                    KeyData('P', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 9),
                ), 0
            ),
            KeyBoardRowData(
                mutableStateListOf(
                    KeyData('A', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 0),
                    KeyData('S', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 1),
                    KeyData('D', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 2),
                    KeyData('F', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 3),
                    KeyData('G', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 4),
                    KeyData('H', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 5),
                    KeyData('I', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 6),
                    KeyData('J', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 7),
                    KeyData('K', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 8),
                    KeyData('L', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 9),
                ), 1
            ),
            KeyBoardRowData(
                mutableStateListOf(
                    KeyData('X', KeyType.ENTER, TileKeyStatus.INITIAL_KEY, 0),
                    KeyData('Z', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 1),
                    KeyData('X', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 2),
                    KeyData('C', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 3),
                    KeyData('V', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 4),
                    KeyData('B', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 5),
                    KeyData('N', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 6),
                    KeyData('M', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY, 7),
                    KeyData('X', KeyType.DELETE, TileKeyStatus.INITIAL_KEY, 8),
                ), 1
            )
        )
    }
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            KeyBoard(rowData[0], rowData[1], rowData[2]) {
                println("Key ${it.char}")
            }
        }
    }
}
