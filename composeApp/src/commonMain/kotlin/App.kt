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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gameboard.GameBoard
import keyboard.KeyBoard
import model.repos.WordlerRepo
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
import model.ui.game_pieces.RowData
import model.ui.game_pieces.TileData
import model.ui.game_pieces.TileKeyStatus
import model.ui.game_pieces.WordDictionary
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
    var initializeGameBoard by remember { mutableStateOf(true) }
    var wordDictionary = remember { mutableStateListOf<WordDictionary>() }
    var gameBoardState = remember { mutableStateListOf<MutableList<TileData>>() }

    var currentRow by remember { mutableStateOf(0) }
    var currentColumn by remember { mutableStateOf(0) }
    val currentGuess = remember { mutableStateListOf('?', '?', '?', '?', '?') }

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


    @Composable
    fun UpdateTiles(guess: List<Char> = currentGuess) {
        if (initializeGameBoard.not() && wordDictionary.isNotEmpty()) {
            for (column in 0..guess.lastIndex) {
                if (wordDictionary[currentRow].wordList[column] == guess[column]) {
                    gameBoardState[currentRow][column] =
                        TileData(guess[column], TileKeyStatus.MATCH_IN_POSITION, column)
                } else {
                    var fullList = wordDictionary[currentRow].wordList
                    val result = fullList.subList(column, fullList.size).firstOrNull {
                        it == guess[column]
                    }?.also {
                        gameBoardState[currentRow][column] =
                            TileData(it, TileKeyStatus.MATCH_OUT_POSITION, column)
                    }
                    if (result == null) {
                        gameBoardState[currentRow][column] =
                            TileData(guess[column], TileKeyStatus.NO_MATCH, column)
                    }
                }
            }
        }
    }


    LaunchedEffect(initializeGameBoard) {
        if (initializeGameBoard) {
            for (row in 0..5) {
                gameBoardState.add(
                    mutableStateListOf(
                        TileData('X', TileKeyStatus.EMPTY, 0),
                        TileData('X', TileKeyStatus.EMPTY, 1),
                        TileData('X', TileKeyStatus.EMPTY, 2),
                        TileData('X', TileKeyStatus.EMPTY, 3),
                        TileData('X', TileKeyStatus.EMPTY, 4),
                    )
                )
            }
        }
        initializeGameBoard = false
    }

    LaunchedEffect(cnt) {
        WordlerRepo.getWordsAndDefinitions().entries.forEach {
            wordDictionary.add(WordDictionary(it.key.map { it.uppercaseChar() }.toList(), it.value))
        }
    }

    UpdateTiles()
    Box(
        Modifier.fillMaxSize().verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        if (gameBoardState.isNotEmpty()) {
            GameBoard(gameBoardState.mapIndexed() { index, titleDataList ->
                RowData(rowPosition = index, tileData = titleDataList)
            })
        }
        Column(Modifier.align(Alignment.BottomCenter)) {
            KeyBoard(Modifier, keyData, restKeyboard, {
                restKeyboard = false
            }) { keyData ->
                when (keyData.keyType) {
                    KeyType.ALPHA -> keyData.char?.also {
                        currentGuess[currentColumn] = it
                        if (++currentColumn > 4) {
                            currentColumn = 4
                        }
                    }//keyData = keyData.copy(it.char)
                    KeyType.DELETE -> {
                        currentGuess[currentColumn] = '?'
                        if (--currentColumn < 0) {
                            currentColumn = 0
                        }
                    }
                    KeyType.ENTER -> {}
                }
            }
            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button({}) {
                    Text("Guess")
                }
                Button({
                    cnt = ++cnt % 2
                }) {
                    Text("new word")
                }
                if (wordDictionary.isNotEmpty()) {
                    Text("${wordDictionary[0].wordList.toString()}")
                }
            }
        }
    }

}


