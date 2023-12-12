import Network.WordlerAPI
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp
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
    var definition by remember { mutableStateOf<String?>(null) }
    var cnt by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()


    fun getMockTileDataList(word: String, definition:String?): List<TileData> {
        val charArray = word.toCharArray()
        if (charArray.isNotEmpty() && definition != null) {
            return listOf(
                TileData(charArray[0], TileStatus.MATCH_IN_POSITION, 0),
                TileData(charArray[1], TileStatus.MATCH_OUT_POSITION, 1),
                TileData(charArray[2], TileStatus.MATCH_IN_POSITION, 2),
                TileData(charArray[3], TileStatus.NO_MATCH, 3),
                TileData(charArray[4], TileStatus.NO_MATCH, 4),
            )
        } else {
            return listOf(
                TileData('X', TileStatus.EMPTY, 0),
                TileData('X', TileStatus.EMPTY, 1),
                TileData('X', TileStatus.EMPTY, 2),
                TileData('X', TileStatus.EMPTY, 3),
                TileData('X', TileStatus.EMPTY, 4),
            )
        }
    }
    LaunchedEffect(cnt) {
        WordlerAPI.getWords().apply {
            onSuccess { wordList ->
                words.clear()
                words.addAll(wordList.map { it.uppercase() })
                WordlerAPI.getDictionaryDefinition(wordList[0]).apply {
                    onSuccess { itemsList ->
                        definition = itemsList[0].meanings[0].definitions[0].definition
                    }
                    onFailure {
                        definition = null
                    }
                }
            }
            onFailure {
                definition = null
            }
        }
    }
    Box(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TileRow(getMockTileDataList(words[0],definition))
            Spacer(Modifier.height(5.dp))
            Text(words.joinToString())
            Spacer(Modifier.height(5.dp))
            definition?.also {
                Text(it, modifier = Modifier.padding(horizontal = 5.dp))
            }
            Spacer(Modifier.height(20.dp))
            Button({
                cnt = ++cnt % 2
            }) {
                Text("Get more words")
            }
        }
    }

}
