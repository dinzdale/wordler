package keyboard

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.PieceColor

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
    Box(
        Modifier.width(36.dp).height(48.dp).background(backGround, RoundedCornerShape(10.dp))
            .clickable { onKeySelection(keyData) },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            keyData.char.toString(),
            style = TextStyle(
                color = foreGround,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
    }
}

@Composable
fun EnterKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    Box(
        Modifier
            .width(64.dp)
            .height(48.dp).background(backGround, RoundedCornerShape(5.dp))
            .clickable {
                if (keyData.enabled) {
                    onKeySelection(keyData)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            "ENT",
            textAlign = TextAlign.Center,
            style = TextStyle(color = foreGround, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        )
    }
}

@Composable
fun DeleteKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    Box(
        Modifier
            .width(64.dp)
            .height(48.dp).background(backGround, RoundedCornerShape(5.dp))
            .clickable {
                if (keyData.enabled) {
                    onKeySelection(keyData)
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            "DEL",
            textAlign = TextAlign.Center,
            style = TextStyle(color = foreGround, fontWeight = FontWeight.Bold, fontSize = 12.sp)
        )
    }
}
