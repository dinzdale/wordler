package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.PieceColor.Companion.getColor

@Composable
fun Key(keyData: KeyData, onKeySelection:(KeyType,Char?)->Unit) {
    when(keyData.keyType) {
        // ALPHA, ENTER, RETURN
        KeyType.ALPHA -> {}
        KeyType.ENTER -> {}
        KeyType.RETURN ->{}
    }
}

@Composable
fun KeyShowAlphaKey(keyData: KeyData, onKeySelection:(KeyType,Char?)->Unit) {

    Button( {onKeySelection(keyData.keyType,keyData.char)},Modifier.height(20.dp).width(10.dp).background(color = getColor(keyData).backGround)) {

    }
}
