package model.ui.game_pieces

import androidx.compose.ui.graphics.Color
import model.DictionaryItem

enum class TileKeyStatus {
    INITIAL_KEY, NO_MATCH, MATCH_IN_POSITION, MATCH_OUT_POSITION, EMPTY,
}

enum class PieceColor(val backGround: Color, val foreGround: Color) {
    EMPTY(Color.Black, Color.Black),
    DISABLED(Color.Gray, Color.White),
    INITIAL_KEY(Color(red = 102, 107, 100), Color.White),
    MATCH_IN_POSITION(Color(red = 70, 235, 52), Color.White),
    MATCH_OUT_POSITION(Color(red = 235, 204, 52), Color.White),
    NO_MATCH(Color.Black, Color.White);

    operator fun component1() = backGround
    operator fun component2() = foreGround

    companion object {
        fun getColor(tileKey: TileKeyData): PieceColor {
            val (status,enabled) = when (val tileKey = tileKey) {
                is TileData -> Pair(tileKey.status,true)
                is KeyData -> Pair(tileKey.status, tileKey.enabled)
            }
            return when (status) {
                TileKeyStatus.INITIAL_KEY -> if (enabled) {
                    INITIAL_KEY
                }
                else {
                    DISABLED
                }
                TileKeyStatus.EMPTY -> EMPTY
                TileKeyStatus.NO_MATCH -> NO_MATCH
                TileKeyStatus.MATCH_IN_POSITION -> MATCH_IN_POSITION
                TileKeyStatus.MATCH_OUT_POSITION -> MATCH_OUT_POSITION
            }
        }
    }
}

enum class KeyType {
    ALPHA, ENTER, DELETE
}

sealed class TileKeyData()
data class TileData(val char: Char, val status: TileKeyStatus, val columnPosition: Int = 0) :
    TileKeyData()

data class KeyData(val char: Char? = null, val enabled: Boolean = true, val keyType: KeyType = KeyType.ALPHA, val status: TileKeyStatus = TileKeyStatus.INITIAL_KEY) :
    TileKeyData()


data class RowData(val tileData: List<TileData>, val rowPosition: Int = 0)


data class WordDictionary(val wordList:List<Char> = emptyList(), val dictionaryItemList: List<DictionaryItem> = emptyList())

data class GuessHit(var char:Char, var found:Boolean)
