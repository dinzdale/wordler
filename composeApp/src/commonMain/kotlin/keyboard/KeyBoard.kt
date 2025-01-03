package keyboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.TileKeyStatus

@Composable
fun KeyBoard(
    modifier: Modifier,
    keyDataUpdate: List<KeyData>,
    reset: Boolean,
    onResetKeyboardComplete: () -> Unit,
    onSelectedKey: (KeyData) -> Unit
) {
    val topRow = remember {
        mutableStateListOf(
            KeyData('Q'),
            KeyData('W'),
            KeyData('E'),
            KeyData('R'),
            KeyData('T'),
            KeyData('Y'),
            KeyData('U'),
            KeyData('I'),
            KeyData('O'),
            KeyData('P')
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
            KeyData('?', enabled = false, KeyType.ENTER),
            KeyData('Z'),
            KeyData('X'),
            KeyData('C'),
            KeyData('V'),
            KeyData('B'),
            KeyData('N'),
            KeyData('M'),
            KeyData('!', keyType = KeyType.DELETE)
        )
    }

    @Composable
    fun ResetKeys(reset: Boolean) {
        if (reset) {
            topRow.set(0, KeyData('Q'))
            topRow.set(1, KeyData('W'))
            topRow.set(2, KeyData('E'))
            topRow.set(3, KeyData('R'))
            topRow.set(4, KeyData('T'))
            topRow.set(5, KeyData('Y'))
            topRow.set(6, KeyData('U'))
            topRow.set(7, KeyData('I'))
            topRow.set(8, KeyData('O'))
            topRow.set(9, KeyData('P'))
            middleRow.set(0, KeyData('A'))
            middleRow.set(1, KeyData('S'))
            middleRow.set(2, KeyData('D'))
            middleRow.set(3, KeyData('F'))
            middleRow.set(4, KeyData('G'))
            middleRow.set(5, KeyData('H'))
            middleRow.set(6, KeyData('J'))
            middleRow.set(7, KeyData('K'))
            middleRow.set(8, KeyData('L'))
            bottomRow.set(0, KeyData('?', enabled = false, KeyType.ENTER))
            bottomRow.set(1, KeyData('Z'))
            bottomRow.set(2, KeyData('X'))
            bottomRow.set(3, KeyData('C'))
            bottomRow.set(4, KeyData('V'))
            bottomRow.set(5, KeyData('B'))
            bottomRow.set(6, KeyData('N'))
            bottomRow.set(7, KeyData('M'))
            bottomRow.set(8, KeyData('!', keyType = KeyType.DELETE))
            onResetKeyboardComplete()
        }
    }
    LaunchedEffect(keyDataUpdate) {
        if (keyDataUpdate.isNotEmpty()) {
            keyDataUpdate.forEach { nxtKeyData ->
                nxtKeyData.keyType.also { keyType ->
                    when (keyType) {
                        KeyType.ALPHA -> {
                            var index = topRow.indexOfFirst { it.char == nxtKeyData.char }
                            if (index != -1) {
                                if (topRow[index].char == nxtKeyData.char) {
                                    if (topRow[index].status == TileKeyStatus.SELECTED && nxtKeyData.status == TileKeyStatus.SELECTED) {
                                        topRow[index] =
                                            nxtKeyData.copy(count = topRow[index].count + 1)
                                    } else if (topRow[index].status == TileKeyStatus.SELECTED && topRow[index].count > 0) {
                                        topRow[index] =
                                            topRow[index].copy(count = topRow[index].count - 1)
                                    } else {
                                        topRow[index] = nxtKeyData
                                    }
                                } else {
                                    topRow[index] = nxtKeyData
                                }
                            } else {
                                index = middleRow.indexOfFirst { it.char == nxtKeyData.char }
                                if (index != -1) {
                                    middleRow[index] = nxtKeyData
                                } else {
                                    index = bottomRow.indexOfFirst { it.char == nxtKeyData.char }
                                    if (index != -1) {
                                        bottomRow[index] = nxtKeyData
                                    }
                                }
                            }
                        }

                        KeyType.ENTER -> {
                            bottomRow.indexOfFirst { it.keyType == KeyType.ENTER }.also { index ->
                                if (index > -1) {
                                    bottomRow[index] = nxtKeyData
                                }
                            }
                        }

                        KeyType.DELETE -> {
                            bottomRow.indexOfFirst { it.keyType == KeyType.DELETE }.also { index ->
                                if (index > -1) {
                                    bottomRow[index] = nxtKeyData
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight(.25f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KeyBoardRow(topRow, onSelectedKey)
        Spacer(Modifier.height(5.dp))
        KeyBoardRow(middleRow, onSelectedKey)
        Spacer(Modifier.height(5.dp))
        KeyBoardRow(bottomRow, onSelectedKey)
    }
    ResetKeys(reset)

}
