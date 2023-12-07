import Network.WordlerAPI
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
//    MaterialTheme {
//        var greetingText by remember { mutableStateOf("Hello World!") }
//        var showImage by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = {
//                greetingText = "Compose: ${Greeting().greet()}"
//                showImage = !showImage
//            }) {
//                Text(greetingText)
//            }
//            AnimatedVisibility(showImage) {
//                Image(
//                    painterResource("compose-multiplatform.xml"),
//                    null
//                )
//            }
//        }
//    }
    ShowLayout()
}

@Composable
fun ShowLayout() {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(modifier = Modifier.fillMaxSize()) {
                ShowGameBoard()
            }
        }
    }
}

@Composable
fun ShowGameBoard() {
    var words by remember { mutableStateOf("Words Go Here") }
    var definition by remember { mutableStateOf("") }
    var cnt by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()

    LaunchedEffect(cnt) {
        WordlerAPI.getWords().apply {
            onSuccess { wordList ->
                words = wordList.joinToString()
                WordlerAPI.getDictionaryDefinition(wordList[0]).apply {
                    onSuccess { itemsList ->
                        definition = itemsList[0].toString()
                    }
                    onFailure {
                        definition = "no definition: ${it.message}"
                    }
                }
            }
            onFailure {
                definition = "word list failed: ${it.message}"
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize().verticalScroll(scrollState), contentAlignment = Alignment.TopCenter) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(words)
            Text(definition.toString())
            Button({
                cnt = ++cnt % 2
            }) {
                Text("Get more words")
            }
        }
    }
}