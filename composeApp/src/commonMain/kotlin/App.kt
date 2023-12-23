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
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import gameboard.GameBoard
import keyboard.KeyBoard
import model.repos.WordlerRepo
import model.ui.game_pieces.GuessHit
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
                InitGame {
                    println("toplevel compositions $it")
                }
            }
        }
    }
}

@Composable
fun InitGame(composeCnt: (Int) -> Unit) {

    var composeCnt by remember { mutableStateOf(0) }

    var restKeyboard by remember { mutableStateOf(false) }

    composeCnt(++composeCnt)

    ShowGameBoard(
        restKeyboard,
        {
            println("Gameboard compositions $it")
        },
        {
            restKeyboard = false
        })

}


@Composable
fun ShowGameBoard(
    resetKeyboard: Boolean,
    onComposeCnt: (Int) -> Unit,
    onKeyboardResetComplete: () -> Unit
) {
    var composeCnt by remember { mutableStateOf(0) }

    var wordDictionary = remember { mutableListOf<WordDictionary>() }
    var wordDictionaryIndex by remember { mutableStateOf(0) }
    var wordSelectionRow by remember { mutableStateOf(0) }

    var currentRow by remember { mutableStateOf(0) }
    var currentColumn by remember { mutableStateOf(0) }
    var renerAsGuess by remember { mutableStateOf(false) }
    var currentGuess = remember {
        mutableStateListOf(
            mutableStateListOf<Char>('?', '?', '?', '?', '?'),
            mutableStateListOf<Char>('?', '?', '?', '?', '?'),
            mutableStateListOf<Char>('?', '?', '?', '?', '?'),
            mutableStateListOf<Char>('?', '?', '?', '?', '?'),
            mutableStateListOf<Char>('?', '?', '?', '?', '?'),
            mutableStateListOf<Char>('?', '?', '?', '?', '?')
        )
    }
    var gameBoardState = remember {
        mutableStateListOf(
            mutableStateListOf(
                TileData('X', TileKeyStatus.EMPTY, 0),
                TileData('X', TileKeyStatus.EMPTY, 1),
                TileData('X', TileKeyStatus.EMPTY, 2),
                TileData('X', TileKeyStatus.EMPTY, 3),
                TileData('X', TileKeyStatus.EMPTY, 4),
            ),
            mutableStateListOf(
                TileData('X', TileKeyStatus.EMPTY, 0),
                TileData('X', TileKeyStatus.EMPTY, 1),
                TileData('X', TileKeyStatus.EMPTY, 2),
                TileData('X', TileKeyStatus.EMPTY, 3),
                TileData('X', TileKeyStatus.EMPTY, 4),
            ),
            mutableStateListOf(
                TileData('X', TileKeyStatus.EMPTY, 0),
                TileData('X', TileKeyStatus.EMPTY, 1),
                TileData('X', TileKeyStatus.EMPTY, 2),
                TileData('X', TileKeyStatus.EMPTY, 3),
                TileData('X', TileKeyStatus.EMPTY, 4),
            )
        )
    }
    var keyDataUpdate = remember {
        mutableStateListOf(
            KeyData(
                ';',
                enabled = false,
                KeyType.ENTER,
                status = TileKeyStatus.INITIAL_KEY
            ),
            KeyData('?'),
            KeyData('?'),
            KeyData('?'),
            KeyData('?'),
        )
    }
    var loadWordDictionary by remember { mutableStateOf(true) }

    var scrollState = rememberScrollState()


    GetWordDictionary(loadWordDictionary) {
        loadWordDictionary = false
        wordDictionary.removeAll { true }
        wordDictionary.addAll(it)
    }

    @Composable
    fun UpdateTiles(
        guess: List<Char> = currentGuess[currentRow],
        renderAsGuess: Boolean = renerAsGuess,
        onRenderCompleted: (Int) -> Unit
    ) {
        var composeCnt by remember { mutableStateOf(0) }
        if (renderAsGuess) {
            val guessHits = mutableListOf<GuessHit>().apply {
                for (i in 0..wordDictionary[wordSelectionRow].wordList.lastIndex) {
                    add(i, GuessHit(wordDictionary[wordSelectionRow].wordList[i], false))
                }
            }

            for (column in 0..guess.lastIndex) {
                if (guessHits[column].char == guess[column] && guessHits[column].found.not()) {
                    guessHits[column].found = true
                    gameBoardState[currentRow][column] =
                        TileData(guess[column], TileKeyStatus.MATCH_IN_POSITION, column)
                    keyDataUpdate[column] =
                        KeyData(guess[column], status = TileKeyStatus.MATCH_IN_POSITION)
                } else {
                    guessHits.firstOrNull { it.found.not() && it.char == guess[column] }?.also {
                        it.found = true
                        gameBoardState[currentRow][column] =
                            TileData(it.char, TileKeyStatus.MATCH_OUT_POSITION, column)
                        keyDataUpdate[column] =
                            KeyData(guess[column], status = TileKeyStatus.MATCH_OUT_POSITION)
                    } ?: run {
                        gameBoardState[currentRow][column] =
                            TileData(guess[column], TileKeyStatus.SELECTED, column)
                        keyDataUpdate[column] =
                            KeyData(guess[column], status = TileKeyStatus.SELECTED)
                    }
                }
            }

        } else {
            //restKeyboard = true
            for (column in 0..guess.lastIndex) {
                if (guess[column] == '?') {
                    gameBoardState[currentRow][column] =
                        TileData(guess[column], TileKeyStatus.EMPTY, column)
                } else {
                    gameBoardState[currentRow][column] =
                        TileData(guess[column], TileKeyStatus.NO_MATCH, column)
                }
            }
        }

        onRenderCompleted(++composeCnt)
    }

    @Composable
    fun UpdateEnterKey() {
        var isEnabled = allowGuess(currentGuess[currentRow][4]).value
        LaunchedEffect(isEnabled) {
            keyDataUpdate[0] = KeyData(';', isEnabled, KeyType.ENTER)
        }
    }
    SetCurrentColumn(currentGuess[currentRow]) {
        currentColumn = it
    }
    UpdateTiles(currentGuess[currentRow], renerAsGuess) {

    }
    UpdateEnterKey()
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
            KeyBoard(
                Modifier,
                keyDataUpdate.toList(),
                resetKeyboard,
                onKeyboardResetComplete
            ) { keyData ->
                when (keyData.keyType) {
                    KeyType.ALPHA -> keyData.char?.also {
                        if (IntRange(0, 4).contains(currentColumn)) {
                            currentGuess[currentRow][currentColumn] = it
                        }
                    }

                    KeyType.DELETE -> {
                        var prevIndex = currentColumn - 1
                        if (prevIndex < 0) {
                            prevIndex = 0
                        }
                        currentGuess[currentRow][prevIndex] = '?'
                    }

                    KeyType.ENTER -> {
                        renerAsGuess = renerAsGuess.not()
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button({
                    wordSelectionRow = ++wordSelectionRow % 3
                    if (wordSelectionRow == 0) {
                        loadWordDictionary = true
                    }
                }) {
                    Text("new word")
                }

                if (wordDictionary.isNotEmpty()) {
                    wordDictionary[wordSelectionRow].also {
                        Text(it.wordList.toString())
                    }
                }
            }
        }
    }
    onComposeCnt(++composeCnt)
}


@Composable
fun GetWordDictionary(load: Boolean, onResults: (List<WordDictionary>) -> Unit) {
    LaunchedEffect(load) {
        if (load) {
            var results = mutableListOf<WordDictionary>()
            WordlerRepo.getWordsAndDefinitions().entries.forEachIndexed() { index, entry ->
                results.add(
                    index,
                    WordDictionary(
                        entry.key.map { it.uppercaseChar() }.toList(),
                        entry.value
                    )
                )
            }
            onResults(results)
        }
    }
}


@Composable
fun allowGuess(lastGuess: Char) = produceState(false, lastGuess) {
    value = lastGuess != '?'
}

@Composable
fun gameInitialized(
    wordDictionaryLoaded: Boolean,
) = produceState(false, wordDictionaryLoaded) {
    value = wordDictionaryLoaded
}

@Composable
fun SetCurrentColumn(guess: List<Char>, onCurrentColumn: (Int) -> Unit) {
    if (guess.isNotEmpty()) {
        guess.indexOf('?').let {
            if (it > -1) {
                onCurrentColumn(it)

            } else {
                onCurrentColumn(5)
            }
        }
    }
}

