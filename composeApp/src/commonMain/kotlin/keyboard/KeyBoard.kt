package keyboard

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.TileKeyStatus

@Composable
fun KeyBoard(
    keyData: KeyData,
    onSelectedKey: (KeyData) -> Unit
) {
    val topRow = remember {
        mutableListOf(
            KeyData('Q'),
            KeyData('W'),
            KeyData('E'),
            KeyData('R'),
            KeyData('T'),
            KeyData('Y'),
            KeyData('U'),
            KeyData('I'),
            KeyData('O'),
            KeyData('P'),
        )
    }
    val middleRow = remember {
        mutableStateListOf(
            KeyData('A'),
            KeyData('S'),
            KeyData('D'),
            KeyData('F'),
            KeyData('G'),
            KeyData('H'),
            KeyData('J'),
            KeyData('K'),
            KeyData('L')
        )
    }
    val bottomRow = remember {
        mutableStateListOf(
            KeyData('?', KeyType.ENTER),
            KeyData('Z'),
            KeyData('X'),
            KeyData('C'),
            KeyData('V'),
            KeyData('B'),
            KeyData('N'),
            KeyData('M'),
            KeyData('!', KeyType.DELETE)
        )
    }
    if (keyData.keyType == KeyType.ALPHA) {
        var index = topRow.indexOfFirst { it.char == keyData.char }
        if (index != -1) {
            topRow[index] = keyData
        } else {
            index = middleRow.indexOfFirst { it.char == keyData.char }
            if (index != -1) {
                middleRow[index] = keyData
            } else {
                index = bottomRow.indexOfFirst { it.char == keyData.char }
                if (index != -1) {
                    bottomRow[index] = keyData
                }
            }
        }
    }

    Column(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KeyBoardRow(topRow, onSelectedKey)
        Spacer(Modifier.height(5.dp))
        KeyBoardRow(middleRow, onSelectedKey)
        Spacer(Modifier.height(5.dp))
        KeyBoardRow(bottomRow, onSelectedKey)
    }
}

@Composable
//@Preview
fun PreviewKeyBoard() {
    MaterialTheme {
        var keyData by remember { mutableStateOf(KeyData('G',KeyType.ALPHA,TileKeyStatus.MATCH_IN_POSITION)) }
        Surface(Modifier.fillMaxSize()) {
            KeyBoard(keyData) {
                  keyData = it
            }
        }
    }
}
