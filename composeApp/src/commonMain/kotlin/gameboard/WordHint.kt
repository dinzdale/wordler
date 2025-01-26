package gameboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.FlowPreview

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WordHint(showHint: Boolean, word: String, onDismiss: () -> Unit) {
    if (showHint) {
        Dialog(onDismiss, DialogProperties(usePlatformDefaultWidth = false)) {
            Text(word, style = TextStyle(fontSize = 24.sp))
        }
    }

}





