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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import gameboard.GameBoard
import gameboard.TileRow
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileStatus
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.random.Random

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
    var words = remember { mutableStateListOf("ABCDE") }
    var definition by remember { mutableStateOf<String?>(null) }
    var cnt by remember { mutableStateOf(0) }

    val scrollState = rememberScrollState()



    LaunchedEffect(cnt) {
        WordlerAPI.getWords(noWords = 6, length = Random.nextInt(3, 5)).apply {
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
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (words.isNotEmpty()) {
                GameBoard(words.map { getMockTileDataList(it,definition) })
            }
            Spacer(Modifier.height(5.dp))
            Text(words.joinToString())
            Spacer(Modifier.height(5.dp))
            definition?.also {
                Text(
                    it,
                    modifier = Modifier.padding(horizontal = 50.dp),
                    textAlign = TextAlign.Start
                )
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

fun getMockTileDataList(word: String, definition: String?): RowData {
    val charArray = word.toCharArray()
    val defaultList = mutableListOf(
        TileData('X', TileStatus.EMPTY, 0),
        TileData('X', TileStatus.EMPTY, 1),
        TileData('X', TileStatus.EMPTY, 2),
        TileData('X', TileStatus.EMPTY, 3),
        TileData('X', TileStatus.EMPTY, 4)
    )
    return definition?.let {
        val tempList = charArray.mapIndexed() { index, char ->
            TileData(charArray[index], TileStatus.values()[Random.nextInt(1, 4)], index)
        }
        tempList.forEachIndexed { index, tileData ->
            defaultList[index] = tileData
        }
         RowData(defaultList, 0)
    }  ?: RowData(defaultList, 0)

}

