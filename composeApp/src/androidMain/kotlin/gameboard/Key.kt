package gameboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.PieceColor
import model.ui.game_pieces.TileKeyStatus

@Composable
fun Key(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    when (keyData.keyType) {
        // ALPHA, ENTER, RETURN
        KeyType.ALPHA -> {}
        KeyType.ENTER -> {}
        KeyType.RETURN -> {}
    }
}

@Composable
fun AlphaKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    Button(
        { onKeySelection(keyData) },
        Modifier.width(36.dp).height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backGround),

        ) {
        Text("Q", textAlign = TextAlign.Center, style = TextStyle(color = foreGround, fontWeight = FontWeight.Bold, fontSize = 24.sp))
    }
}

@Composable
@Preview
fun PreviewAlphaKey() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AlphaKey(keyData = KeyData('Q', KeyType.ALPHA, TileKeyStatus.MATCH_IN_POSITION)) {

                }
            }
        }
    }
}

