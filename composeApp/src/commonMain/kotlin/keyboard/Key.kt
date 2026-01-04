package keyboard

//import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
    KeySurface(Modifier.width(36.dp).height(48.dp), keyData, onKeySelection) {
        Text(
            keyData.char.toString(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
    }
}

@Composable
fun EnterKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    KeySurface(Modifier.width(64.dp).height(48.dp), keyData, onKeySelection) {
        Text(
            "ENT",
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
        )
    }
}

@Composable
fun DeleteKey(keyData: KeyData, onKeySelection: (KeyData) -> Unit) {
    KeySurface(Modifier.width(64.dp).height(48.dp), keyData, onKeySelection) {
        Text(
            "DEL",
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 12.sp)
        )
    }
}

@Composable
private fun KeySurface(
    modifier: Modifier,
    keyData: KeyData,
    onKeySelection: (KeyData) -> Unit,
    content: @Composable () -> Unit
) {
    val (backGround, foreGround) = PieceColor.getColor(keyData)
    
    // Create a subtle concavity effect using a radial gradient for the "dish"
    val concaveBrush = Brush.radialGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.15f),
            Color.Transparent
        )
    )

    Box(
        modifier
            .clip(RoundedCornerShape(8.dp))
            .background(backGround)
            .drawBehind {
                // Subtle top highlight for 3D effect
                drawRect(
                    color = Color.White.copy(alpha = 0.2f),
                    size = size.copy(height = 4f)
                )
                // Bottom shadow
                drawRect(
                    color = Color.Black.copy(alpha = 0.3f),
                    topLeft = androidx.compose.ui.geometry.Offset(0f, size.height - 4f),
                    size = size.copy(height = 4f)
                )
            }
            .clickable { 
                if (keyData.enabled || keyData.keyType == KeyType.ALPHA) {
                    onKeySelection(keyData) 
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        // Concave "dish" overlay
        Box(
            Modifier
                .fillMaxSize()
                .padding(4.dp)
                .background(concaveBrush, RoundedCornerShape(10.dp))
        )
        
        // Key Label
        androidx.compose.runtime.CompositionLocalProvider(
            androidx.compose.material.LocalContentColor provides foreGround
        ) {
            content()
        }
    }
}
