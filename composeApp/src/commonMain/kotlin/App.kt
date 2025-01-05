import Network.WordlerAPI
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
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import gameboard.checkGameBoardHasMatch
import gameboard.GameBoard
import gameboard.getRowData
import gameboard.initGameBoardStates
import gameboard.isKeyBoardStateInitialized
import gameboard.setTileData
import keyboard.KeyBoard
import kotlinx.coroutines.launch
import model.WordlerTopBar
import model.repos.WordlerRepo
import model.ui.game_pieces.GuessHit
import model.ui.game_pieces.KeyData
import model.ui.game_pieces.KeyType
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
            val scope = rememberCoroutineScope()
            val snackbarHostState = remember { SnackbarHostState() }
            Scaffold(
                topBar =  { WordlerTopBar({}){ menuItem->
                    scope.launch {
                        snackbarHostState.showSnackbar("${menuItem.title} selected")
                    }
                } },
                modifier = Modifier.fillMaxSize(),
                snackbarHost = { SnackbarHost(snackbarHostState) }) {
                               InitGame { message ->
                    scope.launch {
                        snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
                    }
                }
            }
        }
    }
}



@Composable
fun InitGame(showSnackBarMessage: (String) -> Unit) {
    GameBoardLayout(showSnackBarMessage)
}


