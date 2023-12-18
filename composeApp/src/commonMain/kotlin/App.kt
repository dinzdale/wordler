import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gameboard.GameBoard
import keyboard.KeyBoard
import model.DictionaryItem
import model.repos.WordlerRepo
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus
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
    var initializeGameBoard by remember { mutableStateOf(true) }
    var wordMap = remember { mutableStateMapOf<String, List<DictionaryItem>>() }
    var gameBoardState = remember { mutableStateMapOf<Int, MutableList<TileData>>() }


    var words = remember { mutableStateListOf("ABCDE") }
    var definition by remember { mutableStateOf<String?>(null) }
    var cnt by remember { mutableStateOf(0) }
    var keyData by remember {
        mutableStateOf(
            KeyData(
                'Q',
                KeyType.ALPHA,
                TileKeyStatus.MATCH_IN_POSITION
            )
        )
    }
    var restKeyboard by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    LaunchedEffect(initializeGameBoard) {
        if (initializeGameBoard) {
            for (row in 0..6) {
                gameBoardState[row] = mutableListOf(
                    TileData('X', TileKeyStatus.EMPTY, 0),
                    TileData('X', TileKeyStatus.EMPTY, 1),
                    TileData('X', TileKeyStatus.EMPTY, 2),
                    TileData('X', TileKeyStatus.EMPTY, 3),
                    TileData('X', TileKeyStatus.EMPTY, 4),
                )
            }
        }
        initializeGameBoard = false
    }

    LaunchedEffect(cnt) {
        WordlerRepo.getWordsAndDefinitions().entries.forEach {
            wordMap[it.key] = it.value
        }
//        WordlerAPI.getWords(noWords = 6, length = Random.nextInt(3, 6)).apply {
//            onSuccess { wordList ->
//                words.clear()
//                words.addAll(wordList.map { it.uppercase() })
//                WordlerAPI.getDictionaryDefinition(wordList[0]).apply {
//                    onSuccess { itemsList ->
//                        definition = itemsList[0].meanings[0].definitions[0].definition
//                    }
//                    onFailure {
//                        definition = null
//                    }
//                }
//            }
//            onFailure {
//                definition = null
//            }
//        }
    }

    Box(
        Modifier.fillMaxSize().verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        if (gameBoardState.isNotEmpty()) {
            GameBoard(gameBoardState.entries.map {
                RowData(rowPosition = it.key, tileData = it.value)
            })
        }
        Column(Modifier.align(Alignment.BottomCenter)) {
            KeyBoard(Modifier, keyData, restKeyboard, {
                restKeyboard = false
            }) {
                when (it.keyType) {
                    KeyType.ALPHA -> keyData = keyData.copy(it.char)
                    KeyType.DELETE -> restKeyboard = true
                    KeyType.ENTER -> {}
                }
            }
            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button({
                    cnt = ++cnt % 2
                }) {
                    Text("new word")
                }
                if (wordMap.isNotEmpty()) {
                    Text("${wordMap.keys.first()}")
                }
            }
        }
    }
//        Column(
//            modifier = Modifier.fillMaxSize(),
//            verticalArrangement = Arrangement.SpaceEvenly,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            if (words.isNotEmpty()) {
//                GameBoard(gameBoardState.entries.map {
//                    RowData(rowPosition = it.key, tileData = it.value)
//                })
//            }
//            Spacer(Modifier.height(5.dp))
//            Text(words.joinToString())
//            Spacer(Modifier.height(5.dp))
//            definition?.also {
//                Text(
//                    it,
//                    modifier = Modifier.padding(horizontal = 50.dp),
//                    textAlign = TextAlign.Start
//                )
//            }
//            Spacer(Modifier.height(20.dp))
//            Button({
//                cnt = ++cnt % 2
//            }) {
//                Text("Get more words")
//            }
//            KeyBoard(keyData,restKeyboard,{
//                restKeyboard = false
//            }) {
//                when(it.keyType) {
//                    KeyType.ALPHA -> keyData = keyData.copy(it.char)
//                    KeyType.DELETE -> restKeyboard = true
//                    KeyType.ENTER -> {}
//                }
//            }

//    }


    fun getMockTileDataList(word: String, definition: String?): RowData {
        val charArray = word.toCharArray()
        val defaultList = mutableListOf(
            TileData('X', TileKeyStatus.EMPTY, 0),
            TileData('X', TileKeyStatus.EMPTY, 1),
            TileData('X', TileKeyStatus.EMPTY, 2),
            TileData('X', TileKeyStatus.EMPTY, 3),
            TileData('X', TileKeyStatus.EMPTY, 4)
        )
        return definition?.let {
            val tempList = charArray.mapIndexed() { index, char ->
                TileData(charArray[index], TileKeyStatus.values()[Random.nextInt(1, 4)], index)
            }
            tempList.forEachIndexed { index, tileData ->
                defaultList[index] = tileData
            }
            RowData(defaultList, 0)
        } ?: RowData(defaultList, 0)

    }
}

