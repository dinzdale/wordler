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
                ShowGameBoard()
            }
        }
    }
}

@Composable
fun ShowGameBoard() {
    var initializeGameBoard by remember { mutableStateOf(true) }
    var wordDictionary = remember { mutableStateListOf<WordDictionary>() }
    var gameBoardState = remember { mutableStateListOf(mutableStateListOf<TileData>()) }

    var renerAsGuess by remember { mutableStateOf(false) }

    var wordSelectionRow by remember { mutableStateOf(0) }
    var currentRow by remember { mutableStateOf(0) }
    var currentColumn by remember { mutableStateOf(0) }

    //var currentGuess = remember { mutableStateListOf(mutableStateListOf<Char>()) }
    var currentGuess = remember { mutableStateListOf(mutableStateListOf<Char>()) }
    var initCurrentGuess by remember { mutableStateOf(true) }

    var cnt by remember { mutableStateOf(0) }
    var keyData by remember {
        mutableStateOf(
            KeyData(
                ';',
                KeyType.ENTER,
                TileKeyStatus.INITIAL_KEY
            )
        )
    }
    var restKeyboard by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()


    @Composable
    fun UpdateTiles(
        guess: List<Char> = currentGuess[currentRow],
        renderAsGuess: Boolean = renerAsGuess,
        onRenderCompleted: (Boolean) -> Unit
    ) {
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
                } else {
                    guessHits.find {
                        it.found.not() && it.char == guess[column]
                    }?.also {
                        it.found = true
                        gameBoardState[currentRow][column] =
                            TileData(it.char, TileKeyStatus.MATCH_OUT_POSITION, column)
                    } ?: {
                        if (guess[column] == '?') {
                            gameBoardState[currentRow][column] =
                                TileData(guess[column], TileKeyStatus.EMPTY, column)
                        } else {
                            gameBoardState[currentRow][column] =
                                TileData(guess[column], TileKeyStatus.NO_MATCH, column)

                        }
                    }
                }
            }
        } else {
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
        onRenderCompleted(renderAsGuess)
    }


    LaunchedEffect(initializeGameBoard) {
        if (initializeGameBoard) {
            if (gameBoardState[0].size == 0) {
                gameBoardState.clear()
                for (row in 0..5) {
                    var column = 0
                    gameBoardState.add(
                        row, mutableStateListOf(
                            TileData('X', TileKeyStatus.EMPTY, column++),
                            TileData('X', TileKeyStatus.EMPTY, column++),
                            TileData('X', TileKeyStatus.EMPTY, column++),
                            TileData('X', TileKeyStatus.EMPTY, column++),
                            TileData('X', TileKeyStatus.EMPTY, column++)
                        )
                    )
                }
            } else {
                for (row in 0..gameBoardState.lastIndex) {
                    for (column in 0..gameBoardState[0].lastIndex) {
                        gameBoardState[row][column] = TileData('X', TileKeyStatus.EMPTY, column)
                    }
                }
            }
            initializeGameBoard = false
        }
    }

    LaunchedEffect(cnt) {
        wordDictionary.clear()
        WordlerRepo.getWordsAndDefinitions().entries.forEach {
            wordDictionary.add(
                WordDictionary(
                    it.key.map { it.uppercaseChar() }.toList(),
                    it.value
                )
            )
        }
    }



    LaunchedEffect(initCurrentGuess) {
        if (initCurrentGuess) {
            if (currentGuess[0].size == 0) {
                currentGuess.clear()
                for (i in 0..5) {
                    currentGuess.add(i, mutableStateListOf('?', '?', '?', '?', '?'))
                }
            } else {
                for (row in 0..currentGuess.lastIndex) {
                    for (column in 0..currentGuess[0].lastIndex) {
                        currentGuess[row][column] = '?'
                    }
                }
            }
            initCurrentGuess = false
        }
    }


    SetCurrentColumn(currentGuess[currentRow]) {
        currentColumn = it
    }
    UpdateTiles() {

    }
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

                    KeyType.ENTER -> {}
                }
            }
            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    { renerAsGuess = renerAsGuess.not() },
                    enabled = if (initializeGameBoard.not()) {
                        allowGuess(currentGuess[currentRow][4]).value
                    } else {
                        false
                    }
                ) {
                    Text("Guess")
                }
                Button({
                    wordSelectionRow = ++wordSelectionRow % 3
                    if (wordSelectionRow == 0) {
                        cnt = ++cnt % 2
                    }
                    initCurrentGuess = true
                    initializeGameBoard = true
                    renerAsGuess = false
                }) {
                    Text("new word")
                }
                if (gameInitialized(
                        initializeGameBoard,
                        wordDictionary.isNotEmpty(),
                        initCurrentGuess
                    ).value
                ) {
                    Text("${wordDictionary[wordSelectionRow].wordList.toString()}")
                }
            }
        }
    }
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

@Composable
fun allowGuess(lastGuess: Char) = produceState(false, lastGuess) {
    value = lastGuess != '?'
}

fun initCurrentGuess() = mutableStateListOf(
    mutableStateListOf('?', '?', '?', '?', '?'),
    mutableStateListOf('?', '?', '?', '?', '?'),
    mutableStateListOf('?', '?', '?', '?', '?'),
    mutableStateListOf('?', '?', '?', '?', '?'),
    mutableStateListOf('?', '?', '?', '?', '?'),
    mutableStateListOf('?', '?', '?', '?', '?'),
)

@Composable
fun gameInitialized(
    initializeGameBoard: Boolean,
    wordDictionaryLoaded: Boolean,
    initCurrentGuess: Boolean
) = produceState(false, initializeGameBoard, wordDictionaryLoaded, initCurrentGuess) {
    value = initializeGameBoard.not() && wordDictionaryLoaded && initCurrentGuess.not()
}