@Composable
fun GameBoardLayout(
    showSnackBarMessage: (String) -> Unit,
) {

    var wordDictionary = remember { mutableListOf<WordDictionary>() }
    var wordSelectionRow by remember { mutableStateOf(0) }

    var currentRow by remember { mutableStateOf(0) }
    var currentColumn by remember { mutableStateOf(0) }
    var renderAsGuess by remember { mutableStateOf(false) }
    var checkForMatch by remember { mutableStateOf(false) }
    var matchFound by remember { mutableStateOf(false) }
    var checkIsWord by remember { mutableStateOf(false) }
    var checkGameFinish by remember { mutableStateOf(false) }
    var gameOverState by remember { mutableStateOf(false) }

    var resetGameBoard by remember { mutableStateOf(false) }
    var resetKeyboard by remember { mutableStateOf(false) }
    var rowUpdatedAllMatches by remember { mutableStateOf(false) }


    var hideWord by remember { mutableStateOf(true) }

    val currentGuess = remember {
        listOf(
            mutableStateListOf('?', '?', '?', '?', '?'),
            mutableStateListOf('?', '?', '?', '?', '?'),
            mutableStateListOf('?', '?', '?', '?', '?'),
            mutableStateListOf('?', '?', '?', '?', '?'),
            mutableStateListOf('?', '?', '?', '?', '?'),
            mutableStateListOf('?', '?', '?', '?', '?')
        )
    }


    val keyDataUpdate = remember {
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

    val scrollState = rememberScrollState()


    LaunchedEffect(resetGameBoard) {
        if (resetGameBoard) {
            gameOverState = false
            renderAsGuess = false
            checkGameFinish = false
            rowUpdatedAllMatches = false
            matchFound = false
            currentRow = 0
            currentColumn = 0

            keyDataUpdate[0] = KeyData(';', false, KeyType.ENTER)
            for (column in 1..keyDataUpdate.lastIndex) {
                keyDataUpdate[column] = KeyData('?')
            }
            for (row in 0..currentGuess.lastIndex) {
                for (column in 0..currentGuess[0].lastIndex) {
                    currentGuess[row][column] = '?'
                }
            }

            initGameBoardStates()

        }
        resetGameBoard = false

    }

    GetWordDictionary(loadWordDictionary) {
        loadWordDictionary = false
        wordDictionary.removeAll { true }
        wordDictionary.addAll(it)
    }


    @Composable
    fun UpdateTiles(
        guess: List<Char> = currentGuess[currentRow],
        asGuess: Boolean = renderAsGuess,
        gameOver: Boolean = gameOverState,
        onRenderCompleted: @Composable (Boolean) -> Unit
    ) {
        if (gameOver.not()) {
            if (asGuess) {
                val guessHits = mutableListOf<GuessHit>().apply {
                    for (i in 0..wordDictionary[wordSelectionRow].wordList.lastIndex) {
                        add(i, GuessHit(wordDictionary[wordSelectionRow].wordList[i], false))
                    }
                }
                for (column in 0..guess.lastIndex) {
                    if (guessHits[column].char == guess[column]) {
                        guessHits[column].found = true
                        setTileData(
                            currentRow,
                            column,
                            TileData(guess[column], TileKeyStatus.MATCH_IN_POSITION)
                        )
                        checkGameBoardHasMatch(guess[column]) {
                            if (it) {
                                keyDataUpdate[column] =
                                    KeyData(guess[column], status = TileKeyStatus.MATCH_IN_POSITION)
                            }
                        }
                    } else {
                        guessHits.firstOrNull { it.found.not() && it.char == guess[column] }?.also {
                            it.found = true
                            setTileData(
                                currentRow,
                                column,
                                TileData(it.char, TileKeyStatus.MATCH_OUT_POSITION)
                            )
                            checkGameBoardHasMatch(guess[column]) {
                                if (it.not()) {
                                    keyDataUpdate[column] =
                                        KeyData(
                                            guess[column],
                                            status = TileKeyStatus.MATCH_OUT_POSITION
                                        )
                                }
                            }
                        } ?: run {
                            setTileData(
                                currentRow,
                                column,
                                TileData(guess[column], TileKeyStatus.SELECTED)
                            )
                            checkGameBoardHasMatch(guess[column]) {
                                if (it.not()) {
                                    keyDataUpdate[column] =
                                        KeyData(guess[column], status = TileKeyStatus.SELECTED)
                                }
                            }
                        }
                    }
                }

            } else {
                for (column in 0..guess.lastIndex) {
                    if (guess[column] == '?') {
                        setTileData(
                            currentRow,
                            column,
                            TileData(guess[column], TileKeyStatus.EMPTY)
                        )
                    } else {
                        setTileData(
                            currentRow,
                            column,
                            TileData(guess[column], TileKeyStatus.NO_MATCH)
                        )
                    }
                }
            }

            onRenderCompleted(asGuess)
        }
    }


    @Composable
    fun UpdateEnterKey(isEnabled: Boolean = allowGuess(currentGuess[currentRow][4]).value) {
        LaunchedEffect(isEnabled) {
            keyDataUpdate[0] = KeyData(';', isEnabled, KeyType.ENTER)
        }
    }

    @Composable
    fun UpdateDeleteKey(asGuess: Boolean = renderAsGuess) {
        LaunchedEffect(asGuess) {
            keyDataUpdate[1] = KeyData(';', asGuess.not(), KeyType.DELETE)
        }
    }

    LaunchedEffect(currentGuess[currentRow][currentColumn]) {
        setCurrentColumn(currentGuess[currentRow]) {
            currentColumn = it
        }
    }



    UpdateTiles(currentGuess[currentRow], renderAsGuess) { isGuess ->
        LaunchedEffect(isGuess) {
            if (isGuess) {
                checkGameFinish = true
            }
        }
    }

    UpdateEnterKey()
    UpdateDeleteKey()
    if (wordDictionary.isNotEmpty()) {
        // Check for match
        LaunchedEffect(checkForMatch) {
            if (checkForMatch) {
                val guess = currentGuess[currentRow].joinToString("")
                val wordToMatch = wordDictionary[wordSelectionRow].wordList.joinToString("")
                if (guess == wordToMatch) {
                    matchFound = true
                    renderAsGuess = true
                } else {
                    checkIsWord = true
                }
                checkForMatch = false
            }
        }
        // Verify if guess is valid word
        LaunchedEffect(checkIsWord) {
            if (checkIsWord) {
                val guess = currentGuess[currentRow].joinToString("")
                WordlerAPI.getDictionaryDefinition(guess).also {
                    it.onSuccess {
                        // yes, show dictionary item and setup to move to next guess
                        //println("${it[0].word}: ${it[0].meanings[0].definitions[0]}")
                        renderAsGuess = true
                    }
                    it.onFailure {
                        // not a word, let user know and continue to edit same guess
                        showSnackBarMessage("Sorry, \"${guess}\" is not a word, please try again.")
                    }
                    checkIsWord = false
                }
            }
        }
    }
    LaunchedEffect(checkGameFinish, rowUpdatedAllMatches) {
        if (checkGameFinish) {
            checkGameFinish = false
            if (matchFound.not()) {
                if (currentRow == 4) {
                    gameOverState = true
                    showSnackBarMessage("GAME OVER: Sorry, you did not guess the word.")
                } else {
                    // move to next guess line
                    // reset everything
                    renderAsGuess = false
                    currentColumn = 0
                    ++currentRow
                }
            }
        }
        if (rowUpdatedAllMatches) {
            gameOverState = true
            if (rowUpdatedAllMatches) {
                showSnackBarMessage("YOU WIN, YOU GUESSED CORRECTLY!! NICE GOING!!")
                rowUpdatedAllMatches = false
            }
        }
    }
    Box(
        Modifier.fillMaxSize().verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter
    ) {
        if (isKeyBoardStateInitialized().value) {
            GameBoard(
                Modifier.fillMaxSize(33f),
                getRowData(),
            ) {
                rowUpdatedAllMatches = it
            }
        }
        Column(Modifier.align(Alignment.BottomCenter)) {
            KeyBoard(
                Modifier,
                keyDataUpdate.toList(),
                resetKeyboard,
                {
                    resetKeyboard = false
                    resetGameBoard = true
                }
            ) { keyData ->
                when (keyData.keyType) {
                    KeyType.ALPHA -> keyData.char?.also {
                        if (IntRange(0, 4).contains(currentColumn)) {
                            currentGuess[currentRow][currentColumn] = it
                        }
                    }

                    KeyType.DELETE -> {
                        if (currentColumn == 4) {
                            if (currentGuess[currentRow][currentColumn] != '?') {
                                currentGuess[currentRow][currentColumn] = '?'
                            } else {
                                currentGuess[currentRow][--currentColumn] = '?'
                            }
                        } else {
                            if (--currentColumn < 0) {
                                currentColumn = 0
                            }
                            currentGuess[currentRow][currentColumn] = '?'
                        }
                    }

                    KeyType.ENTER -> {
                        //renderAsGuess = renderAsGuess.not()
                        checkForMatch = true
                    }
                }
            }
            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button({
                    hideWord = hideWord.not()
                }) {
                    if (hideWord) {
                        Text("show word")
                    } else {
                        Text("hide word")
                    }
                }
                if (hideWord.not()) {
                    Button({
                        if (loadWordDictionary.not()) {
                            wordSelectionRow = ++wordSelectionRow % 3
                            if (wordSelectionRow == 0) {
                                loadWordDictionary = true
                            }
                        }
                        resetKeyboard = true
                        hideWord = true
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
    }
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


fun setCurrentColumn(guess: List<Char>, onCurrentColumn: (Int) -> Unit) {
    if (guess.isNotEmpty()) {
        guess.indexOf('?').also {
            if (it > -1) {
                onCurrentColumn(it)
            } else {
                onCurrentColumn(4)
            }
        }
    }
}



