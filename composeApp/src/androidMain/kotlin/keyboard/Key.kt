package keyboard

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
        KeyType.ALPHA -> AlphaKey(keyData, onKeySelection)
        KeyType.ENTER -> EnterKey(keyData, onKeySelection)
        KeyType.DELETE -> DeleteKey(keyData, onKeySelection)
    }
}

@Composable
fun AlphaKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    Button(
        { onKeySelection(keyData) },
        Modifier
            .width(36.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backGround),

        ) {
        Text(keyData.char.toString(), textAlign = TextAlign.Center, style = TextStyle(color = foreGround, fontWeight = FontWeight.Bold, fontSize = 20.sp))
    }
}

@Composable
fun EnterKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    Button(
        { onKeySelection(keyData) },
        Modifier
            .width(64.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backGround),

        ) {
        Text("ENT", textAlign = TextAlign.Center, style = TextStyle(color = foreGround, fontWeight = FontWeight.Bold, fontSize = 12.sp))
    }
}
@Composable
fun DeleteKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    Button(
        { onKeySelection(keyData) },
        Modifier
            .width(64.dp)
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = backGround),

        ) {
        Text("DEL", textAlign = TextAlign.Center, style = TextStyle(color = foreGround, fontWeight = FontWeight.Bold, fontSize = 12.sp))
    }
}
@Composable
@Preview
fun PreviewAlphaKey() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Key(keyData = KeyData('Q', KeyType.ALPHA, TileKeyStatus.INITIAL_KEY)) {

                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewEnterKey() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Key(keyData = KeyData('Q', KeyType.ENTER, TileKeyStatus.INITIAL_KEY)) {

                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewDeleteKey() {
    MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Key(keyData = KeyData('Q', KeyType.DELETE, TileKeyStatus.INITIAL_KEY)) {

                }
            }
        }
    }
}
