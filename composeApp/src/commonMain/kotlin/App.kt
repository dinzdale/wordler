import Network.WordlerAPI
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gameboard.TileRow
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
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
    val words = remember { mutableStateListOf("ABCDE") }
    var definition by remember { mutableStateOf("") }
    var cnt by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()


    fun getMockTileDataList(word: String): List<TileData> {
        val charArray = word.toCharArray()
        return listOf(
            TileData(charArray[0], TileStatus.MATCH_IN_POSITION, 0),
            TileData(charArray[1], TileStatus.MATCH_OUT_POSITION, 1),
            TileData(charArray[2], TileStatus.MATCH_IN_POSITION, 2),
            TileData(charArray[3], TileStatus.NO_MATCH, 3),
            TileData(charArray[4], TileStatus.NO_MATCH, 4),
        )
    }
    LaunchedEffect(cnt) {
        WordlerAPI.getWords().apply {
            onSuccess { wordList ->
                words.clear()
                words.addAll(wordList.map { it.uppercase() })
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
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TileRow(getMockTileDataList(words[0]))
            Text(words.joinToString())
            Text(definition)
            Button({
                cnt = ++cnt % 2
            }) {
                Text("Get more words")
            }
        }
    }

}
