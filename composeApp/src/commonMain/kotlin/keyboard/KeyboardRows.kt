package keyboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.TileKeyStatus


@Composable
fun KeyBoardRow(keyList: List<KeyData>, onKeySelected: (KeyData)->Unit) {
    Row(Modifier.wrapContentSize(),horizontalArrangement = Arrangement.SpaceAround) {
        keyList.forEach {
            Key(it,onKeySelected)
            Spacer(Modifier.width(3.dp))
        }
    }
}

//@Composable
//@Preview
//fun KeyBoardRowPreview() {
//    var keysTopRow = mutableListOf(
//        KeyData('Q',KeyType.ALPHA,TileKeyStatus.INITIAL_KEY),
//        KeyData('W',KeyType.ALPHA,TileKeyStatus.INITIAL_KEY),
//        KeyData('E',KeyType.ALPHA,TileKeyStatus.INITIAL_KEY),
//        KeyData('R',KeyType.ALPHA,TileKeyStatus.INITIAL_KEY),
//        KeyData('T',KeyType.ALPHA,TileKeyStatus.INITIAL_KEY),
//        KeyData('Y',KeyType.ALPHA,TileKeyStatus.INITIAL_KEY),
//        )
//    MaterialTheme {
//        Surface(Modifier.fillMaxSize()) {
//            KeyBoardRow(keyList = keysTopRow){}
//        }
//    }
//}

