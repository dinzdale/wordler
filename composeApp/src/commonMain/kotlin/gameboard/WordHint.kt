package gameboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WordHint(showHint: Boolean, word: String, onDismiss: () -> Unit) {
    if (showHint) {
        Dialog(onDismiss, DialogProperties()) {
            Box(
                Modifier.shadow(elevation = 8.dp, shape = RoundedCornerShape(10.dp))
                    .wrapContentHeight().fillMaxWidth(.80f).padding(vertical = 6.dp).wrapContentHeight()
                    .background(Color.Yellow),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    word,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }

}





